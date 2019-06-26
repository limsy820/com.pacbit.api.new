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
 * @작성일		: 2017. 12. 29.
 */
public class KrwDepositVO {
	private String reqDt	=	"";
	private String payKndCd	=	"";
	private String crgPrc	=	"";
	private String state	=	"";
	private String compDt	=	"";
    private String inCryCode=   "";
    private String inAmt    =   "";
    private String exRate   =   "";
    private String exCryCode=   "";
    private String cardNum  =   "";
    private String dptFee   =   "";

    public String getDptFee() {
        return dptFee;
    }

    public void setDptFee(String dptFee) {
        this.dptFee = dptFee;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getInCryCode() {
        return inCryCode;
    }

    public void setInCryCode(String inCryCode) {
        this.inCryCode = inCryCode;
    }

    public String getInAmt() {
        return inAmt;
    }

    public void setInAmt(String inAmt) {
        this.inAmt = inAmt;
    }

    public String getExRate() {
        return exRate;
    }

    public void setExRate(String exRate) {
        this.exRate = exRate;
    }

    public String getExCryCode() {
        return exCryCode;
    }

    public void setExCryCode(String exCryCode) {
        this.exCryCode = exCryCode;
    }

    /**
	 * @return the reqDt
	 */
	public String getReqDt() {
		return reqDt;
	}
	/**
	 * @param reqDt the reqDt to set
	 */
	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}
	/**
	 * @return the payKndCd
	 */
	public String getPayKndCd() {
		return payKndCd;
	}
	/**
	 * @param payKndCd the payKndCd to set
	 */
	public void setPayKndCd(String payKndCd) {
		this.payKndCd = payKndCd;
	}
	/**
	 * @return the crgPrc
	 */
	public String getCrgPrc() {
		return crgPrc;
	}
	/**
	 * @param crgPrc the crgPrc to set
	 */
	public void setCrgPrc(String crgPrc) {
		this.crgPrc = crgPrc;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the compDt
	 */
	public String getCompDt() {
		return compDt;
	}
	/**
	 * @param compDt the compDt to set
	 */
	public void setCompDt(String compDt) {
		this.compDt = compDt;
	}

}
