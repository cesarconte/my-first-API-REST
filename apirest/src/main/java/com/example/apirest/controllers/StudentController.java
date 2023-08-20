package com.example.apirest.controllers;

import com.example.apirest.models.Student;
import com.example.apirest.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.apirest.models.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping
    public ApiResponse saveStudent(@RequestBody Student student) {
        try {
            validateStudent(student);

            studentService.saveStudent(student);
            return new ApiResponse(true, student, "Student saved successfully");
        } catch (Exception e) {
            return new ApiResponse(false, null, "An error occurred while saving the student");
        }
    }

    @GetMapping
    public ApiResponse getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            return new ApiResponse(true, students, "Students retrieved successfully");
        } catch (Exception e) {
            return new ApiResponse(false, null, "An error occurred while fetching students");
        }
    }

    @GetMapping("/{id}")
    public ApiResponse getStudentById(@PathVariable int id) {
        try {
            if (id <= 0) {
                return new ApiResponse(false, null, "Invalid student ID");
            }

            Student student = studentService.getStudentById(id);
            if (student == null) {
                return new ApiResponse(false, null, "Student not found");
            }

            return new ApiResponse(true, student, "Student retrieved successfully");
        } catch (Exception e) {
            return new ApiResponse(false, null, "An error occurred while fetching the student");
        }
    }

    @PutMapping("/{id}")
    public ApiResponse updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        try {
            if (id <= 0) {
                return new ApiResponse(false, null, "Invalid student ID");
            }

            Student existingStudent = studentService.getStudentById(id);
            if (existingStudent == null) {
                return new ApiResponse(false, null, "Student not found");
            }

            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setLastName(updatedStudent.getLastName());
            existingStudent.setSubject(updatedStudent.getSubject());
            existingStudent.setGrade(updatedStudent.getGrade());

            studentService.saveStudent(existingStudent);

            return new ApiResponse(true, existingStudent, "Student updated successfully");
        } catch (Exception e) {
            return new ApiResponse(false, null, "An error occurred while updating the student");
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteStudent(@PathVariable int id) {
        try {
            if (id <= 0) {
                return new ApiResponse(false, null, "Invalid student ID");
            }

            studentService.deleteStudent(id);
            return new ApiResponse(true, null, "Student deleted successfully");
        } catch (Exception e) {
            return new ApiResponse(false, null, "An error occurred while deleting the student");
        }
    }

    private void validateStudent(Student student) throws Exception {
        if (student.getFirstName() == null || student.getLastName() == null ||
                student.getSubject() == null || student.getGrade() == null || student.getGrade().isEmpty()) {
            throw new Exception("All fields are required");
        }

        try {
            double grade = Double.parseDouble(student.getGrade());
            if (grade < 0 || grade > 10) {
                throw new Exception("Grade must be between 0 and 10");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Grade must be a valid number");
        }
    }

}
