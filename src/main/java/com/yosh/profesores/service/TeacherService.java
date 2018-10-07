package com.yosh.profesores.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yosh.profesores.dao.ITeacher;
import com.yosh.profesores.models.Teacher;

@Service("teacherService")
@Transactional
public class TeacherService implements ITeacherService{

	@Autowired
	private ITeacher _teacher;
	
	@Override
	public void save(Teacher teacher) {
		_teacher.save(teacher);
	}

	@Override
	public List<Teacher> findAll() {
		return _teacher.findAll();
	}

	@Override
	public Teacher findById(Long idTeacher) {
		return _teacher.findById(idTeacher);
	}

	@Override
	public Teacher findByName(String name) {
		return _teacher.findByName(name);
	}

	@Override
	public void deleteById(Long idTeacher) {
		_teacher.deleteById(idTeacher);
	}

	@Override
	public void update(Teacher teacher) {
		_teacher.update(teacher);
	}

}
