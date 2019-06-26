/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.auth.control;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitkrx.api.auth.vo.*;
import com.bitkrx.api.file.service.FileService;
import com.bitkrx.api.mail.service.MailApiService;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.trade.vo.CmeTradeReqVO;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.util.SFTPUtil;
import com.bitkrx.config.util.Security;
import com.bitkrx.config.util.SeedXUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @프로젝트명 : com.bitkrx.api
 * @패키지 : com.bitkrx.api.auth.control
 * @클래스명 : com.bitkrx.api
 * @작성자 : (주)씨엠이소프트 문영민
 * @작성일 : 2018. 2. 5.
 */
@Controller
@RequestMapping(value = "/bt")
public class UserController extends CmeDefaultExtendController {

    @Autowired
    UserService userService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    FileService fileService;

    @Autowired
    MailApiService mailApiService;

    @Autowired
    SmsApiService smsApiService;

    @Autowired
    TradeService tradeService;

    @RequestMapping(value = "/selectUserList", method = RequestMethod.GET, produces = "application/text; charset=utf8")
    @ResponseBody
    public String selectUserMaskingList(HttpServletRequest req, HttpServletResponse res) throws Exception {
        //ModelAndView mav= new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();

        String value = req.getParameter("value");
        if (value == null || "".equals(value)) {
            map.put("resultCode", "0");
            map.put("resultMsg", "파라미터 값이 없습니다.");
            map.put("list", new ArrayList<UserMaskingVO>());
            //mav.addObject("data", map);
            //mav.setViewName("jsonView");
            String json = "";
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(map);
            //System.out.println(json);
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

            String result = "jsonCallback({\"reponse\":" + json + "})";
            return result;
            //return mav;
        }
        List<UserMaskingVO> list = userService.selectUserMaskingList(value);

        if (list.size() > 0) {
            map.put("resultCode", "1");
            map.put("resultMsg", "사용자 리스트 호출 완료");
            map.put("list", list);
        } else {
            map.put("resultCode", "0");
            map.put("resultMsg", "해당하는 사용자 리스트가 없습니다.");
            map.put("list", new ArrayList<UserMaskingVO>());
        }

        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        json = mapper.writeValueAsString(map);
        //System.out.println(json);
        json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);


        //mav.setViewName("jsonView");
        //return mav;

        //String  result = "jsonCallback({\"reponse\":\"" + json + "\"})";
        String result = "jsonCallback({\"reponse\":" + json + "})";

