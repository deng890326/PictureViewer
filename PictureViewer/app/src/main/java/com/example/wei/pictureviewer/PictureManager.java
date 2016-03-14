package com.example.wei.pictureviewer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei on 2016/2/29 0029.
 */
public class PictureManager {

    private Context mContext;
    private List<String> mPicturePaths = new ArrayList<>();

    private static PictureManager mPictureManager;

    private PictureManager(Context context) {
        mContext = context;
    }

    public static PictureManager getInstance(Context context) {
        if (mPictureManager == null) {
            mPictureManager = new PictureManager(context);
        }
        return mPictureManager;
    }

    public List<String> getPicturePaths() {
        return mPicturePaths;
    }

    public PictureManager loadPictures() {
        mPicturePaths.clear();
        ContentResolver cr = mContext.getContentResolver();
        String col = MediaStore.Images.Media.DATA;
        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] {col}, null, null, col);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mPicturePaths.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }

        return this;
    }
}
