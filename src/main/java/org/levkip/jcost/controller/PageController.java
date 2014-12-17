package org.levkip.jcost.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by levy on 16.10.14.
 */

@Controller
public class PageController{
	
	@RequestMapping(
		value = "/", method = { RequestMethod.GET }
	)
	public String start(Model model) {
		return "redirect:/index";
	}
	
	@RequestMapping(
		value = "/index", method = { RequestMethod.GET }
	)
	public String index(Model model) {
		return "index";
	}	
	
	@RequestMapping(
		value = "/currency", method = { RequestMethod.GET }
	)
	public String currency(Model model) {
		return "currency";
	}
}
