package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paras on 1/13/17.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private ArrayList<String> mUrl;
    public NewsLoader(Context context, ArrayList<String> Url) {
        super(context);
        mUrl = Url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        Log.v("paras", "loadInBackground called");
        ArrayList<News> arr = new ArrayList<>();
        for(int i=0;i<mUrl.size();i++) {
            try {
                String source = QueryUtils.sourceArrayList.get(i).getmName();
                arr.addAll(QueryUtils.getNews(mUrl.get(i), source));
                Log.v("paras: ", "Done"+i);
            } catch (MalformedURLException e) {
                Log.e("Error: ", "Unable to make URL", e);
            } catch (IOException e) {
                Log.e("Error: ", "Unable to connect to net", e);
            } catch (JSONException e) {
                Log.e("Error: ", "Json parsing error", e);
            }
        }
        return arr;
    }
}
