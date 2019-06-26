package com.bitkrx.api.moaCard.control;

import com.bitkrx.api.auth.service.LoginService;
import com.bitkrx.api.auth.service.RTPService;
import com.bitkrx.api.common.service.CoinInfoService;
import com.bitkrx.api.common.vo.CashBalanceVO;
import com.bitkrx.api.common.vo.CoinBalanceVO;
import com.bitkrx.api.mail.service.MailApiService;
import com.bitkrx.api.main.vo.CmeRealTimeCoinPriceResVO;
import com.bitkrx.api.main.vo.CmeUserAssetReqVO;
import com.bitkrx.api.moaCard.service.MoaCardService;
import com.bitkrx.api.moaCard.vo.*;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.trade.service.DepositService;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.trade.vo.CmeTradeReqVO;
import com.bitkrx.api.trade.vo.DepositVO;
import com.bitkrx.api.trade.vo.TrnInfoVO;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.exception.CmeApplicationException;
import com.bitkrx.config.util.*;
import com.bitkrx.config.vo.SendInfoVO;
import com.bitkrx.core.util.HttpComLib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/card")
public class MoaCardController extends CmeDefaultExtendController {

    @Autowired
    MoaCardService moaCardService;

    @Autowired
    RTPService rService;

    @Autowired
    MailApiService mailService;

    @Autowired
    SmsApiService smsApiService;

    @Autowired
    LoginService loginService;

    @Autowired
    TradeService tradeService;

    @Autowired
    DepositService depositService;

    @Autowired
    UserService userService;

    @Autowired
    LocaleResolver localeResolver;

    @Autowired
    CoinInfoService coinInfoService;

    Security security = Security.getinstance();

    @RequestMapping(value = "/insReqCardInfo.dm")
    public @ResponseBody
    CmeResultVO insCardReqInfo(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");
        resultMap.put("orderId", "");


        if ("".equals(vo.getUserEmail())) {
            resultMap.put("failCd", "998");
        } else if ("".equals(vo.getPostCd())) {
            resultMap.put("failCd", "997");
        } else if ("".equals(vo.getAdrs())) {
            resultMap.put("failCd", "996");
        } else if ("".equals(vo.getDtlAdrs())) {
            resultMap.put("failCd", "995");
        } else if ("".equals(vo.getSendMthCd())) {
            resultMap.put("failCd", "994");
        } else if ("".equals(vo.getReqType())) {
            resultMap.put("failCd", "992");
        } else if (!"82".equals(moaCardService.getUserNation(vo.getUserEmail()))) {
            resultMap.put("failCd", "990");
        } else if ("".equals(vo.getUserMobile())) {
            resultMap.put("failCd", "989");
        } else if ("".equals(vo.getUserNm())) {
            resultMap.put("failCd", "988");
        } else if ("".equals(vo.getInMthCd())) {
            resultMap.put("failCd", "986");
        } else if ("".equals(vo.getAmount())) {
            resultMap.put("failCd", "985");
        }

        if ("1".equals(vo.getSendMthCd())) {
            String contents = "안녕하세요. 플랜비트 국제 거래소입니다. \r\n" +
                    "플랜비트 선불카드 신청이 완료 되었습니다.\r\n" +
                    "선불카드는 [서울 강남구] 또는 [서울 선릉] 지점에서 수령 가능합니다.\r\n" +
                    "지점 위치는 고객센터 > 가맹점 안내를 통해 확인이 가능합니다.\r\n" +
                    "감사합니다.";

            // 문자발송
            SendInfoVO svo = new SendInfoVO();
            String userMobile = loginService.getUserMobile(vo.getUserEmail());
            svo.setContents(contents);
            //필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)
            svo.setMobile_info(userMobile);
            //svo.setClientCd(clientNm);
            svo.setRegIp(getIp(request, "127.0.0.1"));
            svo.setUserEmail(vo.getUserEmail());
            smsApiService.sendSms(svo, 0);
            //문자발송

            //푸시발송
            svo.setContents("카드 신청이 완료되었습니다.");
            smsApiService.sendSms(svo);
        }

        int adyCnt = moaCardService.selectUserRegInfo(vo);
        if (adyCnt > 0) { //이미 같은 휴대폰번호와 같은 이름으로 신청 및 등록
            resultMap.put("failCd", "987");
        }

        int reqCnt = moaCardService.userReqCardCnt(vo.getUserEmail());
        if (reqCnt >= 5) { //5개 이상 신청불가
            resultMap.put("failCd", "993");
        }


        try {
            if ("".equals(resultMap.get("failCd"))) {
                String orderId = moaCardService.getOrderIdKey();
                vo.setOrderId(orderId);
                moaCardService.insCardReqInfo(vo);
                resultMap.put("orderId", orderId);

                /*문자발송*//*
                SendInfoVO svo = new SendInfoVO();
                svo.setContents(contents);
//                필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)
                svo.setMobile_info(vo.getUserMobile());
                //svo.setClientCd(clientNm);
                svo.setRegIp(getIp(request, "127.0.0.1"));
                svo.setUserEmail(vo.getUserEmail());
                smsApiService.sendSms(svo, 0);
//                문자발송


                //푸시발송
                svo.setContents("카드 신청이 완료되었습니다.");
                smsApiService.sendSms(svo);*/

            }
        } catch (Exception e) {
            resultMap.put("failCd", "999");
        }
        resultVO.setData(resultMap);

        return resultVO;
    }


    @RequestMapping(value = "/checkUserMobileName.dm")
    public @ResponseBody
    CmeResultVO checkUserMobileName(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");


        if ("".equals(vo.getUserMobile())) {
            resultMap.put("failCd", "989");
        } else if ("".equals(vo.getUserNm())) {
            resultMap.put("failCd", "988");
        }

        int adyCnt = moaCardService.selectUserRegInfo(vo);
        if (adyCnt > 0) { //이미 같은 휴대폰번호와 같은 이름으로 신청 및 등록
            resultMap.put("failCd", "987");
        }

        resultVO.setData(resultMap);

        return resultVO;
    }


