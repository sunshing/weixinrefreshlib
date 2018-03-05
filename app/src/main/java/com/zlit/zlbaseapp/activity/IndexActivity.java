package com.zlit.zlbaseapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.privatee.mylibrary.Base.BaseActivity;
import com.zlit.zlbaseapp.R;
import com.zlit.zlbaseapp.tools.BaseDialog;

import weixinrefresh.HeadLargenMainActivity;

/**
 * Created by SongCaiBain on 2018/2/26.
 */

public class IndexActivity extends BaseActivity {
    @Override
    public String setNowActivityName() {
        return "IndexActivity";
    }

    @Override
    public int setLayout() {
        return R.layout.activity_index;
    }

    @Override
    public void inintView() {
        TextView tv1 = fvbi(R.id.tv1);
        TextView tv2 = fvbi(R.id.tv2);
        TextView tv3 = fvbi(R.id.tv3);
        TextView tv4 = fvbi(R.id.tv4);
        TextView tv5 = fvbi(R.id.tv5);
        TextView tv6 = fvbi(R.id.tv6);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);

    }

    @Override
    public void inintData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv1:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.tv2:
                intent = new Intent(this, MainActivity2.class);
                startActivity(intent);
                break;
            case R.id.tv3:
                intent = new Intent(this, MainActivity3.class);
                startActivity(intent);
                break;
            case R.id.tv4:
                intent = new Intent(this, MainActivity4.class);
                startActivity(intent);
                break;
            case R.id.tv5:
                intent = new Intent(this, MainActivity5.class);
                startActivity(intent);
                break;
            case R.id.tv6:
                intent = new Intent(this, HeadLargenMainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case BaseDialog.BUTTON_NO:
                            break;
                        case BaseDialog.BUTTON_YES:
                            IndexActivity.this.finish();
                            break;
                    }
                    dialog.dismiss();
                }
            };
            BaseDialog.showAlertDialog(this, "提示信息", "确定退出系统？",
                    android.R.drawable.ic_dialog_info,
                    new String[]{"确定", "取消"}, listener);

        }
}
