package com.bitkrx.api.chart.vo;

import java.io.Serializable;

public class CoinChartVO implements Serializable{
	
	/**
	 * /bt.coin.price.chart.dp/proc.go
	 */
	private static final long serialVersionUID = -490851951760831255L;
	private String tradeTime  = "";
	private String openValue  = "";
	private String closeValue = "";
	private String HightValue = "";
	private String LowValue   = "";
	private String ma15Value  = "";
	private String ma50Value  = "";
	private String TradeValue = "";
	private String curcyCd 	  =	"";
	private String tmCode	  = "";
	private String time		  = "";

	
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getOpenValue() {
		return openValue;
	}
	public void setOpenValue(String openValue) {
		this.openValue = openValue;
	}
	public String getCloseValue() {
		return closeValue;
	}
	public void setCloseValue(String closeValue) {
		this.closeValue = closeValue;
	}
	public String getHightValue() {
		return HightValue;
	}
	public void setHightValue(String hightValue) {
		HightValue = hightValue;
	}
	public String getLowValue() {
		return LowValue;
	}
	public void setLowValue(String lowValue) {
		LowValue = lowValue;
	}
	public String getMa15Value() {
		return ma15Value;
	}
	public void setMa15Value(String ma15Value) {
		this.ma15Value = ma15Value;
	}
	public String getMa50Value() {
		return ma50Value;
	}
	public void setMa50Value(String ma50Value) {
		this.ma50Value = ma50Value;
	}
	public String getTradeValue() {
		return TradeValue;
	}
	public void setTradeValue(String tradeValue) {
		TradeValue = tradeValue;
	}
	public String getCurcyCd() {
		return curcyCd;
	}
	public void setCurcyCd(String curcyCd) {
		this.curcyCd = curcyCd;
	}
	/**
	 * @return the tmCode
	 */
	public String getTmCode() {
		return tmCode;
	}
	/**
	 * @param tmCode the tmCode to set
	 */
	public void setTmCode(String tmCode) {
		this.tmCode = tmCode;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
	

}
