package com.bitkrx.api.common.vo;

public class MarketTradeVO {

    private String curcyNm          = "";
    private String curcyCd          = "";
    private String buyLmt1Once       = "";       //  구매 1회 한도
    private String buyLmt1Day       = "";       //  구매 1일 한도
    private String selLmt1Once       = "";       //  판매 1회 한도
    private String selLmt1Day       = "";       //  판매 1일 한도


    public String getBuyLmt1Once() {return buyLmt1Once;}

    public void setBuyLmt1Once(String buyLmt1Once) {this.buyLmt1Once = buyLmt1Once;}

    public String getSelLmt1Once() {return selLmt1Once;}

    public void setSelLmt1Once(String selLmt1Once) {this.selLmt1Once = selLmt1Once;}

    public String getCurcyNm() {return curcyNm;}

    public void setCurcyNm(String curcyNm) {this.curcyNm = curcyNm;}

    public String getCurcyCd() {
        return curcyCd;
    }

    public void setCurcyCd(String curcyCd) {
        this.curcyCd = curcyCd;
    }

    public String getBuyLmt1Day() {
        return buyLmt1Day;
    }

    public void setBuyLmt1Day(String buyLmt1Day) {
        this.buyLmt1Day = buyLmt1Day;
    }

    public String getSelLmt1Day() {
        return selLmt1Day;
    }

    public void setSelLmt1Day(String selLmt1Day) {
        this.selLmt1Day = selLmt1Day;
    }
}
