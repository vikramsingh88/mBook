package vik.com.mbooks.requestbook;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class BookRequest {
    private String bookId;

    public BookRequest(String bookId) {
        this.bookId = bookId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
