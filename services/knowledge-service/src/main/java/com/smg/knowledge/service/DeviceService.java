package com.smg.knowledge.service;

import com.smg.knowledge.node.Component;
import com.smg.knowledge.node.Device;
import com.smg.knowledge.repository.ComponentRepository;
import com.smg.knowledge.repository.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ComponentRepository componentRepository;

    public Device saveDeviceWithComponents(Device device, Set<String> componentIds) {
        logger.info("Saving device with components: {}, componentIds: {}", device, componentIds);
        if (device == null || componentIds == null) {
            logger.error("Device or componentIds is null");
            throw new IllegalArgumentException("Device and componentIds cannot be null");
        }
        Set<Component> components = new HashSet<>(componentRepository.findAllById(componentIds));

        device.setComponents(components);
        return deviceRepository.save(device);
    }

    public Device findById(String id) {
        logger.info("Finding device by ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        return deviceRepository.findById(id).orElse(null);
    }

    public List<Device> findAll() {
        logger.info("Finding all devices");
        return deviceRepository.findAll();
    }

    public List<Device> findByManufacturer(String manufacturer) {
        logger.info("Finding devices by manufacturer: {}", manufacturer);
        if (manufacturer == null) {
            logger.error("Manufacturer is null");
            throw new IllegalArgumentException("Manufacturer cannot be null");
        }
        return deviceRepository.findByManufacturer(manufacturer);
    }

    public Device updateDevice(String id, Device device, Set<String> componentIds) {
        logger.info("Updating device with ID: {}, device: {}, componentIds: {}", id, device, componentIds);
        if (id == null || device == null || componentIds == null) {
            logger.error("ID, device, or componentIds is null");
            throw new IllegalArgumentException("ID, device, and componentIds cannot be null");
        }
        Device existingDevice = deviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Device not found"));
        existingDevice.setName(device.getName());
        existingDevice.setManufacturer(device.getManufacturer());
        existingDevice.setModel(device.getModel());
        existingDevice.setSerialNumber(device.getSerialNumber());
        existingDevice.setInstallationDate(device.getInstallationDate());
        existingDevice.setLocation(device.getLocation());
//        existingDevice.setMaintenanceSchedule(device.getMaintenanceSchedule());
//        existingDevice.setOperationalStatus(device.getOperationalStatus());
        existingDevice.setLastMaintenanceDate(device.getLastMaintenanceDate());
        Set<Component> components = (Set<Component>) componentRepository.findAllById(componentIds);
        existingDevice.setComponents(components);
        return deviceRepository.save(existingDevice);
    }

    public void deleteDevice(String id) {
        logger.info("Deleting device with ID: {}", id);
        if (id == null) {
            logger.error("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        deviceRepository.deleteById(id);
    }

    public Device addComponentToDevice(String deviceId, String componentId) {
        logger.info("Adding component with ID: {} to device with ID: {}", componentId, deviceId);
        if (deviceId == null || componentId == null) {
            logger.error("Device ID or Component ID is null");
            throw new IllegalArgumentException("Device ID and Component ID cannot be null");
        }
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new RuntimeException("Device not found"));
        Component component = componentRepository.findById(componentId).orElseThrow(() -> new RuntimeException("Component not found"));
        device.getComponents().add(component);
        return deviceRepository.save(device);
    }

    public Device removeComponentFromDevice(String deviceId, String componentId) {
        logger.info("Removing component with ID: {} from device with ID: {}", componentId, deviceId);
        if (deviceId == null || componentId == null) {
            logger.error("Device ID or Component ID is null");
            throw new IllegalArgumentException("Device ID and Component ID cannot be null");
        }
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new RuntimeException("Device not found"));
        Component component = componentRepository.findById(componentId).orElseThrow(() -> new RuntimeException("Component not found"));
        device.getComponents().remove(component);
        return deviceRepository.save(device);
    }
}