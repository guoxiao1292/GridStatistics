package com.guo.statisticsGrid;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @author: Gxy
 * @Date: 2019/1/10
 */
@Service
public class PriceMonth {
    public List<Price> getPriceList() {
        return priceList;
    }

    @JsonProperty
    private List<Price> priceList = new ArrayList<>();
}
