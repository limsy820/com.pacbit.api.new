/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.trade.vo;

import java.util.List;
import java.util.Map;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.trade.vo
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 23.
 */
public class DepositVO {

	private String mnyCrgCode = "";
	private String crgPrc = "";
	private String payKndCd = "";
	private String userEmail = "";
	private String regIp = "";
	private String krxActCode = "";
	private List<Map> RESULT = null;
	private String curcyCd = "";
	private String accNo = "";
	private String cnDptCode = "";
	private String dealNo = "";
	private double cnAmt = 0;
	private String sndWletAdr = "";
	private String dptKndCd = "";
	private String cnDptCode2 = "";
	private double cnclAmt = 0;
	private String accNm = "";
	private String bankNm = "";
	private String destinationTag = "";
	private String wletAdr = "";
	private String curcyNm = "";
	private String sfYn = "";
	private String memo = "";
	private String cnKndCd = "";
	private String code = "";
	private String cardNum = "";
	private String cardPwd = "";
	private String cardDate = "";
	private String cardCvc = "";
	private String exCryCode = "";
	private String exRate = "";
	private String subUserEmail = "";
	private String atmPwd = "";
	private String atmKey = "";
	private String urlInfo = "";
	private String curcyType = "";
	private String moveFee = "";
	private String txId = "";
	private String cnKndNm	= "";
	private String privateKey = "";

	public String getPrivateKey() {return privateKey;}

	public void setPrivateKey(String privateKey) {this.privateKey = privateKey;}

	public String getTxId() {return txId;}

	public void setTxId(String txId) {this.txId = txId;}

	public String getCnKndNm() {return cnKndNm;}

	public void setCnKndNm(String cnKndNm) {this.cnKndNm = cnKndNm;}

	public String getMoveFee() {
        return moveFee;
    }

    public void setMoveFee(String moveFee) {
        this.moveFee = moveFee;
    }

    public String getConfirnCnt() {

        return confirnCnt;
    }

    public void setConfirnCnt(String confirnCnt) {
        this.confirnCnt = confirnCnt;
    }

    private String confirnCnt = "";

    public String getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(String urlInfo) {
        this.urlInfo = urlInfo;
    }

    public String getCurcyType() {
        return curcyType;
    }

    public void setCurcyType(String curcyType) {
        this.curcyType = curcyType;
    }

    public String getAtmKey() {
        return atmKey;
    }

    public void setAtmKey(String atmKey) {
        this.atmKey = atmKey;
    }

    public String getAtmPwd() {
        return atmPwd;
    }

    public void setAtmPwd(String atmPwd) {
        this.atmPwd = atmPwd;
    }

    public String getSubUserEmail() { return subUserEmail; }

	public void setSubUserEmail(String subUserEmail) { this.subUserEmail = subUserEmail; }
	private String clientPe = "";

    public String getClientPe() {
        return clientPe;
    }

    public void setClientPe(String clientPe) {
        this.clientPe = clientPe;
    }

    public String getExCryCode() {
        return exCryCode;
    }

    public void setExCryCode(String exCryCode) {
        this.exCryCode = exCryCode;
    }

    public String getExRate() {
        return exRate;
    }

    public void setExRate(String exRate) {
        this.exRate = exRate;
    }

    public String getCardPwd() {
        return cardPwd;
    }

