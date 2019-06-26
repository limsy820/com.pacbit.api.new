/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.mail.service.impl;

import com.bitkrx.api.auth.vo.SendEmailCustVO;
import com.bitkrx.api.auth.vo.SubmitCertVO;
import com.bitkrx.api.board.Service.BoardService;
import com.bitkrx.api.board.dao.BoardDAO;
import com.bitkrx.api.common.control.CoinInfoController;
import com.bitkrx.api.common.dao.CoinInfoDAO;
import com.bitkrx.api.common.service.CoinInfoService;
import com.bitkrx.api.common.vo.CommonExchgInfoVO;
import com.bitkrx.api.mail.service.MailApiService;
import com.bitkrx.api.mail.vo.MailVO;
import com.bitkrx.api.menu.dao.MenuDAO;
import com.bitkrx.api.trade.vo.DepositVO;
import com.bitkrx.api.user.dao.UserDAO;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.annotation.CommonDataSource;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.dbinfo.DataContextHolder;
import com.bitkrx.config.dbinfo.DataSourceType;
import com.bitkrx.config.mail.MailSenderImpl;
import com.bitkrx.config.service.CmeProperties;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.util.ComUtil;
import com.bitkrx.config.util.StringUtils;
import com.bitkrx.config.vo.MailInfoVO;
import com.bitkrx.api.mail.dao.MailDAO;
import com.bitkrx.config.vo.SendInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.mail.service.impl
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 21.
 */
@Service
public class MailServiceImpl implements MailApiService{

	
	@Autowired
	private LocaleResolver localeResolver; 
	 
	@Autowired
	private MessageSource messageSource;	

	@Autowired
	MailDAO mailDAO;

	@Autowired
	MenuDAO menuDAO;

	@Autowired
	CoinInfoDAO coinInfoDAO;

	@Autowired
	UserService userService;

    @Autowired
    CoinInfoService coinInfoService;

    @Autowired
	MailApiService mailService;

	public String getLocale(HttpServletRequest request, String key) {
	      
		String msg = "";     
		msg = messageSource.getMessage(key, null, "", localeResolver.resolveLocale(request));

		String lang ="";
		String exchangeNm = "";
		String mailLogo = "";

		try{
			String userEmail = StringUtils.checkNull(request.getParameter("userEmail"), "");
			lang = userService.getUserLangCd(userEmail);

			if(null == lang){
				if(! "".equals(StringUtils.checkNull(request.getParameter("lang"), "")) ){
					lang = StringUtils.checkNull(request.getParameter("lang"), "");
				}
			}

			SubmitCertVO svo = new SubmitCertVO();
			svo.setLang(lang);
			CommonExchgInfoVO comvo = coinInfoDAO.commonExchgInfo(svo);
			exchangeNm = comvo.getExchgName();
//			SubmitCertVO sbvo = new SubmitCertVO();
//			sbvo.setCertCode(comvo.getBi());
//			mailLogo = coinInfoService.getFile_sn(sbvo);
		} catch (Exception e) {
			System.out.println("getLocale 공통화 데이터 가져오다 오류");
		}
//		return msg.replaceAll("exchangeNm", CmmCdConstant.EXCHANGE_NAME);
		return msg.replaceAll("exchangeNm", exchangeNm);
    }
	
	public static final String BITKRX_MAIL_MAIL_FROM	=	CmmCdConstant.MAIL_FROM;
	public static final String EXCHANGE_NAME			=	CmmCdConstant.EXCHANGE_NAME;			// 거래소명

