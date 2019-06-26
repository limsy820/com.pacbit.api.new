package com.bitkrx.api.trust.service;

import com.bitkrx.api.trust.vo.LendingVO;
import com.bitkrx.api.trust.vo.TrustCoinVO;

import java.util.List;

public interface TrustService {

    public int intApplyTrust(TrustCoinVO vo) throws Exception;

    public List<TrustCoinVO> getTrustList(TrustCoinVO vo) throws Exception;

    public int getTrustListCnt(TrustCoinVO vo) throws Exception;

    public int uptTrustInfo(TrustCoinVO vo) throws Exception;

    public String getCoinMinAmt(String cnKndCd) throws Exception;

    public List<TrustCoinVO> getMonthRateList(String cnKndCd) throws Exception;

    public int plusTrustCoin(TrustCoinVO vo) throws Exception;

    public int minusTrustCoin(TrustCoinVO vo) throws Exception;

    public String getUserCoinPrc(TrustCoinVO vo) throws Exception;

    public TrustCoinVO getTrustInfo(String trustReqCode) throws Exception;

    public LendingVO getInterest(String cnKndCd) throws Exception;

    public LendingVO getLendingAmt(String cnKndCd) throws Exception;

    public String getApplyChk(String userEmail) throws Exception;

    public List<LendingVO> getLendingList(LendingVO vo) throws Exception;

    public int getLendingListCnt(LendingVO vo) throws Exception;

    public LendingVO PR_INS60180001(LendingVO vo) throws Exception;

    public void PR_INS601800012(LendingVO vo) throws Exception;

    public String getLendingDate(LendingVO vo) throws Exception;

    public LendingVO getExpireDt(String renDftCode) throws Exception;

    public LendingVO getLendingInfo(String renDftCode) throws Exception;

    public String getDifDate(String applyDt) throws Exception;

    public LendingVO PR_INS60180001_REPAY(LendingVO vo) throws Exception;

    public LendingVO PR_INS60180001_EXTEND(LendingVO vo) throws Exception;

    public List<LendingVO> getRepaymentList(LendingVO vo) throws Exception;

    public int getRepaymentListCnt(LendingVO vo) throws Exception;

    public LendingVO PR_INS60180001_1_REPAY(LendingVO vo) throws Exception;

    int getTrustInterestCnt(TrustCoinVO vo) throws Exception;

    List<TrustCoinVO> getTrustInterestList(TrustCoinVO vo)throws Exception;

}
