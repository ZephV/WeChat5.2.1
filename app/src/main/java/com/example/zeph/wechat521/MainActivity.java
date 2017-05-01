package com.example.zeph.wechat521;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

  private Toolbar toolbar;
  private ViewPager mViewPager;
  private FragmentPagerAdapter mAdapter;
  private List<Fragment> mData;
  private TextView mChatTextView;
  private TextView mFindTextView;
  private TextView mContractTextView;
  private ImageView mTabLineImageView;

  private int Screen_one_third;
  private int mCurrentPageIndex;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initView(); // 初始化控件

    initTabLine(); // 初始化Tab的下划线长度

  }

  private void initTabLine() {
    mTabLineImageView = (ImageView) findViewById(R.id.select_line);
    DisplayMetrics dm = new DisplayMetrics();
    Display display = getWindowManager().getDefaultDisplay();
    display.getMetrics(dm);
    Screen_one_third = dm.widthPixels / 3;
    LayoutParams lp = mTabLineImageView.getLayoutParams();
    lp.width = Screen_one_third;
    mTabLineImageView.setLayoutParams(lp);
  }

  private void initView() {

    // 初始化各控件
    toolbar = (Toolbar) findViewById(R.id.tool_bar);
    mViewPager = (ViewPager) findViewById(R.id.view_pager);
    mChatTextView = (TextView) findViewById(R.id.tv_chat);
    mFindTextView = (TextView) findViewById(R.id.tv_find);
    mContractTextView = (TextView) findViewById(R.id.tv_contact);

    mData = new ArrayList<Fragment>();

    // 实例化三个Fragment
    ChatTabFragment chatTab = new ChatTabFragment();
    FindTabFragment findTab = new FindTabFragment();
    FriendTabFragment friendTab = new FriendTabFragment();

    // toolbar属性
    toolbar.setLogo(R.drawable.actionbar_icon); // 为toolbar设置Logo
    toolbar.inflateMenu(R.menu.tool_menu); // 为toolbar设置菜单

    // 将fragment中得数据添加进mData
    mData.add(chatTab);
    mData.add(findTab);
    mData.add(friendTab);

    // 初始化适配器为其设置属性
    mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int position) {
        return mData.get(position);
      }

      @Override
      public int getCount() {
        return mData.size();
      }
    };

    // ViewPager属性
    mViewPager.setAdapter(mAdapter); // 为ViewPager设置适配器
    mViewPager.addOnPageChangeListener(new OnPageChangeListener() {//ViewPager添加-页面切换监听
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineImageView
            .getLayoutParams();
        if (mCurrentPageIndex == 0 && position == 0) {//0->1
          lp.leftMargin = (int) (mCurrentPageIndex * Screen_one_third
              + positionOffset * Screen_one_third);
        } else if (mCurrentPageIndex == 1 && position == 0) {
          lp.leftMargin = (int) (mCurrentPageIndex * Screen_one_third
              + (positionOffset - 1) * Screen_one_third);
        } else if (mCurrentPageIndex == 1 && position == 1) {
          lp.leftMargin = (int) (mCurrentPageIndex * Screen_one_third
              + positionOffset * Screen_one_third);
        } else if (mCurrentPageIndex == 2 && position == 1) {
          lp.leftMargin = (int) (mCurrentPageIndex * Screen_one_third
              + (positionOffset - 1) * Screen_one_third);
        }
        mTabLineImageView.setLayoutParams(lp);
      }

      @Override
      public void onPageSelected(int position) {
        resetTextView();
        switch (position) {
          case 0:
            mChatTextView.setTextColor(Color.parseColor("#008000"));
            break;

          case 1:
            mFindTextView.setTextColor(Color.parseColor("#008000"));
            break;

          case 2:
            mContractTextView.setTextColor(Color.parseColor("#008000"));
            break;

        }
        mCurrentPageIndex = position;
      }


      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }


  private void resetTextView() {
    mChatTextView.setTextColor(Color.BLACK);
    mFindTextView.setTextColor(Color.BLACK);
    mContractTextView.setTextColor(Color.BLACK);
  }


}
