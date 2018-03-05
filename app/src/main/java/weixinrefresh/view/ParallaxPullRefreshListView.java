package weixinrefresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.zlit.zlbaseapp.R;

import infinitelist.InfiniteListView;

/**
 * 对普通的列表组件添加了如下特色： 1、视差图片头效果，来自Path app 2、下拉刷新 3、继承于InfiniteListView，多了自动加载更多功能
 * 感谢视差组件的原作者：https://github.com/Gnod/ParallaxListView
 * 
 * @author zhangxiaohui
 * 
 */
public class ParallaxPullRefreshListView extends InfiniteListView {

	public final static double NO_ZOOM = 1;
	public final static double ZOOM_X2 = 2;

	private ImageView mImageView;

	private int mDrawableMaxHeight = -1;
	private int mImageViewHeight = -1;

	private int mDefaultImageViewHeight = 0;

	// 实现下拉刷新
	private final static float REFRESH_VIEW_POS = 0.7f; // 下拉刷新动画占全部下拉距离的0.7

	private ImageView mRefreshImageView;
	private int mRefreshTopMarginMax = 0;
	private int mRefreshTopMarginDefault = 0;

	private ObjectAnimator mRotationAnimator;

	private boolean refreshOnToushUp = false;
	private boolean inRefresh = false;

	// 刷新监听
	private OnRefreshListener onRefreshLister;

	private int mRefreshMaxHeight;

	public interface OnRefreshListener {
		public void onRefresh();
	}

