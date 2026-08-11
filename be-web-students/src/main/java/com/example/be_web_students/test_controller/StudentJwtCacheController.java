package com.example.be_web_students.test_controller;

import com.example.be_web_students.model.cached.StudentJwtCache;
import com.example.be_web_students.repository.cached.StudentJwtCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/cached/student-token")
public class StudentJwtCacheController {

    private final StudentJwtCacheRepository studentJwtCacheRepository;

    @Autowired
    public StudentJwtCacheController(StudentJwtCacheRepository studentJwtCacheRepository) {
        this.studentJwtCacheRepository = studentJwtCacheRepository;
    }

    @PostMapping
    public ResponseEntity<?> postStudentJWTCached(@RequestBody Map<String, Object> studentJWTPost) throws RuntimeException {

        Number studentIdInt = (Number) studentJWTPost.get("studentId");
        Long studentId = studentIdInt != null ? studentIdInt.longValue() : null;
        String jwtToken = (String) studentJWTPost.get("jwtToken");
        LocalDateTime dateLogin = LocalDateTime.now();

        StudentJwtCache studentJwtCache = new StudentJwtCache();
        studentJwtCache.setStudentId(studentId);
        studentJwtCache.setJwtToken(jwtToken);
        studentJwtCache.setDateLogin(dateLogin);

        studentJwtCacheRepository.save(studentJwtCache);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    //curl -X DELETE "http://localhost:8080/api/cached?jwtToken=123"
    public ResponseEntity<?> deleteStudentJWTCached_By_JwtToken(@RequestParam String jwtToken) throws RuntimeException {

//        System.out.println("JWT Token: "+jwtToken);

        StudentJwtCache studentJwtCacheFound = studentJwtCacheRepository.findStudentJwtCacheByJwtToken(jwtToken);

//        System.out.println("Cached student: "+ studentJwtCacheFound);

        if(studentJwtCacheFound != null) {
            studentJwtCacheRepository.delete(studentJwtCacheFound);
            return ResponseEntity.ok("Đã xóa cache thành công cho JWT Token!");
        }
        return ResponseEntity.internalServerError().body("Lỗi khi xóa.");
    }
}