    @RequestMapping(value = "/uptCardAtmPwd.dm")
    public @ResponseBody
    CmeResultVO uptCardAtmPwd(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");


        if ("".equals(vo.getCardNum())) {
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            return resultVO;
        } else if ("".equals(vo.getAtmPwd())) {
            resultMap.put("failCd", "998");
            resultVO.setData(resultMap);
            return resultVO;
        }

        System.out.println("vo.getCardNum() : " + vo.getCardNum() + " //vo.getAtmPwd() : " + vo.getAtmPwd());
        System.out.println("vo.getClientPe() : " + vo.getClientPe());
        try {
            SeedXUtil seedXUtil = SeedXUtil.getinstance();
            String atmPwd = security.decrypt(vo.getAtmPwd(), vo.getClientPe());
            String cardNum = security.decrypt(vo.getCardNum(), vo.getClientPe());
//        String atmPwd = vo.getAtmPwd();
            String encAtmPwd = seedXUtil.SeedEncrypt(atmPwd);
            vo.setAtmPwd(encAtmPwd);
            vo.setCardNum(cardNum);
            System.out.println("atmPwd : " + atmPwd + " //cardNum : " + cardNum + " //encAtmPwd : " + encAtmPwd);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("failCd", "996");
            resultVO.setData(resultMap);
            return resultVO;
        }


        int res = moaCardService.uptCardAtmPwd(vo);
        if (res == 0) { //이미 등록되어있는 카드
            resultMap.put("failCd", "997");
            resultVO.setData(resultMap);
            return resultVO;
        }

        resultVO.setData(resultMap);

        return resultVO;
    }

    @RequestMapping(value = "/getCardAtmPwd.dm")
    public @ResponseBody
    CmeResultVO getCardAtmPwd(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        if ("".equals(vo.getUserEmail())) {
            resultMap.put("failCd", "999");
        }
        MoaCardReqVO resVo = moaCardService.getCardAtmPwd(vo);
        if (resVo == null) {
            resultMap.put("failCd", "998");
        } else {
            resultMap.put("atmPwd", resVo.getAtmPwd());
            resultMap.put("cardNum", resVo.getCardNum());
        }
        resultVO.setData(resultMap);

        return resultVO;
    }


    @RequestMapping(value = "/uptCardSendMail.dm")
    public @ResponseBody
    CmeResultVO uptCardSendMail(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

//        System.out.println("uptCardSendMail");
//        System.out.println("vo.getUserEmail() : " + vo.getUserEmail());
//        System.out.println("vo.getOrderId() : " + vo.getOrderId());

        if ("".equals(vo.getUserEmail())) {
            resultMap.put("failCd", "998");
            resultVO.setData(resultMap);
            return resultVO;
        } else if ("".equals(vo.getOrderId())) {
            resultMap.put("failCd", "997");
            resultVO.setData(resultMap);
            return resultVO;
        }

        try {
            int res = moaCardService.getCardSendInfoCnt(vo);
//            System.out.println("res : " + res);
            if (res == 0) {
                res = moaCardService.uptCardSendInfo(vo);
                String cardNo = moaCardService.getCardNo(vo);
//                System.out.println("cardNo : " + cardNo);
                if (cardNo != null) {
                    vo.setCardNum(cardNo.replaceAll("-", ""));
                    int res1 = moaCardService.setCardNo(vo);
//                    System.out.println("res1 : " + res1);
                }
            } else {
                res = moaCardService.uptCardSendDt(vo);
            }
            if (res > 0) {
                /* 메일전송 */
                Locale locale = new Locale("ko");
                localeResolver.setLocale(request, response, locale);

                Map<String, Object> param = new HashMap<String, Object>();
                String lang = userService.getUserLangCd(vo.getUserEmail());
                //param.put("lang", lang);
                param.put("mailTo", vo.getUserEmail());
                param.put("orderId", vo.getOrderId());
                param.put("userEmail", vo.getUserEmail());
                res = mailService.ApplyCardSendmail(request, param);
                /* 메일전송 */
            }

        } catch (Exception e) {
            resultMap.put("failCd", "999");
        }

        resultVO.setData(resultMap);

        return resultVO;
    }


