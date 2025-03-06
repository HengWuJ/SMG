package com.smg.knowledge.repository;


import com.smg.knowledge.node.Tool;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends Neo4jRepository<Tool, String> {
}
