package com.bitkrx.api.trade.vo;

public class WithdrowCheckVO {

    private String curcyCd          = "";
    private String userEmail        = "";
    private String tradeType        = "";
    private String sndLimtYn        = "";
    private String stdDt            = "";
    private String endDt            = "";
    private String withdrowAmt      = "";
    private String sndAutoYn        = "";
    private String sndAutoLimtAmt   = "";

    public String getSndAutoYn() {return sndAutoYn;}

    public void setSndAutoYn(String sndAutoYn) {this.sndAutoYn = sndAutoYn;}

    public String getSndAutoLimtAmt() {return sndAutoLimtAmt;}

    public void setSndAutoLimtAmt(String sndAutoLimtAmt) {this.sndAutoLimtAmt = sndAutoLimtAmt;}

    public String getWithdrowAmt() {return withdrowAmt;}

    public void setWithdrowAmt(String withdrowAmt) {this.withdrowAmt = withdrowAmt;}

    public String getStdDt() {return stdDt;}

    public void setStdDt(String stdDt) {this.stdDt = stdDt;}

    public String getEndDt() {return endDt;}

    public void setEndDt(String endDt) {this.endDt = endDt;}

    public String getCurcyCd() {return curcyCd;}

    public void setCurcyCd(String curcyCd) {this.curcyCd = curcyCd;}

    public String getUserEmail() {return userEmail;}

    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

    public String getTradeType() {return tradeType;}

    public void setTradeType(String tradeType) {this.tradeType = tradeType;}

    public String getSndLimtYn() {return sndLimtYn;}

    public void setSndLimtYn(String sndLimtYn) {this.sndLimtYn = sndLimtYn;}
}
