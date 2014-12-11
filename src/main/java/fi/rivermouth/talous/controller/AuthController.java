package fi.rivermouth.talous.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.entity.Response;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.model.LoginData;
import fi.rivermouth.talous.service.UserService;

@RestController
@RequestMapping(value = "/api")
public class AuthController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
	public Response registerWjson(@Valid @RequestBody User user) {
		return new Response(HttpStatus.CREATED, userService.create(user));
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public Response register(@Valid @ModelAttribute User user) {
		return new Response(HttpStatus.CREATED, userService.create(user));
	}
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST, consumes = "application/json")
	public Response authWjson(@Valid @RequestBody LoginData loginData) {
		if (userService.authenticate(loginData.getEmail(), loginData.getPassword())) {
			return new Response(HttpStatus.OK, userService.getAuthenticatedUser());
		}
		return new Response(HttpStatus.BAD_REQUEST, new Response.ErrorMessage("Auth failed."));
	}
	@RequestMapping(value = "/auth", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public Response auth(@Valid @ModelAttribute LoginData loginData) {
		if (userService.authenticate(loginData.getEmail(), loginData.getPassword())) {
			return new Response(HttpStatus.OK, userService.getAuthenticatedUser());
		}
		return new Response(HttpStatus.BAD_REQUEST, new Response.ErrorMessage("Auth failed."));
	}
	
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public Response getAuthUser() {
		User user = userService.getAuthenticatedUser();
		if (user != null) {
			return new Response(HttpStatus.OK, user);
		}
		return new Response(HttpStatus.BAD_REQUEST, new Response.ErrorMessage("No auhenticated user found."));
	}
	
}
