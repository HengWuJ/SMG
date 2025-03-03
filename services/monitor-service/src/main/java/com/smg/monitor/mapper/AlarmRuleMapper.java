package com.smg.monitor.mapper;

import com.smg.monitor.pojo.AlarmRule;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AlarmRuleMapper {

    @Insert("INSERT INTO alarm_rule (sensor_type, field_name, min_value, max_value, description) " +
            "VALUES (#{sensorType}, #{fieldName}, #{minValue}, #{maxValue}, #{description})")
    void insert(AlarmRule alarmRule);

    @Update("UPDATE alarm_rule SET sensor_type = #{sensorType}, field_name = #{fieldName}, min_value = #{minValue}, " +
            "max_value = #{maxValue}, description = #{description} WHERE id = #{id}")
    void update(AlarmRule alarmRule);

    @Delete("DELETE FROM alarm_rule WHERE id = #{id}")
    void deleteById(@Param("id") Integer id);

    @Select("SELECT * FROM alarm_rule")
    List<AlarmRule> findAll();
}