package com.smg.kbm.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.smg.kbm.feign.KnowledgeFeignClient;
import com.smg.knowledge.node.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KbmService {

    private final KnowledgeFeignClient knowledgeFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(KbmService.class);
    @Autowired
    public KbmService(KnowledgeFeignClient knowledgeFeignClient) {
        this.knowledgeFeignClient = knowledgeFeignClient;
    }

    // Device related methods
    public Device saveDevice(Device device, Set<String> componentIds) {
        logger.info("Saving device: {}, componentIds: {}", device, componentIds);
        return knowledgeFeignClient.saveDevice(device, componentIds);
    }

    public List<Device> getAllDevices() {
        logger.info("Getting all devices");
        return knowledgeFeignClient.getAllDevices();
    }

    public Device getDeviceById(String id) {
        logger.info("Getting device by ID: {}", id);
        return knowledgeFeignClient.getDeviceById(id);
    }

    public List<Device> getDevicesByManufacturer(String manufacturer) {
        logger.info("Getting devices by manufacturer: {}", manufacturer);
        return knowledgeFeignClient.getDevicesByManufacturer(manufacturer);
    }

    public Device updateDevice(String id, Device device, Set<String> componentIds) {
        logger.info("Updating device with ID: {}, device: {}, componentIds: {}", id, device, componentIds);
        return knowledgeFeignClient.updateDevice(id, device, componentIds);
    }

    public void deleteDevice(String id) {
        logger.info("Deleting device with ID: {}", id);
        knowledgeFeignClient.deleteDevice(id);
    }

    public Device addComponentToDevice(String deviceId, String componentId) {
        logger.info("Adding component with ID: {} to device with ID: {}", componentId, deviceId);
        return knowledgeFeignClient.addComponentToDevice(deviceId, componentId);
    }

    public Device removeComponentFromDevice(String deviceId, String componentId) {
        logger.info("Removing component with ID: {} from device with ID: {}", componentId, deviceId);
        return knowledgeFeignClient.removeComponentFromDevice(deviceId, componentId);
    }
    // Step related methods
    public Step saveStep(Step step) {
        logger.info("Saving step: {}", step);
        return knowledgeFeignClient.saveStep(step);
    }

    public Step getStepById(String id) {
        logger.info("Getting step by ID: {}", id);
        return knowledgeFeignClient.getStepById(id);
    }

    public List<Step> getAllSteps() {
        logger.info("Getting all steps");
        return knowledgeFeignClient.getAllSteps();
    }

    public List<Step> getStepsByEstimatedTimeGreaterThan(int estimatedTime) {
        logger.info("Getting steps with estimated time greater than: {}", estimatedTime);
        return knowledgeFeignClient.getStepsByEstimatedTimeGreaterThan(estimatedTime);
    }

    public Step updateStep(String id, Step step) {
        logger.info("Updating step with ID: {}, step: {}", id, step);
        return knowledgeFeignClient.updateStep(id, step);
    }

    public void deleteStep(String id) {
        logger.info("Deleting step with ID: {}", id);
        knowledgeFeignClient.deleteStep(id);
    }

    // Fault related methods
    public Fault saveFault(Fault fault, Set<String> solutionIds) {
        logger.info("Saving fault: {}, solutionIds: {}", fault, solutionIds);
        return knowledgeFeignClient.saveFault(fault, solutionIds);
    }

    public List<Fault> getAllFaults() {
        logger.info("Getting all faults");
        return knowledgeFeignClient.getAllFaults();
    }

    public Fault getFaultById(String id) {
        logger.info("Getting fault by ID: {}", id);
        return knowledgeFeignClient.getFaultById(id);
    }

    public List<Fault> getFaultsBySeverity(String severity) {
        logger.info("Getting faults by severity: {}", severity);
        return knowledgeFeignClient.getFaultsBySeverity(severity);
    }

    public Fault updateFault(String id, Fault fault, Set<String> solutionIds) {
        logger.info("Updating fault with ID: {}, fault: {}, solutionIds: {}", id, fault, solutionIds);
        return knowledgeFeignClient.updateFault(id, fault, solutionIds);
    }

    public void deleteFault(String id) {
        logger.info("Deleting fault with ID: {}", id);
        knowledgeFeignClient.deleteFault(id);
    }

    public Fault addSolutionToFault(String faultId, String solutionId) {
        logger.info("Adding solution with ID: {} to fault with ID: {}", solutionId, faultId);
        return knowledgeFeignClient.addSolutionToFault(faultId, solutionId);
    }

    public Fault removeSolutionFromFault(String faultId, String solutionId) {
        logger.info("Removing solution with ID: {} from fault with ID: {}", solutionId, faultId);
        return knowledgeFeignClient.removeSolutionFromFault(faultId, solutionId);
    }

    // Solution related methods
    public Solution saveSolutionWithFault(Solution solution, String faultId) {
        logger.info("Saving solution: {}, faultId: {}", solution, faultId);
        return knowledgeFeignClient.saveSolutionWithFault(solution, faultId);
    }

    public Solution getSolutionById(String id) {
        logger.info("Getting solution by ID: {}", id);
        return knowledgeFeignClient.getSolutionById(id);
    }

    public Solution updateSolution(String id, Solution solution, String faultId) {
        logger.info("Updating solution with ID: {}, solution: {}, faultId: {}", id, solution, faultId);
        return knowledgeFeignClient.updateSolution(id, solution, faultId);
    }

    public void deleteSolution(String id) {
        logger.info("Deleting solution with ID: {}", id);
        knowledgeFeignClient.deleteSolution(id);
    }

    // Procedure related methods
    public Procedure saveProcedure(Procedure procedure, Set<String> stepIds) {
        logger.info("Saving procedure: {}, stepIds: {}", procedure, stepIds);
        return knowledgeFeignClient.saveProcedure(procedure, stepIds);
    }

    public List<Procedure> getAllProcedures() {
        logger.info("Getting all procedures");
        return knowledgeFeignClient.getAllProcedures();
    }

    public Procedure getProcedureById(String id) {
        logger.info("Getting procedure by ID: {}", id);
        return knowledgeFeignClient.getProcedureById(id);
    }

    public List<Procedure> getProceduresByCategory(String category) {
        logger.info("Getting procedures by category: {}", category);
        return knowledgeFeignClient.getProceduresByCategory(category);
    }

    public Procedure updateProcedure(String id, Procedure procedure, Set<String> stepIds) {
        logger.info("Updating procedure with ID: {}, procedure: {}, stepIds: {}", id, procedure, stepIds);
        return knowledgeFeignClient.updateProcedure(id, procedure, stepIds);
    }

    public void deleteProcedure(String id) {
        logger.info("Deleting procedure with ID: {}", id);
        knowledgeFeignClient.deleteProcedure(id);
    }

    public Procedure addStepToProcedure(String procedureId, String stepId) {
        logger.info("Adding step with ID: {} to procedure with ID: {}", stepId, procedureId);
        return knowledgeFeignClient.addStepToProcedure(procedureId, stepId);
    }

    public Procedure removeStepFromProcedure(String procedureId, String stepId) {
        logger.info("Removing step with ID: {} from procedure with ID: {}", stepId, procedureId);
        return knowledgeFeignClient.removeStepFromProcedure(procedureId, stepId);
    }

    // Component related methods
    public Component saveComponent(Component component) {
        logger.info("Saving component: {}", component);
        return knowledgeFeignClient.saveComponent(component);
    }

    public Component getComponentById(String id) {
        logger.info("Getting component by ID: {}", id);
        return knowledgeFeignClient.getComponentById(id);
    }

    public List<Component> getAllComponents() {
        logger.info("Getting all components");
        return knowledgeFeignClient.getAllComponents();
    }

    public List<Component> getComponentsByCriticalityLevel(String criticalityLevel) {
        logger.info("Getting components by criticality level: {}", criticalityLevel);
        return knowledgeFeignClient.getComponentsByCriticalityLevel(criticalityLevel);
    }

    public Component updateComponent(String id, Component component) {
        logger.info("Updating component with ID: {}, component: {}", id, component);
        return knowledgeFeignClient.updateComponent(id, component);
    }

    public void deleteComponent(String id) {
        logger.info("Deleting component with ID: {}", id);
        knowledgeFeignClient.deleteComponent(id);
    }

    public Component addFaultToComponent(String componentId, String faultId) {
        logger.info("Adding fault with ID: {} to component with ID: {}", faultId, componentId);
        return knowledgeFeignClient.addFaultToComponent(componentId, faultId);
    }

    public Component removeFaultFromComponent(String componentId, String faultId) {
        logger.info("Removing fault with ID: {} from component with ID: {}", faultId, componentId);
        return knowledgeFeignClient.removeFaultFromComponent(componentId, faultId);
    }

//    public void exportNodesToLocalFile(String filePath) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
//        logger.info("Exporting all nodes to CSV and saving to local file: {}", filePath);
//
//        Map<String, List<?>> nodes = Map.of(
//                "components", knowledgeFeignClient.getAllComponents(),
//                "faults", knowledgeFeignClient.getAllFaults(),
//                "solutions", knowledgeFeignClient.getAllSolutions(),
//                "steps", knowledgeFeignClient.getAllSteps(),
//                "procedures", knowledgeFeignClient.getAllProcedures(),
//                "devices", knowledgeFeignClient.getAllDevices()
//        );
//
//        try (FileWriter writer = new FileWriter(filePath , StandardCharsets.UTF_8)) {
//            // Write each node type to CSV
//            writeCsv(writer, "components", (List<Component>) nodes.get("components"), Component.class);
//            writeCsv(writer, "faults", (List<Fault>) nodes.get("faults"), Fault.class);
//            writeCsv(writer, "solutions", (List<Solution>) nodes.get("solutions"), Solution.class);
//            writeCsv(writer, "steps", (List<Step>) nodes.get("steps"), Step.class);
//            writeCsv(writer, "procedures", (List<Procedure>) nodes.get("procedures"), Procedure.class);
//            writeCsv(writer, "devices", (List<Device>) nodes.get("devices"), Device.class);
//        }
//    }
//
//    private <T> void writeCsv(FileWriter writer, String type, List<T> items, Class<T> clazz) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
//        if (items != null && !items.isEmpty()) {
//            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
//                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
//                    .build();
//            beanToCsv.write(items);
//        }
//    }


    public void exportNodesToLocalFile(String filePath) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        logger.info("Exporting all nodes to CSV and saving to local file: {}", filePath);

        Map<String, List<?>> nodes = Map.of(
                "components", knowledgeFeignClient.getAllComponents(),
                "faults", knowledgeFeignClient.getAllFaults(),
                "solutions", knowledgeFeignClient.getAllSolutions(),
                "steps", knowledgeFeignClient.getAllSteps(),
                "procedures", knowledgeFeignClient.getAllProcedures(),
                "devices", knowledgeFeignClient.getAllDevices()
        );

        try (Writer writer = new FileWriter(filePath, StandardCharsets.UTF_8)) {
            // Write each node type to CSV
            writeComponents(writer, (List<Component>) nodes.get("components"));
            writer.append("\n"); // Add a newline between sections
            writeFaults(writer, (List<Fault>) nodes.get("faults"));
            writer.append("\n");
            writeSolutions(writer, (List<Solution>) nodes.get("solutions"));
            writer.append("\n");
            writeSteps(writer, (List<Step>) nodes.get("steps"));
            writer.append("\n");
            writeProcedures(writer, (List<Procedure>) nodes.get("procedures"));
            writer.append("\n");
            writeDevices(writer, (List<Device>) nodes.get("devices"));
        }
    }

    private void writeComponents(Writer writer, List<Component> components) throws IOException {
        writer.append("COMPONENTID,CRITICALITYLEVEL,FAULTIDS,MANUFACTUREDATE,NAME,PARTNUMBER,REPLACEMENTHISTORY,SPECIFICATION,STOCKQUANTITY,WARRANTYEXPIRATIONDATE\n");
        for (Component component : components) {
            writer.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%d,%s\n",
                    component.getComponentId(),
                    component.getCriticalityLevel(),
                    component.getFaults().stream().map(Fault::getFaultId).collect(Collectors.joining(";")),
                    component.getManufactureDate(),
                    component.getName(),
                    component.getPartNumber(),
                    component.getReplacementHistory().stream().collect(Collectors.joining(";")),
                    component.getSpecification(),
                    component.getStockQuantity(),
                    component.getWarrantyExpirationDate()
            ));
        }
    }

    private void writeFaults(Writer writer, List<Fault> faults) throws IOException {
        writer.append("FAULTID,AFFECTEDSYSTEMS,DATE,DESCRIPTION,RESOLUTIONSTATUS,ROOTCAUSE,SEVERITY,SOLUTIONIDS\n");
        for (Fault fault : faults) {
            writer.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s\n",
                    fault.getFaultId(),
                    fault.getAffectedSystems(),
                    fault.getDate(),
                    fault.getDescription(),
                    fault.getResolutionStatus(),
                    fault.getRootCause(),
                    fault.getSeverity(),
                    fault.getSolutions().stream().map(Solution::getSolutionId).collect(Collectors.joining(";"))
            ));
        }
    }

    private void writeSolutions(Writer writer, List<Solution> solutions) throws IOException {
        writer.append("SOLUTIONID,COST,DATE,DESCRIPTION,DURATION,EFFECTIVENESSRATING,MATERIALSUSED,TECHNICIAN\n");
        for (Solution solution : solutions) {
            writer.append(String.format("%s,%.2f,%s,%s,%d,%d,%s,%s\n",
                    solution.getSolutionId(),
                    solution.getCost(),
                    solution.getDate(),
                    solution.getDescription(),
                    solution.getDuration(),
                    solution.getEffectivenessRating(),
                    solution.getMaterialsUsed(),
                    solution.getTechnician()
            ));
        }
    }

    private void writeSteps(Writer writer, List<Step> steps) throws IOException {
        writer.append("STEPID,DESCRIPTION,ESTIMATEDTIME,ORDER,PREDECESSORSTEPIDS,SUCCESSORSTEPIDS,TOOLSREQUIRED\n");
        for (Step step : steps) {
            writer.append(String.format("%s,%s,%d,%d,%s,%s,%s\n",
                    step.getStepId(),
                    step.getDescription(),
                    step.getEstimatedTime(),
                    step.getOrder(),
                    step.getPredecessorSteps().stream().map(Step::getStepId).collect(Collectors.joining(";")),
                    step.getSuccessorSteps().stream().map(Step::getStepId).collect(Collectors.joining(";")),
                    step.getToolsRequired()
            ));
        }
    }

    private void writeProcedures(Writer writer, List<Procedure> procedures) throws IOException {
        writer.append("PROCEDUREID,CATEGORY,FREQUENCY,NAME,REQUIREDILLS,SAFETYPRECAUTIONS,STEPIDS\n");
        for (Procedure procedure : procedures) {
            writer.append(String.format("%s,%s,%s,%s,%s,%s,%s\n",
                    procedure.getProcedureId(),
                    procedure.getCategory(),
                    procedure.getFrequency(),
                    procedure.getName(),
                    procedure.getRequiredSkills(),
                    procedure.getSafetyPrecautions(),
                    procedure.getSteps().stream().map(Step::getStepId).collect(Collectors.joining(";"))
            ));
        }
    }

    private void writeDevices(Writer writer, List<Device> devices) throws IOException {
        writer.append("DEVICEID,COMPONENTIDS,INSTALLATIONDATE,LASTMAINTENANCEDATE,LOCATION,MANUFACTURER,MODEL,NAME,OPERATINGHOURS,SERIALNUMBER,STATUS,TYPE\n");
        for (Device device : devices) {
            writer.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%d,%s,%s,%s\n",
                    device.getDeviceId(),
                    device.getComponents().stream().map(Component::getComponentId).collect(Collectors.joining(";")),
                    device.getInstallationDate(),
                    device.getLastMaintenanceDate(),
                    device.getLocation(),
                    device.getManufacturer(),
                    device.getModel(),
                    device.getName(),
                    device.getOperatingHours(),
                    device.getSerialNumber(),
                    device.getStatus(),
                    device.getType()
            ));
        }
    }













    // Import all nodes from CSV
