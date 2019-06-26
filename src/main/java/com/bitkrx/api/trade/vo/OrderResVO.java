/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.trade.vo;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.trade.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 9.
 */
public class OrderResVO {

	private String userEmail = "";
	private String oppEmail = "";
	private String cnPrc = "";
	private String cnAmt = "";
	private String totPrc = "";
	private String cnPhsCode = "";
	private String cnSelCode = "";
	private String fee = "";
	private String apntYn = "";
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
	 * @return the oppEmail
	 */
	public String getOppEmail() {
		return oppEmail;
	}
	/**
	 * @param oppEmail the oppEmail to set
	 */
	public void setOppEmail(String oppEmail) {
		this.oppEmail = oppEmail;
	}
	/**
	 * @return the cnPrc
	 */
	public String getCnPrc() {
		return cnPrc;
	}
	/**
	 * @param cnPrc the cnPrc to set
	 */
	public void setCnPrc(String cnPrc) {
		this.cnPrc = cnPrc;
	}
	/**
	 * @return the cnAmt
	 */
	public String getCnAmt() {
		return cnAmt;
	}
	/**
	 * @param cnAmt the cnAmt to set
	 */
	public void setCnAmt(String cnAmt) {
		this.cnAmt = cnAmt;
	}
	/**
	 * @return the totPrc
	 */
	public String getTotPrc() {
		return totPrc;
	}
	/**
	 * @param totPrc the totPrc to set
	 */
	public void setTotPrc(String totPrc) {
		this.totPrc = totPrc;
	}
	/**
	 * @return the cnPhsCode
	 */
	public String getCnPhsCode() {
		return cnPhsCode;
	}
	/**
	 * @param cnPhsCode the cnPhsCode to set
	 */
	public void setCnPhsCode(String cnPhsCode) {
		this.cnPhsCode = cnPhsCode;
	}
	/**
	 * @return the cnSelCode
	 */
	public String getCnSelCode() {
		return cnSelCode;
	}
	/**
	 * @param cnSelCode the cnSelCode to set
	 */
	public void setCnSelCode(String cnSelCode) {
		this.cnSelCode = cnSelCode;
	}
	/**
	 * @return the fee
	 */
	public String getFee() {
		return fee;
	}
	/**
	 * @param fee the fee to set
	 */
	public void setFee(String fee) {
		this.fee = fee;
	}
	/**
	 * @return the apntYn
	 */
	public String getApntYn() {
		return apntYn;
	}
	/**
	 * @param apntYn the apntYn to set
	 */
	public void setApntYn(String apntYn) {
		this.apntYn = apntYn;
	}
	
	
}
