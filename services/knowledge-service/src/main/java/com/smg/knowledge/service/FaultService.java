package com.smg.knowledge.service;

import com.smg.knowledge.node.Component;
import com.smg.knowledge.node.Fault;
import com.smg.knowledge.node.Solution;
import com.smg.knowledge.repository.ComponentRepository;
import com.smg.knowledge.repository.FaultRepository;
import com.smg.knowledge.repository.SolutionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FaultService {

    private static final Logger logger = LoggerFactory.getLogger(FaultService.class);

    @Autowired
    private FaultRepository faultRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private ComponentService componentRepository;

    public Fault saveFaultWithSolutions(Fault fault, Set<String> solutionIds) {
        logger.info("Saving fault with solutions: {}, solutionIds: {}", fault, solutionIds);
        if (fault == null || solutionIds == null) {
            logger.error("Fault or solutionIds is null");
            throw new IllegalArgumentException("Fault and solutionIds cannot be null");
        }
        List<Solution> solutionList = solutionRepository.findAllById(solutionIds);
        Set<Solution> solutions = new HashSet<>(solutionList);
        fault.setSolutions(solutions);
        return faultRepository.save(fault);
    }

    public Fault findById(String id) {
        logger.info("Finding fault by ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        return faultRepository.findById(id).orElse(null);
    }

    public List<Fault> findAll() {
        logger.info("Finding all faults");
        return faultRepository.findAll();
    }

    public List<Fault> findBySeverity(String severity) {
        logger.info("Finding faults by severity: {}", severity);
        if (severity == null) {
            logger.error("Severity is null");
            throw new IllegalArgumentException("Severity cannot be null");
        }
        return faultRepository.findBySeverity(severity);
    }

    public List<Fault> findByDescription(String description) {
        logger.info("Finding faults by description: {}", description);
        if (description == null) {
            logger.error("Description is null");
            throw new IllegalArgumentException("Description cannot be null");
        }
        return faultRepository.findByDescription(description);
    }

    public Fault updateFault(String id, Fault fault, Set<String> solutionIds) {
        logger.info("Updating fault with ID: {}, fault: {}, solutionIds: {}", id, fault, solutionIds);
        if (id == null || fault == null || solutionIds == null) {
            logger.error("ID, fault, or solutionIds is null");
            throw new IllegalArgumentException("ID, fault, and solutionIds cannot be null");
        }
        Fault existingFault = faultRepository.findById(id).orElseThrow(() -> new RuntimeException("Fault not found"));
        existingFault.setDescription(fault.getDescription());
        existingFault.setDate(fault.getDate());
        existingFault.setSeverity(fault.getSeverity());
        existingFault.setResolutionStatus(fault.getResolutionStatus());
        existingFault.setAffectedSystems(fault.getAffectedSystems());
        existingFault.setRootCause(fault.getRootCause());
//        Set<Solution> solutions = (Set<Solution>) solutionRepository.findAllById(solutionIds);
        Set<Solution> solutions = new HashSet<>(solutionRepository.findAllById(solutionIds));
        existingFault.setSolutions(solutions);
        return faultRepository.save(existingFault);
    }

    public void deleteFault(String id) {
        logger.info("Deleting fault with ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        faultRepository.deleteById(id);
    }

    public Fault addSolutionToFault(String faultId, String solutionId) {
        logger.info("Adding solution with ID: {} to fault with ID: {}", solutionId, faultId);
        if (faultId == null || solutionId == null) {
            logger.error("Fault ID or Solution ID is null");
            throw new IllegalArgumentException("Fault ID and Solution ID cannot be null");
        }
        Fault fault = faultRepository.findById(faultId).orElseThrow(() -> new RuntimeException("Fault not found"));
        Solution solution = solutionRepository.findById(solutionId).orElseThrow(() -> new RuntimeException("Solution not found"));
        fault.getSolutions().add(solution);
        return faultRepository.save(fault);
    }

    public Fault removeSolutionFromFault(String faultId, String solutionId) {
        logger.info("Removing solution with ID: {} from fault with ID: {}", solutionId, faultId);
        if (faultId == null || solutionId == null) {
            logger.error("Fault ID or Solution ID is null");
            throw new IllegalArgumentException("Fault ID and Solution ID cannot be null");
        }
        Fault fault = faultRepository.findById(faultId).orElseThrow(() -> new RuntimeException("Fault not found"));
        Solution solution = solutionRepository.findById(solutionId).orElseThrow(() -> new RuntimeException("Solution not found"));
        fault.getSolutions().remove(solution);
        return faultRepository.save(fault);
    }

    public List<Fault> searchFaults(String deviceDescription, String componentDescription, String faultDescription) {
        logger.info("Searching faults with deviceDescription: {}, componentDescription: {}, faultDescription: {}", deviceDescription, componentDescription, faultDescription);
        return faultRepository.searchFaults(deviceDescription, componentDescription, faultDescription);
    }

    public Component getComponentByFaultId(String faultId) {
        logger.info("Getting component by fault ID: {}", faultId);
        String componentId = faultRepository.findComponentsByFaultId(faultId).get(0);
        System.out.println("666666666666666666                 "+componentId);
        return componentRepository.findById(componentId);
        //return faultRepository.findComponentsByFaultId(faultId).stream().findFirst().orElse(null);
    }
}