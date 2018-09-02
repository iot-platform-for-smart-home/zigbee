package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class Device {
    String shortAddress;
    byte Endpoint;
    String profileId;
    String deviceId;
    boolean state;
    String name;
    byte onlineState;
    String IEEE;
    String snid;
    String zoneType;
    double electric;
    byte[] recentState;

    public Device(){}

    public String toString(){
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"shortAddress\":")
                .append("\""+shortAddress+"\"");
        sb.append("\"Endpoint\":")
                .append("\""+Endpoint+"\"");
        sb.append("\"profileId\":")
                .append("\""+profileId+"\"");
        sb.append("\"deviceId\":")
                .append("\""+deviceId+"\"");
        sb.append("\"state\":")
                .append("\""+state+"\"");
        sb.append("\"name\":")
                .append("\""+name+"\"");
        sb.append("\"onlineState\":")
                .append("\""+onlineState+"\"");
        sb.append("\"IEEE\":")
                .append("\""+IEEE+"\"");
        sb.append("\"snid\":")
                .append("\""+snid+"\"");
        sb.append("\"zoneType\":")
                .append("\""+zoneType+"\"");
        sb.append("\"electric\":")
                .append("\""+electric+"\"");
        return sb.toString();
    }
}
