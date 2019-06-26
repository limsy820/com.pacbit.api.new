/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.chart.service;

import java.util.List;

import com.bitkrx.api.chart.vo.CoinChartVO;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.chart.service
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 28.
 */
public interface CoinChartService {

	
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
	public List<CoinChartVO> selectCoinChart(CoinChartVO vo) throws Exception;
	
	
	public void insertChartData(CoinChartVO vo) throws Exception;
}
