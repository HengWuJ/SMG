package com.smg.monitor.service;

import com.smg.monitor.mapper.AlarmRuleMapper;
import com.smg.monitor.pojo.AlarmRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmRuleService {

    private static final Logger logger = LoggerFactory.getLogger(AlarmRuleService.class);

    @Autowired
    private AlarmRuleMapper alarmRuleMapper;

    public void addRule(AlarmRule alarmRule) {
        if (alarmRule == null) {
            logger.error("Attempted to add a null alarm rule.");
            throw new IllegalArgumentException("Alarm rule cannot be null");
        }
        if (alarmRule.getSensorType() == null || alarmRule.getFieldName() == null) {
            logger.error("Sensor type or field name cannot be null for alarm rule: {}", alarmRule);
            throw new IllegalArgumentException("Sensor type and field name cannot be null");
        }
        alarmRuleMapper.insert(alarmRule);
        logger.info("Alarm rule added: {}", alarmRule);
    }

    public void updateRule(AlarmRule alarmRule) {
        if (alarmRule == null) {
            logger.error("Attempted to update a null alarm rule.");
            throw new IllegalArgumentException("Alarm rule cannot be null");
        }
        if (alarmRule.getId() == null) {
            logger.error("Alarm rule ID cannot be null for update: {}", alarmRule);
            throw new IllegalArgumentException("Alarm rule ID cannot be null");
        }
        if (alarmRule.getSensorType() == null || alarmRule.getFieldName() == null) {
            logger.error("Sensor type or field name cannot be null for alarm rule: {}", alarmRule);
            throw new IllegalArgumentException("Sensor type and field name cannot be null");
        }
        alarmRuleMapper.update(alarmRule);
        logger.info("Alarm rule updated: {}", alarmRule);
    }

    public void deleteRule(Integer id) {
        if (id == null) {
            logger.error("Attempted to delete a rule with null ID.");
            throw new IllegalArgumentException("Rule ID cannot be null");
        }
        alarmRuleMapper.deleteById(id);
        logger.info("Alarm rule deleted with ID: {}", id);
    }

    public List<AlarmRule> getAllRules() {
        List<AlarmRule> rules = alarmRuleMapper.findAll();
        logger.info("Retrieved all alarm rules: {}", rules);
        return rules;
    }
}