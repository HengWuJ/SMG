package com.smg.kbm.controller;

import com.smg.kbm.service.KbmService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/kbm")
public class KbmController {

    @Autowired
    private KbmService kbmService;

    // Export all nodes to CSV and save to local file
    @GetMapping("/export")
    public ResponseEntity<String> exportNodes() {
        String filePath = "D:\\24\\nodes.csv"; // 指定保存文件的路径
        try {
            kbmService.exportNodesToLocalFile(filePath);
            return new ResponseEntity<>("Nodes exported successfully to " + filePath, HttpStatus.OK);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            return new ResponseEntity<>("Failed to export nodes: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Import all nodes from CSV
//    @PostMapping("/import")
//    public ResponseEntity<String> importNodes(@RequestParam("file") MultipartFile file) {
//        try (FileReader reader = new FileReader(file.getOriginalFilename())) {
//            kbmService.importNodesFromCsv(reader);
//            return new ResponseEntity<>("Nodes imported successfully", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to import nodes: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @PostMapping("/import")
    public ResponseEntity<String> importNodes(@RequestParam("file") MultipartFile file) {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {
            kbmService.importNodesFromCsv(reader);
            return new ResponseEntity<>("Nodes imported successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to import nodes: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
