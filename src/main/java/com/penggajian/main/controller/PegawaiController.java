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

import com.penggajian.main.entity.Pegawai;
import com.penggajian.main.service.PegawaiService;


/**
 * @author kepo
 *
 */
@Controller
public class PegawaiController 
{
	@Autowired
	private PegawaiService pegawaiService;
	
	private static final String viewPrefix = "pegawai/";
	
	@RequestMapping(value="/pegawai", method=RequestMethod.GET)
	public String Pegawai(Model model)
	{
		model.addAttribute("pegawai", pegawaiService.FindAll());
		return viewPrefix+"pegawai";
	}
	
	@RequestMapping("/pegawai/new")
	public String PegawaiNew(Model model)
	{
		Pegawai pegawai = new Pegawai();
		model.addAttribute("pegawai", pegawai);
		System.out.print(pegawai);
		return viewPrefix+"create_pegawai";
	}
	
	@RequestMapping(value="/pegawai", method=RequestMethod.POST)
	public String createPegawai(@Valid @ModelAttribute("pegawai") Pegawai pegawaiForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		pegawaiService.createPegawai(pegawaiForm);
		redirectAttributes.addFlashAttribute("info", "Pegawai created successfully");
		return "redirect:/pegawai";
	}
	
	@RequestMapping(value="/pegawai/{nip}", method=RequestMethod.GET)
	public String editPegawaiForm(@PathVariable Integer nip, Model model) {
		Pegawai pegawai = pegawaiService.getPegawaiID(nip);
		model.addAttribute("pegawai",pegawai);
		//model.addAttribute("categoriesList",catalogService.getAllCategories());
		return viewPrefix+"edit_pegawai";
	}
	
	@RequestMapping(value="/pegawai/{nip}", method=RequestMethod.POST)
	public String updatePegawai(@Valid @ModelAttribute("pegawai") Pegawai pegawaiForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		pegawaiService.createPegawai(pegawaiForm);
		redirectAttributes.addFlashAttribute("info", "Pegawai update successfully");
		return "redirect:/pegawai";
	}
	
	@RequestMapping(value="/pegawaiDelete/{nip}", method=RequestMethod.GET)
	public String DeletePegawaiForm(@PathVariable Integer nip, Model model,RedirectAttributes redirectAttributes) {
		Pegawai pegawai = pegawaiService.getPegawaiID(nip);
		pegawaiService.deletePegawai(pegawai);
		redirectAttributes.addFlashAttribute("info", "Pegawai delete successfully");
		return "redirect:/pegawai";
	}
	
}
