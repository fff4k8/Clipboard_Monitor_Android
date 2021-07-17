package com.example.Clipboard_monitor;

import java.io.Serializable;

public class Clip implements Serializable {
    public Clip() {
    }

    public  String ip;
    public String message;


    public Clip(String ip, String message) {
        this.ip = ip;
        this.message = message;

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
