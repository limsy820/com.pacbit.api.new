package com.bitkrx.api.auth.vo;

public class CommonVO {

    private String exchgUrl = "";
    private String bi = "";
    private String langCd = "";
    private String fileStreCours = "";
    private String streFileNm = "";
    private String fileSn  = "";

    public String getFileSn() {return fileSn;}

    public void setFileSn(String fileSn) {this.fileSn = fileSn;}

    public String getFileStreCours() {
        return fileStreCours;
    }

    public void setFileStreCours(String fileStreCours) {
        this.fileStreCours = fileStreCours;
    }

    public String getStreFileNm() {
        return streFileNm;
    }

    public void setStreFileNm(String streFileNm) {
        this.streFileNm = streFileNm;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getExchgUrl() {
        return exchgUrl;
    }

    public void setExchgUrl(String exchgUrl) {
        this.exchgUrl = exchgUrl;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }
}
