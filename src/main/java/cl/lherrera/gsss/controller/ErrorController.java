package cl.lherrera.gsss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

	@GetMapping("/recurso-prohibido")
	public String recurso03() {
		return "error/403";
	}

}
