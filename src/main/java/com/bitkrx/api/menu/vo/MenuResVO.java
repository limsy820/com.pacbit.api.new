/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.menu.vo;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.menu.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 15.
 */
public class MenuResVO {

	private String menuCd = "";
	private int sn = 0;
	
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
