package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.annotation.Log;
import com.example.demo.bean.GenericResponse;
import com.example.demo.bean.Student;
import com.example.demo.bean.SysLog;
import com.example.demo.bean.Test;
import com.example.demo.code.ResponseCode;
import com.example.demo.exception.InternalErrorException;
import com.example.demo.mapper.mariadb.TestMapper;
import com.example.demo.service.StudentService;
import com.example.demo.utils.RequestContextUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
public class TestController {

    @GetMapping("testLog4j2")
    public String testLog4j2(String name, String site) {
        log.info("site: {}", site);
        return RequestContextUtils.getParam(name);
    }

    @Autowired
    private TestMapper testMapper;

    @PostMapping("insertTest")
    public void insertTest(@RequestBody Test test, String site) {
        testMapper.insert(test);
    }

    @GetMapping("searchTest")
    public Test searchTest(Long id, String site) {
        return testMapper.search(id);
    }

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/queryByMysql", method = RequestMethod.GET)
    public List<Student> queryByMysql() {
        return studentService.queryByMysql();
    }

    @RequestMapping(value = "/queryByMariadb", method = RequestMethod.GET)
    public List<Student> queryByMariadb() {
        return studentService.queryByMariadb();
    }

    @RequestMapping(value = "/queryByMariadbPage", method = RequestMethod.GET)
    public IPage<Student> queryByMariadbPage(Integer current, Integer size) {
        IPage<Student> page = new Page<>(current, size);
        return studentService.queryByMariadbPage(page);
    }

    @RequestMapping(value = "/queryByMysqlPage", method = RequestMethod.GET)
    public IPage<Student> queryByMysqlPage(Integer current, Integer size) {
        IPage<Student> page = new Page<>(current, size);
        return studentService.queryByMysqlPage(page);
    }

    @Log("getSysLog")
    @GetMapping("getSysLog")
    public SysLog getSysLog(Long id) {
        return studentService.getSysLog(id);
    }

    @GetMapping("queryStudentBySno")
    public Student queryStudentBySno(String sno) {
        return studentService.queryStudentBySno(sno);
    }

    @GetMapping("queryStudent")
    public GenericResponse<Student> queryStudent(String sno) {
        Student student = studentService.queryStudentBySno(sno);
        if (ObjectUtils.isEmpty(student)) {
            throw new InternalErrorException("student not exists, sno: " + sno);
        }
        return new GenericResponse<Student>(ResponseCode.SUCCESS, student);
    }

    @PostMapping("updateStudent")
    public void updateStudent(@RequestBody @Valid Student student) {
        studentService.update(student);
    }

    @DeleteMapping("deleteStudent")
    public void deleteStudent(String sno) {
        studentService.deleteStudentBySno(sno);
    }
}
