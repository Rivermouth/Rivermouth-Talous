package fi.rivermouth.spring.controller;

import java.io.Serializable;
import java.util.List;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.service.BaseService;

public interface ChildCRUDControllerInterface
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable, 
CHILD extends BaseEntity<CHILD_ID>, CHILD_ID extends Serializable> {
	public BaseService<PARENT, PARENT_ID> getParentService();
	/**
	 * Add entity to parent
	 * @param entity
	 * @param parent
	 */
	public void addEntityToParent(CHILD entity, PARENT parent);
	public void removeEntityFromParent(CHILD entity, PARENT parent);
	//public List<CHILD> listByParentId(PARENT_ID parentId);
	public List<CHILD> listByParentId(PARENT_ID parentId);
}
