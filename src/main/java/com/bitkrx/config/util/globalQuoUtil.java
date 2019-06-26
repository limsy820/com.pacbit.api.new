/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.config.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.bitkrx.config.vo.QuotationVO;
import com.bitkrx.core.util.HttpComLib;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.config.util
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 28.
 */
public class globalQuoUtil {

	
	private HashMap hQuotationInfo = new HashMap(); // 거래소별 bitcoin 시세정보
	private int iQuotationConnCnt = 0; // 거래소 인터페이스 연계회수

	   
	 @SuppressWarnings("unused")
	public HashMap gloabalQuo(QuotationVO vo)throws Exception{         
		 
	      Map map = new HashMap();
	      try {
	         // ModelAndView mv = new ModelAndView();
	         // log.DebugLog("-------------test ----------------");
	         Double USD_KRW = 0.0;
	         Double CNY_KRW = 0.0;
	         Double JPY_KRW = 0.0; 
	         //-----------------------------------------------------------
	         //0. 환율계산
	         //-----------------------------------------------------------
	         {
	            int chk_count  = 0;
	             try {
	                   
	                   String yahoo = HttpComLib.httpSendGetAPI("http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote?format=json");
	                   
	                    DecimalFormat format = new DecimalFormat(".##");
	                    JSONParser jsonParser = new JSONParser();
	                     //JSON데이터를 넣어 JSON Object 로 만들어 준다.
	                     JSONObject jsonObject = (JSONObject) jsonParser.parse(yahoo);                     
	                     JSONObject _jsonObject = (JSONObject) jsonObject.get("list");
	                     JSONArray _jsonList = (JSONArray) _jsonObject.get("resources");
	                     
	                     String KRW ="";
	                     String CNY ="";
	                     String JPY ="";
	                     for(int i=0; i<_jsonList.size(); i++){
	                         //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
	                         JSONObject resourceObject = (JSONObject) _jsonList.get(i);
	                         JSONObject innerObj = (JSONObject) resourceObject.get("resource");
	                         JSONObject fieldinnerObj = (JSONObject) innerObj.get("fields");
	                         String name = (String) fieldinnerObj.get("name");
	                          // log.DebugLog("name:"+fieldinnerObj.get("name"));
	                          // log.DebugLog("price:"+fieldinnerObj.get("price"));
	                         
	                           if ("USD/KRW".equals(StringUtils.checkNull(name, ""))) {
	                               KRW = (String) fieldinnerObj.get("price");
	                               // log.DebugLog("name:"+fieldinnerObj.get("name"));
	                               //    log.DebugLog("price:"+KRW);
	                               chk_count++;
	                          } 
	                           if ("USD/CNY".equals(StringUtils.checkNull(name, ""))) {
	                               CNY = (String) fieldinnerObj.get("price");
	                               // log.DebugLog("name:"+fieldinnerObj.get("name"));
	                               // log.DebugLog("price:"+CNY);
	                                  chk_count++;
	                          }
	                           if ("USD/JPY".equals(StringUtils.checkNull(name, ""))) {
	                               JPY = (String) fieldinnerObj.get("price");
	                               // log.DebugLog("name:"+fieldinnerObj.get("name"));
	                               // log.DebugLog("price:"+JPY);
	                                  chk_count++;
	                          }
	                           if( chk_count >= 3) break;
	                     }
	                   // log.DebugLog("USD >> KRW:"+ Double.valueOf(KRW));
	                   //  log.DebugLog("CNY >> KRW:"+ (Double.valueOf(KRW)/Double.valueOf(CNY)));
	                   // log.DebugLog("JPY >> KRW:"+ (Double.valueOf(KRW)/Double.valueOf(JPY)));
	                   USD_KRW = Double.valueOf(KRW);
	                  CNY_KRW = (Double.valueOf(KRW)/Double.valueOf(CNY));
	                  JPY_KRW = (Double.valueOf(KRW)/Double.valueOf(JPY));
	                     
	                } catch (Exception e) {
	                   
	                }
	            
	         }
	         JSONParser jsonParser = new JSONParser();
	         List<QuotationVO> quotLst = new ArrayList<QuotationVO>();
	         
//	         QuotationVO qVO1 = new QuotationVO();

	         //-----------------------------------------------------------
	         //1. BITHUMB
	         //-----------------------------------------------------------
//	         log.DebugLog("-------> 1. BITHUMB 시세정보");
//	         BufferedReader bithumb = new BufferedReader(new InputStreamReader
//	                   (ComUtil.retrieveStream("https://api.bithumb.com/public/ticker/ALL")));   
//	          //JSON데이터를 넣어 JSON Object 로 만들어 준다.
//	          JSONObject bithumbObj = (JSONObject) jsonParser.parse(bithumb);
//	          JSONObject bithumbData = (JSONObject) bithumbObj.get("data"); 
//	          JSONObject bithumbBtc = (JSONObject) bithumbData.get("BTC"); 
//	          {
//	                String lastPrice =  (""+bithumbBtc.get("closing_price"));
//	                String vol =  (""+bithumbBtc.get("volume_1day")); 
//	                String.format("%.0f",Double.valueOf(lastPrice));
//	              qVO1.setExchgNm("BITHUMB");
//	              qVO1.setCoinPrc(""+String.format("%.0f",Double.valueOf(lastPrice)));
//	              qVO1.setSvcTranAmt(""+String.format("%.0f",Double.valueOf(vol)));
//	              qVO1.setExchgGap("100");qVO1.setExchgGapPer("3%");
//	              quotLst.add(qVO1);
//	          }
	         //-----------------------------------------------------------
	          //2. BITFINEX (USD 달러)
	         //-----------------------------------------------------------
	         //  log.DebugLog("-------> 2. BITFINEX 시세정보");
	          
//	          bitcoin       ethereum    bitcoin-cash    ripple
	     
	          
	           String bitfinex = null;
	         QuotationVO qVO2 = new QuotationVO();
	         if("ethereum".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != ""){
	            bitfinex = HttpComLib.httpSendGetAPI("https://api.bitfinex.com/v1/pubticker/ETHUSD");
	         }else if("bitcoin-cash".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != ""){
	        	bitfinex = HttpComLib.httpSendGetAPI("https://api.bitfinex.com/v1/pubticker/BCHUSD");
	         }else if("ripple".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != "") {
	        	 bitfinex = HttpComLib.httpSendGetAPI("https://api.bitfinex.com/v1/pubticker/XRPUSD");
	         }else {
	        	 bitfinex = HttpComLib.httpSendGetAPI("https://api.bitfinex.com/v1/pubticker/BTCUSD");
	         }
	         
	         if(bitfinex != null) {
	        	 JSONObject bitfinexObj = (JSONObject) jsonParser.parse(bitfinex);
		         {
		        	 try {
		        		 String lastPrice =  (""+bitfinexObj.get("last_price"));
			                String vol =  (""+bitfinexObj.get("volume")); 
			                qVO2.setExchgNm("BITFINEX");
			                qVO2.setCoinPrc(""+String.format("%.0f",Double.valueOf(lastPrice)*USD_KRW));
			                qVO2.setSvcTranAmt(""+String.format("%.0f",Double.valueOf(vol)));
			                qVO2.setExchgGap("-100");qVO2.setExchgGapPer("-2%");
			               quotLst.add(qVO2);
		        	 } catch (Exception e) {
		        		 
		        	 }
		               
		          }
	         }
	         
	         //-----------------------------------------------------------
	          //3. BITSTAMP (USD 달러)
	         //-----------------------------------------------------------
	         //log.DebugLog("-------> 3. BITSTAMP 시세정보");
	           String bitstamp = null;
	         QuotationVO qVO3 = new QuotationVO();
	         if("ethereum".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != ""){
	        	 bitstamp = HttpComLib.httpSendGetAPI("https://api.bitfinex.com/v1/pubticker/ethusd");
	         }else if("bitcoin-cash".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != ""){
	        	 bitstamp = null;
	         }else if("ripple".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != "") {
	        	 bitstamp = HttpComLib.httpSendGetAPI("https://www.bitstamp.net/api/v2/ticker/xrpusd");
	         }else {
	        	 bitstamp = HttpComLib.httpSendGetAPI("https://www.bitstamp.net/api/v2/ticker/btcusd");
	         }
	         
	         if(bitstamp != null) { //bitstamp 거래소는 비트코인 캐쉬에 대한 값을 주지ㅣ않는다
	            JSONObject bitstampObj = (JSONObject) jsonParser.parse(bitstamp);
	            {   
	               String lastPrice = null;
	                   
	                  lastPrice =  (""+bitstampObj.get("last")); //비트코인일때 api에서 주는 이름
	                   if("null".equals(lastPrice)) {
	                      lastPrice = (""+bitstampObj.get("last_price")); //이더리움일때 api에서 주는 이름
	                   }
	                   String vol =  (""+bitstampObj.get("volume")); 
	                   qVO3.setExchgNm("BITSTAMP");
	                   qVO3.setCoinPrc(""+String.format("%.0f",Double.valueOf(lastPrice)*USD_KRW));
	                   qVO3.setSvcTranAmt(""+String.format("%.0f",Double.valueOf(vol)));
	                   qVO3.setExchgGap("-500");qVO3.setExchgGapPer("-3.1%");
	                  quotLst.add(qVO3);
	             }
	         }

	         //-----------------------------------------------------------
	          //4. OKCOIN (USD 달러)
	         //-----------------------------------------------------------
	         // log.DebugLog("-------> 4. OKCOIN 시세정보");
	          String okcoin = null;
	         QuotationVO qVO4 = new QuotationVO();
	         
	         if("ethereum".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != ""){
	            okcoin = HttpComLib.httpSendGetAPI("https://www.okcoin.com/api/v1/ticker.do?symbol=eth_usd");
	         }else if("bitcoin-cash".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != ""){
	            okcoin = HttpComLib.httpSendGetAPI("https://www.okcoin.com/api/v1/ticker.do?symbol=bch_usd");
	         }else if("ripple".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != "") {
	            okcoin = null;
	         }else {
	            okcoin = HttpComLib.httpSendGetAPI("https://www.okcoin.com/api/v1/ticker.do?symbol=btc_usd");
	         }
	         
	         if (okcoin != null) {
	            JSONObject okcoinObj = (JSONObject) jsonParser.parse(okcoin);
	            JSONObject okcoinObj2 = (JSONObject) okcoinObj.get("ticker"); 
	            {
	                   String lastPrice =  (""+okcoinObj2.get("last"));
	                   String vol =  (""+okcoinObj2.get("vol")); 
	                   qVO4.setExchgNm("OKCOIN");
	                   qVO4.setCoinPrc(lastPrice);
	                   qVO4.setCoinPrc(""+String.format("%.0f",Double.valueOf(lastPrice)*USD_KRW));
	                   qVO4.setSvcTranAmt(""+String.format("%.0f",Double.valueOf(vol))); //여기
	                   qVO3.setExchgGap("50");qVO3.setExchgGapPer("1.2%");
	                   
	   //                qVO4.setExchgGap(""+iQuotationConnCnt); 소장님코드
	   //                iQuotationConnCnt++;
	                  quotLst.add(qVO4);
	             }
	         }
	         

	         hQuotationInfo.put("list", quotLst);
	         
	         if( iQuotationConnCnt > 2000000000){ // int범위 (2147483647) 초과 제한 
	            iQuotationConnCnt = 0;
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      
	   }
	      List<QuotationVO> quotLst = (List<QuotationVO>)hQuotationInfo.get("list");
	      //-----------------------------------------------------
	      // 초기데이터가 없을경우 보여줄 내용들
	      //-----------------------------------------------------
	      if(quotLst == null || quotLst.size() == 0){
	         // 초기값 세팅
	         quotLst = new ArrayList<QuotationVO>();
//	         QuotationVO qVO1 = new QuotationVO(); qVO1.setExchgNm("BITHUMB");  qVO1.setCoinPrc("-");  qVO1.setSvcTranAmt("-"); qVO1.setExchgGap("100");qVO1.setExchgGapPer("3%"); quotLst.add(qVO1);
	         QuotationVO qVO2 = new QuotationVO(); qVO2.setExchgNm("BITFINEX"); qVO2.setCoinPrc("-");  qVO2.setSvcTranAmt("-"); qVO2.setExchgGap("200");qVO2.setExchgGapPer("2%"); quotLst.add(qVO2);
	         QuotationVO qVO3 = new QuotationVO(); qVO3.setExchgNm("BITSTAMP"); qVO3.setCoinPrc("-");  qVO3.setSvcTranAmt("-"); qVO3.setExchgGap("-300");qVO3.setExchgGapPer("-3%"); quotLst.add(qVO3);
	         QuotationVO qVO4 = new QuotationVO(); qVO4.setExchgNm("OKCOIN");   qVO4.setCoinPrc("-");  qVO4.setSvcTranAmt("-"); qVO4.setExchgGap("400");qVO4.setExchgGapPer("4%"); quotLst.add(qVO4);
	         
	      }else if("bitcoin-cash".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != ""){
	         QuotationVO qVO3 = new QuotationVO(); qVO3.setExchgNm("BITSTAMP"); qVO3.setCoinPrc("-");  
	         qVO3.setSvcTranAmt("-"); qVO3.setExchgGap("3000");qVO3.setExchgGapPer("5%");   quotLst.add(qVO3);
	      }else if("ripple".equals(vo.getCoin_id()) && vo.getCoin_id() != null && vo.getCoin_id() != ""){
	         QuotationVO qVO4 = new QuotationVO(); qVO4.setExchgNm("OKCOIN");   qVO4.setCoinPrc("-"); 
	         qVO4.setSvcTranAmt("-"); qVO4.setExchgGap("-7");qVO4.setExchgGapPer("-5.2%");   quotLst.add(qVO4);
	      }
	      
	      hQuotationInfo.put("list", quotLst);
	      
	      return  hQuotationInfo;
	   }

}
