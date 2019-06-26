package com.bitkrx.api.file.service;

import com.bitkrx.api.auth.vo.SubmitCertVO;

import java.util.Map;
import java.util.List;

public interface FileService {

    public String getFileKey() throws Exception;

    public void insUptFileInfo(Map<String, String> map) throws Exception;

    public String selectFileKey(String userEmail) throws Exception;

    public String getCertFileKey() throws Exception;

    public void insUptCertFileInfo(SubmitCertVO vo) throws Exception;

    public String selectCertFileKey(String userEmail) throws Exception;

    public List<SubmitCertVO> selectCertList(SubmitCertVO vo) throws Exception;

    public int getCertList(SubmitCertVO vo) throws Exception;

    public List<SubmitCertVO> getCertInfo(SubmitCertVO vo) throws Exception;

    public void insCertInfo(SubmitCertVO vo) throws Exception;

    public int uptCertInfo(SubmitCertVO vo) throws Exception;

    public int getEmailCnt(String userEmail) throws Exception;

    public String getRegDt(SubmitCertVO vo) throws Exception;
}
