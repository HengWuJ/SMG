package com.smg.monitor.controller;

import com.smg.monitor.pojo.AlarmRule;
import com.smg.monitor.service.AlarmRuleService;
import com.smg.monitor.feign.LogFeignClient;
import com.smg.pojo.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/alarm-rule")
public class AlarmRuleController {

    private static final Logger logger = LoggerFactory.getLogger(AlarmRuleController.class);

    @Autowired
    private AlarmRuleService alarmRuleService;

    @Autowired
    private LogFeignClient logFeignClient;

    private void createLog(String content, String user, boolean successful) {
        Log log = new Log();
        log.setContent(content);
        log.setTimestamp(LocalDateTime.now());
        log.setUser(user);
        log.setSuccessful(successful ? 1 : 0);
        logFeignClient.createLog(log);
    }

    @PostMapping
    public ResponseEntity<?> addRule(@RequestBody AlarmRule alarmRule) {
        try {
            alarmRuleService.addRule(alarmRule);
            logger.info("Alarm rule added: {}", alarmRule);
            createLog("Added alarm rule: " + alarmRule, "System", true);
            return ResponseEntity.ok("Rule added");
        } catch (Exception e) {
            logger.error("Failed to add alarm rule: {}", alarmRule, e);
            createLog("Failed to add alarm rule: " + alarmRule, "System", false);
            return ResponseEntity.status(500).body("Failed to add rule");
        }
    }

    @PutMapping
    public ResponseEntity<?> updateRule(@RequestBody AlarmRule alarmRule) {
        try {
            alarmRuleService.updateRule(alarmRule);
            logger.info("Alarm rule updated: {}", alarmRule);
            createLog("Updated alarm rule: " + alarmRule, "System", true);
            return ResponseEntity.ok("Rule updated");
        } catch (Exception e) {
            logger.error("Failed to update alarm rule: {}", alarmRule, e);
            createLog("Failed to update alarm rule: " + alarmRule, "System", false);
            return ResponseEntity.status(500).body("Failed to update rule");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRule(@PathVariable Integer id) {
        try {
            alarmRuleService.deleteRule(id);
            logger.info("Alarm rule deleted with ID: {}", id);
            createLog("Deleted alarm rule with ID: " + id, "System", true);
            return ResponseEntity.ok("Rule deleted");
        } catch (Exception e) {
            logger.error("Failed to delete alarm rule with ID: {}", id, e);
            createLog("Failed to delete alarm rule with ID: " + id, "System", false);
            return ResponseEntity.status(500).body("Failed to delete rule");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRules() {
        try {
            List<AlarmRule> rules = alarmRuleService.getAllRules();
            logger.info("Retrieved all alarm rules: {}", rules);
            createLog("Retrieved all alarm rules", "System", true);
            return ResponseEntity.ok(rules);
        } catch (Exception e) {
            logger.error("Failed to retrieve all alarm rules", e);
            createLog("Failed to retrieve all alarm rules", "System", false);
            return ResponseEntity.status(500).body("Failed to retrieve rules");
        }
    }
}
