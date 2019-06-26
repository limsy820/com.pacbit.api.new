package com.bitkrx.api.sample.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bitkrx.api.sample.vo.bataMailVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;

@Repository
public class SampleDAO extends CmeComAbstractDAO  {
    
    public String selectBoard()throws Exception{
        
        return (String)selectByPk("SampleDAO.selectBoard", "");
    }
    
    public String selectMarket()throws Exception{
        
        return (String)selectByPk("SampleDAO.selectMarket", "");
    }

    public String selectBitkrx()throws Exception{
    
    	return (String)selectByPk("SampleDAO.selectBitkrx", "");
    }
    
    public List<bataMailVO> getMailList(bataMailVO vo)throws Exception{
        
    	return list ("SampleDAO.getMailList", vo);
    }
    
    public int updateMailSend(String str)throws Exception{
        
    	return update ("SampleDAO.updateMailSend", str);
    }
    
    public String getMilliTime() throws Exception {
    	
    	return (String)selectByPk("SampleDAO.getMilliTime", null); 
    }

    public String getUserName(String userEmail) throws Exception{
        return (String)selectByPk("SampleDAO.getUserName",userEmail);
    }

    public String getDateTime() throws Exception{
        return (String)selectByPk("SampleDAO.getDateTime", null);
    }
}
