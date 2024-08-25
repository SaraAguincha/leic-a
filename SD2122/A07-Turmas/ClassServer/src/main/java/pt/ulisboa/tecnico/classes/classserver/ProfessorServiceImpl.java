package pt.ulisboa.tecnico.classes.classserver;

import io.grpc.stub.StreamObserver;
import pt.ulisboa.tecnico.classes.Stringify;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorClassServer;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorClassServer.*;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorServiceGrpc;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class ProfessorServiceImpl extends ProfessorServiceGrpc.ProfessorServiceImplBase {

    private final ClassBase classBase;

    ProfessorServiceImpl(ClassBase classBase) {
        this.classBase = classBase;
    }

    /**
     * Function called when professor uses command: list
     *
     * @param request request
     * @param responseObserver server's response
     */
    @Override
    public void listClass(ListClassRequest request, StreamObserver<ListClassResponse> responseObserver){
        ClassesDefinitions.ResponseCode code = classBase.checkListClassCode();

        ClassesDefinitions.ClassState classState;
        ListClassResponse listResponse;

        if (code == ClassesDefinitions.ResponseCode.OK) {

            // Waits for the server to receive a gossip, if a certain amount of time passes without gossip
            // Send response to client nonetheless
            for (int i = 0; i < request.getPrevList().size(); i++ ) {
                if (request.getPrevList().get(i) > classBase.getTimeVector()[i]){
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    classBase.log("Server outdated... \nWaiting for updates...", classBase.isDebug());
                    break;
                }
            }

            classState = classBase.listClass();
            listResponse = ProfessorClassServer.ListClassResponse
                    .newBuilder()
                    .setCode(code)
                    .setClassState(classState)
                    .addAllNewPrev(List.of(classBase.getTimeVector()))
                    .build();
        }
        else {
            listResponse = ProfessorClassServer.ListClassResponse
                    .newBuilder()
                    .setCode(code)
                    .addAllNewPrev(List.of(classBase.getTimeVector()))
                    .build();
        }
        responseObserver.onNext(listResponse);
        responseObserver.onCompleted();

        classBase.log("------\nCommand list called by professor:\n"
                + Stringify.format(code) + "\n------\n", classBase.isDebug());
    }

    /**
     * Function called when professor uses command: openEnrollments
     *
     * @param request request
     * @param responseObserver server's response
     */
    @Override
    public void openEnrollments(OpenEnrollmentsRequest request, StreamObserver<OpenEnrollmentsResponse> responseObserver) {
        ClassesDefinitions.ResponseCode code = classBase.checkOpenEnrollmentsCode(request.getCapacity());

        if (code == ClassesDefinitions.ResponseCode.OK)
            classBase.openEnrollments(request.getCapacity());

        OpenEnrollmentsResponse openEnrollmentsResponse = OpenEnrollmentsResponse
                .newBuilder()
                .setCode(code)
                .addAllNewPrev(List.of(classBase.getTimeVector()))
                .build();

        responseObserver.onNext(openEnrollmentsResponse);
        responseObserver.onCompleted();

        classBase.log("------\nCommand openEnrollments called by professor:\n"
                + Stringify.format(code) + "\n------\n", classBase.isDebug());
    }

    /**
     * Function called when professor uses command: closeEnrollments
     *
     * @param request request
     * @param responseObserver server's response
     */
    @Override
    public void closeEnrollments(CloseEnrollmentsRequest request, StreamObserver<CloseEnrollmentsResponse> responseObserver) {
        ClassesDefinitions.ResponseCode code = classBase.checkCloseEnrollmentsCode();

        if (code == ClassesDefinitions.ResponseCode.OK)
            classBase.closeEnrollments();

        CloseEnrollmentsResponse closeEnrollmentsResponse = CloseEnrollmentsResponse
                .newBuilder()
                .setCode(code)
                .addAllNewPrev(List.of(classBase.getTimeVector()))
                .build();

        responseObserver.onNext(closeEnrollmentsResponse);
        responseObserver.onCompleted();

        classBase.log("------\nCommand closeEnrollments called by professor:\n"
                + Stringify.format(code) + "\n------\n", classBase.isDebug());
    }

    /**
     * Function called when professor uses command: cancelEnrollment
     *
     * @param request request
     * @param responseObserver server's response
     */
    @Override
    public void cancelEnrollment(CancelEnrollmentRequest request, StreamObserver<CancelEnrollmentResponse> responseObserver) {
        ClassesDefinitions.ResponseCode code = classBase.checkCancelEnrollmentCode(request.getStudentId());

        if (code == ClassesDefinitions.ResponseCode.OK)
            classBase.cancelEnrollment(request.getStudentId());

        CancelEnrollmentResponse cancelEnrollmentResponse = CancelEnrollmentResponse
                .newBuilder()
                .setCode(code)
                .addAllNewPrev(List.of(classBase.getTimeVector()))
                .build();

        responseObserver.onNext(cancelEnrollmentResponse);
        responseObserver.onCompleted();

        classBase.log("------\nCommand cancelEnrollment called by professor:\n"
                + Stringify.format(code) + "\n------\n", classBase.isDebug());
    }

}

