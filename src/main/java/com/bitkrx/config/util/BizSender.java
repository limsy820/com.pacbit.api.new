/**
 * (주)크림스 의 시스템 웹어플리케이션 프레임웍 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)크림스
 * CopyRight Creams. inc. Since 2015 
 * 총괄 개발 책임자 : 주식회사 크림스 통합개발연구소 소장 김윤관
 */
package com.bitkrx.config.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.vo.SendInfoVO;


/**
 * @프로젝트명	: cme.web.ApiAPIS
 * @패키지    	: cme.com.web.common.util
 * @클래스명  	: cme.web.ApiAPIS
 * @작성자		: (주)씨엠이소프트 최종근
 * @작성일		: 2016. 12. 3.
 */
public class BizSender extends CmeDefaultExtendController{
	
	private static BizSender bizSender = null;
	
	@Autowired(required=true)
	HttpServletRequest request;
	
	public static BizSender getinstance(){
		synchronized(BizSender.class){
			if(bizSender == null){
				bizSender = new BizSender();
			}
			return bizSender;
		}
	}	
	
	public void sendMailSms(SendInfoVO vo) throws Exception{
		
		ServerTrans tran = ServerTrans.getinstance();			
		tran.BizSender(vo, request);
		
	}

}
