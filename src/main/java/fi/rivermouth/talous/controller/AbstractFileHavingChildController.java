package fi.rivermouth.talous.controller;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.controller.CRUDController;
import fi.rivermouth.spring.controller.ChildCRUDController;
import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.entity.FileHavingEntityInterface;
import fi.rivermouth.spring.service.BaseService;

@RestController
public abstract class AbstractFileHavingChildController<T extends BaseEntity<ID>, ID extends Serializable> 
extends AbstractFileHavingController<T, ID> {

	
	
}
