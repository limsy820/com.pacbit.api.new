package com.bitkrx.api.auth.vo;

import java.util.List;
import java.util.Map;

public class UserBankInfoVO {

    private String userEmail  = "";
    private String bankCd  = "";
    private String bankAccNo  = "";
    private String accntNm  = "";
    private String certYn  = "";
    private String depositNoYn  = "";
    private String regBlcokcd  = "";
    private String useYn  = "";
    private String regIp  = "";
    private String regEmail  = "";
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

    public String getBankCd() {
        return bankCd;
    }

    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

    public String getAccntNm() {
        return accntNm;
    }

    public void setAccntNm(String accntNm) {
        this.accntNm = accntNm;
    }

    public String getCertYn() {
        return certYn;
    }

    public void setCertYn(String certYn) {
        this.certYn = certYn;
    }

    public String getDepositNoYn() {
        return depositNoYn;
    }

    public void setDepositNoYn(String depositNoYn) {
        this.depositNoYn = depositNoYn;
    }

    public String getRegBlcokcd() {
        return regBlcokcd;
    }

    public void setRegBlcokcd(String regBlcokcd) {
        this.regBlcokcd = regBlcokcd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getRegEmail() {
        return regEmail;
    }

    public void setRegEmail(String regEmail) {
        this.regEmail = regEmail;
    }
}
