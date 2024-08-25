package pt.ulisboa.tecnico.classes.classserver;

import io.grpc.stub.StreamObserver;
import pt.ulisboa.tecnico.classes.Stringify;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;
import pt.ulisboa.tecnico.classes.contract.admin.AdminClassServer.*;
import pt.ulisboa.tecnico.classes.contract.admin.AdminServiceGrpc;

public class AdminServiceImpl extends AdminServiceGrpc.AdminServiceImplBase {

    private final ClassBase classBase;

    AdminServiceImpl(ClassBase classBase){this.classBase = classBase; }

    /**
     * Function called when admin uses command: activate
     *
     * @param request request
     * @param responseObserver server's response
     */
    @Override
    public void activate(ActivateRequest request, StreamObserver<ActivateResponse> responseObserver) {

        ResponseCode code = classBase.checkActivateCode();

        if (code == ResponseCode.OK) {
            classBase.activate();
        }
        ActivateResponse activateResponse = ActivateResponse
                .newBuilder()
                .setCode(code)
                .build();

        responseObserver.onNext(activateResponse);
        responseObserver.onCompleted();

        classBase.log("------\nCommand activate called by admin:\n"
                + Stringify.format(code) + "\n------\n", classBase.isDebug());
    }

    /**
     * Function called when admin uses command: deactivate
     *
     * @param request request
     * @param responseObserver server's response
     */
    @Override
    public void deactivate(DeactivateRequest request, StreamObserver<DeactivateResponse> responseObserver){

        ResponseCode code = classBase.checkDeactivateCode();

        if (code == ResponseCode.OK) {
            classBase.deactivate();
            classBase.deactivateGossip();
        }
        DeactivateResponse deactivateResponse = DeactivateResponse
                .newBuilder()
                .setCode(code)
                .build();

        responseObserver.onNext(deactivateResponse);
        responseObserver.onCompleted();

        classBase.log("------\nCommand deactivate called by admin:\n"
                + Stringify.format(code) + "\n------\n", classBase.isDebug());
    }

    /**
     * Function called when admin uses command: dump
     *
     * @param request request
     * @param responseObserver server's response
     */
    @Override
    public void dump(DumpRequest request, StreamObserver<DumpResponse> responseObserver){

        ResponseCode code = classBase.checkDumpCode();
        ClassState classState = ClassState.newBuilder().build();

        if (code == ResponseCode.OK)
            classState = classBase.dump();

        DumpResponse dumpResponse = DumpResponse
                .newBuilder()
                .setCode(code)
                .setClassState(classState)
                .build();

        responseObserver.onNext(dumpResponse);
        responseObserver.onCompleted();

        classBase.log("------\nCommand dump called by admin:\n"
                + Stringify.format(code) + "\n------\n", classBase.isDebug());
    }

    public void activateGossip(ActivateGossipRequest request, StreamObserver<ActivateGossipResponse> responseObserver){
        ResponseCode code = classBase.checkActivateGossipCode();

        if (code == ResponseCode.OK) {
            classBase.activateGossip();
        }

        ActivateGossipResponse activateGossipResponse = ActivateGossipResponse
                .newBuilder()
                .setCode(code)
                .build();

        responseObserver.onNext(activateGossipResponse);
        responseObserver.onCompleted();

        classBase.log("------\nCommand activate gossip called by admin:\n"
                + Stringify.format(code) + "\n------\n", classBase.isDebug());
    }

    public void deactivateGossip(DeactivateGossipRequest request, StreamObserver<DeactivateGossipResponse> responseObserver){
        ResponseCode code = classBase.checkDeactivateGossipCode();

        if (code == ResponseCode.OK) {
            classBase.deactivateGossip();
        }

        DeactivateGossipResponse deactivateGossipResponse = DeactivateGossipResponse
                .newBuilder()
                .setCode(code)
                .build();

        responseObserver.onNext(deactivateGossipResponse);
        responseObserver.onCompleted();

        classBase.log("------\nCommand deactivate gossip called by admin:\n"
                + Stringify.format(code) + "\n------\n", classBase.isDebug());
    }

    public void gossip(GossipRequest request, StreamObserver<GossipResponse> responseObserver){
        ResponseCode code = classBase.checkGossipCode();

        if (code == ResponseCode.OK) {
            classBase.gossip();
        }

        GossipResponse gossipResponse = GossipResponse
                .newBuilder()
                .setCode(code)
                .build();

        responseObserver.onNext(gossipResponse);
        responseObserver.onCompleted();

        classBase.log("------\nCommand gossip called by admin:\n"
                + Stringify.format(code) + "\n------\n", classBase.isDebug());
    }
}
