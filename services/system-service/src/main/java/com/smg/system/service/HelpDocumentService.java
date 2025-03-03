package com.smg.system.service;


import com.smg.system.mapper.HelpDocumentMapper;
import com.smg.system.pojo.HelpDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpDocumentService {

    @Autowired
    private HelpDocumentMapper helpDocumentMapper;

    public List<HelpDocument> getAllDocuments() {
        return helpDocumentMapper.findAll();
    }

    public void createDocument(HelpDocument document) {
        helpDocumentMapper.insert(document);
    }
}