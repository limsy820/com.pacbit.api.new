package com.bitkrx.api.auth.vo;

public class LoginHistoryResVO {

    private String no = "";
    private String userEmail = "";
    private String loginTme = "";
    private String brwsCd = "";
    private String loginYn = "";
    private String connIp = "";
    private String location = "";

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
