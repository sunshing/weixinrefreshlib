package weixinrefresh.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zlit.zlbaseapp.R;

import weixinrefresh.model.Communicate;

public class CommunicateListViewAdapter extends BaseAdapter {

	private List<Communicate> list=new ArrayList<Communicate>();
	private Context context;
	LayoutInflater inflater;

	public CommunicateListViewAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public void setList(List<Communicate> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Communicate getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.parallax_pull_listview_item, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView
					.findViewById(R.id.id_textview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textView.setText(list.get(position).getCommunicateName());

		return convertView;
	}
	
	public static class ViewHolder {
		public TextView textView;
		public ImageView imageView;
		public LinearLayout layout;
	}
}
