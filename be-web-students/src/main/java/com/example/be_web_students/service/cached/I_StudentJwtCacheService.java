package com.example.be_web_students.service.cached;

import com.example.be_web_students.model.cached.StudentJwtCache;
import com.example.be_web_students.model.cached.StudentRefreshToken;
import io.lettuce.core.RedisBusyException;

public interface I_StudentJwtCacheService {

    public boolean addStudentJwtCache(StudentJwtCache studentJwtCache)  throws RedisBusyException;

    public boolean findStudentJwtCacheByJwtToken(String jwtToken) throws RedisBusyException;

    public boolean addStudentRefreshToken(StudentRefreshToken studentRefreshToken)  throws RedisBusyException;

    public boolean findRefreshTokenByJwtToken(String jwtToken) throws RedisBusyException;

}
