package com.example.be_web_students.service.cached.impl;

import com.example.be_web_students.model.cached.StudentJwtCache;
import com.example.be_web_students.model.cached.StudentRefreshToken;
import com.example.be_web_students.repository.cached.StudentJwtCacheRepository;
import com.example.be_web_students.repository.cached.StudentRefreshTokenRepository;
import com.example.be_web_students.service.cached.I_StudentJwtCacheService;
import io.lettuce.core.RedisBusyException;
import org.springframework.stereotype.Service;

@Service
public class StudentJwtCacheServiceImpl implements I_StudentJwtCacheService {

    private final StudentJwtCacheRepository studentJwtCacheRepository;

    private final StudentRefreshTokenRepository studentRefreshTokenRepository;

    public StudentJwtCacheServiceImpl(StudentJwtCacheRepository studentJwtCacheRepository, StudentRefreshTokenRepository studentRefreshTokenRepository) {
        this.studentJwtCacheRepository = studentJwtCacheRepository;
        this.studentRefreshTokenRepository = studentRefreshTokenRepository;
    }

    @Override
    public boolean addStudentJwtCache(StudentJwtCache studentJwtCache) throws RedisBusyException {
        boolean studentJwtCacheFound = findStudentJwtCacheByJwtToken(studentJwtCache.getJwtToken());
        if(!studentJwtCacheFound) {
            studentJwtCacheRepository.save(studentJwtCache);
            return true;
        }
        return false;
    }

    @Override
    public boolean findStudentJwtCacheByJwtToken(String jwtToken) throws RedisBusyException {
        StudentJwtCache studentJwtCacheFound = studentJwtCacheRepository
                .findStudentJwtCacheByJwtToken(jwtToken);
        return studentJwtCacheFound != null;
    }

    @Override
    public boolean addStudentRefreshToken(StudentRefreshToken studentRefreshToken) throws RedisBusyException {
        boolean studentRefreshTokenFound = findRefreshTokenByJwtToken(studentRefreshToken.getJwtToken());
        if(!studentRefreshTokenFound) {
            studentRefreshTokenRepository.save(studentRefreshToken);
            return true;
        }
        return false;
    }

    @Override
    public boolean findRefreshTokenByJwtToken(String jwtToken) throws RedisBusyException {
        StudentRefreshToken studentRefreshToken = studentRefreshTokenRepository
                .findStudentRefreshTokenByJwtToken(jwtToken);
        return studentRefreshToken != null;
    }
}
