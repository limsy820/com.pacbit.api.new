package com.bitkrx.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CmeResultVO implements Serializable{

    private static final long serialVersionUID = 7505549301689585084L;

    private String resultMsg ="";
    private String resultStrCode = "";
    private String resultDescMsg = "";
    private String resultStrCode1 = "";
    private String excelMappingVO = "";
    private List<Map> ProceduresResult = new ArrayList<Map>();
    int resultCode = -1;

    Object data = "";



    public String getResultStrCode() {
        return resultStrCode;
    }
    public void setResultStrCode(String resultStrCode) {
        this.resultStrCode = resultStrCode;
    }
    public String getResultMsg() {
        return resultMsg;
    }
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
    public int getResultCode() {
        return resultCode;
    }
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
    public String getResultStrCode1() {
        return resultStrCode1;
    }
    public void setResultStrCode1(String resultStrCode1) {
        this.resultStrCode1 = resultStrCode1;
    }
    /**
     * @return the excelMappingVO
     */
    public String getExcelMappingVO() {
        return excelMappingVO;
    }
    /**
     * @param excelMappingVO the excelMappingVO to set
     */
    public void setExcelMappingVO(String excelMappingVO) {
        this.excelMappingVO = excelMappingVO;
    }
    /**
     * @return the resultDescMsg
     */
    public String getResultDescMsg() {
        return resultDescMsg;
    }
    /**
     * @param resultDescMsg the resultDescMsg to set
     */
    public void setResultDescMsg(String resultDescMsg) {
        this.resultDescMsg = resultDescMsg;
    }
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		if(data == null) {
			data = new HashMap<String, Object>();
		}
		this.data = data;
	}
	/**
	 * @return the proceduresResult
	 */
	public List<Map> getProceduresResult() {
		return ProceduresResult;
	}
	/**
	 * @param proceduresResult the proceduresResult to set
	 */
	public void setProceduresResult(List<Map> proceduresResult) {
		ProceduresResult = proceduresResult;
	}


}