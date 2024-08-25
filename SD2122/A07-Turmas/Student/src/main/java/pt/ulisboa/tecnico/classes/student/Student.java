package pt.ulisboa.tecnico.classes.student;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pt.ulisboa.tecnico.classes.LookupNamingFrontend;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Student {

  private static final String ENROLL_CMD = "enroll";
  private static final String LIST_CMD = "list";
  private static final String EXIT_CMD = "exit";

  private static final ArrayList<String> PRIMARY_ONLY = new ArrayList<>(Arrays.asList("P"));
  private static final ArrayList<String> ANY = new ArrayList<>(Arrays.asList("P", "S"));

  /**
   * main function for Student
   *
   * @param args alunoxxxx studentname
   */
  public static void main(String[] args) throws InterruptedException {

    // check arguments
    if (args.length < 2) {
      System.out.println("Argument(s) missing!");
      System.out.println("Usage: java aluno id nomeAluno");
      return;
    }

    final String namingHost = "localhost";
    final int namingPort = 5000;

    boolean debug = Objects.equals(args[0], "-debug");

    // Parsing arguments
    int arg_index = 0;
    if (Objects.equals(args[0], "-debug")) {
      arg_index = 1;
    }
    String student_id = args[arg_index];

    if (student_id.startsWith("aluno")) {
      try {
        Integer number = Integer.parseInt(student_id.substring(5));
        if (number.toString().length() != 4){
          System.out.println("Student number doesn't have 4 digits");
          return;
        }
      } catch (NumberFormatException e) {
        System.out.println("ID does not end in a number");
        return;
      }
    } else {
      System.out.println("ID does not start with \"aluno\"");
      return;
    }

    String[] nameArgs = Arrays.copyOfRange(args, 1 + arg_index, args.length);
    StringBuilder nameBuilder = new StringBuilder();

    Arrays.stream(nameArgs).forEach(arg -> nameBuilder.append(arg).append(" "));

    //Removes last space from student's name
    String name = nameBuilder.substring(0, nameBuilder.length() - 1);

    Pattern pattern = Pattern.compile("[A-Za-z\\s]*");
    Matcher matcher = pattern.matcher(name);

    if (!matcher.matches() || name.length() > 30 || name.length() < 3) {
      System.out.println("Name has wrong formatting");
      return;
    }

    Scanner scanner = new Scanner(System.in);

    ArrayList<Integer> prev = new ArrayList<>(Arrays.asList(0, 0));

    while (true) {
      System.out.printf("%n> ");

      //NamingServer
      ManagedChannel namingChannel = ManagedChannelBuilder.forAddress(namingHost, namingPort).usePlaintext().build();

      LookupNamingFrontend lookupNamingFrontend = new LookupNamingFrontend(namingChannel);

      ArrayList<String> response = new ArrayList<>();

      // accepts input from the student
      String line = scanner.nextLine();

      boolean success = false;
      int tries = 3;

      while (!success && tries > 0) {
        tries--;
        // checks what command has been inputted
        switch (line) {
          case ENROLL_CMD, LIST_CMD -> response = lookupNamingFrontend.lookup(ANY);
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
        StudentClassServerFrontend studentClassServerFrontend = new StudentClassServerFrontend(channel,debug);

        // checks what command has been inputted
        switch (line) {
          case ENROLL_CMD -> success = studentClassServerFrontend.enrollCommand(student_id, name, prev);
          case LIST_CMD -> success = studentClassServerFrontend.listCommand(prev);
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
