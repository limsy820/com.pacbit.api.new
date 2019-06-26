/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.menu.vo;

import java.util.List;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.menu.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 15.
 */
public class MenuVO {

	private String userEmail = "";
	private String menuCd = "";
	private int sn = 0;
	private String status = "";
	private List RESULT = null;
	private String regDt = "";
	private String mkState = "";

	public String getMkState() { return mkState; }

	public void setMkState(String mkState) { this.mkState = mkState; }

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the rESULT
	 */
	public List getRESULT() {
		return RESULT;
	}
	/**
	 * @param rESULT the rESULT to set
	 */
	public void setRESULT(List rESULT) {
		RESULT = rESULT;
	}
	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}
	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	/**
	 * @return the menuCd
	 */
	public String getMenuCd() {
		return menuCd;
	}
	/**
	 * @param menuCd the menuCd to set
	 */
	public void setMenuCd(String menuCd) {
		this.menuCd = menuCd;
	}
	/**
	 * @return the sn
	 */
	public int getSn() {
		return sn;
	}
	/**
	 * @param sn the sn to set
	 */
	public void setSn(int sn) {
		this.sn = sn;
	}
}