	public static final String FORM_LOGIN = "frm_beta_login.html";
	public static final String FORM_LOGIN_FAIL = "frm_beta_login_fail.html";
	public static final String FORM_LOGIN_UNLOCK = "frm_beta_account.html";
	public static final String FORM_LIM_ORDER_COMPLETE = "frm_beta_lim_OrderComplete.html";
	public static final String FORM_LIM_ORDER_CONCLUSION = "frm_beta_lim_OrderComplete_Conclusion.html";
	public static final String FORM_LIM_ORDER_CANCEL = "frm_beta_lim_OrderCompleteCancle.html";
	public static final String FORM_LIMSALES_ORDER_CONPLETE = "frm_beta_limSales_Order.html";
	public static final String FORM_LIM_SELL_ORDER_CONCLUSION = "frm_bata_lim_sell_OrderComplete.html";
	public static final String FORM_LIMSALES_ORDER_CALCEL = "frm_beta_limSales_Cancle.html";
	public static final String FORM_MAR_ORDER_COMPLETE = "frm_beta_MarSales_OrderComplete_Conclusion.html";
	public static final String FORM_MAR_ORDER_CONCLUSION = "frm_beta_MarPur_OrderComplete_Conclusion.html";
	public static final String FORM_DEPOSIT_COMPLETE = "frm_beta_krwCharge_deposit_complete.html";
	public static final String FORM_IP_CERTIFICATION = "frm_hts_IP_certification.html";

	public static final String FORM_WITHDRAWAL_APP = "frm_withdrawal_application.html";
	public static final String FORM_WITHDRAWAL_APP_COIN = "frm_withdrawal_application_coin.html";
	public static final String FORM_PW_RESET = "frmPw_reset.html";
	public static final String FORM_REPLY = "frm_beta_Reply_11.html";
	public static final String FORM_KYC_CERT = "frm_beta_KycCert.html";
	public static final String FORM_APPLY_CARD = "frm_beta_Apply_Card.html";
	public static final String FORM_CRRTIFICATION = "frm_beta_certification.html";

	public static final String FORM_USER_EVENT = "frm_beta_UserEvent.html";
	public static final String FORM_TRUST_APPLY = "frm_beta_Trust_apply.html";
	public static final String FORM_TRUST_RELEASE = "frm_beta_Trust_release.html";
	public static final String FORM_LENDING_APPLY = "frm_beta_Lending_Apply.html";
	public static final String FORM_LENDING_EXTENSION = "frm_beta_Lending_extension.html";
	public static final String FORM_LENDING_REPAYMENT = "frm_beta_Lending_repayment.html";



	@Autowired
	MailSenderImpl mailSender;

	@Autowired
    UserDAO userDAO;

	@Autowired
	BoardService boardService;

	@Autowired
	private ServletContext servletContext;

	public  String getDataStatus() throws Exception {
		String path = servletContext.getRealPath("/");
		path += "WEB-INF/classes/cmeconfig/CmeProps";
		//System.out.println(path);
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(path + "/dataStatus.properties");
		props.load(new java.io.BufferedInputStream(fis));
		String status = props.getProperty("status").trim();

		return status;
	}

