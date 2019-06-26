/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.push.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bitkrx.api.auth.vo.CmeLoginVO;
import com.bitkrx.api.push.vo.CmeFcmPushVO;
import com.bitkrx.api.push.vo.HtsPushResVO;
import com.bitkrx.api.push.vo.PushListVO;
import com.bitkrx.api.push.vo.PushYnVO;
import com.bitkrx.api.push.vo.SendMsgVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;


/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.push.dao
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 12. 6.
 */
@Repository
public class CmeFcmPushDAO extends CmeComAbstractDAO{
	
	public int updateToken(CmeFcmPushVO vo)throws Exception {
		
		return (int)update("CmeFcmPushDAO.updateToken", vo);
	}
	
	public String getPushKey()throws Exception {
		
		return (String)selectByPk("CmeFcmPushDAO.getPushKey", "");
	}
	
	public int insertToken(CmeFcmPushVO vo)throws Exception {
		
		return (int)insert("CmeFcmPushDAO.insertToken", vo);
	}
	
	public int selectIsToken(CmeFcmPushVO vo)throws Exception {
		
		return (int)selectByPk("CmeFcmPushDAO.selectIsToken", vo);
	}
	
	public List<CmeFcmPushVO> selectToken(CmeFcmPushVO vo)throws Exception {
		
		return (List<CmeFcmPushVO>)list("CmeFcmPushDAO.selectToken", vo);
	}
	
	public void PR_INSUPT10171061(Map map) throws Exception {
		
		update("pushDAO.PR_INSUPT10171061", map);
	}
	
	public String getPushKey(String str) throws Exception {
		
		return (String) selectByPk("pushDAO.getPushKey", str);
	}
	
	public List<HtsPushResVO> pushNotCompList(String str) throws Exception {
		
		return list("pushDAO.pushNotCompList", str);
	}
	
	public int uptHtsPush(String str) throws Exception {
		
		return update("pushDAO.uptHtsPush", str);
		
	}
	
	public List<PushListVO> getPushList(CmeFcmPushVO vo) throws Exception  {
		return list("pushDAO.getPushList", vo);
	}
	
	public String pushNoticeList(String str) throws Exception {
		
		return (String) selectByPk("pushDAO.pushNotice", str);
	}
	
	public void PR_INSUPT_SND_MSG(SendMsgVO vo) throws Exception {
		
		update("pushDAO.PR_INSUPT_SND_MSG", vo);
	}
	
	public void insUptPushYn(PushYnVO vo) throws Exception {
		
		update("pushDAO.insUptPushYn", vo);
	}
	
	public PushYnVO selectPushYn(PushYnVO vo) throws Exception {
		
		return (PushYnVO) selectByPk ("pushDAO.selectPushYn", vo);
	}
} 
