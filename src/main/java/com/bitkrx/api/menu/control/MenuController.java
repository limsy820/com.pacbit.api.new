/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.menu.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitkrx.api.auth.service.RTPService;
import com.bitkrx.api.menu.service.MenuService;
import com.bitkrx.api.menu.vo.MenuResVO;
import com.bitkrx.api.menu.vo.MenuVO;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.exception.HTTPErrorHander;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.menu.control
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 15.
 */
@Controller
@RequestMapping(value = "/bt/menu")
public class MenuController extends CmeDefaultExtendController{
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	RTPService rService;
	
	@RequestMapping(value = "/insUptMenu.dm")
	public @ResponseBody CmeResultVO insUptMenu(@ModelAttribute MenuVO vo,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		rService.RTPVertify(request);
		
		CmeResultVO res = new CmeResultVO();
		if(vo.getUserEmail().equals("")) {
			res.setResultMsg("사용자 이메일이 없습니다.");
			res.setResultStrCode("-1");
		} else if(vo.getMenuCd().equals("")) {
			res.setResultMsg("메뉴코드가 없습니다.");
			res.setResultStrCode("-1");
		}
		
		menuService.deleteMenu(vo.getUserEmail());
		
		String[] menus = vo.getMenuCd().split(",");
		for(int i = 1; i <= menus.length; i++) {
			String menu = menus[i - 1];
			vo.setMenuCd(menu);
			vo.setSn(i);
			menuService.insUptMenu(vo);
			List list = (List) vo.getRESULT();
			Map map = (Map) list.get(0);
			String rtnCd = (String) map.get("RTN_CD");
			if(rtnCd != null && rtnCd.equals("1")) {
				res.setResultMsg("즐겨찾기 입력/수정 완료");
				res.setResultStrCode("000");
			} else {
				res.setResultMsg( (String) map.get("RNT_MSG"));
				res.setResultStrCode(rtnCd);
				return res;
			}
			
		}
		
		return res;
	}
	
	@RequestMapping(value = "/getMenuList")
	public @ResponseBody CmeResultVO getMenuList(@ModelAttribute MenuVO vo,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		rService.RTPVertify(request);
		
		CmeResultVO res = new CmeResultVO();
		if(vo.getUserEmail().equals("")) {
			res.setResultMsg("사용자 이메일이 없습니다.");
			res.setResultStrCode("-1");
		}
		List<MenuResVO> list = menuService.getMenuList(vo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		res.setData(map);
		res.setResultStrCode("000");
		res.setResultMsg("즐겨찾기 조회 완료");
		return res;
	}

	@RequestMapping(value = "/insUptMtsMenu.dm")
	public @ResponseBody CmeResultVO MtsinsUptMenu(@ModelAttribute MenuVO vo,
												HttpServletRequest request,
												HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);

		CmeResultVO resultVO = new CmeResultVO();
		Map<String , Object> resultMap = new HashMap<String,Object>();
		resultVO.setResultStrCode("000");
		resultMap.put("failCd", "");
		if("".equals(vo.getUserEmail())){
			resultMap.put("failCd", "999");
			resultVO.setResultMsg("사용자 이메일이 없습니다.");
			resultVO.setData(resultMap);
			return resultVO;
		}else if("".equals(vo.getMenuCd())){
			resultMap.put("failCd", "998");
			resultVO.setResultMsg("메뉴코드가 없습니다.");
			resultVO.setData(resultMap);
			return resultVO;
		}else if("".equals(vo.getStatus())){
			resultMap.put("failCd", "997");
			resultVO.setResultMsg("상태값이 없습니다.");
			resultVO.setData(resultMap);
			return resultVO;
		}else if("".equals(vo.getMkState()) || vo.getMkState() == null){
			resultMap.put("failCd", "996");
			resultVO.setResultMsg("마켓값이 없습니다.");
			resultVO.setData(resultMap);
			return resultVO;
		}

		String getSN = menuService.getSN(vo.getUserEmail());
		//System.out.println(getSN);
		int getSNUM = 0;

		if(getSN != null){
			getSNUM = Integer.parseInt(getSN);
		}
		//System.out.println(getSNUM);
		if("01".equals(vo.getStatus())){
			if("".equals(getSNUM)){
				vo.setSn(0);
				menuService.insMtsMenu(vo);
				resultVO.setResultMsg("즐겨찾기 추가 성공");
			}else{
				vo.setSn(getSNUM);
				menuService.insMtsMenu(vo);
				resultVO.setResultMsg("즐겨찾기 추가 성공");
			}
		}else if("02".equals(vo.getStatus())){
			menuService.delMtsMenu(vo);
			resultVO.setResultMsg("즐겨찾기 삭제 성공");
		}

		resultVO.setData(resultMap);
		return resultVO;
	}

	@RequestMapping(value = "/getMtsMenuList")
	public @ResponseBody CmeResultVO getMtsMenuList(@ModelAttribute MenuVO vo,
												 HttpServletRequest request,
												 HttpServletResponse response) throws Exception {
		//rService.RTPVertify(request);

		CmeResultVO resultVO = new CmeResultVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultVO.setResultStrCode("000");
		resultMap.put("failCd", "");

		if("".equals(vo.getUserEmail())) {
			resultVO.setResultMsg("사용자 이메일이 없습니다.");
			resultMap.put("failCd", "999");
			resultVO.setData(resultMap);
			return resultVO;
		}

		List<MenuVO> list = menuService.getMtsMenuList(vo);
		resultMap.put("list",list);
		resultVO.setResultMsg("즐겨찾기 조회 완료");
        resultVO.setData(resultMap);
		return resultVO;
	}
}
