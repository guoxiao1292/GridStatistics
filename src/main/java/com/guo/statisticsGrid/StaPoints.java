package com.guo.statisticsGrid;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @author: Gxy
 * @Date: 2018/12/25
 */
@Service
public class StaPoints {
    @JsonProperty
        private List<Sta> sta = new ArrayList<>();
        public void setSta(List<Sta> sta){
            this.sta = sta;
        }
        public List<Sta> getSta(){
            return this.sta;
        }
}
