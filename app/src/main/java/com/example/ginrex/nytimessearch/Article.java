package com.example.ginrex.nytimessearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ginrex on 18/03/2016.
 */
public class Article implements Serializable {

    private String webURL;

    public String getWebURL() {
        return webURL;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    private String headLine;
    private String thumbnailURL;

    public Article(JSONObject jsonObject) {

        try {

            //take data from the object then pass to the class
            this.webURL = jsonObject.getString("web_url");
            this.headLine = jsonObject.getJSONObject("headline").getString("main");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if(multimedia.length() > 0) {
                //take the first object in the array multimedia of the jsonObject, then take the string 'url'
                JSONObject multimediaJSON = multimedia.getJSONObject(0);
                this.thumbnailURL = "http://www.nytimes.com/" + multimediaJSON.getString("url");
            }else {this.thumbnailURL = "";}

        }catch (JSONException e)  {
            e.printStackTrace();
        }
    }

    public static ArrayList<Article> fromJSONArray (JSONArray array) {

        ArrayList<Article> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new Article(array.getJSONObject(i)));
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

}
