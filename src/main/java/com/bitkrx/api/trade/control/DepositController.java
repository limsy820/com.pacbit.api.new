/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.trade.control;

import java.text.DecimalFormat;
import java.util.*;

import javax.naming.event.ObjectChangeListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitkrex.coin.provider.wallet.*;
import com.bitkrx.api.auth.service.LoginService;
import com.bitkrx.api.common.service.CoinInfoService;
import com.bitkrx.api.common.vo.CoinCoreInfoListVO;
import com.bitkrx.api.trade.service.WithdrowService;
import com.bitkrx.api.trade.vo.*;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.util.Security;
import com.bitkrx.core.util.HttpComLib;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitkrex.coin.enums.CoinEnum;
import com.bitkrx.api.auth.dao.LoginDAO;
import com.bitkrx.api.auth.service.RTPService;
import com.bitkrx.api.mail.service.impl.MailServiceImpl;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.trade.dao.WithdrowDAO;
import com.bitkrx.api.trade.service.DepositService;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.exception.CmeApplicationException;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.util.StringUtils;
import com.bitkrx.config.vo.SendInfoVO;


/**
 * @프로젝트명 : com.bitkrx.api
 * @패키지 : com.bitkrx.api.trade.control
 * @클래스명 : com.bitkrx.api
 * @작성자 : (주)씨엠이소프트 문영민
 * @작성일 : 2017. 11. 23.
 */
@Controller
@RequestMapping(value = "/bt")
public class DepositController extends CmeDefaultExtendController {

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
    TradeService tradeService;

    @Autowired
    WithdrowService withDAO;

    @Autowired
    CoinInfoService coinInfoService;

    @Autowired
    UserService userService;


    Security security = Security.getinstance();


    /**
     * @param vo
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @Method Name  : depositPoint
     * @작성일 : 2017. 11. 23.
     * @작성자 :  (주)씨엠이소프트 문영민
     * @변경이력 :
     * @Method 설명 : 포인트 충전 및 수정
     */
    @RequestMapping(value = "/deposit/point.dm")
    public @ResponseBody
    CmeResultVO depositPoint(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();
        res.setResultStrCode("000");

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("failCd", "");

        if (vo.getUserEmail().equals("")) {
            resultMap.put("failCd", "999");
            res.setResultMsg("EMAIL값이 없습니다.");
            return res;
        } else if (vo.getCrgPrc().equals("")) {
            resultMap.put("failCd", "998");
            res.setResultMsg("충전금액이 없습니다.");
            return res;
        } else if (vo.getKrxActCode().equals("")) {
            resultMap.put("failCd", "997");
            res.setResultMsg("입금은행이 없습니다.");
            return res;
        }
        setLocale(request, response);

        vo.setPayKndCd("CMMC00000000039");
        try {
            depositService.INSUPT30171020(vo);

            List list = (List) vo.getRESULT();
            if (list != null) {// Result
                Map map = (Map) list.get(0);
                String rtnCd = (String) map.get("RTN_CD");
                if (rtnCd != null && rtnCd.equals("1")) {
                    res.setResultMsg("충전 API 호출 완료");
                    res.setResultStrCode("000");
                } else {
                    res.setResultMsg((String) map.get("RNT_MSG"));
                    res.setResultStrCode(rtnCd);
                }
            }

            res.setProceduresResult(vo.getRESULT());

        } catch (Exception e) {
            String errMsg = vo.getUserEmail() + "님의 충전 도중 오류가 발생 , 충전금액 : " + vo.getCrgPrc() + ", IP : " + vo.getRegIp();
            throw new CmeApplicationException(errMsg);
        }

        try {
            //db현재시간
            String sysdate = tradeService.selectDate("");

            /*메일발송*/
            Map<String, Object> model = new HashMap<String, Object>();
            /*필수값 : mailTo(받는사람메일주소), clientCd(클라이언트코드)*/
            model.put("mailTo", vo.getUserEmail());
            model.put("sysdate", sysdate);
            model.put("crgPrc", StringUtils.toNumFomat(Integer.parseInt(vo.getCrgPrc())));
            mailServiceImpl.depositComplete(request, model);
            /*메일발송*/

            /*문자발송*/
            SendInfoVO svo = new SendInfoVO();
            /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
            svo.setMobile_info(loginService.getUserMobile(vo.getUserEmail()));
            svo.setCrgPrc(StringUtils.toNumFomat(Integer.parseInt(vo.getCrgPrc())));
            svo.setUserEmail(vo.getUserEmail());
            smsApiService.depositComplete(request, svo);
            /*문자발송*/
        } catch (Exception e) {

        }

        //res.setData(new HashMap<String, String>());
        res.setData(resultMap);
        return res;
    }


