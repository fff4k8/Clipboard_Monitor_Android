package com.example.Clipboard_monitor;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

public class RcvPacket extends Connection {

    private final int bufSize;
    public Handler mHandler;

    public RcvPacket( int bufSize, Handler handler) throws IOException {
        super();
        mHandler = handler;

        this.bufSize = bufSize;
    }

    public void receive()  {
        MulticastSocket ms = null;
        try {
            while (true) {
                byte[] buf = new byte[bufSize];
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                ms = getMulticastSocket();  // get from superclass
                ms.receive(dp);
                String ip = dp.getAddress().toString();
                String message = new String(dp.getData(), StandardCharsets.UTF_8);
                Clip clipboard = new Clip(ip, message.trim());

                Message rcvMsg = mHandler.obtainMessage(1,clipboard);
                mHandler.sendMessage(rcvMsg);

            }
        } catch (Exception e) {
            assert ms != null;
            try {
                ms.leaveGroup(getInetAddress());
                ms.close(); }
            catch (IOException ioException) { ioException.printStackTrace(); }
            System.err.print("Thread in receive throws IO exception"); e.printStackTrace(); }

        finally { if (ms != null) {
            try {
                ms.leaveGroup(getInetAddress());
                ms.close();
            } catch (IOException ex) { System.err.print(ex); } } } }

}
