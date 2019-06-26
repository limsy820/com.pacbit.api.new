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
 * @작성일		: 2018. 1. 18.
 */
public class KrwWithVO {

	private String userEmail = "";
	private String bankNm = "";
	private String bankAccNo = "";
	private String accntNm = "";
	private String krwPrc = "";
	private String bankCd = "";
	private String exRate = "";
	private String cryCode = "";
	private String krwPrcCard = "";
	private String userMobile = "";
	private String certYn	= "";
	private String useYn	= "";
	private String wdrReqCode = "";
	private String withdrowYn = "";
	private String failMsg	  = "";
	private String outPrc 	  = "";
	private String regIp	  = "";

	public String getRegIp() {return regIp;}

	public void setRegIp(String regIp) {this.regIp = regIp;}

	public String getOutPrc() {return outPrc;}

	public void setOutPrc(String outPrc) {this.outPrc = outPrc;}

	public String getFailMsg() {return failMsg;}

	public void setFailMsg(String failMsg) {this.failMsg = failMsg;}

	public String getWithdrowYn() {return withdrowYn;}

	public void setWithdrowYn(String withdrowYn) {this.withdrowYn = withdrowYn;}

	public String getWdrReqCode() {return wdrReqCode;}

	public void setWdrReqCode(String wdrReqCode) {this.wdrReqCode = wdrReqCode;}

	public String getCertYn() {return certYn;}

	public void setCertYn(String certYn) {this.certYn = certYn;}

	public String getUseYn() {return useYn;}

	public void setUseYn(String useYn) {this.useYn = useYn;}

	public String getUserEmail() {return userEmail;}

	public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

	public String getUserMobile() {return userMobile;}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getKrwPrcCard() {
        return krwPrcCard;
    }

    public void setKrwPrcCard(String krwPrcCard) {
        this.krwPrcCard = krwPrcCard;
    }

    public String getExRate() {
        return exRate;
    }

    public void setExRate(String exRate) {
        this.exRate = exRate;
    }

    public String getCryCode() {
        return cryCode;
    }

    public void setCryCode(String cryCode) {
        this.cryCode = cryCode;
    }

    /**
	 * @return the bankNm
	 */
	public String getBankNm() {
		return bankNm;
	}
	/**
	 * @param bankNm the bankNm to set
	 */
	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}
	/**
	 * @return the bankAccNo
	 */
	public String getBankAccNo() {
		return bankAccNo;
	}
	/**
	 * @param bankAccNo the bankAccNo to set
	 */
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	/**
	 * @return the accntNm
	 */
	public String getAccntNm() {
		return accntNm;
	}
	/**
	 * @param accntNm the accntNm to set
	 */
	public void setAccntNm(String accntNm) {
		this.accntNm = accntNm;
	}
	/**
	 * @return the krwPrc
	 */
	public String getKrwPrc() {
		return krwPrc;
	}
	/**
	 * @param krwPrc the krwPrc to set
	 */
	public void setKrwPrc(String krwPrc) {
		this.krwPrc = krwPrc;
	}
	/**
	 * @return the bankCd
	 */
	public String getBankCd() {
		return bankCd;
	}
	/**
	 * @param bankCd the bankCd to set
	 */
	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}
	
	
}
