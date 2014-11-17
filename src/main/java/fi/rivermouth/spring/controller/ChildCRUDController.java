package fi.rivermouth.spring.controller;

import java.io.Serializable;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.entity.Response;

/**
 * 
 * @author Karri Rasinmäki
 * 
 * 
 *
 * @param <PARENT> extends {@code BaseEntity<PARENT_ID>} 
 * @param <PARENT_ID> extends {@code Serializable}
 * @param <T> extends {@code BaseEntity<PARENT_ID>} 
 * @param <ID> extends {@code Serializable}
 */
@RestController
public abstract class ChildCRUDController
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable, 	// Parent
T extends BaseEntity<ID>, ID extends Serializable> 						// Child (this)
extends BaseController<T, ID> implements ChildCRUDControllerInterface<PARENT, PARENT_ID, T, ID> {
	
	protected static final String 
	PARENT_NOT_FOUND_WITH_ID_S = "Parent not found with id %s.";
	
	protected Response parentNotFoundWithIdResponse(PARENT_ID parentId) {
		return new Response(HttpStatus.BAD_REQUEST, new Response.ErrorMessage(PARENT_NOT_FOUND_WITH_ID_S, parentId));
	}
	
	protected Response parentMismatchResponse(PARENT parent, PARENT_ID parentId) {
		PARENT_ID requestedParentId = null;
		if (parent != null) requestedParentId = parent.getId();
		return new Response(HttpStatus.BAD_REQUEST, new Response.ErrorMessage("Parent " + ID_S_DOES_NOT_MATCH_WITH_ID_S, requestedParentId, parentId));
	}
	
	@Transactional
	private void joinEntityAndParent(PARENT_ID parentId, T entity) {
		PARENT parent = getParentService().get(parentId);
		addEntityToParent(entity, parent);
		getParentService().update(parent);
		getParentService().getRepository().flush();
	}
	
	@Transactional
	private Response seperateEntityAndParent(PARENT_ID parentId, ID id) {
		T entity = getService().get(id);
		PARENT parent = getParentService().get(parentId);
		removeEntityFromParent(entity, parent);
		Response response = super.delete(id);
		getParentService().getRepository().flush();
		return response;
	}
		
	/**
	 * PUT: /{parentId}
	 * success: {@value HttpStatus#CREATED}
	 * error  : {@value HttpStatus#CONFLICT}
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * Create entity
	 * @param entity
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST}, consumes = "application/json")
	public Response createWjson(@PathVariable PARENT_ID parentId, @Valid @RequestBody T entity) {
		if (!getParentService().exists(parentId)) return parentNotFoundWithIdResponse(parentId);
		Response response = super.create(entity);
		joinEntityAndParent(parentId, entity);
		return response;
	}
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST}, consumes = "application/x-www-form-urlencoded")
	public Response create(@PathVariable PARENT_ID parentId, @Valid @ModelAttribute T entity) {
		if (!getParentService().exists(parentId)) return parentNotFoundWithIdResponse(parentId);
		Response response = super.create(entity);
		joinEntityAndParent(parentId, entity);
		return response;
	}
	
	/**
	 * POST: /{parentId}/{id}
	 * success: {@value HttpStatus#OK}
	 * error  : {@value HttpStatus#NOT_MODIFIED}
	 *   parent mismatch  : {@value HttpStatus#BAD_REQUEST}
	 *   parent not found : {@value HttpStatus#BAD_REQUEST}
	 *   id does not match: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * Update entity
	 * @param id
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = "application/json")
	public Response updateWjson(@PathVariable PARENT_ID parentId, @PathVariable ID id, @Valid @RequestBody T entity) {
		if (!getParentService().exists(parentId)) return parentNotFoundWithIdResponse(parentId);
		return super.update(id, entity);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public Response update(@PathVariable PARENT_ID parentId, @PathVariable ID id, @Valid @ModelAttribute T entity) {
		if (!getParentService().exists(parentId)) return parentNotFoundWithIdResponse(parentId);
		return super.update(id, entity);
	}
	
	/**
	 * GET: /{parentId}/{id}
	 * success: {@value HttpStatus#OK}
	 * error  : {@value HttpStatus#NOT_FOUND}
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * Get entity
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response get(@PathVariable PARENT_ID parentId, @PathVariable ID id) {
		if (!getParentService().exists(parentId)) return parentNotFoundWithIdResponse(parentId);
		return super.get(id);
	}
	
	/**
	 * GET: /{parentId}
	 * success: {@value HttpStatus#OK}
	 * error  :
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * List entities
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Response list(@PathVariable PARENT_ID parentId) {
		if (!getParentService().exists(parentId)) return parentNotFoundWithIdResponse(parentId);
		return listResponse(listByParentId(parentId));
	}
	
	/**
	 * DELETE: /{parentId}/{id}
	 * success: {@value HttpStatus#OK}
	 * error  : {@value HttpStatus#NOT_FOUND}
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * Delete entity
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response delete(@PathVariable PARENT_ID parentId, @PathVariable ID id) {
		if (!getParentService().exists(parentId)) return parentNotFoundWithIdResponse(parentId);
		if (!getService().exists(id)) return notFoundWithIdResponse(id);
		return seperateEntityAndParent(parentId, id);
	}
	
}
