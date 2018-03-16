package vik.com.mbooks.main.nearbooks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import vik.com.mbooks.DisplayImageActivity;
import vik.com.mbooks.R;
import vik.com.mbooks.main.response.Book;
import vik.com.mbooks.main.response.BooksResponse;
import vik.com.mbooks.requestbook.BookRequest;
import vik.com.mbooks.retrofit.Response;
import vik.com.mbooks.userdetails.UserDetails;
import vik.com.mbooks.utils.BooksSharedPreferences;
import vik.com.mbooks.utils.CustomProgressDialog;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class NearBooksFragment extends Fragment {
    CompositeDisposable compositeDisposable;
    NearBooksViewModel nearBooksViewModel;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    NearBooksRecyclerViewAdapter mNearBooksAdapter;
    private ProgressDialog mProgressDialog;
    private String loggedInUserId;

    public NearBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        nearBooksViewModel = new NearBooksViewModel(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_near_books, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_books);
        mRecyclerView.setHasFixedSize(true);

        Bundle bundle=getArguments();
        loggedInUserId = bundle.getString("userId", null);
        mProgressDialog = CustomProgressDialog.getProgressDialog(getActivity(), "Requesting...");

        getBooksByLoginUserPincode(BooksSharedPreferences.getToken(getActivity()));
        return view;
    }

    //get books by logged in user pincode meaning nearby available books
    private void getBooksByLoginUserPincode(String authToken) {
        DisposableObserver disposableObserver = nearBooksViewModel.getBooksByLoginUserPincode(authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BooksResponse>() {
                    @Override
                    public void onNext(BooksResponse o) {
                        Log.d("MainActivity", o.getMessage());
                        List<Book> books = o.getBooks();
                        //filter near by books by donor itself
                        List<Book> filteredBooks = new ArrayList<>();
                        for (Book book : books) {
                            if (!loggedInUserId.equals(book.getUserId())) {
                                filteredBooks.add(book);
                            }
                        }

                        if (books != null) {
                            mNearBooksAdapter = new NearBooksRecyclerViewAdapter(filteredBooks,getActivity(), new NearBooksRecyclerViewAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Book item) {
                                    //check if book is donated by logged in user then no need to request for book
                                    if (item.getUserId().equals(loggedInUserId)) {
                                        Toast.makeText(getActivity(), "You are the donor of this book, so no need to request", Toast.LENGTH_SHORT).show();
                                    } else {
                                        requestBook(item.getBookId(), BooksSharedPreferences.getToken(getActivity()));
                                    }
                                }

                                @Override
                                public void onBookImageClick(String bookTitle, String bookImagePath) {
                                    Intent intentDisplayImage = new Intent(getActivity(), DisplayImageActivity.class);
                                    intentDisplayImage.putExtra("bookTitle", bookTitle);
                                    intentDisplayImage.putExtra("bookImagePath", bookImagePath);
                                    startActivity(intentDisplayImage);
                                }

                                @Override
                                public void onUserDetails(String userId, TextView donor) {
                                    geBookDonorDetails(userId, BooksSharedPreferences.getToken(getActivity()), donor);
                                }
                            });
                            mRecyclerView.setAdapter(mNearBooksAdapter);
                            mLayoutManager = new LinearLayoutManager(getActivity());
                            mRecyclerView.setLayoutManager(mLayoutManager);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposableObserver);
    }

    //request book to donor
    private void requestBook(String bookId, String authToken) {
        BookRequest bookRequest = new BookRequest(bookId);
        mProgressDialog.show();
        DisposableObserver disposableObserver = nearBooksViewModel.requestBookByBookId(bookRequest, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response>() {
                    @Override
                    public void onNext(Response o) {
                        Log.d("Book Requested", o.getMessage());
                        if (o.isSuccess()) {
                            Toast.makeText(getActivity(), o.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), o.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        Toast.makeText(getActivity(), "Error in requesting book, please try again", Toast.LENGTH_SHORT).show();
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

    //user book donor details
    private void geBookDonorDetails(String userId, String authToken, final TextView donor) {
        DisposableObserver disposableObserver = nearBooksViewModel.getBookDonorDetail(userId, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserDetails>() {
                    @Override
                    public void onNext(UserDetails o) {
                        Log.d("User details", o.getMessage());
                        donor.setText(o.getUser().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposableObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
