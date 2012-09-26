package de.tilliwilli.phantasien.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class SimpleTestController {

	@RequestMapping(method = RequestMethod.GET)
	public String startPage() {
		return "start";
	}

	@RequestMapping(method = RequestMethod.GET, params = "name")
	public String doTest(@RequestParam("name") String name, ModelMap model) {
		model.addAttribute("name", name);

		return "hello";
	}
}
