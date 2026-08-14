package com.example.be_web_students.repository.cached;



import com.example.be_web_students.model.cached.StudentJwtRefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentJwtRefreshTokenRepository extends CrudRepository<StudentJwtRefreshToken, Long>  {

    StudentJwtRefreshToken findStudentJwtRefreshTokenByJwtToken(String jwtToken);

}
