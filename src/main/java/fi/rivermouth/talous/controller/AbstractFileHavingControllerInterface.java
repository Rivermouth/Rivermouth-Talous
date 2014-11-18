package fi.rivermouth.talous.controller;

import java.io.Serializable;
import java.util.List;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.File;

public interface AbstractFileHavingControllerInterface
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable> {
	/**
	 * Add entity to parent
	 * @param entity
	 * @param parent
	 */
	public void addFileToParent(File file, PARENT parent);
	public void removeFileFromParent(File file, PARENT parent);
	//public List<CHILD> listByParentId(PARENT_ID parentId);
	public List<File> listFilesByParentId(PARENT_ID parentId);
}

