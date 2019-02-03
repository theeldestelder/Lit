package com.lit;

public class DeviceItem {
    private String name;
    private String macAddress;

    DeviceItem(String name, String macAddress) {
        this.name = name;
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public String getMacAddress() {
        return macAddress;
    }
}
