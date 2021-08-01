package com.rootlets.registration.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReportResponse {
    private List<StudentDto> studentDtos;
    private List<CourseResponseDto> courseDtos;
}
