package com.smg.knowledge.controller;

import com.smg.knowledge.node.Tool;
import com.smg.knowledge.service.ToolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tool")
public class ToolController {

    private static final Logger logger = LoggerFactory.getLogger(ToolController.class);

    @Autowired
    private ToolService toolService;

    @GetMapping
    public List<Tool> getAllTools() {
        logger.info("Received request to get all tools");
        return toolService.getAllTools();
    }

    @PostMapping
    public Tool saveTool(@RequestBody Tool tool) {
        logger.info("Received request to save tool: {}", tool);
        return toolService.saveTool(tool);
    }

    @GetMapping("/{id}")
    public Tool getToolById(@PathVariable String id) {
        logger.info("Received request to get tool by ID: {}", id);
        return toolService.findById(id);
    }

    @PutMapping("/{id}")
    public Tool updateTool(@PathVariable String id, @RequestBody Tool tool) {
        logger.info("Received request to update tool with ID: {}, tool: {}", id, tool);
        return toolService.updateTool(id, tool);
    }

    @DeleteMapping("/{id}")
    public void deleteTool(@PathVariable String id) {
        logger.info("Received request to delete tool with ID: {}", id);
        toolService.deleteTool(id);
    }
}