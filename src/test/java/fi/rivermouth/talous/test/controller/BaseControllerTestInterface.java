package fi.rivermouth.talous.test.controller;

import java.io.Serializable;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.service.BaseService;

public interface BaseControllerTestInterface<T extends BaseEntity<ID>, ID extends Serializable> {
	/**
	 * Get entity which non-unique fields are random
	 * @return
	 */
	public T getRandomEntity();
	/**
	 * Get entity which all fields are random
	 * @return
	 */
	public T getTotallyRandomEntity();
	public String getAPIPath();
	public BaseService<T, ID> getService();
}
