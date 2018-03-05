package com.zlit.zlbaseapp.splash;

import android.view.View;
import android.widget.ImageView;

import com.privatee.mylibrary.Base.BaseActivity;
import com.zlit.zlbaseapp.R;
import com.zlit.zlbaseapp.tools.BaseHandlerUtils;
import com.zlit.zlbaseapp.tools.inter.ICallBack;
import com.zlit.zlbaseapp.tools.net.BaseNetWorkUtils;

public class SplashActivityNormal extends BaseActivity {
	private ImageView cover_bg;

	@Override
	public String setNowActivityName() {
		return "SplashActivityNormal";
	}

	@Override
	public int setLayout() {
		return R.layout.com_activity_splash_normal;
	}

	@Override
	public void inintView() {
		cover_bg = fvbi(R.id.cover_bg);
	}

	@Override
	public void inintData() {
    //版本校验
		checkVersion();
	}

	public void checkVersion(){
		boolean flag = BaseNetWorkUtils.isNetworkAvailable(this);
		if(!flag){
			//退出提示 或 游客身份进入
		}else{
			//版本控制
			//闪屏页 暂停5秒
			BaseHandlerUtils.delayed(5000, new ICallBack() {
				@Override
				public void execute() {
					showMain();
				}
			});
		}
	}

	private void showMain(){
		this.finish();
	}

	@Override
	public void onClick(View v) {

	}
}
