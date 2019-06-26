package com.bitkrx.config.dbinfo;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class CmeRoutingDataSource extends AbstractRoutingDataSource{
    @Override
    protected Object determineCurrentLookupKey() {
        // TODO Auto-generated method stub
        return DataContextHolder.getDataSourceType();
    }
}
