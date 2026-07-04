package com.example.demo.bean;

import com.example.demo.config.JacksonConfig;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysLog implements Serializable {

    private Long id;
    private String username;
    private String operation;
    private Integer time;
    private String method;
    private String params;
    private String ip;
    @JsonSerialize(using = JacksonConfig.DateStrFromDateSerializer.class)
    private Date createTime;
}
