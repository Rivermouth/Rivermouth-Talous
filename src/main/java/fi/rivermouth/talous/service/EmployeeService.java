package fi.rivermouth.talous.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fi.rivermouth.talous.domain.Employee;
import fi.rivermouth.talous.repository.EmployeeRepository;

@Service
public class EmployeeService extends BaseService<Employee> {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public JpaRepository<Employee, Long> getRepository() {
		return employeeRepository;
	}

}
