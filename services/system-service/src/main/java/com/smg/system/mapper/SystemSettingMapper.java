package com.smg.system.mapper;


import com.smg.system.pojo.SystemSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemSettingMapper {
    SystemSetting selectByKey(@Param("key") String key);
    void insertOrUpdate(SystemSetting systemSetting);
    List<SystemSetting> getAllSettings();
}