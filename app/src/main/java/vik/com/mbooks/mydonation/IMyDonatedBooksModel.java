package vik.com.mbooks.mydonation;

import io.reactivex.Observable;
import vik.com.mbooks.myrequest.MyRequestedBooksDetails;

/**
 * Created by M1032130 on 2/23/2018.
 */

public interface IMyDonatedBooksModel {
    Observable<MyRequestedBooksDetails> getMyDonatedBooks(String token);
}
