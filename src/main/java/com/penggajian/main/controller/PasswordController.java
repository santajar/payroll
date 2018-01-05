package com.penggajian.main.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.penggajian.main.entity.Password;
import com.penggajian.main.service.PasswordService;

@Controller
public class PasswordController {
	
	@Autowired private PasswordService passwordService;
	
	private static final String viewPrefix = "password/";
	
	@RequestMapping(value="/password", method=RequestMethod.GET)
	public String passwordMaster(Model model)
	{

		model.addAttribute("password", passwordService.findAll());
		
		return viewPrefix+"password";
	}
	
	@RequestMapping("/password/new")
	public String passwordNew(Model model)
	{
		Password password = new Password();
		model.addAttribute("password", password);
		return viewPrefix+"create_password";
	}
	
	@RequestMapping(value="/password", method=RequestMethod.POST)
	public String createBank(@Valid @ModelAttribute("password") Password passForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		String paswd = caesarEncript.encrypt(passForm.getPasswordEnkrip().toString().toUpperCase(), passForm.getPasswordEnkrip().toString().length());
		passForm.setPasswordEnkrip(paswd);
		passwordService.savePass(passForm);
		redirectAttributes.addFlashAttribute("info", "Password created successfully");
		return "redirect:/password";
	}
	
	@RequestMapping(value="/password/{id}", method=RequestMethod.GET)
	public String editBankForm(@PathVariable Integer id, Model model) {
		Password password = passwordService.FindByone(id);
		model.addAttribute("password",password);
		//model.addAttribute("categoriesList",catalogService.getAllCategories());
		return viewPrefix+"edit_password";
	}
	
	@RequestMapping(value="/password/{id}", method=RequestMethod.POST)
	public String editSaveForm(@Valid @ModelAttribute("password") Password passwordForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		String paswd = caesarEncript.encrypt(passwordForm.getPasswordEnkrip().toString().toUpperCase(), passwordForm.getPasswordEnkrip().length());
		passwordForm.setPasswordEnkrip(paswd);
		passwordService.savePass(passwordForm);
		redirectAttributes.addFlashAttribute("info", "Password update successfully");
		//model.addAttribute("categoriesList",catalogService.getAllCategories());
		return viewPrefix+"edit_password";
	}
	
	@RequestMapping(value="/passwordDelete/{id}", method=RequestMethod.GET)
	public String DeleteBankForm(@PathVariable Integer id, Model model,RedirectAttributes redirectAttributes) {
		Password password = passwordService.FindByone(id);
		passwordService.deletePass(password);
		redirectAttributes.addFlashAttribute("info", "Password delete successfully");
		return "redirect:/password";
	}

}
