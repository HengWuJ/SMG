package com.smg.system.service;

import com.smg.system.mapper.SystemSettingMapper;
import com.smg.system.pojo.SystemSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemSettingService {

    @Autowired
    private SystemSettingMapper systemSettingMapper;

    public SystemSetting getSetting(String key) {
        return systemSettingMapper.selectByKey(key);
    }

    public void updateSetting(SystemSetting setting) {
        systemSettingMapper.insertOrUpdate(setting);
    }

    public List<SystemSetting> getAllSettings() {
        return systemSettingMapper.getAllSettings();
    }
}