    @RequestMapping(value = "/deposit/getAcc.dm")
    public @ResponseBody
    CmeResultVO getAcc(DepoBankVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();
        res.setResultMsg("충전 계좌 호출 완료");
        res.setResultStrCode("000");

        String userEmail = vo.getUserEmail();
        vo = depositService.getUserBankInfo(userEmail);
        if (vo != null) {
            map.put("accNo", vo.getAccNo());
            map.put("accNm", vo.getAccNm());
            map.put("bankNm", vo.getBankNm());
        } else {
            res.setResultMsg("계좌등록이 되어 있지 않습니다.");
            res.setResultStrCode("-1");

            return res;
        }

        DepositUserInfoVO dvo = depositService.getUserAccNm(userEmail);
        //String userAccNm = dvo.getUserAccNm() + dvo.getUserMobile();
        vo.setUserAccNm(dvo.getUserAccNm());

        res.setData(vo);

        return res;
    }


    @RequestMapping(value = "/deposit/cnList.dm")
    public @ResponseBody
    CmeResultVO cnList(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();

        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("사용자이메일이 없습니다.");
            res.setResultStrCode("-1");
        } else {

            DepositVO dvo = new DepositVO();
            dvo.setUserEmail(vo.getUserEmail());
            pendingAllCoin(dvo, request);

            res.setResultMsg("코인입금리스트 호출 완료");
            res.setResultStrCode("000");

            List<CoinDepositVO> list = depositService.getCnDepoList(vo);
            if (list == null) {
                list = new ArrayList<CoinDepositVO>();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("list", list);
            res.setData(map);
        }

        return res;
    }

    @RequestMapping(value = "/withdrow/krwView.dm")
    public @ResponseBody
    CmeResultVO withdrowKrwList(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        rService.RTPVertify(request);


        CmeResultVO res = new CmeResultVO();

        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("사용자이메일이 없습니다.");
            res.setResultStrCode("-1");
        } else {

            if (isOper()) {
                DepositVO dvo = new DepositVO();
                dvo.setUserEmail(vo.getUserEmail());
                pendingAllCoin(dvo, request);
            }

            res.setResultMsg("코인출금화면 호출 완료");
            res.setResultStrCode("000");

            KrwWithVO kvo = depositService.getKrwWithdrow(vo);
            String krwUseYnCheck = userService.krwUseYnCheck(vo.getUserEmail());
            if ("N".equals(krwUseYnCheck)) {
                kvo.setKrwPrcCard("0");
            }       // USE_YN 이 N일시 카드출금가능금액 0으로 표시


			/*String krwPointView = depositService.getKrwPoint(vo.getUserEmail());
			kvo.setKrwPrc(krwPointView);*/
            res.setData(kvo);
        }

