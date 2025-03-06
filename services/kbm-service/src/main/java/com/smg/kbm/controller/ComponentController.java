package com.smg.kbm.controller;

import com.smg.kbm.service.KbmService;
import com.smg.knowledge.node.Component;
import com.smg.kbm.feign.LogFeignClient;
import com.smg.pojo.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/component")
public class ComponentController {

    private final KbmService kbmService;
    private final LogFeignClient logFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(ComponentController.class);

    public ComponentController(KbmService kbmService, LogFeignClient logFeignClient) {
        this.kbmService = kbmService;
        this.logFeignClient = logFeignClient;
    }

    private void createLog(String content, String user, boolean successful) {
        Log log = new Log();
        log.setContent(content);
        log.setTimestamp(LocalDateTime.now());
        log.setUser(user);
        log.setSuccessful(successful ? 1 : 0);
        logFeignClient.createLog(log);
    }

    @PostMapping
    public Component saveComponent(@RequestBody Component component) {
        logger.info("Request received to save a new component: {}", component.getComponentId());
        createLog(String.format("Saving a new component with ID: %s", component.getComponentId()), "System", true);
        return kbmService.saveComponent(component);
    }

    @GetMapping("/{id}")
    public Component getComponentById(@PathVariable String id) {
        logger.info("Request received to fetch component by ID: {}", id);
        createLog(String.format("Fetching component by ID: %s", id), "System", true);
        return kbmService.getComponentById(id);
    }

    @GetMapping
    public List<Component> getAllComponents() {
        logger.info("Request received to fetch all components.");
        createLog("Fetching all components.", "System", true);
        return kbmService.getAllComponents();
    }

    @GetMapping("/criticality/{criticalityLevel}")
    public List<Component> getComponentsByCriticalityLevel(@PathVariable String criticalityLevel) {
        logger.info("Request received to fetch components by criticality level: {}", criticalityLevel);
        createLog(String.format("Fetching components by criticality level: %s", criticalityLevel), "System", true);
        return kbmService.getComponentsByCriticalityLevel(criticalityLevel);
    }

    @PutMapping("/{id}")
    public Component updateComponent(@PathVariable String id, @RequestBody Component component) {
        logger.info("Request received to update component by ID: {}", id);
        createLog(String.format("Updating component with ID: %s", id), "System", true);
        return kbmService.updateComponent(id, component);
    }

    @DeleteMapping("/{id}")
    public void deleteComponent(@PathVariable String id) {
        logger.info("Request received to delete component by ID: {}", id);
        createLog(String.format("Deleting component with ID: %s", id), "System", true);
        kbmService.deleteComponent(id);
    }

    @PostMapping("/{componentId}/faults/{faultId}")
    public Component addFaultToComponent(@PathVariable String componentId, @PathVariable String faultId) {
        logger.info("Received request to add fault with ID: {} to component with ID: {}", faultId, componentId);
        createLog(String.format("Adding fault with ID: %s to component with ID: %s", faultId, componentId), "System", true);
        return kbmService.addFaultToComponent(componentId, faultId);
    }

    @DeleteMapping("/{componentId}/faults/{faultId}")
    public Component removeFaultFromComponent(@PathVariable String componentId, @PathVariable String faultId) {
        logger.info("Received request to remove fault with ID: {} from component with ID: {}", faultId, componentId);
        createLog(String.format("Removing fault with ID: %s from component with ID: %s", faultId, componentId), "System", true);
        return kbmService.removeFaultFromComponent(componentId, faultId);
    }

    @GetMapping("/{faultyComponentId}/disassembly-order")
    public List<String> getDisassemblyOrder(@PathVariable String faultyComponentId) {
        logger.info("Received request to get disassembly order for component with ID: {}", faultyComponentId);
        createLog(String.format("Getting disassembly order for component with ID: %s", faultyComponentId), "System", true);
        return kbmService.getDisassemblyOrder(faultyComponentId);
    }

    @GetMapping("/{faultyComponentId}/assembly-order")
    public List<String> getAssemblyOrder(@PathVariable String faultyComponentId) {
        logger.info("Received request to get assembly order for component with ID: {}", faultyComponentId);
        createLog(String.format("Getting assembly order for component with ID: %s", faultyComponentId), "System", true);
        return kbmService.getAssemblyOrder(faultyComponentId);
    }

    @PostMapping("/{sourceId}/precedes/{targetId}")
    public void createPrecedesRelationship(@PathVariable String sourceId, @PathVariable String targetId) {
        logger.info("Received request to create precedes relationship between components with IDs: {} and {}", sourceId, targetId);
        createLog(String.format("Creating precedes relationship between components with IDs: %s and %s", sourceId, targetId), "System", true);
        kbmService.createPrecedesRelationship(sourceId, targetId);
    }

    @DeleteMapping("/{sourceId}/precedes/{targetId}")
    public void deletePrecedesRelationship(@PathVariable String sourceId, @PathVariable String targetId) {
        logger.info("Received request to delete precedes relationship between components with IDs: {} and {}", sourceId, targetId);
        createLog(String.format("Deleting precedes relationship between components with IDs: %s and %s", sourceId, targetId), "System", true);
        kbmService.deletePrecedesRelationship(sourceId, targetId);
    }
}