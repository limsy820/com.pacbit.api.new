/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.push.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.bitkrx.api.auth.vo.SubmitCertVO;
import com.bitkrx.api.common.dao.CoinInfoDAO;
import com.bitkrx.api.common.vo.CommonExchgInfoVO;
import com.bitkrx.api.user.service.UserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitkrx.api.push.dao.CmeFcmPushDAO;
import com.bitkrx.api.push.service.CmeFcmPushService;
import com.bitkrx.api.push.vo.CmeFcmPushVO;
import com.bitkrx.api.push.vo.HtsPushResVO;
import com.bitkrx.api.push.vo.PushListVO;
import com.bitkrx.api.push.vo.PushYnVO;
import com.bitkrx.api.push.vo.SendMsgVO;
import com.bitkrx.config.util.CmmCdConstant;

import javax.servlet.ServletContext;


/**
 * @프로젝트명    : com.bitkrx.api
 * @패키지 : com.bitkrx.api.push.service.impl
 * @클래스명 : com.bitkrx.api
 * @작성자        : (주)씨엠이소프트 문영민
 * @작성일        : 2017. 12. 6.
 */
@Service
public class CmeFcmPushServiceImpl implements CmeFcmPushService {

    @Autowired
    CmeFcmPushDAO pushDao;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    CoinInfoDAO coinInfoDAO;

    @Autowired
    UserService userService;

    private final int NECESSARY_PARAMETER = -1;
    public final static String AUTH_KEY_FCM = "AAAAkH9K4uo:APA91bEEgSP8mBmVrx_GA2qjHRv_XoGWxSsJTkvOb0kI3NREXsyv_ylROwpEqKi8gOIwPjh_EhvqLUbIsxRxEH_gxEwShGdOvWCCjmjDK3KbyyKEesNByl6DZaExWgn0jrZwFYAEDNPi";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public final static String BIZ_CODE = "BZCD00000000002";

