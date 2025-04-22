package com.smg.knowledge.controller;

import com.smg.knowledge.node.Component;
import com.smg.knowledge.node.Device;
import com.smg.knowledge.service.ComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/component")
public class ComponentController {

    private static final Logger logger = LoggerFactory.getLogger(ComponentController.class);

    @Autowired
    private ComponentService componentService;

    @PostMapping
    public Component saveComponent(@RequestBody Component component) {
        logger.info("Received request to save component: {}", component);
        return componentService.saveComponent(component);
    }

    @GetMapping("/{id}")
    public Component getComponentById(@PathVariable String id) {
        System.out.println("\n66666\n");
        logger.info("Received request to get component by ID: {}", id);
        return componentService.findById(id);
    }

    @GetMapping
    public List<Component> getAllComponents() {
        logger.info("Received request to get all components");
        return componentService.findAll();
    }

    @GetMapping("/criticality/{criticalityLevel}")
    public List<Component> getComponentsByCriticalityLevel(@PathVariable String criticalityLevel) {
        logger.info("Received request to get components by criticality level: {}", criticalityLevel);
        return componentService.findByCriticalityLevel(criticalityLevel);
    }

    @PutMapping("/{id}")
    public Component updateComponent(@PathVariable String id, @RequestBody Component component) {
        logger.info("Received request to update component with ID: {}, component: {}", id, component);
        return componentService.updateComponent(id, component);
    }

    @DeleteMapping("/{id}")
    public void deleteComponent(@PathVariable String id) {
        logger.info("Received request to delete component with ID: {}", id);
        componentService.deleteComponent(id);
    }

    @PostMapping("/{componentId}/faults/{faultId}")
    public Component addFaultToComponent(@PathVariable String componentId, @PathVariable String faultId) {
        logger.info("Received request to add fault with ID: {} to component with ID: {}", faultId, componentId);
        return componentService.addFaultToComponent(componentId, faultId);
    }

    @DeleteMapping("/{componentId}/faults/{faultId}")
    public Component removeFaultFromComponent(@PathVariable String componentId, @PathVariable String faultId) {
        logger.info("Received request to remove fault with ID: {} from component with ID: {}", faultId, componentId);
        return componentService.removeFaultFromComponent(componentId, faultId);
    }

//    @GetMapping("/{faultyComponentId}/disassembly-order")
//    public List<Component> getDisassemblyOrder(@PathVariable String faultyComponentId) {
//        return componentService.getDisassemblyOrder(faultyComponentId);
//    }

    @GetMapping("/{faultyComponentId}/disassembly-order")
    public List<String> getDisassemblyOrder(@PathVariable String faultyComponentId) {
        logger.info("Received request to get disassembly order for component with ID: {}", faultyComponentId);
        return componentService.getDisassemblyOrder(faultyComponentId);
    }

    @GetMapping("/{faultyComponentId}/assembly-order")
    public List<String> getAssemblyOrder(@PathVariable String faultyComponentId) {
        logger.info("Received request to get assembly order for component with ID: {}", faultyComponentId);
        return componentService.getAssemblyOrder(faultyComponentId);
    }

    @PostMapping("/{sourceId}/precedes/{targetId}")
    public void createPrecedesRelationship(@PathVariable String sourceId, @PathVariable String targetId) {
        logger.info("Received request to create PRECEDES relationship from {} to {}", sourceId, targetId);
        componentService.createPrecedesRelationship(sourceId, targetId);
    }

    @DeleteMapping("/{sourceId}/precedes/{targetId}")
    public void deletePrecedesRelationship(@PathVariable String sourceId, @PathVariable String targetId) {
        logger.info("Received request to delete PRECEDES relationship from {} to {}", sourceId, targetId);
        componentService.deletePrecedesRelationship(sourceId, targetId);
    }

}