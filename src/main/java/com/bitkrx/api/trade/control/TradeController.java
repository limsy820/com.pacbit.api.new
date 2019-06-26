package com.bitkrx.api.trade.control;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitkrx.api.auth.vo.UserTradeCheckVO;
import com.bitkrx.api.common.vo.CoinMinMaxVO;
import com.bitkrx.api.trade.dao.TradeDAO;
import com.bitkrx.api.trade.service.DepositService;
import com.bitkrx.api.trade.vo.*;
import com.bitkrx.config.util.*;
import com.rabbitmq.client.Channel;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import com.bitkrx.api.auth.dao.LoginDAO;
import com.bitkrx.api.auth.service.RTPService;
import com.bitkrx.api.mail.service.impl.MailServiceImpl;
import com.bitkrx.api.push.service.CmeFcmPushService;
import com.bitkrx.api.push.vo.SendMsgVO;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.exception.HTTPErrorHander;
import com.bitkrx.config.logging.CmeCommonLogger;

@Controller
@RequestMapping(value="/bt")
public class TradeController extends CmeDefaultExtendController {

    @Autowired
    TradeService tradeService;

    @Autowired
    MailServiceImpl mailServiceImpl;
    @Autowired
    SmsApiService smsApiService;
    @Autowired
    LoginDAO loginDAO;
    @Autowired
    RTPService rService;
    @Autowired
    DepositService depositService;
    @Autowired
    CmeFcmPushService pushService;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private MessageSource messageSource;

    public String getLocale(HttpServletRequest request, String key) {

        String msg = "";
        msg = messageSource.getMessage(key, null, "", localeResolver.resolveLocale(request));
        return msg.replaceAll("exchangeNm", CmmCdConstant.EXCHANGE_NAME);

    }

    protected CmeCommonLogger log = new CmeCommonLogger(this.getClass());

    /**
     * 2017-11-01 최초 생성 : 구매 등록
     * IF-EX-012
     * 등록자 : 문영민
     *
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trade/buyOrder.dm")
    public @ResponseBody
    CmeResultVO buyOrder(@ModelAttribute CmeTradeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        rService.RTPVertify(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        //log.ViewErrorLog("구매컨트롤러 진입 : " + sdf.format(new Date()));
        //log.ViewErrorLog("---------x-add4sys01 아이피-------- : " + request.getHeader("x-add4sys01"));

        CmeResultVO res = new CmeResultVO();
        //CmeTradeBuyOrderResVO result = new CmeTradeBuyOrderResVO();

        if (vo.getApntPhsYn().equals("")) {
            res.setResultMsg("구분값이 없습니다.");
            return res;
        } else if (vo.getApntPhsCd().equals("")) {
            res.setResultMsg("거래내용값이 없습니다.");
            return res;
        } else if (vo.getUserEmail().equals("")) {
            res.setResultMsg("Email값이 없습니다.");
            return res;
        } else if (vo.getBuyCurcyCd().equals("")) {
            res.setResultMsg("구매통화값이 없습니다.");
            return res;
        } else if (vo.getPhsPrc().equals("")) {
            res.setResultMsg("주문가격값이 없습니다.");
            return res;
        }/*else if(vo.getPayCurcyCd().equals("")) {
			res.setResultMsg("결제통화값이 없습니다.");
			return res;
		} else if(vo.getPhsAmt().equals("")) {
			res.setResultMsg("주문수량값이 없습니다.");
			return res;
		}*/

        String coinNm = makeCoinNm(vo.getBuyCurcyCd());

        //log.ViewErrorLog("구매프로시저 진입 전 : " + sdf.format(new Date()));
        res = tradeService.buyOrder(vo);
        //log.ViewErrorLog("구매프로시저 완료 : " + sdf.format(new Date()));


        List list = (List) res.getProceduresResult();
        Map map = (Map) list.get(0);
        String rtnCd = (String) map.get("RTN_CD");
        if (rtnCd != null && rtnCd.equals("1")) {
            res.setResultMsg("구매등록 성공");
            res.setResultStrCode("000");
        } else if (rtnCd != null && rtnCd.equals("-99999")) {
            res.setResultMsg("대량 거래를 실행하여 구매등록에 실패하였습니다.");
            res.setResultStrCode(rtnCd);
        } else if (rtnCd != null && rtnCd.equals("-20195")) {
            res.setResultMsg("최대 판매 갯수를 초과했습니다.");
            res.setResultStrCode(rtnCd);
        } else if (rtnCd != null && rtnCd.equals("-20091")) {
            res.setResultMsg("구매 현금이 부족합니다.");
            res.setResultStrCode(rtnCd);
        } else if (rtnCd != null && rtnCd.equals("-20095")) {
            res.setResultMsg("판매대기 리스트가 없습니다.");
            res.setResultStrCode(rtnCd);
        } else {
            res.setResultMsg("접속량이 많아 구매에 실패하였습니다.다시 시도해 주세요.");
            res.setResultStrCode(rtnCd);
        }


        try {
            String sysdate = tradeService.selectDate("");
            //구매내역 저장
            if (rtnCd != null && rtnCd.equals("1")) {
                // Y-> 지정가 N-> 시장가
                if (vo.getApntPhsYn().equals("Y")) {

                    SendMsgVO smvo = new SendMsgVO();
                    smvo.setUserEmail(vo.getUserEmail());
                    smvo.setSendYn("N");
                    smvo.setCmmCd("CMMC00000000197");
                    smvo.setMsg(sysdate + "|" + vo.getClientCd() + "|" + coinNm + "|" + vo.getPhsPrc() + "|" + vo.getPhsAmt());
                    pushService.PR_INSUPT_SND_MSG(smvo);

                }
            }

            //log.ViewErrorLog("구매내역저장 완료/ 체결내역저장 전 : " + sdf.format(new Date()));

            //체결내역저장
            String rntMsg = (String) map.get("RNT_MSG");
            String orderNo = rntMsg.substring(rntMsg.indexOf("CNPH"), rntMsg.indexOf("CNPH") + 19);
            OrderResVO rvo = new OrderResVO();
            rvo.setCnPhsCode(orderNo);
            if (!"".equals(orderNo)) {
                SendMsgVO smvo = new SendMsgVO();
                smvo.setUserEmail(vo.getUserEmail());
                smvo.setSendYn("N");
                smvo.setCmmCd("CMMC00000000178");
                smvo.setMsg(orderNo + "|" + vo.getClientCd());
                pushService.PR_INSUPT_SND_MSG(smvo);
            }
        } catch (Exception e) {

        }

        res.setData(new HashMap<String, String>());

