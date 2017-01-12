package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    private String BASE_URL = "https://newsapi.org/v1/articles";
    private String API_KEY = "d4f99c4b957246ba992e5f7515d13786";
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<News> arr = new ArrayList<>();
        adapter = new NewsAdapter(MainActivity.this, arr);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        getLoaderManager().initLoader(1, null, MainActivity.this);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Log.v("paras", "onCreateLoader called");
        Uri url = Uri.parse(BASE_URL);
        Uri.Builder uri = url.buildUpon();
        uri.appendQueryParameter("source", "the-hindu");
        uri.appendQueryParameter("apiKey", API_KEY);
        return new NewsLoader(MainActivity.this, uri.build().toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        Log.v("paras", "Data Returned");
        if(data!=null){
            adapter.clear();
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }
}
