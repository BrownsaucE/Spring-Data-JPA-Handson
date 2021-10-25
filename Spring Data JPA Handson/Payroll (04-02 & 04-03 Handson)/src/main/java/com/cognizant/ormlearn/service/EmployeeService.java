package com.cognizant.ormlearn.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.repository.EmployeeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeService {
	
	private Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Transactional
	public Employee get(int id) {
		LOGGER.info("Start");
		return employeeRepository.findById(id).get();
	}
	
	@Transactional
	public void save(Employee employee) {
		LOGGER.info("Start");
		employeeRepository.save(employee);
		LOGGER.info("End");
	}
	
	// 04-03 Hands on 2 - Include appropriate method
	@Transactional
	public List<Employee> getAllPermanentEmployees() {
		return employeeRepository.getAllPermanentEmployees();
	}
	
	// 04-03 Hands on 4 - Include a new method in EmployeeService 
	@Transactional
	public double getAverageSalary(int departmentId) {
		return employeeRepository.getAverageSalary(departmentId);
	}
	
	// 04-03 Hands on 5
	@Transactional
	public List<Employee> getAllEmployeesNative(){
		return employeeRepository.getAllEmployeesNative();
	}
}
