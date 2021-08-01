package com.rootlets.registration.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class StudentDto implements Serializable {
    private static final long serialVersionUID = 1013479834262222490L;
    @NotEmpty(message = "Please provide student name")
    private String studentName;
    @Pattern(regexp = "[0-9]{10}", message = "Phone number must be 10 digit numbers")
    private String mobileNumber;
    private String address;
    private List<Long> courseId;
}
