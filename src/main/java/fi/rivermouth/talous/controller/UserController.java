package fi.rivermouth.talous.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.model.AbstractResponse;
import fi.rivermouth.talous.model.Response;
import fi.rivermouth.talous.service.BaseService;
import fi.rivermouth.talous.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User, Long> {
	
	private static final String 
	USER_NOT_FOUND_WITH_EMAIL_S = "User not found with email %s.";

	@Autowired
	private UserService<User, Long> userService;
	
	@RequestMapping(value = "/{email}", method = RequestMethod.GET)
	public Response getByEmail(@PathVariable String email) {
		return conditionalResponse(
				new Response(HttpStatus.OK, userService.getByEmail(email)),
				ifNull(new Response(HttpStatus.NO_CONTENT, 
					new Response.ErrorMessage(USER_NOT_FOUND_WITH_EMAIL_S, email))));
	}

	@Override
	public UserService<User, Long> getService() {
		return userService;
	}
	
}
