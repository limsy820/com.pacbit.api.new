/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.trade.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bitkrx.api.auth.vo.UserTradeCheckVO;
import com.bitkrx.api.common.vo.CoinMinMaxVO;
import com.bitkrx.api.trade.vo.*;
import org.springframework.stereotype.Repository;

import com.bitkrx.api.main.vo.CmeBeforeSellListResVO;
import com.bitkrx.api.main.vo.CmeCoinBeforeBuyListResVO;
import com.bitkrx.api.main.vo.CmeCoinPriceChartReqVO;
import com.bitkrx.api.main.vo.CmeOrderListResVO;
import com.bitkrx.api.main.vo.CmeRealTimeCoinPriceReqVO;
import com.bitkrx.api.main.vo.CmeRealTimeCoinPriceResVO;
import com.bitkrx.api.main.vo.beforeListVO;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;
import com.bitkrx.config.util.CmmCdConstant;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.trade.dao
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 23.
 */
@Repository
public class TradeDAO extends CmeComAbstractDAO{

	/**
	 * @Method Name  : INSUPT30171020
	 * @작성일   : 2017. 11. 23. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :코인구매정보 입력 및 수정
	 * @param vo
	 *//*
	public void INSUPT30171043(CmeTradeReqVO vo) {
		update("tradeDAO.INSUPT30171043", vo);
	}*/
	
	/**
	 * @Method Name  : buyMenuView
	 * @작성일   : 2017. 12. 9. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 구매조회 (원화로 코인 구매시 화면)
	 * @param vo
	 * @return
	 */
	public BuyMenuVO buyMenuView(CmeTradeReqVO vo) {
		
		return (BuyMenuVO) selectByPk("tradeDAO.buyMenuView", vo);
	}
	
	
	/**
	 * @Method Name  : buyMenuView
	 * @작성일   : 2017. 12. 9. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 구매가능금액 조회( 코인으로 코인 구매시 화면)
	 * @param vo
	 * @return
	 */
	public BuyPosPrcVO maxBuyPrice(CmeTradeReqVO vo) {
		
		return (BuyPosPrcVO) selectByPk("tradeDAO.maxBuyPrice", vo);
	}
	
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
	public CmeResultVO buyOrder(CmeTradeReqVO vo) throws Exception {
		
		CmeResultVO res = new CmeResultVO();
		
		convertProc("tradeDAO.INSUPT30171043", vo);
		
		res.setProceduresResult(vo.getRESULT());
		
		return res;
	}

	/**
	 * 
	 * @Method Name  : tradeHistory
	 * @작성일   : 2017. 12. 11. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmeTradeResVO> tradeHistory(CmeTradeReqVO vo) {
		return list("tradeDAO.tradeHistory", vo);
	}

	/**
	 * 
	 * @Method Name  : tradeHistoryCnt
	 * @작성일   : 2017. 12. 11. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 */
	public int tradeHistoryCnt(CmeTradeReqVO vo) {
		return (Integer)getSqlSession().selectOne("tradeDAO.tradeHistoryCnt", vo);
	}
	
	/**
	 * @Method Name  : sellMenuView
	 * @작성일   : 2017. 12. 9. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 판매조회 (코인을 원화로 판매시 화면)
	 * @param vo
	 * @return
	 */
	public CmeTradeSellMenuViewResVO sellMenuView(CmeTradeReqVO vo) {
		
		return (CmeTradeSellMenuViewResVO) selectByPk("tradeDAO.sellMenuView", vo);
	}
	
	/**
	 * @Method Name  : buyMenuView
	 * @작성일   : 2017. 12. 9. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 수령통화가능금액조회
	 * @param vo
	 * @return
	 */
	public RecCoinViewVO recCoinView(CmeTradeReqVO vo) {
		
		return (RecCoinViewVO) selectByPk("tradeDAO.recCoinView", vo);
	}
	
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
	public CmeResultVO sellOrder(CmeTradeReqVO vo) throws Exception {
		
		CmeResultVO res = new CmeResultVO();
		
		convertProc("tradeDAO.INSUPT30171044", vo);
		
		res.setProceduresResult(vo.getRESULT());
		
		return res;
	}
	
	public List<CmeCoinBeforeBuyListResVO> beforeBuyList(CmeCoinPriceChartReqVO vo) throws Exception {
		
		return list ("tradeDAO.beforeBuyList", vo);
	}
	
	public List<CmeBeforeSellListResVO> beforeSellList(CmeCoinPriceChartReqVO vo) throws Exception {
		
		return list ("tradeDAO.beforeSellList", vo);
	}
	
	public List<beforeListVO> beforeList(CmeCoinPriceChartReqVO vo) throws Exception {
		
		return list ("tradeDAO.beforeList", vo);
	}
	
