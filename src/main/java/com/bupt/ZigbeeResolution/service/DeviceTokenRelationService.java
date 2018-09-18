package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
import com.bupt.ZigbeeResolution.mapper.DeviceTokenRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceTokenRelationService {

    @Autowired
    private DeviceTokenRelationMapper deviceTokenRelationMapper;

    public DeviceTokenRelation getRelotionBySnidAndEndPoint(String IEEE, Integer endPoint){
        return deviceTokenRelationMapper.getRelotionBySnidAndEndPoint(IEEE, endPoint);
    }

    public Boolean addARelation(DeviceTokenRelation deviceTokenRelation){
        Integer i = deviceTokenRelationMapper.addARelation(deviceTokenRelation);
        return i==1;
    }

    public Integer getDeviceNumber(String gatewayName, String type){
        return deviceTokenRelationMapper.getdeviceNumber(gatewayName, type);
    }

    public Integer updateShortAddress(String shortAddress, String IEEE){
        return deviceTokenRelationMapper.updateShortAddress(shortAddress, IEEE);
    }

    public Integer updateGatewayName(String gatewayName, String IEEE){
        return deviceTokenRelationMapper.updateShortAddress(gatewayName, IEEE);
    }
}
