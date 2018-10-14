package com.yosh.profesores.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.yosh.profesores.model.Teacher;
import com.yosh.profesores.service.ITeacherService;
import com.yosh.profesores.util.CustomErrorType;

@Controller
@RequestMapping("/v1")
public class Teachercontroller {
	
	@Autowired
	ITeacherService _teacherService;
	
	/**
	 * getTeachers
	 * 
	 *  Get all teachers
	 *  
	 *  @return ResponseEntity<List<Teacher>>
	 */
	@RequestMapping(value="/teachers", method=RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<List<Teacher>> getTeachers() {
		List<Teacher> teacherList = new ArrayList<Teacher>();
		
		teacherList = _teacherService.findAll();
		
		if (teacherList.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Teacher>>(teacherList, HttpStatus.OK);
	}
	
	/**
	 * getTeacher
	 * 
	 *  Get Teacher by Id
	 *  
	 *  @param Long idTeacher
	 *  
	 *  @return ResponseEntity<Teacher>
	 */
	@RequestMapping(value="/teachers/{id}", method=RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<Teacher> getTeacher(@PathVariable("id") Long idTeacher) {
		if (idTeacher == null || idTeacher < 0) {
			return new ResponseEntity(new CustomErrorType("idTeacher is requiered"), HttpStatus.CONFLICT);
		}
		
		Teacher teacher = _teacherService.findById(idTeacher);
		if (teacher == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
	}
	
	
	/**
	 * createTeacher 
	 * 
	 * Create new Teacher if not exists
	 * 
	 * @return ResponseEntity<?>
	 */
	@RequestMapping(value="/teachers", method=RequestMethod.POST, headers="Accept=application/json")
	public ResponseEntity<?> createTeacher(@RequestBody Teacher teacher, UriComponentsBuilder uriComponentBuilder) {
		if (teacher.getName().equals(null) || teacher.getName().isEmpty()) {
			return new ResponseEntity(new CustomErrorType("Teacher name is required"), HttpStatus.CONFLICT);
		}
		if (_teacherService.findByName(teacher.getName()) != null) {
			return new ResponseEntity(new CustomErrorType("Teacher already exists"), HttpStatus.CONFLICT);
		}
		
		_teacherService.save(teacher);
		teacher = _teacherService.findByName(teacher.getName());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponentBuilder.path("/v1/teachers/{id}")
								.buildAndExpand(teacher.getIdTeacher())
								.toUri() );
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	/**
	 *  updateTeacher
	 */
	@RequestMapping(value="/teachers/{id}", method=RequestMethod.PATCH, headers="Accept=application/json")
	public ResponseEntity<?> updateTeacher(@PathVariable("id") Long idTeacher, @RequestBody Teacher teacher) {
		if (idTeacher == null || idTeacher < 0) {
			return new ResponseEntity(new CustomErrorType("idTeacher is requiered"), HttpStatus.CONFLICT);
		}
		
		Teacher currentTeacher = _teacherService.findById(idTeacher);
		if (currentTeacher == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		currentTeacher.setName(teacher.getName());
		currentTeacher.setAvatar(teacher.getAvatar());
		
		_teacherService.update(currentTeacher);
		return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
	}
	
	@RequestMapping(value="/teachers/{id}", method=RequestMethod.DELETE, headers="Accept=application/json")
	public ResponseEntity<?> deleteteacher(@PathVariable("id") Long idTeacher) {
		if (idTeacher == null || idTeacher < 0) {
			return new ResponseEntity(new CustomErrorType("A valid idTeacher is required"), HttpStatus.CONFLICT);
		}
		
		Teacher teacher = _teacherService.findById(idTeacher);
		if (teacher == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		_teacherService.deleteById(idTeacher);
		return new ResponseEntity<Teacher>(HttpStatus.OK);
	}
}

































