package com.example.edwinmperazaduran.gridimagesearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edwinmperazaduran.gridimagesearch.R;
import com.example.edwinmperazaduran.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        ImageView ivImage = (ImageView) findViewById(R.id.ivResult);
        TextView tvImageName = (TextView) findViewById(R.id.tvImageName);

        ImageResult imageResult = (ImageResult) getIntent().getSerializableExtra("result");
        String url = imageResult.getFullUrl();
        Picasso.with(this).load(url).into(ivImage);
        tvImageName.setText(imageResult.getTitle());
       // getActionBar().hide();
        getSupportActionBar().hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_display, menu);
        return true;
    }
}
