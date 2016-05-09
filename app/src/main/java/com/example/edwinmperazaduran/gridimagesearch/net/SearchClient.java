package com.example.edwinmperazaduran.gridimagesearch.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.edwinmperazaduran.gridimagesearch.models.ImageFilter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchClient {
    private static final String API_BASE_URL = "https://www.googleapis.com/customsearch/v1?";
    private static final String API_KEY = "AIzaSyC9qyat9G0tlHrmg8s_dAfT1q4irjIZTlE";
    private static final String CX_KEY = "018166152475396321462:neztg-w1huk";
    private AsyncHttpClient client;

    public SearchClient(){
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl){
        return API_BASE_URL + relativeUrl;   }

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
            String url = getApiUrl("q="+URLEncoder.encode(query,"utf-8")+"&cx="+CX_KEY+"&searchType=image&key="+API_KEY);
            Log.e("URL A BUSCAR: ", url);
            client.get(url, handler);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            Toast.makeText(context,"Search not found", Toast.LENGTH_SHORT).show();
        }
    }
}