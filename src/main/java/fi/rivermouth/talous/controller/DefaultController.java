package fi.rivermouth.talous.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.controller.EndpointDocController;
import fi.rivermouth.talous.model.AppInfo;


@RestController
@RequestMapping(value = "/")
public class DefaultController extends EndpointDocController {

	@RequestMapping(method = RequestMethod.GET)
	public AppInfo appInfo() {
		return new AppInfo();
	}
	
}
