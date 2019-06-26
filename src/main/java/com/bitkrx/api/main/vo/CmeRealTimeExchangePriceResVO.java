package com.bitkrx.api.main.vo;

import com.bitkrx.config.CmeResultVO;

public class CmeRealTimeExchangePriceResVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2338780306885207043L;
	private String exchgNm			=	"";		//거래소이름
	private String coinPrc			=	"";		//변동시세
	private String svcTranAmt		=	"";		//거래금액
	private String chgPrc			=	"";		//변동금액
	private String volume			=	"";		//변동률
	
	public String getCoinPrc() {
		return coinPrc;
	}
	public void setCoinPrc(String coinPrc) {
		this.coinPrc = coinPrc;
	}
	public String getChgPrc() {
		return chgPrc;
	}
	public void setChgPrc(String chgPrc) {
		this.chgPrc = chgPrc;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getSvcTranAmt() {
		return svcTranAmt;
	}
	public void setSvcTranAmt(String svcTranAmt) {
		this.svcTranAmt = svcTranAmt;
	}
	public String getExchgNm() {
		return exchgNm;
	}
	public void setExchgNm(String exchgNm) {
		this.exchgNm = exchgNm;
	}

}