	@Override
	public int sendMail(HttpServletRequest request, Map<String, Object> map) throws Exception {

		//getLocale(request,"validation.login.block");
		
		/*메일발송*/
		MailInfoVO mail = new MailInfoVO();
        mail.setMailFrom(BITKRX_MAIL_MAIL_FROM);

        String subUserEmail = userDAO.getUserSubEmail(map.get("mailTo").toString());
        if(subUserEmail == null || "".equals(subUserEmail)){
			mail.setMailTo(map.get("mailTo").toString());
		}else{
			mail.setMailTo(subUserEmail);
		}
        mail.setMailSubject(map.get("mailSubject").toString());
        mail.setMailTemplateForm(map.get("mailForm").toString());

        //(공통화)
        SubmitCertVO svo = new SubmitCertVO();

        String mailLang = "ko";
        if( map.get("langCd") != null){
            System.out.println("메일 lang:"+map.get("langCd").toString());
            mailLang = map.get("langCd").toString();
        } else if( map.get("lang") != null){
            System.out.println("메일 lang:"+map.get("lang").toString());
            mailLang = map.get("lang").toString();
        } else {
			String userEmail = StringUtils.checkNull(request.getParameter("userEmail"), "");
			mailLang = userService.getUserLangCd(userEmail);
            System.out.println("메일 랭 재검색:"+mailLang);
        }
        svo.setLang(mailLang);
        CommonExchgInfoVO comvo = coinInfoDAO.commonExchgInfo(svo);

//		CoinInfoController coinController = new CoinInfoController();
//        String fileNum = coinController.getFileLogo(comvo.getBi());
//
//		String webUrl = comvo.getExchgUrl();
//		String bi = webUrl+ "/common.file.getImageView.dp/proc.go?atchFileId="+ comvo.getBi()+"&fileSn="+fileNum;//+coinInfoService.getFile_sn(sbvo);
		//String bi = webUrl+ "/common.file.getImageView.dp/proc.go?atchFileId="+ comvo.getBi()+"&fileSn="+ boardService.getFile_sn(sbvo);

        String bi = "";
//        String path = servletContext.getRealPath("/");
//        path += "WEB-INF/classes/cmeconfig/CmeProps";
//        Properties props = new Properties();
//        FileInputStream fis  = new FileInputStream(path + "/dataStatus.properties");
//        props.load(new java.io.BufferedInputStream(fis));
//        String status = props.getProperty("status").trim();
//        if("0".equals(status)){//운영서버일 결우
//            bi = "https://oapi.planbit.io/bt.getLogoImage.dp/proc.go?langCd="+mailLang;
//        } else if("1".equals(status)) {//staging일 결우
//            bi = "http://wplanbit.xlogic.co.kr/bt.getLogoImage.dp/proc.go?langCd="+mailLang;
//        }

        bi = comvo.getExchgUrl()+"/bt.front.getLogoImage.dp/proc.go?langCd="+mailLang;
		map.put("lang", mailLang);
        map.put("webUrl", comvo.getExchgUrl());
        map.put("bi", bi/*CmmCdConstant.EXCHANGE_BI*/); // BI


        //고객센터번호, 거래소사업자번호, 거래소번호, 거래소이메일, 거래소주소, 거래소명은 footer 공통
//		if("0".equals(getDataStatus())) {
//			map.put("webUrl", CmmCdConstant.WEB_DOMAIN_URL);
//		}else if("1".equals(getDataStatus())) {
//			map.put("webUrl", CmmCdConstant.WEB_XLOGIC_DOMAIN_URL);
//		}

		//map.put("webUrl", CmmCdConstant.WEB_DOMAIN_URL);
		map.put("mailFrom", comvo.getCicEmail());/*comvo.getCicEmail());*/
        setLocaleVal(request, map, "title", 9);

        //이하는 메일마다다른 선택 param
        mail.setModel(map);
        
        try {
        	mailSender.sendEmail(mail);
        } catch (Exception e) {
        	e.printStackTrace();
        	return 0;
        }

        /*메일발송*/
        return 1;
	}


