package fi.rivermouth.talous.test.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.Client;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.model.Address;
import fi.rivermouth.talous.service.ClientService;
import fi.rivermouth.talous.service.UserService;

public class ClientControllerTest extends BaseChildControllerTest<User, Long, Client, Long> {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ClientService clientService;
	
	private String vat = "VAT" + RandomStringUtils.random(7);

	@Override
	public User getRandomParent() {
		return new UserControllerTest().getRandomEntity();
	}

	@Override
	public User getTotallyRandomParent() {
		return new UserControllerTest().getTotallyRandomEntity();
	}

	@Override
	public BaseService<User, Long> getParentService() {
		return userService;
	}

	@Override
	public Client getRandomEntity() {
		Client client = new Client("Vividin Oy", vat, 
				new Address(RandomStringUtils.random(8), RandomStringUtils.randomNumeric(5), RandomStringUtils.random(8), RandomStringUtils.random(8)));
		
		return client;
	}

	@Override
	public Client getTotallyRandomEntity() {
		Client client = new Client(RandomStringUtils.random(7), "VAT" + RandomStringUtils.random(7), 
				new Address(RandomStringUtils.random(8), RandomStringUtils.randomNumeric(5), RandomStringUtils.random(8), RandomStringUtils.random(8)));
		
		return client;
	}

	@Override
	public String getAPIPath() {
		return "/users/{parentId}/clients";
	}

	@Override
	public BaseService<Client, Long> getService() {
		return clientService;
	}

//	@Override
//	public void setEntityParent(Client entity, User parent) {
//		entity.setParentId(parent.getId());
//	}

}
