package com.smg.maintenance.controller;

import com.smg.knowledge.node.Procedure;
import com.smg.maintenance.feign.LogFeignClient;
import com.smg.maintenance.service.MaintenanceService;
import com.smg.pojo.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/procedure")
public class ProcedureController {

    private final MaintenanceService maintenanceService;
    private final LogFeignClient logFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(ProcedureController.class);

    public ProcedureController(MaintenanceService maintenanceService, LogFeignClient logFeignClient) {
        this.maintenanceService = maintenanceService;
        this.logFeignClient = logFeignClient;
    }

    private void createLog(String content, String user, boolean successful) {
        Log log = new Log();
        log.setContent(content);
        log.setTimestamp(LocalDateTime.now());
        log.setUser(user); 
        log.setSuccessful(successful ? 1 : 0);
        logFeignClient.createLog(log);
    }

    @GetMapping
    public Iterable<Procedure> getAllProcedures() {
        logger.info("Request received to fetch all procedures.");
        createLog("Fetching all procedures.", "System", true);
        return maintenanceService.getAllProcedures();
    }

    @GetMapping("/{id}")
    public Procedure getProcedureById(@PathVariable String id) {
        logger.info("Request received to fetch procedure by ID: {}", id);
        createLog(String.format("Fetching procedure by ID: %s", id), "System", true);
        return maintenanceService.getProcedureById(id);
    }

}