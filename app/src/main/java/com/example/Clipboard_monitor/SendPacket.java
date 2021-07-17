package com.example.Clipboard_monitor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SendPacket extends Connection {

    public SendPacket() throws IOException {
        super();
    }

    public void sendPacket(String data) {
        try {
            byte[] buf = data.getBytes();

            InetAddress inetAddress = getInetAddress();
            int port = getPORT();
            MulticastSocket ms = getMulticastSocket();
               
            DatagramPacket dp = new DatagramPacket(buf, buf.length, inetAddress, port);

            ms.send(dp);
        } catch (IOException e) {
            System.err.print("IO exception in sendpacket");
            e.printStackTrace();
        }
    }
}
