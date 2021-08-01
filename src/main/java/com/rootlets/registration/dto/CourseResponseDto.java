package com.rootlets.registration.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CourseResponseDto implements Serializable {
    private static final long serialVersionUID = -3770142805983192214L;
    private String courseId;
    private String courseName;
    private String courseDetails;
    private String courseDuration;
}
