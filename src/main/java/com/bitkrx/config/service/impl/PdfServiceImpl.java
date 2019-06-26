/**
 * (주)크림스 의 시스템 웹어플리케이션 프레임웍 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)크림스
 * CopyRight Creams. inc. Since 2015 
 * 총괄 개발 책임자 : 주식회사 크림스 통합개발연구소 소장 김윤관
 */
package com.bitkrx.config.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitkrx.config.dao.PdfDAO;
import com.bitkrx.config.service.PdfService;
import com.bitkrx.config.vo.PdfKeyVO;



/**
 * @프로젝트명	: cme.web.cube
 * @패키지    	: cme.com.web.pdf.service.imple
 * @클래스명  	: cme.web.cube
 * @작성자		: (주)씨엠이소프트 박상웅
 * @작성일		: 2017. 11. 7.
 */
@Service
public class PdfServiceImpl implements PdfService{

	@Autowired
	PdfDAO pdfDao;
	/**
	 * @오바라이딩클래스 : getPDFKeyList
	 * @작성자		: (주)씨엠이소프트 박상웅
	 * @작성일		: 2017. 11. 7.
	 * @Method 설명 :
	 * @return
	 * @throws Exception
	 * @see cme.com.web.pdf.service.PdfService#getPDFKeyList()
	 */
	@Override
	public PdfKeyVO getPDFKeyList() throws Exception {
		return pdfDao.getPDFKeyList();
	}
	/**
	 * @오바라이딩클래스 : updatePdfStaus
	 * @작성자		: (주)씨엠이소프트 박상웅
	 * @작성일		: 2017. 11. 7.
	 * @Method 설명 :
	 * @param vo
	 * @return
	 * @throws Exception
	 * @see cme.com.web.pdf.service.PdfService#updatePdfStaus(cme.com.web.pdf.vo.PdfKeyVO)
	 */
	@Override
	public int updatePdfStaus(PdfKeyVO vo) throws Exception {
		// TODO Auto-generated method stub
		return pdfDao.updatePdfStaus(vo);
	}

	
}