	/**
	 * @프로젝트명	: com.bitkrx.api
	 * @패키지    	: com.bitkrx.api.mail.service.impl
	 * @클래스명  	: com.bitkrx.api
	 * @작성자		:  (주)씨엠이소프트 문영민
	 * @작성일		: 2017. 11. 22.
	 * @내용		:	로그인시 메일 발송
	 */
	@Override
	public int mailLogin(HttpServletRequest request, Map < String, Object > map) throws Exception {
		// TODO Auto-generated method stub

		map.put("mailSubject", getLocale(request,"mail.common.login1") + getLocale(request,"mail.common.login2"));
        map.put("mailForm", FORM_LOGIN);
        setLocaleVal(request, map, "login", 13);
        String connIp = map.get("connIp").toString();
        String langCd = map.get("langCd").toString();
		String loginMsg = "";
		String loginMsg2 = "";

		SubmitCertVO svo = new SubmitCertVO();
		svo.setLang(langCd);
		CommonExchgInfoVO comvo = coinInfoDAO.commonExchgInfo(svo);

        if("ko".equals(langCd)){
        	loginMsg = "IP" + connIp + getLocale(request,"mail.common.login11");
        	loginMsg2 = comvo.getCicEmail() + getLocale(request,"mail.common.login13");
		}else if("en".equals(langCd)){
			loginMsg = getLocale(request,"mail.common.login11") + "IP" + connIp;
			loginMsg2 = getLocale(request,"mail.common.login13") + comvo.getCicEmail();
		}else if("in".equals(langCd)){
			loginMsg = getLocale(request, "mail.common.login11") + "IP" + connIp;
			loginMsg2 = getLocale(request,"mail.common.login13") + comvo.getCicEmail();
		}else if("ja".equals(langCd)){
			loginMsg = "IP" + connIp + getLocale(request,"mail.common.login11");
			loginMsg2 = comvo.getCicEmail() + getLocale(request,"mail.common.login13");
		}else if("kg".equals(langCd)){
			loginMsg = getLocale(request, "mail.common.login11") + "IP" + connIp;
			loginMsg2 = getLocale(request,"mail.common.login13") + comvo.getCicEmail();
		}else if("th".equals(langCd)){
			loginMsg = getLocale(request, "mail.common.login11") + "IP" + connIp;
			loginMsg2 = getLocale(request,"mail.common.login13") + comvo.getCicEmail();
		}else if("vi".equals(langCd)){
			loginMsg = getLocale(request, "mail.common.login11") + "IP" + connIp;
			loginMsg2 = getLocale(request,"mail.common.login13") + comvo.getCicEmail();
		}else if("zh".equals(langCd)){
			loginMsg = getLocale(request, "mail.common.login11") + "IP" + connIp;
			loginMsg2 = getLocale(request,"mail.common.login13") + comvo.getCicEmail();
		}


        /*param*/
		map.put("loginMsg", loginMsg);
		map.put("loginMsg2", loginMsg2);
        map.put("sysdate", new SimpleDateFormat("MM/dd HH:mm").format(new Date()));
        return sendMail(request, map);
	}

