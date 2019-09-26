package com.infosys.student.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infosys.student.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

	@Query("select s from Student s where s.name like %?1% order by name")
	public Page<List<Student>> findByNameContains(String name, Pageable pageable);
}
