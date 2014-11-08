package fi.rivermouth.talous.controller;

import java.io.Serializable;
import java.util.List;

import fi.rivermouth.talous.domain.BaseEntity;
import fi.rivermouth.talous.service.BaseService;

public interface ChildCRUDControllerInterface
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable, 
CHILD extends BaseEntity<CHILD_ID>, CHILD_ID extends Serializable> {
	public BaseService<PARENT, PARENT_ID> getParentService();
	/**
	 * Set parent to entity
	 * @param entity
	 * @param parent
	 */
	public void setEntityParent(CHILD entity, PARENT parent);
	/**
	 * Add entity to parent
	 * @param entity
	 * @param parent
	 */
	public void addEntityToParent(CHILD entity, PARENT parent);
	public void removeEntityFromParent(CHILD entity, PARENT parent);
	public PARENT getEntityParent(CHILD entity);
	//public List<CHILD> listByParentId(PARENT_ID parentId);
	public List<CHILD> listByParent(PARENT parent);
}
