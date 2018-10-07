package com.yosh.profesores.service;

import java.util.List;

import com.yosh.profesores.model.SocialMedia;
import com.yosh.profesores.model.TeacherSocialMedia;

public interface ISocialMediaService {
	
	void save(SocialMedia socialMedia);
	
	List<SocialMedia> findAll();
	
	SocialMedia findById(Long idSocialMedia);
	
	SocialMedia findByName(String name);
	
	void delete(Long idSocialMedia);
	
	void update(SocialMedia socialMedia);
	
	TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String Name);
}
