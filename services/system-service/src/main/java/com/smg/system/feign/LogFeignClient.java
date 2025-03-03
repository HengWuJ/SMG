package com.smg.system.feign;

import com.smg.pojo.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "log-service", url = "${log.service.url}")
public interface LogFeignClient {

    @GetMapping("/logs")
    List<Log> getAllLogs();

    @PostMapping("/logs")
    Log createLog(@RequestBody Log log);

    @DeleteMapping("/logs/{id}")
    void deleteLog(@PathVariable Long id);
}