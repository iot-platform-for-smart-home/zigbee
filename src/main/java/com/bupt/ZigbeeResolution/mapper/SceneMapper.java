package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.Scene;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SceneMapper {

    @Insert("INSERT INTO scene (sceneName) VALUES (#{sceneName})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addSceneOnlySceneName(Scene scene);

    @Select("SELECT * FROM scene WHERE sceneName = #{sceneName}")
    Scene getSceneByName(@Param("sceneName") String sceneName);

    @Update("UPDATE scene SET sceneId = #{sceneId}, sceneNumber = #{sceneNumber} WHERE sceneName = #{sceneName}")
    Integer updateScene(Scene scene);

}
