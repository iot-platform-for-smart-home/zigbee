package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.SceneDevice;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SceneDeviceMapper {
    @Insert("INSERT INTO sceneDevice (scene_id, deviceId, data1, data2, data3, data4) VALUES (#{scene_id}, #{deviceId}, #{data1}, #{data2}, #{data3}, #{data4})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addSceneDevice(SceneDevice sceneDevice);

    @Select("SELECT * FROM sceneDevice WHERE sceneId = #{sceneId}")
    SceneDevice getSceneDeviceBySceneId(@Param("SceneId") Integer SceneId);
}
