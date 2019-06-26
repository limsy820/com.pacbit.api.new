/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.sms.service;

import javax.servlet.http.HttpServletRequest;

import com.bitkrx.api.trade.vo.DepositVO;
import com.bitkrx.config.vo.SendInfoVO;

import java.util.Map;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.sms.service
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 21.
 */
public interface SmsApiService {

	/**
	 * @Method Name  : sendSms
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : SMS발송 공통 메소드 (푸시키가있을경우 푸시발송)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public int sendSms(SendInfoVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : sendSms
	 * @작성일   : 2017. 12. 31. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : SMS로만 발송되는 메소드
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int sendSms(SendInfoVO vo, int i) throws Exception;

	/**
	 * @Method Name  : smsLogin
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :로그인시 SMS 발송
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int smsLogin(HttpServletRequest request, SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : smsLoginFail
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :5회로그인실패
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int smsLoginFail(HttpServletRequest request, SendInfoVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : smsLoginUnlock
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :계정잠금해제
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int smsLoginUnlock(HttpServletRequest request, SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : smsAutoTradeReq
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :자동거래요청
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int smsAutoTradeReq(SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : smsAutoTradeComp
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :자동거래체결
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int smsAutoTradeComp(SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : smsAutoTradeCancel
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :자동거래 취소
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int smsAutoTradeCancel(SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : smsAutoTradeComp
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :코인교환예약
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int smsExchangeSet(SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : smsAutoTradeCancel
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :코인교환거래 실행
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int smsExchangeExcute(SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : limOrderComplete
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :지정가 주문완료
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int limOrderComplete(HttpServletRequest request, SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : limOrderConclusion
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :지정가 구매체결
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int limBuyOrderConclusion(HttpServletRequest request, SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : limSellOrderConclusion
	 * @작성일   : 2017. 12. 31. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :지정가판매체결
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int limSellOrderConclusion(HttpServletRequest request, SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : limOrderCancel
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :지정가구매 취소
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int limOrderCancel(HttpServletRequest request, SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : marOrderComplete
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :시장가 판매 체결
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int marSellOrderComplete(HttpServletRequest request, SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : marOrderConclusion
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :시장가 구매 체결
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int marBuyOrderConclusion(HttpServletRequest request, SendInfoVO vo) throws Exception;
	
	/**
	 * @Method Name  : qnaComplate
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :1:1문의 답변완료
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int qnaComplate(SendInfoVO vo) throws Exception;

	/**
	 * 
	 * @Method Name  : depositComplete
	 * @작성일   : 2017. 12. 13. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @throws Exception
	 */
	public int depositComplete(HttpServletRequest request, SendInfoVO vo) throws Exception;

	/**
	 * 
	 * @Method Name  : limSalesOrderComplete
	 * @작성일   : 2017. 12. 13. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int limSalesOrderComplete(HttpServletRequest request, SendInfoVO vo) throws Exception;

	/**
	 * 
	 * @Method Name  : limSalesOrderCancel
	 * @작성일   : 2017. 12. 13. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @throws Exception
	 */
	public int limSalesOrderCancel(HttpServletRequest request, SendInfoVO vo) throws Exception;


	public int smsDepoComp(HttpServletRequest request, SendInfoVO vo) throws Exception;

	public int smsCoinWithComp(HttpServletRequest request, SendInfoVO vo) throws Exception;


	/**
	 *
	 * @Method Name  : smsCashWithComp
	 * @작성일   : 2018. 04. 13.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 현금출금완료
	 * @param vo
	 * @throws Exception
	 */
	public int smsCashWithComp(HttpServletRequest request, SendInfoVO vo) throws Exception;

	public int CertSms(HttpServletRequest request, Map<String,Object> map) throws Exception;

	public int sendCertSms(HttpServletRequest request, Map<String,Object> map) throws Exception;

	public int sendTrustPush(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int sendTrustSMS(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int sendTrustReleasePush(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int sendTrustReleaseSMS(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int LendingApplySms(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int extensionPush(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int repaymentPush(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int sendDepositPush(HttpServletRequest request , Map<String , Object> map) throws Exception;

	public int depositSendPush(HttpServletRequest request , DepositVO vo) throws Exception;
}
