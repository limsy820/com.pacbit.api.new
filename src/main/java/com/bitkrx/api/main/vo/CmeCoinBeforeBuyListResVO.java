package com.bitkrx.api.main.vo;

import com.bitkrx.config.CmeResultVO;

public class CmeCoinBeforeBuyListResVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1404132414790264353L;
	private String cnAmt				=	"";		//주문량
	private String svcTranPrc			=	"";		//금액

	public String getCnAmt() {
		return cnAmt;
	}
	public void setCnAmt(String cnAmt) {
		this.cnAmt = cnAmt;
	}
	public String getSvcTranPrc() {
		return svcTranPrc;
	}
	public void setSvcTranPrc(String svcTranPrc) {
		this.svcTranPrc = svcTranPrc;
	}
	
}
