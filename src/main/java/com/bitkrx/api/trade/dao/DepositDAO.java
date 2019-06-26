/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.trade.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.bitkrx.api.moaCard.vo.MoaCardCoinVO;
import com.bitkrx.api.moaCard.vo.MoaCardReqVO;
import com.bitkrx.api.trade.vo.*;
import org.springframework.stereotype.Repository;

import com.bitkrx.config.dao.CmeComAbstractDAO;
import com.bitkrx.config.util.MapObjConvertUtil;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.trade.dao
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 23.
 */
@Repository
public class DepositDAO extends CmeComAbstractDAO{

	MapObjConvertUtil cUtil = MapObjConvertUtil.getinstance();
	/**
	 * @Method Name  : INSUPT30171020
	 * @작성일   : 2017. 11. 23. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :포인트 충전 및 수정
	 * @param vo
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public void INSUPT30171020(DepositVO vo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		convertProc("depositDAO.INS30171020", vo);
		//convertProc("depositDAO.INS10171029", vo);
	}
	
	/**
	 * @Method Name  : insAccInfo
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :계좌임시입력
	 * @param vo
	 * @throws Exception
	 */
	public void insAccInfo(DepositVO vo) throws Exception {
		insert("depositDAO.insAccInfo", vo);
	}
	
	
	/**
	 * @Method Name  : getCnDepoList
	 * @작성일   : 2017. 12. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 코인입금내역리스트
	 * @param vo
	 * @throws Exception
	 */
	public List<CoinDepositVO> getCnDepoList(DepositVO vo) throws Exception {
		return list("depositDAO.getCnDepoList", vo);
	}
	
	/**
	 * @Method Name  : getKrwDepoList
	 * @작성일   : 2017. 12. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 원화입금내역리스트
	 * @param vo
	 * @throws Exception
	 */
	public List<KrwDepositVO> getKrwDepoList(DepositVO vo) throws Exception {
		return list("depositDAO.getKrwDepoList", vo);
	}
	
	/**
	 * @Method Name  : getKrwWithList
	 * @작성일   : 2018. 01. 18. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 원화출금화면
	 * @param vo
	 * @throws Exception
	 */
	public KrwWithVO getKrwWithdrow(DepositVO vo) throws Exception {
		return (KrwWithVO) selectByPk("depositDAO.getKrwWithdrow", vo);
	}
	
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
	public DepositUserInfoVO getUserAccNm(String userEmail) throws Exception {
		return (DepositUserInfoVO) selectByPk("depositDAO.getUserAccNm", userEmail);
	}
	
	/**
	 * @Method Name  : getUserBankInfo
	 * @작성일   : 2018. 01. 17.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :유저 은행코드
	 * @param userEmail
	 * @return
	 * @throws Exception
	 */
	public DepoBankVO getUserBankInfo(String userEmail) throws Exception {
		return (DepoBankVO) selectByPk("depositDAO.getUserBankInfo", userEmail);
	}

    /**
     * @Method Name  : getUserBankInfo
     * @작성일   : 2018. 01. 17.
     * @작성자   :  (주)씨엠이소프트 문영민
     * @변경이력  :
     * @Method 설명 :유저 은행코드
     * @param userEmail
     * @return
     * @throws Exception
     */
    public List<DepoBankVO> getUserBankInfoList(String userEmail) throws Exception {
        return list ("depositDAO.getUserBankInfo", userEmail);
    }
	
