package com.example.android.news_app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.news_app.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mNewsTextView;
    private TextView mUrlNewsView;
    private EditText mSearchBoxNewsText;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsTextView = (TextView) findViewById(R.id.ndata);
        mSearchBoxNewsText = (EditText) findViewById(R.id.nsearch);
        mUrlNewsView = (TextView) findViewById(R.id.nurl);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pindicator);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nsearch) {
            mUrlNewsView.setText("");
            loadNewsData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);
        return true;
    }


    private void loadNewsData() {

        URL NewsSearchUrl = NetworkUtils.buildUrl();
        mUrlNewsView.setText(NewsSearchUrl.toString());


        new newsinfo().execute();
    }
    public class newsinfo extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            URL newsRequestURL = NetworkUtils.buildUrl();

            try {
                return NetworkUtils.getResponseFromHttpUrl(newsRequestURL);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String newsData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (newsData != null) {
                mNewsTextView.setText(newsData);
            }
        }
    }
}
