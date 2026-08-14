package com.example.be_web_students.service.jpa.impl;

import com.example.be_web_students.model.cached.StudentJwtCache;
import com.example.be_web_students.model.cached.StudentJwtRefreshToken;
import com.example.be_web_students.model.jpa.Course;
import com.example.be_web_students.model.jpa.Student;
import com.example.be_web_students.security.jwt.JwtTokenProvider;
import com.example.be_web_students.service.I_StudentService;

import com.example.be_web_students.repository.jpa.CourseRepository;
import com.example.be_web_students.repository.jpa.StudentRepository;
import com.example.be_web_students.service.cached.impl.StudentJwtCacheServiceImpl;
import com.example.be_web_students.service.cached.impl.StudentJwtRefreshTokenServiceImpl;
import io.lettuce.core.RedisBusyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//Gradle cho security
// Spring Boot Starter Security (Bao gồm BCryptPasswordEncoder)
//implementation("org.springframework.boot:spring-boot-starter-security")
//
//        // Thư viện hỗ trợ xử lý JWT (jjwt) tương thích Java 21 và Spring Boot 3
//        implementation("io.jsonwebtoken:jjwt-api:0.12.5")
//        runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
//        runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
//
//        // Test dependencies cho Security (nếu cần viết Unit Test)
//        testImplementation("org.springframework.security:spring-security-test")

@Service
public class StudentServiceImpl implements I_StudentService {

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    private final StudentJwtCacheServiceImpl studentJwtCacheService;

    private final StudentJwtRefreshTokenServiceImpl studentJwtRefreshTokenService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository, StudentJwtCacheServiceImpl studentJwtCacheService, StudentJwtRefreshTokenServiceImpl studentJwtRefreshTokenService) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentJwtCacheService = studentJwtCacheService;
        this.studentJwtRefreshTokenService = studentJwtRefreshTokenService;
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

    @Override
    public Object loginStudent(Long studentId, String password) throws RedisBusyException, JpaSystemException {

        String passwordEncoder = generateHash(password, 10);

        Student studentFound = studentRepository.findStudentByStudentId(studentId);

        if(studentFound != null) {
            boolean isMatch = studentFound.getPassword().trim().matches(passwordEncoder.trim());
            if(isMatch) {
                //save JWT
                String jwtToken = jwtTokenProvider.generateToken(studentFound.getStudentId());

                StudentJwtCache studentJwtCache = new StudentJwtCache();
                studentJwtCache.setJwtToken(jwtToken);
                studentJwtCache.setStudentId(studentId);
                studentJwtCache.setDateLogin(LocalDateTime.now());

                boolean studentJwtCacheServiceFound =
                        studentJwtCacheService.findStudentJwtCacheByJwtToken(jwtToken);

                while (studentJwtCacheServiceFound) {
                    jwtToken = jwtTokenProvider.generateToken(studentFound.getStudentId());
                    studentJwtCache.setJwtToken(jwtToken);
                    studentJwtCacheServiceFound =
                            studentJwtCacheService.findStudentJwtCacheByJwtToken(jwtToken);
                    if(!studentJwtCacheServiceFound) break;
                }

                StudentJwtRefreshToken studentJwtRefreshToken = new StudentJwtRefreshToken();
                studentJwtRefreshToken.setJwtToken(jwtToken);
                studentJwtRefreshToken.setStudentId(studentId);
                studentJwtRefreshToken.setDateLogin(LocalDateTime.now());

                if(studentJwtRefreshTokenService.addStudentJwtRefreshToken(studentJwtRefreshToken)) {

                    return true;
                }
                return false;

            } else {
                return false;
            }
        }
        return false;
    }

    public String generateHash(String rawText, int strength) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(strength);
        return encoder.encode(rawText);
    }
}
