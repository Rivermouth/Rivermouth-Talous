package fi.rivermouth.talous.controller;

import java.io.Serializable;

import fi.rivermouth.talous.domain.BaseEntity;
import fi.rivermouth.talous.service.BaseService;

public interface CRUDControllerInterface<T extends BaseEntity<ID>, ID extends Serializable> {
	public BaseService<T, ID> getService();
}
