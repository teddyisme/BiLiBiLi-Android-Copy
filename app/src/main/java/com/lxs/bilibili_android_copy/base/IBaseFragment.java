package com.lxs.bilibili_android_copy.base;

import android.content.Context;
import android.view.View;

/**
 * Fragment接口
 * @author 曾繁添
 * @version 1.0
 *
 */
public interface IBaseFragment {

	/**
	 * 绑定渲染视图的布局文件
	 * @return 布局文件资源id
	 */
	public int bindLayout();
	
	/**
	 * 初始化控件
	 */
	public void initView(final View view);
	
	/**
	 * 业务处理操作（onCreateView方法中调用）
	 * @param mContext  当前Activity对象
	 */
	public void doBusiness(Context mContext);

	/**
	 * evenbus的事件回调，也是页面ui更新管理器
	 */
	void onViewUpdate(Object event);

	/**
	 * 被继承的Fragment必须初始化返回Store
	 * @return
	 */
	Store initStore();
	
}
