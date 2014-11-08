package fi.rivermouth.talous.test.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.model.Address;
import fi.rivermouth.talous.service.UserService;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends BaseControllerTest<User, Long> {
	
	@Autowired
	UserService userService;
	
	private String email = RandomStringUtils.random(6) + "@rivermouth.fi";
	
	@Override
	public User getRandomEntity() {
		User user = new User();
		user.setName(new User.Name(RandomStringUtils.random(6), RandomStringUtils.random(14)));
		user.setEmail(email);
		user.setCompany(new User.Company("Rivermouth Ltd", RandomStringUtils.random(7),
				new Address("Rautalammintie 3 C 710", "00550", "Helsinki", "Finland")));
		
		return user;
	}

	@Override
	public User getTotallyRandomEntity() {
		User user = getRandomEntity();
		user.setEmail(RandomStringUtils.random(6) + "@rivermouth.fi");
		return user;
	}
	
	@Override
	public String getAPIPath() {
		return "/users";
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testGetUserByEmail() throws Exception {
		User user = userService.create(getRandomEntity());
		
		mockMvc.perform(get("/users/findBy/email/{email}", user.getEmail()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.email", is(user.getEmail())));
	}

	@Override
	public UserService getService() {
		return userService;
	}
	
}
