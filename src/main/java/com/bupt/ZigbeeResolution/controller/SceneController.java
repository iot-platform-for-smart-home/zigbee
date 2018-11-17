package com.bupt.ZigbeeResolution.controller;

import com.bupt.ZigbeeResolution.data.*;
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.GatewayGroupService;
import com.bupt.ZigbeeResolution.service.SceneDeviceService;
import com.bupt.ZigbeeResolution.service.SceneService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scene")
public class SceneController{

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private SceneDeviceService sceneDeviceService;

    @Autowired
    private GatewayGroupService gatewayGroupService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addScene(@RequestBody String sceneInfo){
        JsonObject jsonObject = (JsonObject)new JsonParser().parse(sceneInfo);
        String sceneNickName = jsonObject.get("sceneName").getAsString();
        Integer customerId = jsonObject.get("customerId").getAsInt();
        Scene scene = sceneService.getSceneBySceneName(sceneNickName+"_"+customerId);
        Integer scene_id;
        if(scene==null) {
            int sceneNumber = sceneService.getSceneNumber();
            scene = new Scene("scene"+sceneNumber,sceneNickName+"_"+customerId, customerId);
            sceneService.addSceneOnlySceneName(scene);
            scene_id = scene.getScene_id();
        }else{
            scene_id = scene.getScene_id();
        }

        JsonArray sceneArray = jsonObject.get("sceneInfo").getAsJsonArray();
        String gatewayName = "";
        for(JsonElement jsonElement:sceneArray){
            JsonObject deviceInfo = jsonElement.getAsJsonObject();
            String deviceId = deviceInfo.get("deviceId").getAsString();
            DeviceTokenRelation deviceRelation = deviceTokenRelationService.getRelationByUuid(deviceId);

            if(!gatewayName.equals("") && !gatewayName.equals(deviceRelation.getGatewayName())){
                return "error";
            }

            gatewayName = deviceRelation.getGatewayName();

            Device device = new Device();
            device.setShortAddress(deviceRelation.getShortAddress());
            device.setEndpoint(deviceRelation.getEndPoint().byteValue());

            switch (deviceRelation.getType()){
                case "switch":
                    device.setDeviceId("0200");
                    break;
                case "dimmableLight":
                    device.setDeviceId("0101");
                    break;
                case "outlet":
                    device.setDeviceId("0900");
                    break;
            }

            byte data1 =(byte)(0xFF & deviceInfo.get("data1").getAsInt());
            byte data2 =(byte)(0xFF & deviceInfo.get("data2").getAsInt());
            byte data3 =(byte)(0xFF & deviceInfo.get("data3").getAsInt());
            byte data4 =(byte)(0xFF & deviceInfo.get("data4").getAsInt());

            String ip = gatewayGroupService.getGatewayIp(deviceRelation.getShortAddress(), deviceRelation.getEndPoint());

            GatewayMethod gatewayMethod = new GatewayMethodImpl();
            gatewayMethod.addScene(device, data1, data2, data3, data4, "scene"+scene_id,(byte)0x00, (byte)0x01,(byte)0x00, ip);

            SceneDevice sceneDevice = new SceneDevice(scene_id,deviceId,deviceInfo.get("data1").getAsInt(),deviceInfo.get("data2").getAsInt(),deviceInfo.get("data3").getAsInt(),deviceInfo.get("data4").getAsInt());
            if(sceneDeviceService.getSceneDeviceBySceneIdAndDeviceId(scene_id, deviceId)!=null){
                sceneDeviceService.updateSceneDevice(sceneDevice);
            }else {
                sceneDeviceService.addSceneDevice(sceneDevice);
            }

        }
        sceneService.updateGatewayName(gatewayName, scene_id);
        return  "success";
    }

    @RequestMapping(value = "/getAllScene/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public String getAllScene(@PathVariable("customerId") Integer customerId){
        JsonArray sceneArray = new JsonArray();
        List<Scene> scenes = sceneService.getSceneByCustomerId(customerId);
        for(Scene scene:scenes){
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            jsonObject.addProperty("scene_id",scene.getScene_id());
            jsonObject.addProperty("sceneId", scene.getSceneId());
            String sceneName = scene.getSceneName();
            int i = sceneName.lastIndexOf("_");
            sceneName = sceneName.substring(0,i);
            jsonObject.addProperty("sceneName", sceneName);
            jsonObject.addProperty("sceneNumber",scene.getSceneNumber());
            List<SceneDevice> sceneDeviceList = sceneDeviceService.getSceneDevice(scene.getScene_id());
            for(SceneDevice sceneDevice : sceneDeviceList){
                JsonObject sceneDeviceObject = new JsonObject();
                sceneDeviceObject.addProperty("deviceId", sceneDevice.getDeviceId());
                sceneDeviceObject.addProperty("data1", sceneDevice.getData1());
                sceneDeviceObject.addProperty("data2", sceneDevice.getData2());
                sceneDeviceObject.addProperty("data3", sceneDevice.getData3());
                sceneDeviceObject.addProperty("data4", sceneDevice.getData4());
                jsonArray.add(sceneDeviceObject);
            }
            jsonObject.add("detail",jsonArray);
            sceneArray.add(jsonObject);
        }

        return sceneArray.toString();
    }

