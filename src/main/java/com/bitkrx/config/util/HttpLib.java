package com.bitkrx.config.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bitkrx.config.logging.CmeCommonLogger;
import com.bitkrx.config.service.CmeProperties;


public class HttpLib {
	CmeCommonLogger log = new CmeCommonLogger(this.getClass());
	boolean isXmlDebug = Boolean.parseBoolean(CmeProperties.getProperty("Xml.IsDebug"));
	String debugPath = CmeProperties.getProperty("Xml.DebugFilePath");
	String date = "";	
	
	/**
	 * @Method Name  : getData
	 * @작성일   : 2012. 12. 29. 
	 * @작성자   : Kim, YunKwan
	 * @변경이력  :
	 * @Method 설명 : get 방식 HTTP read
	 * @param Url
	 * @param request
	 * @return
	 */
	public String getData(String Url, HttpServletRequest request) {
		String output = "";
		
		String scookie = StringUtils.checkNull(request.getHeader("cookie"));
		String usrAgent = StringUtils.checkNull(request.getHeader("User-Agent"));
		
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHH:mm:ss", new Locale("ko","KOREA"));
			Date nowdate = new Date();
			date = formatter.format(nowdate);
			
			// 연결
			URL aURL = new java.net.URL(Url);
			HttpURLConnection aConnection = (java.net.HttpURLConnection)aURL.openConnection();
			aConnection.setRequestProperty("Cookie", scookie);
			aConnection.setRequestProperty("User-Agent", usrAgent);
			aConnection.setDoOutput(true);
			aConnection.setDoInput(true);
			aConnection.setRequestMethod("GET");
			aConnection.setAllowUserInteraction(false);
			// 데이터를 받아온다.
			InputStream resultStream = aConnection.getInputStream();
			BufferedReader aReader = new java.io.BufferedReader(new java.io.InputStreamReader(resultStream,"utf-8"));
			StringBuffer aResponse = new StringBuffer();
			String aLine = aReader.readLine();

			while(aLine != null)
			{
				aResponse.append(aLine);
				aResponse.append("\r");
				aLine = aReader.readLine();
			}
			resultStream.close();
			output = aResponse.toString();
		
		} 
		catch(Exception ex)
		{
			log.DebugLog("exception:"+ex.getMessage());
			ex.printStackTrace();
		    output = "FAIL";			
		}
		return output;
	}	
	
	/**
	 * @Method Name  : getPostData
	 * @작성일   : 2012. 12. 29. 
	 * @작성자   : Kim, YunKwan
	 * @변경이력  :
	 * @Method 설명 : Http Post 통신
	 * @param url
	 * @param params
	 * @param request
	 * @return
	 */
	public String getPostData(String url, Map<String,Object> params, HttpServletRequest request) {
		String output = "";
		String cookie = "";
		if(request != null)
			cookie = StringUtils.checkNull(request.getHeader("cookie"));
		try {
			String data = ComUtil.MapToParamStr(params);
			URL aURL = new java.net.URL(url);
			HttpURLConnection aConnection = (java.net.HttpURLConnection)aURL.openConnection();
			aConnection.setDoOutput(true);
			aConnection.setDoInput(true);
			aConnection.setRequestMethod("POST");
			aConnection.setRequestProperty("Cookie", cookie);
			aConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			aConnection.setAllowUserInteraction(false);
			// POST Param 파싱
			OutputStreamWriter streamToAuthorize = new java.io.OutputStreamWriter(aConnection.getOutputStream(), "UTF-8");
			streamToAuthorize.write(data);
			streamToAuthorize.flush();
			streamToAuthorize.close();
			// 데이터를 받아온다.
			InputStream resultStream = aConnection.getInputStream();
			BufferedReader aReader = new java.io.BufferedReader(new java.io.InputStreamReader(resultStream,"utf-8"));
			StringBuffer aResponse = new StringBuffer();
			String aLine = aReader.readLine();

			while(aLine != null)
			{
				aResponse.append(aLine);
				aLine = aReader.readLine();
			}
			resultStream.close();
			output = aResponse.toString();

		} catch (Exception e) {
			e.printStackTrace();
			log.ViewErrorLog(e.getMessage());
		}
		return output;
	}

	
}
