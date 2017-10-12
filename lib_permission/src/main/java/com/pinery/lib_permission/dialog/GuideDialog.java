package com.pinery.lib_permission.dialog;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.pinery.lib_permission.R;
import com.pinery.lib_permission.third.base.RomHelper;
import com.pinery.lib_permission.util.PermissionUtil;
import com.pinery.lib_permission.util.Util;
import com.pinery.lib_permission.util.VersionUtil;
import com.pinery.lib_permission.util.ViewUtil;

/**
 * 引导框
 * @author hesong
 *
 */
public class GuideDialog {

	private Context mContext;

	private View mDialogLayout;

	private View mLayoutGuidecontent;
	private TextView tvGuideContent;
	private ImageView ivIcon;
	private TextView tvTitle;
	private TextView tvSummary;
	private View checkBox;
	private View layout;

	private String mGuideContent;

	private boolean isPopViewShowing;
	private boolean isShowAnimation = true;
	private boolean isOnAnimation;//是否正在动画

	private View mView;

	private View.OnClickListener mOnClickListener;

	private Handler mHandler = new Handler(Looper.getMainLooper());

	public GuideDialog(Context context){
		mContext = context;

		initDialog();
	}

	/**
	 * 设置是否显示动画
	 * @param show
	 */
	public void setShowAnimation(boolean show){
		isShowAnimation = show;
	}

	/**
	 * dialog是否正在显示
	 * @return
	 */
	public boolean isDialogShowing(){
		return isPopViewShowing;
	}

	/**
	 * 初始化界面
	 */
	private void initDialog(){

		//创建框
		if(mDialogLayout == null){

			mDialogLayout = getOneCreateDialogView(mContext);
		}

	}

	/**
	 * 创建一个dialog view
	 * @param context
	 * @return
	 */
	private View getOneCreateDialogView(Context context){

		View view = LayoutInflater.from(context).inflate(R.layout.layout_permission_guide, null);
		mView = view;
		mLayoutGuidecontent = ViewUtil.findViewById(view, android.R.id.content);

		try {
			// 填充应用信息
			PackageManager pm = context.getPackageManager();
			ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), 0);

			ivIcon = ((ImageView) view.findViewById(android.R.id.icon));
			tvTitle = ((TextView) view.findViewById(android.R.id.title));
			tvSummary = ((TextView) view.findViewById(android.R.id.summary));
			tvGuideContent = ((TextView) view.findViewById(android.R.id.message));
			checkBox = (view.findViewById(android.R.id.checkbox));
			layout = (view.findViewById(R.id.guide_layout_view));

			ivIcon.setImageDrawable(ai.loadIcon(pm));
			tvTitle.setText(ai.loadLabel(pm));
			tvSummary.setText(R.string.off);
			tvGuideContent.setText(context.getString(R.string.permission_guide_message, ai.loadLabel(pm)));

			// 是否显示图标
			ivIcon.setVisibility(View.VISIBLE);
			// 是否显示开关
			checkBox.setVisibility(View.GONE);

