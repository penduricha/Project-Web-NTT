package com.example.be_web_students.service.impl;

import com.example.be_web_students.model.jpa.Course;
import com.example.be_web_students.model.jpa.Student;
import com.example.be_web_students.service.I_StudentService;

import com.example.be_web_students.repository.jpa.CourseRepository;
import com.example.be_web_students.repository.jpa.StudentRepository;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements I_StudentService {

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public boolean addStudent(Student student, Long courseId) throws JpaSystemException {

        Student studentFound = studentRepository.findStudentByStudentId(student.getStudentId());

        Course courseFound = courseRepository.findCourseByCourseId(courseId);

        if(studentFound == null && courseFound != null)
        {
            Course courseToMap = new Course();
            courseToMap.setCourseId(courseFound.getCourseId());

            //map relationship
            courseToMap.getStudents().add(student);
            student.setCourse(courseToMap);

            studentRepository.save(student);
            return true;
        }
        return false;
    }
}
