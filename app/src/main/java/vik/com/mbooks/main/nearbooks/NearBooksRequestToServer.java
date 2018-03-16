package vik.com.mbooks.main.nearbooks;

import io.reactivex.Observable;
import vik.com.mbooks.main.response.BooksResponse;
import vik.com.mbooks.retrofit.AppDataClient;
import vik.com.mbooks.retrofit.IAppDataAPIs;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class NearBooksRequestToServer {
    IAppDataAPIs mClient;
    String input;

    public NearBooksRequestToServer(String input) {
        mClient = AppDataClient.getClient();
        this.input = input;
    }

    public Observable<BooksResponse> callService() {

        Observable<BooksResponse> observable = mClient.getBooksByLoginUserPincode(input);
        return observable;
    }
}
