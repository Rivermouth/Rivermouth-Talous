package fi.rivermouth.talous.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.rivermouth.talous.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
