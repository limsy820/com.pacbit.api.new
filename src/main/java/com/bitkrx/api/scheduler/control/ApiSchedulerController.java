
/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.scheduler.control;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.bitkrx.config.util.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.bitkrx.api.chart.service.CoinChartService;
import com.bitkrx.api.mail.service.MailApiService;
import com.bitkrx.api.push.service.CmeFcmPushService;
import com.bitkrx.api.sample.service.SampleService;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.trade.vo.CmeTradeReqVO;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;

/**
 * @프로젝트명 : com.bitkrx.api
 * @패키지     : com.bitkrx.api.scheduler.control
 * @클래스명   : com.bitkrx.api
 * @작성자  :  (주)씨엠이소프트 문영민
 * @작성일  : 2017. 12. 14.
 */
@Component
public class ApiSchedulerController extends CmeDefaultExtendController {
    @Autowired
    SampleService sampleService;

    @Autowired
    MailApiService mailApiService;
    @Autowired
    TradeService tradeService;

    @Autowired
    CoinChartService coinChartService;

    @Autowired
    CmeFcmPushService pushService;
    //사용 이유를 모르겠다.
    private String[] users1 =
                   {
//                    "jbd76@naver.com",
                    "banbanguy@naver.com",
//                    "nodo1017@gmail.com",
                    "visop@naver.com",
                    "z01lkss@naver.com"
                   };

    //구매판매 봇 계정
    private String[] users2 =
            {"visop@naver.com",
             "banbanguy@daum.net",
             "ymin2715@naver.com",
             };

    //ADMT 봇 돌리는 계정
    private String[] users3 =
            {"wjddlsdud919@gmail.com",
                    "banbanguy@daum.net"};

    private String[] markets = {"KRW" /*, "ETH" , "BTC" , "USDT"*/};
  /*"visop02@naver.com",
          "z01lkss@nate.com"*/

    /**2017-11-01 최초 생성 : 구매 등록 테스트
     * IF-EX-012a
     * 등록자 : 문영민
     * @return
     * @throws Exception
     */
    //@Scheduled(cron="0 * * * * *")
    public void sellSecretOrder2() throws Exception {

        sellSecretOrder2("ETH");
        sellSecretOrder2("BTC");
        sellSecretOrder2("USDT");


    }

    /**2017-11-01 최초 생성 : 구매/판매 등록 테스트
     * IF-EX-012
     * 등록자 : 문영민
     * @return
     * @throws Exception
     */
//    @Scheduled(cron="40 * * * * *")
    public void orderKRW() throws Exception {
        for (String user : users2) {
            for (int i = 1; i <= 16; i++) {
                Thread.sleep(500);
                buyOrder(i, user, "KRW");
            }
        }

        for (String user : users2) {
            for (int i = 1; i <= 16; i++) {
                Thread.sleep(500);
                sellOrder(i, user, "KRW");
            }
        }

    }

//    @Scheduled(cron="10 2/5 * * * *")
   // @Scheduled(cron="40 * * * * *")
    public void orderETH() throws Exception {

        for (String user : users2) {
            for (int i = 1; i <= 16; i++) {
                Thread.sleep(500);
                buyOrder(i, user, "ETH");
            }
        }

        for (String user : users2) {
            for (int i = 1; i <= 16; i++) {
                Thread.sleep(500);
                sellOrder(i, user, "ETH");
            }
        }

    }

    //@Scheduled(cron="20 3/5 * * * *")
    public void orderUSDT() throws Exception {

        for (String user : users2) {
            for (int i = 1; i <= 8; i++) {
                Thread.sleep(500);
                buyOrder(i, user, "USDT");
            }
        }

        for (String user : users2) {
            for (int i = 1; i <= 8; i++) {
                Thread.sleep(500);
                sellOrder(i, user, "USDT");
            }
        }

    }

//    @Scheduled(cron="30 4/5 * * * *")
    public void orderBTC() throws Exception {

        for (String user : users2) {
            for (int i = 1; i <= 16; i++) {
                Thread.sleep(500);
                buyOrder(i, user, "BTC");
            }
        }

        for (String user : users2) {
            for (int i = 1; i <= 16; i++) {
                Thread.sleep(500);
                sellOrder(i, user, "BTC");
            }
        }

    }

    //@Scheduled(cron="0 20 0/2 * * *")
    public void orderAdmt() throws Exception {

        /*for(int m = 0; m < markets.length; m++){
            for(int i=0; i < users3.length; i++){
                int no = (int) (Math.random() * 50000) + 10000;
                Thread.sleep(no);
                buyAdmtOrder("CMMC00000001467" , users3[i] , markets[m]);
            }
        }*/


        for(int i = 0; i < users2.length; i++){ //봇계정 리스트 3
            for(int m=0; m < markets.length; m++){ //마켓 4
                int no = (int) (Math.random() * 100000) + 10000;
                Thread.sleep(no);
                buyAdmtOrder("CMMC00000001467" , users3[i] , markets[m]);
            }
        }
    }


    //@Scheduled(cron="10 28,52 * * * *")
    //@Scheduled(cron="10 28,51 0/4 * * *")
    public void orderAdmt2() throws Exception {

        for(int m = 0; m < markets.length; m++){ // 4
            for(int i=0; i < users3.length; i++){ // 3
                Thread.sleep(500);
                buyAdmtOrder("CMMC00000001467" , users3[i] , markets[m]);
            }
        }
    }



    //@Scheduled(cron="0 0/2 * * * *")
    public void orderSecretKRW() throws Exception {

        for(int i=0; i < users2.length; i++){
            Thread.sleep(500);
            buySecretOrder("CMMC00000001066" , users2[i] , "KRW");
        }

        for(int i=0; i < users2.length; i++){
            Thread.sleep(500);
            sellSecretOrder("CMMC00000001066" , users2[i] , "KRW");
        }
    }


    //@Scheduled(cron="0 1/2 * * * *")
    public void orderSGCKRW() throws Exception {

        for(int i=0; i < users2.length; i++){
            Thread.sleep(500);
            buySecretOrder("CMMC00000000685" , users2[i] , "KRW");
        }

        for(int i=0; i < users2.length; i++){
            Thread.sleep(500);
            sellSecretOrder("CMMC00000000685" , users2[i] , "KRW");
        }
    }


