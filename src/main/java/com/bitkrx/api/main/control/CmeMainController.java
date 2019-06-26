package com.bitkrx.api.main.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitkrx.config.dbinfo.DataContextHolder;
import com.bitkrx.config.exception.CmeApplicationException;
import com.bitkrx.config.util.RabbitmqClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitkrx.api.auth.dao.LoginDAO;
import com.bitkrx.api.auth.service.RTPService;
import com.bitkrx.api.mail.service.impl.MailServiceImpl;
import com.bitkrx.api.main.vo.CmeBeforeSellListResVO;
import com.bitkrx.api.main.vo.CmeCoinBeforeBuyListResVO;
import com.bitkrx.api.main.vo.CmeCoinPriceChartReqVO;
import com.bitkrx.api.main.vo.CmeOrderListResVO;
import com.bitkrx.api.main.vo.CmeRealTimeCoinPriceReqVO;
import com.bitkrx.api.main.vo.CmeRealTimeCoinPriceResVO;
import com.bitkrx.api.main.vo.CmeRealTimeExchangePriceResVO;
import com.bitkrx.api.main.vo.CmeUserAssetReqVO;
import com.bitkrx.api.main.vo.CmeUserAssetResVO;
import com.bitkrx.api.main.vo.beforeListVO;
import com.bitkrx.api.sample.service.SampleService;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.logging.CmeCommonLogger;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.util.ServerTrans;
import com.bitkrx.config.util.globalQuoUtil;
import com.bitkrx.config.vo.QuotationVO;
import com.bitkrx.config.vo.SendInfoVO;
import com.bitkrx.core.util.RtpUtils;

import org.json.simple.JSONObject;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/bt")
public class CmeMainController extends CmeDefaultExtendController{

	protected CmeCommonLogger log = new CmeCommonLogger(this.getClass());
	
	private ServerTrans serverTrans = ServerTrans.getinstance();
	
	protected RtpUtils oUtil = RtpUtils.getinstance();
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	TradeService tradeService;
	
	@Autowired
	SampleService sampleService;
	
	@Autowired
	RTPService rService;
	
	@Autowired 
	MailServiceImpl mailServiceImpl;
	
	@Autowired
	SmsApiService smsApiService;
	
	@Autowired
	LoginDAO loginDAO;

