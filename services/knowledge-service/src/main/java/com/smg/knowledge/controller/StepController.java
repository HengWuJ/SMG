package com.smg.knowledge.controller;

import com.smg.knowledge.node.Step;
import com.smg.knowledge.service.StepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/step")
public class StepController {

    private static final Logger logger = LoggerFactory.getLogger(StepController.class);

    @Autowired
    private StepService stepService;

    @PostMapping
    public Step saveStep(@RequestBody Step step) {
        logger.info("Received request to save step: {}", step);
        return stepService.saveStep(step);
    }

    @GetMapping("/{id}")
    public Step getStepById(@PathVariable String id) {
        logger.info("Received request to get step by ID: {}", id);
        return stepService.findById(id);
    }

    @GetMapping
    public List<Step> getAllSteps() {
        logger.info("Received request to get all steps");
        return stepService.findAll();
    }

    @GetMapping("/estimated-time/{estimatedTime}")
    public List<Step> getStepsByEstimatedTimeGreaterThan(@PathVariable int estimatedTime) {
        logger.info("Received request to get steps with estimated time greater than: {}", estimatedTime);
        return stepService.findByEstimatedTimeGreaterThan(estimatedTime);
    }

    @PutMapping("/{id}")
    public Step updateStep(@PathVariable String id, @RequestBody Step step) {
        logger.info("Received request to update step with ID: {}, step: {}", id, step);
        return stepService.updateStep(id, step);
    }

    @DeleteMapping("/{id}")
    public void deleteStep(@PathVariable String id) {
        logger.info("Received request to delete step with ID: {}", id);
        stepService.deleteStep(id);
    }
}