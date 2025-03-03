package com.smg.knowledge.controller;

import com.smg.knowledge.node.Procedure;
import com.smg.knowledge.service.ProcedureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/procedure")
public class ProcedureController {

    private static final Logger logger = LoggerFactory.getLogger(ProcedureController.class);

    @Autowired
    private ProcedureService procedureService;

    @PostMapping
    public Procedure saveProcedure(@RequestBody Procedure procedure, @RequestParam Set<String> stepIds) {
        logger.info("Received request to save procedure: {}, stepIds: {}", procedure, stepIds);
        return procedureService.saveProcedureWithSteps(procedure, stepIds);
    }

    @GetMapping
    public List<Procedure> getAllProcedures() {
        logger.info("Received request to get all procedures");
        return procedureService.findAll();
    }

    @GetMapping("/{id}")
    public Procedure getProcedureById(@PathVariable String id) {
        logger.info("Received request to get procedure by ID: {}", id);
        return procedureService.findById(id);
    }

    @GetMapping("/category/{category}")
    public List<Procedure> getProceduresByCategory(@PathVariable String category) {
        logger.info("Received request to get procedures by category: {}", category);
        return procedureService.findByCategory(category);
    }

    @PutMapping("/{id}")
    public Procedure updateProcedure(@PathVariable String id, @RequestBody Procedure procedure, @RequestParam Set<String> stepIds) {
        logger.info("Received request to update procedure with ID: {}, procedure: {}, stepIds: {}", id, procedure, stepIds);
        return procedureService.updateProcedure(id, procedure, stepIds);
    }

    @DeleteMapping("/{id}")
    public void deleteProcedure(@PathVariable String id) {
        logger.info("Received request to delete procedure with ID: {}", id);
        procedureService.deleteProcedure(id);
    }

    @PostMapping("/{procedureId}/steps/{stepId}")
    public Procedure addStepToProcedure(@PathVariable String procedureId, @PathVariable String stepId) {
        logger.info("Received request to add step with ID: {} to procedure with ID: {}", stepId, procedureId);
        return procedureService.addStepToProcedure(procedureId, stepId);
    }

    @DeleteMapping("/{procedureId}/steps/{stepId}")
    public Procedure removeStepFromProcedure(@PathVariable String procedureId, @PathVariable String stepId) {
        logger.info("Received request to remove step with ID: {} from procedure with ID: {}", stepId, procedureId);
        return procedureService.removeStepFromProcedure(procedureId, stepId);
    }
}