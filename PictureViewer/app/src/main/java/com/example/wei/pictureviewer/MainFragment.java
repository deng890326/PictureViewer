package com.example.wei.pictureviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wei on 2016/2/29 0029.
 */
public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    private static final String ARGS_INDEX = "index";

    @InjectView(R.id.name)
    TextView mTextView;

    @InjectView(R.id.picture)
    ImageView mImageView;

    @InjectView(R.id.postion_indicator)
    TextView mPosIndicator;

//    private Callback mCallback;

    public static MainFragment newInstance(int index) {

        Bundle args = new Bundle();
        args.putInt(ARGS_INDEX, index);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, v);

        int index = getArguments().getInt(ARGS_INDEX);
        PictureManager pm = PictureManager.getInstance(getActivity());
        List<String> paths = pm.getPicturePaths();
        
        mTextView.setText(paths.get(index));

        Bitmap bitmap = Utils.getScaledBitmap(paths.get(index), getActivity());
        mImageView.setImageBitmap(bitmap);

        mPosIndicator.setText(getString(R.string.indicator_format, index + 1, paths.size()));

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();

//        updateIndicator();
    }

/*    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        if (context instanceof Callback) {
            mCallback = (Callback) context;
            updateIndicator();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

/*    private void updateIndicator() {
        Pair<Integer, Integer> pair = mCallback.getCurrentPosAndTotal();
        if (mPosIndicator != null) {
            mPosIndicator.setText(getString(R.string.indicator_format, pair.first, pair.second));
        }
    }

    public interface Callback {
        Pair<Integer, Integer> getCurrentPosAndTotal();
    }*/
}