    @Override
    public String sendPushMsgOne(String push_token, String msg, JSONObject data) throws Exception {
        //(공통화)
//        SubmitCertVO svo = new SubmitCertVO();
//        String mailLang = userService.getUserLangCd(vo.getUserEmail());
//        svo.setLang(mailLang);
//        CommonExchgInfoVO comvo = coinInfoDAO.commonExchgInfo(svo);

        String authKey = pushDao.getPushKey(BIZ_CODE); // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        JSONObject info = new JSONObject();

        info.put("title", "[" + CmmCdConstant.EXCHANGE_NAME + "]");
        info.put("body", msg); // Notification body
        info.put("sound", "default");
        info.put("icon", "coin_push_logo");
        info.put("click_action", "PUSH_ACTIVITY");

        json.put("data", data);
        //알림 메시지
        json.put("notification", info);

        //특정 기기로 메시지를 보내려면 to 키를 특정 앱 인스턴스의 등록 토큰으로 설정합니다.
        json.put("to", push_token.trim()); // deviceID

        BufferedReader br = null;
        StringBuffer returnMsg = new StringBuffer();

        try (OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")) {
            //혹시나 한글 깨짐이 발생하면
            //try(OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")){ 인코딩을 변경해준다.

            //System.out.println("JSON:" + data);
            //System.out.println("JSONTOSTRING:" + json.toString());
            wr.write(json.toString());
            wr.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                //throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                return String.valueOf(conn.getResponseCode());
            }

            br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                returnMsg.append(output);
            }

        } catch (Exception e) {
        } finally {
            br.close();
            conn.disconnect();
        }


        return returnMsg.toString();
    }


    @Override
    public String sendPushMsgOne(String push_token, String msg) throws Exception {
        //(공통화)
//        SubmitCertVO svo = new SubmitCertVO();
//        String mailLang = userService.getUserLangCd(vo.getUserEmail());
//        svo.setLang(mailLang);
//        CommonExchgInfoVO comvo = coinInfoDAO.commonExchgInfo(svo);

        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        JSONObject info = new JSONObject();

        info.put("title", "[" + CmmCdConstant.EXCHANGE_NAME + "]");
        info.put("body", msg); // Notification body

        //알림 메시지
        json.put("notification", info);

        //특정 기기로 메시지를 보내려면 to 키를 특정 앱 인스턴스의 등록 토큰으로 설정합니다.
        //json.put("to", push_token.trim()); // deviceID
        json.put("to", ""); // deviceID

        BufferedReader br = null;
        StringBuffer returnMsg = new StringBuffer();

        try (OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")) {
            //혹시나 한글 깨짐이 발생하면
            //try(OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")){ 인코딩을 변경해준다.

            wr.write(json.toString());
            wr.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                //throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                return String.valueOf(conn.getResponseCode());
            }

            br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                returnMsg.append(output);
            }

        } catch (Exception e) {
        } finally {
            br.close();
            conn.disconnect();
        }


        return returnMsg.toString();
    }

    public boolean isOper() throws Exception {
        String path = servletContext.getRealPath("/");
        path += "WEB-INF/classes/cmeconfig/CmeProps";
        //System.out.println(path);
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(path + "/dataStatus.properties");
        props.load(new java.io.BufferedInputStream(fis));
        String status = props.getProperty("status").trim();
        if (status.equals("0")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String sendPushMsgAll(String msg, JSONObject data) throws Exception {
        //(공통화)
//        SubmitCertVO svo = new SubmitCertVO();
//        String mailLang = userService.getUserLangCd(vo.getUserEmail());
//        svo.setLang(mailLang);
//        CommonExchgInfoVO comvo = coinInfoDAO.commonExchgInfo(svo);

        String authKey = pushDao.getPushKey(BIZ_CODE); // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        JSONObject info = new JSONObject();

        info.put("title", "[" + CmmCdConstant.EXCHANGE_NAME + "]");
        info.put("body", msg); // Notification body
        info.put("sound", "default");
        info.put("icon", "coin_push_logo");
        info.put("click_action", "PUSH_ACTIVITY");

        data.put("pushUrl", "/bt.front.board.list.dp/proc.go?pageIndex=1&board_id=BDMT_000000000001");
        data.put("userEmail", "");
        info.put("data", data);
        //알림 메시지
        json.put("notification", info);

        String condition = "";
        if (isOper()) {
            condition = CmmCdConstant.PUSH_CONDITION;
        } else {
            condition = CmmCdConstant.PUSH_CONDITION_STA;
        }
        //전체 푸시
        json.put("condition", "'" + condition + "' in topics"); // deviceID

        System.out.println("json.toString() : " + json.toString());

        BufferedReader br = null;
        StringBuffer returnMsg = new StringBuffer();

        try (OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")) {
            //혹시나 한글 깨짐이 발생하면
            //try(OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")){ 인코딩을 변경해준다.

            wr.write(json.toString());
            wr.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                //throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                return String.valueOf(conn.getResponseCode());
            }

            br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                returnMsg.append(output);
            }

        } catch (Exception e) {
        } finally {
            br.close();
            conn.disconnect();
        }


        return returnMsg.toString();
    }

    @Override
    public void PR_INSUPT10171061(Map map) throws Exception {
        // TODO Auto-generated method stub
        pushDao.PR_INSUPT10171061(map);
    }


    public String getPushKey(String str) throws Exception {
        return pushDao.getPushKey(str);
    }


    public List<HtsPushResVO> pushNotCompList(String str) throws Exception {
        List<HtsPushResVO> list = pushDao.pushNotCompList(str);
        int res = pushDao.uptHtsPush(str);
        return list;
    }

    public List<PushListVO> getPushList(CmeFcmPushVO vo) throws Exception {
        return pushDao.getPushList(vo);
    }

    public String pushNoticeList(String str) throws Exception {

        return pushDao.pushNoticeList(str);
    }

    public void PR_INSUPT_SND_MSG(SendMsgVO vo) throws Exception {

        pushDao.PR_INSUPT_SND_MSG(vo);
    }

    public void insUptPushYn(PushYnVO vo) throws Exception {

        pushDao.insUptPushYn(vo);
    }

    public PushYnVO selectPushYn(PushYnVO vo) throws Exception {

        return pushDao.selectPushYn(vo);
    }

}
