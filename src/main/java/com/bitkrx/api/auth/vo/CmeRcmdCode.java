package com.bitkrx.api.auth.vo;

public class CmeRcmdCode {
    private String natnCode         = "";           // 국가코드
    private String natnNm           = "";           // 국가이름
    private String brhCode          = "";           // 지점코드
    private String brhNm            = "";           // 지점이름
    private String rcmdCode         = "";           // 추천인코드
    private String rcmdNm           = "";           // 추천인이름
    private String telNo            = "";           // 추천인 전화번호

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getNatnCode() {
        return natnCode;
    }

    public void setNatnCode(String natnCode) {
        this.natnCode = natnCode;
    }

    public String getNatnNm() {
        return natnNm;
    }

    public void setNatnNm(String natnNm) {
        this.natnNm = natnNm;
    }

    public String getBrhCode() {
        return brhCode;
    }

    public void setBrhCode(String brhCode) {
        this.brhCode = brhCode;
    }

    public String getBrhNm() {
        return brhNm;
    }

    public void setBrhNm(String brhNm) {
        this.brhNm = brhNm;
    }

    public String getRcmdCode() {
        return rcmdCode;
    }

    public void setRcmdCode(String rcmdCode) {
        this.rcmdCode = rcmdCode;
    }

    public String getRcmdNm() {
        return rcmdNm;
    }

    public void setRcmdNm(String rcmdNm) {
        this.rcmdNm = rcmdNm;
    }
}
