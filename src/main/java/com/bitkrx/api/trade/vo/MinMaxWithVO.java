package com.bitkrx.api.trade.vo;

public class MinMaxWithVO {

    private Double minAmt = 0D;
    private Double maxAmt = 0D;
    private Double maxAmtDay = 0D;
    private Double minAmtL3 = 0D;
    private Double maxAmtL3 = 0D;
    private Double maxAmtDayL3 = 0D;
    private Double remAmtDay = 0D;
    private Double remAmtDayL3 = 0D;
    private String curcyCd = "";
    private String userEmail = "";
    private String grade = "";
    private Double maxAmt1day = 0D;

    public Double getRemAmtDayL3() {return remAmtDayL3;}

    public void setRemAmtDayL3(Double remAmtDayL3) {this.remAmtDayL3 = remAmtDayL3;}

    public Double getMaxAmt1day() {return maxAmt1day;}

    public void setMaxAmt1day(Double maxAmt1day) {this.maxAmt1day = maxAmt1day;}

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Double getMinAmtL3() {
        return minAmtL3;
    }

    public void setMinAmtL3(Double minAmtL3) {
        this.minAmtL3 = minAmtL3;
    }

    public Double getMaxAmtL3() {
        return maxAmtL3;
    }

    public void setMaxAmtL3(Double maxAmtL3) {
        this.maxAmtL3 = maxAmtL3;
    }

    public Double getMaxAmtDayL3() {
        return maxAmtDayL3;
    }

    public void setMaxAmtDayL3(Double maxAmtDayL3) {
        this.maxAmtDayL3 = maxAmtDayL3;
    }

    public Double getMaxAmtDay() {
        return maxAmtDay;
    }

    public void setMaxAmtDay(Double maxAmtDay) {
        this.maxAmtDay = maxAmtDay;
    }

    public Double getRemAmtDay() {
        return remAmtDay;
    }

    public void setRemAmtDay(Double remAmtDay) {
        this.remAmtDay = remAmtDay;
    }

    /**
	 * @return the minAmt
	 */
	public Double getMinAmt() {
		return minAmt;
	}
	/**
	 * @param minAmt the minAmt to set
	 */
	public void setMinAmt(Double minAmt) {
		this.minAmt = minAmt;
	}
	/**
	 * @return the maxAmt
	 */
	public Double getMaxAmt() {
		return maxAmt;
	}
	/**
	 * @param maxAmt the maxAmt to set
	 */
	public void setMaxAmt(Double maxAmt) {
		this.maxAmt = maxAmt;
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
	
}
