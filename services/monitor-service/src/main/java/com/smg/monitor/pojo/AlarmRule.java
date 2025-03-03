package com.smg.monitor.pojo;

import lombok.Data;

@Data
public class AlarmRule {

    private Integer id;
    private String sensorType;
    private String fieldName;
    private Double minValue;
    private Double maxValue;
    private String description;
}