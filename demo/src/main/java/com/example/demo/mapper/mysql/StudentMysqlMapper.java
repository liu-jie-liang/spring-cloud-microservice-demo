package com.example.demo.mapper.mysql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.bean.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMysqlMapper {

    List<Student> getAllStudents();

    IPage<Student> getStudentsByPage(IPage<Student> page);
}
