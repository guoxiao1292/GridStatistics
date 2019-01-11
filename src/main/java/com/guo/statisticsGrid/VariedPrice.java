package com.guo.statisticsGrid;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author: Gxy
 * @Date: 2019/1/11
 */


public class VariedPrice  extends AbstractFunction {
    @Autowired
    private  YearPrice yearPrice;


    @Override
    public AviatorObject call(Map<String, Object> env) {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int hou = cal.get(Calendar.HOUR);
        List<Price> m = yearPrice.monthPriceMap.get(month).getPriceList();
        for (Price ppp : m) {
            if (ppp.getTime().contains(hou)) {
                return new AviatorDouble(ppp.getPrice());
            }
        }
        return  new AviatorDouble(0);
    }


    @Override
    public String getName() {
        return "Price";
    }
}
