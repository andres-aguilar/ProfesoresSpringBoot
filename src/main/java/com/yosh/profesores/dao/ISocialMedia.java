package com.yosh.profesores.dao;

import java.util.List;

import com.yosh.profesores.model.SocialMedia;
import com.yosh.profesores.model.TeacherSocialMedia;

public interface ISocialMedia {

	void save(SocialMedia socialMedia);
	
	List<SocialMedia> findAll();
	
	SocialMedia findById(Long idSocialMedia);
	
	SocialMedia findByName(String name);
	
	void delete(Long idSocialMedia);
	
	void update(SocialMedia socialMedia);
	
	TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String Name);
	
}
