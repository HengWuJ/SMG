package com.smg.knowledge.repository;

import com.smg.knowledge.node.Step;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends Neo4jRepository<Step, String> {
    @Query("MATCH (s:Step) WHERE s.estimatedTime > $estimatedTime RETURN s")
    List<Step> findByEstimatedTimeGreaterThan(int estimatedTime);
}