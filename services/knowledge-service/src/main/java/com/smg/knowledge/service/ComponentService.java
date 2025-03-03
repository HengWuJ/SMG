package com.smg.knowledge.service;

import com.smg.knowledge.node.Component;
import com.smg.knowledge.node.Fault;
import com.smg.knowledge.repository.ComponentRepository;
import com.smg.knowledge.repository.FaultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ComponentService {

    private static final Logger logger = LoggerFactory.getLogger(ComponentService.class);

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private FaultRepository faultRepository;

    public Component saveComponent(Component component) {
        logger.info("Saving component: {}", component);
        if (component == null) {
            logger.error("Component is null");
            throw new IllegalArgumentException("Component cannot be null");
        }
        return componentRepository.save(component);
    }

    public Component findById(String id) {
        logger.info("Finding component by ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        return componentRepository.findById(id).orElse(null);
    }

    public List<Component> findAll() {
        logger.info("Finding all components");
        return componentRepository.findAll();
    }

    public List<Component> findByCriticalityLevel(String criticalityLevel) {
        logger.info("Finding components by criticality level: {}", criticalityLevel);
        if (criticalityLevel == null) {
            logger.error("Criticality level is null");
            throw new IllegalArgumentException("Criticality level cannot be null");
        }
        return componentRepository.findByCriticalityLevel(criticalityLevel);
    }

    public Component updateComponent(String id, Component component) {
        logger.info("Updating component with ID: {}, component: {}", id, component);
        if (id == null || component == null) {
            logger.error("ID or component is null");
            throw new IllegalArgumentException("ID and component cannot be null");
        }
        Component existingComponent = componentRepository.findById(id).orElseThrow(() -> new RuntimeException("Component not found"));
        existingComponent.setName(component.getName());
        existingComponent.setSpecification(component.getSpecification());
        existingComponent.setManufactureDate(component.getManufactureDate());
        existingComponent.setWarrantyExpirationDate(component.getWarrantyExpirationDate());
        existingComponent.setReplacementHistory(component.getReplacementHistory());
        existingComponent.setPartNumber(component.getPartNumber());
        existingComponent.setStockQuantity(component.getStockQuantity());
        existingComponent.setCriticalityLevel(component.getCriticalityLevel());
        existingComponent.setFaults(component.getFaults());
        return componentRepository.save(existingComponent);
    }

    public void deleteComponent(String id) {
        logger.info("Deleting component with ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        componentRepository.deleteById(id);
    }

    public Component addFaultToComponent(String componentId, String faultId) {
        logger.info("Adding fault with ID: {} to component with ID: {}", faultId, componentId);
        if (componentId == null || faultId == null) {
            logger.error("Component ID or Fault ID is null");
            throw new IllegalArgumentException("Component ID and Fault ID cannot be null");
        }
        Component component = componentRepository.findById(componentId).orElseThrow(() -> new RuntimeException("Component not found"));
        Fault fault = faultRepository.findById(faultId).orElseThrow(() -> new RuntimeException("Fault not found"));
        component.getFaults().add(fault);
        return componentRepository.save(component);
    }

    public Component removeFaultFromComponent(String componentId, String faultId) {
        logger.info("Removing fault with ID: {} from component with ID: {}", faultId, componentId);
        if (componentId == null || faultId == null) {
            logger.error("Component ID or Fault ID is null");
            throw new IllegalArgumentException("Component ID and Fault ID cannot be null");
        }
        Component component = componentRepository.findById(componentId).orElseThrow(() -> new RuntimeException("Component not found"));
        Fault fault = faultRepository.findById(faultId).orElseThrow(() -> new RuntimeException("Fault not found"));
        component.getFaults().remove(fault);
        return componentRepository.save(component);
    }
}