package com.zlit.zlbaseapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.zlit.zlbaseapp.R;
import com.zlit.zlbaseapp.activity.view.SmothSidingMenu;

/**
 * 一般的左右侧滑效果，记得要在AndroidManifest.xml文件进行Activity的配置
 */
public class MainActivity4 extends Activity
{ 
	private SmothSidingMenu smothSidingMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main4);
		smothSidingMenu=(SmothSidingMenu) findViewById(R.id.id_menu);
	}
	
	public void toggleMenu(View view){
		smothSidingMenu.toggle();
	}
	

}
