package vik.com.mbooks.requestbook;

import io.reactivex.Observable;
import vik.com.mbooks.retrofit.AppDataClient;
import vik.com.mbooks.retrofit.IAppDataAPIs;
import vik.com.mbooks.retrofit.Response;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class BookRequestToServer {
    IAppDataAPIs mClient;
    String authHeader;
    BookRequest bookRequest;

    public BookRequestToServer(BookRequest bookRequest, String authHeader) {
        mClient = AppDataClient.getClient();
        this.bookRequest = bookRequest;
        this.authHeader = authHeader;
    }

    public Observable<Response> callService() {

        Observable<Response> observable = mClient.requestBookByBookId(bookRequest, authHeader);
        return observable;
    }
}
