package com.yosh.profesores.service;

import java.util.List;

import com.yosh.profesores.models.Teacher;

public interface ITeacherService {
	
	void save(Teacher teacher);
	
	List<Teacher> findAll();
	
	Teacher findById(Long idTeacher);
	
	Teacher findByName(String name);
	
	void deleteById(Long idTeacher);
	
	void update(Teacher teacher);
}
