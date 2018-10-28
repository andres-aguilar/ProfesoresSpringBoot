package com.yosh.profesores.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	
	/* Avatar methods */
	
	public static final String TEACHER_UPLOAD_FOLDER = "images/teachers/";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/teachers/image", method=RequestMethod.POST, headers=("content-type=multipart/form-data"))
	public ResponseEntity<byte[]> uploadTeacherImage(@RequestParam("id_teacher") Long idTeacher, @RequestParam("file") MultipartFile multipartFile,  UriComponentsBuilder uriComponentBuilder) {
		
		if (idTeacher == null) {
			return new ResponseEntity(new CustomErrorType("IdTeacher is required"), HttpStatus.NO_CONTENT);
		}
		if (multipartFile.isEmpty()) {
			return new ResponseEntity(new CustomErrorType("Please select a file to upload"), HttpStatus.NO_CONTENT);
		}
		
		Teacher teacher = _teacherService.findById(idTeacher);
		if (teacher == null) {
			return new ResponseEntity(new CustomErrorType("Error: Teacher not found!"), HttpStatus.NOT_FOUND);
		}
		
		if (teacher.getAvatar() != null || !teacher.getAvatar().isEmpty()) {
			String fileName = teacher.getAvatar();
			Path path = Paths.get(fileName);
			File f = path.toFile();
			
			if (f.exists()) { f.delete(); }
		}
		
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
			String dateName = dateFormat.format(date);
			
			String fileName = String.valueOf(idTeacher) + "-pictureTeacher-" + dateName + "." + multipartFile.getContentType().split("/")[1];
			teacher.setAvatar( TEACHER_UPLOAD_FOLDER + fileName);
			
			byte[] bytes = multipartFile.getBytes();
			Path path = Paths.get(TEACHER_UPLOAD_FOLDER + fileName);
			Files.write(path, bytes);
			
			_teacherService.update(teacher);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
		} catch(Exception e) {
			return new ResponseEntity(new CustomErrorType("Error during upload"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/teachers/{id_teacher}/image", method=RequestMethod.GET)
	public ResponseEntity<byte[]> getTeacherImage(@PathVariable("id_teacher") Long idTeacher) {
		if (idTeacher == null) {
			return new ResponseEntity(new CustomErrorType("IdTeeacher is required"), HttpStatus.NO_CONTENT);
		}
		
		Teacher teacher = _teacherService.findById(idTeacher);
		if (teacher == null) {
			return new ResponseEntity(new CustomErrorType("Teacher not found"), HttpStatus.NOT_FOUND);
		}
		
		try {
			
			String fileName = teacher.getAvatar();
			Path path = Paths.get(fileName);
			File f = path.toFile();
			
			if (!f.exists()) {
				return new ResponseEntity(new CustomErrorType("Image not found"), HttpStatus.NOT_FOUND);
			}
			
			byte[] image = Files.readAllBytes(path);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new CustomErrorType(""), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/teachers/{id_teacher}/image", method=RequestMethod.DELETE, headers="Accept=application/json")
	public ResponseEntity<?> deleteTeacherImage(@PathVariable("id_teacher") Long idTeacher) {
		if (idTeacher == null || idTeacher <= 0) {
			return new ResponseEntity(new CustomErrorType("IdTeacher is required"), HttpStatus.NO_CONTENT);
		}
		Teacher teacher = _teacherService.findById(idTeacher);
		if (teacher == null) {
			return new ResponseEntity(new CustomErrorType("Teacher not found"), HttpStatus.NOT_FOUND);
		}
		
		if (teacher.getAvatar() == null || teacher.getAvatar().isEmpty()) {
			return new ResponseEntity(new CustomErrorType("Teacher doesn't have image assigned"), HttpStatus.NO_CONTENT);
		}
		
		String fileName = teacher.getAvatar();
		Path path = Paths.get(fileName);
		File f = path.toFile();
		
		if (f.exists()) { f.delete(); }
		teacher.setAvatar("");
		
		_teacherService.update(teacher);
		
		return new ResponseEntity(HttpStatus.OK);
	}
}

































