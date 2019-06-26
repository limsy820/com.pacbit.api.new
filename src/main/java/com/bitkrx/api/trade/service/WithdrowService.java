package com.bitkrx.api.trade.service;

import java.util.List;

import com.bitkrx.api.trade.vo.*;

import javax.servlet.http.HttpServletRequest;

public interface WithdrowService {
    public List<WithFeeVO> getWithFee() throws Exception;
	
	public String withdrowCash(WithdrowVO vo) throws Exception;
	
	public List<WithListVO> getWithList(WithdrowVO vo) throws Exception;
	
	public WithdrowVO withdrowCoin(HttpServletRequest request , WithdrowVO vo) throws Exception;

	public List<WithCoinListVO> getWithCoinList(WithdrowVO vo) throws Exception;
	
	public MinMaxWithVO getRemPrc(WithdrowVO vo) throws Exception;

	public MinMaxWithVO getRemPrc3(WithdrowVO vo) throws Exception;

	public MinMaxWithVO getCoinRemPrc(WithdrowVO vo) throws Exception;
	
	public String getPosCnAmt(WithdrowVO vo) throws Exception;
	
	public String withUserCoin(WithdrowVO vo) throws Exception;
	
	//은행리스트 조회
	public List<BankListVO> getBankList(String CmmCd) throws Exception;
	
	public WithCoinListVO selectWithCoin(String wdrReqCode) throws Exception;

    public WithdrowVO getExRateWithdrowPrc(WithdrowVO vo) throws Exception;

    public String getEthCode(String userEmail) throws Exception ;

    public WithdrowVO selectCashWith(String wdrReqCode) throws Exception;

    public int uptCashStatus(WithdrowVO vo) throws Exception;

    public WithdrowVO PR_COIN_OUT_RTN(WithdrowVO vo) throws Exception;

    public String getCard1DayPrc(String userEmail) throws Exception;

    public int uptWithCoinStaProgress(String wdrReqCode) throws Exception;

    public int uptInGubun(String cnDptCode) throws Exception;

    public String getCoinLimitYn(String curcyCd) throws Exception;

    public int getWithdrowUserCheck(WithdrowCheckVO vo) throws Exception;

    public int uptRealFee(WithdrowVO vo) throws Exception;

    public WithCoinListVO selectWithSendCoin(String wdrReqCode) throws Exception;

    public WithdrowCheckVO getWithdrowYn(String curcyCd) throws Exception;

    public int getWithdrowUserAutoCheck(WithdrowCheckVO vo) throws Exception;

    public WithdrowVO getCoinAutoYn(String cnKndCd) throws Exception;

    public int getAutoUserCheck(WithdrowVO vo) throws Exception;

    public List<WithdrowVO> getCoinSendingList() throws Exception;

    public WithdrowVO getWletUserEmail(WithdrowVO vo) throws Exception;

    public String getInSndCheck(String cnKndCd) throws Exception;

    public int getAccCheck(KrwWithVO2 vo) throws Exception;

    public int getWithdrowCheck(KrwWithVO2 vo) throws Exception;

    public int getFreeWithdrawCnt(WithdrowVO vo) throws Exception;

    public void InsBankInfo(KrwWithVO vo) throws Exception;
}
