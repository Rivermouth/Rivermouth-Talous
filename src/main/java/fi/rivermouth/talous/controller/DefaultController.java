package fi.rivermouth.talous.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fi.rivermouth.spring.controller.EndpointDocController;
import fi.rivermouth.talous.model.AppInfo;

@Controller
public class DefaultController {
	
	@ResponseBody
	@RequestMapping(value = "/api/info", method = RequestMethod.GET)
	public AppInfo appInfo() {
		return new AppInfo();
	}
	
	@RequestMapping(value = "/")
	public String index() {
		return "index.html";
	}
	
}
