package com.example.demo.bean;

import com.example.demo.annotation.Enum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class Student implements Serializable {

    @NotBlank
    @Length(min = 3)
    private String sno;

    @NotBlank
    @Length(min = 1)
    private String sname;

    @NotBlank
    @Enum(value = {"M", "F"})
    private String ssex;
}
