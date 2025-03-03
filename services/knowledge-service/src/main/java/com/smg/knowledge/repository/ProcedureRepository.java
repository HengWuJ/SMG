package com.smg.knowledge.repository;

import com.smg.knowledge.node.Procedure;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureRepository extends Neo4jRepository<Procedure, String> {
    @Query("MATCH (p:Procedure {category: $category}) RETURN p")
    List<Procedure> findByCategory(String category);
}