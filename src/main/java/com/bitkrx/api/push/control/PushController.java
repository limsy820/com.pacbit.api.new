/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.api.push.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitkrx.api.auth.vo.SubmitCertVO;
import com.bitkrx.api.common.dao.CoinInfoDAO;
import com.bitkrx.api.common.vo.CommonExchgInfoVO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitkrx.api.auth.service.LoginService;
import com.bitkrx.api.push.service.CmeFcmPushService;
import com.bitkrx.api.push.vo.CmeFcmPushVO;
import com.bitkrx.api.push.vo.HtsPushResVO;
import com.bitkrx.api.push.vo.PushListVO;
import com.bitkrx.api.push.vo.PushYnVO;
import com.bitkrx.api.sms.service.SmsApiService;
import com.bitkrx.api.user.service.UserService;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.logging.CmeCommonLogger;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.util.StringUtils;
import com.bitkrx.config.vo.SendInfoVO;


/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.api.push.control
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 12. 6.
 */
@Controller
@RequestMapping("/bt")
public class PushController extends CmeDefaultExtendController{

	protected CmeCommonLogger log = new CmeCommonLogger(this.getClass());
	
	@Autowired 
	CmeFcmPushService cmeFcmPushService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	SmsApiService smsApiService;
	
	@Autowired
	UserService userService;

	@Autowired
	CoinInfoDAO coinInfoDAO;
	
	private final int SUCESS = 0;
	//필수 파라미터
	private final int NECESSARY_PARAMETER = -1; 
	//Insert or update not sucess
	private final int FAILER = -2;
	private final int SQL_ERROR = -98;
	private final int UnKnown_ERROR = -99; 
	
	@RequestMapping("/pushNotiMsg.dm")
	public @ResponseBody CmeResultVO pushFCMNotiMsg(
			@ModelAttribute CmeFcmPushVO vo,
			HttpServletRequest request,
			HttpServletResponse response
			)throws Exception{
		
		vo.setPush_token("dImHKgk23X0:APA91bFf_CFOoYviqRX1p5NHzWYPED2yYPwLO8Op5s4WrDTlRlCixbtvnoOF3DJDF0wcB_b75uNx2Xi9twS_Y1XfJ9aMT7DMvlCZf_2DFpqU9A8zBYDVQj6NyZDGNYUdPEsD5RMiZUAW");
		//vo.setPush_msg("푸시 테스트입니다.");

		
		//pushToken(받는사람 토큰), push_msg(푸시 메세지)
		CmeResultVO rtnVo = new CmeResultVO();
		try {
				//token , 메시지는 DB
				//token , 입력 받은 메시지
				String push_toekn = StringUtils.checkNull(vo.getPush_token(), "");
				if ("".equals(push_toekn)) {
					rtnVo.setResultCode(NECESSARY_PARAMETER);
					rtnVo.setResultMsg("Token Key is Null");
					
					return rtnVo;
				}
				String push_msg = StringUtils.checkNull(vo.getPush_msg(), "");
				if ("".equals(push_msg)) {
					rtnVo.setResultCode(NECESSARY_PARAMETER);
					rtnVo.setResultMsg("push_msg is Null");
					
					return rtnVo;
				}
				cmeFcmPushService.sendPushMsgOne(vo.getPush_token(),vo.getPush_msg());
				
		} 
		catch (Exception e) {
			e.printStackTrace();
			rtnVo.setResultCode(UnKnown_ERROR);
			rtnVo.setResultMsg("푸시 전송에 실패 하였습니다.");
			return rtnVo;
		}
		
		rtnVo.setResultCode(SUCESS);
		rtnVo.setResultStrCode("000");
		rtnVo.setResultMsg("정상처리 되었습니다.");
		return rtnVo;
		
	}
	
