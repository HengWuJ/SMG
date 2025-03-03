package com.smg.monitor.config;

import com.smg.monitor.pojo.SensorData;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import java.util.function.Consumer;

@Component
public class KafkaConfig {

    @Bean
    public Consumer<Flux<Message<SensorData>>> processSensorData(StreamBridge streamBridge) {
        return flux -> flux.doOnNext(message -> streamBridge.send("sensorData-out-0", message))
                .subscribe(); // 订阅流以触发消息发送
    }
}