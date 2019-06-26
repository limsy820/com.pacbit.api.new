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
 * @작성일		: 2018. 1. 8.
 */
public class CnAccListVO {

	private String accNo = "";
	private String curcyCd = "";
	private String destiTag = "";
	private String curcyNm = "";
	private String privateKey = "";


	public String getPrivateKey() {return privateKey;}

	public void setPrivateKey(String privateKey) {this.privateKey = privateKey;}

	public String getCurcyNm() {
		return curcyNm;
	}

	public void setCurcyNm(String curcyNm) {
		this.curcyNm = curcyNm;
	}

	/**
	 * @return the accNo
	 */
	public String getAccNo() {
		return accNo;
	}
	/**
	 * @param accNo the accNo to set
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
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
	 * @return the destiTag
	 */
	public String getDestiTag() {
		return destiTag;
	}
	/**
	 * @param destiTag the destiTag to set
	 */
	public void setDestiTag(String destiTag) {
		this.destiTag = destiTag;
	}
	
}
