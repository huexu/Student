package com.infosys.student.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.student.exception.NoSuchStudentExption;
import com.infosys.student.exception.StudentAlreadyPresentException;
import com.infosys.student.model.JsonResult;
import com.infosys.student.model.ResultCode;
import com.infosys.student.model.StudentDTO;
import com.infosys.student.service.StudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "StudentController, REST APIs that deal with Student DTO")
@RequestMapping("/APIs")
public class StudentController {

	@Autowired
	StudentService studentService;
	// Records per page
	static final int recordsPerPage = 5;

	// Fetching student details by Get
	@RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Fetch Student by id", response = StudentDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched the student successfully"),
			@ApiResponse(code = 404, message = "No such student") })
	public JsonResult fetchStudent(@PathVariable String id) {
		try {
			return new JsonResult(ResultCode.SUCCESS, "Fetched", studentService.fetchStudent(id));
		} catch (NoSuchStudentExption e) {
			return new JsonResult(ResultCode.NOT_FUND, "No Result", e.getMessage());
		} catch (Exception e) {
			return new JsonResult(ResultCode.EXCEPTION, "Exception occurred!", e.getMessage());
		}
	}

	// add student details by Post
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	@ApiOperation(value = "Create a Student", response = StudentDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Student created successfully") })
	public JsonResult addStudnet(@RequestBody StudentDTO st) {
		if (st.getId() == null) {
			UUID uid = UUID.randomUUID();
			String[] idd = uid.toString().split("-");
			String id = idd[0] + idd[1] + idd[2] + idd[3];
			st.setId(id);
		}
		try {
			return new JsonResult(ResultCode.SUCCESS, "Created", studentService.addStudent(st.getStudent()));
		} catch (StudentAlreadyPresentException e) {
			return new JsonResult(ResultCode.CONFLICT, "Exist", e.getMessage());
		} catch (Exception e) {
			return new JsonResult(ResultCode.EXCEPTION, "Exception occurred!", e.getMessage());
		}
	}

	// Update Student details by Put
	@RequestMapping(value = "/student", method = RequestMethod.PUT)
	@ApiOperation(value = "Update a Student", response = StudentDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Student Update successfully") })
	public JsonResult putStudent(@RequestBody StudentDTO st) {
		try {
			return new JsonResult(ResultCode.SUCCESS, "Updated",
					studentService.updateStudent(st.getId(), st.getName(), st.getClassName()));
		} catch (NoSuchStudentExption e) {
			return new JsonResult(ResultCode.NOT_FUND, "No Result", e.getMessage());
		} catch (Exception e) {
			return new JsonResult(ResultCode.EXCEPTION, "Exception occurred!", e.getMessage());
		}
	}

	// Delete Student according ID
	@RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a Student by Id", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Student Deleted successfully") })
	public JsonResult deleteStudnet(@PathVariable String id) {

		try {
			studentService.deleteStudent(id);
			return new JsonResult(ResultCode.SUCCESS, "Deleted");
		} catch (NoSuchStudentExption e) {
			return new JsonResult(ResultCode.NOT_FUND, "No Result", e.getMessage());
		} catch (Exception e) {
			return new JsonResult(ResultCode.EXCEPTION, "Exception occurred!", e.getMessage());
		}
	}

	// File all student records
	@RequestMapping("/student/findAll")
	@ApiOperation(value = "File all student records", response = ArrayList.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Student Deleted successfully") })
	public JsonResult findAll() {
		return new JsonResult(ResultCode.SUCCESS, "Fetched", studentService.findAll());

	}

	// File all Student records pageable
	@RequestMapping(value = "/student/findByPage/{pageNum}", method = RequestMethod.POST)
	@ApiOperation(value = "File all Student records pageable", response = Page.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch Page successfully") })
	public JsonResult findByPage(@PathVariable("pageNum") int pageNum) {
		return new JsonResult(ResultCode.SUCCESS, "Fetched", studentService.findAllByPage(pageNum, recordsPerPage));
	}

	// File Student by Name, and pageable
	@RequestMapping(value = "/student/findByName", method = RequestMethod.POST)
	@ApiOperation(value = "File Student by Name, and pageable", response = Page.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch Page successfully") })
	public JsonResult findByNameContains(@RequestBody StudentDTO st) {
		return new JsonResult(ResultCode.SUCCESS, "Fetched",
				studentService.findByNameContains(st.getName(), st.getPageNum(), recordsPerPage));
	}
}
