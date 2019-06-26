package com.bitkrx.api.trade.vo;

import com.bitkrx.config.vo.CmeExtendListVO;

import java.util.List;
import java.util.Map;

public class AdmEventVO extends CmeExtendListVO {

    private String userEmail = "";
    private String no = "";
    private String cnCpnCode = "";
    private String cnEvtCode = "";
    private String useYn = "";
    private String regDt = "";
    private String giveAmt = "";
    private String giveDt = "";
    private String clientCd = "";

    private int listSize = 10;
    private int page = 1;
    private List<Map> RESULT 		= null;

    public int getListSize() {return listSize;}

    public void setListSize(int listSize) {this.listSize = listSize;}

    public int getPage() {return page;}

    public void setPage(int page) {this.page = page;}

    public String getClientCd() {return clientCd;}

    public void setClientCd(String clientCd) {this.clientCd = clientCd;}

    public String getGiveDt() {return giveDt;}

    public void setGiveDt(String giveDt) {this.giveDt = giveDt;}

    public String getGiveAmt() {return giveAmt;}

    public void setGiveAmt(String giveAmt) {this.giveAmt = giveAmt;}

    public String getNo() {return no;}

    public void setNo(String no) {this.no = no;}

    public String getCnCpnCode() {return cnCpnCode;}

    public void setCnCpnCode(String cnCpnCode) {this.cnCpnCode = cnCpnCode;}

    public String getCnEvtCode() {return cnEvtCode;}

    public void setCnEvtCode(String cnEvtCode) {this.cnEvtCode = cnEvtCode;}

    public String getUseYn() {return useYn;}

    public void setUseYn(String useYn) {this.useYn = useYn;}

    public String getRegDt() {return regDt;}

    public void setRegDt(String regDt) {this.regDt = regDt;}

    public String getUserEmail() {return userEmail;}

    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

    public List<Map> getRESULT() {return RESULT;}

    public void setRESULT(List<Map> RESULT) {this.RESULT = RESULT;}
}
