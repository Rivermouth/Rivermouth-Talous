package fi.rivermouth.talous.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fi.rivermouth.spring.controller.EndpointDocController;
import fi.rivermouth.talous.model.AppInfo;

/**
 * This controller takes requests that no other controller takes 
 * care of.
 * 
 * @author Karri Rasinmäki
 *
 */

@Controller
@RequestMapping(value = "*")
public class DefaultController {
	
	public String index() {
		return "index";
	}
	
}
