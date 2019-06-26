package com.bitkrx.config.dbinfo;

import java.util.Collections;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;


@Aspect
@Configuration
public class CmeDbTransaction {
    
    @Autowired
    DataSourceConfig dataSourceConfig;
 
    
    @Bean
    public DataSourceTransactionManager txManager() {
        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
        dstm.setDataSource(dataSourceConfig.multiDataSource());
        
        return dstm;
    }
    
    @Bean
    public TransactionInterceptor txAdvice() {
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute transactionAttriubte = new RuleBasedTransactionAttribute();
        transactionAttriubte.setName("*");
        transactionAttriubte.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        source.setTransactionAttribute(transactionAttriubte);
        TransactionInterceptor txAdvice = new TransactionInterceptor(txManager(),source);
        
        return txAdvice;
    }
    
    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.bitkrx..*Impl.*(..)) ");
        return new DefaultPointcutAdvisor(pointcut,txAdvice());
    }

}
