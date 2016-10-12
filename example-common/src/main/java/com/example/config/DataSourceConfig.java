package com.example.config;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Created by binglin on 2016/10/12.
 */
@Configuration
public class DataSourceConfig {

    @Inject
    private DataSourceSetting dataSourceSetting;

    public DataSourceSetting getDataSourceSetting() {
        return dataSourceSetting;
    }

    public void setDataSourceSetting(DataSourceSetting dataSourceSetting) {
        this.dataSourceSetting = dataSourceSetting;
    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(dataSourceSetting.getUserName());
        dataSource.setUrl(dataSourceSetting.getUrl());
        dataSource.setPassword(dataSourceSetting.getPassword());
        dataSource.setDriverClassName(dataSourceSetting.getDriverClassName());
        return dataSource;
    }

    @ConfigurationProperties(prefix = "spring.datasource")
    @Component
    static class DataSourceSetting {

        private String url;

        private String userName;

        private String password;

        private String driverClassName;

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
