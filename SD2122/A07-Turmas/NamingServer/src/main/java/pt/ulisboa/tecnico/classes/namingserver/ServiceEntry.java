package pt.ulisboa.tecnico.classes.namingserver;

import java.util.ArrayList;

public class ServiceEntry {

    private final ArrayList<ServerEntry> serverEntries = new ArrayList<>();

    private String serviceName;

    /**
     *
     * @param serviceName Service name (always turmas)
     * @param serverEntry Server Entry
     */
    public ServiceEntry(String serviceName, ServerEntry serverEntry){
        setServiceName(serviceName);
        addServerEntry(serverEntry);
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     *
     * @param serverEntry New server to be added
     */
    public void addServerEntry(ServerEntry serverEntry){
        getServerEntries().add(serverEntry);
    }

    /**
     *
     * @param host host of the server
     * @param port port of the server
     */
    public void removeServerEntry(String host, int port){
        getServerEntries().removeIf(serverEntry ->
                serverEntry.getPort() == port && serverEntry.getHost().equals(host));
    }

    public ArrayList<ServerEntry> getServerEntries() {
        return serverEntries;
    }
}

