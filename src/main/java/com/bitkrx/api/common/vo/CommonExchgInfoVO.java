package com.bitkrx.api.common.vo;

public class CommonExchgInfoVO {

    private String exchgName = ""; // 거래소명
    private String lang = ""; // 언어 코드
    private String exchgUrl = ""; // 거래소 URL
    private String exchgCeo = ""; // 거래소 대표자명
    private String cicEmail = ""; // 고객센터 이메일
    private String cicPhone = ""; // 고객센터 전화번호
    private String bi = ""; // 이미지 로고

    public String getExchgName() {
        return exchgName;
    }

    public void setExchgName(String exchgName) {
        this.exchgName = exchgName;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getExchgUrl() {
        return exchgUrl;
    }

    public void setExchgUrl(String exchgUrl) {
        this.exchgUrl = exchgUrl;
    }

    public String getExchgCeo() {
        return exchgCeo;
    }

    public void setExchgCeo(String exchgCeo) {
        this.exchgCeo = exchgCeo;
    }

    public String getCicEmail() {
        return cicEmail;
    }

    public void setCicEmail(String cicEmail) {
        this.cicEmail = cicEmail;
    }

    public String getCicPhone() {
        return cicPhone;
    }

    public void setCicPhone(String cicPhone) {
        this.cicPhone = cicPhone;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }
}
