package com.guo.statisticsGrid;

import com.googlecode.aviator.AviatorEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    private MqttPublic mqttPublic;
    @Override
    public void run(String... args) throws Exception {
        mqttPublic.init();

//        String name1 = "$ren_saida_ctrl_rtData_agen$";
//        String name2 = "$ren_saida_ctrl_rtData_gen$";
//        String expression = "1.03*(" + name1 + "-math.sin(" + name2 + "))";
//        System.out.println(expression);
//        com.googlecode.aviator.Expression compiledExp = AviatorEvaluator.compile(expression);
//        Map<String, Object> env = new HashMap<>();
//        Thread thread = new Thread(() -> {
//            while (true) {
//                env.put(name1, new Random().nextDouble() + 10);
//                env.put(name2, new Random().nextDouble() + 10);
//                Double result = (Double) compiledExp.execute(env);
//                System.out.println(result);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }             }         });
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
