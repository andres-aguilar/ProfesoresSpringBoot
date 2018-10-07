package com.yosh.profesores.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yosh.profesores.dao.ICourse;
import com.yosh.profesores.models.Course;

@Service("courseService")
@Transactional
public class CourseService implements ICourseService{

	@Autowired
	private ICourse _course;
	
	@Override
	public void save(Course course) {
		_course.save(course);
	}

	@Override
	public List<Course> findAll() {
		return _course.findAll();
	}

	@Override
	public Course findById(Long idCourse) {
		return _course.findById(idCourse);
	}

	@Override
	public Course findByName(String name) {
		return _course.findByName(name);
	}

	@Override
	public void deleteById(Long idCourse) {
		_course.deleteById(idCourse);
	}

	@Override
	public void update(Course course) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Course> findByIdTeacher(Long idTeacher) {
		// TODO Auto-generated method stub
		return null;
	}

}
