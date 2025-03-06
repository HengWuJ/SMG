package com.smg.knowledge.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.io.Serializable;
import java.util.Set;

@Node("Tool")
@Data
public class Tool implements Serializable {
    @Id
    private String toolId;
    private String name;
    private String type;
    private String manufacturer;
    private String model;
    private double cost;
    private int availability; // 可用数量
    private String location; // 存放位置
}