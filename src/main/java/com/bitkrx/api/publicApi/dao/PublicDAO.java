package com.bitkrx.api.publicApi.dao;

import com.bitkrx.config.dao.CmeComAbstractDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PublicDAO extends CmeComAbstractDAO {

    public Map<String, Object> selectPublicCurrencyInfo(String currency) throws Exception {

        return (Map<String, Object>) selectByPk("publicDAO.selectPublicCurrencyInfo", currency);
    }

    public List<Map<String, Object>> selectPublicOrderCurrencyInfo(String currency) throws Exception {

        return list("publicDAO.selectPublicOrderCurrencyInfo", currency);
    }

    public List<Map<String, Object>> selectPublicOrderCompleteInfo(String currency) throws Exception {

        return list("publicDAO.selectPublicOrderCompleteInfo", currency);
    }
}
