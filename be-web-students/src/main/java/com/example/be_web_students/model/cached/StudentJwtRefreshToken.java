package com.example.be_web_students.model.cached;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
//Time to live records 86400s
@RedisHash(value = "student_jwt_refresh_tokens", timeToLive = 604800)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StudentJwtRefreshToken implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentJwtRefreshTokenId;

    // Khóa chính (Key trên Redis sẽ có dạng: student_tokens:studentId)
    private Long studentId;

    @Column(unique = true)
    @Indexed
    private String jwtToken;

    private LocalDateTime dateLogin;
}