	/**
	 * @Method Name  : beforeTradingList
	 * @작성일   : 2017. 12. 11. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :사용자미체결내역
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<BeforeTradeVO> beforeTradingList(CmeTradeReqVO vo) throws Exception{
		if(vo.getOrderCd().equals(CmmCdConstant.BUY_CD)) {
			return list ("tradeDAO.beforeBuyTradingList", vo);
		} else {
			return list ("tradeDAO.beforeSellTradingList", vo);
		}
		
	}
	

	/**
	 * @Method Name  : orderList
	 * @작성일   : 2017. 12. 11. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 체결내역
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CmeOrderListResVO> orderList(CmeCoinPriceChartReqVO vo) throws Exception {
		// TODO Auto-generated method stub
		return list ("tradeDAO.orderList", vo);
	}
	
	public List<CmeRealTimeCoinPriceResVO> realTimeCoinPrice(CmeRealTimeCoinPriceReqVO vo) throws Exception {
		// TODO Auto-generated method stub
		return list ("tradeDAO.realTimeCoinPrice", vo);
	}


	/**
	 * @Method Name  : notConList
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 임승연
	 * @변경이력  :
	 * @Method 설명 : 코인별 미체결 주문
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NotConListVO> notConList(TradeParamVO vo)  throws Exception {
		return list ("tradeDAO.notConList", vo);
	}


	@SuppressWarnings("unchecked")
	public List<TradeListVO> selectTradeList(TradeParamVO vo) throws Exception{
		return list("tradeDAO.selectTradeList", vo);
	}



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
	public List<WaitTradeListVO> waitBuyList(TradeParamVO vo) {
		return list ("tradeDAO.waitBuyList", vo);
	}

	/**
	 * @Method Name  : notConList
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 임승연
	 * @변경이력  :
	 * @Method 설명 : 거래현황 판매대기
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<WaitTradeListVO> waitSellList(TradeParamVO vo) {
		return list ("tradeDAO.waitSellList", vo);
	}

	/**
	 * @Method Name  : notConList
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 임승연
	 * @변경이력  :
	 * @Method 설명 : 거래현황 거래완료
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CompTradeListVO> compTradeList(TradeParamVO vo) {
		return list ("tradeDAO.compTradeList", vo);
	}
	
	
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
	public CmeResultVO orderCalcel(CmeTradeReqVO vo) throws Exception{

		CmeResultVO res = new CmeResultVO();

		if(vo.getOrderNo().indexOf("CNPH") > -1) {//구매취소
			CmeTradeReqVO remVO = (CmeTradeReqVO) selectByPk("tradeDAO.getRemPrc", vo.getOrderNo());
			vo.setRemPrc(remVO.getRemPrc());
			convertProc ("tradeDAO.PR_CNCL20171043", vo);//취소 프로시저
			List list = (List) vo.getRESULT();
			if(list != null) {//취소 Result
				Map map = (Map) list.get(0);
				String rtnCd = (String) map.get("RTN_CD");
				if(rtnCd != null && rtnCd.equals("1")) {
					res.setResultMsg("미체결취소가 완료되었습니다.");
					res.setResultStrCode("000");
				} else {
					res.setResultMsg( (String) map.get("RNT_MSG"));
					res.setResultStrCode(rtnCd);
				}
			}

			/*if(res.getResultStrCode().equals("000")) {//취소 성공일 경우 실제 취소된 금액 환불
				//CmeTradeReqVO remVO = (CmeTradeReqVO) selectByPk("tradeDAO.getRemPrc", vo.getOrderNo());
				if(vo.getMkState().equals(vo.getCmmNm())){	// 기준마켓일경우
					if(remVO != null) {
						vo.setCnclPrc(remVO.getRemPrc());
						vo.setCrgPrc("0");
						vo.setPayKndCd(remVO.getCurcyCd());
						//convertProc("depositDAO.INS30171020", vo);
						convertProc("depositDAO.INS10171029", vo);
					}
				}else{
					String curcyCd = "";
					if("ETH".equals(vo.getMkState())){
						curcyCd = "CMMC00000000206";
					}else if("BTC".equals(vo.getMkState())){
						curcyCd = "CMMC00000000205";
					}else if("USDT".equals(vo.getMkState())){
						curcyCd = "CMMC00000001086";
					}
					DepositVO dvo = new DepositVO();
					dvo.setCurcyCd(curcyCd);
					dvo.setUserEmail(vo.getUserEmail());
					dvo.setCnclAmt(Double.parseDouble(remVO.getRemPrc()));
					convertProc("depositDAO.INS10171028", dvo);//실제코인 INS
				}
			}*/


		} else if(vo.getOrderNo().indexOf("CNSL") > -1) {//판매취소
			CmeTradeReqVO remVO = (CmeTradeReqVO) selectByPk("tradeDAO.getRemAmt", vo.getOrderNo());
			vo.setRemAmt(remVO.getRemAmt());
			convertProc ("tradeDAO.PR_CNCL20171044", vo);
			List list = (List) vo.getRESULT();
			if(list != null) {//취소 Result
				Map map = (Map) list.get(0);
				String rtnCd = (String) map.get("RTN_CD");
				if(rtnCd != null && rtnCd.equals("1")) {
					res.setResultMsg("미체결취소가 완료되었습니다.");
					res.setResultStrCode("000");
				} else {
					res.setResultMsg( (String) map.get("RNT_MSG"));
					res.setResultStrCode(rtnCd);
				}
			}
			/*if(res.getResultStrCode().equals("000")) {//취소 성공일 경우 실제 취소된 수량 환불
				//CmeTradeReqVO remVO = (CmeTradeReqVO) selectByPk("tradeDAO.getRemAmt", vo.getOrderNo());
				if(remVO != null) {
					DepositVO dvo = new DepositVO();
					//dvo.setCnAmt(Double.parseDouble(remVO.getRemAmt()));
					//dvo.setDptKndCd("CMMC00000000014");
					dvo.setCurcyCd(remVO.getCurcyCd());
					dvo.setUserEmail(vo.getUserEmail());
					//dvo.setRegIp(vo.getRegIp());
					//dvo.setDealNo(vo.getOrderNo());
					//convertProc("depositDAO.INSUPT30171030", dvo);//입금진행 INS
					//dvo.setDptKndCd("CMMC00000000283");
					//DepositVO dvo_ = (DepositVO) selectByPk ("depositDAO.getCnDptCode", dvo);
					//dvo.setCnDptCode(dvo_.getCnDptCode());
					//convertProc("depositDAO.INSUPT30171030", dvo);//입금완료 UPT
					dvo.setCnclAmt(Double.parseDouble(remVO.getRemAmt()));
					convertProc("depositDAO.INS10171028", dvo);//실제코인 INS
				}
			}*/

		}
		
		
		res.setProceduresResult(vo.getRESULT());
		
		return res;
		
		
	}
	
	public CmeTradeReqVO getMarketPrice(CmeTradeReqVO vo) throws Exception {
		return (CmeTradeReqVO) selectByPk("tradeDAO.getMarketPrice", vo);
	}

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
	public CmeTradeReqVO selectOrder(CmeTradeReqVO vo) throws Exception{
		if(vo.getOrderNo().indexOf("CNPH") > -1) {
			vo =  getSqlSession().selectOne("tradeDAO.selectOrderBuy",vo);
		} else if(vo.getOrderNo().indexOf("CNSL") > -1) {
			vo =  getSqlSession().selectOne("tradeDAO.selectOrderSel",vo);
		}
		return vo;
	}

	/**
	 * 
	 * @Method Name  : selectDate
	 * @작성일   : 2017. 12. 15. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public String selectDate(String string) throws Exception{
		return getSqlSession().selectOne("tradeDAO.selectDate", string);
	}
	
	
	public String getCnPrc(String str) throws Exception {
		return (String) selectByPk("tradeDAO.getCnPrice", str);
	}
	
	/**
	 * @Method Name  : getExList
	 * @작성일   : 2018. 1. 9. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :체결내역 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<OrderResVO> getExList(OrderResVO vo) throws Exception {
		
		return list("tradeDAO.getExList", vo);
	}
	
	
	public AutoTradeVO PR_INSUPT20171080(AutoTradeVO vo) throws Exception {
		
		return (AutoTradeVO) convertProc("tradeDAO.PR_INSUPT20171080", vo);
	}
	
	public AutoTradeVO PR_INSUPT20171090(AutoTradeVO vo) throws Exception {
	
		return (AutoTradeVO) convertProc("tradeDAO.PR_INSUPT20171090", vo);
	}
	
	public List<AutoTradeVO> getAutoBuy(AutoTradeVO vo) throws Exception {
		
		return list("tradeDAO.getAutoBuy", vo);
	}
	
	public List<AutoTradeVO> getAutoSell(AutoTradeVO vo) throws Exception {
		
		return list("tradeDAO.getAutoSell", vo);
	}
	
	public int delAutoBuy (AutoTradeVO vo) throws Exception {
		
		return delete("tradeDAO.delAutoBuy", vo);
	}
	
	public int delAutoSell (AutoTradeVO vo) throws Exception {
		
		return delete("tradeDAO.delAutoSell", vo);
	}
	
	public void insAutoCoinTrade(AutoCoinTradeVO vo) throws Exception {
		insert("tradeDAO.insAutoCoinTrade", vo);
	}
	
	public void delAutoCoinTrade(AutoCoinTradeVO vo) throws Exception {
		delete("tradeDAO.delAutoCoinTrade", vo);
	}
	
	public AutoCoinTradeResVO getAutoCoinTrade(AutoCoinTradeVO vo) throws Exception {
		AutoCoinTradeResVO resVO = (AutoCoinTradeResVO) selectByPk ("tradeDAO.getAutoCoinTrade", vo);
		if(resVO == null) resVO = new AutoCoinTradeResVO();
		return resVO;
	}
	
	public List<CoinListVO> getCoinList() throws Exception {
		return list ("tradeDAO.getCoinList", null);
	}

	public CmeTradeReqVO orderMQ(CmeTradeReqVO vo) throws Exception {
	    return (CmeTradeReqVO) convertProc("tradeDAO.orderMQ", vo);
    }

    public CoinMinMaxAmtVO getMaxMinAmt(CoinMinMaxAmtVO vo) throws Exception {
	    return (CoinMinMaxAmtVO) selectByPk("tradeDAO.getMaxMinAmt", vo);
    }

    public List<TradePlusVO> getPlusList(TradeParamVO vo) throws Exception {
	    return list("tradeDAO.getPlusList", vo);
    }

    public List<TradePlusVO> getTimeList(TradeParamVO vo) throws Exception {
        return list("tradeDAO.getTimeList", vo);
    }

    public ExRateVO getExRate(DepositVO vo) throws Exception {
		return (ExRateVO) selectByPk("tradeDAO.getExRate", vo);
	}

	public String getSellCancelOrder(CmeTradeReqVO vo) throws Exception {
	    return (String) selectByPk("tradeDAO.getSellCancelOrder", vo);
    }

    public String getBuyCancelOrder(CmeTradeReqVO vo) throws Exception {
        return (String) selectByPk("tradeDAO.getBuyCancelOrder", vo);
    }

    public String getRandomSellCancelOrder(String cnKndCd) throws Exception {
        return (String) selectByPk("tradeDAO.getRandomSellCancelOrder", cnKndCd);
    }

    public String getRandomBuyCancelOrder(String cnKndCd) throws Exception {
        return (String) selectByPk("tradeDAO.getRandomBuyCancelOrder", cnKndCd);
    }

    public String isPricePer(CmeTradeReqVO vo) throws Exception {
        return (String) selectByPk("tradeDAO.isPricePer", vo);
    }

    public List<CmeTradeReqVO> selectLowPriceSellList(CmeTradeReqVO vo) throws Exception {
        return list("tradeDAO.selectLowPriceSellList", vo);
    }

    public List<CmeTradeReqVO> selectHighPriceBuyList(CmeTradeReqVO vo) throws Exception {
        return list("tradeDAO.selectHighPriceBuyList", vo);
    }


    public List<CoinCoreInfoVO> getCoinCoreInfoList() throws Exception {
	    return list("tradeDAO.getCoinCoreInfoList", null);
    }

    public String realKRWPrice(String curcyCd) throws Exception{
		return (String) selectByPk("tradeDAO.realKRWPrice", curcyCd);
	}

	public String getTodaySellAmt(TradeParamVO vo) throws Exception{
		return (String) selectByPk("tradeDAO.getTodaySellAmt", vo);
	}

	public String getTodayBuyAmt(TradeParamVO vo) throws Exception{
		return (String) selectByPk("tradeDAO.getTodayBuyAmt", vo);
	}

	public CoinMinMaxVO selectCoinTradeCheck(CmeTradeReqVO vo) throws Exception{
		return (CoinMinMaxVO) selectByPk("tradeDAO.selectCoinTradeCheck", vo);
	}

	public int getSellNoChk(String orderNo) throws Exception{
		return (int) selectByPk("tradeDAO.getSellNoChk" , orderNo);
	}

	public int getBuyNoChk(String orderNo) throws Exception{
		return (int) selectByPk("tradeDAO.getBuyNoChk" , orderNo);
	}

	public List<FreeWithdrawVO> getFreeWithdraw(CmeTradeReqVO vo) throws Exception {
		return list ("tradeDAO.getFreeWithdraw", vo);
	}

	public List<FeeSaleVO> getFeeSale(CmeTradeReqVO vo) throws Exception {
		return list ("tradeDAO.getFeeSale", vo);
	}

	public List<CoinTradePrcVO> getCoinTradePrc() throws Exception {
		return list ("tradeDAO.getCoinTradePrc", null);
	}

    public List<ArcMarketVO> getArcMarket(UserTradeCheckVO vo) throws Exception {
        return list ("tradeDAO.getArcMarket", vo);
    }

	public String getCmmNm() throws Exception {
		return (String) selectByPk ("tradeDAO.getCmmNm", null);
	}
}












