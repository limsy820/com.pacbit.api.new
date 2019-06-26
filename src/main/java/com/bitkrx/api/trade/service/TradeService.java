/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.trade.service;

import java.util.List;

import com.bitkrx.api.auth.vo.UserTradeCheckVO;
import com.bitkrx.api.common.vo.CoinMinMaxVO;
import com.bitkrx.api.main.vo.CmeBeforeSellListResVO;
import com.bitkrx.api.main.vo.CmeCoinBeforeBuyListResVO;
import com.bitkrx.api.main.vo.CmeCoinPriceChartReqVO;
import com.bitkrx.api.main.vo.CmeOrderListResVO;
import com.bitkrx.api.main.vo.CmeRealTimeCoinPriceReqVO;
import com.bitkrx.api.main.vo.CmeRealTimeCoinPriceResVO;
import com.bitkrx.api.main.vo.beforeListVO;
import com.bitkrx.api.trade.vo.*;
import com.bitkrx.config.CmeResultVO;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.trade.service
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 23.
 */
public interface TradeService {

	/**
	 * @Method Name  : INSUPT30171020
	 * @작성일   : 2017. 11. 23. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :코인구매정보 입력 및 수정
	 * @param vo
	 *//*
	public void INSUPT30171043(CmeTradeReqVO vo);*/
	
	/**
	 * @Method Name  : beforeBuyList
	 * @작성일   : 2017. 11. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @Method 설명 : 구매 미체결 내역
	 * @서비스ID : IF-EX-007
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public List<CmeCoinBeforeBuyListResVO> beforeBuyList(CmeCoinPriceChartReqVO vo) throws Exception;

	/**
	 * @Method Name  : beforeSellList
	 * @작성일   : 2017. 11. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @Method 설명 : 판매 미체결 내역
	 * @서비스ID : 
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 */
	public List<CmeBeforeSellListResVO> beforeSellList(CmeCoinPriceChartReqVO vo)throws Exception;
	
