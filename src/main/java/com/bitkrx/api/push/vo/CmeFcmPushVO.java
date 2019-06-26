/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.push.vo;

import com.bitkrx.config.vo.CmeExtendListVO;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.push.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 12. 6.
 */
public class CmeFcmPushVO extends CmeExtendListVO {
	private String push_key = "";
	private String tobe_token = "";
	private String push_token = "";
	private String member_id = "";
	private String ad_pkg = "";
	private String reg_user = "";
	private String push_type = ""; // A001 : 단일건, A002: 여러건, A003:소속 (DB에서 가져와서 처리 하는 로직) ad_pkg 필수가 됨
	private String msg_type = ""; // M001 : noti , M002 : MSG, M003 : noti , msg
	private String isMsgDB = "N"; //메시지를 DB 에서 조회해서 가져오는 여보 Y : DB ,  N : parm default
	private String push_msg = "";
	private String push_title = "";
	
	private String userEmail = "";
	private String regIp = "";
	private String cntnsCode = "";
	private String pushCode = "";
	private int listSize = 0;

	public String getPushCode() {
		return pushCode;
	}

	public void setPushCode(String pushCode) {
		this.pushCode = pushCode;
	}
	/**
	 * @return the push_token
	 */
	public String getPush_token() {
		return push_token;
	}
	/**
	 * @param push_token the push_token to set
	 */
	public void setPush_token(String push_token) {
		this.push_token = push_token;
	}
	/**
	 * @return the member_id
	 */
	public String getMember_id() {
		return member_id;
	}
	/**
	 * @param member_id the member_id to set
	 */
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	/**
	 * @return the ad_pkg
	 */
	public String getAd_pkg() {
		return ad_pkg;
	}
	/**
	 * @param ad_pkg the ad_pkg to set
	 */
	public void setAd_pkg(String ad_pkg) {
		this.ad_pkg = ad_pkg;
	}
	/**
	 * @return the reg_user
	 */
	public String getReg_user() {
		return reg_user;
	}
	/**
	 * @param reg_user the reg_user to set
	 */
	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}
	/**
	 * @return the push_type
	 */
	public String getPush_type() {
		return push_type;
	}
	/**
	 * @param push_type the push_type to set
	 */
	public void setPush_type(String push_type) {
		this.push_type = push_type;
	}
	/**
	 * @return the msg_type
	 */
	public String getMsg_type() {
		return msg_type;
	}
	/**
	 * @param msg_type the msg_type to set
	 */
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	
	/**
	 * @return the push_msg
	 */
	public String getPush_msg() {
		return push_msg;
	}
	/**
	 * @param push_msg the push_msg to set
	 */
	public void setPush_msg(String push_msg) {
		this.push_msg = push_msg;
	}
	/**
	 * @return the isMsgDB
	 */
	public String getIsMsgDB() {
		return isMsgDB;
	}
	/**
	 * @param isMsgDB the isMsgDB to set
	 */
	public void setIsMsgDB(String isMsgDB) {
		this.isMsgDB = isMsgDB;
	}
	/**
	 * @return the tobe_token
	 */
	public String getTobe_token() {
		return tobe_token;
	}
	/**
	 * @param tobe_token the tobe_token to set
	 */
	public void setTobe_token(String tobe_token) {
		this.tobe_token = tobe_token;
	}
	/**
	 * @return the push_key
	 */
	public String getPush_key() {
		return push_key;
	}
	/**
	 * @param push_key the push_key to set
	 */
	public void setPush_key(String push_key) {
		this.push_key = push_key;
	}
	/**
	 * @return the push_title
	 */
	public String getPush_title() {
		return push_title;
	}
	/**
	 * @param push_title the push_title to set
	 */
	public void setPush_title(String push_title) {
		this.push_title = push_title;
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
	 * @return the regIp
	 */
	public String getRegIp() {
		return regIp;
	}
	/**
	 * @param regIp the regIp to set
	 */
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	/**
	 * @return the cntnsCode
	 */
	public String getCntnsCode() {
		return cntnsCode;
	}
	/**
	 * @param cntnsCode the cntnsCode to set
	 */
	public void setCntnsCode(String cntnsCode) {
		this.cntnsCode = cntnsCode;
	}
	/**
	 * @return the listSize
	 */
	public int getListSize() {
		return listSize;
	}
	/**
	 * @param listSize the listSize to set
	 */
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	
}
