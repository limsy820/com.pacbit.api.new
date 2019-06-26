/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.config.vo;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.config.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 28.
 */
public class QuotationVO {
	  private String coin_id = ""; //코인id
	   //private String coinSymbol =""; // 코인 단위
	   //private String exchg_nm = "";//거래소 이름   
	   private String exchgNm = "";//거래소 이름   
	   //private String exchg_last_price = "";//시세(KRW)   
	   private String coinPrc = "";//시세(KRW)   
	   //private String exchg_vol = "";//거래량 
	   private String svcTranAmt = "";
	   //private String exchg_gap = "";//가격 차이(%)
	   private String exchgGap = "";//가격 차이
	   private String exchgGapPer = "";//가격 차이(%)
	public String getExchgGap() {
		return exchgGap;
	}
	public void setExchgGap(String exchgGap) {
		this.exchgGap = exchgGap;
	}
	public String getExchgGapPer() {
		return exchgGapPer;
	}
	public void setExchgGapPer(String exchgGapPer) {
		this.exchgGapPer = exchgGapPer;
	}
	public String getExchgNm() {
		return exchgNm;
	}
	public void setExchgNm(String exchgNm) {
		this.exchgNm = exchgNm;
	}
	public String getCoinPrc() {
		return coinPrc;
	}
	public void setCoinPrc(String coinPrc) {
		this.coinPrc = coinPrc;
	}
	public String getSvcTranAmt() {
		return svcTranAmt;
	}
	public void setSvcTranAmt(String svcTranAmt) {
		this.svcTranAmt = svcTranAmt;
	}
	public String getCoin_id() {
		return coin_id;
	}
	public void setCoin_id(String coin_id) {
		this.coin_id = coin_id;
	}
	
	   
}
