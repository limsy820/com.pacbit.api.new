package com.bitkrx.api.trade.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitkrx.api.common.service.CoinInfoService;
import com.bitkrx.api.common.vo.CoinCoreInfoListVO;
import com.bitkrx.api.mail.service.MailApiService;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.trade.service.DepositService;
import com.bitkrx.api.trade.vo.*;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.core.util.HttpComLib;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitkrex.coin.provider.wallet.SimpleWalletRpcProvider;
import com.bitkrx.api.trade.dao.DepositDAO;
import com.bitkrx.api.trade.dao.WithdrowDAO;
import com.bitkrx.api.trade.service.WithdrowService;

import javax.servlet.http.HttpServletRequest;

@Service
public class WithdrowServiceImpl implements WithdrowService{

	@Autowired
	WithdrowDAO withDAO;
	
	@Autowired
	DepositDAO depoDAO;

	@Autowired
    CoinInfoService coinInfoService;

    @Autowired
    SmsApiService smsApiService;

    @Autowired
    MailApiService mailService;

	public List<WithFeeVO> getWithFee() throws Exception {
		return withDAO.getWithFee();
	}
	
	public String withdrowCash(WithdrowVO vo) throws Exception {
		//return withDAO.withdrowCash(vo);

        String wdrReqCode = withDAO.cashOutKey();
        vo.setWdrReqCode(wdrReqCode);

        withDAO.withdrowCash2(vo);

        List list = (List) vo.getRESULT();
        Map map = (Map) list.get(0);
        String rtnCd = (String) map.get("RTN_CD");
        if(rtnCd != null && rtnCd.equals("1")) {
            return wdrReqCode;
        }

        return "";
	}
	
	public List<WithListVO> getWithList(WithdrowVO vo) throws Exception {
		return withDAO.getWithList(vo);
	}

