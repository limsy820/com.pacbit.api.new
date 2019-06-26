/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.auth.vo;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.auth.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 30.
 */
public class UserIpVO {

	private String userEmail = "";
	private String ip = "";
	private String limtHr = "";
	private String loginTm = "";
	private String stdDt = "";
	private String endDt = "";
	private String regIp = "";

	public String getRegIp() {return regIp;}

	public void setRegIp(String regIp) {this.regIp = regIp;}

	/**
	 * @return the stdDt
	 */
	public String getStdDt() {
		return stdDt;
	}
	/**
	 * @param stdDt the stdDt to set
	 */
	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}
	/**
	 * @return the endDt
	 */
	public String getEndDt() {
		return endDt;
	}
	/**
	 * @param endDt the endDt to set
	 */
	public void setEndDt(String endDt) {
		this.endDt = endDt;
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
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the limtHr
	 */
	public String getLimtHr() {
		return limtHr;
	}
	/**
	 * @param limtHr the limtHr to set
	 */
	public void setLimtHr(String limtHr) {
		this.limtHr = limtHr;
	}
	/**
	 * @return the loginTm
	 */
	public String getLoginTm() {
		return loginTm;
	}
	/**
	 * @param loginTm the loginTm to set
	 */
	public void setLoginTm(String loginTm) {
		this.loginTm = loginTm;
	}
	
}
