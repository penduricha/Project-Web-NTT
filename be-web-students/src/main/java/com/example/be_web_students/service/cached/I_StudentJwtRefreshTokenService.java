package com.example.be_web_students.service.cached;

import com.example.be_web_students.model.cached.*;
import io.lettuce.core.RedisBusyException;

public interface I_StudentJwtRefreshTokenService {

    public boolean addStudentJwtRefreshToken(StudentJwtRefreshToken studentJwtRefreshToken) throws RedisBusyException;

}
