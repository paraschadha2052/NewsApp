package com.example.android.newsapp;

/**
 * Created by paras on 1/12/17.
 */

public class News {
    private String mImageURL, mURL, mTitle, mDesc, mSource;

    public News(String ImageURL, String URL, String Title, String Desc, String source){
        mDesc = Desc;
        mTitle = Title;
        mURL = URL;
        mImageURL = ImageURL;
        mSource = source;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public String getmURL() {
        return mURL;
    }

    public String getmSource() {
        return mSource;
    }
}
