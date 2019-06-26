package com.bitkrx.api.trade.vo;

public class CmeTradeResVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3505880388658286409L;
	
	private String orderNo								=	"";		//주문번호
	private String tradeTime							=	"";		//거래시각
	private String compDate								=	"";		//완료일시
	private String tradeFee								=	"";		//수수료
	private String cnAmt								=	"";		//주문량
	private String coinPrc								=	"";		//코인가격
	private String tradeDesc							=	"";		//거래내용
	private String tradePrc								=	"";		//금액
	private String feeCurcyCd							=	"";		//수수료통화
	private String hisCode								=	"";		//구분
	private String curcyCd								=	"";		//통화
	private String orderState							=	"";		//상태
	private String tradeAmt								=	"";
	private String regDate								=	"";
	private String apntYn								=	"";
	private String orderDesc								=	"";
	private String exFlag = "";
	private String userEmail = "";
	private String selTradePrc = "";
	private String buyTradePrc = "";
	private String feeAmt = "";
	private String feePrc = "";
	private String apnt_yn = "";
	private String apnt_phs_yn = "";
	private String apnt_sel_yn = "";
	private String totPrc = "";
	private String payKndCd = "";
	private String regDt = "";
	private String fee = "";
    private String cryCode = "";
    private String exRate = "";
    private String reqAmt = "";
    private String curcyNm =   "";
    private String status = "";
    private String failMsg = "";
	private String key01	= "";
	private String key02 	= "";

	public String getKey01() {return key01;}

	public void setKey01(String key01) {this.key01 = key01;}

	public String getKey02() {return key02;}

	public void setKey02(String key02) {this.key02 = key02;}

	public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurcyNm() {
        return curcyNm;
    }

    public void setCurcyNm(String curcyNm) {
        this.curcyNm = curcyNm;
    }
    public String getCryCode() {
        return cryCode;
    }

    public void setCryCode(String cryCode) {
        this.cryCode = cryCode;
    }

    public String getExRate() {
        return exRate;
    }

    public void setExRate(String exRate) {
        this.exRate = exRate;
    }

    public String getReqAmt() {
        return reqAmt;
    }

    public void setReqAmt(String reqAmt) {
        this.reqAmt = reqAmt;
    }

    /**
	 * @return the tradePrc
	 */
	public String getTradePrc() {
		return tradePrc;
	}

	/**
	 * @param tradePrc the tradePrc to set
	 */
	public void setTradePrc(String tradePrc) {
		this.tradePrc = tradePrc;
	}

	/**
	 * @return the feeCurcyCd
	 */
	public String getFeeCurcyCd() {
		return feeCurcyCd;
	}

	/**
	 * @param feeCurcyCd the feeCurcyCd to set
	 */
	public void setFeeCurcyCd(String feeCurcyCd) {
		this.feeCurcyCd = feeCurcyCd;
	}

	public String getHisCode() {
		return hisCode;
	}

	public void setHisCode(String hisCode) {
		this.hisCode = hisCode;
	}

	public String getCurcyCd() {
		return curcyCd;
	}

	public void setCurcyCd(String curcyCd) {
		this.curcyCd = curcyCd;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getCompDate() {
		return compDate;
	}

	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}

	public String getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(String tradeFee) {
		this.tradeFee = tradeFee;
	}

	public String getCnAmt() {
		return cnAmt;
	}

	public void setCnAmt(String cnAmt) {
		this.cnAmt = cnAmt;
	}

	public String getCoinPrc() {
		return coinPrc;
	}

	public void setCoinPrc(String coinPrc) {
		this.coinPrc = coinPrc;
	}

	public String getTradeDesc() {
		return tradeDesc;
	}

	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	/**
	 * @return the tradeAmt
	 */
	public String getTradeAmt() {
		return tradeAmt;
	}

	/**
	 * @param tradeAmt the tradeAmt to set
	 */
	public void setTradeAmt(String tradeAmt) {
		this.tradeAmt = tradeAmt;
	}

	public String getExFlag() {
		return exFlag;
	}

	public void setExFlag(String exFlag) {
		this.exFlag = exFlag;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getSelTradePrc() {
		return selTradePrc;
	}

	public void setSelTradePrc(String selTradePrc) {
		this.selTradePrc = selTradePrc;
	}

	public String getBuyTradePrc() {
		return buyTradePrc;
	}

	public void setBuyTradePrc(String buyTradePrc) {
		this.buyTradePrc = buyTradePrc;
	}

	public String getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getFeePrc() {
		return feePrc;
	}

	public void setFeePrc(String feePrc) {
		this.feePrc = feePrc;
	}

	public String getApnt_yn() {
		return apnt_yn;
	}

	public void setApnt_yn(String apnt_yn) {
		this.apnt_yn = apnt_yn;
	}

	public String getApnt_phs_yn() {
		return apnt_phs_yn;
	}

	public void setApnt_phs_yn(String apnt_phs_yn) {
		this.apnt_phs_yn = apnt_phs_yn;
	}

	public String getApnt_sel_yn() {
		return apnt_sel_yn;
	}

	public void setApnt_sel_yn(String apnt_sel_yn) {
		this.apnt_sel_yn = apnt_sel_yn;
	}

	public String getTotPrc() {
		return totPrc;
	}

	public void setTotPrc(String totPrc) {
		this.totPrc = totPrc;
	}

    public String getPayKndCd() {
        return payKndCd;
    }

    public void setPayKndCd(String payKndCd) {
        this.payKndCd = payKndCd;
    }

    public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
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

	/**
	 * @return the orderDesc
	 */
	public String getOrderDesc() {
		return orderDesc;
	}

	/**
	 * @param orderDesc the orderDesc to set
	 */
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

}
