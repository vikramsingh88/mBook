package vik.com.mbooks.mydonation;

import io.reactivex.Observable;
import vik.com.mbooks.myrequest.MyRequestedBooksDetails;
import vik.com.mbooks.retrofit.AppDataClient;
import vik.com.mbooks.retrofit.IAppDataAPIs;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class MyDonatedBooksRequestToServer {
    IAppDataAPIs mClient;
    String input;

    public MyDonatedBooksRequestToServer(String input) {
        mClient = AppDataClient.getClient();
        this.input = input;
    }

    public Observable<MyRequestedBooksDetails> callService() {

        Observable<MyRequestedBooksDetails> observable = mClient.getDonatedBooks(input);
        return observable;
    }
}
