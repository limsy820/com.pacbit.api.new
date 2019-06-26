package com.bitkrx.api.common.service.impl;

import com.bitkrx.api.auth.vo.SubmitCertVO;
import com.bitkrx.api.common.dao.CoinInfoDAO;
import com.bitkrx.api.common.service.CoinInfoService;
import com.bitkrx.api.common.vo.*;
import com.bitkrx.api.main.vo.CmeUserAssetReqVO;
import com.bitkrx.api.main.vo.CmeUserAssetResVO;
import com.bitkrx.config.annotation.CommonDataSource;
import com.bitkrx.config.dbinfo.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CoinInfoServiceImpl implements CoinInfoService {

    @Autowired
    CoinInfoDAO coinInfoDAO;

    public CoinInfoVO getCoinInfo(CoinInfoVO vo) throws Exception {
        return coinInfoDAO.getCoinInfo(vo);
    }

    public List<CoinDailyPrcVO> getCoinDailyPrc(CoinDailyPrcVO vo) throws Exception {
        return coinInfoDAO.getCoinDailyPrc(vo);
    }

    public int getCoinDailyPrcCnt(CoinDailyPrcVO vo) throws Exception {
        return coinInfoDAO.getCoinDailyPrcCnt(vo);
    }

    public List<CoinMinMaxVO> getCoinMinMaxInfo() throws Exception {

        return coinInfoDAO.getCoinMinMaxInfo();
    }

    public List<CoinMinMaxVO> getCoinMinMaxInfo2() throws Exception {

        return coinInfoDAO.getCoinMinMaxInfo2();
    }

    public List<CoinPayVO> getCoinPayInfo(CoinPayVO vo) throws Exception {

        return coinInfoDAO.getCoinPayInfo(vo);
    }

    public int insUptMarketCapInfo(Map<String, String> map) throws Exception {

        return coinInfoDAO.insUptMarketCapInfo(map);
    }

    public List<Map<String, String>> getMarketCapInfo() throws Exception {

        return coinInfoDAO.getMarketCapInfo();
    }

    public List<CoinUseVO>
    getUseOkCoin() throws Exception{
        return coinInfoDAO.getUseOkCoin();
    }

    public CoinBalanceVO getCoinBalanceList(CmeUserAssetReqVO vo) throws Exception{
        return coinInfoDAO.getCoinBalanceList(vo);
    }

    public CashBalanceVO getCashBalanceList(CmeUserAssetReqVO vo) throws Exception{
        return coinInfoDAO.getCashBalanceList(vo);
    }

    public String getUserEmail(String cardNum) throws Exception {
        return coinInfoDAO.getUserEmail(cardNum);
    }

    public List<Map<String, String>> getMkCoinList(String cnKndCd)  throws Exception {
        return coinInfoDAO.getMkCoinList(cnKndCd);
    }



    @CommonDataSource(DataSourceType.MKETH)
    public List<CoinDailyPrcVO> getCoinDailyPrcETH(CoinDailyPrcVO vo) throws Exception {
        return coinInfoDAO.getCoinDailyPrc(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public int getCoinDailyPrcCntETH(CoinDailyPrcVO vo) throws Exception {
        return coinInfoDAO.getCoinDailyPrcCnt(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public List<CoinDailyPrcVO> getCoinDailyPrcUSDT(CoinDailyPrcVO vo) throws Exception {
        return coinInfoDAO.getCoinDailyPrc(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public int getCoinDailyPrcCntUSDT(CoinDailyPrcVO vo) throws Exception {
        return coinInfoDAO.getCoinDailyPrcCnt(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public List<CoinDailyPrcVO> getCoinDailyPrcBTC(CoinDailyPrcVO vo) throws Exception {
        return coinInfoDAO.getCoinDailyPrc(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public int getCoinDailyPrcCntBTC(CoinDailyPrcVO vo) throws Exception {
        return coinInfoDAO.getCoinDailyPrcCnt(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public MarketFeeVO getETHFee() throws Exception{
        return coinInfoDAO.getMarketFee();
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public MarketFeeVO getUSDTFee() throws Exception{
        return coinInfoDAO.getMarketFee();
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public MarketFeeVO getBTCFee() throws Exception{
        return coinInfoDAO.getMarketFee();
    }

    public MarketFeeVO getKRWFee() throws Exception{
        return coinInfoDAO.getMarketFee();
    }

    public String getCoinAtmFee(CmeUserAssetReqVO vo) throws Exception {
        return coinInfoDAO.getCoinAtmFee(vo);
    }

    public List<CoinCoreInfoListVO> selectCoinCoreInfoList(CoinCoreInfoListVO vo) throws Exception {
        return coinInfoDAO.selectCoinCoreInfoList(vo);
    }

    public CoinCoreInfoListVO selectCoinCoreInfo(CoinCoreInfoListVO vo) throws Exception {
        return coinInfoDAO.selectCoinCoreInfo(vo);
    }

    public List<MarketTradeVO> getMarketTradeCheck(String marketCode) throws Exception{
        return coinInfoDAO.getMarketTradeCheck(marketCode);
    }

    public List<MarketTradeVO> getMarketInfo() throws Exception{
        return coinInfoDAO.getMarketInfo();
    }

    public String getCoinNm(String curcyCd) throws Exception{
        return coinInfoDAO.getCoinNm(curcyCd);
    }

    public String selectKRWdual() throws Exception{
        return coinInfoDAO.selectDual();
    }

    @CommonDataSource(DataSourceType.MKETH)
    public String selectETHdual() throws Exception{
        return coinInfoDAO.selectDual();
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public String selectBTCdual() throws Exception{
        return coinInfoDAO.selectDual();
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public String selectUSDTdual() throws Exception{
        return coinInfoDAO.selectDual();
    }

    public String getCoinNote(CoinInfoVO vo) throws Exception{
        return coinInfoDAO.getCoinNote(vo);
    }

    @Override
    public CommonExchgInfoVO commonExchgInfo(SubmitCertVO vo) throws Exception{
        return coinInfoDAO.commonExchgInfo(vo);
    }

    @CommonDataSource(DataSourceType.OPRBOARD)
    public String getFile_sn(SubmitCertVO vo) throws Exception{
        return coinInfoDAO.getFile_sn(vo);
    }

}
