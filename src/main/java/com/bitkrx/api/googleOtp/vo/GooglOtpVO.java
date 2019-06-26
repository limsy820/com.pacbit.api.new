/**
 * (주)크림스 의 시스템 웹어플리케이션 프레임웍 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)크림스
 * CopyRight Creams. inc. Since 2015 
 * 총괄 개발 책임자 : 주식회사 크림스 통합개발연구소 소장 김윤관
 */
package com.bitkrx.api.googleOtp.vo;

import java.io.Serializable;

import com.bitkrx.config.CmeResultVO;

/**
 * @프로젝트명	: cme.web.noryapi
 * @패키지    	: cme.web.googleotp.control
 * @클래스명  	: cme.web.noryapi
 * @작성자		: (주)씨엠이소프트 최종근
 * @작성일		: 2017. 7. 27.
 */
public class GooglOtpVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4234796555862772571L;
	
	private String urlHost = "";
	private String encodedKey = "";
	private String qrCodeUrl = "";
	private String authCode = "";
	private String isUpt = "";
	private String userEmail = "";

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getIsUpt() {
		return isUpt;
	}

	public void setIsUpt(String isUpt) {
		this.isUpt = isUpt;
	}

	/**
	 * @return the encodedKey
	 */
	public String getEncodedKey() {
		return encodedKey;
	}
	/**
	 * @param encodedKey the encodedKey to set
	 */
	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}
	/**
	 * @return the qrCodeUrl
	 */
	public String getQrCodeUrl() {
		return qrCodeUrl;
	}
	/**
	 * @param qrCodeUrl the qrCodeUrl to set
	 */
	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}
	/**
	 * @return the urlHost
	 */
	public String getUrlHost() {
		return urlHost;
	}
	/**
	 * @param urlHost the urlHost to set
	 */
	public void setUrlHost(String urlHost) {
		this.urlHost = urlHost;
	}
	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}
	/**
	 * @param authCode the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
}
