package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.SceneDevice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SceneDeviceMapper {
    @Insert("INSERT INTO sceneDevice (scene_id, deviceId, data1, data2, data3, data4) VALUES (#{scene_id}, #{deviceId}, #{data1}, #{data2}, #{data3}, #{data4})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addSceneDevice(SceneDevice sceneDevice);

    @Select("SELECT * FROM sceneDevice WHERE scene_id = #{scene_id}")
    List<SceneDevice> getSceneDeviceBySceneId(@Param("scene_id") Integer scene_id);

    @Delete("DELETE FROM sceneDevice WHERE scene_id = #{scene_id}")
    Integer deleteScenenDeviceBySceneId(@Param("scene_id") Integer scene_id);


}
