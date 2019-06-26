package com.bitkrx.api.rank.vo;

import java.io.Serializable;

public class RankVO implements Serializable {

    private String rank = "";
    private String rankId = "";
    private String todayAmt = "";
    private String totalAmt = "";
    private String totalPrc = "";
    private String falling = "";
    private String isNew = "";

    public String getFalling() {
        return falling;
    }

    public void setFalling(String falling) {
        this.falling = falling;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRankId() {
        return rankId;
    }

    public void setRankId(String rankId) {
        this.rankId = rankId;
    }

    public String getTodayAmt() {
        return todayAmt;
    }

    public void setTodayAmt(String todayAmt) {
        this.todayAmt = todayAmt;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getTotalPrc() {
        return totalPrc;
    }

    public void setTotalPrc(String totalPrc) {
        this.totalPrc = totalPrc;
    }
}
