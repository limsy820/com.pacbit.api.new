package com.bitkrx.api.trust.service.impl;

import com.bitkrx.api.trust.dao.TrustDAO;
import com.bitkrx.api.trust.service.TrustService;
import com.bitkrx.api.trust.vo.LendingVO;
import com.bitkrx.api.trust.vo.TrustCoinVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrustServiceImpl implements TrustService {

    @Autowired
    TrustDAO trustDAO;

    public int intApplyTrust(TrustCoinVO vo) throws Exception{
        return trustDAO.intApplyTrust(vo);
    }

    public List<TrustCoinVO> getTrustList(TrustCoinVO vo) throws Exception{
        return trustDAO.getTrustList(vo);
    }

    public int getTrustListCnt(TrustCoinVO vo) throws Exception{
        return trustDAO.getTrustListCnt(vo);
    }

    public int uptTrustInfo(TrustCoinVO vo) throws Exception{
        return trustDAO.uptTrustInfo(vo);
    }

    public String getCoinMinAmt(String cnKndCd) throws Exception{
        return trustDAO.getCoinMinAmt(cnKndCd);
    }

    public List<TrustCoinVO>  getMonthRateList(String cnKndCd) throws Exception{
        return trustDAO.getMonthRateList(cnKndCd);
    }

    public int plusTrustCoin(TrustCoinVO vo) throws Exception{
        return trustDAO.plusTrustCoin(vo);
    }

    public int minusTrustCoin(TrustCoinVO vo) throws Exception{
        return trustDAO.minusTrustCoin(vo);
    }

    public String getUserCoinPrc(TrustCoinVO vo) throws Exception{
        return trustDAO.getUserCoinPrc(vo);
    }

    public TrustCoinVO getTrustInfo(String trustReqCode) throws Exception{
        return trustDAO.getTrustInfo(trustReqCode);
    }

    ////////////////////////////////////////////////

    @Override
    public LendingVO getInterest(String cnKndCd) throws Exception{
        return trustDAO.getInterest(cnKndCd);
    }

    @Override
    public LendingVO getLendingAmt(String cnKndCd) throws Exception{
        System.out.println("lendingServiceImpl");
        return trustDAO.getLendingAmt(cnKndCd);
    }

    @Override
    public String getApplyChk(String userEmail) throws Exception{
        return trustDAO.getApplyChk(userEmail);
    }

    @Override
    public List<LendingVO> getLendingList(LendingVO vo) throws Exception{
        return trustDAO.getLendingList(vo);
    }

    @Override
    public int getLendingListCnt(LendingVO vo) throws Exception{
        return trustDAO.getLendingListCnt(vo);
    }

    @Override
    public LendingVO PR_INS60180001(LendingVO vo) throws Exception{
        return trustDAO.PR_INS60180001(vo);
    }

    @Override
    public void PR_INS601800012(LendingVO vo) throws Exception{
        trustDAO.PR_INS601800012(vo);
    }

    @Override
    public String getLendingDate(LendingVO vo) throws Exception{
        return trustDAO.getLendingDate(vo);
    }

    @Override
    public LendingVO getExpireDt(String renDftCode) throws Exception{
        return trustDAO.getExpireDt(renDftCode);
    }

    @Override
    public LendingVO getLendingInfo(String renDftCode) throws Exception{
        return trustDAO.getLendingInfo(renDftCode);
    }

    @Override
    public String getDifDate(String applyDt) throws Exception{
        return trustDAO.getDifDate(applyDt);
    }

    @Override
    public LendingVO PR_INS60180001_REPAY(LendingVO vo) throws Exception{
        return trustDAO.PR_INS60180001_REPAY(vo);
    }

    @Override
    public LendingVO PR_INS60180001_EXTEND(LendingVO vo) throws Exception{
        return trustDAO.PR_INS60180001_EXTEND(vo);
    }

    @Override
    public List<LendingVO> getRepaymentList(LendingVO vo) throws Exception{
        return trustDAO.getRepaymentList(vo);
    }

    @Override
    public int getRepaymentListCnt(LendingVO vo) throws Exception{
        return trustDAO.getRepaymentListCnt(vo);
    }

    @Override
    public LendingVO PR_INS60180001_1_REPAY(LendingVO vo) throws Exception{
        return trustDAO.PR_INS60180001_1_REPAY(vo);
    }

    @Override
    public int getTrustInterestCnt(TrustCoinVO vo) throws Exception {
        return trustDAO.getTrustInterestCnt(vo);
    }

    @Override
    public List<TrustCoinVO> getTrustInterestList(TrustCoinVO vo) throws Exception {
        return trustDAO.getTrustInterestList(vo);
    }
}
