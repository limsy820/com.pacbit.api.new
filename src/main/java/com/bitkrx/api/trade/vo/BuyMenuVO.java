package com.bitkrx.api.trade.vo;

import java.util.List;

public class BuyMenuVO {
	private String price = "";
	private String minPrc = "";
	private String pointPrc = "";
	
	private int i = 0;
	private List list;
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMinPrc() {
		return minPrc;
	}
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
	

}
