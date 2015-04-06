package com.coolweather.app.util;

/**
 * Created by xiaof_000 on 2015/4/6.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
