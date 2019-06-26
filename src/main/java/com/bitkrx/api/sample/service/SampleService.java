package com.bitkrx.api.sample.service;

import java.util.List;

import com.bitkrx.api.sample.vo.bataMailVO;

public interface SampleService {
	public List<bataMailVO> getMailList(bataMailVO vo)throws Exception;
    
    public int updateMailSend(String str)throws Exception;
    
    public String getMilliTime() throws Exception;
    public String getUserName(String userEmail) throws Exception;

    public String getDateTime() throws Exception;
}
