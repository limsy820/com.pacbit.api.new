package com.bitkrx.api.moaCard.vo;

public class MoaCardCoinVO {

    private String ptmCoinOutCode   = "";           // PTM코인출금코드
    private String cnKndCd          = "";           // 코인종류코드
    private String useAmt           = "";           // 사용코인수
    private String cnPrc            = "";           // 시세
    private String outPrc           = "";           // 지급금액
    private String feeAmt           = "";           // 수수료
    private String userEmail        = "";           // 사용자이메일
    private String atmMchKey        = "";           // 기기번호
    private String cardNo           = "";           // 카드번호
    private String regIp            = "";

    public String getPtmCoinOutCode() {return ptmCoinOutCode;}

    public void setPtmCoinOutCode(String ptmCoinOutCode) {this.ptmCoinOutCode = ptmCoinOutCode;}

    public String getRegIp() {return regIp;}

    public void setRegIp(String regIp) {this.regIp = regIp;}

    public String getCnKndCd() {return cnKndCd;}

    public void setCnKndCd(String cnKndCd) {this.cnKndCd = cnKndCd;}

    public String getUseAmt() {return useAmt;}

    public void setUseAmt(String useAmt) {this.useAmt = useAmt;}

    public String getCnPrc() {return cnPrc;}

    public void setCnPrc(String cnPrc) {this.cnPrc = cnPrc;}

    public String getOutPrc() {return outPrc;}

    public void setOutPrc(String outPrc) {this.outPrc = outPrc;}

    public String getFeeAmt() {return feeAmt;}

    public void setFeeAmt(String feeAmt) {this.feeAmt = feeAmt;}

    public String getUserEmail() {return userEmail;}

    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

    public String getAtmMchKey() {return atmMchKey;}

    public void setAtmMchKey(String atmMchKey) {this.atmMchKey = atmMchKey;}

    public String getCardNo() {return cardNo;}

    public void setCardNo(String cardNo) {this.cardNo = cardNo;}
}