    //@Scheduled(cron="30 0/2 * * * *")
    public void orderSGCETH() throws Exception {

        for(int i=0; i < users2.length; i++){
            Thread.sleep(500);
            buySecretOrder("CMMC00000000685" , users2[i] , "ETH");
        }

        for(int i=0; i < users2.length; i++){
            Thread.sleep(500);
            sellSecretOrder("CMMC00000000685" , users2[i] , "ETH");
        }
    }



//    @Scheduled(cron="0 * * * * *")
    public void sellBuyCancel() throws Exception {
        System.out.println("planbit SellBuyCanCel!!!!!!!!!!!!!!!!!!!!!!!");
        CmeTradeReqVO vo = new CmeTradeReqVO();
        String[] currencys = {"KRW", "BTC", "ETH"/*, "USDT"*/};

        for(String currency : currencys) {

            for (int i = 1; i <= 16; i++) {
                String coinCode = "";
                String coinNm = "";

                if (i == 1) {
                    coinCode = CmmCdConstant.BCH_CD;
                    coinNm = "BCH";
                } else if (i == 2) {
                    coinCode = CmmCdConstant.QTUM_CD;
                    coinNm = "QTUM";
                } else if (i == 3) {
                    coinCode = CmmCdConstant.BTG_CD;
                    coinNm = "BTG";
                } else if (i == 4) {
                    coinCode = CmmCdConstant.DASH_CD;
                    coinNm = "DASH";
                } else if (i == 5) {
                    coinCode = CmmCdConstant.LTC_CD;
                    coinNm = "LTC";
                } else if (i == 6) {
                    coinCode = CmmCdConstant.BSV_CD;
                    coinNm = "BSV";
                }else if (i == 7) {
                    coinCode = CmmCdConstant.BTC_CD;
                    coinNm = "BTC";
                }else if (i == 8) {
                    coinCode = CmmCdConstant.ETH_CD;
                    coinNm = "ETH";
                }else if (i == 9) {
                    coinCode = CmmCdConstant.XRP_CD;
                    coinNm = "XRP";
                }else if (i == 10) {
                    coinCode = "CMMC00000001507";
                    coinNm = "TRX";
                }else if (i == 11) {
                    coinCode = "CMMC00000001527";
                    coinNm = "XLM";
                }else if (i == 12) {
                    coinCode = "CMMC00000001548";
                    coinNm = "WAVES";
                }else if (i == 13) {
                    coinCode = "CMMC00000001547";
                    coinNm = "POLY";
                }else if (i == 14) {
                    coinCode = "CMMC00000000450";
                    coinNm = "OMG";
                }else if (i == 15) {
                    coinCode = "CMMC00000001587";
                    coinNm = "ICX";
                }else if (i == 16) {
                    coinCode = "CMMC00000001606";
                    coinNm = "XEM";
                }



                UpbitUtil cutil = UpbitUtil.getinstance();
                String cnPrice = cutil.getCoinPrice(coinNm, currency);
                vo.setCurcyCd(coinCode);
                vo.setCnPrc(cnPrice);
                System.out.println("currency(SellBuyCancel) : " + currency + "// coinNm : " + coinNm + "// cnPrice : " + cnPrice);

                if(cnPrice != null) {
                    //String pricePer = tradeService.isPricePer(vo);

                    Method method = getMarketMethod(tradeService, "isPricePer", currency, vo);
                    String pricePer = (String) method.invoke(tradeService, vo);

                    double per = Double.parseDouble(pricePer);
                    System.out.println("pricePer : " + pricePer);

                    double perCnt = 0.005; // 시세보다 차이 날 경우 맞출 %

                    if (per > 1 + perCnt) {
                        //10%이상 현재 시세보다 급격히 내려갔을경우
                        System.out.println((int) (perCnt * 100) + "%이상 현재 시세보다 급격히 내려갔을 경우");
                        method = getMarketMethod(tradeService, "selectHighPriceBuyList", currency, vo);
                        List<CmeTradeReqVO> list = (List<CmeTradeReqVO>) method.invoke(tradeService, vo);

                        // List<CmeTradeReqVO> list = tradeService.selectHighPriceBuyList(vo);
                        for (CmeTradeReqVO ctvo : list) {
                            if (ctvo.getCncl() != null && ctvo.getCncl().equals("")) {
                                ctvo.setRegIp("127.0.0.1");

                                method = getMarketMethod(tradeService, "orderCalcel", currency, ctvo);
                                method.invoke(tradeService, ctvo);
                                //tradeService.orderCalcel(ctvo);
                                //System.out.println(coinCode + "[" + ctvo.getCnPrc() + "] 구매 취소");
                            }
                        }
                        System.out.println("[" + coinNm + "]" + list.size() + "건 구매 취소");
                    } else if (per < 1 - perCnt) {
                        //10%이상 현재 시세보다 급격히 올라갔을 경우
                        System.out.println((int) (perCnt * 100) + "%이상 현재 시세보다 급격히 올라갔을 경우");
                        method = getMarketMethod(tradeService, "selectLowPriceSellList", currency, vo);
                        List<CmeTradeReqVO> list = (List<CmeTradeReqVO>) method.invoke(tradeService, vo);

                        //                    List<CmeTradeReqVO> list = tradeService.selectLowPriceSellList(vo);
                        for (CmeTradeReqVO ctvo : list) {
                            if (ctvo.getCncl() != null && ctvo.getCncl().equals("")) {
                                ctvo.setRegIp("127.0.0.1");
                                method = getMarketMethod(tradeService, "orderCalcel", currency, ctvo);
                                method.invoke(tradeService, ctvo);

                                //tradeService.orderCalcel(ctvo);
                                //System.out.println(coinCode + "[" + ctvo.getCnPrc() + "] 판매 취소");
                            }
                        }
                        System.out.println("[" + coinNm + "]" + list.size() + "건 판매 취소");
                    }

                }

            }

        }

    }



