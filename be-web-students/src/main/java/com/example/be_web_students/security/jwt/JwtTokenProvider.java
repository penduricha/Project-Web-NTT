package com.example.be_web_students.security.jwt;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Khóa bí mật phải có độ dài tối thiểu 32 ký tự (256 bits) đối với HS256
    private static final String JWT_SECRET = "messi_vo_dich_world_cup_ucl_2022_and_champions_league";

    // Thời gian hết hạn token (Ví dụ: 1 ngày = 86400000 ms)
    private static final long JWT_EXPIRATION = 86400000L;

    // Chuyển đổi chuỗi secret thành SecretKey chuẩn cho JJWT 0.12.x
    private SecretKey getSigningKey() {
        byte[] keyBytes = JWT_SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Tạo JWT từ studentId
    public String generateToken(Long studentId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .subject(String.valueOf(studentId)) // Lưu studentId vào phần Subject của token
                .issuedAt(now)                      // Thời điểm phát hành
                .expiration(expiryDate)             // Thời gian hết hạn
                .signWith(getSigningKey())          // Ký token bằng thuật toán HMAC-SHA
                .compact();
    }
}