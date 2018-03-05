package com.zlit.zlbaseapp;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.privatee.mylibrary.Base.BaseAndroid;
import com.privatee.mylibrary.Base.BaseConfig;

import org.xutils.x;

/**
 * Created by SongCaiBain on 2018/2/26.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //版本更新
        BaseConfig config=new BaseConfig();
        BaseAndroid.init(config);
        //任务控制中心, http, image, db, view注入等接口的入口.
        x.Ext.init(this);
        //必须调用初始化
        OkGo.init(this);
    }
}
