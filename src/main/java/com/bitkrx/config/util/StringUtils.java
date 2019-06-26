package com.bitkrx.config.util;

import com.bitkrx.api.auth.vo.ForeignIpVO;
import com.bitkrx.api.user.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class StringUtils {

    /**
     * 영문과 한글과의 크기를 맞추기 위하여 astr에 해당하는 글자수 만큼 더 자른다.
     *
     * @param str
     *            문자열
     * @param cutLength
     *            자를크기
     * @return ...으로 끝나는 문자열
     */
    public static String substr(String str, int cutLength) {
        String astr = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String strTmp = "";
        int count = 0;

        if (str.length() <= cutLength) {
            strTmp = str;
        } else {
            for (int i = 0; i < cutLength; i++) {
                if (astr.indexOf(str.substring(i, i + 1)) >= 0)
                    count++;
            }
            int tempLength = cutLength + count;
            cutLength = Math.min(str.length(), tempLength);

            if (Math.min(str.length(), tempLength) == str.length())
                strTmp = str.substring(0, cutLength);
            else
                strTmp = str.substring(0, cutLength) + "..";
        }

        return strTmp;
    }

    /**
     * 금액관련 처리 ex) StringUtil.makeMoneyType("10000"); ==> 10,000
     *
     * @param no
     *            금액
     * @return 변환된 결과
     */
    public static String makeMoneyType(String no) {

        int index = no.indexOf(".");
        if (index == -1) {
            return makeMoneyType(Long.parseLong(no));
        } else {
            return (makeMoneyType(Long.parseLong(no.substring(0, index))) + no
                    .substring(index, no.length()));
        }
    }

    /**
     * 금액관련 처리
     *
     * @param no
     *            금액
     * @return 변환된 결과
     */
    public static String makeMoneyType(int no) {
        return (makeMoneyType((new Integer(no)).longValue()));
    }

    /**
     * 금액관련 처리
     *
     * @param no
     *            금액
     * @return 변환된 결과
     */
    public static String makeMoneyType(long no) {
        return NumberFormat.getInstance().format(no);
    }

    /**
     * 금액관련 처리
     *
     * @param no
     *            금액
     * @return 변환된 결과
     */
    public static String makeMoneyType(double no) {
        return NumberFormat.getInstance().format(no);
    }

    /**
     * 금액관련 처리
     *
     * @param no
     *            금액
     * @return 변환된 결과
     */
    public static String makeMoneyType(float no) {
        return (makeMoneyType((new Float(no)).doubleValue()));
    }

    /**
     * 문자열이 비어있는지 확인한다. <br />
     * 만약 null일 경우 빈 문자열을 리턴한다.<br />
     *
     * @param str
     * @return 결과
     */
    public static String checkNull(String str) {
        return checkNull(str, "");
    }

    /**
     * 문자열이 비어있는지 확인한다.<br />
     * 만약 null일 경우 기본값으로 설정한 문자열을 리턴한다.<br />
     *
     * @param str
     * @return 결과
     */
    public static String checkNull(String str, String defaultValue) {
        return (str != null && !"".equals(str)) ? str : defaultValue;
    }

    public static boolean checkNull(Locale locale) {
        if (locale != null && !"".equals(locale)){
            return true;
        }
        return false;
    }


    /**
     * 숫자형의 문자열이 비었는지 확인한다.<br />
     * 숫자형의 문자열이 맞을 경우 int형으로 변환한 숫자를 리턴<br />
     * 그렇지 않을 경우 기본값으로 지정한 값을 되돌린다.<br />
     *
     * @param str
     * @param defaultValue
     * @return 결과
     */
    public static int checkNull(String str, int defaultValue) {

        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public static double checkNull(double str, double defaultValue) {

        try {
            return str;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int checkNull(int num) {
        try {
            return (String.valueOf(num) != null) ? num : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 문자열을 받아 구분자로 잘라 String[] 으로 return (split 기능, 1.3버젼에서 제공하지 않음)
     *
     * @param strString
     * @param strDelim
     * @return 문자열 배열
     */
    public static String[] getSplit(String strString, String strDelim) {

        StringTokenizer stk = new StringTokenizer(strString, strDelim);
        int numStk = stk.countTokens();

        String[] newStr = new String[numStk];

        for (int i = 0; i < numStk; i++) {
            String temp = stk.nextToken();
            if (!temp.equals("") && temp != null) {
                newStr[i] = temp;
            } else
                i--;
        }
        return newStr;
    }

    /**
     * HTML 코드 바꾸기
     *
     * @param str
     * @return 변경된 문자열
     */
    public static String replaceHtml(String str) {
        String newStr = "";

        newStr = str.replaceAll("&", "&#38;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("\"", "&quot;");

        return newStr;
    }

    public static boolean isXssTag(String str) {

        if (str.indexOf("script".toLowerCase()) > -1
                || str.indexOf("refresh".toLowerCase()) > -1
                || str.indexOf("&#".toLowerCase()) > -1
                || str.indexOf("FSCommand".toLowerCase()) > -1
                || str.indexOf("onAbort".toLowerCase()) > -1
                || str.indexOf("onAfterPrint".toLowerCase()) > -1
                || str.indexOf("onActivate".toLowerCase()) > -1
                || str.indexOf("onAfterUpdate".toLowerCase()) > -1
                || str.indexOf("onBeforeActivate".toLowerCase()) > -1
                || str.indexOf("onBeforeCopy".toLowerCase()) > -1
                || str.indexOf("onBeforeCut".toLowerCase()) > -1
                || str.indexOf("onBeforeDeactivate".toLowerCase()) > -1
                || str.indexOf("onBeforeEditFocus".toLowerCase()) > -1
                || str.indexOf("onBeforePaste".toLowerCase()) > -1
                || str.indexOf("onBeforePrint".toLowerCase()) > -1
                || str.indexOf("onBeforeUnload".toLowerCase()) > -1
                || str.indexOf("onBegin".toLowerCase()) > -1
                || str.indexOf("onBlur".toLowerCase()) > -1
                || str.indexOf("onBounce".toLowerCase()) > -1
                || str.indexOf("onCellChange".toLowerCase()) > -1
                || str.indexOf("onChange".toLowerCase()) > -1
                || str.indexOf("onClick".toLowerCase()) > -1
                || str.indexOf("onContextMenu".toLowerCase()) > -1
                || str.indexOf("onControlSelect".toLowerCase()) > -1
                || str.indexOf("onCopy".toLowerCase()) > -1
                || str.indexOf("onCut".toLowerCase()) > -1
                || str.indexOf("onDataAvailable".toLowerCase()) > -1
                || str.indexOf("onDataSetChanged".toLowerCase()) > -1
                || str.indexOf("onDataSetComplete".toLowerCase()) > -1
                || str.indexOf("onDblClick".toLowerCase()) > -1
                || str.indexOf("onDeactivate".toLowerCase()) > -1
                || str.indexOf("onDrag".toLowerCase()) > -1
                || str.indexOf("onDragEnd".toLowerCase()) > -1
                || str.indexOf("onDragLeave".toLowerCase()) > -1
                || str.indexOf("onDragEnter".toLowerCase()) > -1
                || str.indexOf("onDragOver".toLowerCase()) > -1
                || str.indexOf("onDragDrop".toLowerCase()) > -1
                || str.indexOf("onDrop".toLowerCase()) > -1
                || str.indexOf("onEnd".toLowerCase()) > -1
                || str.indexOf("onError".toLowerCase()) > -1
                || str.indexOf("onErrorUpdate".toLowerCase()) > -1
                || str.indexOf("onFilterChange".toLowerCase()) > -1
                || str.indexOf("onFinish".toLowerCase()) > -1
                || str.indexOf("onFocus".toLowerCase()) > -1
                || str.indexOf("onFocusIn".toLowerCase()) > -1
                || str.indexOf("onFocusOut".toLowerCase()) > -1
                || str.indexOf("onHelp".toLowerCase()) > -1
                || str.indexOf("onKeyDown".toLowerCase()) > -1
                || str.indexOf("onKeyPress".toLowerCase()) > -1
                || str.indexOf("onLayoutComplete".toLowerCase()) > -1
                || str.indexOf("onLoad".toLowerCase()) > -1
                || str.indexOf("onLoseCapture".toLowerCase()) > -1
                || str.indexOf("onMediaComplete".toLowerCase()) > -1
                || str.indexOf("onMediaError".toLowerCase()) > -1
                || str.indexOf("onMouseDown".toLowerCase()) > -1
                || str.indexOf("onMouseEnter".toLowerCase()) > -1
                || str.indexOf("onMouseLeave".toLowerCase()) > -1
                || str.indexOf("onMouseMove".toLowerCase()) > -1
                || str.indexOf("onMouseOut".toLowerCase()) > -1
                || str.indexOf("onMouseOver".toLowerCase()) > -1
                || str.indexOf("onMouseUp".toLowerCase()) > -1
                || str.indexOf("onMouseWheel".toLowerCase()) > -1
                || str.indexOf("onMove".toLowerCase()) > -1
                || str.indexOf("onMoveEnd".toLowerCase()) > -1
                || str.indexOf("onMoveStart".toLowerCase()) > -1
                || str.indexOf("onOutOfSync".toLowerCase()) > -1
                || str.indexOf("onPaste".toLowerCase()) > -1
                || str.indexOf("onPause".toLowerCase()) > -1
                || str.indexOf("onProgress".toLowerCase()) > -1
                || str.indexOf("onPropertyChange".toLowerCase()) > -1
                || str.indexOf("onReadyStateChange".toLowerCase()) > -1
                || str.indexOf("onRepeat".toLowerCase()) > -1
                || str.indexOf("onReset".toLowerCase()) > -1
                || str.indexOf("onResize".toLowerCase()) > -1
                || str.indexOf("onResizeEnd".toLowerCase()) > -1
                || str.indexOf("onResizeStart".toLowerCase()) > -1
                || str.indexOf("onResume".toLowerCase()) > -1
                || str.indexOf("onReverse".toLowerCase()) > -1
                || str.indexOf("onRowsEnter".toLowerCase()) > -1
                || str.indexOf("onRowExit".toLowerCase()) > -1
                || str.indexOf("onRowDelete".toLowerCase()) > -1
                || str.indexOf("onRowInserted".toLowerCase()) > -1
                || str.indexOf("onScroll".toLowerCase()) > -1
                || str.indexOf("onSeek".toLowerCase()) > -1
                || str.indexOf("onSelect".toLowerCase()) > -1
                || str.indexOf("onSelectionChange".toLowerCase()) > -1
                || str.indexOf("onSelectStart".toLowerCase()) > -1
                || str.indexOf("onStart".toLowerCase()) > -1
                || str.indexOf("onStop".toLowerCase()) > -1
                || str.indexOf("onSyncRestored".toLowerCase()) > -1
                || str.indexOf("onSubmit".toLowerCase()) > -1
                || str.indexOf("onTimeError".toLowerCase()) > -1
                || str.indexOf("onTrackChange".toLowerCase()) > -1
                || str.indexOf("onUnload".toLowerCase()) > -1
                || str.indexOf("onURLFlip".toLowerCase()) > -1
                || str.indexOf("seekSegmentTime".toLowerCase()) > -1) {

            return true;
        }
        return false;
    }

    /**
     * URL HTML 코드 바꾸기
     *
     * @param str
     * @return 변경된 문자
     */
    public static String URLreplaceHtml(String str) {

        String newStr = "";
        newStr = str.replaceAll("&", "&#38;");
        newStr = str.replaceAll("\"", "&quot;");
        newStr = str.replaceAll("<", "&lt;");
        newStr = str.replaceAll(">", "&gt;");
        newStr = str.replaceAll("#", "");
        newStr = str.replaceAll("&nbsp;", "/n");

        return newStr;
    }

    /**
     * 해당 문자열에서 특정 문자를 제거한다.
     * <code>StringUtil.removeChar(&quot;1,234,567,890&quot;, ',');</code> --
     * &quot;1234567890&quot;
     * <code>StringUtil.removeChar(&quot;2001-01-01&quot;, '-');</code> --
     * &quot;20010101&quot;
     * <code>StringUtil.removeChar(&quot;1 2 3 4 5 6 7 8 9 0&quot;, ' ');</code>
     * -- &quot;1234567890&quot;
     *
     * @param str
     * @param rmChar
     *            제거할 문자
     * @return String
     */
    public static String removeChar(String str, char rmChar) {
        if (str == null || str.indexOf(rmChar) == -1)
            return str;

        StringBuffer sb = new StringBuffer();
        char[] arr = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != rmChar)
                sb.append(arr[i]);
        }

        return sb.toString();
    }

    /**
     * 해당 문자열에서 특정 문자들을 제거한다. <code>
     * char[] arr = {'-',' ',':'};<br>
     * StringUtil.removeChar(&quot;2001-01-01 10:10:10&quot;, arr);<br>
     * </code> -- &quot;20010101101010&quot;
     *
     * @param str
     * @param rmChar
     *            제거할 문자
     * @return String
     */
    public static String removeChar(String str, char[] rmChar) {
        if (str == null || rmChar == null)
            return str;

        char[] arr = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            boolean bFlag = true;
            for (int k = 0; k < rmChar.length; k++) {
                if (arr[i] == rmChar[k]) {
                    bFlag = false;
                    break;
                }
            }
            if (bFlag)
                sb.append(arr[i]);
        }

        return sb.toString();
    }

    /**
     * 문자열내의 모든 태그를 제거한다.
     *
     * @param s
     *            문자열
     * @return 태그가 제거된 문자열
     */
    public static String removeTag(String s) {
        return s.replaceAll(
                "(?:<!.*?(?:--.*?--s*)*.*?>)|(?:<(?:[^>\'\"]*|\".*?\"|\'.*?\')+>)",
                "");
    }

    /**
     * 문자열내의 모든 스크립트 태그를 제거한다.
     *
     * @param s
     *            문자열
     * @return 스크립트 태그가 제거된 문자열
     */
    public static String removeScriptTag(String s) {
        s = s.replaceAll("<[s|S][c|C][r|R][i|I][p|P][t|T][^>]*>",
                "&lt;script&gt;");
        s = s.replaceAll("</[s|S][c|C][r|R][i|I][p|P][t|T]>", "&lt;/script&gt;");

        s = s.replaceAll(
                "<[j|J][a|A][v|V][a|A][s|S][c|C][r|R][i|I][p|P][t|T][^>]*>",
                "&lt;javascript&gt;");
        s = s.replaceAll(
                "</[j|J][a|A][v|V][a|A][s|S][c|C][r|R][i|I][p|P][t|T]>",
                "&lt;/javascript&gt;");

        s = s.replaceAll("<[O|o][B|b][J|j][E|e][C|c][T|t][^>]*>",
                "&lt;object&gt;");
        s = s.replaceAll("</[O|o][B|b][J|j][E|e][C|c][T|t]>", "&lt;/object&gt;");

        s = s.replaceAll("<[A|a][P|p][P|p][L|l][E|e][T|t][^>]*>",
                "&lt;applet&gt;");
        s = s.replaceAll("</[A|a][P|p][P|p][L|l][E|e][T|t]>", "&lt;/applet&gt;");

        s = s.replaceAll("<[E|e][M|m][B|b][E|e][D|d][^>]*>", "&lt;embed&gt;");
        s = s.replaceAll("</[E|e][M|m][B|b][E|e][D|d]>", "&lt;/embed&gt;");

        s = s.replaceAll("<[F|f][O|o][R|r][M|m][^>]*>", "&lt;form&gt;");
        s = s.replaceAll("</[F|f][O|o][R|r][M|m]>", "&lt;/form&gt;");

        s = s.replaceAll("<[I|i][N|n][P|p][U|u][T|t][^>]*>", "&lt;input&gt;");
        s = s.replaceAll("</[I|i][N|n][P|p][U|u][T|t]>", "&lt;/input&gt;");

        s = s.replaceAll("<%", "");
        s = s.replaceAll("<[C|c]:", "");
        s = s.replaceAll("<jsp:", "&lt;jsp:");

        return s;
    }

    public static String newPasswd() {
        // 2. 임시 비밀번호를 생성한다.(영+영+숫+영+영+숫=6자리)
        // 기존 6자리에서 11자리로 변경 20170719 양회명
        String newpassword = "";
        for (int i = 1; i <= 11; i++) {
            // 영자
            if (i % 3 != 0) {
                newpassword += getRandomStr('a', 'z');
                // 숫자
            } else {
                newpassword += getRandomNum(0, 9);
            }
        }
        return newpassword;
    }

    /**
     * 시스템 콘솔에서 실행된 명령의 결과를 String 으로 받아 온다
     *
     * @param cmd
     *            실행한 콘솔 명령
     * @return 실행 결과
     */
    public static String getCmd(String cmd) throws IOException {
        Process p = Runtime.getRuntime().exec(cmd);
        InputStream in = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String s = "";
        String temp = "";
        while ((temp = br.readLine()) != null) {
            s += temp + "\n";
        }
        br.close();
        in.close();
        return s;
    }


    /*
     * email 서버 체킹
     */
    public static boolean emailCheck(String email) throws IOException {
        boolean result = false;
        if (email != null && !email.contains("")) {
            String domain = email.substring(email.indexOf("@") + 1,
                    email.length());
            String cmd = getCmd("nslookup -type=MX" + domain);
            if (cmd.indexOf("mail exchanger") != -1) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;

    }

    /**
     * @Method Name  : generateRandomChar
     * @작성일   : 2015. 6. 28.
     * @작성자   : (주)크림소프트 김윤관
     * @변경이력  :
     * @Method 설명 : 숫자와 알파벳 특수문자를 포함한다.
     * @param length
     * @return
     */
    public static String generateRandomChar(int length) {

        final Random sr = new SecureRandom();
        final int charsLength = 57;

        String chars = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ123456789+@";
        String pw = "";

        for (int i = 0; i < length; i++) {
            int index = (int)(sr.nextDouble() * charsLength);
            pw += chars.substring(index, index + 1);
        }

        return pw;
    }


    /**
     * @Method Name  : generateRandomPassword
     * @작성일   : 2015. 6. 28.
     * @작성자   : (주)크림소프트 김윤관
     * @변경이력  :
     * @Method 설명 : flag : 0 - 알파벳만 랜덤, 1: 알파벳 과 숫자 일부특수문자 57개조합, 2: 알파벳과 숫자 특수문자 60개 조합
     * @param length
     * @param flag
     * @return
     */
    public static String generateRandomPassword(int length, int flag) {
        String pw = "";
        final Random sr = new SecureRandom();
        String chars = "";
        int charsLength = 0;

        switch (flag) {
            case 0:
                charsLength = 46;
                chars = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

                for (int i = 0; i < length; i++) {
                    int index = (int)(sr.nextDouble() * charsLength);
                    pw += chars.substring(index, index + 1);
                }
                break;
            case 1:
                charsLength = 56;
                chars = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ1234567890";
                for (int i = 0; i < length; i++) {
                    int index = (int)(sr.nextDouble() * charsLength);
                    pw += chars.substring(index, index + 1);
                }
                break;
            case 2:
                charsLength = 57;
                chars = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ123456789+@";
                for (int i = 0; i < length; i++) {
                    int index = (int)(sr.nextDouble() * charsLength);
                    pw += chars.substring(index, index + 1);
                }
                break;
            case 3:
                charsLength = 60;
                chars = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ123456789+@#$&";
                for (int i = 0; i < length; i++) {
                    int index = (int)(sr.nextDouble() * charsLength);
                    pw += chars.substring(index, index + 1);
                }
                break;
            default:
                break;
        }

        return pw;

    }


    public static String getUrlEncode(String s) throws IOException {
        return URLEncoder.encode(s, "utf-8");
    }

    public static String getUrlDecode(String s) throws IOException {
        return URLDecoder.decode(s, "utf-8");
    }

    public static String addTag(String str) {
        String retStr = str;
        if (!checkNull(retStr).equals("")) {
            retStr = retStr.replaceAll("\n", "<br>");
            retStr = retStr.replaceAll("&lt;", "<");
            retStr = retStr.replaceAll("&gt;", ">");
            retStr = retStr.replaceAll("&quot;", "\"");
        }
        return retStr;
    }

    public static int getRandomNum(int startNum, int endNum) {
        int randomNum = 0;

        try {
            // 랜덤 객체 생성
            SecureRandom rnd = new SecureRandom();

            do {
                // 종료숫자내에서 랜덤 숫자를 발생시킨다.
                randomNum = rnd.nextInt(endNum + 1);
            } while (randomNum < startNum); // 랜덤 숫자가 시작숫자보다 작을경우 다시 랜덤숫자를
            // 발생시킨다.
        } catch (Exception e) {
            ////Logger.getLogger(StringUtils.class).debug(e);// e.printStackTrace();
        }

        return randomNum;
    }

    /**
     * 특정 숫자 집합에서 특정 숫자가 있는지 체크하는 기능 12345678에서 7이 있는지 없는지 체크하는 기능을 제공함
     *
     * @param sourceInt
     *            - 특정숫자집합
     * @param searchInt
     *            - 검색숫자
     * @return 존재여부
     * @exception MyException
     * @see
     */
    public static Boolean getNumSearchCheck(int sourceInt, int searchInt) {
        String sourceStr = String.valueOf(sourceInt);
        String searchStr = String.valueOf(searchInt);

        // 특정숫자가 존재하는지 하여 위치값을 리턴한다. 없을 시 -1
        if (sourceStr.indexOf(searchStr) == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 숫자타입을 문자열로 변환하는 기능 숫자 20081212를 문자열 '20081212'로 변환하는 기능
     *
     * @param srcNumber
     *            - 숫자
     * @return 문자열
     * @exception MyException
     * @see
     */
    public static String getNumToStrCnvr(int srcNumber) {
        String rtnStr = null;

        try {
            rtnStr = String.valueOf(srcNumber);
        } catch (Exception e) {
            //Logger.getLogger(StringUtils.class).debug(e);// e.printStackTrace();
        }

        return rtnStr;
    }

    /**
     * 숫자타입을 데이트 타입으로 변환하는 기능 숫자 20081212를 데이트타입 '2008-12-12'로 변환하는 기능
     *
     * @param srcNumber
     *            - 숫자
     * @return String
     * @exception MyException
     * @see
     */
    public static String getNumToDateCnvr(int srcNumber) {

        String pattern = null;
        String cnvrStr = null;

        String srcStr = String.valueOf(srcNumber);

        // Date 형태인 8자리 및 14자리만 정상처리
        if (srcStr.length() != 8 && srcStr.length() != 14) {
            throw new IllegalArgumentException("Invalid Number: " + srcStr
                    + " Length=" + srcStr.trim().length());
        }

        if (srcStr.length() == 8) {
            pattern = "yyyyMMdd";
        } else if (srcStr.length() == 14) {
            pattern = "yyyyMMddhhmmss";
        }

        SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern,
                Locale.KOREA);

        Date cnvrDate = null;

        try {
            cnvrDate = dateFormatter.parse(srcStr);
        } catch (ParseException e) {
            //Logger.getLogger(StringUtils.class).debug(e);// e.printStackTrace();
        }

        cnvrStr = String.format("%1$tY-%1$tm-%1$td", cnvrDate);

        return cnvrStr;

    }

    /**
     * 체크할 숫자 중에서 숫자인지 아닌지 체크하는 기능 숫자이면 True, 아니면 False를 반환한다
     *
     * @param checkStr
     *            - 체크문자열
     * @return 숫자여부
     * @exception MyException
     * @see
     */
    public static Boolean getNumberValidCheck(String checkStr) {

        int i;
        // String sourceStr = String.valueOf(sourceInt);

        int checkStrLt = checkStr.length();

        try {
            for (i = 0; i < checkStrLt; i++) {

                // 아스키코드값( '0'-> 48, '9' -> 57)
                if (checkStr.charAt(i) > 47 && checkStr.charAt(i) < 58) {
                    continue;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            //Logger.getLogger(StringUtils.class).debug(e);// e.printStackTrace();
        }

        return true;
    }

    /**
     * 특정숫자를 다른 숫자로 치환하는 기능 숫자 12345678에서 123를 999로 변환하는 기능을 제공(99945678)
     *
     * @param srcNumber
     *            - 숫자집합
     * @param cnvrSrcNumber
     *            - 원래숫자
     * @param cnvrTrgtNumber
     *            - 치환숫자
     * @return 치환숫자
     * @exception MyException
     * @see
     */

    public static int getNumberCnvr(int srcNumber, int cnvrSrcNumber,
                                    int cnvrTrgtNumber) {

        // 입력받은 숫자를 문자열로 변환
        String source = String.valueOf(srcNumber);
        String subject = String.valueOf(cnvrSrcNumber);
        String object = String.valueOf(cnvrTrgtNumber);

        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;

        try {

            // 원본숫자에서 변환대상숫자의 위치를 찾는다.
            while (source.indexOf(subject) >= 0) {
                preStr = source.substring(0, source.indexOf(subject)); // 변환대상숫자
                // 위치까지
                // 숫자를
                // 잘라낸다
                nextStr = source.substring(
                        source.indexOf(subject) + subject.length(),
                        source.length());
                source = nextStr;
                rtnStr.append(preStr).append(object); // 변환대상위치 숫자에 변환할 숫자를
                // 붙여준다.
            }
            rtnStr.append(nextStr); // 변환대상 숫자 이후 숫자를 붙여준다.
        } catch (Exception e) {
            //Logger.getLogger(StringUtils.class).debug(e);// e.printStackTrace();
        }

        return Integer.parseInt(rtnStr.toString());
    }

    /**
     * 특정숫자가 실수인지, 정수인지, 음수인지 체크하는 기능 123이 실수인지, 정수인지, 음수인지 체크하는 기능을 제공함
     *
     * @param srcNumber
     *            - 숫자집합
     * @return -1(음수), 0(정수), 1(실수)
     * @exception MyException
     * @see
     */
    public static int checkRlnoInteger(double srcNumber) {

        // byte 1바이트 ▶소수점이 없는 숫자로, 범위 -2^7 ~ 2^7 -1
        // short 2바이트 ▶소수점이 없는 숫자로, 범위 -2^15 ~ 2^15 -1
        // int 4바이트 ▶소수점이 없는 숫자로, 범위 -2^31 ~ 2^31 - 1
        // long 8바이트 ▶소수점이 없는 숫자로, 범위 -2^63 ~ 2^63-1

        // float 4바이트 ▶소수점이 있는 숫자로, 끝에 F 또는 f 가 붙는 숫자 (예:3.14f)
        // double 8바이트 ▶소수점이 있는 숫자로, 끝에 아무것도 붙지 않는 숫자 (예:3.14)
        // ▶소수점이 있는 숫자로, 끝에 D 또는 d 가 붙는 숫자(예:3.14d)

        String cnvrString = null;

        if (srcNumber < 0) {
            return -1;
        } else {
            cnvrString = String.valueOf(srcNumber);

            if (cnvrString.indexOf(".") == -1) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public static final String EMPTY = "";

    /**
     * <p>
     * Padding을 할 수 있는 최대 수치
     * </p>
     */
    // private static final int PAD_LIMIT = 8192;
    /**
     * <p>
     * An array of <code>String</code>s used for padding.
     * </p>
     * <p>
     * Used for efficient space padding. The length of each String expands as
     * needed.
     * </p>
     */
    /*
     * private static final String[] PADDING = new String[Character.MAX_VALUE];
     *
     * static { // space padding is most common, start with 64 chars PADDING[32]
     * = "                                                                "; }
     */

    /**
     * 문자열이 지정한 길이를 초과했을때 지정한길이에다가 해당 문자열을 붙여주는 메서드.
     *
     * @param source
     *            원본 문자열 배열
     * @param output
     *            더할문자열
     * @param slength
     *            지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String source, String output, int slength) {
        String returnVal = null;
        if (source != null) {
            if (source.length() > slength) {
                returnVal = source.substring(0, slength) + output;
            } else
                returnVal = source;
        }
        return returnVal;
    }

    /**
     * 문자열이 지정한 길이를 초과했을때 해당 문자열을 삭제하는 메서드
     *
     * @param source
     *            원본 문자열 배열
     * @param slength
     *            지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String source, int slength) {
        String result = null;
        if (source != null) {
            if (source.length() > slength) {
                result = source.substring(0, slength);
            } else
                result = source;
        }
        return result;
    }

    /**
     * <p>
     * String이 비었거나("") 혹은 null 인지 검증한다.
     * </p>
     *
     * <pre>
     *  StringUtil.isEmpty(null)      = true
     *  StringUtil.isEmpty("")        = true
     *  StringUtil.isEmpty(" ")       = false
     *  StringUtil.isEmpty("bob")     = false
     *  StringUtil.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str
     *            - 체크 대상 스트링오브젝트이며 null을 허용함
     * @return <code>true</code> - 입력받은 String 이 빈 문자열 또는 null인 경우
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>
     * 기준 문자열에 포함된 모든 대상 문자(char)를 제거한다.
     * </p>
     *
     * <pre>
     * StringUtil.remove(null, *)       = null
     * StringUtil.remove("", *)         = ""
     * StringUtil.remove("queued", 'u') = "qeed"
     * StringUtil.remove("queued", 'z') = "queued"
     * </pre>
     *
     * @param str
     *            입력받는 기준 문자열
     * @param remove
     *            입력받는 문자열에서 제거할 대상 문자열
     * @return 제거대상 문자열이 제거된 입력문자열. 입력문자열이 null인 경우 출력문자열은 null
     */
    public static String remove(String str, char remove) {
        if (isEmpty(str) || str.indexOf(remove) == -1) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }

    /**
     * <p>
     * 문자열 내부의 콤마 character(,)를 모두 제거한다.
     * </p>
     *
     * <pre>
     * StringUtil.removeCommaChar(null)       = null
     * StringUtil.removeCommaChar("")         = ""
     * StringUtil.removeCommaChar("asdfg,qweqe") = "asdfgqweqe"
     * </pre>
     *
     * @param str
     *            입력받는 기준 문자열
     * @return " , "가 제거된 입력문자열 입력문자열이 null인 경우 출력문자열은 null
     */
    public static String removeCommaChar(String str) {
        return remove(str, ',');
    }

    /**
     * <p>
     * 문자열 내부의 마이너스 character(-)를 모두 제거한다.
     * </p>
     *
     * <pre>
     * StringUtil.removeMinusChar(null)       = null
     * StringUtil.removeMinusChar("")         = ""
     * StringUtil.removeMinusChar("a-sdfg-qweqe") = "asdfgqweqe"
     * </pre>
     *
     * @param str
     *            입력받는 기준 문자열
     * @return " - "가 제거된 입력문자열 입력문자열이 null인 경우 출력문자열은 null
     */
    public static String removeMinusChar(String str) {
        return remove(str, '-');
    }

    /**
     * 원본 문자열의 포함된 특정 문자열을 새로운 문자열로 변환하는 메서드
     *
     * @param source
     *            원본 문자열
     * @param subject
     *            원본 문자열에 포함된 특정 문자열
     * @param object
     *            변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열
     */
    public static String replace(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        String srcStr = source;

        while (srcStr.indexOf(subject) >= 0) {
            preStr = srcStr.substring(0, srcStr.indexOf(subject));
            nextStr = srcStr
                    .substring(srcStr.indexOf(subject) + subject.length(),
                            srcStr.length());
            srcStr = nextStr;
            rtnStr.append(preStr).append(object);
        }
        rtnStr.append(nextStr);
        return rtnStr.toString();
    }

    /**
     * 원본 문자열의 포함된 특정 문자열 첫번째 한개만 새로운 문자열로 변환하는 메서드
     *
     * @param source
     *            원본 문자열
     * @param subject
     *            원본 문자열에 포함된 특정 문자열
     * @param object
     *            변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열 / source 특정문자열이 없는 경우 원본 문자열
     */
    public static String replaceOnce(String source, String subject,
                                     String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        if (source.indexOf(subject) >= 0) {
            preStr = source.substring(0, source.indexOf(subject));
            nextStr = source
                    .substring(source.indexOf(subject) + subject.length(),
                            source.length());
            rtnStr.append(preStr).append(object).append(nextStr);
            return rtnStr.toString();
        } else {
            return source;
        }
    }

    /**
     * <code>subject</code>에 포함된 각각의 문자를 object로 변환한다.
     *
     * @param source
     *            원본 문자열
     * @param subject
     *            원본 문자열에 포함된 특정 문자열
     * @param object
     *            변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열
     */
    public static String replaceChar(String source, String subject,
                                     String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        String srcStr = source;

        char chA;

        for (int i = 0; i < subject.length(); i++) {
            chA = subject.charAt(i);

            if (srcStr.indexOf(chA) >= 0) {
                preStr = srcStr.substring(0, srcStr.indexOf(chA));
                nextStr = srcStr.substring(srcStr.indexOf(chA) + 1,
                        srcStr.length());
                srcStr = rtnStr.append(preStr).append(object).append(nextStr)
                        .toString();
            }
        }

        return srcStr;
    }

    /**
     * <p>
     * <code>str</code> 중 <code>searchStr</code>의 시작(index) 위치를 반환.
     * </p>
     *
     * <p>
     * 입력값 중 <code>null</code>이 있을 경우 <code>-1</code>을 반환.
     * </p>
     *
     * <pre>
     * StringUtil.indexOf(null, *)          = -1
     * StringUtil.indexOf(*, null)          = -1
     * StringUtil.indexOf("", "")           = 0
     * StringUtil.indexOf("aabaabaa", "a")  = 0
     * StringUtil.indexOf("aabaabaa", "b")  = 2
     * StringUtil.indexOf("aabaabaa", "ab") = 1
     * StringUtil.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str
     *            검색 문자열
     * @param searchStr
     *            검색 대상문자열
     * @return 검색 문자열 중 검색 대상문자열이 있는 시작 위치 검색대상 문자열이 없거나 null인 경우 -1
     */
    public static int indexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.indexOf(searchStr);
    }

    /**
     * <p>
     * 오라클의 decode 함수와 동일한 기능을 가진 메서드이다. <code>sourStr</code>과
     * <code>compareStr</code>의 값이 같으면 <code>returStr</code>을 반환하며, 다르면
     * <code>defaultStr</code>을 반환한다.
     * </p>
     *
     * <pre>
     * StringUtil.decode(null, null, "foo", "bar")= "foo"
     * StringUtil.decode("", null, "foo", "bar") = "bar"
     * StringUtil.decode(null, "", "foo", "bar") = "bar"
     * StringUtil.decode("하이", "하이", null, "bar") = null
     * StringUtil.decode("하이", "하이  ", "foo", null) = null
     * StringUtil.decode("하이", "하이", "foo", "bar") = "foo"
     * StringUtil.decode("하이", "하이  ", "foo", "bar") = "bar"
     * </pre>
     *
     * @param sourceStr
     *            비교할 문자열
     * @param compareStr
     *            비교 대상 문자열
     * @param returnStr
     *            sourceStr와 compareStr의 값이 같을 때 반환할 문자열
     * @param defaultStr
     *            sourceStr와 compareStr의 값이 다를 때 반환할 문자열
     * @return sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며, <br/>
     *         다르면 defaultStr을 반환한다.
     */
    public static String decode(String sourceStr, String compareStr,
                                String returnStr, String defaultStr) {
        if (sourceStr == null && compareStr == null) {
            return returnStr;
        }

        if (sourceStr == null && compareStr != null) {
            return defaultStr;
        }

        if (sourceStr.trim().equals(compareStr)) {
            return returnStr;
        }

        return defaultStr;
    }

    /**
     * <p>
     * 오라클의 decode 함수와 동일한 기능을 가진 메서드이다. <code>sourStr</code>과
     * <code>compareStr</code>의 값이 같으면 <code>returStr</code>을 반환하며, 다르면
     * <code>sourceStr</code>을 반환한다.
     * </p>
     *
     * <pre>
     * StringUtil.decode(null, null, "foo") = "foo"
     * StringUtil.decode("", null, "foo") = ""
     * StringUtil.decode(null, "", "foo") = null
     * StringUtil.decode("하이", "하이", "foo") = "foo"
     * StringUtil.decode("하이", "하이 ", "foo") = "하이"
     * StringUtil.decode("하이", "바이", "foo") = "하이"
     * </pre>
     *
     * @param sourceStr
     *            비교할 문자열
     * @param compareStr
     *            비교 대상 문자열
     * @param returnStr
     *            sourceStr와 compareStr의 값이 같을 때 반환할 문자열
     * @return sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며, <br/>
     *         다르면 sourceStr을 반환한다.
     */
    public static String decode(String sourceStr, String compareStr,
                                String returnStr) {
        return decode(sourceStr, compareStr, returnStr, sourceStr);
    }

    /**
     * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸는 메서드
     *
     * @param object
     *            원본 객체
     * @return resultVal 문자열
     */
    public static String isNullToString(Object object) {
        String string = "";

        if (object != null) {
            string = object.toString().trim();
        }

        return string;
    }

    /**
     * <pre>
     * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
     * </pre>
     */
    public static String nullConvert(Object src) {
        // if (src != null &&
        // src.getClass().getName().equals("java.math.BigDecimal")) {
        if (src != null && src instanceof java.math.BigDecimal) {
            return ((BigDecimal) src).toString();
        }

        if (src == null || src.equals("null")) {
            return "";
        } else {
            return ((String) src).trim();
        }
    }

    /**
     * <pre>
     * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
     * </pre>
     */
    public static String nullConvert(String src) {

        if (src == null || src.equals("null") || "".equals(src)
                || " ".equals(src)) {
            return "";
        } else {
            return src.trim();
        }
    }

    /**
     * <pre>
     * 인자로 받은 String이 null일 경우 &quot;0&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;0&quot;로 바꾼 String 값.
     * </pre>
     */
    public static int zeroConvert(Object src) {

        if (src == null || src.equals("null")) {
            return 0;
        } else {
            return Integer.parseInt(((String) src).trim());
        }
    }

    /**
     * <pre>
     * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
     * </pre>
     */
    public static int zeroConvert(String src) {

        if (src == null || src.equals("null") || "".equals(src)
                || " ".equals(src)) {
            return 0;
        } else {
            return Integer.parseInt(src.trim());
        }
    }

    /**
     * <p>
     * 문자열에서 {@link Character#isWhitespace(char)}에 정의된 모든 공백문자를 제거한다.
     * </p>
     *
     * <pre>
     * StringUtil.removeWhitespace(null)         = null
     * StringUtil.removeWhitespace("")           = ""
     * StringUtil.removeWhitespace("abc")        = "abc"
     * StringUtil.removeWhitespace("   ab  c  ") = "abc"
     * </pre>
     *
     * @param str
     *            공백문자가 제거도어야 할 문자열
     * @return the 공백문자가 제거된 문자열, null이 입력되면 <code>null</code>이 리턴
     */
    public static String removeWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }

        return new String(chs, 0, count);
    }

    /**
     * Html 코드가 들어간 문서를 표시할때 태그에 손상없이 보이기 위한 메서드
     *
     * @param strString
     * @return HTML 태그를 치환한 문자열
     */
    public static String checkHtmlView(String strString) {
        String strNew = "";

        try {
            StringBuffer strTxt = new StringBuffer("");

            char chrBuff;
            int len = strString.length();

            for (int i = 0; i < len; i++) {
                chrBuff = (char) strString.charAt(i);

                switch (chrBuff) {
                    case '<':
                        strTxt.append("&lt;");
                        break;
                    case '>':
                        strTxt.append("&gt;");
                        break;
                    case '"':
                        strTxt.append("&quot;");
                        break;
                    case 10:
                        strTxt.append("<br>");
                        break;
                    case ' ':
                        strTxt.append("&nbsp;");
                        break;
                    // case '&' :
                    // strTxt.append("&amp;");
                    // break;
                    default:
                        strTxt.append(chrBuff);
                }
            }

            strNew = strTxt.toString();

        } catch (Exception ex) {
            return null;
        }

        return strNew;
    }

    /**
     * 문자열을 지정한 분리자에 의해 배열로 리턴하는 메서드.
     *
     * @param source
     *            원본 문자열
     * @param separator
     *            분리자
     * @return result 분리자로 나뉘어진 문자열 배열
     */
    public static String[] split(String source, String separator)
            throws NullPointerException {
        String[] returnVal = null;
        int cnt = 1;

        int index = source.indexOf(separator);
        int index0 = 0;
        while (index >= 0) {
            cnt++;
            index = source.indexOf(separator, index + 1);
        }
        returnVal = new String[cnt];
        cnt = 0;
        index = source.indexOf(separator);
        while (index >= 0) {
            returnVal[cnt] = source.substring(index0, index);
            index0 = index + 1;
            index = source.indexOf(separator, index + 1);
            cnt++;
        }
        returnVal[cnt] = source.substring(index0);

        return returnVal;
    }

    /**
     * <p>
     * {@link String#toLowerCase()}를 이용하여 소문자로 변환한다.
     * </p>
     *
     * <pre>
     * StringUtil.lowerCase(null)  = null
     * StringUtil.lowerCase("")    = ""
     * StringUtil.lowerCase("aBc") = "abc"
     * </pre>
     *
     * @param str
     *            소문자로 변환되어야 할 문자열
     * @return 소문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String lowerCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toLowerCase();
    }



    /**
     * <p>
     * {@link String#toUpperCase()}를 이용하여 대문자로 변환한다.
     * </p>
     *
     * <pre>
     * StringUtil.upperCase(null)  = null
     * StringUtil.upperCase("")    = ""
     * StringUtil.upperCase("aBc") = "ABC"
     * </pre>
     *
     * @param str
     *            대문자로 변환되어야 할 문자열
     * @return 대문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String upperCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toUpperCase();
    }

    /**
     * <p>
     * 입력된 String의 앞쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.
     * </p>
     *
     * <pre>
     * StringUtil.stripStart(null, *)          = null
     * StringUtil.stripStart("", *)            = ""
     * StringUtil.stripStart("abc", "")        = "abc"
     * StringUtil.stripStart("abc", null)      = "abc"
     * StringUtil.stripStart("  abc", null)    = "abc"
     * StringUtil.stripStart("abc  ", null)    = "abc  "
     * StringUtil.stripStart(" abc ", null)    = "abc "
     * StringUtil.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     *
     * @param str
     *            지정된 문자가 제거되어야 할 문자열
     * @param stripChars
     *            제거대상 문자열
     * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String stripStart(String str, String stripChars) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while ((start != strLen)
                    && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((start != strLen)
                    && (stripChars.indexOf(str.charAt(start)) != -1)) {
                start++;
            }
        }

        return str.substring(start);
    }

    /**
     * <p>
     * 입력된 String의 뒤쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.
     * </p>
     *
     * <pre>
     * StringUtil.stripEnd(null, *)          = null
     * StringUtil.stripEnd("", *)            = ""
     * StringUtil.stripEnd("abc", "")        = "abc"
     * StringUtil.stripEnd("abc", null)      = "abc"
     * StringUtil.stripEnd("  abc", null)    = "  abc"
     * StringUtil.stripEnd("abc  ", null)    = "abc"
     * StringUtil.stripEnd(" abc ", null)    = " abc"
     * StringUtil.stripEnd("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str
     *            지정된 문자가 제거되어야 할 문자열
     * @param stripChars
     *            제거대상 문자열
     * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String stripEnd(String str, String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (stripChars == null) {
            while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((end != 0)
                    && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
                end--;
            }
        }

        return str.substring(0, end);
    }

    /**
     * <p>
     * 입력된 String의 앞, 뒤에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.
     * </p>
     *
     * <pre>
     * StringUtil.strip(null, *)          = null
     * StringUtil.strip("", *)            = ""
     * StringUtil.strip("abc", null)      = "abc"
     * StringUtil.strip("  abc", null)    = "abc"
     * StringUtil.strip("abc  ", null)    = "abc"
     * StringUtil.strip(" abc ", null)    = "abc"
     * StringUtil.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str
     *            지정된 문자가 제거되어야 할 문자열
     * @param stripChars
     *            제거대상 문자열
     * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String strip(String str, String stripChars) {
        if (isEmpty(str)) {
            return str;
        }

        String srcStr = str;
        srcStr = stripStart(srcStr, stripChars);

        return stripEnd(srcStr, stripChars);
    }

    /**
     * 문자열을 지정한 분리자에 의해 지정된 길이의 배열로 리턴하는 메서드.
     *
     * @param source
     *            원본 문자열
     * @param separator
     *            분리자
     * @param arraylength
     *            배열 길이
     * @return 분리자로 나뉘어진 문자열 배열
     */
    public static String[] split(String source, String separator,
                                 int arraylength) throws NullPointerException {
        String[] returnVal = new String[arraylength];
        int cnt = 0;
        int index0 = 0;
        int index = source.indexOf(separator);
        while (index >= 0 && cnt < (arraylength - 1)) {
            returnVal[cnt] = source.substring(index0, index);
            index0 = index + 1;
            index = source.indexOf(separator, index + 1);
            cnt++;
        }
        returnVal[cnt] = source.substring(index0);
        if (cnt < (arraylength - 1)) {
            for (int i = cnt + 1; i < arraylength; i++) {
                returnVal[i] = "";
            }
        }

        return returnVal;
    }

    /**
     * 문자열 A에서 Z사이의 랜덤 문자열을 구하는 기능을 제공 시작문자열과 종료문자열 사이의 랜덤 문자열을 구하는 기능
     *
     * @param startChr
     *            - 첫 문자
     * @param endChr
     *            - 마지막문자
     * @return 랜덤문자
     * @exception MyException
     * @see
     */
    public static String getRandomStr(char startChr, char endChr) {

        int randomInt;
        String randomStr = null;

        // 시작문자 및 종료문자를 아스키숫자로 변환한다.
        int startInt = Integer.valueOf(startChr);
        int endInt = Integer.valueOf(endChr);

        // 시작문자열이 종료문자열보가 클경우
        if (startInt > endInt) {
            throw new IllegalArgumentException("Start String: " + startChr
                    + " End String: " + endChr);
        }

        try {
            // 랜덤 객체 생성
            SecureRandom rnd = new SecureRandom();

            do {
                // 시작문자 및 종료문자 중에서 랜덤 숫자를 발생시킨다.
                randomInt = rnd.nextInt(endInt + 1);
            } while (randomInt < startInt); // 입력받은 문자 'A'(65)보다 작으면 다시 랜덤 숫자
            // 발생.

            // 랜덤 숫자를 문자로 변환 후 스트링으로 다시 변환
            randomStr = (char) randomInt + "";
        } catch (Exception e) {
            //Logger.getLogger(StringUtils.class).debug(e);// e.printStackTrace();
        }

        // 랜덤문자열를 리턴
        return randomStr;
    }

    /**
     * 문자열을 다양한 문자셋(EUC-KR[KSC5601],UTF-8..)을 사용하여 인코딩하는 기능 역으로 디코딩하여 원래의 문자열을
     * 복원하는 기능을 제공함 String temp = new String(문자열.getBytes("바꾸기전 인코딩"),"바꿀 인코딩");
     * String temp = new String(문자열.getBytes("8859_1"),"KSC5601"); => UTF-8 에서
     * EUC-KR
     *
     * @param srcString
     *            - 문자열
     * @param srcCharsetNm
     *            - 원래 CharsetNm
     * @param charsetNm
     *            - CharsetNm
     * @return 인(디)코딩 문자열
     * @exception MyException
     * @see
     */
    public static String getEncdDcd(String srcString, String srcCharsetNm,
                                    String cnvrCharsetNm) {

        String rtnStr = null;

        if (srcString == null)
            return null;

        try {
            rtnStr = new String(srcString.getBytes(srcCharsetNm), cnvrCharsetNm);
        } catch (UnsupportedEncodingException e) {
            rtnStr = null;
        }

        return rtnStr;
    }

    /**
     * 특수문자를 웹 브라우저에서 정상적으로 보이기 위해 특수문자를 처리('<' -> & lT)하는 기능이다
     * @param   srcString       - '<'
     * @return  변환문자열('<' -> "&lt"
     * @exception MyException
     * @see
     */
    public static String getSpclStrCnvr(String srcString) {

        String rtnStr = null;

        try {
            StringBuffer strTxt = new StringBuffer("");

            char chrBuff;
            int len = srcString.length();

            for (int i = 0; i < len; i++) {
                chrBuff = (char) srcString.charAt(i);

                switch (chrBuff) {
                    case '<':
                        strTxt.append("&lt;");
                        break;
                    case '>':
                        strTxt.append("&gt;");
                        break;
                    case '&':
                        strTxt.append("&amp;");
                        break;
                    default:
                        strTxt.append(chrBuff);
                }
            }

            rtnStr = strTxt.toString();

        } catch (Exception e) {
            //Logger.getLogger(StringUtils.class).debug(e);// e.printStackTrace();
        }

        return rtnStr;
    }

    /**
     * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
     *
     * @param
     * @return Timestamp 값
     * @exception MyException
     * @see
     */
    public static String getTimeStamp() {

        String rtnStr = null;

        // 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
        String pattern = "yyyyMMddhhmmssSSS";

        try {
            SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern,
                    Locale.KOREA);
            Timestamp ts = new Timestamp(System.currentTimeMillis());

            rtnStr = sdfCurrent.format(ts.getTime());
        } catch (Exception e) {
            //Logger.getLogger(StringUtils.class).debug(e);// e.printStackTrace();
        }

        return rtnStr;
    }

    /**
     * html의 특수문자를 표현하기 위해
     *
     * @param srcString
     * @return String
     * @exception Exception
     * @see
     */
    public static String getHtmlStrCnvr(String srcString) {

        String tmpString = srcString;

        try {
            tmpString = tmpString.replaceAll("&lt;", "<");
            tmpString = tmpString.replaceAll("&gt;", ">");
            tmpString = tmpString.replaceAll("&amp;", "&");
            tmpString = tmpString.replaceAll("&nbsp;", " ");
            tmpString = tmpString.replaceAll("&apos;", "\'");
            tmpString = tmpString.replaceAll("&quot;", "\"");
        } catch (Exception ex) {
            //Logger.getLogger(StringUtils.class).debug(ex);// ex.printStackTrace();
        }

        return tmpString;

    }

    /**
     * <p>
     * 날짜 형식의 문자열 내부에 마이너스 character(-)를 추가한다.
     * </p>
     *
     * <pre>
     * StringUtil.addMinusChar(&quot;20100901&quot;) = &quot;2010-09-01&quot;
     * </pre>
     *
     * @param date
     *            입력받는 문자열
     * @return " - "가 추가된 입력문자열
     */
    public static String addMinusChar(String date) {
        if (date.length() == 8)
            return date.substring(0, 4).concat("-")
                    .concat(date.substring(4, 6)).concat("-")
                    .concat(date.substring(6, 8));
        else
            return "";
    }

    public static String removeMark(String sValue, String mark) {
        StringBuffer sbValueWithoutMark = new StringBuffer();
        sValue = isNullToString(sValue);
        for (int i = 0; i < sValue.length(); i++) {
            if (!sValue.substring(i, i + 1).equals(mark)) {
                sbValueWithoutMark.append(sValue.substring(i, i + 1));
            }
        }
        return sbValueWithoutMark.toString();
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
     * <p>yyyyMMdd 혹은 yyyy-MM-dd 형식의 날짜 문자열을 입력 받아 년, 월, 일을
     * 증감한다. 년, 월, 일은 가감할 수를 의미하며, 음수를 입력할 경우 감한다.</p>
     *
     * <pre>
     * DateUtil.addYearMonthDay("19810828", 0, 0, 19)  = "19810916"
     * DateUtil.addYearMonthDay("20060228", 0, 0, -10) = "20060218"
     * DateUtil.addYearMonthDay("20060228", 0, 0, 10)  = "20060310"
     * DateUtil.addYearMonthDay("20060228", 0, 0, 32)  = "20060401"
     * DateUtil.addYearMonthDay("20050331", 0, -1, 0)  = "20050228"
     * DateUtil.addYearMonthDay("20050301", 0, 2, 30)  = "20050531"
     * DateUtil.addYearMonthDay("20050301", 1, 2, 30)  = "20060531"
     * DateUtil.addYearMonthDay("20040301", 2, 0, 0)   = "20060301"
     * DateUtil.addYearMonthDay("20040229", 2, 0, 0)   = "20060228"
     * DateUtil.addYearMonthDay("20040229", 2, 0, 1)   = "20060301"
     * </pre>
     *
     * @param  dateStr 날짜 문자열(yyyyMMdd, yyyy-MM-dd의 형식)
     * @param  year 가감할 년. 0이 입력될 경우 가감이 없다
     * @param  month 가감할 월. 0이 입력될 경우 가감이 없다
     * @param  day 가감할 일. 0이 입력될 경우 가감이 없다
     * @return  yyyyMMdd 형식의 날짜 문자열
     * @throws IllegalArgumentException 날짜 포맷이 정해진 바와 다를 경우.
     *         입력 값이 <code>null</code>인 경우.
     */
    public static String addYearMonthDay(String sDate, int year, int month, int day) {

        String dateStr = validChkDate(sDate);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        try {
            cal.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }

        if (year != 0)
            cal.add(Calendar.YEAR, year);
        if (month != 0)
            cal.add(Calendar.MONTH, month);
        if (day != 0)
            cal.add(Calendar.DATE, day);
        return sdf.format(cal.getTime());
    }

    /**
     * <p>yyyyMMdd 혹은 yyyy-MM-dd 형식의 날짜 문자열을 입력 받아 년을
     * 증감한다. <code>year</code>는 가감할 수를 의미하며, 음수를 입력할 경우 감한다.</p>
     *
     * <pre>
     * DateUtil.addYear("20000201", 62)  = "20620201"
     * DateUtil.addYear("20620201", -62) = "20000201"
     * DateUtil.addYear("20040229", 2)   = "20060228"
     * DateUtil.addYear("20060228", -2)  = "20040228"
     * DateUtil.addYear("19000101", 200) = "21000101"
     * </pre>
     *
     * @param  dateStr 날짜 문자열(yyyyMMdd, yyyy-MM-dd의 형식)
     * @param  year 가감할 년. 0이 입력될 경우 가감이 없다
     * @return  yyyyMMdd 형식의 날짜 문자열
     * @throws IllegalArgumentException 날짜 포맷이 정해진 바와 다를 경우.
     *         입력 값이 <code>null</code>인 경우.
     */
    public static String addYear(String dateStr, int year) {
        return addYearMonthDay(dateStr, year, 0, 0);
    }

    /**
     * <p>yyyyMMdd 혹은 yyyy-MM-dd 형식의 날짜 문자열을 입력 받아 월을
     * 증감한다. <code>month</code>는 가감할 수를 의미하며, 음수를 입력할 경우 감한다.</p>
     *
     * <pre>
     * DateUtil.addMonth("20010201", 12)  = "20020201"
     * DateUtil.addMonth("19800229", 12)  = "19810228"
     * DateUtil.addMonth("20040229", 12)  = "20050228"
     * DateUtil.addMonth("20050228", -12) = "20040228"
     * DateUtil.addMonth("20060131", 1)   = "20060228"
     * DateUtil.addMonth("20060228", -1)  = "20060128"
     * </pre>
     *
     * @param  dateStr 날짜 문자열(yyyyMMdd, yyyy-MM-dd의 형식)
     * @param  month 가감할 월. 0이 입력될 경우 가감이 없다
     * @return  yyyyMMdd 형식의 날짜 문자열
     * @throws IllegalArgumentException 날짜 포맷이 정해진 바와 다를 경우.
     *         입력 값이 <code>null</code>인 경우.
     */
    public static String addMonth(String dateStr, int month) {
        return addYearMonthDay(dateStr, 0, month, 0);
    }

    /**
     * <p>yyyyMMdd 혹은 yyyy-MM-dd 형식의 날짜 문자열을 입력 받아 일(day)를
     * 증감한다. <code>day</code>는 가감할 수를 의미하며, 음수를 입력할 경우 감한다.
     * <br/><br/>
     * 위에 정의된 addDays 메서드는 사용자가 ParseException을 반드시 처리해야 하는 불편함이
     * 있기 때문에 추가된 메서드이다.</p>
     *
     * <pre>
     * DateUtil.addDay("19991201", 62) = "20000201"
     * DateUtil.addDay("20000201", -62) = "19991201"
     * DateUtil.addDay("20050831", 3) = "20050903"
     * DateUtil.addDay("20050831", 3) = "20050903"
     * // 2006년 6월 31일은 실제로 존재하지 않는 날짜이다 -> 20060701로 간주된다
     * DateUtil.addDay("20060631", 1) = "20060702"
     * </pre>
     *
     * @param  dateStr 날짜 문자열(yyyyMMdd, yyyy-MM-dd의 형식)
     * @param  day 가감할 일. 0이 입력될 경우 가감이 없다
     * @return  yyyyMMdd 형식의 날짜 문자열
     * @throws IllegalArgumentException 날짜 포맷이 정해진 바와 다를 경우.
     *         입력 값이 <code>null</code>인 경우.
     */
    public static String addDay(String dateStr, int day) {
        return addYearMonthDay(dateStr, 0, 0, day);
    }

    /**
     * <p>yyyyMMdd 혹은 yyyy-MM-dd 형식의 날짜 문자열 <code>dateStr1</code>과 <code>
     * dateStr2</code> 사이의 일 수를 구한다.<br>
     * <code>dateStr2</code>가 <code>dateStr1</code> 보다 과거 날짜일 경우에는
     * 음수를 반환한다. 동일한 경우에는 0을 반환한다.</p>
     *
     * <pre>
     * DateUtil.getDaysDiff("20060228","20060310") = 10
     * DateUtil.getDaysDiff("20060101","20070101") = 365
     * DateUtil.getDaysDiff("19990228","19990131") = -28
     * DateUtil.getDaysDiff("20060801","20060802") = 1
     * DateUtil.getDaysDiff("20060801","20060801") = 0
     * </pre>
     *
     * @param  dateStr1 날짜 문자열(yyyyMMdd, yyyy-MM-dd의 형식)
     * @param  dateStr2 날짜 문자열(yyyyMMdd, yyyy-MM-dd의 형식)
     * @return  일 수 차이.
     * @throws IllegalArgumentException 날짜 포맷이 정해진 바와 다를 경우.
     *         입력 값이 <code>null</code>인 경우.
     */
    public static int getDaysDiff(String sDate1, String sDate2) {
        String dateStr1 = validChkDate(sDate1);
        String dateStr2 = validChkDate(sDate2);

        if (!checkDate(sDate1) || !checkDate(sDate2)) {
            throw new IllegalArgumentException("Invalid date format: args[0]=" + sDate1 + " args[1]=" + sDate2);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(dateStr1);
            date2 = sdf.parse(dateStr2);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: args[0]=" + dateStr1 + " args[1]=" + dateStr2);
        }
        int days1 = (int)((date1.getTime()/3600000)/24);
        int days2 = (int)((date2.getTime()/3600000)/24);

        return days2 - days1;
    }

    /**
     * <p>yyyyMMdd 혹은 yyyy-MM-dd 형식의 날짜 문자열을 입력 받아 유효한 날짜인지 검사.</p>
     *
     * <pre>
     * DateUtil.checkDate("1999-02-35") = false
     * DateUtil.checkDate("2000-13-31") = false
     * DateUtil.checkDate("2006-11-31") = false
     * DateUtil.checkDate("2006-2-28")  = false
     * DateUtil.checkDate("2006-2-8")   = false
     * DateUtil.checkDate("20060228")   = true
     * DateUtil.checkDate("2006-02-28") = true
     * </pre>
     *
     * @param  dateStr 날짜 문자열(yyyyMMdd, yyyy-MM-dd의 형식)
     * @return  유효한 날짜인지 여부
     */
    public static boolean checkDate(String sDate) {
        String dateStr = validChkDate(sDate);

        String year  = dateStr.substring(0,4);
        String month = dateStr.substring(4,6);
        String day   = dateStr.substring(6);

        return checkDate(year, month, day);
    }

    /**
     * <p>입력한 년, 월, 일이 유효한지 검사.</p>
     *
     * @param  year 연도
     * @param  month 월
     * @param  day 일
     * @return  유효한 날짜인지 여부
     */
    public static boolean checkDate(String year, String month, String day) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());

            Date result = formatter.parse(year + "." + month + "." + day);
            String resultStr = formatter.format(result);
            if (resultStr.equalsIgnoreCase(year + "." + month + "." + day))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 날짜형태의 String의 날짜 포맷 및 TimeZone을 변경해 주는 메서드
     *
     * @param  strSource       바꿀 날짜 String
     * @param  fromDateFormat  기존의 날짜 형태
     * @param  toDateFormat    원하는 날짜 형태
     * @param  strTimeZone     변경할 TimeZone(""이면 변경 안함)
     * @return  소스 String의 날짜 포맷을 변경한 String
     */
    public static String convertDate(String strSource, String fromDateFormat,
                                     String toDateFormat, String strTimeZone) {
        SimpleDateFormat simpledateformat = null;
        Date date = null;
        String _fromDateFormat = "";
        String _toDateFormat = "";

        if(isNullToString(strSource).trim().equals("")) {
            return "";
        }
        if(isNullToString(fromDateFormat).trim().equals(""))
            _fromDateFormat = "yyyyMMddHHmmss";                    // default값
        if(isNullToString(toDateFormat).trim().equals(""))
            _toDateFormat = "yyyy-MM-dd HH:mm:ss";                 // default값

        try {
            simpledateformat = new SimpleDateFormat(_fromDateFormat, Locale.getDefault());
            date = simpledateformat.parse(strSource);
            if (!isNullToString(strTimeZone).trim().equals("")) {
                simpledateformat.setTimeZone(TimeZone.getTimeZone(strTimeZone));
            }
            simpledateformat = new SimpleDateFormat(_toDateFormat, Locale.getDefault());
        }
        catch(Exception exception) {
            //Logger.getLogger(StringUtils.class).debug(exception);//exception.printStackTrace();
        }
        if(simpledateformat != null && simpledateformat.format(date) != null ){
            return simpledateformat.format(date);
        }else {
            return "";
        }

    }


    /**
     * yyyyMMdd 형식의 날짜문자열을 원하는 캐릭터(ch)로 쪼개 돌려준다<br/>
     * <pre>
     * ex) 20030405, ch(.) -> 2003.04.05
     * ex) 200304, ch(.) -> 2003.04
     * ex) 20040101,ch(/) --> 2004/01/01 로 리턴
     * </pre>
     *
     * @param date yyyyMMdd 형식의 날짜문자열
     * @param ch 구분자
     * @return 변환된 문자열
     */
    public static String formatDate(String sDate, String ch) {
        String dateStr = validChkDate(sDate);

        String str = dateStr.trim();
        String yyyy = "";
        String mm = "";
        String dd = "";

        if (str.length() == 8) {
            yyyy = str.substring(0, 4);
            if (yyyy.equals("0000"))
                return "";

            mm = str.substring(4, 6);
            if (mm.equals("00"))
                return yyyy;

            dd = str.substring(6, 8);
            if (dd.equals("00"))
                return yyyy + ch + mm;

            return yyyy + ch + mm + ch + dd;
        } else if (str.length() == 6) {
            yyyy = str.substring(0, 4);
            if (yyyy.equals("0000"))
                return "";

            mm = str.substring(4, 6);
            if (mm.equals("00"))
                return yyyy;

            return yyyy + ch + mm;
        } else if (str.length() == 4) {
            yyyy = str.substring(0, 4);
            if (yyyy.equals("0000"))
                return "";
            else
                return yyyy;
        } else
            return "";
    }

    /**
     * HH24MISS 형식의 시간문자열을 원하는 캐릭터(ch)로 쪼개 돌려준다 <br>
     * <pre>
     *     ex) 151241, ch(/) -> 15/12/31
     * </pre>
     *
     * @param str HH24MISS 형식의 시간문자열
     * @param ch 구분자
     * @return 변환된 문자열
     */
    public static String formatTime(String sTime, String ch) {
        String timeStr = validChkTime(sTime);
        return timeStr.substring(0, 2) + ch + timeStr.substring(2, 4) + ch + timeStr.substring(4, 6);
    }

    /**
     * 연도를 입력 받아 해당 연도 2월의 말일(일수)를 문자열로 반환한다.
     *
     * @param year
     * @return 해당 연도 2월의 말일(일수)
     */
    public String leapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return "29";
        }

        return "28";
    }

    /**
     * <p>입력받은 연도가 윤년인지 아닌지 검사한다.</p>
     *
     * <pre>
     * DateUtil.isLeapYear(2004) = false
     * DateUtil.isLeapYear(2005) = true
     * DateUtil.isLeapYear(2006) = true
     * </pre>
     *
     * @param  year 연도
     * @return  윤년 여부
     */
    public static boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return false;
        }
        return true;
    }


    /**
     * 현재(한국기준) 날짜정보를 얻는다.                     <BR>
     * 표기법은 yyyy-mm-dd                                  <BR>
     * @return  String      yyyymmdd형태의 현재 한국시간.   <BR>
     */
    public static String getToday(){
        return getCurrentDate("");
    }

    /**
     * 현재(한국기준) 날짜정보를 얻는다.                     <BR>
     * 표기법은 yyyy-mm-dd                                  <BR>
     * @return  String      yyyymmdd형태의 현재 한국시간.   <BR>
     */
    public static String getCurrentDate(String dateType) {
        Calendar aCalendar = Calendar.getInstance();

        int year = aCalendar.get(Calendar.YEAR);
        int month = aCalendar.get(Calendar.MONTH) + 1;
        int date = aCalendar.get(Calendar.DATE);
        String strDate = Integer.toString(year) +
                ((month<10) ? "0" + Integer.toString(month) : Integer.toString(month)) +
                ((date<10) ? "0" + Integer.toString(date) : Integer.toString(date));

        if(!"".equals(dateType)) strDate = convertDate(strDate, "yyyyMMdd", dateType);

        return  strDate;
    }

    /**
     * 날짜형태의 String의 날짜 포맷만을 변경해 주는 메서드
     * @param sDate 날짜
     * @param sTime 시간
     * @param sFormatStr 포멧 스트링 문자열
     * @return 지정한 날짜/시간을 지정한 포맷으로 출력
     * @See Letter  Date or Time Component  Presentation  Examples
    G  Era designator  Text  AD
    y  Year  Year  1996; 96
    M  Month in year  Month  July; Jul; 07
    w  Week in year  Number  27
    W  Week in month  Number  2
    D  Day in year  Number  189
    d  Day in month  Number  10
    F  Day of week in month  Number  2
    E  Day in week  Text  Tuesday; Tue
    a  Am/pm marker  Text  PM
    H  Hour in day (0-23)  Number  0
    k  Hour in day (1-24)  Number  24
    K  Hour in am/pm (0-11)  Number  0
    h  Hour in am/pm (1-12)  Number  12
    m  Minute in hour  Number  30
    s  Second in minute  Number  55
    S  Millisecond  Number  978
    z  Time zone  General time zone  Pacific Standard Time; PST; GMT-08:00
    Z  Time zone  RFC 822 time zone  -0800



    Date and Time Pattern  Result
    "yyyy.MM.dd G 'at' HH:mm:ss z"  2001.07.04 AD at 12:08:56 PDT
    "EEE, MMM d, ''yy"  Wed, Jul 4, '01
    "h:mm a"  12:08 PM
    "hh 'o''clock' a, zzzz"  12 o'clock PM, Pacific Daylight Time
    "K:mm a, z"  0:08 PM, PDT
    "yyyyy.MMMMM.dd GGG hh:mm aaa"  02001.July.04 AD 12:08 PM
    "EEE, d MMM yyyy HH:mm:ss Z"  Wed, 4 Jul 2001 12:08:56 -0700
    "yyMMddHHmmssZ"  010704120856-0700

     */
    public static String convertDate(String sDate, String sTime, String sFormatStr) {
        String dateStr = validChkDate(sDate);
        String timeStr = validChkTime(sTime);

        Calendar cal = null;
        cal = Calendar.getInstance() ;

        cal.set(Calendar.YEAR        , Integer.parseInt(dateStr.substring(0,4)));
        cal.set(Calendar.MONTH       , Integer.parseInt(dateStr.substring(4,6))-1 );
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(6,8)));
        cal.set(Calendar.HOUR_OF_DAY , Integer.parseInt(timeStr.substring(0,2)));
        cal.set(Calendar.MINUTE      , Integer.parseInt(timeStr.substring(2,4)));

        SimpleDateFormat sdf = new SimpleDateFormat(sFormatStr,Locale.ENGLISH);

        return sdf.format(cal.getTime());
    }

    /**
     * 입력받은 일자 사이의 임의의 일자를 반환
     * @param sDate1 시작일자
     * @param sDate2 종료일자
     * @return 임의일자
     */
    public static String getRandomDate(String sDate1, String sDate2) {
        String dateStr1 = validChkDate(sDate1);
        String dateStr2 = validChkDate(sDate2);

        String randomDate   = null;

        int sYear, sMonth, sDay;
        int eYear, eMonth, eDay;

        sYear  = Integer.parseInt(dateStr1.substring(0, 4));
        sMonth = Integer.parseInt(dateStr1.substring(4, 6));
        sDay   = Integer.parseInt(dateStr1.substring(6, 8));

        eYear  = Integer.parseInt(dateStr2.substring(0, 4));
        eMonth = Integer.parseInt(dateStr2.substring(4, 6));
        eDay   = Integer.parseInt(dateStr2.substring(6, 8));

        GregorianCalendar beginDate = new GregorianCalendar(sYear, sMonth-1, sDay,    0, 0);
        GregorianCalendar endDate   = new GregorianCalendar(eYear, eMonth-1, eDay,   23,59);

        if (endDate.getTimeInMillis() < beginDate.getTimeInMillis()) {
            throw new IllegalArgumentException("Invalid input date : " + sDate1 + "~" + sDate2);
        }

        SecureRandom r = new SecureRandom();

        long rand = ((r.nextLong()>>>1)%( endDate.getTimeInMillis()-beginDate.getTimeInMillis() + 1)) + beginDate.getTimeInMillis();

        GregorianCalendar cal = new GregorianCalendar();
        //SimpleDateFormat calformat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat calformat = new SimpleDateFormat("yyyyMMdd",Locale.ENGLISH);
        cal.setTimeInMillis(rand);
        randomDate = calformat.format(cal.getTime());

        // 랜덤문자열를 리턴
        return  randomDate;
    }

    /**
     * <p>
     * Checks if the String contains only unicode
     * letters or digits.
     * </p>
     * <p>
     * <code>null</code> will return <code>false</code>
     * . An empty String ("") will return
     * <code>false</code>.
     * </p>
     *
     * <pre>
     * StringUtil.isAlphaNumeric(null)               = false
     * StringUtil.isAlphaNumeric(&quot;&quot;)     = false
     * StringUtil.isAlphaNumeric(&quot;  &quot;)   = false
     * StringUtil.isAlphaNumeric(&quot;abc&quot;)  = true
     * StringUtil.isAlphaNumeric(&quot;ab c&quot;) = false
     * StringUtil.isAlphaNumeric(&quot;ab2c&quot;) = true
     * StringUtil.isAlphaNumeric(&quot;ab-c&quot;) = false
     * </pre>
     * @param str
     *        the String to check, may be null
     * @return <code>true</code> if only contains
     *         letters or digits, and is non-null
     *         public static boolean
     *         isAlphaNumeric(String str) { if (str ==
     *         null) { return false; } int sz =
     *         str.length(); if (sz == 0) return false;
     *         for (int i = 0; i < sz; i++) { if
     *         (!Character
     *         .isLetterOrDigit(str.charAt(i))) {
     *         return false; } } return true; } /**
     *         <p>
     *         Checks if the String contains only
     *         unicode letters.
     *         </p>
     *         <p>
     *         <code>null</code> will return
     *         <code>false</code>. An empty String ("")
     *         will return <code>false</code>.
     *         </p>
     *
     *         <pre>
     * StringUtil.isAlpha(null)             = false
     * StringUtil.isAlpha(&quot;&quot;)     = false
     * StringUtil.isAlpha(&quot;  &quot;)   = false
     * StringUtil.isAlpha(&quot;abc&quot;)  = true
     * StringUtil.isAlpha(&quot;ab2c&quot;) = false
     * StringUtil.isAlpha(&quot;ab-c&quot;) = false
     * </pre>
     * @param str
     *        the String to check, may be null
     * @return <code>true</code> if only contains
     *         letters, and is non-null public static
     *         boolean isAlpha(String str) { if (str ==
     *         null) { return false; } int sz =
     *         str.length(); if (sz == 0) return false;
     *         for (int i = 0; i < sz; i++) { if
     *         (!Character.isLetter(str.charAt(i))) {
     *         return false; } } return true; } /**
     *         <p>
     *         Checks if the String contains only
     *         unicode digits. A decimal point is not a
     *         unicode digit and returns false.
     *         </p>
     *         <p>
     *         <code>null</code> will return
     *         <code>false</code>. An empty String ("")
     *         will return <code>false</code>.
     *         </p>
     *
     *         <pre>
     * StringUtil.isNumeric(null)              = false
     * StringUtil.isNumeric(&quot;&quot;)     = false
     * StringUtil.isNumeric(&quot;  &quot;)   = false
     * StringUtil.isNumeric(&quot;123&quot;)  = true
     * StringUtil.isNumeric(&quot;12 3&quot;) = false
     * StringUtil.isNumeric(&quot;ab2c&quot;) = false
     * StringUtil.isNumeric(&quot;12-3&quot;) = false
     * StringUtil.isNumeric(&quot;12.3&quot;) = false
     * </pre>
     * @param str
     *        the String to check, may be null
     * @return <code>true</code> if only contains
     *         digits, and is non-null
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        if (sz == 0)
            return false;
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }



    /**
     * 입력받은 요일의 영문명을 국문명의 요일로 반환
     * @param sWeek 영문 요일명
     * @return 국문 요일명
     */
    public static String convertWeek(String sWeek) {
        String retStr = null;

        if        (sWeek.equals("SUN")   ) { retStr = "일요일";
        } else if (sWeek.equals("MON")   ) { retStr = "월요일";
        } else if (sWeek.equals("TUE")   ) { retStr = "화요일";
        } else if (sWeek.equals("WED")   ) { retStr = "수요일";
        } else if (sWeek.equals("THR")   ) { retStr = "목요일";
        } else if (sWeek.equals("FRI")   ) { retStr = "금요일";
        } else if (sWeek.equals("SAT")   ) { retStr = "토요일";
        }

        return retStr;
    }

    /**
     * 입력일자의 유효 여부를 확인
     * @param sDate 일자
     * @return 유효 여부
     */
    public static boolean validDate(String sDate) {
        String dateStr = validChkDate(sDate);

        Calendar cal ;
        boolean ret  = false;

        cal = Calendar.getInstance() ;

        cal.set(Calendar.YEAR        , Integer.parseInt(dateStr.substring(0,4)));
        cal.set(Calendar.MONTH       , Integer.parseInt(dateStr.substring(4,6))-1 );
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(6,8)));

        String year  = String.valueOf(cal.get(Calendar.YEAR        )    );
        String month = String.valueOf(cal.get(Calendar.MONTH       ) + 1);
        String day   = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)    );

        String pad4Str = "0000";
        String pad2Str = "00";

        String retYear  = (pad4Str + year ).substring(year .length());
        String retMonth = (pad2Str + month).substring(month.length());
        String retDay   = (pad2Str + day  ).substring(day  .length());

        String retYMD = retYear+retMonth+retDay;

        if(sDate.equals(retYMD)) {
            ret  = true;
        }

        return ret;
    }

    /**
     * 입력일자, 요일의 유효 여부를 확인
     * @param     sDate 일자
     * @param     sWeek 요일 (DAY_OF_WEEK)
     * @return    유효 여부
     */
    public static boolean validDate(String sDate, int sWeek) {
        String dateStr = validChkDate(sDate);

        Calendar cal ;
        boolean ret  = false;

        cal = Calendar.getInstance() ;

        cal.set(Calendar.YEAR        , Integer.parseInt(dateStr.substring(0,4)));
        cal.set(Calendar.MONTH       , Integer.parseInt(dateStr.substring(4,6))-1 );
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(6,8)));

        int    Week  =                cal.get(Calendar.DAY_OF_WEEK      );

        if (validDate(sDate)) {
            if (sWeek == Week) {
                ret = true;
            }
        }

        return ret;
    }

    /**
     * 입력시간의 유효 여부를 확인
     * @param     sTime 입력시간
     * @return    유효 여부
     */
    public static boolean validTime(String sTime) {
        String timeStr = validChkTime(sTime);

        Calendar cal ;
        boolean ret = false;

        cal = Calendar.getInstance() ;

        cal.set(Calendar.HOUR_OF_DAY  , Integer.parseInt(timeStr.substring(0,2)));
        cal.set(Calendar.MINUTE       , Integer.parseInt(timeStr.substring(2,4)));

        String HH     = String.valueOf(cal.get(Calendar.HOUR_OF_DAY  ));
        String MM     = String.valueOf(cal.get(Calendar.MINUTE       ));

        String pad2Str = "00";

        String retHH = (pad2Str + HH).substring(HH.length());
        String retMM = (pad2Str + MM).substring(MM.length());

        String retTime = retHH + retMM;

        if(sTime.equals(retTime)) {
            ret  = true;
        }

        return ret;
    }

    /**
     * 입력된 일자에 연, 월, 일을 가감한 날짜의 요일을 반환
     * @param sDate 날짜
     * @param year 연
     * @param month 월
     * @param day 일
     * @return 계산된 일자의 요일(DAY_OF_WEEK)
     */
    public static String addYMDtoWeek(String sDate, int year, int month, int day) {
        String dateStr = validChkDate(sDate);

        dateStr = addYearMonthDay(dateStr, year, month, day);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }

        SimpleDateFormat rsdf = new SimpleDateFormat("E",Locale.ENGLISH);

        return rsdf.format(cal.getTime());
    }

    /**
     * 입력된 일자에 연, 월, 일, 시간, 분을 가감한 날짜, 시간을 포멧스트링 형식으로 반환
     * @param sDate 날짜
     * @param sTime 시간
     * @param year 연
     * @param month 월
     * @param day 일
     * @param hour 시간
     * @param minute 분
     * @param formatStr 포멧스트링
     * @return
     */
    public static String addYMDtoDayTime(String sDate, String sTime, int year, int month, int day, int hour, int minute, String formatStr) {
        String dateStr = validChkDate(sDate);
        String timeStr = validChkTime(sTime);

        dateStr = addYearMonthDay(dateStr, year, month, day);

        dateStr = convertDate(dateStr, timeStr, "yyyyMMddHHmm");

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm",Locale.ENGLISH);

        try {
            cal.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }

        if (hour != 0) {
            cal.add(Calendar.HOUR, hour);
        }

        if (minute != 0) {
            cal.add(Calendar.MINUTE, minute);
        }

        SimpleDateFormat rsdf = new SimpleDateFormat(formatStr,Locale.ENGLISH);

        return rsdf.format(cal.getTime());
    }

    /**
     * 입력된 일자를 int 형으로 반환
     * @param sDate 일자
     * @return int(일자)
     */
    public static int datetoInt(String sDate) {
        return Integer.parseInt(convertDate(sDate, "0000", "yyyyMMdd"));
    }

    /**
     * 입력된 시간을 int 형으로 반환
     * @param sTime 시간
     * @return int(시간)
     */
    public static int timetoInt(String sTime) {
        return Integer.parseInt(convertDate("00000101", sTime, "HHmm"));
    }

    /**
     * 입력된 일자 문자열을 확인하고 8자리로 리턴
     * @param sDate
     * @return
     */
    public static String validChkDate(String dateStr) {
        String _dateStr = dateStr;

        if (dateStr == null || !(dateStr.trim().length() == 8 || dateStr.trim().length() == 10)) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
        if (dateStr.length() == 10) {
            _dateStr = removeMinusChar(dateStr);
        }
        return _dateStr;
    }

    /**
     * 입력된 일자 문자열을 확인하고 8자리로 리턴
     * @param sDate
     * @return
     */
    public static String validChkTime(String timeStr) {
        String _timeStr = timeStr;

        if (_timeStr.length() == 5) {
            _timeStr = remove(_timeStr,':');
        }
        if (_timeStr == null || !(_timeStr.trim().length() == 4)) {
            throw new IllegalArgumentException("Invalid time format: " + _timeStr);
        }

        return _timeStr;
    }

    /**
     * @Method Name  : convert2CamelCase
     * @작성일   : 2015. 3. 23.
     * @작성자   : (주)크림소프트 김윤관
     * @변경이력  :
     * @Method 설명 : 카멜표기법으로 변경 USER_ID -> userId
     * @param underScore
     * @return
     */
    public static String convert2CamelCase(String underScore) {

        // '_' 가 나타나지 않으면 이미 camel case 로 가정함.
        // 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
        // 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜
        if (underScore.indexOf('_') < 0
                && Character.isLowerCase(underScore.charAt(0))) {
            return underScore;
        }
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        int len = underScore.length();

        for (int i = 0; i < len; i++) {
            char currentChar = underScore.charAt(i);
            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return result.toString();
    }


    /**
     * <p>
     * Date -> String
     * </p>
     * @param date
     *        Date which you want to change.
     * @return String The Date string. Type, yyyyMMdd
     *         HH:mm:ss.
     */
    public static String DateToString(Date date, String format, Locale locale) {

        if (!"".equals(checkNull(format))) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        if (checkNull(locale)) {
            locale = java.util.Locale.KOREA;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);

        String tmp = sdf.format(date);

        return tmp;
    }

    /**
     * Comment  : String 데이터를 특정 타입을 만들어 준다.
     * @param data
     * @param type
     *  date: 날짜(2008/01/15)
     *  ym: 년월(2008/01)
     *  df: 날짜(2008/01/01)
     *  dt: 년월일 시분(2008/01/15 15:15)
     *  dts: 년월일 시분초(2008/01/15 15:15:15)
     *  bn: 사업자번호(123-45-76890)
     *  post: 우편번호(010-010)
     *  jm: 우편번호(791212-1234567)
     *  ni: 정수형으로 변환
     *  comma: 숫자에 컴마를 넣는다.(123,355)
     *  null :  폼서식을 제거한다.
     * @return
     */
    public static String formData(String data, String type){
        if(type == null || data == null || "null".equals(data))return isNull(data);
        if(type.equals("date")){
            return  formString(data, "####/##/##");
        }else if(type.equals("ym")){
            return  formString(data, "####/##");
        }else if(type.equals("df")){
            return  formString(data, "####/##")+ "/01";
        }else if(type.equals("dt")){
            return  formString(data, "####/##/## ##:##");
        }else if(type.equals("dts")){
            return  formString(data, "####/##/## ##:##:##");
        }else if(type.equals("bn")){
            return  formString(data, "###-##-#####");
        }else if(type.equals("post") ||type.equals("pn")){
            return  formString(data, "###-###");
        }else if(type.equals("jm")){
            return  formString(data, "######-#######");
        }else if(type.equals("comma")){
            return  ComUtil.strToNumber(data);
        }else {
            return data;
        }
    }

    /**
     * Comment  : String 데이터를 format에 맞게 형식화 한다.
     * @param data
     * @param format
     */
    public static String formString(String data, String format){
        if(data==null) return "";
        StringBuffer rtn    = new StringBuffer();
        int i = 0, j = 0;
        for (; i < data.length() &&  j < format.length(); i++, j++) {
            if(format.charAt(j)=='#'){
                rtn.append(data.charAt(i));
            }else if(format.charAt(j)=='0' && format.charAt(j)== '0'){
            }else{
                rtn.append(format.charAt(j));
                i--;
            }
        }
        return rtn.toString();
    }

    /**
     *
     * @Method Name  : toNumFomat
     * @작성일   : 2017. 12. 13.
     * @작성자   :  (주)씨엠이소프트 박상웅
     * @변경이력  :
     * @Method 설명 : 천단위 콤마찍기
     * @param num
     * @return
     */
    public static String toNumFomat(int num) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }


    /**
     *
     * @Method Name  : RandumNumber
     * @작성일   : 2018. 08. 31.
     * @작성자   :  (주)씨엠이소프트 임우빈
     * @변경이력  :
     * @Method 설명 : 6자리 숫자로된 인증코드
     * @param num
     * @return
     */
    public static String RandomNumber(int num) {
        //int[] random = new int[];
        String random = "";
        for(int i=0; i<num; i++){
            int key =  (int)(Math.random() * 10);
            String getKey = String.valueOf(key);
            random = random + getKey;
        }
        return random;
    }

}

