package com.yosh.profesores.dao;

import java.util.List;

import com.yosh.profesores.model.Teacher;

public interface ITeacher {
	
	void save(Teacher teacher);
	
	List<Teacher> findAll();
	
	Teacher findById(Long idTeacher);
	
	Teacher findByName(String name);
	
	void deleteById(Long idTeacher);
	
	void update(Teacher teacher);
}