    public void buyOrder(int curcyCd, String userEmail, String mkState) throws Exception {
        try {

            //CoinmarketcapUtil cutil = CoinmarketcapUtil.getinstance();

            UpbitUtil cutil = UpbitUtil.getinstance();

            //if (!isOper()) {

            CmeTradeReqVO vo = new CmeTradeReqVO();

            String cnPrice = "";
            String coinCode = "";
            double phsAmt = 0;
            double random = 0.01;



//            if (curcyCd == 1) {
//                cnPrice = cutil.getCoinPrice("BTC", mkState);
//                coinCode = CmmCdConstant.BTC_CD;
//                phsAmt = Math.random() * 0.1;
//            } else if (curcyCd == 2) {
//                cnPrice = cutil.getCoinPrice("ETH", mkState);
//                coinCode = CmmCdConstant.ETH_CD;
//                phsAmt = Math.random() * 0.1;
//                /*cnPrice = cutil.getCoinPrice("ETH", mkState);
//                coinCode = CmmCdConstant.ETH_CD;
//                if("KRW".equals(mkState)){
//                    phsAmt = Math.random() * 0.1;
//                }else{
//                    phsAmt = Math.random();
//                }*/
//            } else if (curcyCd == 3) {
//                cnPrice = cutil.getCoinPrice("BCH", mkState);
//                coinCode = CmmCdConstant.BCH_CD;
//                phsAmt = Math.random() * 0.1;
//            } else if (curcyCd == 4) {
//                cnPrice = cutil.getCoinPrice("XRP", mkState);
//                coinCode = CmmCdConstant.XRP_CD;
//                phsAmt = 10 + (Math.random() * 10);
//            } else if (curcyCd == 5) {
//                cnPrice = cutil.getCoinPrice("QTUM", mkState);
//                coinCode = CmmCdConstant.QTUM_CD;
//                phsAmt = Math.random() * 0.1;
//            } else if (curcyCd == 6) {
//                cnPrice = cutil.getCoinPrice("BTG", mkState);
//                coinCode = CmmCdConstant.BTG_CD;
//                phsAmt = Math.random() * 0.1;
//            } else if (curcyCd == 7) {
//                cnPrice = cutil.getCoinPrice("DASH", mkState);
//                coinCode = CmmCdConstant.DASH_CD;
//                phsAmt = Math.random() * 0.1;
//            } else if (curcyCd == 8) {
//                cnPrice = cutil.getCoinPrice("LTC", mkState);
//                coinCode = CmmCdConstant.LTC_CD;
//                phsAmt = Math.random() * 0.1;
//            }

            if (curcyCd == 1) {
                cnPrice = cutil.getCoinPrice("BCH", mkState);
                coinCode = CmmCdConstant.BCH_CD;
                phsAmt = Math.random() * 0.01;
            } else if (curcyCd == 2) {
                cnPrice = cutil.getCoinPrice("QTUM", mkState);
                coinCode = CmmCdConstant.QTUM_CD;
                phsAmt = Math.random() * 0.01;
            } else if (curcyCd == 3) {
                cnPrice = cutil.getCoinPrice("BTG", mkState);
                coinCode = CmmCdConstant.BTG_CD;
                phsAmt = Math.random() * 0.01;
            } else if (curcyCd == 4) {
                cnPrice = cutil.getCoinPrice("DASH", mkState);
                coinCode = CmmCdConstant.DASH_CD;
                phsAmt = Math.random() * 0.01;
            } else if (curcyCd == 5) {
                cnPrice = cutil.getCoinPrice("LTC", mkState);
                coinCode = CmmCdConstant.LTC_CD;
                phsAmt = Math.random() * 0.01;
            } else if (curcyCd == 6) {
                cnPrice = cutil.getCoinPrice("BSV", mkState);
                coinCode = CmmCdConstant.BSV_CD;
                //coinCode = "CMMC00000001506";
                phsAmt = Math.random() * 0.01;
            }else if (curcyCd == 7) {
                cnPrice = cutil.getCoinPrice("BTC", mkState);
                coinCode = CmmCdConstant.BTC_CD;
                //coinCode = "CMMC00000001506";
                phsAmt = Math.random() * 0.01;
            }else if (curcyCd == 8) {
                cnPrice = cutil.getCoinPrice("ETH", mkState);
                coinCode = CmmCdConstant.ETH_CD;
                //coinCode = "CMMC00000001506";
                phsAmt = Math.random() * 0.01;
            }else if (curcyCd == 9) {
                cnPrice = cutil.getCoinPrice("XRP", mkState);
                coinCode = CmmCdConstant.XRP_CD;
                //coinCode = "CMMC00000001506";
                phsAmt = 1 + (Math.random() * 10);
            }else if (curcyCd == 10) {
                cnPrice = cutil.getCoinPrice("TRX", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000001507";
            }else if (curcyCd == 11) {
                cnPrice = cutil.getCoinPrice("XLM", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000001527";
            }else if (curcyCd == 12) {
                cnPrice = cutil.getCoinPrice("WAVES", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000001548";
            }else if (curcyCd == 13) {
                cnPrice = cutil.getCoinPrice("POLY", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000001547";
            }else if (curcyCd == 14) {
                cnPrice = cutil.getCoinPrice("OMG", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000000450";
            }else if (curcyCd == 15) {
                cnPrice = cutil.getCoinPrice("ICX", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000001587";
            }else if (curcyCd == 16) {
                cnPrice = cutil.getCoinPrice("XEM", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000001606";
            }
            if (phsAmt <= 0) {
                phsAmt = 0.01;
            }

            if(cnPrice == null) return;

            double price = Double.parseDouble(cnPrice);

            // 시세더높게
          /*  if("KRW".equals(mkState)){
                if(curcyCd == 2){
                    price = price * 1.05;
                    random = 0.01;
                }
            }*/

            vo.setRegIp("127.0.0.1");
            vo.setUserEmail(userEmail);
            vo.setApntPhsCd("CMMC00000000178");
            vo.setApntPhsYn("Y");

            double cnPrc = price;

            vo.setBuyCurcyCd(coinCode);

            cnPrc = price + ((price * random * (Math.random() - 0.75)));

            if(mkState.equals("KRW")) {
                int digit = 1;
                if(cnPrc >= 2000000) {
                    digit = 1000;
                } else if(cnPrc >= 1000000) {
                    digit = 500;
                } else if(cnPrc >= 500000) {
                    digit = 100;
                } else if(cnPrc >= 100000) {
                    digit = 50;
                } else if(cnPrc >= 10000) {
                    digit = 10;
                } else if(cnPrc >= 1000) {
                    digit = 10;
                } else if(cnPrc >= 100) {
                    digit = 5;
                } else if(cnPrc >= 50) {
                    digit = 5;
                } else {
                    digit = 1;
                }

                System.out.println("cnPrc1 : " + cnPrc);
                cnPrc = (int) cnPrc / digit * digit;
                System.out.println("cnPrc2 : " + cnPrc);

            }
            DecimalFormat form = new DecimalFormat("#.########");
            vo.setPhsAmt(form.format(phsAmt));

            if(mkState.equals("KRW")) {
                form = new DecimalFormat("#");
            } else {
                form = new DecimalFormat("#.########");
            }
            vo.setPhsPrc(form.format(cnPrc));

            vo.setUsePrc(form.format(cnPrc * phsAmt));
            vo.setUsePointPrc("0");

            CmeResultVO res = new CmeResultVO();
            if (!"".equals(vo.getUsePrc()) && cnPrc * phsAmt > 0 && cnPrc > 0 && phsAmt > 0) {
                //res = tradeService.buyOrder(vo);

                CmeTradeReqVO reqVO = new CmeTradeReqVO();
                reqVO.setUserEmail(vo.getUserEmail());
                reqVO.setSellBuyCd("B");
                reqVO.setCurcyCd(vo.getBuyCurcyCd());
                reqVO.setAmount(vo.getUsePrc());
                reqVO.setRegIp(vo.getRegIp());

                Method method = getMarketMethod(tradeService, "orderMQ", mkState, reqVO);
                reqVO = (CmeTradeReqVO) method.invoke(tradeService, reqVO);

                //reqVO = tradeService.orderMQ(reqVO);

                List list = (List) reqVO.getRESULT();
                Map map = (Map) list.get(0);
                String rtnCd = (String) map.get("RTN_CD");

                RabbitmqClient client = null;
                RabbitmqClientETH clientEth = null;
                RabbitmqClientUSDT clientUSDT = null;
                RabbitmqClientBTC clientBTC = null;

                if(rtnCd != null && rtnCd.contains("ERC")) {

                    String payCode = "";
                    if(mkState.equals("KRW")) {
                        payCode = "CMMC00000000204";
                    } else if(mkState.equals("ETH")) {
                        payCode = "CMMC00000000206";
                    } else if(mkState.equals("USDT")) {
                        payCode = "CMMC00000001086";
                    } else if(mkState.equals("BTC")) {
                        payCode = "CMMC00000000205";
                    }
                    JSONObject jobj = new JSONObject();
                    jobj.put("clientType", "CMMC00000000221");
                    jobj.put("prePaymentKey", rtnCd);
                    jobj.put("userEmail", vo.getUserEmail());
                    jobj.put("userRegIp", vo.getRegIp());
                    jobj.put("orderType", "B");
                    jobj.put("tradeType", vo.getApntPhsYn());
                    jobj.put("orderCode", vo.getBuyCurcyCd());
                    jobj.put("orderCodeName", makeCoinNm(vo.getBuyCurcyCd()));
                    jobj.put("usePrc", vo.getUsePrc());
                    jobj.put("orderPrc", vo.getPhsPrc());
                    jobj.put("orderCnt", vo.getPhsAmt());
                    jobj.put("payCode", payCode);
                    String value = jobj.toString();

                    //if(!isOper()) RabbitmqClient.setStaChannel();

                    if(!payCode.equals(vo.getBuyCurcyCd())){
                        if("ETH".equals(mkState)) {
                            System.out.println("ETH rabbit");
                            clientEth = RabbitmqClientETH.getinstance();
                            clientEth.sendMsg(value);
                        } else if("USDT".equals(mkState)) {
                            System.out.println("USDT rabbit");
                            clientUSDT = RabbitmqClientUSDT.getinstance();
                            clientUSDT.sendMsg(value);
                        } else if("BTC".equals(mkState)) {
                            System.out.println("BTC rabbit");
                            clientBTC = RabbitmqClientBTC.getinstance();
                            clientBTC.sendMsg(value);
                        } else {
                            System.out.println("KRW rabbit");
                            if(!vo.getBuyCurcyCd().equals(CmmCdConstant.BTC_CD)) {
                                client = RabbitmqClient.getinstance();
                                client.sendMsg(value);
                            }
                        }
                    }

                    //RabbitmqClient client = RabbitmqClient.getinstance();
                    //client.sendMsg(value);
                } else {
                    //주문번호로 상세조회
                    //String orderNo = tradeService.getBuyCancelOrder(vo);
                    method = getMarketMethod(tradeService, "getBuyCancelOrder", mkState, vo);
                    String orderNo = (String) method.invoke(tradeService, vo);

                    if(orderNo != null) {
                        vo.setOrderNo(orderNo);

                        method = getMarketMethod(tradeService, "selectOrder", mkState, vo);
                        CmeTradeReqVO rvo = (CmeTradeReqVO) method.invoke(tradeService, vo);
                        //CmeTradeReqVO rvo = tradeService.selectOrder(vo);

                        rvo.setUserEmail(vo.getUserEmail());
                        rvo.setRegIp(vo.getRegIp());

                        method = getMarketMethod(tradeService, "orderCalcel", mkState, rvo);
                        method.invoke(tradeService, rvo);
                        /*if (rvo.getCncl() != null && rvo.getCncl().equals("")) {
                            tradeService.orderCalcel(rvo);

                        }*/
                    }
                }
            }

            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sellOrder(int curcyCd, String userEmail, String mkState) throws Exception {
        try {

            //CoinmarketcapUtil cutil = CoinmarketcapUtil.getinstance();

            UpbitUtil cutil = UpbitUtil.getinstance();

            //if(!isOper()) {

            CmeTradeReqVO vo = new CmeTradeReqVO();

            //int curcyCd = (int) (Math.random() * 8) + 1;
            double random = 0.01;


            String cnPrice = "";
            String coinCode = "";
            double phsAmt = 0;
//            if (curcyCd == 1) {
//                cnPrice = cutil.getCoinPrice("BTC", mkState);
//                coinCode = CmmCdConstant.BTC_CD;
//                phsAmt = Math.random() * 0.1;
//            } else if (curcyCd == 2) {
//                cnPrice = cutil.getCoinPrice("ETH", mkState);
//                coinCode = CmmCdConstant.ETH_CD;
//                phsAmt = Math.random() * 0.1;
//            } else if (curcyCd == 3) {
//                cnPrice = cutil.getCoinPrice("BCH", mkState);
//                coinCode = CmmCdConstant.BCH_CD;
//                phsAmt = Math.random() * 0.1;
//            } else if (curcyCd == 4) {
//                cnPrice = cutil.getCoinPrice("XRP", mkState);
//                coinCode = CmmCdConstant.XRP_CD;
//                phsAmt = 10 + (Math.random() * 10);
//            } else if (curcyCd == 5) {
//                cnPrice = cutil.getCoinPrice("QTUM", mkState);
//                coinCode = CmmCdConstant.QTUM_CD;
//                phsAmt = Math.random() * 0.1;
//            } else if (curcyCd == 6) {
//                cnPrice = cutil.getCoinPrice("BTG", mkState);
//                coinCode = CmmCdConstant.BTG_CD;
//                phsAmt = Math.random() * 0.1;
//            } else if (curcyCd == 7) {
//                cnPrice = cutil.getCoinPrice("DASH", mkState);
//                coinCode = CmmCdConstant.DASH_CD;
//                phsAmt = Math.random() * 0.1;
//            } else if (curcyCd == 8) {
//                cnPrice = cutil.getCoinPrice("LTC", mkState);
//                coinCode = CmmCdConstant.LTC_CD;
//                phsAmt = Math.random() * 0.1;
//            }


            if (curcyCd == 1) {
                cnPrice = cutil.getCoinPrice("BCH", mkState);
                coinCode = CmmCdConstant.BCH_CD;
                phsAmt = Math.random() * 0.01;
            } else if (curcyCd == 2) {
                cnPrice = cutil.getCoinPrice("QTUM", mkState);
                coinCode = CmmCdConstant.QTUM_CD;
                phsAmt = Math.random() * 0.01;
            } else if (curcyCd == 3) {
                cnPrice = cutil.getCoinPrice("BTG", mkState);
                coinCode = CmmCdConstant.BTG_CD;
                phsAmt = Math.random() * 0.01;
            } else if (curcyCd == 4) {
                cnPrice = cutil.getCoinPrice("DASH", mkState);
                coinCode = CmmCdConstant.DASH_CD;
                phsAmt = Math.random() * 0.01;
            } else if (curcyCd == 5) {
                cnPrice = cutil.getCoinPrice("LTC", mkState);
                coinCode = CmmCdConstant.LTC_CD;
                phsAmt = Math.random() * 0.01;
            } else if (curcyCd == 6) {
                cnPrice = cutil.getCoinPrice("BSV", mkState);
                coinCode = CmmCdConstant.BSV_CD;
                //coinCode = "CMMC00000001506";
                phsAmt = Math.random() * 0.01;
            }else if (curcyCd == 7) {
                cnPrice = cutil.getCoinPrice("BTC", mkState);
                coinCode = CmmCdConstant.BTC_CD;
                phsAmt = Math.random() * 0.01;
            }else if (curcyCd == 8) {
                cnPrice = cutil.getCoinPrice("ETH", mkState);
                coinCode = CmmCdConstant.ETH_CD;
                phsAmt = Math.random() * 0.01;
            }else if (curcyCd == 9) {
                cnPrice = cutil.getCoinPrice("XRP", mkState);
                coinCode = CmmCdConstant.XRP_CD;
                phsAmt = 1 + (Math.random() * 10);
            }else if (curcyCd == 10) {
                cnPrice = cutil.getCoinPrice("TRX", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000001507";
            }else if (curcyCd == 11) {
                cnPrice = cutil.getCoinPrice("XLM", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000001527";
            }else if (curcyCd == 12) {
                cnPrice = cutil.getCoinPrice("WAVES", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000001548";
            }else if (curcyCd == 13) {
                cnPrice = cutil.getCoinPrice("POLY", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000001547";
            }else if (curcyCd == 14) {
                cnPrice = cutil.getCoinPrice("OMG", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000000450";
            }else if (curcyCd == 15) {
                cnPrice = cutil.getCoinPrice("ICX", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000001587";
            }else if (curcyCd == 16) {
                cnPrice = cutil.getCoinPrice("XEM", mkState);
                phsAmt = 1 + (Math.random() * 10);
                coinCode = "CMMC00000001606";
            }

            if (phsAmt <= 0) {
                phsAmt = 0.01;
            }

            if(cnPrice == null) return;

            double price = Double.parseDouble(cnPrice);

            // 시세더높게
            /*if("KRW".equals(mkState)){
                if(curcyCd == 2){
                    price = price * 1.05;
                    random = 0.01;
                }
            }*/

            vo.setRegIp("127.0.0.1");
            vo.setUserEmail(userEmail);
            vo.setApntPhsCd("CMMC00000000178");
            vo.setApntPhsYn("Y");

            double cnPrc = price;

            vo.setSellCurcyCd(coinCode);

            cnPrc = price + ((price * random * (Math.random() - 0.25)));
            if (phsAmt <= 0) {
                phsAmt = 0.12;
            }

            if(mkState.equals("KRW")) {
                int digit = 1;
                if(cnPrc >= 2000000) {
                    digit = 1000;
                } else if(cnPrc >= 1000000) {
                    digit = 500;
                } else if(cnPrc >= 500000) {
                    digit = 100;
                } else if(cnPrc >= 100000) {
                    digit = 50;
                } else if(cnPrc >= 10000) {
                    digit = 10;
                } else if(cnPrc >= 1000) {
                    digit = 10;
                } else if(cnPrc >= 100) {
                    digit = 5;
                } else if(cnPrc >= 50) {
                    digit = 5;
                } else {
                    digit = 1;
                }
                System.out.println("cnPrc1 : " + cnPrc);
                cnPrc = (int) cnPrc / digit * digit;
                System.out.println("cnPrc2 : " + cnPrc);
            }




            DecimalFormat form = new DecimalFormat("#.########");
            vo.setPhsAmt(form.format(phsAmt));

            if(mkState.equals("KRW")) {
                form = new DecimalFormat("#");
            } else {
                form = new DecimalFormat("#.########");
            }
            //form = new DecimalFormat("#");
            vo.setPhsPrc(form.format(cnPrc));

            if (cnPrc > 0 && Double.parseDouble(vo.getPhsPrc()) > 0) {
                //tradeService.sellOrder(vo);

                CmeTradeReqVO reqVO = new CmeTradeReqVO();
                reqVO.setUserEmail(vo.getUserEmail());
                reqVO.setSellBuyCd("S");
                reqVO.setCurcyCd(vo.getSellCurcyCd());
                reqVO.setAmount(vo.getPhsAmt());
                reqVO.setRegIp(vo.getRegIp());
                Method method = getMarketMethod(tradeService, "orderMQ", mkState, reqVO);
                reqVO = (CmeTradeReqVO) method.invoke(tradeService, reqVO);
                //reqVO = tradeService.orderMQ(reqVO);

                List list = (List) reqVO.getRESULT();
                Map map = (Map) list.get(0);
                String rtnCd = (String) map.get("RTN_CD");

                RabbitmqClient client = null;
                RabbitmqClientETH clientEth = null;
                RabbitmqClientUSDT clientUSDT = null;
                RabbitmqClientBTC clientBTC = null;

                if(rtnCd != null && rtnCd.contains("ERC")) {
                    JSONObject jobj = new JSONObject();

                    String payCode = "";
                    if(mkState.equals("KRW")) {
                        payCode = "CMMC00000000204";
                    } else if(mkState.equals("ETH")) {
                        payCode = "CMMC00000000206";
                    } else if(mkState.equals("USDT")) {
                        payCode = "CMMC00000001086";
                    } else if(mkState.equals("BTC")) {
                        payCode = "CMMC00000000205";
                    }

                    jobj.put("clientType", "CMMC00000000221");
                    jobj.put("prePaymentKey", rtnCd);
                    jobj.put("userEmail", vo.getUserEmail());
                    jobj.put("userRegIp", vo.getRegIp());
                    jobj.put("orderType", "S");
                    jobj.put("tradeType", vo.getApntPhsYn());
                    jobj.put("orderCode", vo.getSellCurcyCd());
                    jobj.put("orderCodeName", makeCoinNm(vo.getSellCurcyCd()));
                    jobj.put("orderPrc", vo.getPhsPrc());
                    jobj.put("orderCnt", vo.getPhsAmt());
                    jobj.put("payCode", payCode);
                    String value = jobj.toString();

                    //if(!isOper()) RabbitmqClient.setStaChannel();
                    //RabbitmqClient client = RabbitmqClient.getinstance();
                    //client.sendMsg(value);

                    if(!payCode.equals(vo.getSellCurcyCd())) {
                        if ("ETH".equals(mkState)) {
                            System.out.println("ETH rabbit");
                            clientEth = RabbitmqClientETH.getinstance();
                            clientEth.sendMsg(value);
                        } else if ("USDT".equals(mkState)) {
                            System.out.println("USDT rabbit");
                            clientUSDT = RabbitmqClientUSDT.getinstance();
                            clientUSDT.sendMsg(value);
                        } else if ("BTC".equals(mkState)) {
                            System.out.println("BTC rabbit");
                            clientBTC = RabbitmqClientBTC.getinstance();
                            clientBTC.sendMsg(value);
                        } else {
                            System.out.println("KRW rabbit");
                            if(!vo.getSellCurcyCd().equals(CmmCdConstant.BTC_CD)) {
                                client = RabbitmqClient.getinstance();
                                client.sendMsg(value);
                            }
                        }
                    }

                } else {

                    //주문번호로 상세조회
                    //String orderNo = tradeService.getSellCancelOrder(vo);
                    method = getMarketMethod(tradeService, "getSellCancelOrder", mkState, vo);
                    String orderNo = (String) method.invoke(tradeService, vo);

                    if(orderNo != null) {
                        vo.setOrderNo(orderNo);
                        method = getMarketMethod(tradeService, "selectOrder", mkState, vo);
                        CmeTradeReqVO rvo = (CmeTradeReqVO) method.invoke(tradeService, vo);

                        //CmeTradeReqVO rvo = tradeService.selectOrder(vo);

                        rvo.setUserEmail(vo.getUserEmail());
                        rvo.setRegIp(vo.getRegIp());
                        method = getMarketMethod(tradeService, "orderCalcel", mkState, rvo);
                        method.invoke(tradeService, rvo);

                        /*if (rvo.getCncl() != null && rvo.getCncl().equals("")) {
                            tradeService.orderCalcel(rvo);
                        }*/
                    }
                }
            }

            //}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void buyAdmtOrder(String curcyCd, String userEmail, String mkState) throws Exception {
        try {

            //CoinmarketcapUtil cutil = CoinmarketcapUtil.getinstance();

            //UpbitUtil cutil = UpbitUtil.getinstance();

            //if (!isOper()) {

            System.out.println("ADMT TOKEN BUYORDERMQ...");
            CmeTradeReqVO vo = new CmeTradeReqVO();

            String cnPrice = "";
            String coinCode = "";
            double phsAmt = 0;
            double random = 0.5;

            double price = 0;
            double currency = 0;
            double stPrice = 500000;
            double endPrice = 1000000;
            double midPrice = 0;

            DecimalFormat form2 = new DecimalFormat("#.########");

                if ("KRW".equals(mkState)) {
                    form2 = new DecimalFormat("#");

                    midPrice = (stPrice + endPrice) / 2;
                    midPrice = Double.parseDouble(form2.format(midPrice + ((midPrice * random * (Math.random() - 0.5)))));

                    int digit = 10000;
                    midPrice = (int) midPrice / digit * digit;
                    //price = Double.parseDouble(tradeService.getCnPrice(curcyCd));
                    price = 45;
                    form2 = new DecimalFormat("#.########");
                    phsAmt = Double.parseDouble(form2.format(midPrice / price));
                } else if ("ETH".equals(mkState)) {
                    form2 = new DecimalFormat("#.########");
                    double ethPrice = Double.parseDouble(tradeService.getCnPrice("CMMC00000000206"));

                    String stPrice2 = form2.format(stPrice / ethPrice);
                    String endPrice2 =  form2.format(endPrice / ethPrice);
                    midPrice = Double.parseDouble(form2.format((Double.parseDouble(stPrice2) + Double.parseDouble(endPrice2)) / 2));
                    midPrice = Double.parseDouble(form2.format(midPrice + ((midPrice * random * (Math.random() - 0.5)))));
                    //price = Double.parseDouble(tradeService.getCnPriceETH(curcyCd));
                    price = 0.00030303;
                    phsAmt = Double.parseDouble(form2.format(midPrice / price));
                } else if("BTC".equals(mkState)){
                    form2 = new DecimalFormat("#.########");
                    double btcPrice = Double.parseDouble(tradeService.getCnPrice("CMMC00000000205"));

                    String stPrice2 = form2.format(stPrice / btcPrice);
                    String endPrice2 =  form2.format(endPrice / btcPrice);
                    midPrice = Double.parseDouble(form2.format((Double.parseDouble(stPrice2) + Double.parseDouble(endPrice2)) / 2));
                    midPrice = Double.parseDouble(form2.format(midPrice + ((midPrice * random * (Math.random() - 0.5)))));
                    price = Double.parseDouble(tradeService.getCnPriceBTC(curcyCd));
                    phsAmt = Double.parseDouble(form2.format(midPrice / price));
                }
                coinCode = "CMMC00000001467";

            vo.setRegIp("127.0.0.1");
            vo.setUserEmail(userEmail);
            vo.setApntPhsCd("CMMC00000000178");
            vo.setApntPhsYn("Y");

            double cnPrc = price;

            vo.setBuyCurcyCd(coinCode);

            cnPrc = price;

            DecimalFormat form = new DecimalFormat("#.########");
            vo.setPhsAmt(form.format(phsAmt));

            if (mkState.equals("KRW")) {
                form = new DecimalFormat("#");
            } else {
                form = new DecimalFormat("#.########");
            }
            vo.setPhsPrc(form.format(cnPrc));


            vo.setUsePrc(form.format(midPrice));
            vo.setUsePointPrc("0");

            CmeResultVO res = new CmeResultVO();
            if (!"".equals(vo.getUsePrc()) && cnPrc * phsAmt > 0 && cnPrc > 0 && phsAmt > 0) {
                //res = tradeService.buyOrder(vo);

                CmeTradeReqVO reqVO = new CmeTradeReqVO();
                reqVO.setUserEmail(vo.getUserEmail());
                reqVO.setSellBuyCd("B");
                reqVO.setCurcyCd(vo.getBuyCurcyCd());
                reqVO.setAmount(vo.getUsePrc());
                reqVO.setRegIp(vo.getRegIp());

                Method method = getMarketMethod(tradeService, "orderMQ", mkState, reqVO);
                reqVO = (CmeTradeReqVO) method.invoke(tradeService, reqVO);

                //reqVO = tradeService.orderMQ(reqVO);

                List list = (List) reqVO.getRESULT();
                Map map = (Map) list.get(0);
                String rtnCd = (String) map.get("RTN_CD");

                RabbitmqClient client = null;
                RabbitmqClientETH clientEth = null;
                RabbitmqClientUSDT clientUSDT = null;
                RabbitmqClientBTC clientBTC = null;

                if (rtnCd != null && rtnCd.contains("ERC")) {

                    String payCode = "";
                    if (mkState.equals("KRW")) {
                        payCode = "CMMC00000000204";
                    } else if (mkState.equals("ETH")) {
                        payCode = "CMMC00000000206";
                    } else if (mkState.equals("USDT")) {
                        payCode = "CMMC00000001086";
                    } else if (mkState.equals("BTC")) {
                        payCode = "CMMC00000000205";
                    }
                    JSONObject jobj = new JSONObject();
                    jobj.put("clientType", "CMMC00000000221");
                    jobj.put("prePaymentKey", rtnCd);
                    jobj.put("userEmail", vo.getUserEmail());
                    jobj.put("userRegIp", vo.getRegIp());
                    jobj.put("orderType", "B");
                    jobj.put("tradeType", vo.getApntPhsYn());
                    jobj.put("orderCode", vo.getBuyCurcyCd());
                    jobj.put("orderCodeName", makeCoinNm(vo.getBuyCurcyCd()));
                    jobj.put("usePrc", vo.getUsePrc());
                    jobj.put("orderPrc", vo.getPhsPrc());
                    jobj.put("orderCnt", vo.getPhsAmt());
                    jobj.put("payCode", payCode);
                    String value = jobj.toString();

                    //if(!isOper()) RabbitmqClient.setStaChannel();

                    if ("ETH".equals(mkState)) {
                        System.out.println("ETH rabbit");
                        clientEth = RabbitmqClientETH.getinstance();
                        clientEth.sendMsg(value);
                    } else if ("USDT".equals(mkState)) {
                        System.out.println("USDT rabbit");
                        clientUSDT = RabbitmqClientUSDT.getinstance();
                        clientUSDT.sendMsg(value);
                    } else if ("BTC".equals(mkState)) {
                        System.out.println("BTC rabbit");
                        clientBTC = RabbitmqClientBTC.getinstance();
                        clientBTC.sendMsg(value);
                    } else {
                        System.out.println("KRW rabbit");
                        client = RabbitmqClient.getinstance();
                        client.sendMsg(value);
                    }

                    //RabbitmqClient client = RabbitmqClient.getinstance();
                    //client.sendMsg(value);
                } else {
                    //주문번호로 상세조회
                    //String orderNo = tradeService.getBuyCancelOrder(vo);
                    method = getMarketMethod(tradeService, "getBuyCancelOrder", mkState, vo);
                    String orderNo = (String) method.invoke(tradeService, vo);

                    if (orderNo != null) {
                        vo.setOrderNo(orderNo);

                        method = getMarketMethod(tradeService, "selectOrder", mkState, vo);
                        CmeTradeReqVO rvo = (CmeTradeReqVO) method.invoke(tradeService, vo);
                        //CmeTradeReqVO rvo = tradeService.selectOrder(vo);

                        rvo.setUserEmail(vo.getUserEmail());
                        rvo.setRegIp(vo.getRegIp());

                        method = getMarketMethod(tradeService, "orderCalcel", mkState, rvo);
                        method.invoke(tradeService, rvo);
                        /*if (rvo.getCncl() != null && rvo.getCncl().equals("")) {
                            tradeService.orderCalcel(rvo);

                        }*/
                    }
                }
            }

            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buySecretOrder(String curcyCd, String userEmail, String mkState) throws Exception {
        try {

            //CoinmarketcapUtil cutil = CoinmarketcapUtil.getinstance();

            //UpbitUtil cutil = UpbitUtil.getinstance();

            //if (!isOper()) {

            CmeTradeReqVO vo = new CmeTradeReqVO();

            String cnPrice = "";
            String coinCode = "";
            double phsAmt = 0;
            double random = 0.8;

            double price = 20;


            if("CMMC00000000685".equals(curcyCd)){

                if("KRW".equals(mkState)){
                    random = 0.8;
                   // cnPrice = "12";
                    price = 20;
                }else if("ETH".equals(mkState)){
                    random = 0.2;
                   // cnPrice = "0.00005500";
                    price = 0.00008000;
                }
                coinCode = CmmCdConstant.SGC_CD;
                phsAmt = 5000 + Math.random() * 15000;
            }else if("CMMC00000001066".equals(curcyCd)){
                price = 30;
                coinCode = CmmCdConstant.SECRET_CD;
                phsAmt = 5000 + Math.random() * 10000;
            }


            vo.setRegIp("127.0.0.1");
            vo.setUserEmail(userEmail);
            vo.setApntPhsCd("CMMC00000000178");
            vo.setApntPhsYn("Y");

            double cnPrc = price;

            vo.setBuyCurcyCd(coinCode);

            cnPrc = price + (price * random * (Math.random() - 0.5));

            DecimalFormat form = new DecimalFormat("#.########");
            vo.setPhsAmt(form.format(phsAmt));

            if(mkState.equals("KRW")) {
                form = new DecimalFormat("#");
            } else {
                form = new DecimalFormat("#.########");
            }
            vo.setPhsPrc(form.format(cnPrc));

            vo.setUsePrc(form.format(cnPrc * phsAmt));
            vo.setUsePointPrc("0");

            CmeResultVO res = new CmeResultVO();
            if (!"".equals(vo.getUsePrc()) && cnPrc * phsAmt > 0 && cnPrc > 0 && phsAmt > 0) {
                //res = tradeService.buyOrder(vo);

                CmeTradeReqVO reqVO = new CmeTradeReqVO();
                reqVO.setUserEmail(vo.getUserEmail());
                reqVO.setSellBuyCd("B");
                reqVO.setCurcyCd(vo.getBuyCurcyCd());
                reqVO.setAmount(vo.getUsePrc());
                reqVO.setRegIp(vo.getRegIp());

                Method method = getMarketMethod(tradeService, "orderMQ", mkState, reqVO);
                reqVO = (CmeTradeReqVO) method.invoke(tradeService, reqVO);

                //reqVO = tradeService.orderMQ(reqVO);

                List list = (List) reqVO.getRESULT();
                Map map = (Map) list.get(0);
                String rtnCd = (String) map.get("RTN_CD");

                RabbitmqClient client = null;
                RabbitmqClientETH clientEth = null;
                RabbitmqClientUSDT clientUSDT = null;
                RabbitmqClientBTC clientBTC = null;

                if(rtnCd != null && rtnCd.contains("ERC")) {

                    String payCode = "";
                    if(mkState.equals("KRW")) {
                        payCode = "CMMC00000000204";
                    } else if(mkState.equals("ETH")) {
                        payCode = "CMMC00000000206";
                    } else if(mkState.equals("USDT")) {
                        payCode = "CMMC00000001086";
                    } else if(mkState.equals("BTC")) {
                        payCode = "CMMC00000000205";
                    }
                    JSONObject jobj = new JSONObject();
                    jobj.put("clientType", "CMMC00000000221");
                    jobj.put("prePaymentKey", rtnCd);
                    jobj.put("userEmail", vo.getUserEmail());
                    jobj.put("userRegIp", vo.getRegIp());
                    jobj.put("orderType", "B");
                    jobj.put("tradeType", vo.getApntPhsYn());
                    jobj.put("orderCode", vo.getBuyCurcyCd());
                    jobj.put("orderCodeName", makeCoinNm(vo.getBuyCurcyCd()));
                    jobj.put("usePrc", vo.getUsePrc());
                    jobj.put("orderPrc", vo.getPhsPrc());
                    jobj.put("orderCnt", vo.getPhsAmt());
                    jobj.put("payCode", payCode);
                    String value = jobj.toString();

                    //if(!isOper()) RabbitmqClient.setStaChannel();

                    if("ETH".equals(mkState)) {
                        System.out.println("ETH rabbit");
                        clientEth = RabbitmqClientETH.getinstance();
                        clientEth.sendMsg(value);
                    } else if("USDT".equals(mkState)) {
                        System.out.println("USDT rabbit");
                        clientUSDT = RabbitmqClientUSDT.getinstance();
                        clientUSDT.sendMsg(value);
                    } else if("BTC".equals(mkState)) {
                        System.out.println("BTC rabbit");
                        clientBTC = RabbitmqClientBTC.getinstance();
                        clientBTC.sendMsg(value);
                    } else {
                        System.out.println("KRW rabbit");
                        client = RabbitmqClient.getinstance();
                        client.sendMsg(value);
                    }

                    //RabbitmqClient client = RabbitmqClient.getinstance();
                    //client.sendMsg(value);
                } else {
                    //주문번호로 상세조회
                    //String orderNo = tradeService.getBuyCancelOrder(vo);
                    method = getMarketMethod(tradeService, "getBuyCancelOrder", mkState, vo);
                    String orderNo = (String) method.invoke(tradeService, vo);

                    if(orderNo != null) {
                        vo.setOrderNo(orderNo);

                        method = getMarketMethod(tradeService, "selectOrder", mkState, vo);
                        CmeTradeReqVO rvo = (CmeTradeReqVO) method.invoke(tradeService, vo);
                        //CmeTradeReqVO rvo = tradeService.selectOrder(vo);

                        rvo.setUserEmail(vo.getUserEmail());
                        rvo.setRegIp(vo.getRegIp());

                        method = getMarketMethod(tradeService, "orderCalcel", mkState, rvo);
                        method.invoke(tradeService, rvo);
                        /*if (rvo.getCncl() != null && rvo.getCncl().equals("")) {
                            tradeService.orderCalcel(rvo);

                        }*/
                    }
                }
            }

            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    public void sellSecretOrder(String curcyCd, String userEmail, String mkState) throws Exception {
        try {

            CmeTradeReqVO vo = new CmeTradeReqVO();


            double random = 0.8;
            String cnPrice = "";
            String coinCode = "";
            double phsAmt = 0;

            double price = 20;

            if("CMMC00000000685".equals(curcyCd)){
                if("KRW".equals(mkState)){
                    random = 0.8;
                   // cnPrice = "12";
                    price = 20;
                }else if("ETH".equals(mkState)){
                    random = 0.2;
                   // cnPrice = "0.00005500";
                    price = 0.00008000;
                }
                coinCode = CmmCdConstant.SGC_CD;
                phsAmt = 5000 + Math.random() * 15000;
            }else if("CMMC00000001066".equals(curcyCd)){
                price = 30;
                coinCode = CmmCdConstant.SECRET_CD;
                phsAmt = 5000 + Math.random() * 10000;
            }




            vo.setRegIp("127.0.0.1");
            vo.setUserEmail(userEmail);
            vo.setApntPhsCd("CMMC00000000178");
            vo.setApntPhsYn("Y");

            double cnPrc = price;

            vo.setSellCurcyCd(coinCode);

            cnPrc = price + (price * random * (Math.random()));

            DecimalFormat form = new DecimalFormat("#.########");
            vo.setPhsAmt(form.format(phsAmt));

            if(mkState.equals("KRW")) {
                form = new DecimalFormat("#");
            } else {
                form = new DecimalFormat("#.########");
            }
            //form = new DecimalFormat("#");
            vo.setPhsPrc(form.format(cnPrc));

            if (cnPrc > 0 && Double.parseDouble(vo.getPhsPrc()) > 0) {
                //tradeService.sellOrder(vo);

                CmeTradeReqVO reqVO = new CmeTradeReqVO();
                reqVO.setUserEmail(vo.getUserEmail());
                reqVO.setSellBuyCd("S");
                reqVO.setCurcyCd(vo.getSellCurcyCd());
                reqVO.setAmount(vo.getPhsAmt());
                reqVO.setRegIp(vo.getRegIp());
                Method method = getMarketMethod(tradeService, "orderMQ", mkState, reqVO);
                reqVO = (CmeTradeReqVO) method.invoke(tradeService, reqVO);
                //reqVO = tradeService.orderMQ(reqVO);

                List list = (List) reqVO.getRESULT();
                Map map = (Map) list.get(0);
                String rtnCd = (String) map.get("RTN_CD");

                RabbitmqClient client = null;
                RabbitmqClientETH clientEth = null;
                RabbitmqClientUSDT clientUSDT = null;
                RabbitmqClientBTC clientBTC = null;

                if(rtnCd != null && rtnCd.contains("ERC")) {
                    JSONObject jobj = new JSONObject();

                    String payCode = "";
                    if(mkState.equals("KRW")) {
                        payCode = "CMMC00000000204";
                    } else if(mkState.equals("ETH")) {
                        payCode = "CMMC00000000206";
                    } else if(mkState.equals("USDT")) {
                        payCode = "CMMC00000001086";
                    } else if(mkState.equals("BTC")) {
                        payCode = "CMMC00000000205";
                    }

                    jobj.put("clientType", "CMMC00000000221");
                    jobj.put("prePaymentKey", rtnCd);
                    jobj.put("userEmail", vo.getUserEmail());
                    jobj.put("userRegIp", vo.getRegIp());
                    jobj.put("orderType", "S");
                    jobj.put("tradeType", vo.getApntPhsYn());
                    jobj.put("orderCode", vo.getSellCurcyCd());
                    jobj.put("orderCodeName", makeCoinNm(vo.getSellCurcyCd()));
                    jobj.put("orderPrc", vo.getPhsPrc());
                    jobj.put("orderCnt", vo.getPhsAmt());
                    jobj.put("payCode", payCode);
                    String value = jobj.toString();

                    //if(!isOper()) RabbitmqClient.setStaChannel();
                    //RabbitmqClient client = RabbitmqClient.getinstance();
                    //client.sendMsg(value);

                    if("ETH".equals(mkState)) {
                        System.out.println("ETH rabbit");
                        clientEth = RabbitmqClientETH.getinstance();
                        clientEth.sendMsg(value);
                    } else if("USDT".equals(mkState)) {
                        System.out.println("USDT rabbit");
                        clientUSDT = RabbitmqClientUSDT.getinstance();
                        clientUSDT.sendMsg(value);
                    } else if("BTC".equals(mkState)) {
                        System.out.println("BTC rabbit");
                        clientBTC = RabbitmqClientBTC.getinstance();
                        clientBTC.sendMsg(value);
                    } else {
                        System.out.println("KRW rabbit");
                        client = RabbitmqClient.getinstance();
                        client.sendMsg(value);
                    }

                } else {

                    //주문번호로 상세조회
                    //String orderNo = tradeService.getSellCancelOrder(vo);
                    method = getMarketMethod(tradeService, "getSellCancelOrder", mkState, vo);
                    String orderNo = (String) method.invoke(tradeService, vo);

                    if(orderNo != null) {
                        vo.setOrderNo(orderNo);
                        method = getMarketMethod(tradeService, "selectOrder", mkState, vo);
                        CmeTradeReqVO rvo = (CmeTradeReqVO) method.invoke(tradeService, vo);

                        //CmeTradeReqVO rvo = tradeService.selectOrder(vo);

                        rvo.setUserEmail(vo.getUserEmail());
                        rvo.setRegIp(vo.getRegIp());
                        method = getMarketMethod(tradeService, "orderCalcel", mkState, rvo);
                        method.invoke(tradeService, rvo);

                        /*if (rvo.getCncl() != null && rvo.getCncl().equals("")) {
                            tradeService.orderCalcel(rvo);
                        }*/
                    }
                }
            }

            //}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sellSecretOrder2(String mkState) throws Exception {
        try {

            for(int i=0; i < users2.length; i++) {

                CmeTradeReqVO vo = new CmeTradeReqVO();

                String userEmail = users2[i];
                double random = 1;
                String cnPrice = "15";
                String coinCode = "";
                double phsAmt = 0;

                cnPrice = "";
                coinCode = "CMMC00000001066";


                double price = 10;

                vo.setRegIp("127.0.0.1");
                vo.setUserEmail(userEmail);
                vo.setApntPhsCd("CMMC00000000178");
                vo.setApntPhsYn("Y");

                double cnPrc = price;

                double plusPrc = 0;
                vo.setSellCurcyCd(coinCode);

                if (mkState.equals("ETH")) {

                    cnPrc = 0.00006930;
                    plusPrc = 0.00000001;
                } else if (mkState.equals("USDT")) {

                    cnPrc = 0.015;
                    plusPrc = 0.0001;
                } else if (mkState.equals("BTC")) {

                    cnPrc = 0.00000206;
                    plusPrc = 0.00000001;
                } else if (mkState.equals("BTC")) {

                    cnPrc = 28;
                    plusPrc = 1;
                }

                for (int j = 0; j < 30; j++) {

                    phsAmt = 1000 + Math.random() * 1000;
                    cnPrc += plusPrc * Math.random() * 5;
                    DecimalFormat form = new DecimalFormat("#.########");
                    vo.setPhsAmt(form.format(phsAmt));

                    if (mkState.equals("KRW")) {
                        form = new DecimalFormat("#");
                    } else {
                        form = new DecimalFormat("#.########");
                    }
                    //form = new DecimalFormat("#");
                    vo.setPhsPrc(form.format(cnPrc));

                    if (cnPrc > 0 && Double.parseDouble(vo.getPhsPrc()) > 0) {
                        //tradeService.sellOrder(vo);

                        CmeTradeReqVO reqVO = new CmeTradeReqVO();
                        reqVO.setUserEmail(vo.getUserEmail());
                        reqVO.setSellBuyCd("S");
                        reqVO.setCurcyCd(vo.getSellCurcyCd());
                        reqVO.setAmount(vo.getPhsAmt());
                        reqVO.setRegIp(vo.getRegIp());
                        Method method = getMarketMethod(tradeService, "orderMQ", mkState, reqVO);
                        reqVO = (CmeTradeReqVO) method.invoke(tradeService, reqVO);
                        //reqVO = tradeService.orderMQ(reqVO);

                        List list = (List) reqVO.getRESULT();
                        Map map = (Map) list.get(0);
                        String rtnCd = (String) map.get("RTN_CD");

                        RabbitmqClient client = null;
                        RabbitmqClientETH clientEth = null;
                        RabbitmqClientUSDT clientUSDT = null;
                        RabbitmqClientBTC clientBTC = null;

                        if (rtnCd != null && rtnCd.contains("ERC")) {
                            JSONObject jobj = new JSONObject();

                            String payCode = "";
                            if (mkState.equals("KRW")) {
                                payCode = "CMMC00000000204";
                            } else if (mkState.equals("ETH")) {
                                payCode = "CMMC00000000206";
                            } else if (mkState.equals("USDT")) {
                                payCode = "CMMC00000001086";
                            } else if (mkState.equals("BTC")) {
                                payCode = "CMMC00000000205";
                            }

                            jobj.put("clientType", "CMMC00000000221");
                            jobj.put("prePaymentKey", rtnCd);
                            jobj.put("userEmail", vo.getUserEmail());
                            jobj.put("userRegIp", vo.getRegIp());
                            jobj.put("orderType", "S");
                            jobj.put("tradeType", vo.getApntPhsYn());
                            jobj.put("orderCode", vo.getSellCurcyCd());
                            jobj.put("orderCodeName", makeCoinNm(vo.getSellCurcyCd()));
                            jobj.put("orderPrc", vo.getPhsPrc());
                            jobj.put("orderCnt", vo.getPhsAmt());
                            jobj.put("payCode", payCode);
                            String value = jobj.toString();

                            //if(!isOper()) RabbitmqClient.setStaChannel();
                            //RabbitmqClient client = RabbitmqClient.getinstance();
                            //client.sendMsg(value);

                            if ("ETH".equals(mkState)) {
                                System.out.println("ETH rabbit");
                                clientEth = RabbitmqClientETH.getinstance();
                                clientEth.sendMsg(value);
                            } else if ("USDT".equals(mkState)) {
                                System.out.println("USDT rabbit");
                                clientUSDT = RabbitmqClientUSDT.getinstance();
                                clientUSDT.sendMsg(value);
                            } else if ("BTC".equals(mkState)) {
                                System.out.println("BTC rabbit");
                                clientBTC = RabbitmqClientBTC.getinstance();
                                clientBTC.sendMsg(value);
                            } else {
                                System.out.println("KRW rabbit");
                                client = RabbitmqClient.getinstance();
                                client.sendMsg(value);
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}