package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class Scene {
    Integer scene_id;
    String sceneId;
    String sceneName;
    int sceneNumber;

    public Scene(){}

    public Scene(String sceneName){
        this.sceneName = sceneName;
    }

    public Scene(Integer scene_id, String sceneId, String sceneName, Integer sceneNumber){
        this.scene_id = scene_id;
        this.sceneId = sceneId;
        this.sceneName = sceneName;
        this.sceneNumber =sceneNumber;
    }



    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"sceneId\":")
                .append("\"" + sceneId + "\"").append(",");
        sb.append("\"sceneName\":")
                .append("\"" + sceneName + "\"").append(",");
        sb.append("\"sceneNumber\":")
                .append("\"" + sceneNumber + "\"");
        sb.append('}');
        return sb.toString();
    }
}
