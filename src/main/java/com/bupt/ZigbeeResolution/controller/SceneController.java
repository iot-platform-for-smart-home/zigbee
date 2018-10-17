package com.bupt.ZigbeeResolution.controller;

import com.bupt.ZigbeeResolution.data.*;
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void addScene(@RequestBody String sceneInfo){
        JsonObject jsonObject = (JsonObject)new JsonParser().parse(sceneInfo);
        String sceneName = jsonObject.get("sceneName").getAsString();
        Integer customerId = jsonObject.get("customerId").getAsInt();
        Scene scene = sceneService.getSceneBySceneName(sceneName+"_"+customerId);
        Integer scene_id;
        if(scene==null) {
            scene = new Scene(sceneName+"_"+customerId, customerId);
            sceneService.addSceneOnlySceneName(scene);
            scene_id = scene.getScene_id();
        }else{
            scene_id = scene.getScene_id();
        }

        JsonArray sceneArray = jsonObject.get("sceneInfo").getAsJsonArray();
        for(JsonElement jsonElement:sceneArray){
            JsonObject deviceInfo = jsonElement.getAsJsonObject();
            String deviceId = deviceInfo.get("deviceId").getAsString();
            DeviceTokenRelation deviceRelation = deviceTokenRelationService.getRelationByUuid(deviceId);

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

            GatewayMethod gatewayMethod = new GatewayMethodImpl();
            gatewayMethod.addScene(device, data1, data2, data3, data4, sceneName+"_"+customerId,(byte)0x00, (byte)0x00,(byte)0x00);

            SceneDevice sceneDevice = new SceneDevice(scene_id,deviceId,deviceInfo.get("data1").getAsInt(),deviceInfo.get("data2").getAsInt(),deviceInfo.get("data3").getAsInt(),deviceInfo.get("data4").getAsInt());
            sceneDeviceService.addSceneDevice(sceneDevice);
        }

    }

    @RequestMapping(value = "/getAllScene/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public String getAllScene(@PathVariable("customerId") Integer customerId){
        JsonArray sceneArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        List<Scene> scenes = sceneService.getSceneByCustomerId(customerId);
        for(Scene scene:scenes){
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
            jsonObject.addProperty("detail",jsonArray.toString());
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
        GatewayMethod gatewayMethod = new GatewayMethodImpl();
        gatewayMethod.deleteSceneMember(scene, device);

        if(!sceneDeviceService.deleteSceneDeviceBySceneId(scene_id)){
            System.err.println("删除场景设备错误！");
            return "error"
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

    }
}
