package com.bitkrx.config.vo;

import java.io.Serializable;

/**
 * @프로젝트명	: Seoil.Act.WebApp
 * @패키지    	: cme.com.web.pdf.vo
 * @클래스명  	: Seoil.Act.WebApp
 * @작성자		: (주)크림소프트 김윤관
 * @작성일		: 2015. 5. 21.
 */
public class PdfKeyVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2956997680028595801L;

	String pdf_username = "";
	String pdf_key = "";
	String pdf_status = "";
	/**
	 * @return the pdf_username
	 */
	public String getPdf_username() {
		return pdf_username;
	}
	/**
	 * @param pdf_username the pdf_username to set
	 */
	public void setPdf_username(String pdf_username) {
		this.pdf_username = pdf_username;
	}
	/**
	 * @return the pdf_key
	 */
	public String getPdf_key() {
		return pdf_key;
	}
	/**
	 * @param pdf_key the pdf_key to set
	 */
	public void setPdf_key(String pdf_key) {
		this.pdf_key = pdf_key;
	}
	/**
	 * @return the pdf_status
	 */
	public String getPdf_status() {
		return pdf_status;
	}
	/**
	 * @param pdf_status the pdf_status to set
	 */
	public void setPdf_status(String pdf_status) {
		this.pdf_status = pdf_status;
	}
	
	
	
}
