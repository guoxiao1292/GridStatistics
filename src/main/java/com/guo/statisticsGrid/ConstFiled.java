package com.guo.statisticsGrid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author REN
 * @version 1.0
 * @title
 * @description
 * @createDate 2018/11/20
 */
@Service
public class ConstFiled {
    @Value("${mqttServer.Url}")
    public String url;
    @Value("${mqttServer.Usr}")
    public String username;
    @Value("${mqttServer.Passwd}")
    public String password;
    @Value("${statisticsTopic}")
    public String staTopic;
    @Value("${statistics}")
    public String sta;
    @Value("${priceTopic}")
    public String priceTopic;
    @Value("${prices}")
    public String prices;
}
