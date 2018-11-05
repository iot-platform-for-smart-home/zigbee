package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.Scene;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SceneMapper {

    @Insert("INSERT INTO scene (sceneName,customerId) VALUES (#{sceneName}, #{customerId})")
    @Options(useGeneratedKeys = true, keyProperty = "scene_id", keyColumn = "scene_id")
    Integer addSceneOnlySceneName(Scene scene);

    @Select("SELECT * FROM scene WHERE sceneName = #{sceneName}")
    Scene getSceneByName(@Param("sceneName") String sceneName);

    @Update("UPDATE scene SET sceneId = #{sceneId}, sceneNumber = #{sceneNumber} WHERE sceneName = #{sceneName}")
    Integer updateScene(Scene scene);

    @Select("SELECT * FROM scene WHERE customerId = #{customerId}")
    List<Scene> getSceneByCustomerId(@Param("customerId") Integer customerId);

    @Select("SELECT * FROM scene WHERE scene_id = #{scene_id}")
    Scene getSceneBySceneId(@Param("scene_id") Integer scene_id);

    @Select("SELECT * FROM scene WHERE gatewayName = #{gatewayName}")
    List<Scene> getSceneByGateway(@Param("gatewayName")String gatewayName);

    @Delete("DELETE FROM scene WHERE scene_id = #{scene_id}")
    Integer deleteSceneBySceneId(@Param("scene_id") Integer scene_id);

    @Update("UPDATE scene SET sceneSelectorId = #{sceneSelectorId} WHERE scene_id = #{scene_id}")
    Integer updateSceneSelector(@Param("sceneSelectorId") String sceneSelectorId, @Param("scene_id") Integer scene_id);

    @Update("UPDATE scene SET gatewayName = #{gatewayName} WHERE scene_id = #{scene_id}")
    Integer updateGatewayName(@Param("gatewayName")String gatewayName, @Param("scene_id") Integer scene_id);

}
