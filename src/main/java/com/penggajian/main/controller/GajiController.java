package com.penggajian.main.controller;


import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.penggajian.main.entity.Pegawai;
import com.penggajian.main.service.GajiService;
import com.penggajian.main.service.PegawaiService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.repo.InputStreamResource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	ServletContext context;
	
	private static final String viewPrefix = "gaji/";
	
	@RequestMapping(value="/trx", method=RequestMethod.GET)
	public String Gaji(Model model)
	{
		List<Gaji> all = new ArrayList<Gaji>();
		List<Gaji> gaj = gajiService.FindAll();
		model.addAttribute("pegawai", pegawaiService.FindAll());
		
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
		model.addAttribute("gaji", all);		
		return viewPrefix+"gaji";
	}
	
	@RequestMapping(value="/getValueNorek", method=RequestMethod.GET)
	public @ResponseBody Integer getValue(@RequestParam("id") Integer id)
	{
		Pegawai pegawai = new Pegawai();
		
		pegawai = pegawaiService.getPegawaiID(id);
		Integer rekpegawai = pegawai.getNomor_rekening();
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
			HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttrs) throws IOException, JRException  {
		Pegawai pegawai = new Pegawai();
		
		
		int nip = Integer.valueOf(request.getParameter("nip").toString());
		String jp = request.getParameter("jumlahPotongan");
		String gb = request.getParameter("gajiBersih");
		String gk = request.getParameter("gajiKotor");
		String pp = request.getParameter("pph21");
		Integer ps = request.getParameter("passwordEnkrip").length();
		
		  String gajiBersih =  caesarEncript.encrypt(gb, ps);
		  String jumlahPotongan =  caesarEncript.encrypt(jp, ps);
		  String gajiKotor =  caesarEncript.encrypt(gk, ps);
		  String pph21 = caesarEncript.encrypt(pp, ps);
		  
		pegawai = pegawaiService.getPegawaiID(nip);
		gajiForm.setJumlahPotongan(jumlahPotongan);
		gajiForm.setGajiBersih(gajiBersih);
		gajiForm.setGajiKotor(gajiKotor);
		gajiForm.setPph21(pph21);
		gajiForm.setPegawai(pegawai);
		gajiService.createGaji(gajiForm);
		redirectAttributes.addFlashAttribute("info", "Gaji created successfully");
		
		Utility util = new Utility();
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		List<Gaji> gaj = gajiService.findByone(gajiForm.getNoGaji());
		
		List<Gaji> all = new ArrayList<Gaji>();
		
		for (Gaji gaji : gaj) {
			System.out.println("masuk "+gaji.getPasswordEnkrip().length());
			  gajiBersih =  caesarEncript.decrypt(gaji.getGajiBersih(), gaji.getPasswordEnkrip().length());
			  jumlahPotongan =  caesarEncript.decrypt(gaji.getJumlahPotongan(), gaji.getPasswordEnkrip().length());
			  gajiKotor =  caesarEncript.decrypt(gaji.getGajiKotor().toString(), gaji.getPasswordEnkrip().length());
			  pph21 = caesarEncript.decrypt(gaji.getPph21(), gaji.getPasswordEnkrip().length());
			  
			  gaji.setGajiBersih(gajiBersih);
			  gaji.setGajiKotor(gajiKotor);
			  gaji.setJumlahPotongan(jumlahPotongan);
			  gaji.setPph21(pph21);
			  
			  all.add(gaji);
		}
		
		JRDataSource datasource = new JRBeanCollectionDataSource(gaj);
		parameterMap.put("datasource", datasource);

		File file;
		
			file = new ClassPathResource("reports/slipgaji.jrxml").getFile();
			String absoluteFile = file.getAbsolutePath();		
		
			JasperPrint jasperPrint = util.getObjectPdf(absoluteFile, parameterMap, datasource);		
			
		
			Utility.sendPdfResponse(response, jasperPrint, "slip gaji");
			
		
		
			return "send:redrect:/trx";
	}
	
	@RequestMapping(path = "/pdf", method = RequestMethod.GET)
    public ModelAndView report(HttpServletResponse response, HttpServletRequest request) throws  JRException, IOException {
		Utility util = new Utility();
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		List<Gaji> gaj = gajiService.findByone("298184fd-f0c4-86b8-409b-cfbd1525d786");
		JRDataSource datasource = new JRBeanCollectionDataSource(gaj);
		parameterMap.put("datasource", datasource);
		
		File file = new ClassPathResource("reports/slipgaji.jrxml").getFile();
		
		String absoluteFile = file.getAbsolutePath();
//				context.getRealPath("resources/reports/slipgaji.jrxml");
//		String path = request.getRealPath("reports/slipgaji.jrxml");		

		JasperPrint jasperPrint = util.getObjectPdf(absoluteFile, parameterMap, datasource);
		
		Utility.sendPdfResponse(response, jasperPrint, "slip gaji");

//		JRPdfExporter exporter = new JRPdfExporter();
//		 
//		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//		exporter.setExporterOutput(
//		  new SimpleOutputStreamExporterOutput("employeeReport.pdf"));
//		 
//		SimplePdfReportConfiguration reportConfig
//		  = new SimplePdfReportConfiguration();
//		reportConfig.setSizePageToContent(true);
//		reportConfig.setForceLineBreakPolicy(false);
//		 
//		SimplePdfExporterConfiguration exportConfig
//		  = new SimplePdfExporterConfiguration();
//		exportConfig.setMetadataAuthor("baeldung");
//		exportConfig.setEncrypted(true);
//		exportConfig.setAllowedPermissionsHint("PRINTING");
//		 
//		exporter.setConfiguration(reportConfig);
//		exporter.setConfiguration(exportConfig);
//		 
//		exporter.exportReport();
		
		return null;
        

    }
	
	

   

}
