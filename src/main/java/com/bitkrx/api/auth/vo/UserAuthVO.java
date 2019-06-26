/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.auth.vo;

import java.util.List;
import java.util.Map;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.auth.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 2.
 */
public class UserAuthVO {

	private String emailCertYn  =   "";
	private String otpNo		=   "";
	private String accountNo	=   "";
	private String userEmail    =   "";
    private String emailCertDt  =   "";
    private String smsCertYn    =   "";
    private String smsCertDt    =   "";
    private String otpSerial    =   "";
    private String kycCertYn    =   "";
    private String kycUptEmail  =   "";
    private String otpCertYn    =   "";
    private String accCertYn    =   "";
    private String userInfoYn   =   "";

    public String getUserInfoYn() {return userInfoYn;}

    public void setUserInfoYn(String userInfoYn) {this.userInfoYn = userInfoYn;}

    public String getAccCertYn() {
        return accCertYn;
    }

    public void setAccCertYn(String accCertYn) {
        this.accCertYn = accCertYn;
    }

    public String getOtpCertYn() { return otpCertYn; }

    public void setOtpCertYn(String otpCertYn) { this.otpCertYn = otpCertYn; }

    public String getKycUptEmail() {
        return kycUptEmail;
    }

    public void setKycUptEmail(String kycUptEmail) {
        this.kycUptEmail = kycUptEmail;
    }

    public String getKycCertYn() {
        return kycCertYn;
    }

    public void setKycCertYn(String kycCertYn) {
        this.kycCertYn = kycCertYn;
    }

    private List<Map> RESULT = null;

    public List<Map> getRESULT() {
        return RESULT;
    }

    public void setRESULT(List<Map> RESULT) {
        this.RESULT = RESULT;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getEmailCertDt() {
        return emailCertDt;
    }

    public void setEmailCertDt(String emailCertDt) {
        this.emailCertDt = emailCertDt;
    }

    public String getSmsCertYn() {
        return smsCertYn;
    }

    public void setSmsCertYn(String smsCertYn) {
        this.smsCertYn = smsCertYn;
    }

    public String getSmsCertDt() {
        return smsCertDt;
    }

    public void setSmsCertDt(String smsCertDt) {
        this.smsCertDt = smsCertDt;
    }

    public String getOtpSerial() {
        return otpSerial;
    }

    public void setOtpSerial(String otpSerial) {
        this.otpSerial = otpSerial;
    }

    /**
     * @return the emailCertYn
     */
    public String getEmailCertYn() {
        return emailCertYn;
    }
    /**
     * @param emailCertYn the emailCertYn to set
     */
    public void setEmailCertYn(String emailCertYn) {
        this.emailCertYn = emailCertYn;
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
}
