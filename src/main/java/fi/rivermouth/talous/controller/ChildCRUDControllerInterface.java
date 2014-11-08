package fi.rivermouth.talous.controller;

import java.io.Serializable;

import fi.rivermouth.talous.domain.BaseEntity;
import fi.rivermouth.talous.service.BaseService;

public interface ChildCRUDControllerInterface<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable> {
	public BaseService<PARENT, PARENT_ID> getParentService();
}
