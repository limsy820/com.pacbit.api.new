package com.bitkrx.api.auth.vo;

import com.bitkrx.config.vo.CmeExtendListVO;

import java.io.Serializable;

public class SubmitCertVO extends CmeExtendListVO implements Serializable {

    private String userEmail = "";      // 사용자아이디
    private String certCode = "";       // 파일ID
    private String userMobile = "";     // 사용자 휴대폰 번호
    private String fileName1 = "";      // 신분증 이미지
    private String fileName2 = "";      // 신분증을 들고있는 본인 이미지
    private String fileName3 = "";      // 개명시 등록할 사진
    private String certYn = "";         // 인증여부 1:인증완료 , 2:인증실패 , 3:관리자확인중
    private String certMsg = "";        // 요청사항
    private String certDt = "";         // 인증완료일자
    private String certGrade = "";      // 구분 부모값 (공통코드값 / 본인인증 / 회원탈퇴요청 / 입출금 사실 증명)
    private String certSubGrade = "";   // 구분 자식값
    private String regDt = "";          // 작성일
    private String regEmail = "";       // 작성자
    private String uptDt = "";          // 수정일
    private String uptEmail = "";       // 수정자
    private String certFailMsg = "";    // 인증실패 메시지
    private String lang = "";    // lang

    public String getLang() { return lang; }

    public void setLang(String lang) { this.lang = lang; }

    public String getCertMsg() {
        return certMsg;
    }

    public void setCertMsg(String certMsg) {
        this.certMsg = certMsg;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFileName1() {
        return fileName1;
    }

    public void setFileName1(String fileName1) {
        this.fileName1 = fileName1;
    }

    public String getFileName2() {
        return fileName2;
    }

    public void setFileName2(String fileName2) {
        this.fileName2 = fileName2;
    }

    public String getFileName3() {
        return fileName3;
    }

    public void setFileName3(String fileName3) {
        this.fileName3 = fileName3;
    }

    public String getRegEmail() {
        return regEmail;
    }

    public void setRegEmail(String regEmail) {
        this.regEmail = regEmail;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getUptEmail() {
        return uptEmail;
    }

    public void setUptEmail(String uptEmail) {
        this.uptEmail = uptEmail;
    }

    public String getUptDt() {
        return uptDt;
    }

    public void setUptDt(String uptDt) {
        this.uptDt = uptDt;
    }

    public String getCertYn() {
        return certYn;
    }

    public void setCertYn(String certYn) {
        this.certYn = certYn;
    }

    public String getCertDt() {
        return certDt;
    }

    public void setCertDt(String certDt) {
        this.certDt = certDt;
    }

    public String getCertGrade() {
        return certGrade;
    }

    public void setCertGrade(String certGrade) {
        this.certGrade = certGrade;
    }

    public String getUserMobile() { return userMobile; }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public String getCertFailMsg() {
        return certFailMsg;
    }

    public void setCertFailMsg(String certFailMsg) {
        this.certFailMsg = certFailMsg;
    }

    public String getCertSubGrade() {
        return certSubGrade;
    }

    public void setCertSubGrade(String certSubGrade) {
        this.certSubGrade = certSubGrade;
    }
}
