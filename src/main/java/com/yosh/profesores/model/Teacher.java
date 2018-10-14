package com.yosh.profesores.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="teacher")
public class Teacher implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_teacher")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idTeacher;
	
	@Column(name="name")
	private String name;
	
	@Column(name="avatar")
	private String avatar;
	
	@OneToMany(mappedBy="teacher")
	@JsonIgnore
	private Set<Course> courses;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_teacher")
	@JsonIgnore
	private Set<TeacherSocialMedia> socialMedia;
	
	public Teacher() {
	}
	
	public Teacher(String name, String avatar) {
		this.name = name;
		this.avatar = avatar;
	}
	
	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Set<TeacherSocialMedia> getSocialMedia() {
		return socialMedia;
	}

	public void setSocialMedia(Set<TeacherSocialMedia> socialMedia) {
		this.socialMedia = socialMedia;
	}

	public Long getIdTeacher() {
		return idTeacher;
	}
	
	public void setIdTeacher(long idTeacher) {
		this.idTeacher = idTeacher;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	

}
