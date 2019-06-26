package com.bitkrx.api.extraSvc.service;

import com.bitkrx.api.extraSvc.vo.AutoTradeServiceVO;

public interface ExtraService {

    AutoTradeServiceVO getAutoTradeService(String userEmail) throws Exception;

    AutoTradeServiceVO insAutoTradeService(AutoTradeServiceVO vo) throws Exception;
}
