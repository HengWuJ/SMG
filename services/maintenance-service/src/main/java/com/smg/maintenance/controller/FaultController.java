package com.smg.maintenance.controller;

import com.smg.knowledge.node.Component;
import com.smg.knowledge.node.Fault;
import com.smg.maintenance.service.MaintenanceService;
import com.smg.maintenance.feign.LogFeignClient;
import com.smg.pojo.FaultReport;
import com.smg.pojo.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/fault")
public class FaultController {

    private final MaintenanceService maintenanceService;
    private final LogFeignClient logFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(FaultController.class);

    public FaultController(MaintenanceService maintenanceService, LogFeignClient logFeignClient) {
        this.maintenanceService = maintenanceService;
        this.logFeignClient = logFeignClient;
    }

    private void createLog(String content, String user, boolean successful) {
        Log log = new Log();
        log.setContent(content);
        log.setTimestamp(LocalDateTime.now());
        log.setUser(user); // 在实际应用中，这里可能需要根据上下文获取真实的用户名
        log.setSuccessful(successful ? 1 : 0);
        logFeignClient.createLog(log);
    }

    @PostMapping
    public Fault saveFault(@RequestBody Fault fault, @RequestParam Set<String> solutionIds) {
        logger.info("Request received to save a new fault: {}", fault.getFaultId());
        createLog(String.format("Saving a new fault with ID: %s and associated solutions: %s", fault.getFaultId(), solutionIds), "System", true);
        return maintenanceService.saveFault(fault, solutionIds);
    }

    @GetMapping
    public List<Fault> getAllFaults() {
        logger.info("Request received to fetch all faults.");
        createLog("Fetching all faults.", "System", true);
        return maintenanceService.getAllFaults();
    }

    @GetMapping("/{id}")
    public Fault getFaultById(@PathVariable String id) {
        logger.info("Request received to fetch fault by ID: {}", id);
        createLog(String.format("Fetching fault by ID: %s", id), "System", true);
        return maintenanceService.getFaultById(id);
    }

    @GetMapping("/severity/{severity}")
    public List<Fault> getFaultsBySeverity(@PathVariable String severity) {
        logger.info("Request received to fetch faults by severity: {}", severity);
        createLog(String.format("Fetching faults by severity: %s", severity), "System", true);
        return maintenanceService.getFaultsBySeverity(severity);
    }

    @GetMapping("/description/{description}")
    public List<Fault> getFaultsByDescription(@PathVariable String description) {
        logger.info("Request received to fetch faults by description: {}", description);
        createLog(String.format("Fetching faults by description: %s", description), "System", true);
        return maintenanceService.getFaultsByDescription(description);
    }
    @PutMapping("/{id}")
    public Fault updateFault(@PathVariable String id, @RequestBody Fault fault, @RequestParam Set<String> solutionIds) {
        logger.info("Request received to update fault by ID: {}", id);
        createLog(String.format("Updating fault with ID: %s and associated solutions: %s", id, solutionIds), "System", true);
        return maintenanceService.updateFault(id, fault, solutionIds);
    }

    @DeleteMapping("/{id}")
    public void deleteFault(@PathVariable String id) {
        logger.info("Request received to delete fault by ID: {}", id);
        createLog(String.format("Deleting fault with ID: %s", id), "System", true);
        maintenanceService.deleteFault(id);
    }

    @GetMapping("/views/{faultId}")
    public Integer getViewCount(@PathVariable String faultId) {
        logger.info("Request received to fetch view count for fault ID: {}", faultId);
        createLog(String.format("Fetching view count for fault ID: %s", faultId), "System", true);
        return maintenanceService.getViewCount(faultId);
    }

    @PostMapping("/views/{faultId}")
    public void incrementViewCount(@PathVariable String faultId) {
        logger.info("Request received to increment view count for fault ID: {}", faultId);
        createLog(String.format("Incrementing view count for fault ID: %s", faultId), "System", true);
        maintenanceService.incrementViewCount(faultId);
    }

    @PostMapping("/fault-reports")
    public void addFaultReport(@RequestBody FaultReport faultReport) {
        logger.info("Request received to add a fault report: {}", faultReport);
        createLog(String.format("Adding a fault report: %s", faultReport), "System", true);
        faultReport.setReportTime(LocalDateTime.now());
        logFeignClient.createFaultReport(faultReport);
    }

    @GetMapping("/search")
    public List<Fault> searchFaultsByViews(
            @RequestParam(required = false) String deviceDescription,
            @RequestParam(required = false) String componentDescription,
            @RequestParam(required = false) String faultDescription) {
        logger.info("Request received to search faults by views with deviceDescription: {}, componentDescription: {}, faultDescription: {}", deviceDescription, componentDescription, faultDescription);
        createLog(String.format("Searching faults by views with deviceDescription: %s, componentDescription: %s, faultDescription: %s", deviceDescription, componentDescription, faultDescription), "System", true);
        return maintenanceService.searchFaultsByViews(deviceDescription, componentDescription, faultDescription);
    }

    @GetMapping("/searchComponent/{faultId}")
    public Component searchComponentByFaultId(@PathVariable String faultId){
        logger.info("Received request to search component by fault ID: {}", faultId);
        return maintenanceService.getComponentByFaultId(faultId);
    }

    @GetMapping("/{faultyComponentId}/disassembly-order")
    public List<String> getDisassemblyOrder(@PathVariable String faultyComponentId) {
        logger.info("Received request to get disassembly order for component with ID: {}", faultyComponentId);
        return maintenanceService.getDisassemblyOrder(faultyComponentId);
    }

    @GetMapping("/{faultyComponentId}/assembly-order")
    public List<String> getAssemblyOrder(@PathVariable String faultyComponentId) {
        logger.info("Received request to get assembly order for component with ID: {}", faultyComponentId);
        return maintenanceService.getAssemblyOrder(faultyComponentId);
    }
}