        return res;
    }


    @RequestMapping(value = "/deposit/getCnAcc.dm")
    public @ResponseBody
    CmeResultVO getCnAcc(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);
        SimpleWalletRpcProvider rpc = null;

        System.out.println("vo.getUserEmail() : " + vo.getUserEmail() + " // vo.getCurcyCd() : " + vo.getCurcyCd());
        CmeResultVO res = new CmeResultVO();

        if(!isOper()) return res;

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("failCd" , "");

        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("사용자이메일이 없습니다.");
            res.setResultStrCode("-1");
        } else if (vo.getCurcyCd().equals("")) {
            res.setResultMsg("통화코드가 없습니다.");
            res.setResultStrCode("-1");
        } else if (vo.getRegIp().equals("")) {
            res.setResultMsg("아이피가 없습니다.");
            res.setResultStrCode("-1");
        } else {
            //임승연 추가
            //민병철 변경(HEC 코인)
//            if (vo.getCurcyCd().equals("CMMC10000000001")) {
//                int cnt = 1;
//                String address = "";
//                while (cnt > 0) {
//                    address = StringUtils.generateRandomPassword(38, 1);
//                    cnt = depositService.getBmcCoinCnt(address);
//                }
//                vo.setAccNo(address);
//                depositService.insAccInfo(vo);
//                map.put("accNo", address);
//                map.put("destiTag", "");
//                res.setData(map);
//                res.setResultMsg("코인 전자지갑주소 생성 완료");
//                res.setResultStrCode("000");
//                return res;
//            }

            List<DepositVO> addr = depositService.getCnAccInfo(vo);
            if (addr.size() == 0) {


                //2018-10-01 코인 코어 공통화 작업 추가부분
                CoinCoreInfoListVO cinfoVO = new CoinCoreInfoListVO();
                cinfoVO.setCnKndCd(vo.getCurcyCd());
                cinfoVO = coinInfoService.selectCoinCoreInfo(cinfoVO);

                if(cinfoVO == null){
                    res.setResultStrCode("000");
                    map.put("failCd" , "777");
                    res.setData(map);
                    return res;
                }

                String address = "";
                if (cinfoVO != null) {
                    if (cinfoVO.getNewAccountType().equals("01")) {//account 주소 생성 타입
                        address = HttpComLib.httpSendGetAPI(cinfoVO.getCoinUrl() + "/newAccount/" + vo.getUserEmail());
                    } else if (cinfoVO.getNewAccountType().equals("02")) {//password 주소 생성 타입
                        address = HttpComLib.httpSendGetAPI(cinfoVO.getCoinUrl() + "/newAccount/" + withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail());
                    } else if (cinfoVO.getNewAccountType().equals("03")) {//tag형 주소 생성 타입
                        address = cinfoVO.getMainAddr();
                        vo.setDestinationTag(depositService.getDestinationTag());
                    } else if (cinfoVO.getNewAccountType().equals("04")) {//이더리움 토큰형 생성 타입
                        vo.setCurcyCd(CmmCdConstant.ETH_CD);
                        addr = depositService.getCnAccInfo(vo);
                        vo.setCurcyCd(cinfoVO.getCnKndCd());
                        if (addr.size() > 0) {
                            vo.setAccNo(addr.get(0).getWletAdr());
                            depositService.insAccInfo(vo);
                            map.put("accNo", vo.getAccNo());
                            map.put("destiTag", "");
                            res.setData(map);
                            res.setResultMsg("코인 전자지갑주소 생성 완료");
                            res.setResultStrCode("000");
                            return res;
                        } else {
                            cinfoVO.setCnKndCd(CmmCdConstant.ETH_CD);
                            cinfoVO = coinInfoService.selectCoinCoreInfo(cinfoVO);
                            address = HttpComLib.httpSendGetAPI(cinfoVO.getCoinUrl() + "/newAccount/" + withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail());
                            System.out.println("코인 공통화 주소 생성3 : " + address + " //cinfoVO.getNewAccountType() : " + cinfoVO.getNewAccountType());
                            JSONParser parser = new JSONParser();
                            JSONObject obj = (JSONObject) parser.parse(address);
                            String result = (String) obj.get("result");
                            String code = (String) obj.get("code");
                            String message = (String) obj.get("message");

                            System.out.println("result : " + result);
                            System.out.println("code : " + code);
                            System.out.println("message : " + message);

                            address = result.replaceAll("\"", "");

                            vo.setAccNo(address);
                            System.out.println("코인 공통화 주소 생성4 : " + vo.getAccNo() + " //cinfoVO.getNewAccountType() : " + cinfoVO.getNewAccountType());

                            depositService.insAccInfo(vo);
                            vo.setCurcyCd(CmmCdConstant.ETH_CD);
                            depositService.insAccInfo(vo);
                            map.put("accNo", address);
                            map.put("destiTag", vo.getDestinationTag());
                            res.setData(map);
                            res.setResultMsg("코인 전자지갑주소 생성 완료");
                            res.setResultStrCode("000");
                            return res;
                        }

                    } else if (cinfoVO.getNewAccountType().equals("05")) {//password(privateKey) 주소 생성 타입
                        address = HttpComLib.httpSendGetAPI(cinfoVO.getCoinUrl() + "/newAccount");
                    } else if (cinfoVO.getNewAccountType().equals("06")) {//crypHash 암호화 타입
                        System.out.println("06 newAccount 타입!");
                        //System.out.println("06 newAccount 타입:" + cinfoVO.getCoinUrl() + "/newAccount/" + withSer.getEthCode(vo.getUserEmail()) + vo.getUserEmail());
                        //address = HttpComLib.httpSendGetAPI(cinfoVO.getCoinUrl() + "/newAccount/" + withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail());
                        address = HttpComLib.httpSendGetAPI(cinfoVO.getCoinUrl() + "/newAccount/" + withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail());
                    }


                    System.out.println("코인 공통화 주소 생성1 : " + address + " //cinfoVO.getNewAccountType() : " + cinfoVO.getNewAccountType());


                    if (!cinfoVO.getNewAccountType().equals("03")) {
                        JSONParser parser = new JSONParser();
                        JSONObject obj = (JSONObject) parser.parse(address);
                        String result = (String) obj.get("result");
                        String code = (String) obj.get("code");
                        String message = (String) obj.get("message");

                        System.out.println("result : " + result);
                        System.out.println("code : " + code);
                        System.out.println("message : " + message);

                        address = result.replaceAll("\"", "");

                        if (address.indexOf("bitcoincash:") > -1) {
                            address = address.replaceAll("bitcoincash:", "");
                        }

                        if (cinfoVO.getNewAccountType().equals("05") || cinfoVO.getNewAccountType().equals("06")) {
                            // security.decrypt(message , vo.getClientPe())
                            vo.setPrivateKey(message); //
                        }
                    }
                    vo.setAccNo(address);

                    System.out.println("코인 공통화 주소 생성2 : " + vo.getAccNo() + " //cinfoVO.getNewAccountType() : " + cinfoVO.getNewAccountType());

                    depositService.insAccInfo(vo);
                    map.put("accNo", address);
                    map.put("destiTag", vo.getDestinationTag());
                    res.setData(map);
                    res.setResultMsg("코인 전자지갑주소 생성 완료");
                    res.setResultStrCode("000");
                    return res;
                }
                //2018-10-01 코인 코어 공통화 작업 추가부분


                try {
                    if (vo.getCurcyCd().equals(CmmCdConstant.BTC_CD)) {
                        rpc = new BitcoinWalletRpcProvider();
                    } else if (vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {
                        rpc = new EthereumWalletRpcProvider();
                    } else if (vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) { //비트코인 캐시
                        rpc = new BitcashWalletRpcProvider();
                    } else if (vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {
                        rpc = new RippleWalletRpcProvider();
                        vo.setDestinationTag(depositService.getDestinationTag());
                    } else if (vo.getCurcyCd().equals(CmmCdConstant.SGC_CD)) {
                        rpc = new StargramWalletRpcProvider();
                    } else if (vo.getCurcyCd().equals(CmmCdConstant.BTG_CD)) {
                        rpc = new BitgoldWalletRpcProvider();
                    } else if (vo.getCurcyCd().equals(CmmCdConstant.QTUM_CD)) {
                        rpc = new QtumWalletRpcProvider();
                    } else if (vo.getCurcyCd().equals(CmmCdConstant.LTC_CD)) {
                        rpc = new LitecoinWalletRpcProvider();
                    } else if (vo.getCurcyCd().equals(CmmCdConstant.DASH_CD)) {
                        rpc = new DashWalletRpcProvider();
                    } else if (vo.getCurcyCd().equals("CMMC00000001005")) {
                        //BMC코인일 경우 , 이더리움 주소가 없을 경우 이더리움 주소를 생성한다.
                        vo.setCurcyCd(CmmCdConstant.ETH_CD);
                        addr = depositService.getCnAccInfo(vo);
                        if (addr.size() > 0) {
                            vo.setAccNo(addr.get(0).getWletAdr());
                            vo.setCurcyCd("CMMC00000001005");
                            depositService.insAccInfo(vo);
                            map.put("accNo", vo.getAccNo());
                            map.put("destiTag", "");
                            res.setData(map);
                            res.setResultMsg("코인 전자지갑주소 생성 완료");
                            res.setResultStrCode("000");
                            return res;
                        }
                        vo.setCurcyCd("CMMC00000001005");
                    } else if (vo.getCurcyCd().equals("CMMC00000001066")) {
                        //시크릿코인일 경우 , 이더리움 주소가 없을 경우 이더리움 주소를 생성한다.
                        vo.setCurcyCd(CmmCdConstant.ETH_CD);
                        addr = depositService.getCnAccInfo(vo);
                        if (addr.size() > 0) {
                            vo.setAccNo(addr.get(0).getWletAdr());
                            vo.setCurcyCd("CMMC00000001066");
                            depositService.insAccInfo(vo);
                            map.put("accNo", vo.getAccNo());
                            map.put("destiTag", "");
                            res.setData(map);
                            res.setResultMsg("코인 전자지갑주소 생성 완료");
                            res.setResultStrCode("000");
                            return res;
                        }
                        vo.setCurcyCd("CMMC00000001066");
                    } else if (vo.getCurcyCd().equals("CMMC00000001086")) {
                        //USDT 코인 지갑생성
                        address = HttpComLib.httpSendGetAPI("http://10.10.0.62:3100/newAccount/" + vo.getUserEmail());
                        if (address != null && !"".equals(address)) {
                            address = address.replaceAll("\"", "");
                            vo.setAccNo(address);
                            depositService.insAccInfo(vo);
                            map.put("accNo", address);
                            map.put("destiTag", "");
                            res.setData(map);
                            res.setResultMsg("코인 전자지갑주소 생성 완료");
                            res.setResultStrCode("000");
                            return res;
                        } else {
                            res.setResultMsg("충전지갑 생성실패");
                            res.setResultStrCode("-1");
                            return res;
                        }
                    } else if (vo.getCurcyCd().equals("CMMC00000001126")) {
                        //HDAC 코인 지갑생성
                        address = HttpComLib.httpSendGetAPI(CmmCdConstant.HDAC_URL + "newAccount");
                        if (address != null && !"".equals(address)) {
                            address = address.replaceAll("\"", "");
                            vo.setAccNo(address);
                            depositService.insAccInfo(vo);
                            map.put("accNo", address);
                            map.put("destiTag", "");
                            res.setData(map);
                            res.setResultMsg("코인 전자지갑주소 생성 완료");
                            res.setResultStrCode("000");
                            return res;
                        } else {
                            res.setResultMsg("충전지갑 생성실패");
                            res.setResultStrCode("-1");
                            return res;
                        }
                    } else if (vo.getCurcyCd().equals("CMMC00000001206")) {//VSTC 코인 (비스타코인)
                        //VSTC 코인 지갑생성
                        address = CmmCdConstant.VSTC_ACC;
                        vo.setAccNo(address);
                        vo.setDestinationTag(depositService.getDestinationTag());
                        depositService.insAccInfo(vo);
                        map.put("accNo", address);
                        map.put("destiTag", vo.getDestinationTag());
                        res.setData(map);
                        res.setResultMsg("코인 전자지갑주소 생성 완료");
                        res.setResultStrCode("000");
                        return res;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    res.setResultMsg("충전지갑 생성실패");
                    res.setResultStrCode("-1");
                    return res;
                }
                address = "";
                if (vo.getCurcyCd().equals("CMMC00000000206") || vo.getCurcyCd().equals("CMMC00000001066") || vo.getCurcyCd().equals("CMMC00000001005")) {
                    //address = rpc.createWallet(withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail());
                    String url = "http://10.10.0.121:3100/newAccount/";
                    //System.out.println("호출 주소 : " + url + withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail());
                    address = HttpComLib.httpSendGetAPI(url + withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail());
                    JSONParser parser = new JSONParser();
                    JSONObject obj = (JSONObject) parser.parse(address);
                    String result = (String) obj.get("result");
                    String code = (String) obj.get("code");
                    String message = (String) obj.get("message");

                    System.out.println("result : " + result);
                    System.out.println("code : " + code);
                    System.out.println("message : " + message);

                    address = result.replaceAll("\"", "");

                } else if (vo.getCurcyCd().equals("CMMC00000000685")) {
                    //address = rpc.createWallet(withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail());
                    String url = "http://10.10.0.122:17000/newAccount/";
                    //System.out.println("호출 주소 : " + url + withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail());
                    address = HttpComLib.httpSendGetAPI(url + withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail());
                    JSONParser parser = new JSONParser();
                    JSONObject obj = (JSONObject) parser.parse(address);
                    String result = (String) obj.get("result");
                    String code = (String) obj.get("code");
                    String message = (String) obj.get("message");

                    System.out.println("result : " + result);
                    System.out.println("code : " + code);
                    System.out.println("message : " + message);

                    address = result.replaceAll("\"", "");
                } else {
                    address = rpc.createWallet(vo.getUserEmail());
                }

                System.out.println("생성주소 : " + address);

                if (address != null && !"".equals(address)) {

                    if (address.indexOf("bitcoincash:") > -1) {
                        address = address.replaceAll("bitcoincash:", "");
                    }
                    address = address.replaceAll("\"", "");
                    vo.setAccNo(address);

                    try {
                        depositService.insAccInfo(vo);
                        map.put("accNo", address);
                        map.put("destiTag", vo.getDestinationTag());
                        res.setData(map);
                        res.setResultMsg("코인 전자지갑주소 생성 완료");
                        res.setResultStrCode("000");
                        if (vo.getCurcyCd().equals("CMMC00000001066") || vo.getCurcyCd().equals("CMMC00000001005")) {
                            vo.setCurcyCd(CmmCdConstant.ETH_CD);
                            depositService.insAccInfo(vo);
                        }
                    } catch (Exception e) {
                        res.setResultMsg("충전지갑 생성실패");
                        res.setResultStrCode("-1");
                    }

                } else {
                    res.setResultMsg("충전지갑 생성실패");
                    res.setResultStrCode("-1");
                }


            } else {

                map.put("accNo", addr.get(0).getWletAdr());
                map.put("destiTag", addr.get(0).getDestinationTag());
                res.setData(map);

                res.setResultMsg("이미 전자지갑주소가 있습니다.");
                res.setResultStrCode("000");
            }

        }


        return res;
    }


    @RequestMapping(value = "/deposit/getCnAccList.dm")
    public @ResponseBody
    CmeResultVO getCnAccList(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();

        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("사용자이메일이 없습니다.");
            res.setResultStrCode("-1");
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            List<CnAccListVO> list = depositService.getCnAccList(vo);
            map.put("list", list);
            res.setData(map);
            res.setResultMsg("코인 전자지갑주소 리스트 호출 완료");
            res.setResultStrCode("000");
        }


        return res;
    }


    @RequestMapping(value = "/deposit/krwList.dm")
    public @ResponseBody
    CmeResultVO krwList(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();

        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("사용자이메일이 없습니다.");
            res.setResultStrCode("-1");
        } else {
            res.setResultMsg("원화입금 리스트 호출 완료");
            res.setResultStrCode("000");

            List<KrwDepositVO> list = depositService.getKrwDepoList(vo);
            if (list == null) {
                list = new ArrayList<KrwDepositVO>();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("list", list);
            res.setData(map);
        }

        return res;
    }


    @RequestMapping(value = "/deposit/pendingCoin.dm")
    public @ResponseBody
    CmeResultVO pendingCoin(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();
        if (!isOper()) return res;
        DepositVO dvo = new DepositVO();
        dvo.setUserEmail(vo.getUserEmail());
        pendingAllCoin(dvo, request);


        res.setResultStrCode("000");
        return res;
    }


    @RequestMapping(value = "/deposit/getTotCoin.dm")
    public @ResponseBody
    CmeResultVO getTotCoin(HttpServletRequest request, HttpServletResponse response) throws Exception {


        CmeResultVO res = new CmeResultVO();
        if (!isOper()) return res;
        Map<String, Object> map = new LinkedHashMap<>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();

        SimpleWalletRpcProvider rpc = new WalletRpcProviderFactory().get(CoinEnum.BITCOIN);
        double bal = 0;

        DecimalFormat df = new DecimalFormat("#,##0.00000000");


        String coins = "";


        //2018-10-01 코인 코어 공통화 작업 추가부분
        CoinCoreInfoListVO cinfoVO = new CoinCoreInfoListVO();
        List<CoinCoreInfoListVO> cList = coinInfoService.selectCoinCoreInfoList(cinfoVO);
        for (CoinCoreInfoListVO cvo : cList) {

            try {

                coins += cvo.getCnKndNm() + ",";

                System.out.println("코인 공통화 코어 잔액 조회 : " + cvo.getCnKndNm() + " 조회 시작");
                String url = "";

                System.out.println("cvo.getCoinSendUrl() : " + cvo.getCoinSendUrl());
                System.out.println("cvo.getCoinUrl() : " + cvo.getCoinUrl());

                if (cvo.getGetBalanceType().equals("04")) {//이더리움 토큰일 경우

                    url = cvo.getCoinUrl() + "/getBalance/" + cvo.getMainAddr();
                    System.out.println("url : " + url);
                    String json = HttpComLib.httpSendGetAPI(url);
                    System.out.println("json : " + json);
                    JSONParser parser = new JSONParser();
                    JSONObject obj = (JSONObject) parser.parse(json);
                    String result = (String) obj.get("result");
                    String code = (String) obj.get("code");
                    String message = (String) obj.get("message");
                    System.out.println("result : " + result);
                    System.out.println("code : " + code);
                    System.out.println("message : " + message);
                    map.put(cvo.getCnKndNm() + "(ETH)", df.format(Double.parseDouble(result)));
                    url = cvo.getTokenGetBalanceUrl() + "/getToken/" + cvo.getCnKndNm() + "/" + cvo.getMainAddr();

                } else if (cvo.getCnKndNm().equals("HDAC")) {
                    url = cvo.getCoinSendUrl() + "/getBalance/main";
                } else if (!cvo.getCoinSendUrl().equals("")) {//코인 전송용 URL이 있을 경우
                    url = cvo.getCoinSendUrl() + "/getBalance/" + cvo.getMainAddr();
                } else {
                    url = cvo.getCoinUrl() + "/getBalance/" + cvo.getMainAddr();
                }
                System.out.println("url : " + url);
                String json = HttpComLib.httpSendGetAPI(url);
                System.out.println("json : " + json);
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(json);

                if (json.contains("vtc")) {
                    obj = (JSONObject) obj.get("result");
                    String vtc = (String) obj.get("vtc");
                    String nem = (String) obj.get("nem");
                    System.out.println("VSTC vtc: " + df.format(Double.parseDouble(vtc)));
                    System.out.println("VSTC nem: " + df.format(Double.parseDouble(nem)));
                    map.put("VSTC", String.valueOf(df.format(Double.parseDouble(vtc))));
                } else if("CMMC00000001606".equals(cvo.getCnKndCd())){
                    //json : {"result":{"nem":"15.12"}, "code" :"0", "message":"success"  }
                    obj = (JSONObject) obj.get("result");
                    String nem = (String) obj.get("nem");
                    System.out.println("XEM nem: " + df.format(Double.parseDouble(nem)));
                    map.put("XEM", String.valueOf(df.format(Double.parseDouble(nem))));
                } else {
                    String result = (String) obj.get("result");
                    String code = (String) obj.get("code");
                    String message = (String) obj.get("message");
                    System.out.println("result : " + result);
                    System.out.println("code : " + code);
                    System.out.println("message : " + message);
                    map.put(cvo.getCnKndNm(), df.format(Double.parseDouble(result)));
                }

            } catch (Exception e) {
                System.out.println(cvo.getCnKndNm() + " getTotcoin 실패");
            }

        }
        System.out.println("코인 공통화 coins : " + coins);
        //2018-10-01 코인 코어 공통화 작업 추가부분


        data.put("list", map);
        res.setData(data);

        return res;
    }


    @RequestMapping(value = "/deposit/getAccList.dm")
    public @ResponseBody
    CmeResultVO getAccList(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        res.setResultMsg("충전 계좌 호출 완료");
        res.setResultStrCode("000");
        Map<String, Object> map = new HashMap<String, Object>();
        List<DepoBankVO> list = depositService.getUserBankInfoList(vo.getUserEmail());
        map.put("list", list);
        res.setData(map);

        return res;
    }

    @RequestMapping(value = "/deposit/selectWletUserInfo.dm")
    public @ResponseBody
    CmeResultVO selectWletUserInfo(WletUserInfoVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();

        res.setResultMsg("조회 완료");
        res.setResultStrCode("000");
        Map<String, Object> map = new HashMap<String, Object>();
        vo = depositService.selectWletUserInfo(vo);
        map.put("data", vo);
        res.setData(map);

        return res;
    }


    @RequestMapping(value = "/deposit/pendingAllCoin.dm")
    public @ResponseBody
    CmeResultVO pendingAllCoin(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //rService.RTPVertify(request);

        CmeResultVO res = new CmeResultVO();
        if (!isOper()) return res;
        DepositVO dvo = new DepositVO();
        List<String> depoUserList = depositService.getDepositUserEmail();
        for (String userEmail : depoUserList) {
            dvo.setUserEmail(userEmail);
            pendingAllCoin(dvo, request);
        }

        res.setResultStrCode("000");
        return res;
    }

    @RequestMapping(value = "/deposit/AccInfo.dm")
    public @ResponseBody
    CmeResultVO AccInfo(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO res = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        res.setResultStrCode("000");
        resultMap.put("failCd", "");
        if (vo.getUserEmail().equals("")) {
            res.setResultMsg("사용자이메일이 없습니다.");
            resultMap.put("failCd", "999");
            res.setData(resultMap);
            return res;
        } else {

            res.setResultMsg("코인출금화면 호출 완료");
            res.setResultStrCode("000");

            KrwWithVO kvo = depositService.getUserAccInfo(vo.getUserEmail());
            if (kvo != null) {
                resultMap.put("userMobile", kvo.getUserMobile());
                resultMap.put("BankAccNo", kvo.getBankAccNo());
            }
            //res.setData(kvo);
        }
        res.setData(resultMap);
        return res;
    }

    @RequestMapping(value = "/deposit/CoinDeposit.dm")
    public @ResponseBody
    CmeResultVO CoinDeposit(DepositVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        /*외 : 61.97.253.196
        내 : 10.10.50.196*/
        //insParamLog(request);

        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");


        String ip = getIp(request, vo.getRegIp());


        if (!"61.97.253.196".equals(ip) && !"10.10.50.196".equals(ip)) {
            resultVO.setResultMsg("다른 IP 입니다.");
            resultMap.put("failCd", "993");
            resultVO.setData(resultMap);
            return resultVO;
        }

        System.out.println("cnKndNm:" + vo.getCnKndNm());
        System.out.println("cnAmt:" + vo.getCnAmt());
        System.out.println("txId:" + vo.getTxId());
        System.out.println("wletAdr:" + vo.getWletAdr());

        if ("".equals(vo.getCnKndNm())) {
            resultVO.setResultMsg("코인명이 없습니다.");
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            return resultVO;
        } else if ("".equals(vo.getCnAmt())) {
            resultVO.setResultMsg("코인수량이 없습니다.");
            resultMap.put("failCd", "998");
            resultVO.setData(resultMap);
            return resultVO;
        } else if ("".equals(vo.getTxId())) {
            resultVO.setResultMsg("txId가 없습니다.");
            resultMap.put("failCd", "997");
            resultVO.setData(resultMap);
            return resultVO;
        } else if ("".equals(vo.getWletAdr())) {
            resultVO.setResultMsg("지갑주소가 없습니다.");
            resultMap.put("failCd", "996");
            resultVO.setData(resultMap);
            return resultVO;
        }


        if (vo.getWletAdr().contains(":")) {//리플, VSTC 일 경우 송금주소에서 데스티네이션코드 분리
            System.out.println(vo.getWletAdr().split(":")[0]);
            System.out.println(vo.getWletAdr().split(":")[1]);
            vo.setDestinationTag(vo.getWletAdr().split(":")[1]);
            vo.setWletAdr(vo.getWletAdr().split(":")[0]);
        } else {
            vo.setWletAdr(vo.getWletAdr());
            vo.setDestinationTag(vo.getDestinationTag());
        }

        String cnKndCd = depositService.getCoinCode(vo.getCnKndNm());
        if ("".equals(cnKndCd) || cnKndCd == null) {
            resultVO.setResultMsg("잘못된 코인명입니다.");
            resultMap.put("failCd", "995");
            resultVO.setData(resultMap);
            return resultVO;
        }

        vo.setCnKndCd(cnKndCd);
        String userEmail = depositService.getWletUserEmail(vo);

        if ("".equals(userEmail) || userEmail == null) {
            resultVO.setResultMsg("조회할 수 없는 회원의 지갑주소입니다.");
            resultMap.put("failCd", "994");
            resultVO.setData(resultMap);
            return resultVO;
        }

        int depositCheck = depositService.getDepositCheckCnt(vo);
        if (depositCheck > 0) {
            resultVO.setResultMsg("이미 입금된 내역입니다.");
            resultMap.put("failCd", "991");
            resultVO.setData(resultMap);
            return resultVO;
        }

        DepositVO dvo = new DepositVO();
        dvo.setUserEmail(userEmail);
        dvo.setDealNo(vo.getTxId());
        dvo.setRegIp("127.0.0.1");
        dvo.setCnAmt(vo.getCnAmt());
        dvo.setDptKndCd("CMMC00000000014");
        dvo.setSndWletAdr(vo.getWletAdr());
        dvo.setCurcyCd(cnKndCd);

        depositService.INSUPT30171030(dvo);//입금진행 INS
        List list = (List) dvo.getRESULT();
        Map<String , Object> map = new HashMap<String , Object>();
        map = (Map) list.get(0);
        String errCd = (String) map.get("ERR_CD");
        System.out.println("errCd" + errCd);
        if("1".equals(errCd)){
            dvo.setDptKndCd("CMMC00000000283");//(입금확정)
            DepositVO dvo2 = depositService.getCnDptCode(dvo);
            dvo.setCnDptCode(dvo2.getCnDptCode());
            depositService.INSUPT30171030(dvo);//입금완료 UPT
            depositService.INS10171028(dvo);//실제코인 INS
        }

        resultVO.setData(resultMap);
        return resultVO;
    }


    @RequestMapping(value = "/deposit/AdmEvent.dm")
    public @ResponseBody
    CmeResultVO AdmEvent(AdmEventVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        if ("".equals(vo.getUserEmail())) {
            resultVO.setResultMsg("사용자 이메일 확인불가");
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            return resultVO;
        }


        // 이미 신청했는지 체크로직
        depositService.PR_INTUPT_ADM_EVENT_CHECK(vo);
        List list = (List) vo.getRESULT();
        Map<String, Object> eventMap = new HashMap<String, Object>();
        eventMap = (Map) list.get(0);
        String eventCd = (String) eventMap.get("RTN_CD");
        System.out.println(eventCd);
        if ("1".equals(eventCd)) {
            resultMap.put("failCd", "997");
            resultVO.setResultMsg("이미 이벤트 참여한 회원");
            resultVO.setData(resultMap);
            return resultVO;
        }


        int eventCnt = depositService.getEventCnt();    // 전체 쿠폰 카운트

        if (eventCnt == 0) {
            resultMap.put("failCd", "996");
            resultVO.setResultMsg("이벤트 종료");
            resultVO.setData(resultMap);
            return resultVO;
        }

        int no = (int) (Math.random() * eventCnt) + 1;   // 쿠폰가져오기

        AdmEventVO avo = new AdmEventVO();
        avo = depositService.getEventInfo(no);

        vo.setCnCpnCode(avo.getCnCpnCode());
        depositService.PR_INTUPT_ADM_EVENT(vo); // 이벤트 신청
        List eventList = (List) vo.getRESULT();
        Map<String, Object> map = new HashMap<String, Object>();
        map = (Map) eventList.get(0);
        String rtnCd = (String) map.get("RTN_CD");
        if ("1".equals(rtnCd)) {
            resultVO.setResultMsg("이벤트 프로시져 호출 성공");
            resultMap.put("giveAmt", avo.getGiveAmt());
        } else {
            resultVO.setResultMsg("기타에러");
            resultMap.put("failCd", "998");
            resultVO.setData(resultMap);
            return resultVO;
        }


        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/deposit/AdmEventCheck.dm")
    public @ResponseBody
    CmeResultVO AdmEventCheck(AdmEventVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        if ("".equals(vo.getUserEmail())) {
            resultVO.setResultMsg("사용자 이메일 확인불가");
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            return resultVO;
        }


        String eventChk = "";
        depositService.PR_INTUPT_ADM_EVENT_CHECK(vo);
        List list = (List) vo.getRESULT();
        Map<String, Object> map = new HashMap<String, Object>();
        map = (Map) list.get(0);
        String rtnCd = (String) map.get("RTN_CD");
        System.out.println(rtnCd);
        if ("0".equals(rtnCd)) {
            eventChk = "Y";
            resultVO.setResultMsg("이벤트 신청 가능한 회원");
        } else if ("1".equals(rtnCd)) {
            eventChk = "N";
            resultVO.setResultMsg("이미 이벤트 참여한 회원");
        }
        //String rtnCd = String.valueOf(map.get("RTN_CD"));

        resultMap.put("eventChk", eventChk);
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/deposit/AdmEventlist.dm")
    public @ResponseBody
    CmeResultVO AdmEventlist(AdmEventVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        if ("".equals(vo.getUserEmail())) {
            resultVO.setResultMsg("사용자 이메일 확인불가");
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

        //vo.setFirstIndex((vo.getPage() -1) * 10);
        vo.setFirstIndex((vo.getPageIndex() - 1) * 10);
        vo.setRecordCountPerPage(vo.getPageUnit());
        int listCnt = depositService.getAdmEventListCnt(vo.getUserEmail());
        resultMap.put("listCnt", listCnt);
        resultMap.put("pageSize", (listCnt - 1) / 10 + 1);
        resultMap.put("page", vo.getPageIndex());
        resultMap.put("pageUnit", vo.getPageUnit());

        List<AdmEventVO> list = depositService.getAdmEventList(vo);

        resultMap.put("list", list);
        resultVO.setData(resultMap);
        return resultVO;
    }

}