        return result;
    }

    @RequestMapping(value = "/getUserInfo.dm")
    @ResponseBody
    public CmeResultVO getUserInfo(UserInfoVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();

        resultVO.setData(userService.getUserInfo(vo));
        resultVO.setResultStrCode("000");

        return resultVO;

    }

    @RequestMapping(value = "/getCountryList.dm")
    @ResponseBody
    public CmeResultVO getCountryList(HttpServletRequest req, HttpServletResponse res) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", userService.getCountryList());
        resultVO.setData(map);
        resultVO.setResultStrCode("000");

        return resultVO;

    }

    @RequestMapping(value = "/getNatnBankList.dm")
    @ResponseBody
    public CmeResultVO getNatnBankList(UserInfoVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", userService.getNatnBankList(vo));
        resultVO.setData(map);
        resultVO.setResultStrCode("000");

        return resultVO;

    }


    @RequestMapping(value = "/uptUserAuthYn.dm")
    @ResponseBody
    public CmeResultVO uptUserAuthYn(UserAuthVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();

        if(vo.getUserEmail() == null || vo.getUserEmail() == ""){
            resultVO.setResultMsg("이메일값이 없습니다.");
            resultVO.setResultStrCode("-1");
        } else {
            //
            if(vo.getAccCertYn() != ""){
                userService.uptAccCertYn(vo);
                resultVO.setResultStrCode("000");
                System.out.println("acc:"+vo);

            }
           // else{
             //   if( vo.getKycCertYn() != "" && vo.getOtpCertYn() != "" ) {
                    vo = userService.INSUPT10171021(vo);
                    List list = (List) vo.getRESULT();
                    Map map = (Map) list.get(0);
                    String rtnCd = (String) map.get("RTN_CD");
                        if (rtnCd != null && rtnCd.equals("1")) {
                            resultVO.setResultStrCode("000");
                        } else {
                           resultVO.setResultStrCode("-1");
                        }
             //   }
            //}

            if(!"".equals(vo.getUserInfoYn())){
                userService.uptInfoYn(vo);
            }

            /*//certyn업데이트처리 22번
            if(vo.getSmsCertYn() != ""){
                userService.uptSmsCertYn(vo);
                resultVO.setResultStrCode("000");
                System.out.println("sms:"+vo);
            }

            if(vo.getKycCertYn() != ""){
                userService.uptKycCertYn(vo);
                resultVO.setResultStrCode("000");
                System.out.println("kyc:"+vo);
            }

            if(vo.getOtpCertYn() != ""){
                userService.uptOtpCertYn(vo);
                resultVO.setResultStrCode("000");
                System.out.println("otp:"+vo);
            }*/

//            if( vo.getKycCertYn() != "" && vo.getOtpCertYn() != "" ) {
//                vo = userService.INSUPT10171021(vo);
//                List list = (List) vo.getRESULT();
//                Map map = (Map) list.get(0);
//                String rtnCd = (String) map.get("RTN_CD");
//                if (rtnCd != null && rtnCd.equals("1")) {
//                    resultVO.setResultStrCode("000");
//                } else {
//                    resultVO.setResultStrCode("-1");
//                }
//            }

        }

        //userService.uptInfoYn(vo.getUserEmail());
        return resultVO;

    }

    @RequestMapping(value = "/uptUserBankInfo.dm")
    @ResponseBody
    public CmeResultVO uptUserAuthYn(UserBankInfoVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        vo = userService.INSUPT10171022(vo);

        List list = (List) vo.getRESULT();
        Map map = (Map) list.get(0);
        String rtnCd = (String) map.get("RTN_CD");

        resultVO.setResultStrCode("000");

        return resultVO;

    }


    @RequestMapping(value = "/userPwdChange.dm")
    @ResponseBody
    public CmeResultVO uptUserAuthYn(SendEmailCustVO vo, HttpServletRequest req, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("failCd" , "");
        resultVO.setResultMsg("정상 처리되었습니다.");
        setLocale(req, response);
        vo.setRegIp(getIp(req, vo.getRegIp()));

        if (vo.getUserEmail().equals("")) {
            resultVO.setResultMsg("이메일값이 없습니다.");
            resultVO.setResultStrCode("-1");
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

        System.out.println("패스워드 변경메일!~!");
        try{
            int res = userService.userPwdChange(req, vo);
            if (res > 0) {
                resultVO.setResultStrCode("000");
            } else {
                resultVO.setResultStrCode("-1");
                resultVO.setResultMsg("메일을 전송중 오류가 발생하였습니다.");
                resultMap.put("failCd", "997");
                resultVO.setData(resultMap);
                return resultVO;
            }
        } catch (Exception e){
            resultVO.setResultStrCode("-1");
            resultVO.setResultMsg("메일을 전송중 오류가 발생하였습니다.");
            resultMap.put("failCd", "997");
            resultVO.setData(resultMap);
            return resultVO;
        }
        resultVO.setData(resultMap);
        return resultVO;

    }

    @RequestMapping(value = "/auth/getLoginHistory.dm")
    public @ResponseBody
    CmeResultVO getLoginHistory(@ModelAttribute LoginHistoryVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> map = new HashMap<String, Object>();

        vo.setFirstIndex((vo.getPageIndex() - 1) * 10);
        vo.setRecordCountPerPage(vo.getPageUnit());

        map.put("list", userService.getLoginHistory(vo));
        int listCnt = userService.getLoginHistoryCnt(vo);
        map.put("listCnt", listCnt);
        map.put("pageSize", (listCnt - 1) / 10 + 1);
        map.put("pageIndex", vo.getPageIndex());
        map.put("pageUnit", vo.getPageUnit());

        resultVO.setData(map);
        resultVO.setResultMsg("거래내역 조회 성공");
        resultVO.setResultStrCode("000");

        return resultVO;
    }

    @RequestMapping(value = "/auth/uploadKycFile")
    public @ResponseBody
    CmeResultVO uploadKycFile(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();

        String fileExt = "jpg|jpeg|png|gif|bmp|JPG|JPEG|PNG|GIF|BMP";
        String userEmail = request.getParameter("userEmail");
        if (userEmail == null || "".equals(userEmail)) {
            resultVO.setResultStrCode("-1");
            resultVO.setResultMsg("이메일이 없습니다.");
            return resultVO;
        }
        long size = 10 * 1024 * 1024;  //파일 사이즈
        MultipartFile multipartFile1 = request.getFile("file1");
        MultipartFile multipartFile2 = request.getFile("file2");
        long size1 = multipartFile1.getSize();
        long size2 = multipartFile2.getSize();

        if (multipartFile1 == null || multipartFile2 == null) {
            resultVO.setResultStrCode("-1");
            resultVO.setResultMsg("파일이 없습니다.");
            return resultVO;
        }
        if (size1 > size || size2 > size) {
            resultVO.setResultStrCode("-1");
            resultVO.setResultMsg("10MB를 초과할 수 없습니다.");
            return resultVO;
        }

        /*String fileKey = "";
        fileKey = request.getParameter("fileId");
        if("".equals(fileKey) || fileKey == null){
            fileKey = fileService.getFileKey();
        }*/

        String fileKey = fileService.selectFileKey(userEmail);
        if (fileKey == null || "".equals(fileKey)) {
            fileKey = fileService.getFileKey();
        }

        //String fileKey = fileService.getFileKey();
        String fileName1 = "";
        String fileName2 = "";
        //String filePath = servletContext.getRealPath("/kyc/");
        String filePath = "/home/upload/kycBak/";
        String realfilePath = "/home/upload/kyc/";
        Iterator<String> iterator = request.getFileNames();
        MultipartFile multipartFile = null;
        File file;

        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
            if (!multipartFile.isEmpty()) {
                String name = multipartFile.getName();
                String fileName = multipartFile.getOriginalFilename();
//                System.out.println("------------- file start -------------");
//                System.out.println("name : " + name);
//                System.out.println("filename : " + multipartFile.getOriginalFilename());
//                System.out.println("size : " + multipartFile.getSize());
//                System.out.println("-------------- file end --------------\n");
                String realFileName = "";
                int i = fileName.lastIndexOf("."); // 파일 확장자 위치
                if (name.equals("file1")) {
                    realFileName = fileKey + "_1" + fileName.substring(i, fileName.length());  //현재시간과 확장자 합치기
                    fileName1 = realFileName;
                } else if (name.equals("file2")) {
                    realFileName = fileKey + "_2" + fileName.substring(i, fileName.length());  //현재시간과 확장자 합치기
                    fileName2 = realFileName;
                }

                if (!fileExt.contains(fileName.substring(i + 1, fileName.length()))) {
                    resultVO.setResultStrCode("-1");
                    resultVO.setResultMsg("이미지 파일(jpg, jpeg, png, gif, bmp)만 등록 가능합니다.");
                    return resultVO;
                }

                File oldFile = new File(filePath + "/" + multipartFile.getOriginalFilename());
                File newFile = new File(filePath + "/" + realFileName);

                oldFile.renameTo(newFile); // 파일명 변경

                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                multipartFile.transferTo(newFile);
                SFTPUtil sftpUtil = new SFTPUtil();
                sftpUtil.init(isOper());
                sftpUtil.upload(realfilePath, newFile);
                sftpUtil.disconnect();
            }

        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("fileId", fileKey);
        map.put("userEmail", userEmail);
        map.put("fileName1", fileName1);
        map.put("fileName2", fileName2);
        fileService.insUptFileInfo(map);

        resultVO.setResultStrCode("000");
        return resultVO;
    }


    @RequestMapping(value = "/auth/uploadKycFileOne")
    public @ResponseBody
    CmeResultVO uploadKycFileOne(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();

        String fileExt = "jpg|jpeg|png|gif|bmp|JPG|JPEG|PNG|GIF|BMP";
        String userEmail = request.getParameter("userEmail");
        if (userEmail == null || "".equals(userEmail)) {
            resultVO.setResultStrCode("-1");
            resultVO.setResultMsg("이메일이 없습니다.");
            return resultVO;
        }

        System.out.println("HTS KYC EMAIL:" + userEmail);

        String fileSn = request.getParameter("fileSn");
        if (fileSn == null) {
            resultVO.setResultStrCode("-1");
            resultVO.setResultMsg("파일번호가 없습니다.");
            return resultVO;
        } else if (!"1".equals(fileSn) && !"2".equals(fileSn)) {
            resultVO.setResultStrCode("-1");
            resultVO.setResultMsg("파일번호가 부정확합니다.");
            return resultVO;
        }

        long size = 10 * 1024 * 1024;  //파일 사이즈

        String fileKey = fileService.selectFileKey(userEmail);
        if (fileKey == null || "".equals(fileKey)) {
            fileKey = fileService.getFileKey();
        }

        String fileName1 = "";
        String fileName2 = "";
        //String filePath = servletContext.getRealPath("/kyc/");
        String filePath = "/home/upload/kycBak/";
        String realfilePath = "/home/upload/kyc/";
        Iterator<String> iterator = request.getFileNames();
        MultipartFile multipartFile = null;
        File file;

        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
            if (!multipartFile.isEmpty()) {
                String name = multipartFile.getName();
                String fileName = multipartFile.getOriginalFilename();
                System.out.println("------------- file start -------------");
                System.out.println("name : " + name);
                System.out.println("filename : " + multipartFile.getOriginalFilename());
                System.out.println("size : " + multipartFile.getSize());
                System.out.println("-------------- file end --------------\n");
                String realFileName = "";
                int i = fileName.lastIndexOf("."); // 파일 확장자 위치

                if (multipartFile.getSize() > size) {
                    resultVO.setResultStrCode("-1");
                    resultVO.setResultMsg("10MB를 초과할 수 없습니다.");
                    return resultVO;
                }

                realFileName = fileKey + "_" + fileSn + fileName.substring(i, fileName.length());  //현재시간과 확장자 합치기
                if ("1".equals(fileSn)) {
                    fileName1 = realFileName;
                } else {
                    fileName2 = realFileName;
                }

                if (!fileExt.contains(fileName.substring(i + 1, fileName.length()))) {
                    resultVO.setResultStrCode("-1");
                    resultVO.setResultMsg("이미지 파일(jpg,jpeg, png, gif, bmp)만 등록 가능합니다.");
                    return resultVO;
                }

                File oldFile = new File(filePath + "/" + multipartFile.getOriginalFilename());
                File newFile = new File(filePath + "/" + realFileName);

                oldFile.renameTo(newFile); // 파일명 변경

                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                multipartFile.transferTo(newFile);
                SFTPUtil sftpUtil = new SFTPUtil();
                sftpUtil.init(isOper());
                sftpUtil.upload(realfilePath, newFile);
                sftpUtil.disconnect();

            } else {

                resultVO.setResultStrCode("-1");
                resultVO.setResultMsg("파일이 없습니다.");
                return resultVO;

            }

        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("fileId", fileKey);
        map.put("userEmail", userEmail);
        map.put("fileName1", fileName1);
        map.put("fileName2", fileName2);
        fileService.insUptFileInfo(map);

        resultVO.setResultStrCode("000");
        return resultVO;
    }

    @RequestMapping(value = "/auth/uploadKycCertFile")
    public @ResponseBody
    CmeResultVO uploadKycCertFile(SubmitCertVO vo, MultipartHttpServletRequest request, HttpServletResponse response, HttpServletRequest req) throws Exception {
        String certMsg = new String(request.getParameterValues("certMsg")[0].getBytes("8859_1"),"utf-8");
        CmeResultVO resultVO = new CmeResultVO();
        vo.setCertMsg(certMsg);
        System.out.println("certMsg1:" + certMsg);

        // 셀렉트 박스(1-1 , 1-2) , 이메일 , 휴대폰번호 , 요청사항 , 신분증(file1) , 신분증 들고있는 본인 이미지(file2) , 기타 추가서류(file3)
        resultVO.setResultStrCode("000");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("failCd", "");

        int useEmail = fileService.getEmailCnt(vo.getUserEmail());

        String fileExt = "jpg|jpeg|png|gif|bmp|JPG|JPEG|PNG|GIF|BMP";
        String userEmail = request.getParameter("userEmail");
        if (userEmail == null || "".equals(userEmail)) {
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("회원이메일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (useEmail == 0) {
            resultMap.put("failCd", "992");
            resultVO.setResultMsg("가입된 이메일이 아닙니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (vo.getUserMobile() == null || "".equals(vo.getUserMobile())) {
            resultMap.put("failCd", "998");
            resultVO.setResultMsg("회원 전화번호가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (vo.getCertGrade() == null || "".equals(vo.getCertGrade())) {
            resultMap.put("failCd", "997");
            resultVO.setResultMsg("부모코드가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (vo.getCertSubGrade() == null || "".equals(vo.getCertSubGrade())) {
            resultMap.put("failCd", "996");
            resultVO.setResultMsg("자식코드가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }
        long size = 10 * 1024 * 1024;  //파일 사이즈
        MultipartFile multipartFile1 = request.getFile("file1");
        MultipartFile multipartFile2 = request.getFile("file2");
        MultipartFile multipartFile3 = request.getFile("file3");
        long size1 = multipartFile1.getSize();
        long size2 = multipartFile2.getSize();
        long size3 = 0;
        if (multipartFile3 != null) {
            size3 = multipartFile3.getSize();
        }

        if (multipartFile1 == null || multipartFile2 == null) {
            resultMap.put("failCd", "995");
            resultVO.setResultMsg("파일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }
        if (size1 > size || size2 > size || size3 > size) {
            resultMap.put("failCd", "994");
            resultVO.setResultMsg("10MB를 초과할 수 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String fileKey = fileService.getCertFileKey();
        String fileName1 = "";
        String fileName2 = "";
        String fileName3 = "";
        //String filePath = servletContext.getRealPath("/Cert/");
        String filePath = "/home/upload/certBak/";
        String realfilePath = "/home/upload/cert/";
        Iterator<String> iterator = request.getFileNames();
        MultipartFile multipartFile = null;
        File file;

        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
            if (!multipartFile.isEmpty()) {
                String name = multipartFile.getName();
                String fileName = multipartFile.getOriginalFilename();
                System.out.println("------------- file start -------------");
                System.out.println("name : " + name);
                System.out.println("filename : " + multipartFile.getOriginalFilename());
                System.out.println("size : " + multipartFile.getSize());
                System.out.println("-------------- file end --------------\n");
                String realFileName = "";
                int i = fileName.lastIndexOf("."); // 파일 확장자 위치
                if (name.equals("file1")) {
                    realFileName = fileKey + "_1" + fileName.substring(i, fileName.length());  //현재시간과 확장자 합치기
                    fileName1 = realFileName;
                } else if (name.equals("file2")) {
                    realFileName = fileKey + "_2" + fileName.substring(i, fileName.length());  //현재시간과 확장자 합치기
                    fileName2 = realFileName;
                } else if (name.equals("file3")) {
                    realFileName = fileKey + "_3" + fileName.substring(i, fileName.length());  //현재시간과 확장자 합치기
                    fileName3 = realFileName;
                }

                if (!fileExt.contains(fileName.substring(i + 1, fileName.length()))) {
                    resultMap.put("failCd", "993");
                    resultVO.setResultMsg("이미지 파일(jpg, jpeg, png, gif, bmp)만 등록 가능합니다.");
                    resultVO.setData(resultMap);
                    return resultVO;
                }

                File oldFile = new File(filePath + "/" + multipartFile.getOriginalFilename());
                File newFile = new File(filePath + "/" + realFileName);

                oldFile.renameTo(newFile); // 파일명 변경

                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                multipartFile.transferTo(newFile);
                SFTPUtil sftpUtil = new SFTPUtil();
                sftpUtil.init(isOper());
                sftpUtil.upload(realfilePath, newFile);
                sftpUtil.disconnect();
            }

        }

        vo.setCertCode(fileKey);
        vo.setCertYn("3");
        vo.setFileName1(fileName1);
        vo.setFileName2(fileName2);
        if (fileName3 != null) {
            vo.setFileName3(fileName3);
        }

        fileService.insUptCertFileInfo(vo);

        Map<String , Object> map = new HashMap<String , Object>();

        map.put("certCode", fileKey);
        map.put("userEmail", userEmail);
        map.put("userMobile", vo.getUserMobile());
        map.put("fileName1", fileName1);
        map.put("fileName2", fileName2);
        map.put("fileName3", fileName3);
        map.put("certMsg", vo.getCertMsg());
        map.put("certGrade", vo.getCertGrade());
        map.put("certSubGrade", vo.getCertSubGrade());
        map.put("certYn", vo.getCertYn());

        setLocale(request , response);
        String lang = userService.getUserLangCd(vo.getUserEmail());
        String regDt = fileService.getRegDt(vo);
        map.put("lang", lang);
        map.put("regDt", regDt);
        map.put("mailTo", vo.getUserEmail());
        mailApiService.CertSendmail(req, map);
        smsApiService.CertSms(req, map);

        System.out.println("email:" + vo.getUserEmail());
        System.out.println("phone:" + vo.getUserMobile());
        System.out.println("certGrade:" + vo.getCertGrade());
        System.out.println("certSubGrade:" + vo.getCertSubGrade());
        System.out.println("certMsg:" + vo.getCertMsg());
        System.out.println("certDt:" + vo.getCertDt());

        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/auth/uploadKycCertFileOne")
    public @ResponseBody
    CmeResultVO uploadKycCertFileOne(SubmitCertVO vo, MultipartHttpServletRequest request, HttpServletResponse response, HttpServletRequest req) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("failCd", "");

        String fileExt = "jpg|jpeg|png|gif|bmp|JPG|JPEG|PNG|GIF|BMP";
        String userEmail = request.getParameter("userEmail");
        String fileName1 = "";
        String fileName2 = "";
        String fileName3 = "";
        if (userEmail == null || "".equals(userEmail)) {
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("회원이메일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (vo.getCertCode() == null || "".equals(vo.getCertCode())) {
            resultMap.put("failCd", "992");
            resultVO.setResultMsg("파일Key가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        long size = 10 * 1024 * 1024;  //파일 사이즈

        String fileSn = request.getParameter("fileSn");
        if (fileSn == null) {
            resultMap.put("failCd", "997");
            resultVO.setResultMsg("파일번호가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (!"1".equals(fileSn) && !"2".equals(fileSn) && !"3".equals(fileSn)) {
            resultMap.put("failCd", "996");
            resultVO.setResultMsg("파일번호가 부정확합니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String fileKey = vo.getCertCode();

        //String filePath = servletContext.getRealPath("/Cert/");
        String filePath = "/home/upload/certBak/";
        String realfilePath = "/home/upload/cert/";
        Iterator<String> iterator = request.getFileNames();
        MultipartFile multipartFile = null;
        File file;

        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
            if (!multipartFile.isEmpty()) {
                String name = multipartFile.getName();
                String fileName = multipartFile.getOriginalFilename();
                System.out.println("------------- file start -------------");
                System.out.println("name : " + name);
                System.out.println("filename : " + multipartFile.getOriginalFilename());
                System.out.println("size : " + multipartFile.getSize());
                System.out.println("-------------- file end --------------\n");
                String realFileName = "";
                int i = fileName.lastIndexOf("."); // 파일 확장자 위치

                if (multipartFile.getSize() > size) {
                    resultMap.put("failCd", "995");
                    resultVO.setResultMsg("10MB를 초과할 수 없습니다.");
                    resultVO.setData(resultMap);
                    return resultVO;
                }

                realFileName = fileKey + "_" + fileSn + fileName.substring(i, fileName.length());  //현재시간과 확장자 합치기
                if ("1".equals(fileSn)) {
                    fileName1 = realFileName;
                } else if ("2".equals(fileSn)) {
                    fileName2 = realFileName;
                } else if ("3".equals(fileSn)) {
                    fileName3 = realFileName;
                }

                if (!fileExt.contains(fileName.substring(i + 1, fileName.length()))) {
                    resultMap.put("failCd", "994");
                    resultVO.setResultMsg("이미지 파일(jpg,jpeg, png, gif, bmp)만 등록 가능합니다.");
                    resultVO.setData(resultMap);
                    return resultVO;
                }
                File oldFile = new File(filePath + "/" + multipartFile.getOriginalFilename());
                File newFile = new File(filePath + "/" + realFileName);

                oldFile.renameTo(newFile); // 파일명 변경

                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                multipartFile.transferTo(newFile);
                SFTPUtil sftpUtil = new SFTPUtil();
                sftpUtil.init(isOper());
                sftpUtil.upload(realfilePath, newFile);
                sftpUtil.disconnect();

            } else {
                resultMap.put("failCd", "993");
                resultVO.setResultMsg("파일이 없습니다.");
                resultVO.setData(resultMap);
                return resultVO;

            }

            vo.setCertCode(fileKey);
            vo.setFileName1(fileName1);
            vo.setFileName2(fileName2);
            if (fileName3 != null) {
                vo.setFileName3(fileName3);
            }
            fileService.uptCertInfo(vo);
        }

        resultMap.put("certCode", fileKey);
        resultMap.put("userEmail", userEmail);
        resultMap.put("userMobile", vo.getUserMobile());
        resultMap.put("fileName1", fileName1);
        resultMap.put("fileName2", fileName2);
        resultMap.put("fileName3", fileName3);
        resultMap.put("certMsg", vo.getCertMsg());
        resultMap.put("certGrade", vo.getCertGrade());
        resultMap.put("certSubGrade", vo.getCertSubGrade());
        resultMap.put("certYn", vo.getCertYn());
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/auth/InsCertInfo")
    public @ResponseBody
    CmeResultVO InsCertInfo(SubmitCertVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("failCd", "");

        int useEmail = fileService.getEmailCnt(vo.getUserEmail());

        String userEmail = request.getParameter("userEmail");
        if (userEmail == null || "".equals(userEmail)) {
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("회원이메일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (useEmail == 0) {
            resultMap.put("failCd", "995");
            resultVO.setResultMsg("가입된 이메일이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (vo.getUserMobile() == null || "".equals(vo.getUserMobile())) {
            resultMap.put("failCd", "998");
            resultVO.setResultMsg("회원 전화번호가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (vo.getCertGrade() == null || "".equals(vo.getCertGrade())) {
            resultMap.put("failCd", "997");
            resultVO.setResultMsg("부모코드가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        } else if (vo.getCertSubGrade() == null || "".equals(vo.getCertSubGrade())) {
            resultMap.put("failCd", "996");
            resultVO.setResultMsg("자식코드가 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }
        String fileKey = fileService.getCertFileKey();
        vo.setCertCode(fileKey);
        vo.setCertYn("3");
        fileService.insCertInfo(vo);

        resultMap.put("userEmail", userEmail);
        resultMap.put("userMobile", vo.getUserMobile());
        resultMap.put("certMsg", vo.getCertMsg());
        resultMap.put("certGrade", vo.getCertGrade());
        resultMap.put("certSubGrade", vo.getCertSubGrade());
        resultMap.put("certYn", vo.getCertYn());
        resultMap.put("certCode", vo.getCertCode());

        setLocale(request, response);
        String lang = userService.getUserLangCd(vo.getUserEmail());
        String regDt = fileService.getRegDt(vo);
        resultMap.put("lang", lang);
        resultMap.put("regDt", regDt);
        resultMap.put("mailTo", vo.getUserEmail());
        mailApiService.CertSendmail(request, resultMap);
        smsApiService.CertSms(request, resultMap);
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/auth/getCertList.dm")
    public @ResponseBody
    CmeResultVO getCertList(SubmitCertVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        if ("".equals(vo.getUserEmail())) {
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("이메일값이 없습니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }

        vo.setFirstIndex((vo.getPageIndex() - 1) * 5);
        vo.setRecordCountPerPage(vo.getPageUnit());

        List<SubmitCertVO> list = fileService.selectCertList(vo);
        int listCnt = fileService.getCertList(vo);

        if (list == null || listCnt < 0) {
            resultMap.put("failCd", "998");
            resultVO.setResultMsg("리스트 출력 실패");
            resultVO.setData(resultMap);
            return resultVO;
        }


        resultMap.put("list", list);
        resultMap.put("listCnt", listCnt);
        resultMap.put("pageSize", (listCnt - 1) / 5 + 1);
        resultMap.put("pageIndex", vo.getPageIndex());
        resultMap.put("pageUnit", vo.getPageUnit());

        resultVO.setData(resultMap);
        resultVO.setResultMsg("리스트 조회 성공");
        return resultVO;
    }

    @RequestMapping(value = "/auth/getAuthCode.dm")
    public @ResponseBody
    CmeResultVO getAuthCode(AuthCodeListVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        List<AuthCodeListVO> list = null;
        if (!"".equals(vo.getCmmUpperCd())) {
            list = userService.getAuthChiCodeList(vo.getCmmUpperCd());
        } else {
            list = userService.getAuthParCodeList();
        }

        if (list == null || list.size() == 0) {
            resultMap.put("failCd", "998");
            resultVO.setResultMsg("리스트 출력 실패");
            resultVO.setData(resultMap);
            return resultVO;
        } else {
            resultMap.put("list", list);
        }

        resultVO.setData(resultMap);
        resultVO.setResultMsg("리스트 조회 성공");
        return resultVO;
    }


    @RequestMapping("/auth/getImageView.dm")
    public void getImageInf(
            Model model,
            @RequestParam(value = "fileNm", required = false) String fileNm,
            @RequestParam(value = "fileType", required = false) String fileType,
            HttpServletResponse response) throws Exception {

        /*if("".equals(fileNm)) {

        } else if("".equals(fileType)) {

        } else if(!"kyc".equals(fileType) && !"cert".equals(fileType)) {

        }
        */

        File file = new File("/home/upload/" + fileType + "/", fileNm);

        FileInputStream fis = null;
        new FileInputStream(file);

        BufferedInputStream in = null;
        ByteArrayOutputStream bStream = null;
        try {
            fis = new FileInputStream(file);
            in = new BufferedInputStream(fis);
            bStream = new ByteArrayOutputStream();
            int imgByte;
            while ((imgByte = in.read()) != -1) {
                bStream.write(imgByte);
            }

            String type = "";

            String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);

            if (ext != null && !"".equals(ext)) {
                if ("jpg".equals(ext.toLowerCase())) {
                    type = "image/jpeg";
                } else {
                    type = "image/" + ext.toLowerCase();
                }
                type = "image/" + ext.toLowerCase();

            } else {
                log.ViewWarnLog("Image fileType is null.");
            }

            response.setHeader("Content-Type", type);
            response.setContentLength(bStream.size());

            bStream.writeTo(response.getOutputStream());

            response.getOutputStream().flush();
            response.getOutputStream().close();

        } catch (Exception e) {
            log.ViewErrorLog(e.getMessage());// e.printStackTrace();
        } finally {
            if (bStream != null) {
                try {
                    bStream.close();
                } catch (Exception est) {
                    log.ViewErrorLog("IGNORED: " + est.getMessage());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ei) {
                    log.ViewErrorLog("IGNORED: " + ei.getMessage());
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception efis) {
                    log.ViewErrorLog("IGNORED: " + efis.getMessage());
                }
            }
        }

    }

    @RequestMapping(value = "/auth/mobileOverlapChk.dm")
    public @ResponseBody
    CmeResultVO mobileOverlapChk(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        String userMobile = request.getParameter("userMobile");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        if("".equals(userMobile) || userMobile == null){
            resultMap.put("failCd", "999");
            resultVO.setResultMsg("전화번호 확인 불가");
            resultVO.setData(resultMap);
            return resultVO;
        }

        int mobileChkCnt = userService.mobileChkCnt(userMobile);
        if(mobileChkCnt > 0){
            resultMap.put("result" , "1");
            resultVO.setResultMsg("중복된 전화번호");
        }else{
            resultMap.put("result","0");
            resultVO.setResultMsg("사용가능한 전화번호");
        }

        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/decTest.dm")
    public @ResponseBody String decTest(String param, String clientPe) throws Exception {

        Security security = Security.getinstance();

        System.out.println("param : " + param);
        System.out.println("clientPe : " + clientPe);
        String str =  security.decrypt(param, clientPe);

        System.out.println("str : " + str);

        return str;
    }


    @RequestMapping(value = "/auth/userTradeCheck.dm")
    public @ResponseBody CmeResultVO userTradeCheck(UserTradeCheckVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        // 판매가능 : Y
        // 판매제한 : N

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("사용자 이메일이 없습니다.");
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getCurcyCd())){
            resultVO.setResultMsg("통화코드가 없습니다.");
            resultMap.put("failCd", "998");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getMkState())){
            resultVO.setResultMsg("마켓타입이 없습니다.");
            resultMap.put("failCd" , "997");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String userTrade = "";
        String userTradeCheck = "";

        // "1": 마켓코인이 판매가능이지만 계정이 예외처리되어 판매 제한인 경우
        // "2": 마켓코인이 판매가능에다가 계정이 예외처리가 안되서 판매 가능한 경우
        // "3": 마켓코인이 판매제한이지만 계정이 예외처리되어 판매 가능한 경우
        // "4": 마켓코인이 판매제한에다가 계정이 예외처리가 되지 않아 판매 제한인 경우

        String tradeYn = userService.getTradeCoinUseYn(vo);
        if("Y".equals(tradeYn)){
            // 판매설정 -> 판매인 경우
            vo.setSelUseYn("N");
            int getUserTradeY = userService.getUserTradeCheck(vo);
            if(getUserTradeY > 0){
                userTradeCheck = "1";
            }else{
                userTradeCheck = "2";
            }
        }else{
            // 판매설정 -> 판매제한인 경우
            vo.setSelUseYn("Y");
            int getUserTradeN = userService.getUserTradeCheck(vo);
            if(getUserTradeN > 0){
                userTradeCheck = "3";
            }else{
                userTradeCheck = "4";
            }
        }


       /* if(userTradeYn == true){
            userTrade = "Y";
        }else{
            userTrade = "N";
        }*/

        resultMap.put("tradeType" , userTradeCheck);
        resultVO.setData(resultMap);
        return resultVO;
    }


    @RequestMapping(value = "/IpCheck.dm")
    public @ResponseBody CmeResultVO IpCheck(HttpServletRequest  request, HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");
        String ip = request.getParameter("regIp");
        resultMap.put("foreignIp" , userService.foreignIpCheck(ip));
        resultVO.setData(resultMap);
        return resultVO;
    }


    @RequestMapping(value = "/MtsIpCheck.dm")
    public @ResponseBody CmeResultVO MtsIpCheck(HttpServletRequest  request, HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();

        String getIp = request.getParameter("regIp");
        resultVO.setResultStrCode("000");
        resultMap.put("failCd", "");

        if("".equals(getIp) || getIp == null){
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            resultVO.setResultMsg("IP값 확인불가.");
            return resultVO;
        }



        resultMap.put("foreignIp" , userService.MtsIpCheck(getIp));
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/UserFuncCheck.dm")
    public @ResponseBody CmeResultVO UserFuncCheck(UserFuncAuthVO vo , HttpServletRequest request, HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("사용자 이메일 확인불가");
            resultMap.put("failCd", "");
            resultVO.setData(resultMap);
            return resultVO;
        }

        UserFuncAuthVO uvo = new UserFuncAuthVO();
        uvo = userService.getUserFunc(vo.getUserEmail());

        if(uvo != null){
            if("Y".equals(uvo.getNoLimtYn())){
                resultMap.put("status" , "Y");
                resultVO.setResultMsg("무기한 기능 제한된 회원입니다.");
            }else{
                int i = userService.getUserFuncDt(vo.getUserEmail());
                if(i > 0){
                    resultMap.put("status" , "Y");
                    resultVO.setResultMsg("기한동안 기능 제한된 회원입니다.");
                }else{
                    resultMap.put("status" , "N");
                    resultVO.setResultMsg("기능제한이 아닌 회원입니다.");
                }
            }
        }else{
            resultMap.put("status" , "N");
            resultVO.setResultMsg("회원 기능이 제한된 회원이 아닙니다.");
            resultVO.setData(resultMap);
            return resultVO;
        }


        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/getUserCertification.dm")
    @ResponseBody
    public CmeResultVO getUserCertification(UserInfoVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("failCd", "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("사용자 이메일 확인불가");
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else {
            resultVO.setData(userService.getUserCertification(vo));
            resultVO.setResultStrCode("000");
        }

        return resultVO;

    }



    @RequestMapping(value = "/buyCheck.dm")
    public @ResponseBody CmeResultVO buyCheck(UserTradeCheckVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getMkKndCd())){
            resultVO.setResultMsg("마켓코드 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getCnKndCd())) {
            resultVO.setResultMsg("통화코드 확인불가");
            resultMap.put("failCd", "998");
            resultVO.setData(resultMap);
            return resultVO;
        }
//        }else if("".equals(vo.getTradePrc())){
//            resultVO.setResultMsg("구매금액 확인불가");
//            resultMap.put("failCd" , "997");
//            resultVO.setData(resultMap);
//            return resultVO;
//        }

        //값을 받으면서 리셋되서 미리 저장해둠 && getTradePrc가 있을때만
        String tradePrcString = vo.getTradePrc();

        // 구매 : Y , 구매제한 : N
        UserTradeCheckVO list = userService.getCoinBuyCheck(vo);

        if("Y".equals(list.getBuyUseYn())) {
            resultMap.put("buyCheck", 'Y');
            resultMap.put("askingPrc", "0"); //이부분 추가 190116 0으로줘도되나 확인해야됨
            if(!"".equals(list.getBuyLmtAsking()) || !"".equals(list.getBuyLmtRate())){
                //호가기준 구매제한금액 비교
                BigDecimal asking = new BigDecimal(list.getBuyLmtAsking());
                BigDecimal rate = new BigDecimal(list.getBuyLmtRate());
                BigDecimal pecent = new BigDecimal("100");
                BigDecimal arcPrc;
                if("CMMC00000000204".equals(vo.getMkKndCd())){
                    arcPrc = asking.multiply(rate.divide(pecent, 2, BigDecimal.ROUND_DOWN)).setScale(0, BigDecimal.ROUND_FLOOR);
                } else {
                    arcPrc = asking.multiply(rate.divide(pecent, 2, BigDecimal.ROUND_DOWN));
                }
                arcPrc = asking.subtract(arcPrc); // 구매제한금액 = 기준금액 - (기준금액*설정한%)
                //            System.out.println("요청금액:" + tradePrc);
                //            System.out.println("호가단위:" + asking);
                //            System.out.println("호가비율:" + rate);
                //            System.out.println("계산호가:" + arcPrc);
                //            System.out.println("둘값비교:" + arcPrc.compareTo(tradePrc) + "/-1 : 작다 , 0 : 같다 , 1 : 크다 ");
                resultMap.put("askingPrc", arcPrc);

                if (!"".equals(vo.getTradePrc())) {
                    BigDecimal tradePrc = new BigDecimal(tradePrcString);
                    if (arcPrc.compareTo(tradePrc) == 1) {
                        resultVO.setResultMsg("호가기준 구매제한금액보다 적습니다.");
                        resultMap.put("failCd", "996");
                    }
                } else {
                    resultVO.setResultMsg("정상처리되었습니다.");
                }
            }
            resultVO.setResultMsg("정상처리되었습니다.");
        } else {
            resultMap.put("buyCheck" , 'N' );
            resultVO.setResultMsg("정상처리되었습니다.");
        }

        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/userOtpCheck.dm")
    @ResponseBody public CmeResultVO userOtpCheck(UserOtpCheckVO vo, HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        resultVO.setResultStrCode("000");
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultMap.put("failCd", "");
        boolean userOtpYn = false;
        String userOtp = "";
        if("".equals(vo.getUserEmail())) {
            resultVO.setResultMsg("사용자 아이디가 없습니다.");
            resultMap.put("failCd", "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String otpYn = userService.getOtpYn();
        if("Y".equals(otpYn)) {
            List<UserOtpCheckVO> countryList = new ArrayList<>();
            countryList = userService.getOtpAuthCountry();
            if(countryList.size() != 0) {
                String userCountryCd = userService.getUserCountryCd(vo.getUserEmail());
                for(int i = 0; i < countryList.size(); i++) {
                    if(countryList.get(i).getOtpEtcCd().equals(userCountryCd)) {
                        userOtpYn = false;
                    }else {
                        int exceptionUser = userService.countException(vo.getUserEmail());
                        if(exceptionUser > 0) {
                            userOtpYn = false;
                        }else {
                            userOtpYn = true;
                        }
                    }
                }
            }else {
                int exceptionUser = userService.countException(vo.getUserEmail());
                if(exceptionUser > 0) {
                    userOtpYn = false;
                }else {
                    userOtpYn = true;
                }
            }
        }else {
            userOtpYn = false;
        }


        if(userOtpYn == true) {
            userOtp = "Y";
        }else {
            userOtp = "N";
        }
        resultMap.put("otpYn", userOtp);
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/getLogoImage.dm")
    public void getImageInf2(
            Model model,
            @RequestParam(value = "fileNm", required = false) String fileNm,
            @RequestParam(value = "fileType", required = false) String fileType, HttpServletRequest request ,
            HttpServletResponse response) throws Exception{


        String langCd = request.getParameter("langCd");
        String logoBi = userService.getLogoUrl(langCd);
        CommonVO cvo = userService.getFileSn(logoBi);

        //File file = new File("/home/upload/" + fileType + "/", fileNm);
        System.out.println("path:" + cvo.getFileStreCours());
        System.out.println("fileNm:" + cvo.getStreFileNm());
        File file = new File(cvo.getFileStreCours(), cvo.getStreFileNm());

        FileInputStream fis = null;
        new FileInputStream(file);

        BufferedInputStream in = null;
        ByteArrayOutputStream bStream = null;
        try {
            fis = new FileInputStream(file);
            in = new BufferedInputStream(fis);
            bStream = new ByteArrayOutputStream();
            int imgByte;
            while ((imgByte = in.read()) != -1) {
                bStream.write(imgByte);
            }

            String type = "";

            String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);

            if (ext != null && !"".equals(ext)) {
                if ("jpg".equals(ext.toLowerCase())) {
                    type = "image/jpeg";
                } else {
                    type = "image/" + ext.toLowerCase();
                }
                type = "image/" + ext.toLowerCase();

            } else {
                log.ViewWarnLog("Image fileType is null.");
            }

            response.setHeader("Content-Type", type);
            response.setContentLength(bStream.size());

            bStream.writeTo(response.getOutputStream());

            response.getOutputStream().flush();
            response.getOutputStream().close();

        } catch (Exception e) {
            log.ViewErrorLog(e.getMessage());// e.printStackTrace();
        } finally {
            if (bStream != null) {
                try {
                    bStream.close();
                } catch (Exception est) {
                    log.ViewErrorLog("IGNORED: " + est.getMessage());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ei) {
                    log.ViewErrorLog("IGNORED: " + ei.getMessage());
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception efis) {
                    log.ViewErrorLog("IGNORED: " + efis.getMessage());
                }
            }
        }
    }

    @RequestMapping(value = "/getPopUpList.dm") // 팝업 리스트 API
    @ResponseBody public CmeResultVO getPopUpList(PopUpVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");



        if("".equals(vo.getLangCd())){
            resultVO.setResultMsg("언어값이 없습니다.");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }

        if("ru".equals(vo.getLangCd())) {
            vo.setLangCd("kg");
        } else if("id".equals(vo.getLangCd())) {
            vo.setLangCd("in");
        }

        List<PopUpVO> list = userService.selectPopUpList(vo);
        int listCnt = userService.selectPopUpListCnt(vo);

        String webDomain = "";
        if("0".equals(getDataStatus())){
            webDomain = CmmCdConstant.WEB_DOMAIN_URL;
        }else {
            webDomain = CmmCdConstant.WEB_XLOGIC_DOMAIN_URL;
        }

        List<PopUpVO> fileList = new ArrayList<PopUpVO>();
        for(PopUpVO pvo : list){
            String fileSn = userService.selectFileSn(pvo.getAtchFileId());
            pvo.setFileSn(fileSn);
            pvo.setPopUrl(webDomain + "/common.file.getImageView.dp/proc.go?atchFileId=" + pvo.getAtchFileId() + "&fileSn=" + pvo.getFileSn());
            fileList.add(pvo);
        }

        resultMap.put("list" , fileList);
        resultMap.put("listCnt" , listCnt);
        resultVO.setData(resultMap);
        return resultVO;
    }

    @RequestMapping(value = "/userSmsIpChk.dm")
    @ResponseBody public CmeResultVO userForeignChk(UserIpVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();
        Map<String , Object> resultMap = new HashMap<String , Object>();
        resultVO.setResultStrCode("000");
        resultMap.put("failCd" , "");

        if("".equals(vo.getUserEmail())){
            resultVO.setResultMsg("이메일 확인불가");
            resultMap.put("failCd" , "999");
            resultVO.setData(resultMap);
            return resultVO;
        }else if("".equals(vo.getRegIp())){
            resultVO.setResultMsg("IP 확인불가");
            resultMap.put("failCd" , "998");
            resultVO.setData(resultMap);
            return resultVO;
        }

        String smsIpChk = "";
        if ("N".equals(userService.foreignIpCheck(vo.getRegIp()))) {
            String smsCertCheck = userService.getSmsCertCheck(vo.getUserEmail());
            if ("1".equals(smsCertCheck)) {
                smsIpChk = "Y";
            } else {
                smsIpChk = "N";
            }
        } else {
            smsIpChk = "Y";
        }

        resultMap.put("smsIpChk" , smsIpChk);
        resultVO.setData(resultMap);
        return resultVO;
    }



    //임승연
    @RequestMapping(value = "/mkBotCancle.dm") // 전체미체결 취소
    @ResponseBody
    public CmeResultVO mkBotCancle(CmeTradeReqVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();

        System.out.println("====== 운영 Y/N =====");
        System.out.println(isOper());
        System.out.println("====== 운영 Y/N =====");

        try{

            Map<String , Object> resultMap = new HashMap<String , Object>();
            resultVO.setResultStrCode("000");
            resultMap.put("failCd" , "");

            if("".equals(vo.getTradeType())){       // S:판매 , B:구매
                resultVO.setResultMsg("거래타입 확인불가");
                resultMap.put("failCd", "");
                resultVO.setData(resultMap);
                return resultVO;
            }else if("".equals(vo.getMkState())){
                resultVO.setResultMsg("마켓 확인불가");
                resultMap.put("failCd", "");
                resultVO.setData(resultMap);
                return resultVO;
            }else if("".equals(vo.getCnKndCd())){
                resultVO.setResultMsg("코인코드 확인불가");
                resultMap.put("failCd", "");
                resultVO.setData(resultMap);
                return resultVO;
            }

            int i = 0;

            List<CmeTradeReqVO> list = new ArrayList<CmeTradeReqVO>();

            List<String> userList = new ArrayList<String>();

            userList.add("ymin2715@naver.com");
            userList.add("banbanguy@daum.net");
            userList.add("visop@naver.com");


            for(int a = 0 ; a < userList.size() ; a ++){ //bot 계정 설정후 list 검색 for문
                vo.setUserEmail(userList.get(a));
                //미체결 리스트
                if("BTC".equals(vo.getMkState())){
                    list  = userService.getBotTradeListBTC(vo);
                }else if("ETH".equals(vo.getMkState())){
                    list  = userService.getBotTradeListETH(vo);
                }else if("USDT".equals(vo.getMkState())){
                    list  = userService.getBotTradeListUSDT(vo);
                }
                //
                if("S".equals(vo.getTradeType())){ //판매취소
                    try{
                        for(i = 0; i < list.size(); i++)    { //판매 미체결 내역 list for문
                            CmeTradeReqVO reqVO = new CmeTradeReqVO();
                            reqVO.setUserEmail(list.get(i).getUserEmail());
                            //reqVO.setCnSelCode(list.get(i).getCnSelCode());
                            reqVO.setOrderNo(list.get(i).getCnSelCode());
                            reqVO.setRegIp("127.0.0.1");

                            CmeResultVO res = new CmeResultVO();
                            if("BTC".equals(vo.getMkState())){
                                res = tradeService.orderCalcelBTC(reqVO);
                            }else if("ETH".equals(vo.getMkState())){
                                res = tradeService.orderCalcelETH(reqVO);
                            }else{
                                res = tradeService.orderCalcelUSDT(reqVO);
                            }

                            List SellBotList = (List) res.getProceduresResult();
                            Map map = (Map) SellBotList.get(0);
                            String rtnCd = (String) map.get("RTN_CD");
                            if (rtnCd != null && rtnCd.equals("1")) {
                                res.setResultMsg("미체결 취소 성공");
                                res.setResultStrCode("000");
                                System.out.println(i + "번째 미체결 판매 취소 완료" + "cnSelCode:" + list.get(i).getCnSelCode() + "remPrc:" + list.get(i).getRemPrc() + "userEmail:" + list.get(i).getUserEmail());
                            } else {
                                res.setResultMsg((String) map.get("RNT_MSG"));
                                res.setResultStrCode(rtnCd);
                            }
                        }

                        System.out.println("===========================================");
                        System.out.println("판매취소 ID // "+vo.getUserEmail());
                        System.out.println("판매취소 건수 // " + list.size());
                        System.out.println("===========================================");

                        resultMap.put("미체결 판매" , vo.getCnKndCd() + "취소 완료");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{ //구매취소 getTradeType = "B"
                    try{
                        for(i = 0; i < list.size(); i++) { //구매 미체결 내역 list for문
                            CmeTradeReqVO reqVO = new CmeTradeReqVO();
                            reqVO.setUserEmail(list.get(i).getUserEmail());
                            reqVO.setRegIp("127.0.0.1");
                            //reqVO.setCnPhsCode(list.get(i).getCnPhsCode());
                            reqVO.setOrderNo(list.get(i).getCnPhsCode());

                            CmeResultVO res = new CmeResultVO();
                            if("BTC".equals(vo.getMkState())){
                                res = tradeService.orderCalcelBTC(reqVO);
                            }else if("ETH".equals(vo.getMkState())){
                                res = tradeService.orderCalcelETH(reqVO);
                            }else{
                                res = tradeService.orderCalcelUSDT(reqVO);
                            }

                            List SellBotList = (List) res.getProceduresResult();
                            Map map = (Map) SellBotList.get(0);
                            String rtnCd = (String) map.get("RTN_CD");
                            if (rtnCd != null && rtnCd.equals("1")) {
                                res.setResultMsg("미체결 취소 성공");
                                res.setResultStrCode("000");
                                System.out.println(i + "번째 미체결 구매 취소 완료" + "cnPhsCode:" + list.get(i).getCnPhsCode() + "remPrc:" + list.get(i).getRemPrc() + "userEmail:" + list.get(i).getUserEmail());
                            } else {
                                res.setResultMsg((String) map.get("RNT_MSG"));
                                res.setResultStrCode(rtnCd);
                            }
                        }

                        System.out.println("===========================================");
                        System.out.println("구매취소 ID // "+vo.getUserEmail());
                        System.out.println("구매취소 건수 // " + list.size());
                        System.out.println("===========================================");

                        resultMap.put("미체결 구매" , vo.getCnKndCd() + "취소 완료");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }


        resultVO.setData(resultMap);

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultVO;
    }


    @RequestMapping(value = "/tradeBotCancel.dm") // 전체미체결 취소
    @ResponseBody
    public CmeResultVO tradeBotCancel(CmeTradeReqVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception{
        CmeResultVO resultVO = new CmeResultVO();

        System.out.println("====== 운영 Y/N =====");
        System.out.println(isOper());
        System.out.println("====== 운영 Y/N =====");

        try{

            Map<String , Object> resultMap = new HashMap<String , Object>();
            resultVO.setResultStrCode("000");
            resultMap.put("failCd" , "");

            if("".equals(vo.getTradeType())){       // S:판매 , B:구매
                resultVO.setResultMsg("거래타입 확인불가");
                resultMap.put("failCd", "");
                resultVO.setData(resultMap);
                return resultVO;
            }else if("".equals(vo.getMkState())){
                resultVO.setResultMsg("마켓 확인불가");
                resultMap.put("failCd", "");
                resultVO.setData(resultMap);
                return resultVO;
            }else if("".equals(vo.getCnKndCd())){
                resultVO.setResultMsg("코인코드 확인불가");
                resultMap.put("failCd", "");
                resultVO.setData(resultMap);
                return resultVO;
            }

            int i = 0;

            List<CmeTradeReqVO> list = new ArrayList<CmeTradeReqVO>();

            if("KRW".equals(vo.getMkState())){
                List<String> userList = new ArrayList<String>();
                // 운영에 있는 봇을 멈추고 - 미체결을 지우고 퇴근한다
                userList.add("visop@naver.com");
                userList.add("banbanguy@daum.net");
                userList.add("ymin2715@naver.com");

                for(int a = 0 ; a < userList.size() ; a ++){ //bot 계정 설정후 list 검색 for문
                    vo.setUserEmail(userList.get(a));
                    list  = userService.getBotTradeList(vo);
                    if("S".equals(vo.getTradeType())){ //판매취소
                        try{
                            for(i = 0; i < list.size(); i++)    { //판매 미체결 내역 list for문
                                CmeTradeReqVO reqVO = new CmeTradeReqVO();
                                reqVO.setUserEmail(list.get(i).getUserEmail());
                                //reqVO.setCnSelCode(list.get(i).getCnSelCode());
                                reqVO.setOrderNo(list.get(i).getCnSelCode());
                                reqVO.setRegIp("127.0.0.1");

                                CmeResultVO res = tradeService.orderCalcel(reqVO);
                                List SellBotList = (List) res.getProceduresResult();
                                Map map = (Map) SellBotList.get(0);
                                String rtnCd = (String) map.get("RTN_CD");
                                if (rtnCd != null && rtnCd.equals("1")) {
                                    res.setResultMsg("미체결 취소 성공");
                                    res.setResultStrCode("000");
                                    System.out.println(i + "번째 미체결 판매 취소 완료" + "cnSelCode:" + list.get(i).getCnSelCode() + "remPrc:" + list.get(i).getRemPrc() + "userEmail:" + list.get(i).getUserEmail());
                                } else {
                                    res.setResultMsg((String) map.get("RNT_MSG"));
                                    res.setResultStrCode(rtnCd);
                                }
                            }

                            System.out.println("===========================================");
                            System.out.println("판매취소 ID // "+vo.getUserEmail());
                            System.out.println("판매취소 건수 // " + list.size());
                            System.out.println("===========================================");

                            resultMap.put("미체결 판매" , vo.getCnKndCd() + "취소 완료");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{ //구매취소 getTradeType = "B"
                        try{
                            for(i = 0; i < list.size(); i++) { //구매 미체결 내역 list for문
                                CmeTradeReqVO reqVO = new CmeTradeReqVO();
                                reqVO.setUserEmail(list.get(i).getUserEmail());
                                reqVO.setRegIp("127.0.0.1");
                                //reqVO.setCnPhsCode(list.get(i).getCnPhsCode());
                                reqVO.setOrderNo(list.get(i).getCnPhsCode());

                                CmeResultVO res = tradeService.orderCalcel(reqVO);
                                List SellBotList = (List) res.getProceduresResult();
                                Map map = (Map) SellBotList.get(0);
                                String rtnCd = (String) map.get("RTN_CD");
                                if (rtnCd != null && rtnCd.equals("1")) {
                                    res.setResultMsg("미체결 취소 성공");
                                    res.setResultStrCode("000");
                                    System.out.println(i + "번째 미체결 구매 취소 완료" + "cnPhsCode:" + list.get(i).getCnPhsCode() + "remPrc:" + list.get(i).getRemPrc() + "userEmail:" + list.get(i).getUserEmail());
                                } else {
                                    res.setResultMsg((String) map.get("RNT_MSG"));
                                    res.setResultStrCode(rtnCd);
                                }
                            }

                            System.out.println("===========================================");
                            System.out.println("구매취소 ID // "+vo.getUserEmail());
                            System.out.println("구매취소 건수 // " + list.size());
                            System.out.println("===========================================");

                            resultMap.put("미체결 구매" , vo.getCnKndCd() + "취소 완료");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

            }

            resultVO.setData(resultMap);

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultVO;
    }

    public  String getDataStatus() throws Exception {
        String path = servletContext.getRealPath("/");
        path += "WEB-INF/classes/cmeconfig/CmeProps";
        //System.out.println(path);
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(path + "/dataStatus.properties");
        props.load(new java.io.BufferedInputStream(fis));
        String status = props.getProperty("status").trim();

        return status;
    }
}


