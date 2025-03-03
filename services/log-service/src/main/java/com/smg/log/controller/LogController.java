package com.smg.log.controller;

import com.smg.pojo.Log;
import com.smg.log.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;

    @GetMapping
    public List<Log> getAllLogs() {
        logger.info("Fetching all logs.");
        return logService.getAllLogs();
    }

    @PostMapping
    public Log createLog(@RequestBody Log log) {
        logger.info("Creating a new log entry: {}", log);
        Log createdLog = logService.createLog(log);
        logger.info("New log entry created successfully with id: {}", createdLog.getId());
        return createdLog;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        logger.info("Deleting log by ID: {}", id);
        logService.deleteLog(id);
        logger.info("Log deleted successfully with id: {}", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/between")
    public List<Log> getLogsByTimestampBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        logger.info("Fetching logs between {} and {}", startTime, endTime);
        return logService.getLogsByTimestampBetween(startTime, endTime);
    }
}