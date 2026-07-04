package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.bean.Student;
import com.example.demo.bean.SysLog;
import com.example.demo.mapper.mariadb.StudentMariadbMapper;
import com.example.demo.mapper.mariadb.SysLogMapper;
import com.example.demo.mapper.mysql.StudentMysqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMysqlMapper studentMysqlMapper;
    @Autowired
    private StudentMariadbMapper studentMariadbMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    public List<Student> queryByMysql() {
        return studentMysqlMapper.getAllStudents();
    }

    public List<Student> queryByMariadb() {
        return studentMariadbMapper.getAllStudents();
    }

    public IPage<Student> queryByMariadbPage(IPage<Student> page) {
        return studentMariadbMapper.getStudentsByPage(page);
    }

    public IPage<Student> queryByMysqlPage(IPage<Student> page) {
        return studentMysqlMapper.getStudentsByPage(page);
    }

    public SysLog getSysLog(Long id) {
        return sysLogMapper.select(id);
    }

    public Student queryStudentBySno(String sno) {
        return studentMariadbMapper.queryStudentBySno(sno);
    }

    public void update(Student student) {
        studentMariadbMapper.update(student);
    }

    public void deleteStudentBySno(String sno) {
        studentMariadbMapper.deleteStudentBySno(sno);
    }
}
