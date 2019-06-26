/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.config.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitkrx.api.main.vo.CmeBeforeSellListResVO;
import com.bitkrx.api.main.vo.CmeCoinBeforeBuyListResVO;
import com.bitkrx.api.main.vo.CmeCoinPriceChartReqVO;
import com.bitkrx.api.main.vo.CmeOrderListResVO;
import com.bitkrx.api.main.vo.CmeUserAssetReqVO;
import com.bitkrx.api.main.vo.CmeUserAssetResVO;
import com.bitkrx.api.main.vo.beforeListVO;
import com.bitkrx.api.trade.vo.BeforeTradeVO;
import com.bitkrx.api.trade.vo.BuyMenuVO;
import com.bitkrx.api.trade.vo.BuyPosPrcVO;
import com.bitkrx.api.trade.vo.CmeTradeReqVO;
import com.bitkrx.api.trade.vo.CmeTradeResVO;
import com.bitkrx.api.trade.vo.CompTradeListVO;
import com.bitkrx.api.trade.vo.TradeParamVO;
import com.bitkrx.api.trade.vo.WaitTradeListVO;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: security
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 28.
 */
public class CoinUtil {

	private CoinUtil() {};
	
	private static CoinUtil cUtil = null;
    
    public synchronized static CoinUtil getinstance(){
        if(cUtil == null ){
        	cUtil = new CoinUtil();
        }
        return cUtil;
    }
    
    public static int btcPrice = 12470000;
    public static int ethPrice = 552200;
    public static int bchPrice = 1909000;
    public static int xrpPrice = 302;
    
    
        
