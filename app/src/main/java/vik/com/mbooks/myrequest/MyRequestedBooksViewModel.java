package vik.com.mbooks.myrequest;

import android.content.Context;

import io.reactivex.Observable;
import vik.com.mbooks.bookdetails.BookDetails;
import vik.com.mbooks.bookdetails.BookDetailsModel;
import vik.com.mbooks.bookdetails.IBookDetailsModel;
import vik.com.mbooks.main.response.BooksResponse;
import vik.com.mbooks.requestbook.BookRequest;
import vik.com.mbooks.requestbook.IRequestBookModel;
import vik.com.mbooks.requestbook.RequestBookModel;
import vik.com.mbooks.retrofit.Response;
import vik.com.mbooks.userdetails.IUserDetailsModel;
import vik.com.mbooks.userdetails.UserDetails;
import vik.com.mbooks.userdetails.UserDetailsModel;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class MyRequestedBooksViewModel {
    IMyRequestedBooksModel requestBookModel;
    IBookDetailsModel bookDetailsModel;
    IUserDetailsModel userDetailsModel;
    Context mContext;

    public MyRequestedBooksViewModel(Context context) {
        requestBookModel = new MyRequestedBooksModel();
        bookDetailsModel = new BookDetailsModel();
        userDetailsModel = new UserDetailsModel();
        mContext = context;
    }

    public Observable<MyRequestedBooksDetails> getMyRequestedBooks(String authToken) {
        return requestBookModel.getMyRequestedBooks(authToken);
    }

    public Observable<BookDetails> getBookDetails(String bookId, String authToken) {
        return bookDetailsModel.getBookDetails(bookId, authToken);
    }

    public Observable<UserDetails> getBookDonorDetail(String userId, String authToken) {
        return userDetailsModel.getUserDetailsByUserId(userId, authToken);
    }
}
