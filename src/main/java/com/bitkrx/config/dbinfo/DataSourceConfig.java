package com.bitkrx.config.dbinfo;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.bitkrx.constant.DBInfoConstant;

@Configuration
@Component
public class DataSourceConfig {

    @Bean
    public CmeRoutingDataSource multiDataSource() {
        CmeRoutingDataSource cliDataSource = new CmeRoutingDataSource();
        Map<Object, Object> targetDataSource = new HashMap<Object, Object>();

        targetDataSource.put(DataSourceType.OPRBIT, oprbit());
        targetDataSource.put(DataSourceType.STABIT, stabit());
        targetDataSource.put(DataSourceType.DEVBIT, devbit());
        targetDataSource.put(DataSourceType.OPRBOARD, oprBoard());
        targetDataSource.put(DataSourceType.STABOARD, staBoard());
        targetDataSource.put(DataSourceType.DEVBOARD, devBoard());
        targetDataSource.put(DataSourceType.BLOCKCHAIN, blockChain());
        targetDataSource.put(DataSourceType.MKETH, MKETH());
        targetDataSource.put(DataSourceType.MKETH_STA, MKETH_STA());
        targetDataSource.put(DataSourceType.MKUSDT, MKUSDT());
        targetDataSource.put(DataSourceType.MKUSDT_STA, MKUSDT_STA());
        targetDataSource.put(DataSourceType.MKBTC, MKBTC());
        targetDataSource.put(DataSourceType.MKBTC_STA, MKBTC_STA());
        cliDataSource.setTargetDataSources(targetDataSource);

        cliDataSource.setDefaultTargetDataSource(oprbit());
        return cliDataSource;
    }

    @Bean(name = "oprBoard", destroyMethod = "close")
    public DataSource oprBoard() {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);

        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=10.10.20.85)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=PLANBITORCL)))");
        dataSource.setUsername("PLANBITBOARD");
        dataSource.setPassword("PLANBIT3838!");

        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }

    @Bean(name = "staBoard", destroyMethod = "close")
    public DataSource staBoard() {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);

        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=61.97.253.140)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=DEVKREXORCL)))");
        dataSource.setUsername("PLANBITBOARD_NEW");
        dataSource.setPassword("PLANBIT3838!");

        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }

    @Bean(name = "devBoard", destroyMethod = "close")
    public DataSource devBoard() {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);

        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=222.239.119.238)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=DEVKREXORCL)))");
        dataSource.setUsername("PLANBITBOARD_NEW");
        dataSource.setPassword("PLANBIT3838!");

        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }

    @Bean(name = "oprbit", destroyMethod = "close")
    public DataSource oprbit() {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);

        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=61.97.253.85)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=PLANBITORCL)))");
        dataSource.setUsername("PLANBIT");
        dataSource.setPassword("PLANBIT3838!");

        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }

    @Bean(name = "stabit", destroyMethod = "close")
    public DataSource stabit() {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);
        /*dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=180.70.92.35)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=DEVKREXORCL)))");
        dataSource.setUsername("PLANBIT");
        dataSource.setPassword("PLANBIT3838!");*/

        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=61.97.253.141)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=KREXORCL)))");
        dataSource.setUsername("APLANBIT");
        dataSource.setPassword("APLANBIT3838!");

        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }

    @Bean(name = "devbit", destroyMethod = "close")
    public DataSource devbit() {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);
        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=61.97.253.140)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=DEVKREXORCL)))");
        dataSource.setUsername("PLANBIT_NEW");
        dataSource.setPassword("PLANBIT3838!");

        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }

    @Bean(name = "blockChain", destroyMethod = "close")
    public DataSource blockChain() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);
        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=222.239.119.230)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=KREXORCL)))");
        dataSource.setUsername("BLOCKCHAIN");
        dataSource.setPassword("BLOCKCHAIN3838!");

        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }


    @Bean(name = "MKETH", destroyMethod = "close")
    public DataSource MKETH() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);
        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=61.97.253.85)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=PLANBITORCL)))");
        dataSource.setUsername("MK_ETH");
        dataSource.setPassword("MK_ETH3838!");

        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }

    @Bean(name = "MKETH_STA", destroyMethod = "close")
    public DataSource MKETH_STA() {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);

        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=61.97.253.141)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=KREXORCL)))");
        dataSource.setUsername("MK_ETH_AP");
        dataSource.setPassword("MK_ETH3838!");

        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }

    @Bean(name = "MKUSDT", destroyMethod = "close")
    public DataSource MKUSDT() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);
        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=61.97.253.85)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=PLANBITORCL)))");
        dataSource.setUsername("MK_USDT");
        dataSource.setPassword("MK_USDT3838!");
        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }

    @Bean(name = "MKUSDT_STA", destroyMethod = "close")
    public DataSource MKUSDT_STA() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);
        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=61.97.253.141)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=KREXORCL)))");
        dataSource.setUsername("MK_USDT_AP");
        dataSource.setPassword("MK_USDT3838!");

        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }


    @Bean(name = "MKBTC", destroyMethod = "close")
    public DataSource MKBTC() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);
        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=61.97.253.85)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=PLANBITORCL)))");
        dataSource.setUsername("MK_BTC");
        dataSource.setPassword("MK_BTC3838!");
        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }

    @Bean(name = "MKBTC_STA", destroyMethod = "close")
    public DataSource MKBTC_STA() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DBInfoConstant.Log4JdbcDriverClassName);
        dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=61.97.253.141)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=KREXORCL)))");
        dataSource.setUsername("MK_BTC_AP");
        dataSource.setPassword("MK_BTC3838!");

        dataSource.setValidationQuery(DBInfoConstant.validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setNumTestsPerEvictionRun(20);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        return dataSource;
    }
}
