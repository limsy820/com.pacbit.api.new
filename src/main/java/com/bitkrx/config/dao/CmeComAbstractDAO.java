package com.bitkrx.config.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.bitkrx.config.logging.CmeCommonLogger;
import com.bitkrx.config.util.MapObjConvertUtil;
import com.bitkrx.config.util.SessionUtil;

public class CmeComAbstractDAO extends SqlSessionDaoSupport{
    
    protected CmeCommonLogger log = new CmeCommonLogger(this.getClass());
    protected SessionUtil sUtil = SessionUtil.getinstance();
    
       /**
     * DmAbstractDAO 는 base class 로만 사용되며 해당 인스턴스를 직접
     * 생성할 수 없도록 protected constructor 로 선언하였음.
     */
    protected CmeComAbstractDAO() {
        // PMD abstract Rule - If the class is intended
        // to be used as a base class only (not to be
        // instantiated
        // directly)
        // a protected constructor can be provided
        // prevent direct instantiation
    }   

    protected void debugLog(String msg){
        log.DebugLog(msg);
    }
    
    /**
     * Annotation 형식으로 sqlMapClient 를 받아와 이를
     * super(SqlMapClientDaoSupport) 의 setSqlMapClient
     * 메서드를 호출하여 설정해 준다.
     * @param sqlMapClient
     *        - ibatis 의 SQL Map 과의 상호작용을 위한 기본 클래스로
     *        mapped statements(select, insert, update,
     *        delete 등) 의 실행을 지원함.
     */
    //@Resource(name = "sqlMapClient")
    @Resource(name = "sqlSessionFactory")    
    public void setSuperSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    /**
     * 입력 처리 SQL mapping 을 실행한다.
     * @param queryId
     *        - 입력 처리 SQL mapping 쿼리 ID
     * @param parameterObject
     *        - 입력 처리 SQL mapping 입력 데이터를 세팅한 파라메터
     *        객체(보통 VO 또는 Map)
     * @return 입력 시 selectKey 를 사용하여 key 를 딴 경우 해당 key
     */
    public Object insert(String queryId, Object parameterObject) {
        return getSqlSession().insert(queryId, parameterObject);
    }

    /**
     * 수정 처리 SQL mapping 을 실행한다.
     * @param queryId
     *        - 수정 처리 SQL mapping 쿼리 ID
     * @param parameterObject
     *        - 수정 처리 SQL mapping 입력 데이터(key 조건 및 변경
     *        데이터)를 세팅한 파라메터 객체(보통 VO 또는 Map)
     * @return DBMS가 지원하는 경우 update 적용 결과 count
     */
    public int update(String queryId, Object parameterObject) {
        return getSqlSession().update(queryId, parameterObject);
    }

    /**
     * 삭제 처리 SQL mapping 을 실행한다.
     * @param queryId
     *        - 삭제 처리 SQL mapping 쿼리 ID
     * @param parameterObject
     *        - 삭제 처리 SQL mapping 입력 데이터(일반적으로 key 조건)를
     *        세팅한 파라메터 객체(보통 VO 또는 Map)
     * @return DBMS가 지원하는 경우 delete 적용 결과 count
     */
    public int delete(String queryId, Object parameterObject) {
        return getSqlSession().delete(queryId, parameterObject);
    }

    /**
     * pk 를 조건으로 한 단건조회 처리 SQL mapping 을 실행한다.
     * @param queryId
     *        - 단건 조회 처리 SQL mapping 쿼리 ID
     * @param parameterObject
     *        - 단건 조회 처리 SQL mapping 입력 데이터(key)를 세팅한
     *        파라메터 객체(보통 VO 또는 Map)
     * @return 결과 객체 - SQL mapping 파일에서 지정한
     *         resultClass/resultMap 에 의한 단일 결과 객체(보통
     *         VO 또는 Map)
     */
    public Object selectByPk(String queryId, Object parameterObject) {
        return getSqlSession().selectOne(queryId,
            parameterObject);
    }

    /**
     * 리스트 조회 처리 SQL mapping 을 실행한다.
     * @param queryId
     *        - 리스트 조회 처리 SQL mapping 쿼리 ID
     * @param parameterObject
     *        - 리스트 조회 처리 SQL mapping 입력 데이터(조회 조건)를
     *        세팅한 파라메터 객체(보통 VO 또는 Map)
     * @return 결과 List 객체 - SQL mapping 파일에서 지정한
     *         resultClass/resultMap 에 의한 결과 객체(보통 VO
     *         또는 Map)의 List
     */
    @SuppressWarnings("unchecked")
    public List list(String queryId, Object parameterObject) {
        return getSqlSession().selectList(queryId, parameterObject);
    }
    
    
    /**
     * vo를 param으로 넘겨서 맵으로 변환 후 프로시저 실행 후 결과값 + 기존값을 다시 vo형태로 변환하여 반환한다.
     * @param queryId
     *        - 프로시저 처리 SQL mapping 쿼리 ID
     * @param parameterObject
     *        - 프로시저 처리 SQL mapping 입력 데이터(조회 조건)를
     *        세팅한 파라메터 객체(VO)
     * @return param, return 값을 모두 가지고 있는 VO, 
     * 		   단 VO에 프로시저 return값과 동일한 이름의 setter 가 존재해야함
     */ 
    public Object convertProc(String queryId, Object parameterObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Map<String, Object> map = MapObjConvertUtil.convertObjectToMap(parameterObject);
    	update(queryId, map);
    	return MapObjConvertUtil.convertMapToObject(map, parameterObject);
    }

}
