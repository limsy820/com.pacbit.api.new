package com.bitkrx.config.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.bitkrx.config.logging.CmeCommonLogger;
import com.bitkrx.config.service.CmeProperties;


public class ComUtil {
    static final CmeCommonLogger log = new CmeCommonLogger(ComUtil.class);
    
    
    
    
    static SessionUtil sUtil = SessionUtil.getinstance();
    
    public static String[] strTok(String str) {
        return str.split("\\.");
    }

    /**
     * Null check. 데이터가 null 일 경우 "" 로 변환
     * 
     * @param str
     * @return str
     */
    public static String isNull(String str) {
        if (null == str || "null".equals(str) || "".equals(str)) {
            str = "";
        }
        return str;
    }

    /**
     * Comment : 배열의 널 체크한다.
     * 
     * @param as
     * @return
     */
    public static String[] isNull(String[] as) {
        String[] ast;
        if (as == null || as.length == 0)
            ast = new String[] {};
        else {
            ast = (String[]) as.clone();
            for (int i = 0; i < ast.length; i++) {
                ast[i] = ast == null ? "" : ast[i];
            }
        }
        return ast;
    }

    /**
     * Comment :현제 날짜에 월을 더하거나 뺄수 있다.
     * 
     * @param month
     * @return
     */
    public static String getDate(int month) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, month);
        return cal.get(Calendar.YEAR)
                + ((cal.get(Calendar.MONTH) + 1 < 10) ? ("0" + (cal
                        .get(Calendar.MONTH) + 1))
                        : ((cal.get(Calendar.MONTH) + 1) + ""))
                + ((cal.get(Calendar.DAY_OF_MONTH) < 10) ? "0"
                        + cal.get(Calendar.DAY_OF_MONTH) : (cal
                        .get(Calendar.DAY_OF_MONTH)) + "");
    }

    /**
     * Comment : where in문을 셋팅한다.
     * 
     * @param as
     * @param params
     * @return
     */
    public static String setInCode(String[] as, ArrayList params) {
        StringBuffer rtn = new StringBuffer();
        if (as == null || as.length == 0)
            rtn.append(" =  NULL");
        else {
            rtn.append(" IN (");
            for (int i = 0; i < as.length; i++) {
                params.add(as[i]);
                if (i == 0) {
                    rtn.append("?");
                } else {
                    rtn.append(", ? ");
                }
            }
            rtn.append(" ) ");
        }
        return rtn.toString();
    }
    
    public static boolean isDmUrl(String url){
        return url.matches("[0-9|a-z|A-z|\\.|\\=|\\+|\\/]*");
    }

    /**
     * Comment : String 데이터를 format에 맞게 형식화 한다.
     * 
     * @param data
     * @param format
     */
    public static String formString(String data, String format) {
        return StringUtils.formString(data, format);
    }

    /**
     * Comment : String 데이터를 특정 타입을 만들어 준다.
     * 
     * @param data
     * @param type
     *            date: 날짜(2008/01/15) ym: 년월(2008/01) df: 날짜(2008/01/01) dt:
     *            년월일 시분(2008/01/15 15:15) d2: 날짜(2008년 1월 1일) dts: 년월일
     *            시분초(2008/01/15 15:15:15) bn: 사업자번호(123-45-76890) post:
     *            우편번호(010-010) jm: 우편번호(791212-1234567) ni: 정수형으로 변환 comma: 숫자에
     *            컴마를 넣는다.(123,355) null : 폼서식을 제거한다.
     * @return
     */
    public static String formData(String data, String type) {
        if (type == null || type.equals(""))
            return data.replaceAll("[,:/-]", "");
        if (type.equals("ni")) {
            return isNull(data).equals("") ? "0" : isNull(data).replaceAll(
                    "[^0-9]", "").equals("") ? "0" : isNull(data).replaceAll(
                    "[^0-9]", "");
        } else {
            return StringUtils.formData(data, type);
        }
    }

    /**
     * 영문 입력 값의 인코딩 처리
     */
    public static String toEng(String data) {
        try {
            if (null == data) {
                return null;
            } else {
                return new String(data.getBytes("euc-kr"), "8859_1");
            }

        } catch (Exception e) {
            return data;
        }
    }

    /**
     * 한글 입력 값의 인코딩 처리
     */
    public static String toKor(String data) {
        try {
            if (null == data) {
                return null;
            } else {
                return new String(data.getBytes("8859_1"), "euc-kr");
            }

        } catch (Exception e) {
            return data;
        }
    }

    /**
     * jsp 연도 콤보 설정 -1, 금년, +1 <option value='yyyy'>yyyy</option>
     * 
     * @return String
     */
    public static String getYear() {
        StringBuffer retData = new StringBuffer();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int iYear = Integer.parseInt(sdf.format(date));

        for (int i = -1; i <= 1; i++) {
            retData.append("<option value='");
            retData.append(iYear + i);
            if (0 == i) {
                retData.append("' selected>");
            } else {
                retData.append("'>");
            }
            retData.append(iYear + i);
            retData.append("</option>\n");
        }
        return retData.toString();
    }
    
    /**
     * jsp 연도 콤보 설정  금년, years <option value='yyyy'>yyyy</option>
     * 
     * @return String
     */
    public static String getAddYear(int startDt, int endDt) {
        StringBuffer retData = new StringBuffer();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int iYear = Integer.parseInt(sdf.format(date));
        
        int bYear = startDt - iYear;
        int aYear = endDt - iYear;
        for (int i = bYear; i <= aYear; i++) {
            retData.append("<option value='");
            retData.append(iYear + i);
            if (0 == i) {
                retData.append("' selected>");
            } else {
                retData.append("'>");
            }
            retData.append(iYear + i);
            retData.append("</option>\n");
        }
        return retData.toString();
    }

    /**
     * jsp 월 콤보 설정 월설정 <option value='01'>01</option>
     * 
     * @return String
     */
    public static String getMonth() {
        StringBuffer retData = new StringBuffer();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        int iMonth = Integer.parseInt(sdf.format(date));

        for (int i = 1; i <= 12; i++) {
            String sMonth = String.valueOf(i);
            if (i < 10)
                sMonth = "0" + i;

            retData.append("<option value='");
            retData.append(sMonth);
            if (i == iMonth) {
                retData.append("' selected>");
            } else {
                retData.append("'>");
            }
            retData.append(sMonth);
            retData.append("</option>\n");
        }
        return retData.toString();
    }

    /**
     * 사업자 코드(xxx-xx-xxxxx)형식으로 변환
     * 
     * @param biz_no
     * @return String
     */
    public static String getBizNo(String biz_no) {
        StringBuffer retData = new StringBuffer();

        for (int i = 0; i < biz_no.length(); i++) {

            if (i == 3 || i == 5)
                retData.append("-");
            retData.append(biz_no.charAt(i));
        }
        return retData.toString();
    }

    /**
     * 입력받은 타입에 따라 날짜 취득
     * 
     * @param type
     * @return String
     */
    public static String getDate(String type) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(type);

        type = isNull(sdf.format(date));

        return type;
    }

    
    public static String[] toStrArrayDate(String inputDate) {
        inputDate = sDateFilter(inputDate);
        String[] retDate = new String[3];       
        
        if(inputDate.length() == 8){
            retDate[0] = inputDate.substring(0, 4);
            retDate[1] = inputDate.substring(4, 6);
            retDate[2] = inputDate.substring(6, 8);
        }else if(inputDate.length() == 6){
            retDate[0] = inputDate.substring(0, 4);
            retDate[1] = inputDate.substring(4, 6);         
        }else if(inputDate.length() == 4){
            retDate[0] = inputDate.substring(0, 4);         
        }
        return retDate;
    }   
    
    
    /**
     * yyyymm - 1달을 구한다.
     * 
     * @param type
     * @return String
     */
    public static String getPreDate(String date) {
        String retData = "";

        int iYear = Integer.parseInt(date.substring(0, 4));
        int iMonth = Integer.parseInt(date.substring(4, 6));

        if (iMonth == 1) {
            iYear = iYear - 1;
            iMonth = 12;
        } else {
            iMonth = iMonth - 1;
        }

        if (10 > iMonth) {
            retData = iYear + "0" + iMonth;
        } else {
            retData = iYear + "" + iMonth;
        }

        return retData;
    }

    /**
     * 특정문자를 제외
     * 
     * @param sValue
     *            , mark
     * @return String
     */
    public static String removeMark(String sValue, String mark) {
        StringBuffer sbValueWithoutMark = new StringBuffer();
        sValue = isNull(sValue);
        for (int i = 0; i < sValue.length(); i++) {
            if (!sValue.substring(i, i + 1).equals(mark)) {
                sbValueWithoutMark.append(sValue.substring(i, i + 1));
            }
        }
        return sbValueWithoutMark.toString();
    }

    /**
     * 날짜 타입을 yyyy/mm/dd 로 반환
     * 
     * @param data
     * @return String
     */
    public static String getDefaultDate(String data) {
        String retData = "";

        if (!"".equals(ComUtil.isNull(data))) {
            if (8 <= data.length()) {
                retData = data.substring(0, 4);
                retData += "/";
                retData += data.substring(4, 6);
                retData += "/";
                retData += data.substring(6, 8);
            }
        }
        return retData;
    }

    /**
     * 날짜 타입을 yyyy.mm.dd 로 반환
     * 
     * @param data
     * @return String
     */
    public static String getDotDate(String data) {
        String retData = "";

        if (!"".equals(ComUtil.isNull(data))) {
            if (8 <= data.length()) {
                retData = data.substring(0, 4);
                retData += ".";
                retData += data.substring(4, 6);
                retData += ".";
                retData += data.substring(6, 8);
            }
        }
        return retData;
    }

    /**
     * yyyymm - 3달을 구한다.
     * 
     * @param type
     * @return String
     */
    public static String getTripleDate(String date) {
        String retData = "";

        int iYear = Integer.parseInt(date.substring(0, 4));
        int iMonth = Integer.parseInt(date.substring(4, 6));

        if (iMonth == 1) {
            iYear = iYear - 1;
            iMonth = 10;
        } else if (iMonth == 2) {
            iYear = iYear - 1;
            iMonth = 11;
        } else if (iMonth == 3) {
            iYear = iYear - 1;
            iMonth = 12;
        } else {
            iMonth = iMonth - 3;
        }

        if (10 > iMonth) {
            retData = iYear + "0" + iMonth;
        } else {
            retData = iYear + "" + iMonth;
        }
        return retData;
    }

    /**
     * yyyymm 다음달을 구한다.
     * 
     * @param type
     * @return String
     */
    public static String getNextDate(String date) {
        String retData = "";

        int iYear = Integer.parseInt(date.substring(0, 4));
        int iMonth = Integer.parseInt(date.substring(4, 6));

        if (iMonth == 12) {
            iYear = iYear + 1;
            iMonth = 1;
        } else {
            iMonth = iMonth + 1;
        }

        if (10 > iMonth) {
            retData = iYear + "0" + iMonth;
        } else {
            retData = iYear + "" + iMonth;
        }
        return retData;
    }

    /**
     * 두 기간의 일수/날짜 간격를 구한다.
     * 
     * @param String
     * @return String
     */
    public static int diffOfDate(String begin, String end) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        Date beginDate = formatter.parse(begin);
        Date endDate = formatter.parse(end);

        long diff = endDate.getTime() - beginDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return (int) diffDays;
    }

    /**
     * jsp 연도 콤보 설정 (다음달) -1, 금년, +1 <option value='yyyy'>yyyy</option>
     * 
     * @return String
     */
    public static String getYear2() {
        StringBuffer retData = new StringBuffer();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        int iYear = Integer.parseInt(sdf.format(date));
        int iMonth = Integer.parseInt(sdf2.format(date));

        if (iMonth == 12) {
            iYear += 1;
        }

        for (int i = -1; i <= 1; i++) {
            retData.append("<option value='");
            retData.append(iYear + i);
            if (0 == i) {
                retData.append("' selected>");
            } else {
                retData.append("'>");
            }
            retData.append(iYear + i);
            retData.append("</option>\n");
        }
        return retData.toString();
    }

    /**
     * @Method Name : setYearOption
     * @작성일 : 2012. 11. 2.
     * @작성자 : Kim, YunKwan
     * @변경이력 :
     * @Method 설명 : 년도를 최소 최대값을 이용해 option 값 설정
     * @param min
     * @param max
     * @param selval
     * @return
     */
    public static String setYearOption(int min, int max, String selval) {
        StringBuffer retData = new StringBuffer();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int iYear = Integer.parseInt(sdf.format(date));
        String _selval = StringUtils.checkNull(selval);
        int setYear = 0;
        
        if("".equals(_selval)){
            retData.append("<option value=''>전체</option>");
        }else{
            setYear = Integer.parseInt(_selval);
            retData.append("<option value='' selected>전체</option>");
        }
        
        for (int i = min; i <= max; i++) {
            retData.append("<option value='");
            retData.append(iYear + i);
            
            if (iYear + i == setYear) {
                /*if (iYear + i != setYear) {
                    retData.append("' selected>");
                } else {
                    retData.append("'>");
                }*/
                retData.append("' selected>");
            }
            else {
                retData.append("'>");               
/*              if (0 == i) {
                    retData.append("' selected>");
                } else {
                    retData.append("'>");
                }*/
            }

            retData.append(iYear + i);
            retData.append("</option>\n");
        }
        return retData.toString();
    }

    /**
     * @Method Name : setMonthOption
     * @작성일 : 2012. 11. 2.
     * @작성자 : Kim, YunKwan
     * @변경이력 :
     * @Method 설명 : 월 일자 option 설정
     * @param selval
     * @return
     */
    public static String setMonthOption(String selval) {
        StringBuffer retData = new StringBuffer();
        boolean nowMonth = false;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        int iMonth = Integer.parseInt(sdf.format(date));
        //String _selval = StringUtils.checkNull(selval, sdf.format(date));
        String _selval = StringUtils.checkNull(selval);
        int setMonth = 0; 
        if("".equals(_selval)){
            retData.append("<option value='' selected>전체</option>");
        }else{
            retData.append("<option value=''>전체</option>");
            
            setMonth = Integer.parseInt(_selval);           
        }

        if (setMonth == iMonth) {
            nowMonth = true;
        }

        for (int i = 1; i <= 12; i++) {
            String sMonth = String.valueOf(i);
            if (i < 10)
                sMonth = "0" + i;

            retData.append("<option value='");
            retData.append(sMonth);

            if (nowMonth) {
                if (i == iMonth) {
                    retData.append("' selected>");
                } else {
                    retData.append("'>");
                }
            } else {
                if (i == setMonth) {
                    retData.append("' selected>");
                } else {
                    retData.append("'>");
                }
            }

            retData.append(sMonth);
            retData.append("</option>\n");
        }
        return retData.toString();
    }

    /**
     * @Method Name : setDayOption
     * @작성일 : 2012. 11. 2.
     * @작성자 : Kim, YunKwan
     * @변경이력 :
     * @Method 설명 : 일자 option설정
     * @param ymd
     * @return
     */
    public static String setDayOption(String ymd) {
        StringBuffer retData = new StringBuffer();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf_day = new SimpleDateFormat("dd");
        ymd = StringUtils.checkNull(ymd);

        if (ymd.length() == 8) {
            try {
                date = sdf.parse(ymd);
                
                int LastDay = getLastDate(sdf.format(date));
                int curDay = Integer.parseInt(sdf_day.format(date));
                retData.append("<option value=''>전체</option>");
                for (int i = 1; i <= LastDay; i++) {
                    String sDay = String.valueOf(i);
                    if (i < 10)
                        sDay = "0" + i;

                    retData.append("<option value='");
                    retData.append(sDay);

                    if (curDay == i) {
                        retData.append("' selected>");
                    } else {
                        retData.append("'>");
                    }

                    retData.append(sDay);
                    retData.append("</option>\n");
                }               
                
                
            } catch (ParseException e) {
                log.ViewErrorLog(e.getMessage());
            }
        }else{
            int LastDay = getLastDate(sdf.format(date));
            int curDay = Integer.parseInt(sdf_day.format(date));
            retData.append("<option value='' selected>전체</option>");
            for (int i = 1; i <= LastDay; i++) {
                String sDay = String.valueOf(i);
                if (i < 10)
                    sDay = "0" + i;

                retData.append("<option value='");
                retData.append(sDay);

                if (curDay == i) {
                    retData.append("'>");
                } else {
                    retData.append("'>");
                }

                retData.append(sDay);
                retData.append("</option>\n");
            }           
        }


        return retData.toString();
    }

    /**
     * jsp 월 콤보 설정(다음 달) 월설정 <option value='01'>01</option>
     * 
     * @return String
     */
    public static String getMonth2() {
        StringBuffer retData = new StringBuffer();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        int iMonth = Integer.parseInt(sdf.format(date));

        if (iMonth == 12) {
            iMonth = 1;
        } else {
            iMonth += 1;
        }

        for (int i = 1; i <= 12; i++) {
            String sMonth = String.valueOf(i);
            if (i < 10)
                sMonth = "0" + i;

            retData.append("<option value='");
            retData.append(sMonth);
            if (i == iMonth) {
                retData.append("' selected>");
            } else {
                retData.append("'>");
            }
            retData.append(sMonth);
            retData.append("</option>\n");
        }
        return retData.toString();
    }

    /**
     * +- 값에 따라 원하는 날짜 취득(YYYY/MM/DD)
     * 
     * @param int iDay
     * @return String
     */
    public static String getAddDate(int iDay) {
        Calendar temp = Calendar.getInstance();
        StringBuffer sbDate = new StringBuffer();

        temp.add(Calendar.DAY_OF_MONTH, iDay);

        int nYear = temp.get(Calendar.YEAR);
        int nMonth = temp.get(Calendar.MONTH) + 1;
        int nDay = temp.get(Calendar.DAY_OF_MONTH);

        sbDate.append(nYear);
        sbDate.append("/");
        if (nMonth < 10)
            sbDate.append("0");
        sbDate.append(nMonth);
        sbDate.append("/");
        if (nDay < 10)
            sbDate.append("0");
        sbDate.append(nDay);

        return sbDate.toString();
    }

    /**
     * +- 값에 따라 원하는 날짜 취득(type) type 형태에 따라 yyyy-mm-dd 등으로 변경됨
     * 
     * @param int iDay , String type
     * @return String
     */
    public static String getAddDate(int iDay, String type) {
        Calendar temp = Calendar.getInstance();
        StringBuffer sbDate = new StringBuffer();

        temp.add(Calendar.DAY_OF_MONTH, iDay);

        int nYear = temp.get(Calendar.YEAR);
        int nMonth = temp.get(Calendar.MONTH) + 1;
        int nDay = temp.get(Calendar.DAY_OF_MONTH);

        sbDate.append(nYear);
        sbDate.append(type);
        if (nMonth < 10)
            sbDate.append("0");
        sbDate.append(nMonth);
        sbDate.append(type);
        if (nDay < 10)
            sbDate.append("0");
        sbDate.append(nDay);

        return sbDate.toString();
    }

    /**
     * 해당월에 마지막일자 얻어오기
     * 
     * @param String
     *            iDay(20111111)
     * @return String
     */
    public static int getLastDate(String iDay) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(iDay.substring(0, 4)),
                Integer.parseInt(iDay.substring(4, 6)) - 1,
                Integer.parseInt(iDay.substring(6)));

        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 해당월에 01일자에 대한 요일명 가져오기
     * 
     * @param int yyyy, int mm
     * @return String
     */
    public static String getFirstDay(int yyyy, int mm) {
        Calendar cal = Calendar.getInstance();
        // 1 2 3 4 5 6 7
        final String[] week = { "일", "월", "화", "수", "목", "금", "토" };

        cal.set(yyyy, mm - 1, 1);

        return week[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 해당월에 01일자에 대한 요일(숫자) 가져오기
     * 
     * @param int yyyy, int mm
     * @return String
     */
    public static int getFirstDayNum(int yyyy, int mm) {
        Calendar cal = Calendar.getInstance();
        // 1 2 3 4 5 6 7
        // { "일", "월", "화", "수", "목", "금", "토" };

        cal.set(yyyy, mm - 1, 1);

        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }
    
    /**
     * 해당월에 01일자에 대한 요일(숫자) 가져오기
     * 
     * @param int yyyy, int mm
     * @return String
     */
    public static int getFirstDayNum(int yyyy, int mm, int dd) {
        Calendar cal = Calendar.getInstance();
        // 1 2 3 4 5 6 7
        // { "일", "월", "화", "수", "목", "금", "토" };

        cal.set(yyyy, mm - 1, dd);

        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 해당일자에 대한 요일 가져오기
     * 
     * @param int yyyy, int mm, int dd
     * @return String
     */
    public static String getDay(int yyyy, int mm, int dd) {
        Calendar cal = Calendar.getInstance();
        // 1 2 3 4 5 6 7
        final String[] week = { "일", "월", "화", "수", "목", "금", "토" };

        cal.set(yyyy, mm - 1, dd);

        return week[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 입력받은 년원일, 일자를 더하여 년월일 구한다.
     * 
     * @param String
     *            yyyymmdd, int dd
     * @return String
     */
    public static String getDay(String yyyymmdd, int dd) {
        Calendar cal = Calendar.getInstance();
        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        cal.set(Integer.parseInt(yyyymmdd.substring(0, 4)),
                Integer.parseInt(yyyymmdd.substring(4, 6)) - 1,
                Integer.parseInt(yyyymmdd.substring(6)) + dd);
        java.util.Date d = cal.getTime();
        String rtnDate = sdf.format(d);
        return rtnDate;
    }

    /**
     * 입력받은 날짜를 변환 2011.12.11 or 2012-12-01;
     * 
     * @param type
     *            -
     * @return String
     */
    public static String getDate(String yyyyMMdd, String type) {
        yyyyMMdd = sDateFilter(yyyyMMdd);
        StringBuffer sb = new StringBuffer();
        sb.append(yyyyMMdd.substring(0, 4));
        sb.append(type);
        sb.append(yyyyMMdd.substring(4, 6));
        sb.append(type);
        sb.append(yyyyMMdd.substring(6));

        return sb.toString();
    }

    /**
     * 문자열을 숫자형식으로 변환
     * 
     * @param str
     * @return str
     */
    public static String strToNumber(String str) {
        if (str == null || str.equals(""))
            return "";
        long value = Long.parseLong(str);
        NumberFormat FORMAT = NumberFormat.getInstance();
        FORMAT.setGroupingUsed(true); // 숫자에 세자리마다 ','를 넣게 세팅한다.
        return FORMAT.format(value); // 입력받은 문자열에 포맷을 적용하여 리턴한다.
    }

    /**
     * 문자열을 숫자형식으로 변환
     * 
     * @param str
     * @return str
     */
    public static String strToNumberNull(String str, boolean chk) {
        str = isNull(str);
        if (str.equals(""))
            str = "0";
        long value = Long.parseLong(str);
        NumberFormat FORMAT = NumberFormat.getInstance();
        FORMAT.setGroupingUsed(chk); // 숫자에 세자리마다 ','를 넣게 세팅한다.
        return FORMAT.format(value); // 입력받은 문자열에 포맷을 적용하여 리턴한다.
    }

    /**
     * 현재 페이지 권한 정보
     * 
     * @param nowPage
     * @return
     */
    public static boolean getAuthorityChk(String sUserGroupId, String sPageId) {
        log.DebugLog("getAuthorityChk");
        // boolean retChk = false;
        //
        // CommonDao dao = new CommonDao();
        //
        // ArrayList params = new ArrayList();
        // // 사용자그룹ID
        // params.add(sUserGroupId);
        // // 현재 페이지 ID
        // params.add(sPageId);
        //
        // if (0 < dao.getAuthorityChk(params).size()) retChk = true;

        boolean retChk = true;
        return retChk;
    }

    /**
     * 특수문자 제거
     * 
     * @param inData
     * @return String
     */
    public static String StringFilter(String inData) {
        String retData = "";
        // [~], [`], [!], [@], [#],
        // [$], [%], [^], [&], [*],
        // [(], [)], [_], [+], [=],
        // [|], [{], [}], [[], []],
        // [;], [:], ['], [<], [>],
        // [?], [/]
        String[] sFilterWord = { "\\~", "\\`", "\\!", "\\@", "\\#", "\\$",
                "\\%", "\\^", "\\&", "\\*", "\\(", "\\)", "\\_", "\\+", "\\=",
                "\\|", "\\{", "\\}", "\\;", "\\:", "\\'", "\\<", "\\>", "\\?",
                "\\/" };

        for (int i = 0; i < sFilterWord.length; i++) {
            retData = inData.replaceAll(sFilterWord[i], "");
            inData = retData;
        }
        return inData;
    }

    /**
     * 특정문자 치환
     * 
     * @param inData
     * @return String
     */
    public static String sDateFilter(String inData) {
        String retData = "";
        String[] sFilterWord = { "-", ".", "/", ":" };
        for (int i = 0; i < sFilterWord.length; i++) {
            String im = sFilterWord[i];
            retData = inData.replace(im, "");
            inData = retData;
        }
        return inData;
    }

    /**
     * jsp 콤보 설정 <option value='minorcd'>cdname2</option>
     * 
     * @param majorcd
     * @return String
     */
    /*
     * public static String getCodeBook2(String majorcd, String sol) {
     * 
     * StringBuffer retData = new StringBuffer(); CommonDao dao = new
     * CommonDao(); retData.append("<option value=''></option>\n"); ArrayList
     * retList = dao.getCodeBookSearch(majorcd); for (int i = 0; i <
     * retList.size(); i++) { Hashtable ht = (Hashtable) retList.get(i); String
     * value = ComUtil.isNull((String) ht.get("cdname2"));
     * 
     * retData.append("<option value='"); retData.append(value);
     * retData.append("'"); if(sol !="" && value.equals(sol)){
     * retData.append("selected"); } retData.append(">"); retData.append(value);
     * retData.append("</option>\n"); } return retData.toString(); }
     */

    /**
     * jsp 콤보 설정 <option value='minorcd'>cdname2</option>
     * 
     * @param majorcd
     * @return String
     */
    /*
     * public static String getCodeBook3(String majorcd, String sol) {
     * 
     * StringBuffer retData = new StringBuffer(); CommonDao dao = new
     * CommonDao(); retData.append("<option value=''></option>\n"); ArrayList
     * retList = dao.getCodeBookSearch2(majorcd); for (int i = 0; i <
     * retList.size(); i++) { Hashtable ht = (Hashtable) retList.get(i); String
     * value = ComUtil.isNull((String) ht.get("cdname2"));
     * 
     * retData.append("<option value='"); retData.append(value);
     * retData.append("'"); if(sol !="" && value.equals(sol)){
     * retData.append("selected"); } retData.append(">"); retData.append(value);
     * retData.append("</option>\n"); } return retData.toString(); }
     */

    /**
     * String에 문자가 속해있을경우 그냥 반환. String이 null 이나 ""일경우 그냥 반환. String이 숫자일 경우 ,를
     * 넣어준다.(단위도 뒤에 붙여줌)
     * 
     * @param amt
     *            String
     * @param back
     *            String 단위 (넣고 싶지않으면 "")
     * @return retAmt String
     */
    public static String setComma(String amt, String back) {

        String retAmt = "___________________";
        char chk;
        NumberFormat nf = new DecimalFormat("#,##0.#"); // 숫자포맷주기

        // Case1 : null or ""
        if (null == amt || "null".equals(amt) || "".equals(amt)) {
            retAmt = amt;
            return retAmt;
        }

        // Exception Case
        if (amt.length() >= 3) {
            String beforeAmt = amt.substring(0, amt.length() - 2);
            String afterAmt = amt.substring(amt.length() - 2, amt.length());

            if (afterAmt.equals("초과") || afterAmt.equals("이상")
                    || afterAmt.equals("미만") || afterAmt.equals("이하")) {

                log.DebugLog("-----Exception Case : 초과, 이상, 미만, 이하일 때");
                log.DebugLog("-----앞에 스트링 : " + beforeAmt + "/ 뒤에 스트링 : "
                        + afterAmt);

                // 문자일 경우
                for (int i = 0; i < beforeAmt.length(); i++) {
                    chk = beforeAmt.charAt(i);
                    if (!Character.isDigit(chk)) {
                        if (chk == '.') {
                            continue;
                        }
                        retAmt = beforeAmt + afterAmt;
                        return retAmt;
                    }
                }

                // 숫자일 경우
                if (!retAmt.equals(beforeAmt)) {
                    retAmt = nf.format(Double.parseDouble(beforeAmt))
                            + afterAmt + back;
                    return retAmt;
                }
            }
        }

        // Case2 : 문자
        for (int i = 0; i < amt.length(); i++) {
            chk = amt.charAt(i);
            if (!Character.isDigit(chk)) {
                if (chk == '.' || (chk == '-' && i == 0)) {
                    continue;
                }
                retAmt = amt;
                return retAmt;
            }
        }

        // Case3 : 숫자
        if (!retAmt.equals(amt)) {
            retAmt = nf.format(Double.parseDouble(amt)) + back;
            return retAmt;
        }

        return retAmt;
    }

    /**
     * 핸드폰 전화번호 자르기 String이 null 이나 ""일경우 그냥 반환. String이 숫자일 경우 ,를 넣어준다.(단위도 뒤에
     * 붙여줌)
     * 
     * @param hp_no
     *            String
     * @param back
     *            String 단위 (넣고 싶지않으면 "")
     * @return retAmt String
     */
    public static String setCellPhone(String hp_no) {

        int lengthHp_no = hp_no.length();
        String retHp_no1 = "";
        String retHp_no2 = "";
        String retHp_no3 = "";

        // 10자리나 11자리가 아니면 바로 리턴
        if (lengthHp_no != 11 && lengthHp_no != 10) {
            return hp_no;
        }

        if (hp_no.length() == 11) {

            retHp_no1 = hp_no.substring(0, 3);
            retHp_no2 = hp_no.substring(3, 7);
            retHp_no3 = hp_no.substring(7, 11);

        } else if (hp_no.length() == 10) {

            retHp_no1 = hp_no.substring(0, 3);
            retHp_no2 = hp_no.substring(3, 6);
            retHp_no3 = hp_no.substring(6, 10);

        }
        return retHp_no1 + "-" + retHp_no2 + "-" + retHp_no3;

    }

    /**
     * 핸드폰 전화번호 자르기 - 특수문자( '-', ',', '.', ' ' 등 포함시) String이 null 이나 ""일경우 그냥
     * 반환.
     * 
     * @param hp_no
     *            String
     * @return retAmt String
     */
    public static String setCellPhone2(String hp_no) {

        String reStr = "";
        String[] arrStr;

        if (hp_no.indexOf("-") > 0) {
            arrStr = hp_no.split("-");
            if (arrStr.length == 3) {
                reStr = arrStr[0] + "-" + arrStr[1] + "-" + arrStr[2];
            } else {
                reStr = setCellPhone(StringFilter(hp_no));
            }
        } else if (hp_no.indexOf(".") > 0) {
            arrStr = hp_no.split(".");
            if (arrStr.length == 3) {
                reStr = arrStr[0] + "-" + arrStr[1] + "-" + arrStr[2];
            } else {
                reStr = setCellPhone(StringFilter(hp_no));
            }
        } else if (hp_no.indexOf(",") > 0) {
            arrStr = hp_no.split(",");
            if (arrStr.length == 3) {
                reStr = arrStr[0] + "-" + arrStr[1] + "-" + arrStr[2];
            } else {
                reStr = setCellPhone(StringFilter(hp_no));
            }
        } else if (hp_no.indexOf(" ") > 0) {
            arrStr = hp_no.split(" ");
            if (arrStr.length == 3) {
                reStr = arrStr[0] + "-" + arrStr[1] + "-" + arrStr[2];
            } else {
                reStr = setCellPhone(StringFilter(hp_no));
            }
        } else {
            reStr = setCellPhone(StringFilter(hp_no));
        }

        return reStr;

    }
   
    /**
     * @Method Name : setParam
     * @작성일 : 2012. 10. 19.
     * @작성자 : Kim, YunKwan
     * @변경이력 :
     * @Method 설명 : 파라메터 값만 재세팅 합니다.
     * @param request
     * @param obj
     * @param _rtnurl
     * @return
     */
    public static Map<String, Object> setParam(HttpServletRequest request,
            Object obj) {

        Map<String, Object> param_map = new HashMap<String, Object>();

        if (obj != null) {
            ObjectMapper m = new ObjectMapper();
            param_map = m.convertValue(obj, Map.class);
        }

        if (request != null) {
            Enumeration<?> param = request.getParameterNames();
            while (param.hasMoreElements()) {
                String name = (String) param.nextElement();
                String value = String.valueOf(request.getParameter(name));
                param_map.put(name, value);
            }
        }

        String[] SecurRvKey =  CmeProperties.getProperty("Secure.Input.Name").split("\\,");
        for(String _s : SecurRvKey){
            if(param_map.containsKey(_s)){
                param_map.remove(_s);
            }
        }       
        
        return param_map;
    }

    /**
     * @Method Name : preUrlInfo
     * @작성일 : 2012. 9. 20.
     * @작성자 : Kim, YunKwan
     * @변경이력 :
     * @Method 설명 : 이전 URL 주소를 알아옵니다.
     * @param request
     * @return
     */
    public static String preUrlInfo(HttpServletRequest request) {
        Enumeration<?> param = request.getHeaders("referer");
        String strParam = "";
        String preUrl = "";
        while (param.hasMoreElements()) {
            String name = (String) param.nextElement();
            String value = request.getParameter(name);
            strParam += name + "=" + value + "&";
        }
        preUrl = request.getRequestURL() + "?" + strParam;
        return preUrl;

        // log.DebugLog(request.getHeader("referer"));
        /*
         * URL url = null; String queryStr = ""; try { url = new
         * URL(request.getHeader("referer")); queryStr = url.getQuery(); } catch
         * (MalformedURLException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); }
         */
        // log.DebugLog(url.getPath()); //호스트를 제외한 패스
        // log.DebugLog(queryStr);//get 파라메터
    }

    public static String MapToParamStr(Map map) {
        String Rtnstr = "";
        int cnt = 0;
        StringBuffer sb = new StringBuffer();
        Iterator<String> keys = map.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            String val = String.valueOf(map.get(key));
            if (cnt > 0) {
                sb.append("&");
            }
            sb.append(key + "=" + val);
            cnt++;
        }
        Rtnstr = sb.toString();
        return Rtnstr;
    }

    public static String MapToParamStr(Map map, ArrayList<String> removeKey) {
        String Rtnstr = "";
        int cnt = 0;
        StringBuffer sb = new StringBuffer();
        Iterator<String> keys = map.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            String val = String.valueOf(map.get(key));
            boolean isAdd = true;
            
            if(removeKey !=null && removeKey.size() > 0){
                for(int i=0; i<removeKey.size(); i++){
                    if(removeKey.get(i).equals(key)){
                        isAdd = false;
                        break;
                    }                   
                }
                
                if(isAdd){
                    if (cnt > 0) {
                        sb.append("&");
                    }
                    sb.append(key + "=" + val);                 
                }
                        
            }else{
                if (cnt > 0) {
                    sb.append("&");
                }               
                sb.append(key + "=" + val);
            }
            cnt++;
        }
        Rtnstr = sb.toString();
        return Rtnstr;
    }   
    
    /**
     * @Method Name : dupChk
     * @작성일 : 2012. 11. 1.
     * @작성자 : choijk
     * @변경이력 :
     * @Method 설명 : 배열 중복제거
     * @param str
     * @return List
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List dupChk(List list) {
        HashSet hashSet = new HashSet(list);
        List list1 = new ArrayList(hashSet);
        Collections.sort(list1); // 정렬
        return list1;
    }

    /**
     * @Method Name : getRandomStr
     * @작성일 : 2012. 11. 2.
     * @작성자 : Kim, YunKwan
     * @변경이력 :
     * @Method 설명 : 랜덤한 알파벳 문자를 조합합니다.
     * @param g
     *            : 0 : 대문자, 1: 소문자, 2: 대소문자 mix
     * @param len
     * @return
     */
    public static String getRandomStr(int g, int len) {

        StringBuffer sb = new StringBuffer();
        char c = 'a';

        for (int i = 0; i < len; i++) {
            switch (g) {
            case 0:// 대문자
                c = (char) ((Math.random() * 26) + 65);
                sb.append(c);
                break;
            case 1:// 소문자
                c = (char) ((Math.random() * 26) + 97);
                sb.append(c);
                break;
            case 2:
                double _num = 0;
                do {
                    _num = (Math.random() * 59) + 65;
                } while ((int) _num > 90 && (int) _num < 97
                        || (int) _num == 123);

                c = (char) (int) _num;
                sb.append(c);
                break;
            }
        }
        return sb.toString();

    }

    /**
     * @Method Name : getWeekCount
     * @작성일 : 2012. 11. 1.
     * @작성자 : Chiss
     * @변경이력 :
     * @Method 설명 : 해당일의 주차 구하기
     * @param str
     * @return List
     */
    public static int getWeekCount(int year, int month, int day) {
        Calendar destDate = Calendar.getInstance();
        destDate.set(year, month - 1, day);

        return destDate.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * @Method Name : removeIllegalTag
     * @작성일 : 2012. 11. 7.
     * @작성자 : Kim, YunKwan
     * @변경이력 :
     * @Method 설명 : 모든 TAG를 제거합니다. - TEXT DB에 인서트전 시전.....
     * @param str
     * @return
     */
    public static String removeIllegalTag(String str) {
        str = StringUtils.removeTag(str);
        str = StringUtils.removeScriptTag(str);
        str = StringUtils.replaceHtml(str);
        return str;
    }

    
    public static String removeBoardContentIllegalTag(String str){
        str = StringUtils.removeTag(str);
        str = removeActionTag(str);
        return str;     
    }
    
    
    public static String removeActionTag(String str) {
        str = StringUtils.removeScriptTag(str);
        return str;
    }

    /**
     * @Method Name : getRemoteIP
     * @작성일 : 2012. 11. 8.
     * @작성자 : Administrator
     * @변경이력 :
     * @Method 설명 : 유저 아이피(IP) 조회
     * @return
     */
    public static String getLocalIP() throws Exception {

        String localhost = "";
        try {
            localhost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        StringTokenizer st = new StringTokenizer(localhost, "/");
        String host = st.nextToken();
        String ip = st.nextToken();

        return ip;
    }

    public static String getRemoteIP(HttpServletRequest request)
            throws Exception {
        
//          getReadHeaderInfo(request);
        String ipaddress = request.getHeader("Proxy-Client-IP");

        if ("".equals(StringUtils.checkNull(ipaddress))) 
            ipaddress = request.getRemoteAddr();        
        
        return ipaddress;
    }

    public static void getReadHeaderInfo(HttpServletRequest request) {
        Enumeration names = request.getHeaderNames();
        String header = "";
        StringBuffer sb = new StringBuffer();
        while (names.hasMoreElements()) {
            header = (String) names.nextElement();
            sb.append(header + " :: " + request.getHeader(header) + "|");
        }
        log.DebugLog(sb.toString());
    }
    
    
    public static StringBuffer readTextInFile(String file_path)
            throws IOException {
        int i;
        StringBuffer full_text = new StringBuffer();

        try {
            FileReader in = new FileReader(file_path);
            while ((i = in.read()) != -1) {
                char ch = (char) i;
                full_text.append(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return full_text;
    }

    public static void writeJsonFile(String path, Object obj)
            throws IOException {
        /*Gson gson = new Gson();
        String json = gson.toJson(obj);

        String _folderNm = CmeProperties.getProperty("MenuData.Path");
        File _folder = new File(_folderNm);

        if (!_folder.exists()) {
            _folder.mkdir();
        }
        File file = new File(path);

        if (file.exists()) {
            file.delete();
        } else {
            file.createNewFile();
        }

        FileWriter writer = new FileWriter(path);
        writer.write(json);
        writer.close();*/

    }
    
    public static void WriteErrorLog(String msg){
//          boolean isFile = Boolean.parseBoolean(EgovProperties.getProperty("Error.IsLog"));
/*      boolean isFile = Boolean.parseBoolean(EgovProperties.getLogProperty("Error.IsLog"));
        String path = EgovProperties.getLogProperty("Error.Log.FilePath");
        
        if(isFile){
            String folderName = "";
            String fileName = "";
            String logTime = "";
            SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMdd", new Locale("ko","KOREA")); //폴더
            SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd.HH", new Locale("ko","KOREA")); //파일
            SimpleDateFormat fmt3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("ko","KOREA")); //폴더
            Date nowdate = new Date();
            
            folderName = fmt1.format(nowdate);
            fileName = fmt2.format(nowdate);
            logTime = fmt3.format(nowdate);
            String _prefix = "";
            try {
                
                if(msg.indexOf("WARN") > -1){
                    _prefix = "warn";
                }else if(msg.indexOf("INFO") > -1){
                    _prefix = "info";
                }else if(msg.indexOf("DEBUG") > -1){
                    _prefix = "debug";
                }else{
                    _prefix = "error";
                }
                
                String errFilePath = path + folderName+"\\"+ _prefix +"_"+fileName+".txt";
                File _file = new File(errFilePath);
                if(_file.exists()){
                    FileWriter fw = new FileWriter(errFilePath,true); //the true will append the new data
                    fw.write("["+logTime+"]: "+msg+"\n");//appends the string to the file
                    fw.close();                 
                }else{
                    makeFile(errFilePath);
                    
                    FileWriter fw = new FileWriter(errFilePath);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("["+logTime+"]: "+msg+"\n");
                    bw.close();
                    fw.close();                 
                }
                
            } catch (Exception e) {
                //log.ViewErrorLog("###############################error:"+e.getMessage());
                Logger logger = Logger.getLogger(ComUtil.class);
                logger.error("File Log Write Error" + e.getMessage(), e.getCause());
                FileWriteErrorLog(e.getMessage());
            }
        }*/
    }

    public static void FileWriteErrorLog(String msg){
/*      boolean isFile = Boolean.parseBoolean(EgovProperties.getLogProperty("Error.IsLog"));
        String path = EgovProperties.getLogProperty("Error.FileWriteError.Path");
        
        if(isFile){
            String folderName = "";
            String fileName = "";
            String logTime = "";
            SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMdd", new Locale("ko","KOREA")); //폴더
            SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd.HH", new Locale("ko","KOREA")); //파일
            SimpleDateFormat fmt3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("ko","KOREA")); //폴더
            Date nowdate = new Date();
            
            folderName = fmt1.format(nowdate);
            fileName = fmt2.format(nowdate);
            logTime = fmt3.format(nowdate);
            
            try {
                
                String errFilePath = path + folderName+"\\error_"+fileName+".txt";
                File _file = new File(errFilePath);
                if(_file.exists()){
                    FileWriter fw = new FileWriter(errFilePath,true); //the true will append the new data
                    fw.write("["+logTime+"]: "+msg+"\n");//appends the string to the file
                    fw.close();                 
                }else{
                    makeFile(errFilePath);
                    
                    FileWriter fw = new FileWriter(errFilePath);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("["+logTime+"]: "+msg+"\n");
                    bw.close();
                    fw.close();                 
                }
                
            } catch (Exception e) {
                //log.ViewErrorLog("###############################error:"+e.getMessage());
                Logger logger = Logger.getLogger(ComUtil.class);
                logger.error("File Log Write Error" + e.getMessage(), e.getCause());
            }
        }*/
    }   
    
    public static void makeFile(String path){
        
        String[] arFile = path.split("\\\\");
        StringBuffer folderNm = new StringBuffer();
        for(int i=0; i< arFile.length-1;i++){
            folderNm.append(arFile[i]+"\\\\");
        }
        
        String fullPath = folderNm.toString() + arFile[arFile.length-1];
        //System.out.println(fullPath);
        File _folder = new File(folderNm.toString());
        
        if(!_folder.exists()){
            _folder.mkdirs();
        }
        
        File _file = new File(fullPath);
        if(!_file.exists()){
            try {
                _file.createNewFile();
            } catch (IOException e) {
                log.ViewErrorLog("FileCreate Error::::::::::::::::::::::::::::"+e.getMessage());
            }
        }
    }

    public static void checkFolder(String path){
        
        String[] arFile = path.split("\\\\");
        StringBuffer folderNm = new StringBuffer();
        for(int i=0; i< arFile.length-1;i++){
            folderNm.append(arFile[i]+"\\\\");
        }
        //System.out.println(folderNm.toString());
        File _folder = new File(folderNm.toString());
        
        if(!_folder.exists()){
            _folder.mkdirs();
        }
    }   
    
    public static void makeFile(String folder, String file){
        
        //File _file = new File(path);
        File _folder = new File(folder);
        
        if(!_folder.exists()){
            log.DebugLog("################################################################");
            _folder.mkdirs();
        }
        File _file = new File(folder+file);
        if(!_file.exists()){
            try {
                _file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }

    public static boolean IsfileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static Date getAddDay(Date date, Integer day) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }
    
    public static Date getAddMonth(Date date, Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }   

    /**
     * @Method Name : copyObject
     * @작성일 : 2012. 11. 23.
     * @작성자 : choijk
     * @변경이력 :
     * @Method 설명 :vo class obj2의 값을 obj1에다가 넣고 싶을때 사용하세용 obj1에 데이터가 있을경우에는 들어
     *         가지 않고 null 이거나 "" 일때 들어 갑니다. 현재는 String 만 했는데 다른 타입이 필요하면 분기로
     *         구현해야됨
     * @param obj1
     * @param obj2
     * @return
     * @throws Exception
     */
    public static Object copyObject(Object obj1, Object obj2) throws Exception {

        Method[] methods = obj2.getClass().getMethods();

        for (int i = 0; i < methods.length; i++) {
            String hMethodName = methods[i].getName();
            // 접두사
            String methodHead = hMethodName.substring(0, 3);
            // 접미사
            String methodTail = hMethodName.substring(3, hMethodName.length());
            String tMethodName = "set" + methodTail;

            if ("get".equals(methodHead)) { // getMethod 만 호출

                Method dymMth = obj2.getClass().getMethod(hMethodName,
                        new Class[] {});
                Object value = dymMth.invoke(obj2, new Object[] {});
                if (value != null) {
                    try {
                        if ("get".equals(methodHead)) { // getMethod 만 호출
                            Method dymMth1 = obj1.getClass().getMethod(
                                    hMethodName, new Class[] {});
                            if (dymMth1.getReturnType() == java.lang.String.class) {
                                String obj1Val = (String) dymMth1.invoke(obj1,
                                        new Object[] {});
                                if ("".equals(ComUtil.isNull(obj1Val))) {
                                    Method dymMth2 = obj1
                                            .getClass()
                                            .getMethod(
                                                    tMethodName,
                                                    new Class[] { java.lang.String.class });
                                    String val = (String) value;
                                    dymMth2.invoke(obj1,
                                            new Object[] { val.trim() });
                                }
                            } else if (dymMth1.getReturnType() == int.class) {
                                int obj1Val = (Integer) dymMth1.invoke(obj1,
                                        new Object[] {});
                                if (obj1Val == 0) { // int 이므로 default 0이다 그러므로
                                                    // vo 에 value 가 0 이면 저장되믄로
                                                    // 이부분은 신경써야됨
                                    Method dymMth2 = obj1.getClass().getMethod(
                                            tMethodName,
                                            new Class[] { int.class });
                                    dymMth2.invoke(obj1,
                                            new Object[] { (Integer) value });
                                }

                            } else if (dymMth1.getReturnType() == List.class) {
                                List obj1Val = (List) dymMth1.invoke(obj1,
                                        new Object[] {});
                                if (obj1Val.size() > 0) {
                                    Method dymMth2 = obj1.getClass().getMethod(
                                            tMethodName,
                                            new Class[] { List.class });
                                    dymMth2.invoke(obj1,
                                            new Object[] { (List) value });
                                }
                            }

                        }
                    } catch (java.lang.NoSuchMethodException ex) {
                        continue;
                    } catch (java.lang.NullPointerException ex) { //다른 타입의 객체가 들어 왔을경우 해당 메서드가 없을경우 메서드가 있는것만 진행시키기 위해 
                        continue;
                    }
                }
            }
        }

        return obj1;
    }
    
    /**
     * @Method Name : String 객체의 UTF-8 로 변경
     * @작성일 : 2012. 11. 23.
     * @작성자 : choijk
     * @변경이력 :
     * @Method 설명 : UTF-8 타입이 아닌경우 UTF-8로 변경
     * @param obj1
     * @param obj2
     * @param type1
     * @param type2
     * @return
     * @throws Exception
     */
    public static Object changeObject(Object obj1, Object obj2, String type1, String type2) throws Exception {

        Method[] methods = obj2.getClass().getMethods();

        for (int i = 0; i < methods.length; i++) {
            String hMethodName = methods[i].getName();
            // 접두사
            String methodHead = hMethodName.substring(0, 3);
            // 접미사
            String methodTail = hMethodName.substring(3, hMethodName.length());
            String tMethodName = "set" + methodTail;

            if ("get".equals(methodHead)) { // getMethod 만 호출

                Method dymMth = obj2.getClass().getMethod(hMethodName,
                        new Class[] {});
                Object value = dymMth.invoke(obj2, new Object[] {});
                if (value != null) {
                    try {
                        if ("get".equals(methodHead)) { // getMethod 만 호출
                            Method dymMth1 = obj1.getClass().getMethod(
                                    hMethodName, new Class[] {});
                            if (dymMth1.getReturnType() == java.lang.String.class) {
                                String obj1Val = (String) dymMth1.invoke(obj1,
                                        new Object[] {});
                                
                                
                                if ("".equals(ComUtil.isNull(obj1Val))) {
                                    Method dymMth2 = obj1
                                            .getClass()
                                            .getMethod(
                                                    tMethodName,
                                                    new Class[] { java.lang.String.class });
                                    String val = (String)value;
                                    val = new String(val.getBytes(type2), type1);
                                    dymMth2.invoke(obj1,
                                            new Object[] { val.trim() });
                                }
                            }
                        }
                    } catch (java.lang.NoSuchMethodException ex) {
                        continue;
                    } catch (java.lang.NullPointerException ex) { //다른 타입의 객체가 들어 왔을경우 해당 메서드가 없을경우 메서드가 있는것만 진행시키기 위해 
                        continue;
                    }
                }
            }
        }

        return obj1;
    }
    
    /**
     * @Method Name : formAction
     * @작성일 : 2012. 11. 28.
     * @작성자 : hrpark
     * @변경이력 :
     * @Method 설명 : FORM을 생성하여 넘기기
     * @param response
     * @param dataMap
     *            :HIDDEN으로 담을 값
     * @param url
     *            : 넘길 URL
     * @return
     * @throws IOException
     */
    public static Object formAction(HttpServletResponse response,
            HashMap dataMap, String url) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter output = response.getWriter();
        StringBuffer sb = new StringBuffer();
        sb.append("<form name=\"frm\" method=\"post\" action=\"" + url + "\">");

        if (dataMap != null) {
            Set keySet = dataMap.keySet();
            Object[] hashKeys = keySet.toArray();
            for (int col = 0; col < dataMap.size(); col++) {
                String key = (String) hashKeys[col];
                String value = String.valueOf(dataMap.get(key));
                sb.append("<input type=\"hidden\" name=\"" + key + "\" value='" + value + "'>");
            }
        }
        sb.append("</form>");
        sb.append("<script type='text/javascript' >");
        sb.append("document.frm.submit();");
        sb.append("</script>");
        log.DebugLog("폼전송:"+sb.toString());
        output.println(sb.toString());
        output.flush();
        output.close();

        return null;
    }
    
    /**
     * @Method Name  : formGetAction
     * @작성일   : 2015. 8. 1. 
     * @작성자   : (주)크림소프트 김윤관
     * @변경이력  :
     * @Method 설명 : 폼전송 GET 방식
     * @param response
     * @param dataMap
     * @param url
     * @return
     * @throws IOException
     */
    public static Object formGetAction(HttpServletResponse response,
            HashMap dataMap, String url) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter output = response.getWriter();
        StringBuffer sb = new StringBuffer();
        sb.append("<form name=\"frm\" method=\"get\" action=\"" + url + "\">");

        if (dataMap != null) {
            Set keySet = dataMap.keySet();
            Object[] hashKeys = keySet.toArray();
            for (int col = 0; col < dataMap.size(); col++) {
                String key = (String) hashKeys[col];
                String value = String.valueOf(dataMap.get(key));
                sb.append("<input type=\"hidden\" name=\"" + key + "\" value='" + value + "'>");
            }
        }
        sb.append("</form>");
        sb.append("<script type='text/javascript' >");
        sb.append("document.frm.submit();");
        sb.append("</script>");
        log.DebugLog("폼전송:"+sb.toString());
        output.println(sb.toString());
        output.flush();
        output.close();

        return null;
    }   

    /**
     * @Method Name  : formGetActionMsg
     * @작성일   : 2015. 8. 1. 
     * @작성자   : (주)크림소프트 김윤관
     * @변경이력  :
     * @Method 설명 : 폼전송 GET 방식 With Msg
     * @param response
     * @param dataMap
     * @param url
     * @param msg
     * @return
     * @throws IOException
     */
    public static Object formGetActionMsg(HttpServletResponse response,
            HashMap dataMap, String url, String msg) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter output = response.getWriter();
        StringBuffer sb = new StringBuffer();
        sb.append("<form name=\"frm\" method=\"get\" action=\"" + url + "\">");     

        if (dataMap != null) {
            Set keySet = dataMap.keySet();
            Object[] hashKeys = keySet.toArray();
            for (int col = 0; col < dataMap.size(); col++) {
                String key = (String) hashKeys[col];
                String value = (String) dataMap.get(key);
                sb.append("<input type=\"hidden\" name=\"" + key + "\" value='" + value + "'>");                
            }
        }
        
        sb.append("</form>");
        sb.append("<script type='text/javascript' >");
        sb.append("document.frm.submit();");
        if(!isNull(msg).equals("")){
            sb.append("alert('" + msg.replaceAll("\\'", "\\\"") + "');");       
        }
        sb.append("</script>");
        output.println(sb.toString());
        output.flush();
        output.close();

        return null;
    }   
    
    
    /**
     * @Method Name : formActionMsg
     * @작성일 : 2012. 11. 28.
     * @작성자 : hrpark_solbi
     * @변경이력 :
     * @Method 설명 :메시지 출력하고 FORM을 생성하여 넘기기
     * @param response
     * @param dataMap
     *            : HIDDEN으로 담을 값
     * @param url
     *            : 넘길 URL
     * @param msg
     * @return
     * @throws IOException
     */
    public static Object formActionMsg(HttpServletResponse response,
            HashMap dataMap, String url, String msg) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter output = response.getWriter();
        StringBuffer sb = new StringBuffer();
        sb.append("<form name=\"frm\" method=\"post\" action=\"" + url + "\">");        

        if (dataMap != null) {
            Set keySet = dataMap.keySet();
            Object[] hashKeys = keySet.toArray();
            for (int col = 0; col < dataMap.size(); col++) {
                String key = (String) hashKeys[col];
                String value = (String) dataMap.get(key);
                sb.append("<input type=\"hidden\" name=\"" + key + "\" value='" + value + "'>");                
            }
        }
        
        sb.append("</form>");
        sb.append("<script type='text/javascript' >");
        sb.append("document.frm.submit();");
        if(!isNull(msg).equals("")){
            sb.append("alert('" + msg.replaceAll("\\'", "\\\"") + "');");       
        }
        sb.append("</script>");
        output.println(sb.toString());
        output.flush();
        output.close();

        return null;
    }
    
    
    public static Object formActionMsg(HttpServletResponse response,
         String url, String msg) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter output = response.getWriter();
        StringBuffer sb = new StringBuffer();
        
        sb.append("<form name=\"frm\" method=\"post\" action=\"" + url+ "\">");
        sb.append("</form>");
        sb.append("<script type='text/javascript' >");
        if(!StringUtils.checkNull(msg).equals("")){
            sb.append("alert('" + msg.replaceAll("\\'", "\\\"") + "');");
        }
        sb.append("document.frm.submit();");
        sb.append("</script>");
        log.DebugLog("폼전송:"+sb.toString());
        output.println(sb.toString());
        output.flush();
        output.close();

        return null;
    }
    
    

    /**
     * @Method Name  : postRedirect
     * @작성일   : 2015. 8. 1. 
     * @작성자   : (주)크림소프트 김윤관
     * @변경이력  :
     * @Method 설명 : Post 방식 리다이렉트
     * @param response
     * @param request
     * @param obj
     * @param url
     * @param msg
     * @throws IOException
     */
    public static void postRedirect(HttpServletResponse response,
            HttpServletRequest request, Object obj, String url, String msg)
            throws IOException {

        Map<String, Object> param_map = setParam(request, obj);
        log.DebugLog("PostRedirect Param:"+ComUtil.MapToParamStr(param_map));
        
        if (!"".equals(StringUtils.checkNull(msg))) {
            formActionMsg(response, (HashMap<String, Object>) param_map, url,
                    msg);
        } else {
            formAction(response, (HashMap<String, Object>) param_map, url);
        }

    }
    
    /**
     * @Method Name  : postRedirect
     * @작성일   : 2015. 8. 1. 
     * @작성자   : (주)크림소프트 김윤관
     * @변경이력  :
     * @Method 설명 : post 방식 리다이렉트
     * @param response
     * @param request
     * @param url
     * @throws IOException
     */
    public static void postRedirect(HttpServletResponse response,
            HttpServletRequest request, String url)
            throws IOException {

        Object obj = null;
        String msg = "";
        
        Map<String, Object> param_map = setParam(request, obj);
        log.DebugLog("PostRedirect Param:"+ComUtil.MapToParamStr(param_map));
        
        if (!"".equals(StringUtils.checkNull(msg))) {
            formActionMsg(response, (HashMap<String, Object>) param_map, url,
                    msg);
        } else {
            formAction(response, (HashMap<String, Object>) param_map, url);
        }

    }   

    /**
     * @Method Name  : getRedirect
     * @작성일   : 2015. 8. 1. 
     * @작성자   : (주)크림소프트 김윤관
     * @변경이력  :
     * @Method 설명 : Get 방식 리다이렉트
     * @param response
     * @param request
     * @param obj
     * @param url
     * @param msg
     * @throws IOException
     */
    public static void getRedirect(HttpServletResponse response,
            HttpServletRequest request, Object obj, String url, String msg)
            throws IOException {

        Map<String, Object> param_map = setParam(request, obj);
        log.DebugLog("PostRedirect Param:"+ComUtil.MapToParamStr(param_map));
        
        if (!"".equals(StringUtils.checkNull(msg))) {
            formGetActionMsg(response, (HashMap<String, Object>) param_map, url,
                    msg);
        } else {
            formGetAction(response, (HashMap<String, Object>) param_map, url);
        }

    }
    
    public static void getRedirect(HttpServletResponse response,
            HttpServletRequest request, String url)
            throws IOException {

        Object obj = null;
        String msg = "";
        
        Map<String, Object> param_map = setParam(request, obj);
        log.DebugLog("PostRedirect Param:"+ComUtil.MapToParamStr(param_map));
        
        if (!"".equals(StringUtils.checkNull(msg))) {
            formGetActionMsg(response, (HashMap<String, Object>) param_map, url,
                    msg);
        } else {
            formGetAction(response, (HashMap<String, Object>) param_map, url);
        }

    }   
    
    
    /**
     * @Method Name : alertMsgBack
     * @작성일 : 2012. 11. 28.
     * @작성자 : hrpark
     * @변경이력 :
     * @Method 설명 :alert 후 이전 페이지
     * @param response
     * @param msg
     * @return
     * @throws IOException
     */
    public static Object alertMsgBack(HttpServletResponse response, String msg)
            throws IOException {

        response.setContentType("text/html;charset=utf-8");

        PrintWriter output = response.getWriter();
        output.println("<script type='text/javascript' charset='utf-8'>");
        output.println("alert('" + msg.replaceAll("\\'", "\\\"") + "');");
        output.println("window.history.back();");
        output.println("</script>");

        output.flush();
        output.close();

        return null;
    }

    public static Collection<String> setObjStr(String... args) {
        Collection<String> h = new HashSet<String>(Arrays.asList(args));
        return h;
    }

    public static String reduceStr(String str, int num) {
        String str_ = "";
        str = isNull(str);
        if (str.length() > num) {
            str_ = str.substring(0, num) + "...";
        } else {
            str_ = str;
        }

        return str_;
    }

    public static boolean isNumber(String str) {
        boolean check = true;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                check = false;
                break;
            }
        }
        return check;
    }

    /**
     * @Method Name : checkDate
     * @작성일 : 2012. 12. 31.
     * @작성자 : Chi Sungso
     * @변경이력 :
     * @Method 설명 : 날짜유효성체크
     * @param String
     * @param boolean
     * @return
     * @throws IOException
     */
    public static boolean checkDate(int year, int month, int date) {
        boolean isValid = false;

        Calendar cal = new GregorianCalendar(year, month - 1, date);
        int calYear = cal.get(Calendar.YEAR);
        int calMonth = cal.get(Calendar.MONTH) + 1;
        int calDate = cal.get(Calendar.DATE);

        // 인수로 받은 년,월,일과 생성한 Date객체의 년,월,일이 일치하면 true
        if (calYear == year && calMonth == month && calDate == date) {
            isValid = true;
        }

        return isValid;
    }

    static final char FILE_SEPARATOR = File.separatorChar;
    // 버퍼사이즈
    static final int BUFFER_SIZE = 1024;

    /**
     * 파일을 암호화하는 기능
     * 
     * @param String
     *            source 암호화할 파일
     * @param String
     *            target 암호화된 파일
     * @return boolean result 암호화여부 True/False
     * @exception Exception
     */
    public static boolean encryptFile(String source, String target)
            throws Exception {

        // 암호화 여부
        boolean result = false;

        String sourceFile = source.replace('\\', FILE_SEPARATOR).replace('/',
                FILE_SEPARATOR);
        String targetFile = target.replace('\\', FILE_SEPARATOR).replace('/',
                FILE_SEPARATOR);
        File srcFile = new File(sourceFile);

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        byte[] buffer = new byte[BUFFER_SIZE];

        try {
            if (srcFile.exists() && srcFile.isFile()) {

                input = new BufferedInputStream(new FileInputStream(srcFile));
                output = new BufferedOutputStream(new FileOutputStream(
                        targetFile));

                int length = 0;
                while ((length = input.read(buffer)) >= 0) {
                    byte[] data = new byte[length];
                    System.arraycopy(buffer, 0, data, 0, length);
                    output.write(encodeBinary(data).getBytes());
                    output.write(System.getProperty("line.separator")
                            .getBytes());
                }

                result = true;
            }
        } catch (Exception ex) {
            // Logger.getLogger(EgovFileScrty.class).debug(ex);//ex.printStackTrace();
            log.ViewErrorLog(ex.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception ignore) {
                    // no-op
                    // Logger.getLogger(EgovFileScrty.class).debug("IGNORE: " +
                    // ignore); //ignore.printStackTrace();
                    log.ViewErrorLog(ignore.getMessage());
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (Exception ignore) {
                    // no-op
                    // Logger.getLogger(EgovFileScrty.class).debug("IGNORE: " +
                    // ignore); //ignore.printStackTrace();
                    log.ViewErrorLog(ignore.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 파일을 복호화하는 기능
     * 
     * @param String
     *            source 복호화할 파일
     * @param String
     *            target 복호화된 파일
     * @return boolean result 복호화여부 True/False
     * @exception Exception
     */
    /*public static boolean decryptFile(String source, String target)
            throws Exception {

        // 복호화 여부
        boolean result = false;

        String sourceFile = source.replace('\\', FILE_SEPARATOR).replace('/',
                FILE_SEPARATOR);
        String targetFile = target.replace('\\', FILE_SEPARATOR).replace('/',
                FILE_SEPARATOR);
        File srcFile = new File(sourceFile);

        BufferedReader input = null;
        BufferedOutputStream output = null;

        // byte[] buffer = new byte[BUFFER_SIZE];
        String line = null;

        try {
            if (srcFile.exists() && srcFile.isFile()) {

                input = new BufferedReader(new InputStreamReader(
                        new FileInputStream(srcFile)));
                output = new BufferedOutputStream(new FileOutputStream(
                        targetFile));

                while ((line = input.readLine()) != null) {
                    byte[] data = line.getBytes();
                    output.write(decodeBinary(new String(data)));
                }

                result = true;
            }
        } catch (Exception ex) {
            log.ViewErrorLog(ex.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception ignore) {
                    // no-op
                    log.ViewErrorLog(ignore.getMessage());
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (Exception ignore) {
                    // no-op
                    log.ViewErrorLog(ignore.getMessage());
                }
            }
        }
        return result;
    }*/

    /**
     * 데이터를 암호화하는 기능
     * 
     * @param byte[] data 암호화할 데이터
     * @return String result 암호화된 데이터
     * @exception Exception
     */
    public static String encodeBinary(byte[] data) throws Exception {
        if (data == null) {
            return "";
        }

        return "";//new String(Base64.encodeBase64(data));
    }

    /**
     * 데이터를 암호화하는 기능
     * 
     * @param String
     *            data 암호화할 데이터
     * @return String result 암호화된 데이터
     * @exception Exception
     */
    public static String encode(String data) throws Exception {
        return encodeBinary(data.getBytes());
    }

    /**
     * 데이터를 복호화하는 기능
     * 
     * @param String
     *            data 복호화할 데이터
     * @return String result 복호화된 데이터
     * @exception Exception
     */
    /*public static byte[] decodeBinary(String data) throws Exception {
        return Base64.decodeBase64(data.getBytes());
    }*/

    /**
     * 데이터를 복호화하는 기능
     * 
     * @param String
     *            data 복호화할 데이터
     * @return String result 복호화된 데이터
     * @exception Exception
     */
    /*public static String decode(String data) throws Exception {
        return new String(decodeBinary(data));
    }*/

    /**
     * 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 MD5 인코딩 방식 적용)
     * 
     * @param String
     *            data 암호화할 비밀번호
     * @return String result 암호화된 비밀번호
     * @exception Exception
     */
    /*public static String encryptPassword(String data) throws Exception {

        if (data == null) {
            return "";
        }

        byte[] plainText = null; // 평문
        byte[] hashValue = null; // 해쉬값
        plainText = data.getBytes();

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        hashValue = md.digest(plainText);

        
         * BASE64Encoder encoder = new BASE64Encoder(); return
         * encoder.encode(hashValue);
         
        return new String(Base64.encodeBase64(hashValue));
    }*/

    public static long getFileSize(String filepath){
        File f = new File(filepath);
        if(f.exists()){
            return f.length();
        }
        return 0L;
    }
    
    public static ArrayList<String> getDirList(File dir){
        ArrayList<String> res = new ArrayList<String>();
         if (dir.isDirectory()) {
             String[] children = dir.list();
             for (int i=0; i<children.length; i++) {
                 res.add(children[i]);
             }
         }      
        return res;
    }
    
    public static String dmUrlParse(String url){
        String rtn ="";
        try {
            String[] _urlparse = url.split("\\/");
            StringBuffer urlparse = new StringBuffer();
            
            urlparse.append("/");
            if(_urlparse.length > 0){
                for(int i=0; i< _urlparse.length; i++){
                    urlparse.append(_urlparse[i].replaceAll(".dm", ".dp").replaceAll(".do", ".dd"));
                }
            }
            urlparse.append("/proc.go");    
            
            rtn = urlparse.toString();
        } catch (Exception e) {
            // TODO: handle exception
            log.ViewErrorLog(e.getMessage());
        }
        return rtn;
    }
    
    /*public static void removeAllUserInfo(HttpServletRequest request, HttpServletResponse response){
        sUtil.RemoveAllSession(request);
        CookieUtil cutil = new CookieUtil(request);
        
        try {
            cutil.removeCookieAll(request, response, null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static boolean isDmUrl(String url){
        return url.matches("[0-9|a-z|A-z|\\.|\\=|\\+|\\/]*");
    }
    
    //키생성
    public static DmSecRsaKey getRsaKey(){
        return DmSecRsaKey.generate(1024); 
    }
    
    public static Map<String, Object> getSecObjectKey(){
        
        DmSecRsaKey key = getRsaKey();
        Map<String,Object> res = new HashMap<String,Object>();
        log.DebugLog(">>>module:"+DmSecRsaKey.toHex(key.getModulus()) + "/exp:" +DmSecRsaKey.toHex(key.getPublicExponent()));

        res.put("rsaKey", key);
        res.put("module", DmSecRsaKey.toHex(key.getModulus()));
        res.put("exponent", DmSecRsaKey.toHex(key.getPublicExponent()));
        
        return res;
    }
    
    public static DmSecTea getTeaSec(HttpServletRequest request, String key){
        DmSecTea tea = null;
        try {
            log.DebugLog("key:"+key);
            DmSecRsaKey rsaKey = (DmSecRsaKey)sUtil.GetSessionValue(request, "rsaObjkey");
            log.DebugLog("rsaKeyModule:" + rsaKey.getModulus() + "/exp:"+rsaKey.getPublicExponent());
            DmSecRsa rsa = new DmSecRsa(rsaKey);
            String teaKey = rsa.decrypt(key);
            tea = new DmSecTea(teaKey);
            
        } catch (Exception e) {
            // TODO: handle exception
            log.ViewErrorLog("복호화 오류"+e.getMessage());
        }
        return tea; 
    }*/
    
    
    /**
     * @Method Name  : mobilSplit
     * @작성일   : 2013. 3. 28. 
     * @작성자   : hrpark
     * @변경이력  :
     * @Method 설명 : 기존회원 휴대폰번호 자리수에맞게 자르기]
     * @param _mobile
     * @return
     */
    public static HashMap<String, String> mobilSplit(String _mobile ){
        String mobile = ComUtil.isNull(_mobile);
        if(mobile.equals("")){
            return null;
        }
        
        HashMap<String, String> data = new HashMap<String, String>();
        
        //핸드폰번호 특수문자제거 후 길이가 10,11,12 자리가 아닐경우에는 화면에 핸드폰번호를 전부 보여주고 수정을 유도한다.
        String mYn = "N"; //화면에 번호를 분기해서 보여줄지 여부 
        
        char[] arr = {'-',' ','_' ,'.',','};
        String mobil = StringUtils.removeChar(mobile,arr);//해당배열문제를 제거한 값을 리턴
        boolean numChk = false; //숫자여부체크
        if(!mobil.equals("")){
            numChk = ComUtil.isNumber(mobil);
            if(numChk == true){//숫자로만 이루어진경우
                if(mobil.length() == 12){ //0130 #### #### 일경우다.
                    if(mobil.substring(0,4).equals("0130")){
                        mYn ="Y";
                        data.put("hp1",mobil.substring(0,4) );
                        data.put("hp2",mobil.substring(4,8) );
                        data.put("hp3",mobil.substring(8,12) );
                    }
                }else if(mobil.length() == 11){
                    mYn ="Y";
                    if(mobil.substring(0,4).equals("0130")){ //0130 ### ####
                        data.put("hp1",mobil.substring(0,4));
                        data.put("hp2",mobil.substring(4,7));
                        data.put("hp3",mobil.substring(7,11));
                    }else{ //010 #### ####
                        data.put("hp1",mobil.substring(0,3));
                        data.put("hp2",mobil.substring(3,7));
                        data.put("hp3",mobil.substring(7,11));
                    }
                }else if(mobil.length() == 10){ //010 ### ####
                    mYn ="Y";
                    data.put("hp1",mobil.substring(0,3));
                    data.put("hp2",mobil.substring(3,6));
                    data.put("hp3",mobil.substring(6,10));
                }
            }else{
                return null;//문자포함시 
            }
        }
        
        if("N".equals(mYn)){
            return null;
        }
        return data;
    }
    
    public static String getLocalIp(){
        String rtn = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ipaddr = addr.getHostAddress();
            rtn = ipaddr;
        } catch (UnknownHostException e) {
            // throw Exception
            log.ViewErrorLog(e.getMessage());
        }
        return rtn;
    }
    
    public static String getLocalHostName(){
        String rtn = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostname = addr.getHostName();
            rtn = hostname;
//              System.out.println(addr.getHostAddress());
//              System.out.println(hostname);
        } catch (UnknownHostException e) {
            // throw Exception
            log.ViewErrorLog(e.getMessage());
        }
        return rtn;
    }
    
    /*
     * VO를 MAP으로 컨버팅
     * 
     * */
    public static Map ConverObjectToMap(Object obj){
        try {
            //Field[] fields = obj.getClass().getFields(); //private field는 나오지 않음.
            Field[] fields = obj.getClass().getDeclaredFields();
            Map resultMap = new HashMap();
            for(int i=0; i<=fields.length-1;i++){
                fields[i].setAccessible(true);
                //System.out.println("KEY ::" + fields[i].getName());
                //System.out.println("VAL ::" + fields[i].get(obj));
                resultMap.put(fields[i].getName(), fields[i].get(obj));
            }
            return resultMap;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getSessionUkey(String userId){
        
        String ts = "";
        /*int uKeySec = Integer.parseInt(CmeProperties.getProperty("loginSessionKey.Sec"));
        switch (uKeySec) {
        case 0:
            ts = DateTime.getDateString("yyyyMMddHHmmssms");
            break;
        case 1:
            ts = DateTime.getDateString("yyyyMMddHHmmss");
            break;
        case 2:
            ts = DateTime.getDateString("yyyyMMddHHmm");
            break;
        case 3:
            ts = DateTime.getDateString("yyyyMMddHH");
            break;          
        case 4:
            ts = DateTime.getDateString("yyyyMMdd");
            break;          
        default:
            break;
        }*/
        log.DebugLog("timeStamp Hour:::::"+ts);
        //String _id = sUtil.GetStrValue(request, CmeSessionConst.INTEG_ID);

//          String _ip = "";
        if("".equals(userId)){
            return "";
        }
/*      try {
            _ip = getRemoteIP(request);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }*/
        
        String _skey = userId +"|" + ts;
        String encStr = "";//EncStr(_skey);
//          log.DebugLog("#############"+DecStr(encStr));
        
        return encStr;
    }
    
    
    /*public static String[] decUkey(String ukey){
        
        String _strTmp = DecStr(ukey);
        log.DebugLog(_strTmp);
        String[] strDecRes = _strTmp.split("\\|");
        log.DebugLog("timeStamp Hour:::::"+strDecRes[1] +"/id:"+strDecRes[0]);
        return strDecRes;
    }*/
    
    
    /**
     * @Method Name  : compareUkey
     * @작성일   : 2013. 11. 7. 
     * @작성자   : Kim, YunKwan
     * @변경이력  : 
     * @Method 설명 : 현재값이 빠르면 true, 느리면 false
     * @param strCur
     * @param strDb
     * @return
     */
    /*public static boolean compareUkey(String strCur, String strDb){
        
        log.DebugLog("현재skey:"+strCur + "/DB skey:"+strDb);
        
        String[] _tmp1 = decUkey(strCur);
        String[] _tmp2 = decUkey(strDb);
        
        log.DebugLog("시간:"+_tmp1[1]+"/" + _tmp2[1]);
        int uKeySec = Integer.parseInt(CmeProperties.getProperty("loginSessionKey.Sec"));
        
        SimpleDateFormat sdf = null;        
        
        switch (uKeySec) {
        case 0:
            sdf = new SimpleDateFormat("yyyyMMddHHmmssms");
            break;
        case 1:
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            break;
        case 2:
            sdf = new SimpleDateFormat("yyyyMMddHHmm");
            break;
        case 3:
            sdf = new SimpleDateFormat("yyyyMMddHH");
            break;          
        case 4:
            sdf = new SimpleDateFormat("yyyyMMdd");
            break;          
        default:
            break;
        }       
        
        try {
            Date curdate = sdf.parse(_tmp1[1]);
            Date dbdate = sdf.parse(_tmp2[1]);
            
            if(curdate.compareTo(dbdate) > 0){
                return true;
            }
            
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return false;
    }*/
    
    /*public static CmeResultVO SessionDupCheck(String id, String sid){

        CmeResultVO res = new CmeResultVO();
        HttpComLib http = new HttpComLib();
        
        String readUrl = CmeConstant.HttpRootContext + "/isDupCheck.dp/proc.go";
        
        Map<String,Object> param = new HashMap<String,Object>() ;
        param.put("user_id", id);
        param.put("session_id", sid);
        
        try {
            String data = http.getPostData(readUrl ,param , null);
            Gson gson = new Gson(); 
            res = gson.fromJson(data, CmeResultVO.class);
        } catch (Exception e) {
            log.DebugLog("error:readJson >getJson:"+e.getMessage());
        }       
        
        return res;
    }*/
    

    

    public static String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }

        String ret = data;
        // ret = ComUtil.removeIllegalTag(ret);
        ret = removeIllegalTag(ret);
        return ret;
    }




}
