/**
 * 씨엠이 소프트 자체 프로젝트 입니다. 허가 없이 복제 및 배포 할 수 없습니다.
 * 개발사 : (주)씨엠이소프트
 * CopyRight 씨엠이소프트 - 2017
 */
package com.bitkrx.config.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.trade.vo.CoinListVO;

/**
 * @프로젝트명	: com.bitkrx.api
 * @패키지    	: com.bitkrx.config.util
 * @클래스명  	: com.bitkrx.api
 * @작성자		:  (주)씨엠이소프트 문영민
 * @작성일		: 2017. 12. 8.
 */
public class CmmCdConstant {

	@Autowired
	TradeService tradeService;
	
	//거래소명
	public static final String EXCHANGE_NAME = "pactbit";
	//앱 별 푸시발송 컨디션
    public static final String PUSH_CONDITION = "pactbit";

    public static final String PUSH_CONDITION_STA = "pactbit_staging";
	//메일발송시 발송되는 메일주소
	public static final String MAIL_FROM = "help@pactbit.com";
    //메일발송시 발송되는 메일주소
    public static final String FOTTER_MAIL = "help@pactbit.com";
	//웹도메인 주소
	public static final String WEB_DOMAIN_URL = "https://www.pactbit.com";
	//개발도메인 주소
	public static final String WEB_XLOGIC_DOMAIN_URL = "http://wpactbit.xlogic.co.kr";

    //public static final String WEB_DOMAIN_URL = "http://wplanbit.noryweb.com";

	//메일 호스트
	public static final String MAIL_HOST = "mail.pactbit.com";
	//대표 메일 계정
	public static final String MAIL_ACC = "help@pactbit.com";
	//대표 메일 비밀번호
	public static final String MAIL_PASS = "pactbit3838!@#";

	//비트코인 설정
    public static final String BTC_PWD = "Pl@nBitCoiN!";

	//이더리움 설정
	public static final String ETH_ACC = "0xD2bB24e348651970e0A3aE50EA89E5ff2B37C09B";
	public static final String ETH_ACC2 = "P1@nbIt";
    public static final String ETH_URL = "http://10.10.0.72:8000/addr/";

	//리플 설정
	public static final String XRP_ACC = "rNXp81JJsfCVZoKD9FzKGv6DXDeS3fLgxa";


    //SGC 설정
    public static final String SGC_ACC = "0xcb1b7287fbA892d72e0dbD2769964778aea496DA";
    public static final String SGC_ACC2 = "planBit$tagrAm";
    public static final String SGC_URL = "http://222.239.218.122:8000/addr/";

    public static final String HDAC_URL = "http://10.10.0.116:3200/";

    //선불카드 URL
    public static final String CARD_URL = "http://coinp2pad.joy365.kr/External/MoaCard_Sign.asp";
    public static final String STORE_ID = "2568801099";

    // 계좌출금 URL
	public static final String WITHDROW_ACC = "http://coinp2pad.joy365.kr/External/AccTran.asp";

    //VISTA(VSTC) 코인 설정
    public static final String VSTC_CD = "CMMC00000001206";
    public static final String VSTC_URL = "http://10.10.0.60:3100/";
    public static final String VSTC_ACC = "NDNNI4DY4N54IHARMG3BQKC77YRPLMBYIHDTHH2V";
    public static final String VSTC_PWD = "d73cee8ee44ed34d9c23d51d07d3e33d1c5be2c5fd189851f70358c9e33ec8f1";


    //URL리스트
    public static final String ETH_URL_NEW 		= "http://10.10.0.72:8000/";
    public static final String HDAC_URL_NEW 	= "http://10.10.0.116:3200/";
    public static final String MIT_URL			= "http://10.10.0.72:3300/";


	/*클라이언트코드CMMP00000000047*/
	public static final String HTS_CD 						= "CMMC00000000221";//HTS
	public static final String MTS_CD 						= "CMMC00000000222";//MTS
	public static final String WEB_CD 						= "CMMC00000000223";//WEB
	