    @RequestMapping(value = "/getScene/{scene_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SceneDevice> getScene(@PathVariable("scene_id")Integer scene_id){
        List<SceneDevice> sceneDeviceList = sceneDeviceService.getSceneDevice(scene_id);
        return sceneDeviceList;
    }

    @RequestMapping(value = "/getSceneByGateway/{gatewayName}", method = RequestMethod.GET)
    @ResponseBody
    public String getSceneByGateway(@PathVariable("gatewayName")String gatewayName){
        JsonArray sceneArray = new JsonArray();

        String[] gateway = gatewayName.split("_");
        String gatewayNumber = gateway[1];
        List<Scene> scenes = sceneService.getSceneByGateway(gatewayNumber);

        for(Scene scene:scenes){
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            jsonObject.addProperty("scene_id",scene.getScene_id());
            jsonObject.addProperty("sceneId", scene.getSceneId());
            String sceneName = scene.getSceneName();
            int i = sceneName.lastIndexOf("_");
            sceneName = sceneName.substring(0,i);
            jsonObject.addProperty("sceneName", sceneName);
            jsonObject.addProperty("sceneNumber",scene.getSceneNumber());
            List<SceneDevice> sceneDeviceList = sceneDeviceService.getSceneDevice(scene.getScene_id());
            for(SceneDevice sceneDevice : sceneDeviceList){
                JsonObject sceneDeviceObject = new JsonObject();
                sceneDeviceObject.addProperty("deviceId", sceneDevice.getDeviceId());
                sceneDeviceObject.addProperty("data1", sceneDevice.getData1());
                sceneDeviceObject.addProperty("data2", sceneDevice.getData2());
                sceneDeviceObject.addProperty("data3", sceneDevice.getData3());
                sceneDeviceObject.addProperty("data4", sceneDevice.getData4());
                jsonArray.add(sceneDeviceObject);
            }
            jsonObject.add("detail",jsonArray);
            sceneArray.add(jsonObject);
        }

        return sceneArray.toString();
    }

    @RequestMapping(value="/deleteScene/{scene_id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteScene(@PathVariable("scene_id")Integer scene_id){
        Scene scene = sceneService.getSceneBySceneId(scene_id);
        Device device = new Device();
        device.setShortAddress("FFFF");
        device.setEndpoint((byte)0xFF);

        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(scene.getGatewayName());
        if (gatewayGroup == null){
            System.err.println("网关不在线");
            return "error";
        }
        String ip = gatewayGroup.getIp();


        GatewayMethod gatewayMethod = new GatewayMethodImpl();
        gatewayMethod.deleteSceneMember(scene, device, ip);

        if(sceneDeviceService.deleteSceneDeviceBySceneId(scene_id)){
            System.err.println("删除场景设备错误！");
            return "error";
        }

        if(!sceneService.deleteSceneBySceneId(scene_id)){
            System.err.println("删除场景错误！");
            return "error";
        }
        return "success";
    }

    @RequestMapping(value = "/bindSelector", method = RequestMethod.POST)
    @ResponseBody
    public String bindSceneSelector(@RequestBody String selectorInfo){
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(selectorInfo);
        String sceneSelectorId = jsonObject.get("sceneSelectorId").getAsString();
        Integer scene_id  = jsonObject.get("scene_id").getAsInt();

        Scene scene = sceneService.getSceneBySceneId(scene_id);
        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(sceneSelectorId);

        Device device = new Device();
        device.setShortAddress(deviceTokenRelation.getShortAddress());
        device.setEndpoint(deviceTokenRelation.getEndPoint().byteValue());

        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(deviceTokenRelation.getGatewayName());

        GatewayMethod gatewayMethod = new GatewayMethodImpl();
        gatewayMethod.setSwitchBindScene(device, scene.getSceneId(), gatewayGroup.getIp());

        if(!sceneService.updateSceneSelector(sceneSelectorId, scene_id)){
            System.err.println("数据库更新错误");
            return  "error";
        }
        return "success";
    }

    @RequestMapping(value = "/useScene/{scene_id}", method = RequestMethod.POST)
    @ResponseBody
    public String useScene(@PathVariable("scene_id")Integer scene_id){
        Scene scene = sceneService.getSceneBySceneId(scene_id);
        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(scene.getGatewayName());

        if (gatewayGroup == null){
            System.err.println("网关不在线");
            return "error";
        }

        GatewayMethod gatewayMethod = new GatewayMethodImpl();
        gatewayMethod.callScene(scene.getSceneId(), gatewayGroup.getIp());

        return "success";
    }

    @RequestMapping(value = "/getBindScene/{deviceId}", method = RequestMethod.POST)
    @ResponseBody
    public String getBindScene(@PathVariable String deviceId){
        Device device = new Device();

        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelationByUuid(deviceId);
        device.setShortAddress(deviceTokenRelation.getShortAddress());
        device.setEndpoint(deviceTokenRelation.getEndPoint().byteValue());

        GatewayGroup gatewayGroup = gatewayGroupService.getGatewayGroup(deviceTokenRelation.getGatewayName());
        GatewayMethod gatewayMethod = new GatewayMethodImpl();
        gatewayMethod.getBindRecord(device, gatewayGroup.getIp());

        return "";
    }
}
