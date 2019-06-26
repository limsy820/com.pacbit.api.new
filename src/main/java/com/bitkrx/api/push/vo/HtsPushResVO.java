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
 * @작성일		: 2017. 12. 27.
 */
public class HtsPushResVO {

	private String pushMsg = "";
	private String pushType = "";
	
	/**
	 * @return the pushMsg
	 */
	public String getPushMsg() {
		return pushMsg;
	}

	/**
	 * @param pushMsg the pushMsg to set
	 */
	public void setPushMsg(String pushMsg) {
		this.pushMsg = pushMsg;
	}

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
}
