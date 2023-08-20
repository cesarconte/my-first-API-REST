package com.example.apirest.services;

import com.example.apirest.models.Student;
import com.example.apirest.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    public Student updateStudent(int id, Student updatedStudent) {
        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent != null) {
            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setLastName(updatedStudent.getLastName());
            existingStudent.setSubject(updatedStudent.getSubject());
            existingStudent.setGrade(updatedStudent.getGrade());

            return studentRepository.save(existingStudent);
        }

        return null;
    }
}
