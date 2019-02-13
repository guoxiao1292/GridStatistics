package com.guo.statisticsGrid;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @author: Gxy
 * @Date: 2018/12/25
 */
@Component
public class Sta {
    private String name;
    private String ex;
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setEx(String ex){
        this.ex = ex;
    }
    public String getEx(){
        return this.ex;
    }
}