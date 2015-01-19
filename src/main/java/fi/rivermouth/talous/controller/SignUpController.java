package fi.rivermouth.talous.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/register")
public class SignUpController {

	@RequestMapping(method = RequestMethod.GET)
	public String showSignUpPage() {
		return "register";
	}
	
}
