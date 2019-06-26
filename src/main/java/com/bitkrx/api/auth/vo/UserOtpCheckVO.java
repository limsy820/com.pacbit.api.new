package com.bitkrx.api.auth.vo;

public class UserOtpCheckVO {

	private String otpEtcCd 		= "";
	private String etcGubun 		= "";
	private String userEmail 		= "";
	/**
	 * @return the otpEtcCd
	 */
	public String getOtpEtcCd() {
		return otpEtcCd;
	}
	/**
	 * @param otpEtcCd the otpEtcCd to set
	 */
	public void setOtpEtcCd(String otpEtcCd) {
		this.otpEtcCd = otpEtcCd;
	}
	/**
	 * @return the etcGubun
	 */
	public String getEtcGubun() {
		return etcGubun;
	}
	/**
	 * @param etcGubun the etcGubun to set
	 */
	public void setEtcGubun(String etcGubun) {
		this.etcGubun = etcGubun;
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
