package com.bitkrx.config.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.logging.CmeCommonLogger;
import com.bitkrx.config.vo.MailInfoVO;


@SuppressWarnings("deprecation")
@ComponentScan("com.bitkrx")
@Service
public class MailSenderImpl {
    protected CmeCommonLogger log = new CmeCommonLogger(this.getClass());
    
    @Autowired 
    JavaMailSender javaMailSender;       
    
    @Autowired
    VelocityEngine getVelocityEngine;
    
    public CmeResultVO sendEmail(MailInfoVO mailVO) {
        
        CmeResultVO vo = new CmeResultVO();
        
        
        log.DebugLog("sendMail Start");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        getVelocityEngine.init();
      
        try {
 
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
 
            mimeMessageHelper.setSubject(mailVO.getMailSubject());
            mimeMessageHelper.setFrom(mailVO.getMailFrom());
            mimeMessageHelper.setTo(mailVO.getMailTo());
            mailVO.setMailContent(
            		VelocityEngineUtils.mergeTemplateIntoString(
            				getVelocityEngine, "/templates/"+mailVO.getMailTemplateForm(),"UTF-8", mailVO.getModel()));
            mimeMessageHelper.setText(mailVO.getMailContent(), true);
 
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            
            vo.setResultCode(1);
            vo.setResultMsg("정상처리되었습니다.");
        } catch (MessagingException e) {
            
            e.printStackTrace();
            vo.setResultCode(-1);
            vo.setResultMsg(e.getMessage());
            return vo;
        }
        
        return vo;
    }
    

}
