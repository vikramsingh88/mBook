package vik.com.mbooks.donatebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import vik.com.mbooks.R;
import vik.com.mbooks.donatebook.request.DonateBookRequest;
import vik.com.mbooks.retrofit.Response;
import vik.com.mbooks.utils.BooksSharedPreferences;
import vik.com.mbooks.utils.CompressImage;
import vik.com.mbooks.utils.CustomDialog;
import vik.com.mbooks.utils.CustomProgressDialog;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class DonateBookFragment extends Fragment implements View.OnClickListener {
    CompositeDisposable compositeDisposable;
    DonateBookViewModel donateBookViewModel;
    private Uri mBookImagePath;

    private Button mBtnCaptureImage;
    private ImageView mImageViewBook;
    private EditText mEditTextBookTitle;
    private EditText mEditTextBookAuthor;
    private EditText mEditTextBookQuantity;
    private ProgressDialog mProgressDialog;

    String[] permissions = {WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE};
    private static final int PERMISSION_REQUEST_CODE = 3000;
    private static final int CAMERA_REQUEST = 1000;

    public DonateBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        compositeDisposable = new CompositeDisposable();
        donateBookViewModel = new DonateBookViewModel(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donate_book, container, false);
        mProgressDialog = CustomProgressDialog.getProgressDialog(getActivity(), "Donating...");
        mBtnCaptureImage = view.findViewById(R.id.btn_capture);
        mImageViewBook = view.findViewById(R.id.img_book);
        mEditTextBookTitle = view.findViewById(R.id.edit_book_title);
        mEditTextBookAuthor = view.findViewById(R.id.edit_book_author);
        mEditTextBookQuantity = view.findViewById(R.id.edit_book_quantity);
        mBtnCaptureImage.setOnClickListener(this);

        return view;
    }

    private String getBookTitle() {
        return mEditTextBookTitle.getText().toString();
    }

    private String getBookAuthor() {
        return mEditTextBookAuthor.getText().toString();
    }

    private String getBookQuantity() {
        return mEditTextBookQuantity.getText().toString();
    }

    private Uri getBookImagePath() {
        return mBookImagePath;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.donate, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_donate) {
            if (getBookTitle() != null &&
                    getBookAuthor() != null &&
                    getBookQuantity() != null &&
                    getBookImagePath() != null) {
                DonateBookRequest donateBookRequest = new DonateBookRequest(
                        getBookTitle(),
                        getBookAuthor(),
                        getBookQuantity(),
                        getBookImagePath());
                donateBooks(donateBookRequest, BooksSharedPreferences.getToken(getActivity()));
            } else {
                Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    //check permissions
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean locationAccepted1 = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && locationAccepted1) {

                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) && shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                                CustomDialog.showMessageOKCancel(getActivity(),"You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(permissions, PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (!checkPermission()) {
            requestPermission();
        } else {
            switch (view.getId()) {
                case R.id.btn_capture:
                    launchCamera();
                    break;
            }
        }
    }

    private void launchCamera() {
        File imageFile = new File(getFilePath());
        mBookImagePath = Uri.fromFile(imageFile);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mBookImagePath);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private File createFolder() {
        String folder_books = "mBooks";
        File folder = new File(Environment.getExternalStorageDirectory(), folder_books);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    private String getFilePath(){
        File outputFile = new File(createFolder(), "book_" + currentDateFormat() + ".png");
        return outputFile.getPath();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            mImageViewBook.setVisibility(View.VISIBLE);
            //compress image
            CompressImage compressImage = new CompressImage();
            String strCompressedImage = compressImage.compressImage(getActivity(), mBookImagePath);
            strCompressedImage = "file://"+strCompressedImage;
            deleteHDImage();
            mBookImagePath = Uri.parse(strCompressedImage);
            Log.d("Compressed Image", strCompressedImage);
            Picasso.with(getActivity())
                    .load(mBookImagePath)
                    .into(mImageViewBook);
        }
    }

    private void deleteHDImage() {
        File fDelete = new File(mBookImagePath.getPath());
        if (fDelete.exists()) {
            fDelete.delete();
        }
    }

    //get books by logged in user pincode meaning nearby available books
    private void donateBooks(DonateBookRequest donateBookRequest, String authToken) {
        mProgressDialog.show();
        DisposableObserver disposableObserver = donateBookViewModel.donateBook(donateBookRequest, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response>() {
                    @Override
                    public void onNext(Response o) {
                        Log.d("Donate books fragment", o.getMessage());
                        Toast.makeText(getActivity(), "Thank you for donating book", Toast.LENGTH_SHORT).show();
                        reset();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                    }
                });
        compositeDisposable.add(disposableObserver);
    }

    private void reset() {
        mEditTextBookTitle.setText("");
        mEditTextBookAuthor.setText("");
        mEditTextBookQuantity.setText("");
        mImageViewBook.setVisibility(View.GONE);
        mBookImagePath = null;
    }
}