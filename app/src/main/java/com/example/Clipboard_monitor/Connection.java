package com.example.Clipboard_monitor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Connection {

    private final String HOSTNAME = "228.0.0.0";
    private final String HOSTNAMEIPv6 = "0:0:0:0:0:FFFF:E400:0000";
    private final int PORT = 2280;
    private InetAddress inetAddress;
    private MulticastSocket multicastSocket;

    public String getHOSTNAME() { return HOSTNAME; }
    public int getPORT() { return PORT; }
    public InetAddress getInetAddress() { return inetAddress; }
    public MulticastSocket getMulticastSocket() { return multicastSocket; }


    public Connection() throws IOException {
         this.inetAddress = InetAddress.getByName(HOSTNAME);
       // this.inetAddress = Inet6Address.getByName(HOSTNAMEIPv6);
        this.multicastSocket = new MulticastSocket(PORT);
        this.multicastSocket.setLoopbackMode(false);
       // this.multicastSocket.setTimeToLive(2);
    }

    public void joinGroup() throws IOException {
        multicastSocket.joinGroup(inetAddress);
    }

    public void leaveGroup() throws IOException {
        multicastSocket.leaveGroup(inetAddress);
    }
}
