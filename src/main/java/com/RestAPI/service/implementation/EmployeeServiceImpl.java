package com.RestAPI.service.implementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.RestAPI.exception.EmployeeNotFoundException;
import com.RestAPI.exception.NoEmployeesFoundException;
import com.RestAPI.model.Employee;
import com.RestAPI.repository.EmployeeRepository;
import com.RestAPI.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    Logger logger
        = LoggerFactory.getLogger(LogController.class);

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public String createEmployee(Employee employee) {
        try {
            employeeRepository.save(employee);
             logger.info("Employee Added Successfully !!"+" Phone number "+employee.empPhoneNumber);
            return "Employee Added Successfully !!";
        } catch (Exception e) {
            logger.error("Failed to add employee: " + e.getMessage());
            throw new RuntimeException("Failed to add employee: " + e.getMessage());
        }
    }

    @Override
    public String updateEmployee(Employee employee) {
        Integer empId = employee.getEmpId();
        try {
            if (!employeeRepository.existsById(empId)) {
                throw new EmployeeNotFoundException("Employee not found with ID: " + empId);
            }
            employeeRepository.save(employee);
             logger.info("Employee Updated Successfully");
            return "Employee Updated Successfully";
        } catch (Exception e) {
            logger.error("Failed to update employee: " + e.getMessage());
            throw new RuntimeException("Failed to update employee: " + e.getMessage());
        }
    }

    @Override
    public void deleteEmployee(Integer empId) {
        try {
            if (!employeeRepository.existsById(empId)) {
                throw new EmployeeNotFoundException("Employee not found with ID: " + empId);
            }
            employeeRepository.deleteById(empId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete employee: " + e.getMessage());
        }
    }

    @Override
    public Employee getEmployee(Integer empId) {
        try {
            return employeeRepository.findById(empId)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + empId));
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve employee: " + e.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployee() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            if (employees.isEmpty()) {
                throw new NoEmployeesFoundException("No employees found");
            }
            logger.info("Fetched All Employees Successfully !!");
            return employees;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve employees: " + e.getMessage());
        }
    }
}
