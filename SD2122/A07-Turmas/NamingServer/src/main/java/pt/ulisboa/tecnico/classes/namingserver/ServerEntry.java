package pt.ulisboa.tecnico.classes.namingserver;

import java.util.ArrayList;

public class ServerEntry {

    private String host;
    private int port;
    private ArrayList<String> qualifiers;

    /**
     *
     * @param host Server entry host
     * @param port Server entry port
     * @param qualifiers Server entry qualifiers
     */
    public ServerEntry(String host, int port, ArrayList<String> qualifiers){
        setHost(host);
        setPort(port);
        setQualifiers(qualifiers);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setQualifiers(ArrayList<String> qualifiers) {
        this.qualifiers = qualifiers;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public ArrayList<String> getQualifiers() {
        return qualifiers;
    }

}
