package com.smg.knowledge.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.Set;

@Node("Device")
@Data
public class Device implements Serializable {
    @Id
    private String deviceId;
    private String name;
    private String type;
    private String manufacturer;
    private String model;
    private String installationDate;
    private String serialNumber;
    private String location;
    private String lastMaintenanceDate;
    private int operatingHours;
    private String status;// 例如：运行中、停机、维修中

    @Relationship(type = "CONTAINS", direction = Relationship.Direction.OUTGOING)
    private Set<Component> components;
}
