package com.smg.knowledge.service;

import com.smg.knowledge.node.Step;
import com.smg.knowledge.repository.StepRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepService {

    private static final Logger logger = LoggerFactory.getLogger(StepService.class);

    @Autowired
    private StepRepository stepRepository;

    public Step saveStep(Step step) {
        logger.info("Saving step: {}", step);
        if (step == null) {
            logger.error("Step is null");
            throw new IllegalArgumentException("Step cannot be null");
        }
        return stepRepository.save(step);
    }

    public Step findById(String id) {
        logger.info("Finding step by ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        return stepRepository.findById(id).orElse(null);
    }

    public List<Step> findAll() {
        logger.info("Finding all steps");
        return stepRepository.findAll();
    }

    public List<Step> findByEstimatedTimeGreaterThan(int estimatedTime) {
        logger.info("Finding steps with estimated time greater than: {}", estimatedTime);
        return stepRepository.findByEstimatedTimeGreaterThan(estimatedTime);
    }

    public Step updateStep(String id, Step step) {
        logger.info("Updating step with ID: {}, step: {}", id, step);
        if (id == null || step == null) {
            logger.error("ID or step is null");
            throw new IllegalArgumentException("ID and step cannot be null");
        }
        Step existingStep = stepRepository.findById(id).orElseThrow(() -> new RuntimeException("Step not found"));
        existingStep.setDescription(step.getDescription());
        existingStep.setOrder(step.getOrder());
        existingStep.setEstimatedTime(step.getEstimatedTime());
        existingStep.setToolsRequired(step.getToolsRequired());
        existingStep.setPredecessorSteps(step.getPredecessorSteps());
        existingStep.setSuccessorSteps(step.getSuccessorSteps());
        return stepRepository.save(existingStep);
    }

    public void deleteStep(String id) {
        logger.info("Deleting step with ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        stepRepository.deleteById(id);
    }
}