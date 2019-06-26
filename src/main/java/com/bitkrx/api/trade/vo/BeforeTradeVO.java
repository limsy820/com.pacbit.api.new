/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.trade.vo;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.trade.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 12. 4.
 */
public class BeforeTradeVO {
	private String orderNo								=	"";		//주문번호
	private String tradeTime							=	"";		//거래시각
	private String cnAmt								=	"";		//주문량
	private String svcTranPrc							=	"";		//금액
	private String orderPrc								=	"";		//주문금액
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
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
	public String getOrderPrc() {
		return orderPrc;
	}
	public void setOrderPrc(String orderPrc) {
		this.orderPrc = orderPrc;
	}
}
