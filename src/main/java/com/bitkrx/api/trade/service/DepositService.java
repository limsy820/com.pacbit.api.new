/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.trade.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bitkrex.coin.provider.wallet.SimpleWalletRpcProvider;
import com.bitkrx.api.block.vo.BlockChainVO;
import com.bitkrx.api.moaCard.vo.MoaCardCoinVO;
import com.bitkrx.api.moaCard.vo.MoaCardReqVO;
import com.bitkrx.api.trade.vo.*;
import com.bitkrx.config.CmeResultVO;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.trade.service
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 23.
 */
public interface DepositService {

	/**
	 * @Method Name  : INSUPT30171020
	 * @작성일   : 2017. 11. 23. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :포인트 충전 및 수정
	 * @param vo
	 */
	public void INSUPT30171020(DepositVO vo) throws Exception;
	
	/**
	 * @Method Name  : INSUPT30171030
	 * @작성일   : 2018. 1. 2. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :코인입금대기 및 완료
	 * @param vo
	 * @throws Exception
	 */
	public void INSUPT30171030(DepositVO vo) throws Exception;
	
	/**
	 * @Method Name  : INS10171028
	 * @작성일   : 2018. 1. 2. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :실제코인입금
	 * @param vo
	 * @throws Exception
	 */
	public void INS10171028(DepositVO vo) throws Exception;
	
	/**
	 * @Method Name  : insAccInfo
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :계좌임시입력
	 * @param vo
	 * @throws Exception
	 */
	public void insAccInfo(DepositVO vo) throws Exception;
	
	/**
	 * @Method Name  : getCnDepoList
	 * @작성일   : 2017. 12. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 코인입금내역리스트
	 * @param vo
	 * @throws Exception
	 */
	public List<CoinDepositVO> getCnDepoList(DepositVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : getKrwDepoList
	 * @작성일   : 2017. 12. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 원화입금내역리스트
	 * @param vo
	 * @throws Exception
	 */
	public List<KrwDepositVO> getKrwDepoList(DepositVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : getUserAccNm
	 * @작성일   : 2017. 12. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :유저 입금자명
	 * @param userEmail
	 * @return
	 * @throws Exception
	 */
	public DepositUserInfoVO getUserAccNm(String userEmail) throws Exception;

	/**
	 * @Method Name  : getCnAccInfo
	 * @작성일   : 2018. 1. 1. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :사용자 전자지갑주소 가져오기
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<DepositVO> getCnAccInfo(DepositVO vo) throws Exception;
	
	/**
	 * @Method Name  : getCnAccList
	 * @작성일   : 2018. 1. 8. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :전자지갑주소 리스트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CnAccListVO> getCnAccList(DepositVO vo) throws Exception;
	
	public DepositVO getCnDptCode(DepositVO vo) throws Exception;
	
	public CmeResultVO pendingCoin(HttpServletRequest request, DepositVO vo) throws Exception;
	
	public DepoBankVO getUserBankInfo(String userEmail) throws Exception;
	
	/**
	 * @Method Name  : getKrwWithList
	 * @작성일   : 2018. 01. 18. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 원화출금화면
	 * @param vo
	 * @throws Exception
	 */
	public KrwWithVO getKrwWithdrow(DepositVO vo) throws Exception;
	
	public String getDestinationTag() throws Exception;

    public List<DepoBankVO> getUserBankInfoList(String userEmail) throws Exception;

    public void ethPending(DepositVO vo, BlockChainVO blockVO, HttpServletRequest request) throws Exception;

    public void insCoinMoveHis(DepositVO vo) throws Exception;

    public int insertEthMoveHistory(DepositVO vo, SimpleWalletRpcProvider rpc, String password) throws Exception;

    public WletUserInfoVO selectWletUserInfo(WletUserInfoVO vo) throws Exception;

    public DepositVO getExRateDepositPrc(DepositVO vo) throws Exception;

    public String getTrnGenKey() throws Exception;

    public void insTrnInfo(TrnInfoVO vo) throws Exception;

    public CmeTradeReqVO depositCash(CmeTradeReqVO vo) throws Exception ;

    public int cardCoinOut(DepositVO vo) throws Exception;

    public int cardCoinIn(DepositVO vo) throws Exception;

    public int cardCashOut(DepositVO vo) throws Exception;

    public String getCardCoinOutKey() throws Exception;

    public String getCardCoinInKey() throws Exception;

    public int insUptCardUseInfo(MoaCardReqVO vo) throws Exception;

    public MoaCardReqVO selectCardCoinUseInfo(String tradeId) throws Exception;

    public int getBmcCoinCnt(String wletAdr) throws Exception;

    public double getTotUsdtAmt() throws Exception;

	public String getKrwPoint(String userEmail) throws Exception;

    public CmeResultVO pendingCoinNew(HttpServletRequest request, DepositVO vo) throws Exception;

    public void insMoaCardHistory(MoaCardCoinVO vo) throws Exception;

    public int getCoinMoveHourCnt(DepositVO vo) throws Exception;

    public List<String> getDepositUserEmail() throws Exception;

    public KrwWithVO getUserAccInfo(String userEmail) throws Exception;

    public String getCoinCode(String curcyNm) throws Exception;

    public String getWletUserEmail(DepositVO vo) throws Exception;

    public int getDepositCheckCnt(DepositVO vo) throws Exception;

    public void PR_INTUPT_ADM_EVENT_CHECK(AdmEventVO vo) throws Exception;

    public int getEventCnt() throws Exception;

    public AdmEventVO getEventInfo(int no) throws Exception;

    public void PR_INTUPT_ADM_EVENT(AdmEventVO vo) throws Exception;

    public List<AdmEventVO> getAdmEventList(AdmEventVO vo) throws Exception;

    public int getAdmEventListCnt(String userEmail) throws Exception;
}
