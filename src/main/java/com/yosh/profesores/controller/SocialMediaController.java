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

import com.yosh.profesores.model.SocialMedia;
import com.yosh.profesores.service.ISocialMediaService;

@Controller
@RequestMapping("/v1")
public class SocialMediaController {
	
	@Autowired
	ISocialMediaService _socialMediaService;

	/**
	 *  getSocialMedias
	 * 
	 *  Get All social medias
	 *  
	 *  @return ResponseEntity<List<SocialMedia>>
	 */
	@RequestMapping(value="/socialMedias", method=RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<List<SocialMedia>> getSocialMedias() {
		List<SocialMedia> socialMedias = new ArrayList<>();
		socialMedias = _socialMediaService.findAll();
		
		if(socialMedias.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<SocialMedia>>(socialMedias, HttpStatus.OK);
	}
	
	/**
	 *  getSocialMediaById
	 *  
	 *  Get One social media by Id
	 *  
	 *  @return ResponseEntity<SocialMedia>
	 */
	@RequestMapping(value="/socialMedias/{id}", method=RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<SocialMedia> getSocialMediaById(@PathVariable("id") Long id) {
		if(id == null || id < 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		SocialMedia socialMedia = _socialMediaService.findById(id);
		if(socialMedia == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<SocialMedia>(socialMedia, HttpStatus.OK);
	} 
	
	/**
	 * createSocialMedia
	 * 
	 * Create a new SocialMedia if not exists
	 * 
	 * @return ResponseEntity<?>
	 */
	@RequestMapping(value="/socialMedias", method=RequestMethod.POST, headers="Accept=application/json")
	public ResponseEntity<?> createSocialMedia(@RequestBody SocialMedia socialMedia, UriComponentsBuilder uriComponentBuilder) {
		if (socialMedia.getName().equals(null) || socialMedia.getName().isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		if (_socialMediaService.findByName(socialMedia.getName()) != null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		_socialMediaService.save(socialMedia);
		socialMedia = _socialMediaService.findByName(socialMedia.getName());
		HttpHeaders headers = new HttpHeaders();
		
		headers.setLocation(uriComponentBuilder.path("/v1/socialMedias/{id}")
											   .buildAndExpand(socialMedia.getIdSocialMedia())
											   .toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	
	/**
	 *  updateSocialMedia
	 *  
	 *   Update a social media
	 *   
	 *   @return ResponseEntity<SocialMedia>
	 */
	@RequestMapping(value="/socialMedias/{id}", method=RequestMethod.PATCH, headers="Accept=appliucation/json")
	public ResponseEntity<?> updateSocialMedia(@PathVariable("id") Long id, @RequestBody SocialMedia socialMedia) {
		if(id == null || id < 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		SocialMedia currentSocialMedia = _socialMediaService.findById(id);
		
		if (currentSocialMedia == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		currentSocialMedia.setName(socialMedia.getName());
		currentSocialMedia.setIcon(socialMedia.getIcon());
		
		_socialMediaService.update(currentSocialMedia);
		
		return new ResponseEntity<SocialMedia>(currentSocialMedia, HttpStatus.OK);
	}
	
	
	/**
	 *  deleteSocialMedia
	 *  
	 *  Delete one social media by Id
	 *  
	 *  @return ResponseEntity<?>
	 */
	@RequestMapping(value="/socialMedias/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteSocialMedia(@PathVariable("id") Long id) {
		if(id == null || id < 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		SocialMedia socialMedia = _socialMediaService.findById(id);
		if(socialMedia == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		_socialMediaService.delete(id);
		
		return new ResponseEntity<SocialMedia>(HttpStatus.OK);
	}
}