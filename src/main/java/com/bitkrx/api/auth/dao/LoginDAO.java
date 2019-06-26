
package com.bitkrx.api.auth.dao;

import java.util.List;
import java.util.Map;

import com.bitkrx.api.auth.vo.*;
import org.springframework.stereotype.Repository;

import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;

@Repository("loginDAO")
public class LoginDAO extends CmeComAbstractDAO{

    public LoginResVO login(CmeLoginVO vo) {

        return  (LoginResVO) selectByPk("loginDAO.login", vo);
    }

    public LoginResVO pinlogin(CmeLoginVO vo) {
        return (LoginResVO) selectByPk("loginDAO.pinlogin" , vo);
    }

    public LoginResVO fingerlogin(CmeLoginVO vo){ return (LoginResVO) selectByPk("loginDAO.fingerlogin" , vo); }

    public LoginResVO getUseYn(CmeLoginVO vo) {
        return (LoginResVO) selectByPk("loginDAO.getUseYn" , vo);
    }

    public int isBlockYn(String userEmail) throws Exception {

        return (int) selectByPk ("loginDAO.isBlockYn", userEmail);
    }

    public UserAuthVO getUserAuth(CmeLoginVO vo) {

        return (UserAuthVO) selectByPk("loginDAO.getUserAuth", vo);
    }

    public String getUserMobile(String str) {

        return (String) selectByPk("loginDAO.getUserMobile", str);
    }

    public String getUserPuchCd(String str) {

        return (String) selectByPk("loginDAO.getUserPuchCd", str);
    }

    public int getEmailCnt(CmeLoginVO vo) {

        return (int) selectByPk("loginDAO.getEmailCnt", vo);
    }

    public void INS10171023(Map map) throws Exception {

        selectByPk("loginDAO.INS10171023", map);
    }

    public void INS10171024(Map map) throws Exception {

        selectByPk("loginDAO.INS10171024", map);
    }

    public String getBlckKey(String str) throws Exception {

        return (String) selectByPk("loginDAO.getBlckKey", str);
    }

    public void INS10171025(Map map) throws Exception {

        selectByPk("loginDAO.INS10171025", map);
    }

    public void INS10171020(Map map) throws Exception {

        update("loginDAO.INSUPT10171020", map);
    }

    public CmeResultVO sendSmsAuthCode(CmeLoginVO vo) throws Exception {

        CmeResultVO res = new CmeResultVO();

        convertProc("loginDAO.sendSmsAuthCode", vo);

        res.setProceduresResult(vo.getRESULT());

        return res;
    }

    public int sendSmsAuthCode2(CmeLoginVO vo) throws Exception{
        return (int) insert("loginDAO.sendSmsAuthCode2" , vo);
    }

    public String SmsAuthCodeVertify(String userEmail) throws Exception {

        return (String) selectByPk("loginDAO.SmsAuthCodeVertify", userEmail);
    }

    public List<CmeRcmdCode> getNatnCode() throws Exception{
        return list("loginDAO.getNatnCode", null);
    }

    public List<CmeRcmdCode> getBrhCode(String natnCode) throws Exception {
        return list("loginDAO.getBrhCode" , natnCode);
    }

    public List<CmeRcmdCode> getRcmdCode(String brhCode) throws Exception{
        return list("loginDAO.getRcmdCode" , brhCode);
    }

    public CheckIpVO getUserIp(String userEmail) throws Exception{
        return (CheckIpVO) selectByPk("loginDAO.getUserIp", userEmail);
    }

    public String SmsAuthCodeCert(String userEmail) throws Exception{
        return (String) selectByPk("loginDAO.SmsAuthCodeCert", userEmail);
    }

    public String SmsAuthCodeCert2(String userEmail) throws Exception{
        return (String) selectByPk("loginDAO.SmsAuthCodeCert2" , userEmail);
    }

    public String getHTSUserIp(String userEmail) throws Exception{
        return (String) selectByPk("loginDAO.getHTSUserIp", userEmail);
    }

    public String mtsDeviceCode(String userEmail) throws Exception{
        return (String) selectByPk("loginDAO.mtsDeviceCode", userEmail);
    }

    public int pinNoCheck(CmeLoginVO vo) throws Exception{
        return (int) selectByPk("loginDAO.pinNoCheck", vo);
    }

    public int InsertPinNo(CmeLoginVO vo) throws Exception{
        return update("loginDAO.InsertPinNo", vo);
    }

    public int pinNoReset(CmeLoginVO vo) throws Exception{
        return update("loginDAO.pinNoReset", vo);
    }

    public int pinLoginCheck(CmeLoginVO vo) throws Exception{
        return (int) selectByPk("loginDAO.pinLoginCheck", vo);
    }

    public int fingerLoginCheck(CmeLoginVO vo) throws Exception{
        return (int) selectByPk("loginDAO.fingerLoginCheck" , vo);
    }

    public int pinUserCheck(CmeLoginVO vo) throws Exception{
        return (int) selectByPk("loginDAO.pinUserCheck", vo);
    }

    public int deviceCheck(CmeLoginVO vo) throws Exception{
        return (int) selectByPk("loginDAO.deviceCheck", vo);
    }

    public int deviceSubCheck(CmeLoginVO vo) throws Exception{
        return (int) selectByPk("loginDAO.deviceCheck", vo);
    }

    public int uptPinInfo(CmeLoginVO vo) throws Exception{
        return update("loginDAO.uptPinInfo", vo);
    }

    public int getPwdCheck(CmeLoginVO vo) throws Exception{
        return (int)selectByPk("loginDAO.getPwdCheck", vo);
    }

    public LoginResVO getUserAuthInfo(String userEmail) throws Exception{
        return (LoginResVO) selectByPk("loginDAO.getUserAuthInfo" , userEmail);
    }

    public int getFingetCheck(CmeLoginVO vo) throws Exception{
        return (int) selectByPk("loginDAO.getFingetCheck" , vo);
    }

    public int InsFingerInfo(CmeLoginVO vo) throws Exception{
        return update("loginDAO.InsFingerInfo" , vo);
    }

    public int getFingerCheckDv(CmeLoginVO vo) throws Exception{
        return (int) selectByPk("loginDAO.getFingerCheckDv" , vo);
    }

    public int getFingerCheckEmail(CmeLoginVO vo) throws Exception{
        return (int) selectByPk("loginDAO.getFingerCheckEmail" , vo);
    }

    public int fingerReset(CmeLoginVO vo) throws Exception{
        return update("loginDAO.fingerReset", vo);
    }

    public int uptFingerInfo(CmeLoginVO vo) throws Exception{
        return update("loginDAO.uptFingerInfo", vo);
    }
}
