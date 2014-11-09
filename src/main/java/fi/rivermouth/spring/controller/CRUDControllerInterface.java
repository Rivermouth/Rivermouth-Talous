package fi.rivermouth.spring.controller;

import java.io.Serializable;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.service.BaseService;

public interface CRUDControllerInterface<T extends BaseEntity<ID>, ID extends Serializable> {
	public BaseService<T, ID> getService();
}
