package com.bitkrx.api.extraSvc.service.impl;

import com.bitkrx.api.extraSvc.dao.ExtraServiceDAO;
import com.bitkrx.api.extraSvc.service.ExtraService;
import com.bitkrx.api.extraSvc.vo.AutoTradeServiceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExtraServiceImpl implements ExtraService {

    @Autowired
    ExtraServiceDAO extraServiceDAO;

    public AutoTradeServiceVO getAutoTradeService(String userEmail) throws Exception {
        return extraServiceDAO.getAutoTradeService(userEmail);
    }

    public AutoTradeServiceVO insAutoTradeService(AutoTradeServiceVO vo) throws Exception {
        return extraServiceDAO.insAutoTradeService(vo);
    }
}
