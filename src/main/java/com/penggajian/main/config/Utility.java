package com.penggajian.main.config;

import java.io.ByteArrayOutputStream;import java.io.File;import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;import javax.annotation.Resource;import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;import net.sf.jasperreports.repo.InputStreamResource;import org.apache.log4j.Logger;import org.apache.tomcat.util.http.fileupload.IOUtils;import org.springframework.context.ApplicationContext;import org.springframework.context.support.FileSystemXmlApplicationContext;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;

public class Utility{
	
	private static final Logger logger = Logger.getLogger(Utility.class);
	
	public static String SpaceOrNol(String data,int panjang)
	{
		int tot=panjang-data.length();
		String tmpNol = "";
		for (int i=1; i<=tot; i++)
		{
			tmpNol+="0";
		}
		data=tmpNol + data;
		return data;
	}

	public String SpaceOrCut(String data,int panjang){		
		int tot=panjang-data.length();
		for (int i=1;i<=tot;i++){data+=" ";}
		if (tot<0) 
			data=data.substring(0,panjang);
		return data;
	}

	public static String enCryptor(String type, String str) 
	{
		byte[] digest = null;

		try 
		{
			MessageDigest md	= MessageDigest.getInstance(type);
			digest 				= md.digest((str).getBytes());			
		} 
		catch (NoSuchAlgorithmException e) 
		{
			//Log.insert("ERROR", e.getMessage());
		}

		return bytes2String(digest); 
	}

	private static String bytes2String(byte[] bytes) 
	{
		StringBuilder string = new StringBuilder();

		for (byte b: bytes) 
		{
			String hexString = Integer.toHexString(0x00FF & b);
			string.append(hexString.length() == 1 ? "0" + hexString : hexString);
		}

		return string.toString();
	}


	public String createMarshalXML(){
		StringWriter sw = new StringWriter();

		return sw.toString();
	}
	public static String listToString(ArrayList<String> arrList, String delimiter) 
	{
		StringBuffer sb	= new StringBuffer();
		int size		= arrList.size();
		int i			= 1;

		for (Iterator<String> iterator = arrList.iterator(); iterator.hasNext();) 
		{
			String string = (String) iterator.next();

			sb.append(string);

			if(i != size)
				sb.append(delimiter);

			i++;
		}

		return sb.toString();
	}
	public static String secureCode(ArrayList<String> arr) 
	{
		StringBuffer sb		= new StringBuffer();
		SimpleDateFormat sf	= new SimpleDateFormat("yyyyMMdd");
		Calendar cal		= Calendar.getInstance();
		String today		= sf.format(cal.getTime());

		for (Iterator<String> iterator = arr.iterator(); iterator.hasNext();) 
		{
			String string = (String) iterator.next();
			sb.append(string);
		}

		sb.append(today);

		return enCryptor("SHA1", sb.toString());
	}

	
	
	public static String getRetrvNumber(int length) 
	{
		Random random	= new Random();
		char[] digits	= new char[length];

		digits[0] = (char) (random.nextInt(9) + '1');

		for (int i = 1; i < length; i++) 
		{
			digits[i] = (char) (random.nextInt(10) + '0');
		}

		return String.valueOf(digits);
	}

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));

				halfbyte = data[i] & 0x0F;
			} while(two_halfs++ < 1);
		}
		return buf.toString();
	}
	//
	public String MD5(String text)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		byte[] md5hash = new byte[32];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		md5hash = md.digest();
		return convertToHex(md5hash);
	}
	
	public static String uuid() {
		UUID uuid = UUID.randomUUID();

		return uuid.toString();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JasperPrint getObjectPdf(String path, Map parameters, JRDataSource dataSource) {
        JasperPrint jasperPrint = null;

        InputStream inStream = null;
        try {
        	
//            inStream = getClass().getClassLoader().getResourceAsStream(path);
//            JasperDesign jasperDesign = JRXmlLoader.load(path);
            JasperReport jasperReport = JasperCompileManager.compileReport(path);
            jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        } catch (JRException jre) {

        	jre.printStackTrace();
            logger.error(jre.getMessage());
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    logger.error("Error closing stream", e);
                }
            }
        }

        return jasperPrint;
    }
    
   
    public static void sendPdfResponse(HttpServletResponse response, JasperPrint jasperPrint, String fileName){

        //Remove all whitespace from file name
        fileName.replaceAll("\\s","");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);                        byte[] data = out.toByteArray();            response.setContentType("application/pdf");//            response.setContentType("application/vdn.ms-excel");            //To make it a download change "inline" to "attachment"                        response.setHeader("Window-target:","_blank");            response.setHeader("title", "public");                        response.setHeader("Refresh", "1; url = localhost:8080/trx");            response.setHeader("Content-disposition", "inline; filename=" + fileName + ".pdf");            response.setContentLength(data.length);                        response.getOutputStream().write(data);                                                            response.getOutputStream().flush();                                    response.reset();            response.getOutputStream()            .close();
        } catch (JRException e1) {        	System.out.println("lagi");
            e1.printStackTrace();
        }catch (IOException e) {            // TODO Auto-generated catch block        	System.out.println("sama");            e.printStackTrace();        }        
    }
	

}
