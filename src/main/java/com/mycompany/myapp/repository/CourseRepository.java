package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Course;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    //根据StringDataJPA的命名规则来声明函数，Hibernate 就会根据函数的名字来生成相应的sql从而实现这个函数
    Optional<Course> findByCourseName(String courseName);
}
