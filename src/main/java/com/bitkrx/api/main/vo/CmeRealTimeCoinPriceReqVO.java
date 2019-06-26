package com.bitkrx.api.main.vo;

public class CmeRealTimeCoinPriceReqVO{

	private String time 			= 	"";		//시간
	private String curcyCd			=	"";		//통화01:BTC , 02:ETH , 03:BCH , 04:XRP , 05:KRW
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCurcyCd() {
		return curcyCd;
	}
	public void setCurcyCd(String curcyCd) {
		this.curcyCd = curcyCd;
	}

}
