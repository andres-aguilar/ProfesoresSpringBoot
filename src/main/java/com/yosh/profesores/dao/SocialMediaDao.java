package com.yosh.profesores.dao;

import java.util.List;

import com.yosh.profesores.models.SocialMedia;

public class SocialMediaDao extends AbstractSession implements ISocialMedia {

	@Override
	public void save(SocialMedia socialMedia) {
		getSession().persist(socialMedia);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SocialMedia> findAll() {
		return (List<SocialMedia>)getSession().createQuery("FROM SocialMedia").list();
	}

	@Override
	public SocialMedia findById(Long idSocialMedia) {
		return getSession().get(SocialMedia.class, idSocialMedia);
	}

	@Override
	public SocialMedia findByName(String name) {
		return (SocialMedia)getSession().createQuery("FROM SocialMedia WHERE name = :name")
				.setParameter("name", name)
				.uniqueResult();
	}

	@Override
	public void delete(Long idSocialMedia) {
		SocialMedia socialMedia = findById(idSocialMedia);
		if (socialMedia != null)
			getSession().delete(socialMedia);
	}

	@Override
	public void update(SocialMedia socialMedia) {
		getSession().update(socialMedia);
	}

}
