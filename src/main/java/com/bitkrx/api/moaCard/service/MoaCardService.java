package com.bitkrx.api.moaCard.service;

import com.bitkrx.api.main.vo.CmeRealTimeCoinPriceResVO;
import com.bitkrx.api.moaCard.vo.*;
import com.bitkrx.api.trade.vo.CmeTradeReqVO;

import java.util.List;

public interface MoaCardService {

    public int userReqCardCnt(String userEmail) throws Exception;

    public void insCardReqInfo(MoaCardReqVO vo) throws Exception;

    public List<MoaCardReqListVO> selectUserReqList(MoaCardReqListVO vo) throws Exception;

    public MoaCardReqListVO selectUserCard(MoaCardReqListVO vo) throws Exception;

    public int uptCardRegComp(MoaCardReqVO vo) throws Exception;

    public int uptCardRegBack(MoaCardReqVO vo) throws Exception;

    public CardReqPayInfoVO cardReqpayInfo() throws Exception;

    public String getOrderIdKey() throws Exception;

    public String getStatusValue(String order_id) throws Exception;

    public String getUserNation(String userEmail) throws Exception;

    public int uptCardSendInfo(MoaCardReqVO vo) throws Exception;

    public int getCardSendInfoCnt(MoaCardReqVO vo) throws Exception;

    public int uptCardSendDt(MoaCardReqVO vo) throws Exception;

    public CardBankInfoVO selectCardBankInfo(String cardNum) throws Exception;

    public int selectUserRegInfo(MoaCardReqVO vo) throws Exception;

    public int cardReqCancel(String orderId) throws Exception;

    public MoaCardReqVO selectUserMobile(String orderId) throws Exception;

    public int uptCardAtmPwd(MoaCardReqVO vo) throws Exception;

    public MoaCardReqVO getCardAtmPwd(MoaCardReqVO vo) throws Exception;

    public int getCardPwdconfirm(MoaCardReqVO vo) throws Exception;

    public String getCardUserNm(MoaCardReqVO vo) throws Exception;

    public void plusCardPoint(MoaCardReqVO vo) throws Exception;

    public void pointCash(String userEmail) throws Exception;

    public String getCardNo(MoaCardReqVO vo) throws Exception;

    public int setCardNo(MoaCardReqVO vo) throws Exception;

    public void backCardPoint(String userEmail) throws Exception;

    public void backPointCash(String userEmail) throws Exception;

    public int userAmountCheck(MoaCardReqVO vo) throws Exception;

    public CmeRealTimeCoinPriceResVO getMoaCoinInfo(String curcyCd) throws Exception;
}
