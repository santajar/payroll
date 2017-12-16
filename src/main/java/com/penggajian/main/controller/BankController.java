/**
 * 
 */
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

import com.penggajian.main.entity.Bank;
import com.penggajian.main.service.BankService;


/**
 * @author kepo
 *
 */
@Controller
public class BankController 
{
	@Autowired
	private BankService bankService;
	
	private static final String viewPrefix = "bank/";
	
	@RequestMapping(value="/bank", method=RequestMethod.GET)
	public String Bank(Model model)
	{

		model.addAttribute("bank", bankService.FindAll());
		
		return viewPrefix+"bank";
	}
	
	@RequestMapping("/bank/new")
	public String BankNew(Model model)
	{
		Bank bank = new Bank();
		model.addAttribute("bank", bank);
		System.out.print(bank);
		return viewPrefix+"create_bank";
	}
	
	@RequestMapping(value="/bank", method=RequestMethod.POST)
	public String createBank(@Valid @ModelAttribute("bank") Bank bankForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		bankService.createBank(bankForm);
		redirectAttributes.addFlashAttribute("info", "Bank created successfully");
		return "redirect:/bank";
	}
	
	@RequestMapping(value="/bank/{id_bank}", method=RequestMethod.GET)
	public String editBankForm(@PathVariable Integer id_bank, Model model) {
		Bank bank = bankService.getBankID(id_bank);
		model.addAttribute("bank",bank);
		//model.addAttribute("categoriesList",catalogService.getAllCategories());
		return viewPrefix+"edit_bank";
	}
	
	@RequestMapping(value="/bank/{id_bank}", method=RequestMethod.POST)
	public String updateBank(@Valid @ModelAttribute("bank") Bank bankForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		bankService.createBank(bankForm);
		redirectAttributes.addFlashAttribute("info", "Bank update successfully");
		return "redirect:/bank";
	}
	
	@RequestMapping(value="/bankDelete/{id_bank}", method=RequestMethod.GET)
	public String DeleteBankForm(@PathVariable Integer id_bank, Model model,RedirectAttributes redirectAttributes) {
		Bank bank = bankService.getBankID(id_bank);
		bankService.deleteBank(bank);
		redirectAttributes.addFlashAttribute("info", "Bank delete successfully");
		return "redirect:/bank";
	}
	
}
