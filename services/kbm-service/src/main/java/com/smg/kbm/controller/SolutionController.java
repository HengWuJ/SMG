package com.smg.kbm.controller;

import com.smg.kbm.service.KbmService;
import com.smg.knowledge.node.Solution;
import com.smg.kbm.feign.LogFeignClient;
import com.smg.pojo.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/solution")
public class SolutionController {

    private final KbmService kbmService;
    private final LogFeignClient logFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(SolutionController.class);

    public SolutionController(KbmService kbmService, LogFeignClient logFeignClient) {
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

    @GetMapping
    public List<Solution> getAllSolutions() {
        logger.info("Received request to get all solutions");
        return kbmService.getAllSolutions();
    }
    @PostMapping("/withFault")
    public Solution saveSolutionWithFault(@RequestBody Solution solution, @RequestParam String faultId) {
        logger.info("Request received to save a new solution with fault ID: {}", faultId);
        createLog(String.format("Saving a new solution with ID: %s associated with fault ID: %s", solution.getSolutionId(), faultId), "System", true);
        return kbmService.saveSolutionWithFault(solution, faultId);
    }

    @PostMapping
    public Solution saveSolution(@RequestBody Solution solution) {
        logger.info("Request received to save a new solution: {}", solution);
        createLog(String.format("Saving a new solution with ID: %s", solution.getSolutionId()), "System", true);
        return kbmService.saveSolution(solution);
    }

    @GetMapping("/{id}")
    public Solution getSolutionById(@PathVariable String id) {
        logger.info("Request received to fetch solution by ID: {}", id);
        createLog(String.format("Fetching solution by ID: %s", id), "System", true);
        return kbmService.getSolutionById(id);
    }

    @PutMapping("/{id}")
    public Solution updateSolution(@PathVariable String id, @RequestBody Solution solution, @RequestParam String faultId) {
        logger.info("Request received to update solution by ID: {} with fault ID: {}", id, faultId);
        createLog(String.format("Updating solution with ID: %s associated with fault ID: %s", id, faultId), "System", true);
        return kbmService.updateSolution(id, solution, faultId);
    }

    @DeleteMapping("/{id}")
    public void deleteSolution(@PathVariable String id) {
        logger.info("Request received to delete solution by ID: {}", id);
        createLog(String.format("Deleting solution with ID: %s", id), "System", true);
        kbmService.deleteSolution(id);
    }

    @PostMapping("/{solutionId}/addTool")
    public Solution addToolToSolution(@PathVariable String solutionId, @RequestParam String toolId) {
        logger.info("Request received to add tool with ID: {} to solution with ID: {}", toolId, solutionId);
        createLog(String.format("Adding tool with ID: %s to solution with ID: %s", toolId, solutionId), "System", true);
        return kbmService.addToolToSolution(solutionId, toolId);
    }

    @PostMapping("/{solutionId}/removeTool")
    public Solution removeToolFromSolution(@PathVariable String solutionId, @RequestParam String toolId) {
        logger.info("Request received to remove tool with ID: {} from solution with ID: {}", toolId, solutionId);
        createLog(String.format("Removing tool with ID: %s from solution with ID: %s", toolId, solutionId), "System", true);
        return kbmService.removeToolFromSolution(solutionId, toolId);
    }

}