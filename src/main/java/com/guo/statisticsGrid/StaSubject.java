package com.guo.statisticsGrid;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @author: Gxy
 * @Date: 2019/1/10
 */
@Service
public class StaSubject implements Subject{
    /**
     * staTagsMap 表达式中的一组变量需要订阅的主题
     */
    private Set<String> topics = new HashSet<>() ;
    public void setTopics(Set<String> topics) {
        this.topics = topics;
    }

    /**
     * 存放观察者列表
     */
    ArrayList<Observer> staList;

    /**
     * 观察到的状态改变
     */
    boolean changed;

    String topic = "";
    String payLoad = "";

    StaSubject() {
        staList = new ArrayList<Observer>();
        changed = false;
    }

    @Override
    public void addObserver(Observer o) {
        if(!(staList.contains(o))){
            staList.add(o);
        }
    }

    @Override
    public void deleteObserver(Observer o) {
        if(staList.contains(o)){
            staList.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
        if(changed){
            for (Observer o:
            staList) {
                o.upDate(topic, payLoad);
            }
        }
    }

    public void getNewTop(String top, String payLoad) {
        if(!topics.contains(top)){
            changed = false;
        }
        else{
            this.topic = top;
            this.payLoad = payLoad;
            changed = true;
        }
    }
}
