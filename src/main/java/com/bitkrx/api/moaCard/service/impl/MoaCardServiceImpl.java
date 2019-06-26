package com.bitkrx.api.moaCard.service.impl;

import com.bitkrx.api.main.vo.CmeRealTimeCoinPriceResVO;
import com.bitkrx.api.moaCard.dao.MoaCardDAO;
import com.bitkrx.api.moaCard.service.MoaCardService;
import com.bitkrx.api.moaCard.vo.*;
import com.bitkrx.api.trade.vo.CmeTradeReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoaCardServiceImpl implements MoaCardService {

    @Autowired
    MoaCardDAO moaCardDAO;

    public int userReqCardCnt(String userEmail) throws Exception {

        return moaCardDAO.userReqCardCnt(userEmail);
    }

    public void insCardReqInfo(MoaCardReqVO vo) throws Exception {

        moaCardDAO.insCardReqInfo(vo);
    }

    public List<MoaCardReqListVO> selectUserReqList(MoaCardReqListVO vo) throws Exception {

        return moaCardDAO.selectUserReqList(vo);
    }

    public int uptCardRegComp(MoaCardReqVO vo) throws Exception {

        return moaCardDAO.uptCardRegComp(vo);
    }

    public int uptCardRegBack(MoaCardReqVO vo) throws Exception {

        return moaCardDAO.uptCardRegBack(vo);
    }

    public MoaCardReqListVO selectUserCard(MoaCardReqListVO vo) throws Exception {

        return moaCardDAO.selectUserCard(vo);
    }

    public CardReqPayInfoVO cardReqpayInfo() throws Exception {

        return moaCardDAO.cardReqpayInfo();
    }

    public String getOrderIdKey() throws Exception {

        return moaCardDAO.getOrderIdKey();
    }

    public String getStatusValue(String order_id) throws Exception {

        return moaCardDAO.getStatusValue(order_id);
    }

    public String getUserNation(String userEmail) throws Exception{
        return moaCardDAO.getUserNation(userEmail);
    }

    public int uptCardSendInfo(MoaCardReqVO vo) throws Exception {
        return moaCardDAO.uptCardSendInfo(vo);
    }

    public int getCardSendInfoCnt(MoaCardReqVO vo) throws Exception {
        return moaCardDAO.getCardSendInfoCnt(vo);
    }

    public int uptCardSendDt(MoaCardReqVO vo) throws Exception {
        return moaCardDAO.uptCardSendDt(vo);
    }

    public CardBankInfoVO selectCardBankInfo(String cardNum) throws Exception {
        return moaCardDAO.selectCardBankInfo(cardNum);
    }

    public int selectUserRegInfo(MoaCardReqVO vo) throws Exception {
        return moaCardDAO.selectUserRegInfo(vo);
    }

    public int cardReqCancel(String orderId) throws Exception {
        return moaCardDAO.cardReqCancel(orderId);
    }

    public MoaCardReqVO selectUserMobile(String orderId) throws Exception {
        return moaCardDAO.selectUserMobile(orderId);
    }

    public int uptCardAtmPwd(MoaCardReqVO vo) throws Exception {
        return moaCardDAO.uptCardAtmPwd(vo);
    }

    public MoaCardReqVO getCardAtmPwd(MoaCardReqVO vo) throws Exception {
        return moaCardDAO.getCardAtmPwd(vo);
    }

    public int getCardPwdconfirm(MoaCardReqVO vo) throws Exception {
        return moaCardDAO.getCardPwdconfirm(vo);
    }

    public String getCardUserNm(MoaCardReqVO vo) throws Exception {
        return moaCardDAO.getCardUserNm(vo);
    }

    public void plusCardPoint(MoaCardReqVO vo) throws Exception{
        moaCardDAO.plusCardPoint(vo);
    }

    public void pointCash(String userEmail) throws Exception{
        moaCardDAO.pointCash(userEmail);
    }

    public String getCardNo(MoaCardReqVO vo) throws Exception {
        return moaCardDAO.getCardNo(vo);
    }

    public int setCardNo(MoaCardReqVO vo) throws Exception {
        return moaCardDAO.setCardNo(vo);
    }

    public void backCardPoint(String userEmail) throws Exception{
        moaCardDAO.backCardPoint(userEmail);
    }

    public void backPointCash(String userEmail) throws Exception{
        moaCardDAO.backPointCash(userEmail);
    }

    public int userAmountCheck(MoaCardReqVO vo) throws Exception{
        return moaCardDAO.userAmountCheck(vo);
    }

    public CmeRealTimeCoinPriceResVO getMoaCoinInfo(String curcyCd) throws Exception{
        return moaCardDAO.getMoaCoinInfo(curcyCd);
    }
}