	public WithdrowVO withdrowCoin(HttpServletRequest request , WithdrowVO vo) throws Exception {
		
		String realAmt = vo.getRealSendAmt();
		
		SimpleWalletRpcProvider rpc = null;
		
		String json = "";
        try {





            //2018-10-01 코인 코어 공통화 작업 추가부분
            CoinCoreInfoListVO cinfoVO = new CoinCoreInfoListVO();
            cinfoVO.setCnKndCd(vo.getCnKndCd());

            //2019-06-07 양길호 추가 헤코코인 내부송금 오류(원인 50171052 테이블에 해코블록체인 정보가 없음) 예외처리
            if("CMMC10000000001".equals(vo.getCnKndCd())){
                cinfoVO.setIsInternal("Y");
            } else {
                cinfoVO = coinInfoService.selectCoinCoreInfo(cinfoVO);
            }

            String passwd = "";
            String cryphash = "";
            if(cinfoVO != null) {

                try {

                    if(cinfoVO.getIsInternal().equals("Y")) {
                        System.out.println("내부송금 적용 코인");
                        WithdrowVO wvo = withDAO.getWletUserEmail(vo);//보내는 주소가 내부주소인지 확인



                        System.out.println(cinfoVO.getCnKndNm() + " 주소(공통화) : vo.getWdrWletAdr() : " + vo.getWdrWletAdr());

                        /*자기 자신에게 송금한 경우 실패처리*/
                        if(wvo != null){
                            if(wvo.getUserEmail().equals(vo.getUserEmail())){
                                vo.setResultCode("-5");
                                System.out.println("코인전송실패!!, 실패사유 : 자기자신에게 송금");
                                //실패시 코드
                                vo.setStatus("CMMC00000000446");
                                vo.setFailRsn("-5");
                                vo.setFailLog("self send");
                                vo = withDAO.PR_COIN_OUT_RTN(vo);
                                return vo;
                            }
                        }


                        if (wvo != null && !"".equals(wvo.getUserEmail())) {//내부 주소인 경우

                            DepositVO dvo = new DepositVO();
                            dvo.setUserEmail(wvo.getUserEmail());
                            dvo.setDealNo(String.valueOf(new Date().getTime()));
                            dvo.setRegIp(vo.getRegIp());
                            dvo.setCnAmt(Double.parseDouble(realAmt));

                            dvo.setDptKndCd("CMMC00000000014");
                            dvo.setSndWletAdr(vo.getWdrWletAdr());
                            dvo.setCurcyCd(vo.getCnKndCd());
                            depoDAO.INSUPT30171030(dvo);//입금진행 INS
                            dvo.setDptKndCd("CMMC00000000283");//(입금확정)
                            DepositVO dvo2 = depoDAO.getCnDptCode(dvo);
                            dvo.setCnDptCode(dvo2.getCnDptCode());
                            depoDAO.INSUPT30171030(dvo);//입금완료 UPT
                            depoDAO.INS10171028(dvo);//실제코인 INS

                            vo.setDealNo(dvo.getDealNo());
                            vo.setFeeRealAmt("0");
                            vo.setResultCode("000");
                            vo.setStatus("CMMC00000000099");
                            vo = withDAO.PR_COIN_OUT_RTN(vo);
                            withDAO.uptInGubun(dvo2.getCnDptCode());
                            System.out.println(vo.getWdrWletAdr() + " 주소로 내부 송금 성공 (공통화)");

                            /*내부송금 메일 푸시 전송*/
                            mailService.depositSendMail(request , dvo);
                            smsApiService.depositSendPush(request , dvo);
                            /*내부송금 메일 푸시 전송*/

                            vo.setFeeRealAmt("0");
                            withDAO.uptRealFee(vo);
                            return vo;

                        }

                        System.out.println("외부 송금 주소");

                    } else {
                        System.out.println("내부송금 미적용 코인 ");
                    }

//                    if(cinfoVO.getCnKndCd().equals("CMMC00000001467")){
//                        vo.setResultCode("-2");
//                        System.out.println("ADMT 토큰이 외부로 나가려 하는 경우...");
//                        //실패시 코드
//                        vo.setStatus("CMMC00000000446");
//                        vo.setFailRsn("-2");
//                        vo.setFailLog("잘못된 지갑주소입니다.");
//                        vo = withDAO.PR_COIN_OUT_RTN(vo);
//                        return vo;
//                    }

                    if(cinfoVO.getCnKndCd().equals("CMMC00000001206")) {
                        passwd = cinfoVO.getMainPass();
                    } else if(!cinfoVO.getMainPass().equals("") && !cinfoVO.getCoinType().equals("06")) {
                        passwd = withDAO.getEthCode(cinfoVO.getMainPass()) + cinfoVO.getMainPass();
                    } else if("06".equals(cinfoVO.getCoinType())){
                        if(cinfoVO.getMainPass().contains(":")){
                            System.out.println("06 타입 코인");
                            passwd = withDAO.getEthCode(cinfoVO.getMainPass().split(":")[0]) + cinfoVO.getMainPass().split(":")[0];
                            cryphash = cinfoVO.getMainPass().split(":")[1];
                            System.out.println("passwd:" + passwd);
                            System.out.println("cryphash:" + cryphash);
                        }
                    }


                    String url = "";
                    if(cinfoVO.getIsToken().equals("Y")) {//토큰일 경우
                        url = cinfoVO.getCoinSendUrl() + "/sendToken";
                    } else if(!cinfoVO.getCoinSendUrl().equals("")) {//코인 전송용 URL이 있을 경우
                        url = cinfoVO.getCoinSendUrl() + "/sendTransaction";
                    } else {
                        url = cinfoVO.getCoinUrl() + "/sendTransaction";
                    }


                    Map<String, Object> map = new HashMap();
                    map.put("passwd", passwd);
                    map.put("from", cinfoVO.getMainAddr());
                    map.put("to", vo.getWdrWletAdr().trim());
                    map.put("amt", realAmt);
                    if(cinfoVO.getIsToken().equals("Y")){
                        map.put("token" , cinfoVO.getCnKndNm());
                    }
                    if(cinfoVO.getCoinType().equals("06")){
                        map.put("crypthash" , cryphash);
                    }
                    map.put("tag", vo.getDestiTag().trim());
                    map.put("exchange", CmmCdConstant.EXCHANGE_NAME);



                    System.out.println(cinfoVO.getCnKndNm() + " 송금(공통화) // url  " + url);
//                    System.out.println(cinfoVO.getCnKndNm() + " 송금(공통화) // passwd  " + passwd);
                    System.out.println(cinfoVO.getCnKndNm() + " 송금(공통화) // from  " + cinfoVO.getMainAddr());
                    System.out.println(cinfoVO.getCnKndNm() + " 송금(공통화) // to  " + vo.getWdrWletAdr().trim());
                    System.out.println(cinfoVO.getCnKndNm() + " 송금(공통화) // amt  " + realAmt);
                    System.out.println(cinfoVO.getCnKndNm() + " 송금(공통화) // tag  " + vo.getDestiTag().trim());
                    json = HttpComLib.httpSendAPI(url, map);
                    System.out.println("(공통화)송금 결과 : " + json);

                    String result = "";
                    String code = "";
                    String message = "";
                    String id = "";
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
                    map.put("coin", cinfoVO.getCnKndNm());
                    map.put("txid", result);
                    map.put("from", cinfoVO.getMainAddr());
                    map.put("to", vo.getWdrWletAdr().trim());
                    map.put("amt", realAmt);
                    map.put("message", message);
                    map.put("core", "api");

                    if ("0".equals(code)) {//성공
                        if(cinfoVO.getCoinType().equals("02") || cinfoVO.getCoinType().equals("04") || cinfoVO.getCoinType().equals("05")) {
                            vo.setStatus("CMMC00000001387");
                        } else {
                            vo.setStatus("CMMC00000000099");
                        }
                        vo.setDealNo(result);
                        vo.setFeeRealAmt("0");
                        vo.setResultCode("000");
                        vo = withDAO.PR_COIN_OUT_RTN(vo);
                        map.put("state", "success");
                    } else if ("-1".equals(code)) {//코어 돈 부족
                        vo.setResultCode("-1");
                        System.out.println("코인전송실패!!, 실패사유 : " + message);
                        //실패시 코드
                        vo.setStatus("CMMC00000000446");
                        vo.setFailRsn("-1");
                        vo.setFailLog(message);
                        vo = withDAO.PR_COIN_OUT_RTN(vo);
                        map.put("state", "error");
                    } else if ("-2".equals(code)) {//주소 틀림
                        vo.setResultCode("-2");
                        System.out.println("코인전송실패!!, 실패사유 : " + message);
                        //실패시 코드
                        vo.setStatus("CMMC00000000446");
                        vo.setFailRsn("-2");
                        vo.setFailLog(message);
                        vo = withDAO.PR_COIN_OUT_RTN(vo);
                        map.put("state", "error");
                    } else {
                        vo.setResultCode("-99");
                        System.out.println("코인전송실패!!, 실패사유 : " + message);
                        //실패시 코드
                        vo.setStatus("CMMC00000000446");
                        vo.setFailRsn("-99");
                        vo.setFailLog(message);
                        map.put("state", "error");
                        //vo = withDAO.PR_COIN_OUT_RTN(vo);
                    }

                    if(cinfoVO.getCnKndNm().toUpperCase().equals("BTC") || cinfoVO.getCnKndNm().toUpperCase().equals("BCH")
                            || cinfoVO.getCnKndNm().toUpperCase().equals("ETH") || cinfoVO.getCnKndNm().toUpperCase().equals("BTG")
                            || cinfoVO.getCnKndNm().toUpperCase().equals("LTC") || cinfoVO.getCnKndNm().toUpperCase().equals("SGC")
                            || cinfoVO.getCnKndNm().toUpperCase().equals("DASH") || cinfoVO.getCnKndNm().toUpperCase().equals("QTUM")
                            || cinfoVO.getCnKndNm().toUpperCase().equals("XRP") || cinfoVO.getCnKndNm().toUpperCase().equals("HDAC")
                            || cinfoVO.getCnKndNm().toUpperCase().equals("BSV") || cinfoVO.getCnKndNm().toUpperCase().equals("TRX")
                            || cinfoVO.getCnKndNm().toUpperCase().equals("XLM")) {
                        if (!vo.getDestiTag().equals("")) {
                            map.put("to", vo.getWdrWletAdr().trim() + "_" + vo.getDestiTag().trim());
                        }

                        if (!"VSTC".equals(cinfoVO.getCnKndNm())) {
                        System.out.println("코인명:" + cinfoVO.getCnKndNm());
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    vo.setResultCode("-3");
                    System.out.println("코인전송실패!!, 실패사유 : 알수없는 오류");
                    //실패시 코드
                    vo.setStatus("CMMC00000000446");
                    vo.setFailRsn("-3");
                    vo.setFailLog("");

                    HashMap map = new HashMap();
                    map.put("id", "");
                    map.put("exchange", CmmCdConstant.EXCHANGE_NAME);
                    map.put("coin", cinfoVO.getCnKndNm());
                    map.put("txid", "");
                    map.put("from", cinfoVO.getMainAddr());
                    map.put("to", vo.getWdrWletAdr().trim());
                    map.put("amt", realAmt);
                    map.put("state", "error");
                    map.put("message", "error");
                    map.put("core", "api");

                    if(cinfoVO.getCnKndNm().toUpperCase().equals("BTC") || cinfoVO.getCnKndNm().toUpperCase().equals("BCH")
                            || cinfoVO.getCnKndNm().toUpperCase().equals("ETH") || cinfoVO.getCnKndNm().toUpperCase().equals("BTG")
                            || cinfoVO.getCnKndNm().toUpperCase().equals("LTC") || cinfoVO.getCnKndNm().toUpperCase().equals("SGC")
                            || cinfoVO.getCnKndNm().toUpperCase().equals("DASH") || cinfoVO.getCnKndNm().toUpperCase().equals("QTUM")
                            || cinfoVO.getCnKndNm().toUpperCase().equals("XRP") || cinfoVO.getCnKndNm().toUpperCase().equals("HDAC")
                            || cinfoVO.getCnKndNm().toUpperCase().equals("BSV") || cinfoVO.getCnKndNm().toUpperCase().equals("TRX")
                            || cinfoVO.getCnKndNm().toUpperCase().equals("XLM")) {
                        if(!vo.getDestiTag().equals("")) {
                            map.put("to", vo.getWdrWletAdr().trim() + "_" + vo.getDestiTag().trim());
                        }


                        if(!"VSTC".equals(cinfoVO.getCnKndNm())){
                            System.out.println("코인명:" + cinfoVO.getCnKndNm());
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
                    }
                    //vo = withDAO.PR_COIN_OUT_RTN(vo);
                }

                return vo;
            }
            //2018-10-01 코인 코어 공통화 작업 추가부분


			
		} catch (Exception e) {
			e.printStackTrace();
            vo.setResultCode("-3");
            System.out.println("코인전송실패!!, 실패사유 : 알수없는 오류");
            //실패시 코드
            vo.setStatus("CMMC00000000446");
            vo.setFailRsn("-3");
            vo.setFailLog("");
            //vo = withDAO.PR_COIN_OUT_RTN(vo);
		}
		
		return vo;
	}



	public List<WithCoinListVO> getWithCoinList(WithdrowVO vo) throws Exception {
		return withDAO.getWithCoinList(vo);
	}
	
	public MinMaxWithVO getRemPrc(WithdrowVO vo) throws Exception {
		
		return withDAO.getRemPrc(vo);
	}

	public MinMaxWithVO getRemPrc3(WithdrowVO vo) throws Exception{
	    return withDAO.getRemPrc3(vo);
    }

	public MinMaxWithVO getCoinRemPrc(WithdrowVO vo) throws Exception {
		
		return withDAO.getCoinRemPrc(vo);
	}

	@Override
	public String getPosCnAmt(WithdrowVO vo) throws Exception {
		// TODO Auto-generated method stub
		return withDAO.getPosCnAmt(vo);
	}

	@Override
	public String withUserCoin(WithdrowVO vo) throws Exception {
		// TODO Auto-generated method stub
		String wdrReqCode = withDAO.coinOutKey();
		vo.setWdrReqCode(wdrReqCode);
		if(!vo.getDestiTag().equals("")) {
			vo.setWdrWletAdr(vo.getWdrWletAdr() + ":" + vo.getDestiTag());
		}
		withDAO.withdrowCoin2(vo);

		List list = (List) vo.getRESULT();
		Map map = (Map) list.get(0);
		String rtnCd = (String) map.get("RTN_CD");
		if(rtnCd != null && rtnCd.equals("1")) {
			return wdrReqCode;
		}
		
		return "";
	}
	
	//은행리스트 조회
	public List<BankListVO> getBankList(String cmmCd) throws Exception {
		return withDAO.getBankList(cmmCd);
	}
	
	
	public WithCoinListVO selectWithCoin(String wdrReqCode) throws Exception {
		return withDAO.selectWithCoin(wdrReqCode);
	}

    public WithdrowVO getExRateWithdrowPrc(WithdrowVO vo) throws Exception {
	    return withDAO.getExRateWithdrowPrc(vo);
    }

    public String getEthCode(String userEmail) throws Exception {

        return withDAO.getEthCode(userEmail);
    }

    public WithdrowVO selectCashWith(String wdrReqCode) throws Exception {
        return withDAO.selectCashWith(wdrReqCode);
    }

    public int uptCashStatus(WithdrowVO vo) throws Exception {
        return withDAO.uptCashStatus(vo);
    }

    public WithdrowVO PR_COIN_OUT_RTN(WithdrowVO vo) throws Exception {
        return withDAO.PR_COIN_OUT_RTN(vo);
    }

    public String getCard1DayPrc(String userEmail) throws Exception {
        return withDAO.getCard1DayPrc(userEmail);
    }

    public int uptWithCoinStaProgress(String wdrReqCode) throws Exception {
        return withDAO.uptWithCoinStaProgress(wdrReqCode);
    }

    public int uptInGubun(String cnDptCode) throws Exception {
        return withDAO.uptInGubun(cnDptCode);
    }



    public String getCoinLimitYn(String curcyCd) throws Exception{
	    return withDAO.getCoinLimitYn(curcyCd);
    }

    public int getWithdrowUserCheck(WithdrowCheckVO vo) throws Exception{
	    return withDAO.getWithdrowUserCheck(vo);
    }


    public int uptRealFee(WithdrowVO vo) throws Exception {
        return withDAO.uptRealFee(vo);
    }

    public WithCoinListVO selectWithSendCoin(String wdrReqCode) throws Exception {
        return withDAO.selectWithSendCoin(wdrReqCode);
    }

    public WithdrowCheckVO getWithdrowYn(String curcyCd) throws Exception{
	    return withDAO.getWithdrowYn(curcyCd);
    }

    public int getWithdrowUserAutoCheck(WithdrowCheckVO vo) throws Exception{
	    return withDAO.getWithdrowUserAutoCheck(vo);
    }

    public WithdrowVO getCoinAutoYn(String cnKndCd) throws Exception{
	    return withDAO.getCoinAutoYn(cnKndCd);
    }

    public int getAutoUserCheck(WithdrowVO vo) throws Exception{
	    return withDAO.getAutoUserCheck(vo);
    }

    public List<WithdrowVO> getCoinSendingList() throws Exception {
        return withDAO.getCoinSendingList();
    }

    public String getInSndCheck(String cnKndCd) throws Exception{
        return withDAO.getInSndCheck(cnKndCd);
    }

    public WithdrowVO getWletUserEmail(WithdrowVO vo) throws Exception{
        return withDAO.getWletUserEmail(vo);
    }

    public int getAccCheck(KrwWithVO2 vo) throws Exception{
	    return withDAO.getAccCheck(vo);
    }

    public int getWithdrowCheck(KrwWithVO2 vo) throws Exception{
	    return withDAO.getWithdrowCheck(vo);
    }

    public int getFreeWithdrawCnt(WithdrowVO vo) throws Exception{
	    return withDAO.getFreeWithdrawCnt(vo);
    }

    public void InsBankInfo(KrwWithVO vo) throws Exception{
	    withDAO.InsBankInfo(vo);
    }
}
