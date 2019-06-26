package com.bitkrx.api.scheduler.control;

import com.bitkrx.api.common.service.CoinInfoService;
import com.bitkrx.config.control.CmeDefaultExtendController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DBConnectScheduler extends CmeDefaultExtendController {

    @Autowired
    CoinInfoService coinInfoService;

    //@Scheduled(cron="0 0/10 * * * *")
    public void dbConnect() throws Exception{

        System.out.println("---------------------DB Connect Start!------------------------------");
        String KRWConnect = coinInfoService.selectKRWdual();
        String ETHConnect = coinInfoService.selectETHdual();
        String BTCConnect = coinInfoService.selectBTCdual();
        String USDTConnect = coinInfoService.selectUSDTdual();

        System.out.println("KRW Connect :" + KRWConnect);
        System.out.println("ETH Connect :" + ETHConnect);
        System.out.println("BTC Connect :" + BTCConnect);
        System.out.println("USDT Connect :" + USDTConnect);
        System.out.println("---------------------DB Connect End!------------------------------");
    }
}
