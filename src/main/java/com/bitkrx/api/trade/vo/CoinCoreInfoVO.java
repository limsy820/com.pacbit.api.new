package com.bitkrx.api.trade.vo;

public class CoinCoreInfoVO {

    private String curcyCd = "";
    private String curcyNm = "";
    private String urlInfo = "";
    private String curcyType = "";
    private String confirmCnt = "";
    private String moveFee = "";

    public String getMoveFee() {
        return moveFee;
    }

    public void setMoveFee(String moveFee) {
        this.moveFee = moveFee;
    }

    public String getConfirmCnt() {
        return confirmCnt;
    }

    public void setConfirmCnt(String confirmCnt) {
        this.confirmCnt = confirmCnt;
    }

    public String getCurcyCd() {
        return curcyCd;
    }

    public void setCurcyCd(String curcyCd) {
        this.curcyCd = curcyCd;
    }

    public String getCurcyNm() {
        return curcyNm;
    }

    public void setCurcyNm(String curcyNm) {
        this.curcyNm = curcyNm;
    }

    public String getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(String urlInfo) {
        this.urlInfo = urlInfo;
    }

    public String getCurcyType() {
        return curcyType;
    }

    public void setCurcyType(String curcyType) {
        this.curcyType = curcyType;
    }
}
