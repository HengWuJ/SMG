package com.smg.knowledge.service;

import com.smg.knowledge.node.Fault;
import com.smg.knowledge.node.Solution;
import com.smg.knowledge.node.Tool;
import com.smg.knowledge.repository.FaultRepository;
import com.smg.knowledge.repository.SolutionRepository;
import com.smg.knowledge.repository.ToolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolutionService {

    private static final Logger logger = LoggerFactory.getLogger(SolutionService.class);

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private FaultRepository faultRepository;

    @Autowired
    private  ToolRepository toolRepository;

    public Solution saveSolutionWithFault(Solution solution, String faultId) {
        logger.info("Saving solution with fault: {}, faultId: {}", solution, faultId);
        if (solution == null || faultId == null) {
            logger.error("Solution or faultId is null");
            throw new IllegalArgumentException("Solution and faultId cannot be null");
        }
        //Fault fault = faultRepository.findById(faultId).orElseThrow(() -> new RuntimeException("Fault not found"));
        //solution.setFault(fault);
        return solutionRepository.save(solution);
    }

    public Solution findById(String id) {
        logger.info("Finding solution by ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        return solutionRepository.findById(id).orElse(null);
    }

    public Solution updateSolution(String id, Solution solution, String faultId) {
        logger.info("Updating solution with ID: {}, solution: {}, faultId: {}", id, solution, faultId);
        if (id == null || solution == null || faultId == null) {
            logger.error("ID, solution, or faultId is null");
            throw new IllegalArgumentException("ID, solution, and faultId cannot be null");
        }
        Solution existingSolution = solutionRepository.findById(id).orElseThrow(() -> new RuntimeException("Solution not found"));
        existingSolution.setDescription(solution.getDescription());
        existingSolution.setDate(solution.getDate());
        existingSolution.setCost(solution.getCost());
        existingSolution.setDuration(solution.getDuration());
        existingSolution.setTechnician(solution.getTechnician());
        existingSolution.setEffectivenessRating(solution.getEffectivenessRating());
        existingSolution.setMaterialsUsed(solution.getMaterialsUsed());
        Fault fault = faultRepository.findById(faultId).orElseThrow(() -> new RuntimeException("Fault not found"));
        //existingSolution.setFault(fault);
        return solutionRepository.save(existingSolution);
    }

    public void deleteSolution(String id) {
        logger.info("Deleting solution with ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        solutionRepository.deleteById(id);
    }

    public List<Solution> getAllSolutions() {
        logger.info("Getting all solutions");
        return solutionRepository.findAll();
    }

    public void saveSolution(Solution solution) {
        logger.info("Saving solution: {}", solution);
        solutionRepository.save(solution);
    }

    public Solution addToolToSolution(String solutionId, String toolId) {
        logger.info("Adding tool with ID: {} to solution with ID: {}", toolId, solutionId);
        if (solutionId == null || toolId == null) {
            logger.error("solutionId or toolId is null");
            throw new IllegalArgumentException("solutionId and toolId cannot be null");
        }
        Solution solution = solutionRepository.findById(solutionId).orElseThrow(() -> new RuntimeException("Solution not found"));
        Tool tool = toolRepository.findById(toolId).orElseThrow(() -> new RuntimeException("Tool not found"));
        solution.getTools().add(tool);
        return solutionRepository.save(solution);
    }

    public Solution removeToolFromSolution(String solutionId, String toolId) {
        logger.info("Removing tool with ID: {} from solution with ID: {}", toolId, solutionId);
        if (solutionId == null || toolId == null) {
            logger.error("solutionId or toolId is null");
            throw new IllegalArgumentException("solutionId and toolId cannot be null");
        }
        Solution solution = solutionRepository.findById(solutionId).orElseThrow(() -> new RuntimeException("Solution not found"));
        Tool tool = toolRepository.findById(toolId).orElseThrow(() -> new RuntimeException("Tool not found"));
        solution.getTools().remove(tool);
        return solutionRepository.save(solution);
    }
}