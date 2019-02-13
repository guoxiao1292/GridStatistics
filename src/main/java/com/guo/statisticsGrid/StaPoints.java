package com.guo.statisticsGrid;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @author: Gxy
 * @Date: 2018/12/25
 */
@Component
@ConfigurationProperties(prefix = "statistics")
public class StaPoints {
    public String getStaTopic() {
        return staTopic;
    }

    public void setStaTopic(String staTopic) {
        this.staTopic = staTopic;
    }

    private String staTopic;
        private List<Sta> sta = new ArrayList<>();
        public void setSta(List<Sta> sta){
            this.sta = sta;
        }
        public List<Sta> getSta(){
            return this.sta;
        }
}
