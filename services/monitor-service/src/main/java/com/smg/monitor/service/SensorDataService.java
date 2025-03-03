package com.smg.monitor.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.smg.monitor.pojo.SensorData;
import com.smg.monitor.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    public List<SensorData> getHistoricalData(String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX").withZone(ZoneId.systemDefault());
        System.out.println("11111");
        ZonedDateTime startDate = ZonedDateTime.parse(startDateStr, formatter);
        System.out.println("12221");
        ZonedDateTime endDate = ZonedDateTime.parse(endDateStr, formatter);
        System.out.println("33333");
        System.out.println("Start date: " + startDate + ", End date: " + endDate);
        return sensorDataRepository.findByTimestampBetween(startDate, endDate);

    }

    public List<SensorData> getAllData() {

        Iterable<SensorData> all = sensorDataRepository.findAll();
        System.out.println(all);
        return StreamSupport.stream(all.spliterator(), false)
                .collect(Collectors.toList());
    }

    public SensorData getLatestData() {
        return sensorDataRepository.findTopByOrderByTimestampDesc();
    }

    public void saveSensorData(SensorData sensorData) {
        sensorDataRepository.save(sensorData);
    }

    public List<SensorData> getDataBySensorId(String sensorId) {
        return sensorDataRepository.findBySensorId(sensorId);
    }

//    @HystrixCommand(fallbackMethod = "fallbackGetDataByLocation")
    public List<SensorData> getDataByLocation(String location) {
        return sensorDataRepository.findByLocation(location);
    }
}