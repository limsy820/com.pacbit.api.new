package com.bitkrx.api.main.vo;

public class beforeListVO {
	private String buyCnAmt = "";
	private String buyTranPrc = "";
	private String sellCnAmt = "";
	private String sellTranPrc = "";
	private String cnKndCd = "";

    public String getCnKndCd() {
        return cnKndCd;
    }

    public void setCnKndCd(String cnKndCd) {
        this.cnKndCd = cnKndCd;
    }

    public String getBuyCnAmt() {
		return buyCnAmt;
	}
	public void setBuyCnAmt(String buyCnAmt) {
		this.buyCnAmt = buyCnAmt;
	}
	public String getBuyTranPrc() {
		return buyTranPrc;
	}
	public void setBuyTranPrc(String buyTranPrc) {
		this.buyTranPrc = buyTranPrc;
	}
	
	public String getSellCnAmt() {
		return sellCnAmt;
	}
	public void setSellCnAmt(String sellCnAmt) {
		this.sellCnAmt = sellCnAmt;
	}
	public String getSellTranPrc() {
		return sellTranPrc;
	}
	public void setSellTranPrc(String sellTranPrc) {
		this.sellTranPrc = sellTranPrc;
	}
	

}
