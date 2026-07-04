package com.example.demo.mapper.mariadb;

import com.example.demo.bean.SysLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogMapper {

    void insert(SysLog sysLog);

    SysLog select(Long id);
}
