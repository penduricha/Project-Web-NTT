package com.example.be_web_students.service.cached.impl;

import com.example.be_web_students.model.cached.StudentJwtCache;
import com.example.be_web_students.model.cached.StudentJwtRefreshToken;
import com.example.be_web_students.repository.cached.StudentJwtCacheRepository;
import com.example.be_web_students.repository.cached.StudentJwtRefreshTokenRepository;
import com.example.be_web_students.service.cached.I_StudentJwtRefreshTokenService;
import io.lettuce.core.RedisBusyException;
import org.springframework.stereotype.Service;

@Service
public class StudentJwtRefreshTokenServiceImpl implements I_StudentJwtRefreshTokenService {

    private final StudentJwtRefreshTokenRepository studentJwtRefreshTokenRepository;


    public StudentJwtRefreshTokenServiceImpl(StudentJwtRefreshTokenRepository studentJwtRefreshTokenRepository) {
        this.studentJwtRefreshTokenRepository = studentJwtRefreshTokenRepository;
    }

    @Override
    public boolean addStudentJwtRefreshToken(StudentJwtRefreshToken studentJwtRefreshToken) throws RedisBusyException {

        StudentJwtRefreshToken studentJwtRefreshTokenFound = studentJwtRefreshTokenRepository
                .findStudentJwtRefreshTokenByJwtToken(studentJwtRefreshToken.getJwtToken());

        if(studentJwtRefreshTokenFound == null) {
            studentJwtRefreshTokenRepository.save(studentJwtRefreshToken);
            return true;
        }
        return false;
    }
}
