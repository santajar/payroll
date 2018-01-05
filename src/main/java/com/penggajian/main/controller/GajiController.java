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
import com.penggajian.main.service.PasswordService;
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
import com.penggajian.main.entity.Password;

@Controller
public class GajiController {
	
	@Autowired
	private GajiService gajiService;
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired private PasswordService passwordService;
	
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
	@ResponseBody
	public ModelAndView getExportdata(@RequestParam("password") String password, RedirectAttributes redirectAttributes){
		
		ModelAndView modelAndView = new ModelAndView();
		String pass = caesarEncript.encrypt(password.toUpperCase(), password.length());
		List<Gaji> all = new ArrayList<Gaji>();
		List<Gaji> gaj = gajiService.findBypassword(pass);
		
			
			for (Gaji gaji : gaj) {
				  String gajiBersih =  vigen.decipher(gaji.getGajiBersih(), pass);
				  String jumlahPotongan =  vigen.decipher(gaji.getJumlahPotongan(), pass);
				  String gajiKotor =  vigen.decipher(gaji.getGajiKotor().toString(), pass);
				  String pph21 = vigen.decipher(gaji.getPph21(), pass);
				  
				  gaji.setGajiBersih(gajiBersih);
				  gaji.setGajiKotor(gajiKotor);
				  gaji.setJumlahPotongan(jumlahPotongan);
				  gaji.setPph21(pph21);
				  
				  all.add(gaji);
			}	
			
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
	
	@RequestMapping(value="/getValuepass", method=RequestMethod.GET)
	public @ResponseBody Password getValuePass(@RequestParam("tanggal") String tanggal, Model model)
	{
		System.out.println(tanggal);
		Password password = passwordService.FindByonePassword(tanggal);
		model.addAttribute("password", password);
		return password;
	}

	@RequestMapping(value="/trx/new" , method=RequestMethod.GET)
	public String GajiNew(Model model)
	{
		Gaji gaji1 = new Gaji();
		Pegawai pegawai = new Pegawai();	
		Password password = new Password();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji", gaji1);
		model.addAttribute("password", password);
		model.addAttribute("listpegawai", pegawaiService.FindAll());
		System.out.print(pegawai);
		
		return viewPrefix+"create_gaji";
	}

	@RequestMapping(value="/trx", method=RequestMethod.POST)
	public String createGaji(@Valid @ModelAttribute("gaji") Gaji gajiForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, 
			HttpServletResponse response, HttpServletRequest request) throws IOException, JRException  {
		Pegawai pegawai = new Pegawai();
		
		Password passwordForm = passwordService.FindByone(Integer.valueOf(request.getParameter("passwordID").toString()));
		int nip = Integer.valueOf(request.getParameter("nip").toString());
		String jp = request.getParameter("jumlahPotongan");
		String gb = request.getParameter("gajiBersih");
		String gk = request.getParameter("gajiKotor");
		String pp = request.getParameter("pph21");
		String password = passwordForm.getPasswordEnkrip().toString();
		
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
		gajiForm.setPassword(passwordForm);
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
//			String pass = caesarEncript.decrypt(gaji.getPasswordEnkrip(), gaji.getPasswordEnkrip().length());
			  String gajiBersih =  vigen.decipher(gaji.getGajiBersih(), gaji.getPassword().getPasswordEnkrip());
			  String jumlahPotongan =  vigen.decipher(gaji.getJumlahPotongan(), gaji.getPassword().getPasswordEnkrip());
			  String gajiKotor =  vigen.decipher(gaji.getGajiKotor().toString(), gaji.getPassword().getPasswordEnkrip());
			  String pph21 = vigen.decipher(gaji.getPph21(), gaji.getPassword().getPasswordEnkrip());
			  
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
	public String ReportPeriod(HttpServletResponse response, HttpServletRequest request) throws IOException
	{	 

		Utility util = new Utility();
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		String bulan = request.getParameter("bulan");
		String tahun = request.getParameter("tahun");
		String pass = request.getParameter("password");
		String password = caesarEncript.encrypt(pass.toUpperCase(), pass.length());
		System.out.println(bulan);
		System.out.println(tahun);
		System.out.println(password);
		
		List<Map<String,Object>> list = nativeQuery.findRport(bulan, tahun, password);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		for (Map<String, Object> map : list) {
		    	
		    	String pph = vigen.decipher(map.get("pph21").toString().trim(), password);
		    	String gajiBersih = vigen.decipher(map.get("gaji_bersih").toString().trim(), password);
		    	String jmlPotong = vigen.decipher(map.get("jumlah_potongan").toString().trim(), password);
		    	
		    	map.put("pph21", Integer.valueOf(pph));
		    	map.put("gaji_bersih", Integer.valueOf(gajiBersih));
		    	map.put("jumlah_potongan", Integer.valueOf(jmlPotong));
		    	
		    	mapList.add(map);
		    	
		    
		}
		
		JRDataSource datasource = new JRBeanCollectionDataSource(mapList);
		parameterMap.put("datasource", datasource);

		File file;
		
		file = new ClassPathResource("reports/periode.jrxml").getFile();
		String absoluteFile = file.getAbsolutePath();		
	
		JasperPrint jasperPrint = util.getObjectPdf(absoluteFile, parameterMap, datasource);	
		
	
		Utility.sendPdfResponse(response, jasperPrint, "slip gaji");
		
		return null;
		
		
	}   
	
//	@RequestMapping(value = { "/tes" }, method = RequestMethod.GET)
//	 public @ResponseBody List<Gaji> userFindAll() {
//
//
//			List<Gaji> gaj = gajiService.findByone("d953b48c-052d-5faf-3330-cdf6c96b44ca");
//			List<Gaji> all = new ArrayList<Gaji>();
//			
//			for (Gaji gaji : gaj) {
////				String pass = caesarEncript.decrypt(gaji.getPasswordEnkrip(), gaji.getPasswordEnkrip().length());
//				  String gajiBersih =  vigen.decipher(gaji.getGajiBersih(), gaji.getPasswordEnkrip());
//				  String jumlahPotongan =  vigen.decipher(gaji.getJumlahPotongan(), gaji.getPasswordEnkrip());
//				  String gajiKotor =  vigen.decipher(gaji.getGajiKotor().toString(), gaji.getPasswordEnkrip());
//				  String pph21 = vigen.decipher(gaji.getPph21(), gaji.getPasswordEnkrip());
//				  
//				  gaji.setGajiBersih(gajiBersih);
//				  gaji.setGajiKotor(gajiKotor);
//				  gaji.setJumlahPotongan(jumlahPotongan);
//				  gaji.setPph21(pph21);
//				  
//				  all.add(gaji);
//			}
//
//		
////			Gson gson = new Gson();
////			String test = gson.toJson(response);
////			System.out.println(test);
//
//			return all;
//
//	    }


}
