package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LocalCache implements Serializable {

    private String key;

    private Object value;
}
