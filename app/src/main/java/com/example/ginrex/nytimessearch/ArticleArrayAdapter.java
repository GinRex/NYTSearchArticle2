package com.example.ginrex.nytimessearch;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ginrex on 19/03/2016.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public ArticleArrayAdapter(Context context, List<Article> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data for the postition
        Article article = this.getItem(position);

        //check if the view is reused, if not, inflate
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }

        TextView title = (TextView)convertView.findViewById(R.id.tvTitle);
        ImageView thumbnail = (ImageView)convertView.findViewById(R.id.ivThumbnail);

        //clear the image to ring in the next
        thumbnail.setImageResource(0);

        // set the data to the view
        title.setText(article.getHeadLine());

        if (!TextUtils.isEmpty(article.getThumbnailURL())) {
            Picasso.with(getContext()).load(article.getThumbnailURL()).into(thumbnail);
        }

        return convertView;
    }
}
