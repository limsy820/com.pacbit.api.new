package com.bitkrx.api.main.vo;

public class CmeUserAssetResVO {

	private String curcyCd				=	"";		        /*통화단위 01:BTC , 02:ETH , 03:BCH , 04:XRP , 05:KRW*/
	private String PosCnAmt					=	"0";		/*거래가능_코인수량*/
	private String impCnAmt					=	"0";		/*거래중_코인수량*/
	private String kwdPrc					=	"0";		/*원화총금액*/
	private String totCoinAmt				=	"0";		/*총 코인 수량*/
	private String PosCnPrc					=	"0";		/*거래가능_코인원화가격*/
	private String impCnPrc					=	"0";		/*거래중_코인원화가격*/
    private String pntPrc                   =   "0";
	private String trustAmt					=   "0";		/*신탁_코인수량*/
	private String trustPrc					=	"0";		// 신탁 코인원화금액
	private String trLimtAmt				=   "0";		// 신탁 금액
	private String cnKndNm					=    "";		// 코인이름

	public String getCnKndNm() {return cnKndNm;}

	public void setCnKndNm(String cnKndNm) {this.cnKndNm = cnKndNm;}

	public String getTrLimtAmt() {return trLimtAmt;}

	public void setTrLimtAmt(String trLimtAmt) {this.trLimtAmt = trLimtAmt;}

	public String getTrustPrc() {return trustPrc;}

	public void setTrustPrc(String trustPrc) {this.trustPrc = trustPrc;}

	public String getTrustAmt() {return trustAmt;}

	public void setTrustAmt(String trustAmt) {this.trustAmt = trustAmt;}

	public String getPntPrc() {
        return pntPrc;
    }

    public void setPntPrc(String pntPrc) {
        this.pntPrc = pntPrc;
    }

	public String getPosCnPrc() {
		return PosCnPrc;
	}

	public void setPosCnPrc(String posCnPrc) {
		PosCnPrc = posCnPrc;
	}

	public String getImpCnPrc() {
		return impCnPrc;
	}

	public void setImpCnPrc(String impCnPrc) {
		this.impCnPrc = impCnPrc;
	}

	public String getCurcyCd() {
		return curcyCd;
	}

	public void setCurcyCd(String curcyCd) {
		this.curcyCd = curcyCd;
	}

	public String getPosCnAmt() {
		return PosCnAmt;
	}

	public void setPosCnAmt(String posCnAmt) {
		PosCnAmt = posCnAmt;
	}

	public String getImpCnAmt() {
		return impCnAmt;
	}

	public void setImpCnAmt(String impCnAmt) {
		this.impCnAmt = impCnAmt;
	}

	public String getKwdPrc() {
		return kwdPrc;
	}

	public void setKwdPrc(String kwdPrc) {
		this.kwdPrc = kwdPrc;
	}

	public String getTotCoinAmt() {
		return totCoinAmt;
	}

	public void setTotCoinAmt(String totCoinAmt) {
		this.totCoinAmt = totCoinAmt;
	}
}
