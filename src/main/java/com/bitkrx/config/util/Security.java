/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.config.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import java.util.Map;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bitkrx.core.util.SecurityUtil;
import com.common.utils.AesUtil;

public class Security {
	private static String RSA_WEB_KEY = "_RSA_WEB_Key_"; // 개인키 session key
    private static String RSA_INSTANCE = "RSA"; // rsa transformation
    
    private static Security security = Security.getinstance();
    private static Map<String,Object> map = null;
    
    
    public static synchronized Security getinstance() {
        if(security == null ){
        	security = new Security();
        } 
        return security;
    }
    
    
    public Map<String, Object> getMap() throws Exception{
    	
    	if(map == null) {
    		map = SecurityUtil.RsaKeyCreate();
    		map.put("createDate", new Date());
    	}
    	Date date = (Date) map.get("createDate");
    	long diff = new Date().getTime() - date.getTime();
    	long diffDays = diff / (24 * 60 * 60 * 1000);
		if(diffDays > 1) {//Key를 생성한지 1일이 지나면 재생성함
			map = SecurityUtil.RsaKeyCreate();
    		map.put("createDate", new Date());
		}
		return Security.map;
	}

    /**
     * @Method Name  : decrypt
     * @작성일   : 2017. 11. 28. 
     * @작성자   :  (주)씨엠이소프트 문영민
     * @변경이력  :
     * @Method 설명 : 문자열 복호화
     * @param endStr
     * @param aceKey
     * @return
     * @throws Exception
     */
    public String decrypt(String endStr, String aceKey) throws Exception {
//        System.out.println("복호화 시작=================================================");
//        System.out.println("endStr : " + endStr);
//        System.out.println("aceKey1 : " + aceKey);
    	aceKey = SecurityUtil.decryptRsa((PrivateKey) map.get("privateKey"), aceKey.trim());
//        System.out.println("aceKey2 : " + aceKey);
    	String decStr = SecurityUtil.Dec256Str(endStr, aceKey);
//        System.out.println("decStr : " + decStr);
//        System.out.println("복호화 후 암호화 시작=================================================");
        return decStr;
    }
    
    /**
     * @Method Name  : encPwd
     * @작성일   : 2017. 11. 28. 
     * @작성자   :  (주)씨엠이소프트 문영민
     * @변경이력  :
     * @Method 설명 : 패스워드 복호화 후 shd256으로 암호화
     * @param pwd
     * @param aceKey
     * @return
     * @throws Exception
     */
    public String encPwd(String pwd, String aceKey) throws Exception {
        //System.out.println("복호화 후 암호화 시작=================================================");
    	//System.out.println("pwd : " + pwd);
    	//System.out.println("aceKey1 : " + aceKey);
    	aceKey = SecurityUtil.decryptRsa((PrivateKey) map.get("privateKey"), aceKey.trim());
    	//System.out.println("aceKey2 : " + aceKey);
    	String Dec256Str = SecurityUtil.Dec256Str(pwd, aceKey);
    	//System.out.println("Dec256Str : " + Dec256Str);
    	String Sha256Encode =SecurityUtil.Sha256Encode(Dec256Str);
    	//System.out.println("Sha256Encode : " + Sha256Encode);
        //System.out.println("복호화 후 암호화 끝=================================================");
    	return Sha256Encode;
    }


	@RequestMapping("/rsaUtilEncSec.dm")
	public ModelAndView rsaUtilEncSec(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ModelAndView mv = null;
		
		Map<String,Object> map = SecurityUtil.RsaKeyCreate();
		PrivateKey privateKey = (PrivateKey) map.get("privateKey");
		//개인키를 session 에 저장한다.
		request.getSession().setAttribute("_RSA_WEB_Key_", privateKey);
		
    	model.addAttribute("pubKeyModule", (String) map.get("pubKeyModule"));
    	model.addAttribute("pubKeyExponent", (String) map.get("pubKeyExponent"));
    	
		mv = new ModelAndView();
		mv.setViewName("/sample/rsaSec");
		return mv;
	}
    
    @RequestMapping("/rsaUtilDncSec.dm")
	public ModelAndView rsaUtilDncSec(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	ModelAndView mv = null;
    	
    	String userId = (String) request.getParameter("USER_ID");
    	String userPw = (String) request.getParameter("USER_PW");
       
        HttpSession session = request.getSession();
        // session 에서 privateKey 가져오기 
        PrivateKey privateKey = (PrivateKey) session.getAttribute("_RSA_WEB_Key_");
        //세션키 제거
      	session.removeAttribute("_RSA_WEB_Key_");
                
        // 복호화
        String aceKey = (String) request.getParameter("ACE_KEY");
        aceKey = SecurityUtil.decryptRsa(privateKey, aceKey);
        //System.out.println("aceKey================>"+aceKey);
        
        String _userID =  SecurityUtil.Dec256Str(userId, aceKey);
        //System.out.println("_userId================>"+_userID);
        
        String _userPw =  SecurityUtil.Dec256Str(userPw, aceKey);
        //System.out.println("_userPw================>"+_userPw);
        
        //패스워드 인 경우 복호화르 하지 않고 암호화 된 상태에서 다시 shd256으로 암호화 하여 저장 하거나 비교 한다.
        String _suserPw = SecurityUtil.Sha256Encode(userPw);
        //System.out.println("_suserPw================>"+_suserPw);        
        /*//
        	비지니스 진행
        
        //*/
        mv = new ModelAndView();
		mv.setViewName("/sample/rsaSec");
		return mv;
	}

