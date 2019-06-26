//package com.bitkrx.api.init;
//
//import com.bitkrx.api.common.service.CoinInfoService;
//import com.bitkrx.api.common.vo.CommonExchgInfoVO;
//import com.bitkrx.config.util.CmmCdConstant;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.WebApplicationInitializer;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//
//public class WebInitializer implements WebApplicationInitializer {
//
////    @Autowired
////    CoinInfoService coinInfoService;
////
////    @Override
////    public void onStartup(ServletContext servletContext) throws ServletException {
////        commonInfo();
////
////        System.out.println("실행시 공통화 호출");
//    }
//
////    public CommonExchgInfoVO commonInfo() {
////        CommonExchgInfoVO comvo = new CommonExchgInfoVO();
////        try {
////            comvo = coinInfoService.commonExchgInfo();
////
//////            CmmCdConstant.EXCHANGE_BI = comvo.getBi();
////            CmmCdConstant.EXCHANGE_NAME = comvo.getExchgName();
////            CmmCdConstant.PUSH_CONDITION = comvo.getExchgName();
//////			FOTTER_MAIL = comvo.getCicEmail();
//////			WEB_DOMAIN_URL = comvo.getExchgUrl();
////
////
////        } catch (Exception e){
////            System.out.println("공통화 변수선언중 오류");
////            e.printStackTrace();
////        }
////
////        return comvo;
////    }
//}
