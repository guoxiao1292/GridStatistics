package com.guo.statisticsGrid;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.aviator.AviatorEvaluator;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @Description 统计量计算
 * @author: Gxy
 * @Date: 2018/12/25
 */
@Slf4j
@Service
public class Statistics implements Observer{
    @Autowired
    private StaPoints staPoints;
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
    private Map<String, Map<String,Object>> staTagsMap= new HashMap<String, Map<String, Object>>();

    /**
     * staTagsMap 表达式中的一组变量需要订阅的主题
     */
    private Set<String> topics = new HashSet<>() ;

    public Set<String> getTopics() {
        return topics;
    }


    public void init(){
        //遍历所有表达式，表达式操作数中的所有变量
        for(int i=0; i<staPoints.getSta().size(); ++i){
            StringBuffer ex = new StringBuffer(staPoints.getSta().get(i).getEx());
            //表达式解析,变量以#包裹
            int pos = 0;
            int endPos;
            Map<String,Object> exTagsMap = new HashMap<String, Object>() ;
            while (-1!=(pos = ex.indexOf("#"))){
                endPos = ex.indexOf("#",pos+1);
                String var = ex.substring(pos+1, endPos);
                vars.put(var, new Object());
                exTagsMap.put(var, new Object());
                ex.delete(0, endPos+1);
                String topic = var.substring(0, var.lastIndexOf("_"));
                topics.add(topic.replace("_","/"));
            }
            staTagsMap.put(staPoints.getSta().get(i).getName(), exTagsMap);
        }
        staSubject.addObserver(this);
        staSubject.setTopics(topics);
    }


    //更新操作数变量数据
    @Override
    public void upDate(String top, String payLoad) {
        //解析数据
        ObjectMapper jackson = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = jackson.readTree(payLoad);
        } catch (IOException e) {
            log.error(payLoad);
            e.printStackTrace();
        }
        if(null == jsonNode){
            return ;
        }
        if(!jsonNode.has("rtData"))
        {
            return ;
        }
        JsonNode rtData = jsonNode.get("rtData");

        //遍历变量,更新val
        Iterator<Map.Entry<String, Object>> entries = vars.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            String topicT = entry.getKey().substring(0, entry.getKey().lastIndexOf("_"));
            String topic = topicT.replace("_","/");
            String field = entry.getKey().substring(entry.getKey().lastIndexOf("_")+1,entry.getKey().length());
            if(!topic.equals(top)){
                continue;
            }
            if (rtData.has(field)) {
                vars.replace(entry.getKey(),rtData.get(field).toString());
            }
        }
    }


    public void run() {
        JSONObject jsonValueOb = new JSONObject();
            loop:for(int i=0; i<staPoints.getSta().size(); ++i){
                //统计量名称
                String name = staPoints.getSta().get(i).getName();

                //统计量对应计算表达式，表达式中去掉配置里包裹符号
                String expression = staPoints.getSta().get(i).getEx().replace("#","");

                //表达式库编译
                com.googlecode.aviator.Expression compiledExp = AviatorEvaluator.compile(expression);

                //统计量对应表达式操作数变量。一个统计量name对应配置的多个操作数varss，分别获取值、替换表达式
                Map<String,Object> varss = staTagsMap.get(name);
                for (Map.Entry<String, Object> it:varss.entrySet()) {
                    Object v = vars.get(it.getKey());
                    //获取到变量的数据异常判断
                    if (0 == v.getClass().getFields().length){
                        log.debug("操作数{}获取数据错误！",it.getKey());
                        continue loop ;
                    }
                    Double f = Double.parseDouble((String)v);
                    it.setValue(f);
                }

                //一个统计量的结果
                jsonValueOb.put(name, compiledExp.execute(staTagsMap.get(name)));
            }
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
