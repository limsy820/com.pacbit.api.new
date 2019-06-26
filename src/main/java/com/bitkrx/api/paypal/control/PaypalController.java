/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.paypal.control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bitkrx.api.auth.dao.LoginDAO;
import com.bitkrx.api.auth.service.RTPService;
import com.bitkrx.api.mail.service.impl.MailServiceImpl;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.trade.service.DepositService;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.trade.vo.DepositVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.util.StringUtils;
import com.bitkrx.config.vo.SendInfoVO;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.paypal.control
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 2. 19.
 */
@Controller
@RequestMapping(value = "/bt")
public class PaypalController extends CmeDefaultExtendController{

	@Autowired
	DepositService depositService;
	
	@Autowired
	RTPService rService;
	
	@Autowired 
	MailServiceImpl mailServiceImpl;
	
	@Autowired
	SmsApiService smsApiService;
	
	@Autowired
	LoginDAO loginDAO;
	
	@Autowired
	TradeService tradeService;
	
	@Autowired
	DepositService depositSvc;
	
	// PDT데이터를 페이팔로 보낼 서버주소
	private static String URL_PAYPAL_VALIDATE;
	// Identity token
	private static String PARAM_AT_VALUE;

	// PDT 첫번째 응답 변수 선언
	private static final String PARAM_TX = "tx";
	private static final String PARAM_CMD = "cmd";
	private static final String PARAM_CMD_VALUE = "_notify-synch";
	private static final String PARAM_AT = "at";
	private static final String RESPONSE_SUCCESS = "SUCCESS";
	private static final String RESPONSE_FAIL = "FAIL";
	
	static
	{
		URL_PAYPAL_VALIDATE = "https://www.sandbox.paypal.com/cgi-bin/webscr";
		//PARAM_AT_VALUE = "Mr-7Y5JPbIYodjuUd5UUcNlyG7T_KsXV2DJpmi7RfkOj9s8HdQA36_iNY1W";
		PARAM_AT_VALUE = "ZGUZC9QOvwjjkMt2pfcVaxHwK21_aJE1mFfa6RNZplyyBtJexYko_s6EevC";
	}

	private static final String PARAM_ITEM_NAME = "item_name"; // 상품이름
	private static final String PARAM_PAYMENT_STATUS = "payment_status"; // 결제 상태
	private static final String PARAM_ITEM_NUMBER = "item_number"; // 상품번호
	private static final String PARAM_MC_GROSS = "mc_gross"; // 페이팔 결제금액
	private static final String PARAM_MC_FEE = "mc_fee"; // 페이팔 수수료금액
	private static final String PARAM_MC_CURRENCY = "mc_currency"; // 화폐
	private static final String PARAM_TXN_ID = "txn_id"; // 거래번호
	private static final String PARAM_RECEIVER_EMAIL = "receiver_email"; // 페이팔 판매자계정 이메일
	private static final String PARAM_PAYER_EMAIL = "payer_email"; // 페이팔 구매자계정 이메일
	private static final String PARAM_CUSTOM = "custom"; // 상점회원번호

	@RequestMapping(value = "/paypal")
	public ModelAndView paypal(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		return new ModelAndView("paypal");
	}
	
