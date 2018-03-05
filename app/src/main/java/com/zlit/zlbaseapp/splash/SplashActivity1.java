package com.zlit.zlbaseapp.splash;

import android.content.Intent;
import android.view.View;

import com.privatee.mylibrary.Base.BaseActivity;
import com.zlit.zlbaseapp.R;
import com.zlit.zlbaseapp.guide.GuideActivity;
import com.zlit.zlbaseapp.tools.BaseHandlerUtils;
import com.zlit.zlbaseapp.tools.inter.ICallBack;

/**
 * Created by SongCaiBain on 2018/2/26.
 * 闪屏页
 * 一般在闪屏页进行版本更新
 */
public class SplashActivity1 extends BaseActivity{

    @Override
    public String setNowActivityName() {
        return "SplashActivity1";
    }

    @Override
    public int setLayout() {
        return R.layout.com_activity_splash_normal;
    }

    @Override
    public void inintView() {

    }

    @Override
    public void inintData() {
        //版本更新的代码
       // BaseAndroid.checkUpdate(this, 2, "http://192.168.1.62:8080/ZlEvaluation/updateTest.apk", "测试", true);
        BaseHandlerUtils.delayed(5000, new ICallBack() {
            @Override
            public void execute() {
                showMain();
            }
        });
    }
    private void showMain(){
       Intent intent=new Intent(this, GuideActivity.class);
       startActivity(intent);
       this.finish();
    }
    @Override
    public void onClick(View v) {

    }
}
