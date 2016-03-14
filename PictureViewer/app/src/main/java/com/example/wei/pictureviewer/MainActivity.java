package com.example.wei.pictureviewer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String KEY_PICTURE_PATH = "com.example.wei.pictureviewer.key_picture_path";

    public static Intent newIntent(Activity activity, String picturePath) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(KEY_PICTURE_PATH, picturePath);
        return intent;
    }

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

    PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        PictureManager pm = PictureManager.getInstance(this);
        final List<String> paths = pm.loadPictures().getPicturePaths();

        mPagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MainFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return paths.size();
            }
        };

        mViewPager.setAdapter(mPagerAdapter);

        String path = getIntent().getStringExtra(KEY_PICTURE_PATH);
        if (path != null) {
            int index = paths.indexOf(path);
            mViewPager.setCurrentItem(index);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled, position=" + position
                        + ", positionOffset=" + positionOffset
                        + ", positionOffsetPixels=" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, postion=" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged, state=" + state);
            }
        });
    }

/*    @Override
    public Pair<Integer, Integer> getCurrentPosAndTotal() {
        return Pair.create(mViewPager.getCurrentItem() + 1, mPagerAdapter.getCount());
    }*/
}
