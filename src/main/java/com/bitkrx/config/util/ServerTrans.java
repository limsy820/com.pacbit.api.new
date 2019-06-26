package com.bitkrx.config.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.vo.SendInfoVO;


public class ServerTrans extends CmeDefaultExtendController{
   
   public static final String HttpRootContext = "Dev.bitkrx.com";
   
   
   private static ServerTrans serverTrans = null;
   
   public static ServerTrans getinstance(){
      synchronized(ServerTrans.class){
         if(serverTrans == null){
            serverTrans = new ServerTrans();
         }
         return serverTrans;
      }
   }   
   

   
   public String BizSender(SendInfoVO vo, HttpServletRequest request) throws Exception {

			
	   String responseData = "";
	   
	   try {
			String url = "http://www.bizmailer.co.kr/bizsmart/action/auto.do";
			debugLog(url);

			HttpLib httpUtil = new HttpLib();
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("m_email", vo.getEmail_info());
			params.put("m_nm", vo.getName_info());
			params.put("m_mobile", vo.getMobile_info());
			params.put("m_memo1", vo.getEtc1());
			params.put("m_memo2", vo.getEtc2());
			params.put("m_memo3", vo.getEtc3());
			params.put("m_memo4", vo.getEtc4());		
			params.put("m_memo5", vo.getEtc5());		
			params.put("auth_key", vo.getAuth_key());
			params.put("biz_id", "cmesoft");
			
			responseData =  httpUtil.getPostData(url, params, request);		
			debugLog("MailSend retuenDATA::====================> Res : " + responseData);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return responseData;
	}
   
}