    /*public Map<String, Object> userAsset(CmeUserAssetReqVO vo) throws Exception{ //임승연 수정
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<CmeUserAssetResVO> list = new ArrayList<CmeUserAssetResVO>();
    	CmeUserAssetResVO result = new CmeUserAssetResVO();
    	int totAsset = 0;
    	for(int i = 1; i <= 6; i++ ) {
			result = new CmeUserAssetResVO();
			if(i == 1) {//비트코인
				result.setCurcyCd(CmmCdConstant.BTC_CD);
				result.setImpCnAmt("1.23658215");
				result.setPosCnAmt("1.00000000");
				result.setImpCnPrc((int)(1.23658215*btcPrice)+"");
				result.setPosCnPrc((int)(1.00000000*btcPrice)+"");
				result.setTotCoinPrc("2.23658215");
				result.setKwdPrc((int)(2.23658215*btcPrice)+"");
				totAsset += (int)(2.23658215*btcPrice);
			} else if(i == 2) {//이더리움
				result.setCurcyCd(CmmCdConstant.ETH_CD);
				result.setImpCnAmt("0.00000000");
				result.setPosCnAmt("15.15246810");
				result.setImpCnPrc("0");
				result.setPosCnPrc((int)(15.15246810*ethPrice)+"");
				result.setTotCoinPrc("15.15246810");
				result.setKwdPrc((int)(15.15246810*ethPrice)+"");
				totAsset += (int)(15.15246810*ethPrice);
			} else if(i == 3) {//비트코인캐시
				result.setCurcyCd(CmmCdConstant.BCH_CD);
				result.setImpCnAmt("0.00000000");
				result.setPosCnAmt("0.00000000");
				result.setImpCnPrc("0");
				result.setPosCnPrc("0");
				result.setTotCoinPrc("0.00000000");
				result.setKwdPrc("0");
			} else if(i == 4) {//리플
				result.setCurcyCd(CmmCdConstant.XRP_CD);
				result.setImpCnAmt("375.00000000");
				result.setPosCnAmt("10523.00000000");
				result.setImpCnPrc((int)(375.00000000*xrpPrice)+"");
				result.setPosCnPrc((int)(10523.00000000*xrpPrice)+"");
				result.setTotCoinPrc("10898.00000000");
				result.setKwdPrc((int)(10898.00000000*xrpPrice)+"");
				totAsset += (int)(10898.00000000*xrpPrice);
			} else if(i == 5) {//원화
				result.setCurcyCd(CmmCdConstant.KRW_CD);
				result.setImpCnPrc("3218500");
				result.setPosCnPrc("10000000");
				result.setTotCoinPrc("13218500");
				result.setKwdPrc("13218500");
				totAsset += 13268500;
			} else if(i == 6) {//포인트
				result.setCurcyCd(CmmCdConstant.POINT_CD);
				result.setImpCnPrc("321850");
				result.setPosCnPrc("1000000");
				result.setTotCoinPrc("1321850");
				result.setKwdPrc("1321850");
				totAsset += 1321850;
			}
			
			list.add(result);
		}
    	
    	map.put("list", list);
    	map.put("totAsset", totAsset);
    	return map;
    }*/
    
    
    public List<CmeCoinBeforeBuyListResVO> beforeBuyList(CmeCoinPriceChartReqVO vo) throws Exception {
		// TODO Auto-generated method stub
    	
    	List<CmeCoinBeforeBuyListResVO> list = new ArrayList<CmeCoinBeforeBuyListResVO>();
    	CmeCoinBeforeBuyListResVO result = new CmeCoinBeforeBuyListResVO();
    	
		int listSize = 10;
		
		listSize = vo.getListSize();
		if(listSize == 0) {
			listSize = 10;
		}
		int coinValue = 0;
		int interval = 0;
    	if(vo.getCurcyCd().equals(CmmCdConstant.BTC_CD)) {
    		coinValue = btcPrice;
    		interval = 1000;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {
    		coinValue = ethPrice;
    		interval = 100;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) {
    		coinValue = bchPrice;
    		interval = 500;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {
    		coinValue = xrpPrice;
    		interval = 1;
    	}
    	DecimalFormat df = new DecimalFormat("0.00000000");
    	
		for(int i = 0; i < listSize; i++ ) {
			result = new CmeCoinBeforeBuyListResVO();
			result.setCnAmt((coinValue - ((i+1) * interval)) + "");
			result.setSvcTranPrc(df.format((1.1332*(i+1))) + "");
			list.add(i, result);
		}
		
		return list;
	}
    
    
    
    public List<CmeBeforeSellListResVO> beforeSellList(CmeCoinPriceChartReqVO vo) throws Exception {
		// TODO Auto-generated method stub
    	
    	List<CmeBeforeSellListResVO> list = new ArrayList<CmeBeforeSellListResVO>();
    	CmeBeforeSellListResVO result = new CmeBeforeSellListResVO();
    	
		int listSize = 10;
		
		listSize = vo.getListSize();
		if(listSize == 0) {
			listSize = 10;
		}
		int coinValue = 0;
		int interval = 0;
    	if(vo.getCurcyCd().equals(CmmCdConstant.BTC_CD)) {
    		coinValue = btcPrice;
    		interval = 1000;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {
    		coinValue = ethPrice;
    		interval = 100;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) {
    		coinValue = bchPrice;
    		interval = 500;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {
    		coinValue = xrpPrice;
    		interval = 1;
    	}
    	
    	DecimalFormat df = new DecimalFormat("0.00000000");

		for(int i = 0; i < listSize; i++ ) {
			result = new CmeBeforeSellListResVO();
			result.setCnAmt((coinValue + ((i+1) * interval)) + "");
			result.setSvcTranPrc(df.format((1.1332*(i+1))) + "");
			list.add(i, result);
		}
		
		return list;
	}
    
    
    public List<beforeListVO> beforeList(CmeCoinPriceChartReqVO vo) throws Exception {
		// TODO Auto-generated method stub
    	
    	List<beforeListVO> list = new ArrayList<beforeListVO>();
    	beforeListVO result = new beforeListVO();
    	
		int listSize = 10;
		
		listSize = vo.getListSize();
		if(listSize == 0) {
			listSize = 10;
		}
		int coinValue = 0;
		int interval = 0;
    	if(vo.getCurcyCd().equals(CmmCdConstant.BTC_CD)) {
    		coinValue = btcPrice;
    		interval = 1000;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {
    		coinValue = ethPrice;
    		interval = 100;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) {
    		coinValue = bchPrice;
    		interval = 500;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {
    		coinValue = xrpPrice;
    		interval = 1;
    	}
    	
    	DecimalFormat df = new DecimalFormat("0.00000000");

		for(int i = 0; i < listSize; i++ ) {
			result = new beforeListVO();
			
			
			//result.setBuyAmt(df.format((0.875*(i+1))) + "");
			result.setBuyCnAmt(df.format((1.1332*(i+1))) + "");
			result.setBuyTranPrc((coinValue - ((i+1) * interval)) + "");
			//result.setSellAmt(df.format((0.5521*(i+1))) + "");
			result.setSellCnAmt(df.format((1.543*(i+1))) + "");
			result.setSellTranPrc((coinValue + ((i+1) * interval)) + "");
			
			list.add(i, result);
		}
		
		return list;
	}
    
    public List<CmeOrderListResVO> orderList(CmeCoinPriceChartReqVO vo) throws Exception {
		// TODO Auto-generated method stub
    	
    	List<CmeOrderListResVO> list = new ArrayList<CmeOrderListResVO>();
    	CmeOrderListResVO result = new CmeOrderListResVO();
    	
		int listSize = 10;
		
		listSize = vo.getListSize();
		if(listSize == 0) {
			listSize = 10;
		}
		int coinValue = 0;
		int interval = 0;
    	if(vo.getCurcyCd().equals(CmmCdConstant.BTC_CD)) {
    		coinValue = btcPrice;
    		interval = 1000;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {
    		coinValue = ethPrice;
    		interval = 100;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) {
    		coinValue = bchPrice;
    		interval = 500;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {
    		coinValue = xrpPrice;
    		interval = 1;
    	}
    	
    	DecimalFormat df = new DecimalFormat("0.00000000");
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	cal.setTime(date);
    	cal.add(Calendar.MINUTE, - vo.getListSize());
    	
		for(int i = 0; i < listSize; i++ ) {
			result = new CmeOrderListResVO();
			
	    	date = cal.getTime();
	    	cal.add(Calendar.MINUTE, +i);
	    	result.setTradeTime(sdf.format(date));
	    	
	    	coinValue += interval;
			result.setCoinPrc(coinValue + "");
			result.setCnAmt(df.format((0.5521*(i+1))));
			result.setTradePrc(df.format(0.5521*(i+1) * (coinValue + interval)));
			if(i/2==0) {
				result.setOrderCd(CmmCdConstant.BUY_CD);
			} else {
				result.setOrderCd(CmmCdConstant.SELL_CD);
			}
			
			list.add(i, result);
		}
		
		return list;
	}
    
    public List<WaitTradeListVO> waitTradeList(TradeParamVO vo) throws Exception {
		// TODO Auto-generated method stub
    	
    	List<WaitTradeListVO> list = new ArrayList<WaitTradeListVO>();
    	WaitTradeListVO result = new WaitTradeListVO();
    	
		int listSize = 10;
		
		listSize = vo.getListSize();
		if(listSize == 0) {
			listSize = 10;
		}
		int coinValue = 0;
		int interval = 0;
    	if(vo.getCurcyCd().equals(CmmCdConstant.BTC_CD)) {
    		coinValue = btcPrice;
    		interval = 1000;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {
    		coinValue = ethPrice;
    		interval = 100;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) {
    		coinValue = bchPrice;
    		interval = 500;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {
    		coinValue = xrpPrice;
    		interval = 1;
    	}
    	
    	DecimalFormat df = new DecimalFormat("0.00000000");
    	
		for(int i = 0; i < listSize; i++ ) {
			result = new WaitTradeListVO();
			result.setTotTradeAmt(df.format((3289*(i+1))));
			result.setTradeAmt(df.format((1952*(i+1))));
			if(vo.getTradeCd().equals(CmmCdConstant.BUY_WAIT_CD)) {
				result.setTradePrc((coinValue - ((i+1) * interval)) + "");
			} else {
				result.setTradePrc((coinValue + ((i+1) * interval)) + "");
			}
			
			list.add(i, result);
		}
		
		return list;
	}
    
    
    public List<CompTradeListVO> compTradeList(TradeParamVO vo) throws Exception {
		// TODO Auto-generated method stub
    	
    	List<CompTradeListVO> list = new ArrayList<CompTradeListVO>();
    	CompTradeListVO result = new CompTradeListVO();
    	
		int listSize = 10;
		
		listSize = vo.getListSize();
		if(listSize == 0) {
			listSize = 10;
		}
		int coinValue = 0;
		int interval = 0;
    	if(vo.getCurcyCd().equals(CmmCdConstant.BTC_CD)) {
    		coinValue = btcPrice;
    		interval = 1000;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {
    		coinValue = ethPrice;
    		interval = 100;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) {
    		coinValue = bchPrice;
    		interval = 500;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {
    		coinValue = xrpPrice;
    		interval = 1;
    	}
    	
    	DecimalFormat df = new DecimalFormat("0.00000000");
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	cal.setTime(date);
    	cal.add(Calendar.MINUTE, - vo.getListSize());
    	
		for(int i = 0; i < listSize; i++ ) {
			result = new CompTradeListVO();
			
	    	date = cal.getTime();
	    	cal.add(Calendar.MINUTE, -i);
	    	result.setTradeTm(sdf.format(date));
			result.setTradeAmt(df.format((1952*(i+1))));
			result.setTradePrc((coinValue - ((i+1) * interval)) + "");
			result.setTotTradePrc((int)((coinValue - ((i+1) * interval)) *(1952*(i+1))) + "");
			if(i/2==0) {
				result.setOrderCd(CmmCdConstant.BUY_CD);
			} else {
				result.setOrderCd(CmmCdConstant.SELL_CD);
			}
			list.add(i, result);
			
		}
		
		return list;
	}
    
    
    
    public List<BeforeTradeVO> beforeTradingList(CmeTradeReqVO vo) throws Exception {
		// TODO Auto-generated method stub
    	
    	List<BeforeTradeVO> list = new ArrayList<BeforeTradeVO>();
    	BeforeTradeVO result = new BeforeTradeVO();
    	
		int listSize = 10;
		
		listSize = vo.getListSize();
		if(listSize == 0) {
			listSize = 10;
		}
		int coinValue = 0;
		int interval = 0;
    	if(vo.getCurcyCd().equals(CmmCdConstant.BTC_CD)) {
    		coinValue = btcPrice;
    		interval = 1000;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {
    		coinValue = ethPrice;
    		interval = 100;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) {
    		coinValue = bchPrice;
    		interval = 500;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {
    		coinValue = xrpPrice;
    		interval = 1;
    	}
    	
    	DecimalFormat df = new DecimalFormat("0.00000000");
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	cal.setTime(date);
    	cal.add(Calendar.MINUTE, 1);
    	
		for(int i = 0; i < listSize; i++ ) {
			result = new BeforeTradeVO();
			
	    	date = cal.getTime();
	    	cal.add(Calendar.MINUTE, -i);
	    	result.setTradeTime(sdf.format(date));
	    	result.setOrderNo("a"+String.format("%06d", i));
	    	result.setCnAmt(df.format((1.91242312*(i+1))));
	    	result.setSvcTranPrc((coinValue - ((i+1) * interval)) + "");
	    	result.setOrderPrc((int)((coinValue - ((i+1) * interval)) *(1.91242312*(i+1))) + "");
	    	
			list.add(i, result);
			
		}
		
		return list;
	}
    
    
    
    public List<CmeTradeResVO> tradeHistory(CmeTradeReqVO vo) throws Exception {
		// TODO Auto-generated method stub
    	
    	List<CmeTradeResVO> list = new ArrayList<CmeTradeResVO>();
    	CmeTradeResVO result = new CmeTradeResVO();
    	
		int listSize = 10;
		
		boolean isRandom = false;
		listSize = vo.getListSize();
		if(listSize == 0) {
			listSize = 10;
		}
		int coinValue = 0;
		int interval = 0;
		if(vo.getCurcyCd().equals(CmmCdConstant.BTC_CD)) {
    		coinValue = btcPrice;
    		interval = 1000;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {
    		coinValue = ethPrice;
    		interval = 100;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) {
    		coinValue = bchPrice;
    		interval = 500;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {
    		coinValue = xrpPrice;
    		interval = 1;
    	}
    	else {
    		isRandom =true;
    	}
    	
    	DecimalFormat df = new DecimalFormat("0.00000000");
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	cal.setTime(date);
    	cal.add(Calendar.MINUTE, 1);
    	String curcyNm = "";
    	
		for(int i = 0; i < listSize; i++ ) {
			result = new CmeTradeResVO();
			
			if(isRandom) {
				switch(i%4) {
					case 0:coinValue = btcPrice;interval = 1000;vo.setCurcyCd(CmmCdConstant.BTC_CD);curcyNm="BTC";break;
					case 1:coinValue = ethPrice;interval = 100;vo.setCurcyCd(CmmCdConstant.ETH_CD);curcyNm="ETH";break;
					case 2:coinValue = bchPrice;interval = 500;vo.setCurcyCd(CmmCdConstant.BCH_CD);curcyNm="BCH";break;
					case 3:coinValue = xrpPrice;interval = 1;vo.setCurcyCd(CmmCdConstant.XRP_CD);curcyNm="XRP";break;
				}
			}
	    	date = cal.getTime();
	    	cal.add(Calendar.MINUTE, -i);
	    	result.setTradeTime(sdf.format(date));
	    	result.setOrderNo("a"+String.format("%06d", i));
	    	result.setCurcyCd(vo.getCurcyCd());
	    	String orderState = CmmCdConstant.BUY_CD;
	    	switch(i%2) {
		    	case 0:orderState = CmmCdConstant.TRADE_COMP;break;
				case 1:orderState = CmmCdConstant.TRADE_WAIT;break;
	    	}
	    	String hisCode = CmmCdConstant.K_DEPOSIT_CD;
	    	String hisStr = "";
	    	switch(i%6) {
		    	case 0:hisCode = CmmCdConstant.K_DEPOSIT_CD;hisStr="충전";break;
				case 1:hisCode = CmmCdConstant.C_DEPOSIT_CD;hisStr="입금";break;
				case 2:hisCode = CmmCdConstant.C_WITHDRAW_CD;hisStr="송금";break;
				case 3:hisCode = CmmCdConstant.K_WITHDRAW_CD;hisStr="출금";break;
				case 4:hisCode = CmmCdConstant.BUY_CD;hisStr="구매";break;
				case 5:hisCode = CmmCdConstant.SELL_CD;hisStr="판매";break;
	    	}
	    	result.setOrderState(orderState);
	    	result.setHisCode(hisCode);
	    	result.setTradeDesc(curcyNm+hisStr+"(시장가)");
	    	result.setCoinPrc((coinValue + ((i+1) * interval)) + "");
	    	result.setCnAmt((int)((coinValue + ((i+1) * interval)) *(1.91242312*(i+1))) + "");
	    	result.setTradeAmt((int)((coinValue + ((i+1) * interval)) *(0.11242312*(i+1))) + "");
	    	result.setTradePrc((coinValue + ((i+1) * interval)) * (int)((coinValue + ((i+1) * interval)) *(0.11242312*(i+1))) + "");
	    	result.setTradeFee(((coinValue + ((i+1) * interval)) * (int)((coinValue + ((i+1) * interval)) *(0.11242312*(i+1)))) * 0.0015 + "");
	    	result.setCompDate(sdf.format(date));
	    	
	    	
			list.add(i, result);
			
		}
		
		return list;
	}
    
    public BuyMenuVO buyMenuView(CmeTradeReqVO vo) throws Exception {
    	
    	int coinValue = 0;
    	if(vo.getCurcyCd().equals(CmmCdConstant.BTC_CD)) {
    		coinValue = btcPrice;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {
    		coinValue = ethPrice;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) {
    		coinValue = bchPrice;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {
    		coinValue = xrpPrice;
    	}
    	BuyMenuVO result = new BuyMenuVO();
    	result.setPrice("5400000");
		result.setMinPrc(coinValue+"");
		result.setPointPrc("51324");
		
		return result;
    }
    
    
    public BuyPosPrcVO maxBuyPrice(CmeTradeReqVO vo) throws Exception {
    	
    	int coinValue = 0;
    	if(vo.getCurcyCd().equals(CmmCdConstant.BTC_CD)) {
    		coinValue = btcPrice;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {
    		coinValue = ethPrice;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) {
    		coinValue = bchPrice;
    	}
    	else if(vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {
    		coinValue = xrpPrice;
    	}
    	
    	BuyPosPrcVO result = new BuyPosPrcVO();
    	result.setPrice("5400000");
    	result.setPointPrc("51324");
    	result.setMaxPrc(coinValue+"");
    	result.setMinPrc((int)(coinValue*0.98)+"");
		
		return result;
    }
}