        return res;
    }


    /**
     * 2017-11-01 최초 생성 : 판매 메뉴 접근시 조회
     * IF-EX-013
     * 등록자 : 문영민
     *
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trade/sellMenuView.dm")
    public @ResponseBody
    CmeResultVO sellMenuView(@ModelAttribute CmeTradeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO res = new CmeResultVO();
        CmeTradeSellMenuViewResVO result = new CmeTradeSellMenuViewResVO();

        if(vo.getCurcyCd().equals("")) {
            res.setResultMsg("판매통화값이 없습니다.");
            res.setData(result);
            return res;
        } else if(vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일값이 없습니다.");
            res.setData(result);
            return res;
        }
        res.setResultStrCode("000");
        res.setResultMsg("판매메뉴 접근조회 성공");

        Method method = getMarketMethod(tradeService, "sellMenuView", vo.getMkState(), vo);
        res.setData(method.invoke(tradeService, vo));

//        if("ETH".equals(vo.getMkState())) {
//            res.setData(tradeService.sellMenuViewETH(vo));
//        } else {
//            res.setData(tradeService.sellMenuView(vo));
//        }


        return res;
    }

    /**
     * 2017-11-01 최초 생성 : 수령통화가능금액 조회
     * IF-EX-014
     * 등록자 : 문영민
     *
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trade/recCoinView.dm")
    public @ResponseBody
    CmeResultVO recCoinView(@ModelAttribute CmeTradeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();
        RecCoinViewVO result = new RecCoinViewVO();

        if(vo.getCurcyCd().equals("")) {
            res.setResultMsg("수령통화값이 없습니다.");
            return res;
        } else if(vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일값이 없습니다.");
            return res;
        }
        Method method = getMarketMethod(tradeService, "recCoinView", vo.getMkState(), vo);
        result = (RecCoinViewVO) method.invoke(tradeService, vo);

//        if("ETH".equals(vo.getMkState())) {
//            result = tradeService.recCoinViewETH(vo);
//        } else if("USDT".equals(vo.getMkState())) {
//            result = tradeService.recCoinViewUSDT(vo);
//        } else {
//            result = tradeService.recCoinView(vo);
//        }

        String krwPrice = tradeService.realKRWPrice(vo.getCurcyCd());
        result.setKrwPrice(krwPrice);
        res.setResultMsg("수령통화 가능금액 조회 성공");
        res.setResultStrCode("000");
        res.setData(result);

        return res;
    }


    /**
     * 2017-11-01 최초 생성 : 판매 등록
     * IF-EX-015
     * 등록자 : 문영민
     *
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trade/sellOrder.dm")
    public @ResponseBody
    CmeResultVO sellOrder(CmeTradeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        rService.RTPVertify(request);
        setLocale(request, response);
        CmeResultVO res = new CmeResultVO();

        if (vo.getApntPhsYn().equals("")) {
            res.setResultMsg("구분값이 없습니다.");
            return res;
        } else if (vo.getUserEmail().equals("")) {
            res.setResultMsg("EMAIL값이 없습니다.");
            return res;
        } else if (vo.getSellCurcyCd().equals("")) {
            res.setResultMsg("판매통화값이 없습니다.");
            return res;
        } /*else if(vo.getRcvCurCd().equals("")) {
			res.setResultMsg("수령통화값이 없습니다.");
			return res;
		}*/ else if (vo.getPhsPrc().equals("")) {
            res.setResultMsg("주문가격값이 없습니다.");
            return res;
        } else if (vo.getPhsAmt().equals("")) {
            res.setResultMsg("주문수량값이 없습니다.");
            return res;
        }

        res = tradeService.sellOrder(vo);

        List list = (List) res.getProceduresResult();
        Map map = (Map) list.get(0);
        String rtnCd = (String) map.get("RTN_CD");
        if (rtnCd != null && rtnCd.equals("1")) {
            res.setResultMsg("판매등록 성공");
            res.setResultStrCode("000");
        } else if (rtnCd != null && rtnCd.equals("-99999")) {
            res.setResultMsg("대량 거래를 실행하여 판매등록에 실패하였습니다.");
            res.setResultStrCode(rtnCd);
        } else if (rtnCd != null && rtnCd.equals("-20091")) {
            res.setResultMsg("구매 현금이 부족합니다.");
            res.setResultStrCode(rtnCd);
        } else if (rtnCd != null && rtnCd.equals("-20090")) {
            res.setResultMsg("구매 포인트가 부족합니다.");
            res.setResultStrCode(rtnCd);
        } else if (rtnCd != null && rtnCd.equals("-20092")) {
            res.setResultMsg("총 금액이 부족합니다.");
            res.setResultStrCode(rtnCd);
        } else if (rtnCd != null && rtnCd.equals("-20095")) {
            res.setResultMsg("판매대기 리스트가 없습니다.");
            res.setResultStrCode(rtnCd);
        } else if (rtnCd != null && rtnCd.equals("-20098")) {
            res.setResultMsg("최대 구매 금액이 부족합니다.");
            res.setResultStrCode(rtnCd);
        } else {
            res.setResultMsg("접속량이 많아 판매에 실패하였습니다.다시 시도해 주세요.");
            res.setResultStrCode(rtnCd);
        }


        try {


            String sysdate = tradeService.selectDate("");
            //판매내역 저장
            if (rtnCd != null && rtnCd.equals("1")) {
                // Y-> 지정가 N-> 시장가
                if (vo.getApntPhsYn().equals("Y")) {

                    SendMsgVO smvo = new SendMsgVO();
                    smvo.setUserEmail(vo.getUserEmail());
                    smvo.setSendYn("N");
                    smvo.setCmmCd("CMMC00000000198");
                    smvo.setMsg(sysdate + "|" + vo.getClientCd() + "|" + makeCoinNm(vo.getSellCurcyCd()) + "|" + vo.getPhsPrc() + "|" + vo.getPhsAmt());
                    pushService.PR_INSUPT_SND_MSG(smvo);

                }
            }


            //체결내역저장
            String rntMsg = (String) map.get("RNT_MSG");
            String orderNo = rntMsg.substring(rntMsg.indexOf("CNSL"), rntMsg.indexOf("CNSL") + 19);
            OrderResVO rvo = new OrderResVO();
            rvo.setCnPhsCode(orderNo);
            if (!"".equals(orderNo)) {
                SendMsgVO smvo = new SendMsgVO();
                smvo.setUserEmail(vo.getUserEmail());
                smvo.setSendYn("N");
                smvo.setCmmCd("CMMC00000000182");
                smvo.setMsg(orderNo + "|" + vo.getClientCd());
                pushService.PR_INSUPT_SND_MSG(smvo);
            }

        } catch (Exception e) {

        }

        res.setData(new HashMap<String, String>());
        return res;
    }


    /**
     * 2017-11-01 최초 생성 : 사용자 구매/판매 미체결 내역
     * IF-EX-016
     * 등록자 : 문영민
     *
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trade/beforeTradingList.dm")
    public @ResponseBody
    CmeResultVO beforeTradingList(@ModelAttribute CmeTradeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();

        List<BeforeTradeVO> list = new ArrayList<BeforeTradeVO>();

        Map<String, Object> map = new HashMap<String, Object>();
        if (vo.getOrderCd().equals("")) {
            res.setResultMsg("구분값이 없습니다.");
            map.put("list", list);
            res.setData(map);
            return res;
        } else if (!vo.getOrderCd().equals(CmmCdConstant.BUY_CD) && !vo.getOrderCd().equals(CmmCdConstant.SELL_CD)) {
            res.setResultMsg("구분값이 올바르지 않습니다");
            map.put("list", list);
            res.setData(map);
            return res;
        } else if (vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일값이 없습니다.");
            map.put("list", list);
            res.setData(map);
            return res;
        } else if (vo.getCurcyCd().equals("")) {
            res.setResultMsg("통화값이 없습니다.");
            map.put("list", list);
            res.setData(map);
        }

        res.setResultMsg("미체결 내역 조회 성공");
        res.setResultStrCode("000");

        int listSize = vo.getListSize();
        if (listSize == 0) {
            vo.setListSize(10);
        }

        list = tradeService.beforeTradingList(vo);

        map.put("list", list);
        res.setData(map);

        return res;
    }


    /**
     * 2017-11-01 최초 생성 : 미체결 취소
     * IF-EX-017
     * 등록자 : 문영민
     *
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trade/tradeCancel.dm")
    public @ResponseBody
    CmeResultVO tradeCancel(@ModelAttribute CmeTradeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {


        rService.RTPVertify(request);
        setLocale(request, response);
        CmeResultVO res = new CmeResultVO();
        //CmeTradeResVO result = new CmeTradeResVO();

        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("ID값이 없습니다.");
            res.setData(new HashMap<String, String>());
            return res;
        } else if (vo.getOrderNo().equals("")) {
            res.setResultMsg("주문번호값이 없습니다.");
            res.setData(new HashMap<String, String>());
            return res;
        } else if (vo.getRegIp().equals("")) {
            res.setResultMsg("아이피가 없습니다.");
            res.setData(new HashMap<String, String>());
            return res;
        }
        //주문번호로 상세조회

        vo.setCmmNm(tradeService.getCmmNm());

        if(vo.getOrderNo().indexOf("CNPH") > -1) {//구매취소
            //int sellNoChk = tradeService.getSellNoChk(vo.getOrderNo());
            Method method = getMarketMethod(tradeService , "getBuyNoChk" , vo.getMkState() , vo.getOrderNo());
            int sellNoChk = (int) method.invoke(tradeService , vo.getOrderNo());
            if(sellNoChk == 0){
                res.setResultStrCode("-9998");
                res.setResultMsg("구매완료된 거래번호입니다.");
                res.setData(new HashMap<String, String>());
                return res;
            }
        }else if(vo.getOrderNo().indexOf("CNSL") > -1){ // 판매취소
            //int buyNoChk = tradeService.getBuyNoChk(vo.getOrderNo());
            Method method = getMarketMethod(tradeService , "getSellNoChk" , vo.getMkState() , vo.getOrderNo());
            int buyNoChk = (int) method.invoke(tradeService , vo.getOrderNo());
            if(buyNoChk == 0){
                res.setResultStrCode("-9997");
                res.setResultMsg("판매완료된 거래번호입니다.");
                res.setData(new HashMap<String, String>());
                return res;
            }
        }else{
            res.setResultStrCode("-9996");
            res.setResultMsg("잘못된 거래번호");
            res.setData(new HashMap<String, String>());
            return res;
        }


        Method method = getMarketMethod(tradeService, "selectOrder", vo.getMkState(), vo);
        CmeTradeReqVO rvo = (CmeTradeReqVO) method.invoke(tradeService, vo);

//        if("ETH".equals(vo.getMkState())) {
//            rvo = tradeService.selectOrderETH(vo);
//        } else if("USDT".equals(vo.getMkState())) {
//            rvo = tradeService.selectOrderUSDT(vo);
//        } else {
//            rvo = tradeService.selectOrder(vo);
//        }

        //db현재시간
        String sysdate = tradeService.selectDate("");
        rvo.setUserEmail(vo.getUserEmail());
        rvo.setRegIp(vo.getRegIp());
        if (rvo.getCncl() != null && rvo.getCncl().equals("")) {

            method = getMarketMethod(tradeService, "orderCalcel", vo.getMkState(), rvo);
            res = (CmeResultVO) method.invoke(tradeService, rvo);

//            if("ETH".equals(vo.getMkState())) {
//                res = tradeService.orderCalcelETH(rvo);
//            } else {
//                res = tradeService.orderCalcel(rvo);
//            }

            List list = (List) res.getProceduresResult();
            Map map = (Map) list.get(0);
            String rtnCd = (String) map.get("RTN_CD");
            if (rtnCd != null && rtnCd.equals("1")) {
                res.setResultMsg("미체결 취소 성공");
                res.setResultStrCode("000");
            } else {
                res.setResultMsg((String) map.get("RNT_MSG"));
                res.setResultStrCode(rtnCd);
            }

            //return res;

            boolean isAuto = false;
            String[] autoUsers =
                    {"wjddlsdud919@gmail.com",
                    "banbanguy@daum.net",
                    "ymin2715@naver.com",
                    "visop02@naver.com",
                    "z01lkss@nate.com",
                    "jbd76@naver.com",
                    "banbanguy@naver.com",
                    "nodo1017@gmail.com",
                    "visop@naver.com",
                    "ryun.kim@cmesoft.co.kr",
                    "z01lkss@naver.com"};
            for(String autoUser : autoUsers) {
                if(vo.getUserEmail().equals(autoUser)) {
                    isAuto = true;
                }
            }
            if(isAuto) {
                res.setData(new HashMap<String, String>());
                return res;
            }

            if (rtnCd != null && rtnCd.equals("1")) {
                //지정가구매 취소메일,sms
                if (vo.getOrderNo().indexOf("CNPH") > -1) {

                    SendMsgVO smvo = new SendMsgVO();
                    smvo.setUserEmail(vo.getUserEmail());
                    smvo.setSendYn("N");
                    smvo.setCmmCd("CMMC00000000168");
                    smvo.setMsg(makeCoinNm(rvo.getCurcyCd()) + "(" + getLocale(request, "mail.common.word1") + ")"
                            + "|" + sysdate + "|" + rvo.getCnPrc()
                            + "|" + rvo.getRemAmt() + "|" + makeCoinNm(rvo.getCurcyCd()) + "|" + vo.getMkState());

                    pushService.PR_INSUPT_SND_MSG(smvo);

                } else {

                    SendMsgVO smvo = new SendMsgVO();
                    smvo.setUserEmail(vo.getUserEmail());
                    smvo.setSendYn("N");
                    smvo.setCmmCd("CMMC00000000169");
                    smvo.setMsg(makeCoinNm(rvo.getCurcyCd()) + "(" + getLocale(request, "mail.common.word1") + ")"
                            + "|" + sysdate + "|" + rvo.getCnPrc()
                            + "|" + rvo.getRemAmt() + "|" + makeCoinNm(rvo.getCurcyCd()) + "|" + vo.getMkState());

                    pushService.PR_INSUPT_SND_MSG(smvo);
                }
            }

        } else {
            res.setResultMsg("이미 취소된 거래입니다.");
            res.setResultStrCode("-9999");
        }


        res.setData(new HashMap<String, String>());

        return res;
    }


    /**
     * 2017-11-01 최초 생성 : 사용자 거래내역
     * IF-EX-018
     * 등록자 : 문영민
     *
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trade/tradeHistory.dm")
    public @ResponseBody
    CmeResultVO tradeHistory(@ModelAttribute CmeTradeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();

        List<CmeTradeResVO> list = new ArrayList<CmeTradeResVO>();
        CmeTradeResVO result = new CmeTradeResVO();
        Map<String, Object> map = new HashMap<String, Object>();


        //orderState코드로 완료,처리중 쿼리 분리


        vo.setFirstIndex((vo.getPageIndex() - 1) * 10);
        vo.setRecordCountPerPage(vo.getPageUnit());

        int listCnt = 0;

        vo.setMkStateDefault(tradeService.getCmmNm());
        Method method = getMarketMethod(tradeService, "tradeHistory", vo.getMkState(), vo);
        list = (List<CmeTradeResVO>) method.invoke(tradeService, vo);
        method = getMarketMethod(tradeService, "tradeHistoryCnt", vo.getMkState(), vo);
        listCnt = (int) method.invoke(tradeService, vo);

//        if("ETH".equals(vo.getMkState())) {
//            list = tradeService.tradeHistoryETH(vo);
//            listCnt = tradeService.tradeHistoryCntETH(vo);
//        } else {
//            list = tradeService.tradeHistory(vo);
//            listCnt = tradeService.tradeHistoryCnt(vo);
//        }

        map.put("list", list);
        map.put("listCnt", listCnt);
        map.put("pageSize", (listCnt - 1) / 10 + 1);
        map.put("pageIndex", vo.getPageIndex());
        map.put("pageUnit", vo.getPageUnit());
        map.put("mkState", vo.getMkState());
        if(list != null && list.size() > 0){
            map.put("nextKey" , list.get(list.size() - 1).getKey01());
        }else{
            map.put("nextKey" , "");
        }

        res.setData(map);
        res.setResultMsg("거래내역 조회 성공");
        res.setResultStrCode("000");

        return res;
    }


    /**
     * 2017-11-01 최초 생성 : 구매가능금액 조회
     * IF-EX-011
     * 등록자 : 문영민
     *
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trade/maxBuyPrice.dm")
    public @ResponseBody
    CmeResultVO maxBuyPrice(@ModelAttribute CmeTradeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();
        BuyPosPrcVO result = new BuyPosPrcVO();

        if (vo.getCurcyCd().equals("")) {
            res.setResultMsg("통화값이 없습니다.");
            res.setData(result);
            return res;
        } else if (vo.getPayCurcyCd().equals("")) {
            res.setResultMsg("결제 통화값이 없습니다.");
            res.setData(result);
            return res;
        } else if (vo.getUserEmail().equals("")) {
            res.setResultMsg("EMAIL값이 없습니다.");
            res.setData(result);
            return res;
        }

        result = tradeService.maxBuyPrice(vo);
        if (result != null) {
            res.setResultMsg("구매메뉴 접근조회 성공");
            res.setResultStrCode("000");
        }
        res.setData(result);

        return res;
    }

    /**
     * 2017-11-01 최초 생성 : 구매 메뉴 접근시 조회
     * IF-EX-009
     * 등록자 : 문영민
     *
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trade/buyMenuView.dm")
    public @ResponseBody
    CmeResultVO buyMenuView(@ModelAttribute CmeTradeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();
        BuyMenuVO result = new BuyMenuVO();

        if (vo.getCurcyCd().equals("")) {
            res.setResultMsg("구매통화값이 없습니다.");
            res.setData(result);
            return res;
        } else if (vo.getUserEmail().equals("")) {
            res.setResultMsg("EMAIL값이 없습니다.");
            res.setData(result);
            return res;
        }

        Method method = getMarketMethod(tradeService, "buyMenuView", vo.getMkState(), vo);
        result = (BuyMenuVO) method.invoke(tradeService, vo);

//        if("ETH".equals(vo.getMkState())) {
//            result = tradeService.buyMenuViewETH(vo);
//        } else {
//            result = tradeService.buyMenuView(vo);
//        }

        if (result != null) {
            res.setResultMsg("구매메뉴 접근조회 성공");
            res.setResultStrCode("000");
        }
        res.setData(result);

        return res;
    }


    /**
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @Method Name  : notConList
     * @작성일 : 2017. 11. 14.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 미체결 주문
     * @서비스ID :
     */
    @RequestMapping(value = "/trade/notConList.dm")
    public @ResponseBody
    CmeResultVO beforeList(TradeParamVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();

        List<NotConListVO> list = new ArrayList<NotConListVO>();
        Map<String, Object> map = new HashMap<String, Object>();

        int listSize = vo.getListSize();
        if (listSize == 0) {
            vo.setListSize(10);
        }

        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("사용자 계정이 없습니다.");
            map.put("list", list);
            res.setData(map);
            return res;
        }

        Method method = getMarketMethod(tradeService, "notConList", vo.getMkState(), vo);
        list = (List<NotConListVO>) method.invoke(tradeService, vo);