	/**
	 * @Method Name  : getCnAccInfo
	 * @작성일   : 2018. 1. 1. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :사용자 코인 전자지갑 주소 가져오기
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<DepositVO> getCnAccInfo(DepositVO vo) throws Exception {
		return list("depositDAO.getCnAccInfo", vo);
	}
	
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
	public List<CnAccListVO> getCnAccList(DepositVO vo) throws Exception {
		return list("depositDAO.getCnAccList", vo);
	}

	/**
	 * @Method Name  : INSUPT30171030
	 * @작성일   : 2018. 1. 2. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :코인입금대기 및 완료
	 * @param vo
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public void INSUPT30171030(DepositVO vo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        convertProc("depositDAO.INSUPT30171030", vo);

        if(vo.getDptKndCd().equals("CMMC00000000283")) {
            update("depositDAO.compDepo", vo.getCnDptCode());
        }

	}
	
	/**
	 * @Method Name  : getCnDptCode
	 * @작성일   : 2018. 1. 2. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :코인입금대기 키 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public DepositVO getCnDptCode(DepositVO vo) throws Exception {
		return (DepositVO) selectByPk ("depositDAO.getCnDptCode", vo);
	}
	
	/**
	 * @Method Name  : INS10171028
	 * @작성일   : 2018. 1. 2. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :실제코인입금
	 * @param vo
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public void INS10171028(DepositVO vo) throws Exception {
		convertProc("depositDAO.INS10171028", vo);
	}
	
	public String getDestinationTag() throws Exception {
		return (String) selectByPk("depositDAO.getDestinationTag", null);
	}

	public void insCoinMoveHis(DepositVO vo) throws Exception {

		insert ("depositDAO.insCoinMoveHis", vo);
	}

	public WletUserInfoVO selectWletUserInfo(WletUserInfoVO vo) throws Exception {

	    return (WletUserInfoVO) selectByPk("depositDAO.selectWletUserInfo", vo);
    }

    public DepositVO getExRateDepositPrc(DepositVO vo) throws Exception {

	    return (DepositVO) selectByPk("depositDAO.getExRateDepositPrc", vo);
    }

    public String getTrnGenKey() throws Exception {

	    return (String) selectByPk("depositDAO.getTrnGenKey", null);
    }

    public void insTrnInfo(TrnInfoVO vo) throws Exception {

	    insert("depositDAO.insTrnInfo", vo);
    }

    public CmeTradeReqVO depositCash(CmeTradeReqVO vo) throws Exception {
        return (CmeTradeReqVO) convertProc("depositDAO.INS10171029", vo);
    }

    public int cardCoinOut(DepositVO vo) throws Exception {
	    return update("depositDAO.cardCoinOut", vo);
    }

    public int cardCoinIn(DepositVO vo) throws Exception {
		return update("depositDAO.cardCoinIn", vo);
	}

    public int cardCashOut(DepositVO vo) throws Exception {
        return update("depositDAO.cardCashOut", vo);
    }

    public String getCardCoinOutKey() throws Exception {
        return (String) selectByPk("depositDAO.getCardCoinOutKey", null);
    }

    public String getCardCoinInKey() throws Exception {
		return (String) selectByPk("depositDAO.getCardCoinInKey" , null);
	}

    public int insUptCardUseInfo(MoaCardReqVO vo) throws Exception {

        return update("depositDAO.insUptCardUseInfo", vo);
    }

    public MoaCardReqVO selectCardCoinUseInfo(String tradeId) throws Exception {
	    return (MoaCardReqVO) selectByPk("depositDAO.selectCardCoinUseInfo", tradeId);
    }

    public int getBmcCoinCnt(String wletAdr) throws Exception {
	    return (int) selectByPk("depositDAO.getBmcCoinCnt", wletAdr);
    }

    public double getTotUsdtAmt() throws Exception {
	    return (double) selectByPk("depositDAO.getTotUsdtAmt", null);
    }

    public String getKrwPoint(String userEmail) throws Exception{
		return (String) selectByPk("depositDAO.getKrwPoint", userEmail);
	}

	public void insMoaCardHistory(MoaCardCoinVO vo) throws Exception{
		insert("depositDAO.insMoaCardHistory" , vo);
	}

	public int getCoinMoveHourCnt(DepositVO vo) throws Exception {
	    return (int) selectByPk("depositDAO.getCoinMoveHourCnt", vo);
    }

    public List<String> getDepositUserEmail() throws Exception {
	    return list ("depositDAO.getDepositUserEmail", null);
    }

    public KrwWithVO getUserAccInfo(String userEmail) throws Exception{
		return (KrwWithVO)selectByPk("depositDAO.getUserAccInfo" , userEmail);
	}

	public String getCoinCode(String curcyNm) throws Exception{
		return (String) selectByPk("depositDAO.getCoinCode" , curcyNm);
	}

	public String getWletUserEmail(DepositVO vo) throws Exception{
		return (String) selectByPk("depositDAO.getWletUserEmail" , vo);
	}

	public int getDepositCheckCnt(DepositVO vo) throws Exception{
		return (int) selectByPk("depositDAO.getDepositCheckCnt" , vo);
	}

	public void PR_INTUPT_ADM_EVENT_CHECK(AdmEventVO vo) throws Exception{
		convertProc("depositDAO.PR_INTUPT_ADM_EVENT_CHECK" , vo);
	}

	public int getEventCnt() throws Exception{
		return (int) selectByPk("depositDAO.getEventCnt" , null);
	}

	public AdmEventVO getEventInfo(int no) throws Exception{
		return (AdmEventVO) selectByPk("depositDAO.getEventInfo" , no);
	}

	public void PR_INTUPT_ADM_EVENT(AdmEventVO vo) throws Exception{
		convertProc("depositDAO.PR_INTUPT_ADM_EVENT" , vo);
	}

	public List<AdmEventVO> getAdmEventList(AdmEventVO vo) throws Exception{
		return list("depositDAO.getAdmEventList" , vo);
	}

	public int getAdmEventListCnt(String userEmail) throws Exception{
		return (int) selectByPk("depositDAO.getAdmEventListCnt" , userEmail);
	}
}
