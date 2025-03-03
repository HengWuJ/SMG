package com.smg.monitor.repository;

import com.smg.monitor.pojo.SensorData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface SensorDataRepository extends ElasticsearchRepository<SensorData, String> {
    List<SensorData> findByTimestampBetween(ZonedDateTime startDate, ZonedDateTime endDate);

    SensorData findTopByOrderByTimestampDesc();
    List<SensorData> findBySensorId(String sensorId);
    List<SensorData> findByLocation(String location);
}