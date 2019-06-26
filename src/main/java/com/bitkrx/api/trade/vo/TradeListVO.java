package com.bitkrx.api.trade.vo;

public class TradeListVO {
	private String orderCd = "";
	private String conTm = "";
	private String conAmt = "";
	private String aveConPrc = "";
	private String conPrc = "";
	private String fee = "";
	private String totConAmt = "";
	private String cnKndCd = "";

    public String getCnKndCd() {
        return cnKndCd;
    }

    public void setCnKndCd(String cnKndCd) {
        this.cnKndCd = cnKndCd;
    }

    public String getOrderCd() {
		return orderCd;
	}
	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}
	public String getConTm() {
		return conTm;
	}
	public void setConTm(String conTm) {
		this.conTm = conTm;
	}
	public String getConAmt() {
		return conAmt;
	}
	public void setConAmt(String conAmt) {
		this.conAmt = conAmt;
	}
	public String getAveConPrc() {
		return aveConPrc;
	}
	public void setAveConPrc(String aveConPrc) {
		this.aveConPrc = aveConPrc;
	}
	public String getConPrc() {
		return conPrc;
	}
	public void setConPrc(String conPrc) {
		this.conPrc = conPrc;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getTotConAmt() {
		return totConAmt;
	}
	public void setTotConAmt(String totConAmt) {
		this.totConAmt = totConAmt;
	}

}
