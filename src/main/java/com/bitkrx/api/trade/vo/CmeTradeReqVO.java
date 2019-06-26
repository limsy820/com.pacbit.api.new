package com.bitkrx.api.trade.vo;

import com.bitkrx.config.vo.CmeExtendListVO;

import java.util.List;
import java.util.Map;

public class CmeTradeReqVO extends CmeExtendListVO{

	private String apntPhsYn							=	"";		//구매구분 Y:지정가 N:시장가
	private String userEmail							=	"";		//사용자 이메일
	private String buyCurcyCd							=	"";		//구매통화
	private String payCurcyCd							=	"";		//결제통화
	private String phsPrc								=	"";		//주문가격
	private String phsAmt								=	"";		//주문수량
	private String sellCurcyCd							=	"";		//판매통화
	private String curcyCd								=	"";		//통화
	private String rcvCurCd								=	"";		//수령통화
	private String orderCd								=	"";		//구분 01:구매, 02:판매
	private String orderNo								=	"";		//주문번호
	private String hisCode								=	"";		//내역구분 01:충전, 02:입금, 03:송금, 04:출금, 05:구매, 06:판매
	private String stdDate								=	"";		//시작일
	private String endDate								=	"";		//종료일
	private String apntPhsCd							= 	"";		//거래내용
	private String cnPhsCode							=	"";		//코인구매코드
	private String cshPrc								=	"";		//현물구매금액
	private String pntPrc								=	"";		//포인트구매금액
	private String cnKndCd								=	"";		//코인코드(공통코드)
	private String regIp								=	"";		//IP
	private String usePrc								=	"";		//총 구매사용원화금액
	private String cnPrc								=	"";		//코인가격
	private String totPrc								=	"";		//총가격
	private String rcvCurcyCd							=	"";		
	private String remAmt								=	"";		//부분취소수량
	private String remPrc								=	"";   	//부분취소금액
	private String isTrade								=	"";		//부분체결여부

    private String mkState                              =   "";
    private String tradeType							= 	"";  	//구매타입 S:판매 , B:구매
	private String autoTradeType						=   "";		//자동거래타입 A:자동거래

	private double remPrc2 								= 0;
	private String mkStateDefault                              =   "";
	private String cmmNm								= 	"";
	private String key01								= 	"";
	private String key02								=	"";
	private String cnSelCode							=	"";

	public String getCnSelCode() {return cnSelCode;}

	public void setCnSelCode(String cnSelCode) {this.cnSelCode = cnSelCode;}

	public String getKey01() {return key01;}

	public void setKey01(String key01) {this.key01 = key01;}

	public String getKey02() {return key02;}

	public void setKey02(String key02) {this.key02 = key02;}

	public String getCmmNm() {return cmmNm;}

	public void setCmmNm(String cmmNm) {this.cmmNm = cmmNm;}

	public String getMkStateDefault() { return mkStateDefault; }

	public void setMkStateDefault(String mkStateDefault) { this.mkStateDefault = mkStateDefault;}

	public double getRemPrc2() {return remPrc2;}

	public void setRemPrc2(double remPrc2) {this.remPrc2 = remPrc2;}

	public String getAutoTradeType() {return autoTradeType;}

	public void setAutoTradeType(String autoTradeType) {this.autoTradeType = autoTradeType;}

	public String getTradeType() {return tradeType;}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getMkState() {
        return mkState;
    }

    public void setMkState(String mkState) {
        this.mkState = mkState;
    }

    private List<Map> RESULT = null;
	
	private String cncl 								=	"";
	
	private String orderState							=	"";		//주문상태
	private String usePointPrc							=	"";		//포인트사용금액
	
	private String clientCd								=	"";
	private int listSize = 10;
	
	private String crgPrc = "";
	private String cnclPrc = "";
	private String payKndCd = "";

	private String sellBuyCd = "";
	private String amount = "";
	private String no = "";

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSellBuyCd() {
        return sellBuyCd;
    }

