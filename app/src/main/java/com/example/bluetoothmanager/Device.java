package com.example.bluetoothmanager;

public class Device {
    private String name;
    private String mac;

    Device(String name, String mac) {
        this.name = name;
        this.mac = mac;
    }

    String getName() {
        return this.name;
    }

    String getMac() {
        return this.mac;
    }
}
