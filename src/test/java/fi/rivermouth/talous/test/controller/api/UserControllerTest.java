package fi.rivermouth.talous.test.controller.api;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import fi.rivermouth.talous.domain.Company;
import fi.rivermouth.talous.domain.File;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.model.Address;
import fi.rivermouth.talous.service.UserService;
import fi.rivermouth.talous.test.controller.BaseControllerTest;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends BaseControllerTest<User, Long> {
	
	@Autowired
	UserService userService;
	
	private String email = RandomStringUtils.random(6).toString() + "@rivermouth.fi";
	
	@Override
	public User getRandomEntity() {
		User user = new User();
		user.setName(new User.Name(RandomStringUtils.random(6).toString(), RandomStringUtils.random(14).toString()));
		user.setEmail(email);
		user.setPassword("5gY7jMn9H");
		user.setCompany(new Company("Rivermouth Ltd", RandomStringUtils.random(7).toString(),
				new Address("Rautalammintie 3 C 710", "00550", "Helsinki", "Finland")));
		
		return user;
	}

	@Override
	public User getTotallyRandomEntity() {
		User user = getRandomEntity();
		user.setEmail(RandomStringUtils.random(6).toString() + "@rivermouth.fi");
		return user;
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
		return "/api/users";
	}

	@Override
	public UserService getService() {
		return userService;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testGetUserByEmail() throws Exception {
		User user = userService.create(getRandomEntity());
		
		mockMvc.perform(get("/api/users/findBy/email/" + user.getEmail()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.email", is(user.getEmail())));
	}
	
	@Test
	public void testSaveFile() throws Exception {
		User user = userService.create(getRandomEntity());
		
		mockMvc.perform(
				put("/api/users/" + user.getId() + "/files")
				.content(asJsonString(getRandomFile())).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.id").exists());
	}
	
}
