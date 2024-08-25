package pt.ulisboa.tecnico.classes.namingserver;

import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import pt.ulisboa.tecnico.classes.contract.naming.ClassServerNamingServer.*;
import pt.ulisboa.tecnico.classes.contract.naming.NamingServerServiceGrpc;

import java.util.ArrayList;

public class NamingServerServiceImpl extends NamingServerServiceGrpc.NamingServerServiceImplBase {

    private final NamingServices namingServices;

    private final boolean debug;

    public boolean isDebug() {
        return debug;
    }

    NamingServerServiceImpl(NamingServices namingServices, boolean debug){
        this.namingServices = namingServices;
        this.debug = debug;
    }

    /**
     * Function called when Server starts
     * Register its data in the naming server
     *
     * @param request request
     * @param responseObserver response
     */
    @Override
    public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver){

        ArrayList<String> qualifiersList = new ArrayList<>(request.getQualifiersList());

        ServerEntry serverEntry = new ServerEntry(request.getHost(), request.getPort(), qualifiersList);

        namingServices.addToServerList(request.getService(), serverEntry);

        RegisterResponse registerResponse = RegisterResponse.newBuilder().build();

        responseObserver.onNext(registerResponse);
        responseObserver.onCompleted();

        log("------\nA server has been registered:\nService Name: "
                + request.getService() + "\n"
                + "Server Host: " + serverEntry.getHost() + "\nServer Port: " + serverEntry.getPort()
                + "\n------\n",isDebug());
    }

    /**
     * Function called when Server is terminated
     * Deletes its data from the naming server
     *
     * @param request request
     * @param responseObserver response
     */
    @Override
    public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver){

        namingServices.removeServer(request.getService(), request.getHost(),request.getPort());

        DeleteResponse deleteResponse = DeleteResponse.newBuilder().build();

        responseObserver.onNext(deleteResponse);
        responseObserver.onCompleted();

        log("------\nA server has been deleted:\nService Name: "
                + request.getService() + "\n"
                + "Server Host: " + request.getHost() + "\nServer Port: " + request.getPort()
                + "\n------\n",isDebug());
    }

    /**
     * Function called when a client or a server needs a server entry data
     * from the naming server database
     *
     * @param request request
     * @param responseObserver response
     */
    @Override
    public void lookup(LookupRequest request, StreamObserver<LookupResponse> responseObserver){

        ArrayList<String> qualifiersList = new ArrayList<>(request.getQualifiersList());

        ServerEntry serverEntry = namingServices.lookup(qualifiersList);
        LookupResponse lookupResponse;

        if (serverEntry != null) {
            lookupResponse = LookupResponse
                    .newBuilder()
                    .setHost(serverEntry.getHost())
                    .setPort(serverEntry.getPort())
                    .setAvailable(true)
                    .build();

            log("------\nServer redirected properties:"
                    + "\nServer Host: " + lookupResponse.getHost()
                    + "\nServer Port: " + lookupResponse.getPort()
                    +"\n------\n",isDebug());

        }
        else {
            lookupResponse = LookupResponse
                    .newBuilder()
                    .setAvailable(false)
                    .build();

        }
        responseObserver.onNext(lookupResponse);
        responseObserver.onCompleted();


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
