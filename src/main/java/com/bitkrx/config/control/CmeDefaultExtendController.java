package com.bitkrx.config.control;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitkrex.coin.enums.CoinEnum;
import com.bitkrex.coin.provider.wallet.SimpleWalletRpcProvider;
import com.bitkrex.coin.provider.wallet.WalletRpcProviderFactory;
import com.bitkrx.api.auth.service.LoginService;
import com.bitkrx.api.auth.service.RTPService;
import com.bitkrx.api.auth.vo.CommonVO;
import com.bitkrx.api.block.service.BlockChainService;
import com.bitkrx.api.common.service.CoinInfoService;
import com.bitkrx.api.common.vo.CoinCoreInfoListVO;
import com.bitkrx.api.log.service.LogService;
import com.bitkrx.api.log.vo.ParamLogVO;
import com.bitkrx.api.mail.service.impl.MailServiceImpl;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.trade.service.WithdrowService;
import com.bitkrx.api.trade.vo.CnAccListVO;
import com.bitkrx.api.trade.vo.CoinCoreInfoVO;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.util.Security;
import com.bitkrx.config.vo.SendInfoVO;
import com.bitkrx.core.util.HttpComLib;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.LocaleResolver;

import com.bitkrx.api.trade.service.DepositService;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.trade.vo.CoinListVO;
import com.bitkrx.api.trade.vo.DepositVO;
import com.bitkrx.config.util.StringUtils;

public class CmeDefaultExtendController extends CmeExtendsControl{
	
	
	@Autowired
	private LocaleResolver localeResolver; 
	
	@Autowired
    UserService userService;
	
	@Autowired
	TradeService tradeService;
	
	@Autowired
	DepositService depositSvc;

	@Autowired
    BlockChainService blockChainService;

    @Autowired
    DepositService depositService;

    @Autowired
    RTPService rService;

    @Autowired
    MailServiceImpl mailServiceImpl;

    @Autowired
    SmsApiService smsApiService;

    @Autowired
    LoginService loginService;

    @Autowired
    WithdrowService withdrowService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    LogService logService;

    @Autowired
    CoinInfoService coinInfoService;

    Security security = Security.getinstance();

	protected void setLocale(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		  String userEmail = StringUtils.checkNull(request.getParameter("userEmail"), "");
		  String lang = userService.getUserLangCd(userEmail);
		  if(lang == null || "".equals(lang)) lang = "en";
	      Locale locale = new Locale(lang);
	      localeResolver.setLocale(request, response, locale);
    }

