package com.zlit.zlbaseapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlit.zlbaseapp.R;

/**
 * Created by SongCaiBain on 2018/2/26.
 */

public class IndexFragment2 extends Fragment {
    private TextView tv;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_index, container, false);
        initView();
        return view;
    }


    private void initView() {
        tv = view.findViewById(R.id.tv);
        tv.setText("空间");
    }

}
