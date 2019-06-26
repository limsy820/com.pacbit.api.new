package com.bitkrx.api.publicApi.service.impl;

import com.bitkrx.api.publicApi.dao.PublicDAO;
import com.bitkrx.api.publicApi.service.PublicService;
import com.bitkrx.api.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PublicServiceImpl implements PublicService {

    @Autowired
    PublicDAO publicDAO;

    @Autowired
    TradeService tradeService;

    @Override
    public Map<String, Object> selectPublicCurrencyInfo(String currency) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map =  publicDAO.selectPublicCurrencyInfo(currency);

        map.put("timestamp", new Date().getTime());
        result.put("result_code", "000");
        result.put("data", map);

        return result;
    }

    @Override
    public Map<String, Object> selectPublicOrderCurrencyInfo(String currency) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list =  publicDAO.selectPublicOrderCurrencyInfo(currency);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("timestamp", new Date().getTime());
        map.put("order_currency", currency);
        map.put("payment_currency", tradeService.getCmmNm());/*"KRW");*/
        map.put("list", list);
        result.put("result_code", "000");
        result.put("data", map);

        return result;
    }

    @Override
    public Map<String, Object> selectPublicOrderCompleteInfo(String currency) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list =  publicDAO.selectPublicOrderCompleteInfo(currency);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("list", list);
        result.put("result_code", "000");
        result.put("data", map);

        return result;
    }
}
