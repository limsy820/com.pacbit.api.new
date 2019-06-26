package com.bitkrx.config.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitkrx.config.util.ComUtil;

public class CmeCommonLogger {
    
    boolean islog = false;
    boolean isDblog = false;
    /*
     * 디버그로그를 보여줄지 여부
     */
    boolean isDebug = true;
    boolean isDbDebug = false;
    /*
     * 에러로그를 보여줄지 여부
     */
    boolean isError = true;
    boolean isDbError = false;
    /*
     * 경고로그를 보여줄지 여부
     */
    boolean isWarn = true;
    boolean isDbWarn = false;
    
    boolean isTimelog = false;
    protected Class<?> thisClass;
    protected Logger logger;
    
    String strCtxId = "com.bitkrx.api";
    
/*  @Autowired
    @Qualifier("DevAdminDataSource")
    DataSource DmHompageDataSource; 
    BasicDataSource dbData = null;  */
    
    @SuppressWarnings("rawtypes")
    public CmeCommonLogger(Class c){
        this.logger = LoggerFactory.getLogger(c);
        thisClass = c;   
    }
    
    /**
     * @Method Name  : ViewLog
     * @작성일   : 2012. 9. 11. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 정보 로그 - 메세지에 '|' 구분자로 메세지를 구분할수 있다. ex) 로그입니다.|나는 정보로그 입니다.
     * @param msg
     * @param c
     */
    public void ViewLog(String msg){
        if(islog){
            logger.debug("□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□[Log View Start]□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
            if(msg.indexOf("|") > -1){
                String[] strTmp = msg.split("\\|");
                for(int i = 0;i< strTmp.length; i++){
                    logger.info("["+strTmp[i]+"]");
                }
            }else{
                logger.info("["+msg+"]");
            }           
            logger.debug("□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□[Log View End]□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
            
            if(isDblog){
//              DmErrorLogVO vo = new DmErrorLogVO();
                try {
/*                  vo.setCtxid(strCtxId);
                    vo.setAppid(thisClass.getName());
                    vo.setSysmsg(msg);
                    vo.setLoglevel("INFO");*/
                    String _prefix = "["+ strCtxId + "]["+ thisClass.getName() +"][INFO]";
                    ComUtil.WriteErrorLog(_prefix + "[" + msg + "]");
                    //dmCommonDao.InsertSysErrLog(vo);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }           
        }
    }

    /**
     * @Method Name  : DebugLog
     * @작성일   : 2012. 9. 11. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 디버그 로그(시작/끝 라인 포함) - 메세지에 '|' 구분자로 메세지를 구분할수 있다. ex) 로그입니다.|나는 디버그로그 입니다.
     * @param msg
     * @param c
     */
    public void DebugLog(String msg){
        if(isDebug){
            logger.debug("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓[Debug Log Start]〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");     
            //System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$:"+thisClass.getName());
            if(msg.indexOf("|") > -1){
                String[] strTmp = msg.split("\\|");
                for(int i = 0;i< strTmp.length; i++){
                    logger.debug("["+strTmp[i]+"]");
                }
            }else{
                logger.debug("["+msg+"]");              
            }
            
            if(isDbDebug){
//              DmErrorLogVO vo = new DmErrorLogVO();
                try {
/*                  vo.setCtxid(strCtxId);
                    vo.setAppid(thisClass.getName());
                    vo.setSysmsg(msg);
                    vo.setLoglevel("DEBUG");*/
                    String _prefix = "["+ strCtxId + "]["+ thisClass.getName() +"][DEBUG]";
                    ComUtil.WriteErrorLog(_prefix + "[" + msg + "]");                   
                    //dmCommonDao.InsertSysErrLog(vo);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }           
            
            logger.debug("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓[Debug Log End]〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");          
        }
    }

    /**
     * @Method Name  : DebugLogMsg
     * @작성일   : 2012. 9. 11. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 디버그 로그의 메세지만 기록(시작,끝라인 없음) - '|' 로 메세지 구분
     * @param msg
     * @param c
     */
    public void DebugLogMsg(String msg){
        if(isDebug){
            if(msg.indexOf("|") > -1){
                String[] strTmp = msg.split("\\|");
                
                for(int i = 0;i< strTmp.length; i++){
                    logger.debug("["+strTmp[i]+"]");
                }
            }else{
                logger.debug("["+msg+"]");              
            }
        }
    }   
    

    /**
     * @Method Name  : DebugLogLine
     * @작성일   : 2012. 9. 11. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 디버그 로그의 시작라인과 끝라인 생성 : 디버그 시작 라인(true:시작,false:끝)
     * @param start
     * @param c
     */
    public void DebugLogLine(boolean start){
        if(isDebug){
            if(start){
                logger.debug("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓[Debug Log Start]〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");             
            }else{
                logger.debug("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓[Debug Log End]〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");              
            }
        }
    }   
    
    public void ResponsTimeLog(String msg){
        if(isTimelog){
            logger.info(msg);
        }
    }
    
    
    /**
     * @Method Name  : ViewErrorLog
     * @작성일   : 2012. 9. 11. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 오류 로그 - 메세지에 '|' 구분자로 메세지를 구분할수 있다. ex) 로그입니다.|나는 오류로그 입니다.
     * @param msg
     * @param c
     */
    public void ViewErrorLog(String msg){
        if(isError){
            logger.debug("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■[Log Error Start]■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
            if(msg.indexOf("|") > -1){
                String[] strTmp = msg.split("\\|");
                for(int i = 0;i< strTmp.length; i++){
                    logger.error("["+strTmp[i]+"]");
                }
            }else{
                logger.error("["+msg+"]");              
            }
            logger.debug("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■[Log Error End]■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
            
        }       
    }
    
    /**
     * @Method Name  : ViewWarnLog
     * @작성일   : 2012. 9. 11. 
     * @작성자   : Kim, YunKwan
     * @변경이력  :
     * @Method 설명 : 경고 로그 - 메세지에 '|' 구분자로 메세지를 구분할수 있다. ex) 로그입니다.|나는 경고로그 입니다.
     * @param msg
     * @param c
     */
    public void ViewWarnLog(String msg){
        if(isWarn){
            logger.debug("◀◀◀◀◀◀◀◀◀◀◀◀◀◀◀◀◀[Log Warnning Start]▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶");
            if(msg.indexOf("|") > -1){
                String[] strTmp = msg.split("\\|");
                for(int i = 0;i< strTmp.length; i++){
                    logger.warn("["+strTmp[i]+"]");
                }
            }else{
                logger.warn("["+msg+"]");               
            }
            logger.debug("◀◀◀◀◀◀◀◀◀◀◀◀◀◀◀◀◀[Log Warnning End]▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶");
            
            if(isDbWarn){
//              DmErrorLogVO vo = new DmErrorLogVO();
                try {
/*                  vo.setCtxid(strCtxId);
                    vo.setAppid(thisClass.getName());
                    vo.setSysmsg(msg);
                    vo.setLoglevel("WARN");*/
                    //dmCommonDao.InsertSysErrLog(vo);
                    String _prefix = "["+ strCtxId + "]["+ thisClass.getName() +"][WARN]";
                    ComUtil.WriteErrorLog(_prefix + "[" + msg + "]");                   
                    
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }           
        }       
    }
}
