package com.zlit.zlbaseapp.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import com.zlit.zlbaseapp.R;
import com.zlit.zlbaseapp.activity.view.SlidingMenu;

/**
 * 仿QQ5.0侧滑效果
 */
public class MainActivity3 extends Activity {

    private SlidingMenu mLeftMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main3);
        mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
    }

    public void toggleMenu(View view) {
        mLeftMenu.toggle();
    }


}
