package com.example.be_web_students.service.cached;

import com.example.be_web_students.model.cached.StudentJwtCache;
import io.lettuce.core.RedisBusyException;

public interface I_StudentJwtCacheService {

    public boolean addStudentJwtCache(StudentJwtCache studentJwtCache)  throws RedisBusyException;

    public boolean findStudentJwtCacheByJwtToken(String jwtToken) throws RedisBusyException;

}
