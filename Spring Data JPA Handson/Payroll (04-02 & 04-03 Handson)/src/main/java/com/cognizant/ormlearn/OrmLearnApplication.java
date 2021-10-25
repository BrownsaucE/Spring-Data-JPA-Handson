package com.cognizant.ormlearn;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Department;
import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.model.Skill;
import com.cognizant.ormlearn.service.DepartmentService;
import com.cognizant.ormlearn.service.EmployeeService;
import com.cognizant.ormlearn.service.SkillService;

// 04-02-spring-data-jpa-handson
// Added and used my own data in MySQL

@SpringBootApplication
public class OrmLearnApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);
	
	private static EmployeeService employeeService;
	private static DepartmentService departmentService;
	private static SkillService skillService;
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
		employeeService = context.getBean(EmployeeService.class);
		departmentService = context.getBean(DepartmentService.class);
		skillService = context.getBean(SkillService.class);
		
		//testGetEmployee();
		//testAddEmployee();
		//testUpdateEmployee();
		//testGetDepartment();
		//testAddSkillToEmployee();
		//testGetAllPermanentEmployees();
		testGetAverageSalary();
		//testGetAllEmployeesNative();
	}

	// 04-02 Hands on 4 - Getting Employee along with Department
	private static void testGetEmployee() {
		LOGGER.info("Start");
		Employee employee = employeeService.get(1);
		LOGGER.debug("Employee:{}", employee); // Employee:Employee [id=1, name=harry potter, salary=4000.00, permanent=true, dateOfBirth=2021-01-01]
		LOGGER.debug("Department:{}", employee.getDepartment()); // Department:Department [id=2, name=department2]
		LOGGER.info("End");
	}
	
	// 04-02 Hands on 4 - Add Employee
	private static void testAddEmployee() {
		LOGGER.info("Start");
		
		Employee employee = new Employee();
		employee.setId(2);
		employee.setName("harry second");
		employee.setSalary(3200.54);
		employee.setPermanent(false);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.US);
    	LocalDate localDate = LocalDate.parse("2021-12-12", formatter);
		employee.setDateOfBirth(java.sql.Date.valueOf(localDate));
		
		employee.setDepartment(departmentService.findDepartmentById(1)); // Had problem with this one. In MySQL, make sure that the Auto-Increment(AI) checkbox for the primary key/id is checked.
		employeeService.save(employee);
		LOGGER.debug("Employee:{}", employee); // Employee:Employee [id=2, name=harry second, salary=3200.54, permanent=false, dateOfBirth=2021-12-12]
		LOGGER.info("End");
	}
	
	// 04-02 Hands on 4 - Update Employee
	private static void testUpdateEmployee() {
		LOGGER.info("Start");
		Employee employee = employeeService.get(1);
		LOGGER.debug("Old Department:{}", employee.getDepartment()); // Old Department:Department [id=1, name=department1]
		Department department = departmentService.findDepartmentById(2);
		employee.setDepartment(department);
		employeeService.save(employee);
		LOGGER.debug("Employee:{}", employee); // Employee:Employee [id=1, name=harry potter, salary=4000.00, permanent=true, dateOfBirth=2021-01-01]
		LOGGER.debug("Department:{}", employee.getDepartment()); // Department:Department [id=2, name=department2]
		LOGGER.info("End");
	}
	
	// 04-02 Hands on 5 & 6 - Fetching Employee along with Skills
	private static void testGetDepartment() {
		LOGGER.info("Start");
		Department department = departmentService.findDepartmentById(1);
		Set<Employee> employeeList = department.getEmployeeList();
		LOGGER.debug("Department:{}", department); // Department:Department [id=1, name=department1]
		LOGGER.debug("Employees:{}", employeeList); // Employees:[Employee [id=2, name=harry second, salary=3200.54, permanent=false, dateOfBirth=2021-12-12], Employee [id=3, name=harry third, salary=1.00, permanent=true, dateOfBirth=2011-09-09]]
		for(Employee employee: employeeList) 
			LOGGER.debug("Skills:{}", employee.getSkillList()); // Skills:[] (Expected result, I did not add any skills to Employee id = 2), Skills:[Skill [id=1, name=skill1]]
		LOGGER.info("End");
	}
	
	// 04-02 Hands on 6 - Add Skill to Employee
	private static void testAddSkillToEmployee() {
		LOGGER.info("Start");
		Employee employee = employeeService.get(3);
		Skill skill = skillService.findSkillById(1);
		employee.getSkillList().add(skill);
		employeeService.save(employee);
		LOGGER.info("End");
		// View the changes in MySQL -  changes in results is as expected in which Employee Id = 3 had no skills now have skills.
	}
	
	// For my own reference 
	private static void testGetSkill() {
		LOGGER.info("Start");
		Skill skill = skillService.findSkillById(1);
		Employee employee = employeeService.get(1);
		LOGGER.debug("Skill:{}", skill.getName());
		LOGGER.debug("Employees:{}", employee.getSkillList().toString());
		LOGGER.info("End");
	}
	
	// 04-03 Hands on 2 - Include a new test method and invoke the service method in OrmLearnApplication.java
	private static void testGetAllPermanentEmployees() {
		LOGGER.info("Start");
		List<Employee> employees = employeeService.getAllPermanentEmployees();
		LOGGER.debug("Permanent Employees:{}", employees); // Permanent Employees:[Employee [id=1, name=harry potter, salary=4000.00, permanent=true, dateOfBirth=2021-01-01], Employee [id=3, name=harry third, salary=1.00, permanent=true, dateOfBirth=2011-09-09]]
		
		employees.forEach(e -> LOGGER.debug("Skills:{}", e.getSkillList()));
		/* Skills:[Skill [id=1, name=skill1]]
		 * Skills:[Skill [id=1, name=skill1]]
		 */
		
		LOGGER.info("End");
	}
	
	// 04-03 Hands on 3 - Include test method in OrmLearnApplication
	private static void testGetAverageSalary() {
		LOGGER.info("Start");
		double averageSalary = employeeService.getAverageSalary(1); 
		LOGGER.debug("averageSalary:{}", averageSalary); // averageSalary:1600.77
		LOGGER.info("End");
	}
	
	// 04-03 Hands on 5
	private static void testGetAllEmployeesNative() {
		LOGGER.info("Start");
		List<Employee> employeeList = employeeService.getAllEmployeesNative(); 
		LOGGER.debug("Employees:{}", employeeList); // Employee:[Employee [id=1, name=harry potter, salary=4000.00, permanent=true, dateOfBirth=2021-01-01], Employee [id=2, name=harry second, salary=3200.54, permanent=false, dateOfBirth=2021-12-12], Employee [id=3, name=harry third, salary=1.00, permanent=true, dateOfBirth=2011-09-09]]
		LOGGER.info("End");
	}
}
