package fi.rivermouth.talous.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.Serializable;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.talous.Application;

public abstract class BaseChildControllerTest
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable,  // Parent
T extends BaseEntity<ID>, ID extends Serializable>  					// Child (this)
extends BaseControllerTest<T, ID> implements BaseChildControllerTestInterface<PARENT, PARENT_ID, T, ID> {
	
	PARENT parent;
//	
//	@Override
//	protected String apiPath(String url) {
//		if (url == null) return getAPIPath();
//		return getAPIPath() + "/{parentId}" + url;
//	}
//	
//	@Override
//	protected String apiPath() {
//		return apiPath(null);
//	}
	
	@Override
	public void setUp() throws Exception {
		getParentService().deleteAll();
		parent = getRandomParent();
		getParentService().save(parent);
		super.setUp();
	}
	
	@Override
	protected MockHttpServletRequestBuilder getPut() {
		return put(apiPath(), parent.getId());
	}

	@Override
	protected MockHttpServletRequestBuilder getGet(T entity) {
//		setEntityParent(entity, parent);
		return get(apiPath("/{id}"), parent.getId(), entity.getId());
	}
	
	@Override
	protected MockHttpServletRequestBuilder getList() {
		return get(apiPath(), parent.getId());
	}

	@Override
	protected MockHttpServletRequestBuilder getPost(T entity) {
//		setEntityParent(entity, parent);
		return post(apiPath("/{id}"), parent.getId(), entity.getId());
	}
	
	@Override
	protected MockHttpServletRequestBuilder getDelete(T entity) {
//		setEntityParent(entity, parent);
		return delete(apiPath("/{id}"), parent.getId(), entity.getId());
	}

}