    public void setCardPwd(String cardPwd) {
        this.cardPwd = cardPwd;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public String getCardCvc() {
        return cardCvc;
    }

    public void setCardCvc(String cardCvc) {
        this.cardCvc = cardCvc;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getTrnId() {
        return trnId;
    }

    public void setTrnId(String trnId) {
        this.trnId = trnId;
    }

    private String trnId = "";

	public String getSfYn() {
		return sfYn;
	}

	public void setSfYn(String sfYn) {
		this.sfYn = sfYn;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCnKndCd() {
		return cnKndCd;
	}

	public void setCnKndCd(String cnKndCd) {
		this.cnKndCd = cnKndCd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the destinationTag
	 */
	public String getDestinationTag() {
		return destinationTag;
	}
	/**
	 * @param destinationTag the destinationTag to set
	 */
	public void setDestinationTag(String destinationTag) {
		this.destinationTag = destinationTag;
	}
	/**
	 * @return the wletAdr
	 */
	public String getWletAdr() {
		return wletAdr;
	}
	/**
	 * @param wletAdr the wletAdr to set
	 */
	public void setWletAdr(String wletAdr) {
		this.wletAdr = wletAdr;
	}
	/**
	 * @return the accNm
	 */
	public String getAccNm() {
		return accNm;
	}
	/**
	 * @param accNm the accNm to set
	 */
	public void setAccNm(String accNm) {
		this.accNm = accNm;
	}
	/**
	 * @return the bankNm
	 */
	public String getBankNm() {
		return bankNm;
	}
	/**
	 * @param bankNm the bankNm to set
	 */
	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}
	/**
	 * @return the dptKndCd
	 */
	public String getDptKndCd() {
		return dptKndCd;
	}
	/**
	 * @param dptKndCd the dptKndCd to set
	 */
	public void setDptKndCd(String dptKndCd) {
		this.dptKndCd = dptKndCd;
	}
	/**
	 * @return the sndWletAdr
	 */
	public String getSndWletAdr() {
		return sndWletAdr;
	}
	/**
	 * @param sndWletAdr the sndWletAdr to set
	 */
	public void setSndWletAdr(String sndWletAdr) {
		this.sndWletAdr = sndWletAdr;
	}
	/**
	 * @return the dealNo
	 */
	public String getDealNo() {
		return dealNo;
	}
	/**
	 * @param dealNo the dealNo to set
	 */
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	/**
	 * @param rESULT the rESULT to set
	 */
	public void setRESULT(List<Map> rESULT) {
		RESULT = rESULT;
	}
	public String getMnyCrgCode() {
		return mnyCrgCode;
	}
	public void setMnyCrgCode(String mnyCrgCode) {
		this.mnyCrgCode = mnyCrgCode;
	}
	public String getCrgPrc() {
		return crgPrc;
	}
	public void setCrgPrc(String crgPrc) {
		this.crgPrc = crgPrc;
	}
	public String getPayKndCd() {
		return payKndCd;
	}
	public void setPayKndCd(String payKndCd) {
		this.payKndCd = payKndCd;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getRegIp() {
		return regIp;
	}
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	/**
	 * @return the krxActCode
	 */
	public String getKrxActCode() {
		return krxActCode;
	}
	/**
	 * @param krxActCode the krxActCode to set
	 */
	public void setKrxActCode(String krxActCode) {
		this.krxActCode = krxActCode;
	}
	/**
	 * @return the rESULT
	 */
	public List getRESULT() {
		return RESULT;
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
	 * @return the accNo
	 */
	public String getAccNo() {
		return accNo;
	}
	/**
	 * @param accNo the accNo to set
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	/**
	 * @return the cnDptCode
	 */
	public String getCnDptCode() {
		return cnDptCode;
	}
	/**
	 * @param cnDptCode the cnDptCode to set
	 */
	public void setCnDptCode(String cnDptCode) {
		this.cnDptCode = cnDptCode;
	}
	/**
	 * @return the cnAmt
	 */
	public double getCnAmt() {
		return cnAmt;
	}
	/**
	 * @param cnAmt the cnAmt to set
	 */
	public void setCnAmt(double cnAmt) {
		this.cnAmt = cnAmt;
	}
	/**
	 * @return the cnDptCode2
	 */
	public String getCnDptCode2() {
		return cnDptCode2;
	}
	/**
	 * @param cnDptCode2 the cnDptCode2 to set
	 */
	public void setCnDptCode2(String cnDptCode2) {
		this.cnDptCode2 = cnDptCode2;
	}
	/**
	 * @return the cnclAmt
	 */
	public double getCnclAmt() {
		return cnclAmt;
	}
	/**
	 * @param cnclAmt the cnclAmt to set
	 */
	public void setCnclAmt(double cnclAmt) {
		this.cnclAmt = cnclAmt;
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
}
