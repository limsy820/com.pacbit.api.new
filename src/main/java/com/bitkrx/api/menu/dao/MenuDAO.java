/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.menu.dao;

import java.util.List;

import com.bitkrx.api.mail.vo.MailVO;
import org.springframework.stereotype.Repository;

import com.bitkrx.api.menu.vo.MenuResVO;
import com.bitkrx.api.menu.vo.MenuVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.menu.dao
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 15.
 */
@Repository
public class MenuDAO extends CmeComAbstractDAO{

	public MenuVO insUptMenu(MenuVO vo) throws Exception {
		return (MenuVO) convertProc("menuDAO.PR_INSUPT10172010", vo);
	}
	
	public int deleteMenu(String userEmail) throws Exception {
		return delete("menuDAO.deleteMenu", userEmail);
	}
	public List<MenuResVO> getMenuList(MenuVO vo) throws Exception {
		return list("menuDAO.getMenuList", vo);
	}

	public List<MailVO> SendAdminAllMail(MailVO mvo) throws Exception{
		return list("menuDAO.SendAdminAllMail" , mvo);
	}

	public String getSN(String userEmail) throws Exception{
		return (String)selectByPk("menuDAO.getSN" , userEmail);
	}

	public void insMtsMenu(MenuVO vo) throws Exception{
		insert("menuDAO.insMtsMenu" , vo);
	}

	public void delMtsMenu(MenuVO vo) throws Exception{
		delete("menuDAO.delMtsMenu", vo);
	}

	public List<MenuVO> getMtsMenuList(MenuVO vo) throws Exception{
		return list("menuDAO.getMtsMenuList", vo);
	}
}
