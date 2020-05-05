package cl.lherrera.gsss.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(path = { "", "/" })
	public String home(HttpSession session) {
		return "home";
	}
}
