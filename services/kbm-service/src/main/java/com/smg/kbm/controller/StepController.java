package com.smg.kbm.controller;

import com.smg.kbm.service.KbmService;
import com.smg.knowledge.node.Step;
import com.smg.kbm.feign.LogFeignClient;
import com.smg.pojo.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/step")
public class StepController {

    private final KbmService kbmService;
    private final LogFeignClient logFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(StepController.class);

    public StepController(KbmService kbmService, LogFeignClient logFeignClient) {
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
    public Step saveStep(@RequestBody Step step) {
        logger.info("Request received to save a new step: {}", step.getStepId());
        createLog(String.format("Saving a new step with ID: %s", step.getStepId()), "System", true);
        return kbmService.saveStep(step);
    }

    @GetMapping("/{id}")
    public Step getStepById(@PathVariable String id) {
        logger.info("Request received to fetch step by ID: {}", id);
        createLog(String.format("Fetching step by ID: %s", id), "System", true);
        return kbmService.getStepById(id);
    }

    @GetMapping
    public List<Step> getAllSteps() {
        logger.info("Request received to fetch all steps.");
        createLog("Fetching all steps.", "System", true);
        return kbmService.getAllSteps();
    }

    @GetMapping("/estimated-time/{estimatedTime}")
    public List<Step> getStepsByEstimatedTimeGreaterThan(@PathVariable int estimatedTime) {
        logger.info("Request received to fetch steps with estimated time greater than: {}", estimatedTime);
        createLog(String.format("Fetching steps with estimated time greater than: %d minutes", estimatedTime), "System", true);
        return kbmService.getStepsByEstimatedTimeGreaterThan(estimatedTime);
    }

    @PutMapping("/{id}")
    public Step updateStep(@PathVariable String id, @RequestBody Step step) {
        logger.info("Request received to update step by ID: {}", id);
        createLog(String.format("Updating step with ID: %s", id), "System", true);
        return kbmService.updateStep(id, step);
    }

    @DeleteMapping("/{id}")
    public void deleteStep(@PathVariable String id) {
        logger.info("Request received to delete step by ID: {}", id);
        createLog(String.format("Deleting step with ID: %s", id), "System", true);
        kbmService.deleteStep(id);
    }
}