package com.infosys.student.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosys.student.entity.Student;
import com.infosys.student.exception.NoSuchStudentExption;
import com.infosys.student.exception.StudentAlreadyPresentException;
import com.infosys.student.repository.StudentRepository;

@Service
@Transactional
public class StudentService {

	@Autowired
	StudentRepository studentRepository;

	public Student fetchStudent(String id) throws NoSuchStudentExption, Exception {
		Optional<Student> student = studentRepository.findById(id);
		if (!student.isPresent())
			throw new NoSuchStudentExption("No such student with id: " + id);
		return student.get();
	}

	public Student updateStudent(String id, String name, String classname) throws NoSuchStudentExption, Exception {
		if (!studentRepository.existsById(id)) {
			String noStudent = "Student with id " + id + " not found.";
			throw new NoSuchStudentExption(noStudent);
		}
		Student entity = new Student(id, name, classname);
		return studentRepository.save(entity);
	}

	public Student addStudent(Student student) throws StudentAlreadyPresentException, Exception {
		boolean b = studentRepository.existsById(student.getId());
		if (b)
			throw new StudentAlreadyPresentException("Student Already PresentException");
		return studentRepository.save(student);

	}

	public void deleteStudent(String id) throws NoSuchStudentExption, Exception {
		if (!studentRepository.existsById(id)) {
			String noStudent = "Unable to delete. Student with id " + id + " not found.";
			throw new NoSuchStudentExption(noStudent);
		} else {
			studentRepository.deleteById(id);
		}

	}

	public List<Student> findAll() {
		Sort sort = new Sort(Sort.Direction.DESC, "name");
		List<Student> list = studentRepository.findAll(sort);
		return list;
	}

	public Page<Student> findAllByPage(int pageNum, int recordCount) {
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		Pageable pageable = PageRequest.of(pageNum - 1, recordCount, sort);
		Page<Student> result = studentRepository.findAll(pageable);
		return result;
	}

	public Page<List<Student>> findByNameContains(String name, int pageNum, int recordCount) {
		Pageable pageable = PageRequest.of(pageNum - 1, recordCount);
		Page<List<Student>> result = studentRepository.findByNameContains(name, pageable);
		return result;
	}
}
