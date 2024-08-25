package pt.ulisboa.tecnico.classes.admin;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pt.ulisboa.tecnico.classes.LookupNamingFrontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Admin {

  private static final String ACTIVATE_CMD = "activate";
  private static final String DEACTIVATE_CMD = "deactivate";

  private static final String ACTIVATE_GOSSIP_CMD = "activateGossip";

  private static final String DEACTIVATE_GOSSIP_CMD = "deactivateGossip";

  private static final String GOSSIP_CMD = "gossip";
  private static final String DUMP_CMD = "dump";
  private static final String EXIT_CMD = "exit";

  private static final ArrayList<String> PRIMARY_ONLY = new ArrayList<>(Arrays.asList("P"));

  private static final ArrayList<String> ANY = new ArrayList<>(Arrays.asList("P", "S"));


  /**
   * main function for Admin
   *
   * @param args no args are expected
   */
  public static void main(String[] args) {

    final String namingHost = "localhost";
    final int namingPort = 5000;
    boolean debug = false;

    // sets the debug flag true if its given has an argument
    if (args.length == 1)
      debug = Objects.equals(args[0], "-debug");

    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.printf("%n> ");

      ManagedChannel namingChannel = ManagedChannelBuilder.forAddress(namingHost, namingPort).usePlaintext().build();
      LookupNamingFrontend lookupNamingFrontend = new LookupNamingFrontend(namingChannel);

      ArrayList<String> response = ANY;

      // accepts input from the admin
      String line = scanner.nextLine();
      String[] command = line.split(" ");

      String serverType = "P";
      if (command.length > 1) {
        serverType = command[1];
      }
      switch (command[0]) {
        case ACTIVATE_CMD, DEACTIVATE_CMD, ACTIVATE_GOSSIP_CMD,
                DEACTIVATE_GOSSIP_CMD, GOSSIP_CMD, DUMP_CMD -> {
          response = lookupNamingFrontend.lookup(
                  new ArrayList<>(
                          Arrays.asList(serverType)
                  ));
        }
        case EXIT_CMD -> System.exit(0);
        default -> {
          System.out.println("Command not found.");
          continue;
        }
      }

      // if there is no server available
      if (response.isEmpty()) {
        System.out.println("No server available, try again later!");
        continue;
      }

      ManagedChannel channel = ManagedChannelBuilder.forAddress(response.get(0), Integer.parseInt(response.get(1))).usePlaintext().build();
      AdminClassServerFrontend adminClassServerFrontend = new AdminClassServerFrontend(channel, debug);

      // checks what command has been inputted
      switch (command[0]) {
        case ACTIVATE_CMD -> adminClassServerFrontend.activateCommand();
        case DEACTIVATE_CMD -> adminClassServerFrontend.deactivateCommand();
        case ACTIVATE_GOSSIP_CMD -> adminClassServerFrontend.activateGossipCommand();
        case DEACTIVATE_GOSSIP_CMD -> adminClassServerFrontend.deactivateGossipCommand();
        case GOSSIP_CMD -> adminClassServerFrontend.gossipCommand();
        case DUMP_CMD -> adminClassServerFrontend.dumpCommand();
      }
      namingChannel.shutdownNow();
      channel.shutdownNow();
    }
  }
}
