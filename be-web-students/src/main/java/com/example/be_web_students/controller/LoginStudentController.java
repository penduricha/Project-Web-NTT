package com.example.be_web_students.controller;

import com.example.be_web_students.dto.LoginRequestStudentDTO;
import com.example.be_web_students.service.jpa.impl.StudentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class LoginStudentController {

    private final StudentServiceImpl studentService;

    public LoginStudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    //    {
    //  "studentId": 2500021772,
    //  "password": "1234"
    //}

    //Login successfully
    //        {
    //            "success": true,
    //                "message": "Đăng nhập thành công",
    //                "data": {
    //            "studentId": 2500021772,
    //                    "jwtToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    //        }
    //        }

    //Login fail
    //    {
    //        "success": false,
    //            "message": "Mã sinh viên hoặc mật khẩu không chính xác",
    //            "data": null
    //    }
    @PostMapping("/auth/login")
    public ResponseEntity<?> loginStudent(@RequestBody Map<String, Object> studentRequestLogin) {
        Number studentIdInt = (Number) studentRequestLogin.get("studentId");
        Long studentId = studentIdInt != null ? studentIdInt.longValue() : null;
        String password = (String) studentRequestLogin.get("password");

        LoginRequestStudentDTO loginRequestStudentDTO = new LoginRequestStudentDTO();
        loginRequestStudentDTO.setStudentId(studentId);
        loginRequestStudentDTO.setPassword(password);

        // Gọi service trả về kết quả (ví dụ trả về Map, Token, hoặc DTO)
        Object result = studentService.loginStudent(
                loginRequestStudentDTO.getStudentId(),
                loginRequestStudentDTO.getPassword()
        );

        return ResponseEntity.ok(result);
    }
}
