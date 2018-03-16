package vik.com.mbooks.requestbook;

import io.reactivex.Observable;
import vik.com.mbooks.retrofit.Response;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class RequestBookModel implements IRequestBookModel {
    @Override
    public Observable<Response> requestBookByBookId(BookRequest bookRequest, String token) {
        return new BookRequestToServer(bookRequest, token).callService();
    }
}
