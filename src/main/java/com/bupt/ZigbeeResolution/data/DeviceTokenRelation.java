package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class DeviceTokenRelation {
    private Integer id;
    private String IEEE;
    private Integer endPoint;
    private String token;
    private String type;
    private String gatewayName;
    private String shortAddress;

    public DeviceTokenRelation(String IEEE, Integer endPoint, String token, String type, String gatewayName, String shortAddress){
        this.IEEE=IEEE;
        this.endPoint = endPoint;
        this.token = token;
        this.type = type;
        this.gatewayName = gatewayName;
        this.shortAddress = shortAddress;
    }

    public DeviceTokenRelation(Integer id, String IEEE, Integer endPoint, String token, String type, String gatewayName, String shortAddress){
        this.id = id;
        this.IEEE=IEEE;
        this.endPoint = endPoint;
        this.token = token;
        this.type = type;
        this.gatewayName = gatewayName;
        this.shortAddress = shortAddress;
    }

}
