package com.smg.log.mapper;

import com.smg.pojo.Log;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LogMapper {

    @Select("SELECT * FROM logs")
    List<Log> findAll();

    @Insert("INSERT INTO logs(content, timestamp, user, successful) VALUES(#{content}, #{timestamp}, #{user}, #{successful})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Log log);

    @Delete("DELETE FROM logs WHERE id = #{id}")
    void deleteById(Long id);

    @Select("SELECT * FROM logs WHERE timestamp BETWEEN #{startTime} AND #{endTime}")
    List<Log> findByTimestampBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}