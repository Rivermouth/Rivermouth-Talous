package fi.rivermouth.talous.service;

import org.springframework.stereotype.Service;

import fi.rivermouth.talous.domain.BaseEntity;

@Service
public abstract class BaseService<T extends BaseEntity> extends fi.rivermouth.spring.service.BaseService<T, Long>{

}
