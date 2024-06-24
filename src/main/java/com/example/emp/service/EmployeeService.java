package com.example.emp.service;

import java.util.List;

import com.example.emp.model.Employee;

public interface EmployeeService {
	List<Employee> getAllEmployees();

	Employee createEmployee(Employee employee);

	Employee getEmployeeById(Long id);

	Employee updateEmployee(Long id, Employee employeeDetails);

	void deleteEmployee(Long id);
}