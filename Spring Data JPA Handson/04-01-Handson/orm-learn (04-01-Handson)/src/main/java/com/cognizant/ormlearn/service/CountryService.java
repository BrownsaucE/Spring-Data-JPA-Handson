package com.cognizant.ormlearn.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;

@Service
public class CountryService {

	@Autowired
	CountryRepository countryRepository;
	
	@Transactional
	public List<Country> getAllCountries(){
		return countryRepository.findAll();
	}
	
	// Find a country based on country code
	@Transactional
	public Country findCountryByCode(String countryCode) throws CountryNotFoundException{
		Optional<Country> result = countryRepository.findById(countryCode);
		if(!result.isPresent())
			throw new CountryNotFoundException("< ---------- Country not found ---------- >");
		else {
			Country country = result.get();
			return country;
		}
	}
	
	// Add new country
	@Transactional
	public void addCountry(Country country) {
		countryRepository.save(country);
	}
	
	// Update new country
	@Transactional
	public void updateCountry(Country country) {
		Country outdatedCountry = countryRepository.findById(country.getCode()).get();
		outdatedCountry.setName(country.getName());
		Country updatedCountry = outdatedCountry;
		countryRepository.save(updatedCountry);
	}
	
	// Delete country
	@Transactional
	public void deleteCountry(String code) {
		countryRepository.deleteById(code);
	}
	
	// Find list of countries matching a partial country name
	public List<Country> findCountryByPartialName(String name){
		return null;
	}
}