	/*통화코드CMMP00000000043*/
	public static final String BTC_CD 						= "CMMC00000000205";//비트코인
	public static final String ETH_CD 						= "CMMC00000000206";//이더리움
	public static final String XRP_CD 						= "CMMC00000000208";//리플
	public static final String BCH_CD 						= "CMMC00000000207";//비트코인캐시
	public static final String KRW_CD 						= "CMMC00000000204";//원화
	public static final String ADA_CD 						= "CMMC00000000447";//에이다
	public static final String ETC_CD 						= "CMMC00000000448";//이더리움클래식
	public static final String IOTA_CD 						= "CMMC00000000449";//아이오타
	public static final String OMG_CD 						= "CMMC00000000450";//오미세고
	public static final String QTUM_CD 						= "CMMC00000000451";//퀀텀
	public static final String SNT_CD 						= "CMMC00000000452";//스테이터스네크워크토큰
    public static final String SGC_CD 						= "CMMC00000000685";//SGC 코인
    public static final String USDT_CD                      = "CMMC00000001086";//USDT 코인
    public static final String HDAC_CD                      = "CMMC00000001126";//HDAC 코인
    public static final String BTG_CD 						= "CMMC00000000926";//비트코인골드 코인
    public static final String DASH_CD 						= "CMMC00000000927";//대시 코인
    public static final String LTC_CD 						= "CMMC00000000928";//라이트코인 코인
	public static final String SECRET_CD 					= "CMMC00000001066";//시크릿 코인


	//	임승연 추가
	public static final String BSV_CD						= "CMMC00000001506";//BSV 코인

	/*로그인 실패사유CMMP00000000046*/
	public static final String EMAIL_IS_NULL 				= "CMMC00000000215";//이메일 널값
	public static final String PWD_IS_NULL 					= "CMMC00000000216";//암호 널값
	public static final String EMAIL_NOT_FOUND 				= "CMMC00000000217";//없는 이메일
	public static final String PWD_NOT_POUND 				= "CMMC00000000218";//암호 틀림
	public static final String EMAIL_BLOCK 					= "CMMC00000000219";//블록된 이메일
	public static final String OTHER_LOGIN_FAIL				= "CMMC00000000220";//기타 로그인 실패
	public static final String IP_NOT_FOUND					= "CMMC00000000385";//등록되지 않은 아이피
	public static final String NOT_USE_ID					= "CMMC00000000386";//사용불가 처리 아이디
	public static final String PWD_CHG_YN					= "CMMC00000000790";//비밀번호 재설정 아이디
	public static final String PIN_IS_NULL					= "CMMC00000001167";//PIN 번호 널값
	public static final String PIN_NOT_POUND				= "CMMC00000001168";//PIN 번호 틀림
	public static final String PIN_REEST					= "CMMC00000001186";//PIN 번호 초기화된 사용자
	public static final String FINGER_DEVICE_IS_NULL		= "CMMC00000001366";//지문등록이 안된 기기
	public static final String FINGER_EMAIL_IS_NULL			= "CMMC00000001367";//지문등록이 안된 계정
	public static final String FINGER_NOT_FOUND				= "CMMC00000001368";//지문이 일치하지 않음

	/*거래구분코드CMMP00000000042*/		
	public static final String K_DEPOSIT_CD 				= "CMMC00000000196";//원화 입금	
	public static final String BUY_CD 						= "CMMC00000000197";//구매
	public static final String SELL_CD 						= "CMMC00000000198";//판매
	public static final String K_WITHDRAW_CD 				= "CMMC00000000199";//원화 출금	
	public static final String C_WITHDRAW_CD 				= "CMMC00000000200";//코인출금
	public static final String C_DEPOSIT_CD 				= "CMMC00000000201";//코인입금
	public static final String OTHER_TRADE_CD 				= "CMMC00000000202";//기타거래코드
	public static final String INNER_TRADE_CD 				= "CMMC00000000203";//거래소내부거래코드
	
	/*미결제구분코드CMMP00000000038*/
	public static final String BUY_WAIT_CD					= "CMMC00000000168";//구매대기	
	public static final String sel_WAIT_CD					= "CMMC00000000169";//판매대기	
	
	/*거래상태코드CMMP00000000045*/	
	public static final String TRADE_COMP					= "CMMC00000000213";//완료	
	public static final String TRADE_WAIT					= "CMMC00000000214";//처리중	