	/**
	 * @Method Name  : mailLoginFail
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :로그인 5회 실패
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int mailLoginFail(HttpServletRequest request, Map < String, Object > map) throws Exception{
		map.put("mailSubject", getLocale(request,"mail.common.login8"));
        map.put("mailForm", FORM_LOGIN_FAIL);
        setLocaleVal(request, map, "login", 10);
        return sendMail(request, map);
	};
	
	/**
	 * @Method Name  : mailLoginUnlock
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :계정잠금해제 메일
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int mailLoginUnlock(HttpServletRequest request, Map < String, Object > map) throws Exception{
		map.put("mailSubject", getLocale(request,"mail.common.account1"));
        map.put("mailForm", FORM_LOGIN_UNLOCK);
        setLocaleVal(request, map, "account", 2);
        return sendMail(request, map);
	}



    /**
	 * @Method Name  : limOrderComplete
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :지정가 주문완료
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int limOrderComplete(HttpServletRequest request, Map < String, Object > map) throws Exception{
		map.put("mailSubject", getLocale(request,"mail.common.order1"));
        map.put("mailForm", FORM_LIM_ORDER_COMPLETE);
        setLocaleVal(request, map, "order", 28);
        return sendMail(request, map);
	};
	
	/**
	 * 
	 * @Method Name  : limSalesOrderComplete
	 * @작성일   : 2017. 12. 12. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 : 지정가판매 주문완료
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int limSalesOrderComplete(HttpServletRequest request, Map < String, Object > map) throws Exception{
		map.put("mailSubject", getLocale(request,"mail.common.order13"));
        map.put("mailForm", FORM_LIMSALES_ORDER_CONPLETE);
        setLocaleVal(request, map, "order", 28);
        return sendMail(request, map);
	}
	
	/**
	 * @Method Name  : limOrderConclusion
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :지정가 구매 체결
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int limOrderConclusion(HttpServletRequest request, Map < String, Object > map) throws Exception{
		map.put("mailSubject", getLocale(request,"mail.common.order7"));
        map.put("mailForm", FORM_LIM_ORDER_CONCLUSION);
        setLocaleVal(request, map, "order", 28);
        return sendMail(request, map);
	};
	
	/**
	 * @Method Name  : limOrderConclusion
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :지정가 판매 체결
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int limSellOrderConclusion(HttpServletRequest request, Map < String, Object > map) throws Exception{
		map.put("mailSubject", getLocale(request,"mail.common.order26"));
        map.put("mailForm", FORM_LIM_SELL_ORDER_CONCLUSION);
        setLocaleVal(request, map, "order", 28);
        return sendMail(request, map);
	};
	
	/**
	 * 
	 * @Method Name  : limSalesOrderCancel
	 * @작성일   : 2017. 12. 13. 
	 * @작성자   :  (주)씨엠이소프트 박상웅
	 * @변경이력  :
	 * @Method 설명 : 지정가판매 취소
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int limSalesOrderCancel(HttpServletRequest request, Map < String, Object > map) throws Exception{
		map.put("mailSubject", getLocale(request,"mail.common.order15"));
        map.put("mailForm", FORM_LIMSALES_ORDER_CALCEL);
        setLocaleVal(request, map, "order", 28);
        return sendMail(request, map);
	}
	
	/**
	 * @Method Name  : limOrderCancel
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :지정가구매 취소
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int limOrderCancel(HttpServletRequest request, Map < String, Object > map) throws Exception{
		map.put("mailSubject", getLocale(request,"mail.common.order5"));
        map.put("mailForm", FORM_LIM_ORDER_CANCEL);
        setLocaleVal(request, map, "order", 28);
        return sendMail(request, map);
	};
	
	/**
	 * @Method Name  : marOrderComplete
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :시장가 판매 체결
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int marOrderComplete(HttpServletRequest request, Map < String, Object > map) throws Exception{
		map.put("mailSubject", getLocale(request,"mail.common.order19"));
        map.put("mailForm", FORM_MAR_ORDER_COMPLETE);
        setLocaleVal(request, map, "order", 28);
        return sendMail(request, map);
	};
	
	/**
	 * @Method Name  : marOrderConclusion
	 * @작성일   : 2017. 11. 22. 
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :시장가 구매 체결
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int marOrderConclusion(HttpServletRequest request, Map < String, Object > map) throws Exception{
		map.put("mailSubject", getLocale(request,"mail.common.order17"));
        map.put("mailForm", FORM_MAR_ORDER_CONCLUSION);
        setLocaleVal(request, map, "order", 28);
        return sendMail(request, map);
	};

	@Override
	public int certification(HttpServletRequest request, Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		map.put("mailSubject", getLocale(request,"mail.common.ipauth1"));
		
		System.out.println("getLocale(request,\"mail.common.ipauth1\") : " + getLocale(request,"mail.common.ipauth1"));
		
        map.put("mailForm", FORM_IP_CERTIFICATION);
        setLocaleVal(request, map, "ipauth", 7);
        System.out.println("certification메일 발송 !!! URL : " + request.getRequestURL());
        return sendMail(request, map);
	}

	/**
	 *
	 * @Method Name  : depositComplete
	 * @작성일   : 2018. 04. 15.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 원화출금신청완료 메일
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int mailWithdrowApplication(HttpServletRequest request, Map<String, Object> map) throws Exception{
		map.put("mailSubject", getLocale(request,"mail.common.withdrow4"));
		map.put("mailForm", FORM_WITHDRAWAL_APP );
		setLocaleVal(request, map, "withdrow", 11);
		return sendMail(request, map);
	}

	/**
	 *
	 * @Method Name  : depositComplete
	 * @작성일   : 2018. 04. 15.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 코인송금신청완료 메일
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int mailWithdrowApplicationCoin(HttpServletRequest request, Map<String, Object> map) throws Exception{
		map.put("mailSubject", getLocale(request,"mail.common.withdrow1"));
		map.put("mailForm", FORM_WITHDRAWAL_APP_COIN );
		setLocaleVal(request, map, "withdrow", 11);
		return sendMail(request, map);
	}

    /**
     *
     * @Method Name  : depositComplete
     * @작성일   : 2017. 12. 12.
     * @작성자   :  (주)씨엠이소프트 박상웅
     * @변경이력  :
     * @Method 설명 : 원화충전완료메일
     * @param map
     * @return
     * @throws Exception
     */
    public int depositComplete(HttpServletRequest request, Map<String, Object> map) throws Exception{
        map.put("mailSubject", getLocale(request,"mail.common.deposit4") + getLocale(request,"mail.common.deposit5") + getLocale(request,"mail.common.deposit6"));
        map.put("mailForm", FORM_DEPOSIT_COMPLETE);
        setLocaleVal(request, map, "deposit", 9);
        return sendMail(request, map);
    }

