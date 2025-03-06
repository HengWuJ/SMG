package com.smg.knowledge.node;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.Set;
@Node("Solution")
@Data
public class Solution implements Serializable {
    @Id
    private String solutionId;
    private String description;
    private String date;
    private double cost;
    private int duration;
    private String technician;
    private int effectivenessRating;// 效果评分
    private String materialsUsed;

    @Relationship(type = "USES_TOOL", direction = Relationship.Direction.OUTGOING)
    private Set<Tool> tools;
//    @JsonManagedReference
//    @Relationship(type = "SOLVES", direction = Relationship.Direction.OUTGOING)
//    private Fault fault;
}
