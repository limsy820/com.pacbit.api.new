package com.bitkrx.api.common.vo;

public class CashBalanceVO {

    private String usePrice = "0";
    private String usePriceKrw = "0";
    private String exrate = "";
    private String cnAmt = "0";
    private String resultStrCode = "";
    private String userNm = "";

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getResultStrCode() {
        return resultStrCode;
    }

    public void setResultStrCode(String resultStrCode) {
        this.resultStrCode = resultStrCode;
    }

    public String getUsePrice() {
        return usePrice;
    }

    public void setUsePrice(String usePrice) {
        this.usePrice = usePrice;
    }

    public String getUsePriceKrw() {
        return usePriceKrw;
    }

    public void setUsePriceKrw(String usePriceKrw) {
        this.usePriceKrw = usePriceKrw;
    }

    public String getExrate() {
        return exrate;
    }

    public void setExrate(String exrate) {
        this.exrate = exrate;
    }

    public String getCnAmt() {
        return cnAmt;
    }

    public void setCnAmt(String cnAmt) {
        this.cnAmt = cnAmt;
    }
}
