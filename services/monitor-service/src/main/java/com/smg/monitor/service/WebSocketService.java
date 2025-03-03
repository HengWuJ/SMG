package com.smg.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    public void sendAlarmMessage(String message) {
        template.convertAndSend("/topic/alarm", message);
    }
}