package com.example.testplugin;

import java.util.HashMap;

public class MethodLooker {

    private static HashMap<String, MethodModel> map = new HashMap<>();

    public static void noteStartTime(String methodName) {
        MethodModel methodModel = map.get(methodName);
        if (methodModel == null) {
            methodModel = new MethodModel(methodName);
        }
        methodModel.setMethodStartTime(System.currentTimeMillis());
        map.put(methodName, methodModel);
    }

    public static void noteEndTime(String methodName) {
        MethodModel methodModel = map.get(methodName);
        if (methodModel == null) {
            return;
        }
        methodModel.setMethodEndTime(System.currentTimeMillis());

        methodModel.getCostTime();
    }
}
