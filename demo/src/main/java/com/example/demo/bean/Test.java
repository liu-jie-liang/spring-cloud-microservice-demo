package com.example.demo.bean;

import com.example.demo.config.JacksonConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Test implements Serializable {

    private Long id;

    @JsonProperty("timestamp")
    @JsonDeserialize(using = JacksonConfig.TimestampDeserializer.class)
    @JsonSerialize(using = JacksonConfig.DateStrFromTimestampSerializer.class)
    private Long timestamp;

    @JsonProperty("date")
    @JsonDeserialize(using = JacksonConfig.DateDeserializer.class)
    @JsonSerialize(using = JacksonConfig.TimestampStrFromDateSerializer.class)
    private Date date;
}
