package com.bitkrx.api.trust.dao;

import com.bitkrx.api.trust.vo.LendingVO;
import com.bitkrx.api.trust.vo.TrustCoinVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrustDAO extends CmeComAbstractDAO {

    public int intApplyTrust(TrustCoinVO vo) throws Exception{
        return (int) insert("trustDAO.intApplyTrust", vo);
    }

    public List<TrustCoinVO> getTrustList(TrustCoinVO vo) throws Exception{
        return (List<TrustCoinVO>) list("trustDAO.getTrustList" , vo);
    }

    public int getTrustListCnt(TrustCoinVO vo) throws Exception{
        return (int) selectByPk("trustDAO.getTrustListCnt" , vo);
    }

    public int uptTrustInfo(TrustCoinVO vo) throws Exception{
        return (int) update("trustDAO.uptTrustInfo" , vo);
    }

    public String getCoinMinAmt(String cnKndCd) throws Exception{
        return (String) selectByPk("trustDAO.getCoinMinAmt" , cnKndCd);
    }

    public List<TrustCoinVO> getMonthRateList(String cnKndCd) throws Exception{
        return (List<TrustCoinVO>) list("trustDAO.getMonthRateList" , cnKndCd);
    }

    public int plusTrustCoin(TrustCoinVO vo) throws Exception{
        return (int) update("trustDAO.plusTrustCoin" , vo);
    }

    public int minusTrustCoin(TrustCoinVO vo) throws Exception{
        return (int) update("trustDAO.minusTrustCoin" , vo);
    }

    public String getUserCoinPrc(TrustCoinVO vo) throws Exception{
        return (String) selectByPk("trustDAO.getUserCoinPrc" , vo);
    }

    public TrustCoinVO getTrustInfo(String trustReqCode) throws Exception{
        return (TrustCoinVO) selectByPk("trustDAO.getTrustInfo" , trustReqCode);
    }

     ////////////////////////////////////////////////////////////////

    public LendingVO getInterest(String cnKndCd) throws Exception{
        return (LendingVO) selectByPk("trustDAO.getInterest" , cnKndCd);
    }

    public LendingVO getLendingAmt(String cnKndCd) throws Exception{
        System.out.println("trustDAO");
        return (LendingVO) selectByPk("trustDAO.getLendingAmt" , cnKndCd);
    }

    public String getApplyChk(String userEmail) throws Exception{
        return (String) selectByPk("trustDAO.getApplyChk" , userEmail);
    }

    public List<LendingVO> getLendingList(LendingVO vo) throws Exception{
        return (List<LendingVO>) list("trustDAO.getLendingList" , vo);
    }

    public int getLendingListCnt(LendingVO vo) throws Exception{
        return (int) selectByPk("trustDAO.getLendingListCnt" , vo);
    }

    public LendingVO PR_INS60180001(LendingVO vo) throws Exception{
        return (LendingVO) convertProc("trustDAO.PR_INS60180001" , vo);
    }

    public void PR_INS601800012(LendingVO vo) throws Exception{
        convertProc("trustDAO.PR_INS60180001" , vo);
    }

    public String getLendingDate(LendingVO vo) throws Exception{
        if(!"".equals(vo.getExtenSionDt())){
            return (String) selectByPk("trustDAO.getExtensionDate" , vo);
        }else{
            return (String) selectByPk("trustDAO.getLendingDate" , vo);
        }

    }

    public LendingVO getExpireDt(String renDftCode) throws Exception{
        return (LendingVO) selectByPk("trustDAO.getExpireDt" , renDftCode);
    }

    public LendingVO getLendingInfo(String renDftCode) throws Exception{
        return (LendingVO) selectByPk("trustDAO.getLendingInfo" , renDftCode);
    }

    public String getDifDate(String applyDt) throws Exception{
        return (String) selectByPk("trustDAO.getDifDate" , applyDt);
    }

    public LendingVO PR_INS60180001_REPAY(LendingVO vo) throws Exception{
        return (LendingVO) convertProc("trustDAO.PR_INS60180001_REPAY" , vo);
    }

    public LendingVO PR_INS60180001_EXTEND(LendingVO vo) throws Exception{
        return (LendingVO) convertProc("trustDAO.PR_INS60180001_EXTEND" , vo);
    }

    public List<LendingVO> getRepaymentList(LendingVO vo) throws Exception{
        return (List<LendingVO>) list("trustDAO.getRepaymentList" , vo);
    }

    public int getRepaymentListCnt(LendingVO vo) throws Exception{
        return (int) selectByPk("trustDAO.getRepaymentListCnt" , vo);
    }

    public LendingVO PR_INS60180001_1_REPAY(LendingVO vo) throws Exception{
        return (LendingVO) convertProc("trustDAO.PR_INS60180001_1_REPAY" , vo);
    }

    public int getTrustInterestCnt(TrustCoinVO vo) throws Exception{
        return (int) selectByPk("trustDAO.getTrustInterestCnt" , vo);
    }

    public List<TrustCoinVO> getTrustInterestList(TrustCoinVO vo) throws Exception{
        return (List<TrustCoinVO>) list("trustDAO.getTrustInterestList" , vo);
    }

}
