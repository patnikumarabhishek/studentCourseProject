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
public class Course implements Serializable {

    private static final long serialVersionUID = -3770142805983192214L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID", unique = true, nullable = false, length = 20)
    private Long courseId;

    @Column(name = "COURSE_NAME")
    @NotEmpty(message = "Please provide a courseName")
    private String courseName;

    @Column(name = "COURSE_DETAILS")
    private String courseDetails;

    @Column(name = "COURSE_DURATION")
    private String courseDuration;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "courses", cascade = CascadeType.ALL)
    @Size(max=5, message = "Upto 50 students are allowed to enroll to course")
    private Set<Student> students;

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        if (students.size() > 50) {
            throw new StudentCourseIllegalStateException("Upto 50 students can enroll to this course " + this.courseName);
        }
        this.students = students;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((courseDetails == null) ? 0 : courseDetails.hashCode());
        result = prime * result + ((courseDuration == null) ? 0 : courseDuration.hashCode());
        result = prime * result + ((courseId == null) ? 0 : courseId.hashCode());
        result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
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
        Course other = (Course) obj;
        if (courseDetails == null) {
            if (other.courseDetails != null)
                return false;
        } else if (!courseDetails.equals(other.courseDetails))
            return false;
        if (courseDuration == null) {
            if (other.courseDuration != null)
                return false;
        } else if (!courseDuration.equals(other.courseDuration))
            return false;
        if (courseId == null) {
            if (other.courseId != null)
                return false;
        } else if (!courseId.equals(other.courseId))
            return false;
        if (courseName == null) {
            if (other.courseName != null)
                return false;
        } else if (!courseName.equals(other.courseName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Course [courseId=" + courseId + ", courseName=" + courseName + ", courseDetails=" + courseDetails
                + ", courseDuration=" + courseDuration + "]";
    }

}
