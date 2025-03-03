package com.smg.log.service;

import com.smg.log.mapper.LogMapper;
import com.smg.pojo.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogMapper logMapper;

    public List<Log> getAllLogs() {
        return logMapper.findAll();
    }

    public Log createLog(Log log) {
        logMapper.insert(log);
        return log;
    }

    public void deleteLog(Long id) {
        logMapper.deleteById(id);
    }

    public List<Log> getLogsByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return logMapper.findByTimestampBetween(startTime, endTime);
    }
}