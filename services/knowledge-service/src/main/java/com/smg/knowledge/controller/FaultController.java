package com.smg.knowledge.controller;

import com.smg.knowledge.node.Component;
import com.smg.knowledge.node.Fault;
import com.smg.knowledge.service.FaultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/fault")
public class FaultController {

    private static final Logger logger = LoggerFactory.getLogger(FaultController.class);

    @Autowired
    private FaultService faultService;

    @PostMapping
    public Fault saveFault(@RequestBody Fault fault, @RequestParam Set<String> solutionIds) {
        logger.info("Received request to save fault: {}, solutionIds: {}", fault, solutionIds);
        return faultService.saveFaultWithSolutions(fault, solutionIds);
    }

    @GetMapping
    public List<Fault> getAllFaults() {
        logger.info("Received request to get all faults");
        System.out.println("111111111111111111111111111111111111111");
        List<Fault> faults = faultService.findAll();
        System.out.println("222222222222222222222222222222222222222");
       // System.out.println(faults);
        return faults;
    }

    @GetMapping("/{id}")
    public Fault getFaultById(@PathVariable String id) {
        logger.info("Received request to get fault by ID: {}", id);
        return faultService.findById(id);
    }

    @GetMapping("/severity/{severity}")
    public List<Fault> getFaultsBySeverity(@PathVariable String severity) {
        logger.info("Received request to get faults by severity: {}", severity);
        return faultService.findBySeverity(severity);
    }

    @GetMapping("/description/{description}")
    public List<Fault> getFaultsByDescription(@PathVariable String description){
        logger.info("Received request to get faults by description: {}", description);
        return faultService.findByDescription(description);
    }

    @PutMapping("/{id}")
    public Fault updateFault(@PathVariable String id, @RequestBody Fault fault, @RequestParam Set<String> solutionIds) {
        logger.info("Received request to update fault with ID: {}, fault: {}, solutionIds: {}", id, fault, solutionIds);
        return faultService.updateFault(id, fault, solutionIds);
    }

    @DeleteMapping("/{id}")
    public void deleteFault(@PathVariable String id) {
        logger.info("Received request to delete fault with ID: {}", id);
        faultService.deleteFault(id);
    }

    @PostMapping("/{faultId}/solutions/{solutionId}")
    public Fault addSolutionToFault(@PathVariable String faultId, @PathVariable String solutionId) {
        logger.info("Received request to add solution with ID: {} to fault with ID: {}", solutionId, faultId);
        return faultService.addSolutionToFault(faultId, solutionId);
    }

    @DeleteMapping("/{faultId}/solutions/{solutionId}")
    public Fault removeSolutionFromFault(@PathVariable String faultId, @PathVariable String solutionId) {
        logger.info("Received request to remove solution with ID: {} from fault with ID: {}", solutionId, faultId);
        return faultService.removeSolutionFromFault(faultId, solutionId);
    }

    @GetMapping("/search")
    public List<Fault> searchFaults(
            @RequestParam(required = false) String deviceDescription,
            @RequestParam(required = false) String componentDescription,
            @RequestParam(required = false) String faultDescription) {
        logger.info("Received request to search faults with deviceDescription: {}, componentDescription: {}, faultDescription: {}", deviceDescription, componentDescription, faultDescription);
        if (deviceDescription == null && componentDescription == null && faultDescription == null) {
            logger.error("At least one of deviceDescription, componentDescription, or faultDescription must be provided");
            throw new IllegalArgumentException("At least one of deviceDescription, componentDescription, or faultDescription must be provided");
        }
        return faultService.searchFaults(deviceDescription, componentDescription, faultDescription);
    }

    @GetMapping("/views/{faultId}")
    public Component getComponentByFaultId(@PathVariable String faultId) {
        logger.info("Received request to get component by fault ID: {}", faultId);
        return faultService.getComponentByFaultId(faultId);
    }
}