package fi.rivermouth.talous.controller.api;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.controller.CRUDController;
import fi.rivermouth.spring.controller.Method;
import fi.rivermouth.spring.entity.Response;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController extends CRUDController<User, Long> {
	
	private static final String 
	USER_NOT_FOUND_WITH_EMAIL_S = "User not found with email %s.";
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/findBy/email/{email:.+}", method = RequestMethod.GET)
	public Response findByEmail(@PathVariable("email") String email) {
		return conditionalResponse(
				new Response(HttpStatus.OK, userService.getByEmail(email)),
				ifNull(new Response(HttpStatus.NO_CONTENT, 
					new Response.ErrorMessage(USER_NOT_FOUND_WITH_EMAIL_S, email))));
	}
	
	@Override
	public BaseService<User, Long> getService() {
		return userService;
	}

	@Override
	public String getEntityKind() {
		return "user";
	}
	
	@Override
	protected <S extends Serializable> boolean isAuthorized(Method method,
			Long id, S ownerId) {
		return userService.isAuthenticatedUserId(id);
	}
	
}
