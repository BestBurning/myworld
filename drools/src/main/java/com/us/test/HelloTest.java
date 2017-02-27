package com.us.test;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

/**
 * @author Bruce
 * @date 2017/1/4
 */
public class HelloTest {

    public static void main(String[] args) {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();



    }

}
