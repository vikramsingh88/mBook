package vik.com.mbooks.mydonation;

import android.content.Context;

import io.reactivex.Observable;
import vik.com.mbooks.bookdetails.BookDetails;
import vik.com.mbooks.bookdetails.BookDetailsModel;
import vik.com.mbooks.bookdetails.IBookDetailsModel;
import vik.com.mbooks.myrequest.IMyRequestedBooksModel;
import vik.com.mbooks.myrequest.MyRequestedBooksDetails;
import vik.com.mbooks.myrequest.MyRequestedBooksModel;
import vik.com.mbooks.userdetails.IUserDetailsModel;
import vik.com.mbooks.userdetails.UserDetails;
import vik.com.mbooks.userdetails.UserDetailsModel;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class MyDonatedBooksViewModel {
    IMyDonatedBooksModel donatedBookModel;
    IBookDetailsModel bookDetailsModel;
    IUserDetailsModel userDetailsModel;
    Context mContext;

    public MyDonatedBooksViewModel(Context context) {
        donatedBookModel = new MyDonatedBooksModel();
        bookDetailsModel = new BookDetailsModel();
        userDetailsModel = new UserDetailsModel();
        mContext = context;
    }

    public Observable<MyRequestedBooksDetails> getMyDonatedBooks(String authToken) {
        return donatedBookModel.getMyDonatedBooks(authToken);
    }

    public Observable<BookDetails> getBookDetails(String bookId, String authToken) {
        return bookDetailsModel.getBookDetails(bookId, authToken);
    }

    public Observable<UserDetails> getBookDonorDetail(String userId, String authToken) {
        return userDetailsModel.getUserDetailsByUserId(userId, authToken);
    }
}
