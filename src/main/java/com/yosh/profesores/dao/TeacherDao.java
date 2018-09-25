package com.yosh.profesores.dao;

import java.util.List;

import com.yosh.profesores.models.Teacher;

public class TeacherDao extends AbstractSession implements ITeacher {
	
	public TeacherDao() {}
	
	@Override
	public void save(Teacher teacher) {
		getSession().persist(teacher);
	}
	
	@Override
	public List<Teacher> findAll() {
		getSession().createQuery("from Teacher").list();
		return null;		
	}

	@Override
	public Teacher findById(Long idTeacher) {
		return getSession().get(Teacher.class, idTeacher);
	}

	@Override
	public Teacher findByName(String name) {
		return (Teacher)getSession().createQuery("from Teacher where name = :name")
				.setParameter("name", name)
				.uniqueResult();
	}

	@Override
	public void deleteById(Long idTeacher) {
		Teacher teacher = findById(idTeacher);
		if(teacher != null) {
			getSession().delete(teacher);
		}
	}

	@Override
	public void update(Teacher teacher) {
		getSession().update(teacher);
	}

}
