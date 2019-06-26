package com.bitkrx.api.main.vo;

public class CmeRealTimeCoinPriceResVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2338780306885207043L;
	private String coinNm			=	"";		//코인명
	private String coinPrc			=	"";		//변동시세
	private String chgPrc			=	"";		//변동금액
	private String volume			=	"";		//변동률
	private String svcTranPrc		=	"";		//거래량
	private String svcTranAmt		=	"";		//거래금액
	private String curcyCd			=	"";
	
	public String getCoinNm() {
		return coinNm;
	}
	public void setCoinNm(String coinNm) {
		this.coinNm = coinNm;
	}
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
	public String getSvcTranPrc() {
		return svcTranPrc;
	}
	public void setSvcTranPrc(String svcTranPrc) {
		this.svcTranPrc = svcTranPrc;
	}
	public String getSvcTranAmt() {
		return svcTranAmt;
	}
	public void setSvcTranAmt(String svcTranAmt) {
		this.svcTranAmt = svcTranAmt;
	}
	public String getCurcyCd() {
		return curcyCd;
	}
	public void setCurcyCd(String curcyCd) {
		this.curcyCd = curcyCd;
	}

}
