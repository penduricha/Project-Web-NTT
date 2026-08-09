package com.example.be_web_students.repository.cached;

import com.example.be_web_students.model.cached.StudentJwtCache;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentJwtCacheRepository extends CrudRepository<StudentJwtCache, Long> {

}
