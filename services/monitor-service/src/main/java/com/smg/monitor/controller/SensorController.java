package com.smg.monitor.controller;

import com.smg.monitor.pojo.SensorData;
import com.smg.monitor.service.SensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SensorDataService sensorDataService;

    @PostMapping
    public ResponseEntity<?> inputSensorData(@RequestBody SensorData sensorData) {
        // Publish to Kafka Topic
        System.out.println("Received sensor data: " + sensorData);
        streamBridge.send("sensorData-out-0", sensorData);

        // Store the latest data in Redis
        redisTemplate.opsForValue().set("latestSensorData", sensorData.toString());

        sensorDataService.saveSensorData(sensorData);

        return ResponseEntity.ok("Data received");
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestData() {
        // Retrieve from Redis
//        String latestData = redisTemplate.opsForValue().get("latestSensorData");
        String latestData = sensorDataService.getLatestData().toString();
        if (latestData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
        }
        return ResponseEntity.ok(latestData);
    }

    @GetMapping("/historyBetween")
    public ResponseEntity<?> getHistoricalData(@RequestParam String startDate, @RequestParam String endDate) {
        List<SensorData> historicalData = sensorDataService.getHistoricalData(startDate, endDate);
        if (historicalData.isEmpty()) {
            return ResponseEntity.status(404).body("No data found within the given date range.");
        }
        return ResponseEntity.ok(historicalData);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistoricalData() {
        List<SensorData> historicalData = sensorDataService.getAllData();
        if (historicalData.isEmpty()) {
            return ResponseEntity.status(404).body("No data found within the given date range.");
        }
        return ResponseEntity.ok(historicalData);
    }

    @GetMapping("/bySensorId")
    public ResponseEntity<?> getDataBySensorId(@RequestParam String sensorId) {
        List<SensorData> sensorDataList = sensorDataService.getDataBySensorId(sensorId);
        if (sensorDataList.isEmpty()) {
            return ResponseEntity.status(404).body("No data found for sensor ID: " + sensorId);
        }
        return ResponseEntity.ok(sensorDataList);
    }

    @GetMapping("/byLocation")
    public ResponseEntity<?> getDataByLocation(@RequestParam String location) {
        List<SensorData> sensorDataList = sensorDataService.getDataByLocation(location);
        if (sensorDataList.isEmpty()) {
            return ResponseEntity.status(404).body("No data found for location: " + location);
        }
        return ResponseEntity.ok(sensorDataList);
    }
}