	/**
	 * @Method Name  : beforeSellList
	 * @작성일   : 2017. 11. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @Method 설명 : 미체결 내역
	 * @서비스ID : 
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 */
	public List<beforeListVO> beforeList(CmeCoinPriceChartReqVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : orderList
	 * @작성일   : 2017. 11. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CmeOrderListResVO> orderList(CmeCoinPriceChartReqVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : waitTradeList
	 * @작성일   : 2017. 11. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<WaitTradeListVO> waitTradeList(TradeParamVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : compTradeList
	 * @작성일   : 2017. 11. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CompTradeListVO> compTradeList(TradeParamVO vo) throws Exception;
	
	/**
	 * @Method Name  : beforeTradingList
	 * @작성일   : 2017. 12. 4. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :사용자미체결내역
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<BeforeTradeVO> beforeTradingList(CmeTradeReqVO vo) throws Exception;
	
	/**
	 * @Method Name  : tradeHistory
	 * @작성일   : 2017. 12. 5. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CmeTradeResVO> tradeHistory(CmeTradeReqVO vo) throws Exception;
	
	/**
	 * @Method Name  : buyMenuView
	 * @작성일   : 2017. 12. 7. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :구매조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public BuyMenuVO buyMenuView(CmeTradeReqVO vo) throws Exception;
	
	/**
	 * @Method Name  : maxBuyPrice
	 * @작성일   : 2017. 12. 7. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :구매가능금액
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public BuyPosPrcVO maxBuyPrice(CmeTradeReqVO vo) throws Exception;

	/**
	 * 
	 * @Method Name  : tradeHistoryCnt
	 * @작성일   : 2017. 12. 11. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int tradeHistoryCnt(CmeTradeReqVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : buyOrder
	 * @작성일   : 2017. 12. 10. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 구매 등록
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public CmeResultVO buyOrder(CmeTradeReqVO vo) throws Exception;
	
	/**
	 * @Method Name  : sellMenuView
	 * @작성일   : 2017. 12. 9. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 판매조회 (코인을 원화로 판매시 화면)
	 * @param vo
	 * @return
	 */
	public CmeTradeSellMenuViewResVO sellMenuView(CmeTradeReqVO vo) throws Exception;
	
	/**
	 * @Method Name  : buyMenuView
	 * @작성일   : 2017. 12. 9. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 수령통화가능금액조회
	 * @param vo
	 * @return
	 */
	public RecCoinViewVO recCoinView(CmeTradeReqVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : buyOrder
	 * @작성일   : 2017. 12. 10. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 판매 등록
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public CmeResultVO sellOrder(CmeTradeReqVO vo) throws Exception;
	
	
	/**
	 * @Method Name  : realTimeCoinPrice
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 실시간코인시세
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CmeRealTimeCoinPriceResVO> realTimeCoinPrice(CmeRealTimeCoinPriceReqVO vo) throws Exception;

	
	/**
	 * @Method Name  : notConList
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 임승연
	 * @변경이력  :
	 * @Method 설명 : 각 코인별 미체결 목록
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NotConListVO> notConList(TradeParamVO vo) throws Exception;

	/**
	 * 
	 * @Method Name  : selectTradeList
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<TradeListVO> selectTradeList(TradeParamVO vo) throws Exception;

	/**
	 * @Method Name  : notConList
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 임승연
	 * @변경이력  :
	 * @Method 설명 : 거래현황 구매대기
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	
	public List<WaitTradeListVO> waitBuyList(TradeParamVO vo) throws Exception;
	/**
	 * @Method Name  : notConList
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 임승연
	 * @변경이력  :
	 * @Method 설명 :거래현황 판매대기
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<WaitTradeListVO> waitSellList(TradeParamVO vo) throws Exception;
	
	/**
	 * @Method Name  : beforeTradingList
	 * @작성일   : 2017. 12. 13. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :사용자미체결취소
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public CmeResultVO orderCalcel(CmeTradeReqVO vo) throws Exception;

	/**
	 * 
	 * @Method Name  : selectOrder
	 * @작성일   : 2017. 12. 13. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public CmeTradeReqVO selectOrder(CmeTradeReqVO vo) throws Exception;

	/**
	 * 
	 * @Method Name  : selectDate
	 * @작성일   : 2017. 12. 15. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 : 현재날자구하기
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public String selectDate(String string) throws Exception;
	
	
	public String getCnPrc(String str) throws Exception;

    public String isPricePer(CmeTradeReqVO vo) throws Exception;

    public String isPricePerETH(CmeTradeReqVO vo) throws Exception;

    public String isPricePerUSDT(CmeTradeReqVO vo) throws Exception;

    public String isPricePerBTC(CmeTradeReqVO vo) throws Exception;

    public List<CmeTradeReqVO> selectLowPriceSellList(CmeTradeReqVO vo) throws Exception;

    public List<CmeTradeReqVO> selectHighPriceBuyList(CmeTradeReqVO vo) throws Exception;

    public List<CmeTradeReqVO> selectLowPriceSellListETH(CmeTradeReqVO vo) throws Exception;

    public List<CmeTradeReqVO> selectHighPriceBuyListETH(CmeTradeReqVO vo) throws Exception;

    public List<CmeTradeReqVO> selectLowPriceSellListUSDT(CmeTradeReqVO vo) throws Exception;

    public List<CmeTradeReqVO> selectHighPriceBuyListUSDT(CmeTradeReqVO vo) throws Exception;

    public List<CmeTradeReqVO> selectLowPriceSellListBTC(CmeTradeReqVO vo) throws Exception;

    public List<CmeTradeReqVO> selectHighPriceBuyListBTC(CmeTradeReqVO vo) throws Exception;

	public List<OrderResVO> getExList(OrderResVO vo) throws Exception;
	
	public AutoTradeVO PR_INSUPT20171080(AutoTradeVO vo) throws Exception;
	
	public AutoTradeVO PR_INSUPT20171090(AutoTradeVO vo) throws Exception;
	
	public List<AutoTradeVO> getAutoBuy(AutoTradeVO vo) throws Exception;
	
	public List<AutoTradeVO> getAutoSell(AutoTradeVO vo) throws Exception;
	
	public int delAutoBuy (AutoTradeVO vo) throws Exception;
	
	public int delAutoSell (AutoTradeVO vo) throws Exception;
	
	
	public void buyOrder2(CmeTradeReqVO vo) throws Exception;
	
	public void sellOrder2(CmeTradeReqVO vo) throws Exception;
	
	public void insAutoCoinTrade(AutoCoinTradeVO vo) throws Exception;
	
	public void delAutoCoinTrade(AutoCoinTradeVO vo) throws Exception;
	
	public AutoCoinTradeResVO getAutoCoinTrade(AutoCoinTradeVO vo) throws Exception;
	
	public List<CoinListVO> getCoinList() throws Exception;

    public CmeTradeReqVO orderMQ(CmeTradeReqVO vo) throws Exception;

    public CoinMinMaxAmtVO getMaxMinAmt(CoinMinMaxAmtVO vo) throws Exception;

    public List<TradePlusVO> getPlusList(TradeParamVO vo) throws Exception;

    public List<TradePlusVO> getTimeList(TradeParamVO vo) throws Exception;

    public ExRateVO getExRate(DepositVO vo) throws Exception;

    public String getSellCancelOrder(CmeTradeReqVO vo) throws Exception;

    public String getSellCancelOrderETH(CmeTradeReqVO vo) throws Exception;

    public String getSellCancelOrderUSDT(CmeTradeReqVO vo) throws Exception;

    public String getSellCancelOrderBTC(CmeTradeReqVO vo) throws Exception;

    public String getBuyCancelOrder(CmeTradeReqVO vo) throws Exception;

    public String getBuyCancelOrderETH(CmeTradeReqVO vo) throws Exception;

    public String getBuyCancelOrderUSDT(CmeTradeReqVO vo) throws Exception;

    public String getBuyCancelOrderBTC(CmeTradeReqVO vo) throws Exception;

    public String getRandomSellCancelOrder(String cnKndCd) throws Exception;

    public String getRandomBuyCancelOrder(String cnKndCd) throws Exception;




    public CmeTradeReqVO orderMQETH(CmeTradeReqVO vo) throws Exception;

    public List<NotConListVO> notConListETH(TradeParamVO vo) throws Exception;

    public List<TradeListVO> selectTradeListETH(TradeParamVO vo) throws Exception;

    public List<CmeTradeResVO> tradeHistoryETH(CmeTradeReqVO vo) throws Exception;

    public int tradeHistoryCntETH(CmeTradeReqVO vo) throws Exception;

    public List<WaitTradeListVO> waitBuyListETH(TradeParamVO vo) throws Exception;

    public List<WaitTradeListVO> waitSellListETH(TradeParamVO vo) throws Exception;

    public List<CompTradeListVO> compTradeListETH(TradeParamVO vo) throws Exception;

    public CmeTradeReqVO selectOrderETH(CmeTradeReqVO vo) throws Exception;

    public CmeResultVO orderCalcelETH(CmeTradeReqVO vo) throws Exception;

    public CmeTradeSellMenuViewResVO sellMenuViewETH(CmeTradeReqVO vo) throws Exception;

    public BuyMenuVO buyMenuViewETH(CmeTradeReqVO vo) throws Exception;

    public RecCoinViewVO recCoinViewETH(CmeTradeReqVO vo) throws Exception;




    public CmeTradeReqVO orderMQUSDT(CmeTradeReqVO vo) throws Exception;

    public List<NotConListVO> notConListUSDT(TradeParamVO vo) throws Exception;

    public List<TradeListVO> selectTradeListUSDT(TradeParamVO vo) throws Exception;

    public List<CmeTradeResVO> tradeHistoryUSDT(CmeTradeReqVO vo) throws Exception;

    public int tradeHistoryCntUSDT(CmeTradeReqVO vo) throws Exception;

    public List<WaitTradeListVO> waitBuyListUSDT(TradeParamVO vo) throws Exception;

    public List<WaitTradeListVO> waitSellListUSDT(TradeParamVO vo) throws Exception;

    public List<CompTradeListVO> compTradeListUSDT(TradeParamVO vo) throws Exception;

    public CmeTradeReqVO selectOrderUSDT(CmeTradeReqVO vo) throws Exception;

    public CmeResultVO orderCalcelUSDT(CmeTradeReqVO vo) throws Exception;

    public CmeTradeSellMenuViewResVO sellMenuViewUSDT(CmeTradeReqVO vo) throws Exception;

    public BuyMenuVO buyMenuViewUSDT(CmeTradeReqVO vo) throws Exception;

    public RecCoinViewVO recCoinViewUSDT(CmeTradeReqVO vo) throws Exception;



    public CmeTradeReqVO orderMQBTC(CmeTradeReqVO vo) throws Exception;

    public List<NotConListVO> notConListBTC(TradeParamVO vo) throws Exception;

    public List<TradeListVO> selectTradeListBTC(TradeParamVO vo) throws Exception;

    public List<CmeTradeResVO> tradeHistoryBTC(CmeTradeReqVO vo) throws Exception;

    public int tradeHistoryCntBTC(CmeTradeReqVO vo) throws Exception;

    public List<WaitTradeListVO> waitBuyListBTC(TradeParamVO vo) throws Exception;

    public List<WaitTradeListVO> waitSellListBTC(TradeParamVO vo) throws Exception;

    public List<CompTradeListVO> compTradeListBTC(TradeParamVO vo) throws Exception;

    public CmeTradeReqVO selectOrderBTC(CmeTradeReqVO vo) throws Exception;

    public CmeResultVO orderCalcelBTC(CmeTradeReqVO vo) throws Exception;

    public CmeTradeSellMenuViewResVO sellMenuViewBTC(CmeTradeReqVO vo) throws Exception;

    public BuyMenuVO buyMenuViewBTC(CmeTradeReqVO vo) throws Exception;

    public RecCoinViewVO recCoinViewBTC(CmeTradeReqVO vo) throws Exception;

    public List<CoinCoreInfoVO> getCoinCoreInfoList() throws Exception ;

    public String realKRWPrice(String curcyCd) throws Exception;

    public String getTodaySellAmtKRW(TradeParamVO vo) throws Exception;

    public String getTodaySellAmt(TradeParamVO vo) throws Exception;

	public String getTodaySellAmtETH(TradeParamVO vo) throws Exception;

	public String getTodaySellAmtBTC(TradeParamVO vo) throws Exception;

	public String getTodaySellAmtUSDT(TradeParamVO vo) throws Exception;

	public String getTodayBuyAmt(TradeParamVO vo) throws Exception;

	public String getTodayBuyAmtETH(TradeParamVO vo) throws Exception;

	public String getTodayBuyAmtBTC(TradeParamVO vo) throws Exception;

	public String getTodayBuyAmtUSDT(TradeParamVO vo) throws Exception;

	public CoinMinMaxVO selectCoinTradeCheck(CmeTradeReqVO vo) throws Exception;

	public int getSellNoChk(String orderNo) throws Exception;

	public int getSellNoChkETH(String orderNo) throws Exception;

	public int getSellNoChkBTC(String orderNo) throws Exception;

	public int getSellNoChkUSDT(String orderNo) throws Exception;

	public int getBuyNoChk(String orderNo) throws Exception;

	public int getBuyNoChkETH(String orderNo) throws Exception;

	public int getBuyNoChkBTC(String orderNo) throws Exception;

	public int getBuyNoChkUSDT(String orderNo) throws Exception;

	public List<FreeWithdrawVO> getFreeWithdraw(CmeTradeReqVO vo) throws Exception;
	public List<FeeSaleVO> getFeeSale(CmeTradeReqVO vo) throws Exception;
	public List<CoinTradePrcVO> getCoinTradePrc() throws Exception;

	public String findCoinTradePrc(String key) throws  Exception;
    public void invalidateCoinTradePrc();

    public List<ArcMarketVO> getArcMarket(UserTradeCheckVO vo) throws Exception;

    public String getCnPrice(String cnKndCd) throws Exception;

	public String getCnPriceETH(String cnKndCd) throws Exception;

	public String getCnPriceBTC(String cnKndCd) throws Exception;

	public String getCnPriceUSDT(String cnKndCd) throws Exception;

	public String getCmmNm() throws  Exception;
}
