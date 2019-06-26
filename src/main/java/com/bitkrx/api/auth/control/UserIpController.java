/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.auth.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitkrx.api.auth.service.UserIpService;
import com.bitkrx.api.auth.vo.UserIpVO;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.auth.control
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 30.
 */
@Controller
@RequestMapping(value="/bt")
public class UserIpController extends CmeDefaultExtendController{
	
	@Autowired
	UserIpService ipService;
	
	@RequestMapping(value="/insUptUserIp")
	public @ResponseBody CmeResultVO insUptUserIp(UserIpVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CmeResultVO rvo = new CmeResultVO();
		rvo.setResultStrCode("-1");
		if("".equals(vo.getUserEmail())) {
			rvo.setResultMsg("사용자 이메일이 없습니다.");
			return rvo;
		} else if ("".equals(vo.getIp())) {
			rvo.setResultMsg("아이피가 없습니다.");
			return rvo;
		} else if ("".equals(vo.getLimtHr())) {
			vo.setLimtHr("99999");
		}
		
		int res = ipService.insUptIpList(vo);
		if(res > 0) {
			rvo.setResultStrCode("000");
			rvo.setResultMsg("등록이 완료되었습니다.");
		}
		return rvo;
	}
	
	
	@RequestMapping(value="/getUserIpList")
	public @ResponseBody CmeResultVO getUserIpList(UserIpVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CmeResultVO rvo = new CmeResultVO();
		rvo.setResultStrCode("-1");
		if("".equals(vo.getUserEmail())) {
			rvo.setResultMsg("사용자 이메일이 없습니다.");
			return rvo;
		} 
		
		List<UserIpVO> list = ipService.selectUserIpList(vo);
		if(list.size() > 0) {
			rvo.setResultStrCode("000");
			rvo.setResultMsg("IP 리스트 조회 완료.");
		} else {
			list = new ArrayList<UserIpVO>();
			rvo.setResultStrCode("000");
			rvo.setResultMsg("IP 리스트가 없습니다.");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		rvo.setData(map);
		return rvo;
	}
	
}
