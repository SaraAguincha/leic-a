package pt.ulisboa.tecnico.classes.namingserver;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.StatusRuntimeException;

import java.io.IOException;
import java.util.Objects;

public class NamingServer {

  public static void main(String[] args) throws IOException, InterruptedException {

    NamingServices namingServices = new NamingServices();

    System.out.printf("Received %d Argument(s)%n", args.length);
    for (int i = 0; i < args.length; i++) {
      System.out.printf("args[%d] = %s%n", i, args[i]);
    }

    final int port = 5000;
    boolean debug = false;

    if (args.length == 1)
      debug = Objects.equals(args[0], "-debug");

    final BindableService namingImpl = new NamingServerServiceImpl(namingServices, debug);

    Server server = ServerBuilder
            .forPort(port)
            .addService(namingImpl)
            .build();

    try {
      // Start the server.
      server.start();
      // Server threads are running in the background.
      System.out.println("Naming server started in port " + port);

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
