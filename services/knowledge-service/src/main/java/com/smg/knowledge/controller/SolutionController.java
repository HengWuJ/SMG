package com.smg.knowledge.controller;

import com.smg.knowledge.node.Solution;
import com.smg.knowledge.service.SolutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solution")
public class SolutionController {

    private static final Logger logger = LoggerFactory.getLogger(SolutionController.class);

    @Autowired
    private SolutionService solutionService;

    @GetMapping
    public List<Solution> getAllSolutions() {
        logger.info("Received request to get all solutions");
        return solutionService.getAllSolutions();
    }

    @PostMapping
    public Solution saveSolutionWithFault(@RequestBody Solution solution, @RequestParam String faultId) {
        logger.info("Received request to save solution: {}, faultId: {}", solution, faultId);
        return solutionService.saveSolutionWithFault(solution, faultId);
    }

    @GetMapping("/{id}")
    public Solution getSolutionById(@PathVariable String id) {
        logger.info("Received request to get solution by ID: {}", id);
        return solutionService.findById(id);
    }

    @PutMapping("/{id}")
    public Solution updateSolution(@PathVariable String id, @RequestBody Solution solution, @RequestParam String faultId) {
        logger.info("Received request to update solution with ID: {}, solution: {}, faultId: {}", id, solution, faultId);
        return solutionService.updateSolution(id, solution, faultId);
    }

    @DeleteMapping("/{id}")
    public void deleteSolution(@PathVariable String id) {
        logger.info("Received request to delete solution with ID: {}", id);
        solutionService.deleteSolution(id);
    }
}