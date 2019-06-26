package com.bitkrx.api.user.dao;

import java.util.List;

import com.bitkrx.api.auth.vo.*;
import com.bitkrx.api.googleOtp.control.GoogleOtpController;
import com.bitkrx.api.googleOtp.vo.GooglOtpVO;
import com.bitkrx.api.trade.vo.CmeTradeReqVO;
import org.springframework.stereotype.Repository;

import com.bitkrx.api.main.vo.CmeUserAssetReqVO;
import com.bitkrx.api.main.vo.CmeUserAssetResVO;
import com.bitkrx.api.main.vo.UserAssetVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;

@Repository
public class UserDAO extends CmeComAbstractDAO{

	public CmeUserAssetResVO userAsset(CmeUserAssetReqVO vo) {
		return (CmeUserAssetResVO) selectByPk("userDAO.userAssetList", vo);
	}

	public CmeUserAssetResVO assetKrw(CmeUserAssetReqVO vo) {
		return (CmeUserAssetResVO) selectByPk("userDAO.assetKrw", vo);
	}

	public CmeUserAssetResVO assetPoint(CmeUserAssetReqVO vo) {
		return (CmeUserAssetResVO) selectByPk("userDAO.assetPoint", vo);
	}

	public List<CmeUserAssetResVO> userCoinList(CmeUserAssetReqVO vo) {
		return list("userDAO.userCoinList", vo);
	}
	
	public UserAssetVO getUserMny(CmeUserAssetReqVO vo) {
		return (UserAssetVO) selectByPk ("userDAO.getUserMny", vo);
	}
	
	public List<UserAssetVO> getUserCoin(CmeUserAssetReqVO vo) {
		return list("userDAO.getUserCoin", vo);
	}

	public String getUserLangCd(String userEmail) throws Exception {
		return (String) selectByPk("userDAO.getUserLangCd", userEmail);
	}
	
	public List<UserMaskingVO> selectUserMaskingList(String value) throws Exception {
		return list("userDAO.selectUserMaskingList", value);
	}

	public int uptOtpSerial(GooglOtpVO vo) throws Exception {
		return update("userDAO.uptOtpSerial", vo);
	}

    public UserInfoVO getUserInfo(UserInfoVO vo) throws Exception {

        return (UserInfoVO) selectByPk("userDAO.getUserInfo", vo);
    }

    public List<CountryVO> getCountryList() throws Exception {
        return list("userDAO.getCountryList", null);
    }

    public List<NatnBankVO> getNatnBankList(UserInfoVO vo) throws Exception {
	    return list ("userDAO.getNatnBankList", vo);
    }

    public UserAuthVO INSUPT10171021(UserAuthVO vo) throws Exception {

        return (UserAuthVO) convertProc("userDAO.INSUPT10171021", vo);
    }

    public UserBankInfoVO INSUPT10171022(UserBankInfoVO vo) throws Exception {

        return (UserBankInfoVO) convertProc("userDAO.INSUPT10171022", vo);
    }

    public void insertSendEmailCust(SendEmailCustVO vo) throws Exception {

	    insert("userDAO.insertSendEmailCust", vo);
    }

    public String getMailCode() throws Exception {
	    return (String) selectByPk("userDAO.getMailCode", null);
    }

    public List<LoginHistoryResVO> getLoginHistory(LoginHistoryVO vo) throws Exception {
	    return list("loginDAO.getLoginHistory", vo);
    }

    public int getLoginHistoryCnt(LoginHistoryVO vo) throws Exception {
        return (int) selectByPk ("loginDAO.getLoginHistoryCnt", vo);
    }

    public String getAdminLangCd(String userEmail) throws Exception{
		return (String) selectByPk("userDAO.getAdminLangCd",userEmail);
	}

	public List<AuthCodeListVO> getAuthParCodeList() throws Exception {
	    return list("userDAO.getAuthParCodeList", null);
    }

    public List<AuthCodeListVO> getAuthChiCodeList(String cmmUpperCd) throws Exception {
        return list("userDAO.getAuthChiCodeList", cmmUpperCd);
    }

    public int mobileChkCnt(String userMobile) throws Exception {
		return (int)selectByPk("userDAO.mobileChkCnt", userMobile);
	}

	public String getUserSubEmail(String userEmail) throws Exception{
		return (String)selectByPk("userDAO.getUserSubEmail", userEmail);
	}

	public AuthVO selectUserAuth(AuthVO avo) throws Exception{
		return (AuthVO) selectByPk("userDAO.selectUserAuth" , avo);
	}