    protected void setLocaleEn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userEmail = StringUtils.checkNull(request.getParameter("userEmail"), "");
        String lang = userService.getUserLangCd(userEmail);
        if(!"ko".equals(lang)) lang = "en";
        Locale locale = new Locale(lang);
        localeResolver.setLocale(request, response, locale);
    }

    protected void setLocaleSms(HttpServletRequest request, HttpServletResponse response , String langCd) throws Exception {
        String lang = "";
        if("ko".equals(langCd)){
            lang = "ko";
        }else{
            lang = "en";
        }
        Locale locale = new Locale(lang);
        localeResolver.setLocale(request, response, locale);
    }

    public Method getMarketMethod(Object Service, String methodNm, String mkState, Object param) throws Exception {

	    String cmmNm = tradeService.getCmmNm();
        Class<?> cls = Service.getClass();

        // 20190624 KRW -> PCT로 바뀌면서 sellOreder에 에러가 남
        // DB에서 이름을 가져오는것과 mkState를 비교해서 원화면 공백처리
        if(mkState == null || "".equals(mkState) ||mkState.equals(cmmNm) || mkState.equals("KRW")) {
            mkState = "";
        }
        return cls.getMethod(methodNm + mkState, param.getClass());
    }

    protected void setAdminLocale(HttpServletRequest request,HttpServletResponse response , String lang) throws Exception {
	    Locale locale = new Locale(lang);
	    localeResolver.setLocale(request, response, locale);
    }

	public String makeCoinNm(String curcyCd) throws Exception {
		
		List<CoinListVO> list = tradeService.getCoinList();
		
		String curcyNm = "";
		
		for(CoinListVO vo : list) {
			if(curcyCd.equals(vo.getCurcyCd())) {
				curcyNm =  vo.getCurcyNm();
			}
		}
		
		return curcyNm;
	}


	public static String getRemoteAddr(HttpServletRequest request) {

        String ipaddress = request.getHeader("x-add4sys01");

        if ("".equals(StringUtils.checkNull(ipaddress))) {
            //ipaddress = request.getRemoteAddr();
            ipaddress = request.getHeader("X-Forwarded-For");
        }

        if (ipaddress == null || ipaddress.length() == 0 || "unknown".equalsIgnoreCase(ipaddress)) {
            ipaddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipaddress == null || ipaddress.length() == 0 || "unknown".equalsIgnoreCase(ipaddress)) {
            ipaddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipaddress == null || ipaddress.length() == 0 || "unknown".equalsIgnoreCase(ipaddress)) {
            ipaddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipaddress == null || ipaddress.length() == 0 || "unknown".equalsIgnoreCase(ipaddress)) {
            ipaddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipaddress == null || ipaddress.length() == 0 || "unknown".equalsIgnoreCase(ipaddress)) {
            ipaddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipaddress == null || ipaddress.length() == 0 || "unknown".equalsIgnoreCase(ipaddress)) {
            ipaddress = request.getHeader("X-Real-IP");
        }
        if (ipaddress == null || ipaddress.length() == 0 || "unknown".equalsIgnoreCase(ipaddress)) {
            ipaddress = request.getHeader("X-RealIP");
        }
        if (ipaddress == null || ipaddress.length() == 0 || "unknown".equalsIgnoreCase(ipaddress)) {
            ipaddress = request.getHeader("REMOTE_ADDR");
        }
        if (ipaddress == null || ipaddress.length() == 0 || "unknown".equalsIgnoreCase(ipaddress)) {
            ipaddress = request.getRemoteAddr();
        }

        return ipaddress;
    }		
	
	
	public void coinRefresh(HttpServletRequest request, String userEmail) throws Exception {
		DepositVO dvo = new DepositVO();
		dvo.setUserEmail(userEmail);
        pendingAllCoin(dvo, request);
	}

	public String getIp(HttpServletRequest request, String regIp) {
        /*String ip = request.getHeader("x-add4sys01");

        if(ip == null || ip.equals("")) {
            ip = getRemoteAddr(request);
            if(ip != null && !"".equals(ip)) {
                return StringUtils.checkNull(regIp, "127.0.0.1");
            }
            return ip;
        }

        return ip;*/

        String ip = getRemoteAddr(request);
        System.out.println("ip1:" + ip);
        if(ip != null && !"".equals(ip)) {
            return StringUtils.checkNull(ip, "127.0.0.1");
        }
        System.out.println("ip2:" + ip);
        return ip;
    }

    public String changeDate(String date) throws Exception{

        String yyyy = date.substring(0,4);
        String mm = date.substring(4,6);
        String dd = date.substring(6,8);

        String dt = yyyy + "-" + mm + "-" + dd;

        return dt;
    }

    public void pendingAllCoin(DepositVO dvo, HttpServletRequest request) throws Exception {

        if(!isOper()) return;

        List<CnAccListVO> list = depositService.getCnAccList(dvo);//회원의 지갑 정보

        CoinCoreInfoListVO cinfoVO = new CoinCoreInfoListVO();
        List<CoinCoreInfoListVO> cnList = coinInfoService.selectCoinCoreInfoList(cinfoVO);


        String coins = "";
        for(CoinCoreInfoListVO cvo : cnList) {
            coins += cvo.getCnKndNm() + ",";
            for(CnAccListVO wvo : list) {

                if(!wvo.getAccNo().equals("") && wvo.getCurcyCd().equals(cvo.getCnKndCd()) && !cvo.getMoveType().equals("03")) {//회원지갑 주소가 일치하는 경우, coinmove가 03( 무브안함) 이 아닐 경우

                    dvo.setCnKndCd(cvo.getCnKndCd());
                    int moveCnt = depositService.getCoinMoveHourCnt(dvo);//1시간 내 무브한 경우 확인
                    if(moveCnt > 0) continue;

                    try {
                        String json = "";

                        if(cvo.getGetBalanceType().equals("01")) {//잔액조회 account 타입
                            System.out.println(cvo.getCnKndNm()+ " getBalance : " + cvo.getCoinUrl() + "/getBalance/" + dvo.getUserEmail());
                            json = HttpComLib.httpSendGetAPI(cvo.getCoinUrl() + "/getBalance/" + dvo.getUserEmail());
                        } else if(cvo.getGetBalanceType().equals("02")) {//잔액조회 address 타입
                            System.out.println(cvo.getCnKndNm()+ " getBalance : " + cvo.getCoinUrl() + "/getBalance/" + wvo.getAccNo());
                            json = HttpComLib.httpSendGetAPI(cvo.getCoinUrl() + "/getBalance/" + wvo.getAccNo());
                        } else if(cvo.getGetBalanceType().equals("04")) {//잔액조회 token 타입
                            System.out.println(cvo.getCnKndNm()+ " getToken : " + cvo.getTokenGetBalanceUrl() + "/getToken/" + wvo.getAccNo());
                            json = HttpComLib.httpSendGetAPI(cvo.getTokenGetBalanceUrl() + "/getToken/" + cvo.getCnKndNm() + "/" + wvo.getAccNo());
                        } else if(cvo.getGetBalanceType().equals("05")) { //잔액조회 privateKey 타입
                            System.out.println(cvo.getCnKndNm()+ " getBalance : " + wvo.getAccNo());
                            json = HttpComLib.httpSendGetAPI(cvo.getCoinUrl() + "/getBalance/" + wvo.getAccNo());
                        }

                        System.out.println(cvo.getCnKndNm()+ " 코인 getBalance 호출 : " + json);


                        JSONParser parser = new JSONParser();
                        JSONObject obj = (JSONObject) parser.parse(json);
                        String result = (String) obj.get("result");
                        String code = (String) obj.get("code");
                        String message = (String) obj.get("message");


                        System.out.println("result1 : " + result);
                        System.out.println("code1 : " + code);
                        System.out.println("message1 : " + message);

                        Double bal = 0D;
                        if(code.equals("0")) {
                            bal = Double.parseDouble(result);
                        }
                        if(bal > 0) {
                            if(cvo.getMoveType().equals("01")) {
                                Map<String , Object> map = new HashMap<String , Object>();
                                map.put("from", dvo.getUserEmail());
                                map.put("to" , cvo.getMainAddr());
                                map.put("amt" , String.valueOf(bal));

                                if(map.get("from").equals(map.get("to"))){    // from 과 to 가 같을경우 return
                                    return;
                                }
                                json = HttpComLib.httpSendAPI(cvo.getCoinUrl() + "/coinMove", map);

                                System.out.println(cvo.getCnKndNm()+ "01타입 무브 : " + dvo.getUserEmail() + "에서 " + cvo.getMainAddr() + "로 " + bal + " 무브");
                                System.out.println(cvo.getCnKndNm()+ "01타입 무브 결과 : " + json);

                                insMoveHis(json, dvo.getUserEmail(), cvo.getCnKndCd(), bal);

                            } else if(cvo.getMoveType().equals("02")) {
                                Double sendFee = Double.parseDouble(cvo.getSendFee());
                                if(bal > sendFee) {

                                    BigDecimal wdrReqAmt = new BigDecimal(result);
                                    BigDecimal cnSndFee = new BigDecimal(cvo.getSendFee());
                                    BigDecimal subtract = wdrReqAmt.subtract(cnSndFee);
                                    bal = subtract.doubleValue();
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("from", wvo.getAccNo());
                                    map.put("to", cvo.getMainAddr());
                                    map.put("amt", String.valueOf(bal));
                                    map.put("passwd", withdrowService.getEthCode(dvo.getUserEmail()) + dvo.getUserEmail());
                                    map.put("exchange", CmmCdConstant.EXCHANGE_NAME);
                                    if("06".equals(cvo.getCoinType())){
                                        map.put("crypthash" , wvo.getPrivateKey());
                                        System.out.println("merge crypthash" + wvo.getPrivateKey());
                                    }

                                    if(map.get("from").equals(map.get("to"))){    // from 과 to 가 같을경우 return
                                        return;
                                    }

                                    json = HttpComLib.httpSendAPI(cvo.getCoinUrl() + "/sendTransaction", map);

                                    System.out.println("sendTransaction json : " + json);

                                    result = "";
                                    code = "";
                                    message = "";
                                    String id = "";

                                    parser = new JSONParser();
                                    obj = (JSONObject) parser.parse(json);
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
                                        } else if (_s[0].indexOf("id") > -1) {
                                            if (_s.length > 1) {
                                                //id = _s[1];
                                                id = _s[1].replaceAll("}", "");
                                            }
                                        }
                                    }
                                    map = new HashMap();
                                    map.put("id", id);
                                    map.put("exchange", CmmCdConstant.EXCHANGE_NAME);
                                    map.put("coin", cvo.getCnKndNm());
                                    map.put("txid", result);
                                    map.put("from", wvo.getAccNo());
                                    if(!wvo.getDestiTag().equals("")){
                                        map.put("to" , cvo.getMainAddr() + "_" + wvo.getDestiTag());
                                    }else{
                                        map.put("to", cvo.getMainAddr());
                                    }
                                    map.put("amt", String.valueOf(bal));
                                    map.put("message", message);
                                    map.put("core", "api");
                                    if ("0".equals(code)) {//성공
                                        map.put("state", "success");
                                    } else {
                                        map.put("state", "error");
                                    }

                                    if(!"VSTC".equals(cvo.getCnKndNm())){
                                        System.out.println("코인명:" + map.get("coin").toString());
                                        System.out.println("id:" + map.get("id").toString());
                                        System.out.println("exchange:" + map.get("exchange").toString());
                                        System.out.println("coin:" + map.get("coin").toString());
                                        System.out.println("txid:" + map.get("txid").toString());
                                        System.out.println("from:" + map.get("from").toString());
                                        System.out.println("to:" + map.get("to").toString());
                                        System.out.println("amt:" + map.get("amt").toString());
                                        System.out.println("message:" + map.get("message").toString());
                                        System.out.println("core:" + map.get("core").toString());
                                        System.out.println("state:" + map.get("state").toString());
                                        HttpComLib.httpSendAPI("http://61.97.253.163:5100/remitInsert", map);
                                    }
                                    System.out.println(cvo.getCnKndNm()+ "02타입 무브 : " + wvo.getAccNo() + "에서 " + cvo.getMainAddr() + "로 " + String.valueOf(subtract.doubleValue()) + " 무브");
                                    System.out.println(cvo.getCnKndNm()+ "02타입 무브 결과 : " + json);

                                    insMoveHis(json, dvo.getUserEmail(), cvo.getCnKndCd(), bal);
                                }

                            } else if(cvo.getMoveType().equals("04")) {
                                json = HttpComLib.httpSendGetAPI(cvo.getCoinUrl() + "/getBalance/" + wvo.getAccNo());
                                parser = new JSONParser();
                                obj = (JSONObject) parser.parse(json);
                                result = (String) obj.get("result");
                                System.out.println("result1 : " + result);
                                Double ethVal = Double.parseDouble(result);
                                if(ethVal > 0.0016) {//토큰머지용 이더 수수료가 있을 경우
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("from", wvo.getAccNo());
                                    map.put("to", cvo.getMainAddr());
                                    map.put("amt", String.valueOf(bal));
                                    map.put("token" , cvo.getCnKndNm());
                                    map.put("passwd", withdrowService.getEthCode(dvo.getUserEmail()) + dvo.getUserEmail());

                                    if(map.get("from").equals(map.get("to"))){    // from 과 to 가 같을경우 return
                                        return;
                                    }

                                    json = HttpComLib.httpSendAPI(cvo.getCoinSendUrl() + "/sendToken", map);
                                    insMoveHis(json, dvo.getUserEmail(), cvo.getCnKndCd(), bal);
                                } else {//토큰머지용 이더 수수료가 없을 경우
                                    dvo.setCnKndCd(CmmCdConstant.ETH_CD);
                                    moveCnt = depositService.getCoinMoveHourCnt(dvo);//1시간 내 무브한 경우 확인
                                    if(moveCnt == 0) {
                                        json = HttpComLib.httpSendGetAPI(cvo.getCoinUrl() + "/getBalance/" + cvo.getMainAddr());
                                        parser = new JSONParser();
                                        obj = (JSONObject) parser.parse(json);
                                        result = (String) obj.get("result");
                                        System.out.println("result1 : " + result);
                                        Double mainVal = Double.parseDouble(result);
                                        if(mainVal > 0.002) {
                                            Map<String, Object> map = new HashMap<String, Object>();
                                            map.put("from", cvo.getMainAddr());
                                            map.put("to", wvo.getAccNo());
                                            map.put("amt", cvo.getSendFee());
                                            map.put("passwd", withdrowService.getEthCode(cvo.getMainPass()) + cvo.getMainPass());
                                            map.put("exchange", CmmCdConstant.EXCHANGE_NAME);

                                            if(map.get("from").equals(map.get("to"))){    // from 과 to 가 같을경우 return
                                                return;
                                            }

                                            json = HttpComLib.httpSendAPI(cvo.getCoinUrl() + "/sendTransaction", map);

                                            System.out.println("sendTransaction json : " + json);

                                            result = "";
                                            code = "";
                                            message = "";
                                            String id = "";

                                            parser = new JSONParser();
                                            obj = (JSONObject) parser.parse(json);
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
                                                } else if (_s[0].indexOf("id") > -1) {
                                                    if (_s.length > 1) {
                                                        //id = _s[1];
                                                        id = _s[1].replaceAll("}", "");
                                                    }
                                                }
                                            }
                                            map = new HashMap();
                                            map.put("id", id);
                                            map.put("exchange", CmmCdConstant.EXCHANGE_NAME);
                                            map.put("coin", cvo.getCnKndNm());
                                            map.put("txid", result);
                                            map.put("from", wvo.getAccNo());
                                            if(!wvo.getDestiTag().equals("")){
                                                map.put("to" , cvo.getMainAddr() + "_" + wvo.getDestiTag());
                                            }else{
                                                map.put("to", cvo.getMainAddr());
                                            }
                                            map.put("amt", String.valueOf(bal));
                                            map.put("message", message);
                                            map.put("core", "api");
                                            if ("0".equals(code)) {//성공
                                                map.put("state", "success");
                                            } else {
                                                map.put("state", "error");
                                            }

                                            if(!"VSTC".equals(cvo.getCnKndNm())){
                                                System.out.println("코인명:" + map.get("coin").toString());
                                                System.out.println("id:" + map.get("id").toString());
                                                System.out.println("exchange:" + map.get("exchange").toString());
                                                System.out.println("coin:" + map.get("coin").toString());
                                                System.out.println("txid:" + map.get("txid").toString());
                                                System.out.println("from:" + map.get("from").toString());
                                                System.out.println("to:" + map.get("to").toString());
                                                System.out.println("amt:" + map.get("amt").toString());
                                                System.out.println("message:" + map.get("message").toString());
                                                System.out.println("core:" + map.get("core").toString());
                                                System.out.println("state:" + map.get("state").toString());
                                                HttpComLib.httpSendAPI("http://61.97.253.163:5100/remitInsert", map);
                                            }


                                            insMoveHis(json, dvo.getUserEmail(), CmmCdConstant.ETH_CD, Double.parseDouble(cvo.getSendFee()));
                                        }
                                    }
                                }
                            } else if(cvo.getMoveType().equals("05")){
                                Map<String , Object> map = new HashMap<String , Object>();
                                map.put("from", dvo.getUserEmail());
                                map.put("to" , cvo.getMainAddr());
                                map.put("amt" , String.valueOf(bal));
                                map.put("passwd" , wvo.getPrivateKey());
                                map.put("exchange" , CmmCdConstant.EXCHANGE_NAME);
                                if(map.get("from").equals(map.get("to"))){    // from 과 to 가 같을경우 return
                                    return;
                                }
                                json = HttpComLib.httpSendAPI(cvo.getCoinUrl() + "/sendTransaction", map);

                                System.out.println(cvo.getCnKndNm()+ "05타입 무브 : " + dvo.getUserEmail() + "에서 " + cvo.getMainAddr() + "로 " + bal + " 무브");
                                System.out.println(cvo.getCnKndNm()+ "05타입 무브 결과 : " + json);

                                insMoveHis(json, dvo.getUserEmail(), cvo.getCnKndCd(), bal);

                            } /*else if(cvo.getMoveType().equals("06")){
                                Map<String , Object> map = new HashMap<String , Object>();
                                map.put("to" , cvo.getMainAddr());
                                map.put("passwd" , withdrowService.getEthCode(dvo.getUserEmail()) + dvo.getUserEmail());
                                map.put("cryphash" , wvo.getPrivateKey());
                                map.put("amt" , String.valueOf(bal));
                                map.put("exchange" , CmmCdConstant.EXCHANGE_NAME);

                                json = HttpComLib.httpSendAPI(cvo.getCoinUrl() + "/sendTransaction" , map);

                                System.out.println(cvo.getCnKndNm()+ "06타입 무브 : " + dvo.getUserEmail() + "에서 " + cvo.getMainAddr() + "로 " + bal + " 무브");
                                System.out.println(cvo.getCnKndNm()+ "06타입 무브 결과 : " + json);

                                insMoveHis(json, dvo.getUserEmail(), cvo.getCnKndCd(), bal);
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }

        System.out.println("pendingAllCoin coins : " + coins);


    }

    public void insMoveHis(String json, String userEmail, String cnKndCd, Double bal) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        String result = "";
        String code = "";
        String message = "";
        obj = (JSONObject) parser.parse( json );
        //System.out.println("json : " + json);
        String str = obj.toString();
        String[] strs = str.split(",");
        for(String s : strs) {

            String[] _s = s.split(":\"");
            _s[1] = _s[1].replaceAll("\"", "");
            if(_s[0].contains("result")) {
                if(_s.length > 1) {
                    result = _s[1];
                }
            } else if(_s[0].contains("code")) {
                if(_s.length > 1) {
                    code = _s[1];
                }
            } else if(_s[0].contains("message")) {
                if(_s.length > 1) {
                    message = _s[1];
                    message = message.replaceAll("}", "");
                }
            }
        }

        DepositVO vo = new DepositVO();
        vo.setCode(code);
        vo.setDealNo(result);
        vo.setMemo(message);
        vo.setCnAmt(bal);
        vo.setUserEmail(userEmail);
        vo.setCnKndCd(cnKndCd);

        if("0".equals(code)) {//0으로 떨어지면 성공
            vo.setSfYn("Y");
        } else {
            vo.setSfYn("N");
        }
        depositService.insCoinMoveHis(vo);
    }

    public static final String EXCHANGE_RATE_URL = "http://apilayer.net/api/live?access_key=a6d705f98b0e219f65c03b0bc78ab134&currencies=USD,KRW,JPY,SGD,THB";

    public Map<String, String> getExchangeRate() throws Exception {
        String jsonStr = HttpComLib.httpSendGetAPI(EXCHANGE_RATE_URL);

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(jsonStr);
        JSONObject data = (JSONObject) obj.get("quotes");

        Map<String, String> map = new HashMap<String, String>();
        map.put("USDKRW", data.get("USDKRW").toString());
        map.put("USDJPY", data.get("USDJPY").toString());
        map.put("USDSGD", data.get("USDSGD").toString());
        map.put("USDTHB", data.get("USDTHB").toString());

        return map;
    }

    //이더리움계열 코인
    //DB 트랜잭션 관계로 controller단으로 뺌
    public void ethPendingOld(DepositVO vo, SimpleWalletRpcProvider rpc, HttpServletRequest request) throws Exception {

        String cnKndCd = vo.getCurcyCd();
        String url = "";
        double minusVal = 0D;
        if (CmmCdConstant.ETH_CD.equals(cnKndCd)) {
            url = CmmCdConstant.ETH_URL;
            minusVal = 0.007;
        } else if (CmmCdConstant.SGC_CD.equals(cnKndCd)) {
            url = CmmCdConstant.SGC_URL;
            minusVal = 0.005;
        }
        List<DepositVO> devoList = depositSvc.getCnAccInfo(vo);
        String jsonStr = "";

        for (DepositVO devo : devoList) {

            String rval = "";

            if (CmmCdConstant.ETH_CD.equals(cnKndCd)) {
                rval = HttpComLib.httpSendGetAPI("http://10.10.0.121:3100/getBalance/" + devo.getWletAdr());
            } else if (CmmCdConstant.SGC_CD.equals(cnKndCd)) {
                rval = HttpComLib.httpSendGetAPI("http://10.10.0.122:17000/getBalance/" + devo.getWletAdr());
            }

            System.out.println(cnKndCd + " rval : " + rval);

            double value = Double.parseDouble(rval);
            if (rval != null && !"".equals(rval) && Double.parseDouble(rval) > 0) {

                jsonStr = HttpComLib.httpSendGetAPI(url + StringUtils.lowerCase(devo.getWletAdr()) + "/30");

                if (jsonStr.contains("<!DOCTYPE html>")) {
                    continue;
                }

                System.out.println(cnKndCd + " jsonStr : " + jsonStr);

                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(jsonStr);
                JSONArray list = (JSONArray) obj.get("data");

                for (Object arr : list) {
                    JSONArray jarr = (JSONArray) arr;
                    /*System.out.println("txId : " + jarr.get(0));
                    System.out.println("txNo : " + jarr.get(1));
                    System.out.println("txFrom : " + jarr.get(2));
                    System.out.println("txTo : " + jarr.get(3));
                    System.out.println("txAmount : " + jarr.get(4));*/
                    String txId = jarr.get(0).toString();
                    String txNo = jarr.get(1).toString();
                    String txFrom = jarr.get(2).toString();
                    String txTo = jarr.get(3).toString();
                    double txAmount = Double.parseDouble(jarr.get(4).toString());

                    vo.setDealNo(jarr.get(0).toString());
                    vo.setRegIp("127.0.0.1");
                    vo.setCnAmt(txAmount);

                    DepositVO dvo = depositSvc.getCnDptCode(vo);

                    if (dvo == null) {// 입금진행중 건수가 없으면

                        vo.setCnDptCode("");
                        vo.setDptKndCd("CMMC00000000014");
                        vo.setSndWletAdr(txTo);
                        depositSvc.INSUPT30171030(vo);//입금진행 INS
                        dvo = depositSvc.getCnDptCode(vo);

                        vo.setDptKndCd("CMMC00000000283");//( 입금확정)
                        vo.setCnDptCode(dvo.getCnDptCode());
                        depositSvc.INSUPT30171030(vo);//입금완료 UPT
                        depositSvc.INS10171028(vo);//실제코인 INS

                        try {

                            vo.setWletAdr(txTo);
                            vo.setCnKndCd(cnKndCd);
                            vo.setCnAmt(txAmount - minusVal);
                            if (value > (txAmount - minusVal)) {
                                String password = withdrowService.getEthCode(vo.getUserEmail()) + vo.getUserEmail();
                                int res = depositSvc.insertEthMoveHistory(vo, rpc, password);
                            }


                        } catch (Exception e) {
                            //System.out.println("코인전송실패!!, 실패사유 : " + e.getMessage());
                            vo.setDealNo("");
                            vo.setSfYn("N");
                            vo.setMemo("알수없는 이유로 실패");
                            depositSvc.insCoinMoveHis(vo);
                        }

                        try {
                            /*문자발송*/
                            SendInfoVO svo = new SendInfoVO();
                            /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
                            svo.setMobile_info(loginService.getUserMobile(vo.getUserEmail()));
                            svo.setCnAmt(String.valueOf(txAmount));
                            svo.setCurcyNm(vo.getCurcyNm());
                            svo.setUserEmail(vo.getUserEmail());
                            smsApiService.smsDepoComp(request, svo);
                            /*문자발송*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (value > 0.01) { //머지가 안되 있을 경우 머지시킴
                        try {
                            vo.setWletAdr(txTo);
                            vo.setCnKndCd(cnKndCd);
                            vo.setCnAmt(value - minusVal);
                            if(vo.getCnAmt() > 0) {
                                String password = withdrowService.getEthCode(vo.getUserEmail()) + vo.getUserEmail();
                                int res = depositSvc.insertEthMoveHistory(vo, rpc, password);
                            }

                            return;

                        } catch (Exception e) {
                            // System.out.println("코인전송실패!!, 실패사유 : " + e.getMessage());
                            vo.setDealNo("");
                            vo.setSfYn("N");
                            vo.setMemo("알수없는 이유로 실패");
                            depositSvc.insCoinMoveHis(vo);
                        }
                    }

                }

            }

        }
    }

    public boolean isOper() throws Exception {
        String path = servletContext.getRealPath("/");
        path += "WEB-INF/classes/cmeconfig/CmeProps";
        //System.out.println(path);
        Properties props = new Properties();
        FileInputStream fis  = new FileInputStream(path + "/dataStatus.properties");
        props.load(new java.io.BufferedInputStream(fis));
        String status = props.getProperty("status").trim();
        if(status.equals("0")){
            return true;
        } else {
            return false;

        }
    }

    public void insParamLog(HttpServletRequest request) throws Exception {

        try {
            String ip = getIp(request, "확인불가");
            Enumeration params = request.getParameterNames();
            String param = "";
            while (params.hasMoreElements()) {
                String name = (String) params.nextElement();
                if (!param.equals("")) {
                    param += ",";
                }
                param += name + ":" + request.getParameter(name);
            }
            ParamLogVO logVO = new ParamLogVO();
            logVO.setParam(param);
            logVO.setRegIp(ip);
            logVO.setReqUrl(request.getRequestURI());
            logVO.setUserEmail(StringUtils.checkNull(request.getParameter("userEmail"), "확인불가"));
            logService.insParamLog(logVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void insSendParamLog(HttpServletRequest request) throws Exception {

        try {
            String ip = getIp(request, "확인불가");
            Enumeration params = request.getParameterNames();
            String param = "";
            while (params.hasMoreElements()) {
                String name = (String) params.nextElement();
                if (!param.equals("")) {
                    param += ",";
                }
                param += name + ":" + request.getParameter(name);
            }
            ParamLogVO logVO = new ParamLogVO();
            logVO.setParam(param);
            logVO.setRegIp(ip);
            logVO.setReqUrl(request.getRequestURI());
            logVO.setUserEmail(StringUtils.checkNull(request.getParameter("userEmail"), "확인불가"));
            logService.insSendParamLog(logVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getClientPe(HttpServletRequest request , HttpServletResponse response) throws Exception{

        String clientPe = "";
        String module = (String) security.getMap().get("pubKeyModule");




        return clientPe;
    }




















    public void pendingAllCoinNew(DepositVO dvo, HttpServletRequest request) throws Exception {

        if(!isOper()) return;

        List<CnAccListVO> list = depositService.getCnAccList(dvo);//회원의 지갑 정보

        CoinCoreInfoListVO cinfoVO = new CoinCoreInfoListVO();
        List<CoinCoreInfoListVO> cnList = coinInfoService.selectCoinCoreInfoList(cinfoVO);


        for(CoinCoreInfoListVO cvo : cnList) {

            for(CnAccListVO wvo : list) {

                if(wvo.getCurcyCd().equals(cvo.getCnKndCd()) && !cvo.getMoveType().equals("03")) {//회원지갑 주소가 일치하는 경우, coinmove가 03( 무브안함) 이 아닐 경우

                    try {
                        String json = "";

                        if(cinfoVO.getGetBalanceType().equals("01")) {//잔액조회 account 타입
                            json = HttpComLib.httpSendGetAPI(cinfoVO.getCoinUrl() + "/getBalance/" + dvo.getUserEmail());
                        } else if(cinfoVO.getGetBalanceType().equals("02")) {//잔액조회 address 타입
                            json = HttpComLib.httpSendGetAPI(cinfoVO.getCoinUrl() + "/getBalance/" + wvo.getAccNo());
                        }

                        System.out.println(cvo.getCnKndNm()+ " 코인 무브 호출 : " + json);

                        JSONParser parser = new JSONParser();
                        JSONObject obj = (JSONObject) parser.parse(json);
                        String result = (String) obj.get("result");
                        String code = (String) obj.get("code");
                        String message = (String) obj.get("message");

                        System.out.println("result1 : " + result);
                        System.out.println("code1 : " + code);
                        System.out.println("message1 : " + message);

                        Double bal = 0D;
                        if(code.equals("0")) {
                            bal = Double.parseDouble(result);
                        }
                        if(bal > 0) {
                            if(cinfoVO.getMoveType().equals("01")) {
                                Map<String , Object> map = new HashMap<String , Object>();
                                map.put("from", dvo.getUserEmail());
                                map.put("to" , cinfoVO.getMainAddr());
                                map.put("amt" , bal);
                                HttpComLib.httpSendAPI(cinfoVO.getCoinUrl() + "/coinMove", map);

                                System.out.println("01타입 무브 : " + dvo.getUserEmail() + "에서 " + cinfoVO.getMainAddr() + "로 " + bal + " 무브");
                            } else if(cinfoVO.getMoveType().equals("02")) {
                                Double sendFee = Double.parseDouble(cinfoVO.getSendFee());
                                if(bal > sendFee) {
                                    BigDecimal wdrReqAmt = new BigDecimal(result);
                                    BigDecimal cnSndFee = new BigDecimal(cinfoVO.getSendFee());
                                    BigDecimal subtract = wdrReqAmt.subtract(cnSndFee);
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("from", wvo.getAccNo());
                                    map.put("to", cinfoVO.getMainAddr());
                                    map.put("amt", String.valueOf(subtract.doubleValue()));
                                    map.put("exchange", CmmCdConstant.EXCHANGE_NAME);
                                    json = HttpComLib.httpSendAPI(cinfoVO.getCoinUrl() + "/sendTransaction", map);
                                    System.out.println("02타입 무브 : " + wvo.getAccNo() + "에서 " + cinfoVO.getMainAddr() + "로 " + String.valueOf(subtract.doubleValue()) + " 무브");
                                }

                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }

    }

}
