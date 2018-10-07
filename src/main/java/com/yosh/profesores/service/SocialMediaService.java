package com.yosh.profesores.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yosh.profesores.dao.ISocialMedia;
import com.yosh.profesores.model.SocialMedia;
import com.yosh.profesores.model.TeacherSocialMedia;

@Service("socialMediaService")
@Transactional
public class SocialMediaService implements ISocialMediaService{

	@Autowired
	private ISocialMedia _socialMedia;
	
	@Override
	public void save(SocialMedia socialMedia) {
		_socialMedia.save(socialMedia);
	}

	@Override
	public List<SocialMedia> findAll() {
		return _socialMedia.findAll();
	}

	@Override
	public SocialMedia findById(Long idSocialMedia) {
		return _socialMedia.findById(idSocialMedia);
	}

	@Override
	public SocialMedia findByName(String name) {
		return _socialMedia.findByName(name);
	}

	@Override
	public void delete(Long idSocialMedia) {
		_socialMedia.delete(idSocialMedia);
	}

	@Override
	public void update(SocialMedia socialMedia) {
		_socialMedia.update(socialMedia);
	}

	@Override
	public TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String Name) {
		return _socialMedia.findSocialMediaByIdAndName(idSocialMedia, Name);
	}

}
