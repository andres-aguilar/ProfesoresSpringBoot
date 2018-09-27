package com.yosh.profesores.dao;

import java.util.List;

import com.yosh.profesores.models.Course;

public class CourseDao extends AbstractSession implements ICourse{
	
	public CourseDao() {}

	@Override
	public void save(Course course) {
		getSession().persist(course);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> findAll() {
		return (List<Course>)getSession().createQuery("from Course").list();
	}

	@Override
	public Course findById(Long idCourse) {
		return getSession().get(Course.class, idCourse);
	}

	@Override
	public void deleteById(Long idCourse) {
		Course course = findById(idCourse);
		if (course != null)
			getSession().delete(course);
	}

	@Override
	public void update(Course course) {
		getSession().update(course);
	}

	@Override
	public Course findByName(String name) {
		return (Course)getSession().createQuery("from Course where name = :name")
				.setParameter("name", name)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> findByIdTeacher(Long idTeacher) {
		return (List<Course>)getSession().createQuery("FROM Course c JOIN Course.teacher t WHERE t.idTeacher = :idTeacher")
				.setParameter("idTeacher", idTeacher)
				.list();
	}

}
