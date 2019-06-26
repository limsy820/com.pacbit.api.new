/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.menu.service;

import java.util.List;

import com.bitkrx.api.menu.vo.MenuResVO;
import com.bitkrx.api.menu.vo.MenuVO;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.menu.service
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 15.
 */
public interface MenuService {

	public MenuVO insUptMenu(MenuVO vo) throws Exception;
	
	public int deleteMenu(String userEmail) throws Exception;
	
	public List<MenuResVO> getMenuList(MenuVO vo) throws Exception;

	public String getSN(String userEmail) throws Exception;

	public void insMtsMenu(MenuVO vo) throws Exception;

	public void delMtsMenu(MenuVO vo) throws Exception;

	public List<MenuVO> getMtsMenuList(MenuVO vo) throws Exception;
}
