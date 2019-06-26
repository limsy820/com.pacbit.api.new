/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.user.service;

import java.util.List;

import com.bitkrx.api.auth.vo.*;
import com.bitkrx.api.googleOtp.vo.GooglOtpVO;
import com.bitkrx.api.main.vo.CmeUserAssetReqVO;
import com.bitkrx.api.main.vo.CmeUserAssetResVO;
import com.bitkrx.api.trade.vo.CmeTradeReqVO;
import com.jcraft.jsch.UserAuth;

import javax.servlet.http.HttpServletRequest;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.user.service
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 11. 28.
 */
public interface UserService {

	public CmeUserAssetResVO userAsset(CmeUserAssetReqVO vo) throws Exception;

	public CmeUserAssetResVO assetKrw(CmeUserAssetReqVO vo) throws Exception;

	public CmeUserAssetResVO assetPoint(CmeUserAssetReqVO vo) throws Exception;
	
	public List userCoinList(CmeUserAssetReqVO vo) throws Exception;
	
	public String getUserLangCd(String userEmail) throws Exception;
	
	public List<UserMaskingVO> selectUserMaskingList(String value) throws Exception;

	public int uptOtpSerial(GooglOtpVO vo) throws Exception;

    public UserInfoVO getUserInfo(UserInfoVO vo) throws Exception;

    public List<CountryVO> getCountryList() throws Exception;

    public List<NatnBankVO> getNatnBankList(UserInfoVO vo) throws Exception;

    public UserAuthVO INSUPT10171021(UserAuthVO vo) throws Exception;

    public UserBankInfoVO INSUPT10171022(UserBankInfoVO vo) throws Exception;

    public int userPwdChange(HttpServletRequest request, SendEmailCustVO vo) throws Exception;

    public List<LoginHistoryResVO> getLoginHistory(LoginHistoryVO vo) throws Exception;

    public int getLoginHistoryCnt(LoginHistoryVO vo) throws Exception;

    public String getAdminLangCd(String userEmail) throws Exception;

    public List<AuthCodeListVO> getAuthParCodeList() throws Exception;

    public List<AuthCodeListVO> getAuthChiCodeList(String cmmUpperCd) throws Exception;

    public int mobileChkCnt(String userMobile) throws Exception;

    public AuthVO selectUserAuth(AuthVO avo) throws Exception;

    public String getTradeCoinUseYn(UserTradeCheckVO vo) throws Exception;

    public int getUserTradeCheck(UserTradeCheckVO vo) throws Exception;

    public String krwUseYnCheck(String userEmail) throws Exception;

    public String foreignIpCheck(String ip) throws Exception;

    public String MtsIpCheck(String ip) throws Exception;

    public UserFuncAuthVO getUserFunc(String userEmail) throws Exception;

    public int getUserFuncDt(String userEmail) throws Exception;

    public String getSmsCertCheck(String userEmail) throws Exception;

    public int uptAccCertYn(UserAuthVO vo) throws Exception;

    public int uptSmsCertYn(UserAuthVO vo) throws Exception;

    public int uptKycCertYn(UserAuthVO vo) throws Exception;

    public int uptOtpCertYn(UserAuthVO vo) throws Exception;

    public UserInfoVO getUserCertification(UserInfoVO vo) throws Exception;

    public List<CmeTradeReqVO> getBotSellList() throws Exception;

    public void uptBotBuyList(CmeTradeReqVO vo) throws Exception;

    public void uptBotMoneyList(CmeTradeReqVO vo) throws Exception;

    public UserTradeCheckVO getCoinBuyCheck(UserTradeCheckVO vo) throws Exception;

    public void uptUserInfo(String userEmail) throws Exception;

    public void uptInfoYn(UserAuthVO vo) throws Exception;

    public String getOtpYn() throws Exception;

    public List<UserOtpCheckVO> getOtpAuthCountry() throws Exception;

    public int countException(String userEmail) throws Exception;

    public String getUserCountryCd(String userEmail) throws Exception;

    public String getLogoUrl(String langCd) throws Exception;

    public CommonVO getFileSn(String fileId) throws Exception;

    public List<PopUpVO> selectPopUpList(PopUpVO vo) throws Exception;

    public int selectPopUpListCnt(PopUpVO vo) throws Exception;

    public String selectFileSn(String atchFileId) throws Exception;

    public List<CmeTradeReqVO> getBotTradeList(CmeTradeReqVO vo) throws Exception;

    List<CmeTradeReqVO> getBotTradeListBTC(CmeTradeReqVO vo)throws Exception;

    List<CmeTradeReqVO> getBotTradeListETH(CmeTradeReqVO vo)throws Exception;

    List<CmeTradeReqVO> getBotTradeListUSDT(CmeTradeReqVO vo)throws Exception;
}
