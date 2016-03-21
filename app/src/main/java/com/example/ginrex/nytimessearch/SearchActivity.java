package com.example.ginrex.nytimessearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    int first;
    String rquery = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        GridView gvArticles = (GridView)findViewById(R.id.gvResults);

        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvArticles.setAdapter(adapter);



        gvArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);

                Article article = articles.get(position);
                i.putExtra("article", article);
                startActivity(i);
            }
        });


            fetchArticle(null, null, 0, 0, 0, 0);


            Intent i = getIntent();
            String date = i.getStringExtra("date");
            int order = i.getIntExtra("order", 0);
            int c1 = i.getIntExtra("c1", 0);
            int c2 = i.getIntExtra("c2", 0);
            int c3 = i.getIntExtra("c3", 0);
            String queryr = i.getStringExtra("rquery");
            fetchArticle(queryr, date, order, c1, c2, c3);

        /*if (first == 0) {
            Intent i = getIntent();
            String date = i.getStringExtra("date");
            int order = i.getIntExtra("order", 0);
            int c1 = i.getIntExtra("c1", 0);
            int c2 = i.getIntExtra("c2", 0);
            int c3 = i.getIntExtra("c3", 0);
            fetchArticle(rquery, date, order, c1, c2, c3);
        }
        else fetchArticle(null, null, 0, 0, 0, 0);*/
    }

    public void fetchArticle(String query, String date, int order, int c1, int c2, int c3) {

        AsyncHttpClient client;
        client = new AsyncHttpClient();

        final String Url = " http://api.nytimes.com/svc/search/v2/articlesearch.json";
        final RequestParams params = new RequestParams();
        params.put("api-key", "2e3541391f46965700250207a3b1f756:1:74742379");
        //params.put("page", 0);
        if (query != null) {
            params.put("q", query);
        }
        if (date != null) {params.put("begin_date", date);}
        if (order != 0){
            if (order == 1) {params.put("sort", "newest");}
            else params.put("sort", "oldest");
        }

        if (c1 != 0) {params.put("fq=news_desk", "Arts");}
        if (c2 != 0) {params.put("fq=news_desk", "Fashion & Style");}
        if (c3 != 0) {params.put("fq=news_desk", "Sports");}

        client.get(Url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleResult = null;
                adapter.clear();
                adapter.notifyDataSetChanged();
                Log.i("debg", Url + params);
                try {

                    articleResult = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.fromJSONArray(articleResult));
                    adapter.notifyDataSetChanged();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem filterItem = menu.findItem(R.id.action_filter);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                fetchArticle(query, null, 0, 0, 0, 0);
                rquery = query;
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }else if (id == R.id.action_filter) {
            Intent i = new Intent(this, FIlterActivity.class);
            i.putExtra("query", rquery);
            first = 0;
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