	@RequestMapping("/uptPushInfo.dm")
	public @ResponseBody CmeResultVO uptPushInfo(CmeFcmPushVO vo) throws Exception {

		//(공통화)
		SubmitCertVO svo = new SubmitCertVO();
		String mailLang = userService.getUserLangCd(vo.getUserEmail());
		svo.setLang(mailLang);
		CommonExchgInfoVO comvo = coinInfoDAO.commonExchgInfo(svo);

		CmeResultVO rtnVo = new CmeResultVO();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//log.ViewErrorLog("uptPushInfo 시작 : " + sdf.format(new Date()));
		
		Map map = new HashMap();
    	map.put("cntnsCode", vo.getCntnsCode());
    	map.put("userEmail", vo.getUserEmail());
    	map.put("regEmail", vo.getUserEmail());
    	map.put("regIp", vo.getRegIp());
    	map.put("pushCompCode", "Y");
    	
    	map.put("rsvDt", "");
    	map.put("pushCode", "PUSH000000000000001");
    	map.put("cntntTitle", "[" + comvo.getExchgName()/*CmmCdConstant.EXCHANGE_NAME */+ "]");
    	map.put("cntnsMsg", "");
    	map.put("pushKndCd", "");
    	map.put("pushSendYn", "");
    	map.put("pushSendDt", "");
    	
    	//log.ViewErrorLog("uptPushInfo PR_INSUPT10171061 시작 : " + sdf.format(new Date()));
    	cmeFcmPushService.PR_INSUPT10171061(map);
    	//log.ViewErrorLog("uptPushInfo PR_INSUPT10171061 끝 : " + sdf.format(new Date()));
    	
    	rtnVo.setResultStrCode1(map.get("RESULT").toString());
    	rtnVo.setResultStrCode("000");
    	
    	//log.ViewErrorLog("uptPushInfo 끝: " + sdf.format(new Date()));
    	return rtnVo;
	}
	
	
	@RequestMapping("/getHtsPushList.dm")
	public @ResponseBody CmeResultVO pushNotCompList(CmeFcmPushVO vo) throws Exception {
		CmeResultVO rtnVo = new CmeResultVO();
		List<HtsPushResVO> bList = cmeFcmPushService.pushNotCompList(vo.getUserEmail());
		List<HtsPushResVO> list = new ArrayList<HtsPushResVO>();
		rtnVo.setResultStrCode("000");
		if(bList != null) {
			for(HtsPushResVO hvo : bList) {
				if(hvo.getPushType().equals("CMMC00000000285") || hvo.getPushType().equals("CMMC00000000665")) {
					String[] strs = hvo.getPushMsg().split(":::");
					if(strs.length > 1) {
						if(strs[0].equals(userService.getUserLangCd(vo.getUserEmail()))) {
							hvo.setPushMsg(strs[1]);
							list.add(hvo);
						}
					}
				} else {
					list.add(hvo);
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		rtnVo.setData(map);
		
		return rtnVo;
	}
	
	
	@RequestMapping("/getPushList.dm")
	public @ResponseBody CmeResultVO getPushList(CmeFcmPushVO vo) throws Exception {
		CmeResultVO rtnVo = new CmeResultVO();
        Map<String, Object> map = new HashMap<String, Object>();
        vo.setFirstIndex((vo.getPageIndex() - 1) * 10);
        vo.setRecordCountPerPage(vo.getPageUnit());

		if("".equals(vo.getPushCode()) || vo.getPushCode() == null){
			vo.setPushCode("PUSH000000000000002");
		}

		if(vo.getListSize() == 0) {
			vo.setListSize(10);
		}
		List<PushListVO> list = cmeFcmPushService.getPushList(vo);
		rtnVo.setResultStrCode("000");
		if(list == null) {
			list = new ArrayList<PushListVO>();
		}

		map.put("list", list);
        rtnVo.setResultStrCode("000");
		rtnVo.setData(map);
		
		return rtnVo;
	}
	
	
	@RequestMapping("/sendPush.dm")
	public @ResponseBody CmeResultVO sendPush(SendInfoVO vo) throws Exception {
		CmeResultVO rtnVo = new CmeResultVO();
		
		int res = 0;
		res =  smsApiService.sendSms(vo);
		if(res > 0) {
			rtnVo.setResultStrCode("000");
			rtnVo.setResultMsg("발송 성공");
		} else {
			rtnVo.setResultStrCode("-1");
			rtnVo.setResultMsg("발송 실패");
		}
		Map<String, Object> rmap = new HashMap<String, Object>();
		rtnVo.setData(rmap);
		
		return rtnVo;
	}
	
	public JSONObject makeJson(List list, String userEmail) {
		//List list = (List) map.get("RESULT");
    	Map returnMap = (Map) list.get(0);
    	JSONObject jobj = new JSONObject();
    	String pushKey = "";
    	try {
    		pushKey = (String) returnMap.get(":B1");
        	pushKey = pushKey.substring(pushKey.indexOf("CNTN"), pushKey.indexOf("CNTN") + 15);
        	jobj = new JSONObject();
    	} catch (Exception e) {
    		pushKey = "";
    	}
    	jobj.put("pushKey", pushKey);
    	jobj.put("userEmail", userEmail);
    	return jobj;
	}
	
	
	@RequestMapping("/noticePush.dm")
	
	public @ResponseBody CmeResultVO noticePush(SendInfoVO vo) throws Exception {
		CmeResultVO rtnVo = new CmeResultVO();

		//(공통화)
		SubmitCertVO svo = new SubmitCertVO();
		String mailLang = userService.getUserLangCd(vo.getUserEmail());
		svo.setLang(mailLang);
		CommonExchgInfoVO comvo = coinInfoDAO.commonExchgInfo(svo);
		
		Map map = new HashMap();
    	map.put("cntnsCode", "");
    	map.put("userEmail", "init@bitkrx.com");
    	map.put("regEmail", "init@bitkrx.com");
    	map.put("regIp", vo.getRegIp());
    	map.put("rsvDt", "");
    	map.put("pushCode", "PUSH000000000000002");
    	map.put("cntntTitle", "[" + comvo.getExchgName()/* CmmCdConstant.EXCHANGE_NAME */+ "]");
    	map.put("cntnsMsg", vo.getLangCd() + ":::" + vo.getContents());
    	map.put("pushKndCd", "CMMC00000000285");
    	map.put("pushSendYn", "N");
    	map.put("pushSendDt", new SimpleDateFormat("yyyyMMdd").format(new Date()));
    	map.put("pushCompCode", "N"); 
    	
    	cmeFcmPushService.PR_INSUPT10171061(map);

        map.put("cntnsCode", "");
        map.put("userEmail", "init@bitkrx.com");
        map.put("regEmail", "init@bitkrx.com");
        map.put("regIp", vo.getRegIp());
        map.put("rsvDt", "");
        map.put("pushCode", "PUSH000000000000002");
        map.put("cntntTitle", "[" + comvo.getExchgName()/*CmmCdConstant.EXCHANGE_NAME*/ + "]");
        map.put("cntnsMsg", vo.getLangCd() + ":::" + vo.getContents());
        map.put("pushKndCd", "CMMC00000000665");
        map.put("pushSendYn", "N");
        map.put("pushSendDt", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        map.put("pushCompCode", "N");

        cmeFcmPushService.PR_INSUPT10171061(map);

        map.put("pushCode", "PUSH000000000000001");

        cmeFcmPushService.PR_INSUPT10171061(map);

        List list = (List) map.get("RESULT");
        Map returnMap = (Map) list.get(0);
        JSONObject jobj = new JSONObject();
        String pushKey = "";
        try {
            pushKey = (String) returnMap.get(":B1");
            pushKey = pushKey.substring(pushKey.indexOf("CNTN"), pushKey.indexOf("CNTN") + 15);
            jobj = new JSONObject();
        } catch (Exception e) {
            pushKey = "";
        }
        jobj.put("pushKey", pushKey);


        cmeFcmPushService.sendPushMsgAll(vo.getContents(), jobj);
    	
		rtnVo.setResultStrCode("000");
		Map<String, Object> rmap = new HashMap<String, Object>();
		rtnVo.setData(rmap);
		
		return rtnVo;
	}
	
	@RequestMapping("/setPushYn.dm")
	public @ResponseBody CmeResultVO insUptPushYn(PushYnVO vo) throws Exception {
		CmeResultVO rtnVo = new CmeResultVO();
		
		try {
			cmeFcmPushService.insUptPushYn(vo);
		} catch (Exception e) {
			e.printStackTrace();
			rtnVo.setResultStrCode("-1");
			Map<String, Object> rmap = new HashMap<String, Object>();
			rtnVo.setData(rmap);
			return rtnVo;
		}
		
    	
		rtnVo.setResultStrCode("000");
		Map<String, Object> rmap = new HashMap<String, Object>();
		rtnVo.setData(rmap);
		
		return rtnVo;
	}
	
	@RequestMapping("/getPushYn.dm")
	public @ResponseBody CmeResultVO selectPushYn(PushYnVO vo) throws Exception {
		CmeResultVO rtnVo = new CmeResultVO();
		
		try {
			vo = cmeFcmPushService.selectPushYn(vo);
		} catch (Exception e) {
			e.printStackTrace();
			rtnVo.setResultStrCode("-1");
			Map<String, Object> rmap = new HashMap<String, Object>();
			rtnVo.setData(rmap);
			return rtnVo;
		}
		
		rtnVo.setResultStrCode("000");
		rtnVo.setData(vo);
		
		return rtnVo;
	}
}








