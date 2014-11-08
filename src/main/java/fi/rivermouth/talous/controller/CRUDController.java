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
import fi.rivermouth.talous.model.Response.Message;

@RestController
public abstract class CRUDController<T extends BaseEntity<ID>, ID extends Serializable> implements CRUDControllerInterface<T, ID> {
	
	protected static final String 
	S_ALREADY_EXISTS = "%s already exists.",
	S_NOT_FOUND = "%s not found.",
	S_NOT_FOUND_WITH_ID_S = "%s not found with ID %s.",
	S_WITH_ID_S_DELETED = "%s with ID %s deleted.",
	ID_S_DOES_NOT_MATCH_WITH_ID_S = "ID %s does not match with ID %s",
	FAILED_TO_UPDATE_ENTITY_WITH_ID_S = "Failed to update entity with ID %s.";
	
	protected Response defaultResponse;

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

	protected Response notFoundWithIdResponse(String kind, ID id) {
		return new Response(HttpStatus.NOT_FOUND, new Response.ErrorMessage(S_NOT_FOUND_WITH_ID_S, kind.toUpperCase(), id));
	}
	
	protected Response notFoundWithIdResponse(ID id) {
		return notFoundWithIdResponse("entity", id);
	}
	
	protected Response notFoundWithIdResponse(T entity) {
		return notFoundWithIdResponse(entity.getKind(), entity.getId());
	}
	
	protected Condition ifNullAlreadyExistsCondition(String kind) {
		return ifNull(new Response(HttpStatus.CONFLICT, 
				new Response.ErrorMessage(S_ALREADY_EXISTS, kind.toUpperCase())));
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

	/**
	 * PUT: /
	 * success: {@value HttpStatus#CREATED}
	 * error  : {@value HttpStatus#CONFLICT}
	 * 
	 * Create entity
	 * @param entity
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Response create(@RequestBody T entity) {
		return conditionalResponse(
				new Response(HttpStatus.CREATED, getService().create(entity)), 
				ifNullAlreadyExistsCondition(entity.getKind()));
	}
	
	/**
	 * POST: /{id}
	 * success: {@value HttpStatus#OK}
	 * error  : {@value HttpStatus#NOT_MODIFIED}
	 *   id does not match: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * Update entity
	 * @param id
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public Response update(@PathVariable ID id, @RequestBody T entity) {
		if (entity.getId() == null) {
			entity.setId(id);
		}
		else if (entity.getId() != id) {
			return new Response(HttpStatus.BAD_REQUEST, 
					new Response.Message(ID_S_DOES_NOT_MATCH_WITH_ID_S, entity.getId(), id));
		}
		return conditionalResponse(
				new Response(HttpStatus.OK, getService().update(entity)), 
				ifNull(new Response(HttpStatus.NOT_MODIFIED, new Response.Message(FAILED_TO_UPDATE_ENTITY_WITH_ID_S, id))));
	}
	
	/**
	 * GET: /{id}
	 * success: {@value HttpStatus#OK}
	 * error  : {@value HttpStatus#NOT_FOUND}
	 * 
	 * Get entity
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response get(@PathVariable ID id) {
		return conditionalResponse(
				new Response(HttpStatus.OK, getService().get(id)),
				ifNull(notFoundWithIdResponse(id)));
	}
	
	/**
	 * GET: /
	 * success: {@value HttpStatus#OK}
	 * 
	 * List entities
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Response list() {
		List<T> entities = getService().list();
		return new Response(HttpStatus.OK, new User().getKind(), entities);
	}
	
	/**
	 * DELETE: /{id}
	 * success: {@value HttpStatus#OK}
	 * error  : {@value HttpStatus#NOT_FOUND}
	 * 
	 * Delete entity
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response delete(@PathVariable ID id) {
		boolean isDeleted = getService().delete(id);
		return conditionalResponse(
				new Response(HttpStatus.OK, new Response.Message(S_WITH_ID_S_DELETED, "entity", id)), 
				ifTrue(!isDeleted, notFoundWithIdResponse(id)));
	}
	
}
