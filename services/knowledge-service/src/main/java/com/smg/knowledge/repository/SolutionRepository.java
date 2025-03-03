package com.smg.knowledge.repository;

import com.smg.knowledge.node.Solution;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends Neo4jRepository<Solution, String> {
}