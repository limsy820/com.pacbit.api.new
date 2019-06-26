package com.bitkrx.api.trust.control;

import com.bitkrx.api.auth.service.LoginService;
import com.bitkrx.api.common.service.CoinInfoService;
import com.bitkrx.api.mail.service.MailApiService;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.trade.service.WithdrowService;
import com.bitkrx.api.trade.vo.WithdrowVO;
import com.bitkrx.api.trust.service.TrustService;
import com.bitkrx.api.trust.vo.LendingVO;
import com.bitkrx.api.trust.vo.TrustCoinVO;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/bt")
public class TrustController extends CmeDefaultExtendController {

    @Autowired
    TrustService trustService;

    @Autowired
    WithdrowService withdrowService;

    @Autowired
    MailApiService mailApiService;

    @Autowired
    SmsApiService smsApiService;

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @Autowired
    CoinInfoService coinInfoService;

    @RequestMapping(value = "/trust/insTrustCoin.dm")        // trust 신청
    @ResponseBody public CmeResultVO insTrustCoin(TrustCoinVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();


        setLocale(request , response);
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getCnKndCd())){
            resultVO.setResultMsg("코인코드 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getReqAmt())){
            resultVO.setResultMsg("신청수량 확인불가");
            resultMap.put("failCd" , "997");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getRate())){
            resultVO.setResultMsg("이자율 확인불가");
            resultMap.put("failCd" , "996");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getExpInterest())){
            resultVO.setResultMsg("예상이자 확인불가");
            resultMap.put("failCd" , "995");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getReqDt())){
            resultVO.setResultMsg("신청일 확인불가");
            resultMap.put("failCd" , "994");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getExpireDt())){
            resultVO.setResultMsg("만기일 확인불가");
            resultMap.put("failCd" , "993");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getMonth())){
            resultVO.setResultMsg("개월수 확인불가");
            resultMap.put("failCd" , "889");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getRegIp())){
            resultVO.setResultMsg("IP 확인불가");
            resultMap.put("failCd" , "888");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String ip = getIp(request , vo.getRegIp());
        vo.setRegIp(ip);
        vo.setTrustStatus("CMMC00000001346");
        // 사용가능금액 체크로직 추가 1028

        WithdrowVO wvo = new WithdrowVO();
        wvo.setUserEmail(vo.getUserEmail());
        wvo.setCnKndCd(vo.getCnKndCd());
        String amt = withdrowService.getPosCnAmt(wvo);

        double posAmt = 0;
        if (amt != null) {
            posAmt = Double.parseDouble(amt);
        }

       /* double reqAmt = 0;
        if(vo.getReqAmt() != null){
            reqAmt = Double.parseDouble(vo.getReqAmt());
    }*/

        if(posAmt >= Double.parseDouble(vo.getReqAmt())){
            //190524 임승연 수정
            BigDecimal totRate = new BigDecimal("0");
            List <TrustCoinVO> rateList = trustService.getMonthRateList(vo.getCnKndCd()); //코인코드로 이자율 가져옴
            if(null != rateList){
                for(int i = 0 ; i < rateList.size() ; i ++){
                    totRate = totRate.add(new BigDecimal(rateList.get(i).getRate())); // DB에서 가져온 개월수에 따른 이자율 더함
                    if(vo.getMonth().equals(rateList.get(i).getMonth())){ //신청개월수와 DB에 month값이 같다면
                        break;
                    }
                } //for end
                vo.setRate(totRate.toString());
                BigDecimal interest = totRate.multiply(new BigDecimal(vo.getReqAmt())); //신청갯수 * 총이자율
                interest = interest.divide(new BigDecimal(100)); // (신청갯수 * 총이자율) / 100
                vo.setExpInterest(interest.toString());

            }else{
                resultVO.setResultMsg("이자율 DB 확인불가");
                resultMap.put("failCd" , "887");
                resultVO.setData(resultMap);
                return resultVO;
            }

            int applyTrust = trustService.intApplyTrust(vo);
            if(applyTrust > 0){
                vo.setPosAmt(Double.parseDouble(vo.getReqAmt()));
                int uptUserCoin = trustService.plusTrustCoin(vo);
                if(uptUserCoin > 0){
                    resultVO.setResultMsg("신청이 완료되었습니다.");

                    double num = Double.parseDouble(vo.getReqAmt()) + Double.parseDouble(vo.getExpInterest());
                    DecimalFormat df = new DecimalFormat("#,###.###");


                    /* 메일 , 푸시 , SMS 전송 */
                    Map<String , Object> map = new HashMap<String , Object>();
                    map.put("mailTo" , vo.getUserEmail());
                    map.put("userMobile" , loginService.getUserMobile(vo.getUserEmail()));
                    map.put("rate" , vo.getRate());
                    map.put("reqDt" , changeDate(vo.getReqDt()));
                    map.put("reqAmt" , vo.getReqAmt());
                    map.put("expireDt" , changeDate(vo.getExpireDt()));
                    map.put("expInterest" , vo.getExpInterest());
                    map.put("totAmt" , df.format(num));
                    map.put("month" , vo.getMonth());
                    map.put("curcyNm" , coinInfoService.getCoinNm(vo.getCnKndCd()));
                    mailApiService.sendTrustMail(request , map);
                    smsApiService.sendTrustPush(request , map);
                    //smsApiService.sendTrustSMS(request , map);
                    /* 메일 , 푸시 , SMS 전송 */


                }else{
                    resultVO.setResultMsg("신청에 실패했습니다.");
                    resultMap.put("failCd" , "991");
                    resultVO.setData(resultMap);
                    return resultVO;
                }
            }else{
                resultVO.setResultMsg("신청에 실패했습니다.");
                resultMap.put("failCd" , "992");
                resultVO.setData(resultMap);
                return resultVO;
            }
        }else{
            resultVO.setResultMsg("신청수량이 보유수량보다 적습니다.");
            resultMap.put("failCd" , "990");
            resultVO.setData(resultMap);
            return resultVO;
        }

        resultVO.setData(resultMap);
        return resultVO;
    }



    @RequestMapping(value = "/trust/Info.dm")                // trust 정보
    @ResponseBody public CmeResultVO trsutInfo(TrustCoinVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();

        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getCnKndCd())){
            resultVO.setResultMsg("코인코드 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }

        // 보유코인
        WithdrowVO wvo = new WithdrowVO();
        wvo.setUserEmail(vo.getUserEmail());
        wvo.setCnKndCd(vo.getCnKndCd());
        String amt = withdrowService.getPosCnAmt(wvo);
        //최소 신청 수량
        //String coinMinAmt = trustService.getCoinMinAmt(vo.getCnKndCd()); 테이블안나옴
        //기간별 이자율
        List<TrustCoinVO> list = trustService.getMonthRateList(vo.getCnKndCd());


        resultMap.put("amt" , amt);
        resultMap.put("coinMinAmt" , trustService.getCoinMinAmt(vo.getCnKndCd()));
        resultMap.put("list" , list);
        resultMap.put("KrwPrc" , trustService.getUserCoinPrc(vo));
        resultVO.setData(resultMap);
        return resultVO;
    }


    @RequestMapping(value = "/trust/list.dm")               // trust 신청내역
    @ResponseBody public CmeResultVO list(@ModelAttribute TrustCoinVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();

        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getListSize())){
            resultVO.setResultMsg("리스트사이즈 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }

        // List<TrustCoinVO> list = trustService.getTrustList(vo);

        if("CMMC00000000223".equals(vo.getClientCd())){
           // vo.setFirstIndex((vo.getPageIndex() - 1) * 10);
            vo.setFirstIndex((vo.getPage() -1) * 10);
            vo.setRecordCountPerPage(vo.getPageUnit());
            int listCnt = trustService.getTrustListCnt(vo);
            resultMap.put("listCnt" , listCnt);
            resultMap.put("pageSize" , (listCnt - 1) / 10 + 1);
            resultMap.put("page" , vo.getPage());
            resultMap.put("pageUnit" , vo.getPageUnit());
        }

        List<TrustCoinVO> list = trustService.getTrustList(vo);

        resultMap.put("list" , list);
        resultVO.setData(resultMap);
        return resultVO;
    }


    @RequestMapping(value = "/trust/releaseTrust.dm")       // trust 해지하기
    @ResponseBody public CmeResultVO releaseTrust(TrustCoinVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<>();

        setLocale(request, response);
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getTrustReqCode())){
            resultVO.setResultMsg("신청코드 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getCancelDt())){
            resultVO.setResultMsg("해지날짜 확인불가");
            resultMap.put("failCd" , "997");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String ip = getIp(request , vo.getRegIp());
        vo.setRegIp(ip);
        vo.setTrustStatus("CMMC00000001347");

        TrustCoinVO tvo = trustService.getTrustInfo(vo.getTrustReqCode());

        //190530 임승연 추가
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String minusDay = "";
        if("CMMC00000001650".equals(tvo.getCnKndCd())){
            minusDay = "20190530";
        }else if("CMMC10000000001".equals(tvo.getCnKndCd())){
            minusDay = "20190607";
        }else if("CMMC00000001467".equals(tvo.getCnKndCd())){
            minusDay = "20190624";
        }

        Date day1 = null; //신청일
        Date day2 = null; //위약금정책 시작한날
        Date day3 = null; //취소신청일
        int a = 0;
        int b = 0;
        try{
            day1 = dateFormat.parse(tvo.getReqDt());
            day2 = dateFormat.parse(minusDay);// 20190607 // 20190624

            a = day1.compareTo(day2);

            day3 = dateFormat.parse(vo.getCancelDt());

            b = day1.compareTo(day3); //day1 신청일 day3은 취소일


            if(a >= 0 && b < 0){
                vo.setMinusAsset("Y");
            }

        }catch (Exception e){
            System.out.println(e.toString());
        }

        vo.setCnKndCd(tvo.getCnKndCd());
        int uptTrust = trustService.uptTrustInfo(vo);
        if(uptTrust > 0){
            int uptUserCoin = trustService.minusTrustCoin(vo);//트러스트 관련 자산 백
            if(uptUserCoin > 0){
                resultVO.setResultMsg("Trust 해지에 성공했습니다.");

                Map<String , Object> map = new HashMap<String , Object>();
                map.put("reqAmt" , tvo.getReqAmt());
                map.put("reqDt" , changeDate(tvo.getReqDt()));
                map.put("cancelDt" , changeDate(vo.getCancelDt()));
                map.put("month" , tvo.getMonth());
                map.put("curcyNm" , coinInfoService.getCoinNm(tvo.getCnKndCd()));
                map.put("userMobile" , loginService.getUserMobile(tvo.getUserEmail()));
                map.put("mailTo" , tvo.getUserEmail());
                /* 메일 , 푸시 , SMS 전송 */
                mailApiService.sendTrustReleaseMail(request , map);
                smsApiService.sendTrustReleasePush(request , map);
                //smsApiService.sendTrustReleaseSMS(request , map);
                /* 메일 , 푸시 , SMS 전송 */
            }
        }else{
            resultVO.setResultMsg("Trust 해지에 실패했습니다.");
            resultMap.put("failCd" , "996");
            resultVO.setData(resultMap);
            return resultVO;
        }

        resultVO.setData(resultMap);
        return resultVO;
    }



    public String changeDate(String date) throws Exception{

        String yyyy = date.substring(0,4);
        String mm = date.substring(4,6);
        String dd = date.substring(6,8);

        String dt = yyyy + "-" + mm + "-" + dd;

        return dt;
    }

    /*랜딩 ------------------------------------------------------------------------------------------------------------ */


    @RequestMapping(value = "/lending/Apply.dm")        // 랜딩 신청
    @ResponseBody
    public CmeResultVO Apply(LendingVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("사용자 이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getCnKndCd())){
            resultVO.setResultMsg("통화코드 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getApplyDt())){
            resultVO.setResultMsg("신청일 확인불가");
            resultMap.put("failCd" , "997");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getApplyAmt())){
            resultVO.setResultMsg("신청수량 확인불가");
            resultMap.put("failCd" , "996");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getMthCmt())){
            resultVO.setResultMsg("개월수 확인불가");
            resultMap.put("failCd" , "995");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getRegIp())){
            resultVO.setResultMsg("ip 확인불가");
            resultMap.put("failCd" , "992");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String applyLendingChk = trustService.getApplyChk(vo.getUserEmail());
        if("N".equals(applyLendingChk)){
            vo.setRegEmail(vo.getUserEmail());
            //trustService.PR_INS601800012(vo);

            //vo = trustService.PR_INS60180001(vo);

            trustService.PR_INS60180001(vo);
            List list = (List) vo.getRESULT();
            Map<String , Object> map = new HashMap<String , Object>();
            map = (Map)list.get(0);
            //String rtnCd = (String) resultMap.get("RTN_CD");
            String rtnCd = String.valueOf(map.get("RTN_CD"));
            if(rtnCd != null && rtnCd.equals("1")){
                resultVO.setResultMsg("랜딩신청 성공");

                LendingVO lendingVO = trustService.getLendingInfo(String.valueOf(map.get("REN_DFT_CODE")));
                LendingVO lvo = trustService.getInterest(vo.getCnKndCd());
                /*랜딩 신청 메일 , 푸시*/
                Map<String , Object> applyMap = new HashMap<String , Object>();

                setLocale(request , response);
                applyMap.put("mailTo" , vo.getUserEmail());
                applyMap.put("applyAmt" , vo.getApplyAmt());                      // 신청수량
                applyMap.put("mthCmt" , vo.getMthCmt());                          // 개월수
                applyMap.put("rate" , lvo.getDftRate());                          // 이자율
                applyMap.put("cnKndNm" , coinInfoService.getCoinNm(vo.getCnKndCd()));   // 코인명
                applyMap.put("totRepayAmt" , lendingVO.getTotRepayAmt());     // 총상환액
                applyMap.put("applyDt" , changeDate(lendingVO.getApplyDt()));       // 신청일
                applyMap.put("expireDt" , changeDate(lendingVO.getExpireDt()));      // 만기일
                applyMap.put("userMobile" , loginService.getUserMobile(vo.getUserEmail()));
                applyMap.put("userEmail" , vo.getUserEmail());
                mailApiService.LendingApplyMail(request , applyMap);
                smsApiService.LendingApplySms(request , applyMap);
                /*랜딩 신청 메일 , 푸시*/

            }else{
                resultMap.put("failCd" , "993");
                resultVO.setResultMsg((String) map.get("RTN_MSG"));
                resultVO.setResultStrCode(rtnCd);
            }
            // 랜딩신청 프로시져 호출
        }else{
            resultVO.setResultMsg("이미 랜딩신청한 회원");
            resultMap.put("failCd" , "994");
            resultVO.setData(resultMap);
            return resultVO;
        }


        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/lending/Info.dm")        // 랜딩 신청 정보
    @ResponseBody
    public CmeResultVO Info(LendingVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getCnKndCd())){
            resultVO.setResultMsg("통화코드 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

        LendingVO lvo = trustService.getLendingAmt(vo.getCnKndCd());
        LendingVO lendingVO = trustService.getInterest(vo.getCnKndCd());


        resultMap.put("interest" , lendingVO.getDftRate());   // 이자율
        resultMap.put("minAmt" , lvo.getMinAmt());    // 최소신청수량
        resultMap.put("maxAmt" , lvo.getMaxAmt());
        resultMap.put("applyDt" , trustService.getLendingDate(vo));
        vo.setExpireDt("1");
        resultMap.put("expireDt" ,trustService.getLendingDate(vo));
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/lending/ApplyList.dm")    // 랜딩 신청내역
    @ResponseBody
    public CmeResultVO ApplyList(LendingVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("사용자 이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

        if("CMMC00000000223".equals(vo.getClientCd())){
            vo.setFirstIndex((vo.getPage() -1) * 10);
            vo.setRecordCountPerPage(vo.getPageUnit());
            int listCnt = trustService.getLendingListCnt(vo);
            resultMap.put("listCnt" , listCnt);
            resultMap.put("pageSize" , (listCnt - 1) / 10 + 1);
            resultMap.put("page" , vo.getPage());
            resultMap.put("pageUnit" , vo.getPageUnit());
        }

        List<LendingVO> list = trustService.getLendingList(vo);

        resultMap.put("list" , list);
        resultVO.setData(resultMap);
        return resultVO;
    }


    @RequestMapping(value = "/lending/repayment.dm")    // 상환하기
    @ResponseBody
    public CmeResultVO repayment(LendingVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getRenDftCode())){
            resultVO.setResultMsg("랜딩코드 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getRegIp())){
            resultVO.setResultMsg("ip 확인불가");
            resultMap.put("failCd" , "997");
            resultVO.setData(resultMap);
            return resultVO;
        }

        LendingVO lendingVO = trustService.getLendingInfo(vo.getRenDftCode());
        vo.setCnKndCd(lendingVO.getCnKndCd());
        trustService.PR_INS60180001_REPAY(vo);
        List list = (List) vo.getRESULT();
        Map<String , Object> map = new HashMap<String , Object>();
        map = (Map)list.get(0);
        //String rtnCd = (String) resultMap.get("RTN_CD");
        String rtnCd = String.valueOf(map.get("RTN_CD"));
        if(rtnCd != null && rtnCd.equals("1")){
            resultVO.setResultMsg("상환하기 성공");

            /* 메일 , 푸시 전송 */
            Map<String , Object> repaymentMap = new HashMap<String , Object>();

            setLocale(request , response);
            repaymentMap.put("mailTo" , vo.getUserEmail());
            repaymentMap.put("repayAmt" , lendingVO.getTotRepayAmt());
            repaymentMap.put("applyDt" , changeDate(lendingVO.getApplyDt()));
            repaymentMap.put("repayDt" , new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            repaymentMap.put("cnKndNm" , coinInfoService.getCoinNm(lendingVO.getCnKndCd()));
            repaymentMap.put("applyAmt" , lendingVO.getApplyAmt());
            repaymentMap.put("mthCmt" , lendingVO.getMthCmt());
            repaymentMap.put("rate" , lendingVO.getInterestRate());
            repaymentMap.put("userMobile" , loginService.getUserMobile(vo.getUserEmail()));
            repaymentMap.put("userEmail" , vo.getUserEmail());
            mailApiService.repaymentMail(request , repaymentMap);
            smsApiService.repaymentPush(request , repaymentMap);
            /* 메일 , 푸시 전송 */
        }else{
            resultMap.put("failCd" , "996");
            resultVO.setResultMsg((String) map.get("RNT_MSG"));
            resultVO.setResultStrCode(rtnCd);
        }

        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/lending/repaymentView.dm")    // 상환하기 뷰
    @ResponseBody
    public CmeResultVO repaymentView(LendingVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getRenDftCode())){
            resultVO.setResultMsg("랜딩코드 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

       /* LendingVO lendingVO = trustService.getLendingInfo(vo.getRenDftCode());
        LendingVO lvo = trustService.getInterest(lendingVO.getCnKndCd());

        double applyAmt = Double.parseDouble(lendingVO.getApplyAmt());
        double remainInterest = 0;
        double difDate = Double.parseDouble(trustService.getDifDate(lendingVO.getApplyDt()));

        if("N".equals(lendingVO.getExtensionYn())){
            if("0".equals(lendingVO.getRenDiv())){
                // 미연체 + 일반
                remainInterest = (Double.parseDouble(lvo.getDftRate()) / 30) * (difDate % 30) * applyAmt;
            }else{
                // 미연체 + 연장
                remainInterest = (Double.parseDouble(lvo.getExtensionRate()) / 30) * (difDate % 30) * applyAmt;
            }
        }else{
            if("0".equals(lendingVO.getRenDiv())){
                // 연체 + 일반
                remainInterest = ((Double.parseDouble(lvo.getExtensionRate()) / 30) + Double.parseDouble(lvo.getDftOverRate()) * (difDate % 30) * applyAmt);
            }else{
                // 연체 + 연장
                remainInterest = ((Double.parseDouble(lvo.getExtensionRate()) / 30) + Double.parseDouble(lvo.getExtensionOverRate()) * (difDate - 718) * applyAmt);
            }
        }*/

        LendingVO lendingVO = trustService.getLendingInfo(vo.getRenDftCode());


        LendingVO lvo = new LendingVO();
        lvo.setCnKndCd(lendingVO.getCnKndCd());
        lvo.setUserEmail(lendingVO.getUserEmail());
        lvo.setRenDftCode(vo.getRenDftCode());
        vo = trustService.PR_INS60180001_1_REPAY(lvo);
        List list = (List) vo.getRESULT();
        Map<String , Object> map = new HashMap<String , Object>();
        map = (Map)list.get(0);
        //String rtnCd = (String) map.get("RTN_CD");
        String rtnCd = String.valueOf(map.get("RTN_CD"));
        if(rtnCd != null && rtnCd.equals("1")){
            resultVO.setResultMsg("상환조회 성공");
        }else{
            resultMap.put("failCd" , "998");
            resultVO.setResultMsg((String) map.get("RNT_MSG"));
            resultVO.setResultStrCode(rtnCd);
        }

        resultMap.put("cnKndNm" , coinInfoService.getCoinNm(lendingVO.getCnKndCd()));
        resultMap.put("applyAmt" , String.valueOf(map.get("APPLY_AMT")));     // 원금
        resultMap.put("remainInterest" , String.valueOf(map.get("RATE_AMT3")));                 // 남은 상환 금액
        resultMap.put("totRepayAmt" , String.valueOf(map.get("TOT_REPAY")));
        resultMap.put("repayDt" , changeDate(String.valueOf(map.get("REPAY_DATE"))));
        resultVO.setData(resultMap);
        return resultVO;
    }


    @RequestMapping(value = "/lending/repaymentList.dm")    // 상환내역
    @ResponseBody
    public CmeResultVO repaymentList(LendingVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getRenDftCode())){
            resultVO.setResultMsg("랜딩코드 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

        /*if("CMMC00000000223".equals(vo.getClientCd())){
            vo.setFirstIndex((vo.getPage() -1) * 10);
            vo.setRecordCountPerPage(vo.getPageUnit());
            int listCnt = trustService.getRepaymentListCnt(vo);
            resultMap.put("listCnt" , listCnt);
            resultMap.put("pageSize" , (listCnt - 1) / 10 + 1);
            resultMap.put("page" , vo.getPage());
            resultMap.put("pageUnit" , vo.getPageUnit());
        }*/

        List<LendingVO> list = trustService.getRepaymentList(vo);

        resultMap.put("list" , list);
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/lending/extension.dm")    // 연장하기
    @ResponseBody
    public CmeResultVO extension(LendingVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getMthCmt())){
            resultVO.setResultMsg("개월수 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getRenDftCode())){
            resultVO.setResultMsg("랜딩코드 확인불가");
            resultMap.put("failCd" , "997");
            resultVO.setData(resultMap);
            return resultVO;
        }

        trustService.PR_INS60180001_EXTEND(vo);
        List list = (List) vo.getRESULT();
        Map<String , Object> map = new HashMap<String , Object>();
        map = (Map)list.get(0);
        //String rtnCd = (String) resultMap.get("RTN_CD");
        String rtnCd = String.valueOf(map.get("RTN_CD"));
        if(rtnCd != null && rtnCd.equals("1")){
            resultVO.setResultMsg("연장하기 성공");

            LendingVO lendingVO = trustService.getLendingInfo(vo.getRenDftCode());
            LendingVO lvo = trustService.getInterest(lendingVO.getCnKndCd());
            /* 메일 , 푸시 전송*/
            Map<String , Object> extensionMap = new HashMap<String , Object>();

            setLocale(request, response);
            extensionMap.put("mailTo" , vo.getUserEmail());      // 이메일
            extensionMap.put("mthCmt" , vo.getMthCmt());            // 기간(12개월)
            extensionMap.put("totRepayAmt" , lendingVO.getTotRepayAmt());   // 총 상환액
            extensionMap.put("extensionRate" , lvo.getExtensionRate());     // 연장이자율
            extensionMap.put("cnKndNm" , coinInfoService.getCoinNm(lendingVO.getCnKndCd()));    // 코인명
            extensionMap.put("extensionDate" , new SimpleDateFormat("yyyy-MM-dd").format(new Date()));  // 연장일
            extensionMap.put("applyDt" , changeDate(lendingVO.getApplyDt()));   // 신청일
            extensionMap.put("expireDt" , changeDate(lendingVO.getExpireDt())); // 만기일
            extensionMap.put("userMobile" , loginService.getUserMobile(vo.getUserEmail()));
            extensionMap.put("userEmail" , vo.getUserEmail());
            mailApiService.extensionMail(request , extensionMap);
            smsApiService.extensionPush(request , extensionMap);
            /* 메일 , 푸시 전송*/
        }else{
            resultMap.put("failCd" , "996");
            resultVO.setResultMsg((String) map.get("RTN_MSG"));
            resultVO.setResultStrCode(rtnCd);
        }

        resultVO.setData(resultMap);
        return resultVO;
    }


    @RequestMapping(value = "/lending/extensionView.dm")    // 연장하기 뷰
    @ResponseBody
    public CmeResultVO extensionView(LendingVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getRenDftCode())){
            resultVO.setResultMsg("랜딩코드 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

        LendingVO lvo = trustService.getExpireDt(vo.getRenDftCode());
        vo.setExtenSionDt(lvo.getExpireDt());

        LendingVO lendingVO = trustService.getInterest(lvo.getCnKndCd());

        resultMap.put("extensionRate" , lendingVO.getExtensionRate());
        resultMap.put("extensionDt" , trustService.getLendingDate(vo));
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/lending/lookUp.dm")    // 정보조회
    @ResponseBody
    public CmeResultVO lookUp(LendingVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/trust/interestList.dm")// trust 이자 지급 리스트
    @ResponseBody public CmeResultVO interestList(@ModelAttribute TrustCoinVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<>();

        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getListSize())){
            resultVO.setResultMsg("리스트사이즈 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }

        // List<TrustCoinVO> list = trustService.getTrustList(vo);

        if("CMMC00000000223".equals(vo.getClientCd())){ //WEB일때 분기 처리
            // vo.setFirstIndex((vo.getPageIndex() - 1) * 10);
            vo.setFirstIndex((vo.getPage() -1) * 10);
            vo.setRecordCountPerPage(vo.getPageUnit());
            int listCnt = trustService.getTrustInterestCnt(vo);
            resultMap.put("listCnt" , listCnt);
            resultMap.put("pageSize" , (listCnt - 1) / 10 + 1);
            resultMap.put("page" , vo.getPage());
            resultMap.put("pageUnit" , vo.getPageUnit());
        }

        List<TrustCoinVO> list = trustService.getTrustInterestList(vo); // 이자 지급 내역

        resultMap.put("list" , list);
        resultVO.setData(resultMap);
        return resultVO;
    }
}
