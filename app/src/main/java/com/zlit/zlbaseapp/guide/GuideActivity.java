package com.zlit.zlbaseapp.guide;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.zlit.zlbaseapp.R;
import com.zlit.zlbaseapp.activity.IndexActivity;
import com.zlit.zlbaseapp.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class GuideActivity extends GuideActivityHorizontal {
    private boolean flag = false;

    @Override
    public List<View> getPageView() {
        List<View> views = new ArrayList<View>();
        int[] pics = {R.drawable.guide_duanhaoyindao_s1, R.drawable.guide_duanhaoyindao_s2};
        for (int i = 0; i < pics.length; i++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setBackgroundResource(pics[i]);
            views.add(layout);
        }
        View startView = View.inflate(this, R.layout.view_guide_start_app, null);
        views.add(startView);
        ImageView guide_go_login = (ImageView) startView.findViewById(R.id.guide_go_login);
        guide_go_login.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                goMain();
            }
        });

        return views;
    }

    @Override
    public OnPageChangeListener setOnPageChangeListener() {
        OnPageChangeListener listener = new OnPageChangeListener() {
            // 当新的页面被选中时调用
            @Override
            public void onPageSelected(int position) {
            }

            // 当当前页面被滑动时调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            // 当滑动状态改变时调用
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:// 正在滑行中
                        flag = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:// 目標加載完畢
                        flag = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:// 空閒
                        if (getViewPager().getCurrentItem() == getViewPager().getAdapter().getCount() - 1 && !flag) {
                            goMain();
                        }
                        flag = true;
                        break;
                }
            }
        };
        return listener;
    }

    public void goMain() {
        Intent intent = new Intent(this, IndexActivity.class);
        startActivity(intent);
        this.finish();
    }
}
