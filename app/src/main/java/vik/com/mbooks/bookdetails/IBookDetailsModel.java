package vik.com.mbooks.bookdetails;

import io.reactivex.Observable;

/**
 * Created by M1032130 on 2/26/2018.
 */

public interface IBookDetailsModel {
    Observable<BookDetails> getBookDetails(String bookId, String token);
}
