package com.rootlets.registration.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
public class CourseDto implements Serializable {
    private static final long serialVersionUID = -3770142805983192214L;
    @NotEmpty(message = "Please provide a courseName")
    private String courseName;
    private String courseDetails;
    @NotEmpty(message = "Please provide a course duration")
    private String courseDuration;
}
