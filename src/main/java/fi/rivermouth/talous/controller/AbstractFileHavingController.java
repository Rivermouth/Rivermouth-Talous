package fi.rivermouth.talous.controller;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.controller.CRUDController;
import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.entity.FileHavingEntityInterface;
import fi.rivermouth.spring.entity.Response;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.File;
import fi.rivermouth.talous.service.FileService;

@RestController
public abstract class AbstractFileHavingController<T extends BaseEntity<ID>, ID extends Serializable> 
extends CRUDController<T, ID> implements AbstractFileHavingControllerInterface<T, ID> {
	
	@Autowired
	private FileService fileService;
	
	@Transactional
	private void joinFileAndParent(ID parentId, File entity) {
		T parent = getService().get(parentId);
		addFileToParent(entity, parent);
		getService().update(parent);
		getService().getRepository().flush();
	}
	
	@Transactional
	private boolean seperateFileAndParent(ID parentId, Long id) {
		File entity = fileService.get(id);
		T parent = getService().get(parentId);
		removeFileFromParent(entity, parent);
		Boolean res = fileService.delete(entity);
		getService().getRepository().flush();
		return res;
	}
	
	private Response _createFile(ID parentId, File file) {
		File justCreated = fileService.create(file);
		if (justCreated != null) joinFileAndParent(parentId, file);
		return conditionalResponse(
				new Response(HttpStatus.CREATED, justCreated), 
				ifNullAlreadyExistsCondition(getEntityKind()));
	}
	
	private Response _updateFile(Long id, File file) {
		if (file.getId() == null) {
			file.setId(id);
		}
		else if (file.getId() != id) {
			return new Response(HttpStatus.BAD_REQUEST, 
					new Response.Message(ID_S_DOES_NOT_MATCH_WITH_ID_S, file.getId(), id));
		}
		return conditionalResponse(
				new Response(HttpStatus.OK, fileService.update(file)), 
				ifNull(new Response(HttpStatus.NOT_MODIFIED, new Response.Message(FAILED_TO_UPDATE_ENTITY_WITH_ID_S, id))));
	}
	
	private Response _getFile(Long id) {
		return conditionalResponse(
				new Response(HttpStatus.OK, fileService.get(id)),
				ifNull(notFoundWithIdResponse("file", id)));
	}
	
	private Response _listFiles(ID parentId) {
		List<File> files = listFilesByParentId(parentId);
		return new Response(HttpStatus.OK, "file", files);
	}
	
	private Response _deleteFile(ID parentId, Long id) {
		boolean isDeleted = seperateFileAndParent(parentId, id);
		return conditionalResponse(
				new Response(HttpStatus.OK, new Response.Message(S_WITH_ID_S_DELETED, "file", id)), 
				ifTrue(!isDeleted, notFoundWithIdResponse("file", id)));
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
	@RequestMapping(value = "/{parentId}/files", method = {RequestMethod.PUT, RequestMethod.POST}, consumes = "application/json")
	public Response createWjson(@PathVariable ID parentId, @Valid @RequestBody File file) {
		if (!getService().exists(parentId)) return notFoundWithIdResponse(parentId);
		Response response = _createFile(parentId, file);
		return response;
	}
	@RequestMapping(value = "/{parentId}/files", method = {RequestMethod.PUT, RequestMethod.POST}, consumes = "application/x-www-form-urlencoded")
	public Response create(@PathVariable ID parentId, @Valid @ModelAttribute File file) {
		if (!getService().exists(parentId)) return notFoundWithIdResponse(parentId);
		Response response = _createFile(parentId, file);
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
	@RequestMapping(value = "/{parentId}/files/{id}", method = RequestMethod.POST, consumes = "application/json")
	public Response updateWjson(@PathVariable ID parentId, @PathVariable Long id, @Valid @RequestBody File file) {
		if (!getService().exists(parentId)) return notFoundWithIdResponse(parentId);
		return _updateFile(id, file);
	}
	@RequestMapping(value = "/{parentId}/files/{id}", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public Response update(@PathVariable ID parentId, @PathVariable Long id, @Valid @ModelAttribute File file) {
		if (!getService().exists(parentId)) return notFoundWithIdResponse(parentId);
		return _updateFile(id, file);
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
	@RequestMapping(value = "/{parentId}/files/{id}", method = RequestMethod.GET)
	public Response get(@PathVariable ID parentId, @PathVariable Long id) {
		if (!getService().exists(parentId)) return notFoundWithIdResponse(parentId);
		return _getFile(id);
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
	@RequestMapping(value = "/{parentId}/files", method = RequestMethod.GET)
	public Response list(@PathVariable ID parentId) {
		if (!getService().exists(parentId)) return notFoundWithIdResponse(parentId);
		return _listFiles(parentId);
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
	@RequestMapping(value = "/{parentId}/files/{id}", method = RequestMethod.DELETE)
	public Response delete(@PathVariable ID parentId, @PathVariable Long id) {
		if (!getService().exists(parentId)) return notFoundWithIdResponse(parentId);
		if (!fileService.exists(id)) return notFoundWithIdResponse("file", id);
		return _deleteFile(parentId, id);
	}
	
}
