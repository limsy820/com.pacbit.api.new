package com.bitkrx.api.auth.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitkrx.api.auth.vo.*;
import com.bitkrx.api.mail.service.impl.MailServiceImpl;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.util.Security;
import com.bitkrx.config.util.StringUtils;
import com.bitkrx.config.vo.SendInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitkrx.api.auth.dao.LoginDAO;
import com.bitkrx.api.auth.service.LoginService;
import com.bitkrx.api.auth.service.RTPService;
import com.bitkrx.api.trade.service.DepositService;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.logging.CmeCommonLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Controller
@RequestMapping(value = "/bt")
public class CmeLoginController extends CmeDefaultExtendController {
    protected CmeCommonLogger log = new CmeCommonLogger(this.getClass());

    @Autowired
    LoginService loginService;

    @Autowired
    RTPService rService;

    @Autowired
    LoginDAO loginDAO;

    @Autowired
    DepositService depositSvc;

    @Autowired
    MailServiceImpl mailServiceImpl;

    @Autowired
    SmsApiService smsApiService;

    @Autowired
    UserService userService;

    Security security = Security.getinstance();


    /**
     * @return
     * @throws Exception
     * @Method Name  : login
     * @작성일 : 2017. 10. 31.
     * @작성자 :  (주)씨엠이소프트 박상웅
     * @변경이력 :
     * @Method 설명 : 로그인
     * @서비스ID : IF-EX-001
     */
    @RequestMapping(value = "/auth/login.dm")
    public @ResponseBody
    CmeResultVO login(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //log.ViewErrorLog("---------x-add4sys01 아이피-------- : " + request.getHeader("x-add4sys01"));
        /*String ip = getIp(request, vo.getRegIp());
        vo.setRegIp(ip);
        vo.setIp(ip);*/

        if(CmmCdConstant.MTS_CD.equals(vo.getClientCd())){
            vo.setIp(vo.getRegIp());
        } else {
            String ip = getIp(request, vo.getRegIp());
            vo.setRegIp(ip);
            vo.setIp(ip);
        }


        //언어세팅
        setLocale(request, response);
        CmeResultVO resultVO = loginService.login(request, vo);
        return resultVO;
    }

    /**
     * @return
     * @throws Exception
     * @Method Name  : login
     * @작성일 : 2018. 09. 11.
     * @작성자 :  (주)씨엠이소프트 임우빈
     * @변경이력 :
     * @Method 설명 : pin로그인
     * @서비스ID : IF-EX-001
     */
    @RequestMapping(value = "/auth/pinLogin.dm")
    public @ResponseBody
    CmeResultVO pinLogin(@ModelAttribute CmeLoginVO vo, HttpServletRequest request , HttpServletResponse response) throws Exception{

        //log.ViewErrorLog("---------x-add4sys01 아이피-------- : " + request.getHeader("x-add4sys01"));
        /*String ip = getIp(request, vo.getRegIp());
        vo.setRegIp(ip);
        vo.setIp(ip);*/


        if(CmmCdConstant.MTS_CD.equals(vo.getClientCd())){
            vo.setIp(vo.getRegIp());
        } else {
            String ip = getIp(request, vo.getRegIp());
            vo.setRegIp(ip);
            vo.setIp(ip);
        }


        //언어세팅
        setLocale(request, response);
        CmeResultVO resultVO = loginService.pinlogin(request, vo);
        return resultVO;
    }


    @RequestMapping(value = "/auth/getUserAuth.dm")
    public @ResponseBody
    CmeResultVO getUserAuth(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //rService.RTPVertify(request);
        //System.out.println("getUserAuth");
        setLocale(request, response);
        CmeResultVO res = new CmeResultVO();
        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("사용자 EMAIL값이 없습니다.");
        } else {
            res.setData(loginService.getUserAuth(vo));
            res.setResultStrCode("000");
        }

