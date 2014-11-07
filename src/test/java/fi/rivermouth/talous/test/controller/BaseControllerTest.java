package fi.rivermouth.talous.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import fi.rivermouth.talous.Application;
import fi.rivermouth.talous.domain.BaseEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public abstract class BaseControllerTest<T extends BaseEntity, ID extends Serializable> implements BaseControllerTestInterface<T, ID> {
	
	@Autowired
    private WebApplicationContext wac;
	
	public MockMvc mockMvc;

	protected String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			System.out.println("JSON STRING: \n" + jsonContent);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				//.alwaysExpect(content().contentType("application/json;charset=UTF-8"))
				.build();
	}
	
	@Test
	public void testCRUD_Create() throws Exception {
		mockMvc.perform(
				post(getAPIPath()).content(asJsonString(getRandomEntity())).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.id").exists());
	}
	
	@Test
	public void testCRUD_Read() throws Exception {
		T entity = getService().create(getRandomEntity());
		
		mockMvc.perform(
				get(getAPIPath() + "/{id}", entity.getId()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testCRUD_Update() throws Exception {
		T oldEntity = getService().create(getRandomEntity());
		T entity = getRandomEntity();
		
		entity.setId(oldEntity.getId());

		mockMvc.perform(
				post(getAPIPath()).content(asJsonString(entity)).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.id").exists());

		mockMvc.perform(
				post(getAPIPath()).content(asJsonString(getRandomEntity())).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isConflict());
	}

}
