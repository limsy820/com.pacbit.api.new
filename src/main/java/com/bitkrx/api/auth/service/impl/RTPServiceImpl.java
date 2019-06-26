/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.auth.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitkrx.api.auth.dao.RTPDAO;
import com.bitkrx.api.auth.service.RTPService;
import com.bitkrx.config.annotation.CommonDataSource;
import com.bitkrx.config.dbinfo.DataSourceType;
import com.bitkrx.config.exception.CmeApplicationException;
import com.bitkrx.config.logging.CmeCommonLogger;
import com.bitkrx.core.util.RtpUtils;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.auth.service.impl
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 12. 5.
 */
@Service
public class RTPServiceImpl implements RTPService{

	@Autowired
    RTPDAO rtpDAO;
    
	RtpUtils rtpUtils = RtpUtils.getinstance();
	
	protected CmeCommonLogger log = new CmeCommonLogger(this.getClass());
	
	public void RTPVertify(HttpServletRequest request) throws Exception {
		
		if(request.getRequestURL().indexOf("localhost") == -1) {
			
			boolean isRTP = false;
			String code = request.getParameter("param3");
			if(code != null && !code.equals("")) {
				isRTP = rtpUtils.vertify(code, rtpDAO.getMilliTime());
				//log.ViewErrorLog("code1 ::: " + code);
				code = rtpUtils.create(rtpDAO.getMilliTime());
				//log.ViewErrorLog("code2 ::: " + code);
			}
			if(!isRTP) throw new CmeApplicationException("비정상적인 접근입니다 : "+request.getRequestURI());
			
		}
		
	}
}
