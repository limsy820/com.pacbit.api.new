package com.bitkrx.api.main.vo;

public class CmeCoinPriceChartReqVO{

	private String curcyCd				=	"";		//통화단위 01:BTC , 02:ETH , 03:BCH , 04:XRP , 05:KRW
	private String time					=	"";		//시간
	private int listSize				=	0;
	private String userEmail			=	"";
	public String getCurcyCd() {
		return curcyCd;
	}
	public void setCurcyCd(String curcyCd) {
		this.curcyCd = curcyCd;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getListSize() {
		return listSize;
	}
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}
	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}
