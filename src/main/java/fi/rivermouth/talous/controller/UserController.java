package fi.rivermouth.talous.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.controller.CRUDController;
import fi.rivermouth.spring.entity.Response;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends CRUDController<User, Long> {
	
	private static final String 
	USER_NOT_FOUND_WITH_EMAIL_S = "User not found with email %s.";

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/findBy/email/{email:.+}", method = RequestMethod.GET)
	public Response findByEmail(@PathVariable String email) {
		return conditionalResponse(
				new Response(HttpStatus.OK, userService.getByEmail(email)),
				ifNull(new Response(HttpStatus.NO_CONTENT, 
					new Response.ErrorMessage(USER_NOT_FOUND_WITH_EMAIL_S, email))));
	}

	@Override
	public UserService getService() {
		return userService;
	}

	@Override
	public String getEntityKind() {
		return "user";
	}
	
}
