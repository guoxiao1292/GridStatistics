package com.guo.statisticsGrid;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @author: Gxy
 * @Date: 2019/1/10
 */
public class Price {
    public List<Integer> getTime() {
        return time;
    }

    public void setTime(List<Integer> time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty
    private List<Integer> time = new ArrayList<>();
    @JsonProperty
    private double price;
    @JsonProperty
    private String type;
}
