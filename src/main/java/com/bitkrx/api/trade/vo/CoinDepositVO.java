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
 * @작성일		: 2017. 12. 29.
 */
public class CoinDepositVO {

	private String reqDt	=	"";
	private String curcyCd	=	"";
	private String cnAmt	=	"";
	private String state	=	"";
	private String compDt	=	"";
	private String exFlag 	=   "";

	public String getExFlag() {return exFlag;}

	public void setExFlag(String exFlag) {this.exFlag = exFlag;}

	/**
	 * @return the reqDt
	 */
	public String getReqDt() {
		return reqDt;
	}
	/**
	 * @param reqDt the reqDt to set
	 */
	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}
	/**
	 * @return the curcyCd
	 */
	public String getCurcyCd() {
		return curcyCd;
	}
	/**
	 * @param curcyCd the curcyCd to set
	 */
	public void setCurcyCd(String curcyCd) {
		this.curcyCd = curcyCd;
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
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the compDt
	 */
	public String getCompDt() {
		return compDt;
	}
	/**
	 * @param compDt the compDt to set
	 */
	public void setCompDt(String compDt) {
		this.compDt = compDt;
	}
	
	
}
