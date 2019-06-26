/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.trade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitkrx.api.auth.vo.UserTradeCheckVO;
import com.bitkrx.api.common.vo.CoinMinMaxVO;
import com.bitkrx.api.trade.vo.*;
import com.bitkrx.config.annotation.CommonDataSource;
import com.bitkrx.config.dbinfo.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bitkrx.api.main.vo.CmeBeforeSellListResVO;
import com.bitkrx.api.main.vo.CmeCoinBeforeBuyListResVO;
import com.bitkrx.api.main.vo.CmeCoinPriceChartReqVO;
import com.bitkrx.api.main.vo.CmeOrderListResVO;
import com.bitkrx.api.main.vo.CmeRealTimeCoinPriceReqVO;
import com.bitkrx.api.main.vo.CmeRealTimeCoinPriceResVO;
import com.bitkrx.api.main.vo.beforeListVO;
import com.bitkrx.api.push.dao.CmeFcmPushDAO;
import com.bitkrx.api.push.vo.SendMsgVO;
import com.bitkrx.api.trade.dao.TradeDAO;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.logging.CmeCommonLogger;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.util.CoinUtil;
import com.bitkrx.config.util.StringUtils;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.trade.service.impl
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 23.
 */
@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	TradeDAO tradeDAO;


	@Autowired
	CmeFcmPushDAO pushDAO;
	
	protected CmeCommonLogger log = new CmeCommonLogger(this.getClass());
	
	CoinUtil coinUtil = CoinUtil.getinstance();
	/**
	 * @Method Name  : INSUPT30171020
	 * @작성일   : 2017. 11. 23. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :코인구매정보 입력 및 수정
	 * @param vo
	 *//*
	public void INSUPT30171043(CmeTradeReqVO vo) {
		tradeDAO.INSUPT30171043(vo);
	}*/

	/**
	 * @Method Name  : beforeBuyList
	 * @작성일   : 2017. 11. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @Method 설명 : 구매 미체결 내역
	 * @서비스ID : IF-EX-007
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<CmeCoinBeforeBuyListResVO> beforeBuyList(CmeCoinPriceChartReqVO vo) throws Exception {
		// TODO Auto-generated method stub
		return tradeDAO.beforeBuyList(vo);
	}

	/**
	 * @Method Name  : beforeSellList
	 * @작성일   : 2017. 11. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<CmeBeforeSellListResVO> beforeSellList(CmeCoinPriceChartReqVO vo) throws Exception{
		// TODO Auto-generated method stub
		return tradeDAO.beforeSellList(vo);
	}
	
	/**
	 * @Method Name  : beforeSellList
	 * @작성일   : 2017. 11. 29. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<beforeListVO> beforeList(CmeCoinPriceChartReqVO vo) throws Exception{
		// TODO Auto-generated method stub
		
		return tradeDAO.beforeList(vo);
	}

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
	@Override
	public List<CmeOrderListResVO> orderList(CmeCoinPriceChartReqVO vo) throws Exception {
		// TODO Auto-generated method stub
		return tradeDAO.orderList(vo);
	}
	
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
	public List<WaitTradeListVO> waitTradeList(TradeParamVO vo) throws Exception{
		return coinUtil.waitTradeList(vo);
	}


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
	public List<BeforeTradeVO> beforeTradingList(CmeTradeReqVO vo) throws Exception{
		
		return tradeDAO.beforeTradingList(vo);
	}

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
	public BuyPosPrcVO maxBuyPrice(CmeTradeReqVO vo) throws Exception{
		return tradeDAO.maxBuyPrice(vo);
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
		
		return tradeDAO.buyOrder(vo);
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
		
		return tradeDAO.sellOrder(vo);
	}

	
	public List<CmeRealTimeCoinPriceResVO> realTimeCoinPrice(CmeRealTimeCoinPriceReqVO vo) throws Exception {
		// TODO Auto-generated method stub
		return tradeDAO.realTimeCoinPrice(vo);
	}


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
	@Override
	public String selectDate(String string) throws Exception {
		return tradeDAO.selectDate(string);
	}
	
	public String getCnPrc(String str) throws Exception {
		return tradeDAO.getCnPrc(str) + "";
	}
	
	/**
	 * @Method Name  : getExList
	 * @작성일   : 2018. 1. 9. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 체결내역 조회
	 * @return
	 * @throws Exception
	 */
	public List<OrderResVO> getExList(OrderResVO vo) throws Exception {
		return tradeDAO.getExList(vo);
	}
	
	
	/**
	 * @Method Name  : PR_INSUPT20171080
	 * @작성일   : 2018. 1. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 자동거래 구매등록
	 * @return
	 * @throws Exception
	 */
	public AutoTradeVO PR_INSUPT20171080(AutoTradeVO vo) throws Exception {
		
		return tradeDAO.PR_INSUPT20171080(vo);
	}
	/**
	 * @Method Name  : PR_INSUPT20171090
	 * @작성일   : 2018. 1. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 자동거래 판매등록
	 * @return
	 * @throws Exception
	 */
	public AutoTradeVO PR_INSUPT20171090(AutoTradeVO vo) throws Exception {
	
		return tradeDAO.PR_INSUPT20171090(vo);
	}
	
	public List<AutoTradeVO> getAutoBuy(AutoTradeVO vo) throws Exception {
		
		return tradeDAO.getAutoBuy(vo);
	}
	
	public List<AutoTradeVO> getAutoSell(AutoTradeVO vo) throws Exception {
		
		return tradeDAO.getAutoSell(vo);
	}
	
	public int delAutoBuy (AutoTradeVO vo) throws Exception {
		
		return tradeDAO.delAutoBuy(vo);
	}
	
	public int delAutoSell (AutoTradeVO vo) throws Exception {
		
		return tradeDAO.delAutoSell(vo);
	}

	
	/**
	 * @Method Name  : buyOrder2
	 * @작성일   : 2018. 1. 31. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :구매주문 테스트용
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	@Async("tradeTaskExecutor")
	public void buyOrder2(CmeTradeReqVO vo) throws Exception {
		// TODO Auto-generated method stub
		CmeResultVO res = new CmeResultVO();
		res = tradeDAO.buyOrder(vo);
		List list = (List) res.getProceduresResult();
		Map map = (Map) list.get(0);
		String rtnCd = (String) map.get("RTN_CD");
		if(rtnCd != null && rtnCd.equals("1")) {
			res.setResultMsg("구매등록 성공");
			res.setResultStrCode("000");
		} else if (rtnCd != null && rtnCd.equals("-99999")){
			res.setResultMsg("대량 거래를 실행하여 구매등록에 실패하였습니다.");
			res.setResultStrCode(rtnCd);
		} else if (rtnCd != null && rtnCd.equals("-20195")){
			res.setResultMsg("최대 판매 갯수를 초과했습니다.");
			res.setResultStrCode(rtnCd);
		} else if (rtnCd != null && rtnCd.equals("-20091")){
			res.setResultMsg("구매 현금이 부족합니다.");
			res.setResultStrCode(rtnCd);
		} else if (rtnCd != null && rtnCd.equals("-20095")){
			res.setResultMsg("판매대기 리스트가 없습니다.");
			res.setResultStrCode(rtnCd);
		} else {
			res.setResultMsg("접속량이 많아 구매에 실패하였습니다.다시 시도해 주세요.");
			res.setResultStrCode(rtnCd);
		}
		
		String coinNm = makeCoinNm(vo.getBuyCurcyCd());
		String sysdate = selectDate("");
		//구매내역 저장
		if(rtnCd != null && rtnCd.equals("1")) {
			// Y-> 지정가 N-> 시장가
			if (vo.getApntPhsYn().equals("Y")) {
				
				SendMsgVO smvo = new SendMsgVO();
				smvo.setUserEmail(vo.getUserEmail());
				smvo.setSendYn("N");
				smvo.setCmmCd("CMMC00000000197");
				smvo.setMsg(sysdate + "|" + vo.getClientCd() + "|" + coinNm + "|" + StringUtils.toNumFomat(Integer.parseInt(vo.getPhsPrc())) + "|" + vo.getPhsAmt());
				pushDAO.PR_INSUPT_SND_MSG(smvo);
				
			}
		}
		
		//체결내역저장
		String rntMsg = (String) map.get("RNT_MSG");
		String orderNo = rntMsg.substring(rntMsg.indexOf("CNPH"), rntMsg.indexOf("CNPH") + 19);
		OrderResVO rvo = new OrderResVO();
		rvo.setCnPhsCode(orderNo);
		if(!"".equals(orderNo)) {
			SendMsgVO smvo = new SendMsgVO();
			smvo.setUserEmail(vo.getUserEmail());
			smvo.setSendYn("N");
			smvo.setCmmCd("CMMC00000000178");
			smvo.setMsg(orderNo + "|" + vo.getClientCd());
			pushDAO.PR_INSUPT_SND_MSG(smvo);
		}
		//log.ViewErrorLog("구매 return ::: " + rntMsg);
		
		
	}
	
	/**
	 * @Method Name  : buyOrder2
	 * @작성일   : 2018. 1. 31. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :판매주문 테스트용
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	@Async("tradeTaskExecutor")
	public void sellOrder2(CmeTradeReqVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		CmeResultVO res = new CmeResultVO();
		res = tradeDAO.sellOrder(vo);
		List list = (List) res.getProceduresResult();
		Map map = (Map) list.get(0);
		String rtnCd = (String) map.get("RTN_CD");
		if(rtnCd != null && rtnCd.equals("1")) {
			res.setResultMsg("판매등록 성공");
			res.setResultStrCode("000");
		} else if (rtnCd != null && rtnCd.equals("-99999")){
			res.setResultMsg("대량 거래를 실행하여 판매등록에 실패하였습니다.");
			res.setResultStrCode(rtnCd);
		} else if(rtnCd != null && rtnCd.equals("-20091")) {
			res.setResultMsg("구매 현금이 부족합니다.");
			res.setResultStrCode(rtnCd);
		} else if(rtnCd != null && rtnCd.equals("-20090")) {
			res.setResultMsg("구매 포인트가 부족합니다.");
			res.setResultStrCode(rtnCd);
		} else if(rtnCd != null && rtnCd.equals("-20092")) {
			res.setResultMsg("총 금액이 부족합니다.");
			res.setResultStrCode(rtnCd);
		} else if(rtnCd != null && rtnCd.equals("-20095")) {
			res.setResultMsg("판매대기 리스트가 없습니다.");
			res.setResultStrCode(rtnCd);
		} else if(rtnCd != null && rtnCd.equals("-20098")) {
			res.setResultMsg("최대 구매 금액이 부족합니다.");
			res.setResultStrCode(rtnCd);
		} else {
			res.setResultMsg("접속량이 많아 판매에 실패하였습니다.다시 시도해 주세요.");
			res.setResultStrCode(rtnCd);
		}
		
		String sysdate = selectDate("");
		//판매내역 저장
		if(rtnCd != null && rtnCd.equals("1")) {
			// Y-> 지정가 N-> 시장가
			if (vo.getApntPhsYn().equals("Y")) {
				
				SendMsgVO smvo = new SendMsgVO();
				smvo.setUserEmail(vo.getUserEmail());
				smvo.setSendYn("N");
				smvo.setCmmCd("CMMC00000000198");
				smvo.setMsg(sysdate + "|" + vo.getClientCd() + "|" + makeCoinNm(vo.getSellCurcyCd()) + "|" + StringUtils.toNumFomat(Integer.parseInt(vo.getPhsPrc())) + "|"+ vo.getPhsAmt());
				pushDAO.PR_INSUPT_SND_MSG(smvo);
			}
		}
		
		//체결내역저장
		String rntMsg = (String) map.get("RNT_MSG");
		String orderNo = rntMsg.substring(rntMsg.indexOf("CNSL"), rntMsg.indexOf("CNSL") + 19);
		OrderResVO rvo = new OrderResVO();
		rvo.setCnPhsCode(orderNo);
		if(!"".equals(orderNo)) {
			SendMsgVO smvo = new SendMsgVO();
			smvo.setUserEmail(vo.getUserEmail());
			smvo.setSendYn("N");
			smvo.setCmmCd("CMMC00000000182");
			smvo.setMsg(orderNo + "|" + vo.getClientCd());
			pushDAO.PR_INSUPT_SND_MSG(smvo);
		}
		
	}
	
	public void insAutoCoinTrade(AutoCoinTradeVO vo) throws Exception {
		tradeDAO.insAutoCoinTrade(vo);
	}
	
	public void delAutoCoinTrade(AutoCoinTradeVO vo) throws Exception {
		tradeDAO.delAutoCoinTrade(vo);
	}
	
	public AutoCoinTradeResVO getAutoCoinTrade(AutoCoinTradeVO vo) throws Exception {
		return tradeDAO.getAutoCoinTrade(vo);
	}
	
	public List<CoinListVO> getCoinList() throws Exception {
		return tradeDAO.getCoinList();
	}
	
	
	public String makeCoinNm(String curcyCd) throws Exception {
		
		List<CoinListVO> list = getCoinList();
		
		String curcyNm = "";
		
		for(CoinListVO vo : list) {
			if(curcyCd.equals(vo.getCurcyCd())) {
				curcyNm =  vo.getCurcyNm();
			}
		}
		
		return curcyNm;
	}

    public List<CmeTradeReqVO> selectLowPriceSellList(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.selectLowPriceSellList(vo);
    }

    public List<CmeTradeReqVO> selectHighPriceBuyList(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.selectHighPriceBuyList(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public List<CmeTradeReqVO> selectLowPriceSellListETH(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.selectLowPriceSellList(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public List<CmeTradeReqVO> selectHighPriceBuyListETH(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.selectHighPriceBuyList(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public List<CmeTradeReqVO> selectLowPriceSellListUSDT(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.selectLowPriceSellList(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public List<CmeTradeReqVO> selectHighPriceBuyListUSDT(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.selectHighPriceBuyList(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public List<CmeTradeReqVO> selectLowPriceSellListBTC(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.selectLowPriceSellList(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public List<CmeTradeReqVO> selectHighPriceBuyListBTC(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.selectHighPriceBuyList(vo);
    }

    public String isPricePer(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.isPricePer(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public String isPricePerETH(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.isPricePer(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public String isPricePerUSDT(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.isPricePer(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public String isPricePerBTC(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.isPricePer(vo);
    }

    public CoinMinMaxAmtVO getMaxMinAmt(CoinMinMaxAmtVO vo) throws Exception {
        return tradeDAO.getMaxMinAmt(vo);
    }

    public List<TradePlusVO> getPlusList(TradeParamVO vo) throws Exception {
        return tradeDAO.getPlusList(vo);
    }

    public List<TradePlusVO> getTimeList(TradeParamVO vo) throws Exception {
        return tradeDAO.getTimeList(vo);
    }

    public ExRateVO getExRate(DepositVO vo) throws Exception {
		return tradeDAO.getExRate(vo);
	}

    public String getSellCancelOrder(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.getSellCancelOrder(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public String getSellCancelOrderETH(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.getSellCancelOrder(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public String getSellCancelOrderUSDT(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.getSellCancelOrder(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public String getSellCancelOrderBTC(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.getSellCancelOrder(vo);
    }

    public String getBuyCancelOrder(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.getBuyCancelOrder(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public String getBuyCancelOrderETH(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.getBuyCancelOrder(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public String getBuyCancelOrderBTC(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.getBuyCancelOrder(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public String getBuyCancelOrderUSDT(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.getBuyCancelOrder(vo);
    }

    public String getRandomSellCancelOrder(String cnKndCd) throws Exception {
        return tradeDAO.getRandomSellCancelOrder(cnKndCd);
    }

    public String getRandomBuyCancelOrder(String cnKndCd) throws Exception {
        return tradeDAO.getRandomBuyCancelOrder(cnKndCd);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public CmeTradeReqVO orderMQETH(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.orderMQ(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public List<NotConListVO> notConListETH(TradeParamVO vo) throws Exception {
        // TODO Auto-generated method stub
        return tradeDAO.notConList(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public List<TradeListVO> selectTradeListETH(TradeParamVO vo) throws Exception {
        return tradeDAO.selectTradeList(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public List<CmeTradeResVO> tradeHistoryETH(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.tradeHistory(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public int tradeHistoryCntETH(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.tradeHistoryCnt(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public List<WaitTradeListVO> waitBuyListETH(TradeParamVO vo) throws Exception{
        // TODO Auto-generated method stub
        return tradeDAO.waitBuyList(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public List<WaitTradeListVO> waitSellListETH(TradeParamVO vo) throws Exception{
        // TODO Auto-generated method stub
        return tradeDAO.waitSellList(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public List<CompTradeListVO> compTradeListETH(TradeParamVO vo) throws Exception{
        return tradeDAO.compTradeList(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public CmeTradeReqVO selectOrderETH(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.selectOrder(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public CmeResultVO orderCalcelETH(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.orderCalcel(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public CmeTradeSellMenuViewResVO sellMenuViewETH(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.sellMenuView(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public BuyMenuVO buyMenuViewETH(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.buyMenuView(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public RecCoinViewVO recCoinViewETH(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.recCoinView(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public CmeTradeReqVO orderMQUSDT(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.orderMQ(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public List<NotConListVO> notConListUSDT(TradeParamVO vo) throws Exception {
        return tradeDAO.notConList(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public List<TradeListVO> selectTradeListUSDT(TradeParamVO vo) throws Exception {
        return tradeDAO.selectTradeList(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public List<CmeTradeResVO> tradeHistoryUSDT(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.tradeHistory(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public int tradeHistoryCntUSDT(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.tradeHistoryCnt(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public List<WaitTradeListVO> waitBuyListUSDT(TradeParamVO vo) throws Exception{
        return tradeDAO.waitBuyList(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public List<WaitTradeListVO> waitSellListUSDT(TradeParamVO vo) throws Exception{
        return tradeDAO.waitSellList(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public List<CompTradeListVO> compTradeListUSDT(TradeParamVO vo) throws Exception{
        return tradeDAO.compTradeList(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public CmeTradeReqVO selectOrderUSDT(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.selectOrder(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public CmeResultVO orderCalcelUSDT(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.orderCalcel(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public CmeTradeSellMenuViewResVO sellMenuViewUSDT(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.sellMenuView(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public BuyMenuVO buyMenuViewUSDT(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.buyMenuView(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public RecCoinViewVO recCoinViewUSDT(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.recCoinView(vo);
    }







    @CommonDataSource(DataSourceType.MKBTC)
    public CmeTradeReqVO orderMQBTC(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.orderMQ(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public List<NotConListVO> notConListBTC(TradeParamVO vo) throws Exception {
        return tradeDAO.notConList(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public List<TradeListVO> selectTradeListBTC(TradeParamVO vo) throws Exception {
        return tradeDAO.selectTradeList(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public List<CmeTradeResVO> tradeHistoryBTC(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.tradeHistory(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public int tradeHistoryCntBTC(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.tradeHistoryCnt(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public List<WaitTradeListVO> waitBuyListBTC(TradeParamVO vo) throws Exception{
        return tradeDAO.waitBuyList(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public List<WaitTradeListVO> waitSellListBTC(TradeParamVO vo) throws Exception{
        return tradeDAO.waitSellList(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public List<CompTradeListVO> compTradeListBTC(TradeParamVO vo) throws Exception{
        return tradeDAO.compTradeList(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public CmeTradeReqVO selectOrderBTC(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.selectOrder(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public CmeResultVO orderCalcelBTC(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.orderCalcel(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public CmeTradeSellMenuViewResVO sellMenuViewBTC(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.sellMenuView(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public BuyMenuVO buyMenuViewBTC(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.buyMenuView(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public RecCoinViewVO recCoinViewBTC(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.recCoinView(vo);
    }











    @Override
    public CmeTradeReqVO orderMQ(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.orderMQ(vo);
    }

    @Override
    public List<NotConListVO> notConList(TradeParamVO vo) throws Exception {
        return tradeDAO.notConList(vo);
    }

    @Override
    public List<TradeListVO> selectTradeList(TradeParamVO vo) throws Exception {
        return tradeDAO.selectTradeList(vo);
    }

    @Override
    public List<CmeTradeResVO> tradeHistory(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.tradeHistory(vo);
    }

    @Override
    public int tradeHistoryCnt(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.tradeHistoryCnt(vo);
    }

    @Override
    public List<WaitTradeListVO> waitBuyList(TradeParamVO vo) throws Exception {
	    return tradeDAO.waitBuyList(vo);
    }

    @Override
    public List<WaitTradeListVO> waitSellList(TradeParamVO vo) throws Exception {
	    return tradeDAO.waitSellList(vo);
    }

    @Override
    public List<CompTradeListVO> compTradeList(TradeParamVO vo) throws Exception{
        return tradeDAO.compTradeList(vo);
    }

    @Override
    public CmeTradeReqVO selectOrder(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.selectOrder(vo);
    }

    @Override
    public CmeResultVO orderCalcel(CmeTradeReqVO vo) throws Exception{
        return tradeDAO.orderCalcel(vo);
    }

    @Override
    public CmeTradeSellMenuViewResVO sellMenuView(CmeTradeReqVO vo) throws Exception{
	    return tradeDAO.sellMenuView(vo);
    }

    @Override
    public BuyMenuVO buyMenuView(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.buyMenuView(vo);
    }

    @Override
    public RecCoinViewVO recCoinView(CmeTradeReqVO vo) throws Exception {
        return tradeDAO.recCoinView(vo);
    }

    @Override
    public List<CoinCoreInfoVO> getCoinCoreInfoList() throws Exception {
        return tradeDAO.getCoinCoreInfoList();
    }

    @Override
	public String realKRWPrice(String curcyCd) throws Exception{
		return tradeDAO.realKRWPrice(curcyCd);
	}

	@Override
	public String getTodaySellAmtKRW(TradeParamVO vo) throws Exception{
		return tradeDAO.getTodaySellAmt(vo);
	}

	@Override
	public String getTodaySellAmt(TradeParamVO vo) throws Exception{
		return tradeDAO.getTodaySellAmt(vo);
	}

	@CommonDataSource(DataSourceType.MKETH)
	public String getTodaySellAmtETH(TradeParamVO vo) throws Exception{
		return tradeDAO.getTodaySellAmt(vo);
	}

	@CommonDataSource(DataSourceType.MKBTC)
	public String getTodaySellAmtBTC(TradeParamVO vo) throws Exception{
		return tradeDAO.getTodaySellAmt(vo);
	}

	@CommonDataSource(DataSourceType.MKUSDT)
	public String getTodaySellAmtUSDT(TradeParamVO vo) throws Exception{
		return tradeDAO.getTodaySellAmt(vo);
	}

	@Override
	public String getTodayBuyAmt(TradeParamVO vo) throws Exception{
		return tradeDAO.getTodayBuyAmt(vo);
	}

	@CommonDataSource(DataSourceType.MKETH)
	public String getTodayBuyAmtETH(TradeParamVO vo) throws Exception{
		return tradeDAO.getTodayBuyAmt(vo);
	}

	@CommonDataSource(DataSourceType.MKBTC)
	public String getTodayBuyAmtBTC(TradeParamVO vo) throws Exception{
		return tradeDAO.getTodayBuyAmt(vo);
	}

	@CommonDataSource(DataSourceType.MKUSDT)
	public String getTodayBuyAmtUSDT(TradeParamVO vo) throws Exception{
		return tradeDAO.getTodayBuyAmt(vo);
	}

	public CoinMinMaxVO selectCoinTradeCheck(CmeTradeReqVO vo) throws Exception{
		return tradeDAO.selectCoinTradeCheck(vo);
	}

	public int getSellNoChk(String orderNo) throws Exception{
		return tradeDAO.getSellNoChk(orderNo);
	}

	@CommonDataSource(DataSourceType.MKETH)
	public int getSellNoChkETH(String orderNo) throws Exception{
		return tradeDAO.getSellNoChk(orderNo);
	}

	@CommonDataSource(DataSourceType.MKBTC)
	public int getSellNoChkBTC(String orderNo) throws Exception{
		return tradeDAO.getSellNoChk(orderNo);
	}

	@CommonDataSource(DataSourceType.MKUSDT)
	public int getSellNoChkUSDT(String orderNo) throws Exception{
		return tradeDAO.getSellNoChk(orderNo);
	}

	public int getBuyNoChk(String orderNo) throws Exception{
		return tradeDAO.getBuyNoChk(orderNo);
	}

	@CommonDataSource(DataSourceType.MKETH)
	public int getBuyNoChkETH(String orderNo) throws Exception{
		return tradeDAO.getBuyNoChk(orderNo);
	}

	@CommonDataSource(DataSourceType.MKBTC)
	public int getBuyNoChkBTC(String orderNo) throws Exception{
		return tradeDAO.getBuyNoChk(orderNo);
	}

	@CommonDataSource(DataSourceType.MKUSDT)
	public int getBuyNoChkUSDT(String orderNo) throws Exception{
		return tradeDAO.getBuyNoChk(orderNo);
	}

	@Override
	public List<FreeWithdrawVO> getFreeWithdraw(CmeTradeReqVO vo) throws Exception {
		return tradeDAO.getFreeWithdraw(vo);
	}

	@Override
	public List<FeeSaleVO> getFeeSale(CmeTradeReqVO vo) throws Exception {
		return tradeDAO.getFeeSale(vo);
	}

	@Override
	public List<CoinTradePrcVO> getCoinTradePrc() throws Exception {
		return tradeDAO.getCoinTradePrc();
	}

	//마켓별 시세정보와 호가단위를 가지고 메모리에저장
	private final Map<String, String> coinTradePrcs = new HashMap<String, String>();
	private long coinArcLoadTime;
    private final long coinArcCacheDuration = 600 * 1000L; // 설정시간
    @Override
    public String findCoinTradePrc(String key) throws Exception {
        long now = System.currentTimeMillis();

        if (coinTradePrcs.isEmpty() || now - coinArcLoadTime > coinArcCacheDuration){
            synchronized (coinTradePrcs) {
                if (coinTradePrcs.isEmpty() || now - coinArcLoadTime > coinArcCacheDuration){
                    Map<String, String> map = new HashMap<String, String>();
                    List<CoinTradePrcVO> list = tradeDAO.getCoinTradePrc();
                    for(int i=0; i < list.size() ;i++) {
                        System.out.println("cnKndCd : " + list.get(i).getCnKndCd()+ " // coinNm : " + list.get(i).getCnKndNm() + " // cnPrice : " + list.get(i).getCoinPrc() + " // krw : " + list.get(i).getKrw() + " // btc : " + list.get(i).getBtc() + " // eth : " + list.get(i).getEth() +  " // usdt : " + list.get(i).getUsdt() );
                        String coinArc = "cnKndCd : " + list.get(i).getCnKndCd()+ " // coinNm : " + list.get(i).getCnKndNm() + " // cnPrice : " + list.get(i).getCoinPrc() + " // krw : " + list.get(i).getKrw() + " // btc : " + list.get(i).getBtc() + " // eth : " + list.get(i).getEth() +  " // usdt : " + list.get(i).getUsdt();
                        map.put(list.get(i).getCnKndNm(), coinArc );
                    }

                    coinTradePrcs.clear();
                    coinTradePrcs.putAll(map);
                    coinArcLoadTime = now;
                }
            }
        }
        return coinTradePrcs.get(key);
    }

    @Override
    public void invalidateCoinTradePrc() {
        coinTradePrcs.clear();
    }

    @Override
    public List<ArcMarketVO> getArcMarket(UserTradeCheckVO vo) throws Exception {
        return tradeDAO.getArcMarket(vo);
    }

    public String getCnPrice(String cnKndCd) throws Exception{
		return tradeDAO.getCnPrc(cnKndCd);
	}

	@CommonDataSource(DataSourceType.MKETH)
	public String getCnPriceETH(String cnKndCd) throws Exception{
		return tradeDAO.getCnPrc(cnKndCd);
	}

	@CommonDataSource(DataSourceType.MKBTC)
	public String getCnPriceBTC(String cnKndCd) throws Exception{
		return tradeDAO.getCnPrc(cnKndCd);
	}

	@CommonDataSource(DataSourceType.MKUSDT)
	public String getCnPriceUSDT(String cnKndCd) throws Exception{
		return tradeDAO.getCnPrc(cnKndCd);
	}

	@Override
	public String getCmmNm() throws Exception {
		return tradeDAO.getCmmNm();
	}
}
