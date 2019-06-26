package com.bitkrx.config.dbinfo;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bitkrx.config.annotation.CommonDataSource;
import com.bitkrx.config.logging.CmeCommonLogger;

import javax.servlet.ServletContext;

@Aspect
@Component
@Order(value=1)
public class CmeDbExcutionLoggingAspect implements InitializingBean{
    protected CmeCommonLogger log = new CmeCommonLogger(this.getClass());

    @Autowired
    private ServletContext servletContext;

    @Around("execution(* kr.sidnancy..*Service.*(..))")
    public Object doServiceProfiling(ProceedingJoinPoint joinPoint) throws Throwable{
        
        log.DebugLog("=======================CmeDbExcutionLoggingAspect start============================");
        String strlog = "DataSource Binding Start|";
        final String methodName = joinPoint.getSignature().getName();
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        
        if(method.getDeclaringClass().isInterface()){
            method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
        }
        //Annotation을 가져온다.
        CommonDataSource dataSource = null;         
        try {
            dataSource = (CommonDataSource) method.getAnnotation(CommonDataSource.class);           
        } catch (Exception e) {
            //log.ViewErrorLog("Error:"+e.getMessage());
        }

        String path = servletContext.getRealPath("/");
        path += "WEB-INF/classes/cmeconfig/CmeProps";
        //System.out.println(path);
        Properties props = new Properties();
        FileInputStream fis  = new FileInputStream(path + "/dataStatus.properties");
        props.load(new java.io.BufferedInputStream(fis));
        String status = props.getProperty("status").trim();

        if(dataSource != null){
            //Method에 해당 dataSource관련 설정이 있을 경우 해당 dataSource의 value를 읽어 들인다.
            log.DebugLog("====>dataSource ::"+dataSource.value());
            DataContextHolder.setDataSourceType(dataSource.value());
            if(dataSource.value() == DataSourceType.OPRBOARD && "0".equals(status)) {//보드 운영
                DataContextHolder.setDataSourceType(DataSourceType.OPRBOARD);
            } else if(dataSource.value() == DataSourceType.OPRBOARD && "1".equals(status)) {//보드 스테이징
                DataContextHolder.setDataSourceType(DataSourceType.STABOARD);
            } else if(dataSource.value() == DataSourceType.OPRBOARD && "2".equals(status)) {//보드 개발
                DataContextHolder.setDataSourceType(DataSourceType.DEVBOARD);
            } else if(dataSource.value() == DataSourceType.MKETH && "0".equals(status)) {//이더마켓 운영
                DataContextHolder.setDataSourceType(DataSourceType.MKETH);
            } else if(dataSource.value() == DataSourceType.MKETH && "1".equals(status)) {//이더마켓 스테이징
                DataContextHolder.setDataSourceType(DataSourceType.MKETH_STA);
            } else if(dataSource.value() == DataSourceType.MKUSDT && "0".equals(status)) {//테더마켓 운영
                DataContextHolder.setDataSourceType(DataSourceType.MKUSDT);
            } else if(dataSource.value() == DataSourceType.MKUSDT && "1".equals(status)) {//테더마켓 스테이징
                DataContextHolder.setDataSourceType(DataSourceType.MKUSDT_STA);
            } else if(dataSource.value() == DataSourceType.MKBTC && "0".equals(status)) {//BTC마켓 운영
                DataContextHolder.setDataSourceType(DataSourceType.MKBTC);
            } else if(dataSource.value() == DataSourceType.MKBTC && "1".equals(status)) {//BTC마켓 스테이징
                DataContextHolder.setDataSourceType(DataSourceType.MKBTC_STA);
            }
        } else if("0".equals(status)){//운영서버일 결우
            DataContextHolder.setDataSourceType(DataSourceType.OPRBIT);

        } else if("1".equals(status)){//staging일 결우
            DataContextHolder.setDataSourceType(DataSourceType.STABIT);

        } else if("2".equals(status)){//dev일 결우
            DataContextHolder.setDataSourceType(DataSourceType.DEVBIT);

        } else {
            //DataSource 기본값세팅
            DataContextHolder.setDataSourceType(DataSourceType.OPRBIT);
            //따로 annotation으로 datasource를 지정하지 않은 경우에는 메소드 이름으로 판단 - 주석처리 일단 무조건 테스트DB를 바라봄
/*              if(!(method.getName().startsWith("get") || method.getName().startsWith("select"))){
                    DataContextHolder.setDataSourceType(DataSourceType.LOCAL);
                }*/
            //DataContextHolder.setDataSourceType(DataSourceType.LOCAL);
        }
        strlog += "Bind DataSource #####===>" + DataContextHolder.getDataSourceType();
        log.DebugLog(strlog);

        Object returnValue = joinPoint.proceed();
        log.DebugLog("returnValue:::"+ returnValue);
        DataContextHolder.clearDataSourceType();
        
        return returnValue;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
    }


}
