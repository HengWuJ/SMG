package com.smg.maintenance.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface FaultViewMapper {

    @Insert("INSERT INTO fault_views (fault_id, view_count) VALUES (#{faultId}, 1) " +
            "ON DUPLICATE KEY UPDATE view_count = view_count + 1")
    void insertOrUpdateFaultView(@Param("faultId") String faultId);

    @Select("SELECT view_count FROM fault_views WHERE fault_id = #{faultId}")
    Integer getViewCount(@Param("faultId") String faultId);
}