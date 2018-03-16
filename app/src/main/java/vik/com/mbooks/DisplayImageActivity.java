package vik.com.mbooks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import vik.com.mbooks.utils.Constants;

public class DisplayImageActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        mImageView = findViewById(R.id.img_full_book);
        mToolbar = findViewById(R.id.toolbar);

        Intent intent = getIntent();
        String bookTitle = intent.getStringExtra("bookTitle");
        String bookImagePath = intent.getStringExtra("bookImagePath");
        setBookImage(bookImagePath);
        mToolbar.setTitle(bookTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setBookImage(String path) {
        path = path.replace("localhost", Constants.IP);
        Picasso.with(this)
                .load(path)
                .into(mImageView);
    }
}
