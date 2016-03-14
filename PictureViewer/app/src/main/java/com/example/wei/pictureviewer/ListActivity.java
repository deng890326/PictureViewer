package com.example.wei.pictureviewer;

import android.support.v4.app.Fragment;

/**
 * Created by wei on 2016/2/29 0029.
 */
public class ListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return ListFragment.newInstance();
    }
}
