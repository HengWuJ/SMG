package com.smg.kbm.controller;

import com.smg.kbm.service.KbmService;
import com.smg.knowledge.node.Tool;
import com.smg.kbm.feign.LogFeignClient;
import com.smg.pojo.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tool")
public class ToolController {

    private final KbmService kbmService;
    private final LogFeignClient logFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(ToolController.class);

    public ToolController(KbmService kbmService, LogFeignClient logFeignClient) {
        this.kbmService = kbmService;
        this.logFeignClient = logFeignClient;
    }

    private void createLog(String content, String user, boolean successful) {
        Log log = new Log();
        log.setContent(content);
        log.setTimestamp(LocalDateTime.now());
        log.setUser(user); // 在实际应用中，这里可能需要根据上下文获取真实的用户名
        log.setSuccessful(successful ? 1 : 0);
        logFeignClient.createLog(log);
    }

    @PostMapping
    public Tool saveTool(@RequestBody Tool tool) {
        logger.info("Request received to save a new tool: {}", tool);
        createLog(String.format("Saving a new tool with ID: %s", tool.getToolId()), "System", true);
        return kbmService.saveTool(tool);
    }

    @GetMapping
    public List<Tool> getAllTools() {
        logger.info("Request received to fetch all tools");
        createLog("Fetching all tools", "System", true);
        return kbmService.getAllTools();
    }

    @GetMapping("/{id}")
    public Tool getToolById(@PathVariable String id) {
        logger.info("Request received to fetch tool by ID: {}", id);
        createLog(String.format("Fetching tool by ID: %s", id), "System", true);
        return kbmService.getToolById(id);
    }

    @PutMapping("/{id}")
    public Tool updateTool(@PathVariable String id, @RequestBody Tool tool) {
        logger.info("Request received to update tool by ID: {}", id);
        createLog(String.format("Updating tool with ID: %s", id), "System", true);
        return kbmService.updateTool(id, tool);
    }

    @DeleteMapping("/{id}")
    public void deleteTool(@PathVariable String id) {
        logger.info("Request received to delete tool by ID: {}", id);
        createLog(String.format("Deleting tool with ID: %s", id), "System", true);
        kbmService.deleteTool(id);
    }
}
