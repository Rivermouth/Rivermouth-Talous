package fi.rivermouth.talous.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.entity.Response;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.model.LoginData;
import fi.rivermouth.talous.service.UserService;

@RestController
public class AuthController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Response register(@Valid @RequestBody User user) {
		return new Response(HttpStatus.CREATED, userService.create(user));
	}
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public Response auth(@Valid @RequestBody LoginData loginData) {
		if (userService.authenticate(loginData.getEmail(), loginData.getPassword())) {
			return new Response(HttpStatus.OK, userService.getAuthenticatedUser());
		}
		return new Response(HttpStatus.BAD_REQUEST, new Response.ErrorMessage("Auth failed."));
	}
	
}
