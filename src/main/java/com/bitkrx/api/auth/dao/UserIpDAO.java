/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.auth.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bitkrx.api.auth.vo.UserIpVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.auth.dao
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 30.
 */
@Repository
public class UserIpDAO extends CmeComAbstractDAO{

	public int insUptIpList(UserIpVO vo) throws Exception {
		
		return update("userDAO.insUptIpList", vo);
	}
	
	public int selectUserIp(UserIpVO vo) throws Exception {
		
		return (int) selectByPk ("userDAO.selectUserIp", vo);
	}
	
	public List<UserIpVO> selectUserIpList(UserIpVO vo) throws Exception {
		
		return list ("userDAO.selectUserIpList", vo);
	}
}
