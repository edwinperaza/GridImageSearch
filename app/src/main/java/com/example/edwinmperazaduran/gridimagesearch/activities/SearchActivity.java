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

    private static int MAX_PAGE = 8;
    EditText etQuery;
    GridView gvResults;
    Button btnSearch;
    ArrayList<ImageResult> imageResults;
    ImageResultArrayAdapter imageAdapter;
    SearchClient client;
    int startPage = 0;
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
                onImageSearch(0);
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
                if (page < MAX_PAGE) {
                    onImageSearch(page - 1);
                }
            }
        });

        imageResults = new ArrayList<>();
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.miAdvancedSearch) {
            FragmentManager fm = getSupportFragmentManager();
            ImageFilterDialog imageFilterDialog= ImageFilterDialog.newInstance("Prueba", imageFilter);
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
            if (startPage == 0)
                imageAdapter.clear();

            if (!query.equals(""))
                client.getSearch(query, startPage, imageFilter, this, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                try {
                                    JSONArray imageJsonResults;
                                    if (response != null) {
                                        imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                                        imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), "Invalid data received", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Please enter a valid search query", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void onFinishDialog(ImageFilter objFilter){

        imageFilter = objFilter;
        onImageSearch(0);
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
