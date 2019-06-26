/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 *//*
package com.bitkrx.config.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bitkrx.api.trade.service.DepositService;
import com.bitkrx.api.trade.vo.DepositVO;
import com.bitkrx.config.CmeResultVO;

import cme.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import cme.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.Transaction;

*//**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.config.util
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2018. 1. 2.
 *//*
public class DepositUtil {

	private DepositUtil() {};
	
	private static DepositUtil dUtil = null;
    
    public synchronized static DepositUtil getinstance(){
        if(dUtil == null ){
        	dUtil = new DepositUtil();
        }
        return dUtil;
    }
    
    @Autowired
    DepositService depositSvc;
    
    public CmeResultVO pendingCoin(DepositVO vo) throws Exception {
    	
    	BitcoinJSONRPCClient client = new BitcoinJSONRPCClient(false);
        List<Transaction> list= client.listTransactions("bitkrex");
        Map<String, Object> map = new HashMap<String, Object>();
        
        for(Transaction tr : list) {
           if(tr.category().equals("receive")) {
				vo.setUserEmail("bitkrex");
				vo.setDealNo(tr.txId());// 입금진행중 건 수
				DepositVO dvo = depositSvc.getCnDptCode(vo);
				int confirm = tr.confirmations();

				vo.setCurcyCd("CMMC00000000205");//비트코인입금
				vo.setRegIp("127.0.0.1");
				double cnAmt = 0;
				try {
					System.out.println("====>" + tr.amount());
					cnAmt = tr.amount();
					vo.setCnAmt(String.valueOf(cnAmt));
				} catch (Exception e) {
					vo.setCnAmt("0");
				}
				if (dvo.getCnDptCode() == null || dvo.getCnDptCode().equals("")) {// 입금진행중 건수가 없으면
					vo.setDprKndCd("CMMC00000000014");
					vo.setSndWletAdr(tr.account());
					depositSvc.INSUPT30171030(vo);
					dvo = depositSvc.getCnDptCode(vo);
				} 
				if (confirm > 6) { //컨펌 수가 6이상( 입금확정)	
					vo.setDprKndCd("CMMC00000000283");
					vo.setCnDptCode(dvo.getCnDptCode());
					depositSvc.INSUPT30171030(vo);
				}
				

				System.out.println("====>" + tr.txId());
				System.out.println("====>" + tr.account());
				System.out.println("====>" + tr.address());
				System.out.println("====>" + tr.category());

				try {
					System.out.println("====>" + tr.fee());
				} catch (Exception e) {

				}

				try {
					System.out.println("====>" + tr.confirmations());
				} catch (Exception e) {

				}

				System.out.println("====>" + tr.time());
				System.out.println("====>" + tr.timeReceived());
        	   
        	   
				
           }
           

        }
        
        CmeResultVO res = new CmeResultVO();
        res.setData(map);
        
        return res;
        
    }
}
*/