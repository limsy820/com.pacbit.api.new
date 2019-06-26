/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.trade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bitkrx.api.block.dao.BlockChainDAO;
import com.bitkrx.api.block.service.BlockChainService;
import com.bitkrx.api.block.vo.BlockChainVO;
import com.bitkrx.api.moaCard.vo.MoaCardCoinVO;
import com.bitkrx.api.moaCard.vo.MoaCardReqVO;
import com.bitkrx.api.trade.vo.*;
import com.bitkrx.config.util.StringUtils;
import com.bitkrx.core.util.HttpComLib;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.utils.Convert;

import com.bitkrex.coin.enums.CoinEnum;
import com.bitkrex.coin.provider.wallet.SimpleWalletRpcProvider;
import com.bitkrex.coin.provider.wallet.WalletRpcProviderFactory;
import com.bitkrx.api.auth.dao.LoginDAO;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.trade.dao.DepositDAO;
import com.bitkrx.api.trade.dao.WithdrowDAO;
import com.bitkrx.api.trade.service.DepositService;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.vo.SendInfoVO;


/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.trade.service.impl
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 23.
 */
@Service
public class DepositServiceImpl implements DepositService {

	@Autowired
	DepositDAO depositDAO;

	@Autowired
	LoginDAO loginDAO;

	@Autowired
	WithdrowDAO withDAO;

	@Autowired
	SmsApiService smsApiService;

	@Autowired
    BlockChainService blockChainService;

	/**
	 * @Method Name  : INSUPT30171020
	 * @작성일   : 2017. 11. 23.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :포인트 충전 및 수정
	 * @param vo
	 */
	public void INSUPT30171020(DepositVO vo) throws Exception {
		depositDAO.INSUPT30171020(vo);
	}


	/**
	 * @Method Name  : INSUPT30171030
	 * @작성일   : 2018. 1. 2.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :코인입금대기 및 완료
	 * @param vo
	 * @throws Exception
	 */
	public void INSUPT30171030(DepositVO vo) throws Exception{
		depositDAO.INSUPT30171030(vo);
	}
	/**
	 * @Method Name  : insAccInfo
	 * @작성일   : 2017. 12. 12.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :계좌임시입력
	 * @param vo
	 * @throws Exception
	 */
	public void insAccInfo(DepositVO vo) throws Exception {
		depositDAO.insAccInfo(vo);
	}

	/**
	 * @Method Name  : getCnDepoList
	 * @작성일   : 2017. 12. 29.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 코인입금내역리스트
	 * @param vo
	 * @throws Exception
	 */
	public List<CoinDepositVO> getCnDepoList(DepositVO vo) throws Exception {
		return depositDAO.getCnDepoList(vo);
	}

	/**
	 * @Method Name  : getKrwDepoList
	 * @작성일   : 2017. 12. 29.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 원화입금내역리스트
	 * @param vo
	 * @throws Exception
	 */
	public List<KrwDepositVO> getKrwDepoList(DepositVO vo) throws Exception {
		return depositDAO.getKrwDepoList(vo);
	}

	/**
	 * @Method Name  : getKrwWithList
	 * @작성일   : 2018. 01. 18.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 : 원화출금화면
	 * @param vo
	 * @throws Exception
	 */
	public KrwWithVO getKrwWithdrow(DepositVO vo) throws Exception {
		return depositDAO.getKrwWithdrow(vo);
	}

	/**
	 * @Method Name  : getCnAccList
	 * @작성일   : 2018. 1. 8.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :전자지갑주소 리스트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CnAccListVO> getCnAccList(DepositVO vo) throws Exception {
		return depositDAO.getCnAccList(vo);
	}

	/**
	 * @Method Name  : getUserAccNm
	 * @작성일   : 2017. 12. 29.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :유저 입금자명
	 * @param userEmail
	 * @return
	 * @throws Exception
	 */
	public DepositUserInfoVO getUserAccNm(String userEmail) throws Exception {
		return depositDAO.getUserAccNm(userEmail);
	}

	/**
	 * @Method Name  : getCnAccInfo
	 * @작성일   : 2018. 1. 1.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :사용자 전자지갑주소 가져오기
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<DepositVO> getCnAccInfo(DepositVO vo) throws Exception {
		return depositDAO.getCnAccInfo(vo);
	}

	public DepositVO getCnDptCode(DepositVO vo) throws Exception {
		return depositDAO.getCnDptCode(vo);
	}

	/**
	 * @Method Name  : INS10171028
	 * @작성일   : 2018. 1. 2.
	 * @작성자   :  (주)씨엠이소프트 문영민
	 * @변경이력  :
	 * @Method 설명 :실제코인입금
	 * @param vo
	 * @throws Exception
	 */
	public void INS10171028(DepositVO vo) throws Exception {
		depositDAO.INS10171028(vo);
	}


