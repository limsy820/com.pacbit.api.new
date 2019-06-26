package com.bitkrx.api.trade.vo;

public class WithCoinListVO {

	private String wdrReqCode = "";/*송금신청코드*/
	private String wdrReqAmt = "";/*송금액*/
	private String feeAmt = "";/*송금수수료*/
	private String status = "";/*상태*/
	private String regDt = "";/*승인완료일*/
	private String dealNo = "";/*거래번호*/
	private String cnKndCd = "";
	private String wdrWletAdr = "";
	private String userEmail = "";
	private String regIp = "";
	private String destiTag = "";
	private String cnSndFee = "";
	private String exFlag   = "";
	private String uptDt	= "";

	public String getUptDt() {return uptDt;}

	public void setUptDt(String uptDt) {this.uptDt = uptDt;}

	public String getExFlag() {return exFlag;}

	public void setExFlag(String exFlag) {this.exFlag = exFlag;}

	public String getCnSndFee() {
        return cnSndFee;
    }

    public void setCnSndFee(String cnSndFee) {
        this.cnSndFee = cnSndFee;
    }

    /**
	 * @return the wdrReqCode
	 */
	public String getWdrReqCode() {
		return wdrReqCode;
	}
	/**
	 * @param wdrReqCode the wdrReqCode to set
	 */
	public void setWdrReqCode(String wdrReqCode) {
		this.wdrReqCode = wdrReqCode;
	}
	/**
	 * @return the wdrReqAmt
	 */
	public String getWdrReqAmt() {
		return wdrReqAmt;
	}
	/**
	 * @param wdrReqAmt the wdrReqAmt to set
	 */
	public void setWdrReqAmt(String wdrReqAmt) {
		this.wdrReqAmt = wdrReqAmt;
	}
	/**
	 * @return the feeAmt
	 */
	public String getFeeAmt() {
		return feeAmt;
	}
	/**
	 * @param feeAmt the feeAmt to set
	 */
	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return regDt;
	}
	/**
	 * @param regDt the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	/**
	 * @return the dealNo
	 */
	public String getDealNo() {
		return dealNo;
	}
	/**
	 * @param dealNo the dealNo to set
	 */
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
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
	public String getWdrWletAdr() {
		return wdrWletAdr;
	}
	public void setWdrWletAdr(String wdrWletAdr) {
		this.wdrWletAdr = wdrWletAdr;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getRegIp() {
		return regIp;
	}
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	public String getDestiTag() {
		return destiTag;
	}
	public void setDestiTag(String destiTag) {
		this.destiTag = destiTag;
	}
	
	
	
}
