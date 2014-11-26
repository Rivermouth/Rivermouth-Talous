package fi.rivermouth.talous.controller;

import java.io.Serializable;
import java.util.List;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.File;
import fi.rivermouth.talous.service.AbstractFileHavingService;
import fi.rivermouth.talous.service.FileService;

public interface AbstractFileHavingControllerInterface
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable> {
	public AbstractFileHavingService<PARENT, PARENT_ID> getFileHavingService();
}
