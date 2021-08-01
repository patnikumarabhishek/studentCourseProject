package com.rootlets.registration.utils;

import com.rootlets.registration.dto.CourseDto;
import com.rootlets.registration.dto.CourseResponseDto;
import com.rootlets.registration.dto.StudentDto;
import com.rootlets.registration.entity.Course;
import com.rootlets.registration.entity.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PojoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CourseDto convertCourseToDto(Course course) {
        CourseDto courseDto = modelMapper.map(course, CourseDto.class);
        return courseDto;
    }

    public CourseResponseDto convertCourseResponseToDto(Course course) {
        CourseResponseDto courseDto = modelMapper.map(course, CourseResponseDto.class);
        return courseDto;
    }

    public List<CourseDto> convertCourseToDto(List<Course> courses) {
        return courses.stream().map(course -> convertCourseToDto(course)).collect(Collectors.toList());
    }

    public List<CourseResponseDto> convertCourseResponseToDto(List<Course> courses) {
        return courses.stream().map(course -> convertCourseResponseToDto(course)).collect(Collectors.toList());
    }

    public Course convertCourseToEntity(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }

    public Student convertStudentToDto(StudentDto dto) {
        Student student = new Student();
        student.setStudentName(dto.getStudentName());
        student.setAddress(dto.getMobileNumber());
        student.setMobileNumber(dto.getMobileNumber());
        return student;
    }

    private void skipObject(ModelMapper modelMapper) {
        modelMapper.createTypeMap(StudentDto.class, Student.class)
                .addMappings(mapper -> mapper.skip(Student::setCourses));
    }


    public StudentDto convertStudentToDto(Student student) {
        StudentDto mapDto = modelMapper.map(student, StudentDto.class);
        if (student.getCourses() != null) {
            mapDto.setCourseId(student.getCourses().stream().map(course -> course.getCourseId()).collect(Collectors.toList()));
        }
        return mapDto;
    }

    public List<StudentDto> studentListToDto(Collection<Student> students) {
        return students.stream().map(student -> convertStudentToDto(student)).collect(Collectors.toList());
    }
}
