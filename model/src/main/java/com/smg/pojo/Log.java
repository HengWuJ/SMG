package com.smg.pojo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private String user;
    private int successful;
}