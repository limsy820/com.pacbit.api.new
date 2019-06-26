package com.bitkrx.api.common.vo;

public class CoinBalanceVO {

    private String curcyCd = "";        // 통화단위
    private String cnAmt = "0";      // 거래가능 코인수량
    private String usePrice = "0";      // 거래가능 코인원화가격
    private String usePriceKrw = "";
    private String resultStrCode = "";
    private String cnPrc = "";
    private String cnPrcKrw = "";
    private String exrate = "";
    private String posCnAmt = "";

    public String getPosCnAmt() {
        return posCnAmt;
    }

    public void setPosCnAmt(String posCnAmt) {
        this.posCnAmt = posCnAmt;
    }

    public String getExrate() {
        return exrate;
    }

    public void setExrate(String exrate) {
        this.exrate = exrate;
    }

    public String getCnPrcKrw() {
        return cnPrcKrw;
    }

    public void setCnPrcKrw(String cnPrcKrw) {
        this.cnPrcKrw = cnPrcKrw;
    }

    public String getCnPrc() {
        return cnPrc;
    }

    public void setCnPrc(String cnPrc) {
        this.cnPrc = cnPrc;
    }

    public String getResultStrCode() {
        return resultStrCode;
    }

    public void setResultStrCode(String resultStrCode) {
        this.resultStrCode = resultStrCode;
    }

    public String getUsePriceKrw() {
        return usePriceKrw;
    }

    public void setUsePriceKrw(String usePriceKrw) {
        this.usePriceKrw = usePriceKrw;
    }

    public String getCurcyCd() {
        return curcyCd;
    }

    public void setCurcyCd(String curcyCd) {
        this.curcyCd = curcyCd;
    }

    public String getCnAmt() {
        return cnAmt;
    }

    public void setCnAmt(String cnAmt) {
        this.cnAmt = cnAmt;
    }

    public String getUsePrice() {
        return usePrice;
    }

    public void setUsePrice(String usePrice) {
        this.usePrice = usePrice;
    }
}
