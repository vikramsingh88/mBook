package vik.com.mbooks.myrequest;

import io.reactivex.Observable;
import vik.com.mbooks.main.response.BooksResponse;
import vik.com.mbooks.retrofit.AppDataClient;
import vik.com.mbooks.retrofit.IAppDataAPIs;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class MyRequestedBooksRequestToServer {
    IAppDataAPIs mClient;
    String input;

    public MyRequestedBooksRequestToServer(String input) {
        mClient = AppDataClient.getClient();
        this.input = input;
    }

    public Observable<MyRequestedBooksDetails> callService() {

        Observable<MyRequestedBooksDetails> observable = mClient.getMyRequestedBooks(input);
        return observable;
    }
}
