package com.smg.maintenance.controller;

import com.smg.knowledge.node.Solution;
import com.smg.maintenance.service.MaintenanceService;
import com.smg.maintenance.feign.LogFeignClient;
import com.smg.pojo.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/solution")
public class SolutionController {

    private final MaintenanceService maintenanceService;
    private final LogFeignClient logFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(SolutionController.class);

    public SolutionController(MaintenanceService maintenanceService, LogFeignClient logFeignClient) {
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
    public Solution saveSolutionWithFault(@RequestBody Solution solution, @RequestParam String faultId) {
        logger.info("Request received to save a new solution with fault ID: {}", faultId);
        createLog(String.format("Saving a new solution with ID: %s associated with fault ID: %s", solution.getSolutionId(), faultId), "System", true);
        return maintenanceService.saveSolutionWithFault(solution, faultId);
    }

    @GetMapping("/{id}")
    public Solution getSolutionById(@PathVariable String id) {
        logger.info("Request received to fetch solution by ID: {}", id);
        createLog(String.format("Fetching solution by ID: %s", id), "System", true);
        return maintenanceService.getSolutionById(id);
    }

    @PutMapping("/{id}")
    public Solution updateSolution(@PathVariable String id, @RequestBody Solution solution, @RequestParam String faultId) {
        logger.info("Request received to update solution by ID: {} with fault ID: {}", id, faultId);
        createLog(String.format("Updating solution with ID: %s associated with fault ID: %s", id, faultId), "System", true);
        return maintenanceService.updateSolution(id, solution, faultId);
    }

    @DeleteMapping("/{id}")
    public void deleteSolution(@PathVariable String id) {
        logger.info("Request received to delete solution by ID: {}", id);
        createLog(String.format("Deleting solution with ID: %s", id), "System", true);
        maintenanceService.deleteSolution(id);
    }
}