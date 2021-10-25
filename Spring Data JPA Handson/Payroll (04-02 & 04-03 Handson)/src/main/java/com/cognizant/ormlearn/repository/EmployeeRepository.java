package com.cognizant.ormlearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	// 04-03 Hands on 2 - Include a new method definition in EmployeeRepsoitory with @Query annotation
	@Query(value="SELECT e FROM Employee e WHERE e.permanent = 1")
	List<Employee> getAllPermanentEmployees();
	
	// 04-03 Hands on 4 - Get average salary using HQL(Hibernate Query Language)
	@Query(value="SELECT AVG(e.salary) FROM Employee e where e.department.id = :id")
	double getAverageSalary(@Param("id") int id);
	
	// 04-03 Hands on 5 - Get all employees using Native Query
	@Query(value="SELECT * FROM employee", nativeQuery = true) // Native query refers to actual sql queries. These queries are the sql statements which can be directly executed in database.
	List<Employee> getAllEmployeesNative();
}
