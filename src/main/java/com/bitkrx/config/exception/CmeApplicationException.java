package com.bitkrx.config.exception;

import java.io.Serializable;

import com.bitkrx.config.util.DateTime;
import com.bitkrx.config.vo.CmeExceptionVO;


public class CmeApplicationException extends RuntimeException implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1101985829361931796L;
    
    String customMessage="";
    String curDateTime;
    CmeExceptionVO exceptVo = null;

    public CmeApplicationException(String msg){
        this.customMessage = msg;
        this.curDateTime = DateTime.getSysdate("yyyy.MM.dd HH:mm:ss", java.util.Locale.KOREA);
    }

    public CmeApplicationException(CmeExceptionVO vo){
        this.exceptVo = vo;
        this.curDateTime = DateTime.getSysdate("yyyy.MM.dd HH:mm:ss", java.util.Locale.KOREA);
    }   
    
    public CmeApplicationException(){
        this.curDateTime = DateTime.getSysdate("yyyy.MM.dd HH:mm:ss", java.util.Locale.KOREA);
    }   
    
    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public String getCurDateTime() {
        return curDateTime;
    }

    public void setCurDateTime(String curDateTime) {
        this.curDateTime = curDateTime;
    }

    public CmeExceptionVO getExceptVo() {
        return exceptVo;
    }

    public void setExceptVo(CmeExceptionVO exceptVo) {
        this.exceptVo = exceptVo;
    }
    
    
    
}
