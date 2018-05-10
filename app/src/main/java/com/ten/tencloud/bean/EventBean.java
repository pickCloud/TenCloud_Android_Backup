package com.ten.tencloud.bean;

import java.util.LinkedHashMap;

/**
 * Created by lxq on 2018/5/10.
 */

public class EventBean {

    private String name;
    private LinkedHashMap<String, String> eventTime;
    private int level;//事件等级 0 常规 1 提醒 2 紧急
    private String source;
    private LinkedHashMap<String, String> effectObj;
    private LinkedHashMap<String, String> des;

    public String getName() {
        return name;
    }

    public EventBean setName(String name) {
        this.name = name;
        return this;
    }

    public LinkedHashMap<String, String> getEventTime() {
        return eventTime;
    }

    public EventBean setEventTime(LinkedHashMap<String, String> eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public EventBean setLevel(int level) {
        this.level = level;
        return this;
    }

    public String getSource() {
        return source;
    }

    public EventBean setSource(String source) {
        this.source = source;
        return this;
    }

    public LinkedHashMap<String, String> getEffectObj() {
        return effectObj;
    }

    public EventBean setEffectObj(LinkedHashMap<String, String> effectObj) {
        this.effectObj = effectObj;
        return this;
    }

    public LinkedHashMap<String, String> getDes() {
        return des;
    }

    public EventBean setDes(LinkedHashMap<String, String> des) {
        this.des = des;
        return this;
    }
}
