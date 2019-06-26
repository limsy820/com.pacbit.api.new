package com.bitkrx.config.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.bitkrx.core.util.HttpComLib;

public class CoinmarketcapUtil {

    private CoinmarketcapUtil() {};

    private static CoinmarketcapUtil cUtil = null;

    public synchronized static CoinmarketcapUtil getinstance() throws Exception {
        if(cUtil == null ){
            cUtil = new CoinmarketcapUtil();
            getJsonCoinPriceList("USD");
        }
        return cUtil;
    }

    private static String url = "https://api.coinmarketcap.com/v2/ticker/?";

    private static Date date = null;

    private static Map<String, String> map = null;

    public String getCoinPrice(String coin, String currency) throws Exception {

        if(date != null && map != null) {
            Date nowDate = new Date();
            long diff = nowDate.getTime() - date.getTime();
            if(diff / 1000 < 60) {
                return map.get(coin);
            } else {
                date = nowDate;
                getJsonCoinPriceList(currency);
                return map.get(coin);
            }
        } else {
            date = new Date();
            getJsonCoinPriceList(currency);
            return map.get(coin);
        }

    }

    public static void getJsonCoinPriceList(String currency) throws Exception {

        map = new HashMap<String, String>();
        String coinPrice = "";

        try {
            String json = HttpComLib.httpSendGetAPI(url + "convert=" + currency + "&limit=50&structure=array");

            JSONParser jsonParser = new JSONParser();
            JSONObject job = (JSONObject) jsonParser.parse(json);
            JSONArray data = (JSONArray) job.get("data");
            for(int i = 0 ; i < data.size(); i++ ) {
                JSONObject j = (JSONObject) data.get(i);
                String symbol = (String) j.get("symbol");
                j = (JSONObject) j.get("quotes");
                j = (JSONObject) j.get(currency);
                double price = (double) j.get("price");
//                DecimalFormat form = new DecimalFormat("#.###");
//                map.put(symbol, form.format(price));
                map.put(symbol, String.valueOf(price));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String getJsonCoinPrice(String coin, String currency) throws Exception {

        //System.out.println("getJsonCoinPrice start=======================");

        String coinPrice = "";

        try {
            String json = HttpComLib.httpSendGetAPI(url + "convert=" + currency + "&limit=50&structure=array");

            JSONParser jsonParser = new JSONParser();
            JSONObject job = (JSONObject) jsonParser.parse(json);
            JSONArray data = (JSONArray) job.get("data");

            //System.out.println(data.toString());

            for(int i = 0 ; i < data.size(); i++ ) {
                JSONObject j = (JSONObject) data.get(i);
                String symbol = (String) j.get("symbol");

                //System.out.println("symbol : " + symbol);
                if(coin.equals(symbol)) {
                    j = (JSONObject) j.get("quotes");
                    j = (JSONObject) j.get(currency);
                    double price = (double) j.get("price");

                    System.out.println("BTC price : " + price);

                    //DecimalFormat form = new DecimalFormat("#.###");

//                    BigDecimal bigDecimal = new BigDecimal(price);
//                    double prc = (double) price;
                    //System.out.println("BTC price (double) : " + bigDecimal.toString());
//                    bigDecimal = new BigDecimal(prc);
                    //System.out.println(coin + " price (int) : " + bigDecimal.toString());

                    coinPrice = String.valueOf(price);
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("getJsonCoinPrice end=======================");
        return coinPrice;
    }
}
