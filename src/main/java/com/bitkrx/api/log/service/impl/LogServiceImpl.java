package com.bitkrx.api.log.service.impl;

import com.bitkrx.api.log.dao.LogDAO;
import com.bitkrx.api.log.service.LogService;
import com.bitkrx.api.log.vo.ParamLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogDAO logDAO;

    @Override
    public void insParamLog(ParamLogVO vo) throws Exception {
        logDAO.insParamLog(vo);
    }

    @Override
    public void insSendParamLog(ParamLogVO vo) throws Exception {
        logDAO.insSendParamLog(vo);
    }
}
