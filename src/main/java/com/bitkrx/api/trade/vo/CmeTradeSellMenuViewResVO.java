package com.bitkrx.api.trade.vo;

public class CmeTradeSellMenuViewResVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3505880388658286409L;
	
	private String avaSellAmt							=	"";		//판매가능수량
	private String maxPrc								=	"";		//미체결최고구매가
	public String getAvaSellAmt() {
		return avaSellAmt;
	}

	public void setAvaSellAmt(String avaSellAmt) {
		this.avaSellAmt = avaSellAmt;
	}

	public String getMaxPrc() {
		return maxPrc;
	}

	public void setMaxPrc(String maxPrc) {
		this.maxPrc = maxPrc;
	}


}