    /**
     *
     * @Method Name  : depositComplete
     * @작성일   : 2018. 04. 24.
     * @작성자   :  (주)씨엠이소프트 문영민
     * @변경이력  :
     * @Method 설명 : 패스워드 변경메일
     * @param map
     * @return
     * @throws Exception
     */
    public int userPwdChangeMail(HttpServletRequest request, Map<String, Object> map) throws Exception{

        String sndCode = userDAO.getMailCode();

        map.put("sndCode", sndCode);
        map.put("mailSubject", getLocale(request,"mail.common.pwchange1"));
        map.put("mailForm", FORM_PW_RESET );
        setLocaleVal(request, map, "pwchange", 2);
        int res =  sendMail(request, map);

        if ( res > 0 ) {

            SendEmailCustVO cvo = new SendEmailCustVO();
            cvo.setSndCode(sndCode);
            cvo.setSndYn("Y");
            cvo.setUserEmail(map.get("mailTo").toString());
            cvo.setCtntsCode("CTNT00000000002");
            userDAO.insertSendEmailCust(cvo);

        }

        return res;
    }

	public Map<String, Object> setLocaleVal(HttpServletRequest request, Map<String, Object> map, String str, int num) throws Exception {
		for(int i = 1; i <= num; i++) {
			 map.put(str + i, getLocale(request,"mail.common." + str + i));
		}
		return map;
	}


