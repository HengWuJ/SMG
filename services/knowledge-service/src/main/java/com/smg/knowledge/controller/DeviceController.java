package com.smg.knowledge.controller;

import com.smg.knowledge.node.Device;
import com.smg.knowledge.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public Device saveDevice(@RequestBody Device device, @RequestParam Set<String> componentIds) {
        logger.info("Received request to save device: {}, componentIds: {}", device, componentIds);
        return deviceService.saveDeviceWithComponents(device, componentIds);
    }

    @GetMapping
    public List<Device> getAllDevices() {
        logger.info("Received request to get all devices");
        return deviceService.findAll();
    }

    @GetMapping("/{id}")
    public Device getDeviceById(@PathVariable String id) {
        logger.info("Received request to get device by ID: {}", id);
        return deviceService.findById(id);
    }

    @GetMapping("/manufacturer/{manufacturer}")
    public List<Device> getDevicesByManufacturer(@PathVariable String manufacturer) {
        logger.info("Received request to get devices by manufacturer: {}", manufacturer);
        return deviceService.findByManufacturer(manufacturer);
    }

    @PutMapping("/{id}")
    public Device updateDevice(@PathVariable String id, @RequestBody Device device, @RequestParam Set<String> componentIds) {
        logger.info("Received request to update device with ID: {}, device: {}, componentIds: {}", id, device, componentIds);
        return deviceService.updateDevice(id, device, componentIds);
    }

    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable String id) {
        logger.info("Received request to delete device with ID: {}", id);
        deviceService.deleteDevice(id);
    }

    @PostMapping("/{deviceId}/components/{componentId}")
    public Device addComponentToDevice(@PathVariable String deviceId, @PathVariable String componentId) {
        return deviceService.addComponentToDevice(deviceId, componentId);
    }

    @DeleteMapping("/{deviceId}/components/{componentId}")
    public Device removeComponentFromDevice(@PathVariable String deviceId, @PathVariable String componentId) {
        return deviceService.removeComponentFromDevice(deviceId, componentId);
    }
}