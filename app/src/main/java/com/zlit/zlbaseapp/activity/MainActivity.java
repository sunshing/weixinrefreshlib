package com.zlit.zlbaseapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zlit.zlbaseapp.R;
import com.zlit.zlbaseapp.activity.view.MoreWindow;
import com.zlit.zlbaseapp.fragment.IndexFragment;
import com.zlit.zlbaseapp.fragment.IndexFragment2;
import com.zlit.zlbaseapp.fragment.IndexFragment3;
import com.zlit.zlbaseapp.fragment.IndexFragment4;
import com.zlit.zlbaseapp.tools.BaseDialog;

/**
 * 仿微博底部菜单栏
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {


    private LinearLayout ll1, ll2, ll3, ll4;
    private TextView tv1, tv2, tv3, tv4;
    private ImageView iv1, iv2, iv3, iv4;

    private Fragment fragment = null;
    MoreWindow mMoreWindow;
   // private MainHandler mainHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.com_activity_main_frame);
     //   mainHandler = new MainHandler(this);
        initView();
    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv1);
        iv1 = (ImageView) findViewById(R.id.iv1);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll1.setOnClickListener(this);

        tv2 = (TextView) findViewById(R.id.tv2);
        iv2 = (ImageView) findViewById(R.id.iv2);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll2.setOnClickListener(this);

        tv3 = (TextView) findViewById(R.id.tv3);
        iv3 = (ImageView) findViewById(R.id.iv3);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll3.setOnClickListener(this);

        tv4 = (TextView) findViewById(R.id.tv4);
        iv4 = (ImageView) findViewById(R.id.iv4);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll4.setOnClickListener(this);
        LinearLayout llf = (LinearLayout) findViewById(R.id.llf);
        llf.bringToFront();
        llf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 仿微博底部弹出方式
                  showMoreWindow(v);

                //目前只需要选择图片发布文字方式一种
              /*  Intent intent = new Intent(MainActivity.this, PictrueActivity.class);
                startActivity(intent);*/

            }
        });

        //  mainHandler.sendMessage(XSYTool.makeNewMessage(ConstantUtil.INDEX, ""));
        //  fragment = new IndexFragment(mainHandler);
        fragment = new IndexFragment();
        goToFragment();
    }

    /**
     * 发布
     */
    private void showMoreWindow(View view) {
        if (null == mMoreWindow) {
            mMoreWindow = new MoreWindow(this);
            mMoreWindow.init();
        }
        mMoreWindow.showMoreWindow(view, 100);
    }


    /**
     * 发布文字
     */
    public void lunchText() {
        Toast.makeText(this,"发布文字",Toast.LENGTH_SHORT).show();
    }

    public void lunchPic() {
        Toast.makeText(this,"发布图片",Toast.LENGTH_SHORT).show();
    }

    public void lunchVideo() {
        Toast.makeText(this,"发布视频",Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll1:
                setll1();
                fragment = new IndexFragment();
                break;
            case R.id.ll2:
                setll2();
                fragment = new IndexFragment2();
                break;
            case R.id.ll3:
                setll3();
                fragment = new IndexFragment3();
                break;
            case R.id.ll4:
                setll4();
                fragment = new IndexFragment4();
                break;
            default:
                break;

        }
        goToFragment();
    }

    public void setll1() {
        iv1.setBackgroundResource(R.drawable.dt_press);
        iv2.setBackgroundResource(R.drawable.ms_unpress);
        iv3.setBackgroundResource(R.drawable.mes_unpress);
        iv4.setBackgroundResource(R.drawable.my_unpress);
        tv1.setTextColor(this.getResources().getColor(R.color.colorBlue));
        tv2.setTextColor(this.getResources().getColor(R.color.colorDark));
        tv3.setTextColor(this.getResources().getColor(R.color.colorDark));
        tv4.setTextColor(this.getResources().getColor(R.color.colorDark));
    }

    public void setll2() {
        iv2.setBackgroundResource(R.drawable.ms_press);
        iv1.setBackgroundResource(R.drawable.dt_unpress);
        iv3.setBackgroundResource(R.drawable.mes_unpress);
        iv4.setBackgroundResource(R.drawable.my_unpress);
        tv2.setTextColor(this.getResources().getColor(R.color.colorBlue));
        tv1.setTextColor(this.getResources().getColor(R.color.colorDark));
        tv3.setTextColor(this.getResources().getColor(R.color.colorDark));
        tv4.setTextColor(this.getResources().getColor(R.color.colorDark));
    }

    public void setll3() {
        iv3.setBackgroundResource(R.drawable.mes_press);
        iv1.setBackgroundResource(R.drawable.dt_unpress);
        iv2.setBackgroundResource(R.drawable.ms_unpress);
        iv4.setBackgroundResource(R.drawable.my_unpress);
        tv3.setTextColor(this.getResources().getColor(R.color.colorBlue));
        tv1.setTextColor(this.getResources().getColor(R.color.colorDark));
        tv2.setTextColor(this.getResources().getColor(R.color.colorDark));
        tv4.setTextColor(this.getResources().getColor(R.color.colorDark));
    }

    public void setll4() {
        iv4.setBackgroundResource(R.drawable.my_press);
        iv1.setBackgroundResource(R.drawable.dt_unpress);
        iv2.setBackgroundResource(R.drawable.ms_unpress);
        iv3.setBackgroundResource(R.drawable.mes_unpress);
        tv4.setTextColor(this.getResources().getColor(R.color.colorBlue));
        tv1.setTextColor(this.getResources().getColor(R.color.colorDark));
        tv2.setTextColor(this.getResources().getColor(R.color.colorDark));
        tv3.setTextColor(this.getResources().getColor(R.color.colorDark));
    }

    private void goToFragment() {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameMain, fragment, "frameMain");
            transaction.commit();
        }
    }

}
