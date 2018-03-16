package vik.com.mbooks.mydonation;

import io.reactivex.Observable;
import vik.com.mbooks.myrequest.MyRequestedBooksDetails;
import vik.com.mbooks.myrequest.MyRequestedBooksRequestToServer;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class MyDonatedBooksModel implements IMyDonatedBooksModel {

    @Override
    public Observable<MyRequestedBooksDetails> getMyDonatedBooks(String token) {
        return new MyDonatedBooksRequestToServer(token).callService();
    }
}
