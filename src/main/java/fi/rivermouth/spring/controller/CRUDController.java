package fi.rivermouth.spring.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.entity.Response;
import fi.rivermouth.talous.domain.User;

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
	@RequestMapping(method = RequestMethod.PUT)
	public Response create(@RequestBody T entity) {
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
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public Response update(@PathVariable ID id, @RequestBody T entity) {
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
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response get(@PathVariable ID id) {
		return super.get(id);
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
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response delete(@PathVariable ID id) {
		return super.delete(id);
	}
	
}
