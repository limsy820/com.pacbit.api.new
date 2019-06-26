package com.bitkrx.api.auth.vo;

import com.bitkrx.config.CmeResultVO;

public class CmeOptVO extends CmeResultVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6049490012119544322L;

	private String opt = "";							//opt
	private String opt_yn = "";							//opt성공여부
	
	public CmeOptVO(){}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getOpt_yn() {
		return opt_yn;
	}

	public void setOpt_yn(String opt_yn) {
		this.opt_yn = opt_yn;
	}
	
	
}
