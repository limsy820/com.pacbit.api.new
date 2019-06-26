package com.bitkrx.api.chart.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitkrx.api.chart.service.CoinChartService;
import com.bitkrx.api.chart.vo.CoinChartVO;
import com.bitkrx.api.trade.vo.TradeParamVO;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;

@Controller
@RequestMapping(value = "/bt")
public class CoinChartController extends CmeDefaultExtendController{
	
	@Autowired
	CoinChartService coinChartService;
	
	/**
	 * 
	 * @Method Name  : coinPriceChart
	 * @작성일   : 2017. 11. 1. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 : 코인 시세 차트
	 * @서비스ID : IF-EX-004
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/coin/price/chart.dm")
	public @ResponseBody CmeResultVO coinPriceChart(@ModelAttribute CoinChartVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		
		CmeResultVO res = new CmeResultVO();
		
		//코인종류 , 구분 :1분 
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (vo.getCurcyCd().equals("")) {
			res.setResultStrCode("-1");
			res.setResultMsg("통화값이 없습니다.");
			return res;
		} else if(vo.getTradeTime().equals("")) {
			res.setResultStrCode("-1");
			res.setResultMsg("구분값이 없습니다.");
			return res;
		}
		
		//CMMC00000000241	1분
		//CMMC00000000242	5분
		//CMMC00000000243	10분
		//CMMC00000000244	30분
		//CMMC00000000245	60분
		//CMMC00000000246	1일		
		//CMMC00000000247	1주
		
		if("CMMC00000000241".equals(vo.getTradeTime())) {
			vo.setTradeTime("1");
		} else if("CMMC00000000242".equals(vo.getTradeTime())) {
			vo.setTradeTime("5");
		} else if("CMMC00000000243".equals(vo.getTradeTime())) {
			vo.setTradeTime("10");
		} else if("CMMC00000000244".equals(vo.getTradeTime())) {
			vo.setTradeTime("30");
		} else if("CMMC00000000245".equals(vo.getTradeTime())) {
			vo.setTradeTime("60");
		} else if("CMMC00000000246".equals(vo.getTradeTime())) {
			int day = 60 * 24;
			vo.setTradeTime(Integer.toString(day));
		} else if("CMMC00000000247".equals(vo.getTradeTime())) {
			int week = 60 * 7 * 24;
			vo.setTradeTime(Integer.toString(week));
		} else  {
			res.setResultStrCode("-1");
			res.setResultMsg("구분값이 없습니다.");
			return res;
		}
		
		List<CoinChartVO> list = coinChartService.selectCoinChart(vo);
		
		if(list == null) {
			res.setResultStrCode("-1");
			res.setResultMsg("데이터가 존재하지 않습니다.");
			return res;
		} else {
			res.setResultStrCode("000");
			map.put("list", list);
			res.setData(map);
		}
		
		return res;
	}
}
