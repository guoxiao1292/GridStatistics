package com.guo.statisticsGrid;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import java.util.Map;


/**
 * @Description //当前收益=∑(g(t2)-g(t1))*price
 * @author: Gxy
 * @Date: 2019/1/8
 */
public class GenerateProfit extends AbstractFunction {
    private double genProfit = 0f;
    private double lastEnergy = 0f;
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3) {
        Double fanEnergy = FunctionUtils.getNumberValue(arg1, env).doubleValue();
        Double sunEnergy = FunctionUtils.getNumberValue(arg2, env).doubleValue();
        Double price = FunctionUtils.getNumberValue(arg3, env).doubleValue();
        double nowEnergy = fanEnergy+sunEnergy;
        genProfit += (nowEnergy-lastEnergy)*price;
        lastEnergy = nowEnergy;
        return new AviatorDouble(genProfit);
    }

    @Override
    public String getName() {
        return "generateProfit";
    }
}