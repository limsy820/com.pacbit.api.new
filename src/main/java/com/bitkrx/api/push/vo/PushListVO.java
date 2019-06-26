/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.push.vo;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.push.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 12. 28.
 */
public class PushListVO {

	private String pushType = "";
	private String regDt 	= "";
	private String cntnsMsg	= "";
	private String no 	= "";
	private String isRead = "";
	private String cntnsCode = "";


	public String getIsRead() { return isRead; }

	public void setIsRead(String isRead) { this.isRead = isRead; }

	public String getCntnsCode() { return cntnsCode; }

	public void setCntnsCode(String cntnsCode) { this.cntnsCode = cntnsCode; }
	/**
	 * @return the pushType
	 */
	public String getPushType() {
		return pushType;
	}
	/**
	 * @param pushType the pushType to set
	 */
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return regDt;
	}
	/**
	 * @param regDt the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	/**
	 * @return the cntnsMsg
	 */
	public String getCntnsMsg() {
		return cntnsMsg;
	}
	/**
	 * @param cntnsMsg the cntnsMsg to set
	 */
	public void setCntnsMsg(String cntnsMsg) {
		this.cntnsMsg = cntnsMsg;
	}

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
