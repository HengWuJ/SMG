package com.smg.log.controller;

import com.smg.log.service.FaultReportService;
import com.smg.pojo.FaultReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fault-reports")
public class FaultReportController {

    @Autowired
    private FaultReportService faultReportService;

    @PostMapping
    public void addFaultReport(@RequestBody FaultReport faultReport) {
        faultReportService.addFaultReport(faultReport);
    }

    @GetMapping("/{id}")
    public FaultReport getFaultReportById(@PathVariable Long id) {
        return faultReportService.getFaultReportById(id);
    }

    @GetMapping
    public List<FaultReport> getAllFaultReports() {
        return faultReportService.getAllFaultReports();
    }

    @PutMapping
    public void updateFaultReport(@RequestBody FaultReport faultReport) {
        faultReportService.updateFaultReport(faultReport);
    }

    @DeleteMapping("/{id}")
    public void deleteFaultReport(@PathVariable Long id) {
        faultReportService.deleteFaultReport(id);
    }
}