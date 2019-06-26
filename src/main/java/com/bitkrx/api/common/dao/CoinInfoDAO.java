package com.bitkrx.api.common.dao;

import com.bitkrx.api.auth.vo.SubmitCertVO;
import com.bitkrx.api.common.vo.*;
import com.bitkrx.api.main.vo.CmeUserAssetReqVO;
import com.bitkrx.api.main.vo.CmeUserAssetResVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class CoinInfoDAO extends CmeComAbstractDAO {

    public CoinInfoVO getCoinInfo(CoinInfoVO vo) {

        return (CoinInfoVO) selectByPk("coinInfoDAO.getCoinInfo", vo);
    }

    public List<CoinDailyPrcVO> getCoinDailyPrc(CoinDailyPrcVO vo) throws Exception {
        return list("coinInfoDAO.getCoinDailyPrc", vo);
    }

    public int getCoinDailyPrcCnt(CoinDailyPrcVO vo) throws Exception {

        return (int) selectByPk("coinInfoDAO.getCoinDailyPrcCnt", vo);
    }

    public List<CoinMinMaxVO> getCoinMinMaxInfo() throws Exception {

        return list("coinInfoDAO.getCoinMinMaxInfo", null);
    }

    public List<CoinMinMaxVO> getCoinMinMaxInfo2() throws Exception {

        return list("coinInfoDAO.getCoinMinMaxInfo2", null);
    }

    public List<CoinPayVO> getCoinPayInfo(CoinPayVO vo) throws Exception {

        return list("coinInfoDAO.getCoinPayInfo", vo);
    }

    public int insUptMarketCapInfo(Map<String, String> map) throws Exception {

        return update("coinInfoDAO.insUptMarketCapInfo", map);
    }

    public List<Map<String, String>> getMarketCapInfo() throws Exception {

        return list("coinInfoDAO.getMarketCapInfo", null);
    }

    public List<CoinUseVO> getUseOkCoin() throws Exception{
        return  list("coinInfoDAO.getUseOkCoin", null);
    }

    public CoinBalanceVO getCoinBalanceList(CmeUserAssetReqVO vo) throws Exception{
        return (CoinBalanceVO) selectByPk("coinInfoDAO.getCoinBalanceList", vo);
    }

    public CashBalanceVO getCashBalanceList(CmeUserAssetReqVO vo) throws Exception{
        return (CashBalanceVO) selectByPk("coinInfoDAO.getCashBalanceList", vo);
    }

    public String getUserEmail(String cardNum) throws Exception {
        return (String)selectByPk("coinInfoDAO.getUserEmail", cardNum);
    }

    public List<Map<String, String>> getMkCoinList(String cnKndCd)  throws Exception {
        return list("coinInfoDAO.getMkCoinList", cnKndCd);
    }

    public MarketFeeVO getMarketFee() throws Exception{
        return (MarketFeeVO) selectByPk("coinInfoDAO.getMarketFee" , null);
    }

    public String getCoinAtmFee(CmeUserAssetReqVO vo) throws Exception {
        return (String) selectByPk("coinInfoDAO.getCoinAtmFee", vo);
    }

    public List<CoinCoreInfoListVO> selectCoinCoreInfoList(CoinCoreInfoListVO vo) throws Exception {
        return list("coinInfoDAO.selectCoinCoreInfo", vo);
    }

    public CoinCoreInfoListVO selectCoinCoreInfo(CoinCoreInfoListVO vo) throws Exception {
        return (CoinCoreInfoListVO) selectByPk("coinInfoDAO.selectCoinCoreInfo", vo);
    }

    public List<MarketTradeVO> getMarketTradeCheck(String marketCode) throws Exception{
        return list("coinInfoDAO.getMarketTradeCheck", marketCode);
    }

    public List<MarketTradeVO> getMarketInfo() throws Exception{
        return list("coinInfoDAO.getMarketInfo", null);
    }

    public String getCoinNm(String curcyCd) throws Exception{
        return (String) selectByPk("coinInfoDAO.getCoinNm" , curcyCd);
    }

    public String selectDual() throws Exception{
        return (String) selectByPk("coinInfoDAO.selectDual" , null);
    }

    public String getCoinNote(CoinInfoVO vo) throws Exception{
        return (String) selectByPk("coinInfoDAO.getCoinNote" , vo);
    }

    public CommonExchgInfoVO commonExchgInfo(SubmitCertVO vo) throws Exception{
        return (CommonExchgInfoVO) selectByPk("coinInfoDAO.commonExchgInfo" , vo);
    }

    public String getFile_sn(SubmitCertVO vo) throws Exception{
        return (String) selectByPk("coinInfoDAO.getFile_sn" , vo);
    }
}