        return res;
    }


    /**
     * @return
     * @throws Exception
     * @Method Name  : otp
     * @작성일 : 2017. 10. 31.
     * @작성자 :  (주)씨엠이소프트 박상웅
     * @변경이력 :
     * @Method 설명 : otp 체크
     * @서비스ID : IF-EX-002
     */
    @RequestMapping(value = "/auth/otp.dm")
    public @ResponseBody
    CmeOptVO otp(@ModelAttribute CmeOptVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeOptVO res = new CmeOptVO();
        if (vo.getOpt().equals("")) {
            res.setResultMsg("OTP값이 없습니다.");
            return res;
        }
        res.setOpt("1234567890");
        res.setOpt_yn("Y");
        res.setResultStrCode("000");
        return res;
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @Method Name  : accUnlock
     * @작성일 : 2017. 11. 21.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 계정잠금해제
     */
    @RequestMapping(value = "/auth/accUnlock.dm")
    public @ResponseBody
    CmeResultVO accUnlock(CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        rService.RTPVertify(request);
        setLocale(request, response);
        CmeResultVO resultVO = loginService.loginUnlock(request, vo);

        int res = 0;
        try {
            resultVO.setResultStrCode1(((Map) resultVO.getData()).get("RESULT").toString());
            res = resultVO.getResultStrCode1().indexOf("-1");//실패코드가 포함되어있는지 확인
        } catch (Exception e) {
            resultVO.setResultMsg("입력 도중 오류가 발생했습니다. 로그인 차단 리스트에 이용자가 없을 수 있습니다.");
        }

        try {
            if (res == -1) {//실패코드가 없을 시
                /*메일발송*/
                Map<String, Object> model = new HashMap<String, Object>();
                /*필수값 : mailTo(받는사람메일주소), clientCd(클라이언트코드)*/
                model.put("mailTo", vo.getUserEmail());
                model.put("clientCd", vo.getClientCd());
                mailServiceImpl.mailLoginUnlock(request, model);
                /*메일발송*/

                /*문자발송*/
                SendInfoVO svo = new SendInfoVO();
                /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
                svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
                svo.setClientCd(vo.getClientCd());
                svo.setUserEmail(vo.getUserEmail());
                smsApiService.smsLoginUnlock(request, svo);
                /*문자발송*/

                resultVO.setResultStrCode("000");
                resultVO.setResultMsg("계정 잠금해제 및 메일/SMS 발송 성공");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultVO;
    }


    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @Method Name  : accUnlock
     * @작성일 : 2017. 11. 21.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 로그인 5회 실패
     */
    @RequestMapping(value = "/auth/loginFail.dm")
    public @ResponseBody
    CmeResultVO loginFail(CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        rService.RTPVertify(request);
        setLocale(request, response);
        CmeResultVO resultVO = loginService.loginFail(request, vo);

        int res = 0;
        try {
            resultVO.setResultStrCode1(((Map) resultVO.getData()).get("RESULT").toString());
            res = resultVO.getResultStrCode1().indexOf("-1");//실패코드가 포함되어있는지 확인
        } catch (Exception e) {
            resultVO.setResultMsg("입력 도중 오류가 발생했습니다. 로그인 차단 리스트에 이용자가 없을 수 있습니다.");
        }

        try {
            if (res == -1) {//실패코드가 없을 시
                /*메일발송*/
                Map<String, Object> model = new HashMap<String, Object>();
                /*필수값 : mailTo(받는사람메일주소), clientCd(클라이언트코드)*/
                model.put("mailTo", vo.getUserEmail());
                model.put("clientCd", vo.getClientCd());
                mailServiceImpl.mailLoginFail(request, model);
                /*메일발송*/

                /*문자발송*/
                SendInfoVO svo = new SendInfoVO();
                /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
                svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
                svo.setClientCd(vo.getClientCd());
                svo.setUserEmail(vo.getUserEmail());
                smsApiService.smsLoginFail(request, svo);
                /*문자발송*/

                resultVO.setResultStrCode("000");
                resultVO.setResultMsg("계정 잠금 및 메일/SMS 발송 성공");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultVO;
    }
    // 대한
    // 예외처리

    /**
     * @return
     * @throws Exception
     * @Method Name  : userInsUpt
     * @작성일 : 2017. 12. 07
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 유저 등록 및 수정
     * @서비스ID :
     */                                                                                                                                                                                                                                                    // 메소드
    @RequestMapping(value = "/auth/userInsUpt.dm")
    public @ResponseBody
    CmeResultVO userInsUpt(CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);
        return loginService.INS10171020(vo);
    }                                                                                                                                                                                                                                                // public
    // ModelAndView
    // exHandler(HttpServletRequest
    // request,

    /**
     * @return
     * @throws Exception
     * @Method Name  : INS10171025
     * @작성일 : 2017. 12. 07
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 로그인 이력 남기기
     * @서비스ID :
     */
    @RequestMapping(value = "/auth/insLoginHis.dm")
    public @ResponseBody
    CmeResultVO INS10171025(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);
        return loginService.INS10171025(vo);

    }                                                                                                                                                                                                                                                    // Exception
    // ex){

    /**
     * @return
     * @throws Exception
     * @Method Name  : INS10171025
     * @작성일 : 2017. 12. 07
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 로그인 성공 문자, 메일 발송
     * @서비스ID :
     */
    @RequestMapping(value = "/auth/loginMailSms.dm")
    public @ResponseBody
    CmeResultVO loginMailSms(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);
        setLocale(request, response);
        return loginService.loginMailSms(request, vo);

    }
    // modelAndView

    /**
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @Method Name  : sendSmsAuthCode
     * @작성일 : 2017. 12. 31.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :6자리 인증번호발송
     */
    @RequestMapping(value = "/auth/sendSmsAuthCode.dm")
    public @ResponseBody
    CmeResultVO sendSmsAuthCode(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);
        setLocale(request, response);
        return loginService.sendSmsAuthCode(request, vo);

    }

    @RequestMapping(value = "/auth/SmsAuthCodeVertify.dm")
    public @ResponseBody
    CmeResultVO SmsAuthCodeVertify(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);
        setLocale(request, response);
        return loginService.SmsAuthCodeVertify(vo);

    }

    @RequestMapping(value = "/auth/getNatnCode.dm")
    public @ResponseBody
    CmeResultVO getNatnCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        List<CmeRcmdCode> list = loginService.getNatnCode();

        resultMap.put("list", list);
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/auth/getBrhCode.dm")
    public @ResponseBody
    CmeResultVO getBrhCode(@ModelAttribute CmeRcmdCode vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");
        if (vo.getNatnCode() == null || "".equals(vo.getNatnCode())) {
            resultVO.setResultMsg("국가코드 확인불가");
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

        List<CmeRcmdCode> list = loginService.getBrhCode(vo.getNatnCode());
        resultMap.put("list", list);

        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/auth/getRcmdCode.dm")
    public @ResponseBody
    CmeResultVO getRcmdCode(@ModelAttribute CmeRcmdCode vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        if (vo.getBrhCode() == null || "".equals(vo.getBrhCode())) {
            resultVO.setResultMsg("지점코드 확인불가");
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

        List<CmeRcmdCode> list = loginService.getRcmdCode(vo.getBrhCode());
        resultMap.put("list", list);

        resultVO.setData(resultMap);
        return resultVO;
    }


    @RequestMapping(value = "/auth/sendCertSms.dm")
    public @ResponseBody
    CmeResultVO sendSms(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        if("U".equals(vo.getAuthType())){
            setLocaleSms(request , response , vo.getLangCd());
        }else{
            setLocaleEn(request, response);
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");
        int res = 0;

        if ("".equals(vo.getUserEmail()) || vo.getUserEmail() == null) {
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("이메일 확인불가");
            resultVO.setData(resultMap);
            return resultVO;
        }

        if ("".equals(vo.getUserMobile()) || vo.getUserMobile() == null) {
            resultMap.put("failCd", "998");
            resultVO.setResultMsg("휴대폰번호 확인불가");
            resultVO.setData(resultMap);
            return resultVO;
        }

        System.out.println("userMobile:" + vo.getUserMobile());
        String code = StringUtils.RandomNumber(6);
        resultMap.put("userEmail", vo.getUserEmail());
        resultMap.put("code", code);
        resultMap.put("userMobile", vo.getUserMobile());
        resultMap.put("authType" , vo.getAuthType());
        /* SMS 전송 시작*/
        res = smsApiService.sendCertSms(request, resultMap);
        if (res == 1) {
            resultMap.put("failCd", "");
            resultVO.setResultMsg("전송 성공");
        } else {
            resultMap.put("failCd", "997");
            resultVO.setData(resultMap);
            resultVO.setResultMsg("전송 실패");
        }
        /* SMS 전송 끝 */

        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/auth/userCertSms.dm")
    public @ResponseBody
    CmeResultVO userCertSms(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("failCd", "");
        resultVO.setResultStrCode("000");

        String code = request.getParameter("code");

        if ("".equals(vo.getUserEmail()) || vo.getUserEmail() == null) {
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("이메일값이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if ("".equals(code) || code == null) {
            resultMap.put("failCd", "998");
            resultVO.setResultMsg("인증코드값이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String getCode = "";

        if("U".equals(vo.getAuthType())){
            getCode = loginService.SmsAuthCodeCert2(vo.getUserEmail());
        }else{
            getCode = loginService.SmsAuthCodeCert(vo.getUserEmail());
        }

        if ("".equals(getCode) || getCode == null) {
            resultMap.put("failCd", "997");
            resultVO.setResultMsg("인증번호 요청후 10분이 지났습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else {
            if (getCode.equals(code) || getCode == code) {
                resultVO.setResultMsg("인증번호가 일치합니다.");
            } else {
                resultVO.setResultMsg("인증번호가 일치하지 않습니다.");
                resultMap.put("failCd", "996");
                resultVO.setData(resultMap);
                return resultVO;
            }
        }


        resultVO.setData(resultMap);
        return resultVO;
    }



    /**
     * @return
     * @throws Exception
     * @Method Name  : pinCheck
     * @작성일 : 2018. 09. 05.
     * @작성자 :  (주)씨엠이소프트 임우빈
     * @변경이력 :
     * @Method 설명 : pin로그인
     * @서비스ID : IF-EX-001
     */
    @RequestMapping(value = "/auth/pinCheck.dm")
    public @ResponseBody
    CmeResultVO pinCheck(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultMap.put("failCd" , "");
        resultVO.setResultStrCode("000");
        if("".equals(vo.getUserEmail()) || vo.getUserEmail() == null){
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("사용자 이메일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        int res = 0;

        if("".equals(vo.getDeviceCd()) || vo.getDeviceCd() == null){
            res = loginService.pinNoCheck(vo);
            if(res > 0){
                resultVO.setResultMsg("핀번호가 있는 사용자입니다.");
            }else{
                resultMap.put("failCd", "998");
                resultVO.setResultMsg("핀번호가 없는 사용자입니다.");
                resultVO.setData(resultMap);
                return resultVO;
            }
        }else{
            res = loginService.pinNoCheck(vo);
            if(res > 0){
                resultVO.setResultMsg(vo.getDeviceNm() + "에 핀번호가 있는 사용자입니다.");
            }else{
                resultMap.put("failCd", "998");
                resultVO.setResultMsg(vo.getDeviceNm() + "에 핀번호가 없는 사용자입니다.");
                resultVO.setData(resultMap);
                return resultVO;
            }
        }



        resultVO.setData(resultMap);
        return resultVO;
    }


    /**
     * @return
     * @throws Exception
     * @Method Name  : pinInsert
     * @작성일 : 2018. 09. 05.
     * @작성자 :  (주)씨엠이소프트 임우빈
     * @변경이력 :
     * @Method 설명 : pin로그인
     * @서비스ID : IF-EX-001
     */
    @RequestMapping(value = "/auth/pinInsert.dm")
    public @ResponseBody
    CmeResultVO pinInsert(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultMap.put("failCd" , "");
        resultVO.setResultStrCode("000");

        if("".equals(vo.getUserEmail()) || vo.getUserEmail() == null){
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("사용자 이메일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getPinNo()) || vo.getPinNo() == null){
            resultMap.put("failCd", "998");
            resultVO.setResultMsg("사용자 핀번호가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getDeviceCd()) || vo.getDeviceCd() == null){
            resultMap.put("failCd", "997");
            resultVO.setResultMsg("사용자 디바이스코드가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getDeviceNm()) || vo.getDeviceNm() == null){
            resultMap.put("failCd", "996");
            resultVO.setResultMsg("사용자 디바이스명이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        vo.setPinNo(security.encPwd(vo.getPinNo() , vo.getClientPe()));
        int res = loginService.InsertPinNo(vo);
        if(res > 0){
            resultVO.setResultMsg("핀번호 등록에 성공하였습니다.");
        }else{
            resultMap.put("failCd" , "995");
            resultVO.setResultMsg("핀번호 등록에 실패하였습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        resultVO.setData(resultMap);
        return resultVO;
    }


    /**
     * @return
     * @throws Exception
     * @Method Name  : pinReset
     * @작성일 : 2018. 09. 05.
     * @작성자 :  (주)씨엠이소프트 임우빈
     * @변경이력 :
     * @Method 설명 : pin로그인
     * @서비스ID : IF-EX-001
     */
    @RequestMapping(value = "/auth/pinReset.dm")
    public @ResponseBody
    CmeResultVO pinReset(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{

        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        if("".equals(vo.getUserEmail()) || vo.getUserEmail() == null){
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("사용자 이메일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        if("CMMC00000000221".equals(vo.getClientCd()) || "CMMC00000000223".equals(vo.getClientCd())){
            if("".equals(vo.getUserPwd())){
                resultMap.put("failCd", "995");
                resultVO.setResultMsg("사용자 패스워드가 없습니다.");
                resultVO.setData(resultMap);
                return resultVO;
            }else if("".equals(vo.getClientPe())){
                resultMap.put("failCd", "994");
                resultVO.setResultMsg("복호화 키값이 없습니다.");
                resultVO.setData(resultMap);
                return resultVO;
            }
        }

        if("CMMC00000000221".equals(vo.getClientCd()) || "CMMC00000000223".equals(vo.getClientCd())){
            vo.setUserPwd(security.encPwd(vo.getUserPwd(), vo.getClientPe()));
            if(loginService.getPwdCheck(vo) == 0){
                resultMap.put("failCd", "993");
                resultVO.setResultMsg("비밀번호가 일치하지 않습니다.");
                resultVO.setData(resultMap);
                return resultVO;
            }
        }


        int res = loginService.pinNoCheck(vo);
        int res2 = loginService.pinUserCheck(vo);



        if(res2 > 0){
            vo.setPinNo("");
            if(res > 0){
                int resetPin = loginService.pinNoReset(vo);
                if(resetPin > 0){
                    resultVO.setResultMsg("핀번호가 초기화되었습니다.");
                }else {
                    resultMap.put("failCd", "997");
                    resultVO.setResultMsg("핀번호 초기화에  실패했습니다.");
                    resultVO.setData(resultMap);
                    return resultVO;
                }
            }else{
                resultVO.setResultMsg("핀번호를 초기화한 사용자입니다. MTS에서 등록해주시기 바랍니다.");
                resultMap.put("failCd", "996");
                resultVO.setData(resultMap);
                return resultVO;
            }
        }else{
            resultMap.put("failCd", "998");
            resultVO.setResultMsg("등록된 핀번호가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/auth/pinResetCheck.dm")
    public @ResponseBody
    CmeResultVO pinResetCheck(@ModelAttribute CmeLoginVO vo, HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

        //int res = loginService.pinUserCheck(vo);

        int res = loginService.pinNoCheck(vo);
        int res2 = loginService.pinUserCheck(vo);

        String pinResetYn = "Y";

        if(res2 > 0){
            if(res > 0){
                pinResetYn = "N";
            }else{
                pinResetYn = "Y";
            }
        }

        // Y - 초기화된 사용자 , N - 초기화안된사용자
        resultMap.put("pinResetYn" , pinResetYn);
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/auth/userAuthInfo.dm")
    public @ResponseBody
    CmeResultVO userAuthInfo(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();

        resultVO.setResultStrCode("000");
        resultMap.put("failCd",  "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }



        LoginResVO rvo = loginService.getUserAuthInfo(vo.getUserEmail());

        String ip = getIp(request, vo.getRegIp());

        String ipCheck = "";
        if("N".equals(userService.foreignIpCheck(ip))){
            String smsCertCheck = userService.getSmsCertCheck(vo.getUserEmail());
            if("1".equals(smsCertCheck)){
                ipCheck = "Y";
            }else{
                ipCheck = "N";
            }
        }else{
            ipCheck = "Y";
        }

        rvo.setForeignIp(ipCheck);

        resultMap.put("data" , rvo);
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/auth/fingerCheck.dm")// 지문등록유무 확인
    public @ResponseBody CmeResultVO fingerCheck(CmeLoginVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        rService.RTPVertify(request);
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일값 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getDeviceCd())){
            resultVO.setResultMsg("지문 디바이스코드 확인불가");
            resultMap.put("failCd" , "997");
            resultVO.setData(resultMap);
            return resultVO;
        }

        int fingerCheck = loginService.getFingetCheck(vo);
        if(fingerCheck > 0){
            resultVO.setResultMsg("지문이 등록된 사용자입니다.");
        }else{
            resultVO.setResultMsg("지문이 등록되지 않은 사용자입니다.");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }

        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/auth/fingerInsert.dm")
    public @ResponseBody CmeResultVO fingerInsert(CmeLoginVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        rService.RTPVertify(request);
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일값 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getDeviceCd())){
            resultVO.setResultMsg("지문 디바이스코드 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getDeviceNm())){
            resultVO.setResultMsg("지문 디바이스이름 확인불가");
            resultMap.put("failCd" , "997");
            resultVO.setData(resultMap);
            return resultVO;
        }
        // EMAIL OR DVCID 같으면 FNGR N처리
        loginService.uptFingerInfo(vo);

        int insertFinger = loginService.InsFingerInfo(vo);
        if(insertFinger > 0){
            resultVO.setResultMsg("지문등록 성공");
        }else{
            resultVO.setResultMsg("지문등록 실패");
            resultMap.put("failCd" , "996");
            resultVO.setData(resultMap);
            return resultVO;
        }

        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/auth/fingerLogin.dm")
    public @ResponseBody CmeResultVO fingerLogin(@ModelAttribute  CmeLoginVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        rService.RTPVertify(request);
        //log.ViewErrorLog("---------x-add4sys01 아이피-------- : " + request.getHeader("x-add4sys01"));
       /* String ip = getIp(request, vo.getRegIp());
        vo.setRegIp(ip);
        vo.setIp(ip);*/



        if(CmmCdConstant.MTS_CD.equals(vo.getClientCd())){
            vo.setIp(vo.getRegIp());
        } else {
            String ip = getIp(request, vo.getRegIp());
            vo.setRegIp(ip);
            vo.setIp(ip);
        }

        //언어세팅
        setLocale(request, response);
        CmeResultVO resultVO = loginService.fingerLogin(request, vo);
        return resultVO;
    }

    /**
     * @return
     * @throws Exception
     * @Method Name  : fingerReset
     * @작성일 : 2018. 12. 06.
     * @작성자 :  (주)씨엠이소프트 박동휘
     * @변경이력 :
     * @Method 설명 : finger로그인
     * @서비스ID :
     */
    @RequestMapping(value = "/auth/fingerReset.dm")
    public @ResponseBody
    CmeResultVO fingerReset(@ModelAttribute CmeLoginVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{

        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        if("".equals(vo.getUserEmail()) || vo.getUserEmail() == null){
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("사용자 이메일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if("".equals(vo.getDeviceCd()) || vo.getDeviceCd() == null){
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("디바이스 id가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        vo.setFingerStatus("");
        int resetFinger = loginService.fingerReset(vo);
        if(resetFinger > 0){
            resultVO.setResultMsg("지문이 초기화되었습니다.");
        }else {
            resultMap.put("failCd", "997");
            resultVO.setResultMsg("지문 초기화에  실패했습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        resultVO.setData(resultMap);
        return resultVO;
    }
}