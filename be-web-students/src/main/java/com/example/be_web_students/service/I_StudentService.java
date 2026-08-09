package com.example.be_web_students.service;

import com.example.be_web_students.model.jpa.Student;
import org.springframework.orm.jpa.JpaSystemException;

public interface I_StudentService {

    public boolean addStudent(Student student, Long courseId) throws JpaSystemException;

}
