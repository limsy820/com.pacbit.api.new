package com.bitkrx.api.log.dao;

import com.bitkrx.api.log.vo.ParamLogVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;
import org.springframework.stereotype.Repository;

@Repository
public class LogDAO extends CmeComAbstractDAO {

    public void insParamLog(ParamLogVO vo) throws Exception {
        insert("logDAO.insParamLog", vo);
    }

    public void insSendParamLog(ParamLogVO vo) throws Exception {
        insert("logDAO.insSendParamLog", vo);
    }
}
