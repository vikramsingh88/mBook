package vik.com.mbooks.bookdetails;

import io.reactivex.Observable;
import vik.com.mbooks.retrofit.AppDataClient;
import vik.com.mbooks.retrofit.IAppDataAPIs;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class BookDetailsRequestToServer {
    IAppDataAPIs mClient;
    String authHeader;
    String userId;

    public BookDetailsRequestToServer(String userId, String authHeader) {
        mClient = AppDataClient.getClient();
        this.userId = userId;
        this.authHeader = authHeader;
    }

    public Observable<BookDetails> callService() {

        Observable<BookDetails> observable = mClient.getBookDetailsByBookId(userId, authHeader);
        return observable;
    }
}
