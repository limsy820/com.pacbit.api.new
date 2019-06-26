package com.bitkrx.api.main.vo;

public class CmeOrderListResVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1404132414790264353L;
	private String tradeTime			=	"";		//체결시각
	private String coinPrc				=	"";		//코인가격
	private String cnAmt				=	"";		//코인 수
	private String tradePrc				=	"";		//체결가
	private String curcyCd				=	"";		//통화단위 01:BTC , 02:ETH , 03:BCH , 04:XRP , 05:KRW
	private String orderCd				=	"";
	
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getCoinPrc() {
		return coinPrc;
	}
	public void setCoinPrc(String coinPrc) {
		this.coinPrc = coinPrc;
	}
	public String getTradePrc() {
		return tradePrc;
	}
	public void setTradePrc(String tradePrc) {
		this.tradePrc = tradePrc;
	}
	public String getCnAmt() {
		return cnAmt;
	}
	public void setCnAmt(String cnAmt) {
		this.cnAmt = cnAmt;
	}
	public String getCurcyCd() {
		return curcyCd;
	}
	public void setCurcyCd(String curcyCd) {
		this.curcyCd = curcyCd;
	}
	public String getOrderCd() {
		return orderCd;
	}
	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}
	
}
