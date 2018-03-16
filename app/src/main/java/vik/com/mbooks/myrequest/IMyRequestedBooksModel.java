package vik.com.mbooks.myrequest;

import io.reactivex.Observable;
import vik.com.mbooks.main.response.BooksResponse;

/**
 * Created by M1032130 on 2/23/2018.
 */

public interface IMyRequestedBooksModel {
    Observable<MyRequestedBooksDetails> getMyRequestedBooks(String token);
}
