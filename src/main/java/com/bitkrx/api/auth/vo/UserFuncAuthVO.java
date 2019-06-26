package com.bitkrx.api.auth.vo;

public class UserFuncAuthVO {

    private String userEmail        = "";
    private String noLimtYn         = "";
    private String stdDt            = "";
    private String endDt            = "";
    private String useYn            = "";
    private String cancelMsg        = "";

    public String getUserEmail() {return userEmail;}

    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

    public String getNoLimtYn() {return noLimtYn;}

    public void setNoLimtYn(String noLimtYn) {this.noLimtYn = noLimtYn;}

    public String getStdDt() {return stdDt;}

    public void setStdDt(String stdDt) {this.stdDt = stdDt;}

    public String getEndDt() {return endDt;}

    public void setEndDt(String endDt) {this.endDt = endDt;}

    public String getUseYn() {return useYn;}

    public void setUseYn(String useYn) {this.useYn = useYn;}

    public String getCancelMsg() {return cancelMsg;}

    public void setCancelMsg(String cancelMsg) {this.cancelMsg = cancelMsg;}
}
