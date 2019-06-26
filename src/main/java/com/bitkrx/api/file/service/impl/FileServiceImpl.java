package com.bitkrx.api.file.service.impl;

import com.bitkrx.api.auth.vo.SubmitCertVO;
import com.bitkrx.api.file.dao.FileDAO;
import com.bitkrx.api.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileDAO fileDAO;

    @Override
    public String getFileKey() throws Exception {
        return fileDAO.getFileKey();
    }

    @Override
    public void insUptFileInfo(Map<String, String> map) throws Exception {

        fileDAO.insUptFileInfo(map);
    }

    @Override
    public String selectFileKey(String userEmail) throws Exception {

        return fileDAO.selectFileKey(userEmail);
    }

    @Override
    public String getCertFileKey() throws Exception {
        return fileDAO.getCertFileKey();
    }

    @Override
    public void insUptCertFileInfo(SubmitCertVO vo) throws Exception {
        fileDAO.insUptCertFileInfo(vo);
    }

    @Override
    public String selectCertFileKey(String userEmail) throws Exception {
        return fileDAO.selectCertFileKey(userEmail);
    }

    @Override
    public List<SubmitCertVO> selectCertList(SubmitCertVO vo) throws Exception {
        return fileDAO.selectCertList(vo);
    }

    @Override
    public int getCertList(SubmitCertVO vo) throws Exception {
        return fileDAO.getCertList(vo);
    }

    @Override
    public List<SubmitCertVO> getCertInfo(SubmitCertVO vo) throws Exception {
        return fileDAO.getCertInfo(vo);
    }

    @Override
    public void insCertInfo(SubmitCertVO vo) throws Exception {
        fileDAO.insCertInfo(vo);
    }

    @Override
    public int uptCertInfo(SubmitCertVO vo) throws Exception {
        return fileDAO.uptCertInfo(vo);
    }

    @Override
    public int getEmailCnt(String userEmail) throws Exception {
        return fileDAO.getEmailCnt(userEmail);
    }

    @Override
    public String getRegDt(SubmitCertVO vo) throws Exception {
        return fileDAO.getRegDt(vo);
    }
}
