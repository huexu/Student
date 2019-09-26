package com.example.demo.test;

import static org.hamcrest.CoreMatchers.is;

import java.text.ParseException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.infosys.student.entity.Student;
import com.infosys.student.exception.NoSuchStudentExption;
import com.infosys.student.exception.StudentAlreadyPresentException;
import com.infosys.student.model.StudentDTO;
import com.infosys.student.repository.StudentRepository;
import com.infosys.student.service.StudentService;

@RunWith(MockitoJUnitRunner.class)
public class TestStudentService {
	@ClassRule
	public static ClearDatabaseRule dbRule = new ClearDatabaseRule();

	@Mock
	private StudentRepository studentRepository;

	@InjectMocks
	private StudentService studentService;

	private static StudentDTO sdto = new StudentDTO();

	@BeforeClass
	public static void setup() throws ParseException {
		sdto = new StudentDTO();
		sdto.setId("1004");
		sdto.setClassName("Cl3");
		sdto.setName("Angle");
	}

	@Test(expected = StudentAlreadyPresentException.class)
	public void testCreateStudent() throws StudentAlreadyPresentException, Exception {
		Student student = sdto.getStudent();
		Student result = studentService.addStudent(student);
		Assert.assertTrue(student.getId().equals(result.getId()));
	}

	@Test(timeout = 2000)
	public void findAll() {
		List<Student> result = studentService.findAll();
		Assert.assertTrue(result.size() >= 0);
	}

	@Test(expected = NoSuchStudentExption.class)
	public void fetchStudent() throws NoSuchStudentExption, Exception {
		Student result = studentService.fetchStudent("1004");
		Assert.assertThat(result.getId(), is("1004"));
	}

	@AfterClass
	public static void destroyInput() {
		sdto = null;
	}
}
