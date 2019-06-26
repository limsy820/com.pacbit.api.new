
/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.scheduler.control;

import com.bitkrx.api.chart.service.CoinChartService;
import com.bitkrx.api.mail.service.MailApiService;
import com.bitkrx.api.push.service.CmeFcmPushService;
import com.bitkrx.api.sample.service.SampleService;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.trade.vo.CmeTradeReqVO;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.util.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * @프로젝트명 : com.bitkrx.api
 * @패키지     : com.bitkrx.api.scheduler.control
 * @클래스명   : com.bitkrx.api
 * @작성자  :  (주)씨엠이소프트 문영민
 * @작성일  : 2017. 12. 14.
 */
@Component
public class OffSchedulerController extends CmeDefaultExtendController {
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

    //구매판매 봇 계정
    private String[] users2 =
        {
         "visop@naver.com",
         "banbanguy@daum.net",
         "ymin2715@naver.com",
         };


    /**2017-11-01 최초 생성 : KRW 마켓 BTC
     * IF-EX-012
     * 등록자 : 문영민
     * @return
     * @throws Exception
     */
    //@Scheduled(cron="0 0/10 * * * *") //1분마다
    //@Scheduled(cron="* /10 * * * *") //10분마다
    public void orderKRW_BTC() throws Exception {
        System.out.println("//////////////////////////////////////////KRW BTC BOT //////////////////////////////////////////");
        for (String user : users2) {
//            for (int i = 1; i <= 1; i++) {
                Thread.sleep(500);
                buyBtc(1, user, "KRW");
//            }
        }

        for (String user : users2) {
//            for (int i = 1; i <= 1; i++) {
                Thread.sleep(500);
                sellBtc(1, user, "KRW");
//            }
        }

    }


    public void buyBtc(int curcyCd, String userEmail, String mkState) throws Exception {
        try {

            //CoinmarketcapUtil cutil = CoinmarketcapUtil.getinstance();

            UpbitUtil cutil = UpbitUtil.getinstance();

            //if (!isOper()) {

            CmeTradeReqVO vo = new CmeTradeReqVO();

            String cnPrice = "";
            String coinCode = "";
            double phsAmt = 0;
            double random = 0.01;


            if (curcyCd == 1) {
                cnPrice = cutil.getCoinPrice("BTC", mkState);
                coinCode = CmmCdConstant.BTC_CD;
                phsAmt = Math.random() * 0.01;
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
                            client = RabbitmqClient.getinstance();
                            client.sendMsg(value);

                        }
                    }

                } else {
                    //주문번호로 상세조회
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
                    }
                }
            }

            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sellBtc(int curcyCd, String userEmail, String mkState) throws Exception {
        try {


            UpbitUtil cutil = UpbitUtil.getinstance();

            CmeTradeReqVO vo = new CmeTradeReqVO();

            //int curcyCd = (int) (Math.random() * 8) + 1;
            double random = 0.01;


            String cnPrice = "";
            String coinCode = "";
            double phsAmt = 0;


            if (curcyCd == 1) {
                cnPrice = cutil.getCoinPrice("BTC", mkState);
                coinCode = CmmCdConstant.BTC_CD;
                phsAmt = Math.random() * 0.01;
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
                                client = RabbitmqClient.getinstance();
                                client.sendMsg(value);
                        }
                    }

                } else {

                    //주문번호로 상세조회
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

                    }
                }
            }

            //}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}