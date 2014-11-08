package fi.rivermouth.talous.controller;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.talous.domain.BaseEntity;
import fi.rivermouth.talous.model.Response;

@RestController
public abstract class ChildCRUDController
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable, 	// Parent
T extends BaseEntity<ID>, ID extends Serializable> 						// Child (this)
extends CRUDController<T, ID> implements ChildCRUDControllerInterface<PARENT, PARENT_ID, T, ID> {
	
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
	@RequestMapping(value = "/{parentId}", method = RequestMethod.PUT)
	public Response create(@PathVariable PARENT_ID parentId, @RequestBody T entity) {
		if (!getParentService().exists(parentId)) return parentNotFoundWithIdResponse(parentId);
		PARENT parent = getParentService().get(parentId);
		setEntityParent(entity, parent);
		addEntityToParent(entity, parent);
		getParentService().update(parent);
		return super.create(entity);
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
	@RequestMapping(value = "/{parentId}/{id}", method = RequestMethod.POST)
	public Response update(@PathVariable PARENT_ID parentId, @PathVariable ID id, @RequestBody T entity) {
		PARENT parent = getEntityParent(entity);
		if (parent == null || parent.getId() == null || parent.getId() != parentId) {
			return parentMismatchResponse(parent, parentId);
		}
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
	@RequestMapping(value = "/{parentId}/{id}", method = RequestMethod.GET)
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
	@RequestMapping(value = "/{parentId}", method = RequestMethod.GET)
	public Response list(@PathVariable PARENT_ID parentId) {
		if (!getParentService().exists(parentId)) return parentNotFoundWithIdResponse(parentId);
		return listResponse(listByParent(getParentService().get(parentId)));
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
	@RequestMapping(value = "/{parentId}/{id}", method = RequestMethod.DELETE)
	public Response delete(@PathVariable PARENT_ID parentId, @PathVariable ID id) {
		if (!getParentService().exists(parentId)) return parentNotFoundWithIdResponse(parentId);
		if (!getService().exists(id)) return notFoundWithIdResponse(id);
		T entity = getService().get(id);
		PARENT parent = getParentService().get(parentId);
		removeEntityFromParent(entity, parent);
		return super.delete(id);
	}
	
}
