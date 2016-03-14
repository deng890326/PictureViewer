package com.example.wei.pictureviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class ListFragment extends Fragment {

    public static ListFragment newInstance() {

        Bundle args = new Bundle();

        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @InjectView(R.id.list)
    RecyclerView mRecyclerView;

    private ThumbnailLoader<PictureHolder> mThumbnailLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThumbnailLoader = new ThumbnailLoader<>(getActivity(), new Handler(),
                new ThumbnailLoader.OnRequestDoneListener<PictureHolder>() {
                    @Override
                    public void onRequestDone(PictureHolder target, Bitmap bitmap) {
                        if (bitmap != null) {
                            target.mImageView.setImageBitmap(bitmap);
                        }
                    }
                });
        mThumbnailLoader.start();
        mThumbnailLoader.getLooper();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.inject(this, v);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        PictureManager pm = PictureManager.getInstance(getActivity());
        List<String> paths = pm.loadPictures().getPicturePaths();
        mRecyclerView.setAdapter(new PictureAdapter(paths));

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        String string = getResources().getQuantityString(R.plurals.items, paths.size(), paths.size());
        activity.getSupportActionBar().setSubtitle(string);
    }

    @Override
    public void onPause() {
        super.onPause();
        mThumbnailLoader.clearQueue();
    }

    class PictureHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @InjectView(R.id.image)
        ImageView mImageView;

        @InjectView(R.id.pic_name)
        TextView mTextView;

        private String mPath;

        public PictureHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindView(String path) {
            mPath = path;
            mTextView.setText(path);
            if (!mThumbnailLoader.isCached(path)) {
                mImageView.setImageResource(R.drawable.picture);
            }
            mThumbnailLoader.queueThumbnail(this, path);
        }

        @Override
        public void onClick(View v) {
            Intent intent = MainActivity.newIntent(getActivity(), mPath);
            startActivity(intent);
        }
    }

    private class PictureAdapter extends RecyclerView.Adapter<PictureHolder> {

        private List<String> mPaths;

        public PictureAdapter(List<String> paths) {
            mPaths = paths;
        }

        @Override
        public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.list_item, parent, false);
            return new PictureHolder(v);
        }

        @Override
        public void onBindViewHolder(PictureHolder holder, int position) {
            holder.bindView(mPaths.get(position));
        }

        @Override
        public int getItemCount() {
            return mPaths.size();
        }
    }
}
