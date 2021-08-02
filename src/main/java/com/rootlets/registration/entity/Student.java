package com.rootlets.registration.entity;

import com.rootlets.registration.exception.StudentCourseIllegalStateException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
public class Student implements Serializable {
    private static final long serialVersionUID = 1013479834262222490L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID", unique = true, nullable = false, length = 20)
    private Long studentId;

    @Column(name = "STUDENT_NAME")
    @NotEmpty(message = "Please provide a studentName")
    private String studentName;

    @Column(name = "MOBILE_NUMBER")
    @NotEmpty(message = "Please provide a mobileNumber")
    private String mobileNumber;

    @Column(name = "ADDRESS")
    private String address;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "student_course", joinColumns = {
            @JoinColumn(name = "STUDENT_ID", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "COURSE_ID", nullable = false, updatable = false)})
    @Size(max = 5, message = "Upto 5 courses are allowed to enroll")
    private Set<Course> courses;


    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        if (courses !=null && courses.size() > 5) {
            throw new StudentCourseIllegalStateException("Student can register upto 5 courses only");
        }
        this.courses = courses;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
        result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
        result = prime * result + ((studentName == null) ? 0 : studentName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (mobileNumber == null) {
            if (other.mobileNumber != null)
                return false;
        } else if (!mobileNumber.equals(other.mobileNumber))
            return false;
        if (studentId == null) {
            if (other.studentId != null)
                return false;
        } else if (!studentId.equals(other.studentId))
            return false;
        if (studentName == null) {
            if (other.studentName != null)
                return false;
        } else if (!studentName.equals(other.studentName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Student [studentId=" + studentId + ", studentName=" + studentName + ", mobileNumber=" + mobileNumber
                + ", address=" + address + "]";
    }

}
