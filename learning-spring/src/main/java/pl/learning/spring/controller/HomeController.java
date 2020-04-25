package pl.learning.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/user")
	@ResponseBody
	public String user() {
		return "<h1>Welcome auth user</hi>";
	}
	
	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "<h1>Welcome admin</hi>";
	}
	
	


}
