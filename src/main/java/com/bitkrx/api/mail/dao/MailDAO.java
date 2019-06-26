package com.bitkrx.api.mail.dao;

import com.bitkrx.api.mail.vo.MailVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MailDAO extends CmeComAbstractDAO {

    public List<MailVO> SendAdminAllMail(MailVO mvo) throws Exception{
        return list("menuDAO.SendAdminAllMail" , mvo);
    }

}
