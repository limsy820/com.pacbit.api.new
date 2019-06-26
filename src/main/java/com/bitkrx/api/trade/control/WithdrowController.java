package com.bitkrx.api.trade.control;

import com.bitkrx.api.auth.service.LoginService;
import com.bitkrx.api.auth.service.RTPService;
import com.bitkrx.api.auth.vo.AuthVO;
import com.bitkrx.api.common.service.CoinInfoService;
import com.bitkrx.api.common.vo.CoinCoreInfoListVO;
import com.bitkrx.api.mail.service.impl.MailServiceImpl;
import com.bitkrx.api.moaCard.service.MoaCardService;
import com.bitkrx.api.moaCard.vo.MoaCardReqListVO;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.trade.service.DepositService;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.trade.service.WithdrowService;
import com.bitkrx.api.trade.vo.*;
import com.bitkrx.api.trust.service.TrustService;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.util.Security;
import com.bitkrx.config.util.SeedXUtil;
import com.bitkrx.config.vo.SendInfoVO;
import com.bitkrx.core.util.HttpComLib;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/bt")
public class WithdrowController extends CmeDefaultExtendController {

    @Autowired
    RTPService rService;

    @Autowired
    WithdrowService withService;

    @Autowired
    LoginService loginService;

    @Autowired
    SmsApiService smsApiService;

    @Autowired
    MailServiceImpl mailServiceImpl;

    @Autowired
    DepositService depositService;

    @Autowired
    MoaCardService moaCardService;

    @Autowired
    UserService userService;

    @Autowired
    CoinInfoService coinInfoService;

    @Autowired
    TrustService trustService;

    @Autowired
    TradeService tradeService;

    Security security = Security.getinstance();

    @RequestMapping(value = "/withdrow/getWithFee.dm")
    public @ResponseBody
    CmeResultVO getWithFee(HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        res.setResultStrCode("000");
        res.setResultMsg("출금수수료 조회 성공");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", withService.getWithFee());
        res.setData(map);
        return res;
    }

