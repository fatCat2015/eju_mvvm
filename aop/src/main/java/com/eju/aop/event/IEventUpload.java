package com.eju.aop.event;

import org.json.JSONObject;

public interface IEventUpload {
    void uploadEvent(String eventCode, String event, String key);
}