	public DepoBankVO getUserBankInfo(String userEmail) throws Exception {
		return depositDAO.getUserBankInfo(userEmail);
	}

	public String getDestinationTag() throws Exception {
		return depositDAO.getDestinationTag();
	}


	public CmeResultVO pendingCoin(HttpServletRequest request, DepositVO vo) throws Exception {

		SimpleWalletRpcProvider rpc = null;

		if(vo.getCurcyCd().equals(CmmCdConstant.BTC_CD)) {

			//비트코인 입금확인
			vo.setCurcyNm("BTC");
			rpc = new WalletRpcProviderFactory().get(CoinEnum.BITCOIN);
			//System.out.println("비트코인START");
			bitPending(vo, rpc, request);
			//System.out.println("비트코인END");


		} else if(vo.getCurcyCd().equals(CmmCdConstant.BCH_CD)) {

			//비트코인캐시 입금확인
			vo.setCurcyNm("BCH");
			rpc = new WalletRpcProviderFactory().get(CoinEnum.BITCASH);
			//System.out.println("비트캐시START");
			bitPending(vo, rpc, request);
			//System.out.println("비트캐시END");

		} else if(vo.getCurcyCd().equals(CmmCdConstant.XRP_CD)) {

            //리플 입금확인
            vo.setCurcyNm("XRP");
            rpc = new WalletRpcProviderFactory().get(CoinEnum.RIPPLE);
            //System.out.println("리플START");
            xrpPending(vo, rpc, request);
            //System.out.println("리플END");
        } else if(vo.getCurcyCd().equals(CmmCdConstant.ETH_CD)) {

            //이더리움 입금확인
            vo.setCurcyNm("ETH");
            rpc = new WalletRpcProviderFactory().get(CoinEnum.ETHEREUM);
            ethPendingOld(vo, rpc, request);


        } else if(vo.getCurcyCd().equals(CmmCdConstant.SGC_CD)) {//SGC

            //SGC 입금확인
            vo.setCurcyNm("SGC");
            rpc = new WalletRpcProviderFactory().get(CoinEnum.STARGRAM);
            ethPendingOld(vo, rpc, request);


        } else if(vo.getCurcyCd().equals(CmmCdConstant.BTG_CD)) {
            //비트코인골드 입금확인
            vo.setCurcyNm("BTG");
            rpc = new WalletRpcProviderFactory().get(CoinEnum.BITGOLD);
            bitPending(vo, rpc, request);

        } else if(vo.getCurcyCd().equals(CmmCdConstant.QTUM_CD)) {
            //퀀텀 입금확인
            vo.setCurcyNm("QTUM");
            rpc = new WalletRpcProviderFactory().get(CoinEnum.QTUM);
            bitPending(vo, rpc, request);


        } else if(vo.getCurcyCd().equals(CmmCdConstant.LTC_CD)) {
            //라이트코인 입금확인
            vo.setCurcyNm("LTC");
            rpc = new WalletRpcProviderFactory().get(CoinEnum.LITECOIN);
            bitPending(vo, rpc, request);


        } else if(vo.getCurcyCd().equals(CmmCdConstant.DASH_CD)) {
            //대시 입금확인
            vo.setCurcyNm("DASH");
            rpc = new WalletRpcProviderFactory().get(CoinEnum.DASH);
            bitPending(vo, rpc, request);
        }

		CmeResultVO res = new CmeResultVO();
		return res;

	}


