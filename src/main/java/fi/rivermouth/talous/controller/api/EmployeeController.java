package fi.rivermouth.talous.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.controller.ChildCRUDController;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.Employee;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.service.EmployeeService;
import fi.rivermouth.talous.service.UserService;

@RestController
@RequestMapping("/api/users/{parentId}/employees")
public class EmployeeController extends ChildCRUDController<User, Long, Employee, Long> {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private UserService userService;

	@Override
	public BaseService<User, Long> getParentService() {
		return userService;
	}

	@Override
	public void addEntityToParent(Employee entity, User parent) {
		parent.getEmployees().add(entity);
	}

	@Override
	public void removeEntityFromParent(Employee entity, User parent) {
		parent.getEmployees().remove(entity);
	}

	@Override
	public List<Employee> listByParentId(Long parentId) {
		return userService.get(parentId).getEmployees();
	}

	@Override
	public BaseService<Employee, Long> getService() {
		return employeeService;
	}

	@Override
	public String getEntityKind() {
		return "employee";
	}

}
