package pt.ulisboa.tecnico.classes.classserver;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;
import pt.ulisboa.tecnico.classes.contract.classserver.ClassServerClassServer.*;
import pt.ulisboa.tecnico.classes.contract.classserver.ClassServerServiceGrpc;

import java.util.List;

public class ClassServerClassServerFrontend {

    private final ClassServerServiceGrpc.ClassServerServiceBlockingStub stub;

    ClassServerClassServerFrontend(ManagedChannel channel){stub = ClassServerServiceGrpc.newBlockingStub(channel); }

    /**
     *
     * @param classBase receives the class
     */
    public void updateState(ClassBase classBase){
        try {
            PropagateStateResponse response = stub.propagateState(PropagateStateRequest
                    .newBuilder()
                    .setClassState(ClassState.newBuilder()
                            .setCapacity(classBase.getCapacity())
                            .setOpenEnrollments(classBase.getOpenEnrollments())
                            .addAllEnrolled(classBase.getEnrolled().values())
                            .addAllDiscarded(classBase.getDiscarded().values())
                            .addAllTimeVector(List.of(classBase.getTimeVector()))
                    )
                    .build());
        }
        catch (StatusRuntimeException e){
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
        }
    }
}
