package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.Scene;
import com.bupt.ZigbeeResolution.mapper.SceneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneService {

    @Autowired
    private SceneMapper sceneMapper;

    public Boolean addSceneOnlySceneName(Scene scene){
        Integer i = sceneMapper.addSceneOnlySceneName(scene);
        return i==1;
    }

    public Scene getSceneBySceneName(String sceneName){
        return sceneMapper.getSceneByName(sceneName);
    }

    public Boolean updateScene(Scene scene){
        Integer i = sceneMapper.updateScene(scene);
        return i==1;
    }
}
