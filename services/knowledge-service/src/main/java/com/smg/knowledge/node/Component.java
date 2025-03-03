package com.smg.knowledge.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.Set;

@Node("Component")
@Data
public class Component implements Serializable {
    @Id
    private String componentId;
    private String name;
    private String specification;
    private String manufactureDate;
    private String warrantyExpirationDate;
    private Set<String> replacementHistory;// 替换历史记录
    private String partNumber;
    private int stockQuantity;
    private String criticalityLevel;// 如：关键、重要、一般

    @Relationship(type = "CAUSES", direction = Relationship.Direction.OUTGOING)
    private Set<Fault> faults;
}