	public String getTradeCoinUseYn(UserTradeCheckVO vo) throws Exception{
		return (String) selectByPk("userDAO.getTradeCoinUseYn" , vo);
	}

	public int getUserTradeCheck(UserTradeCheckVO vo) throws Exception{
		return (int) selectByPk("userDAO.getUserTradeCheck" , vo);
	}

	public String krwUseYnCheck(String userEmail) throws Exception{
		return (String)selectByPk("userDAO.krwUseYnCheck", userEmail);
	}

	public String getSmsCertCheck(String userEmail) throws Exception{
		return (String)selectByPk("userDAO.getSmsCertCheck", userEmail);
	}

	public ForeignIpVO getForeignIpCheck(String ip) throws Exception{
		return (ForeignIpVO)selectByPk("userDAO.getForeignIpCheck" , ip);
	}

	public void insertCheckIp(ForeignIpVO vo) throws Exception{
		insert("userDAO.insertCheckIp" , vo);
	}

	public UserFuncAuthVO getUserFunc(String userEmail) throws Exception{
		return (UserFuncAuthVO)selectByPk("userDAO.getUserFunc" , userEmail);
	}

	public int getUserFuncDt(String userEmail) throws Exception{
		return (int) selectByPk("userDAO.getUserFuncDt" , userEmail);
	}

	public int uptAccCertYn(UserAuthVO vo) throws Exception {
		return update("userDAO.uptAccCertYn", vo);
	}

	public int uptSmsCertYn(UserAuthVO vo) throws Exception {
		return update("userDAO.uptSmsCertYn", vo);
	}
	public int uptKycCertYn(UserAuthVO vo) throws Exception {
		return update("userDAO.uptKycCertYn", vo);
	}

	public int uptOtpCertYn(UserAuthVO vo) throws Exception {
		return update("userDAO.uptOtpCertYn", vo);
	}

    public UserInfoVO getUserCertification(UserInfoVO vo) throws Exception {
        return (UserInfoVO) selectByPk("userDAO.getUserCertification", vo);
    }

    public List<CmeTradeReqVO> getBotSellList() throws Exception{
		return list("userDAO.getBotSellList" , null);
	}

	public void uptBotBuyList(CmeTradeReqVO vo) throws Exception{
		update("userDAO.uptBotBuyList" , vo);
	}

	public void uptBotMoneyList(CmeTradeReqVO vo) throws Exception{
		update("userDAO.uptBotMoneyList" , vo);
	}

	public UserTradeCheckVO getCoinBuyCheck(UserTradeCheckVO vo) throws Exception{
		return (UserTradeCheckVO) selectByPk("userDAO.getCoinBuyCheck" , vo);
	}

	public void uptUserInfo(String userEmail) throws Exception{
		update("userDAO.uptUserInfo" , userEmail);
	}

	public void uptInfoYn(UserAuthVO vo) throws Exception{
		update("userDAO.uptInfoYn" , vo);
	}

	public String getOtpYn() throws Exception{
		return (String)selectByPk("userDAO.getOtpYn", null);
	}

	public List<UserOtpCheckVO> getOtpAuthCountry() throws Exception{
		return list("userDAO.getOtpAuthCountry", null);
	}

	public int countException(String userEmail) throws Exception{
		return (int) selectByPk("userDAO.countException", userEmail);
	}

	public String getUserCountryCd(String userEmail) throws Exception{
		return (String)selectByPk("userDAO.getUserCountryCd", userEmail);
	}

	public String getLogoUrl(String langCd) throws Exception{
		return (String) selectByPk("userDAO.getLogoUrl" , langCd);
	}

	public CommonVO getFileSn(String fileId) throws Exception{
		return (CommonVO) selectByPk("userDAO.getFileSn" , fileId);
	}

	public List<PopUpVO> selectPopUpList(PopUpVO vo) throws Exception{
		return list("userDAO.selectPopUpList" , vo);
	}

	public int selectPopUpListCnt(PopUpVO vo) throws Exception{
		return (int) selectByPk("userDAO.selectPopUpListCnt" , vo);
	}

	public String selectFileSn(String atchFileId) throws Exception{
		return (String) selectByPk("userDAO.selectFileSn" , atchFileId);
	}

	public List<CmeTradeReqVO> getBotTradeList(CmeTradeReqVO vo) throws Exception {
		if ("S".equals(vo.getTradeType())) {
			return list("userDAO.getBotSellList", vo);
		}else{
			return list("userDAO.getBotBuyList", vo);
		}
	}
}