	/**
	 *
	 * @Method Name  : SendAdminAllMail
	 * @작성일   : 2018. 06. 01.
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 : 메일전송 관리자 리스트
	 * @param mvo
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<MailVO> SendAdminAllMail(MailVO mvo) throws Exception{
    	return menuDAO.SendAdminAllMail(mvo);
	}

	/**
	 *
	 * @Method Name  : SendAllMail
	 * @작성일   : 2018. 06. 01.
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 : 1:1문의 등록시 관리자에게 메일전송
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int SendAllMail(HttpServletRequest request, Map<String, Object> map) throws Exception{

    	String sndCode = userDAO.getMailCode();

    	map.put("sndCode",sndCode);
		map.put("mailSubject", getLocale(request,"mail.common.question5"));
    	map.put("mailForm",FORM_REPLY );
		setLocaleVal(request,map,"question",8);
    	int res = sendMail(request, map);

    	if( res > 0 ){
    		SendEmailCustVO cvo = new SendEmailCustVO();
			cvo.setSndCode(sndCode);
			cvo.setUserEmail(map.get("mailTo").toString());
			cvo.setCtntsCode("CTNT00000000002");
		}
		return res;
	}


	/**
	 *
	 * @Method Name  : SendAllMail
	 * @작성일   : 2018. 06. 07.
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 : 인증자료제출시 사용자에게 메일전송
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int CertSendmail(HttpServletRequest request, Map<String , Object> map) throws Exception{


		String sndCode = userDAO.getMailCode();
		LangMail(request , map);
		map.put("sndCode",sndCode);
		map.put("mailSubject", getLocale(request,"mail.common.cert1"));
		map.put("mailForm",FORM_KYC_CERT );
		setLocaleVal(request,map,"cert",18);
		int res = sendMail(request, map);

		if( res > 0 ){
			SendEmailCustVO cvo = new SendEmailCustVO();
			cvo.setSndCode(sndCode);
			cvo.setUserEmail(map.get("mailTo").toString());
			cvo.setCtntsCode("CTNT00000000002");
		}
		return res;
	}


	public Map<String, Object> LangMail(HttpServletRequest request, Map<String,Object> map)throws Exception{

		if("CMMP00000000142".equals(map.get("certGrade"))){
			map.put("certGrade",getLocale(request,"mail.common.cert8"));
		}else if("CMMP00000000143".equals(map.get("certGrade"))){
			map.put("certGrade",getLocale(request,"mail.common.cert9"));
		}else if("CMMP00000000144".equals(map.get("certGrade"))){
			map.put("certGrade",getLocale(request,"mail.common.cert10"));
		}

		if("CMMC00000000905".equals(map.get("certSubGrade"))){
			map.put("certSubGrade",getLocale(request,"mail.common.cert11"));
		}else if("CMMC00000000906".equals(map.get("certSubGrade"))){
			map.put("certSubGrade", getLocale(request,"mail.common.cert12"));
		}else if("CMMC00000000907".equals(map.get("certSubGrade"))){
			map.put("certSubGrade", getLocale(request,"mail.common.cert13"));
		}else if("CMMC00000000908".equals(map.get("certSubGrade"))){
			map.put("certSubGrade", getLocale(request,"mail.common.cert9"));
		}else if("CMMC00000000909".equals(map.get("certSubGrade"))){
			map.put("certSubGrade", getLocale(request, "mail.common.cert14"));
		}else if("CMMC00000000910".equals(map.get("certSubGrade"))){
			map.put("certSubGrade", getLocale(request, "mail.common.cert15"));
		}else if("CMMC00000000986".equals(map.get("certSubGrade"))){
			map.put("certSubGrade", getLocale(request, "mail.common.cert16"));
		}else if("CMMC00000001146".equals(map.get("certSubGrade"))){
			map.put("certSubGrade", getLocale(request, "mail.common.cert17"));
		}else if("CMMC00000001326".equals(map.get("certSubGrade"))){
			map.put("certSubGrade" , getLocale(request,"mail.common.cert18"));
		}

		return map;
	}

	/**
	 *
	 * @Method Name  : SendAllMail
	 * @작성일   : 2018. 06. 22.
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 : 선불카드신청시 회원에게 메일전송
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int ApplyCardSendmail(HttpServletRequest request , Map<String, Object> map) throws Exception{

		map.put("mailSubject" , getLocale(request,"mail.common.card1"));
		map.put("mailForm", FORM_APPLY_CARD );
        //고객센터번호, 거래소사업자번호, 거래소번호, 거래소이메일, 거래소주소, 거래소명은 footer 공통
		setLocaleVal(request, map , "card",24);
		int res = sendMail(request, map);
		return res;

	}


	public int sendEventMail(HttpServletRequest request , Map<String , Object> map) throws Exception{

		map.put("mailSubject", "안녕하세요. 플랜비트 국제 거래소입니다.");
		map.put("mailForm" , FORM_USER_EVENT );
		int res = sendMail(request , map);
		return res;
	}

	public int sendTrustMail(HttpServletRequest request , Map<String , Object> map) throws Exception{

		map.put("mailSubject" , getLocale(request , "mail.common.trust1"));
		map.put("mailForm" , FORM_TRUST_APPLY );

		String calcRate = "";

		/*if("3".equals(map.get("month").toString())){
			map.put("calcRate" , calcRate);
		}else if("6".equals(map.get("month").toString())){
			map.put("calcRate" , calcRate);
		}else if("9".equals(map.get("month").toString())){
			map.put("calcRate" , calcRate);
		}else if("12".equals(map.get("month").toString())){
			map.put("calcRate" , calcRate);
		}*/
		map.put("calcRate", calcRate);

