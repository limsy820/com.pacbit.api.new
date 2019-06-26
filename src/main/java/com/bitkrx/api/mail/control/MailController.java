package com.bitkrx.api.mail.control;

import com.bitkrx.api.auth.vo.SendEmailCustVO;
import com.bitkrx.api.auth.vo.SubmitCertVO;
import com.bitkrx.api.auth.vo.UserAuthVO;
import com.bitkrx.api.board.Service.BoardService;
import com.bitkrx.api.board.vo.BoardVO;
import com.bitkrx.api.common.vo.CommonExchgInfoVO;
import com.bitkrx.api.mail.service.MailApiService;
import com.bitkrx.api.mail.vo.MailVO;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.api.user.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/bt")
public class MailController extends CmeDefaultExtendController {

    @Autowired
    MailApiService mailApiService;

    @Autowired
    BoardService boardService;

    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    private LocaleResolver localeResolver;

    @RequestMapping(value = "/admin/All/Sendmail.dm")
    @ResponseBody public CmeResultVO SendAllmail(@ModelAttribute SendEmailCustVO vo, MailVO mvo, BoardVO bvo, HttpServletRequest request, HttpServletResponse response) throws Exception{

        Map<String, Object> resultMap = new HashMap<String, Object>();
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");
        //setLocale(request, response);

        List<MailVO> list = mailApiService.SendAdminAllMail(mvo);

        if("".equals(bvo.getRegUser())){
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("회원이메일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(bvo.getContentId())){
            resultMap.put("failCd", "998");
            resultVO.setResultMsg("게시글 번호가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        bvo = boardService.selectQuestion(bvo);


        if(bvo != null) {
            int cnt = 0;

            //for(int i=0; i<list.size(); i++){

            for(MailVO mailVO : list) {

                cnt++;
                vo.setUserEmail(mailVO.getUser_email());
                String lang = userService.getAdminLangCd(mailVO.getUser_email());

                if("NATN000000000000001".equals(lang)){
                    lang = "ko";
                }else{
                    lang = "en";
                }

                setAdminLocale(request,response,lang);

                resultMap.put("lang",lang);
                resultMap.put("mailTo",vo.getUserEmail());
                resultMap.put("clientCd",vo.getClientCd());
                resultMap.put("user_email", vo.getUserEmail());
                resultMap.put("content_id",bvo.getContentId());
                resultMap.put("board_title",bvo.getBoardTitle());
                resultMap.put("reg_user",bvo.getRegUser());
                resultMap.put("cat_name",bvo.getCatName());
                resultMap.put("reg_date",bvo.getRegDt());
                resultMap.put("user_name",bvo.getUserName());
                resultMap.put("content_msg",bvo.getContentMsg());
                mailApiService.SendAllMail(request,resultMap);
            }

            if(cnt != 0){
                resultVO.setResultMsg("리스트 출력 성공");
            }else{
                resultMap.put("failCd", "997");
                resultVO.setResultMsg("리스트 출력 실패");
            }

        } else {
            resultMap.put("failCd", "996");
            resultVO.setResultMsg("해당하는 글이 없습니다.");
        }
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/mail/CertSendmail.dm")
    @ResponseBody public CmeResultVO CertSendmail(@ModelAttribute SubmitCertVO vo,  HttpServletRequest request, HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("failCd" , "");

        setLocale(request,response);
        if("".equals(vo.getUserEmail())){
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("회원이메일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String lang = userService.getUserLangCd(vo.getUserEmail());

        resultMap.put("lang", lang);
        resultMap.put("mailTo",vo.getUserEmail());
        resultMap.put("regDt", vo.getRegDt());
        resultMap.put("certGrade", vo.getCertGrade());
        resultMap.put("certSubGrade", vo.getCertSubGrade());
        resultMap.put("certMsg", vo.getCertMsg());

        mailApiService.CertSendmail(request , resultMap);
        resultVO.setResultMsg("메일 전송 성공");
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/mail/commonCertMail.dm")
    @ResponseBody public CmeResultVO commonCertMail(@ModelAttribute SubmitCertVO vo,  HttpServletRequest request, HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("failCd" , "");

        //setLocale(request,response);
        Locale locale = new Locale(vo.getLang());
        localeResolver.setLocale(request, response, locale);

        if("".equals(vo.getUserEmail())){
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("회원 이메일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if("".equals(vo.getLang())){
            resultMap.put("failCd", "998");
            resultVO.setResultMsg("클라이언트 언어가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        resultMap.put("lang", vo.getLang());
        resultMap.put("user_email",vo.getUserEmail());

//        resultMap.put("lang", vo.getLang());
        resultMap.put("mailTo",vo.getUserEmail());
//        resultMap.put("regDt", vo.getRegDt());
//        resultMap.put("certGrade", vo.getCertGrade());
//        resultMap.put("certSubGrade", vo.getCertSubGrade());
//        resultMap.put("certMsg", vo.getCertMsg());


        try {
            mailApiService.commonCertMail(request , resultMap);
        } catch (Exception e){
            resultMap.put("failCd", "997");
            resultVO.setResultMsg("메일 전송중 오류가 발생하였습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        //인증 insert 프로시져
        UserAuthVO uvo = new UserAuthVO();
        uvo.setUserEmail(vo.getUserEmail());
        uvo.setEmailCertYn("N");
        uvo.setSmsCertYn("N");

        try {
            uvo = userService.INSUPT10171021(uvo);
        } catch (Exception e){
            resultMap.put("failCd", "996");
            resultVO.setResultMsg("인증내역을 쌓는 도중 오류가 발생하였습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        resultVO.setResultMsg("common메일 전송 성공");
        resultVO.setData(resultMap);
        return resultVO;
    }
}
