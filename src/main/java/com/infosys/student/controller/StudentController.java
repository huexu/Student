package com.infosys.student.controller;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.student.exception.NoSuchStudentExption;
import com.infosys.student.exception.StudentAlreadyPresentException;
import com.infosys.student.model.ErrorMessage;
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
	@RequestMapping(value = "/student/{id}", method = {RequestMethod.GET,RequestMethod.POST})
	@ApiOperation(value = "Fetch Student by id", response = StudentDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched the student successfully"),
			@ApiResponse(code = 404, message = "No such student") })
	public ResponseEntity<JsonResult> fetchStudent(@PathVariable String id) {
		
		try {
			return ResponseEntity.ok(new JsonResult(ResultCode.SUCCESS, "Fetched", studentService.fetchStudent(id)));
		} catch (NoSuchStudentExption e) {
			return ResponseEntity.ok(new JsonResult(ResultCode.NOT_FUND, "No Result", e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.ok(new JsonResult(ResultCode.EXCEPTION, "Exception occurred!", e.getMessage()));
		}
	}

	// add student details by Post
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	@ApiOperation(value = "Create a Student", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Student created successfully"),
			@ApiResponse(code = 409, message = "Conflict"),
			@ApiResponse(code = 444, message = "Exception occurred") })
	public ResponseEntity addStudnet(@Valid @RequestBody StudentDTO st,Errors errors) {
		JsonResult result = null;
		
		if (errors.hasErrors()) 
		{
			String errString = "";
			errString  = errors.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.joining(","));
			ErrorMessage error = new ErrorMessage();
		    error.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
		    error.setMessage(errString);
		    return ResponseEntity.ok(error);
		}
		
		if (st.getId() == null) {
			UUID uid = UUID.randomUUID();
			String[] idd = uid.toString().split("-");
			String id = idd[0] + idd[1] + idd[2] + idd[3];
			st.setId(id);
		}
		
		try {
			result = new JsonResult(ResultCode.SUCCESS, "Created", studentService.addStudent(st.getStudent()));
			
		} catch (StudentAlreadyPresentException e) {
			result = new JsonResult(ResultCode.CONFLICT, "Exist", e.getMessage());
		} catch (Exception e) {
			result = new JsonResult(ResultCode.EXCEPTION, "Exception occurred!", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}

	// Update Student details by Put
	@RequestMapping(value = "/student", method = RequestMethod.PUT)
	@ApiOperation(value = "Update a Student", response = StudentDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Student Update successfully"),
			@ApiResponse(code = 204, message = "No Data"),
			@ApiResponse(code = 444, message = "Exception occurred") })
	public ResponseEntity<JsonResult> putStudent(@RequestBody StudentDTO st) {
		try {
			return ResponseEntity.ok(new JsonResult(ResultCode.SUCCESS, "Updated",
					studentService.updateStudent(st.getId(), st.getName(), st.getClassName())));
		} catch (NoSuchStudentExption e) {
			return ResponseEntity.ok(new JsonResult(ResultCode.NO_CONTENT, "No Result", e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.ok(new JsonResult(ResultCode.EXCEPTION, "Exception occurred!", e.getMessage()));
		}
	}

	// Delete Student according ID
	@RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a Student by Id", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Student Deleted successfully"),
			@ApiResponse(code = 204, message = "No Data"),
			@ApiResponse(code = 444, message = "Exception occurred")})
	public ResponseEntity<JsonResult> deleteStudnet(@PathVariable String id) {

		try {
			studentService.deleteStudent(id);
			return ResponseEntity.ok(new JsonResult(ResultCode.SUCCESS, "Deleted"));
		} catch (NoSuchStudentExption e) {
			return ResponseEntity.ok(new JsonResult(ResultCode.NO_CONTENT, "No Result", e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.ok(new JsonResult(ResultCode.EXCEPTION, "Exception occurred!", e.getMessage()));
		}
	}

	// File all student records
	@GetMapping("/student/findAll")
	@ApiOperation(value = "File all student records", response = ArrayList.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Student Deleted successfully") })
	public ResponseEntity<JsonResult> findAll() {
		return ResponseEntity.ok(new JsonResult(ResultCode.SUCCESS, "Fetched", studentService.findAll()));

	}

	// File all Student records pageable
	@RequestMapping(value = "/student/findByPage/{pageNum}", method = {RequestMethod.GET,RequestMethod.POST})
	@ApiOperation(value = "File all Student records pageable", response = Page.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch Page successfully") })
	public ResponseEntity<JsonResult> findByPage(@PathVariable("pageNum") int pageNum) {
		return ResponseEntity.ok(new JsonResult(ResultCode.SUCCESS, "Fetched", studentService.findAllByPage(pageNum, recordsPerPage)));
	}

	// File Student by Name, and pageable
	@RequestMapping(value = "/student/findByName", method = {RequestMethod.GET,RequestMethod.POST})
	@ApiOperation(value = "File Student by Name, and pageable", response = Page.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch Page successfully") })
	public ResponseEntity<JsonResult> findByNameContains(@RequestBody StudentDTO st) {
		return ResponseEntity.ok(new JsonResult(ResultCode.SUCCESS, "Fetched",
				studentService.findByNameContains(st.getName(), st.getPageNum(), recordsPerPage)));
	}
}
