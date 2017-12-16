package com.penggajian.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	
	
	@RequestMapping("/")
	public String home(Model model)
	{
		return "public/login";
	}
	
	@RequestMapping("/home")
	public String home1(Model model)
	{
		return "home";
	}


}