    @RequestMapping(value = "/selectCardRemPrc.dm")
    public @ResponseBody
    CmeResultVO selectCardRemPrc(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        resultMap.put("resultMsg", "");
        resultMap.put("rtnMsg", "");
        resultMap.put("remPrc", "0");

        if ("".equals(vo.getUserEmail())) {
            resultMap.put("failCd", "998");
        } else if ("".equals(vo.getCardNum())) {
            resultMap.put("failCd", "997");
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();

        SeedXUtil seedXUtil = SeedXUtil.getinstance();
        String userEmail = seedXUtil.SeedEncrypt(vo.getUserEmail());
        String decCardNum = security.decrypt(vo.getCardNum(), vo.getClientPe());
        String cardNum = seedXUtil.SeedEncrypt(decCardNum);

        CardBankInfoVO cardVO = moaCardService.selectCardBankInfo(decCardNum);

        if (cardVO != null) {
            resultMap.put("bankCd", cardVO.getBankcd());
            resultMap.put("bankNo", cardVO.getVacc());

            paramMap.put("TrdType", "0700");
            paramMap.put("MemID", userEmail);
            paramMap.put("CardNum", cardNum);
            paramMap.put("TrdDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            paramMap.put("storeID", CmmCdConstant.STORE_ID);
            String rtnStr = HttpComLib.httpSendAPI(CmmCdConstant.CARD_URL, paramMap);

            System.out.println(rtnStr);


            try {
                if (rtnStr.startsWith("00,10,")) {
                    String[] strs = rtnStr.split(",");
                    if ("00".equals(strs[0]) && "10".equals(strs[1])) {
                        resultMap.put("remPrc", strs[2]);
                    }
                } else if (rtnStr.startsWith("XX,")) {
                    String[] strs = rtnStr.split(",");
                    if ("XX".equals(strs[0])) {
                        resultMap.put("rtnMsg", strs[1]);
                        resultMap.put("failCd", "999");
                    }
                } else {
                    resultMap.put("failCd", "999");
                }
            } catch (Exception e) {
                resultMap.put("failCd", "999");
            }

        } else {
            resultMap.put("failCd", "996");
        }

        resultVO.setData(resultMap);
        return resultVO;
    }


    @RequestMapping(value = "/selectCardReqList.dm")
    public @ResponseBody
    CmeResultVO selectCardReqList(MoaCardReqListVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultMsg", "");
        resultMap.put("failCd", "");
        resultMap.put("list", new ArrayList<MoaCardReqListVO>());

        try {
            List<MoaCardReqListVO> list = moaCardService.selectUserReqList(vo);
            resultMap.put("list", list);
            resultVO.setData(resultMap);
            if (list.size() > 0) {
                resultMap.put("failCd", "");
            } else {
                resultMap.put("failCd", "998");
            }

        } catch (Exception e) {
            resultMap.put("failCd", "999");
        }

        return resultVO;
    }


    @RequestMapping(value = "/cardReqCancel.dm")
    public @ResponseBody
    CmeResultVO cardReqCancel(MoaCardReqListVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultMsg", "");
        resultMap.put("failCd", "");
        resultVO.setData(resultMap);

        if ("".equals(vo.getOrderId())) {
            resultMap.put("failCd", "998");
            resultVO.setData(resultMap);
            return resultVO;
        }

        try {
            int res = moaCardService.cardReqCancel(vo.getOrderId());

            if (res > 0) {
                resultMap.put("failCd", "");
                MoaCardReqVO mcvo = moaCardService.selectUserMobile(vo.getOrderId());
                /*문자발송*/
                SendInfoVO svo = new SendInfoVO();
                svo.setContents("선불카드 신청이 취소되었습니다.");
//                필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)
                svo.setMobile_info(mcvo.getUserMobile());
                //svo.setClientCd(clientNm);
                svo.setRegIp(getIp(request, "127.0.0.1"));
                svo.setUserEmail(mcvo.getUserEmail());
                int isSendSms = smsApiService.sendSms(svo);
//                문자발송
            } else {
                resultMap.put("failCd", "997");
            }

        } catch (Exception e) {
            resultMap.put("failCd", "999");
        }

        return resultVO;
    }


    @RequestMapping(value = "/deposit/card.dm")
    public @ResponseBody
    CmeResultVO depositCard(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        rService.RTPVertify(request);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        resultMap.put("resultMsg", "");
        resultMap.put("rtnMsg", "");
        resultMap.put("remPrc", "0");

        if (vo.getUserEmail().equals("")) {
            resultMap.put("failCd", "998");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (vo.getCrgPrc().equals("")) {
            resultMap.put("failCd", "997");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (vo.getCardNum().equals("")) {
            resultMap.put("failCd", "996");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (vo.getCardPwd().equals("")) {
            resultMap.put("failCd", "995");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (vo.getCardCvc().equals("")) {
            resultMap.put("failCd", "994");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (vo.getCardDate().equals("")) {
            resultMap.put("failCd", "993");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String ip = getIp(request, vo.getRegIp());

        SeedXUtil seedXUtil = SeedXUtil.getinstance();
        String userEmail = vo.getUserEmail();
        String cardNum = security.decrypt(vo.getCardNum(), vo.getClientPe());
        String cardPwd = security.decrypt(vo.getCardPwd(), vo.getClientPe());
        String cardCvc = security.decrypt(vo.getCardCvc(), vo.getClientPe());
        String cardDate = security.decrypt(vo.getCardDate(), vo.getClientPe());

        String encUserEmail = seedXUtil.SeedEncrypt(vo.getUserEmail());
        String encCardNum = seedXUtil.SeedEncrypt(cardNum);
        String encCardPwd = seedXUtil.SeedEncrypt(cardPwd);
        String encCardCvc = seedXUtil.SeedEncrypt(cardCvc);
        String encCardDate = seedXUtil.SeedEncrypt(cardDate);
        String encCrgPrc = seedXUtil.SeedEncrypt(vo.getCrgPrc());

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("TrdType", "0500");

        MoaCardReqListVO reqVO = new MoaCardReqListVO();
        reqVO.setUserEmail(userEmail);
        reqVO.setStatus("04");
        reqVO.setCardNum(cardNum);
        reqVO = moaCardService.selectUserCard(reqVO);

        if (reqVO == null) { //회원 본인 카드정보 아님
            resultMap.put("failCd", "991");
            resultVO.setData(resultMap);
            return resultVO;
        }

        int price = 0;
        try {
            price = Integer.parseInt(vo.getCrgPrc());
        } catch (Exception e) {
            //충전금액 int 변환 오류
            resultMap.put("failCd", "988");
            resultVO.setData(resultMap);
            return resultVO;
        }

        paramMap.put("MemID", encUserEmail);
        paramMap.put("CardNum", encCardNum);
        paramMap.put("CardPwd", encCardPwd);
        paramMap.put("CardCvc", encCardCvc);
        paramMap.put("CardDate", encCardDate);
        paramMap.put("TrdPoint", encCrgPrc);
        paramMap.put("TrdDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        paramMap.put("storeID", CmmCdConstant.STORE_ID);
        String rtnStr = HttpComLib.httpSendAPI(CmmCdConstant.CARD_URL, paramMap);

        System.out.println(rtnStr);

        String compCode = "";
        try {
            if (rtnStr.startsWith("00,")) {
                String[] strs = rtnStr.split(",");
                compCode = strs[1];
            } else if (rtnStr.startsWith("XX,")) {
                String[] strs = rtnStr.split(",");
                if ("XX".equals(strs[0])) {
                    resultMap.put("rtnMsg", strs[1]);
                    resultMap.put("failCd", "992");
                }
            } else {
                resultMap.put("failCd", "999");
            }
        } catch (Exception e) {
            resultMap.put("failCd", "999");
        }

        double depositFee = 0;
        BigDecimal feePer = new BigDecimal(depositFee);//선불카드 입금수수료
        BigDecimal dptPrc = new BigDecimal(vo.getCrgPrc());//고객 통화 금액
        BigDecimal dptFee = dptPrc.multiply(feePer).setScale(0, BigDecimal.ROUND_HALF_UP);//수수료
        BigDecimal crgPrc = dptPrc.subtract(dptFee);//수수료뺀 금액

        if (!"".equals(compCode)) { //카드 잔액 차감 성공

            setLocale(request, response);
            vo.setCrgPrc(crgPrc.toString());
            //DepositVO exRateDepositVO = depositService.getExRateDepositPrc(vo);
            TrnInfoVO tvo = new TrnInfoVO();
            tvo.setCrgPrc(dptPrc.toString());
            tvo.setExCryCode(tradeService.getCmmNm());/*"KRW");*/
            tvo.setExRate("");
            tvo.setUserEmail(userEmail);
            String trnGenKey = depositService.getTrnGenKey();
            tvo.setGenKey(trnGenKey);
            tvo.setCardNo(cardNum);
            tvo.setDptFee(dptFee.toString());
            tvo.setStatus("01");
            depositService.insTrnInfo(tvo);

            vo.setPayKndCd("CMMC00000000805");
            vo.setCrgPrc(crgPrc.toString());
            vo.setTrnId(trnGenKey);
            vo.setRegIp(ip);

            try {

                depositService.INSUPT30171020(vo);

                List list = (List) vo.getRESULT();
                if (list != null) {// Result
                    Map map = (Map) list.get(0);
                    String rtnCd = (String) map.get("RTN_CD");
                    if (rtnCd == null || !rtnCd.equals("1")) {
                        resultMap.put("failCd", "999");
                        resultMap.put("rtnMsg", (String) map.get("RNT_MSG"));
                    }
                }

                resultVO.setProceduresResult(vo.getRESULT());

            } catch (Exception e) {
                String errMsg = vo.getUserEmail() + "님의 충전 도중 오류가 발생 , 충전금액 : " + vo.getCrgPrc() + ", IP : " + vo.getRegIp();
                throw new CmeApplicationException(errMsg);
            }

            String num = vo.getCrgPrc();
            if (num.contains(".")) {
                String nums[] = num.split("\\.");
                nums[0] = ComUtil.strToNumber(nums[0]);
                num = nums[0] + "." + nums[1];
            } else {
                num = ComUtil.strToNumber(num);
            }

            try {
                //db현재시간
                String sysdate = tradeService.selectDate("");

                System.out.println("mailTo : " + userEmail);
                /*메일발송*/
                Map<String, Object> model = new HashMap<String, Object>();
                /*필수값 : mailTo(받는사람메일주소), clientCd(클라이언트코드)*/
                model.put("mailTo", userEmail);
                model.put("sysdate", sysdate);
                model.put("crgPrc", num);
                mailService.depositComplete(request, model);
                /*메일발송*/
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("메일발송 fail");
            }
            try {
                System.out.println("smsTo : " + userEmail);
                /*문자발송*/
                SendInfoVO svo = new SendInfoVO();
                /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
                svo.setMobile_info(loginService.getUserMobile(userEmail));
                svo.setCrgPrc(num);
                svo.setUserEmail(userEmail);
                smsApiService.depositComplete(request, svo);
                /*문자발송*/

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("문자발송 fail");
            }

        }

        resultVO.setData(resultMap);
        return resultVO;


    }

    @RequestMapping(value = "/selectCardReqpayInfo.dm")
    public @ResponseBody
    CmeResultVO selectCardReqpayInfo(CardReqPayInfoVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultMsg", "");
        resultMap.put("failCd", "");

        try {
            CardReqPayInfoVO cvo = moaCardService.cardReqpayInfo();
            resultVO.setData(cvo);
            if (cvo != null) {
                resultMap.put("failCd", "");
            } else {
                resultMap.put("failCd", "998");
            }

        } catch (Exception e) {
            resultMap.put("failCd", "999");
        }

        return resultVO;
    }

    @RequestMapping(value = "/getStatusValue.dm")
    public @ResponseBody
    CmeResultVO getStatusValue(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");

        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put("resultMsg", "");
        resultMap.put("failCd", "");

        String order_id = request.getParameter("orderId");
        if (order_id == null || "".equals(order_id)) {
            resultMap.put("resultMsg", "결제번호가 없습니다.");
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String getStatus = moaCardService.getStatusValue(order_id);

        System.out.println(getStatus);
        if (getStatus == null || "".equals(getStatus)) {
            resultMap.put("resultMsg", "상태값이 없습니다.");
            resultMap.put("failCd", "998");
            resultVO.setData(resultMap);
            return resultVO;
        } else {
            resultMap.put("resultMsg", "상태값 반환 성공");
            resultMap.put("status", getStatus);
        }

        resultVO.setData(resultMap);
        return resultVO;
    }


    //카드사쪽 API
    @RequestMapping(value = "/uptCardRegComp.dm")
    public @ResponseBody
    Map<String, Object> uptCardRegComp(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        insParamLog(request);

        Map<String, Object> resultMap = new HashMap<String, Object>();
               resultMap.put("resultMsg", "");


        if ("".equals(vo.getUserEmail())) {
            resultMap.put("resultStrCode", "998");
            return resultMap;
        } else if ("".equals(vo.getCardNum())) {
            resultMap.put("resultStrCode", "997");
            return resultMap;
        } else if ("".equals(vo.getUserNm())) {
            resultMap.put("resultStrCode", "995");
            return resultMap;
        }

        System.out.println("iso-8859-1 -> utf-8         : " + new String(vo.getUserNm().getBytes("iso-8859-1"), "utf-8"));
        String userNm = new String(vo.getUserNm().getBytes("iso-8859-1"), "utf-8");
        vo.setUserNm(userNm);
        System.out.println("userNm:" + vo.getUserNm());

        SeedXUtil seedXUtil = SeedXUtil.getinstance();

        String userEmail = seedXUtil.SeedDecrypt(vo.getUserEmail());
        String cardNum = seedXUtil.SeedDecrypt(vo.getCardNum());
//
      /*  String userEmail = vo.getUserEmail();
        String cardNum = vo.getCardNum();*/

        vo.setCardNum(cardNum);
        vo.setUserEmail(userEmail);

        // 등록완료된 카드가 있으면 리턴

        try {
            int res = moaCardService.uptCardRegComp(vo);
            if (res > 0) {
                vo.setStatus("04");
                resultMap.put("resultStrCode", "000");
                int res2 = moaCardService.userAmountCheck(vo);
                if (res2 > 0) {
                    moaCardService.plusCardPoint(vo);
                    moaCardService.pointCash(vo.getUserEmail());

                }
               /* try {
                    SendInfoVO svo = new SendInfoVO();
                    svo.setUserEmail(vo.getUserEmail());
                    svo.setPushType("CMMC00000000825");
                    svo.setContents("카드등록이 완료되었습니다.");
                    svo.setRegIp("127.0.0.1");
                    CmeResultVO rtnVo = new CmeResultVO();
                    smsApiService.sendSms(svo);

                    moaCardService.plusCardPoint(vo);
                    moaCardService.pointCash(vo.getUserEmail());
                } catch (Exception e) {

                }*/
            } else {
                resultMap.put("resultStrCode", "996");
            }


        } catch (Exception e) {
            resultMap.put("resultStrCode", "999");
        }

        System.out.println("resultStrCode : " + resultMap.get("resultStrCode"));
        return resultMap;
    }


    //카드사쪽 API
    @RequestMapping(value = "/uptCardRegBack.dm")
    public @ResponseBody
    Map<String, Object> uptCardRegBack(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        insParamLog(request);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultMsg", "");

        if ("".equals(vo.getUserEmail())) {
            resultMap.put("resultStrCode", "998");
        } else if ("".equals(vo.getCardNum())) {
            resultMap.put("resultStrCode", "997");
        }

        SeedXUtil seedXUtil = SeedXUtil.getinstance();

        String userEmail = seedXUtil.SeedDecrypt(vo.getUserEmail());
        String cardNum = seedXUtil.SeedDecrypt(vo.getCardNum());
//
       /* String userEmail = vo.getUserEmail();
        String cardNum = vo.getCardNum();*/

        vo.setCardNum(cardNum);
        vo.setUserEmail(userEmail);
        vo.setStatus("03");
        try {
            int res = moaCardService.uptCardRegBack(vo);
            if (res > 0) {
                resultMap.put("resultStrCode", "000");
                moaCardService.backCardPoint(vo.getUserEmail());
                moaCardService.backPointCash(vo.getUserEmail());

            } else {
                resultMap.put("resultStrCode", "996");
            }


        } catch (Exception e) {
            resultMap.put("resultStrCode", "999");
        }

        System.out.println("resultStrCode : " + resultMap.get("resultStrCode"));
        return resultMap;
    }


    //카드사쪽 API
    @RequestMapping(value = "/cardRegComp.dm")
    public @ResponseBody
    Map<String, Object> CardRegComp(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        insParamLog(request);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultMsg", "");

        if ("".equals(vo.getUserEmail())) {
            resultMap.put("resultStrCode", "998");
        } else if ("".equals(vo.getCardNum())) {
            resultMap.put("resultStrCode", "997");
        }

        SeedXUtil seedXUtil = SeedXUtil.getinstance();

        String userEmail = seedXUtil.SeedDecrypt(vo.getUserEmail());
        String cardNum = seedXUtil.SeedDecrypt(vo.getCardNum());
//
//        String userEmail = vo.getUserEmail();
//        String cardNum = vo.getCardNum();


        vo.setCardNum(cardNum);
        vo.setUserEmail(userEmail);
        try {
            SendInfoVO svo = new SendInfoVO();
            svo.setUserEmail(vo.getUserEmail());
            svo.setPushType("CMMC00000000825");
            svo.setContents("카드등록이 완료되었습니다.");
            svo.setRegIp("127.0.0.1");
            CmeResultVO rtnVo = new CmeResultVO();
            smsApiService.sendSms(svo);
            resultMap.put("resultStrCode", "000");
        } catch (Exception e) {

        }

        return resultMap;
    }


    //카드사쪽 API
    @RequestMapping(value = "/useCoin.dm")
    public @ResponseBody
    Map<String, Object> useCoin(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        insParamLog(request);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultStrCode", "000");
        if ("".equals(vo.getUserEmail())) {
            resultMap.put("resultStrCode", "998");
        } else if ("".equals(vo.getCurcyCd())) {
            resultMap.put("resultStrCode", "997");
        } else if ("".equals(vo.getAmount())) {
            resultMap.put("resultStrCode", "996");
        }

        SeedXUtil seedXUtil = SeedXUtil.getinstance();

//        String userEmail = vo.getUserEmail();
//        String curcyCd = vo.getCurcyCd();
//        String amount = vo.getAmount();
//
        String userEmail = seedXUtil.SeedDecrypt(vo.getUserEmail());
        String curcyCd = seedXUtil.SeedDecrypt(vo.getCurcyCd());
        String amount = seedXUtil.SeedDecrypt(vo.getAmount());
        double useAmt = 0;
        try {
            useAmt = Double.parseDouble(amount);
        } catch (Exception e) {
            //사용금액 숫자아님
            resultMap.put("resultStrCode", "995");
        }

        CmeUserAssetReqVO reqVO = new CmeUserAssetReqVO();
        reqVO.setUserEmail(userEmail);
        reqVO.setCurcyCd(curcyCd);
        reqVO.setAmount("5000");
        CoinBalanceVO bVO = coinInfoService.getCoinBalanceList(reqVO);

        if (useAmt > Double.parseDouble(bVO.getCnAmt())) {
            //보유금액 초과 신청
            resultMap.put("resultStrCode", "994");
        } else if (useAmt < Double.parseDouble(bVO.getPosCnAmt())) {
            //최소 사용금액 미달
            resultMap.put("resultStrCode", "991");
        }

        double cnPrc = 0;
        double cnPrcKrw = 0;
        try {
            cnPrc = Double.parseDouble(bVO.getCnPrc());
            cnPrcKrw = Double.parseDouble(bVO.getCnPrcKrw());
        } catch (Exception e) {
            //코인 시세 없음
            resultMap.put("resultStrCode", "993");
        }
        if (cnPrc == 0) {
            //코인 시세 없음
            resultMap.put("resultStrCode", "993");
        }

        //파라미터 체크 끝
        if (!"000".equals(resultMap.get("resultStrCode"))) {
            return resultMap;
        }

        try {


            //코인 차감 키
            String tradeId = depositService.getCardCoinOutKey();

            //코인차감이력 insert
            vo.setTradeId(tradeId);
            vo.setUserEmail(userEmail);
            vo.setCurcyCd(curcyCd);
            vo.setStatus("CMMC00000000099");
            vo.setCnAmt(amount);
            vo.setCnPrc(String.valueOf(cnPrc));
            vo.setExRate(bVO.getExrate());
            vo.setUsePrc(String.valueOf((int) (useAmt * cnPrcKrw)));
            vo.setRegIp("127.0.0.1");
            depositService.insUptCardUseInfo(vo);

            //실제 코인 차감
            DepositVO dvo = new DepositVO();
            dvo.setUserEmail(userEmail);
            dvo.setCnAmt(useAmt);
            dvo.setCurcyCd(curcyCd);
            depositService.cardCoinOut(dvo);

            resultMap.put("tradeId", tradeId);
            resultMap.put("usePrc", vo.getUsePrc());
        } catch (Exception e) {
            resultMap.put("resultStrCode", "992");
        }
        return resultMap;

    }


    //카드사쪽 API
    @RequestMapping(value = "/useCoinCancel.dm")
    public @ResponseBody
    Map<String, Object> useCoinCancel(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        insParamLog(request);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultStrCode", "000");
        if ("".equals(vo.getTradeId())) {
            resultMap.put("resultStrCode", "999");
        }

        SeedXUtil seedXUtil = SeedXUtil.getinstance();

        String tradeId = seedXUtil.SeedDecrypt(vo.getTradeId());

        vo = depositService.selectCardCoinUseInfo(tradeId);

        if (vo == null) {
            //일치하는 거래번호 없음
            resultMap.put("resultStrCode", "998");
        } else if ("CMMC00000000101".equals(vo.getStatus())) {
            //이미 취소된 거래
            resultMap.put("resultStrCode", "997");
        } else {
            try {

                //코인차감이력 취소
                vo.setTradeId(tradeId);
                vo.setUserEmail(vo.getUserEmail());
                vo.setStatus("CMMC00000000101");
                vo.setRegIp("127.0.0.1");
                depositService.insUptCardUseInfo(vo);

                //실제 코인 복구
                DepositVO dvo = new DepositVO();
                dvo.setUserEmail(vo.getUserEmail());
                dvo.setCnAmt(Double.parseDouble(vo.getCnAmt()));
                dvo.setCurcyCd(vo.getCurcyCd());
                depositService.INS10171028(dvo);
            } catch (Exception e) {
                resultMap.put("resultStrCode", "996");
            }
        }

        return resultMap;

    }


    //카드사쪽 API
    @RequestMapping(value = "/useCash.dm")
    public @ResponseBody
    Map<String, Object> useCash(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        insParamLog(request);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultStrCode", "000");
        if ("".equals(vo.getCardNum())) {
            resultMap.put("resultStrCode", "998");
        } else if ("".equals(vo.getAmount())) {
            resultMap.put("resultStrCode", "997");
        }

        SeedXUtil seedXUtil = SeedXUtil.getinstance();

//        String cardNum = vo.getCardNum();
//        String amount = vo.getAmount();
//
        String cardNum = seedXUtil.SeedDecrypt(vo.getCardNum());
        String amount = seedXUtil.SeedDecrypt(vo.getAmount());
        vo.setCardNum(cardNum);
        int isPwd = moaCardService.getCardPwdconfirm(vo);
        if (isPwd == 0) {//ATM비밀번호 맞지 않음
            resultMap.put("resultStrCode", "990");
            return resultMap;
        }

        String userEmail = coinInfoService.getUserEmail(cardNum);

        double useAmt = 0;
        try {
            useAmt = Double.parseDouble(amount);
        } catch (Exception e) {
            //사용금액 숫자아님
            resultMap.put("resultStrCode", "995");
            return resultMap;
        }

        CmeUserAssetReqVO reqVO = new CmeUserAssetReqVO();
        reqVO.setUserEmail(userEmail);
        reqVO.setAmount(amount);
        CashBalanceVO bVO = coinInfoService.getCashBalanceList(reqVO);


        if (useAmt > Double.parseDouble(bVO.getUsePriceKrw())) {
            //보유금액 초과 신청
            resultMap.put("resultStrCode", "994");
            return resultMap;
        } else if (useAmt < 5000) {
            //최소 사용금액 미달
            resultMap.put("resultStrCode", "991");
            return resultMap;
        }

        try {
            //코인 차감 키
            String tradeId = depositService.getCardCoinOutKey();

            //코인차감이력 insert
            vo.setTradeId(tradeId);
            vo.setUserEmail(userEmail);
            vo.setCurcyCd("CMMC00000000204");
            vo.setStatus("CMMC00000000099");
            vo.setCnAmt(amount);
            vo.setCnPrc("0");
            vo.setExRate(bVO.getExrate());
            vo.setUsePrc(amount);
            vo.setRegIp("127.0.0.1");
            vo.setFeeAmt("1500");
            depositService.insUptCardUseInfo(vo);

            //실제 캐쉬 차감
            DepositVO dvo = new DepositVO();
            dvo.setUserEmail(userEmail);
            dvo.setCnAmt(Double.parseDouble(amount));
            depositService.cardCashOut(dvo);

            resultMap.put("tradeId", tradeId);
            resultMap.put("usePrc", vo.getUsePrc());
        } catch (Exception e) {
            resultMap.put("resultStrCode", "992");
        }

        resultMap.put("userNm", moaCardService.getCardUserNm(vo));

        return resultMap;

    }


    //카드사쪽 API
    @RequestMapping(value = "/depositCash.dm")
    public @ResponseBody
    Map<String, Object> depositCash(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        insParamLog(request);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultStrCode", "000");
        if ("".equals(vo.getCardNum())) {
            resultMap.put("resultStrCode", "998");
        } else if ("".equals(vo.getCrgPrc())) {
            resultMap.put("resultStrCode", "997");
        }

        SeedXUtil seedXUtil = SeedXUtil.getinstance();

//        String cardNum = vo.getCardNum();
//        String amount = vo.getCrgPrc();
//
        String cardNum = seedXUtil.SeedDecrypt(vo.getCardNum());
        String amount = seedXUtil.SeedDecrypt(vo.getCrgPrc());

        MoaCardReqVO reqVO = new MoaCardReqVO();
        reqVO.setCardNum(cardNum);
        reqVO.setAtmPwd(vo.getAtmPwd());
        /*int isPwd = moaCardService.getCardPwdconfirm(reqVO);
        if(isPwd == 0) {//ATM비밀번호 맞지 않음
            resultMap.put("resultStrCode", "990");
            return resultMap;
        }*/

        String userEmail = coinInfoService.getUserEmail(cardNum);

        if (userEmail == null) {
            resultMap.put("resultStrCode", "996");
        }

        double useAmt = 0;
        try {
            useAmt = Double.parseDouble(amount);
        } catch (Exception e) {
            //사용금액 숫자아님
            resultMap.put("resultStrCode", "995");
        }

        //파라미터 체크 끝
        if (!"000".equals(resultMap.get("resultStrCode"))) {
            return resultMap;
        }

        try {

            setLocale(request, response);

            vo.setUserEmail(userEmail);
            vo.setCrgPrc(amount);
            //DepositVO exRateDepositVO = depositService.getExRateDepositPrc(vo);

            /*BigDecimal feePer = new BigDecimal(0.05);//선불카드 입금수수료
            BigDecimal dptPrc = new BigDecimal(amount);//고객 통화 금액
            BigDecimal dptFee = dptPrc.multiply(feePer).setScale(0, BigDecimal.ROUND_HALF_UP);//수수료
            BigDecimal crgPrc = dptPrc.subtract(dptFee);//수수료뺀 금액*/

            BigDecimal dptPrc = new BigDecimal(amount);//고객 통화 금액
            BigDecimal dptFee = new BigDecimal(0);//수수료
            BigDecimal crgPrc = dptPrc.subtract(dptFee);//수수료뺀 금액

            vo.setCrgPrc(crgPrc.toString());
            TrnInfoVO tvo = new TrnInfoVO();
            tvo.setCrgPrc(amount);
            tvo.setExCryCode(tradeService.getCmmNm());//"KRW");
            tvo.setExRate("");
            tvo.setUserEmail(userEmail);
            String trnGenKey = depositService.getTrnGenKey();
            tvo.setGenKey(trnGenKey);
            tvo.setCardNo(cardNum);
            tvo.setDptFee(dptFee.toString());
            tvo.setStatus("01");
            depositService.insTrnInfo(tvo);
            resultMap.put("tradeId", trnGenKey);

            vo.setPayKndCd("CMMC00000001165");
            vo.setCrgPrc(crgPrc.toString());
            vo.setTrnId(trnGenKey);
            vo.setRegIp("127.0.0.1");

            try {

                depositService.INSUPT30171020(vo);

                List list = (List) vo.getRESULT();
                if (list != null) {// Result
                    Map map = (Map) list.get(0);
                    String rtnCd = (String) map.get("RTN_CD");
                    if (rtnCd == null || !rtnCd.equals("1")) {
                        resultMap.put("failCd", "999");
                        resultMap.put("rtnMsg", (String) map.get("RNT_MSG"));
                    }
                }


            } catch (Exception e) {
                String errMsg = vo.getUserEmail() + "님의 충전 도중 오류가 발생 , 충전금액 : " + vo.getCrgPrc() + ", IP : " + vo.getRegIp();
                throw new CmeApplicationException(errMsg);
            }

            String num = vo.getCrgPrc();
            if (num.contains(".")) {
                String nums[] = num.split("\\.");
                nums[0] = ComUtil.strToNumber(nums[0]);
                num = nums[0] + "." + nums[1];
            } else {
                num = ComUtil.strToNumber(num);
            }

            try {
                //db현재시간
                String sysdate = tradeService.selectDate("");

                System.out.println("mailTo : " + userEmail);
                /*메일발송*/
                Map<String, Object> model = new HashMap<String, Object>();
                /*필수값 : mailTo(받는사람메일주소), clientCd(클라이언트코드)*/
                model.put("mailTo", userEmail);
                model.put("sysdate", sysdate);
                model.put("crgPrc", num);
                mailService.depositComplete(request, model);
                /*메일발송*/
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("메일발송 fail");
            }
            try {
                System.out.println("smsTo : " + userEmail);
                /*문자발송*/
                SendInfoVO svo = new SendInfoVO();
                /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
                svo.setMobile_info(loginService.getUserMobile(userEmail));
                svo.setCrgPrc(num);
                svo.setUserEmail(userEmail);
                smsApiService.depositComplete(request, svo);
                /*문자발송*/

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("문자발송 fail");
            }


//            resultMap.put("tradeId", tradeId);
//            resultMap.put("usePrc", vo.getUsePrc());
        } catch (Exception e) {
            resultMap.put("resultStrCode", "992");
        }

        resultMap.put("userNm", moaCardService.getCardUserNm(reqVO));
        return resultMap;

    }

    //카드사쪽 API
    @RequestMapping(value = "/useCoinKrw.dm")
    public @ResponseBody
    Map<String, Object> useCoinKrw(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        insParamLog(request);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultStrCode", "000");
        if ("".equals(vo.getCardNum())) {
            resultMap.put("resultStrCode", "999");
        } else if ("".equals(vo.getCurcyCd())) {
            resultMap.put("resultStrCode", "998");
        } else if ("".equals(vo.getUsePrc())) {
            resultMap.put("resultStrCode", "997");
        }

        SeedXUtil seedXUtil = SeedXUtil.getinstance();

        /*String cardNum = vo.getCardNum();
        String curcyCd = vo.getCurcyCd();
        String usePrc = vo.getUsePrc();*/
//
        String cardNum = seedXUtil.SeedDecrypt(vo.getCardNum());
        String curcyCd = seedXUtil.SeedDecrypt(vo.getCurcyCd());
        String usePrc = seedXUtil.SeedDecrypt(vo.getUsePrc());

        String userEmail = coinInfoService.getUserEmail(cardNum);
        vo.setCardNum(cardNum);
        vo.setCurcyCd(curcyCd);
        int isPwd = moaCardService.getCardPwdconfirm(vo);
        if (isPwd == 0) {//ATM비밀번호 맞지 않음
            resultMap.put("resultStrCode", "990");
            return resultMap;
        }

        double useAmt = 0;
        try {
            useAmt = Double.parseDouble(usePrc);
        } catch (Exception e) {
            //사용금액 숫자아님
            resultMap.put("resultStrCode", "996");
        }

        CmeUserAssetReqVO reqVO = new CmeUserAssetReqVO();
        reqVO.setUserEmail(userEmail);
        reqVO.setCurcyCd(curcyCd);
        reqVO.setAmount(usePrc);
        CoinBalanceVO bVO = coinInfoService.getCoinBalanceList(reqVO);

        CmeRealTimeCoinPriceResVO coinVO = moaCardService.getMoaCoinInfo(vo.getCurcyCd());

        if (useAmt > Double.parseDouble(bVO.getUsePriceKrw())) {
            //보유금액 초과 신청
            resultMap.put("resultStrCode", "995");

        } else if (useAmt < 5000) {
            //최소 사용금액 미달
            resultMap.put("resultStrCode", "994");
        }

        String amount = bVO.getPosCnAmt();
        double cnPrc = 0;
        double cnPrcKrw = 0;
        try {
            cnPrc = Double.parseDouble(bVO.getCnPrc());
            cnPrcKrw = Double.parseDouble(bVO.getCnPrcKrw());
        } catch (Exception e) {
            //코인 시세 없음
            resultMap.put("resultStrCode", "993");
        }
        if (cnPrc == 0) {
            //코인 시세 없음
            resultMap.put("resultStrCode", "993");
        }

        //파라미터 체크 끝
        if (!"000".equals(resultMap.get("resultStrCode"))) {
            return resultMap;
        }

        try {


            //코인 차감 키
            String tradeId = depositService.getCardCoinOutKey();

            //코인차감이력 insert
            vo.setTradeId(tradeId);
            vo.setUserEmail(userEmail);
            vo.setCurcyCd(curcyCd);
            vo.setStatus("CMMC00000000099");
            vo.setCnAmt(amount);
            vo.setCnPrc(String.valueOf(cnPrc));
            vo.setExRate(bVO.getExrate());
            vo.setUsePrc(String.valueOf((int) useAmt));
            vo.setRegIp("127.0.0.1");
            reqVO.setAmount("1500");
            vo.setFeeAmt(coinInfoService.getCoinAtmFee(reqVO));
            depositService.insUptCardUseInfo(vo);

            //실제 코인 차감
            DepositVO dvo = new DepositVO();
            dvo.setUserEmail(userEmail);
            dvo.setCnAmt(Double.parseDouble(amount));
            dvo.setCurcyCd(curcyCd);
            depositService.cardCoinOut(dvo);


            // master계정으로 코인 넣기 (cmaster@planbit.io)
            depositService.cardCoinIn(dvo);
            // 코인 넣어준 이력쌓기
            String tradeInKey = depositService.getCardCoinInKey();

            MoaCardCoinVO moaCoinVO = new MoaCardCoinVO();
            moaCoinVO.setPtmCoinOutCode(tradeInKey);
            moaCoinVO.setCnKndCd(vo.getCurcyCd());
            moaCoinVO.setUseAmt(amount);
            moaCoinVO.setCnPrc(coinVO.getCoinPrc());
            moaCoinVO.setOutPrc(amount);
            moaCoinVO.setFeeAmt(coinInfoService.getCoinAtmFee(reqVO));
            moaCoinVO.setUserEmail(vo.getUserEmail());
            moaCoinVO.setAtmMchKey(vo.getAtmKey());
            moaCoinVO.setCardNo(vo.getCardNum());
            moaCoinVO.setRegIp("127.0.0.1");
            depositService.insMoaCardHistory(moaCoinVO);

            resultMap.put("tradeId", tradeId);
            resultMap.put("usePrc", vo.getUsePrc());

        } catch (Exception e) {
            resultMap.put("resultStrCode", "992");
        }

        resultMap.put("userNm", moaCardService.getCardUserNm(vo));
        resultMap.put("coinPrice", coinVO.getCoinPrc());
        resultMap.put("coinNm", coinVO.getCoinNm());
        resultMap.put("coinAmount", amount);
        return resultMap;

    }


    @RequestMapping(value = "/completePayment.dm")
    public @ResponseBody
    CmeResultVO completePayment(MoaCardReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        if ("".equals(vo.getUserEmail())) {
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("사용자 이메일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String contents = "안녕하세요. 플랜비트 국제 거래소입니다. \r\n" +
                "플랜비트 선불카드 신청이 완료 되었습니다.\r\n" +
                "선불카드는 입력한 배송지로 약 2주 이내 배송됩니다.\r\n" +
                "감사합니다.\r\n";
       /* if (vo.getSendMthCd().equals("1")) {
            contents = "안녕하세요. 플랜비트 국제 거래소입니다. \r\n" +
                    "플랜비트 선불카드 신청이 완료 되었습니다.\r\n" +
                    "선불카드는 [서울 강남구] 또는 [서울 선릉] 지점에서 수령 가능합니다.\r\n" +
                    "지점 위치는 고객센터 > 가맹점 안내를 통해 확인이 가능합니다.\r\n" +
                    "감사합니다.";
        } else if (vo.getSendMthCd().equals("2")) {
            contents = "안녕하세요. 플랜비트 국제 거래소입니다. \r\n" +
                    "플랜비트 선불카드 신청이 완료 되었습니다.\r\n" +
                    "선불카드는 입력한 배송지로 약 2주 이내 배송됩니다.\r\n" +
                    "감사합니다.\r\n";
        }*/

        // 문자발송
        SendInfoVO svo = new SendInfoVO();
        String userMobile = loginService.getUserMobile(vo.getUserEmail());
        svo.setContents(contents);
        //필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)
        svo.setMobile_info(userMobile);
        //svo.setClientCd(clientNm);
        svo.setRegIp(getIp(request, "127.0.0.1"));
        svo.setUserEmail(vo.getUserEmail());
        smsApiService.sendSms(svo, 0);
        //문자발송

        //푸시발송
        svo.setContents("카드 신청이 완료되었습니다.");
        smsApiService.sendSms(svo);

        CmeTradeReqVO reqVO = new CmeTradeReqVO();
        reqVO.setUserEmail(vo.getUserEmail());
        reqVO.setCrgPrc("10000");
            /*moaCardService.plusCardPoint(vo);
            moaCardService.pointCash(vo.getUserEmail());*/


        resultVO.setData(resultMap);
        return resultVO;
    }
}
