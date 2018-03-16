package vik.com.mbooks.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vik.com.mbooks.R;
import vik.com.mbooks.donatebook.DonateBookFragment;
import vik.com.mbooks.login.LoginActivity;
import vik.com.mbooks.login.response.User;
import vik.com.mbooks.main.nearbooks.NearBooksFragment;
import vik.com.mbooks.mydonation.MyDonatedBooksFragment;
import vik.com.mbooks.myrequest.MyRequestedBooksFragment;
import vik.com.mbooks.utils.BooksSharedPreferences;
import vik.com.mbooks.utils.Constants;
import vik.com.mbooks.utils.RoundedTransformation;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private TextView mTextViewName;
    private TextView mTextViewEmail;
    private ImageView mImageViewProfile;

    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Books Near Me");
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        if (user == null) {
            user = BooksSharedPreferences.getUserInfo(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        View header = mNavigationView.getHeaderView(0);
        mTextViewName = header.findViewById(R.id.txt_name);
        mTextViewEmail = header.findViewById(R.id.txt_email);
        mImageViewProfile = header.findViewById(R.id.image_profile);

        setHeaderDetails(user);

        //Set near books default select
        mNavigationView.setCheckedItem(R.id.nav_near_books);
        Fragment fragment = new NearBooksFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId", user.getId());
        fragment.setArguments(bundle);
        replaceFragment(fragment);
    }

    private void setHeaderDetails(User user) {
        mTextViewName.setText(user.getName());
        mTextViewEmail.setText(user.getEmail());
        String strProfilePicUrl = user.getProfilePic();
        if (strProfilePicUrl != null) {
            strProfilePicUrl = strProfilePicUrl.replace("localhost", Constants.IP);
            Picasso.with(this)
                    .load(strProfilePicUrl)
                    .transform(new RoundedTransformation(100, 4))
                    .resize(200, 200)
                    .centerCrop()
                    .into(mImageViewProfile);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_near_books) {
            mToolbar.setTitle("Books Near Me");
            Fragment fragment = new NearBooksFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userId", user.getId());
            fragment.setArguments(bundle);
            replaceFragment(fragment);
        } else if(id == R.id.nav_my_request) {
            mToolbar.setTitle("My Requests");
            Fragment fragment = new MyRequestedBooksFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userId", user.getId());
            fragment.setArguments(bundle);
            replaceFragment(fragment);
        } else if(id == R.id.nav_request_of_my_books) {
            mToolbar.setTitle("My Donation");
            Fragment fragment = new MyDonatedBooksFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userId", user.getId());
            fragment.setArguments(bundle);
            replaceFragment(fragment);
        } else if (id == R.id.nav_logout) {
            BooksSharedPreferences.saveRememberMe(this, false);
            Intent intentLogin = new Intent(this, LoginActivity.class);
            startActivity(intentLogin);
            finish();
        } else if(id == R.id.nav_donate_book) {
            mToolbar.setTitle("Donate Books");
            Fragment fragment = new DonateBookFragment();
            replaceFragment(fragment);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Change main content on drawer item selection
    private void replaceFragment(Fragment fragment) {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
}
