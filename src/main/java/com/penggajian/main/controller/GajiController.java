package com.penggajian.main.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.penggajian.main.entity.Pegawai;
import com.penggajian.main.repository.NativeRepository;
import com.penggajian.main.service.GajiService;
import com.penggajian.main.service.PegawaiService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.penggajian.main.config.Utility;
import com.penggajian.main.entity.Gaji;

@Controller
public class GajiController {
	
	@Autowired
	private GajiService gajiService;
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired 
	private NativeRepository nativeQuery;
	@Autowired
	ServletContext context;
	
	private static final String viewPrefix = "gaji/";
	
	@RequestMapping(value="/trx", method=RequestMethod.GET)
	public String Gaji(Model model)
	{
//		List<Gaji> all = new ArrayList<Gaji>();
		List<Gaji> gaj = gajiService.FindAll();
		model.addAttribute("pegawai", pegawaiService.FindAll());
		
		model.addAttribute("gaji", gaj);		
		return viewPrefix+"gaji";
	}
		
	@GetMapping("/getData")
	public ModelAndView getExportdata(@RequestParam("password") String password){
		
		List<Gaji> all = new ArrayList<Gaji>();
		List<Gaji> gaj = gajiService.FindAll();
		
		for (Gaji gaji : gaj) {
			System.out.println("masuk "+gaji.getPasswordEnkrip().length());
			  String gajiBersih =  caesarEncript.decrypt(gaji.getGajiBersih(), gaji.getPasswordEnkrip().length());
			  String jumlahPotongan =  caesarEncript.decrypt(gaji.getJumlahPotongan(), gaji.getPasswordEnkrip().length());
			  String gajiKotor =  caesarEncript.decrypt(gaji.getGajiKotor().toString(), gaji.getPasswordEnkrip().length());
			  String pph21 = caesarEncript.decrypt(gaji.getPph21(), gaji.getPasswordEnkrip().length());
			  
			  gaji.setGajiBersih(gajiBersih);
			  gaji.setGajiKotor(gajiKotor);
			  gaji.setJumlahPotongan(jumlahPotongan);
			  gaji.setPph21(pph21);
			  
			  all.add(gaji);
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("gaji", all);
		modelAndView.setViewName("gaji/gaji :: resultsList");
		return modelAndView;
	}
	
	@RequestMapping(value="/getValueNorek", method=RequestMethod.GET)
	public @ResponseBody String getValue(@RequestParam("id") Integer id)
	{
		Pegawai pegawai = new Pegawai();
		
		pegawai = pegawaiService.getPegawaiID(id);
		String rekpegawai = pegawai.getNomor_rekening();
		return rekpegawai;
	}

	@RequestMapping(value="/trx/new" , method=RequestMethod.GET)
	public String GajiNew(Model model)
	{
		Gaji gaji1 = new Gaji();
		Pegawai pegawai = new Pegawai();	
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji", gaji1);
		model.addAttribute("listpegawai", pegawaiService.FindAll());
		System.out.print(pegawai);
		
		return viewPrefix+"create_gaji";
	}

	@RequestMapping(value="/trx", method=RequestMethod.POST)
	public String createGaji(@Valid @ModelAttribute("gaji") Gaji gajiForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, 
			HttpServletResponse response, HttpServletRequest request) throws IOException, JRException  {
		Pegawai pegawai = new Pegawai();
		
		
		int nip = Integer.valueOf(request.getParameter("nip").toString());
		String jp = request.getParameter("jumlahPotongan");
		String gb = request.getParameter("gajiBersih");
		String gk = request.getParameter("gajiKotor");
		String pp = request.getParameter("pph21");
		String pass = request.getParameter("passwordEnkrip").toUpperCase();
		Integer ps = request.getParameter("passwordEnkrip").length();
		
		  String password = caesarEncript.encrypt(pass, ps);
		  String gajiBersih =  vigen.encipher(gb, password);
		  String jumlahPotongan =  vigen.encipher(jp, password);
		  String gajiKotor =  vigen.encipher(gk, password);
		  String pph21 = vigen.encipher(pp, password);
		  
		  
		pegawai = pegawaiService.getPegawaiID(nip);
		gajiForm.setJumlahPotongan(jumlahPotongan);
		gajiForm.setGajiBersih(gajiBersih);
		gajiForm.setGajiKotor(gajiKotor);
		gajiForm.setPph21(pph21);
		gajiForm.setPegawai(pegawai);
		gajiForm.setPasswordEnkrip(password);
		gajiService.createGaji(gajiForm);
		redirectAttributes.addFlashAttribute("info", "Gaji created successfully");
		
		return "redirect:/trx";
	}
	
	@RequestMapping(path = "/pdf/{noGaji}", method = RequestMethod.GET)
    public ModelAndView report(HttpServletResponse response, HttpServletRequest request,
    		@PathVariable String noGaji) throws  JRException, IOException {
		
		Utility util = new Utility();
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		List<Gaji> gaj = gajiService.findByone(noGaji);
		
		List<Gaji> all = new ArrayList<Gaji>();
		
		for (Gaji gaji : gaj) {
			System.out.println("masuk "+gaji.getPasswordEnkrip().length());
			  String gajiBersih =  caesarEncript.decrypt(gaji.getGajiBersih(), gaji.getPasswordEnkrip().length());
			  String jumlahPotongan =  caesarEncript.decrypt(gaji.getJumlahPotongan(), gaji.getPasswordEnkrip().length());
			  String gajiKotor =  caesarEncript.decrypt(gaji.getGajiKotor().toString(), gaji.getPasswordEnkrip().length());
			  String pph21 = caesarEncript.decrypt(gaji.getPph21(), gaji.getPasswordEnkrip().length());
			  
			  gaji.setGajiBersih(gajiBersih);
			  gaji.setGajiKotor(gajiKotor);
			  gaji.setJumlahPotongan(jumlahPotongan);
			  gaji.setPph21(pph21);
			  
			  all.add(gaji);
		}
		
		JRDataSource datasource = new JRBeanCollectionDataSource(all);
		parameterMap.put("datasource", datasource);

		File file;
		
		file = new ClassPathResource("reports/report.jrxml").getFile();
		String absoluteFile = file.getAbsolutePath();		
	
		JasperPrint jasperPrint = util.getObjectPdf(absoluteFile, parameterMap, datasource);	
		
	
		Utility.sendPdfResponse(response, jasperPrint, "slip gaji");
		
		return null;
        

    }
	
	@RequestMapping(value="/period", method=RequestMethod.GET)
	public String Period(Model model)
	{		
		return "report/period";
	}
	
	@RequestMapping(value="/period", method=RequestMethod.POST)
	public String ReportPeriod(HttpServletResponse response, HttpServletRequest request,
			@RequestParam("date") String date,@RequestParam("date1") String date1)
	{	 
		System.out.println(date);
		System.out.println(date1);
		
		List<Map<String,Object>> list = nativeQuery.findRport(date, date1);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		for (Map<String, Object> map : list) {
		    	
				Integer s = map.get("password_enkrip").toString().length(); 
		    	String pph = caesarEncript.decrypt(map.get("pph21").toString().trim(), s);
		    	String gajiBersih = caesarEncript.decrypt(map.get("gaji_bersih").toString().trim(), s);
		    	String jmlPotong = caesarEncript.decrypt(map.get("jumlah_potongan").toString().trim(), s);
		    	
		    	map.put("pph21", pph);
		    	map.put("gaji_bersih", gajiBersih);
		    	map.put("jumlah_potongan", jmlPotong);
		    	
		    	mapList.add(map);
		    	
		    
		}
		
		for (Map<String, Object> map : mapList) {
		    for (Map.Entry<String, Object> entry : map.entrySet()) {
		    	
		        System.out.println("List 2 : "+entry.getKey() + " - " + entry.getValue());
		    }
		}
		
		return "report/period";
	}   

}
