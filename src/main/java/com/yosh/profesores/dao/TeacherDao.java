package com.yosh.profesores.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.yosh.profesores.models.Teacher;

@Repository
@Transactional
public class TeacherDao extends AbstractSession implements ITeacher {
	
	public TeacherDao() {}
	
	@Override
	public void save(Teacher teacher) {
		getSession().persist(teacher);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> findAll() {
		return (List<Teacher>)getSession().createQuery("from Teacher").list();
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
