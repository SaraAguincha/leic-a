package pt.ulisboa.tecnico.classes.classserver;

import io.grpc.stub.StreamObserver;
import pt.ulisboa.tecnico.classes.Stringify;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions;
import pt.ulisboa.tecnico.classes.contract.student.StudentClassServer.*;
import pt.ulisboa.tecnico.classes.contract.student.StudentServiceGrpc;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    private final ClassBase classBase;

    StudentServiceImpl(ClassBase classBase){
        this.classBase = classBase;
    }

    /**
     * Function called when student uses command: list
     *
     * @param request request
     * @param responseObserver server's response
     */
    @Override
    public void listClass(ListClassRequest request, StreamObserver<ListClassResponse> responseObserver) {

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
            listResponse = ListClassResponse
                    .newBuilder()
                    .setCode(code)
                    .setClassState(classState)
                    .addAllNewPrev(List.of(classBase.getTimeVector()))
                    .build();
        }
        else {
            listResponse = ListClassResponse
                    .newBuilder()
                    .setCode(code)
                    .addAllNewPrev(List.of(classBase.getTimeVector()))
                    .build();
        }
        responseObserver.onNext(listResponse);
        responseObserver.onCompleted();

        classBase.log("------\nCommand list called by student:\n"
                + Stringify.format(code) + "\n------\n", classBase.isDebug());
    }

    /**
     * Function called when student uses command: enroll
     *
     * @param request request
     * @param responseObserver server's response
     */
    @Override
    public void enroll(EnrollRequest request, StreamObserver<EnrollResponse> responseObserver) {

        ClassesDefinitions.ResponseCode code = classBase.checkEnrolledCode(request.getStudent());

        if (code == ClassesDefinitions.ResponseCode.OK) {
            classBase.enroll(request.getStudent().getStudentId(), request.getStudent().getStudentName());

        }

        EnrollResponse enrollResponse = EnrollResponse
                .newBuilder()
                .setCode(code)
                .addAllNewPrev(List.of(classBase.getTimeVector()))
                .build();

        responseObserver.onNext(enrollResponse);
        responseObserver.onCompleted();

        classBase.log("------\nCommand enroll called by student:\n"
                + Stringify.format(code) + "\n------\n", classBase.isDebug());
    }
}
