package com.smg.kbm.feign;

import com.smg.knowledge.node.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FeignClient(name = "knowledge-service", url = "${knowledge.service.url}")
public interface KnowledgeFeignClient {

    // DeviceController
    @PostMapping("/device")
    Device saveDevice(@RequestBody Device device, @RequestParam Set<String> componentIds);

    @GetMapping("/device")
    List<Device> getAllDevices();

    @GetMapping("/device/{id}")
    Device getDeviceById(@PathVariable String id);

    @GetMapping("/device/manufacturer/{manufacturer}")
    List<Device> getDevicesByManufacturer(@PathVariable String manufacturer);

    @PutMapping("/device/{id}")
    Device updateDevice(@PathVariable String id, @RequestBody Device device, @RequestParam Set<String> componentIds);

    @DeleteMapping("/device/{id}")
    void deleteDevice(@PathVariable String id);

    @PostMapping("/device/{deviceId}/components/{componentId}")
    Device addComponentToDevice(@PathVariable String deviceId, @PathVariable String componentId);

    @DeleteMapping("/device/{deviceId}/components/{componentId}")
    Device removeComponentFromDevice(@PathVariable String deviceId, @PathVariable String componentId);


    // StepController
    @PostMapping("/step")
    Step saveStep(@RequestBody Step step);

    @GetMapping("/step/{id}")
    Step getStepById(@PathVariable String id);

    @GetMapping("/step")
    List<Step> getAllSteps();

    @GetMapping("/step/estimated-time/{estimatedTime}")
    List<Step> getStepsByEstimatedTimeGreaterThan(@PathVariable int estimatedTime);

    @PutMapping("/step/{id}")
    Step updateStep(@PathVariable String id, @RequestBody Step step);

    @DeleteMapping("/step/{id}")
    void deleteStep(@PathVariable String id);

    // FaultController
    @PostMapping("/fault")
    Fault saveFault(@RequestBody Fault fault, @RequestParam Set<String> solutionIds);

    @GetMapping("/fault")
    List<Fault> getAllFaults();

    @GetMapping("/fault/{id}")
    Fault getFaultById(@PathVariable String id);

    @GetMapping("/fault/severity/{severity}")
    List<Fault> getFaultsBySeverity(@PathVariable String severity);

    @PutMapping("/fault/{id}")
    Fault updateFault(@PathVariable String id, @RequestBody Fault fault, @RequestParam Set<String> solutionIds);

    @DeleteMapping("/fault/{id}")
    void deleteFault(@PathVariable String id);

    @PostMapping("/fault/{faultId}/solutions/{solutionId}")
    Fault addSolutionToFault(@PathVariable String faultId, @PathVariable String solutionId);

    @DeleteMapping("/fault/{faultId}/solutions/{solutionId}")
    Fault removeSolutionFromFault(@PathVariable String faultId, @PathVariable String solutionId);


    // SolutionController
    @GetMapping("/solution")
    List<Solution> getAllSolutions();

    @PostMapping("/solution")
    Solution saveSolution(@RequestBody Solution solution);

    @GetMapping("/solution/{id}")
    Solution getSolutionById(@PathVariable String id);

    @PutMapping("/solution/{id}")
    Solution updateSolution(@PathVariable String id, @RequestBody Solution solution, @RequestParam String faultId);

    @DeleteMapping("/solution/{id}")
    void deleteSolution(@PathVariable String id);

    @PostMapping("/solution/withFault")
    Solution saveSolutionWithFault(@RequestBody Solution solution, @RequestParam String faultId);

    @PostMapping("/solution/{solutionId}/addTool")
    Solution addToolToSolution(@PathVariable String solutionId, @RequestParam String toolId);

    @PostMapping("/solution/{solutionId}/removeTool")
    Solution removeToolFromSolution(@PathVariable String solutionId, @RequestParam String toolId);

    // ProcedureController
    @PostMapping("/procedure")
    Procedure saveProcedure(@RequestBody Procedure procedure, @RequestParam Set<String> stepIds);

    @GetMapping("/procedure")
    List<Procedure> getAllProcedures();

    @GetMapping("/procedure/{id}")
    Procedure getProcedureById(@PathVariable String id);

    @GetMapping("/procedure/category/{category}")
    List<Procedure> getProceduresByCategory(@PathVariable String category);

    @PutMapping("/procedure/{id}")
    Procedure updateProcedure(@PathVariable String id, @RequestBody Procedure procedure, @RequestParam Set<String> stepIds);

    @DeleteMapping("/procedure/{id}")
    void deleteProcedure(@PathVariable String id);

    @PostMapping("/procedure/{procedureId}/steps/{stepId}")
    Procedure addStepToProcedure(@PathVariable String procedureId, @PathVariable String stepId);

    @DeleteMapping("/procedure/{procedureId}/steps/{stepId}")
    Procedure removeStepFromProcedure(@PathVariable String procedureId, @PathVariable String stepId);


    // ComponentController
    @PostMapping("/component")
    Component saveComponent(@RequestBody Component component);

    @GetMapping("/component/{id}")
    Component getComponentById(@PathVariable String id);

    @GetMapping("/component")
    List<Component> getAllComponents();

    @GetMapping("/component/criticality/{criticalityLevel}")
    List<Component> getComponentsByCriticalityLevel(@PathVariable String criticalityLevel);

    @PutMapping("/component/{id}")
    Component updateComponent(@PathVariable String id, @RequestBody Component component);

    @DeleteMapping("/component/{id}")
    void deleteComponent(@PathVariable String id);

    @PostMapping("/component/{componentId}/faults/{faultId}")
    Component addFaultToComponent(@PathVariable String componentId, @PathVariable String faultId);

    @DeleteMapping("/component/{componentId}/faults/{faultId}")
    Component removeFaultFromComponent(@PathVariable String componentId, @PathVariable String faultId);

    @GetMapping("/component/{faultyComponentId}/disassembly-order")
    List<String> getDisassemblyOrder(@PathVariable String faultyComponentId);

    @GetMapping("/component/{faultyComponentId}/assembly-order")
    List<String> getAssemblyOrder(@PathVariable String faultyComponentId);

    @PostMapping("/component/{sourceId}/precedes/{targetId}")
    void createPrecedesRelationship(@PathVariable String sourceId, @PathVariable String targetId);

    @DeleteMapping("/component/{sourceId}/precedes/{targetId}")
    void deletePrecedesRelationship(@PathVariable String sourceId, @PathVariable String targetId);

    @PostMapping("/tool")
    Tool saveTool(@RequestBody Tool tool);

    @GetMapping("/tool")
    List<Tool> getAllTools();

    @GetMapping("/tool/{id}")
    Tool getToolById(@PathVariable String id);

    @PutMapping("/tool/{id}")
    Tool updateTool(@PathVariable String id, @RequestBody Tool tool);

    @DeleteMapping("/tool/{id}")
    void deleteTool(@PathVariable String id);
    }