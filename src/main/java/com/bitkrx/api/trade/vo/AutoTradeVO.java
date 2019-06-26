/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.trade.vo;

import java.util.List;
import java.util.Map;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.trade.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 22.
 */
public class AutoTradeVO {

	private String cnKndCd = "";
	private String userEmail = "";
	private String regIp = "";
	private String sn = "";
	private String udFlag = "";
	private String prc = "";
	private String prcPer = "";
	private String rtPlsPrc = "";
	private String rtMnsPrc = "";
	private String amt = "";
	private String mkState = "";

	public String getMkState() {
		return mkState;
	}

	public void setMkState(String mkState) {
		this.mkState = mkState;
	}

	private List<Map> RESULT 		= null;

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
	 * @return the regIp
	 */
	public String getRegIp() {
		return regIp;
	}

	/**
	 * @param regIp the regIp to set
	 */
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	/**
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * @param sn the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * @return the udFlag
	 */
	public String getUdFlag() {
		return udFlag;
	}

	/**
	 * @param udFlag the udFlag to set
	 */
	public void setUdFlag(String udFlag) {
		this.udFlag = udFlag;
	}

	/**
	 * @return the prc
	 */
	public String getPrc() {
		return prc;
	}

	/**
	 * @param prc the prc to set
	 */
	public void setPrc(String prc) {
		this.prc = prc;
	}

	/**
	 * @return the prcPer
	 */
	public String getPrcPer() {
		return prcPer;
	}

	/**
	 * @param prcPer the prcPer to set
	 */
	public void setPrcPer(String prcPer) {
		this.prcPer = prcPer;
	}

	/**
	 * @return the rtPlsPrc
	 */
	public String getRtPlsPrc() {
		return rtPlsPrc;
	}

	/**
	 * @param rtPlsPrc the rtPlsPrc to set
	 */
	public void setRtPlsPrc(String rtPlsPrc) {
		this.rtPlsPrc = rtPlsPrc;
	}

	/**
	 * @return the rtMnsPrc
	 */
	public String getRtMnsPrc() {
		return rtMnsPrc;
	}

	/**
	 * @param rtMnsPrc the rtMnsPrc to set
	 */
	public void setRtMnsPrc(String rtMnsPrc) {
		this.rtMnsPrc = rtMnsPrc;
	}

	/**
	 * @return the amt
	 */
	public String getAmt() {
		return amt;
	}

	/**
	 * @param amt the amt to set
	 */
	public void setAmt(String amt) {
		this.amt = amt;
	}

	/**
	 * @return the rESULT
	 */
	public List<Map> getRESULT() {
		return RESULT;
	}

	/**
	 * @param rESULT the rESULT to set
	 */
	public void setRESULT(List<Map> rESULT) {
		RESULT = rESULT;
	}

	/**
	 * @return the cnKndCd
	 */
	public String getCnKndCd() {
		return cnKndCd;
	}

	/**
	 * @param cnKndCd the cnKndCd to set
	 */
	public void setCnKndCd(String cnKndCd) {
		this.cnKndCd = cnKndCd;
	}
	
    
    
}
