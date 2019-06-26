package com.bitkrx.api.chart.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bitkrx.api.chart.vo.CoinChartVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;

@Repository
public class CoinChartDAO extends CmeComAbstractDAO{

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
	@SuppressWarnings("unchecked")
	public String selectCoinChart(CoinChartVO vo) throws Exception{
		return (String) selectByPk("CoinChartDAO.selectCoinChart", vo);
	}

	
	public void insertChartData(CoinChartVO vo) throws Exception{
		insert("CoinChartDAO.insertChartData", vo);
	}
}
