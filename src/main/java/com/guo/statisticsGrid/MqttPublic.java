package com.guo.statisticsGrid;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Slf4j
/**
 * @Description
 * @author: Gxy
 * @Date: 2018/11/6
 */
@Component
public class MqttPublic implements Runnable{
    public MqttAsyncClient getClient() {
        return client;
    }

    private MqttAsyncClient client = null;
    private MqttConnectOptions connOpts = new MqttConnectOptions();
    @Autowired
    private ConstFiled constFiled;
    @Autowired
    private Statistics statistics;
    @Autowired
    private StaSubject staSubject;

    @PostConstruct
    public void init() {
        //观察者初始化
        statistics.init();

        //mqtt 客户端
        connOpts.setKeepAliveInterval(20);
        connOpts.setCleanSession(true);
        connOpts.setAutomaticReconnect(true);
        connOpts.setConnectionTimeout(5);
        connOpts.setUserName(constFiled.username);
        connOpts.setPassword(constFiled.password.toCharArray());
        try {
            client = new MqttAsyncClient(constFiled.url, MqttClient.generateClientId());
            Callback ck = new Callback();
            client.setCallback(ck);
            client.connect(connOpts).waitForCompletion();
            System.out.println(client.isConnected());
            for (String topic: statistics.getTopics()) {
                client.subscribe(topic, 1);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }

        Thread r = new Thread(this);
        r.start();
    }


    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true){
            staSubject.notifyObservers();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * class_name: CreateMqClient
     *
     * @author Gxy
     * @version 0.0.0
     * @date 8:52 2018/10/16
     * @description 内部类，实现MqttCallback接口,连接丢失会自动重连、消息到来存储
     */
    class Callback implements MqttCallback {//, IMqttActionListener

        @Override
        public void connectionLost(Throwable cause) {

            System.out.println(cause.getMessage());
            log.error("connectionLost");
        }


        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            String pay = message.toString();
            staSubject.getNewTop(topic, pay);
        }


        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }


        // This deomonstrates manually reconnecting to the broker by calling
        // connect() again. This is a possibility for an application that keeps
        // a copy of it's original connect_options, or if the app wants to
        // reconnect with different options.
        // Another way this can be done manually, if using the same options, is
        // to just call the async_client::reconnect() method.

        public Callback() {

        }
    }
}


