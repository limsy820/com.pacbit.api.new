/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.sms.service.impl;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bitkrx.api.auth.vo.CmeLoginVO;
import com.bitkrx.api.auth.vo.SubmitCertVO;
import com.bitkrx.api.common.dao.CoinInfoDAO;
import com.bitkrx.api.common.service.CoinInfoService;
import com.bitkrx.api.common.vo.CommonExchgInfoVO;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.trade.vo.DepositVO;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.CmeResultVO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.LocaleResolver;

import com.bitkrx.api.auth.dao.LoginDAO;
import com.bitkrx.api.push.service.CmeFcmPushService;
import com.bitkrx.api.push.vo.PushYnVO;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.config.util.BizSender;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.util.StringUtils;
import com.bitkrx.config.vo.SendInfoVO;

/**
 * @프로젝트명    : com.bitkrx.api
 * @패키지 : com.bitkrx.api.sms.service.impl
 * @클래스명 : com.bitkrx.api
 * @작성자        : (주)씨엠이소프트 문영민
 * @작성일        : 2017. 11. 21.
 */
@Repository
public class SmsApiServiceImpl implements SmsApiService {

    protected BizSender bizSender = BizSender.getinstance();

    @Autowired
    CmeFcmPushService cmeFcmPushService;

    @Autowired
    LoginDAO loginDAO;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TradeService tradeService;

    @Autowired
    CoinInfoDAO coinInfoDAO;

    @Autowired
    UserService userService;

    @Autowired
    CoinInfoService coinInfoService;

    protected String getLocale(HttpServletRequest request, String key) {

        String msg = "";
        msg = messageSource.getMessage(key, null, "", localeResolver.resolveLocale(request));
        return msg.replaceAll("exchangeNm", CmmCdConstant.EXCHANGE_NAME);

    }

    //public static final String BITKRX_SMS_AUTH_KEY		=	"1511254812778";	// Bitkrx SMS KEY
    public static final String PLANBIT_SMS_AUTH_KEY = "1532497208084";    // planbit SMS KEY
    public static final String CONTENTS_LOGIN = "거래소에\r\n로그인 하였습니다.\r\n[한국표준시간]";    //로그인시
    public static final String CONTENTS_LOGIN_UNLOCK = "고객님의 BITKRX 계정이 관리자에 의해 잠금해제 되어 정상로그인이 가능합니다.";

    public static final String COIN_EXCHANGE_SET = "코인교환예약이 설정이 완료되었습니다.";
    public static final String COIN_EXCHANGE_EXCUTE = "코인교환거래가 체결되었습니다.\r\n상세내역은 내거래내역에서 확인하시가 바랍니다.";

