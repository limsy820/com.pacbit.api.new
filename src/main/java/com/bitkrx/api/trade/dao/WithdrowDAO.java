package com.bitkrx.api.trade.dao;

import java.util.List;

import com.bitkrx.api.trade.vo.*;
import org.springframework.stereotype.Repository;

import com.bitkrx.config.dao.CmeComAbstractDAO;

@Repository
public class WithdrowDAO extends CmeComAbstractDAO{

	public List<WithFeeVO> getWithFee() throws Exception {
		return list("withdrowDAO.getWithFee", null);
	}
	
	public WithdrowVO withdrowCash(WithdrowVO vo) throws Exception {
		return (WithdrowVO) convertProc("withdrowDAO.PR_CASH_OUT", vo);
	}

	public List<WithListVO> getWithList(WithdrowVO vo) throws Exception {
		return list ("withdrowDAO.getWithList", vo);
	}
	
	public WithdrowVO withdrowCoin(WithdrowVO vo) throws Exception {
		return (WithdrowVO) convertProc("withdrowDAO.PR_COIN_OUT", vo);
	}
	
	public List<WithCoinListVO> getWithCoinList(WithdrowVO vo) throws Exception {
		return list ("withdrowDAO.getWithCoinList", vo);
	}
	
	public MinMaxWithVO getRemPrc(WithdrowVO vo) throws Exception {
		
		return (MinMaxWithVO) selectByPk ("withdrowDAO.getRemPrc", vo);
	}

	public MinMaxWithVO getRemPrc3(WithdrowVO vo) throws Exception{
		return (MinMaxWithVO) selectByPk("withdrowDAO.getRemPrc3" , vo);
	}

	public MinMaxWithVO getCoinRemPrc(WithdrowVO vo) throws Exception {
		
		return (MinMaxWithVO) selectByPk ("withdrowDAO.getCoinRemPrc", vo);
	}
	
	public String getEthCode(String userEmail) throws Exception {
		
		return (String) selectByPk ("withdrowDAO.getEthCode", userEmail);
	}
	
	public String getPosCnAmt(WithdrowVO vo) throws Exception {
		return (String) selectByPk ("withdrowDAO.getPosCnAmt", vo);
	}
	
	public String coinOutKey() throws Exception {
		return (String) selectByPk ("withdrowDAO.coinOutKey", null);
	}

    public String cashOutKey() throws Exception {
        return (String) selectByPk ("withdrowDAO.cashOutKey", null);
    }
	
	public WithdrowVO withdrowCoin2(WithdrowVO vo) throws Exception {
		return (WithdrowVO) convertProc("withdrowDAO.PR_COIN_OUT2", vo);
	}
	
	public WithdrowVO PR_COIN_OUT_RTN(WithdrowVO vo) throws Exception {
		return (WithdrowVO) convertProc("withdrowDAO.PR_COIN_OUT_RTN", vo);
	}
	
	//은행리스트 조회
	public List<BankListVO> getBankList(String cmmCd) throws Exception {
		return list("withdrowDAO.getBankList", cmmCd);
	}
	
	public WithdrowVO getXrpUserEmail(WithdrowVO vo) throws Exception {
		
		return (WithdrowVO) selectByPk ("withdrowDAO.getXrpUserEmail", vo);
	}

    public WithdrowVO getWletUserEmail(WithdrowVO vo) throws Exception {

        return (WithdrowVO) selectByPk ("withdrowDAO.getWletUserEmail", vo);
    }
	
	public WithCoinListVO selectWithCoin(String wdrReqCode) throws Exception {
		return (WithCoinListVO) selectByPk ("withdrowDAO.selectWithCoin", wdrReqCode);
	}

	public WithdrowVO getExRateWithdrowPrc(WithdrowVO vo) throws Exception {
	    return (WithdrowVO) selectByPk("withdrowDAO.getExRateWithdrowPrc", vo);
    }

    public WithdrowVO selectCashWith(String wdrReqCode) throws Exception {
	    return (WithdrowVO) selectByPk("withdrowDAO.selectCashWith", wdrReqCode);
    }

    public int uptCashStatus(WithdrowVO vo) throws Exception {
	    return update("withdrowDAO.uptCashStatus", vo);
    }

    public String getCard1DayPrc(String userEmail) throws Exception {
        return (String) selectByPk("withdrowDAO.getCard1DayPrc", userEmail);
    }

    public int uptWithCoinStaProgress(String wdrReqCode) throws Exception {
	    return update("withdrowDAO.uptWithCoinStaProgress", wdrReqCode );
    }

    public WithdrowVO withdrowCash2(WithdrowVO vo) throws Exception {
        return (WithdrowVO) convertProc("withdrowDAO.PR_CASH_OUT2", vo);
    }

    public int uptInGubun(String cnDptCode) throws Exception {
	    return update("withdrowDAO.uptInGubun", cnDptCode);
    }

    public String getCoinLimitYn(String curcyCd) throws Exception{
		return (String) selectByPk("withdrowDAO.getCoinLimitYn" , curcyCd);
	}

	public int getWithdrowUserCheck(WithdrowCheckVO vo) throws Exception{
		return (int) selectByPk("withdrowDAO.getWithdrowUserCheck" , vo);
	}

    public int uptRealFee(WithdrowVO vo) throws Exception {
	    return update("withdrowDAO.uptRealFee", vo);
    }

    public WithCoinListVO selectWithSendCoin(String wdrReqCode) throws Exception {
        return (WithCoinListVO) selectByPk ("withdrowDAO.selectWithSendCoin", wdrReqCode);
    }

    public WithdrowCheckVO getWithdrowYn(String curcyCd) throws Exception{
		return (WithdrowCheckVO) selectByPk("withdrowDAO.getWithdrowYn" , curcyCd);
	}

	public int getWithdrowUserAutoCheck(WithdrowCheckVO vo) throws Exception{
		return (int) selectByPk("withdrowDAO.getWithdrowUserAutoCheck" , vo);
	}

	public WithdrowVO getCoinAutoYn(String cnKndCd) throws Exception{
		return (WithdrowVO) selectByPk("withdrowDAO.getCoinAutoYn" , cnKndCd);
	}

	public int getAutoUserCheck(WithdrowVO vo) throws Exception{
		return (int) selectByPk("withdrowDAO.getAutoUserCheck" , vo);
	}

	public List<WithdrowVO> getCoinSendingList() throws Exception {
	    return list("withdrowDAO.getCoinSendingList", null);
    }

	public String getInSndCheck(String cnKndCd) throws Exception{
		return (String) selectByPk("withdrowDAO.getInSndCheck" , cnKndCd);
	}

	public int getAccCheck(KrwWithVO2 vo) throws Exception{
		return (int) selectByPk("withdrowDAO.getAccCheck" , vo);
	}

	public int getWithdrowCheck(KrwWithVO2 vo) throws Exception{
		return (int) selectByPk("withdrowDAO.getWithdrowCheck" , vo);
	}

	public int getFreeWithdrawCnt(WithdrowVO vo) throws Exception{
		return (int) selectByPk("withdrowDAO.getFreeWithdrawCnt" , vo);
	}

	public DepositVO getUserAccInfo(DepositVO vo) throws Exception{
		return (DepositVO) selectByPk("withdrowDAO.getUserAccInfo" , vo);
	}

	public void InsBankInfo(KrwWithVO vo) throws Exception{
		insert("withdrowDAO.InsBankInfo" , vo);
	}

}
