package pt.ulisboa.tecnico.classes.namingserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NamingServices {

    private final HashMap<String, ServiceEntry> serverList = new HashMap<>();

    public HashMap<String, ServiceEntry> getServerList() {
        return serverList;
    }

    /**
     *
     * @param service the type of service (always turmas)
     * @param serverEntry new server
     */
    public void addToServerList(String service, ServerEntry serverEntry) {
        if (getServerList().containsKey(service))
            getServerList().get(service).addServerEntry(serverEntry);
        else
            getServerList().put(service, new ServiceEntry(service, serverEntry));
    }

    /**
     *
     * @param service the type of service (always turmas)
     * @param host host of the server
     * @param port post of the server
     */
    public void removeServer(String service, String host, int port) {
        if (getServerList().containsKey(service))
            getServerList().get(service).removeServerEntry(host,port);
    }

    /**
     *
     * @param qualifiers the qualifiers that will be P or S, S is read only
     * @return server entry random from the list availableEntries
     */
    public ServerEntry lookup(ArrayList<String> qualifiers) {
        String serviceName = "turmas";
        ArrayList<ServerEntry> availableEntries = new ArrayList<>();

        if (getServerList().isEmpty())
            return null;
        getServerList().get(serviceName).getServerEntries().forEach(serverEntry -> {
            if (qualifiers.contains(serverEntry.getQualifiers().get(0)))
                availableEntries.add(serverEntry);
        });

        if (availableEntries.isEmpty())
            return null;

        return availableEntries.get(new Random().nextInt(availableEntries.size()));
    }
}