	@RequestMapping(value = "/requestPaypal")
	public ModelAndView requestPaypal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		handleRequestPDT(request);
		return new ModelAndView("paypal_end");
	}
	
	/** 페이팔 결제 PDT정보 핸들링 */
	public void handleRequestPDT(HttpServletRequest request) throws Exception {
		// PayPal로부터온 파라미터를 표시한다.
		Enumeration en = request.getParameterNames();
		
		String readString = "";

		while (en.hasMoreElements()) {

			String paramName = (String) en.nextElement();
			String paramValue = request.getParameter(paramName);
			readString = readString + "&" + paramName + "=" + URLDecoder.decode(paramValue, "UTF-8");

		}


		// 다시 PayPal로 게시하기 위해 파라미터를 구성한다.
		String str = PARAM_CMD + "=" + PARAM_CMD_VALUE;
		en = request.getParameterNames();

		while (en.hasMoreElements()) {

			String paramName = (String) en.nextElement();
			String paramValue = request.getParameter(paramName);
			str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue, "UTF-8");

		}
		
		str = str + "&" + PARAM_AT + "=" + PARAM_AT_VALUE;

		System.out.println("str ::::::::: " + str);
		// 유효성을 검사하기 위해 PayPal로 다시 전송시작.

		URL u = new URL(URL_PAYPAL_VALIDATE);

		HttpURLConnection uc = (HttpURLConnection) u.openConnection();

		uc.setDoOutput(true);

		uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		PrintWriter pw = new PrintWriter(uc.getOutputStream());

		pw.println(str);

		pw.close();


		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));

		String res = in.readLine();

		System.out.println("res ::::::::: " + res);
		if (res.equals(RESPONSE_SUCCESS)) {

			String[] temp;

			HashMap vars = new HashMap();

			while ((res = in.readLine()) != null) {

				temp = res.split("=");

				if (temp.length == 2) {

					vars.put(temp[0], URLDecoder.decode(temp[1], "UTF-8"));

				} else {

					vars.put(temp[0], "");

				}

			}

			String itemName = (String) vars.get(PARAM_ITEM_NAME);

			int itemNumber = Integer.parseInt((String) vars.get(PARAM_ITEM_NUMBER));

			String paymentStatus = (String) vars.get(PARAM_PAYMENT_STATUS);

			double paymentAmount = Double.parseDouble((String) vars.get(PARAM_MC_GROSS));

			double paymentFee = Double.parseDouble((String) vars.get(PARAM_MC_FEE));

			String paymentCurrency = (String) vars.get(PARAM_MC_CURRENCY);

			String txnId = (String) vars.get(PARAM_TXN_ID);

			String receiverEmail = (String) vars.get(PARAM_RECEIVER_EMAIL);

			String payerEmail = (String) vars.get(PARAM_PAYER_EMAIL);

			String userseq = (String) vars.get(PARAM_CUSTOM);

			System.out.println("itemName ::::::::: " + itemName);
			System.out.println("itemNumber ::::::: " + itemNumber);
			System.out.println("paymentStatus :::: " + paymentStatus);
			System.out.println("paymentAmount :::: " + paymentAmount);
			System.out.println("paymentFee ::::::: " + paymentFee);
			System.out.println("paymentCurrency :: " + paymentCurrency);
			System.out.println("txnId :::::::::::: " + txnId);
			System.out.println("receiverEmail :::: " + receiverEmail);
			System.out.println("payerEmail ::::::: " + payerEmail);
			System.out.println("userseq :::::::::: " + userseq);
			
			//실제 입금 금액은 결제금액 - 수수료금액
			double chgprc = paymentAmount - paymentFee;
			
			DepositVO vo = new DepositVO();
			vo.setPayKndCd("CMMC00000000445");
			vo.setCrgPrc(String.format("%.3f", chgprc));
			vo.setRegIp("127.0.0.1");
			vo.setUserEmail(userseq);
			depositService.INSUPT30171020(vo);
			//db현재시간
			String sysdate = tradeService.selectDate("");
			
			/*메일발송*/
	        Map <String, Object> model = new HashMap <String, Object> ();
	        /*필수값 : mailTo(받는사람메일주소), clientCd(클라이언트코드)*/
	        model.put("mailTo", vo.getUserEmail());
	        model.put("sysdate", sysdate);
	        model.put("crgPrc", vo.getCrgPrc());
	        mailServiceImpl.depositComplete(request, model);
	        /*메일발송*/
	        
	        /*문자발송*/
			SendInfoVO svo = new SendInfoVO();
			/*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
			svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
			svo.setCrgPrc(vo.getCrgPrc());
			svo.setUserEmail(vo.getUserEmail());
			smsApiService.depositComplete(request, svo);
			/*문자발송*/
			
			
		} else if (res.equals(RESPONSE_FAIL)) {

			System.out.println("페이팔서버로 부터 PDT유효성 요청이 실패했습니다. 상태:" + res);

		} else {

			System.out.println("페이팔서버로 부터 PDT유효성 요청이 실패했습니다. 상태:" + res);

		}



	 }

	
}
