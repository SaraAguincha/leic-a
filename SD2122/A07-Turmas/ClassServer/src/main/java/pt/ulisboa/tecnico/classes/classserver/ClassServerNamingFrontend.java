package pt.ulisboa.tecnico.classes.classserver;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import pt.ulisboa.tecnico.classes.contract.naming.ClassServerNamingServer;
import pt.ulisboa.tecnico.classes.contract.naming.NamingServerServiceGrpc;

import java.util.ArrayList;

public class ClassServerNamingFrontend {

    private final NamingServerServiceGrpc.NamingServerServiceBlockingStub stub;

    ClassServerNamingFrontend(ManagedChannel channel) {stub = NamingServerServiceGrpc.newBlockingStub(channel); }

    /**
     * Function called when a new Server turmas starts
     * Register the Server turmas in the Naming server
     */
    public void register(String service, String host, int port, ArrayList<String> qualifiers){
        try {
            ClassServerNamingServer.RegisterResponse response = stub.register(ClassServerNamingServer.RegisterRequest.newBuilder()
                    .setService(service)
                    .setHost(host)
                    .setPort(port)
                    .addAllQualifiers(qualifiers)
                    .build());
        }
        catch (StatusRuntimeException e){
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
        }
    }

    /**
     * Function called when a new Server turmas is terminated
     * Deletes the entry og the Server turmas in the Naming server
     */
    public void delete(String service, String host, int port){
        try {
            ClassServerNamingServer.DeleteResponse response = stub.delete(ClassServerNamingServer.DeleteRequest.newBuilder()
                    .setService(service)
                    .setHost(host)
                    .setPort(port)
                    .build());
        }
        catch (StatusRuntimeException e){
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
        }
    }

}
