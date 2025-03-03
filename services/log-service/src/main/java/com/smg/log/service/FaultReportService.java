package com.smg.log.service;

import com.smg.log.mapper.FaultReportMapper;
import com.smg.pojo.FaultReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FaultReportService {

    @Autowired
    private FaultReportMapper faultReportMapper;

    public void addFaultReport(FaultReport faultReport) {
        faultReportMapper.insert(faultReport);
    }

    public FaultReport getFaultReportById(Long id) {
        return faultReportMapper.selectById(id);
    }

    public List<FaultReport> getAllFaultReports() {
        return faultReportMapper.selectAll();
    }

    public void updateFaultReport(FaultReport faultReport) {
        faultReportMapper.update(faultReport);
    }

    public void deleteFaultReport(Long id) {
        faultReportMapper.deleteById(id);
    }

    public List<FaultReport> getFaultReportsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return faultReportMapper.selectByDateRange(startOfDay, endOfDay);
    }
}