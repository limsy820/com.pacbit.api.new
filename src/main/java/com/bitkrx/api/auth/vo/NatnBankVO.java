package com.bitkrx.api.auth.vo;

public class NatnBankVO {

    private String natnBankCode = "";
    private String natnCode = "";
    private String bankNm = "";

    public String getNatnBankCode() {
        return natnBankCode;
    }

    public void setNatnBankCode(String natnBankCode) {
        this.natnBankCode = natnBankCode;
    }

    public String getNatnCode() {
        return natnCode;
    }

    public void setNatnCode(String natnCode) {
        this.natnCode = natnCode;
    }

    public String getBankNm() {
        return bankNm;
    }

    public void setBankNm(String bankNm) {
        this.bankNm = bankNm;
    }
}