package cl.lherrera.gsss.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cl.lherrera.gsss.dto.DetalleDTO;

@Controller
public class HomeController {

	@GetMapping("/user")
	public ModelAndView home(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("user/home");
		modelAndView.addObject("detalle", new DetalleDTO());
		modelAndView.addObject("valores", session.getAttribute("valores"));
		return modelAndView;
	}

	@PostMapping("/users")
	public RedirectView home(HttpSession session, @ModelAttribute DetalleDTO detalles) {
		List<DetalleDTO> valores = new ArrayList<>();
		// si los valores no vienen vacios se añaden a los valores.
		if (session.getAttribute("valores") != null) {
			List<DetalleDTO> valoresDeSesion = (List<DetalleDTO>) session.getAttribute("valores");
			// agrega todos los valores a la lista existente.
			valores.addAll(valoresDeSesion);
		}

		valores.add(detalles);
		// se reemplaza el valor de valores en la sesión,
		// se cambia por el nuevo valor de 
		// valores.
		session.setAttribute("valores", valores);
		return new RedirectView("/user");
	}
}
