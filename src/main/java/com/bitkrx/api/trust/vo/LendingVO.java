package com.bitkrx.api.trust.vo;

import com.bitkrx.config.vo.CmeExtendListVO;

import java.util.List;
import java.util.Map;

public class LendingVO extends CmeExtendListVO {

    private String userEmail                = "";       // 사용자이메일
    private String cnKndCd                  = "";       // 코인코드
    private String applyDt                  = "";       // 신청일
    private String expireDt                 = "";       // 만기일
    private String renDivision              = "";       // 랜딩구분값
    private String applyStatus              = "";       // 신청상태
    private String applyAmt                 = "";       // 신청수량
    private String totRepayAmt              = "";       // 총 상환금액
    private String expInterest              = "";       // 예상이자율
    private String interestRate             = "";       // 이자율
    private String minAmt                   = "";       // 최소수량
    private String maxAmt                   = "";       // 최대수량
    private String mthCmt                   = "";       // 개월수
    private String repayDt                  = "";       // 상환일
    private String btnView                  = "";       // 버튼뷰 (VIEW_Y : 상환 , 연장버튼 둘다 보이기  ,  VIEW_N : 상환버튼만 보이기)
    private String renDiv                   = "";       // 랜딩구분(0:일반 , 1:연장)
    private String overDivDt                = "";       // 연장일
    private String extensionYn              = "";       // 연체여부 (Y:연체 , N:미연체)
    private String clientCd                 = "";       // 클라이언트코드
    private String cnKndNm                  = "";       // 코인명
    private String regEmail                 = "";       // 사용자 이메일
    private String no                       = "";       // 넘버
    private String renDftCode               = "";       // 렌딩시퀀스
    private String extenSionDt              = "";       // 연장만기일
    private String dftRate                  = "";       // 일반 월 이자
    private String dftOverRate              = "";       // 일반 연체 이자
    private String extensionRate            = "";       // 연장 월 이자
    private String extensionOverRate        = "";       // 연장 연체 일 이자
    private String regIp                    = "";       // ip
    private String interestDiv              = "";       // 상환구분(0:일반 , 1:연장)
    private String interestCnt              = "";       // 상환회차
    private String repayYn                  = "";       // 상환 여부(R:대기 , Y:상환 , N:연체)
    private String overInterest             = "";       // 연체이자
    private String memo                     = "";       // 메모
    private String repayAmt                 = "";       // 상환금액

    private int listSize                = 10;           // 페이징
    private int page                    = 1;
    private List<Map> RESULT 		= null;

    public String getRepayAmt() {return repayAmt;}

    public void setRepayAmt(String repayAmt) {this.repayAmt = repayAmt;}

    public String getInterestDiv() {return interestDiv;}

    public void setInterestDiv(String interestDiv) {this.interestDiv = interestDiv;}

    public String getInterestCnt() {return interestCnt;}

    public void setInterestCnt(String interestCnt) {this.interestCnt = interestCnt;}

    public String getRepayYn() {return repayYn;}

    public void setRepayYn(String repayYn) {this.repayYn = repayYn;}

    public String getOverInterest() {return overInterest;}

    public void setOverInterest(String overInterest) {this.overInterest = overInterest;}

    public String getMemo() {return memo;}

    public void setMemo(String memo) {this.memo = memo;}

    public String getRegIp() {return regIp;}

    public void setRegIp(String regIp) {this.regIp = regIp;}

    public String getDftRate() {return dftRate;}

    public void setDftRate(String dftRate) {this.dftRate = dftRate;}

    public String getDftOverRate() {return dftOverRate;}

    public void setDftOverRate(String dftOverRate) {this.dftOverRate = dftOverRate;}

    public String getExtensionRate() {return extensionRate;}

    public void setExtensionRate(String extensionRate) {this.extensionRate = extensionRate;}

    public String getExtensionOverRate() {return extensionOverRate;}

    public void setExtensionOverRate(String extensionOverRate) {this.extensionOverRate = extensionOverRate;}

    public String getExtenSionDt() {return extenSionDt;}

    public void setExtenSionDt(String extenSionDt) {this.extenSionDt = extenSionDt;}

    public String getMthCmt() {return mthCmt;}

    public void setMthCmt(String mthCmt) {this.mthCmt = mthCmt;}

    public String getRenDftCode() {return renDftCode;}

    public void setRenDftCode(String renDftCode) {this.renDftCode = renDftCode;}

    public String getNo() {return no;}

    public void setNo(String no) {this.no = no;}

    public String getRegEmail() {return regEmail;}

    public void setRegEmail(String regEmail) {this.regEmail = regEmail;}

    public String getRepayDt() {return repayDt;}

    public void setRepayDt(String repayDt) {this.repayDt = repayDt;}

    public String getBtnView() {return btnView;}

    public void setBtnView(String btnView) {this.btnView = btnView;}

    public String getCnKndNm() {return cnKndNm;}

    public void setCnKndNm(String cnKndNm) {this.cnKndNm = cnKndNm;}

    public List<Map> getRESULT() {return RESULT;}

    public void setRESULT(List<Map> RESULT) {this.RESULT = RESULT;}

    public String getClientCd() {return clientCd;}

    public void setClientCd(String clientCd) {this.clientCd = clientCd;}

    public int getListSize() {return listSize;}

    public void setListSize(int listSize) {this.listSize = listSize;}

    public int getPage() {return page;}

    public void setPage(int page) {this.page = page;}

    public String getUserEmail() {return userEmail;}

    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

    public String getCnKndCd() {return cnKndCd;}

    public void setCnKndCd(String cnKndCd) {this.cnKndCd = cnKndCd;}

    public String getApplyDt() {return applyDt;}

    public void setApplyDt(String applyDt) {this.applyDt = applyDt;}

    public String getExpireDt() {return expireDt;}

    public void setExpireDt(String expireDt) {this.expireDt = expireDt;}

    public String getRenDivision() {return renDivision;}

    public void setRenDivision(String renDivision) {this.renDivision = renDivision;}

    public String getApplyStatus() {return applyStatus;}

    public void setApplyStatus(String applyStatus) {this.applyStatus = applyStatus;}

    public String getApplyAmt() {return applyAmt;}

    public void setApplyAmt(String applyAmt) {this.applyAmt = applyAmt;}

    public String getTotRepayAmt() {return totRepayAmt;}

    public void setTotRepayAmt(String totRepayAmt) {this.totRepayAmt = totRepayAmt;}

    public String getExpInterest() {return expInterest;}

    public void setExpInterest(String expInterest) {this.expInterest = expInterest;}

    public String getInterestRate() {return interestRate;}

    public void setInterestRate(String interestRate) {this.interestRate = interestRate;}

    public String getMinAmt() {return minAmt;}

    public void setMinAmt(String minAmt) {this.minAmt = minAmt;}

    public String getMaxAmt() {return maxAmt;}

    public void setMaxAmt(String maxAmt) {this.maxAmt = maxAmt;}

    public String getRenDiv() {return renDiv;}

    public void setRenDiv(String renDiv) {this.renDiv = renDiv;}

    public String getOverDivDt() {return overDivDt;}

    public void setOverDivDt(String overDivDt) {this.overDivDt = overDivDt;}

    public String getExtensionYn() {return extensionYn;}

    public void setExtensionYn(String extensionYn) {this.extensionYn = extensionYn;}
}
