package com.bitkrx.config.vo;

import java.io.Serializable;

public class DataSourceVO implements Serializable{
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 3080126694713574910L;
    
    private String Log4JdbcDriverClassName  = "net.sf.log4jdbc.DriverSpy";
    private String url              = "";
    private String username         = "";    
    private String password         = "";
    private String maxActive        = "5";
    private String maxIdle          = "2";
    private String maxWait          = "6000";
    private String minIdle          = "2";
    private String initialSize      = "2";
    private String testOnBorrow     = "true";
    private String numTestsPerEvictionRun   = "3";
    private String poolPreparedStatements   = "true";
    private String validationQuery          = "select 1 from dual";
    private String testWhileIdle            = "true";
    private String timeBetweenEvictionRunsMillis    = "7200000";
    
    
    /**
     * @return the log4JdbcDriverClassName
     */
    public String getLog4JdbcDriverClassName() {
        return Log4JdbcDriverClassName;
    }
    /**
     * @param log4JdbcDriverClassName the log4JdbcDriverClassName to set
     */
    public void setLog4JdbcDriverClassName(String log4JdbcDriverClassName) {
        Log4JdbcDriverClassName = log4JdbcDriverClassName;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the maxActive
     */
    public String getMaxActive() {
        return maxActive;
    }
    /**
     * @param maxActive the maxActive to set
     */
    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }
    /**
     * @return the maxIdle
     */
    public String getMaxIdle() {
        return maxIdle;
    }
    /**
     * @param maxIdle the maxIdle to set
     */
    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }
    /**
     * @return the maxWait
     */
    public String getMaxWait() {
        return maxWait;
    }
    /**
     * @param maxWait the maxWait to set
     */
    public void setMaxWait(String maxWait) {
        this.maxWait = maxWait;
    }
    /**
     * @return the minIdle
     */
    public String getMinIdle() {
        return minIdle;
    }
    /**
     * @param minIdle the minIdle to set
     */
    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }
    /**
     * @return the initialSize
     */
    public String getInitialSize() {
        return initialSize;
    }
    /**
     * @param initialSize the initialSize to set
     */
    public void setInitialSize(String initialSize) {
        this.initialSize = initialSize;
    }
    /**
     * @return the testOnBorrow
     */
    public String getTestOnBorrow() {
        return testOnBorrow;
    }
    /**
     * @param testOnBorrow the testOnBorrow to set
     */
    public void setTestOnBorrow(String testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }
    /**
     * @return the numTestsPerEvictionRun
     */
    public String getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }
    /**
     * @param numTestsPerEvictionRun the numTestsPerEvictionRun to set
     */
    public void setNumTestsPerEvictionRun(String numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }
    /**
     * @return the poolPreparedStatements
     */
    public String getPoolPreparedStatements() {
        return poolPreparedStatements;
    }
    /**
     * @param poolPreparedStatements the poolPreparedStatements to set
     */
    public void setPoolPreparedStatements(String poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }
    /**
     * @return the validationQuery
     */
    public String getValidationQuery() {
        return validationQuery;
    }
    /**
     * @param validationQuery the validationQuery to set
     */
    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }
    /**
     * @return the testWhileIdle
     */
    public String getTestWhileIdle() {
        return testWhileIdle;
    }
    /**
     * @param testWhileIdle the testWhileIdle to set
     */
    public void setTestWhileIdle(String testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }
    /**
     * @return the timeBetweenEvictionRunsMillis
     */
    public String getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }
    /**
     * @param timeBetweenEvictionRunsMillis the timeBetweenEvictionRunsMillis to set
     */
    public void setTimeBetweenEvictionRunsMillis(String timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }
    
    
    
}
