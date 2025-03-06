package com.smg.knowledge.service;

import com.smg.knowledge.node.Tool;
import com.smg.knowledge.repository.ToolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {

    private static final Logger logger = LoggerFactory.getLogger(ToolService.class);

    @Autowired
    private ToolRepository toolRepository;

    public Tool saveTool(Tool tool) {
        logger.info("Saving tool: {}", tool);
        if (tool == null) {
            logger.error("Tool is null");
            throw new IllegalArgumentException("Tool cannot be null");
        }
        return toolRepository.save(tool);
    }

    public Tool findById(String id) {
        logger.info("Finding tool by ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        return toolRepository.findById(id).orElse(null);
    }

    public Tool updateTool(String id, Tool tool) {
        logger.info("Updating tool with ID: {}, tool: {}", id, tool);
        if (id == null || tool == null) {
            logger.error("ID or tool is null");
            throw new IllegalArgumentException("ID and tool cannot be null");
        }
        Tool existingTool = toolRepository.findById(id).orElseThrow(() -> new RuntimeException("Tool not found"));
        existingTool.setName(tool.getName());
        existingTool.setType(tool.getType());
        existingTool.setManufacturer(tool.getManufacturer());
        existingTool.setModel(tool.getModel());
        existingTool.setCost(tool.getCost());
        existingTool.setAvailability(tool.getAvailability());
        existingTool.setLocation(tool.getLocation());
        return toolRepository.save(existingTool);
    }

    public void deleteTool(String id) {
        logger.info("Deleting tool with ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        toolRepository.deleteById(id);
    }

    public List<Tool> getAllTools() {
        logger.info("Getting all tools");
        return toolRepository.findAll();
    }
}