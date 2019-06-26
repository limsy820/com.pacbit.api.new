package com.bitkrx.api.trade.vo;

public class CompTradeListVO {
	private String tradeTm = "";
	private String tradePrc = "";
	private String tradeAmt = "";
	private String totTradePrc = "";
	private String orderCd = "";
	
	public String getTradeTm() {
		return tradeTm;
	}
	public void setTradeTm(String tradeTm) {
		this.tradeTm = tradeTm;
	}
	public String getTradePrc() {
		return tradePrc;
	}
	public void setTradePrc(String tradePrc) {
		this.tradePrc = tradePrc;
	}
	public String getTradeAmt() {
		return tradeAmt;
	}
	public void setTradeAmt(String tradeAmt) {
		this.tradeAmt = tradeAmt;
	}
	public String getTotTradePrc() {
		return totTradePrc;
	}
	public void setTotTradePrc(String totTradePrc) {
		this.totTradePrc = totTradePrc;
	}
	public String getOrderCd() {
		return orderCd;
	}
	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}

}
