package vik.com.mbooks.main.nearbooks;

import android.content.Context;

import io.reactivex.Observable;
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

public class NearBooksViewModel {
    INearBooksModel nearBooksModel;
    IUserDetailsModel userDetailsModel;
    IRequestBookModel requestBookModel;
    Context mContext;

    public NearBooksViewModel(Context context) {
        nearBooksModel = new NearBooksModel();
        userDetailsModel = new UserDetailsModel();
        requestBookModel = new RequestBookModel();
        mContext = context;
    }

    public Observable<BooksResponse> getBooksByLoginUserPincode(String authToken) {
        return nearBooksModel.getBooksByLoginUserPincode(authToken);
    }

    public Observable<UserDetails> getBookDonorDetail(String userId, String authToken) {
        return userDetailsModel.getUserDetailsByUserId(userId, authToken);
    }

    public Observable<Response> requestBookByBookId(BookRequest bookRequest, String authToken) {
        return requestBookModel.requestBookByBookId(bookRequest, authToken);
    }
}
