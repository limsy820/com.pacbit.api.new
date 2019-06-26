package com.bitkrx.api.file.dao;

import com.bitkrx.api.auth.vo.SubmitCertVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.List;
@Repository
public class FileDAO extends CmeComAbstractDAO {

    public String getFileKey() throws Exception {

        return (String) selectByPk("fileDAO.getFileKey", null);
    }

    public void insUptFileInfo(Map<String, String> map) throws Exception {

        insert("fileDAO.insUptFileInfo", map);
    }

    public String selectFileKey(String userEmail) throws Exception {

        return (String) selectByPk("fileDAO.selectFileKey", userEmail);
    }

    public String getCertFileKey() throws Exception {
        return (String) selectByPk("fileDAO.getCertFileKey", null);
    }

    public void insUptCertFileInfo(SubmitCertVO vo) throws Exception{
        insert("fileDAO.insUptCertFileInfo" , vo);
    }

    public String selectCertFileKey(String userEmail) throws Exception{
        return (String) selectByPk("fileDAO.selectCertFileKey", userEmail);
    }

    public List<SubmitCertVO> selectCertList(SubmitCertVO vo) throws Exception{
        return list("fileDAO.selectCertList", vo);
    }

    public int getCertList(SubmitCertVO vo) throws Exception {
        return (int) selectByPk("fileDAO.getCertList", vo);
    }

    public List<SubmitCertVO> getCertInfo(SubmitCertVO vo) throws Exception {
        return list("fileDAO.getCeretInfo", vo);
    }

    public void insCertInfo(SubmitCertVO vo) throws Exception {
        insert("fileDAO.insCertInfo", vo);
    }

    public int uptCertInfo(SubmitCertVO vo) throws Exception {
        return update("fileDAO.uptCertInfo", vo);
    }

    public int getEmailCnt(String userEmail) throws Exception {
        return (int)selectByPk("fileDAO.getEmailCnt" , userEmail);
    }

    public String getRegDt(SubmitCertVO vo) throws Exception {
        return (String)selectByPk("fileDAO.getRegDt", vo);
    }
}