    public void setSellBuyCd(String sellBuyCd) {
        this.sellBuyCd = sellBuyCd;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCnPhsCode() {
		return cnPhsCode;
	}
	
	/**
	 * @return the rcvCurcyCd
	 */
	public String getRcvCurcyCd() {
		return rcvCurcyCd;
	}

	/**
	 * @param rcvCurcyCd the rcvCurcyCd to set
	 */
	public void setRcvCurcyCd(String rcvCurcyCd) {
		this.rcvCurcyCd = rcvCurcyCd;
	}

	/**
	 * @return the crgPrc
	 */
	public String getCrgPrc() {
		return crgPrc;
	}

	/**
	 * @param crgPrc the crgPrc to set
	 */
	public void setCrgPrc(String crgPrc) {
		this.crgPrc = crgPrc;
	}

	/**
	 * @return the payKndCd
	 */
	public String getPayKndCd() {
		return payKndCd;
	}

	/**
	 * @param payKndCd the payKndCd to set
	 */
	public void setPayKndCd(String payKndCd) {
		this.payKndCd = payKndCd;
	}

	public void setCnPhsCode(String cnPhsCode) {
		this.cnPhsCode = cnPhsCode;
	}

	public String getCshPrc() {
		return cshPrc;
	}

	public void setCshPrc(String cshPrc) {
		this.cshPrc = cshPrc;
	}

	public String getPntPrc() {
		return pntPrc;
	}

	public void setPntPrc(String pntPrc) {
		this.pntPrc = pntPrc;
	}

	public String getCnKndCd() {
		return cnKndCd;
	}

	public void setCnKndCd(String cnKndCd) {
		this.cnKndCd = cnKndCd;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public String getApntPhsYn() {
		return apntPhsYn;
	}

	public void setApntPhsYn(String apntPhsYn) {
		this.apntPhsYn = apntPhsYn;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getBuyCurcyCd() {
		return buyCurcyCd;
	}

	public void setBuyCurcyCd(String buyCurcyCd) {
		this.buyCurcyCd = buyCurcyCd;
	}
	

	/**
	 * @return the remAmt
	 */
	public String getRemAmt() {
		return remAmt;
	}

	/**
	 * @param remAmt the remAmt to set
	 */
	public void setRemAmt(String remAmt) {
		this.remAmt = remAmt;
	}

	/**
	 * @return the remPrc
	 */
	public String getRemPrc() {
		return remPrc;
	}

	/**
	 * @param remPrc the remPrc to set
	 */
	public void setRemPrc(String remPrc) {
		this.remPrc = remPrc;
	}
	public String getPayCurcyCd() {
		return payCurcyCd;
	}

	public void setPayCurcyCd(String payCurcyCd) {
		this.payCurcyCd = payCurcyCd;
	}

	public String getPhsPrc() {
		return phsPrc;
	}

	public void setPhsPrc(String phsPrc) {
		this.phsPrc = phsPrc;
	}

	public String getPhsAmt() {
		return phsAmt;
	}

	public void setPhsAmt(String phsAmt) {
		this.phsAmt = phsAmt;
	}

	public String getSellCurcyCd() {
		return sellCurcyCd;
	}

	public void setSellCurcyCd(String sellCurcyCd) {
		this.sellCurcyCd = sellCurcyCd;
	}

	public String getCurcyCd() {
		return curcyCd;
	}

	public void setCurcyCd(String curcyCd) {
		this.curcyCd = curcyCd;
	}

	public String getRcvCurCd() {
		return rcvCurCd;
	}

	public void setRcvCurCd(String rcvCurCd) {
		this.rcvCurCd = rcvCurCd;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getHisCode() {
		return hisCode;
	}

	public void setHisCode(String hisCode) {
		this.hisCode = hisCode;
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

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public String getOrderCd() {
		return orderCd;
	}

	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}

	/**
	 * @return the clientCd
	 */
	public String getClientCd() {
		return clientCd;
	}

	/**
	 * @param clientCd the clientCd to set
	 */
	public void setClientCd(String clientCd) {
		this.clientCd = clientCd;
	}

	/**
	 * @return the usePointPrc
	 */
	public String getUsePointPrc() {
		return usePointPrc;
	}

	/**
	 * @param usePointPrc the usePointPrc to set
	 */
	public void setUsePointPrc(String usePointPrc) {
		this.usePointPrc = usePointPrc;
	}

	/**
	 * @return the apntPhsCd
	 */
	public String getApntPhsCd() {
		return apntPhsCd;
	}

	/**
	 * @param apntPhsCd the apntPhsCd to set
	 */
	public void setApntPhsCd(String apntPhsCd) {
		this.apntPhsCd = apntPhsCd;
	}

	/**
	 * @return the rESULT
	 */
	public List<Map> getRESULT() {
		return RESULT;
	}

	/**
	 * @param rESULT the rESULT to set
	 */
	public void setRESULT(List<Map> rESULT) {
		RESULT = rESULT;
	}

	/**
	 * @return the usePrc
	 */
	public String getUsePrc() {
		return usePrc;
	}

	/**
	 * @param usePrc the usePrc to set
	 */
	public void setUsePrc(String usePrc) {
		this.usePrc = usePrc;
	}

	/**
	 * @return the isTrade
	 */
	public String getIsTrade() {
		return isTrade;
	}

	/**
	 * @param isTrade the isTrade to set
	 */
	public void setIsTrade(String isTrade) {
		this.isTrade = isTrade;
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
	 * @return the totPrc
	 */
	public String getTotPrc() {
		return totPrc;
	}

	/**
	 * @param totPrc the totPrc to set
	 */
	public void setTotPrc(String totPrc) {
		this.totPrc = totPrc;
	}

	/**
	 * @return the cncl
	 */
	public String getCncl() {
		return cncl;
	}

	/**
	 * @param cncl the cncl to set
	 */
	public void setCncl(String cncl) {
		this.cncl = cncl;
	}

	/**
	 * @return the cnclPrc
	 */
	public String getCnclPrc() {
		return cnclPrc;
	}

	/**
	 * @param cnclPrc the cnclPrc to set
	 */
	public void setCnclPrc(String cnclPrc) {
		this.cnclPrc = cnclPrc;
	}
	
}
