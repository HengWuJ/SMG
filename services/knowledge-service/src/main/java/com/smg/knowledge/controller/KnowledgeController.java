package com.smg.knowledge.controller;

import com.smg.knowledge.node.Device;
import com.smg.knowledge.node.Fault;
import com.smg.knowledge.node.Procedure;
import com.smg.knowledge.service.DeviceService;
import com.smg.knowledge.service.FaultService;
import com.smg.knowledge.service.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    @Autowired
    private FaultService faultService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProcedureService procedureService;

    @PostMapping("/faults")
    public Fault createFaultWithSolutions(@RequestBody Fault fault, @RequestParam Set<String> solutionIds) {
        return faultService.saveFaultWithSolutions(fault, solutionIds);
    }

    @GetMapping("/faults/{id}")
    public Fault getFaultById(@PathVariable String id) {
        return faultService.findById(id);
    }

    @PutMapping("/faults/{id}")
    public Fault updateFault(@PathVariable String id, @RequestBody Fault fault, @RequestParam Set<String> solutionIds) {
        return faultService.updateFault(id, fault, solutionIds);
    }

    @DeleteMapping("/faults/{id}")
    public void deleteFault(@PathVariable String id) {
        faultService.deleteFault(id);
    }

    @PostMapping("/devices")
    public Device createDeviceWithComponents(@RequestBody Device device, @RequestParam Set<String> componentIds) {
        return deviceService.saveDeviceWithComponents(device, componentIds);
    }

    @GetMapping("/devices/{id}")
    public Device getDeviceById(@PathVariable String id) {
        return deviceService.findById(id);
    }

    @PutMapping("/devices/{id}")
    public Device updateDevice(@PathVariable String id, @RequestBody Device device, @RequestParam Set<String> componentIds) {
        return deviceService.updateDevice(id, device, componentIds);
    }

    @DeleteMapping("/devices/{id}")
    public void deleteDevice(@PathVariable String id) {
        deviceService.deleteDevice(id);
    }

    @PostMapping("/procedures")
    public Procedure createProcedureWithSteps(@RequestBody Procedure procedure, @RequestParam Set<String> stepIds) {
        return procedureService.saveProcedureWithSteps(procedure, stepIds);
    }

    @GetMapping("/procedures/{id}")
    public Procedure getProcedureById(@PathVariable String id) {
        return procedureService.findById(id);
    }

    @PutMapping("/procedures/{id}")
    public Procedure updateProcedure(@PathVariable String id, @RequestBody Procedure procedure, @RequestParam Set<String> stepIds) {
        return procedureService.updateProcedure(id, procedure, stepIds);
    }

    @DeleteMapping("/procedures/{id}")
    public void deleteProcedure(@PathVariable String id) {
        procedureService.deleteProcedure(id);
    }
}
