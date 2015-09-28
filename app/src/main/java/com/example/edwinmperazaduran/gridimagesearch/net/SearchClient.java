package com.example.edwinmperazaduran.gridimagesearch.net;

import android.content.Context;
import android.widget.Toast;

import com.example.edwinmperazaduran.gridimagesearch.models.ImageFilter;
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

    public String getFilterUrl (ImageFilter imageFilter){
        String filterUrl = "";
            if (imageFilter.getColor() != null && !imageFilter.getColor().equals("Any")){
                filterUrl += "&imgcolor=" + imageFilter.getColor();
            }
            if (imageFilter.getSize() != null && !imageFilter.getSize().equals("Any")){
                filterUrl += "&imgsz=" + imageFilter.getSize();
            }
            if (imageFilter.getType() != null && !imageFilter.getType().equals("Any")){
                filterUrl += "&imgtype=" + imageFilter.getType();
            }
            if (imageFilter.getSite() != null && !imageFilter.getSite().equals("")){
                filterUrl += "&as_sitesearch=" + imageFilter.getSite();
            }

        return filterUrl;
    }
    public void getSearch(final String query, int startPage, ImageFilter imageFilter, Context context, JsonHttpResponseHandler handler ){
        try {
            String url = getApiUrl("?v=1.0&rsz=8&start="+ startPage * 8 + getFilterUrl(imageFilter) + "&q=" + URLEncoder.encode(query,"utf-8") );
            client.get(url, handler);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            Toast.makeText(context,"Search not found", Toast.LENGTH_SHORT).show();
        }
    }
}