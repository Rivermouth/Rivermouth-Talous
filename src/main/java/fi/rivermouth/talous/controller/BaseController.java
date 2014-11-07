package fi.rivermouth.talous.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.talous.domain.BaseEntity;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.model.Responsable;
import fi.rivermouth.talous.model.Response;

@Component
public abstract class BaseController<T extends BaseEntity, ID extends Serializable> implements CRUDControllerInterface<T, ID> {
	
	protected static final String 
	S_ALREADY_EXISTS = "%s already exists.",
	S_NOT_FOUND = "%s not found.",
	S_NOT_FOUND_WITH_ID_S = "%s not found with ID %s.",
	S_WITH_ID_S_DELETED = "%s with id %s deleted.";
	
	private Response defaultResponse;

	protected Response conditionalResponse(Response response, Condition condition) {
		defaultResponse = response;
		return condition.test(response);
	}
	
	protected Condition ifNull(Response response) {
		return new Condition(Condition.Type.NULL, response);
	}
	
	protected Condition ifTrue(Boolean bool, Response response) {
		return new Condition(Condition.Type.IF_TRUE, bool, response);
	}
	
	protected Response defaultResponse() {
		return defaultResponse;
	}
	
	protected Response notFoundWithIdResponse(T entity) {
		return new Response(HttpStatus.NOT_FOUND, new Response.ErrorMessage(S_NOT_FOUND_WITH_ID_S, entity.getKind().toUpperCase(), entity.getId()));
	}
	
	protected Response notFoundResponse(String kind) {
		return new Response(HttpStatus.NOT_FOUND, new Response.ErrorMessage(S_NOT_FOUND, kind.toUpperCase()));
	}
	
	protected Condition ifNullAlreadyExistsCondition(String kind) {
		return ifNull(new Response(HttpStatus.CONFLICT, 
				new Response.ErrorMessage(S_ALREADY_EXISTS, kind.toUpperCase())));
	}
	
	protected Condition ifListEmptyNotFoundCondition(List<?> list, String kind) {
		return ifTrue(list == null || list.size() == 0, notFoundResponse(kind));
	}
	
	private static class Condition {

		public static enum Type {
			NULL,
			IF_TRUE
		}
		
		private Type type;
		private Boolean bool;
		private Response response;
		
		public Condition(Type type, Response response) {
			this.type = type;
			this.response = response;
		}
		
		public Condition(Type type, Boolean bool, Response response) {
			this.type = type;
			this.response = response;
		}
		
		public Response test(Response defaultResponse) {
			switch (type) {
			case NULL:
				if (defaultResponse.getBody().getData() == null) return response;
				break;
			case IF_TRUE:
				if (bool != null && bool) return response;
				break;
			}
			return defaultResponse;
		}
		
	}

	@RequestMapping(method = RequestMethod.POST)
	public Response save(@RequestBody T entity) {
		return conditionalResponse(
				new Response(HttpStatus.CREATED, getService().save(entity)), 
				ifNullAlreadyExistsCondition(entity.getKind()));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response get(@PathVariable ID id) {
		return conditionalResponse(
				new Response(HttpStatus.OK, getService().get(id)),
				ifNull(new Response(HttpStatus.NO_CONTENT, 
					new Response.ErrorMessage(S_NOT_FOUND_WITH_ID_S, "entity", id))));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Response list() {
		List<T> entities = getService().list();
		return conditionalResponse(
				new Response(HttpStatus.OK, new User().getKind(), entities), 
				ifListEmptyNotFoundCondition(entities, "entities"));
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public Response delete(@RequestBody T entity) {
		boolean isDeleted = getService().delete(entity);
		return conditionalResponse(
				new Response(HttpStatus.OK, new Response.Message(S_WITH_ID_S_DELETED, "entity", entity.getId())), 
				ifTrue(!isDeleted, notFoundWithIdResponse(entity)));
	}
	
}
