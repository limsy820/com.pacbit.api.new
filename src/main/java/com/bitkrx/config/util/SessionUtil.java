package com.bitkrx.config.util;

import java.io.Serializable;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bitkrx.config.logging.CmeCommonLogger;

public class SessionUtil extends HttpServlet implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -2242055167813641007L;
    static CmeCommonLogger log = new CmeCommonLogger(SessionUtil.class);
    
    private static SessionUtil sUtil = null;
    
    public static synchronized SessionUtil getinstance(){
        if(sUtil == null ){
            sUtil = new SessionUtil();
        }
        return sUtil;
    }
    
    /**
     * @Method Name  : GetSessionLogView
     * @작성일   : 2012. 9. 12. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 세션에 담겨 있는 정보를 로그에 뿌립니다.
     * @param request
     */
    public void GetSessionLogView(HttpSession session)
    {
        Enumeration names = session.getAttributeNames();
        StringBuffer sb = new StringBuffer();
        
        while (names.hasMoreElements())
        {
            String name = (String) names.nextElement();
            String value = session.getAttribute(name).toString();
            sb.append("Session["+name+"]:"+value + "|");
        }
        log.ViewLog(sb.toString());     
    }

    public void GetSessionLogView(HttpServletRequest request)
    {
        HttpSession session = request.getSession(true);
        Enumeration names = session.getAttributeNames();
        StringBuffer sb = new StringBuffer();
        
        while (names.hasMoreElements())
        {
            String name = (String) names.nextElement();
            String value = session.getAttribute(name).toString();
            sb.append("Session["+name+"]:"+value + "|");
        }
        log.ViewLog(sb.toString());     
    }   
    
    /**
     * @Method Name  : RemoveAllSession
     * @작성일   : 2012. 9. 12. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 세션정보를 초기화 합니다.
     * @param request
     */
    public void RemoveAllSession(HttpServletRequest request)
    {
        HttpSession session = request.getSession(true);
        Enumeration names = session.getAttributeNames();
        StringBuffer sb = new StringBuffer();
        
        while (names.hasMoreElements())
        {
          String name = (String) names.nextElement();
          session.removeAttribute(name);
          sb.append("Remove Session:"+name+"|");              
          
        }
        session.invalidate();
        log.DebugLog(sb.toString());        
    }
    
    //세션을 초기화 합니다.
    /**
     * @Method Name  : RemoveSession
     * @작성일   : 2012. 9. 12. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 특정세션을 초기화 합니다.
     * @param request
     * @param name : 세션명
     */
    public void RemoveSession(HttpServletRequest request, String name)
    {
        HttpSession session = request.getSession(true);
        Enumeration names = session.getAttributeNames();
        
        while (names.hasMoreElements())
        {
          String key = (String) names.nextElement();
          if(key.equals(name))
          {
              log.DebugLog("Remove Session:"+name);
              session.removeAttribute(name);
              break;
          }
        }       
    }

    public void RemoveSession(HttpSession session, String name)
    {
        //HttpSession session = request.getSession(true);
        Enumeration names = session.getAttributeNames();
        
        while (names.hasMoreElements())
        {
          String key = (String) names.nextElement();
          if(key.equals(name))
          {
              log.DebugLog("Remove Session:"+name);
              session.removeAttribute(name);
              break;
          }
        }       
    }   
    
    /**
     * @Method Name  : GetStrValue
     * @작성일   : 2012. 9. 12. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 세션명에 담겨 있는 값을 가져옵니다. - String
     * @param request
     * @param name
     * @return
     */
    public String GetStrValue(HttpServletRequest request, String name)
    {
        HttpSession session = request.getSession(true);
        Enumeration names = session.getAttributeNames();
        while (names.hasMoreElements())
        {
          String key = (String) names.nextElement();
          if(key.equals(name))
          {
//            log.DebugLog("Get Session Value:"+(String)session.getAttribute(name));
              return (String)session.getAttribute(name);
          }
        }
        return "";
    }
    
    
    /**
     * @Method Name  : SetStrSession
     * @작성일   : 2012. 9. 12. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 세션에 값을 담습니다. - String
     * @param request
     * @param name
     * @param value
     */
    public void SetStrSession(HttpServletRequest request, String name, String value)
    {
        HttpSession session = request.getSession(true);
//      log.DebugLog("Add Session:"+name+"/"+value);        
        session.setAttribute(name, value);
    }   
    
    /**
     * @Method Name  : SetObjSession
     * @작성일   : 2012. 9. 12. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 세션에 값을 담습니다. - Object
     * @param request
     * @param name
     * @param value
     */
    public void SetObjSession(HttpServletRequest request, String name, Object value)
    {
        HttpSession session = request.getSession(true);
//      log.DebugLog("Add Session:"+name+"/"+value.toString());     
        session.setAttribute(name, value);
    }
    
    
    /**
     * @Method Name  : SetSessionValue
     * @작성일   : 2012. 9. 25. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 세션을 Type에 맞게 세팅합니다.
     * @param request
     * @param name
     * @param value
     */
    public void SetSessionValue(HttpServletRequest request, String name, Object value){
        HttpSession session = request.getSession(true);
        log.DebugLog("Add Session:"+name+"/"+value.toString());
        if(value instanceof String){
            session.setAttribute(name, (String)value);
        }else if(value instanceof Integer){
            session.setAttribute(name, (Integer)value);
        }else if(value instanceof Boolean){
            session.setAttribute(name, (Boolean)value);
        }else{
            session.setAttribute(name, value);
        }
    }

    /**
     * @Method Name  : GetSessionValue
     * @작성일   : 2012. 9. 25. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : object 형태로 세션값을 리턴합니다. 키값이 없으면 null - 리턴받은후 알맞은 Type으로 변경 [ex. (String)GetSessionValue(reuqest,name) ]
     * @param request
     * @param name
     * @return
     */
    public Object GetSessionValue(HttpServletRequest request, String name){
        HttpSession session = request.getSession(true);
        Enumeration names = session.getAttributeNames();
        while (names.hasMoreElements())
        {
            String key = (String)names.nextElement();
            if(key.equals(name))
            {
                Object obj = session.getAttribute(name);
                return obj;
            }
        }
        return null;
    }
    
    public boolean isSessionExist(HttpServletRequest request, String name){
        HttpSession session = request.getSession(true);
        Enumeration names = session.getAttributeNames();
        while (names.hasMoreElements())
        {
            String key = (String)names.nextElement();
            if(key.equals(name))
            {
                return true;
            }
        }
        return false;
    }
    
    public String GetStrValue(HttpSession session, String name)
    {
        Enumeration names = session.getAttributeNames();
        while (names.hasMoreElements())
        {
          String key = (String) names.nextElement();
          if(key.equals(name))
          {
//            log.DebugLog("Get Session Value:"+(String)session.getAttribute(name));
              return (String)session.getAttribute(name);
          }
        }
        return "";
    }
    
    public void SetStrValue(HttpSession session, String name, String value)
    {
        session.setAttribute(name, value);
    }   
    
    public HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }
    
    public String getStrValue(String name){
        HttpSession session = getSession();
        Enumeration names = session.getAttributeNames();
        
        while (names.hasMoreElements())
        {
          String key = (String) names.nextElement();
          if(key.equals(name))
          {
              return (String)session.getAttribute(name);
          }
        }
        return "";      
    }
    
    public Object getObjValue(String name){
        HttpSession session = getSession();
        Enumeration names = session.getAttributeNames();
        while (names.hasMoreElements())
        {
          String key = (String) names.nextElement();
          if(key.equals(name)){
              return (Object)session.getAttribute(name);
          }
        }
        return null;        
    }   
    
    
    public int getAuthLevel(){
        HttpSession session = getSession();
        Enumeration names = session.getAttributeNames();
        while (names.hasMoreElements())
        {
          String key = (String) names.nextElement();
          if(key.equals("AUTH_ID"))
          {
              return Integer.parseInt((String)session.getAttribute("AUTH_ID"));
          }
        }
        return 0;       
    }
    
    public String getPreMenuId() {
        HttpSession session = getSession();
        Enumeration names = session.getAttributeNames();
        while (names.hasMoreElements()) {
            String key = (String) names.nextElement();
/*          if (key.equals(SiasSessionConst.CurMenuId)) {
                return (String) session
                        .getAttribute(SiasSessionConst.CurMenuId);
            }*/
        }
        return "";
    }
    
    public boolean isAuthenticate(){
        HttpSession session = getSession();
        Enumeration names = session.getAttributeNames();
        while (names.hasMoreElements()){
          String key = (String) names.nextElement();
          //System.out.println(">>>>>SessionCheck::>>" +  key);
          if(key.equals("INTEG_ID")){
              return true;
          }
        }
        return false;       
    }   
    
}
