package com.mycompany.myapp.service.mapper;

<<<<<<< ours
public class CourseMapper {}
=======

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.service.dto.CourseDto;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public CourseDto convert(Course course) {
        return CourseDto.builder()
            .courseName(course.getCourseName())
            .courseContent(course.getCourseContent())
            .courseLocation(course.getCourseLocation())
            .teacherId(course.getTeacherId())
            .build();
    }
}
>>>>>>> theirs
