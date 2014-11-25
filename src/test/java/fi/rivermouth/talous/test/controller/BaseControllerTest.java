package fi.rivermouth.talous.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.talous.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public abstract class BaseControllerTest<T extends BaseEntity<ID>, ID extends Serializable> implements BaseControllerTestInterface<T, ID> {
	
	@Autowired
    private WebApplicationContext wac;
	
	public MockMvc mockMvc;

	@Transactional(readOnly=true)
	protected String asJsonString(final Object obj) {
		try {
			Hibernate.initialize(obj);
			final ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			final String jsonContent = mapper.writeValueAsString(obj);
			System.out.println("JSON STRING: \n" + jsonContent);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String apiPath(String url) {
		if (url == null) return getAPIPath();
		return getAPIPath() + url;
	}
	
	protected String apiPath() {
		return apiPath(null);
	}
	
	protected MockHttpServletRequestBuilder getPut() {
		return put(apiPath());
	}
	
	protected MockHttpServletRequestBuilder getGet(T entity) {
		return get(apiPath("/{id}"), entity.getId());
	}
	
	protected MockHttpServletRequestBuilder getList() {
		return get(apiPath());
	}
	
	protected MockHttpServletRequestBuilder getPost(T entity) {
		return post(apiPath("/{id}"), entity.getId());
	}
	
	protected MockHttpServletRequestBuilder getDelete(T entity) {
		return delete(apiPath("/{id}"), entity.getId());
	}
	
	protected void createTotallyRandomEntity() throws Exception {
		mockMvc.perform(
				getPut().content(asJsonString(getTotallyRandomEntity())).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.id").exists());
	}
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				//.alwaysExpect(content().contentType("application/json;charset=UTF-8"))
				.build();
		
		getService().deleteAll();
	}
	
	@Test
	public void testCRUD_Create() throws Exception {
		mockMvc.perform(
				getPut().content(asJsonString(getRandomEntity())).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.id").exists());
	}
	
	@Test
	public void testCRUD_Read() throws Exception {
		T entity = getService().create(getRandomEntity());
		
		mockMvc.perform(
				getGet(entity))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testCRUD_CreateMultiply_And_List() throws Exception {
		createTotallyRandomEntity();
		createTotallyRandomEntity();
		createTotallyRandomEntity();
		
		mockMvc.perform(
				getList())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data", hasSize(3)));
	}

	@Transactional(readOnly=true)
	private void performCRUD_Update() throws Exception {
		T entity = getService().list().get(0);

		mockMvc.perform(
				getPost(entity).content(asJsonString(entity)).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testCRUD_Update() throws Exception {
		createTotallyRandomEntity();
		performCRUD_Update();
	}
	
	@Test
	public void testCRUD_Delete() throws Exception {
		createTotallyRandomEntity();
		T entity = getService().list().get(0);
		
		mockMvc.perform(
				getDelete(entity))
				.andExpect(status().isOk());
	}

}
