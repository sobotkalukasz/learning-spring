package pl.learning.spring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pl.learning.spring.model.user.dto.UserFormTO;
import pl.learning.spring.service.user.UserService;

@Controller
public class UserController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/register")
	public String register(UserFormTO userFormTO) {
		return "registerForm";
	}

	@PostMapping("/register")
	public String addUser(@ModelAttribute @Valid UserFormTO userFormTO, BindingResult bindResult) {
		if (bindResult.hasErrors()) {
			return "registerForm";
		}
		else {
			userService.addUser(userFormTO);
			return "registerSuccess";
		}
	}
}