		setLocaleVal(request , map , "trust" , 16);
		int res = sendMail(request , map);
		return res;
	}

	public int sendTrustReleaseMail(HttpServletRequest request , Map<String , Object> map) throws Exception{
		map.put("mailSubject" , getLocale(request , "mail.common.trust13"));
		map.put("mailForm" , FORM_TRUST_RELEASE );

		setLocaleVal(request , map , "trust" , 16);
		int res = sendMail(request , map);
		return res;
	}

	public int LendingApplyMail(HttpServletRequest request , Map<String , Object> map) throws Exception{
		map.put("mailSubject" , getLocale(request , "mail.common.lending1"));
		map.put("mailForm" , FORM_LENDING_APPLY );

		setLocaleVal(request , map , "lending" , 27);
		int res = sendMail(request , map);
		return res;
	}

	public int extensionMail(HttpServletRequest request , Map<String , Object> map) throws Exception{
		map.put("mailSubject" , getLocale(request , "mail.common.lending20"));
		map.put("mailForm" , FORM_LENDING_EXTENSION );

		setLocaleVal(request , map , "lending" , 27);
		int res = sendMail(request , map);
		return res;
	}

	public int repaymentMail(HttpServletRequest request , Map<String , Object> map) throws Exception{
		map.put("mailSubject" , getLocale(request , "mail.common.lending24"));
		map.put("mailForm" , FORM_LENDING_REPAYMENT );

		setLocaleVal(request , map , "lending" , 27);
		int res = sendMail(request , map);
		return res;
	}

	/**
	 *
	 * @Method Name  : commonCertMail
	 * @작성일   : 2019. 01. 23.
	 * @작성자   :  (주)씨엠이소프트 박동휘
	 * @변경이력  :
	 * @Method 설명 : (공통화) 웹에서 요청시 메일전송
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int commonCertMail(HttpServletRequest request, Map<String , Object> map) throws Exception{

		String sndCode = userDAO.getMailCode();
		LangMail(request , map);
		map.put("sndCode",sndCode);
		map.put("mailSubject", getLocale(request,"mail.common.certmail1"));  // 제목
		map.put("mailForm",FORM_CRRTIFICATION ); // 폼
		setLocaleVal(request,map,"certmail",10);  // 언어팩

		int res = sendMail(request, map);

		if( res > 0 ){
			SendEmailCustVO cvo = new SendEmailCustVO();
			cvo.setSndCode(sndCode);
			cvo.setSndYn("Y");
			cvo.setUserEmail(map.get("mailTo").toString());
			cvo.setCtntsCode("CTNT00000000003");
			System.out.println("common전송완료");
			userDAO.insertSendEmailCust(cvo);
		}
		return res;
	}


	/**
	 *
	 * @Method Name  : depositSendMail
	 * @작성일   : 2019. 03. 21.
	 * @작성자   :  (주)씨엠이소프트 임우빈
	 * @변경이력  :
	 * @Method 설명 : 내부송금시 메일전송
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int depositSendMail(HttpServletRequest request , DepositVO vo) throws Exception{

		Map<String , Object> map = new HashMap<String , Object>();
		map.put("mailTo" , vo.getUserEmail());
		map.put("cnAmt" , vo.getCnAmt());
		String coinNm = coinInfoService.getCoinNm(vo.getCurcyCd());
		map.put("cnKndNm" , coinNm);
		setLocaleVal(request , map , "deposit" , 9);
		map.put("mailSubject" , coinNm + getLocale(request , "mail.common.deposit5") + getLocale(request , "mail.common.deposit6"));
		map.put("mailForm" , "frm_depositSend.html");
		map.put("sysdate", new SimpleDateFormat("MM/dd HH:mm").format(new Date()));
		int res = sendMail(request , map);

		return res;
	}
}
