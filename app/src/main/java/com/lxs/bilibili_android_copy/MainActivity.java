package com.lxs.bilibili_android_copy;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lxs.bilibili_android_copy.action.MainAction;
import com.lxs.bilibili_android_copy.action.MainActionsCreator;
import com.lxs.bilibili_android_copy.adapter.MyTabFragmentAdapter;
import com.lxs.bilibili_android_copy.base.BaseActivity;
import com.lxs.bilibili_android_copy.base.Store;
import com.lxs.bilibili_android_copy.store.MainStore;
import com.lxs.bilibili_android_copy.utils.ScreenUtils;
import com.lxs.bilibili_android_copy.widet.MyViewPager;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private MyTabFragmentAdapter myTabFragmentAdapter;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.content_viewPager)
    MyViewPager viewPager;

    @BindView(R.id.toggleDrawer)
    LinearLayout toggleIv;

    private MainStore store;
    MainActionsCreator mainActionsCreator;

    private ImageView switchIv;

    private SearchView mSearchView;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void doBusiness(Context mContext) {
        setSupportActionBar(toolbar);
        initTab();
        setDrawerLayout(toolbar);
        initDependencies();
        setThemeStatus();
    }

    private void initDependencies() {
        mainActionsCreator = new MainActionsCreator(dispatcher);
        store = new MainStore();
    }


    @Override
    public void onViewUpdate(Object event) {
        if (event instanceof MainStore.MainStoreEvent) {
            if (MainAction.ACTION_NEW_MESSAGE.equals(((MainStore.MainStoreEvent) event).getOperationType())) {

            }
        }
    }

    @Override
    public Store initStore() {
        return store;
    }

    private void setDrawerLayout(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initNavHeaderLisner();
    }

    private void initNavHeaderLisner() {
        View navView = navigationView.getHeaderView(0);
        switchIv = (ImageView) navView.findViewById(R.id.switch_them);
        switchIv.setOnClickListener(this);
    }

    private void initTab() {
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white));
        myTabFragmentAdapter = new MyTabFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(myTabFragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_index) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @OnClick({R.id.toggleDrawer, R.id.header_game_center, R.id.header_download})
    void setOnclickEnvent(View view) {
        switch (view.getId()) {
            case R.id.toggleDrawer:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.header_game_center:
                navigationView.setBackgroundResource(R.color.background_night);
                Toast.makeText(this, "game center", Toast.LENGTH_LONG).show();
                break;
            case R.id.header_download:
                Toast.makeText(this, "header_download", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switch_them:
                changeTheme();
                setThemeStatus();
                break;
        }
    }

    private void setThemeStatus() {
        if (getThemType() == DAY_THEME) {
            setStatusColor(R.color.colorPrimary);
            switchIv.setBackgroundResource(R.mipmap.ic_switch_daily);
        } else {
            setStatusColor(R.color.background_night_two);
            switchIv.setBackgroundResource(R.mipmap.ic_switch_night);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem item = menu.findItem(R.id.search_contact);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        setSearchView();
        return true;
    }

    //TODO:有待完善。官方searchview蛋疼的要死
    private void setSearchView() {
        final float density = getResources().getDisplayMetrics().density;
        mSearchView.setIconified(true);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setMaxWidth(ScreenUtils.getScreenWidth(this));
        final int closeImgId = getResources().getIdentifier("search_close_btn", "id", getPackageName());
        ImageView closeImg = (ImageView) mSearchView.findViewById(closeImgId);
        if (closeImg != null) {
            LinearLayout.LayoutParams paramsImg = (LinearLayout.LayoutParams) closeImg.getLayoutParams();
            paramsImg.topMargin = (int) (-2 * density);
            closeImg.setImageResource(R.drawable.ic_clear_black_24dp);
            closeImg.setLayoutParams(paramsImg);
        }

        final int editViewId = getResources().getIdentifier("search_src_text", "id", getPackageName());
        SearchView.SearchAutoComplete mEdit = (SearchView.SearchAutoComplete) mSearchView.findViewById(editViewId);
        if (mEdit != null) {
            mEdit.setHintTextColor(ContextCompat.getColor(this, R.color.textMidLight));
            mEdit.setTextColor(ContextCompat.getColor(this, R.color.background_night_two));
            mEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            mEdit.setHint("搜索视频、番剧、up主或AV号");
        }

        LinearLayout rootView = (LinearLayout) mSearchView.findViewById(R.id.search_bar);
//        rootView.setBackgroundResource(R.color.white);
        rootView.setClickable(true);

        LinearLayout editLayout = (LinearLayout) mSearchView.findViewById(R.id.search_plate);
        editLayout.setBackgroundResource(R.color.white);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) editLayout.getLayoutParams();
        LinearLayout tipLayout = (LinearLayout) mSearchView.findViewById(R.id.search_edit_frame);
        LinearLayout.LayoutParams tipParams = (LinearLayout.LayoutParams) tipLayout.getLayoutParams();
        tipParams.leftMargin = 0;
        tipParams.rightMargin = 0;
        tipLayout.setLayoutParams(tipParams);

//        ImageView icTip = (ImageView) mSearchView.findViewById(R.id.search_mag_icon);
//        icTip.setImageResource(R.drawable.ic_keyboard_backspace_black_24dp);
//        icTip.setVisibility(View.VISIBLE);
//
//        ImageView icSearchBtn = (ImageView) mSearchView.findViewById(R.id.search_voice_btn);
//        icSearchBtn.setBackgroundResource(R.drawable.ic_keyboard_backspace_black_24dp);
//        icSearchBtn.setVisibility(View.VISIBLE);

//        params.topMargin = (int) (4 * density);
//        editLayout.setLayoutParams(params);
//        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }
}
