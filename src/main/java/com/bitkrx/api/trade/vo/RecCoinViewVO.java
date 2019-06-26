/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.trade.vo;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.trade.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 12. 10.
 */
public class RecCoinViewVO {

    private String maxPrc = "0";
    private String minPrc = "0";
    private String curcyAmt = "";
    private String rcvCurcyAmt = "";
	private String krwPrice    = "";

	public String getKrwPrice() {return krwPrice;}

	public void setKrwPrice(String krwPrice) {this.krwPrice = krwPrice;}

	public String getCurcyAmt() {
        return curcyAmt;
    }

    public void setCurcyAmt(String curcyAmt) {
        this.curcyAmt = curcyAmt;
    }

    public String getRcvCurcyAmt() {
        return rcvCurcyAmt;
    }

    public void setRcvCurcyAmt(String rcvCurcyAmt) {
        this.rcvCurcyAmt = rcvCurcyAmt;
    }

    /**
	 * @return the maxPrc

	 */
	public String getMaxPrc() {
		return maxPrc;
	}
	/**
	 * @param maxPrc the maxPrc to set
	 */
	public void setMaxPrc(String maxPrc) {
		this.maxPrc = maxPrc;
	}
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
	
}
