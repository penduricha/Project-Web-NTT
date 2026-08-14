package com.example.be_web_students.service.cached.impl;

import com.example.be_web_students.model.cached.StudentJwtCache;
import com.example.be_web_students.repository.cached.StudentJwtCacheRepository;
import com.example.be_web_students.service.cached.I_StudentJwtCacheService;
import io.lettuce.core.RedisBusyException;
import org.springframework.stereotype.Service;

@Service
public class StudentJwtCacheServiceImpl implements I_StudentJwtCacheService {

    private final StudentJwtCacheRepository studentJwtCacheRepository;

    public StudentJwtCacheServiceImpl(StudentJwtCacheRepository studentJwtCacheRepository) {
        this.studentJwtCacheRepository = studentJwtCacheRepository;
    }

    @Override
    public boolean addStudentJwtCache(StudentJwtCache studentJwtCache) throws RedisBusyException {
        StudentJwtCache studentJwtCacheFound = studentJwtCacheRepository
                .findStudentJwtCacheByJwtToken(studentJwtCache.getJwtToken());
        if(studentJwtCacheFound == null) {
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
}
