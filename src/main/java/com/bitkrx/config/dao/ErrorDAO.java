/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.config.dao;

import org.springframework.stereotype.Repository;

import com.bitkrx.config.vo.CmeExceptionVO;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.config.dao
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 22.
 */
@Repository
public class ErrorDAO extends CmeComAbstractDAO{

	public void prErrorLog(CmeExceptionVO vo) {
		insert("errorDAO.prErrorLog", vo);
	}
}
