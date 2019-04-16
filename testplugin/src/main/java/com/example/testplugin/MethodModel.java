package com.example.testplugin;

public class MethodModel {
    private String methodName;
    private long methodStartTime;
    private long methodEndTime;

    public MethodModel(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getMethodStartTime() {
        return methodStartTime;
    }

    public void setMethodStartTime(long methodStartTime) {
        this.methodStartTime = methodStartTime;
    }

    public long getMethodEndTime() {
        return methodEndTime;
    }

    public void setMethodEndTime(long methodEndTime) {
        this.methodEndTime = methodEndTime;
    }

    public void getCostTime() {
        System.out.println("cost time：" + (getMethodEndTime() - getMethodStartTime()));
    }
}
