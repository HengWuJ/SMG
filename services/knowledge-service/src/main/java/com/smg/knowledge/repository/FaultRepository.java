package com.smg.knowledge.repository;

import com.smg.knowledge.node.Component;
import com.smg.knowledge.node.Fault;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaultRepository extends Neo4jRepository<Fault, String> {
    @Query("MATCH (f:Fault {severity: $severity}) RETURN f")
    List<Fault> findBySeverity(String severity);

    @Query("MATCH (f:Fault) WHERE f.description CONTAINS $description RETURN f")
    List<Fault> findByDescription(String description);

    @Query("""
        MATCH (d:Device)-[:CONTAINS]->(c:Component)-[:CAUSES]->(f:Fault)
        WHERE ($deviceDescription IS NULL OR d.name CONTAINS $deviceDescription)
        AND ($componentDescription IS NULL OR c.name CONTAINS $componentDescription)
        AND ($faultDescription IS NULL OR f.description CONTAINS $faultDescription)
        RETURN f
        """)
    List<Fault> searchFaults(String deviceDescription, String componentDescription, String faultDescription);

    @Query("""
        MATCH (c:Component)-[:CAUSES]->(f:Fault {faultId: $faultId})
        RETURN c.componentId AS componentId
    """)
    List<String> findComponentsByFaultId(@Param("faultId") String faultId);

}