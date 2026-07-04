package com.example.demo.mapper.mariadb;

import com.example.demo.bean.Test;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

    void insert(Test test);

    Test search(Long id);
}
