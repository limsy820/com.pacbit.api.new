package com.bitkrx.api.auth.vo;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.auth.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 8. 01.
 */

public class CheckIpVO {
    private String connIp = "";
    private String brwsCd = "";
    private String userEmail = "";
    private String deviceCode = "";

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getConnIp() {
        return connIp;
    }

    public void setConnIp(String connIp) {
        this.connIp = connIp;
    }

    public String getBrwsCd() {
        return brwsCd;
    }

    public void setBrwsCd(String brwsCd) {
        this.brwsCd = brwsCd;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
