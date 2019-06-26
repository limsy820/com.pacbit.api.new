package com.bitkrx.api.main.vo;

public class CmeUserAssetReqVO{

	private String userEmail			=	"";		//사용자 EMAIL
	private String curcyCd 				= 	"";
	private String amount = "";
	private String cardNum = "";

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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


	
	
	
	
}
