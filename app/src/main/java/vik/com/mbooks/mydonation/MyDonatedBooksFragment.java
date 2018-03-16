package vik.com.mbooks.mydonation;

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
import vik.com.mbooks.myrequest.MyRequestedBooksDetails;
import vik.com.mbooks.myrequest.MyRequestedBooksRecyclerViewAdapter;
import vik.com.mbooks.myrequest.MyRequestedBooksViewModel;
import vik.com.mbooks.myrequest.RequestedBook;
import vik.com.mbooks.userdetails.UserDetails;
import vik.com.mbooks.utils.BooksSharedPreferences;
import vik.com.mbooks.utils.Constants;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class MyDonatedBooksFragment extends Fragment {
    CompositeDisposable compositeDisposable;
    MyDonatedBooksViewModel myDonatedBooksViewModel;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MyDonatedBooksRecyclerViewAdapter myRequestedBooksRecyclerViewAdapter;
    private String loggedInUserId;

    public MyDonatedBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        myDonatedBooksViewModel = new MyDonatedBooksViewModel(getActivity());
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

        getMyDonatedBooks(BooksSharedPreferences.getToken(getActivity()));
        return view;
    }

    //get my requested books
    private void getMyDonatedBooks(final String authToken) {
        DisposableObserver disposableObserver = myDonatedBooksViewModel.getMyDonatedBooks(authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MyRequestedBooksDetails>() {
                    @Override
                    public void onNext(MyRequestedBooksDetails o) {
                        List<RequestedBook> requestedBooks = o.getRequestedBookList();
                        if (requestedBooks != null) {
                            myRequestedBooksRecyclerViewAdapter = new MyDonatedBooksRecyclerViewAdapter(requestedBooks, getActivity(),
                                    new MyDonatedBooksRecyclerViewAdapter.OnItemListener() {

                                @Override
                                public void onRequestedBook(String bookId, String requestedUser, MyDonatedBooksRecyclerViewAdapter.MyViewHolder holder) {
                                    getBookDetailsByBookId(bookId, BooksSharedPreferences.getToken(getActivity()), holder);
                                    if (!requestedUser.isEmpty()) {
                                        geBookRequestedUserDetails(requestedUser, authToken, holder.txtPublisher, holder.txtRequester);
                                    }
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
    private void getBookDetailsByBookId(String bookId, final String authToken, final MyDonatedBooksRecyclerViewAdapter.MyViewHolder holder) {
        DisposableObserver disposableObserver = myDonatedBooksViewModel.getBookDetails(bookId, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BookDetails>() {
                    @Override
                    public void onNext(BookDetails o) {
                        if (o != null && o.isSuccess()) {
                            Book book = o.getBook();
                            if (book != null) {
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
    private void geBookRequestedUserDetails(String userId, String authToken, final TextView donor, final TextView requester) {
        DisposableObserver disposableObserver = myDonatedBooksViewModel.getBookDonorDetail(userId, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserDetails>() {
                    @Override
                    public void onNext(UserDetails o) {
                        Log.d("User details", o.getMessage());
                        requester.setText("Requested By");
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