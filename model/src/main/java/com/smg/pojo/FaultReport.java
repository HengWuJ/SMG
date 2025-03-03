package com.smg.pojo;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FaultReport implements Serializable {
    private Long id;
    private String faultId;
    private String workerId;
    private String workerName;
    private String workerPhone;
    private LocalDateTime reportTime;
    private String description;
    private String status; // 例如：待处理、处理中、已解决
    private String severity; // 例如：轻微、中等、严重
    private String affectedSystems; // 如：电气系统、机械系统
    private String rootCause; // 根本原因
    private String resolutionSteps; // 解决步骤
    private String additionalNotes; // 额外备注
}