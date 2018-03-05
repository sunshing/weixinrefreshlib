package weixinrefresh;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.zlit.zlbaseapp.R;

import infinitelist.InfiniteListView;
import infinitelist.OnListEnds;
import weixinrefresh.view.ParallaxPullRefreshListView;
import swiperefreshlayout.SwipeRefreshLayout;
import weixinrefresh.adapter.CommunicateListViewAdapter;
import weixinrefresh.model.Communicate;
import weixinrefresh.model.LocalData;


public class HeadLargenMainActivity extends Activity implements
		ParallaxPullRefreshListView.OnRefreshListener {

	private SwipeRefreshLayout swipeLayout;
	private ParallaxPullRefreshListView listView;
	private boolean hadMore = true;

	private View imageHeader;
	ImageView imageParallax;
	String albumBg;
	private CommunicateListViewAdapter adapter;
	List<Communicate> communicateList = new ArrayList<Communicate>();
	private int pageNum = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parallax_pull_activity_main);
		initView();
		setPullToRefresh(); // 设置下拉刷新
		addParallaxHeader(); // 添加视差效果的头部
		setLoadMore(); // 添加加载更多功能
	}

	private void initView() {
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
		listView = (ParallaxPullRefreshListView) findViewById(R.id.listView);
		adapter = new CommunicateListViewAdapter(this);
		listView.setAdapter(adapter); // 设定适配器
	}

	private void setPullToRefresh() {
		swipeLayout.setEnabled(false);
		listView.setOnRefreshListener(this);
	}

	private void addParallaxHeader() {
		imageHeader = LayoutInflater.from(this).inflate(
				R.layout.view_header_parallax, null);

		// 照片墙信息
		imageParallax = (ImageView) imageHeader
				.findViewById(R.id.layout_header_image);
		/*
		 * albumBg = GlobalTools.getAlbumBg();
		 * 
		 * if(albumBg != null){ ImageLoader.getInstance().displayImage(
		 * GlobalTools.SERVER_PATH + albumBg, imageParallax); }
		 */

		imageParallax.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 照片墙背景选择
				// showGenderChoose();
			}
		});

		ImageView imageRefresh = (ImageView) imageHeader
				.findViewById(R.id.refresh_image);

		listView.setParallaxImageView(imageParallax);
		listView.setRefreshView(imageRefresh);
		listView.addHeaderView(imageHeader);

		imageHeader.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@SuppressLint("NewApi")
					@Override
					public void onGlobalLayout() {

						listView.setViewsBounds(ParallaxPullRefreshListView.ZOOM_X2);

						listView.startRefresh();

						ViewTreeObserver obs = imageHeader
								.getViewTreeObserver();
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							obs.removeOnGlobalLayoutListener(this);
						} else {
							obs.removeGlobalOnLayoutListener(this);
						}
					}
				});

		listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
	}

	private void setLoadMore() {
		// 设置加载更多的选项
		listView.setRefreshIconStyle(InfiniteListView.REFRESH_ICON_LIGHT);
		listView.setOnListEnds(new OnListEnds() {

			@Override
			public void onStartRefresh() {
				// 加载数据
				loadData();
			}
            /**
             * firstVisibleItem:当前页面上能看到的第一条数据
             * visibleItemCount:当前页面能看到的总条数
             * totalItemCount:截至当前加载的总条数=实际条数+2
             */
			@Override
			public boolean isEnd(int firstVisibleItem, int visibleItemCount,
					int totalItemCount) {
				/*Toast.makeText(
						getApplicationContext(),
						"------" + firstVisibleItem + "---" + visibleItemCount
								+ "-----" + totalItemCount, Toast.LENGTH_LONG)
						.show();*/
				Log.i("firstVisibleItem-----", firstVisibleItem+visibleItemCount+"---"+totalItemCount);

				if (hadMore
						&& firstVisibleItem + visibleItemCount + 2 > totalItemCount) {
					return true;
				} else {
					return false;
				}
			}
		});
	}

	@Override
	public void onRefresh() {
		pageNum = 1;
		loadData();
	}

	private void loadData() {
		// Toast.makeText(this, "执行次数", Toast.LENGTH_SHORT).show();
		List<Communicate> listResult = LocalData.getDatas(pageNum);
		if (listResult != null && listResult.size() > 0) {

			if (pageNum == 1) {
				listView.notifyRefreshIsFinished();
				communicateList = listResult;
				adapter.setList(communicateList);
			} else {
				listView.notifyLoadMoreIsFinished();
				communicateList.addAll(listResult);
			}

			if (listResult.size() < 20) {
				hadMore = false;
			} else {
				hadMore = true;
			}
			adapter.notifyDataSetChanged();
			pageNum++;
		} else {
			hadMore = false;
		}

	}

}
