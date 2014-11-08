package fi.rivermouth.talous.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.talous.domain.Client;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.model.Response;
import fi.rivermouth.talous.model.Response.ErrorMessage;
import fi.rivermouth.talous.repository.ClientRepository;
import fi.rivermouth.talous.repository.UserRepository;
import fi.rivermouth.talous.service.BaseService;
import fi.rivermouth.talous.service.ClientService;
import fi.rivermouth.talous.service.UserService;

@RestController
@RequestMapping("/clients")
public class ClientController extends CRUDController<Client, Long> {
	
	private User user;
	
	private static final String
	USER_NOT_FOUND_WITH_ID_S = "User not found with id %s.";

	@Autowired
	ClientService clientService;
	
	@Autowired
	UserService userService;
	
	private Response userFoundConditional(Long userId, Response response) {
		user = userService.get(userId);
		return conditionalResponse(
				response, 
				ifTrue(user == null, new Response(HttpStatus.NO_CONTENT, 
						new ErrorMessage(String.format(USER_NOT_FOUND_WITH_ID_S, userId)))));
	}

	@RequestMapping("/{userId}")
	public class ByOwner {
		
		@RequestMapping(method = RequestMethod.GET)
		public Response list(@PathVariable Long userId) {
			Response response = userFoundConditional(userId, null);
			if (response != null) return response;
			
			return new Response(HttpStatus.OK, new Client().getKind(), clientService.getByOwner(user));
		}
		
		@RequestMapping(method = RequestMethod.POST)
		public Response create(@PathVariable Long userId, @RequestBody Client client) {
			Response response = userFoundConditional(userId, null);
			if (response != null) return response;
			
			return userFoundConditional(userId, conditionalResponse(
					new Response(HttpStatus.CREATED, clientService.save(client)),
					ifNullAlreadyExistsCondition(client.getKind())));
		}
		
	}

	@Override
	public ClientService getService() {
		return clientService;
	}
	
}
