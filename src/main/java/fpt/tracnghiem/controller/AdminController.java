package fpt.tracnghiem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@RequestMapping(method = RequestMethod.GET)
	public String adminHomepage() {
		return "admin/index";
	}
}
