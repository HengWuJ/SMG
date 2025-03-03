package com.smg.log.mapper;

import com.smg.pojo.FaultReport;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface FaultReportMapper {

    @Insert("INSERT INTO fault_report (fault_id, worker_id, worker_name, worker_phone, report_time, description, status, severity, affected_systems, root_cause, resolution_steps, additional_notes) " +
            "VALUES (#{faultId}, #{workerId}, #{workerName}, #{workerPhone}, #{reportTime}, #{description}, #{status}, #{severity}, #{affectedSystems}, #{rootCause}, #{resolutionSteps}, #{additionalNotes})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(FaultReport faultReport);

    @Select("SELECT * FROM fault_report WHERE id = #{id}")
    FaultReport selectById(Long id);

    @Select("SELECT * FROM fault_report")
    List<FaultReport> selectAll();

    @Select("SELECT * FROM fault_report WHERE report_time BETWEEN #{start} AND #{end}")
    List<FaultReport> selectByDateRange(LocalDateTime start, LocalDateTime end);


    @Update("UPDATE fault_report SET fault_id = #{faultId}, worker_id = #{workerId}, worker_name = #{workerName}, worker_phone = #{workerPhone}, report_time = #{reportTime}, " +
            "description = #{description}, status = #{status}, severity = #{severity}, affected_systems = #{affectedSystems}, root_cause = #{rootCause}, " +
            "resolution_steps = #{resolutionSteps}, additional_notes = #{additionalNotes} WHERE id = #{id}")
    void update(FaultReport faultReport);

    @Delete("DELETE FROM fault_report WHERE id = #{id}")
    void deleteById(Long id);
}