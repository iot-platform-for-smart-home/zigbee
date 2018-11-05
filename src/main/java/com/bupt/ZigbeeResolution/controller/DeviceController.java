package com.bupt.ZigbeeResolution.controller;

import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
import com.bupt.ZigbeeResolution.data.GatewayGroup;
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.GatewayGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/device")
public class DeviceController {

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

    @Autowired
    private GatewayGroupService gatewayGroupService;

    @RequestMapping(value = "/deleteDevice/{deviceId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteDevice(@PathVariable("deviceId")String deviceId ){
        DeviceTokenRelation singleDeviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);

        Device device = new Device();
        device.setShortAddress(singleDeviceTokenRelation.getShortAddress());
        device.setEndpoint(singleDeviceTokenRelation.getEndPoint().byteValue());
        device.setIEEE(singleDeviceTokenRelation.getIEEE());

        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(singleDeviceTokenRelation.getGatewayName());
        GatewayMethod gatewayMethod = new GatewayMethodImpl();
        gatewayMethod.deleteDevice(device, gatewayGroup.getIp());

        if(deviceTokenRelationService.deleteDeviceByIEEE(singleDeviceTokenRelation.getIEEE())){
            return "error";
        }
        return "success";
    }
}