	private interface OnOverScrollByListener {
		public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                    int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY,
                                    boolean isTouchEvent);
	}

	private interface OnTouchEventListener {
		public void onTouchEvent(MotionEvent ev);
	}

	public ParallaxPullRefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ParallaxPullRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ParallaxPullRefreshListView(Context context) {
		super(context);
		init(context);
	}

	public void init(Context context) {
		mDefaultImageViewHeight = context.getResources().getDimensionPixelSize(
				R.dimen.size_default_height);
	}

	private void setRotationAnimator() {
		mRotationAnimator = ObjectAnimator.ofFloat(mRefreshImageView, "rotation", 360);
		mRotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
		mRotationAnimator.setDuration(500);

		// 刷新完成后，再执行
		mRotationAnimator.addListener(new OnAnimatorEndListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				onRefreshLister.onRefresh();
			}

			@Override
			public void onAnimationEnd(Animator animation) {

				ValueAnimator refreshViewResetAnimator = ValueAnimator.ofInt(
						mRefreshTopMarginDefault, mRefreshTopMarginMax);
				refreshViewResetAnimator
						.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
							@Override
							public void onAnimationUpdate(ValueAnimator valueAnimator) {
								ViewHelper.setY(mRefreshImageView,
										(Integer) valueAnimator.getAnimatedValue());
							}
						});

				refreshViewResetAnimator.setDuration(300);
				refreshViewResetAnimator.start();
				inRefresh = false;
			};
		});
	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
			int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY,
			boolean isTouchEvent) {
		boolean isCollapseAnimation = false;

		if (!inRefresh) {
			isCollapseAnimation = scrollByListener.overScrollBy(deltaX, deltaY, scrollX, scrollY,
					scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent)
					|| isCollapseAnimation;
		}

		return isCollapseAnimation ? true : super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
				scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		// 临时修改：主要是因为继承别的listview导致过早的触发了 onScroll
		if (mImageView == null) {
			return;
		}

		View firstView = (View) mImageView.getParent();
		// firstView.getTop < getPaddingTop means mImageView will be covered by
		// top padding,
		// so we can layout it to make it shorter

		if (firstView.getTop() < getPaddingTop() && mImageView.getHeight() > mImageViewHeight) {

			// 需要调整回原来的状态
			int offset = getPaddingTop() - firstView.getTop();
			int height = Math.max(mImageView.getHeight() - offset, mImageViewHeight);
//			float ratio = height/mImageView.getHeight();
//			
//			mImageView.getLayoutParams().width = (int) (mImageView.getWidth()*ratio);
			mImageView.getLayoutParams().height = height;

			// 计算刷新动画的位置
			layoutRefreshView(height);

			// to set the firstView.mTop to 0,
			// maybe use View.setTop() is more easy, but it just support from
			// Android 3.0 (API 11)
			firstView.layout(firstView.getLeft(), 0, firstView.getRight(), firstView.getHeight());
			mImageView.requestLayout();
		}
	}

	private void layoutRefreshView(int imageViewHeight) {

		if (imageViewHeight < mImageViewHeight) {
			imageViewHeight = mImageViewHeight;
		}

		if (imageViewHeight > mDrawableMaxHeight) {
			imageViewHeight = mDrawableMaxHeight;
		}

		// 下拉距离比例，从原始大小到最大大小，计算当前位于多少了（0-1)
		float pullRatio = ((float) imageViewHeight - mImageViewHeight)
				/ (mDrawableMaxHeight - mImageViewHeight);

		// 下拉刷新动画位置比例
		float refreshRatio = pullRatio / REFRESH_VIEW_POS < 1 ? pullRatio / REFRESH_VIEW_POS : 1;

		// 下拉刷新动画margin（从 mRefreshTopMarginMax 到 mRefreshTopMarginDefault）
		int margin = (int) (mRefreshMaxHeight * refreshRatio + mRefreshTopMarginMax);
		ViewHelper.setY(mRefreshImageView, margin);

		// 计算动画效果
		// 计算用哪一帧图片
		float frameCount = 25; // 总共有7帧
		int resId = (int) (refreshRatio * (frameCount + 1));
		if (refreshRatio < 1) {
			mRefreshImageView.setImageResource(R.drawable.pull_refresh_01 + resId);
			refreshOnToushUp = false;
		} else {
			// 这里已经抵达刷新位置，更换图片暗示用户可以刷新了
			mRefreshImageView.setImageResource(R.drawable.pull_refresh_up);
			refreshOnToushUp = true;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		touchListener.onTouchEvent(ev);
		return super.onTouchEvent(ev);
	}

	public void setParallaxImageView(ImageView iv) {
		mImageView = iv;
		mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	}

	public void setRefreshView(ImageView iv) {
		mRefreshImageView = iv;
	}

	public void setViewsBounds(double zoomRatio) {
		if (mImageViewHeight == -1) {
			mImageViewHeight = mImageView.getHeight();
			if (mImageViewHeight <= 0) {
				mImageViewHeight = mDefaultImageViewHeight;
			}
			double ratio = ((double) mImageView.getDrawable().getIntrinsicWidth())
					/ ((double) mImageView.getWidth());

			mDrawableMaxHeight = (int) ((mImageView.getDrawable().getIntrinsicHeight() / ratio) * (zoomRatio > 1 ? zoomRatio
					: 1));

			// 将refreshView隐藏在上方先
			mRefreshTopMarginDefault = mRefreshImageView.getTop();
			mRefreshTopMarginMax = -mRefreshImageView.getHeight();

			mRefreshMaxHeight = mRefreshTopMarginDefault - mRefreshTopMarginMax;

			layoutRefreshView(mImageViewHeight);

			// 设定刷新的动画
			setRotationAnimator();
		}
	}

	public void setOnRefreshListener(OnRefreshListener listener) {
		this.onRefreshLister = listener;
	}

	public void notifyRefreshIsFinished() {
		// mImageViewHeight
		if (inRefresh && mRotationAnimator.isRunning())
			mRotationAnimator.end();
	}

	/**
	 * 手动触发刷新功能
	 */
	public void startRefresh() {
		inRefresh = true;
		mRefreshImageView.setImageResource(R.drawable.pull_refresh_up);
		ViewHelper.setY(mRefreshImageView, mRefreshTopMarginDefault);
		mRotationAnimator.start();
	}

	private OnOverScrollByListener scrollByListener = new OnOverScrollByListener() {
		@Override
		public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
				int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY,
				boolean isTouchEvent) {

			// 图片小于最大高度，拉高图片做视差效果
			if (mImageView.getHeight() <= mDrawableMaxHeight && isTouchEvent) {
				// 往回拉
				if (deltaY < 0) {
					if (mImageView.getHeight() - deltaY / 2 >= mImageViewHeight) {
						int height = mImageView.getHeight() - deltaY / 2 < mDrawableMaxHeight ? mImageView
								.getHeight() - deltaY / 2
								: mDrawableMaxHeight;

						mImageView.getLayoutParams().height = height;
						mImageView.requestLayout();
						layoutRefreshView(height);

					}
				} else { // 往回拉
					if (mImageView.getHeight() > mImageViewHeight) {

						int height = mImageView.getHeight() - deltaY > mImageViewHeight ? mImageView
								.getHeight() - deltaY
								: mImageViewHeight;

						mImageView.getLayoutParams().height = height;
						mImageView.requestLayout();
						layoutRefreshView(height);
						return true;
					}
				}
			}
			return false;
		}
	};

	private OnTouchEventListener touchListener = new OnTouchEventListener() {

		@Override
		public void onTouchEvent(MotionEvent ev) {
			if (ev.getAction() == MotionEvent.ACTION_UP) {
				if (mImageViewHeight - 1 < mImageView.getHeight()) {

					// 恢复视差的动画
					final ValueAnimator imageViewanimator = ValueAnimator.ofInt(
							mImageView.getLayoutParams().height, mImageViewHeight);
					imageViewanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator valueAnimator) {
							mImageView.getLayoutParams().height = (Integer) valueAnimator
									.getAnimatedValue();
							mImageView.requestLayout();
						}
					});

					imageViewanimator.setDuration(300);

					AnimatorSet animatorSet = new AnimatorSet();
					View firstView = (View) mImageView.getParent();
					if (refreshOnToushUp && firstView.getTop() >= getPaddingTop()) {
						// reset动画结束，紧跟着运行刷新动画
						inRefresh = true;
						refreshOnToushUp = false;

						animatorSet.play(imageViewanimator).before(mRotationAnimator);
						animatorSet.start();
					} else {
						if (!inRefresh) {
							// 同时做2个恢复动画
							ValueAnimator resetRefreshView = ValueAnimator.ofInt(
									mImageView.getLayoutParams().height, mImageViewHeight);
							resetRefreshView
									.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
										@Override
										public void onAnimationUpdate(ValueAnimator valueAnimator) {
											layoutRefreshView((Integer) valueAnimator
													.getAnimatedValue());
										}
									});

							resetRefreshView.setDuration(300);

							// 同时刷新
							animatorSet.play(imageViewanimator).with(resetRefreshView);
							animatorSet.start();
						}
					}

				}
			}
		}
	};

	private class OnAnimatorEndListener implements AnimatorListener {

		@Override
		public void onAnimationStart(Animator animation) {

		}

		@Override
		public void onAnimationEnd(Animator animation) {

		}

		@Override
		public void onAnimationCancel(Animator animation) {

		}

		@Override
		public void onAnimationRepeat(Animator animation) {

		}

	}
}
