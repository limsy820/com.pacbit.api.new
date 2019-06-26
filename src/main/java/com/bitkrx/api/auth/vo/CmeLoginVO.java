package com.bitkrx.api.auth.vo;

import java.util.List;
import java.util.Map;

import com.bitkrx.config.CmeResultVO;

public class CmeLoginVO extends CmeResultVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5690986010891367007L;

	private String userEmail 		= 	"";			//사용자 EMAIL
	private String userPwd 			=	"";			//사용자 비밀번호
	private String emailCertYn		=	"";			//이메일 인증여부
	private String loginYn			=	"";			//로그인성공여부
	private String clientCd 		=	"";			
	private String clientNm 		=	"";	
	private String clientPe			=	"";
	private String userNm			=	"";
	private String failCd			=	"";			//로그인 실패 코드
	private String brwsCd			=	"";			//로그인브라우저코드
	private String osCd				=	"";			//로그인 OS코드
	private String connIp			=	"";			//접속 IP
	private String userPhone		=	"";
	private String nickNm     		=	"";
	private String userMobile		=	"";
	private String postCd			=	"";
	private String adrs      		=	"";
	private String dtlAdrs			=	"";
	private String useYn  			=	"";
	private String birthDay			=	"";
	private String pwdChgYn			=	"";
	private String tmpPwd   		=	"";
	private String pushToken 		=	"";
	private String signDt  			=	"";                                                  
	private String regIp  			=	"";  
	private String regDt			=	"";
	private String authCode			=	"";
	private String gender			=	"";
	private String country			=	"";
	private String ip				=	"";
	private String langCd			=	"";
	private String lockYn			=	"";
	private String shHaveYn			=	"";
    private String brhCode          = "";
    private String rcmdCode         = "";
    private String subUserEmail     = "";
    private String regEmail         = "";
	private String natnCode 		= "";
	private String brhNm 			= "";
	private String rcmdNm			= "";
	private String deviceCd			= "";
	private String pinNo			= "";
	private String deviceNm 		= "";
	private String pinPushYn		= "";
	private String authType		    = "";     	// E:이메일 , S:SMS , U:회원가입시
	private String fingerStatus 	= "";		// 지문로그인 상태
	private String fingerPushYn		= "";
	private String userInfoYn 		= "";

	public String getUserInfoYn() {return userInfoYn;}

	public void setUserInfoYn(String userInfoYn) {this.userInfoYn = userInfoYn;}

	public String getFingerPushYn() {return fingerPushYn;}

	public void setFingerPushYn(String fingerPushYn) {this.fingerPushYn = fingerPushYn;}

	public String getFingerStatus() {return fingerStatus;}

	public void setFingerStatus(String fingerStatus) {this.fingerStatus = fingerStatus;}

	public String getAuthType() {return authType;}

	public void setAuthType(String authType) {this.authType = authType;}

	public String getPinPushYn() {return pinPushYn; }

	public void setPinPushYn(String pinPushYn) {this.pinPushYn = pinPushYn; }

	public String getDeviceNm() {return deviceNm;}

	public void setDeviceNm(String deviceNm) {this.deviceNm = deviceNm;}

	public String getPinNo() { return pinNo; }

	public void setPinNo(String pinNo) { this.pinNo = pinNo; }

	public String getDeviceCd() { return deviceCd; }

	public void setDeviceCd(String deviceCd) { this.deviceCd = deviceCd; }

	public String getRcmdNm() { return rcmdNm; }

	public void setRcmdNm(String rcmdNm) { this.rcmdNm = rcmdNm; }

	public String getBrhNm() { return brhNm; }

	public void setBrhNm(String brhNm) { this.brhNm = brhNm; }

	public String getNatnCode() { return natnCode; }

	public void setNatnCode(String natnCode) { this.natnCode = natnCode; }

    public String getRegEmail() {
        return regEmail;
    }

    public void setRegEmail(String regEmail) {
        this.regEmail = regEmail;
    }

    public String getBrhCode() {
        return brhCode;
    }

    public void setBrhCode(String brhCode) {
        this.brhCode = brhCode;
    }

    public String getRcmdCode() {
        return rcmdCode;
    }

    public void setRcmdCode(String rcmdCode) {
        this.rcmdCode = rcmdCode;
    }

    public String getSubUserEmail() {
        return subUserEmail;
    }

    public void setSubUserEmail(String subUserEmail) {
        this.subUserEmail = subUserEmail;
    }

    /**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	private List RESULT           =   null;
	private Map<String, String> resultData = null;
	
	/**
	 * @return the rESULT
	 */
	public List getRESULT() {
		return RESULT;
	}
	/**
	 * @param rESULT the rESULT to set
	 */
	public void setRESULT(List rESULT) {
		RESULT = rESULT;
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
	/**
	 * @return the userPhone
	 */
	public String getUserPhone() {
		return userPhone;
	}
	/**
	 * @param userPhone the userPhone to set
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	/**
	 * @return the nickNm
	 */
	public String getNickNm() {
		return nickNm;
	}
	/**
	 * @param nickNm the nickNm to set
	 */
	public void setNickNm(String nickNm) {
		this.nickNm = nickNm;
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
	/**
	 * @return the postCd
	 */
	public String getPostCd() {
		return postCd;
	}
	/**
	 * @param postCd the postCd to set
	 */
	public void setPostCd(String postCd) {
		this.postCd = postCd;
	}
	/**
	 * @return the adrs
	 */
	public String getAdrs() {
		return adrs;
	}
	/**
	 * @param adrs the adrs to set
	 */
	public void setAdrs(String adrs) {
		this.adrs = adrs;
	}
	/**
	 * @return the dtlAdrs
	 */
	public String getDtlAdrs() {
		return dtlAdrs;
	}
	/**
	 * @param dtlAdrs the dtlAdrs to set
	 */
	public void setDtlAdrs(String dtlAdrs) {
		this.dtlAdrs = dtlAdrs;
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
	 * @return the birthDay
	 */
	public String getBirthDay() {
		return birthDay;
	}
	/**
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	/**
	 * @return the pwdChgYn
	 */
	public String getPwdChgYn() {
		return pwdChgYn;
	}
	/**
	 * @param pwdChgYn the pwdChgYn to set
	 */
	public void setPwdChgYn(String pwdChgYn) {
		this.pwdChgYn = pwdChgYn;
	}
	/**
	 * @return the tmpPwd
	 */
	public String getTmpPwd() {
		return tmpPwd;
	}
	/**
	 * @param tmpPwd the tmpPwd to set
	 */
	public void setTmpPwd(String tmpPwd) {
		this.tmpPwd = tmpPwd;
	}
	/**
	 * @return the signDt
	 */
	public String getSignDt() {
		return signDt;
	}
	/**
	 * @param signDt the signDt to set
	 */
	public void setSignDt(String signDt) {
		this.signDt = signDt;
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
	 * @return the brwsCd
	 */
	public String getBrwsCd() {
		return brwsCd;
	}
	/**
	 * @param brwsCd the brwsCd to set
	 */
	public void setBrwsCd(String brwsCd) {
		this.brwsCd = brwsCd;
	}
	/**
	 * @return the osCd
	 */
	public String getOsCd() {
		return osCd;
	}
	/**
	 * @param osCd the osCd to set
	 */
	public void setOsCd(String osCd) {
		this.osCd = osCd;
	}
	
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getLoginYn() {
		return loginYn;
	}
	public void setLoginYn(String loginYn) {
		this.loginYn = loginYn;
	}
	public Map<String, String> getResultData() {
		return resultData;
	}
	public void setResultData(Map<String, String> resultData) {
		this.resultData = resultData;
	}
	public String getClientCd() {
		return clientCd;
	}
	public void setClientCd(String clientCd) {
		this.clientCd = clientCd;
	}
	public String getClientNm() {
		return clientNm;
	}
	public void setClientNm(String clientNm) {
		this.clientNm = clientNm;
	}
	public String getClientPe() {
		return clientPe;
	}
	public void setClientPe(String clientPe) {
		this.clientPe = clientPe;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	/**
	 * @return the connIp
	 */
	public String getConnIp() {
		return connIp;
	}
	/**
	 * @param connIp the connIp to set
	 */
	public void setConnIp(String connIp) {
		this.connIp = connIp;
	}
	/**
	 * @return the pushToken
	 */
	public String getPushToken() {
		return pushToken;
	}
	/**
	 * @param pushToken the pushToken to set
	 */
	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
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
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
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
	 * @return the shHaveYn
	 */
	public String getShHaveYn() {
		return shHaveYn;
	}
	/**
	 * @param shHaveYn the shHaveYn to set
	 */
	public void setShHaveYn(String shHaveYn) {
		this.shHaveYn = shHaveYn;
	}
	

}
