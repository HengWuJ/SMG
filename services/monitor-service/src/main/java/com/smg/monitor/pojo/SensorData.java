package com.smg.monitor.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Document(indexName = "sensordata")
public class SensorData {

    @Id
    private String id;

    @Field(type = FieldType.Double)
    private Double temperature;

    @Field(type = FieldType.Double)
    private Double humidity;

    @Field(type = FieldType.Double)
    private Double pressure;

    @Field(type = FieldType.Double)
    private Double lightIntensity;

    @Field(type = FieldType.Text)
    private String sensorId;

    @Field(type = FieldType.Text)
    private String location;

    @Field(type = FieldType.Date, format = DateFormat.date_time_no_millis) // Specify date-time format without milliseconds.
    private ZonedDateTime timestamp;
}
