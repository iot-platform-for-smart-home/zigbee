package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class Scene {
    String sceneId;
    String sceneName;
    int sceneNumber;

    public Scene(){}

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
