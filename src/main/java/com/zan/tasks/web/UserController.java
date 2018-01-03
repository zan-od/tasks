package com.zan.tasks.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.zan.tasks.model.User;
import com.zan.tasks.service.BoardService;
import com.zan.tasks.service.SecurityService;
import com.zan.tasks.service.UserService;
import com.zan.tasks.validator.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserValidator userValidator;
	
	@GetMapping("/registration")
    public String registration(Model model) {
 		
		model.addAttribute("userForm", new User());
        
		return "registration";
    }

	@PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);
		
		if (bindingResult.hasErrors()){
			return "registration";
		}
		
		if(userForm.getName().isEmpty()){
			userForm.setName(userForm.getUsername());
		}
		
		userService.save(userForm);
		securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
        
		return "redirect:tasks";
    }
	
	@GetMapping("/login")
    public String login(Model model, String error, String logout) {
		if (error != null) {
			model.addAttribute("error", "Username or password is incorrect!");
		}
		
		if (logout != null) {
			model.addAttribute("message", "Logged out successfully!");
		}
		
		return "login";
    }
	
	@GetMapping({"/", "/index"})
    public String index(Model model) {
 		
		User currentUser = userService.getCurrentUser();
		if (currentUser != null){
			model.addAttribute("boards", boardService.getBoards(currentUser));
			model.addAttribute("currentBoard", currentUser.getCurrentBoard());
		} else {
			model.addAttribute("boards", null);
			model.addAttribute("currentBoard", null);
		}
		
		return "index";
    }
	
	@GetMapping("/login-demo")
    public String loginDemo(Model model) {
 		
		securityService.autoLogin("demo", "demo");
		
		return "redirect:";
    }
	
	@GetMapping("/admin")
    public String admin(Model model) {
 		
		return "admin";
    }
}
