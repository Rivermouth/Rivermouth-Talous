package fi.rivermouth.spring.controller;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.entity.Response;

@RestController
public abstract class CRUDController<T extends BaseEntity<ID>, ID extends Serializable> 
extends BaseController<T, ID> {

	/**
	 * PUT: /
	 * success: {@value HttpStatus#CREATED}
	 * error  : {@value HttpStatus#CONFLICT}
	 * 
	 * Create entity
	 * @param entity
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
	public Response createWjson(@Valid @RequestBody T entity) {
		checkAuthorization(Method.CREATE, null, null);
		return super.create(entity);
	}
	@Override
	@RequestMapping(method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public Response create(@Valid @ModelAttribute T entity) {
		checkAuthorization(Method.CREATE, null, null);
		return super.create(entity);
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
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = "application/json")
	public Response updateWjson(@Valid @PathVariable ID id, @RequestBody T entity) {
		checkAuthorization(Method.UPDATE, id, null);
		return super.update(id, entity);
	}
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public Response update(@Valid @PathVariable ID id, @ModelAttribute T entity) {
		checkAuthorization(Method.UPDATE, id, null);
		return super.update(id, entity);
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
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response get(@PathVariable ID id) {
		checkAuthorization(Method.GET, id, null);
		return super.get(id);
	}
	
	/**
	 * GET: /
	 * success: {@value HttpStatus#OK}
	 * 
	 * List entities
	 * @return
	 */
	@Override
	@RequestMapping(method = RequestMethod.GET)
	public Response list() {
		checkAuthorization(Method.LIST, null, null);
		return super.list();
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
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response delete(@PathVariable ID id) {
		checkAuthorization(Method.DELETE, id, null);
		return super.delete(id);
	}
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response handleException(MethodArgumentNotValidException exception) {
        return new Response(HttpStatus.BAD_REQUEST, "error", exception);
    }
	
}
