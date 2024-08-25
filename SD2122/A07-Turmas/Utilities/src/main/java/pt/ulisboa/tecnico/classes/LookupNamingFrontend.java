package pt.ulisboa.tecnico.classes;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import pt.ulisboa.tecnico.classes.contract.naming.ClassServerNamingServer.*;
import pt.ulisboa.tecnico.classes.contract.naming.NamingServerServiceGrpc;

import java.util.ArrayList;

public class LookupNamingFrontend {

    private final NamingServerServiceGrpc.NamingServerServiceBlockingStub stub;

    public LookupNamingFrontend(ManagedChannel channel){
        stub = NamingServerServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Function called when a client or a server needs a server entry data
     * from the naming server database
     *
     * @param qualifiers server qualifiers
     * @return an array with the host (0) and port (1)
     */
    public ArrayList<String> lookup(ArrayList<String> qualifiers){

        ArrayList<String> arguments = new ArrayList<>();

        try {
            LookupResponse response = stub.lookup(LookupRequest.newBuilder()
                    .setService("turmas")
                    .addAllQualifiers(qualifiers)
                    .build());

            if (response.getAvailable()) {
                arguments.add(response.getHost());
                arguments.add(Integer.toString(response.getPort()));
            }
        }
        catch (StatusRuntimeException e){
            System.err.println("Caught StatusRuntimeException with message: " + e.getMessage());
        }

        return arguments;
    }

}
