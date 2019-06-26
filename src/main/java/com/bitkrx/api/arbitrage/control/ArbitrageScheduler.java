
/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.arbitrage.control;


import com.bitkrx.config.control.CmeDefaultExtendController;

import com.bitkrx.core.util.HttpComLib;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Component
public class ArbitrageScheduler extends CmeDefaultExtendController {

//  "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=AUTHKEY1234567890&searchdate=20180102&data=AP01";
    String APIURL = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?";
    String AUTHKEY = "iOr0bleaJsR87XGrP4pGk0ZMQzgHwnmT"; //임승연 개인키
    String SDATE = "";
    String DATATYPE = "AP01";

    @Scheduled(cron="10 * * * * *")
    //@Scheduled(cron="0 0 12 * * ?") //매일 낮 12시
    public void amRate() throws Exception {
        exchageRate();
    }

//    @Scheduled(cron="0 0 24 * * ?") //매일 밤 12시
    public void pmRate() throws Exception {
        exchageRate();
    }

    public void exchageRate() throws Exception {

        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMdd");
        SDATE = DateFormat.format(new Date()); // 현재날짜

        JSONParser parser = new JSONParser();
        String str = HttpComLib.httpSendGetAPI(APIURL + "authkey=" + AUTHKEY + "&searchdate=" + SDATE + "&data="+ DATATYPE );
        System.out.println(str);

        JSONObject obj = (JSONObject) parser.parse(str);


    }
}