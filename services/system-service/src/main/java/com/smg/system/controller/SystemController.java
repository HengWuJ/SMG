package com.smg.system.controller;

import com.smg.system.pojo.SystemSetting;
import com.smg.system.service.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemSettingService systemSettingService;

    @GetMapping("/settings/{key}")
    public ResponseEntity<?> getSetting(@PathVariable String key) {
        SystemSetting setting = systemSettingService.getSetting(key);
        if (setting == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(setting);
    }

    @PutMapping("/settings")
    public ResponseEntity<?> updateSetting(@RequestBody SystemSetting setting) {
        systemSettingService.updateSetting(setting);
        return ResponseEntity.ok("Setting updated");
    }

    @GetMapping("/settings")
    public ResponseEntity<?> getAllSettings() {
        return ResponseEntity.ok(systemSettingService.getAllSettings());
    }
}