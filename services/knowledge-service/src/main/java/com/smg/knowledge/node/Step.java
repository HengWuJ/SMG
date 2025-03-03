package com.smg.knowledge.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.Set;

@Node("Step")
@Data
public class Step implements Serializable {
    @Id
    private String stepId;
    private String description;
    private int order;
    private int estimatedTime;
    private String toolsRequired;
    @Relationship(type = "PRECEDES", direction = Relationship.Direction.OUTGOING)
    private Set<Step> predecessorSteps;
    @Relationship(type = "FOLLOWS", direction = Relationship.Direction.OUTGOING)
    private Set<Step> successorSteps;
//    @Relationship(type = "PART_OF", direction = Relationship.Direction.INCOMING)
//    private Procedure procedure;
}
