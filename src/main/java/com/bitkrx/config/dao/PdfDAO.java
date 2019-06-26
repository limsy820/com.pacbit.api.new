/**
 * (주)크림스 의 시스템 웹어플리케이션 프레임웍 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)크림스
 * CopyRight Creams. inc. Since 2015 
 * 총괄 개발 책임자 : 주식회사 크림스 통합개발연구소 소장 김윤관
 */
package com.bitkrx.config.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bitkrx.config.vo.PdfKeyVO;


/**
 * @프로젝트명	: cme.web.cube
 * @패키지    	: cme.com.web.pdf.dao
 * @클래스명  	: cme.web.cube
 * @작성자		: (주)씨엠이소프트 박상웅
 * @작성일		: 2017. 11. 7.
 */
@Repository
public class PdfDAO extends CmeComAbstractDAO{

	public static final String PdfStandStaus = "5";
	
	/**
	 * @Method Name  : getPDFKeyList
	 * @작성일   : 2017. 11. 7. 
	 * @작성자   : (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @return
	 */
	public PdfKeyVO getPDFKeyList() throws Exception{
		String sta = PdfStandStaus;
		log.DebugLog("STASUT 기준 : " + sta);
		
		Map<String,String> param = new HashMap<String,String>();
		param.put("sta", sta);
		return (PdfKeyVO)selectByPk("PdfDAO.selectPDFkeyList", param);
	}

	/**
	 * @Method Name  : updatePdfStaus
	 * @작성일   : 2017. 11. 7. 
	 * @작성자   : (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 */
	public int updatePdfStaus(PdfKeyVO vo) throws Exception{
		int res = (int)update("PdfDAO.updatePDFkey", vo);
		return res;
	}

	
}
