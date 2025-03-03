package com.smg.maintenance.service;

import com.smg.knowledge.node.Fault;
import com.smg.knowledge.node.Procedure;
import com.smg.knowledge.node.Solution;
import com.smg.maintenance.feign.KnowledgeFeignClient;
import com.smg.maintenance.mapper.FaultViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {

    private static final Logger logger = LoggerFactory.getLogger(MaintenanceService.class);

    @Autowired
    private KnowledgeFeignClient knowledgeFeignClient;

    @Autowired
    private FaultViewMapper faultViewMapper;

    // Fault related methods
    public Fault saveFault(Fault fault, Set<String> solutionIds) {
        if (fault == null) {
            logger.error("Attempted to save a null fault.");
            throw new IllegalArgumentException("Fault cannot be null");
        }
        if (solutionIds == null) {
            logger.error("Attempted to save fault with null solution IDs.");
            throw new IllegalArgumentException("Solution IDs cannot be null");
        }
        logger.info("Saving fault: {}", fault);
        Fault savedFault = knowledgeFeignClient.saveFault(fault, solutionIds);
        logger.info("Fault saved: {}", savedFault);
        return savedFault;
    }

    public List<Fault> getAllFaults() {
        logger.info("Fetching all faults.");
        List<Fault> faults = knowledgeFeignClient.getAllFaults();
        logger.info("Fetched faults: {}", faults);
        return faults;
    }

    public Fault getFaultById(String id) {
        if (id == null) {
            logger.error("Attempted to fetch fault with null ID.");
            throw new IllegalArgumentException("Fault ID cannot be null");
        }
        logger.info("Fetching fault by ID: {}", id);
        Fault fault = knowledgeFeignClient.getFaultById(id);
        logger.info("Fetched fault: {}", fault);
        return fault;
    }

    public List<Fault> getFaultsBySeverity(String severity) {
        if (severity == null) {
            logger.error("Attempted to fetch faults with null severity.");
            throw new IllegalArgumentException("Severity cannot be null");
        }
        logger.info("Fetching faults by severity: {}", severity);
        List<Fault> faults = knowledgeFeignClient.getFaultsBySeverity(severity);
        logger.info("Fetched faults: {}", faults);
        return faults;
    }

    public List<Fault> getFaultsByDescription(String description) {
        if (description == null) {
            logger.error("Attempted to fetch faults with null description.");
            throw new IllegalArgumentException("Description cannot be null");
        }
        logger.info("Fetching faults by description: {}", description);
        List<Fault> faults = knowledgeFeignClient.getFaultsByDescription(description);
        logger.info("Fetched faults: {}", faults);
        return faults;
    }

    public Fault updateFault(String id, Fault fault, Set<String> solutionIds) {
        if (id == null) {
            logger.error("Attempted to update fault with null ID.");
            throw new IllegalArgumentException("Fault ID cannot be null");
        }
        if (fault == null) {
            logger.error("Attempted to update a null fault.");
            throw new IllegalArgumentException("Fault cannot be null");
        }
        if (solutionIds == null) {
            logger.error("Attempted to update fault with null solution IDs.");
            throw new IllegalArgumentException("Solution IDs cannot be null");
        }
        logger.info("Updating fault with ID: {}", id);
        Fault updatedFault = knowledgeFeignClient.updateFault(id, fault, solutionIds);
        logger.info("Fault updated: {}", updatedFault);
        return updatedFault;
    }

    public void deleteFault(String id) {
        if (id == null) {
            logger.error("Attempted to delete fault with null ID.");
            throw new IllegalArgumentException("Fault ID cannot be null");
        }
        logger.info("Deleting fault with ID: {}", id);
        knowledgeFeignClient.deleteFault(id);
        logger.info("Fault deleted with ID: {}", id);
    }

    // Solution related methods
    public Solution saveSolutionWithFault(Solution solution, String faultId) {
        if (solution == null) {
            logger.error("Attempted to save a null solution.");
            throw new IllegalArgumentException("Solution cannot be null");
        }
        if (faultId == null) {
            logger.error("Attempted to save solution with null fault ID.");
            throw new IllegalArgumentException("Fault ID cannot be null");
        }
        logger.info("Saving solution with fault ID: {}", faultId);
        Solution savedSolution = knowledgeFeignClient.saveSolutionWithFault(solution, faultId);
        logger.info("Solution saved: {}", savedSolution);
        return savedSolution;
    }

    public Solution getSolutionById(String id) {
        if (id == null) {
            logger.error("Attempted to fetch solution with null ID.");
            throw new IllegalArgumentException("Solution ID cannot be null");
        }
        logger.info("Fetching solution by ID: {}", id);
        Solution solution = knowledgeFeignClient.getSolutionById(id);
        logger.info("Fetched solution: {}", solution);
        return solution;
    }

    public Solution updateSolution(String id, Solution solution, String faultId) {
        if (id == null) {
            logger.error("Attempted to update solution with null ID.");
            throw new IllegalArgumentException("Solution ID cannot be null");
        }
        if (solution == null) {
            logger.error("Attempted to update a null solution.");
            throw new IllegalArgumentException("Solution cannot be null");
        }
        if (faultId == null) {
            logger.error("Attempted to update solution with null fault ID.");
            throw new IllegalArgumentException("Fault ID cannot be null");
        }
        logger.info("Updating solution with ID: {}", id);
        Solution updatedSolution = knowledgeFeignClient.updateSolution(id, solution, faultId);
        logger.info("Solution updated: {}", updatedSolution);
        return updatedSolution;
    }

    public void deleteSolution(String id) {
        if (id == null) {
            logger.error("Attempted to delete solution with null ID.");
            throw new IllegalArgumentException("Solution ID cannot be null");
        }
        logger.info("Deleting solution with ID: {}", id);
        knowledgeFeignClient.deleteSolution(id);
        logger.info("Solution deleted with ID: {}", id);
    }

    public void incrementViewCount(String faultId) {
        if (faultId == null) {
            logger.error("Attempted to increment view count with null fault ID.");
            throw new IllegalArgumentException("Fault ID cannot be null");
        }
        logger.info("Incrementing view count for fault ID: {}", faultId);
        faultViewMapper.insertOrUpdateFaultView(faultId);
    }

    public int getViewCount(String faultId) {
        if (faultId == null) {
            logger.error("Attempted to get view count with null fault ID.");
            throw new IllegalArgumentException("Fault ID cannot be null");
        }
        logger.info("Getting view count for fault ID: {}", faultId);
        Integer viewCount = faultViewMapper.getViewCount(faultId);
        return viewCount != null ? viewCount : 0;
    }

    public List<Fault> searchFaultsByViews(
            String deviceDescription,
            String componentDescription,
            String faultDescription) {
        logger.info("Searching faults with deviceDescription: {}, componentDescription: {}, faultDescription: {}", deviceDescription, componentDescription, faultDescription);
        List<Fault> faults = knowledgeFeignClient.searchFaults(deviceDescription, componentDescription, faultDescription);
        return faults.stream()
                .sorted(Comparator.comparingInt(f -> -getViewCount(f.getFaultId())))
                .collect(Collectors.toList());
    }

    public List<Procedure> getAllProcedures() {
        logger.info("Getting all procedures");
        return knowledgeFeignClient.getAllProcedures();
    }
}
