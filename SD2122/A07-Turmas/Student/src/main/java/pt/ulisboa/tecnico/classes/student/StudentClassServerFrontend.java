package pt.ulisboa.tecnico.classes.student;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import pt.ulisboa.tecnico.classes.Stringify;
import pt.ulisboa.tecnico.classes.contract.student.StudentClassServer.*;
import pt.ulisboa.tecnico.classes.contract.student.StudentServiceGrpc;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.ResponseCode;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class StudentClassServerFrontend {

    private final StudentServiceGrpc.StudentServiceBlockingStub stub;
    private final boolean debug;

    public boolean isDebug() {
        return debug;
    }

    StudentClassServerFrontend(ManagedChannel channel, boolean debug){
        stub = StudentServiceGrpc.newBlockingStub(channel);
        this.debug = debug;
    }

    /**
     * Function called when student uses command: list
     *
     * @param prev of the client
     *
     * @return true if the request was successful
     */
    public boolean listCommand(ArrayList<Integer> prev){

        try {
            ListClassResponse response = stub.listClass(ListClassRequest.newBuilder().addAllPrev(prev).build());


            // Updates the students time vector
            for (int i = 0; i < prev.size(); i++){
                prev.set(i, response.getNewPrevList().get(i));
            }

            if (response.getCode() == ResponseCode.OK){
                System.out.println(Stringify.format(response.getClassState()));
            }

            log("------\nCommand list called by student:\n"
                    + Stringify.format(response.getCode()) + "\n------\n",isDebug());

            return response.getCode() != ResponseCode.INACTIVE_SERVER;
        }
        catch (StatusRuntimeException e){
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Function called when student uses command: enroll
     *
     * @param studentId to enroll
     * @param studentName to enroll
     * @param prev of the client
     *
     * @return true if the request was successful
     */
    public boolean enrollCommand(String studentId, String studentName,ArrayList<Integer> prev){
        try {
            ClassesDefinitions.Student student = ClassesDefinitions.Student
                    .newBuilder()
                    .setStudentId(studentId)
                    .setStudentName(studentName)
                    .build();
            EnrollResponse response = stub.enroll(EnrollRequest.newBuilder().setStudent(student).addAllPrev(prev).build());

            // Updates the students time vector
            for (int i = 0; i < prev.size(); i++){
                prev.set(i, response.getNewPrevList().get(i));
            }

            log("------\nCommand enroll called by student:\n"
                    + Stringify.format(response.getCode()) + "\n------\n",isDebug());

            return response.getCode() != ResponseCode.INACTIVE_SERVER;
        }
        catch (StatusRuntimeException e){
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param message to be printed
     * @param print boolean true if the debug is active
     */
    public void log(String message, boolean print){
        if (print)
            System.out.println(message);
    }
}
