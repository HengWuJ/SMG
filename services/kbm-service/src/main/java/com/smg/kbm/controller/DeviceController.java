package com.smg.kbm.controller;

import com.smg.kbm.service.KbmService;
import com.smg.knowledge.node.Device;
import com.smg.kbm.feign.LogFeignClient;
import com.smg.pojo.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final KbmService kbmService;
    private final LogFeignClient logFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    public DeviceController(KbmService kbmService, LogFeignClient logFeignClient) {
        this.kbmService = kbmService;
        this.logFeignClient = logFeignClient;
    }

    private void createLog(String content, String user, boolean successful) {
        Log log = new Log();
        log.setContent(content);
        log.setTimestamp(LocalDateTime.now());
        log.setUser(user);
        log.setSuccessful(successful ? 1 : 0);
        logFeignClient.createLog(log);
    }

    @PostMapping
    public Device saveDevice(@RequestBody Device device, @RequestParam Set<String> componentIds) {
        logger.info("Request received to save a new device: {}", device.getDeviceId());
        createLog(String.format("Saving a new device with ID: %s and associated components: %s", device.getDeviceId(), componentIds), "System", true);
        return kbmService.saveDevice(device, componentIds);
    }

    @GetMapping
    public Iterable<Device> getAllDevices() {
        logger.info("Request received to fetch all devices.");
        createLog("Fetching all devices.", "System", true);
        return kbmService.getAllDevices();
    }

    @GetMapping("/{id}")
    public Device getDeviceById(@PathVariable String id) {
        logger.info("Request received to fetch device by ID: {}", id);
        createLog(String.format("Fetching device by ID: %s", id), "System", true);
        return kbmService.getDeviceById(id);
    }

    @GetMapping("/manufacturer/{manufacturer}")
    public Iterable<Device> getDevicesByManufacturer(@PathVariable String manufacturer) {
        logger.info("Request received to fetch devices by manufacturer: {}", manufacturer);
        createLog(String.format("Fetching devices by manufacturer: %s", manufacturer), "System", true);
        return kbmService.getDevicesByManufacturer(manufacturer);
    }

    @PutMapping("/{id}")
    public Device updateDevice(@PathVariable String id, @RequestBody Device device, @RequestParam Set<String> componentIds) {
        logger.info("Request received to update device by ID: {}", id);
        createLog(String.format("Updating device with ID: %s and associated components: %s", id, componentIds), "System", true);
        return kbmService.updateDevice(id, device, componentIds);
    }

    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable String id) {
        logger.info("Request received to delete device by ID: {}", id);
        createLog(String.format("Deleting device with ID: %s", id), "System", true);
        kbmService.deleteDevice(id);
    }

    @PostMapping("/{deviceId}/components/{componentId}")
    public Device addComponentToDevice(@PathVariable String deviceId, @PathVariable String componentId) {
        logger.info("Received request to add component with ID: {} to device with ID: {}", componentId, deviceId);
        createLog(String.format("Adding component with ID: %s to device with ID: %s", componentId, deviceId), "System", true);
        return kbmService.addComponentToDevice(deviceId, componentId);
    }

    @DeleteMapping("/{deviceId}/components/{componentId}")
    public Device removeComponentFromDevice(@PathVariable String deviceId, @PathVariable String componentId) {
        logger.info("Received request to remove component with ID: {} from device with ID: {}", componentId, deviceId);
        createLog(String.format("Removing component with ID: %s from device with ID: %s", componentId, deviceId), "System", true);
        return kbmService.removeComponentFromDevice(deviceId, componentId);
    }
}