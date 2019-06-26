package com.bitkrx.api.trade.vo;

import com.bitkrx.config.vo.CmeExtendListVO;

public class TradeParamVO extends CmeExtendListVO{

	private String userEmail = "";
	private String curcyCd = "";
	private int listSize = 10;
	private String tradeCd = "";
	private String tradeTime = "";
	private String stdDate = "";
	private String endDate = "";

	private String mkState = "";

    public String getMkState() {
        return mkState;
    }

    public void setMkState(String mkState) {
        this.mkState = mkState;
    }

    public String getStdDate() {
        return stdDate;
    }

    public void setStdDate(String stdDate) {
        this.stdDate = stdDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getCurcyCd() {
		return curcyCd;
	}
	public void setCurcyCd(String curcyCd) {
		this.curcyCd = curcyCd;
	}
	public int getListSize() {
		return listSize;
	}
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	public String getTradeCd() {
		return tradeCd;
	}
	public void setTradeCd(String tradeCd) {
		this.tradeCd = tradeCd;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	
}
