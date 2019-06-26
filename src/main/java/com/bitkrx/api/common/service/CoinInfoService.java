package com.bitkrx.api.common.service;

import com.bitkrx.api.auth.vo.SubmitCertVO;
import com.bitkrx.api.common.vo.*;
import com.bitkrx.api.main.vo.CmeUserAssetReqVO;
import com.bitkrx.api.main.vo.CmeUserAssetResVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CoinInfoService {

    public CoinInfoVO getCoinInfo(CoinInfoVO vo) throws Exception;

    public List<CoinDailyPrcVO> getCoinDailyPrc(CoinDailyPrcVO vo) throws Exception;

    public int getCoinDailyPrcCnt(CoinDailyPrcVO vo) throws Exception;

    public List<CoinMinMaxVO> getCoinMinMaxInfo() throws Exception;

    public List<CoinMinMaxVO> getCoinMinMaxInfo2() throws Exception;

    public List<CoinPayVO> getCoinPayInfo(CoinPayVO vo) throws Exception;

    public int insUptMarketCapInfo(Map<String, String> map) throws Exception;

    public List<Map<String, String>> getMarketCapInfo() throws Exception ;

    public List<CoinUseVO> getUseOkCoin() throws Exception;

    public CoinBalanceVO getCoinBalanceList(CmeUserAssetReqVO vo) throws Exception;

    public CashBalanceVO getCashBalanceList(CmeUserAssetReqVO vo) throws Exception;

    public String getUserEmail(String cardNum) throws Exception;

    public List<Map<String, String>> getMkCoinList(String cnKndCd)  throws Exception;

    public List<CoinDailyPrcVO> getCoinDailyPrcETH(CoinDailyPrcVO vo) throws Exception;

    public int getCoinDailyPrcCntETH(CoinDailyPrcVO vo) throws Exception;

    public List<CoinDailyPrcVO> getCoinDailyPrcUSDT(CoinDailyPrcVO vo) throws Exception;

    public int getCoinDailyPrcCntUSDT(CoinDailyPrcVO vo) throws Exception;

    public List<CoinDailyPrcVO> getCoinDailyPrcBTC(CoinDailyPrcVO vo) throws Exception;

    public int getCoinDailyPrcCntBTC(CoinDailyPrcVO vo) throws Exception;

    public MarketFeeVO getETHFee() throws Exception;

    public MarketFeeVO getUSDTFee() throws Exception;

    public MarketFeeVO getKRWFee() throws Exception;

    public MarketFeeVO getBTCFee() throws Exception;

    public String getCoinAtmFee(CmeUserAssetReqVO vo) throws Exception;

    public List<CoinCoreInfoListVO> selectCoinCoreInfoList(CoinCoreInfoListVO vo) throws Exception;

    public CoinCoreInfoListVO selectCoinCoreInfo(CoinCoreInfoListVO vo) throws Exception;

    public List<MarketTradeVO> getMarketTradeCheck(String marketCode) throws Exception;

    public List<MarketTradeVO> getMarketInfo() throws Exception;

    public String getCoinNm(String curcyCd) throws Exception;

    public String selectKRWdual() throws Exception;

    public String selectETHdual() throws Exception;

    public String selectBTCdual() throws Exception;

    public String selectUSDTdual() throws Exception;

    public String getCoinNote(CoinInfoVO vo) throws Exception;

    public CommonExchgInfoVO commonExchgInfo(SubmitCertVO vo) throws Exception;

    public String getFile_sn(SubmitCertVO vo) throws Exception;
}