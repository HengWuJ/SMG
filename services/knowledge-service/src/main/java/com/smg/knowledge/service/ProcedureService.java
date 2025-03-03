package com.smg.knowledge.service;

import com.smg.knowledge.node.Procedure;
import com.smg.knowledge.node.Step;
import com.smg.knowledge.repository.ProcedureRepository;
import com.smg.knowledge.repository.StepRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProcedureService {

    private static final Logger logger = LoggerFactory.getLogger(ProcedureService.class);

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private StepRepository stepRepository;

    public Procedure saveProcedureWithSteps(Procedure procedure, Set<String> stepIds) {
        logger.info("Saving procedure with steps: {}, stepIds: {}", procedure, stepIds);
        if (procedure == null || stepIds == null) {
            logger.error("Procedure or stepIds is null");
            throw new IllegalArgumentException("Procedure and stepIds cannot be null");
        }
        Set<Step> steps = new HashSet<>(stepRepository.findAllById(stepIds));
        procedure.setSteps(steps);
        return procedureRepository.save(procedure);
    }

    public Procedure findById(String id) {
        logger.info("Finding procedure by ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        return procedureRepository.findById(id).orElse(null);
    }

    public List<Procedure> findAll() {
        logger.info("Finding all procedures");
        return procedureRepository.findAll();
    }

    public List<Procedure> findByCategory(String category) {
        logger.info("Finding procedures by category: {}", category);
        if (category == null) {
            logger.error("Category is null");
            throw new IllegalArgumentException("Category cannot be null");
        }
        return procedureRepository.findByCategory(category);
    }

    public Procedure updateProcedure(String id, Procedure procedure, Set<String> stepIds) {
        logger.info("Updating procedure with ID: {}, procedure: {}, stepIds: {}", id, procedure, stepIds);
        if (id == null || procedure == null || stepIds == null) {
            logger.error("ID, procedure, or stepIds is null");
            throw new IllegalArgumentException("ID, procedure, and stepIds cannot be null");
        }
        Procedure existingProcedure = procedureRepository.findById(id).orElseThrow(() -> new RuntimeException("Procedure not found"));
        existingProcedure.setName(procedure.getName());
        existingProcedure.setCategory(procedure.getCategory());
        existingProcedure.setFrequency(procedure.getFrequency());
        existingProcedure.setRequiredSkills(procedure.getRequiredSkills());
        existingProcedure.setSafetyPrecautions(procedure.getSafetyPrecautions());
        Set<Step> steps = (Set<Step>) stepRepository.findAllById(stepIds);
        existingProcedure.setSteps(steps);
        return procedureRepository.save(existingProcedure);
    }

    public void deleteProcedure(String id) {
        logger.info("Deleting procedure with ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        procedureRepository.deleteById(id);
    }

    public Procedure addStepToProcedure(String procedureId, String stepId) {
        logger.info("Adding step with ID: {} to procedure with ID: {}", stepId, procedureId);
        if (procedureId == null || stepId == null) {
            logger.error("Procedure ID or Step ID is null");
            throw new IllegalArgumentException("Procedure ID and Step ID cannot be null");
        }
        Procedure procedure = procedureRepository.findById(procedureId).orElseThrow(() -> new RuntimeException("Procedure not found"));
        Step step = stepRepository.findById(stepId).orElseThrow(() -> new RuntimeException("Step not found"));
        procedure.getSteps().add(step);
        return procedureRepository.save(procedure);
    }

    public Procedure removeStepFromProcedure(String procedureId, String stepId) {
        logger.info("Removing step with ID: {} from procedure with ID: {}", stepId, procedureId);
        if (procedureId == null || stepId == null) {
            logger.error("Procedure ID or Step ID is null");
            throw new IllegalArgumentException("Procedure ID and Step ID cannot be null");
        }
        Procedure procedure = procedureRepository.findById(procedureId).orElseThrow(() -> new RuntimeException("Procedure not found"));
        Step step = stepRepository.findById(stepId).orElseThrow(() -> new RuntimeException("Step not found"));
        procedure.getSteps().remove(step);
        return procedureRepository.save(procedure);
    }
}