package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.SceneDevice;
import com.bupt.ZigbeeResolution.mapper.SceneDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneDeviceService {

    @Autowired
    private SceneDeviceMapper sceneDeviceMapper;

    public Boolean addSceneDevice(SceneDevice sceneDevice){
        Integer i = sceneDeviceMapper.addSceneDevice(sceneDevice);
        return i==1;
    }

    public SceneDevice getSceneDevice(Integer sceneId){
        return sceneDeviceMapper.getSceneDeviceBySceneId(sceneId);
    }
}
