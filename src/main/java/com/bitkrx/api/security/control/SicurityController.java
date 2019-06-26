/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.security.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitkrx.api.security.vo.SicurityVO;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.exception.HTTPErrorHander;
import com.bitkrx.config.util.Security;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.security.control
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 27.
 */
@Controller
@RequestMapping(value="/bt")
public class SicurityController extends CmeDefaultExtendController{

	Security security = Security.getinstance();
	
	@RequestMapping(value = "/security/getPublicKey.dm")
	public @ResponseBody CmeResultVO loginFail1(SicurityVO vo, HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		CmeResultVO res = new CmeResultVO();
		
		res.setResultMsg("public key");
		res.setResultStrCode("000");
		
		vo.setPubKeyExponent((String) security.getMap().get("pubKeyExponent"));
		vo.setPubKeyModule((String) security.getMap().get("pubKeyModule"));
    	
		res.setData(vo);
		
		return res;
	}
	
																																																														// 대한
											 																																																			// 예외처리
	@RequestMapping(value = "/security/getDnc.dm")
	public @ResponseBody CmeResultVO getDnc(SicurityVO vo, HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		CmeResultVO res = new CmeResultVO();
		
		String aceKey = request.getParameter("aceKey");
		String encStr = request.getParameter("encStr");
		
		String decStr = security.decrypt(encStr, aceKey);
		
		res.setResultMsg("복호화 테스트 값 : " + decStr);
		res.setResultStrCode("000");
		
    	
		res.setData(decStr);
		
		return res;
	}																																																													// 메소드
												
}
