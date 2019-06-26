package com.bitkrx.api.trade.vo;

import java.util.List;
import java.util.Map;

public class WithdrowVO {

	private String userEmail = "";
	private String wdrPrc = "";
	private String cnSndFee = "";
	private String regIp = "";
	private String wdrReqAmt = "";
	private String cnKndCd = "";
	private String wdrWletAdr = "";
	private String dealNo = "";
	private String destiTag = "";
	private String feeRealAmt = "";
	private String resultCode = "";
	private String wdrReqCode = "";
	private String status = "";
	private String failRsn = "";
	private String failLog = "";
	private String bankCd = "";
	private String bankAccNo = "";
	private String cmmCd = "";
	private String inCryCode = "";
    private String exCryCode = "";
    private String exRate = "";
    private String outPrc = "";
    private String payMthCd = "";
    private String cardNo = "";
    private String failMsg = "";
    private String uptEmail = "";
    private String clientPe = "";
    private String coinUrl = "";
	private String langCd = "";
	private String privateKey = "";
	private String depositEmail = "";

	public String getDepositEmail() {return depositEmail;}

	public void setDepositEmail(String depositEmail) {this.depositEmail = depositEmail;}

	public String getPrivateKey() {return privateKey;}

	public void setPrivateKey(String privateKey) {this.privateKey = privateKey;}

	public String getLangCd() {return langCd;}

	public void setLangCd(String langCd) {this.langCd = langCd;}

	public String getCoinUrl() {
        return coinUrl;
    }

    public void setCoinUrl(String coinUrl) {
        this.coinUrl = coinUrl;
    }

    private String realSendAmt = "";

    private String sndAutoYn	= "";
    private String sndAutoLimtAmt	= "";

	public String getSndAutoYn() {
		return sndAutoYn;
	}

	public void setSndAutoYn(String sndAutoYn) {this.sndAutoYn = sndAutoYn;}

	public String getSndAutoLimtAmt() {return sndAutoLimtAmt;}

	public void setSndAutoLimtAmt(String sndAutoLimtAmt) {
		this.sndAutoLimtAmt = sndAutoLimtAmt;
	}

	public String getRealSendAmt() {
        return realSendAmt;
    }

    public void setRealSendAmt(String realSendAmt) {
        this.realSendAmt = realSendAmt;
    }

    public String getClientPe() {
        return clientPe;
    }

    public void setClientPe(String clientPe) {
        this.clientPe = clientPe;
    }

    public String getUptEmail() {
        return uptEmail;
    }

    public void setUptEmail(String uptEmail) {
        this.uptEmail = uptEmail;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPayMthCd() {
        return payMthCd;
    }

    public void setPayMthCd(String payMthCd) {
        this.payMthCd = payMthCd;
    }

    public String getInCryCode() {
        return inCryCode;
    }

    public void setInCryCode(String inCryCode) {
        this.inCryCode = inCryCode;
    }

    public String getExCryCode() {
        return exCryCode;
    }

    public void setExCryCode(String exCryCode) {
        this.exCryCode = exCryCode;
    }

    public String getExRate() {
        return exRate;
    }

    public void setExRate(String exRate) {
        this.exRate = exRate;
    }

    public String getOutPrc() {
        return outPrc;
    }

    public void setOutPrc(String outPrc) {
        this.outPrc = outPrc;
    }

    private String curcyNm = "";
	private String userMobile = "";

	public String getCurcyNm() {
		return curcyNm;
	}

	public void setCurcyNm(String curcyNm) {
		this.curcyNm = curcyNm;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getCmmCd() {
		return cmmCd;
	}
	public void setCmmCd(String cmmCd) {
		this.cmmCd = cmmCd;
	}
	public String getBankCd() {
		return bankCd;
	}
	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	private List<Map> RESULT = null;
	
	
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
	 * @return the failRsn
	 */
	public String getFailRsn() {
		return failRsn;
	}
	/**
	 * @param failRsn the failRsn to set
	 */
	public void setFailRsn(String failRsn) {
		this.failRsn = failRsn;
	}
	/**
	 * @return the failLog
	 */
	public String getFailLog() {
		return failLog;
	}
	/**
	 * @param failLog the failLog to set
	 */
	public void setFailLog(String failLog) {
		this.failLog = failLog;
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
	/**
	 * @return the wdrWletAdr
	 */
	public String getWdrWletAdr() {
		return wdrWletAdr;
	}
	/**
	 * @param wdrWletAdr the wdrWletAdr to set
	 */
	public void setWdrWletAdr(String wdrWletAdr) {
		this.wdrWletAdr = wdrWletAdr;
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
	 * @return the wdrPrc
	 */
	public String getWdrPrc() {
		return wdrPrc;
	}
	/**
	 * @param wdrPrc the wdrPrc to set
	 */
	public void setWdrPrc(String wdrPrc) {
		this.wdrPrc = wdrPrc;
	}
	/**
	 * @return the cnSndFee
	 */
	public String getCnSndFee() {
		return cnSndFee;
	}
	/**
	 * @param cnSndFee the cnSndFee to set
	 */
	public void setCnSndFee(String cnSndFee) {
		this.cnSndFee = cnSndFee;
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
	 * @return the destiTag
	 */
	public String getDestiTag() {
		return destiTag;
	}
	/**
	 * @param destiTag the destiTag to set
	 */
	public void setDestiTag(String destiTag) {
		this.destiTag = destiTag;
	}
	/**
	 * @return the feeRealAmt
	 */
	public String getFeeRealAmt() {
		return feeRealAmt;
	}
	/**
	 * @param feeRealAmt the feeRealAmt to set
	 */
	public void setFeeRealAmt(String feeRealAmt) {
		this.feeRealAmt = feeRealAmt;
	}
	/**
	 * @return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}
	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
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
	
}
