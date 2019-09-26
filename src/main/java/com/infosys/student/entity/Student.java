package com.infosys.student.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student {
	@Id
	String id;
	String name;
	String className;
	
	public Student() {
		
	}
	
	public Student(String id, String name, String className) {
		this.id=id;
		this.name = name;
		this.className = className;
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
