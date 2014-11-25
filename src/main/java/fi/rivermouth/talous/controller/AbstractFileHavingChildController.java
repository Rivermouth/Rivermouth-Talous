package fi.rivermouth.talous.controller;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.controller.CRUDController;
import fi.rivermouth.spring.controller.ChildCRUDController;
import fi.rivermouth.spring.controller.ChildCRUDControllerInterface;
import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.entity.FileHavingEntityInterface;
import fi.rivermouth.spring.entity.Response;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.File;

@RestController
public abstract class AbstractFileHavingChildController
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable,
T extends BaseEntity<ID>, ID extends Serializable> 
extends BaseFileHavingController<T, ID> implements ChildCRUDControllerInterface<PARENT, PARENT_ID, T, ID>  {

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
	
	private Response checkExists(PARENT_ID parentId, ID entityId) {
		if (!getParentService().exists(parentId)) return parentNotFoundWithIdResponse(parentId);
		if (!getService().exists(entityId)) return notFoundWithIdResponse(entityId);
		return null;
	}

	/**
	 * PUT: /{entityId}/files
	 * success: {@value HttpStatus#CREATED}
	 * error  : {@value HttpStatus#CONFLICT}
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * Create entity
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/{entityId}/files", method = {RequestMethod.PUT, RequestMethod.POST}, consumes = "application/json")
	public Response createWjson(@PathVariable PARENT_ID parentId, @PathVariable ID entityId, @Valid @RequestBody File file) {
		Response err = checkExists(parentId, entityId);
		if (err != null) return err;
		Response response = _createFile(entityId, file);
		return response;
	}
	@RequestMapping(value = "/{entityId}/files", method = {RequestMethod.PUT, RequestMethod.POST}, consumes = "application/x-www-form-urlencoded")
	public Response create(@PathVariable PARENT_ID parentId, @PathVariable ID entityId, @Valid @ModelAttribute File file) {
		Response err = checkExists(parentId, entityId);
		if (err != null) return err;
		Response response = _createFile(entityId, file);
		return response;
	}
	
	/**
	 * POST: /{entityId}/files/{id}
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
	@RequestMapping(value = "/{entityId}/files/{id}", method = RequestMethod.POST, consumes = "application/json")
	public Response updateWjson(@PathVariable PARENT_ID parentId, @PathVariable ID entityId, @PathVariable Long id, @Valid @RequestBody File file) {
		Response err = checkExists(parentId, entityId);
		if (err != null) return err;
		return _updateFile(id, file);
	}
	@RequestMapping(value = "/{entityId}/files/{id}", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public Response update(@PathVariable PARENT_ID parentId, @PathVariable ID entityId, @PathVariable Long id, @Valid @ModelAttribute File file) {
		Response err = checkExists(parentId, entityId);
		if (err != null) return err;
		return _updateFile(id, file);
	}
	
	/**
	 * GET: /{entityId}/files/{id}
	 * success: {@value HttpStatus#OK}
	 * error  : {@value HttpStatus#NOT_FOUND}
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * Get entity
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{entityId}/files/{id}", method = RequestMethod.GET)
	public Response get(@PathVariable PARENT_ID parentId, @PathVariable ID entityId, @PathVariable Long id) {
		Response err = checkExists(parentId, entityId);
		if (err != null) return err;
		return _getFile(id);
	}
	
	/**
	 * GET: /{entityId}/files
	 * success: {@value HttpStatus#OK}
	 * error  :
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * List entities
	 * @return
	 */
	@RequestMapping(value = "/{entityId}/files", method = RequestMethod.GET)
	public Response list(@PathVariable PARENT_ID parentId, @PathVariable ID entityId) {
		Response err = checkExists(parentId, entityId);
		if (err != null) return err;
		return _listFiles(entityId);
	}
	
	/**
	 * DELETE: /{entityId}/files/{id}
	 * success: {@value HttpStatus#OK}
	 * error  : {@value HttpStatus#NOT_FOUND}
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * Delete entity
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/{entityId}/files/{id}", method = RequestMethod.DELETE)
	public Response delete(@PathVariable PARENT_ID parentId, @PathVariable ID entityId, @PathVariable Long id) {
		Response err = checkExists(parentId, entityId);
		if (err != null) return err;
		if (!fileService.exists(id)) return notFoundWithIdResponse("file", id);
		return _deleteFile(entityId, id);
	}
	
}
