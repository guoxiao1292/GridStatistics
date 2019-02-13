package com.guo.statisticsGrid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author REN
 * @version 1.0
 * @title
 * @description
 * @createDate 2018/11/20
 */
@Component
@ConfigurationProperties(prefix = "mqtt")
public class ConstFiled {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPriceTopic() {
        return priceTopic;
    }

    public void setPriceTopic(String priceTopic) {
        this.priceTopic = priceTopic;
    }

    public Sta getSta() {
        return sta;
    }

    public void setSta(Sta sta) {
        this.sta = sta;
    }

    public String url;
    public String username;
    public String password;
    public String priceTopic;
    private Sta sta;
}
