package com.bitkrx.api.auth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bitkrx.api.auth.vo.*;
import com.bitkrx.api.common.dao.CoinInfoDAO;
import com.bitkrx.api.common.vo.CommonExchgInfoVO;
import com.bitkrx.api.push.vo.PushYnVO;
import com.bitkrx.api.sample.service.SampleService;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import com.bitkrx.api.auth.dao.LoginDAO;
import com.bitkrx.api.auth.dao.UserIpDAO;
import com.bitkrx.api.auth.service.LoginService;
import com.bitkrx.api.mail.service.impl.MailServiceImpl;
import com.bitkrx.api.push.dao.CmeFcmPushDAO;
import com.bitkrx.api.push.service.CmeFcmPushService;
import com.bitkrx.api.push.vo.SendMsgVO;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.user.dao.UserDAO;

import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.logging.CmeCommonLogger;
import com.bitkrx.config.vo.SendInfoVO;
import com.bitkrx.core.util.RtpUtils;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginDAO loginDAO;

    @Autowired
    CmeFcmPushDAO pushDAO;

    @Autowired
    UserIpDAO ipDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserService userService;

    @Autowired
    MailServiceImpl mailServiceImpl;
    @Autowired
    SmsApiService smsApiService;
    @Autowired
    CmeFcmPushService cmeFcmPushService;

    @Autowired
    TradeService tradeService;

    @Autowired
    SampleService sampleService;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    CoinInfoDAO coinInfoDAO;

    RtpUtils rtpUtils = RtpUtils.getinstance();

    MapObjConvertUtil cUtil = MapObjConvertUtil.getinstance();

    Security security = Security.getinstance();

    protected CmeCommonLogger log = new CmeCommonLogger(this.getClass());

    public String getLocale(HttpServletRequest request, String key) {

        String msg = "";
        msg = messageSource.getMessage(key, null, "", localeResolver.resolveLocale(request));
        return msg.replaceAll("exchangeNm", CmmCdConstant.EXCHANGE_NAME);

    }

    @SuppressWarnings("unused")
    @Override
    public CmeResultVO login(HttpServletRequest request, CmeLoginVO vo) throws Exception {

        CmeResultVO cvo = new CmeResultVO();
        LoginResVO rvo = new LoginResVO();
        LoginResVO useVO = new LoginResVO();


        String clientNm = "";
        //01:HTS 02:MTS 03:WEB 04:ADM
        if (vo.getClientCd().equals(CmmCdConstant.HTS_CD)) {
            //clientNm = getLocale(request,"mail.common.login5");
            clientNm = "HTS";
        } else if (vo.getClientCd().equals(CmmCdConstant.MTS_CD)) {
            //clientNm = getLocale(request,"mail.common.login3");
            clientNm = "MTS";
        } else if (vo.getClientCd().equals(CmmCdConstant.WEB_CD)) {
            //clientNm = getLocale(request,"mail.common.login4");
            clientNm = "WEB";
        }


        /*로그인 history를 위한 map*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userEmail", vo.getUserEmail());
        //map.put("brwsCd", vo.getClientCd());
        map.put("osCd", clientNm);
        map.put("connIp", vo.getRegIp());
        map.put("brwsCd", clientNm);
        map.put("deviceCd", vo.getDeviceCd());

        /*공통화*/
        SubmitCertVO svo = new SubmitCertVO();
        System.out.println("lang:"+vo.getLangCd());
        if("".equals(vo.getLangCd())){
            svo.setLang("ko");
        } else if("ru".equals(vo.getLangCd())) {
            svo.setLang("kg");
        } else if("id".equals(vo.getLangCd())) {
            svo.setLang("in");
        } else {
            svo.setLang(vo.getLangCd());
        }
        CommonExchgInfoVO comvo = coinInfoDAO.commonExchgInfo(svo);



        if (loginDAO.getEmailCnt(vo) == 0) {
            cvo.setResultStrCode("");
            cvo.setResultStrCode("000");
            cvo.setResultMsg("아이디가 존재하지 않습니다.");
            map.put("failCd", CmmCdConstant.EMAIL_NOT_FOUND);
            map.put("loginYn", "N");
            cvo.setData(map);
            return cvo;
        }

        vo.setUserPwd(security.encPwd(vo.getUserPwd(), vo.getClientPe()));
        if (loginDAO.getPwdCheck(vo) == 0) {
            cvo.setResultStrCode("000");
            cvo.setResultMsg("비밀번호가 일치하지 않습니다.");
            map.put("failCd", CmmCdConstant.PWD_NOT_POUND);
            map.put("loginYn", "N");
            cvo.setData(map);
            return cvo;
        }

		/*int blockCnt = loginDAO.isBlockYn(vo.getUserEmail());
		if(blockCnt > 0) {
			rvo.setLoginYn("N");
			cvo.setResultStrCode("000");
			cvo.setResultMsg("로그인 5회 실패로 로그인이 제한되었습니다. 고객센터로 문의해주시기바랍니다. ");
			rvo.setFailCd(CmmCdConstant.EMAIL_BLOCK);
			rvo.setBlckYn("Y");
			map.put("failCd", CmmCdConstant.EMAIL_BLOCK);
			map.put("loginYn", "N");
			cvo.setData(map);
			return cvo;
		}*/
		
		/*if(vo.getUserEmail() == null || !vo.getUserEmail().equals("ymin2715@cmesoft.co.kr")) {
			rvo.setLoginYn("N");
			cvo.setResultStrCode("");
			cvo.setResultMsg("시스템 점검중입니다.");
			rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
			map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
			map.put("loginYn", "N");
			cvo.setResultStrCode("000");
			cvo.setData(rvo);
			return cvo;
		}*/

        String regIp = vo.getIp();
        String isIpFirst = "N";

        if (vo.getUserEmail().equals("")) {
            cvo.setResultMsg("메일 주소가 없습니다.");
            rvo.setFailCd(CmmCdConstant.EMAIL_IS_NULL);
            map.put("failCd", CmmCdConstant.EMAIL_IS_NULL);
            map.put("loginYn", "N");
        } else if (vo.getUserPwd().equals("")) {
            cvo.setResultMsg("비밀번호값이 없습니다.");
            rvo.setFailCd(CmmCdConstant.PWD_IS_NULL);
            map.put("failCd", CmmCdConstant.PWD_IS_NULL);
            map.put("loginYn", "N");
        } else if (vo.getClientCd().equals(CmmCdConstant.MTS_CD) && vo.getUserMobile().equals("")) {
            cvo.setResultMsg("휴대폰번호가 없습니다.");
            rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
            map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
            map.put("loginYn", "N");
        } else if (vo.getClientCd().equals("")) {
            cvo.setResultMsg("클라이언트 코드가 없습니다.");
            rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
            map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
            map.put("loginYn", "N");
        } else {
            UserIpVO ivo = new UserIpVO();
            //ivo.setIp(vo.getIp());
            ivo.setUserEmail(vo.getUserEmail());
            List<UserIpVO> ipList = ipDAO.selectUserIpList(ivo);
            if (ipList.size() == 0 && vo.getClientCd().equals(CmmCdConstant.HTS_CD) && vo.getIp() != null && !"".equals(vo.getIp())) {//ip등록 리스트가 하나도 없을 경우 자동등록
                ivo.setLimtHr("99999");
                ivo.setIp(vo.getIp());
                ipDAO.insUptIpList(ivo);
                isIpFirst = "Y";
            } else if (ipList.size() > 0 && vo.getClientCd().equals(CmmCdConstant.HTS_CD)) {
                boolean isIp = false;
                for (UserIpVO ipVO : ipList) {
                    if (ipVO.getIp().equals(regIp)) {
                        isIp = true;
                    }
                }
                if (!isIp) {
                    rvo.setLoginYn("N");
                    cvo.setResultStrCode("000");
                    cvo.setResultMsg("등록되어있지 않은 아이피입니다.");
                    rvo.setFailCd(CmmCdConstant.IP_NOT_FOUND);
                    map.put("failCd", CmmCdConstant.IP_NOT_FOUND);
                    map.put("loginYn", "N");
                    rvo.setRegIp(regIp);
                    rvo.setIsIpFirst(isIpFirst);
                    cvo.setResultStrCode("000");
                    cvo.setData(rvo);
                    return cvo;
                }
            }

            useVO = loginDAO.getUseYn(vo);
            int blockCnt = loginDAO.isBlockYn(vo.getUserEmail());

            if (loginDAO.getEmailCnt(vo) == 0) {
                rvo.setLoginYn("N");
                cvo.setResultStrCode("");
                cvo.setResultMsg("아이디가 존재하지 않습니다.");
                rvo.setFailCd(CmmCdConstant.EMAIL_NOT_FOUND);
                map.put("failCd", CmmCdConstant.EMAIL_NOT_FOUND);
                map.put("loginYn", "N");
            } else if ("N".equals(useVO.getUseYn())) {
                System.out.println(vo.getUserEmail() + "사용중지된 사용자");
                System.out.println("USE YN:" + useVO.getUseYn());
                rvo.setLoginYn("N");
                cvo.setResultStrCode("");
                cvo.setResultMsg("사용중지된 사용자입니다. 고객센터로 문의주시기 바랍니다.");
                rvo.setUseYn("N");
                rvo.setFailCd(CmmCdConstant.NOT_USE_ID);
                map.put("failCd", CmmCdConstant.NOT_USE_ID);
                map.put("loginYn", "N");
            } else if (blockCnt > 0) {
                rvo.setLoginYn("N");
                cvo.setResultStrCode("000");
                cvo.setResultMsg("로그인 5회 실패로 로그인이 제한되었습니다. 고객센터로 문의해주시기바랍니다. ");
                rvo.setFailCd(CmmCdConstant.EMAIL_BLOCK);
                rvo.setBlckYn("Y");
                map.put("failCd", CmmCdConstant.EMAIL_BLOCK);
                map.put("loginYn", "N");
            } else {

                //vo.setUserPwd(security.encPwd(vo.getUserPwd(), vo.getClientPe()));

                rvo = loginDAO.login(vo);

                if (rvo != null) {

                    cvo.setResultStrCode("000");

                    if (rvo.getLockYn().equals("Y")) {
                        rvo.setLoginYn("N");
                        cvo.setResultStrCode("");
                        cvo.setResultMsg("계정잠금된 계정입니다.");
                        rvo.setUseYn("N");
                        rvo.setFailCd(CmmCdConstant.NOT_USE_ID);
                        map.put("lockYn", "Y");
                        map.put("failCd", CmmCdConstant.NOT_USE_ID);
                        map.put("loginYn", "N");
                    } /*else if(rvo.getUseYn().equals("N")) {
						rvo.setLoginYn("N");
						cvo.setResultStrCode("");
						cvo.setResultMsg("사용중지된 사용자입니다. 고객센터로 문의주시기 바랍니다.");
						rvo.setUseYn("N");
						rvo.setFailCd(CmmCdConstant.NOT_USE_ID);
						map.put("failCd", CmmCdConstant.NOT_USE_ID);
						map.put("loginYn", "N");
					}*/ else if (rvo.getBlckYn().equals("Y") && rvo.getRelYn().equals("N")) {
                        rvo.setLoginYn("N");
                        cvo.setResultStrCode("");
                        cvo.setResultMsg("로그인 5회 실패로 로그인이 제한되었습니다. 고객센터로 문의해주시기바랍니다.");
                        rvo.setFailCd(CmmCdConstant.EMAIL_BLOCK);
                        map.put("failCd", CmmCdConstant.EMAIL_BLOCK);
                        map.put("loginYn", "N");
                    } else if (rvo.getPwdChgYn().equals("Y")) {
                        rvo.setLoginYn("N");
                        cvo.setResultStrCode("");
                        cvo.setResultMsg("비밀번호를 재설정한 사용자입니다. 비밀번호를 재설정 해주시기 바랍니다.");
                        rvo.setFailCd(CmmCdConstant.PWD_CHG_YN);
                        map.put("failCd", CmmCdConstant.PWD_CHG_YN);
                        map.put("loginYn", "N");
                    }  /*else if(vo.getClientCd().equals(CmmCdConstant.MTS_CD) && rvo.getUserMobile().equals("00000000000")) {
						rvo.setLoginYn("N");
						cvo.setResultStrCode("");
						cvo.setResultMsg("휴대폰본인 인증이 완료되지 않은 아이디는 MTS에 로그인하실 수 없습니다. 웹거래소로 로그인하여  휴대폰본인인증을 완료하신 후에 인증하신 휴대폰으로 로그인해주세요.");
						rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
						map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
						map.put("loginYn", "N");
					} else if(vo.getClientCd().equals(CmmCdConstant.MTS_CD) && !rvo.getUserMobile().equals(vo.getUserMobile())) {
						rvo.setLoginYn("N");
						cvo.setResultStrCode("");
						cvo.setResultMsg("인증하신 휴대폰번호와 해당 휴대폰번의 번호가 일치하지 않습니다. 휴대폰번호 변경 시 [웹,HTS > 인증센터] 에서 변경 후 이용해주시기 바랍니다.");
						rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
						map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
						map.put("loginYn", "N");
					}*/ else {

                        rvo.setLoginYn("Y");
                        cvo.setResultMsg("로그인에 성공하였습니다.");
                        String notice = pushDAO.pushNoticeList("");

                        if (notice != null) {
                            String[] strs = notice.split(":::");
                            if (strs.length > 1) {
                                if (strs[0].equals(userDAO.getUserLangCd(vo.getUserEmail()))) {
                                    rvo.setNotice(strs[1]);
                                }
                            }
                        }


                        map.put("failCd", "");
                        map.put("loginYn", "Y");

                        String sendMailYN = "";
                        String sendCode = "";
                        if ("HTS".equals(clientNm)) {
                            String getHtsIp = loginDAO.getHTSUserIp(vo.getUserEmail());
                            if (vo.getIp().equals(getHtsIp)) {
                                sendMailYN = "N";
                                sendCode = vo.getIp();
                            } else {
                                sendMailYN = "Y";
                                sendCode = vo.getIp();
                            }
                        } else if ("MTS".equals(clientNm)) {
                            String mtsDeviceCode = loginDAO.mtsDeviceCode(vo.getUserEmail());
                            if (vo.getDeviceCd().equals(mtsDeviceCode)) {
                                sendMailYN = "N";
                                sendCode = vo.getIp();
                            } else {
                                sendMailYN = "Y";
                                sendCode = vo.getIp();
                            }
                        }

                        SendMsgVO smvo = new SendMsgVO();
                        smvo.setUserEmail(vo.getUserEmail());
                        smvo.setSendYn("N");
                        smvo.setCmmCd("CMMC00000000284");
                        smvo.setMsg(clientNm + "|" + sendMailYN + "|" + sendCode);
                        cmeFcmPushService.PR_INSUPT_SND_MSG(smvo);

                    }

                } else {
                    rvo = new LoginResVO();
                    rvo.setLoginYn("N");
                    cvo.setResultStrCode("");
                    cvo.setResultMsg("비밀번호가 일치하지 않습니다.");
                    rvo.setFailCd(CmmCdConstant.PWD_NOT_POUND);
                    map.put("failCd", CmmCdConstant.PWD_NOT_POUND);
                    map.put("loginYn", "N");

                }


                rvo.setRegIp(regIp);
                rvo.setIsIpFirst(isIpFirst);
            }

        }

        try {
            loginDAO.INS10171025(map);
            cvo.setResultStrCode1(map.get("RESULT").toString());
        } catch (Exception e) {
            cvo.setResultStrCode1("로그인 내역 입력 도중 오류가 발생했습니다.");
        }


        String ipCheck = "";
        if ("N".equals(userService.foreignIpCheck(vo.getRegIp()))) {
            String smsCertCheck = userDAO.getSmsCertCheck(vo.getUserEmail());
            if ("1".equals(smsCertCheck)) {
                ipCheck = "Y";
            } else {
                ipCheck = "N";
            }
        } else {
            ipCheck = "Y";
        }

        rvo.setForeignIp(ipCheck);
        rvo.setTitle(comvo.getExchgName());
        cvo.setResultStrCode("000");
        cvo.setData(rvo);

        return cvo;
    }


    @SuppressWarnings("unused")
    @Override
    public CmeResultVO pinlogin(HttpServletRequest request, CmeLoginVO vo) throws Exception {

        CmeResultVO cvo = new CmeResultVO();
        LoginResVO rvo = new LoginResVO();
        LoginResVO useVO = new LoginResVO();
        //LoginResVO pinVO = new LoginResVO();

        String clientNm = "";
        //01:HTS 02:MTS 03:WEB 04:ADM
        if (vo.getClientCd().equals(CmmCdConstant.HTS_CD)) {
            //clientNm = getLocale(request,"mail.common.login5");
            clientNm = "HTS";
        } else if (vo.getClientCd().equals(CmmCdConstant.MTS_CD)) {
            //clientNm = getLocale(request,"mail.common.login3");
            clientNm = "MTS";
        } else if (vo.getClientCd().equals(CmmCdConstant.WEB_CD)) {
            //clientNm = getLocale(request,"mail.common.login4");
            clientNm = "WEB";
        }


        /*로그인 history를 위한 map*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userEmail", vo.getUserEmail());
        //map.put("brwsCd", vo.getClientCd());
        map.put("osCd", clientNm);
        map.put("connIp", vo.getRegIp());
        map.put("brwsCd", clientNm);

        map.put("deviceCd", vo.getDeviceCd());

		/*int blockCnt = loginDAO.isBlockYn(vo.getUserEmail());
		if(blockCnt > 0) {
			rvo.setLoginYn("N");
			cvo.setResultStrCode("000");
			cvo.setResultMsg("로그인 5회 실패로 로그인이 제한되었습니다. 고객센터로 문의해주시기바랍니다. ");
			rvo.setFailCd(CmmCdConstant.EMAIL_BLOCK);
			rvo.setBlckYn("Y");
			map.put("failCd", CmmCdConstant.EMAIL_BLOCK);
			map.put("loginYn", "N");
			cvo.setData(map);
			return cvo;
		}*/

		/*if(vo.getUserEmail() == null || !vo.getUserEmail().equals("ymin2715@cmesoft.co.kr")) {
			rvo.setLoginYn("N");
			cvo.setResultStrCode("");
			cvo.setResultMsg("시스템 점검중입니다.");
			rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
			map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
			map.put("loginYn", "N");
			cvo.setResultStrCode("000");
			cvo.setData(rvo);
			return cvo;
		}*/

        String regIp = vo.getIp();
        String isIpFirst = "N";

        if (vo.getUserEmail().equals("")) {
            cvo.setResultMsg("메일 주소가 없습니다.");
            rvo.setFailCd(CmmCdConstant.EMAIL_IS_NULL);
            map.put("failCd", CmmCdConstant.EMAIL_IS_NULL);
            map.put("pinLoginYn", "N");
        } else if (vo.getPinNo().equals("")) {
            cvo.setResultMsg("PIN번호 값이 없습니다.");
            rvo.setFailCd(CmmCdConstant.PIN_IS_NULL);
            map.put("failCd", CmmCdConstant.PIN_IS_NULL);
            map.put("pinLoginYn", "N");
        } else if (vo.getClientCd().equals(CmmCdConstant.MTS_CD) && vo.getUserMobile().equals("")) {
            cvo.setResultMsg("휴대폰번호가 없습니다.");
            rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
            map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
            map.put("pinLoginYn", "N");
        } else if (vo.getClientCd().equals("")) {
            cvo.setResultMsg("클라이언트 코드가 없습니다.");
            rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
            map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
            map.put("pinLoginYn", "N");
        } else {
            UserIpVO ivo = new UserIpVO();
            //ivo.setIp(vo.getIp());
            ivo.setUserEmail(vo.getUserEmail());
            List<UserIpVO> ipList = ipDAO.selectUserIpList(ivo);
            if (ipList.size() == 0 && vo.getClientCd().equals(CmmCdConstant.HTS_CD) && vo.getIp() != null && !"".equals(vo.getIp())) {//ip등록 리스트가 하나도 없을 경우 자동등록
                ivo.setLimtHr("99999");
                ivo.setIp(vo.getIp());
                ipDAO.insUptIpList(ivo);
                isIpFirst = "Y";
            } else if (ipList.size() > 0 && vo.getClientCd().equals(CmmCdConstant.HTS_CD)) {
                boolean isIp = false;
                for (UserIpVO ipVO : ipList) {
                    if (ipVO.getIp().equals(regIp)) {
                        isIp = true;
                    }
                }
                if (!isIp) {
                    rvo.setPinLoginYn("N");
                    cvo.setResultStrCode("000");
                    cvo.setResultMsg("등록되어있지 않은 아이피입니다.");
                    rvo.setFailCd(CmmCdConstant.IP_NOT_FOUND);
                    map.put("failCd", CmmCdConstant.IP_NOT_FOUND);
                    map.put("pinLoginYn", "N");
                    rvo.setRegIp(regIp);
                    rvo.setIsIpFirst(isIpFirst);
                    cvo.setResultStrCode("000");
                    cvo.setData(rvo);
                    return cvo;
                }
            }

            useVO = loginDAO.getUseYn(vo);
            int blockCnt = loginDAO.isBlockYn(vo.getUserEmail());
            int pinResetCheck = loginDAO.pinNoCheck(vo);

            if (loginDAO.getEmailCnt(vo) == 0) {
                rvo.setPinLoginYn("N");
                cvo.setResultStrCode("");
                cvo.setResultMsg("아이디가 존재하지 않습니다.");
                rvo.setFailCd(CmmCdConstant.EMAIL_NOT_FOUND);
                map.put("failCd", CmmCdConstant.EMAIL_NOT_FOUND);
                map.put("pinLoginYn", "N");
            } else if ("N".equals(useVO.getUseYn())) {
                System.out.println(vo.getUserEmail() + "사용중지된 사용자");
                System.out.println("USE YN:" + useVO.getUseYn());
                rvo.setPinLoginYn("N");
                cvo.setResultStrCode("");
                cvo.setResultMsg("사용중지된 사용자입니다. 고객센터로 문의주시기 바랍니다.");
                rvo.setUseYn("N");
                rvo.setFailCd(CmmCdConstant.NOT_USE_ID);
                map.put("failCd", CmmCdConstant.NOT_USE_ID);
                map.put("pinLoginYn", "N");
            } else if (blockCnt > 0) {
                rvo.setPinLoginYn("N");
                cvo.setResultStrCode("000");
                cvo.setResultMsg("로그인 5회 실패로 로그인이 제한되었습니다. 고객센터로 문의해주시기바랍니다. ");
                rvo.setFailCd(CmmCdConstant.EMAIL_BLOCK);
                rvo.setBlckYn("Y");
                map.put("failCd", CmmCdConstant.EMAIL_BLOCK);
                map.put("pinLoginYn", "N");
            } else if (pinResetCheck == 0) {
                rvo.setPinLoginYn("N");
                cvo.setResultStrCode("000");
                cvo.setResultMsg("PIN번호가 초기화된 사용자입니다. ");
                rvo.setFailCd(CmmCdConstant.PIN_REEST);
                rvo.setBlckYn("Y");
                rvo.setPinResetYn("N");
                map.put("failCd", CmmCdConstant.PIN_REEST);
                map.put("pinLoginYn", "N");
            } else {

                //vo.setUserPwd(security.encPwd(vo.getUserPwd(), vo.getClientPe()));
                vo.setPinNo(security.encPwd(vo.getPinNo(), vo.getClientPe()));

                int deviceCheckCnt = loginDAO.deviceCheck(vo);
                if (deviceCheckCnt > 0) {
                    int deviceSubCheckCnt = loginDAO.deviceSubCheck(vo);
                    if (deviceSubCheckCnt == 0) {
                        int uptPinInfo = loginDAO.uptPinInfo(vo);
                        cvo.setResultStrCode("");
                        cvo.setResultMsg("새로운 디바이스로 로그인하였습니다.");
                    }
                }

                int res = loginDAO.pinLoginCheck(vo);

                rvo = loginDAO.pinlogin(vo);

                if (rvo != null) {

                    cvo.setResultStrCode("000");

                    if (rvo.getLockYn().equals("Y")) {
                        rvo.setPinLoginYn("N");
                        cvo.setResultStrCode("");
                        cvo.setResultMsg("계정잠금된 계정입니다.");
                        rvo.setUseYn("N");
                        rvo.setFailCd(CmmCdConstant.NOT_USE_ID);
                        map.put("lockYn", "Y");
                        map.put("failCd", CmmCdConstant.NOT_USE_ID);
                        map.put("pinLoginYn", "N");
                    } /*else if(rvo.getUseYn().equals("N")) {
						rvo.setLoginYn("N");
						cvo.setResultStrCode("");
						cvo.setResultMsg("사용중지된 사용자입니다. 고객센터로 문의주시기 바랍니다.");
						rvo.setUseYn("N");
						rvo.setFailCd(CmmCdConstant.NOT_USE_ID);
						map.put("failCd", CmmCdConstant.NOT_USE_ID);
						map.put("loginYn", "N");
					}*/ else if (rvo.getBlckYn().equals("Y") && rvo.getRelYn().equals("N")) {
                        rvo.setPinLoginYn("N");
                        cvo.setResultStrCode("");
                        cvo.setResultMsg("로그인 5회 실패로 로그인이 제한되었습니다. 고객센터로 문의해주시기바랍니다.");
                        rvo.setFailCd(CmmCdConstant.EMAIL_BLOCK);
                        map.put("failCd", CmmCdConstant.EMAIL_BLOCK);
                        map.put("loginYn", "N");
                    } else if (rvo.getPwdChgYn().equals("Y")) {
                        rvo.setPinLoginYn("N");
                        cvo.setResultStrCode("");
                        cvo.setResultMsg("비밀번호를 재설정한 사용자입니다. 비밀번호를 재설정 해주시기 바랍니다.");
                        rvo.setFailCd(CmmCdConstant.PWD_CHG_YN);
                        map.put("failCd", CmmCdConstant.PWD_CHG_YN);
                        map.put("pinLoginYn", "N");
                    }  /*else if(vo.getClientCd().equals(CmmCdConstant.MTS_CD) && rvo.getUserMobile().equals("00000000000")) {
						rvo.setLoginYn("N");
						cvo.setResultStrCode("");
						cvo.setResultMsg("휴대폰본인 인증이 완료되지 않은 아이디는 MTS에 로그인하실 수 없습니다. 웹거래소로 로그인하여  휴대폰본인인증을 완료하신 후에 인증하신 휴대폰으로 로그인해주세요.");
						rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
						map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
						map.put("loginYn", "N");
					} else if(vo.getClientCd().equals(CmmCdConstant.MTS_CD) && !rvo.getUserMobile().equals(vo.getUserMobile())) {
						rvo.setLoginYn("N");
						cvo.setResultStrCode("");
						cvo.setResultMsg("인증하신 휴대폰번호와 해당 휴대폰번의 번호가 일치하지 않습니다. 휴대폰번호 변경 시 [웹,HTS > 인증센터] 에서 변경 후 이용해주시기 바랍니다.");
						rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
						map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
						map.put("loginYn", "N");
					}*/ else if (res == 1) {
                        rvo.setPinLoginYn("Y");
                        cvo.setResultMsg("로그인에 성공하였습니다.");
                        String notice = pushDAO.pushNoticeList("");

                        if (notice != null) {
                            String[] strs = notice.split(":::");
                            if (strs.length > 1) {
                                if (strs[0].equals(userDAO.getUserLangCd(vo.getUserEmail()))) {
                                    rvo.setNotice(strs[1]);
                                }
                            }
                        }


                        map.put("failCd", "");
                        map.put("pinLoginYn", "Y");

                        String sendMailYN = "";
                        String sendCode = "";
                        if ("HTS".equals(clientNm)) {
                            String getHtsIp = loginDAO.getHTSUserIp(vo.getUserEmail());
                            if (vo.getIp().equals(getHtsIp)) {
                                sendMailYN = "N";
                                sendCode = " ";
                            } else {
                                sendMailYN = "Y";
                                sendCode = vo.getIp();
                            }
                        } else if ("MTS".equals(clientNm)) {
                            String mtsDeviceCode = loginDAO.mtsDeviceCode(vo.getUserEmail());
                            if (vo.getDeviceCd().equals(mtsDeviceCode)) {
                                sendMailYN = "N";
                                sendCode = vo.getIp();
                            } else {
                                sendMailYN = "Y";
                                sendCode = vo.getIp();
                            }
                        }

                        if ("Y".equals(vo.getPinPushYn())) {
                            SendMsgVO smvo = new SendMsgVO();
                            smvo.setUserEmail(vo.getUserEmail());
                            smvo.setSendYn("N");
                            smvo.setCmmCd("CMMC00000000284");
                            smvo.setMsg(clientNm + "|" + sendMailYN + "|" + sendCode);
                            cmeFcmPushService.PR_INSUPT_SND_MSG(smvo);
                        }
                    } else {
                        rvo.setPinLoginYn("N");
                        cvo.setResultMsg("로그인에 실패했습니다.");
                        rvo.setUseYn("N");
                    }

                } else {
                    rvo = new LoginResVO();
                    rvo.setPinLoginYn("N");
                    cvo.setResultStrCode("");
                    cvo.setResultMsg("PIN번호가 일치하지 않습니다.");
                    rvo.setFailCd(CmmCdConstant.PIN_NOT_POUND);
                    map.put("failCd", CmmCdConstant.PIN_NOT_POUND);
                    map.put("loginYn", "N");

                }


                rvo.setRegIp(regIp);
                rvo.setIsIpFirst(isIpFirst);
            }

        }

        try {
            map.put("loginYn", "Y");
            loginDAO.INS10171025(map);
            cvo.setResultStrCode1(map.get("RESULT").toString());
        } catch (Exception e) {
            cvo.setResultStrCode1("로그인 내역 입력 도중 오류가 발생했습니다.");
        }


        String ipCheck = "";
        if ("N".equals(userService.foreignIpCheck(vo.getRegIp()))) {
            String smsCertCheck = userDAO.getSmsCertCheck(vo.getUserEmail());
            if ("1".equals(smsCertCheck)) {
                ipCheck = "Y";
            } else {
                ipCheck = "N";
            }
        } else {
            ipCheck = "Y";
        }

        rvo.setForeignIp(ipCheck);
        cvo.setResultStrCode("000");
        cvo.setData(rvo);

        return cvo;
    }



    @SuppressWarnings("unused")
    @Override
    public CmeResultVO fingerLogin(HttpServletRequest request, CmeLoginVO vo) throws Exception {

        CmeResultVO cvo = new CmeResultVO();
        LoginResVO rvo = new LoginResVO();
        LoginResVO useVO = new LoginResVO();
        vo.setFingerStatus("Y");

        String clientNm = "";
        //01:HTS 02:MTS 03:WEB 04:ADM
        if (vo.getClientCd().equals(CmmCdConstant.HTS_CD)) {
            //clientNm = getLocale(request,"mail.common.login5");
            clientNm = "HTS";
        } else if (vo.getClientCd().equals(CmmCdConstant.MTS_CD)) {
            //clientNm = getLocale(request,"mail.common.login3");
            clientNm = "MTS";
        } else if (vo.getClientCd().equals(CmmCdConstant.WEB_CD)) {
            //clientNm = getLocale(request,"mail.common.login4");
            clientNm = "WEB";
        }


        /*로그인 history를 위한 map*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userEmail", vo.getUserEmail());
        //map.put("brwsCd", vo.getClientCd());
        map.put("osCd", clientNm);
        map.put("connIp", vo.getRegIp());
        map.put("brwsCd", clientNm);

        map.put("deviceCd", vo.getDeviceCd());



        String regIp = vo.getIp();
        String isIpFirst = "N";

        if (vo.getUserEmail().equals("")) {
            cvo.setResultMsg("메일 주소가 없습니다.");
            rvo.setFailCd(CmmCdConstant.EMAIL_IS_NULL);
            map.put("failCd", CmmCdConstant.EMAIL_IS_NULL);
            map.put("fingerLoginYn", "N");
        } else if (vo.getDeviceCd().equals("")) {
            cvo.setResultMsg("PIN번호 값이 없습니다.");
            rvo.setFailCd(CmmCdConstant.PIN_IS_NULL);
            map.put("failCd", CmmCdConstant.PIN_IS_NULL);
            map.put("fingerLoginYn", "N");
        } else if (vo.getClientCd().equals("")) {
            cvo.setResultMsg("클라이언트 코드가 없습니다.");
            rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
            map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
            map.put("fingerLoginYn", "N");
        } else {
            UserIpVO ivo = new UserIpVO();
            //ivo.setIp(vo.getIp());
            ivo.setUserEmail(vo.getUserEmail());
            List<UserIpVO> ipList = ipDAO.selectUserIpList(ivo);
            if (ipList.size() == 0 && vo.getClientCd().equals(CmmCdConstant.HTS_CD) && vo.getIp() != null && !"".equals(vo.getIp())) {//ip등록 리스트가 하나도 없을 경우 자동등록
                ivo.setLimtHr("99999");
                ivo.setIp(vo.getIp());
                ipDAO.insUptIpList(ivo);
                isIpFirst = "Y";
            } else if (ipList.size() > 0 && vo.getClientCd().equals(CmmCdConstant.HTS_CD)) {
                boolean isIp = false;
                for (UserIpVO ipVO : ipList) {
                    if (ipVO.getIp().equals(regIp)) {
                        isIp = true;
                    }
                }
                if (!isIp) {
                    rvo.setFingerLoginYn("N");
                    cvo.setResultStrCode("000");
                    cvo.setResultMsg("등록되어있지 않은 아이피입니다.");
                    rvo.setFailCd(CmmCdConstant.IP_NOT_FOUND);
                    map.put("failCd", CmmCdConstant.IP_NOT_FOUND);
                    map.put("fingerLoginYn", "N");
                    rvo.setRegIp(regIp);
                    rvo.setIsIpFirst(isIpFirst);
                    cvo.setResultStrCode("000");
                    cvo.setData(rvo);
                    return cvo;
                }
            }

            useVO = loginDAO.getUseYn(vo);
            int blockCnt = loginDAO.isBlockYn(vo.getUserEmail());

            if (loginDAO.getEmailCnt(vo) == 0) {
                rvo.setFingerLoginYn("N");
                cvo.setResultStrCode("");
                cvo.setResultMsg("아이디가 존재하지 않습니다.");
                rvo.setFailCd(CmmCdConstant.EMAIL_NOT_FOUND);
                map.put("failCd", CmmCdConstant.EMAIL_NOT_FOUND);
                map.put("fingerLoginYn", "N");
            } else if ("N".equals(useVO.getUseYn())) {
                rvo.setFingerLoginYn("N");
                cvo.setResultStrCode("");
                cvo.setResultMsg("사용중지된 사용자입니다. 고객센터로 문의주시기 바랍니다.");
                rvo.setUseYn("N");
                rvo.setFailCd(CmmCdConstant.NOT_USE_ID);
                map.put("failCd", CmmCdConstant.NOT_USE_ID);
                map.put("fingerLoginYn", "N");
            } else if (blockCnt > 0) {
                rvo.setFingerLoginYn("N");
                cvo.setResultStrCode("000");
                cvo.setResultMsg("로그인 5회 실패로 로그인이 제한되었습니다. 고객센터로 문의해주시기바랍니다. ");
                rvo.setFailCd(CmmCdConstant.EMAIL_BLOCK);
                rvo.setBlckYn("Y");
                map.put("failCd", CmmCdConstant.EMAIL_BLOCK);
                map.put("fingerLoginYn", "N");
            } else {

                /*int deviceCheckCnt = loginDAO.deviceCheck(vo);
                if (deviceCheckCnt > 0) {
                    int deviceSubCheckCnt = loginDAO.deviceSubCheck(vo);
                    if (deviceSubCheckCnt == 0) {
                        int uptPinInfo = loginDAO.uptPinInfo(vo);
                        cvo.setResultStrCode("");
                        cvo.setResultMsg("새로운 디바이스로 로그인하였습니다.");
                    }
                }*/
                int fingerCheckDv = loginDAO.getFingerCheckDv(vo);
                int fingerCheckEmail = loginDAO.getFingerCheckEmail(vo);
                if(fingerCheckDv == 0){
                    rvo.setFingerLoginYn("N");
                    cvo.setResultStrCode("000");
                    cvo.setResultMsg("지문등록이 안된 기기입니다. ");
                    rvo.setFailCd(CmmCdConstant.FINGER_DEVICE_IS_NULL);
                    map.put("failCd", CmmCdConstant.FINGER_DEVICE_IS_NULL);
                    map.put("fingerLoginYn", "N");
                }else if(fingerCheckEmail == 0){
                    rvo.setFingerLoginYn("N");
                    cvo.setResultStrCode("000");
                    cvo.setResultMsg("지문등록이 안된 계정입니다. ");
                    rvo.setFailCd(CmmCdConstant.FINGER_EMAIL_IS_NULL);
                    map.put("failCd", CmmCdConstant.FINGER_EMAIL_IS_NULL);
                    map.put("fingerLoginYn", "N");
                }

                int res = loginDAO.fingerLoginCheck(vo);

                rvo = loginDAO.fingerlogin(vo);

                if (rvo != null) {

                    cvo.setResultStrCode("000");

                    if (rvo.getLockYn().equals("Y")) {
                        rvo.setFingerLoginYn("N");
                        cvo.setResultStrCode("");
                        cvo.setResultMsg("계정잠금된 계정입니다.");
                        rvo.setUseYn("N");
                        rvo.setFailCd(CmmCdConstant.NOT_USE_ID);
                        map.put("lockYn", "Y");
                        map.put("failCd", CmmCdConstant.NOT_USE_ID);
                        map.put("fingerLoginYn", "N");
                    } /*else if(rvo.getUseYn().equals("N")) {
						rvo.setLoginYn("N");
						cvo.setResultStrCode("");
						cvo.setResultMsg("사용중지된 사용자입니다. 고객센터로 문의주시기 바랍니다.");
						rvo.setUseYn("N");
						rvo.setFailCd(CmmCdConstant.NOT_USE_ID);
						map.put("failCd", CmmCdConstant.NOT_USE_ID);
						map.put("loginYn", "N");
					}*/ else if (rvo.getBlckYn().equals("Y") && rvo.getRelYn().equals("N")) {
                        rvo.setFingerLoginYn("N");
                        cvo.setResultStrCode("");
                        cvo.setResultMsg("로그인 5회 실패로 로그인이 제한되었습니다. 고객센터로 문의해주시기바랍니다.");
                        rvo.setFailCd(CmmCdConstant.EMAIL_BLOCK);
                        map.put("failCd", CmmCdConstant.EMAIL_BLOCK);
                        map.put("fingerLoginYn", "N");
                    } else if (rvo.getPwdChgYn().equals("Y")) {
                        rvo.setFingerLoginYn("N");
                        cvo.setResultStrCode("");
                        cvo.setResultMsg("비밀번호를 재설정한 사용자입니다. 비밀번호를 재설정 해주시기 바랍니다.");
                        rvo.setFailCd(CmmCdConstant.PWD_CHG_YN);
                        map.put("failCd", CmmCdConstant.PWD_CHG_YN);
                        map.put("fingerLoginYn", "N");
                    }  /*else if(vo.getClientCd().equals(CmmCdConstant.MTS_CD) && rvo.getUserMobile().equals("00000000000")) {
						rvo.setLoginYn("N");
						cvo.setResultStrCode("");
						cvo.setResultMsg("휴대폰본인 인증이 완료되지 않은 아이디는 MTS에 로그인하실 수 없습니다. 웹거래소로 로그인하여  휴대폰본인인증을 완료하신 후에 인증하신 휴대폰으로 로그인해주세요.");
						rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
						map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
						map.put("loginYn", "N");
					} else if(vo.getClientCd().equals(CmmCdConstant.MTS_CD) && !rvo.getUserMobile().equals(vo.getUserMobile())) {
						rvo.setLoginYn("N");
						cvo.setResultStrCode("");
						cvo.setResultMsg("인증하신 휴대폰번호와 해당 휴대폰번의 번호가 일치하지 않습니다. 휴대폰번호 변경 시 [웹,HTS > 인증센터] 에서 변경 후 이용해주시기 바랍니다.");
						rvo.setFailCd(CmmCdConstant.OTHER_LOGIN_FAIL);
						map.put("failCd", CmmCdConstant.OTHER_LOGIN_FAIL);
						map.put("loginYn", "N");
					}*/ else if (res == 1) {
                        rvo.setFingerLoginYn("Y");
                        cvo.setResultMsg("로그인에 성공하였습니다.");
                        String notice = pushDAO.pushNoticeList("");

                        if (notice != null) {
                            String[] strs = notice.split(":::");
                            if (strs.length > 1) {
                                if (strs[0].equals(userDAO.getUserLangCd(vo.getUserEmail()))) {
                                    rvo.setNotice(strs[1]);
                                }
                            }
                        }


                        map.put("failCd", "");
                        map.put("fingerLoginYn", "Y");

                        String sendMailYN = "";
                        String sendCode = "";
                         if ("MTS".equals(clientNm)) {
                            String mtsDeviceCode = loginDAO.mtsDeviceCode(vo.getUserEmail());
                            if (vo.getDeviceCd().equals(mtsDeviceCode)) {
                                sendMailYN = "N";
                                sendCode = vo.getIp();
                            } else {
                                sendMailYN = "Y";
                                sendCode = vo.getIp();
                            }
                        }

                        if ("Y".equals(vo.getFingerPushYn())) {
                            SendMsgVO smvo = new SendMsgVO();
                            smvo.setUserEmail(vo.getUserEmail());
                            smvo.setSendYn("N");
                            smvo.setCmmCd("CMMC00000000284");
                            smvo.setMsg(clientNm + "|" + sendMailYN + "|" + sendCode);
                            cmeFcmPushService.PR_INSUPT_SND_MSG(smvo);
                        }
                    } else {
                        rvo.setFingerLoginYn("N");
                        cvo.setResultMsg("로그인에 실패했습니다.");
                        rvo.setUseYn("N");
                    }

                } else {
                    rvo = new LoginResVO();
                    rvo.setFingerLoginYn("N");
                    cvo.setResultStrCode("");
                    cvo.setResultMsg("지문이 일치하지 않습니다.");
                    rvo.setFailCd(CmmCdConstant.FINGER_NOT_FOUND);
                    map.put("failCd", CmmCdConstant.FINGER_NOT_FOUND);
                    map.put("fingerLoginYn", "N");

                }


                rvo.setRegIp(regIp);
                rvo.setIsIpFirst(isIpFirst);
            }

        }

        try {
            map.put("loginYn", "Y");
            loginDAO.INS10171025(map);
            cvo.setResultStrCode1(map.get("RESULT").toString());
        } catch (Exception e) {
            cvo.setResultStrCode1("로그인 내역 입력 도중 오류가 발생했습니다.");
        }


        String ipCheck = "";
        if ("N".equals(userService.foreignIpCheck(vo.getRegIp()))) {
            String smsCertCheck = userDAO.getSmsCertCheck(vo.getUserEmail());
            if ("1".equals(smsCertCheck)) {
                ipCheck = "Y";
            } else {
                ipCheck = "N";
            }
        } else {
            ipCheck = "Y";
        }

        rvo.setForeignIp(ipCheck);
        cvo.setResultStrCode("000");
        cvo.setData(rvo);

        return cvo;
    }


    @Override
    public CmeResultVO loginFail(HttpServletRequest request, CmeLoginVO vo) throws Exception {

        CmeResultVO cvo = new CmeResultVO();
        if (vo.getUserEmail().equals("")) {
            cvo.setResultMsg("사용자 EMAIL값이 없습니다.");
            return cvo;
        }

        Map map = new HashMap();
        map.put("userEmail", vo.getUserEmail());
        int res = 0;
        try {
            loginDAO.INS10171023(map);
        } catch (Exception e) {
            cvo.setResultMsg("입력 도중 오류가 발생했습니다. 이미 차단된 사용자일 수 있습니다.");
        }
        cvo.setData(map);

        return cvo;
    }


    @Override
    public CmeResultVO loginUnlock(HttpServletRequest request, CmeLoginVO vo) throws Exception {
        // TODO Auto-generated method stub
        CmeResultVO cvo = new CmeResultVO();
        if (vo.getUserEmail().equals("")) {
            cvo.setResultMsg("사용자 EMAIL값이 없습니다.");
            return cvo;
        }

        String blckCd = loginDAO.getBlckKey(vo.getUserEmail());
        //System.out.println("subMail:"+ vo.getSubUserEmail());

        Map map = new HashMap();
        map.put("blckCd", blckCd);
        map.put("userEmail", vo.getUserEmail());
        map.put("regIp", vo.getRegIp());

        try {
            loginDAO.INS10171024(map);
        } catch (Exception e) {
            cvo.setResultMsg("입력 도중 오류가 발생했습니다. 로그인 차단 리스트에 이용자가 없을 수 있습니다.");
        }
        cvo.setData(map);

        return cvo;
    }


    /**
     * @param vo
     * @throws Exception
     * @Method Name  : INS10171020
     * @작성일 : 2017. 12. 6.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :
     */
    public CmeResultVO INS10171020(CmeLoginVO vo) throws Exception {

        CmeResultVO cvo = new CmeResultVO();

        Map map = cUtil.convertObjectToMap(vo);
        loginDAO.INS10171020(map);
        vo = (CmeLoginVO) cUtil.convertMapToObject(map, vo);

        cvo.setResultStrCode("000");
        cvo.setResultMsg("USER 등록 및 수정 완료");

        return cvo;
    }


    /**
     * @param vo
     * @throws Exception
     * @Method Name  : INS10171020
     * @작성일 : 2017. 12. 7.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :로그인 이력 쌓기
     */
    public CmeResultVO INS10171025(CmeLoginVO vo) throws Exception {

        CmeResultVO cvo = new CmeResultVO();
        String clientNm = "";
        if (vo.getClientCd().equals(CmmCdConstant.HTS_CD)) {
            clientNm = "HTS";
        } else if (vo.getClientCd().equals(CmmCdConstant.MTS_CD)) {
            clientNm = "MTS";
        } else if (vo.getClientCd().equals(CmmCdConstant.WEB_CD)) {
            clientNm = "WEB";
        }

        Map map = new HashMap();
        map.put("userEmail", vo.getUserEmail());
        map.put("osCd", clientNm);
        map.put("connIp", vo.getConnIp());
        map.put("failCd", vo.getFailCd());
        map.put("loginYn", vo.getLoginYn());
        map.put("brwsCd", clientNm);
        map.put("deviceCd", vo.getDeviceCd());
        try {
            loginDAO.INS10171025(map);
            cvo.setResultStrCode1(map.get("RESULT").toString());
        } catch (Exception e) {
            cvo.setResultStrCode1("로그인 내역 입력 도중 오류가 발생했습니다.");
            return cvo;
        }

        cvo.setResultStrCode("000");
        cvo.setResultMsg("로그인 내역 입력 완료");

        return cvo;
    }


    /**
     * @param vo
     * @return
     * @throws Exception
     * @Method Name  : loginMailSms
     * @작성일 : 2017. 12. 7.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :로그인 성공 메일, 문자 발송
     */
    @Override
    public CmeResultVO loginMailSms(HttpServletRequest request, CmeLoginVO vo) throws Exception {

        CmeResultVO cvo = new CmeResultVO();

        String clientNm = "";
        if (vo.getClientCd().equals(CmmCdConstant.HTS_CD)) {
            //clientNm = getLocale(request,"mail.common.login5");
            clientNm = "HTS";
        } else if (vo.getClientCd().equals(CmmCdConstant.MTS_CD)) {
            //clientNm = getLocale(request,"mail.common.login3");
            clientNm = "MTS";
        } else if (vo.getClientCd().equals(CmmCdConstant.WEB_CD)) {
            //clientNm = getLocale(request,"mail.common.login4");
            clientNm = "WEB";
        } else {
            cvo.setResultMsg("클라이언트 코드가 맞지 않습니다.");
        }
        /*메일발송*/
        PushYnVO pushYnVO = new PushYnVO();
        pushYnVO.setUserEmail(vo.getUserEmail());
        pushYnVO = cmeFcmPushService.selectPushYn(pushYnVO);
        int isSendMail = 0;
        //String userIp = loginDAO.getUserIp(vo.getUserEmail());
        CheckIpVO checkIpVO = loginDAO.getUserIp(vo.getUserEmail());
        if (!"N".equals(pushYnVO.getBsBs01())) {
            if (checkIpVO == null || !vo.getConnIp().equals(checkIpVO.getConnIp())) {
                Map<String, Object> model = new HashMap<String, Object>();
                //*필수값 : mailTo(받는사람메일주소)*//*
                model.put("mailTo", vo.getUserEmail());
                model.put("clientNm", clientNm);
                model.put("connIp", vo.getConnIp());
                model.put("langCd", userDAO.getUserLangCd(vo.getUserEmail()));
                isSendMail = mailServiceImpl.mailLogin(request, model);
            }
        }
        /*메일발송*/

        /*문자발송*/
        int isSendSms = 0;
        SendInfoVO svo = new SendInfoVO();
        /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
        svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
        svo.setClientCd(clientNm);
        svo.setUserEmail(vo.getUserEmail());
        isSendSms = smsApiService.smsLogin(request, svo);
        /*문자발송*/

        if (isSendMail > 0 && isSendSms > 0) {
            cvo.setResultStrCode("000");
            cvo.setResultMsg("문자, 메일 발송 성공");
        } else if (isSendMail == 0 && isSendMail > 0) {
            cvo.setResultStrCode("999");
            cvo.setResultMsg("문자 발송 성공");
        } else if (isSendMail > 0 && isSendMail == 0) {
            cvo.setResultStrCode("998");
            cvo.setResultMsg("메일 발송 성공");
        } else {
            cvo.setResultStrCode("997");
            cvo.setResultMsg("문자, 메일 발송 실패");
        }
        return cvo;
    }


    /**
     * @param vo
     * @throws Exception
     * @Method Name  : sendSmsAuthCode
     * @작성일 : 2017. 12. 31.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 6자리 인증번호 발송
     */
    public CmeResultVO sendSmsAuthCode(HttpServletRequest request, CmeLoginVO vo) throws Exception {

        CmeResultVO rvo = new CmeResultVO();
        String code = StringUtils.generateRandomPassword(6, 1);
        vo.setAuthCode(code);
        vo.setAuthType("E");
        rvo = loginDAO.sendSmsAuthCode(vo);
		
		
		/*문자발송
        SendInfoVO svo = new SendInfoVO();
        svo.setContents("인증번호는 [" + code + "] 입니다.");
       	필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)
		svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
		//svo.setClientCd(clientNm);
		svo.setUserEmail(vo.getUserEmail());
		int isSendSms =smsApiService.sendSms(svo, 0);
		문자발송
		rvo.setResultStrCode("000");
		rvo.setResultMsg("인증번호 발송 성공");*/

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("mailTo", vo.getUserEmail());
        model.put("code", code);
        model.put("url", CmmCdConstant.WEB_DOMAIN_URL + "/bt.auth.sendMailAuthCode.dp/proc.go?userEmail=" + vo.getUserEmail());
        model.put("sysdate", sampleService.getDateTime());

        System.out.println("sysdate : " + sampleService.getDateTime());

        mailServiceImpl.certification(request, model);

        rvo.setResultStrCode("000");
        rvo.setResultMsg("인증번호 발송 완료");

        return rvo;

    }

    /**
     * @param vo
     * @throws Exception
     * @Method Name  : sendSmsAuthCode
     * @작성일 : 2017. 12. 31.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 6자리 인증번호 검증
     */
    public CmeResultVO SmsAuthCodeVertify(CmeLoginVO vo) throws Exception {
        CmeResultVO rvo = new CmeResultVO();

        String code = loginDAO.SmsAuthCodeVertify(vo.getUserEmail());

        if (code != null && vo.getAuthCode().equals(code)) {
            rvo.setResultMsg("인증이 완료되었습니다.");
            rvo.setResultStrCode("000");
        } else {
            rvo.setResultMsg("인증에 실패하였습니다.");
            rvo.setResultStrCode("-1");
        }

        return rvo;
    }

    /**
     * @param vo
     * @return
     * @Method Name  : getUserAuth
     * @작성일 : 2018. 1. 2.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 :사용자인증정보
     */
    public UserAuthVO getUserAuth(CmeLoginVO vo) {
        return loginDAO.getUserAuth(vo);
    }

    public String getUserPuchCd(String str) {

        return loginDAO.getUserPuchCd(str);
    }

    public String getUserMobile(String str) {

        return loginDAO.getUserMobile(str);
    }

    public List<CmeRcmdCode> getNatnCode() throws Exception {
        return loginDAO.getNatnCode();
    }

    public List<CmeRcmdCode> getBrhCode(String natnCode) throws Exception {
        return loginDAO.getBrhCode(natnCode);
    }

    public List<CmeRcmdCode> getRcmdCode(String brhCode) throws Exception {
        return loginDAO.getRcmdCode(brhCode);
    }

    public String SmsAuthCodeCert(String userEmail) throws Exception {
        return loginDAO.SmsAuthCodeCert(userEmail);
    }

    public String SmsAuthCodeCert2(String userEmail) throws Exception {
        return loginDAO.SmsAuthCodeCert2(userEmail);
    }

    public int pinNoCheck(CmeLoginVO vo) throws Exception {
        return loginDAO.pinNoCheck(vo);
    }

    public int InsertPinNo(CmeLoginVO vo) throws Exception {
        return loginDAO.InsertPinNo(vo);
    }

    public int pinNoReset(CmeLoginVO vo) throws Exception {
        return loginDAO.pinNoReset(vo);
    }

    public int pinUserCheck(CmeLoginVO vo) throws Exception {
        return loginDAO.pinUserCheck(vo);
    }

    public String userFuncCheck(String userEmail) throws Exception {
        String status = "";

        UserFuncAuthVO uvo = new UserFuncAuthVO();
        uvo = userService.getUserFunc(userEmail);

        if (uvo != null) {
            if ("Y".equals(uvo.getNoLimtYn())) {
                status = "Y";
            } else {
                int i = userService.getUserFuncDt(userEmail);
                if (i > 0) {
                    status = "Y";
                } else {
                    status = "N";
                }
            }
        } else {
            status = "N";
        }

        return status;
    }


    public LoginResVO getUserAuthInfo(String userEmail) throws Exception {
        return loginDAO.getUserAuthInfo(userEmail);
    }

    public int getFingetCheck(CmeLoginVO vo) throws Exception{
        return loginDAO.getFingetCheck(vo);
    }

    public int InsFingerInfo(CmeLoginVO vo) throws Exception{
        return loginDAO.InsFingerInfo(vo);
    }

    public int fingerReset(CmeLoginVO vo) throws Exception {
        return loginDAO.fingerReset(vo);
    }

    public int uptFingerInfo(CmeLoginVO vo) throws Exception {
        return loginDAO.uptFingerInfo(vo);
    }

    public int getPwdCheck(CmeLoginVO vo) throws Exception{
        return loginDAO.getPwdCheck(vo);
    }
}
