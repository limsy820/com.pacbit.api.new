package com.bitkrx.api.auth.service;

import javax.servlet.http.HttpServletRequest;

import com.bitkrx.api.auth.vo.*;
import com.bitkrx.config.CmeResultVO;

import java.util.List;

public interface LoginService {

	/**
	 * @Method Name  : login
	 * @작성일   : 2017. 11. 21. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 로그인 로직
	 * @param vo
	 * @return
	 */
	public CmeResultVO login(HttpServletRequest request,  CmeLoginVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : loginFaill
	 * @작성일   : 2017. 11. 21. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :로그인 5회 실패
	 * @param vo
	 * @return
	 */
	public CmeResultVO loginFail(HttpServletRequest request, CmeLoginVO vo) throws Exception; 
	
	/**
	 * @Method Name  : loginFaill
	 * @작성일   : 2017. 11. 21. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :로그인 잠금해제
	 * @param vo
	 * @return
	 */
	public CmeResultVO loginUnlock(HttpServletRequest request, CmeLoginVO vo) throws Exception; 
	
	
	/**
	 * @Method Name  : INS10171020
	 * @작성일   : 2017. 12. 6. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @throws Exception
	 */
	public CmeResultVO INS10171020(CmeLoginVO vo) throws Exception;
	
	/**
	 * @Method Name  : INS10171020
	 * @작성일   : 2017. 12. 7. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :로그인 이력 쌓기
	 * @param vo
	 * @throws Exception
	 */
	public CmeResultVO INS10171025(CmeLoginVO vo) throws Exception;
	
	/**
	 * @Method Name  : loginMailSms
	 * @작성일   : 2017. 12. 7. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :로그인 메일, 문자 발송
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public CmeResultVO loginMailSms(HttpServletRequest request, CmeLoginVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : sendSmsAuthCode
	 * @작성일   : 2017. 12. 31. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 6자리 인증번호 발송
	 * @param vo
	 * @throws Exception
	 */
	public CmeResultVO sendSmsAuthCode(HttpServletRequest request, CmeLoginVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : sendSmsAuthCode
	 * @작성일   : 2017. 12. 31. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 6자리 인증번호 검증
	 * @param vo
	 * @throws Exception
	 */
	public CmeResultVO SmsAuthCodeVertify(CmeLoginVO vo) throws Exception;
	
	/**
	 * @Method Name  : getUserAuth
	 * @작성일   : 2018. 1. 2. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :사용자인증정보
	 * @param vo
	 * @return
	 */
	public UserAuthVO getUserAuth(CmeLoginVO vo);
	
	public String getUserPuchCd(String str);

	public String getUserMobile(String str);

	public List<CmeRcmdCode> getNatnCode() throws Exception;

	public List<CmeRcmdCode> getBrhCode(String natnCode) throws Exception;

	public List<CmeRcmdCode> getRcmdCode(String brhCode) throws Exception;

	public String SmsAuthCodeCert(String userEmail) throws Exception;

	public String SmsAuthCodeCert2(String userEmail) throws Exception;

	/**
	 * @Method Name  : getUserAuth
	 * @작성일   : 2018. 09. 05.
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 :핀번호 체크
	 * @param vo
	 * @return
	 */
	public int pinNoCheck(CmeLoginVO vo) throws Exception;


	/**
	 * @Method Name  : getUserAuth
	 * @작성일   : 2018. 09. 05.
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 :핀번호 Insert
	 * @param vo
	 * @return
	 */
	public int InsertPinNo(CmeLoginVO vo) throws Exception;

	/**
	 * @Method Name  : getUserAuth
	 * @작성일   : 2018. 09. 05.
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 :핀번호 RESET
	 * @param vo
	 * @return
	 */
	public int pinNoReset(CmeLoginVO vo) throws Exception;


	/**
	 * @Method Name  : pinlogin
	 * @작성일   : 2018. 09. 05.
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 : 핀번호 로그인 로직
	 * @param vo
	 * @return
	 */
	public CmeResultVO pinlogin(HttpServletRequest request,  CmeLoginVO vo) throws Exception;


	/**
	 * @Method Name  : pinUserCheck
	 * @작성일   : 2018. 09. 11.
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 : 핀번호 초기화 로직
	 * @param vo
	 * @return
	 */
	public int pinUserCheck(CmeLoginVO vo) throws Exception;

	public LoginResVO getUserAuthInfo(String userEmail) throws Exception;

	public int getFingetCheck(CmeLoginVO vo) throws Exception;

	public int InsFingerInfo(CmeLoginVO vo) throws Exception;

	public CmeResultVO fingerLogin(HttpServletRequest request , CmeLoginVO vo) throws Exception;

	/**
	 * @Method Name  : getUserAuth
	 * @작성일   : 2018. 12. 07.
	 * @작성자   :  (주)씨엠이소프트 박동휘
	 * @변경이력  :
	 * @Method 설명 :지문번호 RESET
	 * @param vo
	 * @return
	 */
	public int fingerReset(CmeLoginVO vo) throws Exception;

	public int uptFingerInfo(CmeLoginVO vo) throws Exception;

	public int getPwdCheck(CmeLoginVO vo) throws Exception;
}
