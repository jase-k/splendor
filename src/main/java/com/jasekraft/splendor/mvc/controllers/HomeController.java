package com.jasekraft.splendor.mvc.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jasekraft.splendor.mvc.models.LoginUser;
import com.jasekraft.splendor.mvc.models.User;
import com.jasekraft.splendor.mvc.services.UserService;

@RestController
public class HomeController {

    private final UserService userServ;
	
    @Autowired
    public HomeController(UserService userServ) {
    	this.userServ = userServ;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "index.jsp";
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser, 
            BindingResult result, Model model, HttpSession session) {
        userServ.register(newUser, result);
        if(result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "index.jsp";
        }
        session.setAttribute("user_id", newUser.getId());
        return "redirect:/home";
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public User login(@RequestBody Map<String, Object> body,BindingResult result, Model model, HttpSession session) {
        LoginUser newLogin = new LoginUser();
        newLogin.setUsername((String) body.get("username"));
        newLogin.setPassword((String) body.get("password"));
    	User user = userServ.login(newLogin, result);
        return user;
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	session.removeAttribute("user_id");
    	return "redirect:/";
    }
}