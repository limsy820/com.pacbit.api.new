package com.bitkrx.api.trade.vo;

public class NotConListVO {
	private String orderNo = "";
	private String orderCd = "";
	private String orderTm = "";
	private String orderAmt = "0";
	private String orderPrc = "0";
	private String userEmail = "";
	private String tradeAmt= "0";
	private String avrTradePrc= "0";
	private String buyAllYn= "";
	private String coinNm = "";
	private String cnKndCd = "";

    public String getCnKndCd() {
        return cnKndCd;
    }

    public void setCnKndCd(String cnKndCd) {
        this.cnKndCd = cnKndCd;
    }

    /**
	 * @return the coinNm
	 */
	public String getCoinNm() {
		return coinNm;
	}
	/**
	 * @param coinNm the coinNm to set
	 */
	public void setCoinNm(String coinNm) {
		this.coinNm = coinNm;
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
	/**
	 * @return the avrTradePrc
	 */
	public String getAvrTradePrc() {
		return avrTradePrc;
	}
	/**
	 * @param avrTradePrc the avrTradePrc to set
	 */
	public void setAvrTradePrc(String avrTradePrc) {
		this.avrTradePrc = avrTradePrc;
	}
	/**
	 * @return the buyAllYn
	 */
	public String getBuyAllYn() {
		return buyAllYn;
	}
	/**
	 * @param buyAllYn the buyAllYn to set
	 */
	public void setBuyAllYn(String buyAllYn) {
		this.buyAllYn = buyAllYn;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderCd() {
		return orderCd;
	}
	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}
	public String getOrderTm() {
		return orderTm;
	}
	public void setOrderTm(String orderTm) {
		this.orderTm = orderTm;
	}
	public String getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}
	public String getOrderPrc() {
		return orderPrc;
	}
	public void setOrderPrc(String orderPrc) {
		this.orderPrc = orderPrc;
	}

}
