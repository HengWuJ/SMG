package com.smg.knowledge.node;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.Set;

@Node("Fault")
@Data
public class Fault implements Serializable {
    @Id
    private String faultId;
    private String description;
    private String date;
    private String severity;// 轻微、中等、严重
    private String resolutionStatus;// 未解决、已解决
    private String affectedSystems;// 如：电气系统、机械系统
    private String rootCause;// 根本原因

//    @JsonBackReference
    @Relationship(type = "SOLVED_BY", direction = Relationship.Direction.INCOMING)
    private Set<Solution> solutions;
}
