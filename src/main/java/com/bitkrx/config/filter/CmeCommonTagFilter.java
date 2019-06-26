package com.bitkrx.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import com.bitkrx.config.logging.CmeCommonLogger;
import com.bitkrx.config.service.CmeProperties;
import com.bitkrx.config.util.SessionUtil;

public class CmeCommonTagFilter implements Filter{

    private FilterConfig config;
    private CmeCommonLogger log = new CmeCommonLogger(this.getClass());
    boolean isWhite = false;
    boolean isWhiteParam = false;
    boolean isAuthedPassURL = false;
    SessionUtil sUtil = SessionUtil.getinstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        this.config = filterConfig;
        
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;        
        String UrlList = CmeProperties.getProperty("URL.White.List"); // 프레임웍 특유의 URL 규칙을 따르지 않아도 되는 URL 리스트
        String UrlParamList = CmeProperties.getProperty("URL.White.Param.List"); // 파라메터의 값들중 특수문자들을 정규식화 하지 않아도 되는 URL 들
        //String AdminUrlList = CmeProperties.getProperty("URL.Admin.List"); // BackOffice URL 리스트
        
        String queryString = request.getQueryString();
        String reqUri = request.getRequestURI();
        
        log.DebugLog("QueryString::"+queryString);
        HttpSession session = request.getSession();
        
        if(reqUri.indexOf(";") > -1){
            if (session.isNew()) {
                // 새로운 세션일 경우 jessionid 를 회피하기 위해 다시 리다이렉트
                request.getRequestDispatcher(reqUri).forward(request,response);
                return;
            } else if (session.getAttribute("verified") == null) {
                //세션이 아직 살아 있지 않다면, 세션을 넣어준다.
                session.setAttribute("verified", true);
                if (request.isRequestedSessionIdFromCookie()) {
                    //쿠키에 jsessionid 가 없는 상태에서 리다이렉트 된경우 jsessionid 를 url 에서 제거한다.
                    request.getRequestDispatcher(reqUri.split(";")[0]).forward(request,response);                   
                    return;
                }
            }           
        }
        
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        
        String[] WhiteList = UrlList.split("\\,");
        String[] WhiteParamUrlList = UrlParamList.split("\\,");
        String curpath = request.getRequestURI();
        
        String inurl = "";
        String preurl = "";
        
        if(curpath.indexOf("dm_none.dp") == -1){
            inurl = ""+request.getRequestURL();         
            /*preurl = sUtil.GetStrValue(request, CmeSessionConst.LOG_URL);           
            sUtil.SetStrSession(request, CmeSessionConst.LOG_URL, inurl);
            sUtil.SetStrSession(request, CmeSessionConst.LOG_PRE_URL, preurl);   */       
        }
        log.DebugLog("Dm TagFilter:>>>>>>>#######"+curpath);
        
        for(String s : WhiteList){
            if(s.equals(curpath)){
                isWhite = true;
                break;
            }
        }
 
        
        for(String s : WhiteParamUrlList){
            if(curpath.indexOf(s) > -1){
                isWhiteParam = true;
                break;
            }
        }
        
        
       if(!isWhite){
            String[] tmpval = curpath.split("\\/");
            if(!tmpval[tmpval.length-1].equals("proc.go") && !tmpval[tmpval.length-1].equals("dmparse.dm")){
                //log.ViewErrorLog("URL 특성 무시된 URL" + tmpval[tmpval.length-1]);
                response.sendRedirect("/dm_none.dp/proc.go");
                return;
            }
        }
        
        
        HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(
                (HttpServletResponse) response) {
            public String encodeRedirectUrl(String url) {
                return url;
            }

            public String encodeRedirectURL(String url) {
                return url;
            }

            public String encodeUrl(String url) {
                return url;
            }

            public String encodeURL(String url) {
                return url;
            }
        };          
        
        //log.DebugLog(">>>isWhiteParam : " + isWhiteParam);
        chain.doFilter(new CmeTagWhiteFilterRequestWrapper(request), wrappedResponse);
        /*
        if(!isWhiteParam){
            chain.doFilter(new CmeTagFilterRequestWrapper(request), wrappedResponse);            
        }else{
            chain.doFilter(new CmeTagWhiteFilterRequestWrapper(request), wrappedResponse);
        }*/
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }
    
    

}
