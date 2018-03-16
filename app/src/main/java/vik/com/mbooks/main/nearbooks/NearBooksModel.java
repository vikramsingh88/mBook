package vik.com.mbooks.main.nearbooks;

import io.reactivex.Observable;
import vik.com.mbooks.main.response.BooksResponse;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class NearBooksModel implements INearBooksModel {
    @Override
    public Observable<BooksResponse> getBooksByLoginUserPincode(String token) {
        return new NearBooksRequestToServer(token).callService();
    }
}