//    public void importNodesFromCsv(Reader reader) throws IOException {
//        logger.info("Importing all nodes from CSV");
//
//        // Read each node type from CSV
//        readCsv(reader, Component.class, knowledgeFeignClient::saveComponent);
//        readCsv(reader, Fault.class, fault -> knowledgeFeignClient.saveFault(fault, null));
//        readCsv(reader, Solution.class, solution -> knowledgeFeignClient.saveSolutionWithFault(solution, null));
//        readCsv(reader, Step.class, knowledgeFeignClient::saveStep);
//        readCsv(reader, Procedure.class, procedure -> knowledgeFeignClient.saveProcedure(procedure, null));
//        readCsv(reader, Device.class, device -> knowledgeFeignClient.saveDevice(device, null));
//    }
//
//    private <T> void readCsv(Reader reader, Class<T> clazz, java.util.function.Consumer<T> saveFunction) throws IOException {
//        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
//                .withType(clazz)
//                .build();
//        List<T> items = csvToBean.parse();
//
//        for (T item : items) {
//            saveFunction.accept(item);
//        }
//    }

    public void importNodesFromCsv(Reader reader) throws IOException, CsvValidationException {
        logger.info("Importing all nodes from CSV");

        // Read each node type from CSV
        readComponents(reader);
        readFaults(reader);
        readSolutions(reader);
        readSteps(reader);
        readProcedures(reader);
        readDevices(reader);
    }

    private void readComponents(Reader reader) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(reader);
        String[] line;
        boolean isFirstLine = true;

        while ((line = csvReader.readNext()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue; // Skip header line
            }

            Component component = new Component();
            component.setComponentId(line[0]);
            component.setCriticalityLevel(line[1]);
//            component.setFaultIds(Arrays.stream(line[2].split(";")).collect(Collectors.toSet()));
            component.setManufactureDate(line[3]);
            component.setName(line[4]);
            component.setPartNumber(line[5]);
            component.setReplacementHistory(Arrays.stream(line[6].split(";")).collect(Collectors.toSet()));
            component.setSpecification(line[7]);
            component.setStockQuantity(Integer.parseInt(line[8]));
            component.setWarrantyExpirationDate(line[9]);

            knowledgeFeignClient.saveComponent(component);
        }
    }

    private void readFaults(Reader reader) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(reader);
        String[] line;
        boolean isFirstLine = true;

        while ((line = csvReader.readNext()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue; // Skip header line
            }

            Fault fault = new Fault();
            fault.setFaultId(line[0]);
            fault.setAffectedSystems(line[1]);
            fault.setDate(line[2]);
            fault.setDescription(line[3]);
            fault.setResolutionStatus(line[4]);
            fault.setRootCause(line[5]);
            fault.setSeverity(line[6]);
//            fault.setSolutionIds(Arrays.stream(line[7].split(";")).collect(Collectors.toSet()));

            knowledgeFeignClient.saveFault(fault, null);
        }
    }

    private void readSolutions(Reader reader) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(reader);
        String[] line;
        boolean isFirstLine = true;

        while ((line = csvReader.readNext()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue; // Skip header line
            }

            Solution solution = new Solution();
            solution.setSolutionId(line[0]);
            solution.setCost(Double.parseDouble(line[1]));
            solution.setDate(line[2]);
            solution.setDescription(line[3]);
            solution.setDuration(Integer.parseInt(line[4]));
            solution.setEffectivenessRating(Integer.parseInt(line[5]));
            solution.setMaterialsUsed(line[6]);
            solution.setTechnician(line[7]);

            knowledgeFeignClient.saveSolutionWithFault(solution, null);
        }
    }

    private void readSteps(Reader reader) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(reader);
        String[] line;
        boolean isFirstLine = true;

        while ((line = csvReader.readNext()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue; // Skip header line
            }

            Step step = new Step();
            step.setStepId(line[0]);
            step.setDescription(line[1]);
            step.setEstimatedTime(Integer.parseInt(line[2]));
            step.setOrder(Integer.parseInt(line[3]));
            step.setToolsRequired(line[6]);

            knowledgeFeignClient.saveStep(step);
        }
    }

    private void readProcedures(Reader reader) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(reader);
        String[] line;
        boolean isFirstLine = true;

        while ((line = csvReader.readNext()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue; // Skip header line
            }

            Procedure procedure = new Procedure();
            procedure.setProcedureId(line[0]);
            procedure.setCategory(line[1]);
            procedure.setFrequency(line[2]);
            procedure.setName(line[3]);
            procedure.setRequiredSkills(line[4]);
            procedure.setSafetyPrecautions(line[5]);
//            procedure.setStepIds(Arrays.stream(line[6].split(";")).collect(Collectors.toSet()));

            knowledgeFeignClient.saveProcedure(procedure, null);
        }
    }

    private void readDevices(Reader reader) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(reader);
        String[] line;
        boolean isFirstLine = true;

        while ((line = csvReader.readNext()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue; // Skip header line
            }

            Device device = new Device();
            device.setDeviceId(line[0]);
//            device.setComponentIds(Arrays.stream(line[1].split(";")).collect(Collectors.toSet()));
            device.setInstallationDate(line[2]);
            device.setLastMaintenanceDate(line[3]);
            device.setLocation(line[4]);
            device.setManufacturer(line[5]);
            device.setModel(line[6]);
            device.setName(line[7]);
            device.setOperatingHours(Integer.parseInt(line[8]));
            device.setSerialNumber(line[9]);
            device.setStatus(line[10]);
            device.setType(line[11]);

            knowledgeFeignClient.saveDevice(device, null);
        }
    }
}




