package com.bitkrx.api.trust.vo;

import com.bitkrx.config.vo.CmeExtendListVO;

import java.io.Serializable;

public class TrustCoinVO extends CmeExtendListVO{

    private String trustReqCode         = "";           // Turst 신청코드
    private String trustStatus          = "";           // Trust 신청상태
    private String userEmail            = "";           // 이메일
    private String cnKndCd              = "";           // 코인코드
    private String reqAmt               = "";           // 신청수량
    private String rate                 = "";           // 이자율
    private String expInterest          = "";           // 예상이자
    private String reqDt                = "";           // 신청일
    private String expireDt             = "";           // 만기일
    private String cancelDt             = "";           // 해지일
    private String month                = "";           // 개월
    private String regIp                   = "";        // ip
    private String totAmt               = "";           // 총 수령액
    private String cmmNm                = "";           // 코인이름
    private String no                   = "";           // 넘버
    private String totalAmt             = "";           // 총 수령액
    private String clientCd             = "";           // 클라이언트코드

    private String cnDptCode             = "";          // 입금코드
    private String curcyCd               = "";          // 코인코드
    private String cnAmt                 = "";          // 이자입금 수량
    private double posAmt               =  0;           // 신청한 코인수량
    private int listSize                = 10;           // 페이징

    private int page                    = 1;

    private String minusAsset = "";

    public String getMinusAsset() {
        return minusAsset;
    }

    public void setMinusAsset(String minusAsset) {
        this.minusAsset = minusAsset;
    }

    public int getPage() {return page;}

    public void setPage(int page) {this.page = page;}

    public String getRegIp() {return regIp;}

    public void setRegIp(String regIp) {this.regIp = regIp;}

    public String getClientCd() {return clientCd;}

    public void setClientCd(String clientCd) {this.clientCd = clientCd;}

    public String getTotalAmt() {return totalAmt;}

    public void setTotalAmt(String totalAmt) {this.totalAmt = totalAmt;}

    public String getNo() {return no;}

    public void setNo(String no) {this.no = no;}

    public int getListSize() {return listSize;}

    public void setListSize(int listSize) {this.listSize = listSize;}

    public double getPosAmt() {return posAmt;}

    public void setPosAmt(double posAmt) {this.posAmt = posAmt;}

    public String getTrustReqCode() {return trustReqCode;}

    public void setTrustReqCode(String trustReqCode) {this.trustReqCode = trustReqCode;}

    public String getCmmNm() {return cmmNm;}

    public void setCmmNm(String cmmNm) {this.cmmNm = cmmNm;}

    public String getTotAmt() {return totAmt;}

    public void setTotAmt(String totAmt) {this.totAmt = totAmt;}

    public String getMonth() {return month;}

    public void setMonth(String month) {this.month = month;}

    public String getExpInterest() {return expInterest;}

    public void setExpInterest(String expInterest) {this.expInterest = expInterest;}

    public String getTrustStatus() {return trustStatus;}

    public void setTrustStatus(String trustStatus) {this.trustStatus = trustStatus;}

    public String getUserEmail() {return userEmail;}

    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

    public String getCnKndCd() {return cnKndCd;}

    public void setCnKndCd(String cnKndCd) {this.cnKndCd = cnKndCd;}

    public String getReqAmt() {return reqAmt;}

    public void setReqAmt(String reqAmt) {this.reqAmt = reqAmt;}

    public String getRate() {return rate;}

    public void setRate(String rate) {this.rate = rate;}

    public String getReqDt() {return reqDt;}

    public void setReqDt(String reqDt) {this.reqDt = reqDt;}

    public String getExpireDt() {return expireDt;}

    public void setExpireDt(String expireDt) {this.expireDt = expireDt;}

    public String getCancelDt() {return cancelDt;}

    public void setCancelDt(String cancelDt) {this.cancelDt = cancelDt;}


    public String getCnDptCode() {
        return cnDptCode;
    }

    public void setCnDptCode(String cnDptCode) {
        this.cnDptCode = cnDptCode;
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
}
