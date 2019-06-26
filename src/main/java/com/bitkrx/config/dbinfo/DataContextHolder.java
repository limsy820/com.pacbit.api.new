package com.bitkrx.config.dbinfo;

public class DataContextHolder {
    private static final ThreadLocal<DataSourceType> ctxHolder = new ThreadLocal<DataSourceType>();

    public static void setDataSourceType(DataSourceType dataSourceType){
        ctxHolder.set(dataSourceType);
    }
    
    public static DataSourceType getDataSourceType(){
        return ctxHolder.get();
    }
    
    public static void clearDataSourceType(){
        ctxHolder.remove();
    }

}
