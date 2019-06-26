package com.bitkrx.api.sample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitkrx.api.sample.dao.SampleDAO;
import com.bitkrx.api.sample.service.SampleService;
import com.bitkrx.api.sample.vo.bataMailVO;
import com.bitkrx.config.annotation.CommonDataSource;
import com.bitkrx.config.dbinfo.DataSourceType;

@Service
public class SampleServiceImpl implements SampleService {

    @Autowired
    SampleDAO sampleDAO;


	public List<bataMailVO> getMailList(bataMailVO vo)throws Exception{
        
    	return sampleDAO.getMailList(vo);
    }
    public int updateMailSend(String str)throws Exception{
        
    	return sampleDAO.updateMailSend(str);
    }
    
    public String getMilliTime() throws Exception{
    	
    	return sampleDAO.getMilliTime();
    }

    public String getUserName(String userEmail) throws Exception{
	    return sampleDAO.getUserName(userEmail);
    }

    public String getDateTime() throws Exception{
	    return sampleDAO.getDateTime();
    }
}
