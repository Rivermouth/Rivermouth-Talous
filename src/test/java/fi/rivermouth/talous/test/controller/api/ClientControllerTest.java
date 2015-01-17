package fi.rivermouth.talous.test.controller.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.Client;
import fi.rivermouth.talous.domain.File;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.model.Address;
import fi.rivermouth.talous.service.ClientService;
import fi.rivermouth.talous.service.UserService;
import fi.rivermouth.talous.test.controller.BaseChildControllerTest;

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
	
	private File getRandomFile() {
		File file = new File();
		file.setName(RandomStringUtils.random(8));
		file.setMimeType("text/plain");
		file.setContent(RandomStringUtils.random(287).getBytes());
		return file;
	}

	@Override
	public String getAPIPath() {
		return "/api/users/{parentId}/clients";
	}

	@Override
	public BaseService<Client, Long> getService() {
		return clientService;
	}
	
	@Test
	public void testSaveFile() throws Exception {
		Client client = createEntity();
		
		mockMvc.perform(
				put("/api/users/" + parent.getId() + "/clients/" + client.getId() + "/files")
				.content(asJsonString(getRandomFile())).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.id").exists());
	}

}
