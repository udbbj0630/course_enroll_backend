package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CourseService;
import com.mycompany.myapp.service.dto.CourseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api") // common path prefix
public class CourseController {

    private CourseService courseService;

    /*
     * 1. Requirements: 列出所有已有课程
     * 2. Http method: GET
     * 3. URL: /courses
     * 4. HTTP status code: 200
     * 5. Request body : None
     *      - PathVariable
     *      - QueryParameter
     *      - RequestBody
     *      - None
     * 6. Response body: List<CourseDto>
     * 7. (Request) Header: JWT Token
     * */

    @GetMapping("/courses")
    public List<CourseDto> getCourses() {
        return courseService.getCourses();
    }

    /*
     * 1. Requirements: 实现学生选课功能
     * 2. Http method: POST
     * 3. URL: /student/course/{courseName}
     * 4. HTTP status code: 201
     * 5. Request body : PathVariable
     *      - PathVariable
     *      - QueryParameter
     *      - RequestBody
     *      - None
     * 6. Response body: null
     * 7. (Request) Header: JWT Token
     * */
    @PostMapping("/student/course/{courseName}")
    public void selectCourse(@PathVariable String courseName) {
        String userName = getUsername();
        courseService.selectCourse(userName, courseName);
    }

    /*
     * 1. Requirements: 列出一个所有学生已选课程
     * 2. Http method: GET
     * 3. URL: /student/courses
     * 4. HTTP status code: 200
     * 5. Request body : None
     *      - PathVariable
     *      - QueryParameter
     *      - RequestBody
     *      - None
     * 6. Response body: List<CourseDto>
     * 7. (Request) Header: JWT Token
     * */
    @GetMapping("/student/courses")
    public List<CourseDto> getStudentCourses() {
        String username = getUsername();
        return courseService.getStudentCourses(username);
    }

    /*
     * 1. Requirements: 删除已选课程
     * 2. Http method: DELETE
     * 3. URL: /student/course/{courseName}
     * 4. HTTP status code: 204
     * 5. Request body : PathVariable
     *      - PathVariable
     *      - QueryParameter
     *      - RequestBody
     *      - None
     * 6. Response body: nothing
     * 7. (Request) Header: JWT Token
     * */
    @DeleteMapping("/student/course/{courseName}")
    public void deleteCourse(@PathVariable String courseName) {
        String userName = getUsername();
        courseService.deleteCourse(userName, courseName);
    }

    /*
    Extract username from JWT token
    */
    private String getUsername() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
