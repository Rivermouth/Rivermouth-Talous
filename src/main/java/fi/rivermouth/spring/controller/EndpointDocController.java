package fi.rivermouth.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import fi.rivermouth.spring.entity.Response;

@RestController
public abstract class EndpointDocController {

	@Autowired
	public RequestMappingHandlerMapping handlerMapping;

	@RequestMapping(value = "/endpoints", method = RequestMethod.GET)
	public Response show() {
		return new Response(HttpStatus.OK, "doc", handlerMapping.getHandlerMethods());
	}
	
}
