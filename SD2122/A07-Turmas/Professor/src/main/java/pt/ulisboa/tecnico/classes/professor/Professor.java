package pt.ulisboa.tecnico.classes.professor;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pt.ulisboa.tecnico.classes.LookupNamingFrontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Professor{

  private static final String OPEN_CMD = "openEnrollments";
  private static final String CLOSE_CMD = "closeEnrollments";
  private static final String LIST_CMD = "list";
  private static final String CANCEL_CMD = "cancelEnrollment";
  private static final String EXIT_CMD = "exit";

  private static final ArrayList<String> PRIMARY_ONLY = new ArrayList<>(Arrays.asList("P"));

  private static final ArrayList<String> ANY = new ArrayList<>(Arrays.asList("P", "S"));


  /**
   * main function for Professor
   *
   * @param args no args are expected
   */
  public static void main(String[] args) throws InterruptedException {

    // port and host hardcoded
    final String namingHost = "localhost";
    final int namingPort = 5000;
    boolean debug = false;

    // sets the debug flag true if its given has an argument
    if (args.length == 1)
      debug = Objects.equals(args[0], "-debug");

    Scanner scanner = new Scanner(System.in);

    ArrayList<Integer> prev = new ArrayList<>(Arrays.asList(0, 0));

    while (true) {
      System.out.printf("%n> ");


      ManagedChannel namingChannel = ManagedChannelBuilder.forAddress(namingHost, namingPort).usePlaintext().build();
      LookupNamingFrontend lookupNamingFrontend = new LookupNamingFrontend(namingChannel);

      ArrayList<String> response = ANY;

      // accepts input from the professor
      String line = scanner.nextLine();
      String[] command = line.split(" ");

      boolean success = false;
      int tries = 3;

      while (!success && tries > 0) {
        tries--;
        switch (command[0]) {
          case LIST_CMD -> response = lookupNamingFrontend.lookup(ANY);
          case OPEN_CMD, CANCEL_CMD -> {
            if (command.length <= 1) {
              System.out.println("Wrong number of arguments.");
              tries = 0;
              continue;
            }
            response = lookupNamingFrontend.lookup(PRIMARY_ONLY);
          }
          case CLOSE_CMD -> response = lookupNamingFrontend.lookup(PRIMARY_ONLY);
          case EXIT_CMD -> System.exit(0);
          default -> {
            System.out.println("Command not found.");
            tries = 0;
            continue;
          }
        }

        // if there is no server available
        if (response.isEmpty()) {
          System.out.println("No server available, try again later!");
          tries = 0;
          continue;
        }

        ManagedChannel channel = ManagedChannelBuilder.forAddress(response.get(0), Integer.parseInt(response.get(1))).usePlaintext().build();
        ProfessorClassServerFrontend professorClassServerFrontend = new ProfessorClassServerFrontend(channel, debug);

        // checks what command has been inputted
        switch (command[0]) {
          case LIST_CMD -> success = professorClassServerFrontend.listCommand(prev);
          case OPEN_CMD -> success = professorClassServerFrontend.openEnrollmentsCommand(Integer.parseInt(command[1]),prev);
          case CLOSE_CMD -> success = professorClassServerFrontend.closeEnrollmentsCommand(prev);
          case CANCEL_CMD -> success = professorClassServerFrontend.cancelEnrollmentCommand(command[1],prev);
        }

        if (!success){
          System.out.println("Retrying...");
          TimeUnit.SECONDS.sleep(1);
        };
        channel.shutdownNow();
      }
      namingChannel.shutdownNow();
    }
  }
}
