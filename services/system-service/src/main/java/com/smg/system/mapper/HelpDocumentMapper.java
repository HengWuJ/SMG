package com.smg.system.mapper;


import com.smg.system.pojo.HelpDocument;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HelpDocumentMapper {
    List<HelpDocument> findAll();
    void insert(HelpDocument helpDocument);
}
