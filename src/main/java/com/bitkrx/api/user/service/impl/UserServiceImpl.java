/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.user.service.impl;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitkrx.api.auth.dao.LoginDAO;
import com.bitkrx.api.auth.service.LoginService;
import com.bitkrx.api.auth.vo.*;
import com.bitkrx.api.googleOtp.vo.GooglOtpVO;
import com.bitkrx.api.mail.service.MailApiService;
import com.bitkrx.api.trade.vo.CmeTradeReqVO;
import com.bitkrx.config.annotation.CommonDataSource;
import com.bitkrx.config.dbinfo.DataSourceType;
import com.bitkrx.config.util.ComUtil;
import com.bitkrx.core.util.HttpComLib;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitkrx.api.main.vo.CmeUserAssetReqVO;
import com.bitkrx.api.main.vo.CmeUserAssetResVO;
import com.bitkrx.api.user.dao.UserDAO;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.util.CoinUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.user.service.impl
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 28.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;

	@Autowired
    LoginDAO loginDAO;

	@Autowired
    MailApiService mailService;

	@Autowired
    LoginService loginService;

	CoinUtil coinUtil = CoinUtil.getinstance();
	
	@Override
	public CmeUserAssetResVO userAsset(CmeUserAssetReqVO vo) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.userAsset(vo);
	}

	@Override
	public CmeUserAssetResVO assetKrw(CmeUserAssetReqVO vo) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.assetKrw(vo);
	}

	@Override
	public CmeUserAssetResVO assetPoint(CmeUserAssetReqVO vo) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.assetPoint(vo);
	}

	@Override
	public List userCoinList(CmeUserAssetReqVO vo) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.userCoinList(vo);
	}
	
	@Override
	public String getUserLangCd(String userEmail) throws Exception {
		return userDAO.getUserLangCd(userEmail);
	}

	@Override
	public List<UserMaskingVO> selectUserMaskingList(String value) throws Exception {
		return userDAO.selectUserMaskingList(value);
	}

	@Override
	public int uptOtpSerial(GooglOtpVO vo) throws Exception {
        UserAuthVO authVO = new UserAuthVO();
        authVO.setUserEmail(vo.getUserEmail());
        authVO.setOtpSerial(vo.getEncodedKey());
        authVO = userDAO.INSUPT10171021(authVO);
        List list = (List) authVO.getRESULT();
        Map map = (Map) list.get(0);
        String rtnCd = (String) map.get("RTN_CD");
        if(rtnCd != null && rtnCd.equals("1")) {
            return 1;
        }
        return 0;
	}

    public UserInfoVO getUserInfo(UserInfoVO vo) throws Exception {

        return userDAO.getUserInfo(vo);
    }

    public List<CountryVO> getCountryList() throws Exception {
        return userDAO.getCountryList();
    }

    public List<NatnBankVO> getNatnBankList(UserInfoVO vo) throws Exception {
        return userDAO.getNatnBankList(vo);
    }

    public UserAuthVO INSUPT10171021(UserAuthVO vo) throws Exception {

        return userDAO.INSUPT10171021(vo);
    }

    public UserBankInfoVO INSUPT10171022(UserBankInfoVO vo) throws Exception {

        return userDAO.INSUPT10171022(vo);
    }

    public int userPwdChange(HttpServletRequest request, SendEmailCustVO vo) throws Exception {

        CmeLoginVO lvo = new CmeLoginVO();
        lvo.setUserEmail(vo.getUserEmail());
        lvo.setRegIp(vo.getRegIp());
        lvo.setPwdChgYn("Y");

        System.out.println(lvo.getPwdChgYn());
        loginService.INS10171020(lvo);

        System.out.println(lvo.getProceduresResult());

        /*메일발송*/
        Map <String, Object> model = new HashMap<String, Object>();
        /*필수값 : mailTo(받는사람메일주소), clientCd(클라이언트코드)*/
        model.put("mailTo", vo.getUserEmail());
        model.put("clientCd", vo.getClientCd());
        model.put("lang", userDAO.getUserLangCd(vo.getUserEmail()));
        model.put("userEmail", vo.getUserEmail());
        return mailService.userPwdChangeMail(request, model);
        /*메일발송*/

    }

    public List<LoginHistoryResVO> getLoginHistory(LoginHistoryVO vo) throws Exception {
        List<LoginHistoryResVO> list =  userDAO.getLoginHistory(vo);

        System.out.print(new Date());
        String ip = "";
        String location = "";
        for(LoginHistoryResVO hvo : list) {

            System.out.print(new Date());

            if(!ip.equals(hvo.getConnIp())) {
                String ipInfo = HttpComLib.httpSendGetAPI("http://ip-api.com/json/" + hvo.getConnIp());
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(ipInfo);
                if("success".equals((String) obj.get("status"))) {
                    String country = (String) obj.get("country");
                    String city = (String) obj.get("city");
                    location = country + ", " + city;
                    ip = hvo.getConnIp();

                }
            }

            hvo.setLocation(location);


        }
        System.out.print(new Date());

        return list;
    }

    public int getLoginHistoryCnt(LoginHistoryVO vo) throws Exception {

        return userDAO.getLoginHistoryCnt(vo);
    }

    public String getAdminLangCd(String userEmail) throws Exception{
	    return userDAO.getAdminLangCd(userEmail);
    }

    public List<AuthCodeListVO> getAuthParCodeList() throws Exception {
        return userDAO.getAuthParCodeList();
    }

    public List<AuthCodeListVO> getAuthChiCodeList(String cmmUpperCd) throws Exception {
        return userDAO.getAuthChiCodeList(cmmUpperCd);
    }

    public int mobileChkCnt(String userMobile) throws Exception{
	    return userDAO.mobileChkCnt(userMobile);
    }

    public AuthVO selectUserAuth(AuthVO avo) throws Exception{
	    return userDAO.selectUserAuth(avo);
    }

    public String getTradeCoinUseYn(UserTradeCheckVO vo) throws Exception{
	    return userDAO.getTradeCoinUseYn(vo);
    }

    public int getUserTradeCheck(UserTradeCheckVO vo) throws Exception{
	    return userDAO.getUserTradeCheck(vo);
    }

    public String krwUseYnCheck(String userEmail) throws Exception{
	    return userDAO.krwUseYnCheck(userEmail);
    }

    public String foreignIpCheck(String ip) throws Exception{
        //String ip = ComUtil.getRemoteIP(request);
        String status = "";


            try{
                ForeignIpVO fvo = new ForeignIpVO();
                fvo = userDAO.getForeignIpCheck(ip);
                if(fvo == null){
                    HttpURLConnection urlcon = (HttpURLConnection) new URL("http://ip2c.org/" + ip).openConnection();
                    urlcon.setDefaultUseCaches(false);
                    urlcon.setUseCaches(false);
                    urlcon.connect();

                    InputStream is = urlcon.getInputStream();
                    int c = 0;
                    String s = "";
                    while ((c = is.read()) != -1) s += (char)c;
                    is.close();
                    switch (s.charAt(0)){
                        case '0':
                            break;
                        case '1':
                            String[] reply = s.split(";");
                            if("KR".equals(reply[1])){
                                status = "N";
                            }else{
                                status = "Y";
                            }

                            ForeignIpVO avo = new ForeignIpVO();
                            avo.setIp(ip);
                            avo.setInternalYn(status);
                            userDAO.insertCheckIp(avo);
                        case '2':
                            break;
                    }

                }else{
                    status = fvo.getInternalYn();
                }

            }catch (Exception e){
                e.printStackTrace();
            }


        return status;
    }


    public String MtsIpCheck(String ip) throws Exception{
        String status = "";
        try{
            ForeignIpVO fvo = new ForeignIpVO();
            fvo = userDAO.getForeignIpCheck(ip);
            if(fvo == null){
                HttpURLConnection urlcon = (HttpURLConnection) new URL("http://ip2c.org/" + ip).openConnection();
                urlcon.setDefaultUseCaches(false);
                urlcon.setUseCaches(false);
                urlcon.connect();

                InputStream is = urlcon.getInputStream();
                int c = 0;
                String s = "";
                while ((c = is.read()) != -1) s += (char)c;
                is.close();
                switch (s.charAt(0)){
                    case '0':
                        break;
                    case '1':
                        String[] reply = s.split(";");
                        if("KR".equals(reply[1])){
                            status = "N";
                        }else{
                            status = "Y";
                        }
                        ForeignIpVO avo = new ForeignIpVO();
                        avo.setIp(ip);
                        avo.setInternalYn(status);
                        userDAO.insertCheckIp(avo);
                    case '2':
                        break;
                }
            }else{
                status = fvo.getInternalYn();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return status;
    }

    public UserFuncAuthVO getUserFunc(String userEmail) throws Exception{
	    return userDAO.getUserFunc(userEmail);
    }

    public int getUserFuncDt(String userEmail) throws Exception{
	    return userDAO.getUserFuncDt(userEmail);
    }

    public String getSmsCertCheck(String userEmail) throws Exception{
	    return userDAO.getSmsCertCheck(userEmail);
    }

    public int uptAccCertYn(UserAuthVO vo) throws Exception {
        return userDAO.uptAccCertYn(vo);
    }

    public int uptSmsCertYn(UserAuthVO vo) throws Exception {
        return userDAO.uptSmsCertYn(vo);
    }

    public int uptKycCertYn(UserAuthVO vo) throws Exception {
        return userDAO.uptKycCertYn(vo);
    }

    public int uptOtpCertYn(UserAuthVO vo) throws Exception {
        return userDAO.uptOtpCertYn(vo);
    }

    public UserInfoVO getUserCertification(UserInfoVO vo) throws Exception {

        return userDAO.getUserCertification(vo);
    }

    public List<CmeTradeReqVO> getBotSellList() throws Exception{
	    return userDAO.getBotSellList();
    }

    public void uptBotBuyList(CmeTradeReqVO vo) throws Exception{
	    userDAO.uptBotBuyList(vo);
    }

    public void uptBotMoneyList(CmeTradeReqVO vo) throws Exception{
	    userDAO.uptBotMoneyList(vo);
    }

    public UserTradeCheckVO getCoinBuyCheck(UserTradeCheckVO vo) throws Exception{
        return userDAO.getCoinBuyCheck(vo);
    }

    public void uptUserInfo(String userEmail) throws Exception{
	    userDAO.uptUserInfo(userEmail);
    }

    public void uptInfoYn(UserAuthVO vo) throws Exception{
	    userDAO.uptInfoYn(vo);
    }

    public String getOtpYn() throws Exception{
        return userDAO.getOtpYn();
    }

    public List<UserOtpCheckVO> getOtpAuthCountry() throws Exception{
        return userDAO.getOtpAuthCountry();
    }

    public int countException(String userEmail) throws Exception{
        return userDAO.countException(userEmail);
    }

    public String getUserCountryCd(String userEmail) throws Exception{
        return userDAO.getUserCountryCd(userEmail);
    }

    public String getLogoUrl(String langCd) throws Exception{
	    return userDAO.getLogoUrl(langCd);
    }

    @CommonDataSource(DataSourceType.OPRBOARD)
    public CommonVO getFileSn(String fileId) throws Exception {
        return userDAO.getFileSn(fileId);
    }

    public List<PopUpVO> selectPopUpList(PopUpVO vo) throws Exception{
        return userDAO.selectPopUpList(vo);
    }

    public int selectPopUpListCnt(PopUpVO vo) throws Exception{
        return userDAO.selectPopUpListCnt(vo);
    }

    @CommonDataSource(DataSourceType.OPRBOARD)
    public String selectFileSn(String atchFileId) throws Exception{
        return userDAO.selectFileSn(atchFileId);
    }

    public List<CmeTradeReqVO> getBotTradeList(CmeTradeReqVO vo) throws Exception{
        return userDAO.getBotTradeList(vo);
    }

    @CommonDataSource(DataSourceType.MKBTC)
    public List<CmeTradeReqVO> getBotTradeListBTC(CmeTradeReqVO vo) throws Exception {
        return userDAO.getBotTradeList(vo);
    }

    @CommonDataSource(DataSourceType.MKETH)
    public List<CmeTradeReqVO> getBotTradeListETH(CmeTradeReqVO vo) throws Exception {
        return userDAO.getBotTradeList(vo);
    }

    @CommonDataSource(DataSourceType.MKUSDT)
    public List<CmeTradeReqVO> getBotTradeListUSDT(CmeTradeReqVO vo) throws Exception {
        return userDAO.getBotTradeList(vo);
    }
}
