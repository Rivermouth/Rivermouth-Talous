package fi.rivermouth.talous.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.controller.CRUDController;
import fi.rivermouth.spring.entity.Response;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.File;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractFileHavingController<User, Long> {
	
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
	public void addFileToParent(File file, User parent) {
		parent.getFiles().add(file);
	}

	@Override
	public void removeFileFromParent(File file, User parent) {
		parent.getFiles().remove(file);
	}

	@Override
	public List<File> listFilesByParentId(Long parentId) {
		return userService.get(parentId).getFiles();
	}

	@Override
	public BaseService<User, Long> getService() {
		return userService;
	}

	@Override
	public String getEntityKind() {
		return "user";
	}
	
}
