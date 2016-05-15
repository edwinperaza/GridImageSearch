package com.example.edwinmperazaduran.gridimagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.edwinmperazaduran.gridimagesearch.Dialogs.ImageFilterDialog;
import com.example.edwinmperazaduran.gridimagesearch.R;
import com.example.edwinmperazaduran.gridimagesearch.adapters.ImageResultArrayAdapter;
import com.example.edwinmperazaduran.gridimagesearch.helpers.EndlessScrollListener;
import com.example.edwinmperazaduran.gridimagesearch.models.ImageFilter;
import com.example.edwinmperazaduran.gridimagesearch.models.ImageResult;
import com.example.edwinmperazaduran.gridimagesearch.net.SearchClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements
        ImageFilterDialog.ImageFilterDialogListener{

    private static int MAX_PAGE = 10;
    EditText etQuery;
    GridView gvResults;
    Button btnSearch;
    ArrayList<ImageResult> imageResults;
    ImageResultArrayAdapter imageAdapter;
    SearchClient client;
    int startPage = 1;
    String query;
    ImageFilter imageFilter = new ImageFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupViews();
    }

    public void setupViews(){
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                onImageSearch(1);
            }
        });

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(position);
                i.putExtra("result", imageResult);
                startActivity(i);
            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (page <= MAX_PAGE) {
                    onImageSearch((10*(page-1)) + 1);
                }
            }
        });

        imageResults = new ArrayList<>();
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.miAdvancedSearch) {
            FragmentManager fm = getSupportFragmentManager();
            ImageFilterDialog imageFilterDialog= ImageFilterDialog.newInstance(imageFilter);
            imageFilterDialog.show(fm, "fragment_image_filter");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onImageSearch(int start) {

        if (isNetworkAvailable()) {
            client = new SearchClient();
            query = etQuery.getText().toString();
            startPage = start;
            if (startPage == 1)
                imageAdapter.clear();

            if (!query.equals(""))
                client.getSearch(query, startPage, imageFilter, this, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                try {
                                    JSONArray imageJsonResults;
                                    if (response != null) {
                                        imageJsonResults = response.getJSONArray("items");
                                        imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), R.string.invalid_data, Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                            }
                        }
                );
            else {
                Toast.makeText(this, R.string.invalid_query, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    public void onFinishDialog(ImageFilter objFilter){
        imageFilter = objFilter;
        onImageSearch(1);
    }

    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}