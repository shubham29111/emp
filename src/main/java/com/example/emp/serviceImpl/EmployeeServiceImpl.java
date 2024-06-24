package com.example.emp.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.emp.exception.ResourceAlreadyExistsException;
import com.example.emp.exception.ResourceNotFoundException;
import com.example.emp.model.Employee;
import com.example.emp.repository.EmployeeRepository;
import com.example.emp.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


	
	@Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

	   @Override
	    public Employee createEmployee(Employee employee) {
	        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employee.getEmail());
	        if (existingEmployee.isPresent()) {
	            throw new ResourceAlreadyExistsException("Employee with email " + employee.getEmail() + " already exists");
	        }
	        return employeeRepository.save(employee);
	    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " does not exist"));
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " does not exist"));

        employee.setFname(employeeDetails.getFname());
        employee.setLname(employeeDetails.getLname());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setDesignation(employeeDetails.getDesignation());
        employee.setJoiningDate(employeeDetails.getJoiningDate());
        employee.setSalary(employeeDetails.getSalary());

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " does not exist"));
        employeeRepository.delete(employee);
    }
}
