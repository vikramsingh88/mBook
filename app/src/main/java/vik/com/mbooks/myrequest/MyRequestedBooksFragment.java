package vik.com.mbooks.myrequest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import vik.com.mbooks.R;
import vik.com.mbooks.bookdetails.BookDetails;
import vik.com.mbooks.main.response.Book;
import vik.com.mbooks.userdetails.UserDetails;
import vik.com.mbooks.utils.BooksSharedPreferences;
import vik.com.mbooks.utils.Constants;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class MyRequestedBooksFragment extends Fragment {
    CompositeDisposable compositeDisposable;
    MyRequestedBooksViewModel myRequestedBooksViewModel;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MyRequestedBooksRecyclerViewAdapter myRequestedBooksRecyclerViewAdapter;
    private String loggedInUserId;

    public MyRequestedBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        myRequestedBooksViewModel = new MyRequestedBooksViewModel(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_requested_books, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_books);
        mRecyclerView.setHasFixedSize(true);

        Bundle bundle=getArguments();
        loggedInUserId = bundle.getString("userId", null);

        getMyRequestedBooks(BooksSharedPreferences.getToken(getActivity()));
        return view;
    }

    //get my requested books
    private void getMyRequestedBooks(String authToken) {
        DisposableObserver disposableObserver = myRequestedBooksViewModel.getMyRequestedBooks(authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MyRequestedBooksDetails>() {
                    @Override
                    public void onNext(MyRequestedBooksDetails o) {
                        List<RequestedBook> requestedBooks = o.getRequestedBookList();
                        if (requestedBooks != null) {
                            myRequestedBooksRecyclerViewAdapter = new MyRequestedBooksRecyclerViewAdapter(requestedBooks, getActivity(),
                                    new MyRequestedBooksRecyclerViewAdapter.OnItemListener() {

                                @Override
                                public void onRequestedBook(String bookId, MyRequestedBooksRecyclerViewAdapter.MyViewHolder holder) {
                                    getBookDetailsByBookId(bookId, BooksSharedPreferences.getToken(getActivity()), holder);
                                }
                            });
                            mRecyclerView.setAdapter(myRequestedBooksRecyclerViewAdapter);
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

    //get book details by book id
    private void getBookDetailsByBookId(String bookId, final String authToken, final MyRequestedBooksRecyclerViewAdapter.MyViewHolder holder) {
        DisposableObserver disposableObserver = myRequestedBooksViewModel.getBookDetails(bookId, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BookDetails>() {
                    @Override
                    public void onNext(BookDetails o) {
                        if (o != null && o.isSuccess()) {
                            Book book = o.getBook();
                            if (book != null) {
                                geBookDonorDetails(book.getUserId(), authToken, holder.txtPublisher);
                                holder.txtPublishedDate.setText(book.getBookAuther());
                                holder.txtQuantity.setText(book.getBookQuantity());
                                holder.txtBookTitle.setText(book.getBookTitle());
                                setBookImage(book.getBookImagePath(), holder.imgBook);
                            }
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

    //user book donor details
    private void geBookDonorDetails(String userId, String authToken, final TextView donor) {
        DisposableObserver disposableObserver = myRequestedBooksViewModel.getBookDonorDetail(userId, authToken)
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

    private void setBookImage(String path, ImageView imageView) {
        path = path.replace("localhost", Constants.IP);
        Picasso.with(getActivity())
                .load(path)
                .into(imageView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}