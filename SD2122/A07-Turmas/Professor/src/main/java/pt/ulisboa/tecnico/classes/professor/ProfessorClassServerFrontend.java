package pt.ulisboa.tecnico.classes.professor;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import pt.ulisboa.tecnico.classes.Stringify;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorClassServer.*;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorServiceGrpc;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;

import java.util.ArrayList;

public class ProfessorClassServerFrontend {

    private final ProfessorServiceGrpc.ProfessorServiceBlockingStub stub;
    private final boolean debug;

    public boolean isDebug() {
        return debug;
    }

    ProfessorClassServerFrontend(ManagedChannel channel, boolean debug){
        stub = ProfessorServiceGrpc.newBlockingStub(channel);
        this.debug = debug;
    }

    /**
     * Function called when professor uses command: list
     *
     * @param prev of the client
     *
     * @return true if the request was successful
     */
    public boolean listCommand(ArrayList<Integer> prev){
        try {
            ListClassResponse response = stub.listClass(ListClassRequest.newBuilder().addAllPrev(prev).build());

            // Updates the professor time vector
            for (int i = 0; i < prev.size(); i++){
                prev.set(i, response.getNewPrevList().get(i));
            }

            if (response.getCode() == ResponseCode.OK)
                System.out.println(Stringify.format(response.getClassState()));

            log("------\nCommand list called by professor:\n"
                    + Stringify.format(response.getCode()) + "\n------\n",isDebug());

            return response.getCode() != ResponseCode.INACTIVE_SERVER;
        }
        catch (StatusRuntimeException e){
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Function called when professor uses command: openEnrollments
     *
     * @param prev of the client
     * @param capacity class's capacity to set
     *
     * @return true if the request was successful
     */
    public boolean openEnrollmentsCommand(int capacity, ArrayList<Integer> prev) {
        try {
            OpenEnrollmentsResponse response = stub.openEnrollments(OpenEnrollmentsRequest.newBuilder().setCapacity(capacity).addAllPrev(prev).build());

            // Updates the professor time vector
            for (int i = 0; i < prev.size(); i++){
                prev.set(i, response.getNewPrevList().get(i));
            }

            log("------\nCommand openEnrollments called by professor:\n"
                    + Stringify.format(response.getCode()) + "\n------\n",isDebug());

            return response.getCode() != ResponseCode.INACTIVE_SERVER;
        }
        catch (StatusRuntimeException e){
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Function called when professor uses command: closeEnrollments
     *
     * @param prev of the client
     *
     * @return true if the request was successful
     */
    public boolean closeEnrollmentsCommand(ArrayList<Integer> prev) {
        try {
            CloseEnrollmentsResponse response = stub.closeEnrollments(CloseEnrollmentsRequest.newBuilder().addAllPrev(prev).build());

            // Updates the professor time vector
            for (int i = 0; i < prev.size(); i++){
                prev.set(i, response.getNewPrevList().get(i));
            }

            log("------\nCommand closeEnrollments called by professor:\n"
                    + Stringify.format(response.getCode()) + "\n------\n",isDebug());

            return response.getCode() != ResponseCode.INACTIVE_SERVER;
        }
        catch (StatusRuntimeException e){
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Function called when professor uses command: closeEnrollments
     *
     * @param studentId to cancel
     * @param prev of the client
     *
     * @return true if the request was successful
     */
    public boolean cancelEnrollmentCommand(String studentId, ArrayList<Integer> prev) {
        try {
            CancelEnrollmentResponse response = stub.cancelEnrollment(CancelEnrollmentRequest.newBuilder().setStudentId(studentId).addAllPrev(prev).build());

            // Updates the professor time vector
            for (int i = 0; i < prev.size(); i++){
                prev.set(i, response.getNewPrevList().get(i));
            }

            log("------\nCommand cancelEnrollment called by professor:\n"
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
