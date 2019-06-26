/**
 * 서일회계법인 프로젝트 관리 시스템 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)크림소프트
 * CopyRight 서일회계법인 - 2015
 */
package com.bitkrx.config.vo;

import java.io.Serializable;

/**
 * @프로젝트명	: Seoil.Act.WebApp
 * @패키지    	: cme.com.web.common.util
 * @클래스명  	: Seoil.Act.WebApp
 * @작성자		: (주)크림소프트 김윤관
 * @작성일		: 2015. 5. 21.
 */
public class resultVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3704094953275722757L;
	int result=-1;
	String resultId="";
	String id="";
	String resultMsg="";

	/**
	 * @return the result
	 */
	public int getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}
	/**
	 * @return the resultId
	 */
	public String getResultId() {
		return resultId;
	}
	/**
	 * @param resultId the resultId to set
	 */
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}
	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	
	
}
