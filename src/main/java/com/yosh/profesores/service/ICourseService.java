package com.yosh.profesores.service;

import java.util.List;

import com.yosh.profesores.models.Course;

public interface ICourseService {

	void save(Course course);
	
	List<Course> findAll();
	
	Course findById(Long idCourse);
	
	Course findByName(String name);
	
	void deleteById(Long idCourse);
	
	void update(Course course);
	
	List<Course> findByIdTeacher(Long idTeacher);
	
}
