package com.bitkrx.api.trade.vo;

public class BuyPosPrcVO {

	private String price = "";
	private String maxPrc = "";
	private String minPrc = "";
	private String pointPrc = "";
	private String cnAmt = "";
	
	/**
	 * @return the minPrc
	 */
	public String getMinPrc() {
		return minPrc;
	}
	/**
	 * @param minPrc the minPrc to set
	 */
	public void setMinPrc(String minPrc) {
		this.minPrc = minPrc;
	}
	/**
	 * @return the pointPrc
	 */
	public String getPointPrc() {
		return pointPrc;
	}
	/**
	 * @param pointPrc the pointPrc to set
	 */
	public void setPointPrc(String pointPrc) {
		this.pointPrc = pointPrc;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMaxPrc() {
		return maxPrc;
	}
	public void setMaxPrc(String maxPrc) {
		this.maxPrc = maxPrc;
	}
	/**
	 * @return the cnAmt
	 */
	public String getCnAmt() {
		return cnAmt;
	}
	/**
	 * @param cnAmt the cnAmt to set
	 */
	public void setCnAmt(String cnAmt) {
		this.cnAmt = cnAmt;
	}
}
