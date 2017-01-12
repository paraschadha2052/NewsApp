package com.example.android.newsapp;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by paras on 1/12/17.
 */

final public class QueryUtils{

    public static ArrayList<News> getNews(String Url) throws MalformedURLException, IOException, JSONException {
        Log.v("paras", "getNews called with "+Url);
        ArrayList<News> arr = new ArrayList<>();
        String response = null;
        if(!TextUtils.isEmpty(Url)){
            URL url = new URL(Url);
            response = makeConnection(url);
            arr = extractNews(response);
        }
        return arr;
    }

    public static String makeConnection(URL Url) throws IOException {
        Log.v("paras", "makeConnection called");
        HttpURLConnection connection = (HttpURLConnection) Url.openConnection();
        connection.setConnectTimeout(1000);
        connection.setReadTimeout(1000);
        connection.setRequestMethod("GET");
        connection.connect();

        String response = null;
        if(connection.getResponseCode()==200){
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while(line!=null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            response = stringBuilder.toString();
        }
        return response;
    }

    public static ArrayList<News> extractNews(String json) throws JSONException {
        Log.v("paras", "extractNews called");
        ArrayList<News> arr = new ArrayList<>();
        JSONObject base = new JSONObject(json);
        String source = base.getString("source");
        JSONArray articles = base.getJSONArray("articles");
        for(int i=0;i<articles.length();i++){
            JSONObject article = articles.getJSONObject(i);
            arr.add(new News(article.optString("urlToImage", ""), article.optString("url", ""), article.optString("title", ""), article.optString("description", ""), source));
        }
        return arr;
    }

}



