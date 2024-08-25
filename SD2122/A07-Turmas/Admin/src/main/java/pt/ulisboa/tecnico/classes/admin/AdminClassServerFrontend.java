package pt.ulisboa.tecnico.classes.admin;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import pt.ulisboa.tecnico.classes.Stringify;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;
import pt.ulisboa.tecnico.classes.contract.admin.AdminClassServer.*;
import pt.ulisboa.tecnico.classes.contract.admin.AdminServiceGrpc;

public class AdminClassServerFrontend {

    private final AdminServiceGrpc.AdminServiceBlockingStub stub;
    private final boolean debug;

    public boolean isDebug() {
        return debug;
    }

    AdminClassServerFrontend(ManagedChannel channel, boolean debug){
        stub = AdminServiceGrpc.newBlockingStub(channel);
        this.debug = debug;
    }
    /**
     * Function called when admin uses command: activate
     */
    public void activateCommand(){
        try {
            ActivateResponse response = stub.activate(ActivateRequest.newBuilder().build());

            log("------\nCommand activate called by admin:\n"
                    + Stringify.format(response.getCode()) + "\n------\n",isDebug());
        }
        catch (StatusRuntimeException e){
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
        }
    }

    /**
     * Function called when admin uses command: deactivate
     */
    public void deactivateCommand(){
        try {
            DeactivateResponse response = stub.deactivate(DeactivateRequest.newBuilder().build());

            log("------\nCommand deactivate called by admin:\n"
                    + Stringify.format(response.getCode()) + "\n------\n",isDebug());
        }
        catch (StatusRuntimeException e){
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
        }
    }

    /**
     * Function called when admin uses command: dump
     */
    public void dumpCommand(){
        try {
            DumpResponse response = stub.dump(DumpRequest.newBuilder().build());

            if (response.getCode() == ResponseCode.OK) {
                System.out.println(Stringify.format(response.getClassState()));
            }

            log("------\nCommand dump called by admin:\n"
                    + Stringify.format(response.getCode()) + "\n------\n",isDebug());
        }
        catch (StatusRuntimeException e){
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
        }
    }

    /**
     * Function called when admin uses command: activateGossip
     */
    public void activateGossipCommand(){
        try {
            ActivateGossipResponse response = stub.activateGossip(ActivateGossipRequest.newBuilder().build());

            log("------\nCommand activateGossip called by admin:\n"
                    + Stringify.format(response.getCode()) + "\n------\n",isDebug());
        }
        catch(StatusRuntimeException e) {
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
        }
    }

    /**
     * Function called when admin uses command: deactivateGossip
     */
    public void deactivateGossipCommand(){
        try {
            DeactivateGossipResponse response = stub.deactivateGossip(DeactivateGossipRequest.newBuilder().build());

            log("------\nCommand deactivateGossip called by admin:\n"
                    + Stringify.format(response.getCode()) + "\n------\n",isDebug());
        }
        catch(StatusRuntimeException e) {
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
        }
    }

    /**
     * Function called when admin uses command: gossip
     */
    public void gossipCommand(){
        try {
            GossipResponse response = stub.gossip(GossipRequest.newBuilder().build());

            log("------\nCommand gossip called by admin:\n"
                    + Stringify.format(response.getCode()) + "\n------\n",isDebug());
        }
        catch(StatusRuntimeException e) {
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
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
