package com.bitkrx.api.auth.vo;

public class LoginResVO {

	private String loginYn = "N";
	private String emailCertYn = "";
	private String userEmail = "";
	private String userNm = "";
	private String blckYn = "";
	private String relYn = "";
	private String otpNo = "";
	private String accountNo = "";
	private String userMobile = "";
	private String smsCertYn = "";
	private String useYn = "Y";
	private String failCd = "";
	private String notice = "";
	private String regIp = "";
	private String isIpFirst = "";
	private String lockYn = "";
	private String langCd = "";
	private String pwdChgYn = "";
	private String pinLoginYn = "";
	private String pinUseYn			= "";
	private String pinResetYn = "";
	private String foreignIp = "";
	private String fingerLoginYn = "";
	private String accCertYn = "";
	private String userInfoYn = "";
	private String title = "";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserInfoYn() {return userInfoYn;}

	public void setUserInfoYn(String userInfoYn) {
		this.userInfoYn = userInfoYn;
	}

	public String getAccCertYn() {
		return accCertYn;
	}

	public void setAccCertYn(String accCertYn) {
		this.accCertYn = accCertYn;
	}

	public String getFingerLoginYn() {return fingerLoginYn;}

	public void setFingerLoginYn(String fingerLoginYn) {this.fingerLoginYn = fingerLoginYn;}

	public String getForeignIp() {return foreignIp;}

	public void setForeignIp(String foreignIp) {this.foreignIp = foreignIp;}

	public String getPinResetYn() { return pinResetYn; }

	public void setPinResetYn(String pinResetYn) { this.pinResetYn = pinResetYn; }

	public String getPinUseYn() {return pinUseYn; }

	public void setPinUseYn(String pinUseYn) {this.pinUseYn = pinUseYn; }

	public String getPinLoginYn() {return pinLoginYn;}

	public void setPinLoginYn(String pinLoginYn) {this.pinLoginYn = pinLoginYn;}

	/**
	 * @return the failCd
	 */
	public String getFailCd() {
		return failCd;
	}
	/**
	 * @param failCd the failCd to set
	 */
	public void setFailCd(String failCd) {
		this.failCd = failCd;
	}
	/**
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}
	/**
	 * @param notice the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}
	/**
	 * @return the smsCertYn
	 */
	public String getSmsCertYn() {
		return smsCertYn;
	}
	/**
	 * @param smsCertYn the smsCertYn to set
	 */
	public void setSmsCertYn(String smsCertYn) {
		this.smsCertYn = smsCertYn;
	}
	
	/**
	 * @return the otpNo
	 */
	public String getOtpNo() {
		return otpNo;
	}
	/**
	 * @param otpNo the otpNo to set
	 */
	public void setOtpNo(String otpNo) {
		this.otpNo = otpNo;
	}
	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}
	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	/**
	 * @return the userMobile
	 */
	public String getUserMobile() {
		return userMobile;
	}
	/**
	 * @param userMobile the userMobile to set
	 */
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getBlckYn() {
		return blckYn;
	}
	public void setBlckYn(String blckYn) {
		this.blckYn = blckYn;
	}
	public String getRelYn() {
		return relYn;
	}
	public void setRelYn(String relYn) {
		this.relYn = relYn;
	}
	public String getLoginYn() {
		return loginYn;
	}
	public void setLoginYn(String loginYn) {
		this.loginYn = loginYn;
	}
	public String getEmailCertYn() {
		return emailCertYn;
	}
	public void setEmailCertYn(String emailCertYn) {
		this.emailCertYn = emailCertYn;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	/**
	 * @return the useYn
	 */
	public String getUseYn() {
		return useYn;
	}
	/**
	 * @param useYn the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
	 * @return the isIpFirst
	 */
	public String getIsIpFirst() {
		return isIpFirst;
	}
	/**
	 * @param isIpFirst the isIpFirst to set
	 */
	public void setIsIpFirst(String isIpFirst) {
		this.isIpFirst = isIpFirst;
	}
	/**
	 * @return the lockYn
	 */
	public String getLockYn() {
		return lockYn;
	}
	/**
	 * @param lockYn the lockYn to set
	 */
	public void setLockYn(String lockYn) {
		this.lockYn = lockYn;
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

    public String getPwdChgYn() {
        return pwdChgYn;
    }

    public void setPwdChgYn(String pwdChgYn) {
        this.pwdChgYn = pwdChgYn;
    }
}
