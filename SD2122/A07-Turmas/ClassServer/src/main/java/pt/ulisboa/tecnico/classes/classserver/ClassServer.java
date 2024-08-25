package pt.ulisboa.tecnico.classes.classserver;

import io.grpc.*;
import pt.ulisboa.tecnico.classes.LookupNamingFrontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.TimerTask;

public class ClassServer {

  /**
   * main function for Server
   *
   * @param args (-debug) >serviceName< >host< >port< >qualifiers<
   */
  public static void main(String[] args) {

    // Print the received arguments
    System.out.println(ClassServer.class.getSimpleName());
    System.out.printf("Received %d Argument(s)%n", args.length);
    for (int i = 0; i < args.length; i++) {
      System.out.printf("args[%d] = %s%n", i, args[i]);
    }

    // Check number arguments.
    if (args.length < 4) {
      System.err.println("Argument(s) missing!");
      System.err.println("Usage: serviceName host port qualifiers");
      return;
    }

    // Parsing arguments
    int arg_index = 0;
    if (Objects.equals(args[0], "-debug"))
      arg_index = 1;
    String serviceName = args[arg_index];
    String host = args[1 + arg_index];
    int port = Integer.parseInt(args[2 + arg_index]);
    ArrayList<String> qualifiers;
    qualifiers = new ArrayList<String>(Arrays.asList
            (Arrays.copyOfRange(
                    args, 3 + arg_index, args.length))
    );

    // Connection to Naming Server
    final String namingServerHost = "localhost";
    final int namingServerPort = 5000;

    final ManagedChannel channel = ManagedChannelBuilder.forAddress(namingServerHost, namingServerPort).usePlaintext().build();
    ClassServerNamingFrontend classServerNamingFrontend = new ClassServerNamingFrontend(channel);

    final ManagedChannel lookupChannel = ManagedChannelBuilder.forAddress(namingServerHost, namingServerPort).usePlaintext().build();
    LookupNamingFrontend lookupNamingFrontend = new LookupNamingFrontend(lookupChannel);

    // Build Server
    ClassBase classBase = new ClassBase();

    // Updates debug flag in classBase
    if (Objects.equals(args[0], "-debug"))
      classBase.setDebug(true);

    final BindableService studentImpl = new StudentServiceImpl(classBase);
    final BindableService professorImpl = new ProfessorServiceImpl(classBase);
    final BindableService adminImpl = new AdminServiceImpl(classBase);
    final BindableService classServerImpl = new ClassServerServiceImpl(classBase);

    // Create a new server to listen on port.
    Server server = ServerBuilder.forPort(port)
            .addService(studentImpl)
            .addService(professorImpl)
            .addService(adminImpl)
            .addService(classServerImpl)
            .build();

    try {
      // Start the server.
      server.start();

      // Register on the Naming Server
      classServerNamingFrontend.register(serviceName, host, port, qualifiers);

      if (qualifiers.contains("P")){
        classBase.setQualifier("P");
        classBase.setVectorEntry(0);
      }
      else if (qualifiers.contains("S")){
        classBase.setQualifier("S");
        classBase.setVectorEntry(1);
      }

      // Server threads are running in the background.
      System.out.println("Server started");

      // Catch if the server terminated forcefully
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        classServerNamingFrontend.delete(serviceName, host, port);
        System.out.println("Server Terminated ...");
        server.shutdownNow();
      }));

      // Propagate state timer
      TimerTask task = new TimerTask() {
        @Override
        public void run() {

          if (classBase.isGossip() || classBase.isForceGossip()) {

            ArrayList<String> secondaryDetails = null;

            // Request the secondary server port and host to the naming server
            if (qualifiers.contains("P")) {
              secondaryDetails = lookupNamingFrontend.lookup(new ArrayList<>(Arrays.asList("S")));
            } else if (qualifiers.contains("S")) {
              secondaryDetails = lookupNamingFrontend.lookup(new ArrayList<>(Arrays.asList("P")));
            }

            if (secondaryDetails == null || secondaryDetails.isEmpty())
              return;

            System.out.println("Propagating state to host " + secondaryDetails.get(0) + " and port " + secondaryDetails.get(1));

            final ManagedChannel classServerChannel = ManagedChannelBuilder
                    .forAddress(
                            secondaryDetails.get(0),
                            Integer.parseInt(secondaryDetails.get(1)))
                    .usePlaintext()
                    .build();

            ClassServerClassServerFrontend classServerClassServerFrontend = new ClassServerClassServerFrontend(classServerChannel);
            classServerClassServerFrontend.updateState(classBase);

            classServerChannel.shutdownNow();
          }

        }
      };

      classBase.setGossipTask(task);

      // Timer default is 10 seconds to propagate its state
      classBase.getTimer().scheduleAtFixedRate(task, 2000, 2000);

      // Do not exit the main thread. Wait until server is terminated.
      server.awaitTermination();
    }
    catch (IOException e){
      System.err.println("Caught IOException with message: " + e.getMessage());
    }
    catch (InterruptedException e){
      System.err.println("Caught InterruptedException with message: " + e.getMessage());
    }
    catch (StatusRuntimeException e){
      System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
    }
    finally {
      System.exit(0);
    }

  }
}