	/*그래프시간간격 CMMP00000000049*/
	public static final String CHART_1MIN					= "CMMC00000000241";//1분	
	public static final String CHART_5MIN					= "CMMC00000000242";//5분	
	public static final String CHART_10MIN					= "CMMC00000000243";//10분	
	public static final String CHART_30MIN					= "CMMC00000000244";//30분	
	public static final String CHART_60MIN					= "CMMC00000000245";//60분	
	public static final String CHART_1DAY					= "CMMC00000000246";//1일	
	public static final String CHART_1WEEK					= "CMMC00000000247";//1주
	
	/*실시간코인시세시간코드	CMMP00000000048	*/
	public static final String REALTIME_24					= "CMMC00000000224";//24시간	
	public static final String REALTIME_12					= "CMMC00000000225";//12시간	
	public static final String REALTIME_01					= "CMMC00000000226";//1시간
	
	/*주문구분CMMP00000000050*/
	public static final String LIMIT_PRICE					= "CMMC00000000249";//지정가	
	public static final String MARKET_PRICE					= "CMMC00000000250";//시장가
	
	/*거래내용코드CMMP00000000041*/
	public static final String KRW_DEPOSIT_ACCOUNT			= "CMMC00000000174";//원화충전(계좌)	
	public static final String KRW_DEPOSIT_TOSS				= "CMMC00000000175";//원화충전(토스)	
	public static final String KRW_DEPOSIT_GIFTCARD			= "CMMC00000000176";//원화충전(상품권)	
	public static final String KRW_DEPOSIT_EVENT			= "CMMC00000000177";//원화충전(이벤트)	
	public static final String BUY_LIMIT_PRICE				= "CMMC00000000178";//구매(지정가)	BTC구매(지정가)로 표시
	public static final String BUY_MARKET_PRICE				= "CMMC00000000179";//구매(시장가)	
	public static final String BUY_AUTO						= "CMMC00000000180";//구매(자동거래)	
	public static final String BUY_EXCHANGE					= "CMMC00000000181";//구매(코인교환)	
	public static final String SELL_LIMIT_PRICE				= "CMMC00000000182";//판매(지정가)	BTC판매(지정가)로 표시
	public static final String SELL_MARKET_PRICE			= "CMMC00000000183";//판매(시장가)	
	public static final String SELL_AUTO					= "CMMC00000000184";//판매(자동거래)	
	public static final String SELL_EXCHANGE				= "CMMC00000000185";//판매(코인교환)	
	public static final String KRW_WITHDRAW					= "CMMC00000000186";//원화출금	
	public static final String DEPOSIT_COIN					= "CMMC00000000187";//입금	BTC입금으로 표시
	public static final String WITHDRAW_COIN				= "CMMC00000000188";//송금	BTC송금으로 표시
	public static final String SERVICE_CHART				= "CMMC00000000189";//부가(시세알림)	부가서비스 신청
	public static final String SERVICE_FEE					= "CMMC00000000190";//부가(수수료쿠폰)	부가서비스 신청
	public static final String SERVICE_AUTO					= "CMMC00000000191";//부가(자동예약거래)	부가서비스 신청
	public static final String BLOCK_FEE_INNER				= "CMMC00000000192";//블록체인수수료	"거래구분"이 "거래소내부"일 경우
	public static final String EXCHANGE_SELL_INNER			= "CMMC00000000193";//거래소정산판매	"거래구분"이 "거래소내부"일 경우, 거래소정산용 코인판매
	public static final String EXCHANGE_WITHDRAW_INNER		= "CMMC00000000194";//거래소정산출금	"거래구분"이 "거래소내부"일 경우
	public static final String BANK_FEE_INNER				= "CMMC00000000195";//은행이체수수료	"거래구분"이 "거래소내부"일 경우
	
	public String makeCoinNm(String curcyCd) throws Exception {
		
		
		List<CoinListVO> list = tradeService.getCoinList();
		
		String curcyNm = "";
		
		for(CoinListVO vo : list) {
			if(curcyCd.equals(vo.getCurcyCd())) {
				curcyNm =  vo.getCurcyNm();
			}
		}
		
		return curcyNm;
	}
}
















