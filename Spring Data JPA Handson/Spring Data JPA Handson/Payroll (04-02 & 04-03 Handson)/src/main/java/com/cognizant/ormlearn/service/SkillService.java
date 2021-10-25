package com.cognizant.ormlearn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.ormlearn.model.Skill;
import com.cognizant.ormlearn.repository.SkillRepository;

@Service
public class SkillService {

	@Autowired
	SkillRepository skillRepository;
	
	public Skill findSkillById(int id) {
		return skillRepository.findById(id).get();
	}
	
}