        @RequestMapping(value = "/withdrow/cash.dm")
    public @ResponseBody
    CmeResultVO cashWithdrow(WithdrowVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //rService.RTPVertify(request);


        Map<String, Object> map = new HashMap<String, Object>();
        CmeResultVO res = new CmeResultVO();
        res.setResultStrCode("000");
        map.put("failCd", "");

        if (vo.getUserEmail().equals("")) {
            map.put("failCd", "999");
            res.setResultMsg("EMAIL값이 없습니다.");
            res.setData(map);
            return res;
        } else if (vo.getWdrPrc().equals("")) {
            map.put("failCd", "998");
            res.setResultMsg("출금금액이 없습니다.");
            res.setData(map);
            return res;
        } else if (vo.getCnSndFee().equals("")) {
            map.put("failCd", "997");
            res.setResultMsg("출금수수료가 없습니다.");
            res.setData(map);
            return res;
        } else if (vo.getRegIp().equals("")) {
            map.put("failCd", "996");
            res.setResultMsg("아이피가 없습니다.");
            res.setData(map);
            return res;
        } else if (vo.getPayMthCd().equals("")) {
            map.put("failCd", "995");
            res.setResultMsg("출금구분이 없습니다.");
            res.setData(map);
            return res;
        }

        /*if(true) {
            res.setResultMsg("임시 송금 금지");
            map.put("failCd", "-7");
            res.setData(map);
            return res;
        }*/

        //테스트 계정들 출금 금지
        String[] userList = {
                "Minhokim1122@gmail.com",
                "init1@bitkrx.com",
                "init2@bitkrx.com",
                "init3@bitkrx.com",
                "init4@bitkrx.com",
                "init5@bitkrx.com"
        };
        for (String user : userList) {
            if (vo.getUserEmail().equals(user)) {
                map.put("failCd", "994");
                res.setResultMsg("테스트 계정으로 출금 할 수 없습니다.");
                res.setData(map);
                return res;
            }
        }

        vo.setCnKndCd("CMMC00000000204");
        double wdrPrc = 0;
        try {
            wdrPrc = Double.parseDouble(vo.getWdrPrc());

        } catch (Exception e) {
            map.put("failCd", "987");
            res.setData(map);
            return res;
        }

        //현금 출금시와 카드출금시 최대, 최소 출금가능금액이 달랐으나
        //같도록 변경완료함 2018.06.26
        //if(vo.getPayMthCd().equals("A")) {

        AuthVO avo = new AuthVO();
        avo.setUserEmail(vo.getUserEmail());
        avo = userService.selectUserAuth(avo);

        if (avo != null && "Y".equals(avo.getEmailCertYn()) && "".equals(avo.getOtpSerial())) {
            avo.setLvl("1"); // OTP 인증 단계
        } else if (avo != null && !"".equals(avo.getOtpSerial()) && !"1".equals(avo.getKycCertYn())) {
            avo.setLvl("2"); //KYC 인증 단계
        } else if (avo != null && "1".equals(avo.getKycCertYn())) {
            avo.setLvl("3"); //인증 완료 단계
        }

        MinMaxWithVO mvo = new MinMaxWithVO();

        if (avo != null && "2".equals(avo.getLvl())) {
            mvo = withService.getRemPrc(vo);
        } else if (avo != null && "3".equals(avo.getLvl())) {
            mvo = withService.getRemPrc3(vo);
        }

        if(vo.getLangCd().equals("zh") && avo.getLvl().equals("1")){
            mvo = withService.getRemPrc(vo);
        }

        int withdrowCheckCnt = withService.getFreeWithdrawCnt(vo);

        if(!"kim0681@hanmail.net".equals(vo.getUserEmail()) && withdrowCheckCnt == 0){
            //MinMaxWithVO mvo = withService.getRemPrc(vo);
            if (mvo != null && mvo.getMinAmt() - Double.parseDouble(vo.getCnSndFee()) >= wdrPrc) {
                map.put("failCd", "993");
                res.setResultMsg("최소송금액보다 작은 금액으로 출금할 수 없습니다.");
                res.setData(map);
                return res;
            } else if (mvo != null && mvo.getMaxAmt() < wdrPrc) {
                map.put("failCd", "992");
                res.setResultMsg("1회");
                res.setData(map);
                return res;
            } else if (mvo != null && mvo.getMaxAmt1day() < wdrPrc) {
                map.put("failCd", "985");
                res.setResultMsg("1일 최대 출금액 초과");
                res.setData(map);
                return res;
            }
        }

        //MinMaxWithVO mvo = withService.getRemPrc(vo);
        /*if (mvo != null && mvo.getMinAmt() - Double.parseDouble(vo.getCnSndFee()) >= wdrPrc) {
            map.put("failCd", "993");
            res.setResultMsg("최소송금액보다 작은 금액으로 출금할 수 없습니다.");
            res.setData(map);
            return res;
        } else if (mvo != null && mvo.getMaxAmt() < wdrPrc) {
            map.put("failCd", "992");
            res.setResultMsg("1회");
            res.setData(map);
            return res;
        } else if (mvo != null && mvo.getMaxAmt1day() < wdrPrc) {
            map.put("failCd", "992");
            res.setResultMsg("1일 최대 출금액 초과");
            res.setData(map);
            return res;
        }*/

        //}

        if (vo.getPayMthCd().equals("C") && !vo.getCardNo().equals("")) { //카드 출금일 경우
            String cardNo = security.decrypt(vo.getCardNo(), vo.getClientPe());
            System.out.println("복호화 전 cardNo : " + vo.getCardNo());
            System.out.println("복호화 후 cardNo : " + cardNo);
            vo.setCardNo(cardNo);
            MoaCardReqListVO reqVO = new MoaCardReqListVO();
            reqVO.setUserEmail(vo.getUserEmail());
            reqVO.setStatus("04");
            reqVO.setCardNum(vo.getCardNo());
            reqVO = moaCardService.selectUserCard(reqVO);

            if (reqVO == null) { //회원 본인 카드정보 아님
                map.put("failCd", "991");
                res.setData(map);
                return res;
            }

            DepositVO deVO = new DepositVO();
            deVO.setUserEmail(vo.getUserEmail());

            KrwWithVO kvo = depositService.getKrwWithdrow(deVO);
            if (Double.parseDouble(kvo.getKrwPrcCard()) < wdrPrc - 1) {
                map.put("failCd", "986");
                res.setResultMsg("카드출금시 카드출금가능금액 초과");
                res.setData(map);
                return res;
            }

        }

//        WithdrowVO wvo = withService.getExRateWithdrowPrc(vo);
        WithdrowVO wvo = new WithdrowVO();

        vo.setInCryCode(tradeService.getCmmNm());/*"KRW");*/
        vo.setExCryCode(tradeService.getCmmNm());/*"KRW");*/
        vo.setExRate("");
        vo.setOutPrc(vo.getWdrPrc());

        String wdrReqCode = withService.withdrowCash(vo);

        if (wdrReqCode != null && !wdrReqCode.equals("")) {
            res.setResultStrCode("000");
            res.setResultMsg("출금신청 성공");



            /*은행내역 데이터 추가*/
            DepositVO deVO = new DepositVO();
            deVO.setUserEmail(vo.getUserEmail());
            KrwWithVO kvo = depositService.getKrwWithdrow(deVO);
            kvo.setUserEmail(vo.getUserEmail());
            kvo.setWdrReqCode(wdrReqCode);
            withService.InsBankInfo(kvo);
            /*은행내역 데이터 추가*/

            int r = 0;
            if (vo.getPayMthCd().equals("C")) {
                wvo.setWdrReqCode(wdrReqCode);
                wvo.setUptEmail(vo.getUserEmail());
                wvo.setPayMthCd("C");
                wvo.setStatus("CMMC00000000426");
                //if(isOper()) {
                //r = sendCash(wvo);
                //}
            }

            if (r > 0) {
                try {

                    setLocale(request, response);

                    /*문자발송*/
                    SendInfoVO svo = new SendInfoVO();
                    /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
                    svo.setMobile_info(loginService.getUserMobile(vo.getUserEmail()));
                    svo.setCnAmt(vo.getWdrPrc());
                    svo.setUserEmail(vo.getUserEmail());
                    smsApiService.smsCashWithComp(request, svo);
                    /*문자발송*/

                    /*메일발송*/
                    Map<String, Object> model = new HashMap<String, Object>();
                    /*필수값 : mailTo(받는사람메일주소)*/
                    model.put("mailTo", vo.getUserEmail());
                    model.put("sysdate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
                    model.put("amount", vo.getWdrPrc());
                    mailServiceImpl.mailWithdrowApplication(request, model);
                    /*메일발송*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            map.put("failCd", "990");
            res.setResultMsg((String) map.get("RNT_MSG"));
            res.setResultStrCode("000");
        }

        res.setData(map);
        return res;
    }

    @RequestMapping(value = "/withdrow/cashList.dm")
    public @ResponseBody
    CmeResultVO cashList(WithdrowVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //rService.RTPVertify(request);

        Map<String, Object> map = new HashMap<String, Object>();

        CmeResultVO res = new CmeResultVO();
        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("EMAIL값이 없습니다.");
            return res;
        }

        List list = withService.getWithList(vo);
        map.put("list", list);
        res.setResultStrCode("000");
        res.setResultMsg("출금내역 조회 성공");
        res.setData(map);
        return res;
    }


    @RequestMapping(value = "/withdrow/coin.dm")
    public @ResponseBody
    CmeResultVO coinWithdrow(WithdrowVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        insSendParamLog(request);

        //rService.RTPVertify(request);

        Map<String, Object> map = new HashMap<String, Object>();

        CmeResultVO res = new CmeResultVO();
        //if(!isOper()) return res;
        res.setResultStrCode("000");
        map.put("failCd", "");

        CoinCoreInfoListVO cinfoVO = new CoinCoreInfoListVO();
        cinfoVO.setCnKndCd(vo.getCnKndCd());
        cinfoVO = coinInfoService.selectCoinCoreInfo(cinfoVO);

        //2019-06-07 양길호 추가 헤코코인 내부송금 오류(원인 50171052 테이블에 해코블록체인 정보가 없음) 예외처리
        if(!"CMMC10000000001".equals(vo.getCnKndCd().trim())){
            if(cinfoVO == null){
                map.put("failCd" , "777");
                res.setData(map);
                return res;
            }
        }

        if(vo.getLangCd() != null || "".equals(vo.getLangCd())){
            System.out.println("LangCd:" + vo.getLangCd());
        }


        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("EMAIL값이 없습니다.");
            map.put("failCd", "999");
            res.setData(map);
            return res;
        } else if (vo.getWdrReqAmt().equals("")) {
            res.setResultMsg("송금액이 없습니다.");
            map.put("failCd", "998");
            res.setData(map);
            return res;
        } else if (vo.getCnKndCd().equals("")) {
            res.setResultMsg("코인코드가 없습니다.");
            map.put("failCd", "997");
            res.setData(map);
            return res;
        } else if (vo.getCnSndFee().equals("")) {
            res.setResultMsg("코인송금수수료가 없습니다.");
            map.put("failCd", "996");
            res.setData(map);
            return res;
        } else if (vo.getWdrWletAdr().equals("")) {
            res.setResultMsg("코인송금주소가 없습니다.");
            map.put("failCd", "995");
            res.setData(map);
            return res;
        } else if (vo.getRegIp().equals("")) {
            res.setResultMsg("아이피가 없습니다.");
            map.put("failCd", "994");
            res.setData(map);
            return res;
        } else if (vo.getCnKndCd().equals("CMMC00000000208") && vo.getDestiTag().equals("")) {
            res.setResultMsg("리플일 경우, 데스티네이션 코드는 필수입니다.");
            map.put("failCd", "993");
            res.setData(map);
            return res;
        } else if (vo.getCnKndCd().equals("CMMC00000001206") && vo.getDestiTag().equals("")) {
            res.setResultMsg("VISTA 일 경우, 메세지는 필수입니다.");
            map.put("failCd", "993");
            res.setData(map);
            return res;
        }

        /*if(!vo.getUserEmail().equals("grace.alexlim@gmail.com")) {
            res.setResultMsg("임시 송금 금지");
            map.put("failCd", "-7");
            res.setData(map);
            return res;
        }*/

        //테스트 계정들 출금 금지
        String[] userList = {
                "Minhokim1122@gmail.com",
                "init1@bitkrx.com",
                "init2@bitkrx.com",
                "init3@bitkrx.com",
                "init4@bitkrx.com",
                "init5@bitkrx.com"
        };
        for (String user : userList) {
            if (vo.getUserEmail().equals(user)) {
                res.setResultMsg("테스트 계정으로 출금 할 수 없습니다.");
                return res;
            }
        }

        System.out.println("송금 process1 : " + vo.getUserEmail());
        System.out.println("송금 금액 : " + vo.getWdrReqAmt() + "송금 수수료 : " + vo.getCnSndFee());

        int withdrowCheckCnt = withService.getFreeWithdrawCnt(vo);

        System.out.println("코인출금:" + vo.getUserEmail() + "," + vo.getCnKndCd() + "," + withdrowCheckCnt);

        if ((!"duduri1004@daum.net".equals(vo.getUserEmail().trim()) || !"kim0681@hanmail.net".equals(vo.getUserEmail())) && withdrowCheckCnt == 0) {

            System.out.println("최소/최대 송금액 체크 : " + vo.getUserEmail());

            Double fee = Double.parseDouble(vo.getCnSndFee());
            Double wdrReqAmt = Double.parseDouble(vo.getWdrReqAmt());
            MinMaxWithVO mvo = withService.getCoinRemPrc(vo);

            Double maxAmt = 0D;
            Double minAmt = 0D;
            Double remAmtDay = 0D;



            if (mvo != null) {
                if (mvo.getGrade().equals("3")) {
                    maxAmt = mvo.getMaxAmtL3();
                    minAmt = mvo.getMinAmtL3();
                    //remAmtDay = mvo.getMaxAmtDayL3();
                    remAmtDay = mvo.getRemAmtDayL3();
                } else if (mvo.getGrade().equals("2")) {
                    maxAmt = mvo.getMaxAmt();
                    minAmt = mvo.getMinAmt();
                    //remAmtDay = mvo.getMaxAmtDay();
                    remAmtDay = mvo.getRemAmtDay();
                }
            }

            if(vo.getLangCd().equals("zh") && mvo.getGrade().equals("1")){
                maxAmt = mvo.getMaxAmt();
                minAmt = mvo.getMinAmt();
                remAmtDay = mvo.getMaxAmtDay();
            }



            /*송금 process1 : ymin2715@naver.com
            송금 금액 : 100000.11송금 수수료 : 0.01
            최소/최대 송금액 체크 : ymin2715@naver.com
            maxAmt : 100000.0, minAmt : 10.0, remAmtDay : 1000000.0
            wdrReqAmt : 100000.11, fee : 0.01*/

            BigDecimal _wdrReqAmt = new BigDecimal(vo.getWdrReqAmt());
            BigDecimal _cnSndFee = new BigDecimal(vo.getCnSndFee());
            BigDecimal _subtract = _wdrReqAmt.subtract(_cnSndFee);

            System.out.println("maxAmt : " + maxAmt + ", minAmt : " + minAmt + ", remAmtDay : " + remAmtDay);
            System.out.println("wdrReqAmt : " + wdrReqAmt + ", fee : " + fee);
            System.out.println("_subtract : " + _subtract);

            if (mvo != null && minAmt > _subtract.doubleValue()) {
                map.put("failCd", "-4");
                res.setResultMsg("최소송금액보다 작은 금액으로 송금할 수 없습니다.");
                res.setData(map);
                return res;
            } else if (mvo != null && maxAmt < _subtract.doubleValue()) {
                map.put("failCd", "-5");
                res.setResultMsg("1회 최대 송금액 초과");
                res.setData(map);
                return res;
            } else if (mvo != null && remAmtDay < _subtract.doubleValue()) {
                map.put("failCd", "-6");
                res.setResultMsg("1일 송금액 초과");
                res.setData(map);
                return res;
            }


        }

        System.out.println("송금 금액2 : " + vo.getWdrReqAmt() + "송금 수수료2 : " + vo.getCnSndFee());

        String amt = withService.getPosCnAmt(vo);

        double posAmt = 0;
        if (amt != null) {
            posAmt = Double.parseDouble(amt);
        }

        if (Double.parseDouble(vo.getWdrReqAmt()) > posAmt) {
            System.out.println("======================================================");
            res.setResultStrCode("000");
            res.setResultMsg("송금신청 실패");
            map.put("failCd", "-7");
            res.setResultMsg("가진 코인이 부족합니다.");
            res.setData(map);
            System.out.println("송금 실패 코드 -7 !!");
            System.out.println("======================================================");
            return res;
        }

        System.out.println("송금 금액3 : " + vo.getWdrReqAmt() + "송금 수수료3 : " + vo.getCnSndFee());

        String wdrReqCode = withService.withUserCoin(vo);

        String curcyNm = makeCoinNm(vo.getCnKndCd());

        String autoCoin = CoinAutoUserCheck(vo);

        if (wdrReqCode != null && !"".equals(wdrReqCode)) {
            if("Y".equals(autoCoin)){
                WithCoinListVO withCoinVO = new WithCoinListVO();
                withCoinVO.setUserEmail(vo.getUserEmail());
                withCoinVO.setWdrReqCode(wdrReqCode);
                withCoinVO.setRegIp(vo.getRegIp());
                withCoinVO.setWdrReqAmt(vo.getWdrReqAmt());
                withCoinVO.setCnSndFee(vo.getCnSndFee());
                System.out.println("자동 송금신청");
                CmeResultVO sendResult = sendCoin(request , withCoinVO);

                if(sendResult.getResultStrCode().equals("000")){
                    res.setResultStrCode("000");
                    res.setResultMsg("송금신청 성공");
                    map.put("failCd", "");
                    //map.put("wdrReqCpde" , wdrReqCode);
                    System.out.println("송금신청 성공");
                    sendMailPush(vo , request);
                }else {
                    res.setResultStrCode("000");
                    res.setResultMsg("송금신청 실패");
                    map.put("failCd", sendResult.getResultStrCode());
                }
            }else {
                res.setResultStrCode("000");
                res.setResultMsg("송금신청 성공");
                map.put("failCd", "");
                //map.put("wdrReqCode" , wdrReqCode);
                System.out.println("수동 송금신청");

                /* 내부송금 정책*/
                if(vo.getWdrWletAdr().contains(":")) {//리플, VSTC 일 경우 송금주소에서 데스티네이션코드 분리
                    vo.setDestiTag(vo.getWdrWletAdr().split(":")[1]);
                    vo.setWdrWletAdr(vo.getWdrWletAdr().split(":")[0]);
                } else {
                    vo.setWdrWletAdr(vo.getWdrWletAdr());
                    vo.setDestiTag(vo.getDestiTag());
                }
                WithdrowVO wvo = withService.getWletUserEmail(vo);//보내는 주소가 내부주소인지 확인
                if(wvo != null && !"".equals(wvo.getUserEmail())){
                    String inSndCheck = withService.getInSndCheck(vo.getCnKndCd());
                    if("Y".equals(inSndCheck) && !"O".equals(autoCoin)){ // 내부이면서 자동송금
                        WithCoinListVO withCoinVO = new WithCoinListVO();
                        withCoinVO.setUserEmail(vo.getUserEmail());
                        withCoinVO.setWdrReqCode(wdrReqCode);
                        withCoinVO.setRegIp(vo.getRegIp());
                        if("CMMC00000000208".equals(vo.getCnKndCd().trim()) ||  "CMMC00000001206".equals(vo.getCnKndCd().trim())){
                            withCoinVO.setWdrWletAdr(vo.getWdrWletAdr()+":"+vo.getDestiTag());
                        }else {
                            withCoinVO.setWdrWletAdr(vo.getWdrWletAdr());
                        }
                        withCoinVO.setCnSndFee(vo.getCnSndFee());
                        System.out.println("자동 송금 신청!");
                        CmeResultVO sendResult = sendCoin(request , withCoinVO);

                        if (sendResult.getResultStrCode().equals("000")) {
                            res.setResultStrCode("000");
                            res.setResultMsg("송금신청 성공");
                            map.put("failCd", "");
                            //map.put("wdrReqCode" , wdrReqCode);
                            System.out.println("송금신청 성공");
                            sendMailPush(vo, request);	// 성공시 메일푸시
                        } else {
                            res.setResultStrCode("000");
                            res.setResultMsg("송금신청 실패");
                            map.put("failCd", sendResult.getResultStrCode());
                        }
                    }else{  // 내부이면서 수동송금
                    sendMailPush(vo , request);
                    }
                }else{
                    sendMailPush(vo , request);
                }
            }
        } else {
            res.setResultStrCode("000");
            res.setResultMsg("송금신청 실패");
            map.put("failCd", "-3");
        }

        if(!"".equals(wdrReqCode) || wdrReqCode != null){
            map.put("wdrReqCode" , wdrReqCode);
        }
        res.setData(map);
        return res;
    }

    //어드민단에서 호출 하여 실제 코인송금시키는 API
    @RequestMapping(value = "/withdrow/coinAdm.dm")
    public @ResponseBody
    CmeResultVO withdrowCoin(WithCoinListVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //rService.RTPVertify(request);
        //insSendParamLog(request);
        setLocale(request, response);
        Map<String, Object> map = new HashMap<String, Object>();
        CmeResultVO res = new CmeResultVO();

        //if(!isOper()) return res;

        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("EMAIL값이 없습니다.");
            map.put("failCd", "-1");
            return res;
        } else if (vo.getWdrReqCode().equals("")) {
            res.setResultMsg("송금코드가 없습니다.");
            map.put("failCd", "-2");
            return res;
        } else if (vo.getRegIp().equals("")) {
            res.setResultMsg("아이피가 없습니다.");
            map.put("failCd", "-3");
            return res;
        }


		/*if("CMMC00000000685".equals(vo.getCnKndCd())) {
            map.put("failCd", "-3");
            res.setResultStrCode("-3");
            res.setData(map);
            return res;
        }*/

        String wdrReqCode = vo.getWdrReqCode();

        WithdrowVO wvo = new WithdrowVO();
        wvo.setUserEmail(vo.getUserEmail());
        wvo.setRegIp(vo.getRegIp());
        vo = withService.selectWithCoin(wdrReqCode);
        int uptRes = withService.uptWithCoinStaProgress(wdrReqCode);
        if (uptRes > 0 && vo != null) {

            BigDecimal wdrReqAmt = new BigDecimal(vo.getWdrReqAmt());
            BigDecimal cnSndFee = new BigDecimal(vo.getFeeAmt());
            BigDecimal subtract = wdrReqAmt.subtract(cnSndFee);
            String realAmt = subtract.toString();
            /*DecimalFormat df = new DecimalFormat("###0.00000000");

            System.out.println(realAmt);
            System.out.println(Double.parseDouble(realAmt));
            System.out.println(df.format(Double.parseDouble(realAmt)));

            realAmt = df.format(Double.parseDouble(realAmt));*/
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(8);
            nf.setRoundingMode(RoundingMode.DOWN);
            nf.setGroupingUsed(true);
            realAmt = nf.format(Double.parseDouble(realAmt)).replaceAll(",", "");

            System.out.println("realAmt : " + realAmt);

            wvo.setWdrReqCode(vo.getWdrReqCode());
            wvo.setRealSendAmt(realAmt);
            wvo.setCnSndFee(cnSndFee.toString());
            wvo.setCnKndCd(vo.getCnKndCd());
            wvo.setWdrReqAmt(vo.getWdrReqAmt());

            if (vo.getWdrWletAdr().contains(":")) {//리플, VSTC 일 경우 송금주소에서 데스티네이션코드 분리
                wvo.setWdrWletAdr(vo.getWdrWletAdr().split(":")[0]);
                wvo.setDestiTag(vo.getWdrWletAdr().split(":")[1]);
            } else {
                wvo.setWdrWletAdr(vo.getWdrWletAdr());
                wvo.setDestiTag(vo.getDestiTag());
            }
            wvo = withService.withdrowCoin(request , wvo);
            if (wvo.getResultCode().equals("000")) {
                if(wvo.getDepositEmail() != null){
                    /* 상대방 입금완료 푸시*/
                    Map<String , Object> depositMap = new HashMap<String , Object>();
                    depositMap.put("mailTo" , wvo.getDepositEmail());
                    depositMap.put("cnKndCd" , wvo.getCnKndCd());
                    depositMap.put("amt" , wvo.getWdrReqAmt());
                    depositMap.put("cnKndNm" , coinInfoService.getCoinNm(wvo.getCnKndCd()));
                    int i = smsApiService.sendDepositPush(request , depositMap);
                    /* 상대방 입금완료 푸시*/
                }
                map.put("failCd", "");
                res.setResultStrCode("000");
            } else if (wvo.getResultCode().equals("-2")) {
                map.put("failCd", "-2");
                res.setResultStrCode("-2");
            } else if (wvo.getResultCode().equals("-1")) {
                map.put("failCd", "-1");
                res.setResultStrCode("-1");
            } else {
                map.put("failCd", "-3");
                res.setResultStrCode("-3");
            }

        } else {
            res.setResultMsg("존재하지 않는 송금코드입니다.");
            map.put("failCd", "-4");
            return res;
        }

        res.setData(map);
        return res;
    }



    @RequestMapping(value = "/withdrow/coinList.dm")
    public @ResponseBody
    CmeResultVO coinList(WithdrowVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //rService.RTPVertify(request);

        Map<String, Object> map = new HashMap<String, Object>();

        CmeResultVO res = new CmeResultVO();
        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("EMAIL값이 없습니다.");
            return res;
        }

      /*  System.out.println("userEmail:" + vo.getUserEmail());
        System.out.println("cnKndCd:" + vo.getCnKndCd());*/

        List<WithCoinListVO> list = withService.getWithCoinList(vo);
        map.put("list", list);
        res.setResultStrCode("000");
        res.setResultMsg("코인송금내역 조회 성공");
        res.setData(map);
        return res;
    }


    @RequestMapping(value = "/withdrow/getBankList.dm")
    public @ResponseBody
    CmeResultVO getBankList(WithdrowVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        CmeResultVO res = new CmeResultVO();
        if ("".equals(vo.getCmmCd())) {
            vo.setCmmCd("CMMP00000000123");
        }
        List list = withService.getBankList(vo.getCmmCd());
        map.put("list", list);
        res.setResultStrCode("000");
        res.setResultMsg("은행내역 조회 성공");
        res.setData(map);
        return res;
    }

//    getWithList

    @RequestMapping(value = "/withdrow/cashAdm.dm")
    public @ResponseBody
    CmeResultVO cashAdm(WithdrowVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        rService.RTPVertify(request);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("failCd", "");
        CmeResultVO res = new CmeResultVO();
        res.setResultStrCode("000");

        if (!isOper()) return res;
        if (vo.getWdrReqCode().equals("")) {
            map.put("failCd", "999");
            res.setResultMsg("출금코드가 없습니다.");
            res.setData(map);
            return res;
        } else if (vo.getStatus().equals("")) {
            map.put("failCd", "998");
            res.setResultMsg("상태값이 없습니다.");
            res.setData(map);
            return res;
        } else if (!vo.getStatus().equals("CMMC00000000426") && !vo.getStatus().equals("CMMC00000000845")) {
            map.put("failCd", "997");
            res.setResultMsg("상태값이 없습니다.");
            res.setData(map);
            return res;
        } else if (vo.getUptEmail().equals("")) {
            map.put("failCd", "994");
            res.setResultMsg("처리자가 없습니다.");
            res.setData(map);
            return res;
        }

        System.out.println("cashAdm!");
        System.out.println("WdrReqCode" + vo.getWdrReqCode());
        System.out.println("Status:" + vo.getStatus());
        System.out.println("uptEmail:" + vo.getUptEmail());

        WithdrowVO wvo = withService.selectCashWith(vo.getWdrReqCode());
        if ("C".equals(wvo.getPayMthCd()) && vo.getStatus().equals("CMMC00000000426")) {
            //카드사 API(현금 출금, 카드충전) 호출
            System.out.println("카드출금 API 접속...");
            Map<String, Object> paramMap = new HashMap<String, Object>();


            SeedXUtil seedXUtil = SeedXUtil.getinstance();
            String userEmail = seedXUtil.SeedEncrypt(wvo.getUserEmail());
            String cardNum = seedXUtil.SeedEncrypt(wvo.getCardNo());
            String outPrc = seedXUtil.SeedEncrypt(wvo.getOutPrc());


            paramMap.put("TrdType", "0300");
            paramMap.put("MemID", userEmail);
            paramMap.put("CardNum", cardNum);
            //paramMap.put("CardPwd", vo.getUserEmail());
            paramMap.put("TrdPoint", outPrc);
            paramMap.put("TrdDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            paramMap.put("storeID", CmmCdConstant.STORE_ID);
            String rtnStr = HttpComLib.httpSendAPI(CmmCdConstant.CARD_URL, paramMap);

            System.out.println("카드출금일때:" + rtnStr);
            try {
                if (rtnStr.startsWith("00,")) {
                    String[] strs = rtnStr.split(",");
                    if ("XX".equals(strs[0])) {
                        map.put("rtnMsg", strs[1]);
                        map.put("failCd", "995");
                    } else {
                        withService.uptCashStatus(vo);
                    }
                } else {
                    map.put("failCd", "995");
                }
            } catch (Exception e) {
                map.put("failCd", "996");
            }
        }else if("A".equals(wvo.getPayMthCd()) && vo.getStatus().equals("CMMC00000000426")){
            //계좌 출금 API 호출
            /*System.out.println("계좌출금 API 접속...");
            DepositVO dvo = new DepositVO();
            dvo.setUserEmail(wvo.getUserEmail());
            KrwWithVO kvo = depositService.getKrwWithdrow(dvo);
            Map<String , Object> withdrowMap = new HashMap<String , Object>();

            System.out.println("BankNm:" + kvo.getBankNm());
            System.out.println("BankCd:" + kvo.getBankCd());
            System.out.println("userEmail:" + vo.getUserEmail());
            System.out.println("accntNm:" + kvo.getAccntNm());
            System.out.println("BankAccNo:" + kvo.getBankAccNo());
            System.out.println("wdrReqCode:" + wvo.getWdrReqCode());
            System.out.println("ourPrc:" + wvo.getOutPrc());

            SeedXUtil seedXUtil = SeedXUtil.getinstance();
            String BankNm = seedXUtil.SeedEncrypt(kvo.getBankNm());
            String BankCd = seedXUtil.SeedEncrypt(kvo.getBankCd());
            String userEmail = seedXUtil.SeedEncrypt(wvo.getUserEmail());
            String accntNm = seedXUtil.SeedEncrypt(kvo.getAccntNm());
            String BankAccNo = seedXUtil.SeedEncrypt(kvo.getBankAccNo());
            String wdrReqCode = seedXUtil.SeedEncrypt(wvo.getWdrReqCode());
            String ourPrc = seedXUtil.SeedEncrypt(wvo.getOutPrc());

            System.out.println("------------------------------암호화----------------------------");
            System.out.println("BankNm:" + BankNm);
            System.out.println("BankCd:" + BankCd);
            System.out.println("userEmail:" + userEmail);
            System.out.println("accntNm:" + accntNm);
            System.out.println("BankAccNo:" + BankAccNo);
            System.out.println("wdrReqCode:" + wdrReqCode);
            System.out.println("ourPrc:" + ourPrc);
            System.out.println("------------------------------암호화----------------------------");

            withdrowMap.put("BankNm" , BankNm);
            withdrowMap.put("BankCd" , BankCd);
            withdrowMap.put("userEmail" , userEmail);
            withdrowMap.put("accntNm" , accntNm);
            withdrowMap.put("BankAccNo" , BankAccNo);
            withdrowMap.put("wdrReqCode" , wdrReqCode);
            withdrowMap.put("outPrc" , ourPrc);

            String rtnStr = HttpComLib.httpSendAPI(CmmCdConstant.WITHDROW_ACC , withdrowMap);
            System.out.println("계좌출금:" + rtnStr);
            try {
                if (rtnStr.startsWith("00")) {
                    //String[] strs = rtnStr.split(",");
                    *//*if ("XX".equals(strs[0])) {
                        map.put("rtnMsg", rtnStr);
                        map.put("failCd", "994");
                    } else {
                        withService.uptCashStatus(vo);
                    }*//*
                    vo.setStatus("CMMC00000000091");
                    withService.uptCashStatus(vo);
                } else {
                    vo.setStatus("CMMC00000001486");
                    withService.uptCashStatus(vo);
                    map.put("failCd", "994");
                }
            } catch (Exception e) {
                vo.setStatus("CMMC00000001486");
                withService.uptCashStatus(vo);
                map.put("failCd", "993");
            }*/
            withService.uptCashStatus(vo);
        }

        // 출금거절
        if (vo.getStatus().equals("CMMC00000000845")) {
            CmeTradeReqVO reqVO = new CmeTradeReqVO();
            reqVO.setUserEmail(wvo.getUserEmail());
            reqVO.setCnclPrc("0");
            reqVO.setCrgPrc(wvo.getWdrPrc());
            depositService.depositCash(reqVO);
            withService.uptCashStatus(vo);
        }


        res.setData(map);
        return res;
    }


    //어드민단에서 호출 하여 코인송금 취소시키는 API
    @RequestMapping(value = "/withdrow/coinAdmFail.dm")
    public @ResponseBody
    CmeResultVO coinAdmFail(WithCoinListVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        rService.RTPVertify(request);
        Map<String, Object> map = new HashMap<String, Object>();
        CmeResultVO res = new CmeResultVO();
        res.setResultStrCode("000");

        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("EMAIL값이 없습니다.");
            map.put("failCd", "999");
            return res;
        } else if (vo.getWdrReqCode().equals("")) {
            res.setResultMsg("송금코드가 없습니다.");
            map.put("failCd", "998");
            return res;
        } else if (vo.getRegIp().equals("")) {
            res.setResultMsg("아이피가 없습니다.");
            map.put("failCd", "997");
            return res;
        }

        WithdrowVO wvo = new WithdrowVO();
        wvo.setRegIp(vo.getRegIp());
        wvo.setUserEmail(vo.getUserEmail());
        wvo.setWdrReqCode(vo.getWdrReqCode());
        vo = withService.selectWithCoin(vo.getWdrReqCode());
        if (vo.getStatus().equals("CMMC00000000101")) {
            res.setResultMsg("이미 취소된 송금입니다.");
            map.put("failCd", "994");
            return res;
        }

        try {
            if (vo != null) {
                wvo.setStatus("CMMC00000000101");
                wvo.setFailRsn("관리자 송금 취소");
                wvo.setWdrReqAmt(vo.getWdrReqAmt());
                wvo.setCnKndCd(vo.getCnKndCd());
                withService.PR_COIN_OUT_RTN(wvo);
                map.put("failCd", "");
            } else {
                res.setResultMsg("존재하지 않는 송금코드입니다.");
                map.put("failCd", "996");
                return res;
            }

        } catch (Exception e) {
            map.put("failCd", "995");
            return res;
        }

        res.setData(map);
        return res;
    }

    @RequestMapping(value = "/withdrow/limitCheck.dm")
    public @ResponseBody
    CmeResultVO limitCheck(WithdrowCheckVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("failCd", "");
        resultVO.setResultStrCode("000");

        if ("".equals(vo.getUserEmail())) {
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("이메일값이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if ("".equals(vo.getCurcyCd())) {
            resultMap.put("failCd", "998");
            resultVO.setResultMsg("통화코드가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }


        // "1" : 코인이 출금 가능이지만 회원이 예외처리 되어 출금이 제한된 경우(출금제한)
        // "2" : 코인이 출금 가능에다가 회원이 예외처리 되지 않아 출금 가능한 경우(출금가능)
        // "3" : 코인이 출금 제한이지만 회원이 예외처리 되어 출금이 가능한 경우(출금가능)
        // "4" : 코인이 출금 제한에다가 회원이 예외처리 되지 않아 출금 제한인 겨웅(출금제한)

        String userWithdrowCheck = "";
        int getWithdrowUser = 0;
        String LimitYn = withService.getCoinLimitYn(vo.getCurcyCd());
        WithdrowCheckVO wvo = new WithdrowCheckVO();

        /*String applyChk = trustService.getApplyChk(vo.getUserEmail());
        if("Y".equals(applyChk)){
            userWithdrowCheck = "1";
            resultMap.put("withdrowType", userWithdrowCheck);
            resultVO.setData(resultMap);
            return resultVO;
        }*/

        if ("Y".equals(LimitYn)) {
            // 출금가능인 경우
            vo.setSndLimtYn("N");
            // 출금가능한 경우에서 예외인 회원 조회(출금제한)
            getWithdrowUser = withService.getWithdrowUserCheck(vo);
            if (getWithdrowUser > 0) {
                userWithdrowCheck = "1";
            } else {
                userWithdrowCheck = "2";
            }
        } else {
            // 출금제한인 경우
            vo.setSndLimtYn("Y");
            // 출금제한인 경우에서 예외인 회원 조회(출금가능)
            getWithdrowUser = withService.getWithdrowUserCheck(vo);
            if (getWithdrowUser > 0) {
                userWithdrowCheck = "3";
            } else {
                userWithdrowCheck = "4";
            }
        }

        resultMap.put("withdrowType", userWithdrowCheck);
        resultVO.setData(resultMap);
        return resultVO;
    }


    public CmeResultVO sendCoin(HttpServletRequest request , WithCoinListVO vo) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        CmeResultVO res = new CmeResultVO();

        try {

            //System.out.println("1");
            //if (!isOper()) return res;
            String wdrReqCode = vo.getWdrReqCode();

            WithdrowVO wvo = new WithdrowVO();
            wvo.setUserEmail(vo.getUserEmail());
            wvo.setRegIp(vo.getRegIp());
            vo = withService.selectWithCoin(wdrReqCode);
            int uptRes = withService.uptWithCoinStaProgress(wdrReqCode);
            if (uptRes > 0 && vo != null) {

                BigDecimal wdrReqAmt = new BigDecimal(vo.getWdrReqAmt());
                BigDecimal cnSndFee = new BigDecimal(vo.getFeeAmt());
                BigDecimal subtract = wdrReqAmt.subtract(cnSndFee);
                String realAmt = subtract.toString();
                /*DecimalFormat df = new DecimalFormat("###0.00000000");

                System.out.println(realAmt);
                System.out.println(Double.parseDouble(realAmt));
                System.out.println(df.format(Double.parseDouble(realAmt)));

                realAmt = df.format(Double.parseDouble(realAmt));*/

                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(8);
                nf.setRoundingMode(RoundingMode.DOWN);
                nf.setGroupingUsed(true);
                realAmt = nf.format(Double.parseDouble(realAmt)).replaceAll(",", "");

                System.out.println("realAmt : " + realAmt);

                wvo.setWdrReqCode(vo.getWdrReqCode());
                wvo.setRealSendAmt(realAmt);
                wvo.setCnSndFee(vo.getCnSndFee());
                wvo.setCnKndCd(vo.getCnKndCd());
                wvo.setWdrReqAmt(vo.getWdrReqAmt());

                if (vo.getWdrWletAdr().contains(":")) {//리플, VSTC 일 경우 송금주소에서 데스티네이션코드 분리
                    wvo.setWdrWletAdr(vo.getWdrWletAdr().split(":")[0]);
                    wvo.setDestiTag(vo.getWdrWletAdr().split(":")[1]);
                } else {
                    wvo.setWdrWletAdr(vo.getWdrWletAdr());
                    wvo.setDestiTag(vo.getDestiTag());
                }


                try {
                    wvo = withService.withdrowCoin(request , wvo);
                } catch (Exception e) {
                    e.printStackTrace();
                    wvo.setResultCode("-3");
                    System.out.println("코인전송실패!!, 실패사유 : 알수없는 오류");
                    //실패시 코드
                    wvo.setStatus("CMMC00000000446");
                    wvo.setFailRsn("-3");
                    wvo.setFailLog("");
                    //wvo = withService.PR_COIN_OUT_RTN(wvo);
                }


                if (wvo.getResultCode().equals("000")) {
                    map.put("failCd", "");
                    res.setResultStrCode("000");
                } else if (wvo.getResultCode().equals("-2")) {
                    map.put("failCd", "-2");
                    res.setResultStrCode("-2");
                } else if (wvo.getResultCode().equals("-1")) {
                    map.put("failCd", "-1");
                    res.setResultStrCode("-1");
                } else {
                    map.put("failCd", "-3");
                    res.setResultStrCode("-3");
                }

            } else {
                res.setResultMsg("존재하지 않는 송금코드입니다.");
                map.put("failCd", "-4");
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        res.setData(map);
        return res;
    }

    public int sendCash(WithdrowVO vo) throws Exception {

        //if (!isOper()) return -1;

        WithdrowVO wvo = withService.selectCashWith(vo.getWdrReqCode());
        if (vo.getStatus().equals("CMMC00000000426")) {
            //카드사 API(현금 출금, 카드충전) 호출
            Map<String, Object> paramMap = new HashMap<String, Object>();

            SeedXUtil seedXUtil = SeedXUtil.getinstance();
            String userEmail = seedXUtil.SeedEncrypt(wvo.getUserEmail());
            String cardNum = seedXUtil.SeedEncrypt(wvo.getCardNo());
            String outPrc = seedXUtil.SeedEncrypt(wvo.getOutPrc());

            paramMap.put("TrdType", "0300");
            paramMap.put("MemID", userEmail);
            paramMap.put("CardNum", cardNum);
            paramMap.put("TrdPoint", outPrc);
            paramMap.put("TrdDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            paramMap.put("storeID", CmmCdConstant.STORE_ID);
            String rtnStr = HttpComLib.httpSendAPI(CmmCdConstant.CARD_URL, paramMap);

//            String rtnStr = "00,0,00";

            try {
                if (rtnStr.startsWith("00,")) {
                    String[] strs = rtnStr.split(",");
                    if ("XX".equals(strs[0])) {
                        return -2;
                    } else {
                        withService.uptCashStatus(vo);
                    }
                } else {
                    return -3;
                }
            } catch (Exception e) {
                return -4;
            }
        }

        return 1;
    }


    //어드민단에서 호출 하여 코인송금 실제 수수료 DB insert
    @RequestMapping(value = "/withdrow/coinSendRealFee.dm")
    public @ResponseBody
    CmeResultVO coinSendRealFee(WithdrowVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //rService.RTPVertify(request);
        Map<String, Object> map = new HashMap<String, Object>();
        CmeResultVO res = new CmeResultVO();
        res.setResultStrCode("000");
        map.put("failCd" , "");


        if (vo.getWdrReqCode().equals("")) {
            res.setResultMsg("송금코드가 없습니다.");
            map.put("failCd", "999");
            return res;
        } else if (vo.getRegIp().equals("")) {
            res.setResultMsg("아이피가 없습니다.");
            map.put("failCd", "998");
            return res;
        }

        try {
            WithCoinListVO wvo = withService.selectWithSendCoin(vo.getWdrReqCode());

            CoinCoreInfoListVO cinfoVO = new CoinCoreInfoListVO();
            cinfoVO.setCnKndCd(wvo.getCnKndCd());
            cinfoVO = coinInfoService.selectCoinCoreInfo(cinfoVO);

            String url = "";
            if (cinfoVO.getIsToken().equals("Y")) {//토큰일 경우
                url = cinfoVO.getCoinSendUrl();
            } else if (!cinfoVO.getCoinSendUrl().equals("")) {//코인 전송용 URL이 있을 경우
                url = cinfoVO.getCoinSendUrl();
            } else {
                url = cinfoVO.getCoinUrl();
            }

            System.out.println(cinfoVO.getCnKndNm() + " 코인 feeCheck 호출 : " + url + "/feeCheck/" + wvo.getDealNo());

            String json = HttpComLib.httpSendGetAPI(url + "/feeCheck/" + wvo.getDealNo());

            System.out.println(cinfoVO.getCnKndNm() + " 코인 feeCheck 호출 결과: " + json);

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(json);
            String result = (String) obj.get("result");
            String code = (String) obj.get("code");
            String message = (String) obj.get("message");

            System.out.println("result1 : " + result);
            System.out.println("code1 : " + code);
            System.out.println("message1 : " + message);

            if (result != null && !result.equals("")) {
                vo.setFeeRealAmt(result);
                int r = withService.uptRealFee(vo);
            }

            map.put("realFee", result);

        } catch (Exception e) {
            e.printStackTrace();
            res.setResultMsg("기타오류");
            map.put("failCd", "997");
            return res;
        }

        res.setData(map);
        return res;
    }


    @RequestMapping(value = "/withdrow/coinAutoCheck.dm")           // 수동출금 or 자동출금 API
    public @ResponseBody CmeResultVO  coinAutoCheck(WithdrowCheckVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();

        resultVO.setResultStrCode("000");
        resultMap.put("failCd" ,  "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getCurcyCd())){
            resultVO.setResultMsg("코인코드 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getWithdrowAmt())){
            resultVO.setResultMsg("출금수량 확인불가");
            resultMap.put("failCd" , "997");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String status = "";     // Y: 자동출금 , N:수동출금
        WithdrowCheckVO wvo = withService.getWithdrowYn(vo.getCurcyCd());
        if("Y".equals(wvo.getSndAutoYn())){// x 이하 자동출금(예외회원)
            if(Double.parseDouble(vo.getWithdrowAmt()) > Double.parseDouble(wvo.getSndAutoLimtAmt())){
                status = "N";
            }else {
                status = "Y";
            }
        }else if("N".equals(wvo.getSndAutoYn())){ // 무조건 수동출금(예외회원)
            int withDrowUserAutoCheck = withService.getWithdrowUserAutoCheck(vo);
            if(withDrowUserAutoCheck > 0){
                status = "Y";
            }else{
                status = "N";
            }
        }

        resultMap.put("status" , status);
        resultVO.setData(resultMap);
        return resultVO;
    }

    public String CoinAutoUserCheck(WithdrowVO vo) throws Exception{
        String autoYn = "";

        System.out.println("coinValue amt:" + vo.getWdrReqAmt());
        WithdrowVO coinValue = withService.getCoinAutoYn(vo.getCnKndCd());
        System.out.println("coinValue Yn:" + coinValue.getSndAutoYn());
        System.out.println("coinValue Limt:" + coinValue.getSndAutoLimtAmt());
        if("Y".equals(coinValue.getSndAutoYn())){
            if(Double.parseDouble(vo.getWdrReqAmt()) > Double.parseDouble(coinValue.getSndAutoLimtAmt())){
                autoYn = "O";
            }else{
                autoYn = "Y";
            }
        }else{
            int autoUserCheck = withService.getAutoUserCheck(vo);
            if(autoUserCheck > 0){
                if(Double.parseDouble(vo.getWdrReqAmt()) > Double.parseDouble(coinValue.getSndAutoLimtAmt())){
                    autoYn = "Y";
                }else{
                    autoYn = "Y";
                }
            }else{
                autoYn = "N";
            }
        }
        System.out.println("userEmail:" + vo.getUserEmail());
        System.out.println("cnKndCd:" + vo.getCnKndCd());
        System.out.println("autoYn:" + autoYn);
        return autoYn;
    }




    @RequestMapping(value = "/withdrow/coinSendCheck.dm")
    public @ResponseBody CmeResultVO  coinSendCheck(HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();

        resultVO.setResultStrCode("000");
        resultMap.put("failCd" ,  "");

        List<WithdrowVO> list = withService.getCoinSendingList();
        for(WithdrowVO vo : list) {
            if(!vo.getDealNo().equals("")) {
                String json = HttpComLib.httpSendGetAPI(vo.getCoinUrl() + "/txCheck/" + vo.getDealNo());
                System.out.println("송금 확인 결과 : " + json);

                String result = "";
                String code = "";
                String message = "";
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(json);
                String str = obj.toString();
                String[] strs = str.split(",");
                for (String s : strs) {
                    s = s.replaceAll("\"", "");
                    String[] _s = s.split(":");
                    if (_s[0].indexOf("result") > -1) {
                        if (_s.length > 1) {
                            result = _s[1];
                        }
                    } else if (_s[0].indexOf("code") > -1) {
                        if (_s.length > 1) {
                            code = _s[1];
                        }
                    } else if (_s[0].indexOf("message") > -1) {
                        if (_s.length > 1) {
                            for(int i = 0; i < _s.length; i++) {
                                if(i > 0) {
                                    message += _s[i];
                                }
                            }
                            message = message.replaceAll("}", "");
                        }
                    }
                }

                vo.setDealNo(vo.getDealNo());
                vo.setFailRsn(code);
                vo.setFailLog(message);
                vo.setRegIp("127.0.0.1");
                if ("0".equals(code)) { //정상송금상태
                    vo.setStatus("CMMC00000000099");
                    vo.setFeeRealAmt("0");
                    vo.setResultCode("000");
                    vo = withService.PR_COIN_OUT_RTN(vo);
                } else if ("-10".equals(code)) { //팬딩상태
                    System.out.println(vo.getDealNo() + " : pending 상태");
                } else if ("-20".equals(code)) { //전송 실패
                    vo.setResultCode(code);
                    System.out.println(vo.getDealNo() + " : 전송 실패 상태");
                    //실패시 코드
                    vo.setStatus("CMMC00000001388");
                    vo = withService.PR_COIN_OUT_RTN(vo);
                } else {    //알수 없는 오류
                    vo.setResultCode(code);
                    System.out.println("코인전송실패!!, 실패사유 : " + message);
                    //실패시 코드
                    vo.setStatus("CMMC00000001389");
                    vo = withService.PR_COIN_OUT_RTN(vo);
                }

            }
        }
        return resultVO;
    }

    @RequestMapping(value = "/withdrow/AccCheck.dm")        // 계좌출금 검증API
    public @ResponseBody CmeResultVO AccCheck(KrwWithVO2 vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        insParamLog(request);

        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultMap.put("failCd" , "");

        if("".equals(vo.getBankAccNo())){
            resultVO.setResultMsg("계좌번호 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getBankCd())){
            resultVO.setResultMsg("은행코드 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getAccntNm())){
            resultVO.setResultMsg("예금주 확인불가");
            resultMap.put("failCd" , "997");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "996");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getWdrReqCode())){
            resultVO.setResultMsg("출금코드 확인불가");
            resultMap.put("failCd" , "994");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getOutPrc())){
            resultVO.setResultMsg("출금금액 확인불가");
            resultMap.put("failCd" , "993");
            resultVO.setData(resultMap);
            return resultVO;
        }

        /* 파라미터 복호화 */
        SeedXUtil seedXUtil = SeedXUtil.getinstance();
        KrwWithVO2 kvo = new KrwWithVO2();
        kvo.setBankAccNo(seedXUtil.SeedDecrypt(vo.getBankAccNo()));
        kvo.setBankCd(seedXUtil.SeedDecrypt(vo.getBankCd()));
        kvo.setAccntNm(seedXUtil.SeedDecrypt(vo.getAccntNm()));
        kvo.setUserEmail(seedXUtil.SeedDecrypt(vo.getUserEmail()));
        kvo.setWdrReqCode(seedXUtil.SeedDecrypt(vo.getWdrReqCode()));
        kvo.setOutPrc(seedXUtil.SeedDecrypt(vo.getOutPrc()));
        /* 파라미터 복호화 */


        System.out.println("AccCheck!");
        System.out.println("BankAccNo:" + kvo.getBankAccNo());
        System.out.println("BankCd:" + kvo.getBankCd());
        System.out.println("AccntNm:" + kvo.getAccntNm());
        System.out.println("userEmail:" + kvo.getUserEmail());
        System.out.println("wdrReqCode:" + kvo.getWdrReqCode());
        System.out.println("outPrc:" + kvo.getOutPrc());

        int AccCheck = withService.getAccCheck(kvo);
        int AccCheck2 = withService.getWithdrowCheck(kvo);
        if(AccCheck2 == 1){
            if(AccCheck == 1){
                resultVO.setResultStrCode("000");


            }else{
                resultVO.setResultStrCode("999");
                resultVO.setResultMsg("검증실패");
                resultMap.put("failCd" , "995");
                resultVO.setData(resultMap);
                return resultVO;
            }
        }else{
            resultVO.setResultStrCode("999");
            resultVO.setResultMsg("검증실패");
            resultMap.put("failCd" , "995");
            resultVO.setData(resultMap);
            return resultVO;
        }

        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/withdrow/statusUpdate.dm")        // 폰뱅킹 출금확인(Y - 출금완료 , N - 출금실패)
    public @ResponseBody CmeResultVO statusUpdate(KrwWithVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        insParamLog(request);

        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultMap.put("failCd" , "");
        resultVO.setResultStrCode("");


        if("".equals(vo.getWdrReqCode())){
            resultVO.setResultMsg("거래번호 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getWithdrowYn())){
            resultVO.setResultMsg("성공여부 확인불가");
            resultMap.put("failCd" , "997");
            resultVO.setData(resultMap);
            return resultVO;
        }

        // 파라미터 복호화
        SeedXUtil seedXUtil = SeedXUtil.getinstance();
        KrwWithVO kvo = new KrwWithVO();
        kvo.setFailMsg(seedXUtil.SeedDecrypt(vo.getFailMsg()));

        System.out.println("iso-8859-1 -> utf-8 : " + new String(kvo.getFailMsg().getBytes("iso-8859-1"), "utf-8"));
        String failMsg = new String(kvo.getFailMsg().getBytes("iso-8859-1"), "utf-8");

        /*kvo.setWdrReqCode(seedXUtil.SeedDecrypt(vo.getWdrReqCode()));
        kvo.setUserEmail(seedXUtil.SeedDecrypt(vo.getUserEmail()));
        kvo.setWithdrowYn(seedXUtil.SeedDecrypt(vo.getWithdrowYn()));*/
        // 파라미터 복호화

        System.out.println("statusUpdate!");
        System.out.println("WdrReqCode:" + vo.getWdrReqCode());
        System.out.println("userEmail:" +  vo.getUserEmail());
        System.out.println("WithdrowYn:" + vo.getWithdrowYn());
        System.out.println("failMsg:" + failMsg);

        WithdrowVO wvo = new WithdrowVO();

        wvo.setWdrReqCode(vo.getWdrReqCode());
        wvo.setUptEmail(vo.getUserEmail());
        //wvo.setFailMsg(kvo.getFailMsg());
        wvo.setFailMsg(failMsg);

        System.out.println("성공여부:" + vo.getWithdrowYn());
        if("N".equals(vo.getWithdrowYn())){
            //wvo.setFailMsg(vo.getFailMsg());
            System.out.println("실패사유:" + vo.getFailMsg());
        }


        if("Y".equals(vo.getWithdrowYn())){
            wvo.setStatus("CMMC00000000426");
            withService.uptCashStatus(wvo);
            resultVO.setResultStrCode("000");
        }else{
            wvo.setStatus("CMMC00000001486");
            withService.uptCashStatus(wvo);
            resultVO.setResultStrCode("999");
            resultMap.put("failCd" , "996");
            resultVO.setData(resultMap);
            return resultVO;
        }


        resultVO.setData(resultMap);
        return resultVO;
    }


    public void sendMailPush(WithdrowVO vo , HttpServletRequest request) throws Exception{
        String curcyNm = makeCoinNm(vo.getCnKndCd());
        try {
            /*문자발송*/
            SendInfoVO svo = new SendInfoVO();
            /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
            svo.setMobile_info(vo.getUserMobile());
            svo.setCnAmt(vo.getWdrReqAmt());
            svo.setCurcyNm(curcyNm);
            svo.setUserEmail(vo.getUserEmail());
            smsApiService.smsCoinWithComp(request, svo);
            /*문자발송*/

            /*메일발송*/
            Map<String, Object> model = new HashMap<String, Object>();
            /*필수값 : mailTo(받는사람메일주소)*/
            model.put("mailTo", vo.getUserEmail());
            model.put("sysdate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            model.put("amount", String.valueOf(vo.getWdrReqAmt()));
            model.put("curcyNm", curcyNm);
            mailServiceImpl.mailWithdrowApplicationCoin(request, model);
            /*메일발송*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}






