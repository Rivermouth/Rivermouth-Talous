package fi.rivermouth.talous.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.talous.domain.Client;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.service.BaseService;
import fi.rivermouth.talous.service.ClientService;
import fi.rivermouth.talous.service.UserService;

@RestController
@RequestMapping("/clients")
public class ClientController extends ChildCRUDController<User, Long, Client, Long> {

	@Autowired
	ClientService clientService;
	
	@Autowired
	UserService userService;

	@Override
	public BaseService<User, Long> getParentService() {
		return userService;
	}

	@Override
	public void setEntityParent(Client entity, User parent) {
		entity.setOwner(parent);
	}

	@Override
	public void addEntityToParent(Client entity, User parent) {
		parent.getClients().add(entity);
	}

	@Override
	public User getEntityParent(Client entity) {
		return entity.getOwner();
	}

	@Override
	public List<Client> listByParent(User parent) {
		return clientService.getByOwner(parent);
	}

	@Override
	public BaseService<Client, Long> getService() {
		return clientService;
	}

	@Override
	public void removeEntityFromParent(Client entity, User parent) {
		parent.getClients().remove(entity);
	}
	
}
