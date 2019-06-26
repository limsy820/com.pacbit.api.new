/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.push.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.bitkrx.api.push.vo.CmeFcmPushVO;
import com.bitkrx.api.push.vo.HtsPushResVO;
import com.bitkrx.api.push.vo.PushListVO;
import com.bitkrx.api.push.vo.PushYnVO;
import com.bitkrx.api.push.vo.SendMsgVO;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.push.service
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 12. 6.
 */
public interface CmeFcmPushService {

	String sendPushMsgOne(String userDeviceIdKey, String msg, JSONObject data) throws Exception ;
	
	String sendPushMsgOne(String userDeviceIdKey, String msg) throws Exception ;

    public String sendPushMsgAll(String msg, JSONObject data) throws Exception;

    void PR_INSUPT10171061(Map map) throws Exception;
	
	public String getPushKey(String str) throws Exception;
	
	public List<HtsPushResVO> pushNotCompList(String str) throws Exception;
	
	public List<PushListVO> getPushList(CmeFcmPushVO vo) throws Exception;
	
	public String pushNoticeList(String str) throws Exception ;
	
	public void PR_INSUPT_SND_MSG(SendMsgVO vo) throws Exception ;
	
	public void insUptPushYn(PushYnVO vo) throws Exception;
	
	public PushYnVO selectPushYn(PushYnVO vo) throws Exception;
}
