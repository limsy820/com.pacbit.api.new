package com.bitkrx.config.vo;

public class CmeExceptionVO {
    /**
     * 
     */
    private static final long serialVersionUID = 7198526699432336632L;

    String errorMessageCode="";
    String errorStatusCode="";
    String errorTitleCode="";
    String title="";
    String msg="";
    String status="";
    String exmessage ="";
    String errorMessage = "";
    String timestamp = "";
    String returnUrl = "";
    String eTitle = "";
    String eRtnUrl = "";
    
    String errorPrgm = "";
    String errorLine = "";
    String errorKind = "";
   
    

    public String getErrorPrgm() {
		return errorPrgm;
	}
	public void setErrorPrgm(String errorPrgm) {
		this.errorPrgm = errorPrgm;
	}
	public String getErrorLine() {
		return errorLine;
	}
	public void setErrorLine(String errorLine) {
		this.errorLine = errorLine;
	}
	public String getErrorKind() {
		return errorKind;
	}
	public void setErrorKind(String errorKind) {
		this.errorKind = errorKind;
	}
	public String getErrorMessageCode() {
        return errorMessageCode;
    }
    public void setErrorMessageCode(String errorMessageCode) {
        this.errorMessageCode = errorMessageCode;
    }
    public String getErrorStatusCode() {
        return errorStatusCode;
    }
    public void setErrorStatusCode(String errorStatusCode) {
        this.errorStatusCode = errorStatusCode;
    }
    public String getErrorTitleCode() {
        return errorTitleCode;
    }
    public void setErrorTitleCode(String errorTitleCode) {
        this.errorTitleCode = errorTitleCode;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getExmessage() {
        return exmessage;
    }
    public void setExmessage(String exmessage) {
        this.exmessage = exmessage;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    /**
     * @return the returnUrl
     */
    public String getReturnUrl() {
        return returnUrl;
    }
    /**
     * @param returnUrl the returnUrl to set
     */
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    /**
     * @return the eTitle
     */
    public String geteTitle() {
        return eTitle;
    }
    /**
     * @param eTitle the eTitle to set
     */
    public void seteTitle(String eTitle) {
        this.eTitle = eTitle;
    }
    /**
     * @return the eRtnUrl
     */
    public String geteRtnUrl() {
        return eRtnUrl;
    }
    /**
     * @param eRtnUrl the eRtnUrl to set
     */
    public void seteRtnUrl(String eRtnUrl) {
        this.eRtnUrl = eRtnUrl;
    }
}
