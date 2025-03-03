package com.smg.knowledge.repository;

import com.smg.knowledge.node.Device;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends Neo4jRepository<Device, String> {
    @Query("MATCH (d:Device {manufacturer: $manufacturer}) RETURN d")
    List<Device> findByManufacturer(String manufacturer);
}