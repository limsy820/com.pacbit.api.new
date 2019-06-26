package com.bitkrx.config.dbinfo;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis 설정 빈
 *
 * com.bitkrx 패키지 이하에서 {@link com.bitkrx.Mapper}
 * 애노테이션이 선언된 클래스를 찾아 스프링 빈으로 등록한다.
 * 
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.bitkrx")
public class MybatisConfig {
    
    @Autowired
    DataSourceConfig dataSourceConfig;
    
    /**
     * myBatis의 {@link org.apache.ibatis.session.SqlSessionFactory}을 생성하는 팩토리빈을 등록한다.
     * @throws Exception 
     */
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory  getSqlSessionFactoryBean() throws Exception {
        
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        ApplicationContext appContext = new ClassPathXmlApplicationContext();
        
        // 마이바티스가 사용한 DataSource를 등록
        factoryBean.setDataSource(dataSourceConfig.multiDataSource());
        
        // 마이바티스 설정파일 위치 설정  
        factoryBean.setConfigLocation(appContext.getResource("classpath:/cmeconfig/sqlmap/mybatis/config/cme-config.xml"));

        // META-INF/mybatis/mappers 패키지 이하의 모든 XML을 매퍼로 등록
        factoryBean.setMapperLocations(appContext.getResources("classpath:/cmeconfig/sqlmap/mybatis/query/**/*.xml"));
        
        //applicationContext close
        ((ClassPathXmlApplicationContext) appContext).close();
        return factoryBean.getObject();
    }

    /**
     * 마이바티스 {@link org.apache.ibatis.session.SqlSession} 빈을 등록한다.
     *
     * SqlSessionTemplate은 SqlSession을 구현하고 코드에서 SqlSession를 대체하는 역할을 한다.
     * 쓰레드에 안전하게 작성되었기 때문에 여러 DAO나 매퍼에서 공유 할 수 있다.
     * @throws Exception 
     */  
 // mybatis sql template
    @Bean 
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
     
        return new SqlSessionTemplate(getSqlSessionFactoryBean());
    }
  
    
}
