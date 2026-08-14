package com.example.be_web_students.security.jwt;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Chuyển đổi chuỗi secret thành SecretKey chuẩn cho JJWT 0.12.x
    private SecretKey getSigningKey() {
        // Khóa bí mật dùng để ký token (Độ dài tối thiểu phải từ 256 bits / 32 ký tự đối với thuật toán HS256)
        String JWT_SECRET = "messi_vo_dich_world_cup_ucl";
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    // Tạo JWT từ studentId
    public String generateToken(Long studentId) {
        Date now = new Date();
        long JWT_EXPIRATION = 86400000L;
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .subject(String.valueOf(studentId)) // Lưu studentId vào phần Subject của token
                .issuedAt(now)                      // Thời điểm phát hành
                .expiration(expiryDate)             // Thời gian hết hạn
                .signWith(getSigningKey())          // Ký token bằng thuật toán HMAC-SHA
                .compact();
    }
}
