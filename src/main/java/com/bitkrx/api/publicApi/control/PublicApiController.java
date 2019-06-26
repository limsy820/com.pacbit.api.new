package com.bitkrx.api.publicApi.control;

import com.bitkrx.api.publicApi.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/public")
public class PublicApiController {

    @Autowired
    PublicService publicService;

    @RequestMapping(value = "/ticker.dm")
    public @ResponseBody
    Map<String, Object> selectPublicCurrencyInfo(String currency) throws Exception {
        if(currency == null || "".equals(currency)) {
            currency = "BTC";
        }
        return publicService.selectPublicCurrencyInfo(currency);

    }

    @RequestMapping(value = "/order.dm")
    public @ResponseBody
    Map<String, Object> selectPublicOrderCurrencyInfo(String currency) throws Exception {
        if(currency == null || "".equals(currency)) {
            currency = "BTC";
        }
        return publicService.selectPublicOrderCurrencyInfo(currency);

    }

    @RequestMapping(value = "/transactions.dm")
    public @ResponseBody
    Map<String, Object> selectPublicOrderCompleteInfo(String currency) throws Exception {
        if(currency == null || "".equals(currency)) {
            currency = "BTC";
        }
        return publicService.selectPublicOrderCompleteInfo(currency);

    }
}
