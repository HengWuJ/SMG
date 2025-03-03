package com.smg.kbm.controller;

import com.smg.kbm.service.KbmService;
import com.smg.knowledge.node.Fault;
import com.smg.kbm.feign.LogFeignClient;
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

    private final KbmService kbmService;
    private final LogFeignClient logFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(FaultController.class);

    public FaultController(KbmService kbmService, LogFeignClient logFeignClient) {
        this.kbmService = kbmService;
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

    @PostMapping
    public Fault saveFault(@RequestBody Fault fault, @RequestParam Set<String> solutionIds) {
        logger.info("Request received to save a new fault: {}", fault.getFaultId());
        createLog(String.format("Saving a new fault with ID: %s and associated solutions: %s", fault.getFaultId(), solutionIds), "System", true);
        return kbmService.saveFault(fault, solutionIds);
    }

    @GetMapping
    public List<Fault> getAllFaults() {
        logger.info("Request received to fetch all faults.");
        createLog("Fetching all faults.", "System", true);
        return kbmService.getAllFaults();
    }

    @GetMapping("/{id}")
    public Fault getFaultById(@PathVariable String id) {
        logger.info("Request received to fetch fault by ID: {}", id);
        createLog(String.format("Fetching fault by ID: %s", id), "System", true);
        return kbmService.getFaultById(id);
    }

    @GetMapping("/severity/{severity}")
    public List<Fault> getFaultsBySeverity(@PathVariable String severity) {
        logger.info("Request received to fetch faults by severity: {}", severity);
        createLog(String.format("Fetching faults by severity: %s", severity), "System", true);
        return kbmService.getFaultsBySeverity(severity);
    }

    @PutMapping("/{id}")
    public Fault updateFault(@PathVariable String id, @RequestBody Fault fault, @RequestParam Set<String> solutionIds) {
        logger.info("Request received to update fault by ID: {}", id);
        createLog(String.format("Updating fault with ID: %s and associated solutions: %s", id, solutionIds), "System", true);
        return kbmService.updateFault(id, fault, solutionIds);
    }

    @DeleteMapping("/{id}")
    public void deleteFault(@PathVariable String id) {
        logger.info("Request received to delete fault by ID: {}", id);
        createLog(String.format("Deleting fault with ID: %s", id), "System", true);
        kbmService.deleteFault(id);
    }

    @PostMapping("/{faultId}/solutions/{solutionId}")
    public Fault addSolutionToFault(@PathVariable String faultId, @PathVariable String solutionId) {
        logger.info("Received request to add solution with ID: {} to fault with ID: {}", solutionId, faultId);
        createLog(String.format("Adding solution with ID: %s to fault with ID: %s", solutionId, faultId), "System", true);
        return kbmService.addSolutionToFault(faultId, solutionId);
    }

    @DeleteMapping("/{faultId}/solutions/{solutionId}")
    public Fault removeSolutionFromFault(@PathVariable String faultId, @PathVariable String solutionId) {
        logger.info("Received request to remove solution with ID: {} from fault with ID: {}", solutionId, faultId);
        createLog(String.format("Removing solution with ID: %s from fault with ID: %s", solutionId, faultId), "System", true);
        return kbmService.removeSolutionFromFault(faultId, solutionId);
    }
}