package com.smg.monitor.service;

import com.smg.monitor.pojo.AlarmRule;
import com.smg.monitor.pojo.SensorData;
import com.smg.monitor.repository.SensorDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SensorDataMonitoringService {

    private static final Logger logger = LoggerFactory.getLogger(SensorDataMonitoringService.class);

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private AlarmRuleService alarmRuleService;

    @Autowired
    private WebSocketService webSocketService;

    @Scheduled(fixedRate = 60000)
    public void checkSensorData() {
        logger.info("Starting sensor data monitoring...");

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).truncatedTo(ChronoUnit.SECONDS).plusHours(8);
        ZonedDateTime oneMinuteAgo = now.minusMinutes(1);

//        System.out.println("6666666666666666666666666666666666666666\n");
        logger.info("Querying sensor data between: {} and {}", oneMinuteAgo, now);
//        System.out.println("6666666666666666666666666666666666666666\n");

        List<SensorData> recentData = sensorDataRepository.findByTimestampBetween(oneMinuteAgo, now);
        logger.info("Retrieved recent sensor data: {}", recentData);

        if (recentData.isEmpty()) {
            logger.warn("No sensor data found in the last minute.");
        }

        List<AlarmRule> rules = alarmRuleService.getAllRules();
        logger.info("Retrieved alarm rules: {}", rules);

        for (AlarmRule rule : rules) {
            double sum = 0;
            int count = 0;

            for (SensorData data : recentData) {
                if (rule.getFieldName().equals("temperature")) {
                    sum += data.getTemperature();
                    count++;
                } else if (rule.getFieldName().equals("humidity")) {
                    sum += data.getHumidity();
                    count++;
                }
            }

            if (count > 0) {
                double average = sum / count;
                logger.info("Average {} for rule {}: {}", rule.getFieldName(), rule.getId(), average);

                if (average < rule.getMinValue() || average > rule.getMaxValue()) {
                    String message = String.format("Alarm triggered for rule ID %d: Average %s is out of range (%f, %f). Current average: %f",
                            rule.getId(), rule.getFieldName(), rule.getMinValue(), rule.getMaxValue(), average);
                    logger.warn(message);
                    webSocketService.sendAlarmMessage(message);
                }
            } else {
                logger.info("No data found for rule ID {} in the last minute.", rule.getId());
            }
        }
    }
}
