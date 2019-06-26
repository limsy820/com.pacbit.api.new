package com.bitkrx.api.moaCard.dao;

import com.bitkrx.api.main.vo.CmeRealTimeCoinPriceResVO;
import com.bitkrx.api.moaCard.service.MoaCardService;
import com.bitkrx.api.moaCard.vo.*;
import com.bitkrx.api.trade.vo.CmeTradeReqVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MoaCardDAO extends CmeComAbstractDAO {

    public int userReqCardCnt(String userEmail) throws Exception {

        return (int) selectByPk("moaCardDAO.userReqCardCnt", userEmail);
    }

    public void insCardReqInfo(MoaCardReqVO vo) throws Exception {

        insert("moaCardDAO.insCardReqInfo", vo);
    }

    public List<MoaCardReqListVO> selectUserReqList(MoaCardReqListVO vo) throws Exception {

        return list("moaCardDAO.selectUserReqList", vo);
    }

    public int uptCardRegComp(MoaCardReqVO vo) throws Exception {

        return update("moaCardDAO.uptCardRegComp", vo);
    }

    public int uptCardRegBack(MoaCardReqVO vo) throws Exception {

        return update("moaCardDAO.uptCardRegBack", vo);
    }

    public MoaCardReqListVO selectUserCard(MoaCardReqListVO vo) throws Exception {

        return (MoaCardReqListVO) selectByPk("moaCardDAO.selectUserReqList", vo);
    }

    public CardReqPayInfoVO cardReqpayInfo() throws Exception {

        return (CardReqPayInfoVO) selectByPk("moaCardDAO.cardReqpayInfo", null);
    }

    public String getOrderIdKey() throws Exception {

        return (String) selectByPk("moaCardDAO.getOrderIdKey", null);
    }

    public String getStatusValue(String order_id) throws Exception {

        return (String) selectByPk("moaCardDAO.getStatusValue", order_id);
    }

    public String getUserNation(String userEmail) throws Exception {
        return (String) selectByPk("moaCardDAO.getUserNation" , userEmail);
    }

    public int uptCardSendInfo(MoaCardReqVO vo) throws Exception {
        return update("moaCardDAO.uptCardSendInfo", vo);
    }

    public int getCardSendInfoCnt(MoaCardReqVO vo) throws Exception {
        return (int) selectByPk("moaCardDAO.getCardSendInfoCnt", vo);
    }

    public int uptCardSendDt(MoaCardReqVO vo) throws Exception{
        return update("moaCardDAO.uptCardSendDt", vo);
    }

    public CardBankInfoVO selectCardBankInfo(String cardNum) throws Exception {
        return (CardBankInfoVO) selectByPk("moaCardDAO.selectCardBankInfo", cardNum);
    }

    public int selectUserRegInfo(MoaCardReqVO vo) throws Exception {
        return (int) selectByPk("moaCardDAO.selectUserRegInfo", vo);
    }

    public int cardReqCancel(String orderId) throws Exception {
        return update("moaCardDAO.cardReqCancel", orderId);
    }

    public MoaCardReqVO selectUserMobile(String orderId) throws Exception {
        return (MoaCardReqVO) selectByPk("moaCardDAO.selectUserMobile", orderId);
    }

    public int uptCardAtmPwd(MoaCardReqVO vo) throws Exception {
        return update("moaCardDAO.uptCardAtmPwd", vo);
    }

    public MoaCardReqVO getCardAtmPwd(MoaCardReqVO vo) throws Exception {
        return (MoaCardReqVO) selectByPk("moaCardDAO.getCardAtmPwd", vo);
    }

    public int getCardPwdconfirm(MoaCardReqVO vo) throws Exception {
        return (int) selectByPk("moaCardDAO.getCardPwdconfirm", vo);
    }

    public String getCardUserNm(MoaCardReqVO vo) throws Exception {
        return (String) selectByPk("moaCardDAO.getCardUserNm", vo);
    }

    public void plusCardPoint(MoaCardReqVO vo) throws Exception {
        insert("moaCardDAO.plusCardPoint", vo);
    }

    public void pointCash(String userEmail) throws Exception {
        update("moaCardDAO.pointCash", userEmail);
    }

    public String getCardNo(MoaCardReqVO vo) throws Exception {
        return (String) selectByPk("moaCardDAO.getCardNo", vo);
    }
    public int setCardNo(MoaCardReqVO vo) throws Exception {
        return update("moaCardDAO.setCardNo", vo);
    }

    public void backCardPoint(String userEmail) throws Exception{
        delete("moaCardDAO.backCardPoint" , userEmail);
    }

    public void backPointCash(String userEmail) throws Exception{
        update("moaCardDAO.backPointCash" , userEmail);
    }

    public int userAmountCheck(MoaCardReqVO vo) throws Exception{
        return (int) selectByPk("moaCardDAO.userAmountCheck", vo);
    }

    public CmeRealTimeCoinPriceResVO getMoaCoinInfo(String curcyCd) throws Exception{
        return (CmeRealTimeCoinPriceResVO) selectByPk("moaCardDAO.getMoaCoinInfo" , curcyCd);
    }
}
