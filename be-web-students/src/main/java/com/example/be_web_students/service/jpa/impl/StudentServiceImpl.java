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
import java.util.HashMap;
import java.util.Map;

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

    private final int strengthPassword = 12;

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
        //    {
        //        "success": false,
        //            "message": "Mã sinh viên hoặc mật khẩu không chính xác",
        //            "data": null
        //    }


        // 1. Tìm sinh viên theo ID
        Student studentFound = studentRepository.findStudentByStudentId(studentId);
        if (studentFound == null) {
            Map<String, Object> responseNotFound = new HashMap<>();
            responseNotFound.put("success", false);
            responseNotFound.put("message", "Student not found.");
            responseNotFound.put("data", null);
            return responseNotFound;
        }

        // 2. Kiểm tra mật khẩu chuẩn bảo mật (Ví dụ dùng Spring Security PasswordEncoder hoặc thư viện BCrypt)
        // Lưu ý: Không dùng hàm generateHash để so sánh trực tiếp vì hash sinh ra mỗi lần là khác nhau do salt.
        boolean isMatch = comparePasswordBcrypt(password, studentFound.getPassword(), strengthPassword);
        if (!isMatch) {
            Map<String, Object> responsePasswordNotMatched = new HashMap<>();
            responsePasswordNotMatched.put("success", false);
            responsePasswordNotMatched.put("message", "Password not matched.");
            responsePasswordNotMatched.put("data", null);
            return responsePasswordNotMatched;
        }

        // 3. Tạo JWT Token
        String jwtToken = jwtTokenProvider.generateToken(studentFound.getStudentId());

        if (studentJwtCacheService.findStudentJwtCacheByJwtToken(jwtToken)) {
            jwtToken = jwtTokenProvider.generateToken(studentFound.getStudentId());
        }

        LocalDateTime now = LocalDateTime.now();

        // 4. Lưu Cache
        StudentJwtCache studentJwtCache = new StudentJwtCache();
        studentJwtCache.setJwtToken(jwtToken);
        studentJwtCache.setStudentId(studentId);
        studentJwtCache.setDateLogin(now);

        // 5. Lưu Refresh Token và trả về kết quả
        StudentJwtRefreshToken studentJwtRefreshToken = new StudentJwtRefreshToken();
        studentJwtRefreshToken.setJwtToken(jwtToken);
        studentJwtRefreshToken.setStudentId(studentId);
        studentJwtRefreshToken.setDateLogin(now);

        //Login successfully
        //        {
        //            "success": true,
        //                "message": "Đăng nhập thành công",
        //                "data": {
        //            "studentId": 2500021772,
        //                    "jwtToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        //        }
        //        }
        Map<String, Object> responseSuccess = new HashMap<>();

        if(studentJwtCacheService.addStudentJwtCache(studentJwtCache)) {
            if(studentJwtRefreshTokenService.addStudentJwtRefreshToken(studentJwtRefreshToken)) {

                responseSuccess.put("success", true);
                responseSuccess.put("massage", "Login successfully.");

                Map<String, Object> responseSuccessData= new HashMap<>();
                responseSuccessData.put("studentId", studentJwtCache.getStudentId());
                responseSuccessData.put("jwtToken", studentJwtCache.getJwtToken());
                responseSuccess.put("data", responseSuccessData);
            }
        }
        return responseSuccess;
    }

    public String generateHash(String rawText, int strength) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(strength);
        return encoder.encode(rawText);
    }

    public boolean comparePasswordBcrypt(String rawText, String hashedPassword, int strength) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(strength);
        return encoder.matches(rawText, hashedPassword);
    }
}
