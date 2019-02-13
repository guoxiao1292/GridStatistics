package com.guo.statisticsGrid;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.aviator.AviatorEvaluator;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @Description 价格更新
 * @author: Gxy
 * @Date: 2019/1/10
 */
@Slf4j
@Service
public class PriceParams implements Observer{
    @Autowired
    private StaPoints staPoints;
    @Autowired
    private ConstFiled sta;
    @Autowired
    private YearPrice yearPrice;
    @Autowired
    private StaSubject staSubject;
    @Autowired
    private MqttPublic Mqtt;
    /**
     * vars 所有统计量的表达式中所有变量表，其中key：表达式中变量，value：对应的值，从mqtt获取
     */
    private Map<String, Object> vars = new HashMap<String, Object>();

    /**
     * staTagsMap 发布信息表。key:统计tag（co2Reduction）；val：表达式中的一组变量及value
     */
    private StringBuffer priceEx;

    /**
     * staTagsMap 表达式中的一组变量需要订阅的主题
     */
    private Set<String> topics = new HashSet<>() ;

    public Set<String> getTopics() {
        return topics;
    }
    String var;
    Object val;
    String tag;
    double tagVal;

    public void init(){
        priceEx = new StringBuffer(sta.getSta().getEx());
        Map<String,Object> exTagsMap = new HashMap<String, Object>() ;
        int pos = 0;
        int endPos = 0;
        while (-1!=(pos = priceEx.indexOf("#"))){
            endPos = priceEx.indexOf("#",pos+1);
            var = priceEx.substring(pos+1, endPos);
            priceEx.delete(0, endPos+1);
            String topic = var.substring(0, var.lastIndexOf("_"));
            topics.add(topic.replace("_","/"));
        }
        tag = sta.getSta().getName();
        staSubject.addObserver(this);
        staSubject.setTopics(topics);
    }


    @Override
    public void upDate(String top, String payLoad) {
        //解析数据
        ObjectMapper jackson = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            yearPrice = (YearPrice) jackson.readValue(payLoad, YearPrice.class);
            jsonNode = jackson.readTree(payLoad);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void priceRun(){
        String expression = "Price()";
        com.googlecode.aviator.Expression compiledExp = AviatorEvaluator.compile(expression);
        JSONObject jsonValueOb = new JSONObject();
        jsonValueOb.put(tag, compiledExp.execute());
        //发布
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("rtData", jsonValueOb);
        if (Mqtt.getClient().isConnected()){
            try {
                Mqtt.getClient().publish(staPoints.getStaTopic(), jsonPayload.toString().getBytes(), 1, false);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
}
