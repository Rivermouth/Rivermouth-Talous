package fi.rivermouth.talous.service;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.controller.AbstractFileHavingControllerInterface;
import fi.rivermouth.talous.domain.User;

@Service
public abstract class AbstractFileHavingService<T extends BaseEntity<ID>, ID extends Serializable> extends BaseService<T, ID> 
implements AbstractFileHavingServiceInterface<T, ID> {

}
