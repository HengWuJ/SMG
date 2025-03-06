package com.smg.maintenance.feign;

import com.smg.knowledge.node.Fault;
import com.smg.knowledge.node.Procedure;
import com.smg.knowledge.node.Solution;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FeignClient(name = "knowledge-service", url = "${knowledge.service.url}")
public interface KnowledgeFeignClient {

    // FaultController
    @PostMapping("/fault")
    Fault saveFault(@RequestBody Fault fault, @RequestParam Set<String> solutionIds);

    @GetMapping("/fault")
    List<Fault> getAllFaults();

    @GetMapping("/fault/{id}")
    Fault getFaultById(@PathVariable String id);

    @GetMapping("/fault/severity/{severity}")
    List<Fault> getFaultsBySeverity(@PathVariable String severity);

    @GetMapping("/fault/description/{description}")
    List<Fault> getFaultsByDescription(@PathVariable String description);

    @PutMapping("/fault/{id}")
    Fault updateFault(@PathVariable String id, @RequestBody Fault fault, @RequestParam Set<String> solutionIds);

    @DeleteMapping("/fault/{id}")
    void deleteFault(@PathVariable String id);

    // SolutionController
    @PostMapping("/solution")
    Solution saveSolutionWithFault(@RequestBody Solution solution, @RequestParam String faultId);

    @GetMapping("/solution/{id}")
    Solution getSolutionById(@PathVariable String id);

    @PutMapping("/solution/{id}")
    Solution updateSolution(@PathVariable String id, @RequestBody Solution solution, @RequestParam String faultId);

    @DeleteMapping("/solution/{id}")
    void deleteSolution(@PathVariable String id);

    @GetMapping("/fault/search")
    List<Fault> searchFaults(
            @RequestParam(required = false) String deviceDescription,
            @RequestParam(required = false) String componentDescription,
            @RequestParam(required = false) String faultDescription);

    @GetMapping("/procedure")
    List<Procedure> getAllProcedures();

    @GetMapping("/component/{faultyComponentId}/disassembly-order")
    List<String> getDisassemblyOrder(@PathVariable String faultyComponentId);

    @GetMapping("/component/{faultyComponentId}/assembly-order")
    List<String> getAssemblyOrder(@PathVariable String faultyComponentId);
}