	@RequestMapping("/rsaEncSec.dm")
	public ModelAndView rsaEncSec(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ModelAndView mv = null;
		
		//String test = testDecryptRsa(initRsa(request),encRsa(request));
		initRsa(request);
		////System.out.println("init ::"+test);
		
		mv = new ModelAndView();
		mv.setViewName("/sample/rsaSec");
		return mv;
	}
	
	@RequestMapping("/rsaDncSec.dm")
	public ModelAndView rsaDencSec(
			@RequestParam(name="USER_ID",required=false) String userId,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		ModelAndView mv = null;
		
		
		//String userId = (String) request.getParameter("USER_ID");
        String userPw = (String) request.getParameter("USER_PW");
 
        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(Security.RSA_WEB_KEY);
        
        if(privateKey == null) {
        	//System.out.println("========== privateKey null =============");
        }
        
        // 복호화
        String aceKey = (String) request.getParameter("ACE_KEY");
        aceKey = decryptRsa(privateKey, aceKey);
        //System.out.println("aceKey================>"+aceKey);
        //System.out.println("userId================>"+userId);
        String _userID = AesUtil.AES_256Decode(userId, aceKey);
        //System.out.println("_userId================>"+_userID);
        		
        String _userPw = AesUtil.AES_256Decode(userPw, aceKey);
        String _suserPw = SecurityUtil.Sha256Encode(userPw);
        //System.out.println("_userPw================>"+_userPw);
        //System.out.println("_suserPw================>"+_suserPw);
		session.removeAttribute(Security.RSA_WEB_KEY);
		
		mv = new ModelAndView();
		mv.setViewName("/sample/rsaSec");
		return mv;
	}
	
	/**
     * 복호화
     * 
     * @param privateKey
     * @param securedValue
     * @return
     * @throws Exception
     */
    private String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
        Cipher cipher = Cipher.getInstance(Security.RSA_INSTANCE);
        
        byte[] encryptedBytes = hexToByteArray(securedValue);
        /*byte[] encryptedBytes2 = new byte[encryptedBytes.length-1];
        if(encryptedBytes[0] == 0){
        System.arraycopy(encryptedBytes, 1, encryptedBytes2, 0, encryptedBytes.length-1);
        }*/
        
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
        return decryptedValue;
    }
    
    /**
     * 16진 문자열을 byte 배열로 변환한다.
     * 
     * @param hex
     * @return
     */
    public static byte[] hexToByteArray(String hex) {
    	int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                 + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
      
    }



	
	/**
     * rsa 공개키, 개인키 생성
     * 
     * @param request
     */
    public PrivateKey initRsa(HttpServletRequest request) {
        HttpSession session = request.getSession();
 
        KeyPairGenerator generator;
        PrivateKey privateKey = null;
        try {
            generator = KeyPairGenerator.getInstance(Security.RSA_INSTANCE);
            generator.initialize(1024);
 
            KeyPair keyPair = generator.genKeyPair();
            KeyFactory keyFactory = KeyFactory.getInstance(Security.RSA_INSTANCE);
            PublicKey publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
 
            session.setAttribute(Security.RSA_WEB_KEY, privateKey); // session에 RSA 개인키를 세션에 저장
 
            RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            String publicKeyModulus = publicSpec.getModulus().toString(16);
            String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
            
            //System.out.println("pubKeyModule ::"+publicKeyModulus);
            //System.out.println("pubKeyExponent ::"+publicKeyExponent);
            
            request.setAttribute("pubKeyModule", publicKeyModulus); // rsa modulus 를 request 에 추가
            request.setAttribute("pubKeyExponent", publicKeyExponent); // rsa exponent 를 request 에 추가
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return privateKey;
    }
    
    /*
     * rsa 통한 암호화 - 테스트를 위해 public key 로 암호화 한다.
     */
    public byte[] encRsa(HttpServletRequest request) throws Exception {
    	String publicKeyModulus = (String) request.getAttribute("RSAModulus"); // rsa modulus 를 request 에 추가
    	String publicKeyExponent = (String) request.getAttribute("RSAExponent"); // rsa exponent 를 request 에 추가
    	
    	BigInteger modulus = new BigInteger(publicKeyModulus, 16);
    	BigInteger exponent = new BigInteger(publicKeyExponent, 16);
    	RSAPublicKeySpec pubks = new RSAPublicKeySpec(modulus, exponent);
    	
    	KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    	 
    	PublicKey publicKey = keyFactory.generatePublic(pubks);
    	
    	Cipher cipher = Cipher.getInstance("RSA");
    	//System.out.println("byte --::"+ Base64.encode("testtest".getBytes()));
    	 cipher.init(Cipher.ENCRYPT_MODE, publicKey);
         byte[] cipherText = cipher.doFinal("testtest".getBytes());
         //System.out.println("cipher: ("+ cipherText.length +")"+ new String(cipherText));
    	
         return cipherText;
    }
    
    /**
     * public key 암호화 한것을 복호화
     *   
     */
    private String testDecryptRsa(PrivateKey privateKey,byte[] by) throws Exception {
        Cipher cipher = Cipher.getInstance(Security.RSA_INSTANCE);
       
        
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(by);
        String decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
        return decryptedValue;
    }


}