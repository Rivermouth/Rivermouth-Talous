package fi.rivermouth.talous.test.controller;

import java.io.Serializable;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.service.BaseService;

public interface BaseChildControllerTestInterface
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable,
T extends BaseEntity<ID>, ID extends Serializable> {
	/**
	 * Get entity which non-unique fields are random
	 * @return
	 */
	public PARENT getRandomParent();
	/**
	 * Get entity which all fields are random
	 * @return
	 */
	public PARENT getTotallyRandomParent();
	public BaseService<PARENT, PARENT_ID> getParentService();
	public void setEntityParent(T entity, PARENT parent);
}