    @Override
    public int sendSms(SendInfoVO vo) throws Exception {
        // TODO Auto-generated method stub

        //(공통화)
        SubmitCertVO svo = new SubmitCertVO();
        String mailLang = userService.getUserLangCd(vo.getUserEmail());
        svo.setLang(mailLang);
        CommonExchgInfoVO comvo = coinInfoDAO.commonExchgInfo(svo);

        Map map = new HashMap();
        map.put("cntnsCode", "");
        map.put("userEmail", vo.getUserEmail());
        map.put("regEmail", vo.getUserEmail());
        map.put("regIp", vo.getRegIp());
        map.put("rsvDt", "");
        map.put("pushCode", "PUSH000000000000001");
        map.put("cntntTitle", "[" + comvo.getExchgName()/*CmmCdConstant.EXCHANGE_NAME*/ + "]");
        map.put("cntnsMsg", vo.getContents());
        map.put("pushKndCd", StringUtils.checkNull(vo.getPushType(), "CMMC00000000292"));
        map.put("pushSendYn", "Y");
        map.put("pushSendDt", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        map.put("pushCompCode", "N");

        String puchCd = loginDAO.getUserPuchCd(vo.getUserEmail());
        if (puchCd != null && !puchCd.equals("")) {//푸시코드가 있으면 푸시발송

            cmeFcmPushService.PR_INSUPT10171061(map);
            cmeFcmPushService.sendPushMsgOne(puchCd, vo.getContents(), makeJson((List) map.get("RESULT"), vo.getUserEmail()));

        }

        if (!"Y".equals(vo.getIsOnlyMts())) {
            /*HTS용 PUSH기록*/
            map.put("pushCode", "PUSH000000000000002");
            map.put("pushSendYn", "N");
            cmeFcmPushService.PR_INSUPT10171061(map);
        }

        return 1;
    }


    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : sendSms
     * @작성일 : 2017. 12. 31.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : SMS로만 발송되는 메소드
     */
    public int sendSms(SendInfoVO vo, int i) throws Exception {
        vo.setName_info(vo.getContents());
        vo.setAuth_key(PLANBIT_SMS_AUTH_KEY);
        vo.setMobile_info(vo.getMobile_info());
        System.out.println("SMS 전송 번호 : " + vo.getMobile_info());
        try {
            bizSender.sendMailSms(vo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
        return 1;
    }


    /**
     * @프로젝트명    : com.bitkrx.api
     * @패키지 : com.bitkrx.api.sms.service.impl
     * @클래스명 : com.bitkrx.api
     * @작성자        : (주)씨엠이소프트 문영민
     * @작성일        : 2017. 11. 22.
     * @내용        :	로그인시 SMS 발송
     */
    @Override
    public int smsLogin(HttpServletRequest request, SendInfoVO vo) throws Exception {
        // TODO Auto-generated method stub
        int res = 0;

        if (isSendYn(vo.getUserEmail(), "bsBs01")) {
            String msg = getLocale(request, "sms.common.login0") + "(" + vo.getClientCd() + ")"
                    + getLocale(request, "sms.common.login1")
                    + "\r\n"
                    + getLocale(request, "sms.common.login2")
                    + new SimpleDateFormat("MM/dd HH:mm").format(new Date());
            vo.setContents(msg);
            vo.setPushType("CMMC00000000284");
            res = sendSms(vo);
        }

        return res;
    }

    public JSONObject makeJson(List list, String userEmail) {
        //List list = (List) map.get("RESULT");
        Map returnMap = (Map) list.get(0);
        JSONObject jobj = new JSONObject();
        String pushKey = "";
        String cntnsCode = "";
        try {
            pushKey = (String) returnMap.get(":B1");
            pushKey = pushKey.substring(pushKey.indexOf("CNTN"), pushKey.indexOf("CNTN") + 15);

            cntnsCode = (String) returnMap.get(":B1");
            cntnsCode = cntnsCode.substring(cntnsCode.indexOf("CNTN"), cntnsCode.indexOf("CNTN") + 19);

            jobj = new JSONObject();
        } catch (Exception e) {
            pushKey = "";
        }
        jobj.put("pushKey", pushKey);
        jobj.put("cntnsCode", cntnsCode);
        jobj.put("userEmail", userEmail);
        return jobj;
    }

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : smsLoginFail
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :5회로그인실패
     */
    public int smsLoginFail(HttpServletRequest request, SendInfoVO vo) throws Exception {

        vo.setContents(getLocale(request, "sms.common.login3"));
        vo.setPushType("CMMC00000000284");
        return sendSms(vo);
    }

    ;

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : smsLoginUnlock
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :계정잠금해제
     */
    public int smsLoginUnlock(HttpServletRequest request, SendInfoVO vo) throws Exception {
        vo.setContents(getLocale(request, "sms.common.login4"));
        vo.setPushType("CMMC00000000284");
        return sendSms(vo);
    }

    ;

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : smsAutoTradeReq
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :자동거래요청
     */
    public int smsAutoTradeReq(SendInfoVO vo) throws Exception {
        String Contents = vo.getCurcyNm() + "자동거래" + vo.getAutoGubun() + "요청이 완료되었습니다.\r\n" +
                "수수료는 거래시 결제됩니다.";
        vo.setContents(Contents);
        vo.setPushType("CMMC00000000286");
        return sendSms(vo);
    }

    ;

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : smsAutoTradeComp
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :자동거래체결
     */
    public int smsAutoTradeComp(SendInfoVO vo) throws Exception {
        String Contents = "자동거래 금액에 도달하여" + vo.getAutoGubun() + "가 체결되었습니다.\r\n" +
                "체결금액:" + vo.getTradeAmt() + vo.getCurcyNm() + "/" + vo.getTradePrc() + getDefaultCmmCd();
        vo.setContents(Contents);
        vo.setPushType("CMMC00000000286");
        return sendSms(vo);
    }

    ;

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : smsAutoTradeCancel
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :자동거래 취소
     */
    public int smsAutoTradeCancel(SendInfoVO vo) throws Exception {
        String Contents = "자동거래 " + vo.getAutoGubun() + "요청이 취소되었습니다.";
        vo.setContents(Contents);
        vo.setPushType("CMMC00000000286");
        return sendSms(vo);
    }

    ;

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : smsAutoTradeComp
     * @작성일 : 2017. 11. 22. h
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :코인교환예약
     */
    public int smsExchangeSet(SendInfoVO vo) throws Exception {
        vo.setContents(COIN_EXCHANGE_SET);
        vo.setPushType("CMMC00000000286");
        return sendSms(vo);
    }

    ;

    /**
     * @return
     * @throws Exception
     * @Method Name  : smsAutoTradeCancel
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :코인교환거래 실행
     */
    public int smsExchangeExcute(SendInfoVO vo) throws Exception {
        vo.setContents(COIN_EXCHANGE_EXCUTE);
        vo.setPushType("CMMC00000000286");
        return sendSms(vo);
    }

    ;


    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : limOrderComplete
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :지정가 주문완료
     */
    public int limOrderComplete(HttpServletRequest request, SendInfoVO vo) throws Exception {
        if (isSendYn(vo.getUserEmail(), "bsSb01")) {
            String Contents = getLocale(request, "sms.common.order1")
                    + vo.getTradeAmt() + "(" + vo.getCurcyNm() + ")";
            vo.setContents(Contents);
            vo.setPushType("CMMC00000000286");
            return sendSms(vo);
        }
        return 0;
    }

    ;

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : limOrderConclusion
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :지정가 체결
     */
    public int limBuyOrderConclusion(HttpServletRequest request, SendInfoVO vo) throws Exception {
        if (isSendYn(vo.getUserEmail(), "bsSb02")) {
            String Contents = getLocale(request, "sms.common.order3")
                    + vo.getTradeAmt() + vo.getCurcyNm() + "/" + vo.getTradePrc() + getDefaultCmmCd();
            vo.setContents(Contents);
            vo.setPushType("CMMC00000000286");
            return sendSms(vo);
        }
        return 0;
    }

    ;

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : limOrderConclusion
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :지정가 판매 체결
     */
    public int limSellOrderConclusion(HttpServletRequest request, SendInfoVO vo) throws Exception {
        if (isSendYn(vo.getUserEmail(), "bsSb04")) {
            String Contents = getLocale(request, "sms.common.order6")
                    + vo.getTradeAmt() + vo.getCurcyNm() + "/" + vo.getTradePrc() + getDefaultCmmCd();
            vo.setContents(Contents);
            vo.setPushType("CMMC00000000286");
            return sendSms(vo);
        }
        return 0;
    }

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : limOrderCancel
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :지정가구매 취소
     */
    public int limOrderCancel(HttpServletRequest request, SendInfoVO vo) throws Exception {

        String Contents = getLocale(request, "sms.common.order5")
                + vo.getTradeAmt() + vo.getCurcyNm();
        vo.setContents(Contents);
        vo.setPushType("CMMC00000000286");
        return sendSms(vo);
    }

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : marOrderComplete
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :시장가 판매 체결
     */
    public int marSellOrderComplete(HttpServletRequest request, SendInfoVO vo) throws Exception {
        if (isSendYn(vo.getUserEmail(), "bsSb04")) {
            String Contents = getLocale(request, "sms.common.order8")
                    + vo.getTradeAmt() + vo.getCurcyNm() + "/" + vo.getTradePrc() + getDefaultCmmCd();
            vo.setContents(Contents);
            vo.setPushType("CMMC00000000286");
            return sendSms(vo);
        }
        return 0;
    }

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : marOrderConclusion
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :시장가 구매 체결
     */
    public int marBuyOrderConclusion(HttpServletRequest request, SendInfoVO vo) throws Exception {
        if (isSendYn(vo.getUserEmail(), "bsSb02")) {
            String Contents = getLocale(request, "sms.common.order7")
                    + vo.getTradeAmt() + vo.getCurcyNm() + "/" + vo.getTradePrc() + getDefaultCmmCd();
            vo.setContents(Contents);
            vo.setPushType("CMMC00000000286");
            return sendSms(vo);
        }
        return 0;
    }

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : qnaComplate
     * @작성일 : 2017. 11. 22.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :1:1문의 답변완료
     */
    public int qnaComplate(SendInfoVO vo) throws Exception {
        String Contents = "문의하신 1:1문의에 답변이 완료되었습니다.";
        vo.setContents(Contents);
        vo.setPushType("CMMC00000000289");
        return sendSms(vo);
    }

    /**
     * @param vo
     * @throws Exception
     * @Method Name  : depositComplete
     * @작성일 : 2017. 12. 13.
     * @작성자 :  (주)씨엠이소프트 박상웅
     * @변경이력 :
     * @Method 설명 :
     */
    @Override
    public int depositComplete(HttpServletRequest request, SendInfoVO vo) throws Exception {
        if (isSendYn(vo.getUserEmail(), "bsIo03")) {
            String Contents = getLocale(request, "sms.common.deposit1") +
                    vo.getCrgPrc() + getLocale(request, "sms.common.deposit2");
            vo.setContents(Contents);
            vo.setPushType("CMMC00000000287");
            return sendSms(vo);
        }
        return 0;
    }

    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : limSalesOrderComplete
     * @작성일 : 2017. 12. 13.
     * @작성자 :  (주)씨엠이소프트 박상웅
     * @변경이력 :
     * @Method 설명 :
     */
    @Override
    public int limSalesOrderComplete(HttpServletRequest request, SendInfoVO vo) throws Exception {
        if (isSendYn(vo.getUserEmail(), "bsSb03")) {
            String Contents = getLocale(request, "sms.common.order4") +
                    vo.getTradeAmt() + vo.getCurcyNm();
            vo.setContents(Contents);
            vo.setPushType("CMMC00000000286");
            return sendSms(vo);
        }
        return 0;
    }

    /**
     * @param vo
     * @throws Exception
     * @Method Name  : limSalesOrderCancel
     * @작성일 : 2017. 12. 13.
     * @작성자 :  (주)씨엠이소프트 박상웅
     * @변경이력 :
     * @Method 설명 :
     */
    @Override
    public int limSalesOrderCancel(HttpServletRequest request, SendInfoVO vo) throws Exception {
        String Contents = getLocale(request, "sms.common.order5")
                + vo.getTradeAmt() + vo.getCurcyNm();
        vo.setContents(Contents);
        vo.setPushType("CMMC00000000286");
        return sendSms(vo);
    }

    public int smsDepoComp(HttpServletRequest request, SendInfoVO vo) throws Exception {
        if (isSendYn(vo.getUserEmail(), "bsIo01")) {
            String Contents = vo.getCnAmt() + vo.getCurcyNm()
                    + getLocale(request, "sms.common.deposit3");
            vo.setContents(Contents);
            vo.setPushType("CMMC00000000287");
            vo.setRegIp("127.0.0.1");
            return sendSms(vo);
        }
        return 0;
    }

    /**
     * @param vo
     * @throws Exception
     * @Method Name  : smsCoinWithComp
     * @작성일 : 2018. 04. 13.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 코인송금완료
     */
    public int smsCoinWithComp(HttpServletRequest request, SendInfoVO vo) throws Exception {
        if (isSendYn(vo.getUserEmail(), "bsIo02")) {
            String Contents = getLocale(request, "sms.common.withdrow1") + "\r\n"
                    + getLocale(request, "sms.common.withdrow2")
                    + vo.getCnAmt() + vo.getCurcyNm();
            vo.setContents(Contents);
            vo.setPushType("CMMC00000000287");
            vo.setRegIp("127.0.0.1");
            return sendSms(vo);
        }
        return 0;
    }

    /**
     * @param vo
     * @throws Exception
     * @Method Name  : smsCashWithComp
     * @작성일 : 2018. 04. 13.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력
     * @Method 설명 : 현금출금완료
     */
    public int smsCashWithComp(HttpServletRequest request, SendInfoVO vo) throws Exception {
        if (isSendYn(vo.getUserEmail(), "bsIo04")) {
            String Contents = getLocale(request, "sms.common.withdrow3")
                    + getLocale(request, "sms.common.withdrow4")
                    + vo.getCnAmt() + vo.getCurcyNm() +" \r\n"
                    + getLocale(request, "sms.common.withdrow3");
            vo.setContents(Contents);
            vo.setPushType("CMMC00000000287");
            vo.setRegIp("127.0.0.1");
            return sendSms(vo);
        }
        return 0;
    }

    public Map<String, Object> setLocaleVal(HttpServletRequest request, Map<String, Object> map, String str, int num) throws Exception {
        for (int i = 1; i <= num; i++) {
            map.put(str + i, getLocale(request, "mail.common." + str + i));
        }
        return map;
    }

    private boolean isSendYn(String userEmail, String val) throws Exception {
        PushYnVO vo = new PushYnVO();
        String isSendYn = "";
        vo.setUserEmail(userEmail);
        vo = cmeFcmPushService.selectPushYn(vo);
        if (val != null && val.substring(0, 1).equals("b") && "N".equals(vo.getAlrmBsYn())) {
            return false;
        }
        if (val != null && val.substring(0, 1).equals("t") && "N".equals(vo.getAlrmTmYn())) {
            return false;
        }
        Method[] methods = vo.getClass().getDeclaredMethods();
        for (int i = 0; i <= methods.length - 1; i++) {
            if (methods[i].getName().equals("get" + val.substring(0, 1).toUpperCase() + val.substring(1))) {
                //System.out.println("invoke : "+methodString);
                isSendYn = (String) methods[i].invoke(vo);
            }
        }

        return "Y".equals(isSendYn);
    }


    public int CertSms(HttpServletRequest request, Map<String, Object> map) throws Exception {
        int res = 0;

        SendInfoVO vo = new SendInfoVO();
        String userEmail = String.valueOf(map.get("mailTo"));
        vo.setUserEmail(userEmail);
        String msg = getLocale(request, "sms.common.0")
                + getLocale(request, "sms.common.cert1")
                + "\r\n"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        vo.setContents(msg);
        vo.setPushType("CMMC00000000945");
        res = sendSms(vo);

        return res;
    }

    public int sendCertSms(HttpServletRequest request, Map<String, Object> map) throws Exception {
        int res = 0;
        int i = 0;

        SendInfoVO vo = new SendInfoVO();
        String userEmail = map.get("userEmail").toString();
        String code = map.get("code").toString();
        String userMobile = map.get("userMobile").toString();

        String msg = "[" + code + "]"
                + getLocale(request, "sms.common.smsCert1")
                + "\r\n"
                + "[" + getLocale(request, "sms.common.smsCert2") + "]";
        vo.setContents(msg);
        vo.setMobile_info(userMobile);

        res = sendSms(vo, i);

        if (res > 0) {
            if ("U".equals(map.get("authType").toString())) {
                CmeLoginVO cvo = new CmeLoginVO();
                cvo.setUserEmail(userEmail);
                cvo.setAuthCode(code);
                cvo.setAuthType("U");
                int smsSend = loginDAO.sendSmsAuthCode2(cvo);
            } else {
                CmeLoginVO cvo = new CmeLoginVO();
                cvo.setUserEmail(userEmail);
                cvo.setAuthCode(code);
                cvo.setAuthType("S");
                CmeResultVO rvo = new CmeResultVO();
                rvo = loginDAO.sendSmsAuthCode(cvo);
            }
        }
        return res;
    }

    public int sendTrustPush(HttpServletRequest request, Map<String, Object> map) throws Exception {
        int res = 0;

        SendInfoVO vo = new SendInfoVO();
        String userEmail = String.valueOf(map.get("mailTo"));
        vo.setUserEmail(userEmail);
        String msg = getLocale(request, "sms.common.0")
                + getLocale(request, "sms.common.trust1")
                + "\r\n"
                + getLocale(request, "sms.common.trust4") + ":" + map.get("reqAmt").toString() + map.get("curcyNm").toString()
                + "\r\n"
                + getLocale(request, "sms.common.trust5") + ":" + map.get("reqDt").toString() + "~" + map.get("expireDt").toString() + "(" + map.get("month").toString() + getLocale(request, "sms.common.trust10") + ")"
                + "\r\n"
                + getLocale(request, "sms.common.trust6") + ":" + map.get("rate").toString() + "%";
        vo.setContents(msg);
        res = sendSms(vo);

        return res;
    }

    public int sendTrustSMS(HttpServletRequest request, Map<String, Object> map) throws Exception {
        int res = 0;
        int i = 0;

        SendInfoVO vo = new SendInfoVO();
        String userMobile = map.get("userMobile").toString();

        String msg = getLocale(request, "sms.common.0")
                + getLocale(request, "sms.common.trust1")
                + "\r\n"
                + getLocale(request, "sms.common.trust4") + ":" + map.get("reqAmt").toString() + map.get("curcyNm").toString()
                + "\r\n"
                + getLocale(request, "sms.common.trust5") + ":" + map.get("month").toString();

        vo.setContents(msg);
        vo.setMobile_info(userMobile);

        System.out.println(vo.getContents());

        res = sendSms(vo, i);

        return res;
    }

    public int sendTrustReleasePush(HttpServletRequest request, Map<String, Object> map) throws Exception {
        int res = 0;

        SendInfoVO vo = new SendInfoVO();
        String userEmail = String.valueOf(map.get("mailTo"));
        vo.setUserEmail(userEmail);
        String msg = getLocale(request, "sms.common.0")
                + getLocale(request, "sms.common.trust13")
                + "\r\n"
                + getLocale(request, "sms.common.trust11") + ":" + map.get("cancelDt").toString();

        vo.setContents(msg);
        res = sendSms(vo);

        return res;
    }

    public int sendTrustReleaseSMS(HttpServletRequest request, Map<String, Object> map) throws Exception {
        int res = 0;
        int i = 0;

        SendInfoVO vo = new SendInfoVO();
        String userMobile = map.get("userMobile").toString();

        String msg = getLocale(request, "sms.common.0")
                + getLocale(request, "sms.common.trust13")
                + "\r\n"
                + getLocale(request, "sms.common.trust11") + ":" + map.get("cancelDt").toString();
        vo.setContents(msg);
        vo.setMobile_info(userMobile);

        res = sendSms(vo, i);

        return res;
    }

    public int LendingApplySms(HttpServletRequest request, Map<String, Object> map) throws Exception {
        int res = 0;

        SendInfoVO vo = new SendInfoVO();
        String userEmail = String.valueOf(map.get("mailTo"));
        System.out.println("userEmail:" + String.valueOf(map.get("mailTo")));
        vo.setUserEmail(userEmail);
        String msg = getLocale(request, "sms.common.lending1")
                + "\r\n"
                + getLocale(request, "sms.common.lending6") + ":" + map.get("applyAmt").toString() + "(" + map.get("cnKndNm").toString() + ")"
                + "\r\n"
                + getLocale(request, "sms.common.lending7") + ":" + map.get("mthCmt").toString() + getLocale(request, "sms.common.lending15")
                + "\r\n"
                + getLocale(request, "sms.common.lending8") + ":" + map.get("rate").toString() + "%";
        vo.setContents(msg);
        res = sendSms(vo);

        return res;
    }

    public int extensionPush(HttpServletRequest request, Map<String, Object> map) throws Exception {
        int res = 0;

        SendInfoVO vo = new SendInfoVO();
        String userEmail = String.valueOf(map.get("mailTo"));
        System.out.println("userEmail:" + String.valueOf(map.get("mailTo")));
        vo.setUserEmail(userEmail);
        String msg = getLocale(request, "sms.common.lending4")
                + "\r\n"
                + getLocale(request, "sms.common.lending9") + ":" + map.get("totRepayAmt").toString() + map.get("cnKndNm").toString()
                + "\r\n"
                + getLocale(request, "sms.common.lending11") + ":" + map.get("extensionDate").toString();
        vo.setContents(msg);
        res = sendSms(vo);

        return res;
    }

    public int repaymentPush(HttpServletRequest request, Map<String, Object> map) throws Exception {
        int res = 0;

        SendInfoVO vo = new SendInfoVO();
        String userEmail = String.valueOf(map.get("mailTo"));
        System.out.println("userEmail:" + String.valueOf(map.get("mailTo")));
        vo.setUserEmail(userEmail);
        String msg = getLocale(request, "sms.common.lending2")
                + "\r\n"
                + getLocale(request, "sms.common.lending6") + ":" + map.get("applyAmt").toString() + map.get("cnKndNm").toString()
                + "\r\n"
                + getLocale(request, "sms.common.lending7") + ":" + map.get("mthCmt").toString() + getLocale(request, "sms.common.lending15")
                + "\r\n"
                + getLocale(request, "sms.common.lending14") + ":" + map.get("rate").toString() + "%";
        vo.setContents(msg);
        res = sendSms(vo);

        return res;
    }

    public int sendDepositPush(HttpServletRequest request , Map<String , Object> map) throws Exception{
        int res = 0;

        SendInfoVO vo = new SendInfoVO();
        String userEmail = String.valueOf(map.get("mailTo"));
        vo.setUserEmail(userEmail);
        System.out.println("sendDepositPush:" + vo.getUserEmail());
        String msg = map.get("amt").toString() + "" + map.get("cnKndNm").toString() + ""
                + getLocale(request , "mail.common.deposit5") + " " + getLocale(request , "mail.common.deposit6");
        vo.setContents(msg);
        res = sendSms(vo);

        return res;
    }

    public String getDefaultCmmCd(){
        String cmmNm = "KRW";
        try{
            cmmNm  = tradeService.getCmmNm();
        } catch (Exception e){
            System.out.println("공통화 기본통화가져오다 오류발생 smsApiService");
        }

        return cmmNm;
    }

    public int depositSendPush(HttpServletRequest request , DepositVO vo) throws Exception{
        int res = 0;

        SendInfoVO svo = new SendInfoVO();
        svo.setUserEmail(vo.getUserEmail());
        String msg = getLocale(request , "mail.common.deposit5") + getLocale(request , "mail.common.deposit6")
                + "\r\n"
                + getLocale(request , "mail.common.deposit2") + ":" + vo.getCnAmt() + coinInfoService.getCoinNm(vo.getCurcyCd());

        svo.setContents(msg);
        res = sendSms(svo);
        return res;
    }
}
