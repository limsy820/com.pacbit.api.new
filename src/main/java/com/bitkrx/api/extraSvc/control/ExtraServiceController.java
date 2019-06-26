package com.bitkrx.api.extraSvc.control;

import com.bitkrx.api.common.service.CoinInfoService;
import com.bitkrx.api.common.vo.CoinPayVO;
import com.bitkrx.api.extraSvc.service.ExtraService;
import com.bitkrx.api.extraSvc.vo.AutoTradeServiceVO;
import com.bitkrx.config.CmeResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping( value = "/bt/extra")
public class ExtraServiceController {

    @Autowired
    ExtraService extraService;

    @Autowired
    CoinInfoService coinInfoService;

    @RequestMapping(value="/selectAutoTrade.dm")
    public @ResponseBody
    CmeResultVO selectAutoTrade(String userEmail, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();

        resultVO.setData(extraService.getAutoTradeService(userEmail));
        resultVO.setResultStrCode("000");

        return resultVO;
    }

    @RequestMapping(value="/insAutoTradeService.dm")
    public @ResponseBody
    CmeResultVO insAutoTradeService(AutoTradeServiceVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();

        CoinPayVO payVO = new CoinPayVO();
        payVO.setCnKndCd(vo.getCnKndCd());
        List<CoinPayVO> payList = coinInfoService.getCoinPayInfo(payVO);

        for(CoinPayVO pvo : payList) {
            vo.setPayAmt(pvo.getPayAmt());
        }
        if(!"".equals(vo.getPayAmt()) && !"0".equals(vo.getPayAmt()) && Double.parseDouble(vo.getPayAmt()) > 0) {
            vo = extraService.insAutoTradeService(vo);
        }

        try {
            List list = (List) vo.getRESULT();
            Map map = (Map) list.get(0);
            String rtnCd = (String) map.get("RTN_CD");
            if (rtnCd != null && rtnCd.equals("1")) {
                resultVO.setResultMsg("자동거래 서비스 등록 성공");
                resultVO.setResultStrCode("000");
            }
        } catch (Exception e) {

        }


        return resultVO;
    }
}
