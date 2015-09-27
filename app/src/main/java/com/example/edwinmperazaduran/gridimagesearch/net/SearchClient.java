package com.example.edwinmperazaduran.gridimagesearch.net;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchClient {
    private static final String API_BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images";
    private AsyncHttpClient client;

    public SearchClient(){
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl){
        return API_BASE_URL + relativeUrl;
    }

    public void getSearch(final String query, int startPage, Context context, JsonHttpResponseHandler handler ){
        try {
            String url = getApiUrl("?v=1.0&rsz=8&start="+ startPage * 8 +"&q=");
            client.get(url + URLEncoder.encode(query,"utf-8"), handler);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            Toast.makeText(context,"Error getSearch", Toast.LENGTH_SHORT).show();
        }
    }
}