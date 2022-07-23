package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.UserCourse;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.repository.UserCourseRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.dto.CourseDto;
import com.mycompany.myapp.service.mapper.CourseMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @AllArgsConstructor
    private CourseRepository courseRepository;

    private CourseMapper courseMapper;
    private UserRepository userRepository;
    private UserCourseRepository userCourseRepository;

    public List<CourseDto> getCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course -> courseMapper.convert(course)).collect(Collectors.toList());
    }

    /**
     * 1.User exit?
     * 2.Course exist?
     * 3.UserCourse combination not exit(de-dupe).
     * 4.Save UserCourse to DB.
     * @param username
     * @param courseName
     */

    public void selectCourse(String username, String courseName) {
        // 1 + 2
        UserCourse userCourse = getUserCourse(username, courseName);
        //3. UserCourse  = combination not exist(de-dupe)
        Optional<UserCourse> optionalUserCourse = userCourseRepository.findOneByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
        optionalUserCourse.ifPresent(existUserCourse -> {
            throw new IllegalArgumentException(String.format("UserCourse: {} already exists", existUserCourse.toString()));
        });
        //4. Save UserCourse to DB
        userCourseRepository.save(userCourse);
    }

    private UserCourse getUserCourse(String userName, String courseName) {
        //1. User exist
        Optional<User> optionalUser = userRepository.findOneByLogin(userName);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException(String.format("No such user.")));
        //2, Course exist?
        Optional<Course> optionalCourse = courseRepository.findByCourseName(courseName);
        Course course = optionalCourse.orElseThrow(() ->
            new IllegalArgumentException(String.format("CourseName: {}, No such course.", courseName))
        );

        //3. return UserCourse
        return new UserCourse(user, course);
    }

    public List<CourseDto> getStudentCourses(String userName) {
        //1. User exist
        Optional<User> optionalUser = userRepository.findOneByLogin(userName);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("No such user."));
        //2. find all UserCourse by user from UserCourse table -> List<UserCourse>
        List<UserCourse> userCourseList = userCourseRepository.findAllByUser(user);
        //3. Convert to List of CourseDTO: UserCourse -> getCourse -> CourseMapper.convert

        return userCourseList
            .stream()
            .map(userCourse -> userCourse.getCourse())
            .map(course -> courseMapper.convert(course))
            .collect(Collectors.toList());
    }

    /**
     * 1.User exit?
     * 2.Course exist?
     * 3.Drop UserCourse.
     * 4.Save UserCourse to DB.
     * @param userName
     * @param courseName
     */
    public void deleteCourse(String userName, String courseName) {
        // 1 + 2
        UserCourse userCourse = getUserCourse(userName, courseName);
        //Drop UserCourse
        userCourseRepository.deleteByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
        return;
    }
}
