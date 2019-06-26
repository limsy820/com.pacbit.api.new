package com.bitkrx.api.publicApi.service;

import java.util.Map;

public interface PublicService {

    public Map<String, Object> selectPublicCurrencyInfo(String currency) throws Exception;

    public Map<String, Object> selectPublicOrderCurrencyInfo(String currency) throws Exception;

    public Map<String, Object> selectPublicOrderCompleteInfo(String currency) throws Exception;
}
