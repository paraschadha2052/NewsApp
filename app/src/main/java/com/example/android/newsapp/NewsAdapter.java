package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by paras on 1/12/17.
 */

public class NewsAdapter extends ArrayAdapter<News>{
    public NewsAdapter(Context context, List<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        final News news = getItem(position);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView source = (TextView) convertView.findViewById(R.id.source);
        final TextView desc = (TextView) convertView.findViewById(R.id.desc);
        final ImageView expand = (ImageView) convertView.findViewById(R.id.expand);
        final ImageView shrink = (ImageView) convertView.findViewById(R.id.shrink);
        final RelativeLayout rl2 = (RelativeLayout) convertView.findViewById(R.id.rl2);
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getmURL()));
                getContext().startActivity(in);
                rl2.setVisibility(View.GONE);
                expand.setVisibility(View.VISIBLE);
            }
        };

        Picasso.with(getContext()).load(news.getmImageURL()).fit().centerCrop().into(image);
        title.setText(news.getmTitle());
        source.setText(news.getmSource());
        desc.setText(news.getmDesc());

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                desc.setVisibility(View.VISIBLE);
                shrink.setVisibility(View.VISIBLE);
            }
        });

        shrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desc.setVisibility(View.GONE);
                v.setVisibility(View.GONE);
                //rl2.setVisibility(View.GONE);
                expand.setVisibility(View.VISIBLE);
            }
        });

        title.setOnClickListener(click);
        image.setOnClickListener(click);
        desc.setOnClickListener(click);
        source.setOnClickListener(click);
        //rl2.setVisibility(View.GONE);
        expand.setVisibility(View.VISIBLE);
        desc.setVisibility(View.GONE);
        shrink.setVisibility(View.GONE);

        return convertView;
    }
}
