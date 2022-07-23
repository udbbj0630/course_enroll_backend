package com.mycompany.myapp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private String courseName;
    private String courseContent;
    private String courseLocation;
    private Long teacherId;
}