    //비트코인계열
    public void bitPending(DepositVO vo, SimpleWalletRpcProvider rpc, HttpServletRequest request) throws Exception {

        List<DepositVO> devoList = depositDAO.getCnAccInfo(vo);
        if(devoList.size() == 0) {
            return;
        }


      /*  double bal = Double.parseDouble(rpc.getbalance("main"));

        if(bal > 0) {
            rpc.coinMove(vo.getUserEmail(), "main", String.valueOf(bal));
        }
*/


        String json = rpc.getTransjaction(vo.getUserEmail());

        int confirmCnt = 3;
        if(CmmCdConstant.BTG_CD.equals(vo.getCurcyCd())) {
            confirmCnt = 50;
        }

        System.out.println(vo.getCurcyNm() + " json : " + json );

        JSONParser parser = new JSONParser();
        Object obj = parser.parse( json );
        JSONArray jsonArr = (JSONArray) obj;

        if(jsonArr.size() > 0) {

            for(Object o : jsonArr) {

                JSONObject jsonObj = (JSONObject) o;


                //System.out.println("category : " + jsonObj.get("category"));
                //System.out.println("confirmations : " + jsonObj.get("confirmations"));
                //System.out.println("txid : " + jsonObj.get("txid"));
                //System.out.println("amount : " + jsonObj.get("amount"));
                //System.out.println("address : " + jsonObj.get("address"));

                if(jsonObj.get("category").equals("receive")) {//트랜잭션이 receive일 경우

                    double cnAmt = 0D;

                    long confirm = (long) jsonObj.get("confirmations");//컨펌개수를 가져온다
                    String txid = (String) jsonObj.get("txid");//txid를 가져온다
                    vo.setDealNo(txid);
                    DepositVO dvo = getCnDptCode(vo);//이미 입력한 건인지 확인쿼리

                    vo.setRegIp("127.0.0.1");

                    Double doubleAmt = null;
                    Long longAmt = null;
                    if(jsonObj.get("amount").getClass().equals(Long.class)) {
                        longAmt = (Long) jsonObj.get("amount");//개수를 가져온다
                    } else {
                        doubleAmt = (double) jsonObj.get("amount");//개수를 가져온다
                    }


                    if(longAmt != null) {
                        cnAmt = longAmt.doubleValue();
                    } else {
                        cnAmt = doubleAmt;
                    }

                    vo.setCnAmt(cnAmt);


                    if (dvo == null) {// 입금진행중 건수가 없으면

                        vo.setCnDptCode("");
                        vo.setDptKndCd("CMMC00000000014");
                        vo.setSndWletAdr((String) jsonObj.get("address"));
                        INSUPT30171030(vo);//입금진행 INS
                        dvo = getCnDptCode(vo);
                    }
                    if(dvo != null) {

                        if (confirm >= confirmCnt && dvo.getCnDptCode2().equals("")) { //컨펌 수가 3이상( 입금확정)
                            vo.setDptKndCd("CMMC00000000283");
                            vo.setCnDptCode(dvo.getCnDptCode());
                            INSUPT30171030(vo);//입금완료 UPT
                            INS10171028(vo);//실제코인 INS
                            rpc.coinMove(vo.getUserEmail(), "main", String.valueOf(cnAmt));

                            try {
                                /*문자발송*/

                                SendInfoVO svo = new SendInfoVO();
                                /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
                                svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
                                svo.setCnAmt(String.valueOf(cnAmt));
                                svo.setCurcyNm(vo.getCurcyNm());
                                svo.setUserEmail(vo.getUserEmail());
                                smsApiService.smsDepoComp(request, svo);
                                /*문자발송*/

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                }

            }
        }


    }

    //이더리움계열 코인
    public void ethPendingOld(DepositVO vo, SimpleWalletRpcProvider rpc, HttpServletRequest request) throws Exception {

        String cnKndCd = vo.getCurcyCd();
        String url = "";
        double minusVal = 0D;
        if(CmmCdConstant.ETH_CD.equals(cnKndCd)) {
            url = CmmCdConstant.ETH_URL;
            minusVal = 0.007;
        } else if(CmmCdConstant.SGC_CD.equals(cnKndCd)) {
            url = CmmCdConstant.SGC_URL;
            minusVal = 0.005;
        }
        List<DepositVO> devoList = depositDAO.getCnAccInfo(vo);
        String jsonStr = "";

        for(DepositVO devo : devoList) {

            String rval = "";
            if (CmmCdConstant.ETH_CD.equals(cnKndCd)) {
                rval = HttpComLib.httpSendGetAPI("http://10.10.0.121:3100/getBalance/" + devo.getWletAdr());
            } else if (CmmCdConstant.SGC_CD.equals(cnKndCd)) {
                rval = HttpComLib.httpSendGetAPI("http://10.10.0.122:17000/getBalance/" + devo.getWletAdr());
            }

            System.out.println(cnKndCd + " rval : " + rval);

            if(rval != null && !"".equals(rval) && Double.parseDouble(rval) > 0 ) {

                jsonStr = HttpComLib.httpSendGetAPI(url + StringUtils.lowerCase(devo.getWletAdr()) + "/10");

                if(jsonStr.contains("<!DOCTYPE html>")) {
                    continue;
                }

                System.out.println(cnKndCd + " jsonStr : " + jsonStr);

                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(jsonStr);
                JSONArray list = (JSONArray) obj.get("data");

                for(Object arr : list) {
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

                    DepositVO dvo = getCnDptCode(vo);

                    if (dvo == null) {// 입금진행중 건수가 없으면
                        vo.setCnDptCode("");
                        vo.setDptKndCd("CMMC00000000014");
                        vo.setSndWletAdr(txTo);
                        INSUPT30171030(vo);//입금진행 INS
                        dvo = getCnDptCode(vo);

                        vo.setDptKndCd("CMMC00000000283");//( 입금확정)
                        vo.setCnDptCode(dvo.getCnDptCode());
                        INSUPT30171030(vo);//입금완료 UPT
                        INS10171028(vo);//실제코인 INS

                        try {

                            vo.setWletAdr(txTo);
                            vo.setCnKndCd(cnKndCd);
                            vo.setCnAmt(txAmount - minusVal);
                            String password = withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail();
                            int res = insertEthMoveHistory(vo, rpc, password);
                            /*if(res == 0) {
                                insertEthMoveHistory(vo, rpc, vo.getUserEmail());
                            }*/

                        } catch (Exception e) {
                            //System.out.println("코인전송실패!!, 실패사유 : " + e.getMessage());
                            vo.setDealNo("");
                            vo.setSfYn("N");
                            vo.setMemo("알수없는 이유로 실패");
                            depositDAO.insCoinMoveHis(vo);
                        }

                        try {
                            /*문자발송*/
                            SendInfoVO svo = new SendInfoVO();
                            /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
                            svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
                            svo.setCnAmt(String.valueOf(txAmount));
                            svo.setCurcyNm(vo.getCurcyNm());
                            svo.setUserEmail(vo.getUserEmail());
                            smsApiService.smsDepoComp(request, svo);
                            /*문자발송*/
                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

            }

        }



        /*String json = "";

        for(DepositVO devo : devoList) {
            json = rpc.getTransjaction(devo.getWletAdr());

            System.out.println(vo.getCurcyNm() + " : " + json);

            parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse( json );
            JSONArray jsonArr = (JSONArray) jsonObj.get("transaction");;
            if(jsonArr.size() > 0) {
                for(Object o : jsonArr) {
                    JSONObject tempObj = (JSONObject) o;

                    System.out.println("TX_ID : " + tempObj.get("TX_ID"));
                    System.out.println("TX_VALUE : " + tempObj.get("TX_VALUE"));

                    vo.setDealNo(tempObj.get("TX_ID").toString());
                    DepositVO dvo = getCnDptCode(vo);
                    vo.setRegIp("127.0.0.1");


                    double cnAmt = 0;
                    cnAmt = Convert.fromWei(tempObj.get("TX_VALUE").toString(), Convert.Unit.ETHER).doubleValue();

                    System.out.println("cnAmt : " + cnAmt);
                    vo.setCnAmt(cnAmt);

                    if (dvo == null || dvo.getCnDptCode() == null || dvo.getCnDptCode().equals("")) {// 입금진행중 건수가 없으면
                        vo.setCnDptCode("");
                        vo.setDptKndCd("CMMC00000000014");
                        vo.setSndWletAdr(devo.getWletAdr());
                        INSUPT30171030(vo);//입금진행 INS
                        dvo = getCnDptCode(vo);

                        vo.setDptKndCd("CMMC00000000283");//( 입금확정)
                        vo.setCnDptCode(dvo.getCnDptCode());
                        INSUPT30171030(vo);//입금완료 UPT
                        INS10171028(vo);//실제코인 INS

						try {

							vo.setWletAdr(devo.getWletAdr());
							vo.setCnKndCd(cnKndCd);
							vo.setCnAmt(cnAmt - 0.003);
							String password = withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail();
							int res = insertEthMoveHistory(vo, rpc, password);
							if(res == 0) {
								insertEthMoveHistory(vo, rpc, vo.getUserEmail());
							}

						} catch (Exception e) {
							//System.out.println("코인전송실패!!, 실패사유 : " + e.getMessage());
							vo.setDealNo("");
							vo.setSfYn("N");
							vo.setMemo("알수없는 이유로 실패");
							depositDAO.insCoinMoveHis(vo);
						}

                        try {
                            //문자발송

                            SendInfoVO svo = new SendInfoVO();
                            //필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)

                            svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
                            svo.setCnAmt(String.valueOf(cnAmt));
                            svo.setCurcyNm(vo.getCurcyNm());
                            svo.setUserEmail(vo.getUserEmail());
                            smsApiService.smsDepoComp(request, svo);
                            //문자발송

                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }*/

    }


	//이더리움 ( 예전 버전 , DB 조회 하는 버전이었으나 현재 사용 안함)
	public void ethPending(DepositVO vo, BlockChainVO blockVO, HttpServletRequest request) throws Exception {

        SimpleWalletRpcProvider rpc = new WalletRpcProviderFactory().get(CoinEnum.ETHEREUM);

        vo.setDealNo(blockVO.getTxId());
        DepositVO dvo = getCnDptCode(vo);
        vo.setRegIp("127.0.0.1");

        double cnAmt = 0;
        cnAmt = Convert.fromWei(blockVO.getTxValue(), Convert.Unit.ETHER).doubleValue();

        vo.setCnAmt(cnAmt);

        if (dvo == null || dvo.getCnDptCode() == null || dvo.getCnDptCode().equals("")) {// 입금진행중 건수가 없으면
            vo.setCnDptCode("");
            vo.setDptKndCd("CMMC00000000014");
            vo.setSndWletAdr(blockVO.getTxId());
            INSUPT30171030(vo);//입금진행 INS
            dvo = getCnDptCode(vo);

            vo.setDptKndCd("CMMC00000000283");//( 입금확정)
            vo.setCnDptCode(dvo.getCnDptCode());
            INSUPT30171030(vo);//입금완료 UPT
            INS10171028(vo);//실제코인 INS

            try {

                vo.setWletAdr(blockVO.getTxTo());
                vo.setCnKndCd("CMMC00000000206");
                vo.setCnAmt(cnAmt - 0.007);
                String password = withDAO.getEthCode(vo.getUserEmail()) + vo.getUserEmail();
                int res = insertEthMoveHistory(vo, rpc, password);
                if(res == 0) {
                    insertEthMoveHistory(vo, rpc, vo.getUserEmail());
                }

            } catch (Exception e) {
                //System.out.println("코인전송실패!!, 실패사유 : " + e.getMessage());
                vo.setDealNo("");
                vo.setSfYn("N");
                vo.setMemo("알수없는 이유로 실패");
                depositDAO.insCoinMoveHis(vo);
            }

            try {
                /*문자발송*/
                SendInfoVO svo = new SendInfoVO();
                /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
                svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
                svo.setCnAmt(String.valueOf(cnAmt));
                svo.setCurcyNm(vo.getCurcyNm());
                svo.setUserEmail(vo.getUserEmail());
                smsApiService.smsDepoComp(request, svo);
                /*문자발송*/
            }catch (Exception e) {
                e.printStackTrace();
            }

        }




	}

	//리플
	public void xrpPending(DepositVO vo, SimpleWalletRpcProvider rpc, HttpServletRequest request) throws Exception {

		List<DepositVO> devoList = depositDAO.getCnAccInfo(vo);
		String json = "";
		for(DepositVO devo : devoList) {
			json = rpc.getTransjaction(devo.getDestinationTag());

			//System.out.println("json = rpc.getTransjaction(devo.getDestinationTag()); : " + devo.getDestinationTag());
			System.out.println("리플json : " + json);

			JSONParser parser = new JSONParser();
			JSONObject jsonObj = (JSONObject) parser.parse( json );
			JSONArray jsonArr = (JSONArray) jsonObj.get("transaction");;
			if(jsonArr.size() > 0) {
				for(Object o : jsonArr) {
					JSONObject tempObj = (JSONObject) o;


					//System.out.println("TX_ID : " + tempObj.get("TX_ID"));
					//System.out.println("TX_TO_VALUE : " + tempObj.get("TX_TO_VALUE"));

					vo.setDealNo(tempObj.get("TX_ID").toString());
					DepositVO dvo = getCnDptCode(vo);
					vo.setRegIp("127.0.0.1");

					double cnAmt = 0;
					cnAmt = Double.parseDouble(tempObj.get("TX_TO_VALUE").toString()) / 10; // 리플은 10으로 나눈다

					//System.out.println("cnAmt : " + cnAmt);
					vo.setCnAmt(cnAmt);

					if (dvo == null || dvo.getCnDptCode() == null || dvo.getCnDptCode().equals("")) {// 입금진행중 건수가 없으면
						vo.setCnDptCode("");
						vo.setDptKndCd("CMMC00000000014");
						vo.setSndWletAdr(devo.getDestinationTag());

						INSUPT30171030(vo);//입금진행 INS
						dvo = getCnDptCode(vo);

						vo.setDptKndCd("CMMC00000000283");//( 입금확정)
						vo.setCnDptCode(dvo.getCnDptCode());
						INSUPT30171030(vo);//입금완료 UPT
						INS10171028(vo);//실제코인 INS
						try {
							/*문자발송*/
							SendInfoVO svo = new SendInfoVO();
							/*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
							svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
							svo.setCnAmt(String.valueOf(cnAmt));
							svo.setCurcyNm(vo.getCurcyNm());
							svo.setUserEmail(vo.getUserEmail());
							smsApiService.smsDepoComp(request, svo);
							/*문자발송*/
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public int insertEthMoveHistory(DepositVO vo, SimpleWalletRpcProvider rpc, String password) throws Exception {
        String json = "";
	    /*if(vo.getCnKndCd().equals(CmmCdConstant.ETH_CD)) {
            json = rpc.sendTransaction(password , vo.getWletAdr(), CmmCdConstant.ETH_ACC, String.valueOf(vo.getCnAmt()));
        } else if(vo.getCnKndCd().equals(CmmCdConstant.SGC_CD)) {
            json = rpc.sendTransaction(password , vo.getWletAdr(), CmmCdConstant.SGC_ACC, String.valueOf(vo.getCnAmt()));
        }*/

        if(vo.getCnKndCd().equals(CmmCdConstant.ETH_CD)) {
            //json = rpc.sendTransaction(password , vo.getWletAdr(), CmmCdConstant.ETH_ACC, String.valueOf(vo.getCnAmt()));
            String url = "http://10.10.0.121:3100/sendTransaction";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("passwd", password);
            map.put("from", vo.getWletAdr().trim());
            map.put("to", CmmCdConstant.ETH_ACC);
            map.put("amt", String.valueOf(vo.getCnAmt()));
            map.put("exchange", CmmCdConstant.EXCHANGE_NAME);
            //System.out.println("pwd : " + password);
            json = HttpComLib.httpSendAPI(url, map);
        } else if(vo.getCnKndCd().equals(CmmCdConstant.SGC_CD)) {
            String url = "http://10.10.0.122:17000/sendTransaction";
            Map<String, Object> map = new HashMap();
            map.put("passwd", password);
            map.put("from", vo.getWletAdr().trim());
            map.put("to", CmmCdConstant.SGC_ACC);
            map.put("amt", String.valueOf(vo.getCnAmt()));
            map.put("exchange", CmmCdConstant.EXCHANGE_NAME);
            //System.out.println("pwd : " + password);
            json = HttpComLib.httpSendAPI(url, map);
        }

		String result = "";
		String code = "";
		String message = "";
		JSONParser parser = new JSONParser();
		Object obj = parser.parse( json );
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
		vo.setCode(code);
		if("0".equals(code)) {//0으로 떨어지면 성공
			//System.out.println("코인전송성공!! : " + message);
			vo.setDealNo(result);
			vo.setSfYn("Y");
			vo.setMemo(message);
			depositDAO.insCoinMoveHis(vo);
			return 1;
		} else {
			vo.setDealNo(result);
			vo.setSfYn("N");
			vo.setMemo(message);
			depositDAO.insCoinMoveHis(vo);
			return 0;
		}

	}

    public List<DepoBankVO> getUserBankInfoList(String userEmail) throws Exception {
        return depositDAO.getUserBankInfoList(userEmail);
    }

    public void insCoinMoveHis(DepositVO vo) throws Exception {

	    depositDAO.insCoinMoveHis(vo);
    }

    public WletUserInfoVO selectWletUserInfo(WletUserInfoVO vo) throws Exception {

        return depositDAO.selectWletUserInfo(vo);
    }


    public DepositVO getExRateDepositPrc(DepositVO vo) throws Exception {

        return depositDAO.getExRateDepositPrc(vo);
    }

    public String getTrnGenKey() throws Exception {

        return depositDAO.getTrnGenKey();
    }

    public void insTrnInfo(TrnInfoVO vo) throws Exception {

        depositDAO.insTrnInfo(vo);
    }

    public CmeTradeReqVO depositCash(CmeTradeReqVO vo) throws Exception {
        return depositDAO.depositCash(vo);
    }

    public int cardCoinOut(DepositVO vo) throws Exception {
        return depositDAO.cardCoinOut(vo);
    }

    public int cardCoinIn(DepositVO vo) throws Exception {
	    return depositDAO.cardCoinIn(vo);
    }

    public int cardCashOut(DepositVO vo) throws Exception {
        return depositDAO.cardCashOut(vo);
    }

    public String getCardCoinOutKey() throws Exception {
        return depositDAO.getCardCoinOutKey();
    }

    public String getCardCoinInKey() throws Exception {
	    return depositDAO.getCardCoinInKey();
    }

    public int insUptCardUseInfo(MoaCardReqVO vo) throws Exception {

        return depositDAO.insUptCardUseInfo(vo);
    }

    public MoaCardReqVO selectCardCoinUseInfo(String tradeId) throws Exception {
        return depositDAO.selectCardCoinUseInfo(tradeId);
    }

    public int getBmcCoinCnt(String wletAdr) throws Exception {
        return depositDAO.getBmcCoinCnt(wletAdr);
    }

    public double getTotUsdtAmt() throws Exception {
        return depositDAO.getTotUsdtAmt();
    }

    public String getKrwPoint(String userEmail) throws Exception{
	    return depositDAO.getKrwPoint(userEmail);
    }

    public void insMoaCardHistory(MoaCardCoinVO vo) throws Exception{
	    depositDAO.insMoaCardHistory(vo);
    }















    public CmeResultVO pendingCoinNew(HttpServletRequest request, DepositVO vo) throws Exception {

	    if(vo.getCurcyType().equals("BTC")) {
            bitPendingNew(vo, request);
        } else if(vo.getCurcyType().equals("ETH")) {
            ethPendingNew(vo, request);
        } else if(vo.getCurcyType().equals("XRP")) {
            xrpPendingNew(vo, request);
        }
        return new CmeResultVO();

    }

    //리플
    public void xrpPendingNew(DepositVO vo, HttpServletRequest request) throws Exception {

        List<DepositVO> devoList = depositDAO.getCnAccInfo(vo);
        String json = "";
        for(DepositVO devo : devoList) {
            //json = rpc.getTransjaction(devo.getDestinationTag());

            //System.out.println("json = rpc.getTransjaction(devo.getDestinationTag()); : " + devo.getDestinationTag());
            System.out.println("리플json : " + json);

            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse( json );
            JSONArray jsonArr = (JSONArray) jsonObj.get("transaction");;
            if(jsonArr.size() > 0) {
                for(Object o : jsonArr) {
                    JSONObject tempObj = (JSONObject) o;


                    //System.out.println("TX_ID : " + tempObj.get("TX_ID"));
                    //System.out.println("TX_TO_VALUE : " + tempObj.get("TX_TO_VALUE"));

                    vo.setDealNo(tempObj.get("TX_ID").toString());
                    DepositVO dvo = getCnDptCode(vo);
                    vo.setRegIp("127.0.0.1");

                    double cnAmt = 0;
                    cnAmt = Double.parseDouble(tempObj.get("TX_TO_VALUE").toString()) / 10; // 리플은 10으로 나눈다

                    //System.out.println("cnAmt : " + cnAmt);
                    vo.setCnAmt(cnAmt);

                    if (dvo == null || dvo.getCnDptCode() == null || dvo.getCnDptCode().equals("")) {// 입금진행중 건수가 없으면
                        vo.setCnDptCode("");
                        vo.setDptKndCd("CMMC00000000014");
                        vo.setSndWletAdr(devo.getDestinationTag());

                        INSUPT30171030(vo);//입금진행 INS
                        dvo = getCnDptCode(vo);

                        vo.setDptKndCd("CMMC00000000283");//( 입금확정)
                        vo.setCnDptCode(dvo.getCnDptCode());
                        INSUPT30171030(vo);//입금완료 UPT
                        INS10171028(vo);//실제코인 INS
                        try {
                            /*문자발송*/
                            SendInfoVO svo = new SendInfoVO();
                            /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
                            svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
                            svo.setCnAmt(String.valueOf(cnAmt));
                            svo.setCurcyNm(vo.getCurcyNm());
                            svo.setUserEmail(vo.getUserEmail());
                            smsApiService.smsDepoComp(request, svo);
                            /*문자발송*/
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }





    //비트코인계열
    public void bitPendingNew(DepositVO vo, HttpServletRequest request) throws Exception {

        String json = HttpComLib.httpSendGetAPI(vo.getUrlInfo() + "/getBalance/" + vo.getWletAdr());

        int confirmCnt = Integer.parseInt(vo.getConfirnCnt());

        System.out.println(vo.getCurcyNm() + " getBalance json : " + json );

        double bal = Double.parseDouble(json);
        if(bal > 0) {
            json = HttpComLib.httpSendGetAPI(vo.getUrlInfo() + "/listtransaction/" + vo.getUserEmail());

            JSONParser parser = new JSONParser();
            Object obj = parser.parse( json );
            JSONArray jsonArr = (JSONArray) obj;

            if(jsonArr.size() > 0) {

                for(Object o : jsonArr) {

                    JSONObject jsonObj = (JSONObject) o;

                    //System.out.println("category : " + jsonObj.get("category"));
                    //System.out.println("confirmations : " + jsonObj.get("confirmations"));
                    //System.out.println("txid : " + jsonObj.get("txid"));
                    //System.out.println("amount : " + jsonObj.get("amount"));
                    //System.out.println("address : " + jsonObj.get("address"));

                    if(jsonObj.get("category").equals("receive")) {//트랜잭션이 receive일 경우

                        double cnAmt = 0D;

                        long confirm = (long) jsonObj.get("confirmations");//컨펌개수를 가져온다
                        String txid = (String) jsonObj.get("txid");//txid를 가져온다
                        vo.setDealNo(txid);
                        DepositVO dvo = getCnDptCode(vo);//이미 입력한 건인지 확인쿼리

                        vo.setRegIp("127.0.0.1");

                        Double doubleAmt = null;
                        Long longAmt = null;
                        if(jsonObj.get("amount").getClass().equals(Long.class)) {
                            longAmt = (Long) jsonObj.get("amount");//개수를 가져온다
                        } else {
                            doubleAmt = (double) jsonObj.get("amount");//개수를 가져온다
                        }


                        if(longAmt != null) {
                            cnAmt = longAmt.doubleValue();
                        } else {
                            cnAmt = doubleAmt;
                        }

                        vo.setCnAmt(cnAmt);


                        if (dvo == null) {// 입금진행중 건수가 없으면

                            vo.setCnDptCode("");
                            vo.setDptKndCd("CMMC00000000014");
                            vo.setSndWletAdr((String) jsonObj.get("address"));
                            INSUPT30171030(vo);//입금진행 INS
                            dvo = getCnDptCode(vo);
                        }
                        if(dvo != null) {

                            if (confirm >= confirmCnt && dvo.getCnDptCode2().equals("")) { //컨펌 수가 3이상( 입금확정)
                                vo.setDptKndCd("CMMC00000000283");
                                vo.setCnDptCode(dvo.getCnDptCode());
                                INSUPT30171030(vo);//입금완료 UPT
                                INS10171028(vo);//실제코인 INS
                                //rpc.coinMove(vo.getUserEmail(), "main", String.valueOf(cnAmt));

                                try {
                                    /*문자발송*/

                                    SendInfoVO svo = new SendInfoVO();
                                    /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
                                    svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
                                    svo.setCnAmt(String.valueOf(cnAmt));
                                    svo.setCurcyNm(vo.getCurcyNm());
                                    svo.setUserEmail(vo.getUserEmail());
                                    smsApiService.smsDepoComp(request, svo);
                                    /*문자발송*/

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    //이더리움계열 코인
    public void ethPendingNew(DepositVO vo, HttpServletRequest request) throws Exception {

        String cnKndCd = vo.getCurcyCd();
        String url = "";
        double minusVal = Double.parseDouble(vo.getMoveFee());
        String jsonStr = "";


        String rval = HttpComLib.httpSendGetAPI(vo.getUrlInfo() + "/getBalance/" + vo.getWletAdr());

        System.out.println(vo.getCurcyNm() + " rval : " + rval);

        double bal = Double.parseDouble(rval);

        if(bal > 0) {

            jsonStr = HttpComLib.httpSendGetAPI(vo.getUrlInfo() + "/listtransaction/" + StringUtils.lowerCase(vo.getWletAdr()));

            System.out.println(vo.getCurcyNm() + " jsonStr : " + jsonStr);

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            JSONArray list = (JSONArray) obj.get("data");

            for(Object arr : list) {
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

                DepositVO dvo = getCnDptCode(vo);

                if (dvo == null) {// 입금진행중 건수가 없으면
                    vo.setCnDptCode("");
                    vo.setDptKndCd("CMMC00000000014");
                    vo.setSndWletAdr(txTo);
                    INSUPT30171030(vo);//입금진행 INS
                    dvo = getCnDptCode(vo);

                    vo.setDptKndCd("CMMC00000000283");//( 입금확정)
                    vo.setCnDptCode(dvo.getCnDptCode());
                    INSUPT30171030(vo);//입금완료 UPT
                    INS10171028(vo);//실제코인 INS

                    try {
                        /*문자발송*/
                        SendInfoVO svo = new SendInfoVO();
                        /*필수값 : 받는사람 휴대폰 번호, clientCd(클라이언트코드)*/
                        svo.setMobile_info(loginDAO.getUserMobile(vo.getUserEmail()));
                        svo.setCnAmt(String.valueOf(txAmount));
                        svo.setCurcyNm(vo.getCurcyNm());
                        svo.setUserEmail(vo.getUserEmail());
                        smsApiService.smsDepoComp(request, svo);
                        /*문자발송*/
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public int getCoinMoveHourCnt(DepositVO vo) throws Exception {
        return depositDAO.getCoinMoveHourCnt(vo);
    }

    public List<String> getDepositUserEmail() throws Exception {
        return depositDAO.getDepositUserEmail();
    }

    public KrwWithVO getUserAccInfo(String userEmail) throws Exception{
	    return depositDAO.getUserAccInfo(userEmail);
    }

    public String getCoinCode(String curcyNm) throws Exception{
	    return depositDAO.getCoinCode(curcyNm);
    }

    public String getWletUserEmail(DepositVO vo) throws Exception{
	    return depositDAO.getWletUserEmail(vo);
    }

    public int getDepositCheckCnt(DepositVO vo) throws Exception{
	    return depositDAO.getDepositCheckCnt(vo);
    }

    public void PR_INTUPT_ADM_EVENT_CHECK(AdmEventVO vo) throws Exception{
	    depositDAO.PR_INTUPT_ADM_EVENT_CHECK(vo);
    }

    public int getEventCnt() throws Exception{
	    return depositDAO.getEventCnt();
    }

    public AdmEventVO getEventInfo(int no) throws Exception{
	    return depositDAO.getEventInfo(no);
    }

    public void PR_INTUPT_ADM_EVENT(AdmEventVO vo) throws Exception{
	    depositDAO.PR_INTUPT_ADM_EVENT(vo);
    }

    public List<AdmEventVO> getAdmEventList(AdmEventVO vo) throws Exception{
	    return depositDAO.getAdmEventList(vo);
    }

    public int getAdmEventListCnt(String userEmail) throws Exception{
	    return depositDAO.getAdmEventListCnt(userEmail);
    }
}
