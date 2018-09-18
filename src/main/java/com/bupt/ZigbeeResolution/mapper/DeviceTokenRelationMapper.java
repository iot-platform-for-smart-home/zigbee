package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.DeviceTokenRelation;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DeviceTokenRelationMapper {

    @Insert("INSERT INTO deviceTokenRelation(IEEE, endPoint, token, type, gatewayName,shortAddress) VALUES (#{IEEE}, #{endPoint}, #{token}, #{type}, #{gatewayName}, #{shortAddress})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addARelation(DeviceTokenRelation deviceTokenRelation);

    @Select("SELECT * FROM deviceTokenRelation WHERE IEEE = #{IEEE} AND endPoint = #{endPoint}")
    DeviceTokenRelation getRelotionBySnidAndEndPoint(String IEEE, Integer endPoint);

    @Select("SELECT count(*) FROM deviceTokenRelation WHERE gatewayName = #{gatewayName} AND type = #{type}")
    Integer getdeviceNumber(String gatewayName, String type);

    @Update("UPDATE deviceTokenRelation SET shortAddress = #{shortAddress} WHERE IEEE = #{IEEE}")
    Integer updateShortAddress(String shortAddress, String IEEE);

    @Update("UPDATE deviceTokenRelation SET gatewayName = #{gatewayName} WHERE IEEE = #{IEEE}")
    Integer updateGatewayName(String gatewayName, String IEEE);
}
