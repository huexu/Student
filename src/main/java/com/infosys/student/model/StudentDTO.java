package com.infosys.student.model;

import org.springframework.stereotype.Component;

import com.infosys.student.entity.Student;

@Component
public class StudentDTO {
	String id;
	String name;
	String className;
	int pageNum = 1;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public StudentDTO() {
	}

	public StudentDTO(String id, String name, String className) {
		this.id = id;
		this.name = name;
		this.className = className;
	}

	public Student getStudent() {
		return new Student(this.id, this.name, this.className);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String classname) {
		this.className = classname;
	}

}
