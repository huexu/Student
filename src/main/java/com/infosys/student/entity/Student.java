package com.infosys.student.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student {
	@Id
//	create sequence oracle_seq; Oracle创建序列，主键使用序列
//	@SequenceGenerator(name="id",sequenceName="oracle_seq") // You need to specify sequenceName if it is different from name, otherwise just use name
//  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="id")	
	
//  @GeneratedValue(strategy = GenerationType.IDENTITY) //H2 Not support
//	@Column(updatable = false)
	String id;

    @Column(nullable = false)
	String name;

    @Column(nullable = false,name = "classname")
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
