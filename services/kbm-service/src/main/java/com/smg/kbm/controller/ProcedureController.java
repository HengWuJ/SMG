package com.smg.kbm.controller;

import com.smg.kbm.service.KbmService;
import com.smg.knowledge.node.Procedure;
import com.smg.kbm.feign.LogFeignClient;
import com.smg.pojo.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/procedure")
public class ProcedureController {

    private final KbmService kbmService;
    private final LogFeignClient logFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(ProcedureController.class);

    public ProcedureController(KbmService kbmService, LogFeignClient logFeignClient) {
        this.kbmService = kbmService;
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
    public Procedure saveProcedure(@RequestBody Procedure procedure, @RequestParam Set<String> stepIds) {
        logger.info("Request received to save a new procedure: {}", procedure.getProcedureId());
        createLog(String.format("Saving a new procedure with ID: %s and associated steps: %s", procedure.getProcedureId(), stepIds), "System", true);
        return kbmService.saveProcedure(procedure, stepIds);
    }

    @GetMapping
    public Iterable<Procedure> getAllProcedures() {
        logger.info("Request received to fetch all procedures.");
        createLog("Fetching all procedures.", "System", true);
        return kbmService.getAllProcedures();
    }

    @GetMapping("/{id}")
    public Procedure getProcedureById(@PathVariable String id) {
        logger.info("Request received to fetch procedure by ID: {}", id);
        createLog(String.format("Fetching procedure by ID: %s", id), "System", true);
        return kbmService.getProcedureById(id);
    }

    @GetMapping("/category/{category}")
    public Iterable<Procedure> getProceduresByCategory(@PathVariable String category) {
        logger.info("Request received to fetch procedures by category: {}", category);
        createLog(String.format("Fetching procedures by category: %s", category), "System", true);
        return kbmService.getProceduresByCategory(category);
    }

    @PutMapping("/{id}")
    public Procedure updateProcedure(@PathVariable String id, @RequestBody Procedure procedure, @RequestParam Set<String> stepIds) {
        logger.info("Request received to update procedure by ID: {}", id);
        createLog(String.format("Updating procedure with ID: %s and associated steps: %s", id, stepIds), "System", true);
        return kbmService.updateProcedure(id, procedure, stepIds);
    }

    @DeleteMapping("/{id}")
    public void deleteProcedure(@PathVariable String id) {
        logger.info("Request received to delete procedure by ID: {}", id);
        createLog(String.format("Deleting procedure with ID: %s", id), "System", true);
        kbmService.deleteProcedure(id);
    }

    @PostMapping("/{procedureId}/steps/{stepId}")
    public Procedure addStepToProcedure(@PathVariable String procedureId, @PathVariable String stepId) {
        logger.info("Received request to add step with ID: {} to procedure with ID: {}", stepId, procedureId);
        createLog(String.format("Adding step with ID: %s to procedure with ID: %s", stepId, procedureId), "System", true);
        return kbmService.addStepToProcedure(procedureId, stepId);
    }

    @DeleteMapping("/{procedureId}/steps/{stepId}")
    public Procedure removeStepFromProcedure(@PathVariable String procedureId, @PathVariable String stepId) {
        logger.info("Received request to remove step with ID: {} from procedure with ID: {}", stepId, procedureId);
        createLog(String.format("Removing step with ID: %s from procedure with ID: %s", stepId, procedureId), "System", true);
        return kbmService.removeStepFromProcedure(procedureId, stepId);
    }
}