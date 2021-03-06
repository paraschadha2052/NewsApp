package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
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

        try {
            //For internet connection check
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()) {
                QueryUtils.addSources();
                getLoaderManager().initLoader(1, null, MainActivity.this);
            }
            else{
                ProgressBar pb = (ProgressBar) findViewById(R.id.pb);
                pb.setVisibility(View.GONE);
                TextView tx = (TextView) findViewById(R.id.error);
                tx.setVisibility(View.VISIBLE);
                tx.setText("Not connected to Internet");
            }
        } catch (JSONException e) {
            Log.e("Error: ", "Unable to make list of sources", e);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Log.v("paras", "onCreateLoader called");
        ArrayList<String> urls=new ArrayList<>();
        for(int i=0;i<QueryUtils.sourceArrayList.size();i++){
            Uri url = Uri.parse(BASE_URL);
            Uri.Builder uri = url.buildUpon();
            uri.appendQueryParameter("source", QueryUtils.sourceArrayList.get(i).getmId());
            uri.appendQueryParameter("apiKey", API_KEY);
            urls.add(uri.build().toString());
        }
        return new NewsLoader(MainActivity.this, urls);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        ProgressBar pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.GONE);
        Log.v("paras", "Data Returned");
        if(data!=null){
            adapter.clear();
            Collections.shuffle(data);
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }
}
