package com.bitkrx.api.log.service;

import com.bitkrx.api.log.vo.ParamLogVO;

public interface LogService {

    public void insParamLog(ParamLogVO vo) throws Exception;

    public void insSendParamLog(ParamLogVO vo) throws Exception;
}
