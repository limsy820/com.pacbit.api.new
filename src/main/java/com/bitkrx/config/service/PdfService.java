/**
 * (주)크림스 의 시스템 웹어플리케이션 프레임웍 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)크림스
 * CopyRight Creams. inc. Since 2015 
 * 총괄 개발 책임자 : 주식회사 크림스 통합개발연구소 소장 김윤관
 */
package com.bitkrx.config.service;

import com.bitkrx.config.vo.PdfKeyVO;

/**
 * @프로젝트명	: cme.web.cube
 * @패키지    	: cme.com.web.pdf.service
 * @클래스명  	: cme.web.cube
 * @작성자		: (주)씨엠이소프트 박상웅
 * @작성일		: 2017. 11. 7.
 */
public interface PdfService {

	/**
	 * @Method Name  : getPDFKeyList
	 * @작성일   : 2017. 11. 7. 
	 * @작성자   : (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @return
	 */
	PdfKeyVO getPDFKeyList() throws Exception;

	/**
	 * @Method Name  : updatePdfStaus
	 * @작성일   : 2017. 11. 7. 
	 * @작성자   : (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 */
	int updatePdfStaus(PdfKeyVO vo) throws Exception;

}
