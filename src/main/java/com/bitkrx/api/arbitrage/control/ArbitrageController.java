package com.bitkrx.api.arbitrage.control;



import com.bitkrx.api.arbitrage.service.ArbitrageService;
import com.bitkrx.api.arbitrage.vo.ArbitrageVO;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping (value = "/bt/arbitrage")
public class ArbitrageController extends CmeDefaultExtendController {

    @Autowired
    ArbitrageService arbitrageService;

    //아비트리지 신청
    @RequestMapping(value="/insService.dm")
    public @ResponseBody
    CmeResultVO insAutoTradeService(ArbitrageVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        vo.setUserEmail("limsy820@cmesoft.co.kr");
        CmeResultVO res = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultMap.put("failCd" , "");
        res.setResultStrCode("000");

//        ArbitrageVO chk = arbitrageService.getArbitrageInfo(vo);
//        신청된 사람인지 아닌지 판단
//        if(null == chk){
//            아닌사람이면
//            부가서비스 테이블 insert
//            아비트리 서버로 회원 정보를 보낸다 (내가 지금 무슨거래소 인지 담아서 보내준다) Z
//
//        }else{
//        신청이 된사람이면 return시킨다
//            res.setResultMsg("이미 arbitrage를 설정한 회원");
//            resultMap.put("failCd" , "999");
//        }

        res.setData(resultMap);
        return res;
    }


    //회원가입 여부 판단 하는거  , 내가 지금 무슨거래소인지 담아서 보낸준다


    //회원가입



    //=======================================아비트리지서버===============================================
    //회원정보를 받는다
    //신청이 들어온 거래소를 제외한 거래소에 회원가입여부 판단 api 호출
    //회원가입이 안되어있다고 리턴된 거래소에
    //회원가입 api 호출

}
