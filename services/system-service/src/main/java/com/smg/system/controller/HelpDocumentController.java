package com.smg.system.controller;

import com.smg.system.pojo.HelpDocument;
import com.smg.system.service.HelpDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/help")
public class HelpDocumentController {

    @Autowired
    private HelpDocumentService helpDocumentService;

    @GetMapping("/documents")
    public ResponseEntity<List<HelpDocument>> getHelpDocuments() {
        return ResponseEntity.ok(helpDocumentService.getAllDocuments());
    }

    @PostMapping("/documents")
    public ResponseEntity<HelpDocument> createHelpDocument(@RequestBody HelpDocument document) {
        helpDocumentService.createDocument(document);
        return ResponseEntity.ok(document);
    }
}