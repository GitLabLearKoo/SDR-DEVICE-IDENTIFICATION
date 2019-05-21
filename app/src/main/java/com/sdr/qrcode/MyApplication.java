package com.sdr.qrcode;

import android.app.Application;

import com.sdr.lib.SDR_LIBRARY;

/**
 * Created by HyFun on 2019/05/20.
 * Email: 775183940@qq.com
 * Description:
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDR_LIBRARY.register(this, new ActivityConfig(getApplicationContext()));
    }
}
