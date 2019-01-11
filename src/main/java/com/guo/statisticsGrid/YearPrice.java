package com.guo.statisticsGrid;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @author: Gxy
 * @Date: 2019/1/10
 */
@Service
public class YearPrice {
    @JsonProperty
    Map<Integer,PriceMonth> monthPriceMap=new HashMap<>();
}
