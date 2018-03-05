package com.zlit.zlbaseapp.tools;

import android.os.Handler;

import com.zlit.zlbaseapp.tools.inter.ICallBack;

/**
 * Created by SongCaiBain on 2018/2/26.
 */

public class BaseHandlerUtils {
    /**
     * 延迟关闭
     * @param delayMillis 延迟时间
     * @param callBack 回调接口
     */
    public static void delayed(long delayMillis,final ICallBack callBack) {
        new Handler().postDelayed(new Runnable(){
            public void run() {
                if(callBack!=null) callBack.execute();
            }
        }, delayMillis);
    }
}
