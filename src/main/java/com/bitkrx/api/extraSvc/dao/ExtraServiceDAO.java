package com.bitkrx.api.extraSvc.dao;

import com.bitkrx.api.extraSvc.vo.AutoTradeServiceVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;
import org.springframework.stereotype.Repository;

@Repository
public class ExtraServiceDAO extends CmeComAbstractDAO {

    public AutoTradeServiceVO getAutoTradeService(String userEmail) throws Exception {
        return (AutoTradeServiceVO) selectByPk("extraServiceDAO.getAutoTradeService", userEmail);
    }

    public AutoTradeServiceVO insAutoTradeService(AutoTradeServiceVO vo) throws Exception {
        return (AutoTradeServiceVO) convertProc("extraServiceDAO.PR_INSUPT40171020", vo);
    }
}
