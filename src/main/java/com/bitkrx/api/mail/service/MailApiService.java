/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.mail.service;

import com.bitkrx.api.mail.vo.MailVO;
import com.bitkrx.api.trade.vo.DepositVO;

import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.mail.service
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 21.
 */
public interface MailApiService {

	
	/**
	 * @Method Name  : sendMail
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 메일발송 공통 메소드
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int sendMail(HttpServletRequest request, Map < String, Object > map) throws Exception;
	
	/**
	 * @Method Name  : mailLogin
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 로그인시 메일발송
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int mailLogin(HttpServletRequest request, Map < String, Object > map) throws Exception;
	
	/**
	 * @Method Name  : mailLoginFail
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :로그인 5회 실패
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int mailLoginFail(HttpServletRequest request, Map < String, Object > map) throws Exception;
	
	/**
	 * @Method Name  : mailLoginUnlock
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :계정잠금해제 메일
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int mailLoginUnlock(HttpServletRequest request, Map< String, Object > map) throws Exception;


	/**
	 * @Method Name  : limOrderComplete
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :지정가 주문완료
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int limOrderComplete(HttpServletRequest request, Map< String, Object > map) throws Exception;
	
	/**
	 * @Method Name  : limOrderConclusion
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :지정가 체결
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int limOrderConclusion(HttpServletRequest request, Map< String, Object > map) throws Exception;
	
	/**
	 * @Method Name  : limOrderCancel
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :지정가구매 취소
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int limOrderCancel(HttpServletRequest request, Map< String, Object > map) throws Exception;
	
	/**
	 * @Method Name  : marOrderComplete
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :시장가 판매 체결
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int marOrderComplete(HttpServletRequest request, Map< String, Object > map) throws Exception;
	
	/**
	 * @Method Name  : marOrderConclusion
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :시장가 구매 체결
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int marOrderConclusion(HttpServletRequest request, Map< String, Object > map) throws Exception;
	

	/**
	 *
	 * @Method Name  : depositComplete
	 * @작성일   : 2018. 04. 15.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 원화출금신청완료 메일
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int mailWithdrowApplication(HttpServletRequest request, Map<String, Object> map) throws Exception;

	/**
	 *
	 * @Method Name  : depositComplete
	 * @작성일   : 2018. 04. 15.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 코인송금신청완료 메일
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int mailWithdrowApplicationCoin(HttpServletRequest request, Map<String, Object> map) throws Exception;

    /**
     *
     * @Method Name  : depositComplete
     * @작성일   : 2017. 12. 12.
     * @작성자   :  (주)씨엠이소프트 박상웅
     * @변경이력  :
     * @Method 설명 : 원화충전완료메일
     * @param map
     * @return
     * @throws Exception
     */
    public int depositComplete(HttpServletRequest request, Map<String, Object> map) throws Exception;

	public int certification(HttpServletRequest request, Map<String, Object> map) throws Exception;

    /**
     *
     * @Method Name  : depositComplete
     * @작성일   : 2018. 04. 24.
     * @작성자   :  (주)씨엠이소프트 문영민
     * @변경이력  :
     * @Method 설명 : 패스워드 변경메일
     * @param map
     * @return
     * @throws Exception
     */
    public int userPwdChangeMail(HttpServletRequest request, Map<String, Object> map) throws Exception;


	/**
	 *
	 * @Method Name  : depositComplete
	 * @작성일   : 2018. 05.23
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 : 관리자에게 메일 전송
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<MailVO> SendAdminAllMail(MailVO mvo) throws Exception;


	/**
	 *
	 * @Method Name  : depositComplete
	 * @작성일   : 2018. 05.23
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 : 관리자에게 메일 전송
	 * @param
	 * @return
	 * @throws Exception
	 */
	public int SendAllMail(HttpServletRequest request,Map<String, Object> map) throws Exception;


	/**
	 *
	 * @Method Name  : depositComplete
	 * @작성일   : 2018. 06.07
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 : 인증자료제출시 메일전송
	 * @param
	 * @return
	 * @throws Exception
	 */
	public int CertSendmail(HttpServletRequest request, Map<String,Object> map) throws Exception;

	/**
	 *
	 * @Method Name  : depositComplete
	 * @작성일   : 2018. 06.22
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 : 선불카드 신청시 메일전송
	 * @param
	 * @return
	 * @throws Exception
	 */
	public int ApplyCardSendmail(HttpServletRequest request , Map<String,Object> map) throws Exception;

	public int sendEventMail(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int sendTrustMail(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int sendTrustReleaseMail(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int LendingApplyMail(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int extensionMail(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int repaymentMail(HttpServletRequest request , Map<String , Object> map) throws Exception;


	/**
	 *
	 * @Method Name  : commonCertMail
	 * @작성일   : 2019. 01. 23.
	 * @작성자   :  (주)씨엠이소프트 박동휘
	 * @변경이력  :
	 * @Method 설명 : (공통화) 웹에서 요청시 메일전송
	 * @param
	 * @return
	 * @throws Exception
	 */
	public int commonCertMail(HttpServletRequest request , Map<String,Object> map) throws Exception;

	public int depositSendMail(HttpServletRequest request , DepositVO vo) throws Exception;
}
