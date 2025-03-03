package com.smg.knowledge.repository;

import com.smg.knowledge.node.Component;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends Neo4jRepository<Component, String> {

    @Query("MATCH (c:Component {criticalityLevel: $criticalityLevel}) RETURN c")
    List<Component> findByCriticalityLevel(String criticalityLevel);
}