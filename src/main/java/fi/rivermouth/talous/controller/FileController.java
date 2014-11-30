package fi.rivermouth.talous.controller;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.controller.BaseController;
import fi.rivermouth.spring.controller.CRUDController;
import fi.rivermouth.spring.controller.Method;
import fi.rivermouth.spring.entity.Response;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.File;
import fi.rivermouth.talous.service.FileService;
import fi.rivermouth.talous.service.UserService;

@RestController
@RequestMapping("/files/{ownerId}")
public class FileController extends BaseController<File, Long> {

	protected static final String PARENT_NOT_FOUND_WITH_ID_S = "Parent not found with id %s.";

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	protected Response parentNotFoundWithIdResponse(Long ownerId) {
		return new Response(HttpStatus.BAD_REQUEST, new Response.ErrorMessage(PARENT_NOT_FOUND_WITH_ID_S, ownerId));
	}

	/**
	 * PUT: /{ownerId}/{collection}/{parentId}
	 * success: {@value HttpStatus#CREATED}
	 * error  : {@value HttpStatus#CONFLICT}
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * Create entity and attach it to parent
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/{collection}/{parentId}", method = RequestMethod.PUT, consumes = "application/json")
	public Response createWjson(@PathVariable("ownerId") Long ownerId, @PathVariable("collection") String collection,
			@PathVariable("parentId") Long parentId, @Valid @RequestBody File entity) {
		return _create(ownerId, collection, parentId, entity);
	}

	@RequestMapping(value = "/{collection}/{parentId}", method = RequestMethod.POST,
			consumes = "application/x-www-form-urlencoded")
	public Response create(@PathVariable("ownerId") Long ownerId, @PathVariable("collection") String collection,
			@PathVariable("parentId") Long parentId, @Valid @ModelAttribute File entity) {
		return _create(ownerId, collection, parentId, entity);
	}

	/**
	 * PUT: /{ownerId}/{collection}
	 * success: {@value HttpStatus#CREATED}
	 * error  : {@value HttpStatus#CONFLICT}
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * Create entity and attach it to owner
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/{collection}/root", method = RequestMethod.PUT, consumes = "application/json")
	public Response createWjsonToRoot(@PathVariable("ownerId") Long ownerId,
			@PathVariable("collection") String collection, @Valid @RequestBody File entity) {
		return _create(ownerId, collection, ownerId, entity);
	}

	@RequestMapping(value = "/{collection}/root", method = RequestMethod.POST,
			consumes = "application/x-www-form-urlencoded")
	public Response createToRoot(@PathVariable("ownerId") Long ownerId, @PathVariable("collection") String collection,
			@Valid @ModelAttribute File entity) {
		return _create(ownerId, collection, ownerId, entity);
	}

	private Response _create(Long ownerId, String collection, Long parentId, File entity) {
		checkAuthorization(Method.CREATE, null, ownerId);
		if (!userService.exists(ownerId))
			return parentNotFoundWithIdResponse(ownerId);
		entity.setCollection(collection);
		entity.setOwner(ownerId);
		entity.setAttachedTo(parentId);
		Response response = super.create(entity);
		return response;
	}

	/**
	 * POST: /{ownerId}/{id}
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
	public Response updateWjson(@PathVariable("ownerId") Long ownerId, @PathVariable("id") Long id,
			@Valid @RequestBody File entity) {
		return _update(ownerId, id, entity);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public Response update(@PathVariable("ownerId") Long ownerId, @PathVariable("id") Long id,
			@Valid @ModelAttribute File entity) {
		return _update(ownerId, id, entity);
	}

	private Response _update(Long ownerId, Long id, File entity) {
		checkAuthorization(Method.UPDATE, id, ownerId);
		if (!userService.exists(ownerId))
			return parentNotFoundWithIdResponse(ownerId);
		return super.update(id, entity);
	}

	/**
	 * GET: /{ownerId}/{id}
	 * success: {@value HttpStatus#OK}
	 * error  : {@value HttpStatus#NOT_FOUND}
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * Get entity
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response get(@PathVariable("ownerId") Long ownerId, @PathVariable("id") Long id) {
		checkAuthorization(Method.GET, id, ownerId);
		if (!userService.exists(ownerId))
			return parentNotFoundWithIdResponse(ownerId);
		return super.get(id);
	}

	/**
	 * GET: /{ownerId}/{collection}/{parentId}
	 * success: {@value HttpStatus#OK}
	 * error  :
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * List entities by collection
	 * @return
	 */
	@RequestMapping(value = "/{collection}/all", method = RequestMethod.GET)
	public Response list(@PathVariable("ownerId") Long ownerId, @PathVariable("collection") String collection) {
		return _list(ownerId, collection, null);
	}
	@RequestMapping(value = "/{collection}/{parentId}", method = RequestMethod.GET)
	public Response listByParentId(@PathVariable("ownerId") Long ownerId, @PathVariable("collection") String collection,
			@PathVariable("parentId") Long parentId) {
		return _list(ownerId, collection, parentId);
	}
	private Response _list(Long ownerId, String collection, Long parentId) {
		checkAuthorization(Method.LIST, null, ownerId);
		if (!userService.exists(ownerId))
			return parentNotFoundWithIdResponse(ownerId);
		return listResponse(fileService.list(ownerId, collection, parentId));
	}

	/**
	 * DELETE: /{ownerId}/{id}
	 * success: {@value HttpStatus#OK}
	 * error  : {@value HttpStatus#NOT_FOUND}
	 *   parent not found: {@value HttpStatus#BAD_REQUEST}
	 * 
	 * Delete entity
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response delete(@PathVariable("ownerId") Long ownerId, @PathVariable("id") Long id) {
		checkAuthorization(Method.DELETE, id, ownerId);
		if (!userService.exists(ownerId))
			return parentNotFoundWithIdResponse(ownerId);
		if (!getService().exists(id))
			return notFoundWithIdResponse(id);
		return super.delete(id);
	}

	@Override
	public BaseService<File, Long> getService() {
		return fileService;
	}

	@Override
	public String getEntityKind() {
		return "file";
	}
	
	@Override
	protected <S extends Serializable> boolean isAuthorized(Method method, Long id, S ownerId) {
		// TODO Auto-generated method stub
		return (ownerId == userService.getAuthenticatedUser().getId());
	}
	
}
