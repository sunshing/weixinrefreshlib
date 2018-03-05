package com.zlit.zlbaseapp.activity.view;

//import com.nineoldandroids.view.ViewHelper;
import com.zlit.zlbaseapp.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 创建时间： 项目名称：
 * 
 * @author SongCaibian
 * @version 1.0
 * @since JDK 1.6.0_21
 * @ClassName 类说明：
 */
public class SmothSidingMenu extends HorizontalScrollView {
	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	private int mScrrenWidth;
	private int mMenuRightPadding = 50;
	private int mMenuWidth;
	
	private boolean once=false;
	private boolean isOpen;

	/**未使用自定义属性时，调用
	 * @param context
	 * @param attrs
	 */
	public SmothSidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
    /**
     * 使用自定义属性时调用
     * @param context
     * @param attrs
     * @param defStyle
     */
	public SmothSidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
     // TypedArray获取我们定义的属性
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SmothSidingMenu, defStyle, 0);

		int n = a.getIndexCount();
		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
			case R.styleable.SlidingMenu_rightPadding:
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50, context
										.getResources().getDisplayMetrics()));
				break;
			}
		}
		//TypedArray使用完时，一定要回收
		a.recycle();
		
		WindowManager windManager = (WindowManager) context
				.getSystemService(context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		windManager.getDefaultDisplay().getMetrics(outMetrics);
		mScrrenWidth = outMetrics.widthPixels;
		
	}
	public SmothSidingMenu(Context context) {
		this(context,null);
		
	}



	/**
	 * 设置子View的宽和高 设置自己的宽和高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	if(!once){
		mWapper=(LinearLayout) getChildAt(0);
		mMenu=(ViewGroup) mWapper.getChildAt(0);
		mContent=(ViewGroup) mWapper.getChildAt(1);
		mMenuWidth=mMenu.getLayoutParams().width=mScrrenWidth-mMenuRightPadding;
		mContent.getLayoutParams().width=mScrrenWidth;
		once=true;	
	}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 设置menu的显示与隐藏
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if(changed){
			this.scrollTo(mMenuWidth, 0);
		}
	}

	/**
	 * 手指滑动事件（只需处理action_up事件）
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
        switch (action) {
		case MotionEvent.ACTION_UP:
			int scrollX=getScrollX();
			if(scrollX>=mMenuWidth/2){
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
			}else{
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
		   return true;
		
		} 
		return super.onTouchEvent(ev);
	}
	
	
	/**
	 * 打开菜单
	 */
	public void openMenu()
	{
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}
	/**
	 * 关闭菜单
	 */
	public void closeMenu()
	{
		if (!isOpen)
			return;
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = false;
	}

	/**
	 * 切换菜单
	 */
	public void toggle()
	{
		if (isOpen)
		{
			closeMenu();
		} else
		{
			openMenu();
		}
	}
	
	/**
	 * 抽屉式侧滑效果
	 * Android3.0以下兼容：导入包
	 */
	/* (non-Javadoc)
	 * @see android.view.View#onScrollChanged(int, int, int, int)
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth; // 1 ~ 0
		//ViewHelper.setTranslationX(mMenu, mMenuWidth*scale);
	}
}
