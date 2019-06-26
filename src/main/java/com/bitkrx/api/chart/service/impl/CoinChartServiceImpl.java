/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.chart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitkrx.api.chart.dao.CoinChartDAO;
import com.bitkrx.api.chart.service.CoinChartService;
import com.bitkrx.api.chart.vo.CoinChartVO;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.util.CoinUtil;
import com.bitkrx.config.util.ComUtil;
import com.bitkrx.config.util.StringUtils;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.chart.service.impl
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 28.
 */
@Service
public class CoinChartServiceImpl implements CoinChartService{

	CoinUtil coinUtil = CoinUtil.getinstance();
	
	@Autowired
	CoinChartDAO coinChartDAO;
	
	/**
	 * 
	 * @Method Name  : selectCoinChart
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 :
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<CoinChartVO> selectCoinChart(CoinChartVO vo) throws Exception {
		
		String strCoinChart = coinChartDAO.selectCoinChart(vo);
		
		if(strCoinChart == null) {
			return null;
		} else {
			String[] arrCoinChart = strCoinChart.split(","); 
			CoinChartVO iVo = null;
			List<CoinChartVO> list = new ArrayList<CoinChartVO>();
			for(String str : arrCoinChart) {
				String[] _arrCoinChart = str.split(":"); 
				iVo = new CoinChartVO();
				//TRADE_TIME ||':'|| OPEN_VALUE||':'||CLOSE_VALUE||':'||LOW_VALUE||':'||HIGHT_VALUE||':'||TRADEV_ALUE||':'||SUM_MA15_VALUE||':'||SUM_MA50_VALUE;
				iVo.setTradeTime(StringUtils.checkNull(_arrCoinChart[0], "0"));
				iVo.setOpenValue(StringUtils.checkNull(_arrCoinChart[1], "0"));
				iVo.setCloseValue(StringUtils.checkNull(_arrCoinChart[2], "0"));
				iVo.setLowValue(StringUtils.checkNull(_arrCoinChart[3], "0"));
				iVo.setHightValue(StringUtils.checkNull(_arrCoinChart[4], "0"));
				iVo.setTradeValue(StringUtils.checkNull(_arrCoinChart[5], "0"));
				iVo.setMa15Value(StringUtils.checkNull(_arrCoinChart[6], "0"));
				iVo.setMa50Value(StringUtils.checkNull(_arrCoinChart[7], "0"));
				
				list.add(iVo);
			}
			
			return list;
		}
	}

	
	public void insertChartData(CoinChartVO vo) throws Exception{
		vo.setCurcyCd(CmmCdConstant.BTC_CD);
		coinChartDAO.insertChartData(vo);
		vo.setCurcyCd(CmmCdConstant.ETH_CD);
		coinChartDAO.insertChartData(vo);
		vo.setCurcyCd(CmmCdConstant.BCH_CD);
		coinChartDAO.insertChartData(vo);
		vo.setCurcyCd(CmmCdConstant.XRP_CD);
		coinChartDAO.insertChartData(vo);
	}
}
