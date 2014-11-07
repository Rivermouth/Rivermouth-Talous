package fi.rivermouth.talous.test.controller;

import java.io.Serializable;

import fi.rivermouth.talous.domain.BaseEntity;
import fi.rivermouth.talous.service.BaseService;

public interface BaseControllerTestInterface<T extends BaseEntity, ID extends Serializable> {
	public T getRandomEntity();
	public String getAPIPath();
	public BaseService<T, ID> getService();
}
