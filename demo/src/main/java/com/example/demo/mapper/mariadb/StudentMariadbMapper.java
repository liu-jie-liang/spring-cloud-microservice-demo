package com.example.demo.mapper.mariadb;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.annotation.DoubleCache;
import com.example.demo.bean.Student;
import com.example.demo.constant.CacheType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@CacheConfig(cacheNames = "student")
@Mapper
public interface StudentMariadbMapper {

    List<Student> getAllStudents();

//    @CacheEvict(key = "#p0.sno", allEntries = true)
    @DoubleCache(prefix = "student", key = "#student.sno", type = CacheType.DELETE)
    void update(Student student);

//    @CacheEvict(key = "#p0", allEntries = true)
    @DoubleCache(prefix = "student", key = "#sno", type = CacheType.DELETE)
    void deleteStudentBySno(String sno);

//    @Cacheable(key = "#p0")
    @DoubleCache(prefix = "student", key = "#sno", type = CacheType.FULL)
    Student queryStudentBySno(String sno);

    IPage<Student> getStudentsByPage(IPage<Student> page);
}