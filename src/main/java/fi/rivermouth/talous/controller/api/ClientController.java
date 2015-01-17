package fi.rivermouth.talous.controller.api;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.controller.ChildCRUDController;
import fi.rivermouth.spring.controller.Method;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.Client;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.service.ClientService;
import fi.rivermouth.talous.service.UserService;

@RestController
@RequestMapping("/api/users/{parentId}/clients")
public class ClientController extends ChildCRUDController<User, Long, Client, Long> {

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private UserService userService;

	@Override
	public BaseService<User, Long> getParentService() {
		return userService;
	}

	@Override
	public void addEntityToParent(Client entity, User parent) {
		parent.getClients().add(entity);
	}

	@Override
	public List<Client> listByParentId(Long parentId) {
		return userService.get(parentId).getClients();
	}

	@Override
	public BaseService<Client, Long> getService() {
		return clientService;
	}

	@Override
	public void removeEntityFromParent(Client entity, User parent) {
		parent.getClients().remove(entity);
	}

	@Override
	public String getEntityKind() {
		return "client";
	}
	
	@Override
	protected <S extends Serializable> boolean isAuthorized(Method method,
			Long id, S ownerId) {
		return userService.isAuthenticatedUserId((Long) ownerId);
	}
	
}
