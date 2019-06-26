/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.push.vo;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.trade.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 20.
 */
public class SendMsgVO {
	private String seq				= "";
	private String cmmCd			= "";
	private String userEmail		= "";
	private String msg				= "";
	private String sendYn			= "";
	private String langCd 			= "";
	
	/**
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}
	/**
	 * @return the cmmCd
	 */
	public String getCmmCd() {
		return cmmCd;
	}
	/**
	 * @param cmmCd the cmmCd to set
	 */
	public void setCmmCd(String cmmCd) {
		this.cmmCd = cmmCd;
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
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the sendYn
	 */
	public String getSendYn() {
		return sendYn;
	}
	/**
	 * @param sendYn the sendYn to set
	 */
	public void setSendYn(String sendYn) {
		this.sendYn = sendYn;
	}
	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return langCd;
	}
	/**
	 * @param langCd the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}
	
	
}
