package com.bitkrx.api.auth.vo;

import com.bitkrx.config.vo.CmeExtendListVO;

public class LoginHistoryVO extends CmeExtendListVO {

    private String userEmail = "";
    private String loginTme = "";
    private String brwsCd = "";
    private String loginYn = "";
    private String connIp = "";
    private String location = "";

    private String stdDate = "";
    private String endDate = "";

    public String getStdDate() {
        return stdDate;
    }

    public void setStdDate(String stdDate) {
        this.stdDate = stdDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLoginTme() {
        return loginTme;
    }

    public void setLoginTme(String loginTme) {
        this.loginTme = loginTme;
    }

    public String getBrwsCd() {
        return brwsCd;
    }

    public void setBrwsCd(String brwsCd) {
        this.brwsCd = brwsCd;
    }

    public String getLoginYn() {
        return loginYn;
    }

    public void setLoginYn(String loginYn) {
        this.loginYn = loginYn;
    }

    public String getConnIp() {
        return connIp;
    }

    public void setConnIp(String connIp) {
        this.connIp = connIp;
    }
}
