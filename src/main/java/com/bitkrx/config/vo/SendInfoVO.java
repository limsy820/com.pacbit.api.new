/**
 * (주)크림스 의 시스템 웹어플리케이션 프레임웍 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)크림스
 * CopyRight Creams. inc. Since 2015 
 * 총괄 개발 책임자 : 주식회사 크림스 통합개발연구소 소장 김윤관
 */
package com.bitkrx.config.vo;

import java.io.Serializable;

/**
 * @프로젝트명	: cme.web.ApiAPIS
 * @패키지    	: cme.com.web.common.vo
 * @클래스명  	: cme.web.ApiAPIS
 * @작성자		: (주)씨엠이소프트 최종근
 * @작성일		: 2016. 12. 3.
 */
public class SendInfoVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1204457403404359773L;
	String email_info		=		"";
	String name_info		=		"";
	String mobile_info		=		"";
	String send_date		=		"";
	String etc1				=		"";
	String etc2				=		"";
	String etc3				=		"";
	String etc4				=		"";	
	String etc5				=		"";
	String s_gubun			=		"";
	String auth_key			=		"";	
	String biz_id			=		"";	
	String clientCd			=		"";
	String clientNm			=		"";
	
	private String langCd = "";
	private String userEmail	= "";
	private String sellCurCd  	= "";		//판매통화 01:BTC,02:ETH,03:BCH,04:XRP,05:KRW
	private String sellCurNm  	= "";		//BTC, ETH, BCH, XRP, KRW
	private String autoGubun 	= "";		//판매 / 구매 구분값
	private String exchgNm  	= "";		//
	private String customerTel  = "";		//
	private String bizNo  		= "";		//
	private String bizTel  		= "";		//
	private String bizEmail 	= "";		//
	private String bizAddr  	= "";		//
	private String tradeDt  	= "";		//
	private String tradePrc  	= "";		//
	private String tradeAmt  	= "";		//
	private String url  		= "";		//
	private String cnDt  		= "";		//
	private String cnPrc  		= "";		//
	private String cnAmt  		= "";		//
	private String totCnAmt  	= "";		//
	private String setDt  		= "";		//
	private String sellCnAmt  	= "";		//
	private String getCnAmt  	= "";		//
	private String sellCnPrc  	= "";		//
	private String getCnPrc  	= "";		//
	private String transPrc  	= "";		//
	private String curcyCd		= "";		//
	private String curcyNm		= "";		//
	private String regIp		= "";		//
	private String crgPrc		= "";
	
	private String isOnlyMts = "";
	private String pushType = "";
	/**
	 * @return the exchgNm
	 */
	public String getExchgNm() {
		return exchgNm;
	}
	/**
	 * @param exchgNm the exchgNm to set
	 */
	public void setExchgNm(String exchgNm) {
		this.exchgNm = exchgNm;
	}
	/**
	 * @return the customerTel
	 */
	public String getCustomerTel() {
		return customerTel;
	}
	/**
	 * @param customerTel the customerTel to set
	 */
	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}
	/**
	 * @return the bizNo
	 */
	public String getBizNo() {
		return bizNo;
	}
	/**
	 * @param bizNo the bizNo to set
	 */
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	/**
	 * @return the bizTel
	 */
	public String getBizTel() {
		return bizTel;
	}
	/**
	 * @param bizTel the bizTel to set
	 */
	public void setBizTel(String bizTel) {
		this.bizTel = bizTel;
	}
	/**
	 * @return the bizEmail
	 */
	public String getBizEmail() {
		return bizEmail;
	}
	/**
	 * @param bizEmail the bizEmail to set
	 */
	public void setBizEmail(String bizEmail) {
		this.bizEmail = bizEmail;
	}
	/**
	 * @return the bizAddr
	 */
	public String getBizAddr() {
		return bizAddr;
	}
	/**
	 * @param bizAddr the bizAddr to set
	 */
	public void setBizAddr(String bizAddr) {
		this.bizAddr = bizAddr;
	}
	/**
	 * @return the tradeDt
	 */
	public String getTradeDt() {
		return tradeDt;
	}
	/**
	 * @param tradeDt the tradeDt to set
	 */
	public void setTradeDt(String tradeDt) {
		this.tradeDt = tradeDt;
	}
	/**
	 * @return the tradePrc
	 */
	public String getTradePrc() {
		return tradePrc;
	}
	/**
	 * @param tradePrc the tradePrc to set
	 */
	public void setTradePrc(String tradePrc) {
		this.tradePrc = tradePrc;
	}
	/**
	 * @return the tradeAmt
	 */
	public String getTradeAmt() {
		return tradeAmt;
	}
	/**
	 * @param tradeAmt the tradeAmt to set
	 */
	public void setTradeAmt(String tradeAmt) {
		this.tradeAmt = tradeAmt;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the cnDt
	 */
	public String getCnDt() {
		return cnDt;
	}
	/**
	 * @param cnDt the cnDt to set
	 */
	public void setCnDt(String cnDt) {
		this.cnDt = cnDt;
	}
	/**
	 * @return the cnPrc
	 */
	public String getCnPrc() {
		return cnPrc;
	}
	/**
	 * @param cnPrc the cnPrc to set
	 */
	public void setCnPrc(String cnPrc) {
		this.cnPrc = cnPrc;
	}
	/**
	 * @return the cnAmt
	 */
	public String getCnAmt() {
		return cnAmt;
	}
	/**
	 * @param cnAmt the cnAmt to set
	 */
	public void setCnAmt(String cnAmt) {
		this.cnAmt = cnAmt;
	}
	/**
	 * @return the totCnAmt
	 */
	public String getTotCnAmt() {
		return totCnAmt;
	}
	/**
	 * @param totCnAmt the totCnAmt to set
	 */
	public void setTotCnAmt(String totCnAmt) {
		this.totCnAmt = totCnAmt;
	}
	/**
	 * @return the setDt
	 */
	public String getSetDt() {
		return setDt;
	}
	/**
	 * @param setDt the setDt to set
	 */
	public void setSetDt(String setDt) {
		this.setDt = setDt;
	}
	/**
	 * @return the sellCnAmt
	 */
	public String getSellCnAmt() {
		return sellCnAmt;
	}
	/**
	 * @param sellCnAmt the sellCnAmt to set
	 */
	public void setSellCnAmt(String sellCnAmt) {
		this.sellCnAmt = sellCnAmt;
	}
	/**
	 * @return the getCnAmt
	 */
	public String getGetCnAmt() {
		return getCnAmt;
	}
	/**
	 * @param getCnAmt the getCnAmt to set
	 */
	public void setGetCnAmt(String getCnAmt) {
		this.getCnAmt = getCnAmt;
	}
	/**
	 * @return the sellCnPrc
	 */
	public String getSellCnPrc() {
		return sellCnPrc;
	}
	/**
	 * @param sellCnPrc the sellCnPrc to set
	 */
	public void setSellCnPrc(String sellCnPrc) {
		this.sellCnPrc = sellCnPrc;
	}
	/**
	 * @return the getCnPrc
	 */
	public String getGetCnPrc() {
		return getCnPrc;
	}
	/**
	 * @param getCnPrc the getCnPrc to set
	 */
	public void setGetCnPrc(String getCnPrc) {
		this.getCnPrc = getCnPrc;
	}
	/**
	 * @return the transPrc
	 */
	public String getTransPrc() {
		return transPrc;
	}
	/**
	 * @param transPrc the transPrc to set
	 */
	public void setTransPrc(String transPrc) {
		this.transPrc = transPrc;
	}
	public String getSellCurCd() {
		return sellCurCd;
	}
	public void setSellCurCd(String sellCurCd) {
		this.sellCurCd = sellCurCd;
	}
	public String getAutoGubun() {
		return autoGubun;
	}
	public void setAutoGubun(String autoGubun) {
		this.autoGubun = autoGubun;
	}
	public String getClientCd() {
		return clientCd;
	}
	public void setClientCd(String clientCd) {
		this.clientCd = clientCd;
	}
	public String getClientNm() {
		return clientNm;
	}
	public void setClientNm(String clientNm) {
		this.clientNm = clientNm;
	}
	private String contents = "";
	/**
	 * @return the email_info
	 */
	public String getEmail_info() {
		return email_info;
	}
	/**
	 * @param email_info the email_info to set
	 */
	public void setEmail_info(String email_info) {
		this.email_info = email_info;
	}
	/**
	 * @return the name_info
	 */
	public String getName_info() {
		return name_info;
	}
	/**
	 * @param name_info the name_info to set
	 */
	public void setName_info(String name_info) {
		this.name_info = name_info;
	}
	/**
	 * @return the mobile_info
	 */
	public String getMobile_info() {
		return mobile_info;
	}
	/**
	 * @param mobile_info the mobile_info to set
	 */
	public void setMobile_info(String mobile_info) {
		this.mobile_info = mobile_info;
	}
	/**
	 * @return the send_date
	 */
	public String getSend_date() {
		return send_date;
	}
	/**
	 * @param send_date the send_date to set
	 */
	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}
	/**
	 * @return the etc1
	 */
	public String getEtc1() {
		return etc1;
	}
	/**
	 * @param etc1 the etc1 to set
	 */
	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}
	/**
	 * @return the etc2
	 */
	public String getEtc2() {
		return etc2;
	}
	/**
	 * @param etc2 the etc2 to set
	 */
	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}
	/**
	 * @return the etc3
	 */
	public String getEtc3() {
		return etc3;
	}
	/**
	 * @param etc3 the etc3 to set
	 */
	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}
	/**
	 * @return the etc4
	 */
	public String getEtc4() {
		return etc4;
	}
	/**
	 * @param etc4 the etc4 to set
	 */
	public void setEtc4(String etc4) {
		this.etc4 = etc4;
	}
	/**
	 * @return the etc5
	 */
	public String getEtc5() {
		return etc5;
	}
	/**
	 * @param etc5 the etc5 to set
	 */
	public void setEtc5(String etc5) {
		this.etc5 = etc5;
	}
	/**
	 * @return the s_gubun
	 */
	public String getS_gubun() {
		return s_gubun;
	}
	/**
	 * @param s_gubun the s_gubun to set
	 */
	public void setS_gubun(String s_gubun) {
		this.s_gubun = s_gubun;
	}
	/**
	 * @return the auth_key
	 */
	public String getAuth_key() {
		return auth_key;
	}
	/**
	 * @param auth_key the auth_key to set
	 */
	public void setAuth_key(String auth_key) {
		this.auth_key = auth_key;
	}
	/**
	 * @return the biz_id
	 */
	public String getBiz_id() {
		return biz_id;
	}
	/**
	 * @param biz_id the biz_id to set
	 */
	public void setBiz_id(String biz_id) {
		this.biz_id = biz_id;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getSellCurNm() {
		return sellCurNm;
	}
	public void setSellCurNm(String sellCurNm) {
		this.sellCurNm = sellCurNm;
	}
	/**
	 * @return the curcyCd
	 */
	public String getCurcyCd() {
		return curcyCd;
	}
	/**
	 * @param curcyCd the curcyCd to set
	 */
	public void setCurcyCd(String curcyCd) {
		this.curcyCd = curcyCd;
	}
	/**
	 * @return the curcyNm
	 */
	public String getCurcyNm() {
		return curcyNm;
	}
	/**
	 * @param curcyNm the curcyNm to set
	 */
	public void setCurcyNm(String curcyNm) {
		this.curcyNm = curcyNm;
	}
	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}
	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	/**
	 * @return the regIp
	 */
	public String getRegIp() {
		return regIp;
	}
	/**
	 * @param regIp the regIp to set
	 */
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	public String getCrgPrc() {
		return crgPrc;
	}
	public void setCrgPrc(String crgPrc) {
		this.crgPrc = crgPrc;
	}
	/**
	 * @return the pushType
	 */
	public String getPushType() {
		return pushType;
	}
	/**
	 * @param pushType the pushType to set
	 */
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	/**
	 * @return the isOnlyMts
	 */
	public String getIsOnlyMts() {
		return isOnlyMts;
	}
	/**
	 * @param isOnlyMts the isOnlyMts to set
	 */
	public void setIsOnlyMts(String isOnlyMts) {
		this.isOnlyMts = isOnlyMts;
	}
	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return langCd;
	}
	/**
	 * @param langCd the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}	
	
}
