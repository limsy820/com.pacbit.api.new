package com.bitkrx.api.common.control;

import com.bitkrx.api.auth.vo.SubmitCertVO;
import com.bitkrx.api.common.service.impl.CoinInfoServiceImpl;
import com.bitkrx.api.common.vo.*;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.trade.service.WithdrowService;
import com.bitkrx.api.trade.vo.CmeTradeResVO;
import com.bitkrx.api.trade.vo.RecCoinViewVO;
import com.bitkrx.api.trade.vo.WithdrowVO;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.util.ComUtil;
import com.bitkrx.config.util.SeedXUtil;
import com.bitkrx.config.util.StringUtils;
import com.google.gson.JsonObject;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import com.bitkrx.api.main.vo.CmeUserAssetReqVO;
import com.bitkrx.api.main.vo.CmeUserAssetResVO;



@Controller
@RequestMapping(value="/bt")
public class CoinInfoController extends CmeDefaultExtendController {

    @Autowired
    CoinInfoServiceImpl coinInfoService;

    @Autowired
    UserService userService;

    @Autowired
    WithdrowService withdrowService;

    @Autowired
    TradeService tradeService;

    @RequestMapping(value="/getCoinInfo.dm")
    public @ResponseBody
    CmeResultVO getCoinInfo(CoinInfoVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();

        CoinInfoVO result = coinInfoService.getCoinInfo(vo);
        resultVO.setData(result);
        resultVO.setResultStrCode("000");
        return resultVO;
    }

    @RequestMapping(value = "/getCoinDailyPrc.dm")
    public @ResponseBody
    CmeResultVO getCoinDailyPrc(CoinDailyPrcVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();

        vo.setFirstIndex((vo.getPageIndex() - 1) * 10);
        vo.setRecordCountPerPage(vo.getPageUnit());

        List<CoinDailyPrcVO> list = null;
        int listCnt = 0;

        Method method = getMarketMethod(coinInfoService, "getCoinDailyPrc", vo.getMkState(), vo);
        list = (List<CoinDailyPrcVO>) method.invoke(coinInfoService, vo);
        method = getMarketMethod(coinInfoService, "getCoinDailyPrcCnt", vo.getMkState(), vo);
        listCnt = (int) method.invoke(coinInfoService, vo);

        /*if("ETH".equals(vo.getMkState())) {
            list = coinInfoService.getCoinDailyPrcETH(vo);
            listCnt = coinInfoService.getCoinDailyPrcCntETH(vo);
        } else {
            list = coinInfoService.getCoinDailyPrc(vo);
            listCnt = coinInfoService.getCoinDailyPrcCnt(vo);
        }*/

        map.put("list", list);
        map.put("listCnt", listCnt);
        map.put("pageSize", (listCnt -1 ) / 10 + 1);
        map.put("pageIndex", vo.getPageIndex());
        map.put("pageUnit", vo.getPageUnit());

        resultVO.setData(map);
        resultVO.setResultMsg("코인 일별 시세 조회 성공");
        resultVO.setResultStrCode("000");

        return resultVO;
    }

    @RequestMapping(value = "/getCoinMinMaxInfo.dm")
    public @ResponseBody
    CmeResultVO getCoinMinMaxInfo(CoinMinMaxVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //System.out.println("getCoinMinMaxInfo 시작:" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));

        CmeResultVO resultVO = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();
        //System.out.println("--------------------------------------------------------");
        //System.out.println("KRW getCOin  시작:" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));
        map.put("list", coinInfoService.getCoinMinMaxInfo());
        //System.out.println("KRW getCOin  종료:" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));

        //System.out.println("--------------------------------------------------------");
        //System.out.println("ETH 마켓 시작:" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));
        MarketFeeVO mkETH = coinInfoService.getETHFee();
        //System.out.println("ETH 마켓 종료:" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));
        //System.out.println("--------------------------------------------------------");
        //System.out.println("KRW 마켓 시작:" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));
        MarketFeeVO mkKRW = coinInfoService.getKRWFee();
        //System.out.println("KRW 마켓 종료:" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));
        //System.out.println("--------------------------------------------------------");
        //System.out.println("USDT 마켓 시작:" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));
        MarketFeeVO mkUSDT = coinInfoService.getUSDTFee();
        //System.out.println("USDT 마켓 종료:" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));
        //System.out.println("--------------------------------------------------------");
        //System.out.println("BTC 마켓 시작:" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));
        MarketFeeVO mkBTC = coinInfoService.getBTCFee();
        //System.out.println("BTC 마켓 종료:" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));
        //System.out.println("--------------------------------------------------------");


        map.put("mkETH" , mkETH.getCnPhsFee());
        map.put("mkKRW" , mkKRW.getCnPhsFee());
        map.put("mkUSDT", mkUSDT.getCnPhsFee());
        map.put("mkBTC", mkBTC.getCnPhsFee());

        resultVO.setData(map);
        resultVO.setResultMsg("코인별 한도 조회 성공");
        resultVO.setResultStrCode("000");


        //System.out.println("getCoinMinMaxInfo 종료:" + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()));
        return resultVO;
    }

