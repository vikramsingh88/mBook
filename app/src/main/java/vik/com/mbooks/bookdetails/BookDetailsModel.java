package vik.com.mbooks.bookdetails;

import io.reactivex.Observable;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class BookDetailsModel implements IBookDetailsModel {
    @Override
    public Observable<BookDetails> getBookDetails(String bookId, String token) {
        return new BookDetailsRequestToServer(bookId, token).callService();
    }
}
