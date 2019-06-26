package com.bitkrx.config.util;

import com.bitkrx.core.util.HttpComLib;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpbitUtil {

    private UpbitUtil() {};

    private static UpbitUtil cUtil = null;

    public synchronized static UpbitUtil getinstance() throws Exception {
        if(cUtil == null ){
            cUtil = new UpbitUtil();
            getJsonCoinPriceList();
        }
        return cUtil;
    }

    private static String url = "https://api.upbit.com/v1/ticker/?";

    private static Date date = null;

    private static Map<String, String> map = null;

    public String getCoinPrice(String coin, String currency) throws Exception {

        if(date != null && map != null) {
            Date nowDate = new Date();
            long diff = nowDate.getTime() - date.getTime();
            if(diff / 1000 < 300) {
                return map.get(currency + "-" + coin);
            } else {
                date = nowDate;
                getJsonCoinPriceList();
                return map.get(currency + "-" + coin);
            }
        } else {
            date = new Date();
            getJsonCoinPriceList();
            return map.get(currency + "-" + coin);
        }

    }

    public static void getJsonCoinPriceList() throws Exception {

        map = new HashMap<String, String>();
        String coinPrice = "";
        String[] currencys = {"KRW", "BTC", "ETH", "USDT"};

        for(String currency : currencys) {
            try {
                String[] coins = {"BTC","ETH","BCH","XRP","QTUM","BTG","DASH","LTC","BSV","TRX","XLM","WAVES","POLY","OMG","ICX","XEM"};
                for (String coin : coins) {
                    if (!coin.equals(currency)) {
                        String json = HttpComLib.httpSendGetAPI(url + "markets=" + currency + "-" + coin);
                        System.out.println("json : " + json);
                        if (!json.contains("error")) {
                            JSONParser jsonParser = new JSONParser();
                            JSONArray array = (JSONArray) jsonParser.parse(json);
                            JSONObject job = (JSONObject) array.get(0);
                            System.out.println("currency : " + currency + "// coin : " + coin + " // trade_price : " + String.valueOf(job.get("trade_price")));
                            map.put(currency + "-" + coin, String.valueOf(job.get("trade_price")));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
