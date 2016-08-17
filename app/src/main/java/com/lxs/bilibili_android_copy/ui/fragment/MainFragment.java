package com.lxs.bilibili_android_copy.ui.fragment;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lxs.bilibili_android_copy.R;
import com.lxs.bilibili_android_copy.action.MainFragmentAction;
import com.lxs.bilibili_android_copy.action.MainFragmentActionsCreator;
import com.lxs.bilibili_android_copy.adapter.GalleryPagerAdapter;
import com.lxs.bilibili_android_copy.adapter.VideoItemViewAdapter;
import com.lxs.bilibili_android_copy.base.BaseFragment;
import com.lxs.bilibili_android_copy.base.Store;
import com.lxs.bilibili_android_copy.bean.VideoItemBean;
import com.lxs.bilibili_android_copy.store.MainFragmentStore;
import com.lxs.bilibili_android_copy.widet.LoopPaper.AutoLoopViewPager;
import com.lxs.bilibili_android_copy.widet.LoopPaper.CirclePageIndicator;
import com.lxs.bilibili_android_copy.widet.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.loop_view)
    AutoLoopViewPager lunboPagerAv;

    @BindView(R.id.lunbo_indicator)
    CirclePageIndicator lunboIndicatorCi;

    @BindView(R.id.list_view)
    RecyclerView listViewRv;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    private VideoItemViewAdapter videoAdapter;
    List<VideoItemBean> videoList = new ArrayList<VideoItemBean>();

    private GalleryPagerAdapter galleryAdapter;

    private Context mContext;

    List<String> looperImageUrl = new ArrayList<String>();

    private MainFragmentActionsCreator creator;
    private MainFragmentStore store;

    private int re_sum = 0;

    @Override
    public int bindLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void doBusiness(Context mContext) {
        this.mContext = mContext;
        creator = new MainFragmentActionsCreator(dispatcher);
        store = new MainFragmentStore();

        initLooperView();

        initVideoItems();

        initSwipeRefresh();

        setRefreshState(true);
        onRefresh();
    }

    private void initSwipeRefresh() {
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary)
                , ContextCompat.getColor(getContext(), R.color.colorAccent));
        swipeLayout.setOnRefreshListener(this);
    }

    private void initLooperView() {
        galleryAdapter = new GalleryPagerAdapter(mContext, looperImageUrl);
        lunboPagerAv.setAdapter(galleryAdapter);
        lunboPagerAv.setInterval(5000);
        lunboIndicatorCi.setViewPager(lunboPagerAv);
        lunboIndicatorCi.setPadding(5, 5, 5, 5);
        lunboIndicatorCi.setStrokeColor(ContextCompat.getColor(getActivity(), R.color.transparent));
        lunboIndicatorCi.setFillColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        lunboIndicatorCi.setPageColor(ContextCompat.getColor(getActivity(), R.color.white));
    }

    private void initVideoItems() {
        videoAdapter = new VideoItemViewAdapter(getActivity(), videoList);
        listViewRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        listViewRv.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        listViewRv.setAdapter(videoAdapter);
    }

    @Override
    public void onViewUpdate(Object event) {
        if (event instanceof MainFragmentStore.MainFragmentStoreEvent) {
            if (MainFragmentAction.ACTION_AD_IMAGES.equals(((MainFragmentStore.MainFragmentStoreEvent) event).getOperationType())) {
                looperImageUrl = store.getAdUrls();
                initLooperView();
                re_sum++;
            } else if (MainFragmentAction.VIDEO_ITEMS.equals(((MainFragmentStore.MainFragmentStoreEvent) event).getOperationType())) {
                videoList.clear();
                videoList.addAll(store.getVideoItemBeens());
                videoAdapter.notifyDataSetChanged();
                re_sum++;
            }
            if (re_sum >= 2) {
                setRefreshState(false);
            }
        }
    }

    @Override
    public Store initStore() {
        return store;
    }

    @Override
    public void onRefresh() {
        re_sum = 0;
        creator.setLooperViewImages();
        creator.setVideoItems();
    }

    private void setRefreshState(final boolean is) {
        swipeLayout.post(new Runnable() {
                             @Override
                             public void run() {
                                 swipeLayout.setRefreshing(is);
                             }
                         }
        );
    }
}