	@RequestMapping(value = "/index.dm")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        model.addAttribute("exchangeNm", CmmCdConstant.EXCHANGE_NAME);
		return new ModelAndView("index");
	}

	/**
	 * 
	 * @Method Name  : userAsset
	 * @작성일   : 2017. 11. 1. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 : 사용자 자산현황
	 * @서비스ID : IF-EX-003
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/asset.dm")
	public @ResponseBody CmeResultVO userAsset(@ModelAttribute CmeUserAssetReqVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//log.ViewErrorLog("자산컨트롤러 진입1 : " + sdf.format(new Date()));
		
		//rService.RTPVertify(request);
		//log.ViewErrorLog("자산컨트롤러 진입2 : " + sdf.format(new Date()));
		
		CmeResultVO res = new CmeResultVO();
		List<CmeUserAssetResVO> list = new ArrayList<CmeUserAssetResVO>();
		JSONObject obj = new JSONObject();
		
		//log.ViewErrorLog("자산컨트롤러 진입3 : " + sdf.format(new Date()));
		
		long totAsset = 0;
		
		if ("".equals(vo.getUserEmail())) {
			res.setResultDescMsg("사용자ID가 없습니다.");
			obj.put("totAsset", totAsset);
			obj.put("list", list);
			res.setData(obj);
			return res;
		}
		
		//log.ViewErrorLog("자산컨트롤러 진입4 : " + sdf.format(new Date()));
		
		res.setResultStrCode("000");
		
		//log.ViewErrorLog("자산쿼리조회시작 : " + sdf.format(new Date()));
		List<CmeUserAssetResVO> assetList = userService.userCoinList(vo);
		//log.ViewErrorLog("자산쿼리조회끝 : " + sdf.format(new Date()));
		/*vo.setCurcyCd(CmmCdConstant.KRW_CD);
		assetList.add(userService.assetKrw(vo));
		vo.setCurcyCd(CmmCdConstant.POINT_CD);
		assetList.add(userService.assetPoint(vo));*/
		
		for(CmeUserAssetResVO uvo : assetList) {
			totAsset += (long)Math.round(Double.parseDouble(uvo.getKwdPrc())); //자산현황들의 합
		}
		/*for(int i = 0; i <= 5; i++ ) {
			if(i == 0) {//비트코인 자산현황
				vo.setCurcyCd(CmmCdConstant.BTC_CD);
				assetList.add(userService.userAsset(vo)); 
			}
			else if(i==1) { //이더리움 자산현황
				vo.setCurcyCd(CmmCdConstant.ETH_CD);
				assetList.add(userService.userAsset(vo));
			}
			else if(i==2) { //비트코인 캐쉬 자산현황
				vo.setCurcyCd(CmmCdConstant.BCH_CD);
				assetList.add(userService.userAsset(vo));
			}
			else if(i==3) { //리플자산현황
				vo.setCurcyCd(CmmCdConstant.XRP_CD);
				assetList.add(userService.userAsset(vo));
			}
			else if(i==4) { //원화 자산현황
				vo.setCurcyCd(CmmCdConstant.KRW_CD);
				assetList.add(userService.assetKrw(vo));
			}
			else if(i==5) { //포인트 자산현황
				vo.setCurcyCd(CmmCdConstant.POINT_CD);
				assetList.add(userService.assetPoint(vo));
			} 
			totAsset += (long)Math.round(Double.parseDouble(assetList.get(i).getKwdPrc())); //자산현황들의 합
			 
		}*/
		obj.put("list", assetList);
		obj.put("totAsset", totAsset);
		
		res.setData(obj); 
		
		//log.ViewErrorLog("자산컨트롤러 끝 : " + sdf.format(new Date()));
		
		return res;
	}
	
	
	/**
	 * 
	 * @Method Name  : realTimeCoinPrice
	 * @작성일   : 2017. 12. 08
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 실시간 코인 시세
	 * @서비스ID : IF-EX-005
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/realtime/coin/price.dm")
	public @ResponseBody CmeResultVO realTimeCoinPrice(@ModelAttribute CmeRealTimeCoinPriceReqVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		
		CmeResultVO res = new CmeResultVO();
		List<CmeRealTimeCoinPriceResVO> list = new ArrayList<CmeRealTimeCoinPriceResVO>();
		CmeRealTimeCoinPriceResVO result = new CmeRealTimeCoinPriceResVO();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (vo.getTime().equals("")) {
			res.setResultStrCode("-1");
			res.setResultMsg("시간값이 없습니다.");
			res.setData(result);
			return res;
		} else if (CmmCdConstant.REALTIME_12.equals(vo.getTime())) {
			vo.setTime("12");
		} else if (CmmCdConstant.REALTIME_01.equals(vo.getTime())) {
			vo.setTime("1");
		} else {
			vo.setTime("24");
		} 
		
		res.setResultStrCode("000");
		
		list = tradeService.realTimeCoinPrice(vo);
		
		map.put("list", list);
		res.setData(map);
		
		return res;
	}
	
	/**
	 * 
	 * @Method Name  : orderList
	 * @작성일   : 2017. 11. 1. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 구매/판매 주문완료 내역
	 * @서비스ID : IF-EX-009
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/coin/orderList.dm")
	public @ResponseBody CmeResultVO orderList(@ModelAttribute CmeCoinPriceChartReqVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		
		CmeResultVO res = new CmeResultVO();
		List<CmeOrderListResVO> list = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(vo.getCurcyCd().equals("")) {
			res.setResultMsg("통화값이 없습니다.");
			list = new ArrayList<CmeOrderListResVO>();
			map.put("list", list);
			res.setData(map);
			return res;
		} 
		
		int listSize = 10;
		
		listSize = vo.getListSize();
		if(listSize == 0) {
			vo.setListSize(10);
		}
		
		list = tradeService.orderList(vo);
		
		res.setResultStrCode("000");
		map.put("list", list);
		res.setData(map);
		
		return res;
		
	}
	
	
	/**
	 * 
	 * @Method Name  : beforeSellList
	 * @작성일   : 2017. 11. 1. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 판매 미체결 내역
	 * @서비스ID : IF-EX-008
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/coin/beforeSellList.dm")
	public @ResponseBody CmeResultVO beforeSellList(CmeCoinPriceChartReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CmeResultVO res = new CmeResultVO();
		List<CmeBeforeSellListResVO> list = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(vo.getCurcyCd().equals("")) {
			res.setResultMsg("통화값이 없습니다.");
			list = new ArrayList<CmeBeforeSellListResVO>();
			map.put("list", list);
			res.setData(map);
			return res;
		} 
		int listSize = vo.getListSize();
		if(listSize == 0) {
			vo.setListSize(10);
		}
		list = tradeService.beforeSellList(vo);
		
		res.setResultStrCode("000");
		map.put("list", list);
		res.setData(map);
		
		return res;
	}
	
	/**
	 * 
	 * @Method Name  : beforeBuyList
	 * @작성일   : 2017. 11. 1. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 구매 미체결 내역
	 * @서비스ID : IF-EX-007
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/coin/beforeBuyList.dm")
	public @ResponseBody CmeResultVO beforeBuyList(CmeCoinPriceChartReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		CmeResultVO res = new CmeResultVO();
		List<CmeCoinBeforeBuyListResVO> list = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(vo.getCurcyCd().equals("")) {
			res.setResultMsg("통화값이 없습니다.");
			list = new ArrayList<CmeCoinBeforeBuyListResVO>();
			map.put("list", list);
			res.setData(map);
			return res;
		} 
		int listSize = vo.getListSize();
		if(listSize == 0) {
			vo.setListSize(10);
		}
		list = tradeService.beforeBuyList(vo);
		
		res.setResultStrCode("000");
		map.put("list", list);
		res.setData(map);
		
		return res;
	}
	
	/**
	 * 
	 * @Method Name  : beforeBuyList
	 * @작성일   : 2017. 11. 1. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 미체결 내역
	 * @서비스ID :
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/coin/beforeList.dm")
	public @ResponseBody CmeResultVO beforeList(CmeCoinPriceChartReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//rService.RTPVertify(request);
		
		
		CmeResultVO res = new CmeResultVO();
		List<beforeListVO> list = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		/*if(vo.getCurcyCd().equals("")) {
			res.setResultMsg("통화값이 없습니다.");
			list = new ArrayList<beforeListVO>();
			map.put("list", list);
			res.setData(map);
			return res;
		} */
		
		
		int listSize = vo.getListSize();
		if(listSize == 0) {
			vo.setListSize(10);
		}
		
		list = tradeService.beforeList(vo);
		
		res.setResultStrCode("000");
		map.put("list", list);
		res.setData(map);
		
		return res;
	}
	
	/**
	 * 
	 * @Method Name  : realTimeExchangePrice
	 * @작성일   : 2017. 11. 1. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 실시간 거래소 시세
	 * @서비스ID : IF-EX-006
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/realtime/exchange/price.dm")
	public @ResponseBody CmeResultVO realTimeExchangePrice(@ModelAttribute CmeRealTimeCoinPriceReqVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		CmeResultVO res = new CmeResultVO();
		
		/*List<CmeRealTimeExchangePriceResVO> list = new ArrayList<CmeRealTimeExchangePriceResVO>();
		CmeRealTimeExchangePriceResVO result = new CmeRealTimeExchangePriceResVO();
		Map<String, Object> map = new HashMap<String, Object>();
		if (vo.getCurcyCd().equals("")) {
			res.setResultMsg("통화값이 없습니다.");
			map.put("list", list);
			res.setData(map);
			return res;
		}
		
		JSONObject obj = new JSONObject();
		String jsonStr = HttpComLib.httpSendGetAPI("https://api.bithumb.com/public/ticker/ALL");
		JSONParser parser = new JSONParser();      
		
		obj = (JSONObject) parser.parse(jsonStr);
		
		
		for(int i = 0; i < 2; i++ ) {
			result.setExchgNm("빗썸");
			result.setCoinPrc("30000");
			result.setSvcTranAmt("191239810");
			result.setChgPrc("1000");
			result.setVolume("13.2");
			list.add(result);
		}
		
		res.setResultStrCode("000");
		map.put("list", list);
		res.setData(map);*/
		
		/*if("ethereum".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != ""){
            bitfinex = HttpComLib.httpSendGetAPI("https://api.bitfinex.com/v1/pubticker/ETHUSD");
            qVO2.setCoinSymbol("ETH");
         }else if("bitcoin-cash".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != ""){
        	bitfinex = HttpComLib.httpSendGetAPI("https://api.bitfinex.com/v1/pubticker/BCHUSD");
            qVO2.setCoinSymbol("BCH");
         }else if("ripple".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != "") {
        	 bitfinex = HttpComLib.httpSendGetAPI("https://api.bitfinex.com/v1/pubticker/XRPUSD");
            qVO2.setCoinSymbol("XRP");
         }else {
        	 bitfinex = HttpComLib.httpSendGetAPI("https://api.bitfinex.com/v1/pubticker/BTCUSD");
            qVO2.setCoinSymbol("BTC");
         }*/
		
		List<CmeRealTimeExchangePriceResVO> list = new ArrayList<CmeRealTimeExchangePriceResVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (vo.getCurcyCd().equals("")) {
			res.setResultMsg("통화값이 없습니다.");
			map.put("list", list);
			res.setData(map);
			return res;
		}
		
		QuotationVO qvo = new QuotationVO();
		String curcyCd = "";
		if(vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {
			curcyCd = "ethereum";
		} else if(vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) {
			curcyCd = "bitcoin-cash";
		} else if(vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {
			curcyCd = "ripple";
		} 
		qvo.setCoin_id(curcyCd);
		globalQuoUtil g = new globalQuoUtil();
		
		map = g.gloabalQuo(qvo);
		res.setResultStrCode("000");
		res.setData(map);
		return res;
	}
	
	
	
	/**
	 *
	 * @Method Name  : userAsset
	 * @작성일   : 2017. 11. 1.
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 : 사용자 자산현황
	 * @서비스ID : IF-EX-003
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTime.dm")
	public @ResponseBody CmeResultVO getTime(HttpServletRequest request , HttpServletResponse response) throws Exception{

//        insParamLog(request);

        CmeResultVO res = new CmeResultVO();

	    try {


            JSONObject obj = new JSONObject();

            obj.put("sysTime", sampleService.getMilliTime());

            obj.put("sysDate", new SimpleDateFormat("hh:mm:ss").format(new Date()));

            res.setResultStrCode("000");
            res.setData(obj);

            return res;
        } catch (Exception e) {

            res.setResultStrCode("999");
            res.setResultMsg(e.getMessage());
            return res;
            /*if(!isRTP) throw new CmeApplicationException("비정상적인 접근입니다 : "+request.getRequestURI());*/
        }

	}
	
	
	/**
	 * 
	 * @Method Name  : userAsset
	 * @작성일   : 2017. 12. 05 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : RTP 검증
	 * @서비스ID : IF-EX-003
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/vertify.dm")
	public @ResponseBody String vertify(HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		String code = (String)request.getParameter("code");
		String sysTime = (String)request.getParameter("sysTime");
		String res = "";
		if(code != null && !code.equals("")) {
			if(oUtil.vertify(code, sysTime)) {
				res = "000";
			};
		}
		
		return res;
	}
	
	/**
	 * 
	 * @Method Name  : autoTradeSet
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 : 자동거래 요청메일
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/autoTradeSet.dm")
	public @ResponseBody CmeResultVO autoTradeSet(HttpServletRequest request , HttpServletResponse response) throws Exception{
		rService.RTPVertify(request);
		CmeResultVO res = new CmeResultVO();
		Map<String, Object> map = new HashMap<String, Object>();
		
		String userEmail = request.getParameter("userEmail");
		String autoGubun = request.getParameter("autoGubun");
		String curcyCd = request.getParameter("curcyCd");
		
		/*메일발송*//*
        Map <String, Object> model = new HashMap <String, Object> ();
        *//*필수값 : mailTo(받는사람메일주소), clientCd(클라이언트코드)*//*
        model.put("mailTo", userEmail);
        model.put("sysdate", new SimpleDateFormat("MM/dd HH:mm").format(new Date()));
        mailServiceImpl.mailAutoTradeReq(request, model);
        *//*메일발송*/
        
		/*문자발송*/
		SendInfoVO svo = new SendInfoVO();
		/*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
		svo.setMobile_info(loginDAO.getUserMobile(userEmail));
		svo.setAutoGubun(autoGubun);
		svo.setCurcyNm(makeCoinNm(curcyCd));
		svo.setUserEmail(userEmail);
		smsApiService.smsAutoTradeReq(svo);
		/*문자발송*/
        
        
        res.setResultStrCode("000");
        res.setResultMsg("메일성공");
		return res;
	}
	
}
