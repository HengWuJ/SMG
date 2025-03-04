package com.smg.knowledge.repository;

import com.smg.knowledge.node.Component;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends Neo4jRepository<Component, String> {

    @Query("MATCH (c:Component {criticalityLevel: $criticalityLevel}) RETURN c")
    List<Component> findByCriticalityLevel(String criticalityLevel);

    @Query("MATCH (c1:Component {componentId: $sourceId}), (c2:Component {componentId: $targetId}) " +
            "CREATE (c1)-[:PRECEDES]->(c2)")
    void createPrecedesRelationship(String sourceId, String targetId);


    @Query("MATCH (c1:Component {componentId: $sourceId})-[r:PRECEDES]->(c2:Component {componentId: $targetId}) " +
            "DELETE r")
    void deletePrecedesRelationship(String sourceId, String targetId);

    @Query("MATCH (c:Component {componentId: $componentId})-[:PRECEDES]->(next:Component) RETURN next")
    Set<Component> findDirectPrecedes(String componentId);


    @Query("MATCH (c:Component {componentId: $componentId})<-[:PRECEDES]-(prev:Component) RETURN prev")
    Set<Component> findDirectDependsOn(String componentId);

//    @Query("MATCH (c:Component) " +
//            "CALL apoc.algo.topologicalSort('Component', 'PRECEDES') YIELD value AS componentId " +
//            "MATCH (c:Component {componentId: componentId}) " +
//            "RETURN c")
//    List<Component> getTopologicalOrder();

//    @Query("CALL apoc.cypher.run(\"MATCH (c:Component) RETURN c, size((:Component)-[:PRECEDES]->(c)) AS inDegree\", {}) YIELD value WITH collect(value) AS nodesWithInDegree CALL apoc.cypher.doIt(\"UNWIND $nodes AS node WITH node.node AS c, node.inDegree AS inDegree WHERE inDegree = 0 MATCH (c)-[r:PRECEDES]->(next:Component) DELETE r WITH c, collect(next) AS nextNodes UNWIND nextNodes AS next MATCH (next) SET next.inDegree = next.inDegree - 1 RETURN c, size(nextNodes) AS removedEdges\", {nodes: nodesWithInDegree}, {batchSize: 100}) YIELD value WITH collect(value) AS processedNodes CALL apoc.coll.sortMaps(processedNodes, ['removedEdges']) YIELD value UNWIND value AS orderedNode RETURN orderedNode.node.componentId AS componentId, orderedNode.node.name AS componentName ORDER BY orderedNode.node.componentId")
//    List<Map<String, Object>> getTopologicalSort();
//
//    @Query("CALL apoc.cypher.run(\"MATCH (c:Component) RETURN c, size([(c)<-[:PRECEDES]-(:Component)]) AS inDegree\", {}) YIELD value WITH collect(value) AS nodesWithInDegree CALL apoc.cypher.doIt(\"UNWIND $nodes AS node WITH node.node AS c, node.inDegree AS inDegree WHERE inDegree = 0 MATCH (c)-[r:PRECEDES]->(next:Component) DELETE r WITH c, collect(next) AS nextNodes UNWIND nextNodes AS next MATCH (next) SET next.inDegree = next.inDegree - 1 RETURN c, size(nextNodes) AS removedEdges\", {nodes: nodesWithInDegree}, {batchSize: 100}) YIELD value WITH collect(value) AS processedNodes CALL apoc.coll.sortMaps(processedNodes, ['removedEdges']) YIELD value UNWIND value AS orderedNode RETURN orderedNode.node.componentId AS componentId, orderedNode.node.name AS componentName ORDER BY orderedNode.node.componentId")
//    List<Component> getTopologicalSort();
    @Query("MATCH (c1:Component {componentId: $sourceId}), (c2:Component {componentId: $targetId}) " +
            "CREATE (c1)-[:ADJACENT_TO]->(c2)")
    void createAdjacentToRelationship(String sourceId, String targetId);

    @Query("MATCH (c1:Component {componentId: $sourceId})-[r:ADJACENT_TO]->(c2:Component {componentId: $targetId}) " +
            "DELETE r")
    void deleteAdjacentToRelationship(String sourceId, String targetId);


    @Query("MATCH (c:Component {componentId: $componentId})-[:ADJACENT_TO]->(next:Component) RETURN next")
    Set<Component> findDirectAdjacentTo(String componentId);

    @Query("MATCH (c:Component {componentId: $componentId})<-[:ADJACENT_TO]-(prev:Component) RETURN prev")
    Set<Component> findDirectAdjacentFrom(String componentId);

    /**
     * 获取逆拓扑排序顺序（组装顺序）
     */
    @Query("MATCH (c:Component) " +
            "CALL apoc.algo.topologicalSort('Component', 'PRECEDES') YIELD value AS componentId " +
            "MATCH (c:Component {componentId: componentId}) " +
            "RETURN c ORDER BY id(c) DESC")
    List<Component> getReverseTopologicalOrder();
}