//        if("ETH".equals(vo.getMkState())) {
//            list = tradeService.notConListETH(vo); //각코인별 미체결 목록
//        } else {
//            list = tradeService.notConList(vo); //각코인별 미체결 목록
//        }

        res.setResultStrCode("000");
        map.put("list", list);
        res.setData(map);

        return res;
    }

    /**
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @Method Name  : beforeBuyList
     * @작성일 : 2017. 11. 14.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 최근거래내역
     * @서비스ID :
     */
    @RequestMapping(value = "/trade/list.dm")
    public @ResponseBody
    CmeResultVO list(TradeParamVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();

        List<TradeListVO> list = new ArrayList<TradeListVO>();
        Map<String, Object> map = new HashMap<String, Object>();

        int listSize = vo.getListSize();

        if (listSize == 0) {
            listSize = 10;
        }

        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일값이 없습니다.");
            map.put("list", list);
            res.setData(map);
            return res;
        }

        vo.setPageUnit(listSize);
        vo.setFirstIndex((vo.getPageIndex() - 1) * 10);
        vo.setRecordCountPerPage(vo.getPageUnit());

        Method method = getMarketMethod(tradeService, "selectTradeList", vo.getMkState(), vo);
        list = (List<TradeListVO>) method.invoke(tradeService, vo);