			view.setFocusableInTouchMode(true);
			view.setOnKeyListener(new View.OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {

					if(keyCode == KeyEvent.KEYCODE_BACK){
						dismissDialog();
					}

					return false;
				}
			});
			mLayoutGuidecontent.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismissDialog();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	public GuideDialog setOnClickListener(View.OnClickListener listener){
		mOnClickListener = listener;

		return this;
	}

	public View.OnClickListener getOnClickListener(){
		return mOnClickListener;
	}

	/**
	 * 设置引导内容
	 * @param text
	 */
	public GuideDialog setGuideContent(String text){

		mGuideContent = text;

		if(!TextUtils.isEmpty(text)){
			tvGuideContent.setText(text);
		}

		return this;
	}

	/**
	 * 设置引导内容
	 * @param textId
	 */
	public GuideDialog setGuideContent(int textId){

		mGuideContent = mContext.getString(textId);

		if(!TextUtils.isEmpty(mGuideContent)){
			tvGuideContent.setText(mGuideContent);
		}

		return this;
	}

	public String getGuideContent(){
		return mGuideContent;
	}

	/**
	 * 只显示消息内容
	 * @param textId
	 * @return
	 */
	public GuideDialog setOnlyShowText(int textId){
		return setOnlyShowText(mContext.getString(textId));
	}

	/**
	 * 只显示消息内容
	 * @param text
	 * @return
	 */
	public GuideDialog setOnlyShowText(String text){
		mGuideContent = text;

		//只显示消息内容
		layout.setVisibility(View.INVISIBLE);
		tvGuideContent.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
		tvGuideContent.setText(mGuideContent);

		return this;
	}

	/**
	 * 关闭
	 */
	public void dismissDialog(){

		if(!isPopViewShowing){
			return;
		}

		isPopViewShowing = false;

		if(isShowAnimation){

			showCloseAnimation();

		}else{

			dismiss();

		}

	}

	/**
	 * 显示打开动画
	 */
	private void showOpenAnimation(){
		View view = mLayoutGuidecontent;

		float fromX = Util.getWindowSize(mContext).x;
		float toX = 0;
		//ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", fromX, toX).setDuration(500);
		ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(500);
		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				isOnAnimation = true;
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {

				isOnAnimation = false;

			}

			@Override
			public void onAnimationCancel(Animator animation) {

				isOnAnimation = false;

			}
		});

		animator.start();
	}

	/**
	 * 显示关闭动画
	 */
	private void showCloseAnimation(){
		View view = mLayoutGuidecontent;

		float fromX = 0;
		float toX = Util.getWindowSize(mContext).x;
		//ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", fromX, toX).setDuration(500);
		ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1, 0).setDuration(500);
		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				isOnAnimation = true;
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {

				isOnAnimation = false;

				dismiss();
			}

			@Override
			public void onAnimationCancel(Animator animation) {

				isOnAnimation = false;

				dismiss();
			}
		});

		animator.start();

	}

	public View getView() {
		return mDialogLayout;
	}

	public WindowManager.LayoutParams getLayoutParams() {
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();

		params.format = PixelFormat.RGBA_8888;
		params.gravity = Gravity.LEFT | Gravity.TOP;

		if(VersionUtil.getAndroidSDKVersion() >= 19){
			//4.4版本及以上,有悬浮窗权限,则用悬浮窗,没有,则用Toast
			if(!PermissionUtil.checkHasPermissionFloatForVersion6(mContext) || !RomHelper.hasFloatWindowPermission(mContext)){
				params.type = WindowManager.LayoutParams.TYPE_TOAST;
			}else{
				params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
			}
		}else{
			//4.4版本以下,用悬浮窗
			params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		}

		params.flags =
				//全屏显示
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;

		params.x = 0;
		params.y = 0;
		params.width = Util.getWindowSizeFitOriatation(mContext).x;
		params.height = Util.getWindowSizeFitOriatation(mContext).y;

		return params;
	}

	public void show() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {

					//显示为顶层
					showInner();

				}
		}, 800);
	}

	private void showInner(){
		//正在动画，return
		if(isOnAnimation){
			return;
		}

		initDialog();

		//显示框
		if(!isPopViewShowing){

			isPopViewShowing = true;

			//添加层
			WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
			windowManager.addView(getView(), getLayoutParams());

			//显示动画
			if(isShowAnimation){
				showOpenAnimation();
			}

			//MyApplication.getInstance().mHandler.removeCallbacks(mCloseRunnable);
			//MyApplication.getInstance().mHandler.postDelayed(mCloseRunnable, 5000);
		}

	}

	private Runnable mCloseRunnable = new Runnable() {
		@Override
		public void run() {
			dismissDialog();
		}
	};

	public void dismiss(){
		//添加层
		WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		windowManager.removeView(getView());

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if(mOnClickListener != null){
					mOnClickListener.onClick(null);
				}
			}
		}, 300);
	}

	private WindowManager.LayoutParams getCommonFloatParams(){
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();

		params.format = PixelFormat.RGBA_8888;
		params.gravity = Gravity.LEFT | Gravity.TOP;

		if(VersionUtil.getAndroidSDKVersion() >= 19){

			//4.4版本及以上,有悬浮窗权限,则用悬浮窗,没有,则用Toast
			if(!PermissionUtil.checkHasPermissionFloatForVersion6(mContext) || !RomHelper.hasFloatWindowPermission(mContext)){

				params.type = WindowManager.LayoutParams.TYPE_TOAST;
			}else{

				params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
			}

		}else{

			//4.4版本以下,用悬浮窗
			params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;

		}

		return params;
	}


}
