package pt.ulisboa.tecnico.classes.classserver;

import io.grpc.stub.StreamObserver;
import pt.ulisboa.tecnico.classes.Stringify;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;
import pt.ulisboa.tecnico.classes.contract.classserver.ClassServerClassServer.*;
import pt.ulisboa.tecnico.classes.contract.classserver.ClassServerServiceGrpc;

public class ClassServerServiceImpl extends ClassServerServiceGrpc.ClassServerServiceImplBase {

    private final ClassBase classBase;

    ClassServerServiceImpl(ClassBase classBase){this.classBase = classBase; }

    /**
     * Propagates the server (primary) state, to the other servers
     * when the timer ticks
     *
     * @param request request
     * @param responseObserver response
     */
    public void propagateState(PropagateStateRequest request, StreamObserver<PropagateStateResponse> responseObserver){

        ResponseCode code = classBase.updateState(request.getClassState());
        PropagateStateResponse propagateStateResponse;

        propagateStateResponse = PropagateStateResponse
                .newBuilder()
                .setCode(code)
                .build();
        responseObserver.onNext(propagateStateResponse);
        responseObserver.onCompleted();

    }
}