//        if("ETH".equals(vo.getMkState())) {
//            list = tradeService.selectTradeListETH(vo);
//        } else {
//            list = tradeService.selectTradeList(vo);
//        }

        res.setResultStrCode("000");
        map.put("list", list);
        res.setData(map);

        return res;
    }


    /**
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @Method Name  : waitTradeList
     * @작성일 : 2017. 11. 14.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 거래현황(구매/판매 대기)
     * @서비스ID :
     */
    @RequestMapping(value = "/trade/waitTradeList.dm")
    public @ResponseBody
    CmeResultVO waitTradeList(TradeParamVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        List<WaitTradeListVO> list = new ArrayList<WaitTradeListVO>();
        Map<String, Object> map = new HashMap<String, Object>();
        WaitTradeListVO result = new WaitTradeListVO();

        int listSize = vo.getListSize();

        if (listSize == 0) {
            listSize = 10;
        }
        if (vo.getCurcyCd().equals("")) {
            res.setResultMsg("통화값이 없습니다.");
            map.put("list", list);
            res.setData(map);
            return res;
        } else if (vo.getTradeCd().equals("")) {
            res.setResultMsg("거래구분값이 없습니다.");
            map.put("list", list);
            res.setData(map);
            return res;
        }

        if(vo.getTradeCd().equals("CMMC00000000168")) { //구매대기
            Method method = getMarketMethod(tradeService, "waitBuyList", vo.getMkState(), vo);
            list = (List<WaitTradeListVO>) method.invoke(tradeService, vo);
        } else if (vo.getTradeCd().equals("CMMC00000000169")){ //판매대기
            Method method = getMarketMethod(tradeService, "waitSellList", vo.getMkState(), vo);
            list = (List<WaitTradeListVO>) method.invoke(tradeService, vo);
        } else {
            res.setResultMsg("거래구분값이 틀립니다.");
            map.put("list", list);
            res.setData(map);
            return res;
        }


        /*if("ETH".equals(vo.getMkState())) {
            if(vo.getTradeCd().equals("CMMC00000000168")) { //구매대기
                list = tradeService.waitBuyListETH(vo);
            }else if(vo.getTradeCd().equals("CMMC00000000169")){ //판매대기
                list = tradeService.waitSellListETH(vo);
            }else{
                res.setResultMsg("거래구분값이 틀립니다.");
                map.put("list", list);
                res.setData(map);
                return res;
            }

        } else {
            if(vo.getTradeCd().equals("CMMC00000000168")) { //구매대기
                list = tradeService.waitBuyList(vo);
            }else if(vo.getTradeCd().equals("CMMC00000000169")){ //판매대기
                list = tradeService.waitSellList(vo);
            }else{
                res.setResultMsg("거래구분값이 틀립니다.");
                map.put("list", list);
                res.setData(map);
                return res;
            }
        }*/

        res.setResultStrCode("000");
        map.put("list", list);
        res.setData(map);

        return res;
    }

    /**
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @Method Name  : compTradeList
     * @작성일 : 2017. 11. 14.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 거래현황(거래 완료)
     * @서비스ID :
     */
    @RequestMapping(value = "/trade/compTradeList.dm")
    public @ResponseBody
    CmeResultVO compTradeList(TradeParamVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        List<CompTradeListVO> list = new ArrayList<CompTradeListVO>();
        Map<String, Object> map = new HashMap<String, Object>();

        int listSize = vo.getListSize();

        if (listSize == 0) {
            listSize = 10;
        }
        if (vo.getCurcyCd().equals("")) {
            res.setResultMsg("통화값이 없습니다.");
            map.put("list", list);
            res.setData(map);
            return res;
        }

        Method method = getMarketMethod(tradeService, "compTradeList", vo.getMkState(), vo);
        list = (List<CompTradeListVO>) method.invoke(tradeService, vo);

//        if("ETH".equals(vo.getMkState())) {
//            list = tradeService.compTradeListETH(vo);
//        } else {
//            list = tradeService.compTradeList(vo);
//        }

        res.setResultStrCode("000");
        map.put("list", list);
        res.setData(map);

        return res;
    }


    @RequestMapping(value = "/trade/autoBuy.dm")
    public @ResponseBody
    CmeResultVO autoBuy(AutoTradeVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();


        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일이 없습니다.");
            res.setData(map);
            return res;
        } else if (vo.getRegIp().equals("")) {
            res.setResultMsg("아이피가 없습니다.");
            res.setData(map);
            return res;
        }

        vo = tradeService.PR_INSUPT20171080(vo);
        List list = (List) vo.getRESULT();
        map = (Map) list.get(0);
        String rtnCd = (String) map.get("RTN_CD");
        if (rtnCd != null && rtnCd.equals("1")) {
            res.setResultStrCode("000");
            res.setResultMsg("자동거래 구매 등록 성공");
        } else {
            res.setResultMsg((String) map.get("RNT_MSG"));
            res.setResultStrCode(rtnCd);
        }

        res.setData(map);

        return res;
    }

    @RequestMapping(value = "/trade/autoSell.dm")
    public @ResponseBody
    CmeResultVO autoSell(AutoTradeVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();


        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일이 없습니다.");
            res.setData(map);
            return res;
        } else if (vo.getRegIp().equals("")) {
            res.setResultMsg("아이피가 없습니다.");
            res.setData(map);
            return res;
        }

        vo = tradeService.PR_INSUPT20171090(vo);
        List list = (List) vo.getRESULT();
        map = (Map) list.get(0);
        String rtnCd = (String) map.get("RTN_CD");
        if (rtnCd != null && rtnCd.equals("1")) {
            res.setResultStrCode("000");
            res.setResultMsg("자동거래 판매 등록 성공");
        } else {
            res.setResultMsg((String) map.get("RNT_MSG"));
            res.setResultStrCode(rtnCd);
        }

        res.setData(map);
        return res;
    }

    @RequestMapping(value = "/trade/getAutoBuy.dm")
    public @ResponseBody
    CmeResultVO getAutoBuy(AutoTradeVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();


        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일이 없습니다.");
            return res;
        }

        List<AutoTradeVO> list = tradeService.getAutoBuy(vo);

        if (list.size() > 0) {
            res.setResultStrCode("000");
            res.setData(vo);
            res.setResultMsg("구매 자동거래 조회 성공");
        } else {
            res.setResultStrCode("000");
            res.setData(new AutoTradeVO());
            res.setResultMsg("자동거래 데이터가 없습니다.");
        }
        map.put("list", list);
        res.setData(map);
        return res;
    }

    @RequestMapping(value = "/trade/getAutoSell.dm")
    public @ResponseBody
    CmeResultVO getAutoSell(AutoTradeVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();


        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일이 없습니다.");
            return res;
        }

        List<AutoTradeVO> list = tradeService.getAutoSell(vo);

        if (list.size() > 0) {
            res.setResultStrCode("000");
            res.setData(vo);
            res.setResultMsg("판매 자동거래 조회 성공");
        } else {
            res.setResultStrCode("000");
            res.setData(new AutoTradeVO());
            res.setResultMsg("자동거래 데이터가 없습니다.");
        }
        map.put("list", list);
        res.setData(map);
        return res;
    }


    @RequestMapping(value = "/trade/delAutoBuy.dm")
    public @ResponseBody
    CmeResultVO delAutoBuy(AutoTradeVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일이 없습니다.");
            return res;
        }

        int r = tradeService.delAutoBuy(vo);

        if (r > 0) {
            res.setResultStrCode("000");
            res.setResultMsg("자동구매 삭제 성공");
        } else {
            res.setResultStrCode("000");
            res.setResultMsg("자동거래 데이터가 없습니다.");
        }
        return res;
    }

    @RequestMapping(value = "/trade/delAutoSell.dm")
    public @ResponseBody
    CmeResultVO delAutoSell(AutoTradeVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일이 없습니다.");
            return res;
        }

        int r = tradeService.delAutoSell(vo);

        if (r > 0) {
            res.setResultStrCode("000");
            res.setResultMsg("자동판매 삭제 성공");
        } else {
            res.setResultStrCode("000");
            res.setResultMsg("자동거래 데이터가 없습니다.");
        }
        return res;
    }


    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }



    @RequestMapping(value = "/trade/insAutoCoin.dm")
    public @ResponseBody
    CmeResultVO insAutoCoin(AutoCoinTradeVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();


        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일이 없습니다.");
            res.setData(map);
            return res;
        } else if (vo.getRegIp().equals("")) {
            res.setResultMsg("아이피가 없습니다.");
            res.setData(map);
            return res;
        }

        res.setResultStrCode("000");
        res.setResultMsg("자동코인교환거래 등록 성공");
        vo.setSn("1");
        try {
            tradeService.insAutoCoinTrade(vo);
        } catch (Exception e) {

            res.setResultMsg("-1");
            res.setResultStrCode("자동코인교환거래 등록 실패");
        }

        return res;
    }


    @RequestMapping(value = "/trade/delAutoCoin.dm")
    public @ResponseBody
    CmeResultVO delAutoCoin(AutoCoinTradeVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();


        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일이 없습니다.");
            res.setData(map);
            return res;
        }

        res.setResultStrCode("000");
        res.setResultMsg("자동코인교환거래 삭제 성공");

        try {
            tradeService.delAutoCoinTrade(vo);
        } catch (Exception e) {

            res.setResultMsg("-1");
            res.setResultStrCode("자동코인교환거래 삭제 실패");
        }

        return res;
    }


    @RequestMapping(value = "/trade/getAutoCoin.dm")
    public @ResponseBody
    CmeResultVO getAutoCoin(AutoCoinTradeVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();


        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("이메일이 없습니다.");
            return res;
        }

        res.setResultStrCode("000");
        res.setResultMsg("자동코인교환거래 조회 성공");

        res.setData(tradeService.getAutoCoinTrade(vo));
        return res;
    }

    @RequestMapping(value = "/trade/getCoinList.dm")
    public @ResponseBody
    CmeResultVO getCoinList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        res.setResultStrCode("000");
        res.setResultMsg("코인 리스트 조회 성공");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", tradeService.getCoinList());
        res.setData(map);
        return res;
    }

    @RequestMapping(value = "/trade/sellOrderMQ.dm")
    public @ResponseBody
    CmeResultVO sellOrderMQ(CmeTradeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        rService.RTPVertify(request);

        //System.out.println("SellOrderMq Start :" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));

        CmeResultVO res = new CmeResultVO();
        res.setResultStrCode("000");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("failCd", "");



        if (vo.getApntPhsYn().equals("")) {
            res.setResultMsg("구분값이 없습니다.");
            resultMap.put("failCd", "999");
            res.setData(resultMap);
            return res;
        } else if (vo.getUserEmail().equals("")) {
            res.setResultMsg("EMAIL값이 없습니다.");
            resultMap.put("failCd", "998");
            res.setData(resultMap);
            return res;
        } else if (vo.getSellCurcyCd().equals("")) {
            res.setResultMsg("판매통화값이 없습니다.");
            resultMap.put("failCd", "997");
            res.setData(resultMap);
            return res;
        } else if (vo.getRcvCurcyCd().equals("")) {
            res.setResultMsg("수령통화값이 없습니다.");
            resultMap.put("failCd", "996");
            res.setData(resultMap);
            return res;
        } else if (vo.getPhsPrc().equals("")) {
            res.setResultMsg("주문가격값이 없습니다.");
            resultMap.put("failCd", "995");
            res.setData(resultMap);
            return res;
        } else if (vo.getPhsAmt().equals("") || Double.parseDouble(vo.getPhsAmt()) == 0) {
            res.setResultMsg("주문수량값이 없습니다.");
            resultMap.put("failCd", "994");
            res.setData(resultMap);
            return res;
        }


        if("A".equals(vo.getAutoTradeType()) && "N".equals(vo.getApntPhsYn())){ // 자동거래인 경우에만 타게됨
            vo.setCurcyCd(vo.getSellCurcyCd());
            String autoCheck = AutoTradeCheck(vo);
            if(!"".equals(autoCheck)){
                res.setResultMsg("자동거래 판매에 실패했습니다.");
                resultMap.put("failCd" , autoCheck);
                res.setData(resultMap);
                return res;
            }
        }


        try {
            if(Double.parseDouble(vo.getPhsPrc()) <= 0) {
                res.setResultMsg("주문가격이 0보다 작거나 같음.");
                return res;
            }
        } catch (Exception e) {
            res.setResultMsg("주문가격이 숫자형식 아님.");
            return res;
        }

        try {
            if(Double.parseDouble(vo.getPhsAmt()) <= 0) {
                res.setResultMsg("주문수량이 0보다 작거나 같음.");
                return res;
            }
        } catch (Exception e) {
            res.setResultMsg("주문수량이 숫자형식 아님.");
            return res;
        }

        /*DecimalFormat form = new DecimalFormat("#.########");
        vo.setPhsAmt(form.format(vo.getPhsAmt()));*/

        CmeTradeReqVO reqVO = new CmeTradeReqVO();
        reqVO.setUserEmail(vo.getUserEmail());
        reqVO.setSellBuyCd("S");
        reqVO.setCurcyCd(vo.getSellCurcyCd());
        reqVO.setAmount(vo.getPhsAmt());
        reqVO.setRegIp(vo.getRegIp());

        Method method = getMarketMethod(tradeService, "orderMQ", vo.getMkState(), reqVO);
        reqVO = (CmeTradeReqVO) method.invoke(tradeService, reqVO);

//        if("ETH".equals(vo.getMkState())) {
//            reqVO = tradeService.orderMQETH(reqVO);
//        } else {
//            reqVO = tradeService.orderMQ(reqVO);
//        }

        List list = (List) reqVO.getRESULT();
        Map map = (Map) list.get(0);
        String rtnCd = (String) map.get("RTN_CD");

        RabbitmqClient client = null;
        RabbitmqClientETH clientEth = null;
        RabbitmqClientUSDT clientUSDT = null;
        RabbitmqClientBTC clientBTC = null;

        if(rtnCd != null && rtnCd.contains("ERC")) {

            JSONObject jobj = new JSONObject();
            jobj.put("clientType", vo.getClientCd());
            jobj.put("prePaymentKey", rtnCd);
            jobj.put("userEmail", vo.getUserEmail());
            jobj.put("userRegIp", vo.getRegIp());
            jobj.put("orderType", "S");
            jobj.put("tradeType", vo.getApntPhsYn());
            jobj.put("orderCode", vo.getSellCurcyCd());
            jobj.put("orderCodeName", makeCoinNm(vo.getSellCurcyCd()));
            jobj.put("orderPrc", vo.getPhsPrc());
            jobj.put("orderCnt", vo.getPhsAmt());
            jobj.put("payCode", vo.getRcvCurcyCd());
            String value = jobj.toString();

            System.out.println("vo.getMkState() : " + vo.getMkState());
            System.out.println("SellOrderMq... :" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));

            if("ETH".equals(vo.getMkState())) {
                System.out.println("ETH rabbit");
                clientEth = RabbitmqClientETH.getinstance();
                clientEth.sendMsg(value);
            } else if("USDT".equals(vo.getMkState())) {
                System.out.println("USDT rabbit");
                clientUSDT = RabbitmqClientUSDT.getinstance();
                clientUSDT.sendMsg(value);
            } else if("BTC".equals(vo.getMkState())) {
                System.out.println("BTC rabbit");
                clientBTC = RabbitmqClientBTC.getinstance();
                clientBTC.sendMsg(value);
            } else {
                System.out.println("KRW rabbit");
                client = RabbitmqClient.getinstance();
                client.sendMsg(value);
            }

            //System.out.println("SellOrderMq End :" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));
            res.setResultMsg("판매등록요청 성공");
            res.setResultStrCode("000");
        }

        res.setData(resultMap);
        return res;
    }

    @RequestMapping(value = "/trade/buyOrderMQ.dm")
    public @ResponseBody
    CmeResultVO buyOrderMQ(CmeTradeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        rService.RTPVertify(request);

        //System.out.println("buyOrderMq Start :" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));

        CmeResultVO res = new CmeResultVO();
        res.setResultStrCode("000");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("failCd", "");

        if (vo.getApntPhsYn().equals("")) {
            res.setResultMsg("구분값이 없습니다.");
            resultMap.put("failCd", "999");
            res.setData(resultMap);
            return res;
        } else if (vo.getApntPhsCd().equals("")) {
            res.setResultMsg("거래내용값이 없습니다.");
            resultMap.put("failCd", "998");
            res.setData(resultMap);
            return res;
        } else if (vo.getUserEmail().equals("")) {
            res.setResultMsg("Email값이 없습니다.");
            resultMap.put("failCd", "997");
            res.setData(resultMap);
            return res;
        } else if (vo.getBuyCurcyCd().equals("")) {
            res.setResultMsg("구매통화값이 없습니다.");
            resultMap.put("failCd", "996");
            res.setData(resultMap);
            return res;
        } else if (vo.getPhsPrc().equals("") || (Double.parseDouble(vo.getPhsPrc()) == 0)) {
            res.setResultMsg("주문가격값이 없습니다.");
            resultMap.put("failCd", "995");
            res.setData(resultMap);
            return res;
        }

        /*if("A".equals(vo.getAutoTradeType()) && "N".equals(vo.getApntPhsYn())){ // 자동거래인 경우에만 타게됨
            vo.setCurcyCd(vo.getBuyCurcyCd());
            vo.setPhsAmt(vo.getUsePrc());
            String autoCheck = AutoTradeCheck(vo);
            if(!"".equals(autoCheck)){
                res.setResultMsg("자동거래 구매에 실패했습니다.");
                resultMap.put("failCd" , autoCheck);
                res.setData(resultMap);
                return res;
            }
        }*/

        try {
            if(Double.parseDouble(vo.getPhsPrc()) <= 0) {
                res.setResultMsg("주문가격이 0보다 작거나 같음.");
                resultMap.put("failCd", "994");
                res.setData(resultMap);
                return res;
            }
        } catch (Exception e) {
            res.setResultMsg("주문가격이 숫자형식 아님.");
            resultMap.put("failCd", "993");
            res.setData(resultMap);
            return res;
        }

        try {
            if(Double.parseDouble(vo.getPhsAmt()) <= 0) {
                res.setResultMsg("주문수량이 0보다 작거나 같음.");
                resultMap.put("failCd", "992");
                res.setData(resultMap);
                return res;
            }
        } catch (Exception e) {
            res.setResultMsg("주문수량이 숫자형식 아님.");
            resultMap.put("failCd", "991");
            res.setData(resultMap);
            return res;
        }

        try {
            if(Double.parseDouble(vo.getUsePrc()) <= 0) {
                res.setResultMsg("총 주문금액이 0보다 작거나 같음.");
                resultMap.put("failCd", "990");
                res.setData(resultMap);
                return res;
            }
        } catch (Exception e) {
            res.setResultMsg("총 주문금액이 숫자형식 아님.");
            resultMap.put("failCd", "989");
            res.setData(resultMap);
            return res;
        }

        CmeTradeReqVO reqVO = new CmeTradeReqVO();
        reqVO.setUserEmail(vo.getUserEmail());
        reqVO.setSellBuyCd("B");
        reqVO.setCurcyCd(vo.getBuyCurcyCd());
        reqVO.setAmount(vo.getUsePrc());
        reqVO.setRegIp(vo.getRegIp());

        Method method = getMarketMethod(tradeService, "orderMQ", vo.getMkState(), reqVO);
        reqVO = (CmeTradeReqVO) method.invoke(tradeService, reqVO);

//        if("ETH".equals(vo.getMkState())) {
//            reqVO = tradeService.orderMQETH(reqVO);
//        } else {
//            reqVO = tradeService.orderMQ(reqVO);
//        }

        List list = (List) reqVO.getRESULT();
        Map map = (Map) list.get(0);
        String rtnCd = (String) map.get("RTN_CD");

        RabbitmqClient client = null;
        RabbitmqClientETH clientEth = null;
        RabbitmqClientUSDT clientUSDT = null;
        RabbitmqClientBTC clientBTC = null;

        if(rtnCd != null && rtnCd.contains("ERC")) {

            JSONObject jobj = new JSONObject();
            jobj.put("clientType", vo.getClientCd());
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
            jobj.put("payCode", vo.getPayCurcyCd());
            String value = jobj.toString();

            System.out.println("vo.getMkState() : " + vo.getMkState());
            System.out.println("buyOrderMq... :" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));


            if("ETH".equals(vo.getMkState())) {
                System.out.println("ETH rabbit");
                clientEth = RabbitmqClientETH.getinstance();
                clientEth.sendMsg(value);
            } else if("USDT".equals(vo.getMkState())) {
                System.out.println("USDT rabbit");
                clientUSDT = RabbitmqClientUSDT.getinstance();
                clientUSDT.sendMsg(value);
            } else if("BTC".equals(vo.getMkState())) {
                System.out.println("BTC rabbit");
                clientBTC = RabbitmqClientBTC.getinstance();
                clientBTC.sendMsg(value);
            } else {
                System.out.println("KRW rabbit");
                client = RabbitmqClient.getinstance();
                client.sendMsg(value);
            }

            //System.out.println("buyOrderMq End :" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));
            res.setResultMsg("구매등록요청 성공");


        }

        res.setData(resultMap);
        return res;
    }


    @RequestMapping(value = "/trade/getMaxMinAmt.dm")
    public @ResponseBody
    CmeResultVO getMaxMinAmt(CoinMinMaxAmtVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        res.setResultStrCode("000");
        res.setResultMsg("코인 미체결 최고, 최저 금액 조회 성공");

        vo = tradeService.getMaxMinAmt(vo);
        res.setData(vo);
        return res;

    }

    @RequestMapping(value = "/trade/getPlusList.dm")
    public @ResponseBody
    CmeResultVO getPlusList(TradeParamVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", tradeService.getPlusList(vo));
        resultVO.setData(map);
        resultVO.setResultMsg("시세차익 조회 성공");
        resultVO.setResultStrCode("000");

        return resultVO;

    }

    @RequestMapping(value = "/trade/getTimeList.dm")
    public @ResponseBody
    CmeResultVO getTimeList(TradeParamVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", tradeService.getTimeList(vo));
        resultVO.setData(map);
        resultVO.setResultMsg("기간별 시세차익 조회 성공");
        resultVO.setResultStrCode("000");

        return resultVO;

    }


    /**
     * 2017-11-01 최초 생성 : 코인스왑
     * IF-EX-015
     * 등록자 : 문영민
     *
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trade/coinSwapMQ.dm")
    public @ResponseBody
    CmeResultVO coinSwapMQ(CmeTradeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        rService.RTPVertify(request);
        setLocale(request, response);
        CmeResultVO res = new CmeResultVO();


        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("EMAIL값이 없습니다.");
            return res;
        } else if (vo.getBuyCurcyCd().equals("")) {
            res.setResultMsg("구매통화값이 없습니다.");
            return res;
        } else if (vo.getPayCurcyCd().equals("")) {
            res.setResultMsg("결제통화값이 없습니다.");
            return res;
        } else if (vo.getPhsAmt().equals("")) {
            res.setResultMsg("주문수량이 없습니다.");
            return res;
        } else if (vo.getRegIp().equals("")) {
            res.setResultMsg("IP가 없습니다.");
            return res;
        }

        CmeTradeReqVO reqVO = new CmeTradeReqVO();
        reqVO.setUserEmail(vo.getUserEmail());
        reqVO.setSellBuyCd("S");
        reqVO.setCurcyCd(vo.getPayCurcyCd());
        reqVO.setAmount(vo.getPhsAmt());
        reqVO.setRegIp(vo.getRegIp());
        reqVO = tradeService.orderMQ(reqVO);

        List list = (List) reqVO.getRESULT();
        Map map = (Map) list.get(0);
        String rtnCd = (String) map.get("RTN_CD");

        if(rtnCd != null && rtnCd.contains("ERC")) {
            JSONObject jobj = new JSONObject();
            jobj.put("clientType", vo.getClientCd());
            jobj.put("prePaymentKey", rtnCd);
            jobj.put("userEmail", vo.getUserEmail());
            jobj.put("userRegIp", vo.getRegIp());
            jobj.put("orderType", "S");
            jobj.put("tradeType", "N");
            jobj.put("orderCode", vo.getBuyCurcyCd());
            jobj.put("orderCodeName", makeCoinNm(vo.getBuyCurcyCd()));
            jobj.put("orderPrc", "1");
            jobj.put("orderCnt", vo.getPhsAmt());
            jobj.put("payCode", vo.getPayCurcyCd());
            String value = jobj.toString();

            RabbitmqClient client = RabbitmqClient.getinstance();
            //client.createChannel();
            client.sendMsg(value);
            //client.close();
            res.setResultMsg("스왑등록요청 성공");
            res.setResultStrCode("000");
        }
        res.setData(new HashMap<String, String>());
        return res;

    }

    /**
     * 2018-05-29 최초 생성 : 환율리스트
     * IF-EX-015
     * 등록자 : 임우빈
     *
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trade/getExRate.dm")
    public @ResponseBody
    CmeResultVO getExRate(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String,Object> resultMap = new HashMap<String,Object>();
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd","");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("사용자 이메일이 없습니다.");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }

        ExRateVO evo = tradeService.getExRate(vo);

        if(evo != null){
            resultMap.put("cryCode",evo.getCryCode());
            resultMap.put("exRate",evo.getExRate());
            resultVO.setResultMsg("환율 정보 출력");
        }else{
            resultVO.setResultMsg("환율 정보가 없습니다.");
            resultMap.put("failCd" , "999");

        }

        resultVO.setData(resultMap);
        return resultVO;
    }


    @RequestMapping(value="/trade/getTodaySellAmt.dm")
    public @ResponseBody CmeResultVO getTodaySellAmt(@ModelAttribute TradeParamVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();
        res.setResultStrCode("000");
        Map map = new HashMap<String, String>();
        map.put("failCd", "");
        if(vo.getCurcyCd().equals("")) {
            res.setResultMsg("구매통화값이 없습니다.");
            map.put("failCd", "999");
            res.setData(map);
            return res;
        } else if(vo.getUserEmail().equals("")) {
            res.setResultMsg("EMAIL값이 없습니다.");
            map.put("failCd", "998");
            res.setData(map);
            return res;
        }

        Double sellAmt = sgcSellAmt(vo);

        map.put("sellAmt", sellAmt);        res.setData(map);

        return res;
    }

    public Double sgcSellAmt(TradeParamVO vo) throws Exception{
        String KRWResult = tradeService.getTodaySellAmtKRW(vo);
        String ETHResult = tradeService.getTodaySellAmtETH(vo);
        String USDTResult = tradeService.getTodaySellAmtUSDT(vo);
        String BTCResult = tradeService.getTodaySellAmtBTC(vo);

        double KRWvalue = 0;
        double ETHvalue = 0;
        double USDTvalue = 0;
        double BTCvalue = 0;

        if(KRWResult != null){
            KRWvalue = Double.parseDouble(KRWResult);
        }
        if(ETHResult != null){
            ETHvalue = Double.parseDouble(ETHResult);
        }
        if(USDTResult != null){
            USDTvalue = Double.parseDouble(USDTResult);
        }
        if(BTCResult != null){
            BTCvalue = Double.parseDouble(BTCResult);
        }
        double result = 0;

        result = KRWvalue + ETHvalue + USDTvalue + BTCvalue;

        if("".equals(result)){
            result = 0;
        }


        return result;
    }


    @RequestMapping(value = "/trade/getAmtCheck.dm")
    public @ResponseBody
    CmeResultVO getAmtCheck(CmeTradeReqVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        if("".equals(vo.getUserEmail())){           // 사용자 이메일
            resultVO.setResultMsg("사용자 이메일이 없습니다.");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getMkState())){       // 마켓 타입
            resultVO.setResultMsg("마켓 타입이 없습니다.");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getCurcyCd())){       // 코인 코드
            resultVO.setResultMsg("코인 코드가 없습니다.");
            resultMap.put("failCd" , "997");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getTradeType())){     // 구매: B , 판매: S
            resultVO.setResultMsg("거래타입이 없습니다.");
            resultMap.put("failCd" , "996");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getPhsAmt())){        // 수량
            resultVO.setResultMsg("거래수량이 없습니다.");
            resultMap.put("failCd" , "995");
            resultVO.setData(resultMap);
            return resultVO;
        }

        CoinMinMaxVO coinVO = tradeService.selectCoinTradeCheck(vo);
        resultMap.put("buyLmt1Once" , coinVO.getBuyLmt1Once());
        resultMap.put("buyLmt1Day" , coinVO.getBuyLmt1Day());
        resultMap.put("selLmt1Once" , coinVO.getSelLmt1Once());
        resultMap.put("selLmt1Day" , coinVO.getSelLmt1Day());

        TradeParamVO remVO = new TradeParamVO();
        remVO.setUserEmail(vo.getUserEmail());
        remVO.setCurcyCd(vo.getCurcyCd());

       String market = "";
       if("CMMC00000000204".equals(vo.getMkState())){
            market = tradeService.getCmmNm();
       }else if("CMMC00000000205".equals(vo.getMkState())){
            market = "BTC";
       }else if("CMMC00000000206".equals(vo.getMkState())){
            market = "ETH";
       }else if("CMMC00000001086".equals(vo.getMkState())){
            market = "USDT";
       }



        if("B".equals(vo.getTradeType())){
            if(Double.parseDouble(vo.getPhsAmt()) > Double.parseDouble(coinVO.getBuyLmt1Once()) && !"999999999".equals(coinVO.getBuyLmt1Once())){
                resultVO.setResultMsg("1회 구매한도를 초과했습니다.");
                resultMap.put("failCd", "994");
                resultVO.setData(resultMap);
                return resultVO;
            }else{
                Method method = getMarketMethod(tradeService, "getTodayBuyAmt", market, remVO);
                String buyAmt = (String) method.invoke(tradeService, remVO);

                if(buyAmt != null){
                    if(Double.parseDouble(buyAmt) + Double.parseDouble(vo.getPhsAmt()) > Double.parseDouble(coinVO.getBuyLmt1Day()) && !"999999999".equals(coinVO.getBuyLmt1Day())){
                        resultVO.setResultMsg("1일 구매한도를 초과했습니다.");
                        resultMap.put("failCd", "993");
                        resultVO.setData(resultMap);
                        return resultVO;
                    }
                }
            }
        }else if("S".equals(vo.getTradeType())){
            if(Double.parseDouble(vo.getPhsAmt()) > Double.parseDouble(coinVO.getSelLmt1Once()) && !"999999999".equals(coinVO.getSelLmt1Once())){
                resultVO.setResultMsg("1회 판매한도를 초과했습니다.");
                resultMap.put("failCd", "992");
                resultVO.setData(resultMap);
                return resultVO;
            }else{
                Method method = getMarketMethod(tradeService, "getTodaySellAmt", market, remVO);
                String sellAmt = (String) method.invoke(tradeService, remVO);

                if(sellAmt != null){
                    if(Double.parseDouble(sellAmt) + Double.parseDouble(vo.getPhsAmt()) > Double.parseDouble(coinVO.getSelLmt1Day()) && !"999999999".equals(coinVO.getSelLmt1Day())){
                        resultVO.setResultMsg("1일 판매한도를 초과했습니다.");
                        resultMap.put("failCd" , "991");
                        resultVO.setData(resultMap);
                        return resultVO;
                    }
                }
            }
        }


        resultVO.setData(resultMap);
        return resultVO;
    }

    public String AutoTradeCheck(CmeTradeReqVO vo) throws Exception{
        String status = "";
        String market = "";
        String defaultCdNm = tradeService.getCmmNm(); // KRW
        if(defaultCdNm.equals(vo.getMkState())){
            market = defaultCdNm;
            vo.setMkState("CMMC00000000204");
        }else if("BTC".equals(vo.getMkState())){
            market = "BTC";
            vo.setMkState("CMMC00000000205");
        }else if("ETH".equals(vo.getMkState())){
            market = "ETH";
            vo.setMkState("CMMC00000000206");
        }else if("USDT".equals(vo.getMkState())){
            market = "USDT";
            vo.setMkState("CMMC00000001086");
        }

        CoinMinMaxVO coinVO = tradeService.selectCoinTradeCheck(vo);

        TradeParamVO remVO = new TradeParamVO();
        remVO.setUserEmail(vo.getUserEmail());
        remVO.setCurcyCd(vo.getCurcyCd());

        if("B".equals(vo.getTradeType())){
            if(Double.parseDouble(vo.getPhsAmt()) > Double.parseDouble(coinVO.getBuyLmt1Once()) && !"999999999".equals(coinVO.getBuyLmt1Once())){
                status = "888";
                return status;
            }else{
                Method method = getMarketMethod(tradeService, "getTodayBuyAmt", market, remVO);
                String buyAmt = (String) method.invoke(tradeService, remVO);

                if(buyAmt != null){
                    if(Double.parseDouble(buyAmt) + Double.parseDouble(vo.getPhsAmt()) > Double.parseDouble(coinVO.getBuyLmt1Day()) && !"999999999".equals(coinVO.getBuyLmt1Day())){
                       status = "887";
                       return status;
                    }
                }
            }
        }else if("S".equals(vo.getTradeType())){
            if(Double.parseDouble(vo.getPhsAmt()) > Double.parseDouble(coinVO.getSelLmt1Once()) && !"999999999".equals(coinVO.getSelLmt1Once())){
                status = "886";
                return status;
            }else{
                Method method = getMarketMethod(tradeService, "getTodaySellAmt", market, remVO);
                String sellAmt = (String) method.invoke(tradeService, remVO);

                if(sellAmt != null){
                    if(Double.parseDouble(sellAmt) + Double.parseDouble(vo.getPhsAmt()) > Double.parseDouble(coinVO.getSelLmt1Day()) && !"999999999".equals(coinVO.getSelLmt1Day())){
                        status = "885";
                        return status;
                    }
                }
            }
        }



        return status;
    }

    @RequestMapping(value = "/trade/getFreeWithdraw.dm")
    public @ResponseBody CmeResultVO getFreeWithdraw(CmeTradeReqVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO res = new CmeResultVO();
        Map<String , Object> map = new HashMap<String , Object>();
        res.setResultStrCode("000");
        map.put("failCd", "");

        if("".equals(vo.getUserEmail())){           // 사용자 이메일
            res.setResultMsg("사용자 이메일이 없습니다.");
            map.put("failCd" , "999");
            res.setData(map);
            return res;
        }

        List<FreeWithdrawVO> list = tradeService.getFreeWithdraw(vo);
        map.put("list" , list);
        res.setData(map);
        res.setResultMsg("정상 처리되었습니다.");
        return res;
    }

    @RequestMapping(value = "/trade/getFeeSale.dm")
    public @ResponseBody CmeResultVO getFeeSale(CmeTradeReqVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO res = new CmeResultVO();
        Map<String , Object> map = new HashMap<String , Object>();
        res.setResultStrCode("000");
        map.put("failCd", "");

        if("".equals(vo.getUserEmail())){           // 사용자 이메일
            res.setResultMsg("사용자 이메일이 없습니다.");
            map.put("failCd" , "999");
            res.setData(map);
            return res;
        }
        List<FeeSaleVO> list = tradeService.getFeeSale(vo);
        map.put("data" , list);
        res.setData(map);
        res.setResultMsg("정상 처리되었습니다.");
        return res;
    }

    //마켓별 시세정보와 호가단위를 가지고 메모리에저장
    @RequestMapping(value = "/trade/getArcTest.dm")
    public @ResponseBody CmeResultVO getArcTest(CmeTradeReqVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO res = new CmeResultVO();
        Map<String , Object> map = new HashMap<String , Object>();
        res.setResultStrCode("000");
        map.put("failCd", "");

        String[] currencys = {"BTC","ETH","BCH","XRP","QTUM","SGC","BTG","DASH","LTC","HDAC","SECRET","VSTC"};

        for(String currency : currencys) {
            String result = tradeService.findCoinTradePrc(currency);
            System.out.println(currencys);
            map.put(currency, result);
        }


        res.setData(map);
        res.setResultMsg("정상 처리되었습니다.");
        return res;
    }

    @RequestMapping(value = "/trade/getArc.dm")
    public @ResponseBody CmeResultVO getArc(UserTradeCheckVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO res = new CmeResultVO();
        Map<String , Object> map = new HashMap<String , Object>();
        res.setResultStrCode("000");
        map.put("failCd", "");

        if(vo.getMkState().equals("")){
            Map<String , Object> KRWMap = new HashMap<String , Object>();
            Map<String , Object> BTCMap = new HashMap<String , Object>();
            Map<String , Object> ETHMap = new HashMap<String , Object>();
            Map<String , Object> USDTMap = new HashMap<String , Object>();

            vo.setMkState("CMMC00000000204");
            ArrayList<ArcMarketVO> kRWList = (ArrayList<ArcMarketVO>) tradeService.getArcMarket(vo);
            vo.setMkState("CMMC00000000205");
            ArrayList<ArcMarketVO> BTCList = (ArrayList<ArcMarketVO>) tradeService.getArcMarket(vo);
            vo.setMkState("CMMC00000000206");
            ArrayList<ArcMarketVO> ETHList = (ArrayList<ArcMarketVO>) tradeService.getArcMarket(vo);
            vo.setMkState("CMMC00000001086");
            ArrayList<ArcMarketVO> USDTList = (ArrayList<ArcMarketVO>) tradeService.getArcMarket(vo);

            ArrayList<Map<String , Object>> alist = new ArrayList<Map<String, Object>>();

            KRWMap.put("marketCd" , "CMMC00000000204");
            BTCMap.put("marketCd" , "CMMC00000000205");
            ETHMap.put("marketCd" , "CMMC00000000206");
            USDTMap.put("marketCd" , "CMMC00000001086");

            KRWMap.put("marketNm" , tradeService.getCmmNm());
            BTCMap.put("marketNm" , "BTC");
            ETHMap.put("marketNm" , "ETH");
            USDTMap.put("marketNm" , "USDT");

            KRWMap.put("list" , kRWList);
            BTCMap.put("list" , BTCList);
            ETHMap.put("list" , ETHList);
            USDTMap.put("list" , USDTList);

            alist.add(KRWMap);
            alist.add(BTCMap);
            alist.add(ETHMap);
            alist.add(USDTMap);

            map.put("list" , alist);

        }else {
            // 코인코드가 있으면 다르게 처리
            Map<String , Object> MarketMap = new HashMap<String , Object>();
            ArrayList<ArcMarketVO> MarketList = (ArrayList<ArcMarketVO>) tradeService.getArcMarket( vo );

            String CoinNm = "";

            if("CMMC00000000204".equals( vo.getMkState() )){
                CoinNm = tradeService.getCmmNm();
            }else if("CMMC00000000205".equals( vo.getMkState() )){
                CoinNm = "BTH";
            }else if("CMMC00000000206".equals( vo.getMkState() )){
                CoinNm = "ETH";
            }else if("CMMC00000001086".equals( vo.getMkState() )){
                CoinNm = "USDT";
            }

            map.put("marketCd" , vo.getMkState());
            map.put("marketNm" , CoinNm);
            map.put("list" , MarketList);

        }

/*
        List<ArcMarketVO> list = tradeService.getCoinTradePrc();
        System.out.println(list);
        for(int i=0; i < list.size() ;i++) {
            System.out.println("cnKndCd : " + list.get(i).getMkState()+ " // coinNm : " + list.get(i).getCnKndNm() + " // cnPrice : " + list.get(i).getCoinPrc() + " // krw : " + list.get(i).getKrw() + " // btc : " + list.get(i).getBtc() + " // eth : " + list.get(i).getEth() +  " // usdt : " + list.get(i).getUsdt() );
            String coinArc = "cnKndCd : " + list.get(i).getCnKndCd()+ " // coinNm : " + list.get(i).getCnKndNm() + " // cnPrice : " + list.get(i).getCoinPrc() + " // krw : " + list.get(i).getKrw() + " // btc : " + list.get(i).getBtc() + " // eth : " + list.get(i).getEth() +  " // usdt : " + list.get(i).getUsdt();
            map.put(list.get(i).getCnKndNm(), coinArc );
        }
        */

        res.setData(map);
        res.setResultMsg("정상 처리되었습니다.");
        return res;
    }
}
