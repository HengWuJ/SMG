package com.smg.knowledge.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.Set;
@Node("Procedure")
@Data
public class Procedure implements Serializable {

    @Id
    private String procedureId;
    private String name;
    private String category;// 类别（如：预防性维护、纠正性维护）
    private String frequency;// 执行频率（如：每周、每月）
    private String requiredSkills;// 所需技能
    private String safetyPrecautions;

    @Relationship(type = "FOLLOWED_BY", direction = Relationship.Direction.OUTGOING)
    private Set<Step> steps;
}