    @RequestMapping(value = "/getCoinPayInfo.dm")
    public @ResponseBody
    CmeResultVO getCoinPayInfo(CoinPayVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", coinInfoService.getCoinPayInfo(vo));
        resultVO.setData(map);
        resultVO.setResultMsg("코인별 10$ 결제 금액");
        resultVO.setResultStrCode("000");

        return resultVO;
    }


    @RequestMapping(value = "/getMarketCapInfo.dm")
    public @ResponseBody
    CmeResultVO getMarketCapInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", coinInfoService.getMarketCapInfo());
        resultVO.setData(map);
        resultVO.setResultMsg("마켓캡 정보 조회");
        resultVO.setResultStrCode("000");

        return resultVO;
    }


    @RequestMapping(value="/getExchangeRate.dm")
    public @ResponseBody
    CmeResultVO getExchangeRateControl(HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();

        resultVO.setData(getExchangeRate());
        resultVO.setResultStrCode("000");
        return resultVO;
    }

    //사용가능 코인종류
    @RequestMapping(value="/getUseOkCoin.dm")
    public @ResponseBody
    Map<String, Object> getUseOkCoin(HttpServletRequest request, HttpServletResponse response) throws Exception{

        insParamLog(request);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultStrCode", "000");

        List<CoinUseVO> list = coinInfoService.getUseOkCoin();
        resultMap.put("list", list);
        return resultMap;

    }

    //코인잔액조회
    @RequestMapping(value="/getCoinBalance.dm")
    public @ResponseBody
    CoinBalanceVO getCoinBalanceList(CmeUserAssetReqVO vo, HttpServletRequest request , HttpServletResponse response) throws Exception{

        insParamLog(request);

        CoinBalanceVO resultVO = new CoinBalanceVO();


        if(vo.getUserEmail() == null || "".equals(vo.getUserEmail())){
            resultVO.setResultStrCode("999");
            return resultVO;
        }else if(vo.getCurcyCd() == null || "".equals(vo.getCurcyCd())){
            resultVO.setResultStrCode("998");
            return resultVO;
        }

        resultVO = coinInfoService.getCoinBalanceList(vo);
        if(resultVO != null) {
            resultVO.setResultStrCode("000");
        } else {
            resultVO.setResultStrCode("997");
        }
        return resultVO;

    }

    //현금잔액조회
    @RequestMapping(value="/getCashBalance.dm")
    public @ResponseBody
    CashBalanceVO getCashBalanceList(CmeUserAssetReqVO vo, HttpServletRequest request , HttpServletResponse response) throws Exception{

        insParamLog(request);

        CashBalanceVO resultVO = new CashBalanceVO();

        if(vo.getCardNum() == null || "".equals(vo.getCardNum())){
            resultVO.setResultStrCode("999");
            return resultVO;
        }

        SeedXUtil seedXUtil = SeedXUtil.getinstance();

        String cardNum = seedXUtil.SeedDecrypt(vo.getCardNum());

        vo.setCardNum(cardNum);

        String userEmail = coinInfoService.getUserEmail(vo.getCardNum());

        if(userEmail == null) {
            resultVO.setResultStrCode("998");
            return resultVO;
        }

        vo.setUserEmail(userEmail);
        String krwUseYnCheck = userService.krwUseYnCheck(vo.getUserEmail());
        resultVO = coinInfoService.getCashBalanceList(vo);



        if(resultVO != null) {
            resultVO.setResultStrCode("000");
        } else {
            resultVO.setResultStrCode("997");
        }
        return resultVO;
    }

    @RequestMapping(value = "/getMkCoinList.dm")
    public @ResponseBody CmeResultVO getMkCoinList(String curcyCd, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, String>> list = coinInfoService.getMkCoinList(curcyCd);

        map.put("list", list);
        resultVO.setData(map);
        resultVO.setResultMsg("마켓별 코인 조회");
        resultVO.setResultStrCode("000");

        return resultVO;
    }


    @RequestMapping(value = "/getMarketTradeCheck.dm")
    public @ResponseBody CmeResultVO getMarketTradeCheck(HttpServletRequest request , HttpServletResponse response) throws Exception{

        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        LinkedHashMap<String , Object> linkMap = new LinkedHashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        MarketTradeVO vo = new MarketTradeVO();

        List<MarketTradeVO> marketInfoList = (List<MarketTradeVO>)coinInfoService.getMarketInfo();

        List<MarketTradeVO> list = null;

        Map<String , Object> KRWMap = new HashMap<String , Object>();
        Map<String , Object> BTCMap = new HashMap<String , Object>();
        Map<String , Object> ETHMap = new HashMap<String , Object>();
        Map<String , Object> USDTMap = new HashMap<String , Object>();

        ArrayList<MarketTradeVO> kRWList = (ArrayList<MarketTradeVO>) coinInfoService.getMarketTradeCheck("CMMC00000000204");
        ArrayList<MarketTradeVO> BTCList = (ArrayList<MarketTradeVO>) coinInfoService.getMarketTradeCheck("CMMC00000000205");
        ArrayList<MarketTradeVO> ETHList = (ArrayList<MarketTradeVO>) coinInfoService.getMarketTradeCheck("CMMC00000000206");
        ArrayList<MarketTradeVO> USDTList = (ArrayList<MarketTradeVO>) coinInfoService.getMarketTradeCheck("CMMC00000001086");

        ArrayList<Map<String , Object>> alist = new ArrayList<Map<String, Object>>();

        KRWMap.put("marketCd" , "CMMC00000000204");
        BTCMap.put("marketCd" , "CMMC00000000205");
        ETHMap.put("marketCd" , "CMMC00000000206");
        USDTMap.put("marketCd" , "CMMC00000001086");

        KRWMap.put("marketNm" , tradeService.getCmmNm());
        BTCMap.put("marketNm" , "BTC");
        ETHMap.put("marketNm" , "ETH");
        USDTMap.put("marketNm" , "USDT");

        KRWMap.put("list" , kRWList);
        BTCMap.put("list" , BTCList);
        ETHMap.put("list" , ETHList);
        USDTMap.put("list" , USDTList);

        alist.add(KRWMap);
        alist.add(BTCMap);
        alist.add(ETHMap);
        alist.add(USDTMap);

        /*for(int i = 0; i < marketInfoList.size(); i++){
            list = (List<MarketTradeVO>)coinInfoService.getMarketTradeCheck(marketInfoList.get(i).getCurcyCd());
            linkMap.put(marketInfoList.get(i).getCurcyCd() , list);
        }*/

        resultMap.put("list" , alist);
        resultVO.setData(resultMap);
        return resultVO;
    }


    @RequestMapping(value = "/getCoinNote.dm")
    public @ResponseBody CmeResultVO getCoinNote(CoinInfoVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String, Object>();
        resultMap.put("failCd" , "");
        resultVO.setResultStrCode("000");

        if("".equals(vo.getCnKndCd())){
            resultMap.put("failCd" , "999");
            resultVO.setResultMsg("코인코드가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getLangCd())){
            resultMap.put("failCd" , "998");
            resultVO.setResultMsg("언어구분값이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getUserEmail())){
            resultMap.put("failCd" , "997");
            resultVO.setResultMsg("사용자 이메일 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        if("id".equals(vo.getLangCd())){
            vo.setLangCd("in");
        }else if("ru".equals(vo.getLangCd())){
            vo.setLangCd("kg");
        }

        String note = coinInfoService.getCoinNote(vo);      // 코인 송금시 주의사항
        if("".equals(note) || note == null){
            resultMap.put("note" ,"");
        }else {
            resultMap.put("note" , note);
        }

        WithdrowVO wvo = new WithdrowVO();
        wvo.setUserEmail(vo.getUserEmail());
        wvo.setCnKndCd(vo.getCnKndCd());
        String amt = withdrowService.getPosCnAmt(wvo);

        resultMap.put("amt" , amt);
        resultVO.setData(resultMap);
        return resultVO;
    }

    public String getFileLogo(String fileNm) {
        String fileNum = "";

        SubmitCertVO sbvo = new SubmitCertVO();
        sbvo.setCertCode(fileNm);
        try {
            fileNum = coinInfoService.getFile_sn(sbvo);
        } catch (Exception e){
            System.out.println("로고가져오다 오류");
        }

        return fileNum;
    }
}
